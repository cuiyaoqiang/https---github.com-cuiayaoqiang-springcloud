package com.bh.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bh.common.HttpUtil;
import com.bh.common.UrlProperties;
import com.bh.dao.RoleDao;
import com.bh.entity.AccountEntity;
import com.bh.entity.AccountServer;
import com.bh.entity.RoleEntity;
import com.bh.entity.RoleServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class AccountSer {
	
	@Autowired
	private AccountServer accountServer;

	@Autowired 
	private RoleServer roleServer;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UrlProperties urlpro;
	
	
	/**
	 * 获取所有账号
	 * @return
	 */
	public List<Map<String,String>> findAll(){
		System.out.println("2111111111111111111"+urlpro.toString());
	   String str = HttpUtil.get(urlpro.getUrl()+"/HrMicroService/person/query");
	   Gson gson = new Gson();
	   List<Map<String,String>> list = gson.fromJson(str,new TypeToken<List<Map<String,String>>>() {}.getType());
	   Map<String,String> map1 = new HashMap<>();
	   for(int i=0 ;list!=null&& i<list.size(); i++){
		   if(!"".equals(list.get(i).get("perNum")) && !"".equals(list.get(i).get("perName"))){
			   map1.put(list.get(i).get("perNum"),list.get(i).get("perName"));
		   }
	   }
	   List<RoleEntity> roleEntities = roleDao.findAll();
	   Map<String,String> map = new HashMap<String,String>();
	   for (RoleEntity roleEntity : roleEntities) {
			map.put(roleEntity.getRolenum(),roleEntity.getRolename());
	   }
	   List<Map<String,String>> result = new ArrayList<Map<String,String>>();
	   List<AccountEntity> accountEntities = accountServer.findAll();		
	   for (AccountEntity accountEntity : accountEntities) {
			 Map<String,String> maps = new HashMap<String,String>();
			 maps.put("id",String.valueOf(accountEntity.getId()));
			 maps.put("account",accountEntity.getAccount());
			 maps.put("pernum",map1.get(accountEntity.getPernum()));
			 maps.put("rolenum",map.get(accountEntity.getRolenum()));
			 maps.put("state",accountEntity.getState());
			 result.add(maps);
		}
		return result;
	}
	/**功能：查询自己的记录
	 * @param pernum
	 * @return
	 */
	public List<Map<String,String>> findByItself(String account){
		   String str = HttpUtil.get(urlpro.getUrl()+"/HrMicroService/person/query");
		   Gson gson = new Gson();
		   List<Map<String,String>> list = gson.fromJson(str,new TypeToken<List<Map<String,String>>>() {}.getType());
		   Map<String,String> map1 = new HashMap<>();
		   for(int i=0 ; i < list.size(); i++){
			   if(!"".equals(list.get(i).get("perNum")) && !"".equals(list.get(i).get("perName"))){
				   map1.put(list.get(i).get("perNum"),list.get(i).get("perName"));
			   }
		   }
		   List<RoleEntity> roleEntities = roleDao.findAll();
		   Map<String,String> map = new HashMap<String,String>();
		   for (RoleEntity roleEntity : roleEntities) {
				map.put(roleEntity.getRolenum(),roleEntity.getRolename());
		   }
		   List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		   AccountEntity acc = accountServer.findByAccount(account);	
		   if(null != acc){
				 Map<String,String> maps = new HashMap<String,String>();
				 maps.put("id",String.valueOf(acc.getId()));
				 maps.put("account",acc.getAccount());
				 maps.put("pernum",map1.get(acc.getPernum()));
				 maps.put("rolenum",map.get(acc.getRolenum()));
				 maps.put("state",acc.getState());
				 result.add(maps);
			}
			return result;
	}
	/**
	 * 获取单个账号
	 * @param account 账号
	 * @return
	 */
	public Map<String,Object> findByAccount(String account){
		AccountEntity accountEntity = accountServer.findByAccount(account);
		Gson gson = new Gson();
		Map<String,String> map = new HashMap<String,String>();
		if(null != accountEntity){
			map.put("id",String.valueOf(accountEntity.getId()));
			map.put("account",accountEntity.getAccount());
			String person = HttpUtil.get(urlpro.getUrl()+"/HrMicroService/person/"+accountEntity.getPernum());
			Map<String,String> personMap = gson.fromJson(person, new TypeToken<Map<String,String>>() {}.getType());	
			map.put("pernum",accountEntity.getPernum());
			map.put("perName",personMap.get("perName"));
			map.put("organiNum",personMap.get("organiId"));
			map.put("rolenum",accountEntity.getRolenum());
			map.put("roleName",roleServer.findByRolenum(accountEntity.getRolenum()).getRolename());
			map.put("state",accountEntity.getState());
		}
		
		//账号实体
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("account",map);
		
		List<RoleEntity> roleEntities = roleDao.findAll();
	   
		List<Map<String,String>> roles = new ArrayList<Map<String,String>>();
	    for (RoleEntity roleEntity : roleEntities) {
	    	Map<String,String> role = new HashMap<String,String>();
	    	role.put("rolenum",roleEntity.getRolenum());
	    	role.put("rolename",roleEntity.getRolename());
	    	roles.add(role);
	    }
	    //角色下拉框使用
	    result.put("role",roles);
	    List<Map<String,Object>> list =  gson.fromJson(HttpUtil.get(urlpro.getUrl()+ "/HrMicroService/organ/cas"), new TypeToken<List<Map<String,Object>>>() {}.getType());
	    result.put("perData",list);
		return result;
	}
	
	/**
	 * 验证账号是否可用
	 * @param account
	 * @return
	 */
	public String findByAccountVerification(String account){
		String result = "false";
		AccountEntity accountEntity = accountServer.findByAccount(account);
		if(accountEntity == null){
			result = "true";
		}
		return result;
	}
	
	/**
	 * 获取新增页面信息
	 * @return
	 */
	public List<Map<String,String>> findRole(){
		List<RoleEntity> roleEntities = roleDao.findAll();
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for (RoleEntity roleEntity : roleEntities) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("rolenum",roleEntity.getRolenum());
			map.put("rolename",roleEntity.getRolename());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 新增账号
	 * @param accountEntity
	 * @return
	 */
	public String addAccount(AccountEntity accountEntity){
		String result = "false";
		if(null != accountEntity){
			AccountEntity acc = accountEntity;
			acc.setPassword(acc.getAccount());
			AccountEntity account = accountServer.addAccount(acc);
			if(account != null){
				result = "true";
			}
		}
		return result;
	}
	
	/**
	 * 更新账号
	 * @param accountEntity
	 * @return
	 */
	public String updateAccount(AccountEntity accountEntity){
		String result = "false";
		if(accountEntity != null){
			AccountEntity acc = accountEntity;
			acc.setPassword(accountEntity.getAccount());
			AccountEntity account = accountServer.updateAccount(acc);
			if(account!=null){
				result = "true";
			}
		}
		return result;
	}
}