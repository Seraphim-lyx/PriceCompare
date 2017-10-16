package com.pricecompare.dao.author;

import java.io.Serializable;

public class webpage implements Serializable{
	private Integer sid;
	private String mername;
	private Float price;
	private String detail;
	private String image;
	private String category;
	private String categorydtl;
	private String fromweb;
	private String baseUrl;
	private String id;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getMername() {
		return mername;
	}
	public void setMername(String mername) {
		this.mername = mername;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getCategorydtl() {
		return categorydtl;
	}
	public void setCategorydtl(String categorydtl) {
		this.categorydtl = categorydtl;
	}
	
	public String getFromweb() {
		return fromweb;
	}
	public void setFromweb(String fromweb) {
		this.fromweb = fromweb;
	}
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	
}
