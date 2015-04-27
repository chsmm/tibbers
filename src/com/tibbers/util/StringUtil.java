package com.tibbers.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

public final class StringUtil {
	/**
	 * 创建list
	 * @param parameter
	 * @param Collection
	 */
	public static void paresToList(String parameter,Collection<String> container,String delim){
		if(null==parameter){
			return;
		}
		 StringTokenizer st = new StringTokenizer(parameter, delim);
		 while (st.hasMoreTokens()) {
	         String token = st.nextToken().trim();
	         if (token.length() > 0) {
	        	 container.add(token);
	         }
	     }
	}
	
	/**
	 * 指定字符 分割   返回数组
	 * @param parameter
	 * @param Collection
	 */
	public static String[] paresToArray(String parameter,String delim){
		if(!hasLength(parameter)){
			return null;
		}
		List<String> container = new ArrayList<String>();
		paresToList(parameter,container,delim);
		return (container.toArray(new String[container.size()]));
	}
	
	/**
	 * 判断字符是否为空<br>
	 * StringUtils.hasLength(null) = false<br>
	 * StringUtils.hasLength("") = false<br>
	 * StringUtils.hasLength(" ") = true<br>
	 * StringUtils.hasLength("Hello") = true<br>
	 * @param str
	 * @return
	 */
	public static boolean hasLength(String str) {
		return hasLength((CharSequence)str);
	}
	
	
	/**
	 * 判断字符数组是否为空<br>
	 * StringUtils.hasLength(null) = false<br>
	 * StringUtils.hasLength({}) = false<br>
	 * StringUtils.hasLength({"Hello"}) = true 
	 * @param str
	 * @return
	 */
	public static boolean hasLength(String[] str){
		return hasLength((CharSequence[])str);
	}
	
	/**
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true
	 * StringUtils.hasLength("Hello") = true
	 * @param str
	 * @return
	 */
	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}
	
	/**
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength({}) = false
	 * StringUtils.hasLength({"Hello"}) = true 
	 * @param str
	 * @return
	 */
	public static boolean hasLength(CharSequence[] str) {
		return (str != null && str.length > 0);
	}
	
	/**
	 * 合并数组
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static String[] mergeStringArrays(String[] array1, String[] array2) {
		List<String> result = new ArrayList<String>();
		result.addAll(Arrays.asList(array1));
		for (String str : array2) {
			if (!result.contains(str)) {
				result.add(str);
			}
		}
		return toStringArray(result);
	}
	
	/**
	 * 转换集合成数组
	 * @param collection
	 * @return
	 */
	public static String[] toStringArray(Collection<String> collection) {
		if (collection == null) {
			return null;
		}
		return collection.toArray(new String[collection.size()]);
	}
	
	/**
	 * 数组转成字符串
	 * @param arr
	 * @param delim
	 * @return
	 */
	public static String arrayToDelimitedString(Object[] arr, String delim) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append(arr[i]);
		}
		return sb.toString();
	}

}
