package com.pricecompare.system.data.core;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.pricecompare.dao.product.MonthClick;
import com.pricecompare.dao.product.SeasonClick;
import com.pricecompare.util.DaoUtil;

public class MonthClickMgr {
	public static List<MonthClick> forMonthList(Integer year,Integer productid){
		DetachedCriteria dc=DetachedCriteria.forClass(MonthClick.class)
				.createAlias("yearclick", "yc")
				.add(Restrictions.eq("yc.year", year))
				.add(Restrictions.eq("yc.product.id",productid));
		return (List<MonthClick>)DaoUtil.search(dc);
	}
	public static List<MonthClick> forMonthOrder(Integer year,Integer month,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(MonthClick.class)
				.createAlias("yearclick", "yc")
				.add(Restrictions.eq("month", month))
				.add(Restrictions.eq("yc.year", year))
				.addOrder(Order.desc("count"));
		return (List<MonthClick>)DaoUtil.search(dc,start,limit);
	}
	public static List<MonthClick> forMonthOrderAsc(Integer year,Integer month,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(MonthClick.class)
				.createAlias("yearclick", "yc")
				.add(Restrictions.eq("month", month))
				.add(Restrictions.eq("yc.year", year))
				.addOrder(Order.asc("count"));
		return (List<MonthClick>)DaoUtil.search(dc,start,limit);
	}
	public static Integer forMonthOrderCount(Integer year,Integer month,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(MonthClick.class)
				.setProjection(Projections.rowCount())
				.createAlias("yearclick", "yc")
				.add(Restrictions.eq("month", month))
				.add(Restrictions.eq("yc.year", year))
				.addOrder(Order.desc("count"));
		return ((Long)DaoUtil.search(dc).get(0)).intValue();
	}
	public static List<MonthClick> forMonthPriceOrder(Integer year,Integer month,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(MonthClick.class)
				.createAlias("yearclick", "yc")
				.createAlias("yearclick.product", "p")
				.createAlias("yearclick.product.info","pi")
				.add(Restrictions.eq("month", month))
				.add(Restrictions.eq("yc.year", year))
				.addOrder(Order.desc("pi.price"));
		return (List<MonthClick>)DaoUtil.search(dc,start,limit);
	}
	public static MonthClick forMonthClick(Integer month,Integer productid,Integer year){
		DetachedCriteria dc=DetachedCriteria.forClass(MonthClick.class)
				.createAlias("yearclick", "yc")
				.add(Restrictions.eq("month", month))
				.add(Restrictions.eq("yc.product.id", productid))
				.add(Restrictions.eq("yc.year", year));
				
		return (MonthClick)DaoUtil.get(dc);
	}
	public static List<MonthClick> forMonthClickByYearId(Integer yearid){
		DetachedCriteria dc=DetachedCriteria.forClass(MonthClick.class)
				.add(Restrictions.eq("yearclick.id", yearid));
		return (List<MonthClick>)DaoUtil.search(dc);
	}
}
