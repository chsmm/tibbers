package com.tibbers.config;


public interface PropertyValues {
	/**
     * @param 根据属性名获取属性
     * @return 属性
     */
    String getParameter(String name);
    
    boolean contains(String name);
    
    boolean isEmpty();
    
    

}
