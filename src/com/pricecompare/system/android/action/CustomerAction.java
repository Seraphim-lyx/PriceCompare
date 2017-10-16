package com.pricecompare.system.android.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.pricecompare.dao.author.Customer;
import com.pricecompare.dao.author.CustomerProduct;
import com.pricecompare.dao.author.Keyword;
import com.pricecompare.dao.product.Product;
import com.pricecompare.system.android.core.CustomerMgr;
import com.pricecompare.util.DaoUtil;

public class CustomerAction extends ActionSupport{
	private String name;
	private Integer id;
	private String phone;
	private String password;
	private Integer sex;
	private Integer edu;
	private Integer lock;
	private Integer productid;
	private Integer point;
	
	private String statusCode="200";
	private String message=null;
	private String callbackType="closeCurrent";
	private String navTabId="";
	
	private Integer pageNum=1;
	private Integer numPerPage=10;
	private Integer totalCount=0;//查询总数
	
	
	
	public Integer getSex() {
		return sex;
	}


	public void setSex(Integer sex) {
		this.sex = sex;
	}


	public Integer getEdu() {
		return edu;
	}


	public void setEdu(Integer edu) {
		this.edu = edu;
	}


	public Integer getPoint() {
		return point;
	}


	public void setPoint(Integer point) {
		this.point = point;
	}


	public Integer getProductid() {
		return productid;
	}


	public void setProductid(Integer productid) {
		this.productid = productid;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public Integer getLock() {
		return lock;
	}


	public void setLock(Integer lock) {
		this.lock = lock;
	}


	public String getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getCallbackType() {
		return callbackType;
	}


	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}


