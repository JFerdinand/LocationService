package com.lingtu.controller;

import com.lingtu.interceptor.functionId;
import com.lingtu.service.AndroidLoginService;
import com.lingtu.util.MD5;
import com.lingtu.util.MemcacheTest;
import com.lingtu.util.ToStringUtil;
import com.lingtu.util.jsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AndroidLoginController {
	@Resource(name="androidloginservice")
	AndroidLoginService service;
		
		/**
		 * Android:手机端对接受回来的用于名称和密码进行处理
		 * http:192.168.1.101:8080/lingtuapp/androidlogin.do
		 */
		@functionId("2")
		@RequestMapping("/interface/androidlogin.do")
		public String loginAndroid(HttpServletRequest request,HttpServletResponse response)
			throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset-utf-8");
			request.setCharacterEncoding("utf-8");
			
			String userMessage=request.getParameter("username");//用户名或手机号
			String userregex = "^1(3|4|5|7|8)\\d{9}$";
			Pattern patt= Pattern.compile(userregex);
			Matcher matcher = patt.matcher(userMessage);
	        String userPassword=request.getParameter("pass");
	      
			//MD进行加密
			String newPassword=MD5.MD5Encode("10001111111000"+userPassword);
			if(matcher.matches()==true){
				response.getWriter().print(jsonUtil.toJson( service.selectByPhoneAndroid(userMessage, newPassword)));
			}else{
				response.getWriter().print(jsonUtil.toJson( service.selectUserAndroid(userMessage, newPassword)));
			}
			return null;
		}
		
		/**
		 * Android:找回密码：接收手机端的用户名或者手机号，并请信息返回给Android
		 * 
		 */
		@SuppressWarnings("unused")
		@functionId("2")
		@RequestMapping("/interface/androidPhone.do")
		public ModelAndView lookPhonePass(HttpServletRequest request,HttpServletResponse response)
			throws ServletRequestBindingException,UnsupportedEncodingException,IOException{		
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset-utf-8");
			request.setCharacterEncoding("utf-8");
			
			HttpSession  session = request.getSession();
			session.setAttribute("sessionID", session.getId());
			String user=request.getParameter("user");//接收手机号或者用户名
			
			//用户名的正则表达式
	        String userregex="^[a-zA-Z\u4e00-\u9fa5]{1}[a-zA-Z0-9_\u4e00-\u9fa5]{1,14}$";
	        Pattern patt= Pattern.compile(userregex);
			Matcher matcher = patt.matcher(user);
			Map map =  new HashMap<String, Object>();
			if(user!=null&&matcher.matches()==true){
				//调用session缓存储数据
				session.setAttribute("User",user);
				session.setAttribute("mark",0);
				//调用传输用户名的方法	
				map= service.selectUserNameAndroid(user);		 
			}else if(user!=null&&user.length()==11){
				//调用session的缓存储数据
				session.setAttribute("User",user);
				session.setAttribute("mark",1);
				//调用传输手机号的方法	
				map= service.selectUserPhoneAndroid(user);
			}
			response.getWriter().print(jsonUtil.toJson(map));//返回值		
			return null;
		}
		/**
		 * Android:接收手机端获取验证码的请求
		 * 
		 */
		//@SuppressWarnings("unused")
		@functionId("2")
		@RequestMapping("/interface/PhonePass.do")
		public ModelAndView lastpassWord(HttpServletRequest request,HttpServletResponse response)
			throws ServletRequestBindingException,UnsupportedEncodingException,IOException{		
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset-utf-8");
			request.setCharacterEncoding("utf-8");

			HttpSession session = request.getSession();
			String sessionid =request.getRequestedSessionId();
		    //调用生成验证码的方法，返回一个String
			String ident =ToStringUtil.Identiy();
			//调用Memcache的缓存储数据
			MemcacheTest.setData(sessionid, ident, new Date(120000));
			int code=1;
			//将验证码存在map中
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ident",ident);
			map.put("code", code);
			//返回值
			response.getWriter().print(jsonUtil.toJson(map));
			
			return null;
		}
		
		/**
		 * Android:对验证码进行判断
		 * 
		 */
		//@SuppressWarnings("unused")
		@functionId("2")
		@RequestMapping("/interface/judgePhonePass.do")
		public ModelAndView panduanpassWord(HttpServletRequest request,HttpServletResponse response)
			throws ServletRequestBindingException,UnsupportedEncodingException,IOException{		
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset-utf-8");
			request.setCharacterEncoding("utf-8");
			
			HttpSession session = request.getSession();
			String sessionID=(String) session.getAttribute("sessionID");
			String sessionid =request.getRequestedSessionId();

			if(sessionID.equals(sessionid)){
				//调用Memcache的getData方法，获取值
			    String ident=(String)MemcacheTest.getData(sessionid);
			    String idnum =request.getParameter("code");				
			    int code=1;
				//将验证码存在map中
				Map<String, Object> map = new HashMap<String, Object>();
				if(ident.equals(idnum)){
					code=1;
					map.put("result", "正确");
					map.put("code", 1);
				}else{
					code=2;
					map.put("result", "您输入的验证码不正确");
					map.put("code", 2);
				}
				response.getWriter().print(jsonUtil.toJson(map));
			}else{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("result", "您输入的验证码不正确");
				map.put("code", 2);
				response.getWriter().print(jsonUtil.toJson(map));
			}
			return null;
		}
		/**
		 * Android:接收手机端输入的密码
		 * 
		 */
		//@SuppressWarnings("unused")//屏蔽java的一些警告信息
		@functionId("2")
		@RequestMapping("/interface/androidIdentifyingCode.do")
		public ModelAndView androidPhonePassWord(HttpServletRequest request,HttpServletResponse response)
			throws ServletRequestBindingException,UnsupportedEncodingException,IOException{		
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset-utf-8");
			request.setCharacterEncoding("utf-8");
			
			HttpSession session = request.getSession();
			String user =(String) session.getAttribute("User");
			int mark =Integer.parseInt(String.valueOf(session.getAttribute("mark")));
			//获取新密码
			String userPwd = request.getParameter("phonePass");
			String pass = MD5.MD5Encode("10001111111000"+userPwd);
			Map map =new HashMap();
			if(mark==0){
				//调用根据用户名修改密码的方法
				map = service.updatePassWordUserName(user, pass);
			}else if(mark==1){
				//调用根据手机号修改密码的方法
				map = service.updatePassWordPhone(user, pass);
			}			
			response.getWriter().print(jsonUtil.toJson(map));//返回值	
			return null;
		}

}
