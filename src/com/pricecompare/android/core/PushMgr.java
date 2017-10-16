package com.pricecompare.android.core;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.pricecompare.dao.product.Push;
import com.pricecompare.util.DaoUtil;

public class PushMgr {
	public static List<Push> forAllPush(){
		DetachedCriteria dc=DetachedCriteria.forClass(Push.class);
		return (List<Push>)DaoUtil.search(dc);
	}

}
