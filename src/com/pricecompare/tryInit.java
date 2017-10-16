package com.pricecompare;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.pricecompare.dao.author.Author;
import com.pricecompare.dao.author.Role;
import com.pricecompare.util.DaoUtil;

/**
 * Application Lifecycle Listener implementation class tryInit
 *
 */
@WebListener
public class tryInit implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public tryInit() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
//    	initPathList(arg0.getServletContext());
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
    public static void initPathList(ServletContext event) {
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
    	String s=null;
    	try{
    	 DocumentBuilder db = dbf.newDocumentBuilder();  
         Document doc = db.parse(new FileInputStream(event.getRealPath("WEB-INF/path.xml"))); 
         initData("系统管理员","admin",doc);
//         initData("安卓管理员","android",doc);
//         initData("信息分析员","data",doc);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    public static void initData(String roleName,String rolePath,Document doc){
    	DaoUtil.begin();
        Role role=new Role();
        	role.setName(roleName);
        	role.setAuthor(new ArrayList<Author>());
        Element e=(Element)doc.getElementsByTagName(rolePath).item(0);
        NodeList nl=e.getElementsByTagName("path");
        for(int i=0;i<nl.getLength();i++){
       	 Author author=new Author();
       	 author.setPath(nl.item(i).getFirstChild().getNodeValue());
       	 role.getAuthor().add(author);
       	 DaoUtil.save(author);
       	
        }
        DaoUtil.save(role);
        DaoUtil.commit();
        DaoUtil.close();
    }
	
}
