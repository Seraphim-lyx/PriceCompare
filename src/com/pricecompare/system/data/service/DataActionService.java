package com.pricecompare.system.data.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pricecompare.dao.product.MonthClick;
import com.pricecompare.dao.product.Product;
import com.pricecompare.dao.product.SeasonClick;
import com.pricecompare.dao.product.YearClick;
import com.pricecompare.system.data.core.MonthClickMgr;
import com.pricecompare.system.data.core.SeasonClickMgr;
import com.pricecompare.system.data.core.YearClickMgr;
import com.pricecompare.util.DaoUtil;

public class DataActionService {
	
	
//	public static Integer[] catelist;
//	public static Integer[] data;
//	public static Integer listsize=0;
	public static Calendar cal=Calendar.getInstance();
	public static Integer forYearCount(Integer productid,JSONObject json,JSONArray arr,Integer listsize,List<YearClick> yearlist,List<List> yclist,List<List> calculate){
			Integer data[];
			Integer catelist[];
			Integer total=0;
			Integer max=0;
			Integer min=0;
			Integer balance=0;
			List<Integer> c=new ArrayList<Integer>();
			DaoUtil.begin();
				
				YearClick yc=(YearClick)YearClickMgr.forYear(productid, cal.get(Calendar.YEAR));
				if(yc!=null){
					List<YearClick> list=(List<YearClick>)YearClickMgr.forYearList(productid);
					min=list.get(0).getCount();
					max=list.get(0).getCount();
					yearlist=list;
					catelist=new Integer[list.size()];
					
					data=new Integer[list.size()];
					for(int i=0;i<list.size();i++){
						if(list.get(i).getCount()>max){
							max=list.get(i).getCount();
						}
						if(list.get(i).getCount()<min){
							min=list.get(i).getCount();
						}
						total=total+list.get(i).getCount();
						catelist[i]=list.get(i).getYear();
						data[i]=list.get(i).getCount();
					}
					balance=total/list.size();
					if(list.size()>listsize){
						
						listsize=list.size();
						json.put("catelist",catelist);
					}
					
				}
				else{
					yc=initialYear(productid);
					yearlist.add(yc);
					DaoUtil.save(yc);
					DaoUtil.commit();
					
					data=new Integer[1];
					data[0]=0;
					if(listsize==0){
					catelist=new Integer[1];
					catelist[0]=cal.get(Calendar.YEAR);
					json.put("catelist", catelist);
					}
				}
				String nname=((Product)DaoUtil.get(Product.class,productid)).getInfo().getName();
				JSONObject obj=new JSONObject();
				obj.put("name", nname.length()>30?nname.substring(0, 30):nname);
				obj.put("type", "column");
				obj.put("data",data);
				arr.add(obj);
			DaoUtil.close();
			
			for(int i=0,size=yearlist.size();i<listsize-size;i++){
				YearClick y=new YearClick();
				y.setCount(0);
				yearlist.add(y);
			}
//			System.out.println(yearlist.get(0).getCount());
			yclist.add(yearlist);
			c.add(min);
			c.add(max);
			c.add(balance);
			c.add(total);
			calculate.add(c);
			return listsize;
	}
	public static void forMonthCount(Integer productid,JSONObject json,JSONArray arr,Integer year,List<MonthClick> monthlist,List<List> yclist,List<List> calculate){
		Integer total=0;
		Integer max=0;
		Integer min=0;
		Integer balance=0;
		Integer data[]=new Integer[12];
		List<Integer> c=new ArrayList<Integer>();
		MonthClick mc=null;
		Arrays.fill(data,0);
		DaoUtil.begin();
			mc=(MonthClick)MonthClickMgr.forMonthClick(cal.get(Calendar.MONTH)+1, productid, cal.get(Calendar.YEAR));
			
			if(mc!=null){
				List<MonthClick> list=(List<MonthClick>)MonthClickMgr.forMonthList(year,productid);
				min=list.get(0).getCount();
				max=list.get(0).getCount();
				
				for(int i=0;i<list.get(0).getMonth()-1;i++){
					MonthClick m=new MonthClick();
					m.setCount(0);
					monthlist.add(m);
				}
				monthlist.addAll(list.get(0).getMonth()-1, list);
			
				for(int i=0;i<list.size();i++){
					if(list.get(i).getCount()>max){
						max=list.get(i).getCount();
					}
					if(list.get(i).getCount()<min){
						min=list.get(i).getCount();
					}
					total=total+list.get(i).getCount();
					data[list.get(i).getMonth()-1]=list.get(i).getCount();
				}
				balance=total/list.size();
			}
			else{
				YearClick yc=(YearClick)YearClickMgr.forYear(productid, year);
				for(int i=0;i<cal.get(Calendar.MONTH);i++){
					MonthClick m=new MonthClick();
					m.setCount(0);
					monthlist.add(m);
				}
				if(yc!=null){
					mc=initialMonth(yc);
					DaoUtil.save(mc);
					DaoUtil.commit();
				}
				else{
					yc=initialYear(productid);
					mc=initialMonth(yc);
					DaoUtil.save(yc);
					DaoUtil.save(mc);
					DaoUtil.commit();
				}
				monthlist.add(Calendar.MONTH,mc);
			}
			String nname=((Product)DaoUtil.get(Product.class,productid)).getInfo().getName();
			JSONObject obj=new JSONObject();
			obj.put("name", nname.length()>30?nname.substring(0, 30):nname);
			obj.put("data", data);
			arr.add(obj);
			DaoUtil.close();
			for(int i=0,size=monthlist.size();i<12-size;i++){
//				System.out.println(monthlist.size());
				MonthClick m=new MonthClick();
				m.setCount(0);
				monthlist.add(m);
			}
			yclist.add(monthlist);
			c.add(min);
			c.add(max);
			c.add(balance);
			c.add(total);
			calculate.add(c);
	}
	public static void forSeasonCount(Integer productid,JSONObject json,JSONArray arr,Integer year,List<SeasonClick> seasonlist,List<List> yclist,List<List> calculate){
		Integer total=0;
		Integer max=0;
		Integer min=0;
		Integer balance=0;
		List<Integer> c=new ArrayList<Integer>();
		Integer data[]=null;
		DaoUtil.begin();
			SeasonClick sc=(SeasonClick)SeasonClickMgr.forSeasonClick(judgeSeason(), productid, year);
			
			if(sc!=null){
				List<SeasonClick> list=(List<SeasonClick>)SeasonClickMgr.forSeasonList(year, productid);
				min=list.get(0).getCount();
				max=list.get(0).getCount();
				for(int i=0;i<list.get(0).getSeason()-1;i++){
					SeasonClick m=new SeasonClick();
					m.setCount(0);
					seasonlist.add(m);
				}
				seasonlist.addAll(list.get(0).getSeason()-1, list);
				data=new Integer[4];
				Arrays.fill(data, 0);
				for(int i=0;i<list.size();i++){
					if(list.get(i).getCount()>max){
						max=list.get(i).getCount();
					}
					if(list.get(i).getCount()<min){
						min=list.get(i).getCount();
					}
					total=total+list.get(i).getCount();
					data[list.get(i).getSeason()-1]=list.get(i).getCount();
				}
				balance=total/list.size();
			}
			else{
				YearClick yc=(YearClick)YearClickMgr.forYear(productid, year);
				for(int i=0;i<judgeSeason()-1;i++){
					SeasonClick m=new SeasonClick();
					m.setCount(0);
					seasonlist.add(m);
				}
				if(yc!=null){
					sc=initialSeason(yc);
					DaoUtil.save(sc);
					DaoUtil.commit();
				}
				else{
					yc=initialYear(productid);
					sc=initialSeason(yc);
					DaoUtil.save(yc);
					DaoUtil.save(sc);
					DaoUtil.commit();
				}
				seasonlist.add(judgeSeason()-1,sc);
			}
			String nname=((Product)DaoUtil.get(Product.class,productid)).getInfo().getName();
			JSONObject obj=new JSONObject();
				obj.put("name", nname.length()>30?nname.substring(0, 30):nname);
				obj.put("type", "column");
				obj.put("data", data);
			arr.add(obj);
			DaoUtil.close();
			for(int i=0,size=seasonlist.size();i<4-size;i++){
//				System.out.println(monthlist.size());
				SeasonClick m=new SeasonClick();
				m.setCount(0);
				seasonlist.add(m);
			}
			yclist.add(seasonlist);
			c.add(min);
			c.add(max);
			c.add(balance);
			c.add(total);
			calculate.add(c);
	}
			
	
	public static YearClick initialYear(Integer productid){
		/*
		 * 初始化年份
		 */
		YearClick yc=new YearClick();
		yc.setProduct((Product)DaoUtil.get(Product.class, productid));
		yc.setCount(0);
		yc.setYear(cal.get(Calendar.YEAR));
		return yc;
	}
	public static MonthClick initialMonth(YearClick yc){
		MonthClick mc=new MonthClick();
		mc.setCount(0);
		mc.setMonth(cal.get(Calendar.MONTH)+1);
		mc.setYearclick(yc);
		return mc;
	}
	public static SeasonClick initialSeason(YearClick yc){
		SeasonClick sc=new SeasonClick();
		sc.setCount(0);
		sc.setSeason(judgeSeason());
		sc.setYearclick(yc);
		return sc;
	}
	public static Integer judgeSeason(){
		Integer month=cal.get(Calendar.MONTH)+1;
		Integer season=0;
		if(month>=1&&month<=3){
			season=1;
		}
		else if(month>=4&&month<=6){
			season=2;
		}
		else if(month>=7&&month<=9){
			season=3;
		}
		else if(month>=10&&month<=12){
			season=4;
		}
		
		return season;
	}

}
