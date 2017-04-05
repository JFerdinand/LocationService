package com.lingtu.controller;

import com.lingtu.service.userManagerService;
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
import java.util.HashMap;
import java.util.List;

@Controller
public class UserManagerController {
	@Resource(name="usermanagerservice")
	userManagerService  usermanager ;
	/**
	 * 根据公司id查询该公司的所有用户的权限和用户名
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("/user/authoritymanager.do")
	public ModelAndView peopleCenter(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{	 
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");	
		
		HttpSession session = request.getSession();
		int companyid =Integer.parseInt(String.valueOf(session.getAttribute("companyid")));
		List<HashMap> list =usermanager.userToComanyId(companyid);
		response.getWriter().print(jsonUtil.toJson(list));
		return null;
	}
	/**
	 * 根据id和权限对用户的权限进行修改
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("/user/authorityrevision.do")
	public ModelAndView userAuthority(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{	 
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");	
		
		int roleid=Integer.parseInt(request.getParameter("roleid"));
		String userid =request.getParameter("userid");
		usermanager.updateToAutroityByUserId(userid,roleid);
		response.getWriter().print(1);
		return null;
	}
}
