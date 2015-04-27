package com.tibbers.resolver;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
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

import com.tibbers.config.PropertyValues;
import com.tibbers.exception.TibbersException;
import com.tibbers.resolver.fileManager.DefaultFileManager;
import com.tibbers.resolver.fileManager.FileManager;
import com.tibbers.util.ClassLoaderUtil;
import com.tibbers.util.DomHelper;
import com.tibbers.util.xml.SaxEntityResolver;
import com.tibbers.util.xml.SaxErrorHandler;


/**
 * xml配置文件解析器 用于解析xml配置文件
 * @author ch
 * @version 1.0
 * @serial 2015-04-17 
 */
public class XmlContextResolver extends AbstractContextResolver {
	
	private static final String INCLUDE_NODE="include";
	private static final String INCLUDE_ATTRIBUTE="src";
	
	protected FileManager fileManager = new DefaultFileManager();
	
	protected ErrorHandler errorHandler = new SaxErrorHandler();
	
	protected EntityResolver entityResolver = new SaxEntityResolver();
	
	protected Set<String> loadConfigurationFiles = new HashSet<String>();
	
	private List<Document> documents = new ArrayList<Document>();
	
	public XmlContextResolver(PropertyValues config) {
		super(config);
	}

	@Override
	public void loading() {
		loadConfigurationFiles(getContextConfig());
		for(Document doc : documents){
			print(doc);
		}
		//System.out.println(documents);
	}
	
	/**
	 * 加载配置文件
	 * @param fileName
	 */
	private void loadConfigurationFiles(String fileName){
		if(!loadConfigurationFiles.contains(fileName)){
			loadConfigurationFiles.add(fileName);
			URL url = getConfigurationUrl(getContextConfig());
			InputStream is =fileManager.loadFile(url);
			InputSource in = new InputSource(is);
			Document doc;
			try {
				doc = doLoadDocument(in);
			} finally{
				try {
					is.close();
				} catch (IOException e) {
				}
			}
			loadIncludeElementValue(doc);
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
					rootElement.removeChild(node);
				}
			}	
		}
		documents.add(doc);
	}
	
	private void print(Document doc){
		Element rootElement =  doc.getDocumentElement();
		NodeList children = rootElement.getChildNodes();
		int childSize = children.getLength();
		for (int i = 0; i < childSize; i++) {
			Node node =children.item(i);
			System.out.println(node.getNodeName());
		}
	}
	
	/**
	 * 加载并解析配置文件数据
	 * @param in 配置文件数据 {@link InputSource}
	 * @return Document文档 {@link Document}
	 */
	protected Document doLoadDocument(InputSource in){
		try {
			return DomHelper.parse(in,errorHandler,entityResolver);
		} 
		catch (SAXParseException ex) {
			throw new TibbersException(
					"解析配置文件 ["+getContextConfig()+"]  第"+ex.getLineNumber()+"行,"+ex.getColumnNumber()+"列错误."+ex.getMessage(), ex);
		}
		catch (ParserConfigurationException ex) {
			throw new TibbersException("解析配置文件["+getContextConfig()+"]失败."+ex.getMessage(), ex);
		}
		catch(Exception ex){
			throw new TibbersException("解析配置文件["+getContextConfig()+"]失败."+ex.getMessage(), ex);
		}
	}
	
	

	
	/**
	 * 获取配置文件url 
	 * @return URL {@link URL}
	 */
	protected URL getConfigurationUrl(String fileName){
		return ClassLoaderUtil.getResource(fileName, XmlContextResolver.class);
	}

}
