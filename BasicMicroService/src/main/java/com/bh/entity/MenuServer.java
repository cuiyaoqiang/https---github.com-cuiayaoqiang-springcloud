package com.bh.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bh.dao.MenuDao;

/**
*
* @author lilei
* @version 创建时间：2017年12月13日 下午3:14:02
* 
*
*/
@Repository
public class MenuServer {
	@Autowired
	private MenuDao menuDao;
	
	/**
	 * 查所有
	 * @author lilei
	 * @date   2017年12月13日 下午3:21:39 
	 * @return
	 */
	public List<MenuEntity> findAll() {
		return menuDao.findAll();
	}

	/**
	 * 根据id查
	 * @author lilei
	 * @date   2017年12月13日 下午3:22:54 
	 * @param id
	 * @return
	 */
	public MenuEntity findById(int id){
		return menuDao.findOne(id);
	}
	/**
	 * 保存
	 * @author lilei
	 * @date   2017年12月13日 下午3:24:30 
	 * @param menuEntity
	 * @return
	 */
	public MenuEntity saveMenu(MenuEntity menuEntity){
		return menuDao.save(menuEntity);
	}
	/**
	 * 删除
	 * @author lilei
	 * @date   2017年12月13日 下午3:26:17 
	 * @param menuEntity
	 */
	public void deleteMenu(MenuEntity menuEntity){
		menuDao.delete(menuEntity);
	}
	public List<MenuEntity> findByparentnum(String parentnum){
		return menuDao.findByparentnum(parentnum);
	}
	
	public MenuEntity findFirstByMenunum(String menunum){
		return menuDao.findFirstByMenunum(menunum);
	}
}
