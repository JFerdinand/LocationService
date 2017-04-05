package com.lingtu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lingtu.bean.VOEDevelopment;
import com.lingtu.bean.VOELevel;
import com.lingtu.entity.TabDevice;
import com.lingtu.entity.TabDeviceType;
import com.lingtu.entity.TabUserDevice;
import com.lingtu.util.jsonUtil;

@Service("mapservice")
public class MapService extends BaseService {
	/**
	 * 将部门信息、分组及设备信息分别查出，并一一对应
	 * @param companyid
	 * @return
	 */
	public List<VOEDevelopment> findmember(int companyid,List<TabDevice> devicelist){
		List<VOEDevelopment> development = (List<VOEDevelopment>)dao.selectList("com.lingtu.entity.mapper.TabDevelopmentMapper.selectAll", companyid);
		List<VOELevel> voelevel = (List<VOELevel>)dao.selectList("com.lingtu.entity.mapper.TabDevelopmentMapper.select", companyid);
		List<TabDevice> device = (List<TabDevice>)dao.selectList("com.lingtu.entity.mapper.TabDeviceMapper.selectdeviceinfo", devicelist);	//根据设备id查设备类型id
		int a;
		for (int i = 0; i < voelevel.size(); i++) {
			a = voelevel.get(i).getDevelopid();
			List<TabDevice> temp = new ArrayList<TabDevice>();
			for (int j = 0; j < device.size(); j++) {
				if(a==device.get(j).getDevelopid()){
					temp.add(device.get(j));
					voelevel.get(i).setList(temp);	
				}
			}	 
		}
		List<VOELevel> last = new ArrayList();
		for (int i = 0; i < voelevel.size(); i++) {
			if(voelevel.get(i).getList()!=null){
				last.add(voelevel.get(i));
			}
		}
		for (int i = 0; i < development.size(); i++) {
			List<VOELevel> templevel = new ArrayList<VOELevel>();
			a = development.get(i).getDevelopid();
			for (int j = 0; j < last.size(); j++) {
				if(a == last.get(j).getParentid()){
					templevel.add(last.get(j));
					development.get(i).setLevel(templevel);
				} 
			}		
		}
		List<VOEDevelopment> delast = new ArrayList();
		for (int i = 0; i < development.size(); i++) {
			if(development.get(i).getLevel()!=null&&development.get(i).getLevel().size()>0){
				delast.add(development.get(i));
			}
		}
		return delast;
	} 
	/**
	 * 和QueryService中的代码重复，可以省略
	 * @param companyid
	 * @return
	 */
	public List<HashMap> findspecial(int companyid,List<TabDevice> devices){
		List<TabDevice> device = (List<TabDevice>)dao.selectList("com.lingtu.entity.mapper.TabDeviceMapper.selectdeviceinfo", devices);	//根据设备id查设备类型id
		List temp = new ArrayList();
		if(!device.isEmpty()){
			temp.add(device.get(0).getTypeid());
			/*
			 * 去掉重复的类型id
			 */
			for (int i = 0; i < device.size(); i++) {
				boolean mark = false;
				lib:for (int j = 0; j < temp.size(); j++) {
					if(device.get(i).getTypeid()==temp.get(j)){
						mark = true;
						break;
					}
					else{
						continue lib;	
					}
				}
				if(mark==false){
					temp.add(device.get(i).getTypeid());
				}
			}
			List<TabDeviceType> tablename= (List<TabDeviceType>)dao.selectList("com.lingtu.entity.mapper.TabDeviceTypeMapper.selecttablename", temp); //根据设备类型id查找表名
			List<HashMap> tablefield = (List<HashMap>)dao.selectList("com.lingtu.entity.mapper.TabFieldMapper.selectfield", temp);	//根据设备类型id查找字段名
			List<HashMap> devicelist = new ArrayList();
			/*
			 * 拼接字段名以 字段名 as 中文,的格式拼接 ，传入sql语句查出相关信息
			 */
			for (int i = 0; i < tablename.size(); i++) {
				HashMap deviceinfo = new HashMap();
				String field="";
				for (int j = 0; j < tablefield.size(); j++) {
					if(tablename.get(i).getTypeid()==tablefield.get(j).get("TYPEID")){
						field+="a."+tablefield.get(j).get("FIELD")+" as "+"\'"+tablefield.get(j).get("CHINESE")+"\'"+",";
					}	
				}
				HashMap map = new HashMap();
				map.put("tablename", tablename.get(i).getTabname()); 
				map.put("field", field.substring(0, field.length()-1));
				List<HashMap> table  = new ArrayList();
				table=(List<HashMap>)dao.selectList("dynamictable.dynamicinfo", map);
				deviceinfo.put("devicespecial", table); //将查询出来的信息放入map中
				devicelist.add(deviceinfo); //将map循环放入list中
			}
//			System.out.println(jsonUtil.toJson(devicelist));
			return devicelist;
		}
		return null;
	}
	public List finddeviceinfo(int typeid){
		Map map = (Map)dao.selectOne("com.lingtu.entity.mapper.TabDeviceTypeMapper.selectByPrimaryKey", typeid);
		
		return null;
	}

}
