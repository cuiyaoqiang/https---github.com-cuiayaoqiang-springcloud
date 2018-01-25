package com.bh.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bh.entity.RoleEntity;
import com.bh.service.RoleSer;
/**
 * 角色资源层<br>
 * 2017-12-7	吕晓晖     创建
 */
@RestController
@RequestMapping("/role")
public class RoleResource {
	
	@Autowired
	private RoleSer roleSer;
	
	/*
	 * 获取所有角色
	 */
	@GetMapping(value="/findAll")
	public List<Map<String,String>> findAllRole(){
		return roleSer.findAll();
	}
	
	/*
	 * 查询单个角色
	 */
	@GetMapping(value="/findOne/{rolenum}")
	public RoleEntity findByRolenum(@PathVariable("rolenum") String rolenum){
		return roleSer.findByRolenum(rolenum);
	}
	
	/*
	 * 验证角色编号是否可用
	 */
	@GetMapping(value="/verification/{rolenum}")
	public String findByRolenumVerification(@PathVariable("rolenum") String rolenum){
		return roleSer.findByRolenumVerification(rolenum);
	}
	
	/*
	 * 添加角色
	 */
	@PostMapping(value="/add")
	public String addRole(@RequestBody RoleEntity role){
		return roleSer.addRole(role);
	}
	
	/*
	 * 添加角色
	 */
	@PutMapping(value="/update")
	public String updateRole(@RequestBody RoleEntity role){
		return roleSer.updateRole(role);
	}
}	