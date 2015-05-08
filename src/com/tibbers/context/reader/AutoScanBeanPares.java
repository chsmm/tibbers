package com.tibbers.context.reader;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import com.tibbers.container.Container;
import com.tibbers.util.BeanUtil;
import com.tibbers.util.ClassLoaderUtil;
import com.tibbers.util.StringUtil;

/**
 * 处理自动装载
 * @author ch
 * @version 1.0
 * @serial 2015-05-07
 */
public class AutoScanBeanPares {
	
	private static final String DEFUALT_SUFFIX=".class";
	
	/**
	 * 解析
	 * @param basePackge 配置解析的包
	 * @return Set<String> className
	 */
	public Set<String> pares(String basePackge,Container container){
		String[] basePackges = StringUtil.paresToArray(basePackge, ";");
		//存放实际packge容器
		Set<String> packges= new HashSet<>(basePackges.length);
		//根据basepackge 解析 实际需要加载的packge
		for(String packge : basePackges){
			int end =  packge.indexOf('*');
			String newPackge = end==-1 ? packge:packge.substring(0, end-1);
			packges.add(newPackge);
		}
		return doPackgePares(packges.toArray(new String[packges.size()]),basePackges,container);
	}
	
	/**
	 * 实际处理解析方法
	 * @param packges
	 * @param basePackges
	 * @return Set<String> className
	 */
	private Set<String> doPackgePares(String[] packges,String[] basePackges,Container container){
		Set<String> beans = new HashSet<String>();
		for(int i=0;i<packges.length;i++){
			String packge = nameConvertToDirectory(packges[i]);
			Enumeration<URL> urls = ClassLoaderUtil.getResources(packge);
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				try {
					File file = new File(URLDecoder.decode(url.getFile(),"UTF-8"));
					getBeanameByPackge(file,beans,packge,basePackges[i],false,container);
				} catch (UnsupportedEncodingException e) {
				}
			}
		}
		return beans;
	}
	
	/**
	 * 遍历包获取bean name
	 * @param file
	 * @param set
	 * @param path
	 * @param basePakge
	 * @param isAdd
	 */
	private void getBeanameByPackge(File file,Set<String> beans,String path,String basePakge,boolean isAdd,Container container){
		for(File subFile  : file.listFiles()){
			String dir = path+"/"+subFile.getName();
			String name = directoryConvertToName(dir);
			if(subFile.isDirectory()){
				getBeanameByPackge(subFile,beans,dir,basePakge,checkPackge(0,basePakge,0,name),container);
			}else{
				if(isAdd && isDefualtSuffix(name) && !beans.contains(name)){
					String className = name.substring(0,name.length()-DEFUALT_SUFFIX.length());
					BeanUtil.rigesteredBean(className, container);
				}
			}	
		}
	}
	
	private boolean isDefualtSuffix(String name){
		return name.endsWith(DEFUALT_SUFFIX);
	}
	/**
	 * 检查是否是自动装载包
	 * @param basePakgeStart
	 * @param basePakge
	 * @param pakgeStart
	 * @param pakges
	 * @return
	 */
	private  boolean checkPackge(int basePakgeStart, String basePakge,int pakgeStart, String pakges) {
		if (basePakge.length() <= pakges.length()) {
			int start = basePakge.indexOf("*", basePakgeStart);
			boolean b = start == - 1;
			String context = basePakge.substring(basePakgeStart,!b?start:basePakge.length());
			if(!b){
				if (pakges.startsWith(context, pakgeStart)) {
					int len = context.length();
					basePakgeStart = basePakgeStart + len + 1;
					pakgeStart = pakges.indexOf(".",pakgeStart + len);
					return checkPackge(basePakgeStart, basePakge, pakgeStart,pakges);
				}
			}else{
				return pakges.startsWith(context, pakgeStart);
			}
		}
		return false;
	}
	
	private String nameConvertToDirectory(String packge){
		return packge.replace('.', '/');
	}
	
	private String directoryConvertToName(String packge){
		return packge.replace('/','.');
	}

}
