package com.pricecompare.android.service;

import java.util.List;

import com.pricecompare.android.core.ProductMgr;
import com.pricecompare.dao.product.Product;
import com.pricecompare.util.DaoUtil;

public class ProductService {
	public List<Product> getProductList(){
		DaoUtil.begin();
		List<Product> product = (List<Product>)ProductMgr.productForList(0, 20);
		DaoUtil.close();
		return product;
	}
	public List<Product> getMoreProduct(int i){
		DaoUtil.begin();
		List<Product> product = (List<Product>)ProductMgr.productForList(0, 20*i);
		DaoUtil.close();
		return product;
	}

}
