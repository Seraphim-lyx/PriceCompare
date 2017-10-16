package com.pricecompare.system.author.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.pricecompare.dao.author.Author;
import com.pricecompare.dao.author.Role;
import com.pricecompare.dao.author.User;
import com.pricecompare.dao.record.Record;
import com.pricecompare.system.author.core.RoleMgr;
import com.pricecompare.system.author.core.UserMgr;
import com.pricecompare.system.record.core.RecordMgr;
import com.pricecompare.util.DaoUtil;
import com.pricecompare.util.crypto.CryptoUtil;
import com.pricecompare.util.crypto.RSACrypto;

public class UserAction extends ActionSupport {
	
	private Map session;
	private String username;
	private String password;
	private Integer roleid;
	private Integer userid;
	private String result="error";
	
	private String statusCode="200";
	private String message=null;
	private String callbackType="closeCurrent";
	private String navTabId="";
	
	private Integer pageNum=1;
	private Integer numPerPage=10;
	private Integer totalCount=0;//查询总数
	
	
	
	
	public String getNavTabId() {
		return navTabId;
	}
	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
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
	public String getResult() {
		return result;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getRoleid() {
		return roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	public String index(){
		/*
		 * 登入界面
		 * 生成秘钥对
		 * 返回公钥
		 */
		ActionContext ctx=ActionContext.getContext();
		
		KeyPair kp=RSACrypto.getInstance().generateKey();
		session=ctx.getSession();
		session.put("key",kp);
		session.put("index", "index");
		
		
		RSAPublicKey pubky=(RSAPublicKey)kp.getPublic();
		
		String modul=pubky.getModulus().toString(16);
		String expo=pubky.getPublicExponent().toString(16);
		
		ctx.put("Modul", modul);
		ctx.put("Expo", expo);

		return SUCCESS;
	}
	public String error(){
		return SUCCESS;
	}
	public String logout(){
		/*
		 * 登出
		 * 注销session
		 */
		ActionContext ctx=ActionContext.getContext();
		session=ctx.getSession();
		session.remove("userid");
		return SUCCESS;
	}
	public String adminIndex(){
		return SUCCESS;
	}
	
	public String login() throws IOException{
//		 HttpServletResponse response = ServletActionContext.getResponse();  
//	        PrintWriter writer = response.getWriter(); 
		result="error";
		List<String> authorList=new ArrayList<String>();
		ActionContext ctx=ActionContext.getContext();
		session=ctx.getSession();
		KeyPair kp=(KeyPair)session.get("key");
		RSAPrivateKey priky=(RSAPrivateKey)kp.getPrivate();
		byte[] encrypted=RSACrypto.hexToBytes(password);
		byte[] decrypted=new RSACrypto().decrypt(encrypted,priky);
		
		DaoUtil.begin();
		User user=(User)UserMgr.getByName(username);
		if(user!=null){
			List<Author> list=user.getRole().getAuthor();
			for(int i=0;i<list.size();i++){
				authorList.add(list.get(i).getPath());
			}
			result="success";
			session.put("userid", user.getId());
			session.put("authorList", authorList);
			byte[] salt=user.getSalt();
			ByteBuffer bf=ByteBuffer.allocate(decrypted.length+salt.length);
			bf.put(decrypted).put(salt);
			byte[] digest=RSACrypto.getInstance().MD5Digest(bf.array());
			for(int i=0;i<digest.length;i++){
				if(digest[i]!=user.getPassword()[i]){
					result="error";
					session.remove("userid");
					session.remove("authorList");
					break;
//					writer.print("fuck");
//					break;
//					return ERROR;
				}
			}
			
		}
		if(result=="success"){
			user.setLoginTime(new Date());
			DaoUtil.commit();
			session.remove("index");
		}
		DaoUtil.close();
//		writer.println("you");
//		writer.flush();
//		writer.close();
	
		return SUCCESS;
	}
	public String register(){
		try{
		Random r=new Random();
		byte[] salt=ByteBuffer.allocate(8).putLong(r.nextLong()).array();
   		byte[] pass=password.getBytes("utf-8");
   		ByteBuffer buf=ByteBuffer.allocate(pass.length+salt.length);
   		buf.put(pass).put(salt);
   		byte digest[]=RSACrypto.getInstance().MD5Digest(buf.array());
   		
   		
		DaoUtil.begin();
		User user=new User();
			user.setName(username);
			user.setRegitTime(new Date());
			user.setEditTime(new Date());
			user.setLoginTime(new Date());
			user.setRole((Role)DaoUtil.get(Role.class, roleid));
			user.setSalt(salt);
			user.setPassword(digest);
		DaoUtil.save(user);
		DaoUtil.commit();
		DaoUtil.close();
		message="添加成功";
		navTabId="userList";
//			return SUCCESS;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String addUser(){
		DaoUtil.begin();
		List<Role> rolelist=(List<Role>)RoleMgr.getRoleList();
		ActionContext.getContext().put("roleList", rolelist);
		DaoUtil.close();
	
		return SUCCESS;
	}
	public String editUser() throws IOException{
		try{
			DaoUtil.begin();
			User user=(User)DaoUtil.get(User.class, userid);
			List<Role> list=(List<Role>)RoleMgr.getRoleList();
			ActionContext.getContext().put("user", user);
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
	public String doEditUser(){
			try{
			Random r=new Random();
			byte[] salt=ByteBuffer.allocate(8).putLong(r.nextLong()).array();
	   		byte[] pass=password.getBytes("utf-8");
	   		ByteBuffer buf=ByteBuffer.allocate(pass.length+salt.length);
	   		buf.put(pass).put(salt);
	   		byte digest[]=RSACrypto.getInstance().MD5Digest(buf.array());
	   		
	   		DaoUtil.begin();
	   		User user=(User)DaoUtil.get(User.class, userid);
	   			user.setSalt(salt);
	   			user.setPassword(digest);
	   			user.setRole((Role)DaoUtil.get(Role.class,roleid));
	   			user.setEditTime(new Date());
	   		DaoUtil.commit();
	   		
	   		message="修改成功！";
	   		navTabId="userList";
	   		System.out.println(message);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return SUCCESS;
		
	}
	public String deleteUser(){
		try{
		DaoUtil.begin();
		User user=(User)DaoUtil.get(User.class,userid);
		List<Record> list=(List<Record>)RecordMgr.recordForUser(userid);
		for(Record r:list){
			DaoUtil.delete(r);
		}
		DaoUtil.delete(user);
		DaoUtil.commit();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String userList() throws Exception{
			ActionContext context=ActionContext.getContext();
		try{
			DaoUtil.begin();
			List<User> list=(List<User>)UserMgr.forAllUser((pageNum-1)*numPerPage, numPerPage);
			List<Role> rolelist=(List<Role>)RoleMgr.getRoleList();
			context.put("list", list);
			context.put("rolelist", rolelist);
			totalCount=UserMgr.forAllUser().size();
			return SUCCESS;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String userSearch() throws IOException{
		try{
			DaoUtil.begin();
			List<User> list=(List<User>)UserMgr.userSearch(username, (pageNum-1)*numPerPage, numPerPage);
			List<Role> rolelist=(List<Role>)RoleMgr.getRoleList();
			ActionContext.getContext().put("rolelist", rolelist);
			ActionContext.getContext().put("list",list);
			totalCount=UserMgr.userSearch(username,null,null).size();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String roleSearch() throws IOException{
		try{
			DaoUtil.begin();
			System.out.println(roleid);
			List<User> list=(List<User>)UserMgr.roleSearch(roleid);
			List<Role> rolelist=(List<Role>)RoleMgr.getRoleList();
			ActionContext.getContext().put("rolelist", rolelist);
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
