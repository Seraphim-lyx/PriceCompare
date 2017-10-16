package com.pricecompare.android.core;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.pricecompare.dao.author.Customer;
import com.pricecompare.util.DaoUtil;

public class CustomerMgr {
	public static List<Customer> forAllCustomer(){
		DetachedCriteria dc=DetachedCriteria.forClass(Customer.class);
		return (List<Customer>)DaoUtil.search(dc);
	}
	public static Customer customerForId(int id){
		DetachedCriteria dc=DetachedCriteria.forClass(Customer.class)
				.add(Restrictions.eq("id", id));
		return (Customer)DaoUtil.get(dc);
	}
	public static  Customer customerForName(String name){
		DetachedCriteria dc=DetachedCriteria.forClass(Customer.class)
				.add(Restrictions.eq("name", name));
		return (Customer)DaoUtil.get(dc);
	}
	public static Customer customerForPhone(String phone){
		DetachedCriteria dc=DetachedCriteria.forClass(Customer.class)
				.add(Restrictions.eq("phone", phone));
		return (Customer)DaoUtil.get(dc);
	}
}
