package com.lingtu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lingtu.entity.TabUser;
import com.lingtu.entity.TabUserInfor;
import com.lingtu.util.DesUtils;
import com.lingtu.util.jsonUtil;
import com.lingtu.util.sendMail;

@Service("userloginservice")
public class UserLoginService extends BaseService{

	/**
	 * 根据用户名登录。并返回权限id
	 * @param userName
	 * @param userPassword
	 * @return
	 */
	public HashMap<String,Object> selectUserName(String userName,String userPassword){
		HashMap<String,Object> map = (HashMap<String,Object>)dao.selectOne("com.lingtu.entity.mapper.TabUserMapper.selectUserName", userName);	
		
		if(map!=null&&map.get("USERPWD").equals(userPassword)&&map.get("USERNAME").equals(userName)){
			Long userid=(Long) map.get("USERID");		
			HashMap<String,Object> map2 = (HashMap<String,Object>)dao.selectOne("com.lingtu.entity.mapper.TabUserMapper.selectUserName2", userid);		
			return map2 ;  //登录成功 ,并返回用户的权限id
		}else{
			HashMap<String,Object> map1 = new HashMap<String,Object>();
			map1.put("AUTHORITY", 10);
			return map1 ;  //用户名或密码错误
		}		
    }
	/**
	 * 根据用户iD查找用户权限
	 * @param userid
	 * @return
	 */
	public HashSet<Integer> selectPrivilegeId(Long userid){
		List<HashMap> list = (List<HashMap>) dao.selectList("com.lingtu.entity.mapper.TabUserMapper.selectUserName3", userid);
		HashSet<Integer> set = new HashSet<Integer>();
		for(int i=0;i<list.size();i++){
			set.add((Integer) list.get(i).get("privilegeid"));	
		}
		return set;	
	}
	/**
	 * 根据用户ID查找设备id;
	 * 此时权限为3，只能查看自己的设备
	 * @param userid
	 * @return
	 */
	public List<String> selectByDeviced(Long userid){
		List<HashMap> list = (List<HashMap>) dao.selectList("com.lingtu.entity.mapper.TabUserMapper.selectByDeviced", userid);
		if(!list.isEmpty()&&list.get(0)!=null){
			List<String> li =new ArrayList<String>();
			for(int i=0;i<list.size();i++){
				li.add((String) list.get(i).get("DEVICEID"));	
			}
			return li;	
		}else{
			return null;
		}
			
	}
	/**
	 * 根据用户ID查找设备id;
	 * 此时用户权限为2，根据公司id查找设备信息
	 * @param companyid
	 * @return
	 */
	public List<String> selectByCompanyIdToDeviced(int companyid){
		List<HashMap> list = (List<HashMap>) dao.selectList("com.lingtu.entity.mapper.TabUserMapper.selectByCompanyID", companyid);
		if(!list.isEmpty()){
			List<String> li =new ArrayList<String>();
			for(int i=0;i<list.size();i++){
				li.add((String) list.get(i).get("DEVICEID"));	
			}
			return li;	
		}else{
			return null;
		}
			
	}
	
	/**
	 * 根据手机号登录，并返回id
	 * @param userPhone
	 * @param userPassword
	 * @return
	 */
	public HashMap<String, Object> selectUserPhone(String userPhone,String userPassword){
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map = (HashMap<String, Object>) dao.selectOne("com.lingtu.entity.mapper.TabUserMapper.selectUserPhone", userPhone);
		if(map!=null&& map.get("USERPWD").equals(userPassword)&&map.get("USERPHONE").equals(userPhone)){	
			Long userid=(Long) map.get("USERID");
			HashMap<String, Object> map2 = (HashMap<String, Object>)dao.selectOne("com.lingtu.entity.mapper.TabUserMapper.selectUserName2", userid);
			return map2 ;  //登录成功 ,并返回用户的权限id
		}else{
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("AUTHORITY", 10);
			return  map1 ;  //用户名或密码错误
		}	
    }

	
}
