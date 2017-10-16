package com.pricecompare.dao.product;

public class Info {
	private Integer id;
	private Float price=Float.parseFloat("0");
	private String name;
	private String webaddress;
	private String website;
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
	public String getWebaddress() {
		return webaddress;
	}
	public void setWebaddress(String webaddress) {
		this.webaddress = webaddress;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Float getPrice() {
		return price;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
}
