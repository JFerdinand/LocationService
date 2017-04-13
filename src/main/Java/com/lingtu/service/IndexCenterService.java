package com.lingtu.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.lingtu.entity.TabUserInfor;
import com.lingtu.util.jsonUtil;

@Service("indexcenterservice")
public class IndexCenterService extends BaseService  {

/**
 * 根据手机号码查找用户信息
 * @param userPhone
 * @return
 */
public HashMap<String,Object> selectByUserPhone (String userPhone){
	return (HashMap<String,Object>) dao.selectOne("com.lingtu.entity.mapper.TabUserInforMapper.selectByUserPhone", userPhone);				
   }
/**
 * 忘记密码：根据手机号修改密码(不需要id)
 * @param userPhone
 * @param passWord
 * @return
 */
public int updateToPass(String userPhone,String passWord){
	HashMap<Object,Object> map = new HashMap<Object,Object> ();
	map.put("userPhone", userPhone);
	map.put("passWord", passWord);
	HashMap<String, Object> map1 =(HashMap) dao.selectOne("com.lingtu.entity.mapper.TabUserInforMapper.selectByUserPhone", userPhone);	
	if(map1!=null && map1.get("USERPHONE").equals(userPhone)){
		HashMap map2 =(HashMap) dao.selectOne("com.lingtu.entity.mapper.TabUserInforMapper.selectByUserPhone",userPhone);
		if(map2!=null && map2.get("USERPWD").equals(passWord)){
			return 2;//新密码与旧密码一致
		}else{
			int num =dao.update("com.lingtu.entity.mapper.TabUserInforMapper.updatePassWordPhone", map);
			return num;
		}
	}else{
		return 3;//你输入的手机号码不存在
	}
	
}
/**
 * 查询手机号是否已注册
 * @param userPhone	用户手机号
 * @return	1表示手机号未注册，0 表示手机号已注册	
 */
public int selectPhone2(String userPhone){
	HashMap map = (HashMap)dao.selectOne("com.lingtu.entity.mapper.TabUserInforMapper.selectToUserphone1", userPhone);
	if(map==null){
		return 1;
	}else{
		return 0;
	}
}

/**
 * 根据用户ID修改手机号
 * @param userid
 * @param newPhone
 * @return
 */
public int updateById(Long userid,String newPhone){
	HashMap<Object,Object> map = new HashMap<Object,Object> ();
	map.put("userid", userid);
	map.put("newPhone", newPhone);
	HashMap map1 = (HashMap) dao.selectOne("com.lingtu.entity.mapper.TabUserInforMapper.selectToUserId", userid);
	System.out.println(jsonUtil.toJson(map1));
	int num=2; 
	if(map1 !=null){
		num =dao.update("com.lingtu.entity.mapper.TabUserInforMapper.updateToUserId", map);
		return num ; 
	  }else{
		return num ; 
	  }
	
}
/**
 * 个人中心设置：根据id修改手机号码(需要id)
 * @param
 * @param userPhone
 * @param passWord
 * @return
 */
public int updateByIdPhone(String userPhone,String passWord){
	HashMap<Object,Object> map = new HashMap<Object,Object> ();
	map.put("userPhone", userPhone);
	map.put("passWord", passWord);
	int num =dao.update("com.lingtu.entity.mapper.TabUserInforMapper.updatePassWordPhone", map);
	return num;

}

/**
 * 个人中心设置：根据id查找信息(需要id)
 * @param userid
 * @return
 */
public HashMap selectUserinfor3(Long userid){
	HashMap<String, Object> map1 = (HashMap<String, Object>) dao.selectOne("com.lingtu.entity.mapper.TabUserInforMapper.selectToUserId", userid);
	return map1;
	
}
/**
 * 根据用户名查找用户信息
 * @param userName
 * @return
 */
public  HashMap<String,Object> selectByUserName(String userName){
	HashMap<String,Object> map = new HashMap<String,Object>();
	map  =(HashMap) dao.selectOne("com.lingtu.entity.mapper.TabUserInforMapper.selectByUserName", userName);	
	return map;
				
   }

/**
 * 根据邮箱修改邮箱
 * @param oldEmail
 * @param newEmail
 * @return
 */
public int updateByUserEmail(String oldEmail,String newEmail){
    HashMap<String, Object> map=  new HashMap<String, Object>();
    map.put("oldEmail", oldEmail);
    map.put("newEmail", newEmail);
	int num = (int) dao.update("com.lingtu.entity.mapper.TabUserInforMapper.updateByOldEmail", map);
	return num;					
   }

/**
 * 根据新邮箱查找用户信息
 * @param newEmail
 * @return
 */
public HashMap<String,Object>  selectByUserEmail(String newEmail){
	 HashMap<String, Object> map=  new HashMap<String, Object>();
	 map =  (HashMap<String, Object>) dao.selectOne("com.lingtu.entity.mapper.TabUserInforMapper.selectByUserEmail", newEmail);
	 return map;					
   }

}
