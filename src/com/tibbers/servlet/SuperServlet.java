package com.tibbers.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.tibbers.config.ServletConfigProperty;
import com.tibbers.resolver.Resolver;
import com.tibbers.resolver.XmlContextResolver;

public abstract class SuperServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		Resolver resolver = createResolver();
		resolver.setContainer(null);
		resolver.loading();	
	}
	
	
	
	private Resolver createResolver(){
		return new XmlContextResolver(new ServletConfigProperty(getServletConfig()));
		
	}
	
	
	
	

}
