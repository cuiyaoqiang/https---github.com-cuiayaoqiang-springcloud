package com.bh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bh.entity.MenuEntity;

/**
*
* @author lilei
* @version 创建时间：2017年12月9日 上午11:37:44
* 
*
*/
public interface MenuDao extends JpaRepository<MenuEntity,Integer>{

	//根据父num查
	public List<MenuEntity> findByparentnum(String parentnum);
	
	public MenuEntity findFirstByMenunum(String menunum);
}
