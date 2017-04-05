package com.lingtu.service;

import com.lingtu.entity.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 该类主要是对和设备有关的信息的处理
 */
@Service("DeviceService")
public class DeviceService  extends BaseService{
	
	
	/**
	 * 查询设备数量
	 * @param username 用户名
	 * @return	设备数
	 */
	public int selectCompanyCount(String username){
		return Integer.parseInt(String.valueOf(dao.selectOne("com.lingtu.entity.mapper.TabUserDeviceMapper.selectCompanyCount",username)));
	} 
	/**
	 * 查询设备信息
	 * @param companyid 公司ID
	 * @param
	 * @return	设备信息
	 */
	public List<Map> selectDevice(int companyid){
		//page=(page-1)*10;
		Map map = new HashMap();
		//map.put("page", page);
		map.put("companyid", companyid);
		List<Map> list = (List<Map>)dao.selectList("com.lingtu.entity.mapper.TabDeviceMapper.selectBycompanyid",map);
		return list;
	} 
	/**
	 * 通过设备名查询设备信息
	 * @param dname 设备名
	 * @param companyid 公司ID
	 * @return	设备信息
	 */
	public List selectByDname(String dname,int companyid){
		dname="'%"+dname+"%'";
		Map mp = new HashMap();
		mp.put("dname",dname);
		mp.put("companyid",companyid);
		List<Map> list = (List<Map>)dao.selectList("com.lingtu.entity.mapper.TabDeviceMapper.selectByDname",mp);
		return list;
	}
	/**
	 * 查询设备类型
	 * @param
	 * @return	所有下级部门信息
	 */
	public List<TabDeviceType> selectDeviceType(){
		List<TabDeviceType> list = (List<TabDeviceType>)dao.selectList("com.lingtu.entity.mapper.TabDeviceTypeMapper.selectAll",null);
		return list;
	} 
	/**
	 * 查询所有部门组信息
	 * @param companyid	部门id
	 * @return
	 */
	public List<Map<String, Object>> selectAllDevelopment(int companyid){
		List<TabDevelopment> list = (List<TabDevelopment>)dao.selectList("com.lingtu.entity.mapper.TabDevelopmentMapper.selectTabDevelopment",companyid);
		List<Map<String, Object>> tabDevelopmentList = new ArrayList<Map<String, Object>>();
		for(TabDevelopment tabDevelopment:list){
			int count = Integer.parseInt(String.valueOf(dao.selectOne("com.lingtu.entity.mapper.TabDevelopmentMapper.selectDevelopmentCount", tabDevelopment.getDevelopid())));
			List<TabDevelopment> developmentList = (List<TabDevelopment>)dao.selectList("com.lingtu.entity.mapper.TabDevelopmentMapper.selectDevelopmentSubordinate",tabDevelopment.getDevelopid());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("developid", tabDevelopment.getDevelopid());
			map.put("development", tabDevelopment.getDevelopment());
			map.put("developmentcount", count);
			map.put("developmentList", developmentList);
			tabDevelopmentList.add(map);
		}
		return tabDevelopmentList;
	} 
	
	/**
	 * 查询所有部门
	 * @param companyid 公司ID
	 * @return	所有设备部门
	 */
	public List<TabDevelopment> selectDevelopment(int companyid){
		List<TabDevelopment> list = (List<TabDevelopment>)dao.selectList("com.lingtu.entity.mapper.TabDevelopmentMapper.selectTabDevelopment",companyid);
		return list;
	} 
	
	/**
	 * 修改设备信息
	 * @param tabDevice 修改后的设备信息
	 * @return	编号    1 成功  0  失败 
	 */
	public int updateDevice(TabDevice tabDevice){
		return dao.update("com.lingtu.entity.mapper.TabDeviceMapper.updateByPrimaryKeySelective",tabDevice);
	} 
	/**
	 * 删除设备
	 * @param
	 */
	public void deleteByDeviceid(String[] deviceidList){
		for (String deviceid : deviceidList) {
			int typeid = Integer.parseInt(String.valueOf(dao.selectOne("com.lingtu.entity.mapper.TabDeviceMapper.selectTypeid", deviceid)));
			TabDeviceType deviceType =(TabDeviceType)dao.selectOne("com.lingtu.entity.mapper.TabDeviceTypeMapper.selectByPrimaryKey", typeid);
			Map mp = new HashMap();
			System.out.println(deviceType.getTabbigname()+","+deviceid);
			mp.put("tabbigname", deviceType.getTabbigname());
			mp.put("deviceid", "'"+deviceid+"'");
			dao.delete("dynamictable.deleteByDeveieid",mp);
			dao.delete("com.lingtu.entity.mapper.TabUserDeviceMapper.deleteByDeviceid", deviceid);
			dao.delete("com.lingtu.entity.mapper.TabWarningRecordMapper.deleteByDeviceid", deviceid);
			TabDeviceWarning deviceWarning =(TabDeviceWarning)dao.selectOne("com.lingtu.entity.mapper.TabDeviceWarningMapper.selectByDeviceid", deviceid);
			if(deviceWarning!=null){
				dao.delete("com.lingtu.entity.mapper.TabDeviceWarningMapper.deleteByDeviceid", deviceid);
				if(deviceWarning.getNumid()!=null){
					dao.delete("com.lingtu.entity.mapper.TabNumWarningMapper.deleteByPrimaryKey", deviceWarning.getNumid());
				}
				if(deviceWarning.getCircleid()!=null){
					dao.delete("com.lingtu.entity.mapper.TabCircleRangeMapper.deleteByPrimaryKey", deviceWarning.getCircleid());
				}
				if(deviceWarning.getPolygonid()!=null){
				dao.delete("com.lingtu.entity.mapper.TabPolyRangeMapper.deleteByPrimaryKey", deviceWarning.getPolygonid());
				}
			}
			dao.delete("com.lingtu.entity.mapper.TabDeviceMapper.deleteByPrimaryKey", deviceid);
		}
	} 
	/**
	 * 添加设备
	 * @param tabDevice 要添加的设备信息   
	 * @param
	 * @return	code:0 失败 
	 */
	public int insertTabDevice(TabDevice tabDevice,Long userid,int companyid){
		Map<String, Object> mp =new HashMap<String, Object>();
		TabUserDevice tabUserDevice = new TabUserDevice();
		tabUserDevice.setDeviceid(tabDevice.getDeviceid());
		tabUserDevice.setUserid(userid);
		tabUserDevice.setCompanyid(companyid);
		TabDevice device = (TabDevice)dao.selectOne("com.lingtu.entity.mapper.TabDeviceMapper.selectdevice", tabDevice);
		if(device == null){
			dao.insert("com.lingtu.entity.mapper.TabUserDeviceMapper.insertSelective", tabUserDevice);
			int result = (int)dao.insert("com.lingtu.entity.mapper.TabDeviceMapper.insertSelective", tabDevice);
			return result;
		}else{
			return 0;
		}
		
	} 
}
