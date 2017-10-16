package com.pricecompare.system.data.core;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.pricecompare.dao.product.Click;
import com.pricecompare.util.DaoUtil;

public class ClickMgr {
	public static List<Click> clickListForMonth(Integer id,Integer year){
		DetachedCriteria dc=DetachedCriteria.forClass(Click.class)
				.add(Restrictions.eq("product.id", id))
				.add(Restrictions.eq("year", year));
		return (List<Click>)DaoUtil.search(dc);
	}
}
