package com.lingtu.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

@Service("usermanagerservice")
public class userManagerService  extends BaseService{
   public List<HashMap> userToComanyId(int companyid){
	  List<HashMap> list = (List<HashMap>) dao.selectList("com.lingtu.entity.mapper.TabCompanyMapper.selectToCompanyId", companyid);
	  return list; 
   }
   
/*   public int updateToAutroity(String userName){
	 int num= dao.update("com.lingtu.entity.mapper.TabCompanyMapper.updateTouserName", userName);
	  return num;   
   }*/
  
   public int updateToAutroityByUserId(String userid,int roleid){
	     HashMap<String, Object> map = new HashMap<String, Object>();
	     map.put("userid", userid);
	     map.put("roleid", roleid);
		 int num= dao.update("com.lingtu.entity.mapper.TabCompanyMapper.updateToUserId", map);
		 return num;		   
	   }
}
