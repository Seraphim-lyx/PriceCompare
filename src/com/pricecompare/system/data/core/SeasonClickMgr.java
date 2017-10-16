package com.pricecompare.system.data.core;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.pricecompare.dao.product.SeasonClick;
import com.pricecompare.util.DaoUtil;

public class SeasonClickMgr {
	public static List<SeasonClick> forSeasonList(Integer year,Integer productid){
		DetachedCriteria dc=DetachedCriteria.forClass(SeasonClick.class)
				.createAlias("yearclick", "yc")
				.add(Restrictions.eq("yc.year",year))
				.add(Restrictions.eq("yc.product.id",productid));
		return (List<SeasonClick>)DaoUtil.search(dc);
	}
	public static List<SeasonClick> forSeasonOrder(Integer year,Integer season,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(SeasonClick.class)
				.createAlias("yearclick", "yc")
				.add(Restrictions.eq("yc.year", year))
				.add(Restrictions.eq("season", season))
				.addOrder(Order.desc("count"));
		return (List<SeasonClick>)DaoUtil.search(dc,start,limit);
	}
	public static Integer forSeasonOrderCount(Integer year,Integer season,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(SeasonClick.class)
				.setProjection(Projections.rowCount())
				.createAlias("yearclick", "yc")
				.add(Restrictions.eq("yc.year", year))
				.add(Restrictions.eq("season", season))
				.addOrder(Order.desc("count"));
		return ((Long)DaoUtil.search(dc).get(0)).intValue();
	}
	public static List<SeasonClick> typeForSeasonOrder(Integer year,Integer season,Integer typeid,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(SeasonClick.class)
				.createAlias("yearclick", "yc")
				.createAlias("yearclick.product","p")
				.add(Restrictions.eq("yc.year", year))
				.add(Restrictions.eq("p.type.id", typeid))
				.add(Restrictions.eq("season", season))
				.addOrder(Order.desc("count"));
		return (List<SeasonClick>)DaoUtil.search(dc, start, limit);
	}
	public static SeasonClick forSeasonClick(Integer season,Integer productid,Integer year){
		DetachedCriteria dc=DetachedCriteria.forClass(SeasonClick.class)
				.createAlias("yearclick", "yc")
				.add(Restrictions.eq("yc.product.id", productid))
				.add(Restrictions.eq("season", season))
				.add(Restrictions.eq("yc.year", year));
		return (SeasonClick)DaoUtil.get(dc);
	}
	public static List<SeasonClick> forSeasonClickByYearId(Integer yearid){
		DetachedCriteria dc=DetachedCriteria.forClass(SeasonClick.class)
				.add(Restrictions.eq("yearclick.id", yearid));
		return (List<SeasonClick>)DaoUtil.search(dc);
	}
}
