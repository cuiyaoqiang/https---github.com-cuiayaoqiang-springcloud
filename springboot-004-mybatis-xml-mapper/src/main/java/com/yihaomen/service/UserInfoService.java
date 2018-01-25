package com.yihaomen.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihaomen.dao.UserInfoDao;
import com.yihaomen.model.UserInfo;


@Service
public class UserInfoService {

	@Autowired
	private UserInfoDao userInfoDao;
	
	public List<UserInfo>  findAllUsers(){
		return userInfoDao.findAllUsers();
	}
	
	public UserInfo createUser() {
		UserInfo u =new UserInfo();
		long curMillis = System.currentTimeMillis();
		u.setName("Name-" + curMillis);
		u.setAddress("Address: " + curMillis);
		userInfoDao.addUserInfo(u);
		return u;
	}
	
}
