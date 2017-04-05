package com.lingtu.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lingtu.interceptor.functionId;
import com.lingtu.service.IndexCenterService;
import com.lingtu.util.MD5;
import com.lingtu.util.MemcacheTest;
import com.lingtu.util.ToStringUtil;
import com.lingtu.util.jsonUtil;
import com.lingtu.util.sendMail;

@Controller
public class IndexCenterController {
	@Resource(name="indexcenterservice")
	IndexCenterService service;

	@RequestMapping("/index/setinfor.do")
	public ModelAndView peopleCenter(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{	 
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");		
		return new ModelAndView("WEB-INF/jsp/setinformation.jsp");
	}	
	
	/**
	 * 加载页面:接收验证码的请求
	 * 
	 */
	@functionId("1")
	@SuppressWarnings("deprecation")
	@RequestMapping("/index/sendPhoneCode.do")
	public ModelAndView sendJspIdentify(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{	 
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");		
		
		//随机产生6位数的验证码
		String ident =ToStringUtil.Identiy();
		System.out.println("验证码:"+ident);
		//创建一个session缓存
		MemcacheTest.setData("ident", ident, new Date(120000));//设置验证码有效期的时间为1分钟
	    response.getWriter().print(jsonUtil.toJson(ident));
	    return null;
	}
	
	/**
	 * 接收手机号，判断该手机号是否已被使用
	 * 
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/index/getphone.do")
	public ModelAndView getPhone(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{	 
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		
		String newPhone =request.getParameter("newPhone");
		int num = service.selectPhone2(newPhone);
		response.getWriter().print(jsonUtil.toJson(num));
	    return null;
	}
	
	/**
	 * 加载页面:设置手机验证码的有效期,并将验证码返回
	 * 
	 */
	@RequestMapping("/index/newPsassPhone.do")
	public ModelAndView newPhone(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{	
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String newPhone =request.getParameter("newPhone");
		Long userid =(Long) session.getAttribute("userid");
		
		String idnum = (String) MemcacheTest.getData("ident");
		
		int num =service.updateById(userid, newPhone);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("idnum", idnum);
		map.put("num", num);
		response.getWriter().print(jsonUtil.toJson(map));
		return null;
	}	
	
	/**
	 * 个人中心设置：需要id；根据手机号码重置密码
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("/index/newPsass.do")
	public ModelAndView newPsass(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{	
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		
		
		String userPhone =request.getParameter("newPhone");					
		String passWord =request.getParameter("newpass");
		String identif =request.getParameter("identify1");
		//MD进行加密
		passWord=MD5.MD5Encode("10001111111000"+passWord);		
		
		HttpSession session = request.getSession();		
		Long userid =(Long) session.getAttribute("userid");		
		String idnum = (String) MemcacheTest.getData("ident");
		
		HashMap map =service.selectUserinfor3(userid);
	     if(map.get("USERPHONE").equals(userPhone)){
	    	 if(identif.equals(idnum)){
	    		 if(!map.get("USERPWD").equals(passWord)){
	    			int num= service.updateByIdPhone(userPhone, passWord);
	    			session.invalidate();
	    			response.getWriter().print(num); 
	    		 }else{
	    			 response.getWriter().print(2);  
	    		 }
	    	 }else{
	    		 response.getWriter().print(3); 
	    	 }
	     }else{
	    	 response.getWriter().print(4);
	     }
		
		return null;
	}
	
	/**
	 * 加载忘记密码页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@functionId("1")
	@RequestMapping("/set/password.do")
	public ModelAndView setPassWord(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{	 
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");		
		return new ModelAndView("WEB-INF/jsp/setPassWord.jsp");
	}
	
	/**
	 * 忘记密码，不需要id；根据手机号号码修改密码
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@functionId("1")
	@RequestMapping("/set/bypass.do")
	public ModelAndView setPass(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{	 
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");		
		
		String userPhone =request.getParameter("newPhone");					
		String passWord =request.getParameter("newpass");	
		//MD进行加密
		passWord=MD5.MD5Encode("10001111111000"+passWord);		
		HttpSession session = request.getSession();			
		String idnum = (String) MemcacheTest.getData("ident");
	    
		int num =service.updateToPass(userPhone, passWord);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("idnum", idnum);
		map.put("num", num);
		
		response.getWriter().print(jsonUtil.toJson(map));
		return null;
	}
	
}

