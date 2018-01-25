package com.bh.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="permission")
public class PermissionEntity {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String rolenum; //角色编号
	private String functionpointnum; //功能点编号

	
	public PermissionEntity() {
	}
	
	public PermissionEntity(Integer id, String rolenum, String menunum, String functionpointnum, String functionpointurl) {
		this.id = id;
		this.rolenum = rolenum;
		this.functionpointnum = functionpointnum;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRolenum() {
		return rolenum;
	}

	public void setRolenum(String rolenum) {
		this.rolenum = rolenum;
	}

	

	public String getFunctionpointnum() {
		return functionpointnum;
	}

	public void setFunctionpointnum(String functionpointnum) {
		this.functionpointnum = functionpointnum;
	}

	@Override
	public String toString() {
		return "PermissionEntity [id=" + id + ", rolenum=" + rolenum + ", functionpointnum=" + functionpointnum + "]";
	}

	

	

}