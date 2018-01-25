package com.bh.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bh.entity.AccountEntity;
/**
 * 账号数据层<br>
 * 2017-12-7	吕晓辉     创建
 */
public interface AccountDao extends JpaRepository<AccountEntity,Integer>{
	public AccountEntity findByAccount(String account);
}
