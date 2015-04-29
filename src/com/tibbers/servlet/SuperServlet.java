package com.tibbers.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.tibbers.config.ServletConfigProperty;
import com.tibbers.context.Context;
import com.tibbers.context.WebXmlContext;

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
		Context context = createContext();
		context.setContainer(null);
		context.loading();	
	}
	
	
	
	private Context createContext(){
		return new WebXmlContext(new ServletConfigProperty(getServletConfig()));
		
	}
	
	
	
	

}
