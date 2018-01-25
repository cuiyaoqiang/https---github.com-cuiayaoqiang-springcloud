package com.bh.entity;

public class AccountEntity {
	
	private Integer id;
	private String username; //账号
	private String password; //密码

	public AccountEntity() {
	}

	public AccountEntity(Integer id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

 

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "AccountEntity [id=" + id + ", account=" + username + ", password=" + password + "]";
	}
	
}