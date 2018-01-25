package com.bh.entity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bh.dao.RoleDao;

@Repository
public class RoleServer {
	
	@Autowired
	private RoleDao roleDao;
	
	/**
	 * 获取所有角色
	 * @return
	 */
	public List<RoleEntity> findAll(){
		System.out.println(roleDao.findAll());
		return roleDao.findAll();
	}
	
	/**
	 * 新增实体
	 * @param role 角色实体
	 * @return 返回实体
	 */
	public RoleEntity addRole(RoleEntity role){
		RoleEntity RoleEntity =	roleDao.save(role);
		return RoleEntity;
	}
	
	/**
	 * 根据编号查询
	 * @param rolenum
	 * @return
	 */
	public RoleEntity findByRolenum(String rolenum){
		RoleEntity roleEntity =	roleDao.findByRolenum(rolenum);
		return roleEntity;
	}
	
	/**
	 * 更新实体
	 * @param role
	 * @return
	 */
	public RoleEntity updateRole(RoleEntity role){
		RoleEntity roleEntity =	roleDao.save(role);
		return roleEntity;
	}
}