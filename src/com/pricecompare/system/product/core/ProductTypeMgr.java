package com.pricecompare.system.product.core;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.pricecompare.dao.product.Brand;
import com.pricecompare.dao.product.Type;
import com.pricecompare.util.DaoUtil;

public class ProductTypeMgr {
	public static List<Type> forTypeList(Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Type.class)
				.add(Restrictions.isNull("relatename"));
		return (List<Type>)DaoUtil.search(dc,start,limit);
	}
	public static List<Brand> forBrandList(Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Brand.class)
				.add(Restrictions.isNull("relatename"));
		return (List<Brand>)DaoUtil.search(dc,start,limit);
	}
	public static List<Type> forTypeListByName(String typename,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Type.class)
				.add(Restrictions.like("name", "%"+typename+"%"))
				.add(Restrictions.isNull("relatename"));
		return (List<Type>)DaoUtil.search(dc,start,limit);
	}
	public static List<Brand> forBrandListByNameType(Integer typeid,String brandname,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Brand.class)
				.add(Restrictions.eq("type.id", typeid))
				.add(Restrictions.like("name", "%"+brandname+"%"))
				.add(Restrictions.isNull("relatename"));
		return (List<Brand>)DaoUtil.search(dc,start,limit);
	}
	public static List typeGroup(){
		DetachedCriteria dc=DetachedCriteria.forClass(Type.class)
				.setProjection(Projections.projectionList().add(Projections.max("name")));

		
		return (List)DaoUtil.search(dc);
	}
	public static List<Brand> forBrandListByType(Integer typeid){
		DetachedCriteria dc=DetachedCriteria.forClass(Brand.class)
				.add(Restrictions.eq("type.id", typeid))
				.add(Restrictions.isNull("relatename"));
		return (List<Brand>)DaoUtil.search(dc);
	}
	public static List<Type> getRelateType(String typename){
		DetachedCriteria dc=DetachedCriteria.forClass(Type.class)
				.add(Restrictions.eq("relatename", typename));
		return (List<Type>)DaoUtil.search(dc);
		
	}
	public static List<Brand> getRelateBrand(String brandname){
		DetachedCriteria dc=DetachedCriteria.forClass(Brand.class)
				.add(Restrictions.eq("relatename", brandname));
		return (List<Brand>)DaoUtil.search(dc);
		
	}
}
