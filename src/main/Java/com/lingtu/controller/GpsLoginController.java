package com.lingtu.controller;

import com.lingtu.interceptor.functionId;
import com.lingtu.service.UserLoginService;
import com.lingtu.util.MD5;
import com.lingtu.util.jsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



@Controller
public class GpsLoginController extends MultiActionController {
	@Resource(name="userloginservice")
	UserLoginService service;
	// ===============================JSP接口===========================================
	/**
	 * 加载页面:登录
	 * 1
	 */
	@functionId("1")
	@RequestMapping("/login/login.do")
	public ModelAndView loginMap(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		return new ModelAndView("WEB-INF/jsp/loginlocation.jsp");
	}
	
	/**
	 * 对接受回来的用于名称和密码进行处理
	 * 1
	 */
	@functionId("1")
	@RequestMapping("/login/userinfor.do")
	public ModelAndView loginMap1(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		String userInformation=request.getParameter("username");//用户名或者手机号
		String userregex = "^1(3|4|5|7|8)\\d{9}$";
		Pattern patt= Pattern.compile(userregex);
		Matcher matcher = patt.matcher(userInformation);
		String userPassword=request.getParameter("pass");
		System.out.println(userInformation+"==="+userPassword);
		//MD进行加密
		String newPassword=MD5.MD5Encode("10001111111000"+userPassword);
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(30*60);
		Map map = new HashMap();
		if(matcher.matches()==true){
			map =service.selectUserPhone(userInformation, newPassword);
			if(map.size() !=1){
				HashSet<Integer> set =service.selectPrivilegeId((Long)map.get("USERID"));
				List list=new ArrayList();
				if(Integer.parseInt(String.valueOf(map.get("roleid")))==2){
				     list = service.selectByCompanyIdToDeviced(Integer.parseInt(String.valueOf(map.get("COMPANYID"))));
				}else if(Integer.parseInt(String.valueOf(map.get("roleid")))==3){
					 list =service.selectByDeviced((Long)map.get("USERID"));
				}
				session.setAttribute("deviceid", list);
				session.setAttribute("privilegeid", set);
				session.setAttribute("userid", map.get("USERID"));
				session.setAttribute("companyid", map.get("COMPANYID"));
				session.setAttribute("username", map.get("USERNAME"));
			}
			response.getWriter().print(jsonUtil.toJson(map));
		}else{
			map=service.selectUserName(userInformation, newPassword);
			if(map.size() !=1){
				HashSet<Integer> set =service.selectPrivilegeId((Long)map.get("USERID"));
				List<String> list=new ArrayList<String>();
				if(Integer.parseInt(String.valueOf(map.get("roleid")))==2){
				     list = service.selectByCompanyIdToDeviced(Integer.parseInt(String.valueOf(map.get("COMPANYID"))));
				}else if(Integer.parseInt(String.valueOf(map.get("roleid")))==3){
					 list =service.selectByDeviced((Long)map.get("USERID"));
				}
				session.setAttribute("deviceid", list);
				session.setAttribute("privilegeid", set);
				session.setAttribute("userid", map.get("USERID"));
				session.setAttribute("companyid", map.get("COMPANYID"));
				session.setAttribute("username", map.get("USERNAME"));
			}			
			response.getWriter().print(jsonUtil.toJson(map));	
		}
		return null;
	}
	
}

