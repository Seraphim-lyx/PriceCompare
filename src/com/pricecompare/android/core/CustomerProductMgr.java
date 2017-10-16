package com.pricecompare.android.core;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.pricecompare.dao.author.CustomerProduct;
import com.pricecompare.util.DaoUtil;

public class CustomerProductMgr {
	public static List<CustomerProduct> forAllCustomerProduct(){
		DetachedCriteria dc=DetachedCriteria.forClass(CustomerProduct.class);
		return (List<CustomerProduct>)DaoUtil.search(dc);
	}
	public static CustomerProduct CustomerProductForId(int id){
		DetachedCriteria dc=DetachedCriteria.forClass(CustomerProduct.class)
				.add(Restrictions.eq("id", id));
		return (CustomerProduct)DaoUtil.get(dc);
	}
	public static  CustomerProduct CustomerProductForCP(int productid,int customerid){
		DetachedCriteria dc=DetachedCriteria.forClass(CustomerProduct.class)
				.add(Restrictions.eq("product.id", productid))
				.add(Restrictions.eq("customer.id", customerid));
		return (CustomerProduct)DaoUtil.get(dc);
	}

}
