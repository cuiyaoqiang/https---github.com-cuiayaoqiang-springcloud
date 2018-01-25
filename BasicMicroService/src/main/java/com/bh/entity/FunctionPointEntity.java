package com.bh.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
*
* @author lilei
* @version 创建时间：2017年12月11日 上午10:05:19
* 
*
*/
@Entity(name="functionPoint")
public class FunctionPointEntity {

	@Id
	@GeneratedValue
	private Integer id;
	private String num;//功能点编号
	private String name;//功能点名
	private String url;//功能点url
	private String menunum;//菜单编号
	private String datalevel;//数据权限等级
	
	public FunctionPointEntity() {
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMenunum() {
		return menunum;
	}
	public void setMenunum(String menunum) {
		this.menunum = menunum;
	}
	public String getDatalevel() {
		return datalevel;
	}
	public void setDatalevel(String datalevel) {
		this.datalevel = datalevel;
	}
	@Override
	public String toString() {
		return "FunctionPointEntity [id=" + id + ", num=" + num + ", name=" + name + ", url=" + url + ", menunum="
				+ menunum + ", datalevel=" + datalevel + "]";
	}
	
}
