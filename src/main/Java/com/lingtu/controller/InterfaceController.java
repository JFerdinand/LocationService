package com.lingtu.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.lingtu.entity.TabDevice;
import com.lingtu.entity.TabPath;
import com.lingtu.entity.TabUserDevice;
import com.lingtu.interceptor.functionId;
import com.lingtu.service.InterfaceService;
import com.lingtu.util.Uuid;
import com.lingtu.util.jsonUtil;
@Controller
public class InterfaceController extends MultiActionController {
	@Resource(name="interfaceservice")
	InterfaceService interfaceservice ;

	/**
	 * 用于设备测试
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@functionId("2")
	@RequestMapping("/interface/testinsert.do")
	public ModelAndView testinsert(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String deviceid = request.getParameter("deviceid");
		System.out.println("userid"+request.getParameter("userid")+"====");
		
		/*
		 * 如果没有设备id
		 */
		if(deviceid==null||deviceid.equals("")){
			deviceid = Uuid.uuid();
			System.out.println(deviceid+"++++++");
			/*
			 * 判断用户id是否为空，不为空将用户和设备关联
			 */
			if(request.getParameter("userid")!=null&&!request.getParameter("userid").equals("")&&!request.getParameter("userid").equals("0")){
				System.out.println("********");
				TabUserDevice user = new TabUserDevice();
				user.setDeviceid(deviceid);
				user.setUserid(Long.parseLong(request.getParameter("userid")));
				interfaceservice.updatedevice(user);
			}
		}
		/*
		 * 如果有设备id则是更新信息操作
		 */
		else {
			interfaceservice.deletedevice(deviceid);
		}
		int typeid = Integer.parseInt(request.getParameter("typeid"));
		TabDevice device = new TabDevice();
		device.setDeviceid(deviceid);
		if(typeid==1){
			String str = URLDecoder.decode(request.getParameter("name"),"utf-8");
			device.setDname(str);
			device.setDinfor(request.getParameter("telephone"));
			device.setTypeid(typeid);
			interfaceservice.insertDevice(device);			
		}
		else if(typeid==2){
			String carid = URLDecoder.decode(request.getParameter("carid"),"utf-8");
		    //String carid = new String(request.getParameter("carid").getBytes("iso-8859-1"), "utf-8");  
			device.setDname(carid);
			device.setTypeid(typeid);
			interfaceservice.insertDevice(device);

		}
		HashMap map = new HashMap();
		PrintWriter out  = response.getWriter();
		map.put("code", 1);
		map.put("deviceid", deviceid);
		String a = jsonUtil.toJson(map);
		out.print(a);
		return null;
	}
	/**
	 * 返回轨迹时间段
	 */
	@functionId("2")
	@RequestMapping("/interface/times.do")
	public ModelAndView times(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		List<HashMap> times = interfaceservice.findtimes(request.getParameter("deviceid"));
		PrintWriter out = response.getWriter();
		out.print(jsonUtil.toJson(times));
		return null;
	}
	/**
	 * 返回轨迹
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@functionId("2")
	@RequestMapping("/interface/path.do")
	public ModelAndView path(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String stime = request.getParameter("stime");
		String etime = request.getParameter("etime");
//		System.out.println(s);
		HashMap map = new HashMap();
		map.put("stime", "\'"+stime+"\'");
		map.put("etime", "\'"+etime+"\'");
		map.put("deviceid", request.getParameter("deviceid"));
		List<HashMap> list = interfaceservice.findcheckpath(map);
		PrintWriter out = response.getWriter();
		out.print(jsonUtil.toJson(list));
		return null;
	}
	/**
	 * 返回实时位置信息
	 */
	@functionId("2")
	@RequestMapping("/interface/monitor.do")
	public ModelAndView monitor(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		TabDevice device = interfaceservice.findLngLat(request.getParameter("deviceid"));
		if(device!=null){
			HashMap map = new HashMap();
			PrintWriter out = response.getWriter();
			map.put("lng", device.getLng());
			map.put("lat", device.getLat());
			out.print(jsonUtil.toJson(map));
		}
		return null;
	}
}
