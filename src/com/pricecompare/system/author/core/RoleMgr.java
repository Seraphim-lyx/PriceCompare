package com.pricecompare.system.author.core;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.pricecompare.dao.author.Author;
import com.pricecompare.dao.author.Role;
import com.pricecompare.util.DaoUtil;

public class RoleMgr {
	public static Role getByName(String name){
		DetachedCriteria dc=DetachedCriteria.forClass(Role.class)
				.add(Restrictions.eq("name", name));
		return (Role)DaoUtil.get(dc);
	}
	public static Role getByAuthor(Integer id){
		DetachedCriteria dc=DetachedCriteria.forClass(Role.class)
				.add(Restrictions.eq("author.id",id));
		return (Role)DaoUtil.get(dc);
	}
	public static List<Role> getRoleList(){
		DetachedCriteria dc=DetachedCriteria.forClass(Role.class);
		return (List<Role>)DaoUtil.search(dc);
	}
}
