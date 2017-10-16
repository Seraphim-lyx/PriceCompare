package com.pricecompare.system.data.action;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.pricecompare.dao.product.MonthClick;
import com.pricecompare.dao.product.SeasonClick;
import com.pricecompare.dao.product.Type;
import com.pricecompare.dao.product.YearClick;
import com.pricecompare.system.data.core.MonthClickMgr;
import com.pricecompare.system.data.core.SeasonClickMgr;
import com.pricecompare.system.data.core.YearClickMgr;
import com.pricecompare.system.data.service.DataActionService;
import com.pricecompare.system.product.core.ProductTypeMgr;
import com.pricecompare.util.DaoUtil;

public class OrderAction extends ActionSupport{
	private Integer year=null;
	private Integer month=null;
	private Integer season=null;
	
	private String statusCode="200";
	private String message="fuck";
	private String callbackType="closeCurrent";
	private String navTabId="dataIndex"; 
	private String forwardUrl="toyearData";
	
	private Integer pageNum=1;
	private Integer numPerPage=10;
	private Integer totalCount=0;//查询总数
	private Calendar cal=Calendar.getInstance();
	
	

	
	

	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getSeason() {
		return season;
	}
	public void setSeason(Integer season) {
		this.season = season;
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
	
	public OrderAction(){
		if(year==null){
			year=cal.get(Calendar.YEAR);
		}
		if(month==null){
			month=cal.get(Calendar.MONTH)+1;
		}
		if(season==null){
			season=DataActionService.judgeSeason();
		}
		
	}
	
	public void validateTime(){
		if(year==-1){
			year=cal.get(Calendar.YEAR);
		}
		if(month==-1){
			month=cal.get(Calendar.MONTH)+1;
		}
		if(season==-1){
			season=DataActionService.judgeSeason();
		}
	}
	public String dataOrder(){
		try{
			DaoUtil.begin();
			JSONObject json=new JSONObject();
			JSONObject title=new JSONObject();
			title.put("text", "");
			json.put("title", title);
			List<Type> typelist=(List<Type>)ProductTypeMgr.forTypeList(null,null);
			
			
			ActionContext.getContext().put("type", "");
			ActionContext.getContext().put("typelist", typelist);
			ActionContext.getContext().put("json", json);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String yearOrder(){
		try{
			validateTime();
			JSONObject json=new JSONObject();
			JSONObject data=new JSONObject();
			JSONObject title=new JSONObject();
			JSONArray series=new JSONArray();
		
			DaoUtil.begin();
			List<YearClick> list=(List<YearClick>)YearClickMgr.forYearOrder(year, (pageNum-1)*numPerPage, numPerPage);
			totalCount=YearClickMgr.forYearOrderCount(year, null, null);
			String[] catelist=new String[list.size()];
			Integer[] count=new Integer[list.size()];
			Integer[] idList=new Integer[list.size()];
			Integer[] numlist=new Integer[list.size()];
			for(int i=0;i<catelist.length;i++){
				numlist[i]=i+1;
				catelist[i]=list.get(i).getProduct().getInfo().getName();
				count[i]=list.get(i).getCount();
				idList[i]=list.get(i).getProduct().getId();
			}
			data.put("data", count);
			data.put("type", "column");
			List<String> orderlist=Arrays.asList(catelist);
			title.put("text",year+"年年度排行");
			series.add(data);
			json.put("catelist", catelist);
			json.put("series", series);
			json.put("title", title);
			json.put("numlist", numlist);
			ActionContext.getContext().put("action", "yearOrder");
			ActionContext.getContext().put("type", "year");
			ActionContext.getContext().put("count", count);
			ActionContext.getContext().put("idList", idList);
			ActionContext.getContext().put("orderlist", orderlist);
			ActionContext.getContext().put("title", year+"年年度排行");
			ActionContext.getContext().put("json", json);
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
	public String seasonOrder(){
		try{
			validateTime();
			JSONObject json=new JSONObject();
			JSONObject data=new JSONObject();
			JSONObject title=new JSONObject();
			JSONArray series=new JSONArray();
			DaoUtil.begin();
			List<SeasonClick> list=(List<SeasonClick>)SeasonClickMgr.forSeasonOrder(year, season, (pageNum-1)*numPerPage, numPerPage);
			totalCount=SeasonClickMgr.forSeasonOrderCount(year, season, null, null);
			String[] catelist=new String[list.size()];
			Integer[] count=new Integer[list.size()];
			Integer[] idList=new Integer[list.size()];
			Integer[] numlist=new Integer[list.size()];
			for(int i=0;i<catelist.length;i++){
				numlist[i]=i+1;
				catelist[i]=list.get(i).getYearclick().getProduct().getInfo().getName();
				count[i]=list.get(i).getCount();
				idList[i]=list.get(i).getYearclick().getProduct().getId();
			}
			
			List<String> orderlist=Arrays.asList(catelist);
			data.put("data", count);
			data.put("type", "column");
//			System.out.println(catelist);
			series.add(data);
			json.put("catelist", catelist);
			json.put("series", series);
			title.put("text", year+"年第"+season+"季度排行");
			json.put("title", title);
			json.put("numlist", numlist);
			ActionContext.getContext().put("action", "seasonOrder");
			ActionContext.getContext().put("type", "season");
			ActionContext.getContext().put("idList", idList);
			ActionContext.getContext().put("count", count);
			ActionContext.getContext().put("orderlist", orderlist);
			ActionContext.getContext().put("title", year+"年第"+season+"季度排行");
			ActionContext.getContext().put("json", json);
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
	public String monthOrder(){
		try{
			validateTime();
			JSONObject json=new JSONObject();
			JSONObject data=new JSONObject();
			JSONObject title=new JSONObject();
			JSONArray series=new JSONArray();
			DaoUtil.begin();
			List<MonthClick> list=(List<MonthClick>)MonthClickMgr.forMonthOrder(year, month,(pageNum-1)*numPerPage, numPerPage);
			totalCount=MonthClickMgr.forMonthOrderCount(year, month, null, null);
			String[] catelist=new String[list.size()];
			Integer[] count=new Integer[list.size()];
			Integer[] idList=new Integer[list.size()];
			Integer[] numlist=new Integer[list.size()];
			for(int i=0;i<catelist.length;i++){
				numlist[i]=i+1;
				catelist[i]=list.get(i).getYearclick().getProduct().getInfo().getName();
				count[i]=list.get(i).getCount();
				idList[i]=list.get(i).getYearclick().getProduct().getId();
			}
			title.put("text",  year+"年"+month+"月排行");
			data.put("data", count);
			data.put("type", "column");
			List<String> orderlist=Arrays.asList(catelist);
			series.add(data);
			json.put("catelist", catelist);
			json.put("series", series);
			json.put("title", title);
			json.put("numlist", numlist);
			ActionContext.getContext().put("action", "monthOrder");
			ActionContext.getContext().put("type", "month");
			ActionContext.getContext().put("idList", idList);
			ActionContext.getContext().put("count", count);
			ActionContext.getContext().put("orderlist", orderlist);
			ActionContext.getContext().put("title", year+"年"+month+"月排行");
			ActionContext.getContext().put("json", json);
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
}
