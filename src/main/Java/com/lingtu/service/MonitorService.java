package com.lingtu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lingtu.bean.VOELevel;
import com.lingtu.entity.TabDevice;
import com.lingtu.entity.TabDeviceType;
import com.lingtu.util.jsonUtil;
@Service("moniterservice")
public class MonitorService extends BaseService {
	/**
	 * 轨迹查询
	 * @param map
	 * @return
	 */
	public List<HashMap> findcheckpath(HashMap map){
		TabDevice device = (TabDevice)dao.selectOne("com.lingtu.entity.mapper.TabDeviceMapper.selectByPrimaryKey", map.get("deviceid"));
		int typeid = device.getTypeid();
		TabDeviceType  devicetype = (TabDeviceType)dao.selectOne("com.lingtu.entity.mapper.TabDeviceTypeMapper.selectByPrimaryKey", typeid);
		List<HashMap> fieldlist = (List<HashMap>)dao.selectList("com.lingtu.entity.mapper.TabFieldMapper.selectfieldone", typeid);
		String field = "";
		for (int i = 0; i < fieldlist.size(); i++) {
			field+=fieldlist.get(i).get("FIELD")+",";
		}
		map.put("tablename", devicetype.getTabbigname());//设备个性记录表
		map.put("field", field.substring(0, field.length()-1));
		List<HashMap> list = (List<HashMap>)dao.selectList("dynamictable.selectPath", map);//根据传入的时间从动态个性记录表中查询经纬度
		return list ;
	}
}
