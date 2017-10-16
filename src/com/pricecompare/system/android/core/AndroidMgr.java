package com.pricecompare.system.android.core;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.pricecompare.dao.product.Push;
import com.pricecompare.util.DaoUtil;

public class AndroidMgr {
	public static List<Push> forPushList(Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Push.class);
		return (List<Push>)DaoUtil.search(dc,start,limit);
	}
	public static List<Push> forPushListByProduct(Integer productid){
		DetachedCriteria dc=DetachedCriteria.forClass(Push.class)
				.add(Restrictions.eq("product.id", productid));
		return (List<Push>)DaoUtil.search(dc);
	}
}
