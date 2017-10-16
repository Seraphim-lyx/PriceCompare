package com.pricecompare.entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Type {
	private Integer id;
	private String name;
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
