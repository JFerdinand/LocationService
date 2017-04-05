package com.lingtu.controller;

import com.lingtu.entity.TabDevice;
import com.lingtu.service.QueryService;
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
public class QueryController extends MultiActionController {
	@Resource(name="queryservice")
	QueryService queryservice ;	
	/**
	 * 设备信息记录查询
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("/query/deviceinfo.do")
	public ModelAndView deviceinfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html:charset=utf-8");
		HashMap map = new HashMap();
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
			List<HashMap> list = queryservice.findDeviceinfo(companyid,devicelist);
			session.setAttribute("list", list); //将查询的所有记录放入session中以便后面直接调用
			
			map.put("list", list);
		}
		return new ModelAndView("/WEB-INF/jsp/query.jsp",map);
	}
	/**
	 * 名称搜素需要用到
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("/query/singleinfo.do")
	public ModelAndView singleinfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html:charset=utf-8");
		HttpSession session = request.getSession();
		List<HashMap> list = (List<HashMap>)session.getAttribute("list");
//		System.out.println(jsonUtil.toJson(list));
		HashMap map = new HashMap();
		map.put("list", list);
		PrintWriter out = response.getWriter();
		String str = jsonUtil.toJson(map);
		out.print(str);		
		return null;
	}
	/**
	 * 警告查询功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("/query/warningrecord.do")
	public ModelAndView warningrecord(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html:charset=utf-8");
		HttpSession session = request.getSession();
		List<HashMap> list = (List<HashMap>)session.getAttribute("list"); //从session将一开始人员查询的所有记录取出来
		List<HashMap> showwarningrecord = new ArrayList(); //定义一个list来存放需要显示的数据
		List<HashMap> warningrecord = queryservice.findWarningRecord(1); //传入公司id得到相应的报警记录
		/**
		 * 将报警记录中的设备id、记录时间与人员记录中的id和时间相比较，如果相同的则拿出来放入showwarningrecord这个list中
		 * 这样一个list中可以将设备的信息和报警记录的信息都包含，将这个list返回前端并显示
		 */
		for (int i = 0; i < list.size(); i++) {
			List<HashMap> devicespecial = (List<HashMap>)list.get(i).get("devicespecial");
			for (int j = 0; j < devicespecial.size(); j++) {
				for (int j2 = 0; j2 < warningrecord.size(); j2++) {
					if(devicespecial.get(j).get("deviceid").equals(warningrecord.get(j2).get("DEVICEID"))&&devicespecial.get(j).get("时间").equals(warningrecord.get(j2).get("RECORDTIME"))){
						devicespecial.get(j).putAll(warningrecord.get(j2));
						showwarningrecord.add(devicespecial.get(j));
					}
				}
			}
		}
//		System.out.println(jsonUtil.toJson(showwarningrecord));
		HashMap map = new HashMap();
		map.put("list", showwarningrecord);
		PrintWriter out = response.getWriter();
		String str = jsonUtil.toJson(map);
		out.print(str);	
		return null;
	}
}
