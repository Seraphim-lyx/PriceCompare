package com.pricecompare.dao.author;

public class Author {
	
	private Integer id;
	
	private String path;
	
	public Author(){
		
	}
	public Author(String path){
		this.path=path;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	
	
	
}
