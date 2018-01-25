package com.bh.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bh.entity.FunctionPointEntity;

/**
*
* @author lilei
* @version 创建时间：2017年12月11日 上午10:35:37
*/
public interface FunctionPointDao extends JpaRepository<FunctionPointEntity,Integer>{
	//获得第一个
	FunctionPointEntity findFirstByNum(String num);

}
