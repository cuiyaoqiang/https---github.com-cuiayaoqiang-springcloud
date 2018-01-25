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
import com.bh.entity.RoleEntity;
import com.bh.entity.RoleServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class RoleSer {
	
	@Autowired 
	private RoleServer roleServer;
	
	@Autowired
	private UrlProperties urlpro;
	
	/**
	 * 获取所有角色
	 * @return
	 */
	public List<Map<String,String>> findAll(){
		String str = HttpUtil.get(urlpro.getUrl()+"/HrMicroService/person/query");
	    Gson gson = new Gson();
	    List<Map<String,String>> list = gson.fromJson(str,new TypeToken<List<Map<String,String>>>() {}.getType());
	    Map<String,String> map1 = new HashMap<>();
	    for(int i=0 ; i<list.size(); i++){
		   map1.put(list.get(i).get("perNum"),list.get(i).get("perName"));
	    }
	    List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
	    List<RoleEntity> roleEntitys = roleServer.findAll();
	    for (RoleEntity roleEntity : roleEntitys) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("id",String.valueOf(roleEntity.getId()));
			map.put("rolenum", roleEntity.getRolenum());
			map.put("rolename", roleEntity.getRolename());
			map.put("recordpername",map1.get(roleEntity.getRecordpernum()));
			map.put("recordpernum",roleEntity.getRecordpernum());
			map.put("recordtime",roleEntity.getRecordtime());
			resultList.add(map);
		}
		return resultList;
	}
	
	/**
	 * @param role 角色实体
	 * @return
	 */
	public String addRole(RoleEntity role){
		String result = "false";
		if(null != role){
			RoleEntity roleEntity = roleServer.addRole(role);
			if(null != roleEntity){
				result = "true";
			}
		}
		return result;
	}
	
	/**
	 * @param rolenum 角色编号
	 * @return
	 */
	public RoleEntity findByRolenum(String rolenum){
		RoleEntity roleEntity = null;
		if(null != rolenum){
			roleEntity = roleServer.findByRolenum(rolenum);
		}
		return roleEntity;
	}
	
	/**
	 * @param rolenum 角色编号
	 * @return
	 */
	public String findByRolenumVerification(String rolenum){
		String result = "false";
		if(null != rolenum){
			RoleEntity roleEntity = roleServer.findByRolenum(rolenum);
			if(roleEntity == null){
				result = "true";
			}
		}
		return result;
	}	
	
	/**
	 * 更新一个实体
	 * @param role
	 * @return
	 */
	public String updateRole(RoleEntity role){
		RoleEntity roleEntity = roleServer.updateRole(role);
		String result = "false";
		if(roleEntity != null){
			result = "true";
		}
		return result;
	}
}