package com.pricecompare.android.core;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.pricecompare.dao.product.Type;
import com.pricecompare.util.DaoUtil;

public class TypeMgr {
	public static List<Type> forAllType(){
		DetachedCriteria dc=DetachedCriteria.forClass(Type.class);
		return (List<Type>)DaoUtil.search(dc);
	}
	public static List typeGroup(){
		DetachedCriteria dc=DetachedCriteria.forClass(Type.class)
				.setProjection(Projections.projectionList().add(Projections.max("name")));	
		return (List)DaoUtil.search(dc);
	}
	public static Type forTypeByName(String name){
		DetachedCriteria dc=DetachedCriteria.forClass(Type.class)
				.add(Restrictions.eq("name", name));
		return (Type)DaoUtil.get(dc);
	}

}
