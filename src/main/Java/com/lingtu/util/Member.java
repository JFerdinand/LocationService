package com.lingtu.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lingtu.bean.VOEDevelopment;
import com.lingtu.entity.TabDevice;

public class Member {
	public static List<HashMap> con (List<VOEDevelopment> development){
		List<HashMap> list = new ArrayList();
		for (int i = 0; i < development.size(); i++) {
			List listnode2 = new ArrayList();
			for (int j = 0; j < development.get(i).getLevel().size(); j++) {
				List listnode3 = new ArrayList();
				System.out.println(jsonUtil.toJson(development));
				for (int j2 = 0; j2 < development.get(i).getLevel().get(j).getList().size(); j2++) {
					List<TabDevice> list1 = development.get(i).getLevel().get(j).getList();
					HashMap node3 = new HashMap();
					node3.put("developmentname", development.get(i).getDevelopment());
					node3.put("levelname",development.get(i).getLevel().get(j).getDevelopment());
					node3.put("deviceid", list1.get(j2).getDeviceid());
					node3.put("name", list1.get(j2).getDname());
					node3.put("relng", list1.get(j2).getRelng());
					node3.put("relat", list1.get(j2).getRelat());
					node3.put("dtime", list1.get(j2).getDtime());
					node3.put("dpic", list1.get(j2).getDpic());
					listnode3.add(node3);
				}
				HashMap node2 = new HashMap();
				node2.put("name",development.get(i).getLevel().get(j).getDevelopment());
				node2.put("children", listnode3);
				listnode2.add(node2);
			}
			HashMap node1 = new HashMap();
			node1.put("name", development.get(i).getDevelopment());
			node1.put("children", listnode2);
			list.add(node1);
		}
		return list;
	}
}
