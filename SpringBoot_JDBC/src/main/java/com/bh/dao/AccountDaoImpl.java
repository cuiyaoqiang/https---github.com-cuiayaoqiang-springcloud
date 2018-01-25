package com.bh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.bh.entity.AccountEntity;

@Service
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(String username, String password) {
        jdbcTemplate.update("insert into jdbc_account(username, password) values(?, ?)", username, password);
    }

    @Override
    public void deleteByName(String username) {
        jdbcTemplate.update("delete from jdbc_account where username = ?", username);
    }

    @Override
    public List<AccountEntity> getAllAccounts() {
        return jdbcTemplate.queryForObject("select * from jdbc_account", List.class);
    }

    @Override
    public void deleteAllAccounts() {
        jdbcTemplate.update("delete from jdbc_account");
    }
}