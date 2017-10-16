package com.pricecompare.entity;

import java.util.ArrayList;
import java.util.List;

public class Product {
	private Integer id;
	private Info info;
	private Description description;
	private List<Picture> picture=new ArrayList<Picture>();
	private Brand brand;
	private List<Comment> comment=new ArrayList<Comment>();
	private Message message;
	private Integer pointed=0;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
	public Description getDescription() {
		return description;
	}
	public void setDescription(Description description) {
		this.description = description;
	}
	
	public List<Picture> getPicture() {
		return picture;
	}
	public void setPicture(List<Picture> picture) {
		this.picture = picture;
	}
	
	

	
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public Integer getPointed() {
		return pointed;
	}
	public void setPointed(Integer pointed) {
		this.pointed = pointed;
	}
	public List<Comment> getComment() {
		return comment;
	}
	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	
	
}
