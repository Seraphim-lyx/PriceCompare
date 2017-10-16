package com.pricecompare.dao.author;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pricecompare.dao.product.Product;

public class Customer {
	private Integer id;
	private String name;
	private String password;
	private String phone;
	private Date regitTime;
	private Date editTime;
	private Integer sex=0;//1=�У�2=Ů
	private Integer education=0;//1=Сѧ��2=���У�3=���У�4=���ƻ�����
	private Integer locked=0;
	private List<Keyword> keyword=new ArrayList<Keyword>();
	private List<CustomerProduct> customerproduct=new ArrayList<CustomerProduct>();
	
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
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getRegitTime() {
		return regitTime;
	}
	public void setRegitTime(Date regitTime) {
		this.regitTime = regitTime;
	}
	public Date getEditTime() {
		return editTime;
	}
	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}
	
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getEducation() {
		return education;
	}
	public void setEducation(Integer education) {
		this.education = education;
	}
	public Integer getLocked() {
		return locked;
	}
	public void setLocked(Integer locked) {
		this.locked = locked;
	}
	public List<Keyword> getKeyword() {
		return keyword;
	}
	public void setKeyword(List<Keyword> keyword) {
		this.keyword = keyword;
	}
	public List<CustomerProduct> getCustomerproduct() {
		return customerproduct;
	}
	public void setCustomerproduct(List<CustomerProduct> customerproduct) {
		this.customerproduct = customerproduct;
	}
	
	
	
	
	
}
