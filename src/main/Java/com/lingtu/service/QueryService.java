package com.lingtu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.lingtu.entity.TabDevice;
import com.lingtu.entity.TabDeviceType;
import com.lingtu.entity.TabUserDevice;
import com.lingtu.util.jsonUtil;
/**
 * 设备信息记录查询
 * @author jiangmingjun
 * @time 2016年12月9日上午11:17:23
 */
@Service("queryservice")
public class QueryService extends BaseService {
	@SuppressWarnings("unchecked")
	public List<HashMap> findDeviceinfo(int companyid,List<TabDevice> devices){
		List<TabDevice> device = (List<TabDevice>)dao.selectList("com.lingtu.entity.mapper.TabDeviceMapper.selectdeviceinfo", devices);	//根据设备id查设备类型id
		List temp = new ArrayList();
		if(!device.isEmpty()){
			temp.add(device.get(0).getTypeid());
			/*
			 * 去掉重复的typeid
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
				map.put("tablename", tablename.get(i).getTabbigname()); 
				map.put("field", field.substring(0, field.length()-1));
				List<HashMap> table  = new ArrayList();
				table=(List<HashMap>)dao.selectList("dynamictable.dynamicinfobig", map);
//				System.out.println(jsonUtil.toJson(table));
				deviceinfo.put("devicespecial", table); //将查询出来的信息放入map中
				devicelist.add(deviceinfo); //将map循环放入list中
			}
//			System.out.println(jsonUtil.toJson(devicelist));
			return devicelist;
		}
		return null;
	}
	/**
	 * 报警记录查询
	 */
	public List<HashMap> findWarningRecord(int companyid){
		List<TabUserDevice> userdevice = (List<TabUserDevice>)dao.selectList("com.lingtu.entity.mapper.TabUserDeviceMapper.select", companyid); //根据公司id查设备id
		List<HashMap> warningrecord = (List<HashMap>)dao.selectList("com.lingtu.entity.mapper.TabWarningRecordMapper.selectWarningRecord", userdevice);
		for (int i = 0; i < warningrecord.size(); i++) {
			if(warningrecord.get(i).get("CIRCLEID")!=null||warningrecord.get(i).get("POLYGONID")!=null){
				warningrecord.get(i).put("ARANGEWARNING", "出界");
			}
			else if(warningrecord.get(i).get("CIRCLEID")==null||warningrecord.get(i).get("POLYGONID")==null){
				warningrecord.get(i).put("ARANGEWARNING", "无");
			}
		}
//		System.out.println(jsonUtil.toJson(warningrecord));
		return warningrecord;
	}
}
