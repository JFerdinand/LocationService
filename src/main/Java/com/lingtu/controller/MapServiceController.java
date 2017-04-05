package com.lingtu.controller;

import com.lingtu.bean.VOEDevelopment;
import com.lingtu.entity.TabDevice;
import com.lingtu.interceptor.functionId;
import com.lingtu.service.MapService;
import com.lingtu.util.HttpClientTest;
import com.lingtu.util.Member;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Controller
public class MapServiceController extends MultiActionController {
	@Resource(name="mapservice")
	MapService mapservice ;
	

	@RequestMapping("/main.do")
	public ModelAndView showmain(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html:charset=utf-8");
		HttpSession session = request.getSession();
		HashMap map = new HashMap();
		map.put("username", session.getAttribute("username"));
		return new ModelAndView("/WEB-INF/jsp/main.jsp",map);
	}
	@RequestMapping("/exit.do")
	public ModelAndView exit(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html:charset=utf-8");
		HttpSession session = request.getSession();
		session.invalidate();
		PrintWriter out = response.getWriter();
		out.print("1");
		return null;
	}
	/**
	 * 中转
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@functionId("1")
	@RequestMapping("/session.do")
	public ModelAndView session(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html:charset=utf-8");

		return new ModelAndView("/WEB-INF/jsp/session.jsp");
	}	
	@RequestMapping("/map/show.do")
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html:charset=utf-8");
		return new ModelAndView("/WEB-INF/jsp/mapsolve.jsp");
	}
	@SuppressWarnings("unused")
	@RequestMapping("/map/membershow.do")
	public ModelAndView membershow(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html:charset=utf-8");
		//session中取出companyid，根据companyid判断有哪些部门
		HttpSession session = request.getSession();
		List<String> deviceid = (List<String>)session.getAttribute("deviceid");
		int companyid = Integer.parseInt(String.valueOf(session.getAttribute("companyid")));
		if(deviceid!=null){
			List<TabDevice> devicelist = new ArrayList();
			for (int i = 0; i <deviceid.size() ; i++) {
				TabDevice device = new TabDevice();
				device.setDeviceid(deviceid.get(i));
				devicelist.add(device);
			}
			List<VOEDevelopment> development = mapservice.findmember(companyid,devicelist);
			List<HashMap> devicespecial = mapservice.findspecial(companyid,devicelist);
			HashMap map = new HashMap();
			map.put("list", Member.con(development));
			map.put("list2", devicespecial);
			PrintWriter out = response.getWriter();
			String str = jsonUtil.toJson(map);
			out.print(str);
		}
		return null;
	}
	/**
	 * 根据IP定位
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * 
	 */
	@RequestMapping("/map/location.do")
	public ModelAndView location(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html:charset=utf-8");
		String str = HttpClientTest.GetUrl("http://api.map.baidu.com/highacciploc/v1?qcqterm=pc&ak=LixCd2gnmFkUjNC1u7T5uUne4ICYnkbH&coord=bd09ll");
		String str1 = jsonUtil.parseJson(str).getAsJsonObject("content").toString();
		String str2 = jsonUtil.parseJson(str1).get("location").toString();
		PrintWriter out = response.getWriter();
		out.print(str2);
		return null;
	}
	
//	@RequestMapping("/map/alarmrange.do")
//	public ModelAndView alarmrange(HttpServletRequest request, HttpServletResponse response)
//			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
//		TabAlarmRange alarmrange = new TabAlarmRange();
//		TabDevice device = new TabDevice();
//		alarmrange.setLng(Double.parseDouble(request.getParameter("circlelng")));
//		alarmrange.setLat(Double.parseDouble(request.getParameter("circlelat")));
//		alarmrange.setRadius(Double.parseDouble(request.getParameter("circleradius")));
//		String split[] = request.getParameter("idarr").split(",");
//		for (int i = 0; i < split.length; i++) {
//			device.setDeviceid(Long.parseLong(split[i]));
//		}
//		return null;
//	}
}
