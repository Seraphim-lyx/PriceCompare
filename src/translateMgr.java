import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.pricecompare.dao.author.webpage;
import com.pricecompare.dao.product.Brand;
import com.pricecompare.dao.product.Type;
import com.pricecompare.util.DaoUtil;


public class translateMgr {
	public static List<webpage> forlist(){
		DetachedCriteria dc=DetachedCriteria.forClass(webpage.class);
		return (List<webpage>)DaoUtil.search(dc);
	}
	public static List<Type> forname(String type){
		DetachedCriteria dc=DetachedCriteria.forClass(Type.class)
				.add(Restrictions.like("name", "%"+type+"%"));
		return (List<Type>)DaoUtil.search(dc);
	}
	public static Type forType(String type){
		DetachedCriteria dc=DetachedCriteria.forClass(Type.class)
				.add(Restrictions.eq("name", type));
		return (Type)DaoUtil.get(dc);
	}
	public static Brand forBrand(String brand){
		DetachedCriteria dc=DetachedCriteria.forClass(Brand.class)
				.add(Restrictions.eq("name", brand));
		return (Brand)DaoUtil.get(dc);
	}
}
