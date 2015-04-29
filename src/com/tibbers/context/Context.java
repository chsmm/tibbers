package com.tibbers.context;

import com.tibbers.container.Container;

/**
 * 解析器接口 所有配置文件的解析处理应当继承该接口
 * @author ch
 * @version 1.0
 * @since 2015-04-17
 *
 */
public interface Context {
	
	/**
	 * 配置文件属性名称
	 */
	String CONTEXT_CONFIG="contextConfigLocation";
	
	
	/**
	 * 设置存储容器
	 * @param container
	 */
	void setContainer(Container container);
	
	/**
	 * 加载并解析指定的配置文件
	 */
	void loading();

}
