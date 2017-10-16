package com.pricecompare.dao.author;

import java.util.ArrayList;
import java.util.List;

public class Role {
	private Integer id;
	
	private String name;
	
	private List<Author> author=new ArrayList<Author>();

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

	public List<Author> getAuthor() {
		return author;
	}

	public void setAuthor(List<Author> author) {
		this.author = author;
	}

	
	
	
}
