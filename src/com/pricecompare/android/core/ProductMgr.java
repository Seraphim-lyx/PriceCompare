package com.pricecompare.android.core;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.pricecompare.dao.product.Comment;
import com.pricecompare.dao.product.Product;
import com.pricecompare.util.DaoUtil;

public class ProductMgr {
	public static List<Product> forAllProduct(){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.addOrder(Order.desc("id"));
		return (List<Product>)DaoUtil.search(dc);
	}
	public static List<Product> productForList(Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class);
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForType(Integer id,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("brand", "b")
				.add(Restrictions.eq("b.type.id", id));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForBrand(Integer brandid,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.add(Restrictions.eq("brand.id", brandid));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForName(String name,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info", "inf")
				.add(Restrictions.like("inf.name","%"+name+"%" ));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForNameByPriceAsc(String name,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info", "inf")
				.add(Restrictions.like("inf.name","%"+name+"%" ))
				.addOrder(Order.asc("inf.price"));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForNameByPriceDesc(String name,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info", "inf")
				.add(Restrictions.like("inf.name","%"+name+"%" ))
				.addOrder(Order.desc("inf.price"));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForPriceRange(Float minprice,Float maxprice,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info","inf")
				.add(Restrictions.between("inf.price", minprice, maxprice));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForPointed(Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.add(Restrictions.eq("pointed", 1));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForPricePointed(Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info", "in")
				.add(Restrictions.eq("pointed", 1))
				.addOrder(Order.desc("in.price"));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List groupProduct(Integer typeid){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info", "in")
				.add(Restrictions.eq("type.id", typeid))
				.setProjection(Projections.projectionList()
						.add(Projections.max("in.price"))
						.add(Projections.min("in.price"))
						.add(Projections.avg("in.price"))
						
						
						);
		return (List)DaoUtil.search(dc);
	}
	public static Product productForProductId(int productid){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.add(Restrictions.eq("id", productid));
		return (Product)DaoUtil.get(dc);
	}
	public static List<Product> productForNameByPriceAsc(String name, String brand, Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info", "inf")
				.createAlias("brand", "b")
				.add(Restrictions.like("b.name","%"+brand+"%"))
				.add(Restrictions.like("inf.name","%"+name+"%" ))
				.addOrder(Order.asc("inf.price"));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForNameByPriceDesc(String name, String brand, Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info", "inf")
				.createAlias("brand", "b")
				.add(Restrictions.like("b.name","%"+brand+"%"))
				.add(Restrictions.like("inf.name","%"+name+"%" ))
				.addOrder(Order.desc("inf.price"));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForNameByProductIdAsc(String name, String brand, Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info", "inf")
				.createAlias("brand", "b")
				.add(Restrictions.like("b.name","%"+brand+"%"))
				.add(Restrictions.like("inf.name","%"+name+"%" ))
				.addOrder(Order.asc("id"));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForNameByProductIdDesc(String name, String brand, Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info", "inf")
				.createAlias("brand", "b")
				.add(Restrictions.like("b.name","%"+brand+"%"))
				.add(Restrictions.like("inf.name","%"+name+"%" ))
				.addOrder(Order.desc("id"));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> forAllProduct(Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.addOrder(Order.desc("id"));
		return (List<Product>)DaoUtil.search(dc, start, limit);
	}
	public static List<Product> productForName(String name){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info", "inf")
				.add(Restrictions.like("inf.name","%"+name+"%" ))
				.addOrder(Order.asc("inf.price"));
		return (List<Product>)DaoUtil.search(dc);
	}

}
