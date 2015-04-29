package com.tibbers.context;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import com.tibbers.config.PropertyValues;
import com.tibbers.container.Container;
/**
 * 实现解析器的抽象类 
 * 该类主要用于 设置配置文件 与 容器{@link Container}}
 * @author ch
 * @version 1.0
 * @since 2015-04-17
 * @see Container
 *
 */
public abstract class AbstractContext implements Context {
	
	private List<Document> documents = new ArrayList<Document>();
	
	private PropertyValues config;
	/**
	 * 配置文件
	 */
	private String contextConfig;
	
	/**
	 * 容器
	 */
	private Container container;
	
	
	public AbstractContext(PropertyValues config) {
		this.config = config;
	}
	
	
	protected String getContextConfig() {
		if(contextConfig==null){
			contextConfig = config.getParameter(CONTEXT_CONFIG);
		}
		return contextConfig;
	}

	protected List<Document> getDocuments() {
		return documents;
	}


	protected void addDocument(Document doc){
		documents.add(doc);
	}
	
	@Override
	public void setContainer(Container container) {
		this.container = container;
	}
	
	public Container getContainer() {
		return container;
	}
	
	
	
	
	
	@Override
	public abstract void loading();

}
