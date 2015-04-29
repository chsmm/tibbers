package com.tibbers.util;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

public abstract class CollectionUtils {
	
	/**
	 * 将Properties 合并到map中
	 * @param props
	 * @param map
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> void mergePropertiesIntoMap(Properties props, Map<K, V> map) {
		if (map == null) {
			throw new IllegalArgumentException("map 不能为空");
		}
		if (props != null) {
			for (Enumeration<?> en = props.propertyNames(); en.hasMoreElements();) {
				String key = (String) en.nextElement();
				Object value = props.getProperty(key);
				if (value == null) {
					value = props.get(key);
				}
				map.put((K) key, (V) value);
			}
		}
	}
	
	/**
	 * 判断集合是否为空
	 * @param collection
	 * @return
	 */
	public static <E> boolean isEmpty(Collection<E> collection){
		return (collection!=null&&!collection.isEmpty());
	}

}
