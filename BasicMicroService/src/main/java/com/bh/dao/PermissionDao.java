package com.bh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bh.entity.PermissionEntity;
/**
 * 权限数据层<br>
 * 2017-12-7	李慧仙     创建
 */
public interface PermissionDao extends JpaRepository<PermissionEntity,Integer>{
	List<PermissionEntity> findByRolenum(String Rolenum);
}
