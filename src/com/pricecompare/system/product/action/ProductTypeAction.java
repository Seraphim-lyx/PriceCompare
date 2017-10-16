package com.pricecompare.system.product.action;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.pricecompare.dao.product.Brand;
import com.pricecompare.dao.product.Product;
import com.pricecompare.dao.product.Type;
import com.pricecompare.system.product.core.ProductMgr;
import com.pricecompare.system.product.core.ProductTypeMgr;
import com.pricecompare.util.DaoUtil;

public class ProductTypeAction extends ActionSupport {
	private Integer pageNum=1;
	private Integer numPerPage=10;
	private Integer totalCount=0;//查询总数
	
	private String statusCode="200";
	private String message=null;
	private String callbackType="closeCurrent";
	private String navTabId;
	private String forwardUrl;
	
	private Integer id;
	private Integer brandid;
	private Integer[] ids;
	private String[] name;
	private Integer code;
	private InputStream is;
	private String from;
	
	
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getBrandid() {
		return brandid;
	}


	public void setBrandid(Integer brandid) {
		this.brandid = brandid;
	}


	


	public Integer[] getIds() {
		return ids;
	}


	public void setIds(Integer[] ids) {
		this.ids = ids;
	}


	public InputStream getIs() {
		return is;
	}


	public void setIs(InputStream is) {
		this.is = is;
	}

	

	


	public Integer getCode() {
		return code;
	}


	public void setCode(Integer code) {
		this.code = code;
	}


