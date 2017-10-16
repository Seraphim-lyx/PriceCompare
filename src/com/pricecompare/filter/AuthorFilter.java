package com.pricecompare.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.pricecompare.dao.product.Type;
import com.pricecompare.util.DaoUtil;

/**
 * Servlet Filter implementation class AuthorFilter
 */
@WebFilter("/AuthorFilter")
public class AuthorFilter implements Filter {
	private String 	include;
    /**
     * Default constructor. 
     */
    public AuthorFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		// pass the request along the filter chain
		 HttpServletRequest request = (HttpServletRequest)arg0;   
         HttpServletResponse response = (HttpServletResponse)arg1;   
         HttpSession session=request.getSession();
       
		 if(request.getParameter("index")==null){
			 if(session.getAttribute("userid")==null){
				 response.sendRedirect("loginPage");
				 return;
			 }
			 else{
				 if(session.getAttribute("authorList")!=null){
					 List<String> authorList=(List<String>)session.getAttribute("authorList");
					String urls[]=request.getRequestURI().split("/");
					String url=urls[urls.length-1];
					Integer result=0;
					System.out.println(url);
					for(String s:authorList){

						if(s.equals(url)){
							result=1;
							break;
						}
					}
					if(result==0){
						response.sendRedirect("error?index=index");
						return;
					}
					else{
						System.out.println("admit");
					}
				 }
			 }
		 }
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
