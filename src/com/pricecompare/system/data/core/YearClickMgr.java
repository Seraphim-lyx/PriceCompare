package com.pricecompare.system.data.core;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.pricecompare.dao.product.YearClick;
import com.pricecompare.util.DaoUtil;

public class YearClickMgr {
	public static List<YearClick> forYearList(Integer productid){
		DetachedCriteria dc=DetachedCriteria.forClass(YearClick.class)
				.add(Restrictions.eq("product.id",productid));
		return (List<YearClick>)DaoUtil.search(dc);
	}
	public static YearClick forYear(Integer productid,Integer year){
		DetachedCriteria dc=DetachedCriteria.forClass(YearClick.class)
				.add(Restrictions.eq("year", year))
				.add(Restrictions.eq("product.id", productid));
		return (YearClick)DaoUtil.get(dc);
	}
	public static List<YearClick> forYearOrder(Integer year,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(YearClick.class)
				.add(Restrictions.eq("year", year))
				.addOrder(Order.desc("count"));
		return (List<YearClick>)DaoUtil.search(dc,start,limit);
	}
	public static Integer forYearOrderCount(Integer year,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(YearClick.class)
				.setProjection(Projections.rowCount())
				.add(Restrictions.eq("year", year))
				.addOrder(Order.desc("count"));
		return ((Long)DaoUtil.search(dc).get(0)).intValue();
	}
	public static List<YearClick> brandForYearOrder(Integer brandid,Integer year,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(YearClick.class)
				.createAlias("product", "p")
				.add(Restrictions.eq("year", year))
				.add(Restrictions.eq("p.brand.id", brandid))
				.addOrder(Order.desc("count"));
		return (List<YearClick>)DaoUtil.search(dc,start,limit);
	}
	public static YearClick forYearClick(Integer productid,Integer year){
		DetachedCriteria dc=DetachedCriteria.forClass(YearClick.class)
				.add(Restrictions.eq("year", year))
				.add(Restrictions.eq("product.id",productid));
		return (YearClick)DaoUtil.get(dc);
	}
}