	public String getNavTabId() {
		return navTabId;
	}


	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}


	public Integer getPageNum() {
		return pageNum;
	}


	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}


	public Integer getNumPerPage() {
		return numPerPage;
	}


	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}


	public Integer getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}


	public String customerIndex(){
		try{
			DaoUtil.begin();
			List<Customer> list=(List<Customer>)CustomerMgr.forCustomerList((pageNum-1)*numPerPage, numPerPage);
			ActionContext.getContext().put("list", list);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String customerSearch(){
		try{
			DaoUtil.begin();
			List<Customer> list=(List<Customer>)CustomerMgr.forCustomerNameList(name, (pageNum-1)*numPerPage, numPerPage);
			ActionContext.getContext().put("list", list);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String customerUpdatePage(){
		try{
			DaoUtil.begin();
			Customer c=(Customer)DaoUtil.get(Customer.class, id);
			ActionContext.getContext().put("customer",c);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String customerUpdate(){
		try{
			DaoUtil.begin();
			Customer c=(Customer)DaoUtil.get(Customer.class,id);
				c.setEditTime(new Date());
				c.setName(name);
				c.setPassword(password);
				c.setPhone(phone);
				c.setLocked(lock);
			DaoUtil.update(c);
			DaoUtil.commit();
			navTabId="customerIndex";
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		navTabId="customerIndex";
		return SUCCESS;
	}
	public String customerAddPage(){
		try{
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
		}
		return SUCCESS;
	}
	public String customerSave(){
		try{
			DaoUtil.begin();
			Customer c=new Customer();
				c.setName(name);
				c.setPassword(password);
				c.setPhone(phone);
				c.setRegitTime(new Date());
				c.setEditTime(new Date());
				c.setLocked(0);
				c.setEducation(edu);
				c.setSex(sex);
			DaoUtil.save(c);
			DaoUtil.commit();
			navTabId="customerIndex";
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String customerDelete(){
		try{
			DaoUtil.begin();
			Customer c=(Customer)DaoUtil.get(Customer.class,id);
			DaoUtil.delete(c);
			DaoUtil.commit();
			navTabId="customerIndex";
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
		}
		return SUCCESS;
	}
	public String customerLock(){
		try{
			DaoUtil.begin();
			Customer c=(Customer)DaoUtil.get(Customer.class,id);
			c.setLocked(lock);
			DaoUtil.update(c);
			DaoUtil.commit();
			navTabId="customerIndex";
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String customerKeyword(){
		try{
			DaoUtil.begin();
			Customer c=(Customer)DaoUtil.get(Customer.class,id);
			List<Keyword> list=c.getKeyword();
			List<Long> countlist=new ArrayList<Long>();
			for(int i=0;i<list.size();i++){
				countlist.add(CustomerMgr.forKeywordCount(list.get(i).getWord()));
			}
			ActionContext.getContext().put("list", list);
			ActionContext.getContext().put("countlist", countlist);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String customerProduct(){
		try{
			DaoUtil.begin();
			Customer c=(Customer)DaoUtil.get(Customer.class,id);
			List<CustomerProduct> list=c.getCustomerproduct();
//			System.out.println(list.size());
			List<Long> countlist=new ArrayList<Long>();
			for(CustomerProduct cp:list){
				countlist.add(CustomerMgr.forProductCount(cp.getProduct().getId()));
			}
			ActionContext.getContext().put("list", list);
			ActionContext.getContext().put("countlist", countlist);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String customerAnalysis(){
		try{
			DaoUtil.begin();
			JSONObject sexchart=new JSONObject();
			JSONArray sexarray=new JSONArray();
			JSONObject sexobj=new JSONObject();
				sexobj.put("type", "pie");
				sexobj.put("name", "num");
			JSONArray sexdata=new JSONArray();
			JSONArray maledata=new JSONArray();
				Integer male=(Integer)CustomerMgr.forCustomerProductSex(productid, 1);
				maledata.add("男性");
				maledata.add(male);
			JSONArray femaledata=new JSONArray();
				Integer female=(Integer)CustomerMgr.forCustomerProductSex(productid, 2);
				femaledata.add("女性");
				femaledata.add(female);
			sexdata.add(maledata);
			sexdata.add(femaledata);
			sexobj.put("data", sexdata);
			sexarray.add(sexobj);
			sexchart.put("series", sexarray);
			
			ActionContext.getContext().put("sexchart",sexchart);
			ActionContext.getContext().put("maleNum", male);
			ActionContext.getContext().put("femaleNum", female);
			
			JSONObject educhart=new JSONObject();
			JSONArray eduarray=new JSONArray();
			JSONObject eduobj=new JSONObject();
				eduobj.put("type", "pie");
				eduobj.put("name", "num");
			JSONArray edudata=new JSONArray();
			JSONArray pridata=new JSONArray();
				Integer pri=(Integer)CustomerMgr.forCustomerProductEducation(productid, 1);
				pridata.add("小学");
				pridata.add(pri);
			JSONArray secdata=new JSONArray();
				Integer sec=(Integer)CustomerMgr.forCustomerProductEducation(productid, 2);
				secdata.add("初中");
				secdata.add(sec);
			JSONArray highdata=new JSONArray();
			 	Integer high=(Integer)CustomerMgr.forCustomerProductEducation(productid,3);
			 	highdata.add("高中");
			 	highdata.add(high);
			JSONArray unidata=new JSONArray();
				Integer uni=(Integer)CustomerMgr.forCustomerProductEducation(productid, 4);
				unidata.add("大学或以上");
				unidata.add(uni);
			edudata.add(pridata);
			edudata.add(secdata);
			edudata.add(highdata);
			edudata.add(unidata);
			eduobj.put("data", edudata);
			eduarray.add(eduobj);
			educhart.put("series", eduarray);
			ActionContext.getContext().put("educhart",educhart);
			ActionContext.getContext().put("priNum", pri);
			ActionContext.getContext().put("secNum", sec);
			ActionContext.getContext().put("highNum", high);
			ActionContext.getContext().put("uniNum", uni);
			
			Product p=(Product)DaoUtil.get(Product.class,productid);
			ActionContext.getContext().put("product", p);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String customerEduAnalysis(){
		try{
			DaoUtil.begin();
			JSONObject educhart=new JSONObject();
			JSONObject title=new JSONObject();
			if(point==1){
				title.put("text", "男性-学历商品关注统计");
			}
			else{
				title.put("text", "女性-学历商品关注统计");
			}
			JSONArray eduarray=new JSONArray();
			JSONObject eduobj=new JSONObject();
				eduobj.put("type", "pie");
				eduobj.put("name", "num");
			JSONArray edudata=new JSONArray();
			JSONArray pridata=new JSONArray();
				Integer pri=(Integer)CustomerMgr.forCustomerProductSexForEdu(productid, point, 1);
				pridata.add("小学");
				pridata.add(pri);
			JSONArray secdata=new JSONArray();
				Integer sec=(Integer)CustomerMgr.forCustomerProductSexForEdu(productid, point, 2);
				secdata.add("初中");
				secdata.add(sec);
			JSONArray highdata=new JSONArray();
			 	Integer high=(Integer)CustomerMgr.forCustomerProductSexForEdu(productid, point, 3);
			 	highdata.add("高中");
			 	highdata.add(high);
			JSONArray unidata=new JSONArray();
				Integer uni=(Integer)CustomerMgr.forCustomerProductSexForEdu(productid, point, 4);
				unidata.add("大学或以上");
				unidata.add(uni);
			edudata.add(pridata);
			edudata.add(secdata);
			edudata.add(highdata);
			edudata.add(unidata);
			eduobj.put("data", edudata);
			eduarray.add(eduobj);
			educhart.put("series", eduarray);
			educhart.put("title", title);
			Product p=(Product)DaoUtil.get(Product.class,productid);
			ActionContext.getContext().put("product", p);
			ActionContext.getContext().put("educhart",educhart);
			ActionContext.getContext().put("priNum", pri);
			ActionContext.getContext().put("secNum", sec);
			ActionContext.getContext().put("highNum", high);
			ActionContext.getContext().put("uniNum", uni);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String customerSexAnalysis(){
		try{
			DaoUtil.begin();
			JSONObject sexchart=new JSONObject();
			JSONObject title=new JSONObject();
			if(point==1){
				title.put("text", "小学学历-性别商品关注统计");
			}
			else if(point==2){
				title.put("text", "初中学历-性别商品关注统计");
			}
			else if(point==3){
				title.put("text","高中学历-性别商品关注统计");
			}
			else{
				title.put("text","大学或以上学历-性别商品关注统计");
			}
			JSONArray sexarray=new JSONArray();
			JSONObject sexobj=new JSONObject();
				sexobj.put("type", "pie");
				sexobj.put("name", "num");
			JSONArray sexdata=new JSONArray();
			JSONArray maledata=new JSONArray();
				Integer male=(Integer)CustomerMgr.forCustomerProductEducationForSex(productid, point,1);
				maledata.add("男性");
				maledata.add(male);
			JSONArray femaledata=new JSONArray();
				Integer female=(Integer)CustomerMgr.forCustomerProductEducationForSex(productid, point,2);
				femaledata.add("女性");
				femaledata.add(female);
			sexdata.add(maledata);
			sexdata.add(femaledata);
			sexobj.put("data", sexdata);
			sexarray.add(sexobj);
			sexchart.put("series", sexarray);
			sexchart.put("title", title);
			Product p=(Product)DaoUtil.get(Product.class,productid);
			ActionContext.getContext().put("product", p);
			ActionContext.getContext().put("sexchart",sexchart);
			ActionContext.getContext().put("maleNum", male);
			ActionContext.getContext().put("femaleNum", female);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	
}
