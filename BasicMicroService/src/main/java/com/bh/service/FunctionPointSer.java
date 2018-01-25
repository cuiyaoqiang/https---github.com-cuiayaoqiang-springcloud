package com.bh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bh.dao.FunctionPointDao;
import com.bh.entity.FunctionPointEntity;

/**
*
* @author lilei
* @version 创建时间：2017年12月11日 上午10:35:05
* 
*
*/
@Service
public class FunctionPointSer {
	@Autowired
	private FunctionPointDao functionPointDao;
	
	public FunctionPointEntity findFirstByNum(String num){
		return functionPointDao.findFirstByNum(num);
	}

}
