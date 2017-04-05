package com.lingtu.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.lingtu.entity.TabCompany;
import com.lingtu.entity.TabUser;
import com.lingtu.entity.TabUserDevice;
import com.lingtu.entity.TabUserInfor;
import com.lingtu.entity.TabUserRoleKey;
import com.lingtu.interceptor.functionId;
import com.lingtu.service.RegisterService;
import com.lingtu.util.MD5;
import com.lingtu.util.MemcacheTest;
import com.lingtu.util.Tools;
import com.lingtu.util.Uuid;
import com.lingtu.util.jsonUtil;
import com.lingtu.util.sendCode;

@Controller
public class RegisterController {
	@Resource(name="RegisterService")
	RegisterService service;
	/**
	 * 加载页面：注册
	 */
	@functionId("1")
	@RequestMapping("/register.do")
	public ModelAndView registerEntrance(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		List<TabCompany> companyList = service.selectAllCompany();
		Map<String,Object> mp = new HashMap<String,Object>();
		mp.put("companyList", companyList);
		return new ModelAndView("WEB-INF/jsp/register.jsp",mp);
	}
	/**
	 * 查询用户名是否存在
	 */
	@functionId("1")
	@RequestMapping("/register/judgeUserName.do")
	public ModelAndView gaodeMap(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		String username = request.getParameter("username");//接受用户名
		int result = service.selectUsername(username);
		response.getWriter().print(result);
		return null;
	}
	/**
	 * 查询用户邮箱账号是否已注册
	 */
	@functionId("1")
	@RequestMapping("/register/judgeUserEmail.do")
	public ModelAndView emailVerify(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		String email = request.getParameter("email");
		int result = service.selectEmail(email);
		response.getWriter().print(result);
		return null;
	}
	/**
	 * 查询用户手机号是否已注册
	 */
	@functionId("1")
	@RequestMapping("/register/judgeUserPhone.do")
	public ModelAndView phoneVerify(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		String phone = request.getParameter("phone");
		int result = service.selectPhone(phone);
		response.getWriter().print(result);
		return null;
	}
	/**
	 * 向邮箱发送验证码
	 */
	@functionId("1")
	@RequestMapping("/register/sendemailcode.do")
	public ModelAndView sendcode(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		String email = request.getParameter("email");
		String code = sendCode.produceCode();
//		HttpSession session = request.getSession();
//		session.setMaxInactiveInterval(3*60);
//		session.setAttribute("code",code);
		System.out.println(email+","+code);
		MemcacheTest.setData("code", code,new Date(360000));
		boolean result = com.lingtu.util.sendMail.sendCode(email, code);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result",result);
		map.put("code",code);
		System.out.println(jsonUtil.toJson(map));
		response.getWriter().print(jsonUtil.toJson(map));
		return null;
	}
	/**
	 * 向手机发送验证码
	 */
	@functionId("2")
	@RequestMapping("/interface/sendphonecode.do")
	public ModelAndView sendPhoneCode(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		String phone = request.getParameter("phone");
		int num = service.selectPhone(phone);
		Map<String, Object> map = new HashMap<String, Object>();
		if(num==1){
			map.put("result", "手机号已注册");
			map.put("code", "0");
		}else{
			String code = sendCode.produceCode();
			HttpSession session = request.getSession();
			session.setAttribute("sessionID", session.getId());
//			session.setMaxInactiveInterval(3*60);//设置缓存时间
//			session.setAttribute("code",code);//将用户名放入缓存
//			System.out.println(phone+","+code);
			MemcacheTest.setData(session.getId(), code,new Date(360000));
			//MemcacheTest.putData("code",code);//将用户名放入Memcache缓存
			String result = "发送成功";
			map.put("result", result);
			map.put("code", code);
		}
		response.getWriter().print(jsonUtil.toJson(map));
		return null;
	}
	/**
	 * 添加用户
	 */
	@functionId("1")
	@RequestMapping("/register/insterUser.do")
	public ModelAndView register(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		TabUser tabUser = new TabUser();
		Long id = Tools.getUniqueInteger();
		tabUser.setUserid(id);
		tabUser.setUsername(request.getParameter("username"));
		String pwd = request.getParameter("password");
		tabUser.setUserpwd(MD5.MD5Encode("10001111111000"+pwd));
	
		TabUserInfor tabUserInfor = new TabUserInfor();
		tabUserInfor.setUserphone(request.getParameter("phone"));
		tabUserInfor.setUserid(id);
		TabUserDevice tabUserDevice = new TabUserDevice();
		tabUserDevice.setUserid(id);
		HttpSession session = request.getSession();
		String sessionID = (String) session.getAttribute("sessionID");
		String code=(String) MemcacheTest.getData(sessionID);
		TabUserRoleKey userRole = new TabUserRoleKey();
		userRole.setUserid(id);
		userRole.setRoleid(3);
		Map<String, Object> map = new HashMap<String, Object>();
		int result =0;
		String reason = "";
		if(code==null){
			reason="*验证码已失效，请重新获取";
		}else{
			tabUserDevice.setCompanyid(Integer.parseInt(request.getParameter("companyid")));
			service.insertUser(tabUser,tabUserInfor,tabUserDevice,userRole);
			result=1;
		}
		map.put("result", result);
		map.put("reason", reason);
		response.getWriter().print(jsonUtil.toJson(map));
		return null;
	}
	/**
	 * 向手机端发送公司列表
	 */
	@functionId("2")
	@RequestMapping("/interface/sendcompany.do")
	public ModelAndView sendcompany(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		List<TabCompany> companyList = service.selectAllCompany();
		response.getWriter().print(jsonUtil.toJson(companyList));
		return null;
	}
	/**
	 * 接收手机端用户注册信息
	 */
	@functionId("2")
	@RequestMapping("/interface/phoneUser.do")
	public ModelAndView registerPhone(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String sessionID = (String) session.getAttribute("sessionID");
		String sessionid = request.getRequestedSessionId();
		if(sessionID.equals(sessionid)){
			Long userid = Tools.getUniqueInteger();
			TabUser tabUser = new TabUser();
			tabUser.setUserid(userid);
			tabUser.setUsername(request.getParameter("loginName"));
			String pwd = request.getParameter("password");
			tabUser.setUserpwd(MD5.MD5Encode("10001111111000"+pwd));
			TabUserInfor tabUserInfor = new TabUserInfor();
			tabUserInfor.setUserphone(request.getParameter("phone"));
			tabUserInfor.setUserid(userid);
			String code =(String) MemcacheTest.getData(sessionid);
			String phonecode = request.getParameter("phonecode");
			TabUserDevice tabUserDevice = new TabUserDevice();
			String deviceid = request.getParameter("deviceid");
			if(deviceid!=null && deviceid!=""){
				tabUserDevice.setDeviceid(deviceid);
			}
			tabUserDevice.setUserid(userid);
			tabUserDevice.setCompanyid(Integer.parseInt(request.getParameter("companyid")));
			TabUserRoleKey userRole = new TabUserRoleKey();
			userRole.setUserid(userid);
			userRole.setRoleid(3);
			response.getWriter().print(jsonUtil.toJson(service.insertPhoneUser(tabUser,tabUserInfor,tabUserDevice,userRole,code,phonecode)));
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", "0");
			map.put("result", "验证码错误");
			response.getWriter().print(jsonUtil.toJson(map));
		}
		
		return null;
	}
}
