import java.util.Date;
import java.util.List;

import com.pricecompare.dao.author.webpage;
import com.pricecompare.dao.product.Brand;
import com.pricecompare.dao.product.Description;
import com.pricecompare.dao.product.Info;
import com.pricecompare.dao.product.Picture;
import com.pricecompare.dao.product.Product;
import com.pricecompare.dao.product.Type;
import com.pricecompare.util.DaoUtil;


public class dataTranslator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DaoUtil.begin();
		List<webpage> list=(List<webpage>)translateMgr.forlist();
		for(int i=0;i<list.size();i++){
			Product p=new Product();
			String t=list.get(i).getCategory().replaceAll("[?]", "").replaceAll(" ","").trim();
			Type type=(Type)translateMgr.forType(t);
			if(type==null){
				type=new Type();
				type.setName(t);
				type.setEditTime(new Date());
				DaoUtil.save(type);
//				System.out.println(t);

			}
			String b=list.get(i).getCategorydtl().replaceAll("[?]", "").replaceAll(" ","");
			Brand brand=(Brand)translateMgr.forBrand(b);
			if(brand==null){
				brand=new Brand();
				brand.setName(b);
				if(type.getRelatename()!=null){
					Type tt=(Type)translateMgr.forType(type.getRelatename());
					brand.setType(tt);
				}
				else{
					brand.setType(type);
				}
				DaoUtil.save(brand);
			}
			
			if(brand.getRelatename()!=null){
				Brand bb=(Brand)translateMgr.forBrand(brand.getRelatename());
				p.setBrand(bb);
			}
			else{
				p.setBrand(brand);
			}
			Info info=new Info();
				info.setName(list.get(i).getMername());
				info.setPrice(list.get(i).getPrice());
				info.setWebaddress(list.get(i).getBaseUrl());
				info.setWebsite(list.get(i).getFromweb());
			DaoUtil.save(info);
			p.setInfo(info);
			
			Description des=new Description();
				des.setDescription(list.get(i).getDetail());
			DaoUtil.save(des);
			p.setDescription(des);
			
			List<Picture> plist=p.getPicture();
			String pic=list.get(i).getImage();
			String pics[]=pic.split("\\$\\$\\$");
			for(String pi:pics){
				Picture picture=new Picture();
				picture.setAddress(pi);
				DaoUtil.save(picture);
				plist.add(picture);
			}
			p.setPicture(plist);
			DaoUtil.save(p);
//			
		}
//
		DaoUtil.commit();
//		String pic=list.get(0).getImage();
//		System.out.println(pic);
//		String pics[]=pic.split("\\$\\$\\$");
//		for(String p:pics){
//			System.out.println(p);
//		}
//		System.out.println(list.get(30).getMername());
		DaoUtil.close();
	}

}
