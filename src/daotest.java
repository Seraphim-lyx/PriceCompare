import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;



import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pricecompare.tt;
import com.pricecompare.ttt;
import com.pricecompare.dao.author.Author;
import com.pricecompare.dao.author.Customer;
import com.pricecompare.dao.author.CustomerProduct;
import com.pricecompare.dao.author.Role;
import com.pricecompare.dao.author.User;
import com.pricecompare.dao.author.webpage;
import com.pricecompare.dao.product.Brand;
import com.pricecompare.dao.product.Comment;
//import com.pricecompare.dao.product.Brand;
import com.pricecompare.dao.product.Info;
import com.pricecompare.dao.product.MonthClick;
import com.pricecompare.dao.product.Picture;
import com.pricecompare.dao.product.Product;
import com.pricecompare.dao.product.SeasonClick;
import com.pricecompare.dao.product.Type;
import com.pricecompare.dao.product.YearClick;
import com.pricecompare.system.android.core.CustomerMgr;
import com.pricecompare.system.android.service.AndroidService;
import com.pricecompare.system.author.core.AuthorMgr;
import com.pricecompare.system.author.core.RoleMgr;
import com.pricecompare.system.author.core.UserMgr;
import com.pricecompare.system.data.core.MonthClickMgr;
import com.pricecompare.system.data.core.SeasonClickMgr;
import com.pricecompare.system.data.core.YearClickMgr;
import com.pricecompare.system.data.service.DataActionService;
import com.pricecompare.system.product.core.ProductMgr;
import com.pricecompare.system.product.core.ProductTypeMgr;
import com.pricecompare.util.DaoUtil;


public class daotest {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		DaoUtil.begin();
		CustomerProduct cp=(CustomerProduct)DaoUtil.get(CustomerProduct.class,3);
//		DaoUtil.delete(cp);
//		DaoUtil.commit();
		
		DaoUtil.close();
	}		
}
