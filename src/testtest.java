import com.alibaba.fastjson.JSONObject;
import com.pricecompare.dao.product.Type;
import com.pricecompare.util.DaoUtil;


public class testtest {
	public static void f(){
		Type type=new Type();
		type.setName("fuck");
		DaoUtil.save(type);
		DaoUtil.commit();
	}
}
