package com.pricecompare.dao.product;

public class MonthClick {
	private Integer id;
	private Integer month;
	private Integer count=0;
	private YearClick yearclick;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
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
