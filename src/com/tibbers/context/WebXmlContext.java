package com.tibbers.context;

import com.tibbers.config.PropertyValues;
import com.tibbers.context.reader.DefaultContextReader;
import com.tibbers.context.reader.DefaultDocumentReader;
import com.tibbers.context.reader.Reader;


/**
 * xml配置文件解析器 用于解析xml配置文件
 * @author ch
 * @version 1.0
 * @serial 2015-04-17 
 */
public class WebXmlContext extends AbstractContext {
	
	private Reader contextReader;
	private Reader documentReader;
	
	public WebXmlContext(PropertyValues config) {
		super(config);
	}

	@Override
	public void loading() {
		contextReader();
		documentReader();
	}
	
	
	
	/**
	 * 创建默认的上下文阅读器
	 * @return
	 */
	private void contextReader(){
		if(contextReader==null){
			contextReader = new DefaultContextReader(getContextConfig(),getDocuments());
		}
		reader(contextReader);
	}
	
	/**
	 * 创建默认的Document阅读器
	 * @return
	 */
	private void documentReader(){
		if(documentReader==null){
			documentReader = new DefaultDocumentReader(getDocuments(),getContainer());
		}
		reader(documentReader); 
	}
	
	
	/**
	 * 读取上下文或者document信息
	 * @param reader
	 */
	private void reader(Reader reader){
		reader.reader();
	}
	

}
