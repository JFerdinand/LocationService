package com.lingtu.controller;

import com.lingtu.entity.TabDevelopment;
import com.lingtu.service.DevelopmentService;
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
import java.util.Map;

@Controller
public class AllocationController {
	
	@Resource(name="DevelopmentService")
	DevelopmentService service;
	
	/**
	 * 加载页面：部门组管理
	 */
	@RequestMapping("/allocation/manage.do")
	public ModelAndView manage(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		return new ModelAndView("WEB-INF/jsp/allocation.jsp");
	}
	
	/**
	 * 查询部门组信息
	 */
	@RequestMapping("/allocation/development.do")
	public ModelAndView development(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		int companyid = Integer.parseInt(String.valueOf(session.getAttribute("companyid")));
		List<Map<String, Object>> development = service.selectAllDevelopment(companyid);
		Map<String,Object> mp = new HashMap<String,Object>();
		mp.put("developmentList", development);
		response.getWriter().print(jsonUtil.toJson(mp));
		//System.out.println(jsonUtil.toJson(mp));
		return null;
	}
	/**
	 * 删除部门组
	 */
	@RequestMapping("/department/deldem.do")
	public ModelAndView deldem(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		int developid = Integer.parseInt(request.getParameter("developid"));
		Map mp = service. deleteIsDevelopment(developid);
//		System.out.println(jsonUtil.toJson(mp));
		response.getWriter().print(jsonUtil.toJson(mp));
		return null;
	}
	/**
	 * 确认删除部门组
	 */
	@RequestMapping("/department/delverify.do")
	public ModelAndView delverify(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		int developid = Integer.parseInt(request.getParameter("developid"));
		int result = service.deleteDevelopment(developid);
		response.getWriter().print(result);
		return null;
	}
	/**
	 * 修改部门组
	 */
	@RequestMapping("/department/editdem.do")
	public ModelAndView editdem(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		TabDevelopment development =new TabDevelopment();
		development.setDevelopid(Integer.parseInt(request.getParameter("developid")));
		development.setDevelopment(request.getParameter("development"));
		int parentid = Integer.parseInt(request.getParameter("parentid"));
		if(parentid!=0){
			development.setParentid(parentid);
		}
		HttpSession session = request.getSession();
		int companyid = Integer.parseInt(String.valueOf(session.getAttribute("companyid")));
		development.setCompanyid(companyid);
		int result = service.updateDevelopment(development);
		response.getWriter().print(result);
		return null;
	}
	/**
	 * 添加部门组
	 */
	@RequestMapping("/department/insertdem.do")
	public ModelAndView insertdem(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		TabDevelopment development =new TabDevelopment();
		int parentid = Integer.parseInt(request.getParameter("parentid"));
		if(parentid!=0){
			development.setParentid(parentid);
		}
		development.setDevelopment(request.getParameter("development"));
		HttpSession session = request.getSession();
		int companyid = Integer.parseInt(String.valueOf(session.getAttribute("companyid")));
		development.setCompanyid(companyid);
		int result = service.insertDevelopment(development);
		response.getWriter().print(result);
		return null;
	}
}
