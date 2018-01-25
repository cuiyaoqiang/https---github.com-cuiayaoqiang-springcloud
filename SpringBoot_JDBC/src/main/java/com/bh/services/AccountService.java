package com.bh.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bh.dao.AccountDao;
import com.bh.entity.AccountEntity;

@Service
public class AccountService {

	@Autowired
	private AccountDao accountDao;

	/**
	 * 获取所有账号
	 * @return
	 */
	public List<AccountEntity> findAll(){

		List<AccountEntity> accountEntities = accountDao.getAllAccounts();		
		return accountEntities;
	}
}