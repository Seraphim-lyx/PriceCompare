package com.pricecompare.android.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.pricecompare.android.core.CustomerMgr;
import com.pricecompare.android.core.CustomerProductMgr;
import com.pricecompare.android.core.ProductMgr;
import com.pricecompare.dao.author.Customer;
import com.pricecompare.dao.author.CustomerProduct;
import com.pricecompare.dao.product.Comment;
import com.pricecompare.dao.product.Description;
import com.pricecompare.dao.product.Info;
import com.pricecompare.dao.product.Picture;
import com.pricecompare.dao.product.Product;
import com.pricecompare.util.DaoUtil;

public class CustomerProductAction extends ActionSupport{
	public String getListCustomerProduct() throws Exception{
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			JSONArray arr = new JSONArray();
			int userid = Integer.parseInt(""+request.getSession().getAttribute("userid"));
			Customer c = (Customer)CustomerMgr.customerForId(userid);
			List<Product> product = new ArrayList<Product>();
			for(int i = 0;i<c.getCustomerproduct().size();i++){
				product.add(c.getCustomerproduct().get(i).getProduct());
			}
			if(!product.isEmpty()){
				for(int j = 0;j<product.size();j++){
					JSONObject obj =new JSONObject();
	            	Info info = product.get(j).getInfo();
	            	Description description = product.get(j).getDescription();
	            	List<Picture> picture = product.get(j).getPicture();
	            	List<Comment> comment = product.get(j).getComment();
	                JSONObject pictureObj = new JSONObject();
	                JSONObject commentObj = new JSONObject();
	            	obj.put("productId", product.get(j).getId());
	            	obj.put("productName", info.getName());
	            	obj.put("productWebsite", info.getWebsite());
	            	obj.put("productWebAddress", info.getWebaddress());
	            	obj.put("productPrice",info.getPrice());
	            	for(int k = 0;k<picture.size();k++){
	            		pictureObj.put("picture"+k, picture.get(k).getAddress());
	            	}
	            	obj.put("pictures", pictureObj);
	            	obj.put("description", description);
	            	for(int l = 0;l<comment.size();l++){
	            		commentObj.put("comment"+l, comment.get(l).getComment());	
	            		commentObj.put("commentId"+l, comment.get(l).getCustomer().getName());
	            		commentObj.put("commentDate"+l, comment.get(l).getDate());
	            	}
	            	obj.put("comments", commentObj);
	            	obj.put("isNull", 1);
					arr.add(j, obj);
				}
				
			}
			if(!arr.isEmpty()){
				System.out.println(arr.toJSONString());
            	PrintWriter out = response.getWriter();  
                out.println(arr.toJSONString());
                out.flush();
                out.close();
			}
			else{
				JSONObject json = new JSONObject();
				json.put("isNull", 0);
				arr.add(0, json);
        		PrintWriter out = response.getWriter();  
                out.println(arr.toJSONString());
                out.flush();
                out.close();
            }
		}
		catch(Exception e){
			e.printStackTrace();
		}
		DaoUtil.close();
		return SUCCESS;
	}
	public String isCustomerProduct()throws Exception{
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			JSONArray arr = new JSONArray();
			JSONObject obj = new JSONObject();
			int pid = Integer.parseInt(request.getParameter("productid"));
			Customer c = (Customer)CustomerMgr.customerForId(Integer.parseInt(""+request.getSession().getAttribute("userid")));
			int j = -1;
			for(int i=0;i<c.getCustomerproduct().size();i++){
				if(c.getCustomerproduct().get(i).getProduct().getId()==pid){
					j = 1;
					break;
				}
				else{
					j = 0;
				}
			}
			obj.put("isCustomerProduct", j);
			PrintWriter out = response.getWriter();  
            out.println(obj.toJSONString());
            out.flush();
            out.close();
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e.toString());
		}
		DaoUtil.close();
		return SUCCESS;
	}
	public String setCustomerProduct(){
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			JSONArray arr = new JSONArray();
			JSONObject obj = new JSONObject();
			int productid = Integer.parseInt(request.getParameter("productid"));
			Product p = (Product)ProductMgr.productForProductId(productid);
			Customer c = (Customer)CustomerMgr.customerForId(Integer.parseInt(""+request.getSession().getAttribute("userid")));
			CustomerProduct cp = new CustomerProduct();
			cp.setCustomer(c);
			cp.setProduct(p);
			List<CustomerProduct> list = c.getCustomerproduct();
			list.add(cp);
			c.setCustomerproduct(list);
			DaoUtil.save(cp);
			DaoUtil.update(c);
			DaoUtil.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e.toString());
		}
		DaoUtil.close();
		return SUCCESS;
	}
	public String deleteCustomerProduct(){
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			JSONArray arr = new JSONArray();
			JSONObject obj = new JSONObject();
			int productid = Integer.parseInt(request.getParameter("productid"));
			Product p = (Product)ProductMgr.productForProductId(productid);
			int userid = Integer.parseInt(""+request.getSession().getAttribute("userid"));
			CustomerProduct cp = (CustomerProduct)CustomerProductMgr.CustomerProductForCP(productid,userid);
			Customer c = (Customer)CustomerMgr.customerForId(userid);
			List<CustomerProduct> list = c.getCustomerproduct();
			for(int i = 0;i<list.size();i++){
				if(list.get(i).equals(cp)){
					list.remove(i);
					break;
				}
			}
			c.setCustomerproduct(list);
			DaoUtil.update(c);
			DaoUtil.delete(cp);
			DaoUtil.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e.toString());
		}
		DaoUtil.close();
		return SUCCESS;
	}


}
