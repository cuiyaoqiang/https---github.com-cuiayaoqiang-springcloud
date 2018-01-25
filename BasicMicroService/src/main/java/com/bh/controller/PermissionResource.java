package com.bh.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bh.service.PermissionSer;
/**
 * 权限资源层<br>
 * 2017-12-7	李慧仙     创建
 */
@RestController
@RequestMapping("/permissions")
public class PermissionResource {
	
	@Autowired
	private PermissionSer permissionSer;
	/**
	 * 根据角色获取权限
	 * @param rolenum 角色编号
	 * @return
	 */
	@GetMapping(value="/rolepermissions/{rolenum}")
	public List<Object> findAllPermissions(@PathVariable("rolenum") String rolenum){
		return permissionSer.findAllPermissions(rolenum);
	}
	
	/**
	 * 更新权限
	 * @param permissionMap 角色编号和功能点字符串
	 * @return
	 */
	@PutMapping(value="/permission")
	public String updatePermission(@RequestBody Map<String,String> permissionMap){
		if(null != permissionSer.updatePermission(permissionMap)){
	    	return "true";
	    }
		else{
			return "false";
		}
	}
}
