package com.pricecompare.system.product.core;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.pricecompare.dao.product.Product;
import com.pricecompare.util.DaoUtil;

public class ProductMgr {
	public static List<Product> productForList(Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class);
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static Integer productForListCount(){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.setProjection(Projections.rowCount());
		return ((Long)DaoUtil.search(dc).get(0)).intValue();
	}
	public static List<Product> productForType(Integer id,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("brand", "b")
				.add(Restrictions.eq("b.type.id", id));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForTypeDesc(Integer id,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("brand", "b")
				.createAlias("info", "inf")
				.add(Restrictions.eq("b.type.id", id))
				.addOrder(Order.desc("inf.price"));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForTypeAsc(Integer id,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("brand", "b")
				.createAlias("info", "inf")
				.add(Restrictions.eq("b.type.id", id))
				.addOrder(Order.asc("inf.price"));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForBrand(Integer brandid,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.add(Restrictions.eq("brand.id", brandid));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForBrandDesc(Integer brandid,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info", "inf")
				.add(Restrictions.eq("brand.id", brandid))
				.addOrder(Order.desc("inf.price"));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForBrandAsc(Integer brandid,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info", "inf")
				.add(Restrictions.eq("brand.id", brandid))
				.addOrder(Order.asc("inf.price"));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForName(String name,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info", "inf")
				.createAlias("brand", "b")
				.createAlias("brand.type", "bt")
				.add(Restrictions.or(Restrictions.like("inf.name","%"+name+"%" ),Restrictions.like("b.name", "%"+name+"%"),Restrictions.like("bt.name", "%"+name+"%")));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static Integer productForNameCount(String name,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.setProjection(Projections.rowCount())
				.createAlias("info", "inf")
				.createAlias("brand", "b")
				.createAlias("brand.type", "bt")
				.add(Restrictions.or(Restrictions.like("inf.name","%"+name+"%" ),Restrictions.like("b.name", "%"+name+"%"),Restrictions.like("bt.name", "%"+name+"%")));
		return ((Long)DaoUtil.search(dc).get(0)).intValue();
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
	public static List<Product> productForPriceRangeDesc(Float minprice,Float maxprice,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info","inf")
				.add(Restrictions.between("inf.price", minprice, maxprice))
				.addOrder(Order.desc("inf.price"));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForPriceRangeAsc(Float minprice,Float maxprice,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info","inf")
				.add(Restrictions.between("inf.price", minprice, maxprice))
				.addOrder(Order.asc("inf.price"));
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
	public static List<Product> productForPricePointedAsc(Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info", "in")
				.add(Restrictions.eq("pointed", 1))
				.addOrder(Order.asc("in.price"));
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
	public static List<Product> productForMix(String website,String productname,Float minprice,Float maxprice,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info","inf")
				.add(Restrictions.between("inf.price", minprice, maxprice))
				.add(Restrictions.like("inf.name", "%"+productname+"%"))
				.add(Restrictions.like("inf.website", "%"+website+"%"));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForMixDesc(String website,String productname,Float minprice,Float maxprice,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info","inf")
				.add(Restrictions.between("inf.price", minprice, maxprice))
				.add(Restrictions.like("inf.name", "%"+productname+"%"))
				.add(Restrictions.like("inf.website", "%"+website+"%"))
				.addOrder(Order.desc("inf.price"));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForMixAsc(String website,String productname,Float minprice,Float maxprice,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info","inf")
				.add(Restrictions.between("inf.price", minprice, maxprice))
				.add(Restrictions.like("inf.name", "%"+productname+"%"))
				.add(Restrictions.like("inf.website", "%"+website+"%"))
				.addOrder(Order.asc("inf.price"));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForMixType(String website,String productname,Float minprice,Float maxprice,Integer typeid,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info","inf")
				.createAlias("brand", "b")
				.add(Restrictions.between("inf.price", minprice, maxprice))
				.add(Restrictions.like("inf.name","%"+productname+"%"))
				.add(Restrictions.like("inf.website","%"+website+"%"))
				.add(Restrictions.eq("b.type.id", typeid));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForMixTypeDesc(String website,String productname,Float minprice,Float maxprice,Integer typeid,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info","inf")
				.createAlias("brand", "b")
				.add(Restrictions.between("inf.price", minprice, maxprice))
				.add(Restrictions.like("inf.name","%"+productname+"%"))
				.add(Restrictions.like("inf.website","%"+website+"%"))
				.add(Restrictions.eq("b.type.id", typeid))
				.addOrder(Order.desc("inf.price"));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForMixTypeAsc(String website,String productname,Float minprice,Float maxprice,Integer typeid,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info","inf")
				.createAlias("brand", "b")
				.add(Restrictions.between("inf.price", minprice, maxprice))
				.add(Restrictions.like("inf.name","%"+productname+"%"))
				.add(Restrictions.like("inf.website","%"+website+"%"))
				.add(Restrictions.eq("b.type.id", typeid))
				.addOrder(Order.asc("inf.price"));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForMixBrand(String website,String productname,Float minprice,Float maxprice,Integer brandid,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info","inf")
				.createAlias("brand", "b")
				.add(Restrictions.between("inf.price", minprice, maxprice))
				.add(Restrictions.like("inf.name", "%"+productname+"%"))
				.add(Restrictions.like("inf.website","%"+website+"%"))
				.add(Restrictions.eq("b.id", brandid));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForMixBrandDesc(String website,String productname,Float minprice,Float maxprice,Integer brandid,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info","inf")
				.createAlias("brand", "b")
				.add(Restrictions.between("inf.price", minprice, maxprice))
				.add(Restrictions.like("inf.name", "%"+productname+"%"))
				.add(Restrictions.like("inf.website","%"+website+"%"))
				.add(Restrictions.eq("b.id", brandid))
				.addOrder(Order.desc("inf.price"));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
	public static List<Product> productForMixBrandAsc(String website,String productname,Float minprice,Float maxprice,Integer brandid,Integer start,Integer limit){
		DetachedCriteria dc=DetachedCriteria.forClass(Product.class)
				.createAlias("info","inf")
				.createAlias("brand", "b")
				.add(Restrictions.between("inf.price", minprice, maxprice))
				.add(Restrictions.like("inf.name", "%"+productname+"%"))
				.add(Restrictions.like("inf.website","%"+website+"%"))
				.add(Restrictions.eq("b.id", brandid))
				.addOrder(Order.asc("inf.price"));
		return (List<Product>)DaoUtil.search(dc,start,limit);
	}
}
