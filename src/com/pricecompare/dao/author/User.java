package com.pricecompare.dao.author;

import java.util.Date;

public class User {
	private Integer id;
	
	private String name;
	
	private byte[] password;
	
	private byte[] salt;
	
	private Role role;
	
	private Date loginTime;
	
	private Date regitTime;
	
	private Date editTime;
	
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
	
	public byte[] getPassword() {
		return password;
	}
	public void setPassword(byte[] password) {
		this.password = password;
	}
	public byte[] getSalt() {
		return salt;
	}
	public void setSalt(byte[] salt) {
		this.salt = salt;
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
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	
}
