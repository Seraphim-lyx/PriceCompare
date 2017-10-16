package com.pricecompare.android.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionSupport;
import com.pricecompare.android.core.CustomerMgr;
import com.pricecompare.android.core.CustomerProductMgr;
import com.pricecompare.android.core.KeywordMgr;
import com.pricecompare.android.core.ProductMgr;
import com.pricecompare.android.core.PushMgr;
import com.pricecompare.android.core.BrandMgr;
import com.pricecompare.android.core.TypeMgr;
import com.pricecompare.dao.author.Customer;
import com.pricecompare.dao.author.CustomerProduct;
import com.pricecompare.dao.author.Keyword;
import com.pricecompare.dao.product.Comment;
import com.pricecompare.dao.product.Description;
import com.pricecompare.dao.product.Info;
import com.pricecompare.dao.product.Picture;
import com.pricecompare.dao.product.Product;
import com.pricecompare.dao.product.Push;
import com.pricecompare.dao.product.Brand;
import com.pricecompare.dao.product.Type;
import com.pricecompare.system.android.service.AndroidService;
import com.pricecompare.util.DaoUtil;

public class ProductAction extends ActionSupport{
	private static String name = "";
	private static String brand = "";
	private static int s = 0, page = 0, nextPage = 1;
	private HttpSession session;
	public String test() throws Exception{
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");  
            String send = request.getParameter("send"); 
            JSONArray arr = new JSONArray();
            List<Product> product = new ArrayList<Product>();
            switch(s){
            case 0:
            	product = (List<Product>)ProductMgr.forAllProduct(20*page, 20*nextPage);
            	break;
            case 1:
            	product = (List<Product>)ProductMgr.productForNameByProductIdAsc(name, brand, 20*page, 20*nextPage);
            	break;
            case 2:
            	product = (List<Product>)ProductMgr.productForNameByProductIdDesc(name, brand, 20*page, 20*nextPage);
            	break;
            case 3:
            	product = (List<Product>)ProductMgr.productForNameByPriceAsc(name, brand, 20*page, 20*nextPage);
            	break;
            case 4:
            	product = (List<Product>)ProductMgr.productForNameByPriceDesc(name, brand, 20*page, 20*nextPage);
            	break;
            }
            for(int i = 0;i <product.size();i++){
                JSONObject obj =new JSONObject();
            	Info info = product.get(i).getInfo();
            	Description description = product.get(i).getDescription();
            	List<Picture> picture = product.get(i).getPicture();
            	List<Comment> comment = product.get(i).getComment();
                JSONObject pictureObj = new JSONObject();
                JSONObject commentObj = new JSONObject();
            	obj.put("productId", product.get(i).getId());
            	obj.put("productName", info.getName());
            	obj.put("productWebsite", info.getWebsite());
            	obj.put("productWebAddress", info.getWebaddress());
            	obj.put("productPrice",info.getPrice());
            	for(int j = 0;j<picture.size();j++){
            		pictureObj.put("picture"+j, picture.get(j).getAddress());
            	}
            	obj.put("pictures", pictureObj);
            	obj.put("description", description);
            	for(int k = 0;k<comment.size();k++){
            		commentObj.put("comment"+k, comment.get(k).getComment());	
            		commentObj.put("commentId"+k, comment.get(k).getCustomer().getName());
            		commentObj.put("commentDate"+k, comment.get(k).getDate());
            	}
            	obj.put("comments", commentObj);
            	obj.put("isNull", 1);
            	arr.add(i, obj);
            }
            if(!arr.isEmpty()){
            	if(send.equals("send")){
                    PrintWriter out = response.getWriter();  
                    out.println(JSON.toJSONString(arr,SerializerFeature.DisableCircularReferenceDetect));
                    out.flush();
                    out.close();
            	}
            }
            else{
            	JSONObject json = new JSONObject();
				json.put("isNull", 0);
				arr.add(0, json);
            	PrintWriter out = response.getWriter();  
                out.println(JSON.toJSONString(arr,SerializerFeature.DisableCircularReferenceDetect));
                out.flush();
                out.close();
            }
            
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(brand);
		System.out.println(s);
		DaoUtil.close();
		return SUCCESS;
	}
	
	public String brand() throws Exception{
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");  
            String brand = request.getParameter("brand"); 
            System.out.println(brand);
            JSONArray arr = new JSONArray();
            JSONObject obj =new JSONObject();
            Type t = (Type)TypeMgr.forTypeByName(brand);
            List<Brand> brandProduct = (List<Brand>)BrandMgr.forBrandListByType(t.getId());
            System.out.println(brandProduct.size());
            for(int i = 0;i <brandProduct.size();i++){
            	arr.add(i, brandProduct.get(i));
            }
            PrintWriter out = response.getWriter();  
            out.println(arr.toJSONString());
            out.flush();
            out.close();
            System.out.println(arr.toJSONString().length());
            System.out.println(arr.toJSONString());
                        
		}
		catch(Exception e){
			e.printStackTrace();
		}
		DaoUtil.close();
		return SUCCESS;
	}
	public String type() throws Exception{
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");  
            String type = request.getParameter("type"); 
            System.out.println(type);
            JSONArray arr = new JSONArray();
            JSONObject obj =new JSONObject();
            List<Type> typeProduct = (List<Type>)TypeMgr.forAllType();
            System.out.println(typeProduct.size());
            for(int i = 0;i <typeProduct.size();i++){
            	arr.add(i, typeProduct.get(i));
            }
            PrintWriter out = response.getWriter();  
            out.println(arr.toJSONString());
            out.flush();
            out.close();
            System.out.println(arr.toJSONString().length());
            System.out.println(arr.toJSONString());
                        
		}
		catch(Exception e){
			e.printStackTrace();
		}
		DaoUtil.close();
		return SUCCESS;
	}
	
	
	public String search() throws Exception{
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");  
            String name = request.getParameter("name");
            this.setName(name);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("test:"+this.getName());
		DaoUtil.close();
		return SUCCESS;
	}
	public String searchBrand() throws Exception{
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");  
            String brand = request.getParameter("brand");
            this.setBrand(brand);
            this.setS(3);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("testBrand:"+this.getBrand());
		DaoUtil.close();
		return SUCCESS;
	}
	public String changeSequence() throws Exception{
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");  
            int i = Integer.parseInt(request.getParameter("s"));
            System.out.println("i="+i);
            this.setS(i);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("testS:"+this.getS());
		DaoUtil.close();
		return SUCCESS;
	}
	public String changePage() throws Exception{
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");  
            int i = Integer.parseInt(request.getParameter("page"));
            this.setPage(i);
            int j = Integer.parseInt(request.getParameter("nextPage"));
            this.setNextPage(j);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("test:"+this.getBrand());
		DaoUtil.close();
		return SUCCESS;
	}
	
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		ProductAction.name = name;
	}
	public static String getBrand() {
		return brand;
	}
	public static void setBrand(String brand) {
		ProductAction.brand = brand;
	}
	public static int getS() {
		return s;
	}
	public static void setS(int s) {
		ProductAction.s = s;
	}
	public static int getPage() {
		return page;
	}
	public static void setPage(int page) {
		ProductAction.page = page;
	}
	public static int getNextPage() {
		return nextPage;
	}
	public static void setNextPage(int nextPage) {
		ProductAction.nextPage = nextPage;
	}
	
	
}
