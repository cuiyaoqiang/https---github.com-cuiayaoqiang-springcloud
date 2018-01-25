package com.bh.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
*
* @author lilei
* @version 创建时间：2017年12月9日 上午11:26:06
* 
*
*/
@Entity(name="menu")
public class MenuEntity {
	@Id
	@GeneratedValue
	private Integer id;
	private String menunum;//菜单编号
	private String parentnum;//菜单父编号
	private String menuname;//菜单名
	private String menuurl;//菜单url
	public MenuEntity() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMenunum() {
		return menunum;
	}
	public void setMenunum(String menunum) {
		this.menunum = menunum;
	}
	public String getParentnum() {
		return parentnum;
	}
	public void setParentnum(String parentnum) {
		this.parentnum = parentnum;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public String getMenuurl() {
		return menuurl;
	}
	public void setMenuurl(String menuurl) {
		this.menuurl = menuurl;
	}
	@Override
	public String toString() {
		return "MenuEntity [id=" + id + ", menunum=" + menunum + ", parentnum=" + parentnum + ", menuname=" + menuname
				+ ", menuurl=" + menuurl + "]";
	}

}
