package com.lingtu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lingtu.entity.TabDevice;
import com.lingtu.entity.TabDeviceType;
import com.lingtu.entity.TabPath;
import com.lingtu.entity.TabUserDevice;
import com.lingtu.util.DateUtil;
import com.lingtu.util.jsonUtil;

@Service("interfaceservice")
public class InterfaceService extends BaseService {
	/**
	 * 插入设备
	 * @param device
	 */
	public void insertDevice(TabDevice device){
		dao.insert("com.lingtu.entity.mapper.TabDeviceMapper.insertSelective", device);
	}
	public String findid(String name){
		if(dao.selectOne("com.lingtu.entity.mapper.TabDeviceMapper.selectId", name)==null){
			return "-1";
		}
		return (String)dao.selectOne("com.lingtu.entity.mapper.TabDeviceMapper.selectId", name);
	}
	/**
	 * 轨迹查询
	 * @param map
	 * @return
	 */
	public List<HashMap> findcheckpath(HashMap map){
		TabDevice device = (TabDevice)dao.selectOne("com.lingtu.entity.mapper.TabDeviceMapper.selectByPrimaryKey", map.get("deviceid"));
		int typeid = device.getTypeid();
		TabDeviceType  devicetype = (TabDeviceType)dao.selectOne("com.lingtu.entity.mapper.TabDeviceTypeMapper.selectByPrimaryKey", typeid);
		map.put("tablename", devicetype.getTabbigname());//设备个性记录表
		List<HashMap> list = new ArrayList<HashMap>();
		if(typeid==1){
			list= (List<HashMap>)dao.selectList("dynamictable.interfacePath2", map);//根据传入的时间从动态个性记录表中查询经纬度
		}
		else if(typeid==2){
			list= (List<HashMap>)dao.selectList("dynamictable.interfacePath", map);//根据传入的时间从动态个性记录表中查询经纬度
		}
		for (HashMap hashMap : list) {
			hashMap.put("typeid", typeid);
		}
		return list ;
	}
	/**
	 * 时间段查询
	 */
	public List<HashMap> findtimes(String deviceid){
		List<TabPath> times = (List<TabPath>)dao.selectList("com.lingtu.entity.mapper.TabPathMapper.selecttimes", deviceid);
		List<HashMap> list = new ArrayList();
		for (TabPath tabPath : times) {
			HashMap map = new HashMap();
			map.put("etime", DateUtil.getDate(tabPath.getEtime()));
			map.put("deviceid", tabPath.getDeviceid());
			map.put("stime", tabPath.getStime());
			map.put("pathid", tabPath.getPathid());
			list.add(map);
		}
		return list;
	}
	/**
	 * 根据设备id删除设备
	 */
	public void deletedevice(String deviceid){
		dao.delete("com.lingtu.entity.mapper.TabDeviceMapper.deleteByPrimaryKey", deviceid);
	}
	/**
	 * 根据用户id关联设备id
	 */
	public void updatedevice(TabUserDevice user){
		dao.update("com.lingtu.entity.mapper.TabUserDeviceMapper.updateDeviceid", user);
	}
	/**
	 * 根据设备id查询坐标点
	 */
	public TabDevice findLngLat(String deviceid){
		TabDevice device = (TabDevice)dao.selectOne("com.lingtu.entity.mapper.TabDeviceMapper.selectByPrimaryKey", deviceid);
		if(device != null){
			return device;
		}
		return null;
	} 
}
