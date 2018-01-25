package com.bh.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="account")
public class AccountEntity {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String account; //账号
	private String password; //密码
	private String pernum; //人员编号
	private String rolenum; //角色编号
	private String state; //状态
	
	public AccountEntity() {
	}
	
	public AccountEntity(Integer id, String account, String password, String pernum, String rolenum, String state) {
		this.id = id;
		this.account = account;
		this.password = password;
		this.pernum = pernum;
		this.rolenum = rolenum;
		this.state = state;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPernum() {
		return pernum;
	}
	public void setPernum(String pernum) {
		this.pernum = pernum;
	}
	public String getRolenum() {
		return rolenum;
	}
	public void setRolenum(String rolenum) {
		this.rolenum = rolenum;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "AccountEntity [id=" + id + ", account=" + account + ", password=" + password + ", pernum=" + pernum
				+ ", rolenum=" + rolenum + ", state=" + state + "]";
	}
}