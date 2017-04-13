package com.lingtu.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lingtu.entity.TabCompany;
import com.lingtu.entity.TabUser;
import com.lingtu.entity.TabUserDevice;
import com.lingtu.entity.TabUserInfor;
import com.lingtu.entity.TabUserRoleKey;
import com.lingtu.util.MD5;
import com.lingtu.util.jsonUtil;

/**
 * 该类只要是对数据库的操作，以及对查询结果的判断
 */
@Service("RegisterService")
public class RegisterService extends BaseService {
	
	/**
	 * 查询所有公司
	 * @return	所有公司名称
	 */
	public List<TabCompany> selectAllCompany(){
		List<TabCompany> companyList = (List<TabCompany>)dao.selectList("com.lingtu.entity.mapper.TabCompanyMapper.selectAllCompany", null);
		return companyList;
	}
	/**
	 * 查询用户名是否存在
	 * @param
	 * @return		0 表示用户名不存在，1 表示用户名已存在
	 */
	public int selectUsername(String username){
		TabUser tabUser = (TabUser)dao.selectOne("com.lingtu.entity.mapper.TabUserMapper.selectByUsername", username);
		if(tabUser==null){
			return 0;
		}else{
			return 1;
		}
	}
	/**
	 * 查询邮箱是否已注册
	 * @param useremail	用户邮箱账号
	 * @return		0 表示邮箱未注册，1 表示邮箱已注册	
	 */
	public int selectEmail(String useremail){
		TabUserInfor user = (TabUserInfor)dao.selectOne("com.lingtu.entity.mapper.TabUserInforMapper.selectByUseremail", useremail);
		if(user==null){
			return 0;
		}else{
			return 1;
		}
	}
	/**
	 * 查询手机号是否已注册
	 * @param userphone	用户手机号
	 * @return	0 表示手机号未注册，1 表示手机号已注册	
	 */
	public int selectPhone(String userphone){
		TabUserInfor user = (TabUserInfor)dao.selectOne("com.lingtu.entity.mapper.TabUserInforMapper.selectByUserphone", userphone);
		if(user==null){
			return 0;
		}else{
			return 1;
		}
	}
	/**
	 *	将用户信息添加到数据库
	 * @param tabUser	用户信息
	 * @param tabUserInfor	用户信息
	 */
	public void insertUser(TabUser tabUser,TabUserInfor tabUserInfor,TabUserDevice tabUserDevice,TabUserRoleKey tabUserRole){
		dao.insert("com.lingtu.entity.mapper.TabUserMapper.insertSelective",tabUser);
		dao.insert("com.lingtu.entity.mapper.TabUserInforMapper.insertSelective", tabUserInfor);
		dao.insert("com.lingtu.entity.mapper.TabUserDeviceMapper.insertSelective", tabUserDevice);
		dao.insert("com.lingtu.entity.mapper.TabUserRoleMapper.insertSelective", tabUserRole);
	}
	/**
	 * 对手机端用户注册信息进行验证，通过验证，添加到数据库
	 * 						  未通过验证，返回错误信息
	 * @param tabUser	用户信息
	 * @param code	验证码
	 * @param code1	用户填写的验证码
	 * @return		返回验证结果
	 */
	public Map<String, Object> insertPhoneUser(TabUser tabUser,TabUserInfor tabUserInfor,TabUserDevice tabUserDevice,TabUserRoleKey tabUserRole,String code,String code1){
		Map<String, Object> map = new HashMap<String, Object>();
		int num = 0;
		TabUser tabUser1 = (TabUser)dao.selectOne("com.lingtu.entity.mapper.TabUserMapper.selectByUsername",tabUser.getUsername());
		TabUserInfor tabUserInfor1 = (TabUserInfor)dao.selectOne("com.lingtu.entity.mapper.TabUserInforMapper.selectByUserphone", tabUserInfor.getUserphone());
		//User user3 = (User)dao.selectOne("com.lingtu.entity.mapper.UserMapper.selectUserName", user.getUserEmail());
		if(tabUser1!=null){
			map.put("result", "用户名已存在，请重新输入");
		}else if(tabUserInfor1!=null){
			map.put("result", "手机号码已注册，请直接登录");
		}else if(code==null){
			map.put("result", "验证码已失效，请重新获取验证码");
		}else if(!code.equalsIgnoreCase(code1)){
			map.put("result", "验证码错误，请重新输入");
		}else{
			insertUser(tabUser,tabUserInfor,tabUserDevice,tabUserRole);
			num=1;
			map.put("result", "注册成功");
		}
		map.put("code", num);
		return map;
		
	}
}
