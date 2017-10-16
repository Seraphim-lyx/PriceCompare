package com.pricecompare.android.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionSupport;
import com.pricecompare.android.core.CustomerMgr;
import com.pricecompare.android.core.ProductMgr;
import com.pricecompare.dao.author.Customer;
import com.pricecompare.dao.product.Comment;
import com.pricecompare.dao.product.Product;
import com.pricecompare.util.DaoUtil;

public class CommentAction extends ActionSupport{
	public String comment() throws Exception{
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");  
            String comment = request.getParameter("comment");
            int productid = Integer.parseInt(request.getParameter("productid"));
            Customer customer = (Customer)CustomerMgr.customerForId(Integer.parseInt(""+request.getSession().getAttribute("userid")));
            JSONArray arr = new JSONArray();
            JSONObject obj =new JSONObject();
            Comment c = new Comment();
            c.setComment(comment);
            c.setCustomer(customer);
            c.setDate(new Date());
            DaoUtil.save(c);
            DaoUtil.commit();
            DaoUtil.close();
            DaoUtil.begin();
            Product p = (Product)ProductMgr.productForProductId(productid);
            List<Comment> list = (List<Comment>)p.getComment();
            list.add(c);
            p.setComment(list);
            DaoUtil.update(p);
            DaoUtil.commit();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		DaoUtil.close();
		return SUCCESS;
	}
	public String getListComment() throws Exception{
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			int productid = Integer.parseInt(request.getParameter("productid"));
			Product p = (Product)ProductMgr.productForProductId(productid);
            List<Comment> list = (List<Comment>)p.getComment();
            JSONObject obj =new JSONObject();
            JSONObject commentObj = new JSONObject();
            for(int i=0; i<list.size(); i++){
            	commentObj.put("comment"+i, list.get(i).getComment());	
            	commentObj.put("commentId"+i, list.get(i).getCustomer().getName());
            	commentObj.put("commentDate"+i, list.get(i).getDate());
            }
            obj.put("isNull", 1);
            obj.put("comments", commentObj);
            if(!list.isEmpty()){
            	System.out.println(JSON.toJSONString(obj,SerializerFeature.DisableCircularReferenceDetect));
            	PrintWriter out = response.getWriter();  
                out.println(obj.toJSONString());
                out.flush();
                out.close();
            } 
            else{
				obj.put("isNull", 0);
        		PrintWriter out = response.getWriter();  
                out.println(obj.toJSONString());
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

}
