package com.bh.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="role")
public class RoleEntity {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String rolenum; //角色编号
	private String rolename; //角色名称
	private String recordpernum; //录入人编号
	private String recordtime; //录入时间
	
	
	public RoleEntity() {
		
	}

	public RoleEntity(Integer id, String rolenum, String rolename, String recordpernum, String recordtime) {
		this.id = id;
		this.rolenum = rolenum;
		this.rolename = rolename;
		this.recordpernum = recordpernum;
		this.recordtime = recordtime;
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
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getRecordpernum() {
		return recordpernum;
	}
	public void setRecordpernum(String recordpernum) {
		this.recordpernum = recordpernum;
	}
	public String getRecordtime() {
		return recordtime;
	}
	public void setRecordtime(String recordtime) {
		this.recordtime = recordtime;
	}

	@Override
	public String toString() {
		return "RoleEntity [id=" + id + ", rolenum=" + rolenum + ", rolename=" + rolename + ", recordpernum="
				+ recordpernum + ", recordtime=" + recordtime + "]";
	}
}