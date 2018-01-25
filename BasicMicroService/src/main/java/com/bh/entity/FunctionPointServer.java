package com.bh.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bh.dao.FunctionPointDao;

/**
*
* @author lilei
* @version 创建时间：2017年12月13日 下午3:14:02
* 
*
*/
@Repository
public class FunctionPointServer {
	@Autowired
	private FunctionPointDao functionPointDao;
	
	/**
	 * 查所有
	 * @author lilei
	 * @date   2017年12月13日 下午3:21:39 
	 * @return
	 */
	public List<FunctionPointEntity> findAll() {
		return functionPointDao.findAll();
	}

	/**
	 * 根据id查
	 * @author lilei
	 * @date   2017年12月13日 下午3:22:54 
	 * @param id
	 * @return
	 */
	public FunctionPointEntity findById(int id){
		return functionPointDao.findOne(id);
	}
	/**
	 * 保存
	 * @author lilei
	 * @date   2017年12月13日 下午3:24:30 
	 * @param functionPointEntity
	 * @return
	 */
	public FunctionPointEntity saveFunctionPoint(FunctionPointEntity functionPointEntity){
		return functionPointDao.save(functionPointEntity);
	}
	/**
	 * 删除
	 * @author lilei
	 * @date   2017年12月13日 下午3:26:17 
	 * @param functionPointEntity
	 */
	public void deleteFunctionPoint(FunctionPointEntity functionPointEntity){
		functionPointDao.delete(functionPointEntity);
	}
}
