package com.bh.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bh.entity.FunctionPointEntity;
import com.bh.entity.FunctionPointServer;
import com.bh.entity.MenuEntity;
import com.bh.entity.MenuServer;
import com.bh.entity.PermissionEntity;
import com.bh.entity.PermissionServer;

@Service
public class PermissionSer {
	@Autowired
	private PermissionServer permissionServer;
	@Autowired
	private FunctionPointServer functionPointServer;
	@Autowired
	private MenuServer menuServer;
	/**
	 * 根据角色获取所有权限
	 * @return
	 */
	public List<Object> findAllPermissions(String rolenum){
		List<Map<String,Object>> urllist = new ArrayList<Map<String,Object>>(); 
		//构造前台显示的所有一级菜单、二级菜单、功能点、数据权限
		Map<String,Object> menumap = new LinkedHashMap<String, Object>();
		//构造前台显示的一行记录
		Map<String,Object> rowmap = new LinkedHashMap<String, Object>();
		//所有菜单（键：菜单编号，值：菜单实体）
		Map<String,MenuEntity> firstmap = new HashMap<String, MenuEntity>();
		List<MenuEntity> Menulist = menuServer.findAll();
		for(MenuEntity menuEntity:Menulist){
			firstmap.put(menuEntity.getMenunum(), menuEntity);
			System.out.println("菜单："+firstmap);
		}
		//构造数据权限
		Map<String,Object> urlmap = new LinkedHashMap<String, Object>();
		//查询所有的功能点
		List<FunctionPointEntity> functionPointlist = functionPointServer.findAll(); 
		System.out.println("数据库功能点表："+functionPointlist);
		for(FunctionPointEntity functionPointEntity : functionPointlist)
		{
			String menuAndFun = functionPointEntity.getMenunum()+functionPointEntity.getName();
			if(menumap.get(menuAndFun)==null){
				rowmap = new LinkedHashMap<String, Object>();
				 //构造数据权限
				 urllist = new ArrayList<Map<String,Object>>();
				 urlmap = new LinkedHashMap<String, Object>();
				 urlmap.put("label", functionPointEntity.getDatalevel());
				 urlmap.put("value", functionPointEntity.getNum());
				 urllist.add(urlmap);
				 rowmap.put("urllist",urllist);//数据权限
				 rowmap.put("functionName", functionPointEntity.getName());//功能点
				 System.out.println("bianhao "+functionPointEntity);
				 rowmap.put("secondMenu", firstmap.get( functionPointEntity.getMenunum()).getMenuname());//二级菜单
				 rowmap.put("firstMenu", firstmap.get(firstmap.get( functionPointEntity.getMenunum()).getParentnum()).getMenuname());//一级菜单
				 menumap.put(menuAndFun, rowmap);
			}else{
				 urlmap = new HashMap<String, Object>();
				 urlmap.put("label", functionPointEntity.getDatalevel());
				 urlmap.put("value", functionPointEntity.getNum());
				((ArrayList)((LinkedHashMap)menumap.get(menuAndFun)).get("urllist")).add(urlmap);
			}
		}
		List<Object> valueList = new ArrayList<Object>();
		Iterator<Object> it = menumap.values().iterator();
    	while(it.hasNext()){
    		valueList.add(it.next());//把value迭代存放到valueList
    	}
		
		//根据角色查询权限表
		List<PermissionEntity> permissionlist = permissionServer.findByRolenum(rolenum);
		//把所有的权限放到map中做唯一标识
		Map<String,String> liststrmap = new LinkedHashMap<String,String>();
		for (int i = 0; i < permissionlist.size(); i++) {
			liststrmap.put(permissionlist.get(i).getFunctionpointnum(), "xz");
		}
		//构造角色选中的权限
		Map<String,Object> arowmap = new LinkedHashMap<String,Object>();
		for (int i = 0; i < valueList.size(); i++) {
			arowmap =((LinkedHashMap<String,Object>)valueList.get(i));
			ArrayList<Map<String,Object>> rowurl = ((ArrayList<Map<String,Object>>)arowmap.get("urllist"));
			arowmap.put("funisselect",false);//功能点是否选中
			arowmap.put("funnum", ""); //功能点编号
			for (int j = 0; j < rowurl.size(); j++) {
				if("xz".equals(liststrmap.get(rowurl.get(j).get("value")))){
					arowmap.put("funnum", rowurl.get(j).get("value"));
					arowmap.put("funisselect",true);
				}
			}
		}
		System.out.println("返回："+valueList);
		return valueList;
	}
	/**
	 * 根据角色获取权限
	 * @param Rolenum
	 * @return
	 */
	public List<PermissionEntity> findByRolenum(String Rolenum){
		return permissionServer.findByRolenum(Rolenum);
	}
	/**
	 * 更新角色
	 * @param permissionMap
	 * @return
	 */
	public  List<PermissionEntity>  updatePermission(Map<String ,String> permissionMap){
		String rolenum = permissionMap.get("rolenum");
		List<PermissionEntity> permissionlist = permissionServer.findByRolenum(rolenum);
		if(null != permissionlist){
			permissionServer.deleteByRolenum(permissionlist);
		}
		String funnums = permissionMap.get("funnumstr");
		List<PermissionEntity> list = new ArrayList<PermissionEntity>();
        String [] result = funnums.split(",");
        for(int a = 0;a<result.length;a++){
        	PermissionEntity permissionEntity = new PermissionEntity();
        	permissionEntity.setRolenum(rolenum);
        	permissionEntity.setFunctionpointnum(result[a]);
        	list.add(permissionEntity);
        }
		return 	permissionServer.savePermission(list);
	}

	

	

}
