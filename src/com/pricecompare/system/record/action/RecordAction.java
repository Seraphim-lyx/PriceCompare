package com.pricecompare.system.record.action;

import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.pricecompare.dao.author.User;
import com.pricecompare.dao.record.Record;
import com.pricecompare.system.record.core.RecordMgr;
import com.pricecompare.util.DaoUtil;

public class RecordAction extends ActionSupport{
	private Integer id;
	private String type;
	private String text;
	private String title;
	private String search;
	
	private String statusCode="200";
	private String message=null;
	private String callbackType="closeCurrent";
	private String navTabId="";
	
	private Integer pageNum=1;
	private Integer numPerPage=10;
	private Integer totalCount=0;//查询总数
	
	
	
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	
	public String recordIndex(){
		try{
			DaoUtil.begin();
			List<Record> list=(List<Record>)RecordMgr.recordList((pageNum-1)*numPerPage,numPerPage);
			totalCount=list.size();
			ActionContext.getContext().put("list", list);
			ActionContext.getContext().put("action", "recordIndex");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String addRecordPage(){
		try{
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String editRecordPage(){
		try{
			DaoUtil.begin();
			
			Record record=(Record)DaoUtil.get(Record.class,id);
			if((Integer)ActionContext.getContext().getSession().get("userid")!=record.getUser().getId()){
				message="不具有修改权限";
				statusCode="301";
			}
				ActionContext.getContext().put("record", record);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String addRecord(){
		try{
			DaoUtil.begin();
			Record record=new Record();
			User user=(User)DaoUtil.get(User.class,(Integer)ActionContext.getContext().getSession().get("userid"));
				record.setText(text);
				record.setEditTime(new Date());
				record.setTitle(title);
				record.setType(type);
				record.setUser(user);
			DaoUtil.save(record);
			DaoUtil.commit();
			message="添加成功";
			navTabId="recordIndex";
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String editRecord(){
		try{
			DaoUtil.begin();
			Record record=(Record)DaoUtil.get(Record.class,id);
			if((Integer)ActionContext.getContext().getSession().get("userid")!=record.getUser().getId()){
				message="不具有修改权限";
			}
			else{
				record.setText(text);
				record.setEditTime(new Date());
				record.setTitle(title);
				record.setType(type);
				DaoUtil.update(record);
				DaoUtil.commit();
				message="修改成功";
			}
			navTabId="recordIndex";
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String deleteRecord(){
		try{
			DaoUtil.begin();
			Record record=(Record)DaoUtil.get(Record.class,id);
			if((Integer)ActionContext.getContext().getSession().get("userid")!=record.getUser().getId()){
				message="不具有删除权限";
			}
			else{
			DaoUtil.delete(record);
			DaoUtil.commit();
			message="删除成功";
			}
			navTabId="recordIndex";
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String recordInfo(){
		try{
			DaoUtil.begin();
			Record r=(Record)DaoUtil.get(Record.class,id);
			ActionContext.getContext().put("record", r);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String recordSearch(){
		try{
			DaoUtil.begin();
			List<Record> list=(List<Record>)RecordMgr.recordSearch(search, (pageNum-1)*numPerPage,numPerPage);
			totalCount=RecordMgr.recordSearch(search, null, null).size();
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
