package com.pricecompare.dao.product;

public class SeasonClick {
	private Integer id;
	private Integer season;
	private Integer count=0;
	private YearClick yearclick;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSeason() {
		return season;
	}
	public void setSeason(Integer season) {
		this.season = season;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public YearClick getYearclick() {
		return yearclick;
	}
	public void setYearclick(YearClick yearclick) {
		this.yearclick = yearclick;
	}
	
	
}
