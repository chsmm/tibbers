package com.tibbers.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public final class ClassLoaderUtil {
	
	private static final String XML_FILE_EXTENSION = ".xml";
	
	/**
	 * 根据路径获取URL
	 * @param resourceName 文件路径
	 * @param clazz  class
	 * @return URL
	 */
	public static URL getResource(String resourceName,Class<?> clazz){
		URL url;
		url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
		if(url == null){
			url = ClassLoaderUtil.class.getClassLoader().getResource(resourceName);
		}
		if(url == null){
			url =clazz.getClassLoader().getResource(resourceName);
		}
		if(url == null)throw new IllegalArgumentException("找不到文件路径 : '" + resourceName + "'");
		
		return url;
		
	}
	
	/**
	 * 根据路径获取Properties
	 * @param resourceName
	 * @return Properties
	 * @throws IOException
	 */
	public static Properties loadAllProperties(String resourceName) throws IOException{
		URL url = getResource(resourceName,ClassLoaderUtil.class);
		Properties prop = new Properties();
		InputStream is = url.openStream();
		try {
			if(StringUtil.hasLength(resourceName) && resourceName.endsWith(XML_FILE_EXTENSION)){
				prop.loadFromXML(is);
			}else{
				prop.load(is);
			}
		}finally{
			is.close();
		}
		return prop;
	}
	
	/**
	 * 根据路径获取输入流
	 * @param resourceName 文件路径
	 * @return InputStream
	 * @throws FileNotFoundException
	 */
	public static InputStream getInputStream(String resourceName) throws FileNotFoundException {
		InputStream is = null ;
		is = ClassLoaderUtil.class.getResourceAsStream(resourceName);
		if(is==null){
			is = ClassLoaderUtil.class.getClassLoader().getResourceAsStream(resourceName);
		}
		if(is==null){
			is = ClassLoader.getSystemResourceAsStream(resourceName);
		}
		if(is==null){
			throw new FileNotFoundException("文件 ["+resourceName+"] 无法打开");
		}
		return is;
	}

}
