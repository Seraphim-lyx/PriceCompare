package com.pricecompare.android.service;

import java.util.List;

import com.pricecompare.android.core.BrandMgr;
import com.pricecompare.android.core.TypeMgr;
import com.pricecompare.dao.product.Brand;
import com.pricecompare.dao.product.Type;
import com.pricecompare.util.DaoUtil;
public class BrandService {
	public static List<Brand> getBrand(String str){
		DaoUtil.begin();
		Type type = (Type)TypeMgr.forTypeByName(str);
		int i = type.getId();
		List<Brand> list = (List<Brand>)BrandMgr.forBrandListByType(i);
		DaoUtil.close();
		return list;
	}

}
