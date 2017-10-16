package com.pricecompare.system.author.core;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.pricecompare.dao.author.webpage;
import com.pricecompare.util.DaoUtil;

public class AuthorMgr {
	public static List<webpage> forList(){
		DetachedCriteria dc=DetachedCriteria.forClass(webpage.class);
		return (List<webpage>)DaoUtil.search(dc,0,3);
	}
}
