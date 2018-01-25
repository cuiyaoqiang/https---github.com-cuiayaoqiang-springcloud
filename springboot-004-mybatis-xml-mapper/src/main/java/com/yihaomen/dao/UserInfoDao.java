package com.yihaomen.dao;

import java.util.List;

import com.yihaomen.model.UserInfo;


public interface UserInfoDao {
	
	public List<UserInfo> findAllUsers();
	
	public int addUserInfo(UserInfo userInfo);

}
