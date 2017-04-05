package com.lingtu.controller;

import com.lingtu.entity.TabDevice;
import com.lingtu.entity.TabDeviceType;
import com.lingtu.service.DeviceService;
import com.lingtu.util.Uuid;
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
public class DeviceController {
	
	@Resource(name="DeviceService")
	DeviceService service;
	
	/**
	 * 进入设备管理界面
	 */
	@RequestMapping("/device/devicemanager.do")
	public ModelAndView devicemanager(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		int companyid = Integer.parseInt(String.valueOf(session.getAttribute("companyid")));
		List<Map> list = service.selectDevice(companyid);
		List<TabDeviceType> deviceTypeList = service.selectDeviceType();
		List<Map<String, Object>> developmentList = service.selectAllDevelopment(companyid);
		Map<String,Object> mp = new HashMap<String,Object>();
		mp.put("deviceTypeList", deviceTypeList);
		mp.put("developmentList", developmentList);
		mp.put("deviceList", list);
		//System.out.println(jsonUtil.toJson(list));
		response.getWriter().print(jsonUtil.toJson(mp));
		return null;
	}
	/**
	 * 通过设备名搜索设备
	 */
	@RequestMapping("/device/searchdevice.do")
	public ModelAndView searchdevice(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		int companyid = Integer.parseInt(String.valueOf(session.getAttribute("companyid")));
		String dname = request.getParameter("dname");
		List<Map> deviceList = service.selectByDname(dname, companyid);
		Map<String,Object> mp = new HashMap<String,Object>();
		mp.put("deviceList", deviceList);
		response.getWriter().print(jsonUtil.toJson(mp));
		return null;
	}
	/**
	 *删除设备
	 */
	@RequestMapping("/device/deldevice.do")
	public ModelAndView deldevice(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		String str = request.getParameter("dList");
		String[] deviceidlist = request.getParameter("dList").trim().split(",");
		service.deleteByDeviceid(deviceidlist);
		response.getWriter().print(jsonUtil.toJson(1));
		return null;
	}
	/**
	 *修改设备
	 */
	@RequestMapping("/device/updatedevice.do")
	public ModelAndView updatedevice(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		TabDevice device = new TabDevice();
		device.setDeviceid(request.getParameter("deviceid"));
		device.setDname(request.getParameter("dname"));
		device.setDinfor(request.getParameter("dinfor"));
		device.setTypeid(Integer.parseInt(request.getParameter("typeid")));
		device.setDevelopid(Integer.parseInt(request.getParameter("developid")));
		int result = service.updateDevice(device);
		response.getWriter().print(jsonUtil.toJson(result));
		return null;
	}
	/**
	 *添加设备
	 */
	@RequestMapping("/device/insertdevice.do")
	public ModelAndView insertdevice(HttpServletRequest request,HttpServletResponse response)
		throws ServletRequestBindingException,UnsupportedEncodingException,IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset-utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		TabDevice device = new TabDevice();
		device.setDeviceid(Uuid.uuid());
		device.setDname(request.getParameter("dname"));
		device.setDinfor(request.getParameter("dinfor"));
		device.setTypeid(Integer.parseInt(request.getParameter("typeid")));
		device.setDevelopid(Integer.parseInt(request.getParameter("developid")));
		int companyid = Integer.parseInt(String.valueOf(session.getAttribute("companyid")));
		Long userid = (Long) session.getAttribute("userid");
		int result = service.insertTabDevice(device, userid, companyid);
		Map<String,Object> mp = new HashMap<String,Object>();
		mp.put("result", result);
		mp.put("deviceid", device.getDeviceid());
		response.getWriter().print(jsonUtil.toJson(mp));
		return null;
	}
}
