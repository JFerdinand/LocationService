package com.lingtu.controller;

import com.lingtu.entity.TabCircleRange;
import com.lingtu.entity.TabDevice;
import com.lingtu.entity.TabPolyRange;
import com.lingtu.service.SetAlarmService;
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
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
@Controller
public class SetAlarmController extends MultiActionController {
	@Resource(name="setalarmservice")
	SetAlarmService setalarmservice ;	
	/**
	 * 查询设备信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("/setalarm/deviceinfo.do")
	public ModelAndView deviceinfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html:charset=utf-8");
		HttpSession session = request.getSession();
		int companyid = Integer.parseInt(String.valueOf(session.getAttribute("companyid")));
		List<TabDevice> list = setalarmservice.finddeviceinfo(companyid);
		HashMap map = new HashMap();
		map.put("list", list);
		PrintWriter out = response.getWriter();
		String str = jsonUtil.toJson(map);
		out.print(str);
		return null;
	}
	/**
	 * 区域报警范围显示
	 */
	@RequestMapping("/setalarm/rangeshow.do")
	public ModelAndView rangeshow(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html:charset=utf-8");
		HttpSession session = request.getSession();
		int companyid = Integer.parseInt(String.valueOf(session.getAttribute("companyid")));
		List<TabDevice> list = setalarmservice.finddeviceinfo(companyid);
		List<TabCircleRange> circleRange = setalarmservice.findCircleRange(companyid);
		List<TabPolyRange> polyRange = setalarmservice.findPolyRange(companyid);
		HashMap map = new HashMap();
		map.put("circleRange", circleRange);
		map.put("polyRange", polyRange);
//		System.out.println(jsonUtil.toJson(map));
		PrintWriter out = response.getWriter();
		String str = jsonUtil.toJson(map);
		System.out.println(str);
		out.print(str);
		return null;
	}
	/**
	 * 保存区域报警设置
	 */
	@RequestMapping("/setalarm/saveRalarm.do")
	public ModelAndView saveRalarm(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html:charset=utf-8");
		String deviceid = request.getParameter("deviceid");
		int result = -1;
		if(request.getParameter("type").equals("0")){
			TabCircleRange circle = new TabCircleRange();
			circle.setLng(Double.parseDouble(request.getParameter("lng")));
			circle.setLat(Double.parseDouble(request.getParameter("lat")));
			circle.setRadius(Double.parseDouble(request.getParameter("radius")));
			result = setalarmservice.insertrange(circle, 0,deviceid);
		}
		else{
			TabPolyRange polygon = new TabPolyRange();
			polygon.setPointarr(request.getParameter("pointarr"));
			result = setalarmservice.insertrange(polygon, 1,deviceid);
		}
		HashMap map = new HashMap();
		if(result > 0){
			map.put("result", "成功");
		}
		else{
			map.put("result", "失败");
		}
		PrintWriter out = response.getWriter();
		String str = jsonUtil.toJson(map);
		out.print(str);
		return null;
	}
	/**
	 * 删除区域报警设置
	 */
	@RequestMapping("/setalarm/deletealarm.do")
	public ModelAndView deletealarm(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html:charset=utf-8");
		int typeid = Integer.parseInt(request.getParameter("typeid"));
		if(typeid==0){
			setalarmservice.deleteCirlce(Integer.parseInt(request.getParameter("id")));
		}
		else if(typeid==1){
			setalarmservice.deletePolygon(Integer.parseInt(request.getParameter("id")));
		}
		return null;
	}

}
