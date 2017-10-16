package com.pricecompare.system.data.action;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.pricecompare.dao.product.Message;
import com.pricecompare.dao.product.MonthClick;
import com.pricecompare.dao.product.Product;
import com.pricecompare.dao.product.SeasonClick;
import com.pricecompare.dao.product.Type;
import com.pricecompare.dao.product.YearClick;
import com.pricecompare.system.author.core.UserMgr;
import com.pricecompare.system.data.core.YearClickMgr;
import com.pricecompare.system.data.service.DataActionService;
import com.pricecompare.system.product.core.ProductMgr;
import com.pricecompare.system.product.core.ProductTypeMgr;
import com.pricecompare.util.DaoUtil;


public class DataAction extends ActionSupport{
	public static final String monthList[]={"Jan","Feb","Mar","Apl","May","Jun","July","Aug","Sep","Oct","Dec","Nov"};
	public static final String seasonList[]={"第一季度","第二季度","第三季度","第四季度"};
	
	private Integer order=-1;
	private String from;
	private String website;
	private Integer[] productid=null;
	private String productname;
	private String productNames[];
	private Integer year;
	private Integer typeid;
	private Integer brandid;
	private Float minprice=new Float(0);
	private Float maxprice=new Float(0);
	private Integer lock;
	private String action;
	private String text;

	
	private String statusCode="200";
	private String message="";
	private String callbackType="closeCurrent";
	private String navTabId="dataIndex"; 
	private String forwardUrl="toyearData";
	
	private Integer pageNum=1;
	private Integer numPerPage=10;
	private Integer totalCount=0;//查询总数
	
	private static List<List> yclist;
	private List<List> calculate=new ArrayList<List>();
	private Calendar cal=Calendar.getInstance();
//	public ActionContext ctx;
	public DataAction(){
//		ctx=ActionContext.getContext();
		if(year==null){
			
			year=cal.get(Calendar.YEAR);
		}
	}
	
	
	
	
	public Integer getOrder() {
		return order;
	}




	public void setOrder(Integer order) {
		this.order = order;
	}




	public String getWebsite() {
		return website;
	}




	public void setWebsite(String website) {
		this.website = website;
	}




	public String getText() {
		return text;
	}




	public void setText(String text) {
		this.text = text;
	}




	public String getAction() {
		return action;
	}




	public void setAction(String action) {
		this.action = action;
	}




	public Integer getLock() {
		return lock;
	}




	public void setLock(Integer lock) {
		this.lock = lock;
	}




	public String getFrom() {
		return from;
	}




	public void setFrom(String from) {
		this.from = from;
	}




	public Integer getBrandid() {
		return brandid;
	}




	public void setBrandid(Integer brandid) {
		this.brandid = brandid;
	}




