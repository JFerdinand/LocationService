package com.lingtu.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lingtu.entity.TabCircleRange;
import com.lingtu.entity.TabDevice;
import com.lingtu.entity.TabDeviceWarning;
import com.lingtu.entity.TabPolyRange;
import com.lingtu.entity.TabUserDevice;
import com.lingtu.util.jsonUtil;
/**
 * 设备报警设置包括范围报警和数值报警
 * @author jiangmingjun
 * @time 2016年12月16日上午9:05:15
 */
@Service("setalarmservice")
public class SetAlarmService extends BaseService {
	/**
	 * 查出设备信息
	 * @param companyid
	 * @return
	 */
	public List<TabDevice> finddeviceinfo(int companyid){
		List<TabUserDevice> deviceid = (List<TabUserDevice>)dao.selectList("com.lingtu.entity.mapper.TabUserDeviceMapper.select", companyid);
		List<TabDevice> device =(List<TabDevice>) dao.selectList("com.lingtu.entity.mapper.TabDeviceMapper.selectdeviceinfo", deviceid);
		return device;
	}
	/**
	 * 插入报警范围
	 * @param o
	 * @param type
	 * @param deviceid
	 */
	public int insertrange(Object o,int type,String deviceid){
		int result = 0;
		if(type==0){
			TabCircleRange circle = (TabCircleRange)o;
			dao.insert("com.lingtu.entity.mapper.TabCircleRangeMapper.insert", circle);
			String[] split = deviceid.split(",");
			for (int i = 0; i < split.length; i++) {
				TabDeviceWarning devicewarning = new TabDeviceWarning();
				devicewarning.setDeviceid(split[i]);
				devicewarning.setCircleid(circle.getCircleid());
				result = dao.insert("com.lingtu.entity.mapper.TabDeviceWarningMapper.insertSelective", devicewarning);
			}
		}
		else if(type==1){
			TabPolyRange polygon = (TabPolyRange)o;
			dao.insert("com.lingtu.entity.mapper.TabPolyRangeMapper.insertSelective", polygon);
			String[] split = deviceid.split(",");
			for (int i = 0; i < split.length; i++) {
				TabDeviceWarning devicewarning = new TabDeviceWarning();
				devicewarning.setDeviceid(split[i]);
				devicewarning.setPolygonid(polygon.getPolygonid());
				result = dao.insert("com.lingtu.entity.mapper.TabDeviceWarningMapper.insertSelective", devicewarning);
			}
		}
		return result;
	}
	public List<TabCircleRange> findCircleRange(int companyid){
		List<TabUserDevice> userdevice = (List<TabUserDevice>)dao.selectList("com.lingtu.entity.mapper.TabUserDeviceMapper.select", companyid);
		List<TabCircleRange> circlerange = (List<TabCircleRange>)dao.selectList("com.lingtu.entity.mapper.TabCircleRangeMapper.selectRange", userdevice);
		return circlerange;
	}
	public List<TabPolyRange> findPolyRange(int companyid){
		List<TabUserDevice> userdevice = (List<TabUserDevice>)dao.selectList("com.lingtu.entity.mapper.TabUserDeviceMapper.select", companyid);
		List<TabPolyRange> polyrange = (List<TabPolyRange>)dao.selectList("com.lingtu.entity.mapper.TabPolyRangeMapper.selectRange", userdevice);
		return polyrange;
	}
	public void deleteCirlce(int circleid){
		dao.delete("com.lingtu.entity.mapper.TabCircleRangeMapper.deleteByPrimaryKey", circleid);
	}
	public void deletePolygon(int polygonid){
		dao.delete("com.lingtu.entity.mapper.TabPolyRangeMapper.deleteByPrimaryKey", polygonid);
	}
}
