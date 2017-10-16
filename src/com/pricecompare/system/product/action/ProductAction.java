package com.pricecompare.system.product.action;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.pricecompare.dao.author.CustomerProduct;
import com.pricecompare.dao.product.Brand;
import com.pricecompare.dao.product.Description;
import com.pricecompare.dao.product.Info;
import com.pricecompare.dao.product.MonthClick;
import com.pricecompare.dao.product.Picture;
import com.pricecompare.dao.product.Product;
import com.pricecompare.dao.product.Push;
import com.pricecompare.dao.product.SeasonClick;
import com.pricecompare.dao.product.Type;
import com.pricecompare.dao.product.YearClick;
import com.pricecompare.system.android.core.AndroidMgr;
import com.pricecompare.system.android.core.CustomerMgr;
import com.pricecompare.system.data.core.MonthClickMgr;
import com.pricecompare.system.data.core.SeasonClickMgr;
import com.pricecompare.system.data.core.YearClickMgr;
import com.pricecompare.system.product.core.ProductMgr;
import com.pricecompare.system.product.core.ProductTypeMgr;
import com.pricecompare.util.DaoUtil;

public class ProductAction extends ActionSupport{
	
	private Integer typeid;
	private Integer brandid;
	private Integer id;
	private Integer picid;
	private String productname;
	private String description;
	private Integer descriptionid;
	private String price;
	private String pic;
	private String webaddress;
	
	private Integer pageNum=1;
	private Integer numPerPage=10;
	private Integer totalCount=0;//查询总数
	
	private String statusCode="200";
	private String message=null;
	private String callbackType="closeCurrent";
	private String navTabId="";
	private String forwardUrl="";
	
	private Integer start=(pageNum-1)*numPerPage;
	private Integer limit=numPerPage;
	
	
	


	public Integer getPicid() {
		return picid;
	}

	public void setPicid(Integer picid) {
		this.picid = picid;
	}

	public Integer getBrandid() {
		return brandid;
	}

