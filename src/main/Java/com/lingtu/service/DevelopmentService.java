package com.lingtu.service;
import com.lingtu.entity.TabDevelopment;
import com.lingtu.entity.TabDevice;
import com.lingtu.entity.TabDeviceType;
import com.lingtu.entity.TabDeviceWarning;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 该类主要是对部门组有关的信息的处理
 */
@Service("DevelopmentService")
public class DevelopmentService  extends BaseService{
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
	 * 模糊查询部门组信息
	 * @param companyid	部门id
	 * @return
	 */
	public List<Map<String, Object>> selectSousuoDevelopment(int companyid,String development){
		development="'%"+development+"%'";
		List<TabDevelopment> list = (List<TabDevelopment>)dao.selectList("com.lingtu.entity.mapper.TabDevelopmentMapper.selectTabDevelopment",companyid);
		List<Map<String, Object>> tabDevelopmentList = new ArrayList<Map<String, Object>>();
		for(TabDevelopment tabDevelopment:list){
			Map<String, Object> mp =new HashMap<String, Object>();
			mp.put("developid", tabDevelopment.getDevelopid());
			mp.put("development", development);
			List<TabDevelopment> developmentList = (List<TabDevelopment>)dao.selectList("com.lingtu.entity.mapper.TabDevelopmentMapper.selectbyDevelopment",mp);
			if(!developmentList.isEmpty()){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("developid", tabDevelopment.getDevelopid());
				map.put("development", tabDevelopment.getDevelopment());
				map.put("developmentList", developmentList);
				tabDevelopmentList.add(map);
			}
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
	 * 修改部门信息
	 * @param tabDevelopment 要修改的部门组信息
	 * @return	所有设备部门
	 */
	public int updateDevelopment(TabDevelopment tabDevelopment){
		TabDevelopment development = new TabDevelopment();
		if(tabDevelopment.getParentid()==null){
			development = (TabDevelopment)dao.selectOne("com.lingtu.entity.mapper.TabDevelopmentMapper.selectDevelopidByD", tabDevelopment);
		}else{
			development = (TabDevelopment)dao.selectOne("com.lingtu.entity.mapper.TabDevelopmentMapper.selectDevelopidByDAndP", tabDevelopment);
		}
		if(development==null){
			tabDevelopment.setParentid(null);
			return (int)dao.update("com.lingtu.entity.mapper.TabDevelopmentMapper.updateByPrimaryKeySelective",tabDevelopment);
		}
		return 0;
	} 
	/**
	 * 删除部门及部门下的组别和设备
	 * @param developid 部门ID
	 * @return	0 失败  1成功
	 */
	public int deleteDevelopment(int developid){
		List<TabDevelopment> list = (List<TabDevelopment>)dao.selectList("com.lingtu.entity.mapper.TabDevelopmentMapper.selectDevelopmentSubordinate", developid);
		if(list.isEmpty()){
			List<TabDevice> deviceList = (List<TabDevice>)dao.selectList("com.lingtu.entity.mapper.TabDeviceMapper.selectByDevelopid", developid);
			if(!deviceList.isEmpty()){
				for(TabDevice device:deviceList){
					deleteByDeviceid(device.getDeviceid());
				}
			}
			
			dao.delete("com.lingtu.entity.mapper.TabDeviceMapper.deleteByDevelopid", developid);
		}else{
			for(TabDevelopment development:list){
				List<TabDevice> deviceList = (List<TabDevice>)dao.selectList("com.lingtu.entity.mapper.TabDeviceMapper.selectByDevelopid",  development.getDevelopid());
				for(TabDevice device:deviceList){
					deleteByDeviceid(device.getDeviceid());
				}
				dao.delete("com.lingtu.entity.mapper.TabDeviceMapper.deleteByDevelopid", development.getDevelopid());
				dao.delete("com.lingtu.entity.mapper.TabDevelopmentMapper.deleteByPrimaryKey", development.getDevelopid());
			}
		}
		return (int)dao.delete("com.lingtu.entity.mapper.TabDevelopmentMapper.deleteByPrimaryKey",developid);
	} 
	/**
	 * 删除设备
	 * @param deviceid	设备id
	 */
	public void deleteByDeviceid(String deviceid){
		int typeid = Integer.parseInt(String.valueOf(dao.selectOne("com.lingtu.entity.mapper.TabDeviceMapper.selectTypeid", deviceid)));
		TabDeviceType deviceType =(TabDeviceType)dao.selectOne("com.lingtu.entity.mapper.TabDeviceTypeMapper.selectByPrimaryKey", typeid);
		Map mp = new HashMap();
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
		
	} 
	
	/**
	 * 判断是否删除部门或级别
	 * @param developid 部门ID
	 * @return	是否删除及原因   result: 0 未删除 1 删除   
	 */
	public Map<String, Object> deleteIsDevelopment(int developid){
		int result =0;
		String reason ="";
		List<TabDevelopment> developmentlist = (List<TabDevelopment>)dao.selectList("com.lingtu.entity.mapper.TabDevelopmentMapper.selectDevelopmentSubordinate", developid);
		if(developmentlist.isEmpty()){
			List<TabDevice> tabDevicelist = (List<TabDevice>)dao.selectList("com.lingtu.entity.mapper.TabDeviceMapper.selectByDevelopid", developid);
			if(tabDevicelist.isEmpty()){
				deleteDevelopment(developid);
				result=1;
				reason="删除成功";
			}else{
				reason="是否删除该分组下的所有设备？";
			}
			
		}else{
			reason="是否删除该部门下的设备和分组？";
		}
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("result", result);
		mp.put("reason", reason);
		return mp;
	} 
	/**
	 * 添加部门组
	 * @param
	 * @return	添加失败：0   添加成功：部门组ID
	 */
	public int insertDevelopment(TabDevelopment tabDevelopment){
		TabDevelopment development = new TabDevelopment();
		if(tabDevelopment.getParentid()==null){
			development = (TabDevelopment)dao.selectOne("com.lingtu.entity.mapper.TabDevelopmentMapper.selectDevelopidByD", tabDevelopment);
		 }else{
			 development = (TabDevelopment)dao.selectOne("com.lingtu.entity.mapper.TabDevelopmentMapper.selectDevelopidByDAndP",tabDevelopment);
		 }
		if(development!=null){
			return 0;
		}else{
			 dao.insert("com.lingtu.entity.mapper.TabDevelopmentMapper.insertSelective",tabDevelopment);
			 if(tabDevelopment.getParentid()==null){
				 development = (TabDevelopment)dao.selectOne("com.lingtu.entity.mapper.TabDevelopmentMapper.selectDevelopidByD", tabDevelopment);
			 }else{
				 development = (TabDevelopment)dao.selectOne("com.lingtu.entity.mapper.TabDevelopmentMapper.selectDevelopidByDAndP",tabDevelopment);
			 }
			 return development.getDevelopid();
		}
		
	} 
	
}
