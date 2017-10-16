package com.pricecompare.dao.product;

import java.util.Date;

public class Brand {
	private Integer id;
	private String name;
	private Type type;
	private Date editTime;
	private String relatename;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Date getEditTime() {
		return editTime;
	}
	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}
	public String getRelatename() {
		return relatename;
	}
	public void setRelatename(String relatename) {
		this.relatename = relatename;
	}
	
}
