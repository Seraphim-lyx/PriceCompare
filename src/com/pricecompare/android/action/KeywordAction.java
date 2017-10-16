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
import com.pricecompare.android.core.KeywordMgr;
import com.pricecompare.android.core.ProductMgr;
import com.pricecompare.dao.author.Customer;
import com.pricecompare.dao.author.Keyword;
import com.pricecompare.dao.product.Comment;
import com.pricecompare.dao.product.Description;
import com.pricecompare.dao.product.Info;
import com.pricecompare.dao.product.Picture;
import com.pricecompare.dao.product.Product;
import com.pricecompare.util.DaoUtil;

public class KeywordAction extends ActionSupport{
	public String getListKeyword() throws Exception{
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
			System.out.println("c.getKeyword().size()"+c.getKeyword().size());
			for(int i = 0;i<c.getKeyword().size();i++){
				List<Product> p = (List<Product>)ProductMgr.productForName(c.getKeyword().get(i).getWord());
				System.out.println("p.size = "+p.size());
				for(int j = 0;j<p.size();j++)
					product.add(p.get(j));
			}
			if(!product.isEmpty()){
				System.out.println(product.size());
				for(int x = 0;x<product.size();x++){
					JSONObject obj =new JSONObject();
	            	Info info = product.get(x).getInfo();
	            	Description description = product.get(x).getDescription();
	            	List<Picture> picture = product.get(x).getPicture();
	            	List<Comment> comment = product.get(x).getComment();
	                JSONObject pictureObj = new JSONObject();
	                JSONObject commentObj = new JSONObject();
	            	obj.put("productId", product.get(x).getId());
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
	            	arr.add(x, obj);
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
			System.out.println(e.toString());
		}
		DaoUtil.close();
		return SUCCESS;
	}
	
	public String getKeyword() throws Exception{
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			JSONArray arr = new JSONArray();
			Customer c = (Customer)CustomerMgr.customerForId(Integer.parseInt(""+request.getSession().getAttribute("userid")));
			for(int i = 0 ;i<c.getKeyword().size();i++){
				JSONObject obj = new JSONObject();
				obj.put("isNull", 1);
				obj.put("keyword", c.getKeyword().get(i).getWord());
				arr.add(i, obj);
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
			System.out.println(e.toString());
		}
		DaoUtil.close();
		return SUCCESS;
	}
	public String setKeyword() throws Exception{
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			JSONArray arr = new JSONArray();
			JSONObject obj = new JSONObject();
			String k = request.getParameter("keyword");
			Customer c = (Customer)CustomerMgr.customerForId(Integer.parseInt(""+request.getSession().getAttribute("userid")));
			int j = -1;
			for(int i = 0;i<c.getKeyword().size();i++){
				if(k.equals(c.getKeyword().get(i).getWord())){
					j = 1;
					break;
				}
				else{
					j = 0;
				}
			}
			if(j == 0){
				Keyword key = new Keyword();
				List<Keyword> list = c.getKeyword();
				key.setCustomer(c);
				key.setWord(k);
				DaoUtil.save(key);
				list.add(key);
				c.setKeyword(list);
				DaoUtil.update(c);
				DaoUtil.commit();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e.toString());
		}
		DaoUtil.close();
		return SUCCESS;
	}
	public String deleteKeyword(){
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			JSONArray arr = new JSONArray();
			JSONObject obj = new JSONObject();
			String key = request.getParameter("keyword");
			System.out.println(key);
			int userid = Integer.parseInt(""+request.getSession().getAttribute("userid"));
			Customer c = (Customer)CustomerMgr.customerForId(userid);
			Keyword k = (Keyword)KeywordMgr.KeywordForWord(key,userid);
			List<Keyword> list = c.getKeyword();
			for(int i = 0;i<list.size();i++){
				if(list.get(i).equals(k)){
					list.remove(i);
					break;
				}
			}
			c.setKeyword(list);
			DaoUtil.update(c);
			DaoUtil.delete(k);
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
