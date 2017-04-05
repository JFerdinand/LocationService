package com.lingtu.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("androidloginservice")
public class AndroidLoginService extends BaseService{

//============================Android的service方法====================================================
	/**
	 * Android:用户名登录
	 * @param userName
	 * @param userPassword
	 * 因为事务原因，方法中涉及update，而update是有事务的，所以方法名是需要改为update开头的
	 * @return
	 */
	public Map<String, Object> selectUserAndroid(String userName,String userPassword){
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> map1 =(Map<String,Object>) dao.selectOne("com.lingtu.entity.mapper.TabUserMapper.selectToNameAndroid", userName);
		int code=1;		
		if(map1!=null&& map1.get("USERNAME").equals(userName) && map1.get("USERPWD").equals(userPassword)){
			Long userid =(Long) map1.get("USERID");
			String num =(String) dao.selectOne("com.lingtu.entity.mapper.TabUserMapper.selectToUseridDeviceId", userid);
			if(num==null){
				map.put("mark","空");
			}else{
				map.put("mark", num);
			}
			int roleid =Integer.parseInt(String.valueOf(map1.get("roleid")));
			if(roleid==2){
				//返回权限为3的用户设备给用户
				int companyid =Integer.parseInt(String.valueOf(map1.get("COMPANYID")));
				List<HashMap>	list =(List<HashMap>) dao.selectList("com.lingtu.entity.mapper.TabUserMapper.selectToUseridRolied", companyid);
				map.put("list", list);
			}
			map.put("code", code);
			map.put("userid", userid);
			map.put("result", "登录成功");
		}else{
			code=0;
			map.put("code", code);
			map.put("result", "用户名或密码错误");
		}	
		//System.out.println("打印"+jsonUtil.toJson(map));
		return map;
	}
	
	/**
	 * Android:手机号登录
	 * @param userPhone
	 * @param userPassword
	 * @return
	 */
	public Map<String, Object> selectByPhoneAndroid(String userPhone,String userPassword){
		HashMap map1 = new HashMap();
		map1 = (HashMap)dao.selectOne("com.lingtu.entity.mapper.TabUserMapper.selectUserPhoneAndroid", userPhone);				
		Map<String,Object> map = new HashMap<String, Object>();
		int code=1;
		if(map1!=null&&map1.get("USERPHONE").equals(userPhone)&&map1.get("USERPWD").equals(userPassword)){
			Long userid =(Long) map1.get("USERID");
			String num =(String) dao.selectOne("com.lingtu.entity.mapper.TabUserMapper.selectToUseridDeviceId", userid);
			if(num==null){
				map.put("mark","空");
			}else{
				map.put("mark", num);
			}
			int roleid =Integer.parseInt(String.valueOf(map1.get("roleid")));
			if(roleid==2){
				int companyid =Integer.parseInt(String.valueOf(map1.get("COMPANYID")));
				//返回权限为3的用户设备给用户
				List<HashMap>	list =(List<HashMap>) dao.selectList("com.lingtu.entity.mapper.TabUserMapper.selectToUseridRolied", companyid);
				map.put("list", list);
			}		
			map.put("code", code);
			map.put("userid", userid);
			map.put("result", "登录成功");
		}else{
		   code=0;
			map.put("code", code);
			map.put("result", "手机号或密码错误");
		}	
		//System.out.println(jsonUtil.toJson(map));
		return map;
	}
	/**
	 * Android:根据用户名查找用户是否存在，并返回信息
	 * @param userName
	 * @return
	 */
	public Map<String, Object> selectUserNameAndroid(String userName){
		Map<String, Object> map = new HashMap<String, Object>();
		int code=1;
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1 =(HashMap<String, Object>)dao.selectOne("com.lingtu.entity.mapper.TabUserMapper.selectNameToPhoneAndroid", userName);
		if(map1!=null&&map1.get("USERNAME").equals(userName)){
			map.put("result",map1.get("USERPHONE"));
			map.put("code", code);
		}else{
			code =0;
			map.put("result", "你输入的用户名不存在");
			map.put("code", code);
		}
		//System.out.println(jsonUtil.toJson(map));
		return map;
	} 
	/**
	 * Android:根据电话查找用户是否存在，并返回信息
	 * @param
	 * @return
	 */
	public Map<String, Object> selectUserPhoneAndroid(String userPhone){
		Map<String, Object> map = new HashMap<String, Object>();
		int code=1;
		HashMap map1 = new HashMap();		
		map1 =(HashMap)dao.selectOne("com.lingtu.entity.mapper.TabUserMapper.selectUserPhoneAndroid", userPhone);
		if(map1!=null&&map1.get("USERPHONE").equals(userPhone)){
			map.put("result",map1.get("USERPHONE"));
			map.put("code", code);
		}else{
			code =0;
			map.put("result", "你输入的手机号不存在");
			map.put("code", code);
		}
		//System.out.println(jsonUtil.toJson(map));
		return map;
	} 

	/**
	 * Android:根据用户名确认密码修改
	 * @param userName
	 * @param
	 * @return
	 */
	public Map<Object, Object> updatePassWordUserName(String userName,String userpwd){
		Map<Object, Object> map = new HashMap<Object, Object>();
		HashMap map1=new HashMap();
		map1.put("userName", userName);
		map1.put("userpwd", userpwd);
		HashMap map2=(HashMap)dao.selectOne("com.lingtu.entity.mapper.TabUserMapper.selectUserName", userName);
		if(map2.get("USERPWD").equals(userpwd)){
			map.put("result", "你输入的密码不能与原密码一致");
			map.put("code", 0);
		}else{
			dao.update("com.lingtu.entity.mapper.TabUserMapper.updatePassWord", map1);
			map.put("result", "密码修改成功");
			map.put("code", 1);
		}
		//System.out.println(jsonUtil.toJson(map));
		return map;
		}

	/**
	 * Android:根据手机号码确认密码修改
	 * @param userPhone  
	 * @param userPwd
	 * @return
	 */
	public Map<Object, Object> updatePassWordPhone(String userPhone,String userPwd){
		Map<Object, Object> map = new HashMap<Object, Object>();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("userPhone", userPhone);
		map1.put("userPwd", userPwd);
		HashMap<String, Object>  map2 =(HashMap<String, Object>)dao.selectOne("com.lingtu.entity.mapper.TabUserMapper.selectUserPhoneAndroid", userPhone);
		if(map2.get("USERPWD").equals(userPwd)){
			map.put("result", "你输入的密码不能与原密码一致");
			map.put("code", 0);
		}else{
			dao.update("com.lingtu.entity.mapper.TabUserMapper.updatePassWordPhone", map1);
			map.put("result", "密码修改成功");
			map.put("code", 1);
		}
		//System.out.println(jsonUtil.toJson(map));
		return map;
		}
	
}
