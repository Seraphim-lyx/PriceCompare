package com.pricecompare.system.record.core;

import java.util.List;

import com.pricecompare.dao.record.Record;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.pricecompare.util.DaoUtil;

public class RecordMgr {
	public static List<Record> recordForUser(Integer userid){
		DetachedCriteria dc=DetachedCriteria.forClass(Record.class)
				.add(Restrictions.eq("user.id", userid));
		return (List<Record>)DaoUtil.search(dc);
	}
	public static List<Record> recordList(Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Record.class)
				.addOrder(Order.desc("editTime"));
		return (List<Record>)DaoUtil.search(dc,start,limit);
	}
	public static List<Record> recordSearch(String search,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Record.class)
				.createAlias("user","u")
				.add(Restrictions.or(Restrictions.like("title", "%"+search+"%"),Restrictions.like("type", "%"+search+"%"),Restrictions.like("u.name", "%"+search+"%")));
		return (List<Record>)DaoUtil.search(dc,start,limit);
	}
}
