package com.pricecompare.system.android.core;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.pricecompare.dao.author.Customer;
import com.pricecompare.dao.author.CustomerProduct;
import com.pricecompare.dao.author.Keyword;
import com.pricecompare.dao.product.Product;
import com.pricecompare.util.DaoUtil;

public class CustomerMgr {
	public static List<Customer> forCustomerList(Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Customer.class);
			return (List<Customer>)DaoUtil.search(dc,start,limit);
	}
	public static List<Customer> forCustomerNameList(String name,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Customer.class)
				.add(Restrictions.like("name", "%"+name+"%"));
		return (List<Customer>)DaoUtil.search(dc,start,limit);
	}
	public static Long forKeywordCount(String keyword){
		DetachedCriteria dc=DetachedCriteria.forClass(Keyword.class)
				.setProjection(Projections.rowCount())
				.add(Restrictions.eq("word", keyword));
		return (Long)DaoUtil.search(dc).get(0);
	}
	public static Long forProductCount(Integer productid){
		DetachedCriteria dc=DetachedCriteria.forClass(CustomerProduct.class)
				.setProjection(Projections.rowCount())
				.add(Restrictions.eq("product.id", productid));
		return (Long)DaoUtil.search(dc).get(0);
	}
	
	public static List<CustomerProduct>forCustomerProductList(Integer productid){
		DetachedCriteria dc=DetachedCriteria.forClass(CustomerProduct.class)
				.add(Restrictions.eq("product.id", productid));
		return (List<CustomerProduct>)DaoUtil.search(dc);
	}
	public static Integer forCustomerProductSex(Integer productid,Integer sex){
		DetachedCriteria dc=DetachedCriteria.forClass(CustomerProduct.class)
				.createAlias("customer", "c")
				.setProjection(Projections.rowCount())
				.add(Restrictions.eq("product.id", productid))
				.add(Restrictions.eq("c.sex", sex));
		
		return ((Long)DaoUtil.search(dc).get(0)).intValue();
	}
	public static Integer forCustomerProductEducation(Integer productid,Integer edu){
		DetachedCriteria dc=DetachedCriteria.forClass(CustomerProduct.class)
				.createAlias("customer", "c")
				.setProjection(Projections.rowCount())
				.add(Restrictions.eq("product.id", productid))
				.add(Restrictions.eq("c.education", edu));
		
		return ((Long)DaoUtil.search(dc).get(0)).intValue();
	}
	public static Integer forCustomerProductSexForEdu(Integer productid,Integer sex,Integer edu){
		DetachedCriteria dc=DetachedCriteria.forClass(CustomerProduct.class)
				.createAlias("customer", "c")
				.setProjection(Projections.rowCount())
				.add(Restrictions.eq("product.id", productid))
				.add(Restrictions.eq("c.sex", sex))
				.add(Restrictions.eq("c.education", edu));
		
		return ((Long)DaoUtil.search(dc).get(0)).intValue();
	}
	public static Integer forCustomerProductEducationForSex(Integer productid,Integer edu,Integer sex){
		DetachedCriteria dc=DetachedCriteria.forClass(CustomerProduct.class)
				.createAlias("customer", "c")
				.setProjection(Projections.rowCount())
				.add(Restrictions.eq("product.id", productid))
				.add(Restrictions.eq("c.sex", sex))
				.add(Restrictions.eq("c.education", edu));
		
		return ((Long)DaoUtil.search(dc).get(0)).intValue();
	}
}
