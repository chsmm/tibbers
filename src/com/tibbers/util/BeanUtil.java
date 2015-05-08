package com.tibbers.util;

import com.tibbers.container.Container;
/**
 * beanUtil
 * @author ch
 * @version 1.0
 * @serial 2015-05-08
 */
public class BeanUtil {
	
	/**
	 *	向容器注册bean
	 * @param beanName 
	 * @param container 容器
	 */
	public static void rigesteredBean(String beanName,Container container){
		try {
			if(StringUtil.hasLength(beanName)){
				Class<?> beanClass = ClassLoaderUtil.forName(beanName);
				if(beanClass!=null){
					container.rigesteredBean(beanClass);
				}
			}
			
		} catch (ClassNotFoundException e) {
		}
	}

}
