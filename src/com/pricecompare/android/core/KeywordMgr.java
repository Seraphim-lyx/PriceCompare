package com.pricecompare.android.core;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.pricecompare.dao.author.Keyword;
import com.pricecompare.util.DaoUtil;

public class KeywordMgr {
	public static List<Keyword> forAllKeyword(){
		DetachedCriteria dc=DetachedCriteria.forClass(Keyword.class);
		return (List<Keyword>)DaoUtil.search(dc);
	}
	public static Keyword KeywordForId(int id){
		DetachedCriteria dc=DetachedCriteria.forClass(Keyword.class)
				.add(Restrictions.eq("id", id));
		return (Keyword)DaoUtil.get(dc);
	}
	public static  Keyword KeywordForWord(String word,int userid){
		DetachedCriteria dc=DetachedCriteria.forClass(Keyword.class)
				.add(Restrictions.eq("word", word))
				.add(Restrictions.eq("customer.id", userid));
		return (Keyword)DaoUtil.get(dc);
	}
}