	public void setBrandid(Integer brandid) {
		this.brandid = brandid;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

	public String getWebaddress() {
		return webaddress;
	}

	public void setWebaddress(String webaddress) {
		this.webaddress = webaddress;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDescriptionid() {
		return descriptionid;
	}

	public void setDescriptionid(Integer descriptionid) {
		this.descriptionid = descriptionid;
	}

	public String getNavTabId() {
		return navTabId;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
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

	public String productIndex(){
		
		try{
			DaoUtil.begin();
			List<Type> typelist=(List<Type>)ProductTypeMgr.forTypeList(null,null);
			ActionContext.getContext().put("typelist", typelist);
			List<Product> list=(List<Product>)ProductMgr.productForList((pageNum-1)*numPerPage, numPerPage);
			totalCount=ProductMgr.productForListCount();
			String action="productIndex";
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
	public String productTypeSearch(){
		try{
			DaoUtil.begin();
			List<Type> typelist=(List<Type>)ProductTypeMgr.forTypeList(null,null);
			ActionContext.getContext().put("typelist", typelist);
			List<Product> list=(List<Product>)ProductMgr.productForType(typeid,(pageNum-1)*numPerPage, numPerPage);
			totalCount=ProductMgr.productForType(typeid,null,null).size();
			ActionContext.getContext().put("typeid", typeid);
			ActionContext.getContext().put("list", list);
			ActionContext.getContext().put("action", "productTypeSearch");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String productInfo(){
		try{
//			System.out.println(id);
			DaoUtil.begin();
			Product product=(Product)DaoUtil.get(Product.class, id);
			ActionContext.getContext().put("product", product);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	
	public String editProductPage(){
		try{
//			System.out.println(id);
			DaoUtil.begin();
			Product product=(Product)DaoUtil.get(Product.class, id);
			List<Brand> brandlist=(List<Brand>)ProductTypeMgr.forBrandListByType(product.getBrand().getType().getId());
//			System.out.println(brandlist.size());
			ActionContext.getContext().put("product", product);
			ActionContext.getContext().put("brandlist", brandlist);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String editDescriptionPage(){
		try{
			DaoUtil.begin();
			Product product=(Product)DaoUtil.get(Product.class, id);
			List<Type> typelist=(List<Type>)ProductTypeMgr.forTypeList(null,null);
			ActionContext.getContext().put("product", product);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String updateDescription(){
		try{
			message="修改成功";
			navTabId="editProductPage";
			DaoUtil.begin();
			Description des=(Description)DaoUtil.get(Description.class, descriptionid);
			des.setDescription(description);
			DaoUtil.update(des);
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
	public String productSearch(){
		try{
			DaoUtil.begin();
			List<Type> typelist=(List<Type>)ProductTypeMgr.forTypeList(null,null);
			ActionContext.getContext().put("typelist", typelist);
			List<Product> list=new ArrayList<Product>();
			String[] productNames=productname.split(" ");
			for(String p:productNames){
			list.addAll((List<Product>)ProductMgr.productForName(p, null,null));
			totalCount=totalCount+ProductMgr.productForNameCount(p, null,null);
			}
			list=list.subList((pageNum-1)*numPerPage, pageNum*numPerPage>list.size()?list.size():pageNum*numPerPage);
			ActionContext.getContext().put("action", "productSearch");
			ActionContext.getContext().put("productname", productname);
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
	public String productUpdate(){
		try{
			DaoUtil.begin();
			Product product=(Product)DaoUtil.get(Product.class, id);
			
//			Type type=(Type)DaoUtil.get(Type.class,typeid);
//			product.setType(type);
			Brand brand=(Brand)DaoUtil.get(Brand.class,brandid);
			product.setBrand(brand);
			Info info=product.getInfo();
				info.setName(productname);
				info.setPrice(Float.parseFloat(price));
				info.setWebaddress(webaddress);
//			Picture p=product.getPicture();
//				p.setAddress(pic);
			
			DaoUtil.save(product);
			DaoUtil.save(info);
//			DaoUtil.save(p);
			DaoUtil.commit();
//			
			callbackType="forward";
			forwardUrl="productInfo?id="+id;
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String productDelete(){
		try{
			DaoUtil.begin();
			Product p=(Product)DaoUtil.get(Product.class,id);
			List<YearClick> list=(List<YearClick>)YearClickMgr.forYearList(id);
			for(YearClick yc:list){
				List<SeasonClick> sclist=(List<SeasonClick>)SeasonClickMgr.forSeasonClickByYearId(yc.getId());
				for(SeasonClick sc:sclist){
					DaoUtil.delete(sc);
				}
				List<MonthClick> mclist=(List<MonthClick>)MonthClickMgr.forMonthClickByYearId(yc.getId());
				for(MonthClick mc:mclist){
					DaoUtil.delete(mc);
				}
				DaoUtil.delete(yc);
			}
			List<CustomerProduct> cplist=(List<CustomerProduct>)CustomerMgr.forCustomerProductList(id);
			for(CustomerProduct cp:cplist){
				DaoUtil.delete(cp);
			}
			List<Push> plist=(List<Push>)AndroidMgr.forPushListByProduct(id);
			for(Push push:plist){
				DaoUtil.delete(push);
			}
			DaoUtil.delete(p);
			DaoUtil.commit();
			navTabId="productIndex";
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
	public String picTest(){
		ActionContext.getContext().put("pic", pic);
		return SUCCESS;
	}
	public String addPicPage(){
		try{
			ActionContext.getContext().put("id", id);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	public String addPic(){
		try{
			DaoUtil.begin();
			Picture pic=new Picture();
			pic.setAddress(webaddress);
			DaoUtil.save(pic);
			Product p=(Product)DaoUtil.get(Product.class,id);
			List<Picture> pl=p.getPicture();
			pl.add(pic);
			p.setPicture(pl);
			DaoUtil.update(p);
			DaoUtil.commit();
			navTabId="editProductPage?id="+id;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DaoUtil.close();
		}
		return SUCCESS;
	}
	public String deletePic(){
		try{
			DaoUtil.begin();
			Picture p=(Picture)DaoUtil.get(Picture.class,picid);
			DaoUtil.delete(p);
			DaoUtil.commit();
			callbackType="forward";
			forwardUrl="editProductPage?id="+id;
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