	public String getForwardUrl() {
		return forwardUrl;
	}


	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}


	public String getNavTabId() {
		return navTabId;
	}


	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}




	public String[] getName() {
		return name;
	}


	public void setName(String[] name) {
		this.name = name;
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


	


	public String productType(){
		try{
			DaoUtil.begin();
			List<Type> list=(List<Type>)ProductTypeMgr.forTypeList((pageNum-1)*numPerPage,numPerPage);
			totalCount=ProductTypeMgr.forTypeList(null,null).size();
			ActionContext.getContext().put("list", list);
			ActionContext.getContext().put("action", "productType");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String productBrand(){
		try{
			DaoUtil.begin();
			List<Brand> list=(List<Brand>)ProductTypeMgr.forBrandListByType(id);
			ActionContext.getContext().put("list", list);
			ActionContext.getContext().put("typeid", id);
			ActionContext.getContext().put("action", "productBrand?typeid="+id);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String typeSearch(){
		try{
			DaoUtil.begin();
			String []typename=name[0].split(" ");
			List<Type> list=new ArrayList<Type>();
			
			for(String t:typename){
				list.addAll((List<Type>)ProductTypeMgr.forTypeListByName(t, null,null));
				totalCount=totalCount+ProductTypeMgr.forTypeListByName(t, null,null).size();
			}
			list=list.subList((pageNum-1)*numPerPage, pageNum*numPerPage>list.size()?list.size():pageNum*numPerPage);
			ActionContext.getContext().put("list", list);
			ActionContext.getContext().put("name", name[0]);
			ActionContext.getContext().put("action", "typeSearch");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String brandSearch(){
		try{
			DaoUtil.begin();
			String []brandname=name[0].split(" ");
			List<Brand> list=new ArrayList<Brand>();
			
			for(String t:brandname){
				list.addAll((List<Brand>)ProductTypeMgr.forBrandListByNameType(id,t, null,null));
				totalCount=totalCount+ProductTypeMgr.forBrandListByNameType(id,t, null,null).size();
			}
//			System.out.println(list.size());
//			list=list.subList(10, 17);
//			System.out.println(id);
//			System.out.println(brandname.length);
//			list=list.subList((pageNum-1)*numPerPage, pageNum*numPerPage>list.size()?list.size():pageNum*numPerPage);
			ActionContext.getContext().put("list", list);
			ActionContext.getContext().put("typeid", id);
			ActionContext.getContext().put("name", name);
			ActionContext.getContext().put("action", "brandSearch");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String targetType(){
		try{
			String td="";
			for(Integer typeid:ids){
				td=td+"ids="+typeid+"&";
			}
			DaoUtil.begin();
			List<Type> list=(List<Type>)ProductTypeMgr.forTypeList((pageNum-1)*numPerPage,numPerPage);
			totalCount=ProductTypeMgr.forTypeList(null,null).size();
			ActionContext.getContext().put("list", list);
			ActionContext.getContext().put("td", td);
			ActionContext.getContext().put("action", "targetType?td"+td);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String targetBrand(){
		try{
			String bd="";
			for(Integer brandid:ids){
				bd=bd+"ids="+brandid+"&";
			}
			DaoUtil.begin();
			List<Brand> list=(List<Brand>)ProductTypeMgr.forBrandListByType(id);
			ActionContext.getContext().put("list", list);
			ActionContext.getContext().put("bd", bd);
			ActionContext.getContext().put("typeid", id);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String combineType(){
		try{
			DaoUtil.begin();
			Type targetType=(Type)DaoUtil.get(Type.class,id);
			for(Integer t:ids){
				if(t==id){
					continue;
				}
				else{
					Type type=(Type)DaoUtil.get(Type.class,t);
					List<Type> relateType=(List<Type>)ProductTypeMgr.getRelateType(type.getName());
					for(Type rt:relateType){
						rt.setRelatename(targetType.getName());
						DaoUtil.update(rt);
					}
					List<Brand> brandList=(List<Brand>)ProductTypeMgr.forBrandListByType(t);
					for(Brand brand:brandList){
						brand.setType(targetType);
						DaoUtil.update(brand);
					}
					type.setRelatename(targetType.getName());
					DaoUtil.update(type);
					
				}
			}
			targetType.setEditTime(new Date());
			DaoUtil.update(targetType);
			DaoUtil.commit();
			
			navTabId="productType";
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String combineBrand(){
		try{
			DaoUtil.begin();
			Brand targetBrand=(Brand)DaoUtil.get(Brand.class,brandid);
			for(Integer b:ids){
				if(b.equals(brandid)){
					continue;
				}
				else{
					Brand brand=(Brand)DaoUtil.get(Brand.class,b);
					List<Product> list=(List<Product>)ProductMgr.productForBrand(b,null,null);
					List<Brand> relateBrand=(List<Brand>)ProductTypeMgr.getRelateBrand(brand.getName());
					for(Brand rb:relateBrand){
						rb.setRelatename(targetBrand.getName());
						DaoUtil.update(rb);
					}
					for(Product p:list){
						p.setBrand(targetBrand);
						DaoUtil.update(p);
					}
					brand.setRelatename(targetBrand.getName());
					DaoUtil.update(brand);
					
				}
			}
			targetBrand.setEditTime(new Date());
			DaoUtil.update(targetBrand);
			DaoUtil.commit();
			
			navTabId="productBrand?id="+id;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String addPage(){
		String action="";
		if(code==1){
			action="addBrand?id="+id;
		}
		else{
			action="addType";
		}
		ActionContext.getContext().put("action", action);
		return SUCCESS;
	}
	public String updatePage(){
		DaoUtil.begin();
		String action="";
		Object obj=null;
		if(code==1){
			action="updateBrand?brandid="+brandid+"&id="+id;
			obj=(Brand)DaoUtil.get(Brand.class,brandid);
		}
		else{
			action="updateType?id="+id;
			obj=(Type)DaoUtil.get(Type.class,id);
		}
		ActionContext.getContext().put("action", action);
		ActionContext.getContext().put("obj", obj);
		DaoUtil.close();
		return SUCCESS;
	}
	
	public String addType(){
		try{
			DaoUtil.begin();
			for(int i=0;i<name.length;i++){
				if(name[i].equals("")){
					continue;
				}
				
				Type type=new Type();
				type.setName(name[i]);
				type.setEditTime(new Date());
				DaoUtil.save(type);
			
			}
			DaoUtil.commit();
			
			message="添加成功";
			navTabId="productType";

		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String addBrand(){
		
		try{
			DaoUtil.begin();
			Type type=(Type)DaoUtil.get(Type.class,id);
			for(int i=0;i<name.length;i++){

				if(name[i].equals("")){
					continue;
				}
				Brand brand=new Brand();
				brand.setName(name[i]);
				brand.setType(type);
				brand.setEditTime(new Date());
				DaoUtil.save(brand);
			}
			DaoUtil.commit();
			navTabId="productBrand?id="+id;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String updateType(){
		try{
			DaoUtil.begin();
			Type type=(Type)DaoUtil.get(Type.class,id);
				type.setName(name[0]);
				type.setEditTime(new Date());
			DaoUtil.update(type);
			DaoUtil.commit();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		navTabId="productType";
		return SUCCESS;
	}
	public String updateBrand(){
		try{
			DaoUtil.begin();
			Brand brand=(Brand)DaoUtil.get(Brand.class,brandid);
				brand.setName(name[0]);
				brand.setEditTime(new Date());
			DaoUtil.update(brand);
			DaoUtil.commit();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		navTabId="productBrand?id="+id;
		return SUCCESS;
	}
	public String delType(){
		try{
			DaoUtil.begin();
			Type type=(Type)DaoUtil.get(Type.class, id);
			List<Brand> list=(List<Brand>)ProductTypeMgr.forBrandListByType(id);
			for(Brand b:list){
				DaoUtil.delete(b);
			}
			DaoUtil.delete(type);
			DaoUtil.commit();
			navTabId="productType";
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String delBrand(){
		try{
			DaoUtil.begin();
			Brand brand=(Brand)DaoUtil.get(Brand.class,brandid);
			DaoUtil.delete(brand);
			DaoUtil.commit();
			navTabId="productBrand?id="+id;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String typeToBrand(){
		try{
//		String js;
//		JSONArray arr=new JSONArray();
//		JSONArray d=new JSONArray();
//		String str="尼玛";
//		d.add("1");
//		d.add(new String(str.getBytes("utf-8"),"iso-8859-1"));
//		arr.add(d);
//		js=arr.toJSONString();
//		is=new StringBufferInputStream(js);
		String bb="品牌";
		DaoUtil.begin();
		JSONArray json=new JSONArray();
		JSONArray i=new JSONArray();
			i.add(-1);
			i.add(new String(bb.getBytes("utf-8"),"iso-8859-1"));
		json.add(i);
		List<Brand> list=(List<Brand>)ProductTypeMgr.forBrandListByType(code);
		for(Brand b:list){
			JSONArray br=new JSONArray();
			br.add(b.getId());
			br.add(new String(b.getName().getBytes("utf-8"),"iso-8859-1"));
			json.add(br);
		}
		is=new StringBufferInputStream(json.toJSONString());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public Type getRelateTyped(){
		return null;
	}
}
