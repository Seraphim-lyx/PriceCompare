package com.pricecompare.android.core;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.pricecompare.dao.product.Brand;
import com.pricecompare.dao.product.Type;
import com.pricecompare.util.DaoUtil;

public class BrandMgr {
	public static List<Brand> forAllBrand(){
		DetachedCriteria dc=DetachedCriteria.forClass(Brand.class);
		return (List<Brand>)DaoUtil.search(dc);
	}
	public static List<Brand> forBrandListByType(Integer typeid){
		DetachedCriteria dc=DetachedCriteria.forClass(Brand.class)
				.add(Restrictions.eq("type.id", typeid));
		return (List<Brand>)DaoUtil.search(dc);
	}

}
