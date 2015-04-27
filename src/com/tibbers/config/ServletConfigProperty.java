package com.tibbers.config;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
/**
 * 
 * servlet 初始化配置封装
 *
 */
public class ServletConfigProperty implements PropertyValues {
	
	
	private Map<String, String> parameter =  new HashMap<String, String>(0);
	
	public ServletConfigProperty(ServletConfig config) {
		setParameter(config);
    }
	
	private void setParameter(ServletConfig config){
		if(isEmpty()){
			Enumeration<String> en = config.getInitParameterNames();
			parameter = new HashMap<String, String>();
			while (en.hasMoreElements()) {
				String name = en.nextElement();
				String value = config.getInitParameter(name);
				parameter.put(name, value);
				
			}
		}
	}
	
	@Override
	public String getParameter(String name) {
		return  parameter.get(name);
	}

	@Override
	public boolean contains(String name) {
		return parameter.containsKey(name);
	}

	@Override
	public boolean isEmpty() {
		return parameter.isEmpty();
	}



	

}
