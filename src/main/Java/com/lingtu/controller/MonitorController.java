package com.lingtu.controller;

import com.lingtu.bean.VOEDevelopment;
import com.lingtu.entity.TabDevice;
import com.lingtu.service.MapService;
import com.lingtu.service.MonitorService;
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
public class MonitorController extends MultiActionController {
	@Resource(name="moniterservice")
	MonitorService moniterservice ;
	@Resource(name="mapservice")
	MapService mapservice ;
	@RequestMapping("/monitor/show.do")
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html:charset=utf-8");
		return new ModelAndView("/WEB-INF/jsp/monitor.jsp");
	}
	
	@RequestMapping("/monitor/solve.do")
	public ModelAndView solve(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html:charset=utf-8");
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
			map.put("list", Member.con(development));//查询的设备信息
			map.put("list2", devicespecial);//查询的轨迹点
			PrintWriter out  = response.getWriter();
			String a = jsonUtil.toJson(map);
			out.print(a);
		}
		return null;
	}
	/**
	 * 轨迹追踪
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("/monitor/checkpath.do")
	public ModelAndView checkpath(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html:charset=utf-8");
		String stime = request.getParameter("time")+" 00:00:00";
		String etime = request.getParameter("time")+" 23:59:59";;
		HashMap map = new HashMap();
		map.put("stime", "\'"+stime+"\'");
		map.put("etime", "\'"+etime+"\'");
		map.put("deviceid", request.getParameter("deviceid"));
		List<HashMap> list = moniterservice.findcheckpath(map);
//		List<String> list1 = new ArrayList<String>();
		map.put("list", list);
//		for (int i = 1; i < list.size()-1;) {
//			String point = (double)list.get(i).get("relng")+","+(double)list.get(i).get("relat");
//			list1.add(point);
//			i=i+5;
//		}
//		List pointarr = new ArrayList();
//		for (int i = 0; i < list1.size()-10;) {
//			List list2 = new ArrayList();
//			for (int j = i; j < i+10; j++) {
//				list2.add(list1.get(j));
//			}
//			i=i+10;
//			pointarr.add(list2);
//		}
//		System.out.println(jsonUtil.toJson(pointarr));
//		map.put("pointarr", pointarr);
		PrintWriter out = response.getWriter();
		String str = jsonUtil.toJson(map);
		out.print(str);
		return null;
	}
}
