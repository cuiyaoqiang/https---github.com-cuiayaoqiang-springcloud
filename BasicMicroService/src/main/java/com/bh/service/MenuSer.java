package com.bh.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jose4j.json.JsonUtil;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bh.entity.FunctionPointEntity;
import com.bh.entity.MenuEntity;
import com.bh.entity.MenuServer;
import com.bh.entity.PermissionEntity;

/**
*
* @author lilei
* @version 创建时间：2017年12月9日 上午11:41:26
* 
*
*/
@Service
public class MenuSer {
	@Autowired
	private MenuServer menuServer;
	@Autowired
	private MenuSer menuService;
	@Autowired
	private PermissionSer permissionService;
	@Autowired
	private FunctionPointSer functionPointService;
	
	public List<MenuEntity> findAll(){
		return menuServer.findAll();
	}
	public List<MenuEntity> findByparentnum(String parentnum){
		return menuServer.findByparentnum(parentnum);
	}
	public MenuEntity findFirstByMenunum(String menunum){
		return menuServer.findFirstByMenunum(menunum);
	}
	/**
	 * 根据账号的角色权限的到以一级菜单num为key，以二级菜单为value的map
	 * @author lilei
	 * @date   2017年12月12日 上午11:57:12 
	 * @param account
	 * @return
	 */
	public Map<String, List<MenuEntity>> findMenuMapByRolenum(String roleNum){
		Map<String, List<MenuEntity>> menuMap = new HashMap<>();//菜单map
		Map<String, MenuEntity> secondMenuMap = new HashMap<>();//以二级菜单num为key
		List<PermissionEntity> permissionList = permissionService.findByRolenum(roleNum);
		
		if(permissionList!=null&&permissionList.size()!=0){
			for(PermissionEntity permission:permissionList){
				FunctionPointEntity functionPoint = functionPointService.findFirstByNum(permission.getFunctionpointnum());
				//根据菜单编号去查Menu，再放入以menenum为key的map中
        		if(!functionPoint.getMenunum().isEmpty()){
        			MenuEntity menuEntity = menuService.findFirstByMenunum(functionPoint.getMenunum());
        			List<MenuEntity>mList = new ArrayList<>();
        			if(secondMenuMap.get(menuEntity.getMenunum())==null){//去功能点多个导致的二级菜单重复
        				secondMenuMap.put(menuEntity.getMenunum(), menuEntity);
        				if(menuMap.get(menuEntity.getParentnum())!=null){//添加到list
            				mList = menuMap.get(menuEntity.getParentnum());
            				mList.add(menuEntity);
                		}else{//添加值
                			mList.add(menuEntity);
                		}
        				menuMap.put(menuEntity.getParentnum(), mList);//功能点以二级菜单的父菜单，也就是根
            			//菜单的num为key放入map
        			}
        		}
			}
		}
		return menuMap;
	}	
}
