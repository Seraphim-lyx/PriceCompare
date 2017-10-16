package com.pricecompare.system.android.service;

import java.util.Calendar;

import com.pricecompare.dao.product.MonthClick;
import com.pricecompare.dao.product.Product;
import com.pricecompare.dao.product.SeasonClick;
import com.pricecompare.dao.product.YearClick;
import com.pricecompare.system.data.core.MonthClickMgr;
import com.pricecompare.system.data.core.SeasonClickMgr;
import com.pricecompare.system.data.core.YearClickMgr;
import com.pricecompare.system.data.service.DataActionService;
import com.pricecompare.util.DaoUtil;

public class AndroidService {
	private Calendar cal=Calendar.getInstance();
	
	private Integer currentYear=cal.get(Calendar.YEAR);
	private Integer currentSeason=DataActionService.judgeSeason();
	private Integer currentMonth=cal.get(Calendar.MONTH)+1;
	
	public void clickCountUpdate(Integer productid){
		/*
		 * 更新点击量
		 * 传入商品ID
		 */
		MonthClick mc=null;
		SeasonClick sc=null;
		DaoUtil.begin();
		YearClick yc=(YearClick)YearClickMgr.forYearClick(productid, currentYear);
		if(yc==null){
			yc=initYear(productid);
			sc=initSeason(yc);
			mc=initMonth(yc);
			
		}
		else{
			yc.setCount(yc.getCount()+1);
			mc=(MonthClick)MonthClickMgr.forMonthClick(currentMonth, productid,currentYear);
			sc=(SeasonClick)SeasonClickMgr.forSeasonClick(currentSeason, productid,currentYear);
			if(mc==null){
				mc=initMonth(yc);
				
			}
			else{
				mc.setCount(mc.getCount()+1);
			}
			if(sc==null){
				sc=initSeason(yc);
			}
			else{
				
				sc.setCount(sc.getCount()+1);
			}
		}
		DaoUtil.save(yc);
		DaoUtil.save(mc);
		DaoUtil.save(sc);
		DaoUtil.commit();
		
		
		
		DaoUtil.close();
	}
	
	
	public YearClick initYear(Integer productid){
		/*
		 * 初始化月份
		 */
		YearClick yc=new YearClick();
		yc.setCount(1);
		yc.setProduct((Product)DaoUtil.get(Product.class, productid));
		yc.setYear(currentYear);
		
		return yc;
	}
	public SeasonClick initSeason(YearClick yc){
		/*
		 * 初始化季度
		 */
		SeasonClick sc=new SeasonClick();
		sc.setSeason(currentSeason);
		sc.setCount(1);
		sc.setYearclick(yc);
		return sc;
	}
	public MonthClick initMonth(YearClick yc){
		/*
		 * 初始化月份
		 */
		MonthClick mc=new MonthClick();
		mc.setCount(1);
		mc.setMonth(currentMonth);
		mc.setYearclick(yc);
		return mc;
	}
}
