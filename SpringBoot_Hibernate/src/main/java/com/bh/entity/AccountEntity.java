package com.bh.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="account")
public class AccountEntity {

	@Id
	@GeneratedValue
	private Integer id;
	private String username; //账号
	private String password; //密码
	private Date birthday;
	
	public AccountEntity() {
	}

	 
	public AccountEntity(Integer id, String username, String password, Date birthday) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.birthday = birthday;
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

	public Date getBirthday() {
		return birthday;
	}


	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "AccountEntity [id=" + id + ", username=" + username + ", password=" + password + ", birthday="
				+ birthday + "]";
	}
}