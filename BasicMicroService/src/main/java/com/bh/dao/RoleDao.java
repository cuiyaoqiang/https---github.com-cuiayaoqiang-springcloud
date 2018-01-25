package com.bh.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bh.entity.RoleEntity;
/**
 * 角色数据层<br>
 * 2017-12-7	吕晓辉     创建
 */
public interface RoleDao extends JpaRepository<RoleEntity,Integer>{
	public RoleEntity findByRolenum(String rolenum);
}
