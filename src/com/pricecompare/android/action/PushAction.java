package com.pricecompare.android.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionSupport;
import com.pricecompare.android.core.PushMgr;
import com.pricecompare.dao.product.Comment;
import com.pricecompare.dao.product.Description;
import com.pricecompare.dao.product.Info;
import com.pricecompare.dao.product.Picture;
import com.pricecompare.dao.product.Product;
import com.pricecompare.dao.product.Push;
import com.pricecompare.util.DaoUtil;

public class PushAction extends ActionSupport{
	public String push() throws Exception{
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");  
            String push = request.getParameter("push"); 
            System.out.println(push);
            JSONArray arr = new JSONArray();
            List<Push> pushProduct = (List<Push>)PushMgr.forAllPush();
            System.out.println(pushProduct.size());
            for(int i = 0;i <pushProduct.size();i++){
            	JSONObject obj =new JSONObject();
            	Product product = pushProduct.get(i).getProduct();
            	Info info = product.getInfo();
            	Description description = product.getDescription();
            	List<Picture> picture = product.getPicture();
            	List<Comment> comment = product.getComment();
                JSONObject pictureObj = new JSONObject();
                JSONArray pictures = new JSONArray();
                JSONObject commentObj = new JSONObject();
                JSONArray comments = new JSONArray();
            	obj.put("productId", product.getId());
            	obj.put("productName", info.getName());
            	obj.put("productWebsite", info.getWebsite());
            	obj.put("productWebAddress", info.getWebaddress());
            	obj.put("productPrice",info.getPrice());
            	obj.put("isNull", 1);
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
            	arr.add(i, obj);
            }
            if(!arr.isEmpty()){
            	PrintWriter out = response.getWriter();  
                out.println(JSON.toJSONString(arr,SerializerFeature.DisableCircularReferenceDetect));
                out.flush();
                out.close();
                System.out.println(arr.toJSONString().length());
                System.out.println(arr.toJSONString());
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
		DaoUtil.close();
		return SUCCESS;
	}

}