	public Float getMinprice() {
		return minprice;
	}
	public void setMinprice(Float minprice) {
		this.minprice = minprice;
	}
	public Float getMaxprice() {
		return maxprice;
	}
	public void setMaxprice(Float maxprice) {
		this.maxprice = maxprice;
	}
	public Integer getTypeid() {
		return typeid;
	}
	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
	public String[] getProductNames() {
		return productNames;
	}
	public void setProductNames(String[] productNames) {
		this.productNames = productNames;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
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
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getForwardUrl() {
		return forwardUrl;
	}
	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public Integer[] getProductid() {
		return productid;
	}
	public void setProductid(Integer[] productid) {
		this.productid = productid;
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
	public String dataIndex(){
		try{
			DaoUtil.begin();
			List<Product> list=(List<Product>)ProductMgr.productForList((pageNum-1)*numPerPage, numPerPage);
			totalCount=ProductMgr.productForListCount();
			String action="dataIndex";
			ActionContext.getContext().put("list", list);
			ActionContext.getContext().put("action", action);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String dataMessage(){
		try{
			DaoUtil.begin();
			Product p=(Product)DaoUtil.get(Product.class,productid[0]);
			ActionContext.getContext().put("product", p);
			ActionContext.getContext().put("action", action);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String addMessage(){
		try{
			
			DaoUtil.begin();
			Product p=(Product)DaoUtil.get(Product.class,productid[0]);
			if(p.getMessage()==null){
				Message msg=new Message();
					msg.setText(text);
				p.setMessage(msg);
				DaoUtil.save(msg);
				DaoUtil.update(p);
			}
			else{
				Message msg=p.getMessage();
					msg.setText(text);
				DaoUtil.update(msg);
			}
			DaoUtil.commit();
			navTabId="dataIndex";
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String dataPointedList(){
		try{
			DaoUtil.begin();
			List<Product> list=(List<Product>)ProductMgr.productForPointed((pageNum-1)*numPerPage, numPerPage);
			totalCount=ProductMgr.productForPointed(null,null).size();
			ActionContext.getContext().put("action", "dataPointedList");
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
	public String productSearch(){
		try{
			DaoUtil.begin();
			System.out.println(productname);
			List<Product> list=(List<Product>)ProductMgr.productForName(productname, (pageNum-1)*numPerPage, numPerPage);
			ActionContext.getContext().put("list", list);
			totalCount=ProductMgr.productForName(productname,null,null).size();
			ActionContext.getContext().put("action", "dataSearch");
			ActionContext.getContext().put("productname", productname);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String dataTest(){
		System.out.println("yes");
		navTabId="productIndex";
		return SUCCESS;
	}
	public String yearData(){
		try{
			
			yclist=new ArrayList<List>();
			
			String path="";
			JSONObject json=new JSONObject();
			JSONObject title=new JSONObject();
			JSONArray series=new JSONArray();
			JSONObject catelist=new JSONObject();
			
			
			List<Integer> yearlist=new ArrayList<Integer>();
			Integer listsize=0;
			title.put("text", "年度趋势统计");
			json.put("series", series);
			json.put("title", title);
			if(productid!=null){
			for(Integer product:productid){
				List<YearClick> yearclick=new ArrayList<YearClick>();
				listsize=DataActionService.forYearCount(product,json,series,listsize,yearclick,yclist,calculate);
				path=path+"productid="+product+"&";
				System.out.println("ceshi"+yclist.size());
			}
			DaoUtil.begin();
			List<YearClick> list=(List<YearClick>)YearClickMgr.forYearList(productid[0]);
			
			for(YearClick yc:list){
				yearlist.add(yc.getYear());
			}
			DaoUtil.close();
			}
			
			ActionContext.getContext().put("c", calculate);
			ActionContext.getContext().put("yclist",yclist);
			ActionContext.getContext().put("yearlist", yearlist);
			ActionContext.getContext().put("json",json);
			ActionContext.getContext().put("path", path);
//			System.out.println(yclist.size());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
		}
		return SUCCESS;
	}
	public String monthData(){
		try{
			yclist=new ArrayList<List>();
			String path="";
			JSONObject title=new JSONObject();
			JSONObject json=new JSONObject();
			JSONArray series=new JSONArray();
			List<Integer> yearlist=new ArrayList<Integer>();
			title.put("text", "月度趋势统计");
			json.put("series", series);
			json.put("title", title);
			json.put("catelist", monthList);
			if(productid!=null){
				for(Integer product:productid){
					List<MonthClick> monthclick=new ArrayList<MonthClick>();
					DataActionService.forMonthCount(product,json,series,year,monthclick,yclist,calculate);
					path=path+"productid="+product+"&";
				}
		
			DaoUtil.begin();
				List<YearClick> list=(List<YearClick>)YearClickMgr.forYearList(productid[0]);
			
				for(YearClick yc:list){
					yearlist.add(yc.getYear());
				}
			DaoUtil.close();
			}
			ActionContext.getContext().put("c", calculate);
			ActionContext.getContext().put("month",cal.get(Calendar.MONTH)+1 );
			ActionContext.getContext().put("yclist",yclist);
			ActionContext.getContext().put("yearlist", yearlist);
			ActionContext.getContext().put("json", json);
			ActionContext.getContext().put("path", path);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
		}
		return SUCCESS;
	}
	public String seasonData(){
		try{
			yclist=new ArrayList<List>();
			String path="";
			JSONObject json=new JSONObject();
			JSONObject title=new JSONObject();
			JSONArray series=new JSONArray();
			List<Integer> yearlist=new ArrayList<Integer>();
			title.put("text", "季度趋势统计");
			
			json.put("title", title);
			json.put("series", series);
			json.put("catelist", seasonList);
			
			if(productid!=null){
			for(Integer product:productid){
				
				List<SeasonClick> seasonclick=new ArrayList<SeasonClick>();
				DataActionService.forSeasonCount(product,json,series,year,seasonclick,yclist,calculate);
				path=path+"productid="+product+"&";
			}
			DaoUtil.begin();
			List<YearClick> list=(List<YearClick>)YearClickMgr.forYearList(productid[0]);
			
			for(YearClick yc:list){
				yearlist.add(yc.getYear());
			}
			DaoUtil.close();
			}
			ActionContext.getContext().put("c", calculate);
			ActionContext.getContext().put("season", DataActionService.judgeSeason());
			ActionContext.getContext().put("yclist",yclist);
			ActionContext.getContext().put("yearlist", yearlist);
			ActionContext.getContext().put("json", json);
			ActionContext.getContext().put("path", path);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
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
	public String nameDataSearch(){
		try{
			
			DaoUtil.begin();
			List<Type> typelist=(List<Type>)ProductTypeMgr.forTypeList(null,null);
			ActionContext.getContext().put("typelist", typelist);
			List<Product> list=new ArrayList<Product>();
			for(String product:productNames){
				if(product==null||product==""){
					continue;
				}
				List<Product> plist=(List<Product>)ProductMgr.productForName(product,null,null);
				list.addAll(plist);
			}
			totalCount=list.size();
			Integer fromIndex=(pageNum-1)*numPerPage;
			Integer toIndex= (pageNum-1)*numPerPage+numPerPage;
			if(toIndex>totalCount){
				toIndex=totalCount;
			}
			list=list.subList(fromIndex, toIndex);
			ActionContext.getContext().put("list", list);
			ActionContext.getContext().put("productNames", productNames);
			ActionContext.getContext().put("action", action);	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String typeDataSearch(){
		try{
			List<Product> list;
			DaoUtil.begin();
			List<Type> typelist=(List<Type>)ProductTypeMgr.forTypeList(null,null);
			ActionContext.getContext().put("typelist", typelist);
			if(brandid==-1){
				list=(List<Product>)ProductMgr.productForType(typeid, (pageNum-1)*numPerPage, numPerPage);
				totalCount=ProductMgr.productForType(typeid, null,null).size();
			}
			else{
				list=(List<Product>)ProductMgr.productForBrand(brandid, (pageNum-1)*numPerPage, numPerPage);
				totalCount=ProductMgr.productForBrand(brandid, null,null).size();
			}
//			System.out.println("list");
			ActionContext.getContext().put("action", action);
			ActionContext.getContext().put("list", list);
			ActionContext.getContext().put("typeid", typeid);
			ActionContext.getContext().put("brandid", brandid);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String priceDataSearch(){
		try{
			initialPrice();
			DaoUtil.begin();
			List<Type> typelist=(List<Type>)ProductTypeMgr.forTypeList(null,null);
			ActionContext.getContext().put("typelist", typelist);
//			System.out.println(minprice.isNaN());
			List<Product> list=(List<Product>)ProductMgr.productForPriceRange(minprice, maxprice, (pageNum-1)*numPerPage, numPerPage);
			totalCount=ProductMgr.productForPriceRange(minprice, maxprice,null,null).size();
			ActionContext.getContext().put("list", list);
			ActionContext.getContext().put("minprice", minprice);
			ActionContext.getContext().put("maxprice", maxprice);
			ActionContext.getContext().put("action", action);	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String mixDataSearch(){
		try{
			initialPrice();
			DaoUtil.begin();
			List<Type> typelist=(List<Type>)ProductTypeMgr.forTypeList(null,null);
			ActionContext.getContext().put("typelist", typelist);
			List<Product> list=new ArrayList<Product>();
		
			if(typeid==-1){
				list=(List<Product>)ProductMgr.productForMix(website, productname, minprice, maxprice,(pageNum-1)*numPerPage, numPerPage);
				totalCount=ProductMgr.productForMix(website, productname, minprice, maxprice,null, null).size();
			}
			else if(brandid==-1){
				list=(List<Product>)ProductMgr.productForMixType(website, productname, minprice, maxprice, typeid,(pageNum-1)*numPerPage, numPerPage);
				totalCount=ProductMgr.productForMixType(website, productname, minprice, maxprice, typeid,null,null).size();
			}
			else{
				list=(List<Product>)ProductMgr.productForMixBrand(website, productname, minprice, maxprice, brandid,(pageNum-1)*numPerPage, numPerPage);
				totalCount=ProductMgr.productForMixBrand(website, productname, minprice, maxprice, brandid,null, null).size();
			}
			ActionContext.getContext().put("list", list);
			ActionContext.getContext().put("website", website);
			ActionContext.getContext().put("productname", productname);
			ActionContext.getContext().put("minprice", minprice);
			ActionContext.getContext().put("maxprice", maxprice);
			ActionContext.getContext().put("action", action);
			ActionContext.getContext().put("typeid", typeid);
			ActionContext.getContext().put("brandid", brandid);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String productPointed(){
		try{
			DaoUtil.begin();
			Integer pid=productid[0];
			Product p=(Product)DaoUtil.get(Product.class,pid);
				p.setPointed(lock);
			DaoUtil.update(p);
			DaoUtil.commit();
			System.out.println(action);
			callbackType="forward";
			forwardUrl=action;
				
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public void initialPrice(){
		if(minprice==null){
			minprice=new Float(0);
		}
		if(maxprice==null){
			maxprice=new Float(99999);
		}
	}
}
