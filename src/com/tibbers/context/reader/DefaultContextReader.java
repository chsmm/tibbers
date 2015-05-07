package com.tibbers.context.reader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

import com.tibbers.context.WebXmlContext;
import com.tibbers.context.fileManager.DefaultFileManager;
import com.tibbers.context.fileManager.FileManager;
import com.tibbers.exception.TibbersException;
import com.tibbers.util.ClassLoaderUtil;
import com.tibbers.util.DomHelper;
import com.tibbers.util.xml.SaxEntityResolver;
import com.tibbers.util.xml.SaxErrorHandler;
/**
 * 解析配置文件
 * @author ch
 * @version 1.0
 * @serial 2015-05-07
 */
public class DefaultContextReader implements ContextReader{
	
	
	private static final String INCLUDE_NODE="include";
	private static final String INCLUDE_ATTRIBUTE="src";
	
	protected FileManager fileManager = new DefaultFileManager();
	
	protected ErrorHandler errorHandler = new SaxErrorHandler();
	
	protected EntityResolver entityResolver = new SaxEntityResolver();
	
	protected Set<String> loadConfigurationFiles = new HashSet<String>();
	
	private String contextConfig;
	
	private List<Document> documents;
	
	
	public DefaultContextReader(String contextConfig,List<Document> documents){
		this.contextConfig = contextConfig;
		this.documents = documents;
	}

	@Override
	public void reader() {
		loadConfigurationFiles(contextConfig);
	}
	
	
	/**
	 * 加载配置文件
	 * @param fileName
	 */
	private void loadConfigurationFiles(String contextConfig){
		if(!loadConfigurationFiles.contains(contextConfig)){
			loadConfigurationFiles.add(contextConfig);
			URL url = getConfigurationUrl(contextConfig);
			InputStream is =fileManager.loadFile(url);
			InputSource in = new InputSource(is);
			Document doc;
			try {
				doc = doLoadDocument(in,contextConfig);
			} finally{
				try {
					is.close();
				} catch (IOException e) {
				}
			}
			loadIncludeElementValue(doc);
			//保存doc
			documents.add(doc);
		}	
	}
	
	/**
	 * 加载inculude 节点引入的文件
	 * @param doc
	 */
	private void loadIncludeElementValue(Document doc){
		Element rootElement =  doc.getDocumentElement();
		NodeList children = rootElement.getChildNodes();
		int childSize = children.getLength();
		for (int i = 0; i < childSize; i++) {
			Node node =children.item(i);
			if(node instanceof Element){
				Element element =(Element)node;
				final String nodeName = element.getNodeName();
				if(INCLUDE_NODE.equals(nodeName)){
					loadConfigurationFiles(element.getAttribute(INCLUDE_ATTRIBUTE));
					//rootElement.removeChild(node);
				}
			}	
		}
	}
	
	/**
	 * 加载并解析配置文件数据
	 * @param in 配置文件数据 {@link InputSource}
	 * @return Document文档 {@link Document}
	 */
	protected Document doLoadDocument(InputSource in,String contextConfig){
		try {
			return DomHelper.parse(in,errorHandler,entityResolver);
		} 
		catch (SAXParseException ex) {
			throw new TibbersException(
					"解析配置文件 ["+contextConfig+"]  第"+ex.getLineNumber()+"行,"+ex.getColumnNumber()+"列错误."+ex.getMessage(), ex);
		}
		catch (ParserConfigurationException ex) {
			throw new TibbersException("解析配置文件["+contextConfig+"]失败."+ex.getMessage(), ex);
		}
		catch(Exception ex){
			throw new TibbersException("解析配置文件["+contextConfig+"]失败."+ex.getMessage(), ex);
		}
	}
	
	

	
	/**
	 * 获取配置文件url 
	 * @return URL {@link URL}
	 */
	protected URL getConfigurationUrl(String fileName){
		return ClassLoaderUtil.getResource(fileName, WebXmlContext.class);
	}


}
