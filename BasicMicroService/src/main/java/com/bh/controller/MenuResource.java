package com.bh.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.jose4j.json.JsonUtil;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bh.common.JwtUtil;
import com.bh.entity.AccountEntity;
import com.bh.entity.AccountServer;
import com.bh.entity.FunctionPointEntity;
import com.bh.entity.MenuEntity;
import com.bh.entity.PermissionEntity;
import com.bh.service.FunctionPointSer;
import com.bh.service.MenuSer;
import com.bh.service.PermissionSer;

/**
*
* @author lilei
* @version 创建时间：2017年12月9日 上午11:42:58
* 
*
*/
@RestController
@RequestMapping("/menu")
public class MenuResource {
	
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private MenuSer menuService;
	@Autowired
	private PermissionSer permissionService;
	@Autowired
	private FunctionPointSer functionPointService;
	@Autowired
	private AccountServer accountServer;
	
	
	@GetMapping(value="/findAll")
	public List<MenuEntity> findAll(){
		return menuService.findAll();
	}
	/**
	 * 获得菜单树字符串（遍历所有树）
	 * @author lilei
	 * @date   2017年12月9日 下午12:24:28 
	 * @return
	 */
	@GetMapping(value="/getMenuTree")
	public List<Object> getMenuTree(){
		List<Object> menutree = new ArrayList<Object>();
		List<MenuEntity>rootList=menuService.findByparentnum("-1");
		if(rootList.size()!=0){
			for(MenuEntity root : rootList) {
				Map<String, Object> map = new HashMap<>();
				map.put("name", root.getMenuname());
				map.put("icon", root.getMenuurl());
				List<MenuEntity>childList=menuService.findByparentnum(root.getMenunum());
				if(childList.size()!=0){
					List<Object> cmapList = new ArrayList<Object>();
					for(MenuEntity child : childList){
						Map<String, Object> cmap = new HashMap<>();
						//分割字符串
						String menuUrl = child.getMenuurl();
						String name = "";
		        		if(!menuUrl.isEmpty()){
		        			menuUrl=menuUrl.trim();
		        			int beginIndex = menuUrl.lastIndexOf("/");
		        			if(beginIndex!=-1){
		        				name = menuUrl.substring(beginIndex+1,menuUrl.length());
		        			}else{
		        				//没有斜杠，
		        				name = menuUrl;
		        			}
		        		}
						cmap.put("name", child.getMenuname());
						cmap.put("url","/"+ name);
						cmapList.add(cmap);
					}
					map.put("clist", cmapList);
				}
				menutree.add(map);
			}
		}else{
			return null;
		}
		return menutree;
		
	}
	
	/**
	 * 根据账号的角色权限查菜单树
	 * @author lilei
	 * @date   2017年12月12日 上午11:54:15 
	 * @param account
	 * @return
	 */
//	@GetMapping(value="/getMenuTreeByAccount/{account}")
//	public List<Object> findMenuTreeByAccount(@PathVariable String account){
//		List<Object> menutree = new ArrayList<Object>();
//		AccountEntity accountEntity =accountServer.findByAccount(account);
//        if(accountEntity!=null){
//        	Map<String, List<MenuEntity>> menuMap = this.findMenuMapByRolenum(accountEntity);
//        	for(Entry<String, List<MenuEntity>> entry : menuMap.entrySet()){
//        		Map<String, Object> map = new HashMap<>();
//        		MenuEntity root = menuService.findFirstByMenunum(entry.getKey());
//        		map.put("name", root.getMenuname());
//				map.put("icon", root.getMenuurl());
//				List<MenuEntity>childList=entry.getValue();
//				if(childList.size()!=0){
//					List<Object> cmapList = new ArrayList<Object>();
//					for(MenuEntity child : childList){
//						Map<String, Object> cmap = new HashMap<>();
//						cmap.put("name", child.getMenuname());
//						//分割字符串
//						String menuUrl = child.getMenuurl();
//						String name = "";
//		        		if(!menuUrl.isEmpty()){
//		        			menuUrl=menuUrl.trim();
//		        			int beginIndex = menuUrl.lastIndexOf("/");
//		        			if(beginIndex!=-1){
//		        				name = menuUrl.substring(beginIndex+1,menuUrl.length());
//		        			}else{
//		        				//没有斜杠，
//		        				name = menuUrl;
//		        			}
//		        		}
//		        		cmap.put("name", child.getMenuname());
//						cmap.put("url","/"+ name);
//						cmapList.add(cmap);
//					}
//					map.put("clist", cmapList);
//				}
//				menutree.add(map);
//        	}
//        }
//        return menutree;
//	}
	@GetMapping(value="/getMenuTreeByJwt")
	public List<Object> findMenuTreeByAccount (HttpServletRequest httpServletRequest){
		//从请求头里的令牌中获得该用户的rolenum
		JSONObject jsonObject =jwtUtil.getJwtjsonByRequire(httpServletRequest);
		JSONArray jarray = (JSONArray) jsonObject.get("authorities");
		
		List<Object> menutree = new ArrayList<Object>();
        if(jarray.get(0)!=null){
        	String rolenum = (String) jarray.get(0);
        	Map<String, List<MenuEntity>> menuMap = menuService.findMenuMapByRolenum(rolenum);
        	for(Entry<String, List<MenuEntity>> entry : menuMap.entrySet()){
        		Map<String, Object> map = new HashMap<>();
        		MenuEntity root = menuService.findFirstByMenunum(entry.getKey());
        		map.put("name", root.getMenuname());
				map.put("icon", root.getMenuurl());
				List<MenuEntity>childList=entry.getValue();
				if(childList.size()!=0){
					List<Object> cmapList = new ArrayList<Object>();
					for(MenuEntity child : childList){
						Map<String, Object> cmap = new HashMap<>();
						cmap.put("name", child.getMenuname());
						
						//分割字符串
						String menuUrl = child.getMenuurl();
						String name = "";
		        		if(!menuUrl.isEmpty()){
		        			menuUrl=menuUrl.trim();
		        			int beginIndex = menuUrl.lastIndexOf("/");
		        			if(beginIndex!=-1){
		        				name = menuUrl.substring(beginIndex+1,menuUrl.length());
		        			}else{
		        				//没有斜杠，
		        				name = menuUrl;
		        			}
		        		}
		        		cmap.put("name", child.getMenuname());
						cmap.put("url","/"+ name);
						cmapList.add(cmap);
					}
					map.put("clist", cmapList);
				}
				menutree.add(map);
        	}
        }
        return menutree;
	}
	
	

	
	


}
