package com.bh.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bh.dao.AccountDao;

@Repository
public class AccountServer {
	
	@Autowired
	private AccountDao accountDao;
	
	/**
	 * 获取所有账号
	 * @return
	 */
	public List<AccountEntity> findAll(){
		return accountDao.findAll();
	}
	
	/**
	 * 获取单个账号
	 * @param account
	 * @return
	 */
	public AccountEntity findByAccount(String account){
		return accountDao.findByAccount(account);
	}
	
	/**
	 * 添加账号
	 * @param accountEntity
	 * @return
	 */
	public AccountEntity addAccount(AccountEntity accountEntity){
		AccountEntity account = accountDao.save(accountEntity);
		return account;
	}
	
	/**
	 * 更新账号
	 * @param accountEntity
	 * @return
	 */
	public AccountEntity updateAccount(AccountEntity accountEntity){
		AccountEntity account = accountDao.save(accountEntity);
		return account;
	}
}