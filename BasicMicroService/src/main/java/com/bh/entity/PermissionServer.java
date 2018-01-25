package com.bh.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bh.dao.PermissionDao;

@Repository
public class PermissionServer {
	
	@Autowired
	private PermissionDao permissionDao;
	
	/**
	 * 获取所有权限
	 * @return
	 */
	public List<PermissionEntity> findAll(){
		return permissionDao.findAll();
	}
	/**
	 * 新增权限
	 * @param permissionList
	 * @return
	 */
	public List<PermissionEntity> savePermission(List<PermissionEntity> permissionList){
		return permissionDao.save(permissionList);
	}
	/**
	 * 根据角色获取权限
	 * @param Rolenum
	 * @return
	 */
	public List<PermissionEntity> findByRolenum(String Rolenum){
		return permissionDao.findByRolenum(Rolenum);
	}
	/**
	 * 删除权限
	 * @param permissionEntity
	 */
	public void deleteByRolenum(List<PermissionEntity>  permissionEntity){
		permissionDao.delete(permissionEntity);
	}
}
