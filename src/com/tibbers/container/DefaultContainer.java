package com.tibbers.container;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultContainer implements Container{
	
	protected Map<String, Class<?>> map = new ConcurrentHashMap<String, Class<?>>();
	
	protected List<Class<?>> list = new ArrayList<>();

	@Override
	public <T> void rigesteredBean(Class<T> clazz) {
		/*if(!map.containsKey(id)){
			map.put(id, clazz);
		}*/
		if(!list.contains(clazz)){
			list.add(clazz);
		}
	}

	@Override
	public <T> T[] getType(Class<T> clazz) {
		/*List<Class<?>> list= new ArrayList<Class<?>>();
		for(Entry<String, Class<?>> entry : map.entrySet() ){
			entry.getValue().isInstance(clazz);
		}*/
		return null;
	}
	
	@Override
	public int getBeanCount(){
		return list.size();
	}
	
	

}
