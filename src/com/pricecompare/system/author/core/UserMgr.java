package com.pricecompare.system.author.core;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.pricecompare.dao.author.User;
import com.pricecompare.util.DaoUtil;

public class UserMgr {
	public static User getUser(Integer id){
		DetachedCriteria dc=DetachedCriteria.forClass(User.class)
				.add(Restrictions.eq("id", id));
		return (User)DaoUtil.get(dc);
	}
	public static User getByName(String username){
		DetachedCriteria dc=DetachedCriteria.forClass(User.class)
				.add(Restrictions.eq("name",username));
		return (User)DaoUtil.get(dc);
		
	}
	
	public static List<User> forAllUser(){
		DetachedCriteria dc=DetachedCriteria.forClass(User.class);
		return (List<User>)DaoUtil.search(dc);
	}
	public static List<User> forAllUser(Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(User.class);
		return (List<User>)DaoUtil.search(dc,start,limit);
	}
	public static List<User> userSearch(String username,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(User.class)
				.add(Restrictions.like("name", "%"+username+"%"));
		return (List<User>)DaoUtil.search(dc,start,limit);
	}
	public static List<User> roleSearch(Integer id){
		DetachedCriteria dc=DetachedCriteria.forClass(User.class)
				.add(Restrictions.eq("role.id", id));
		return (List<User>)DaoUtil.search(dc);
	}
}
