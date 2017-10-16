package com.pricecompare.android.action;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.pricecompare.android.core.CustomerMgr;
import com.pricecompare.dao.author.Customer;
import com.pricecompare.system.android.service.AndroidService;
import com.pricecompare.util.DaoUtil;

public class CustomerAction extends ActionSupport{
	public String login() throws Exception{
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");  
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            JSONArray arr = new JSONArray();
            JSONObject obj =new JSONObject();
            Customer customer = (Customer)CustomerMgr.customerForName(username);
            if(customer != null && username.equals(customer.getName()) && password.equals(customer.getPassword())){
            	request.getSession(true).setAttribute("userid", customer.getId());
            	System.out.println(customer.getId());
            	if(customer.getLocked()!=1){
            		obj.put("userid", request.getSession().getAttribute("userid"));
                	obj.put("user", customer);
            	}
            	else{
            		obj.put("userid", -1);
            	}
            	PrintWriter out = response.getWriter();  
                out.println(obj.toJSONString());
                out.flush();
                out.close();
            }
            else{
            	obj.put("userid",0);
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
	public String register() throws Exception{
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");  
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String phone = request.getParameter("phone");
            int sex = Integer.parseInt(request.getParameter("sex"));
            int edu = Integer.parseInt(request.getParameter("edu"));
            JSONArray arr = new JSONArray();
            JSONObject obj =new JSONObject();
            Customer customer1 = (Customer)CustomerMgr.customerForName(username);
            Customer customer2 = (Customer)CustomerMgr.customerForPhone(phone);
            if(customer1 == null && customer2 == null){
            	Customer customer = new Customer();
            	customer.setName(username);
            	customer.setPassword(password);
            	customer.setPhone(phone);
            	customer.setLocked(0);
            	customer.setRegitTime(new Date());
            	customer.setEditTime(new Date());
            	customer.setSex(sex);
            	customer.setEducation(edu);
            	DaoUtil.save(customer);
            	DaoUtil.commit();
            	Customer c = (Customer)CustomerMgr.customerForName(username);
            	request.getSession(true).setAttribute("userid", c.getId());
            	System.out.println(c.getId());
            	obj.put("userid", request.getSession().getAttribute("userid"));
            	PrintWriter out = response.getWriter();  
                out.println(obj.toJSONString());
                out.flush();
                out.close();
            }
            else{
            	obj.put("userid",0);
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
	public String isOpw() throws Exception{
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8"); 
			JSONArray arr = new JSONArray();
            JSONObject obj =new JSONObject();
			String password = request.getParameter("password");
			int userid = Integer.parseInt(""+request.getSession().getAttribute("userid"));
			Customer c = (Customer)CustomerMgr.customerForId(userid);
			if(password.equals(c.getPassword())){
				obj.put("userid",userid);
				PrintWriter out = response.getWriter();  
                out.println(obj.toJSONString());
                out.flush();
                out.close();
			}
			else{
				obj.put("userid",0);
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
	public String changePwd() throws Exception{
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8"); 
			JSONArray arr = new JSONArray();
            JSONObject obj =new JSONObject();
			String password = request.getParameter("newPassword");
			System.out.println(password);
			int userid = Integer.parseInt(""+request.getSession().getAttribute("userid"));
			System.out.println(userid);
			Customer c = (Customer)CustomerMgr.customerForId(userid);
			c.setPassword(password);
			DaoUtil.update(c);
			DaoUtil.commit();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		DaoUtil.close();
		return SUCCESS;
	}
	public String click(){
		try {
			DaoUtil.begin();
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			int productid = Integer.parseInt(request.getParameter("click"));
			AndroidService as = new AndroidService();
			as.clickCountUpdate(productid);
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e.toString());
		}
		DaoUtil.close();
		return SUCCESS;
	}
	public String logout(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();  
	        HttpServletRequest request = ServletActionContext.getRequest(); 
	        response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			request.getSession().removeAttribute("userid");
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return SUCCESS;
	}

}
