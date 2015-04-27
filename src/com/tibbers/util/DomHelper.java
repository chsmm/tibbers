package com.tibbers.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;

/**
 * 文件解析帮助类 
 * @author ch
 * @version 1.0
 * @since 2015-04-24
 *
 */
public class DomHelper {
	
	/**
	 * JAXP属性用于配置验证的模式语言
	 */
	private static final String SCHEMA_LANGUAGE_ATTRIBUTE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

	/**
	 * 属性的值表示使用XSD架构语言
	 */
	private static final String XSD_SCHEMA_LANGUAGE = "http://www.w3.org/2001/XMLSchema";
	
	/**
	 * 解析文件数据流
	 * @param in {@link InputSource} 文件数据流
	 * @param errorHandler {@link ErrorHandler} 解析数据错误处理 
	 * @param entityResolver {@link EntityResolver}  解析实体
	 * @return Document
	 */
	public static Document parse(InputSource in,ErrorHandler errorHandler,EntityResolver entityResolver)throws Exception{
		DocumentBuilderFactory factory= createDocumentBuilderFactory();
		DocumentBuilder builder = createDocumentBuilder(factory,errorHandler,entityResolver);
		return builder.parse(in);
	}
	
	/**
	 * 创建 DocumentBuilderFactory
	 * @return DocumentBuilderFactory
	 */
	private static DocumentBuilderFactory createDocumentBuilderFactory(){
		DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		factory.setNamespaceAware(true);
		factory.setAttribute(SCHEMA_LANGUAGE_ATTRIBUTE, XSD_SCHEMA_LANGUAGE);
		return factory;
	}
	
	/**
	 * 创建 createDocumentBuilder
	 * @param DocumentBuilderFactory  DOM 解析器的工厂
	 * @param ErrorHandler {@link ErrorHandler} 解析错误处理
	 * @param EntityResolver {@link EntityResolver}  解析实体 
	 * @return DocumentBuilder 获取 DOM 文档实例
	 * @throws Exception
	 */
	private static DocumentBuilder createDocumentBuilder(DocumentBuilderFactory factory,ErrorHandler errorHandler,EntityResolver entityResolver) throws Exception{
		DocumentBuilder builder = factory.newDocumentBuilder();
		if(entityResolver!=null){
			builder.setEntityResolver(entityResolver);
		}
		if(errorHandler!=null){
			builder.setErrorHandler(errorHandler);
		}
		return builder;
		
	}
	 
	
	
	
	
}
