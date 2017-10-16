package com.pricecompare.system.android.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.pricecompare.dao.product.MonthClick;
import com.pricecompare.dao.product.Product;
import com.pricecompare.dao.product.Push;
import com.pricecompare.dao.product.Type;
import com.pricecompare.system.android.core.AndroidMgr;
import com.pricecompare.system.data.core.MonthClickMgr;
import com.pricecompare.system.product.core.ProductMgr;
import com.pricecompare.system.product.core.ProductTypeMgr;
import com.pricecompare.util.DaoUtil;

public class AndroidAction extends ActionSupport{
	private Integer id;
	private Integer[] productsid;
	private Integer productid;
	private Integer pageNum=1;
	private Integer numPerPage=10;
	private Integer totalCount=0;//查询总数
	private Integer order=-1;
	private String productname;
	
	private String statusCode="200";
	private String message=null;
	private String callbackType="closeCurrent";
	private String navTabId="";
	private String forwardUrl="";
	
	private Calendar cal=Calendar.getInstance();
	
	
	
	
	
	
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public Integer[] getProductsid() {
		return productsid;
	}
	public void setProductsid(Integer[] productsid) {
		this.productsid = productsid;
	}
	public Integer getProductid() {
		return productid;
	}
	public void setProductid(Integer productid) {
		this.productid = productid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getForwardUrl() {
		return forwardUrl;
	}
	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}
	
	
	public String androidIndex(){
		return SUCCESS;
	}
	public String androidPush(){
		try{
			DaoUtil.begin();
			List<Push> list=(List<Push>)AndroidMgr.forPushList((pageNum-1)*numPerPage, numPerPage);
			totalCount=AndroidMgr.forPushList(null,null).size();
			ActionContext.getContext().put("list", list);
			ActionContext.getContext().put("action", "androidPush");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	
	public String removePush(){
		try{
			DaoUtil.begin();
			Push p=(Push)DaoUtil.get(Push.class,id);
			DaoUtil.delete(p);
			DaoUtil.commit();
			
			message="移除成功";
			navTabId="androidPush";
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String androidPushOrder(){
		try{
			DaoUtil.begin();

			List<MonthClick> list=(List<MonthClick>)MonthClickMgr.forMonthOrder(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, (pageNum-1)*numPerPage, numPerPage);
			ActionContext.getContext().put("list",list);
			totalCount=MonthClickMgr.forMonthOrder(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1,null,null).size();
			ActionContext.getContext().put("action", "androidPushOrder");
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String androidClickPriceOrder(){
		try{
			DaoUtil.begin();
			List<MonthClick> list=(List<MonthClick>)MonthClickMgr.forMonthPriceOrder(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, (pageNum-1)*numPerPage, numPerPage);
			if(order==0){
				Collections.reverse(list);
			}
			totalCount=MonthClickMgr.forMonthPriceOrder(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, null,null).size();
			ActionContext.getContext().put("list", list);
			ActionContext.getContext().put("action", "androidClickPriceOrder?order="+order);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String androidClickCountOrder(){
		try{
			List<MonthClick> list=new ArrayList<MonthClick>();
			DaoUtil.begin();
			
			if(order==0){
				list=(List<MonthClick>)MonthClickMgr.forMonthOrderAsc(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, (pageNum-1)*numPerPage, numPerPage);
			}
			else{
				list=(List<MonthClick>)MonthClickMgr.forMonthOrder(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, (pageNum-1)*numPerPage, numPerPage);
			}
			totalCount=MonthClickMgr.forMonthOrder(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, null,null).size();
			ActionContext.getContext().put("list", list);
			ActionContext.getContext().put("action", "androidClickCountOrder?order="+order);
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String androidPushPointed(){
		try{
			DaoUtil.begin();
			List<Product> list=(List<Product>)ProductMgr.productForPointed((pageNum-1)*numPerPage, numPerPage);
			totalCount=ProductMgr.productForPointed(null,null).size();
			ActionContext.getContext().put("list", list);
			ActionContext.getContext().put("action", "androidPushOrder");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String androidPricePointed(){
		try{
			List<Product> list=new ArrayList<Product>();
			DaoUtil.begin();
			
			totalCount=ProductMgr.productForPricePointed(null,null).size();
			if(order==0){
				list=(List<Product>)ProductMgr.productForPricePointedAsc((pageNum-1)*numPerPage, numPerPage);
			}
			else{
				list=(List<Product>)ProductMgr.productForPricePointed((pageNum-1)*numPerPage, numPerPage);
			}
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
	public String androidProductSearch(){
		try{
			List<Product> list=new ArrayList<Product>();
			
			DaoUtil.begin();
			if(order==0){
				list=(List<Product>)ProductMgr.productForNameByPriceAsc(productname,(pageNum-1)*numPerPage, numPerPage);
				System.out.println(productname);
			}
			else if(order==1){
				list=(List<Product>)ProductMgr.productForNameByPriceDesc(productname,(pageNum-1)*numPerPage, numPerPage);
			}
			else{
			list=(List<Product>)ProductMgr.productForName(productname,(pageNum-1)*numPerPage, numPerPage);
			}
			totalCount=ProductMgr.productForName(productname, null, null).size();
			
			ActionContext.getContext().put("token", "android");
			ActionContext.getContext().put("androidOrder", "androidProductSearch");
			ActionContext.getContext().put("list", list);
			ActionContext.getContext().put("action","androidProductSearch");
			ActionContext.getContext().put("productname", productname);
			ActionContext.getContext().put("order", order);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String androidPushChoose(){
		try{
			System.out.println(productsid.length);
			DaoUtil.begin();
			List<Push> pushList=(List<Push>)AndroidMgr.forPushList(null, null);
			
			for(Integer p:productsid){
				if(pushList.size()>0){
					for(int i=0;i<pushList.size();i++){
						if(pushList.get(i).getProduct().getId().equals(p)){
							break;
						}
						else if(i==pushList.size()-1){
							Product pro=(Product)DaoUtil.get(Product.class,p);
							Push push=new Push();
							push.setProduct(pro);
							DaoUtil.save(push);
						
						}
					}
				}
				else{
					Product pro=(Product)DaoUtil.get(Product.class,p);
					Push push=new Push();
					push.setProduct(pro);
					DaoUtil.save(push);
				}
				
				
			}
			DaoUtil.commit();
			navTabId="androidPush";
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		callbackType="forward";
		forwardUrl="androidPush";
		return SUCCESS;
	}
	public String AndroidProductData(){
		try{
			DaoUtil.begin();
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
	public String searchPage(){
		DaoUtil.begin();
			List<Type> typelist=(List<Type>)ProductTypeMgr.forTypeList(null,null);
		DaoUtil.close();
		ActionContext.getContext().put("list", typelist);
		return SUCCESS;
	}
	public String androidProductMessage(){
		try{
			DaoUtil.begin();
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
	
}
