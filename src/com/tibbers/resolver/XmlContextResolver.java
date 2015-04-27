package com.tibbers.resolver;

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
	
	protected FileManager fileManager = new DefaultFileManager();
	
	protected ErrorHandler errorHandler = new SaxErrorHandler();
	
	protected EntityResolver entityResolver = new SaxEntityResolver();
	
	protected Set<String> set = new HashSet<String>();
	
	public XmlContextResolver(PropertyValues config) {
		super(config);
	}

	@Override
	public void loading() {
		URL url = getConfigurationUrl(getContextConfig());
		InputStream is =fileManager.loadFile(url);
		InputSource in = new InputSource(is);
		try {
			Document doc = doLoadDocument(in);
			System.out.println(doc);
			//Element  rootElement =  doc.getDocumentElement();
			//parse(rootElement);
		} finally{
			try {
				is.close();
			} catch (IOException e) {
			}
		}	
	}
	
	
	private void loadConfigurationFiles(String fileName){
		if(!set.contains(fileName)){
			set.add(fileName);
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
	
	
	private  void parse(Element  rootElement){
		NodeList list = rootElement.getChildNodes();
		if(list!=null&&list.getLength()>0){
			for (int i = 0; i < list.getLength(); i++) {
				Node node =list.item(i);
				if(node instanceof Element){
					Element e =(Element)node;
					System.out.println(e .getNodeName());
					parse(e);
					System.out.println(e .getNodeName());
					System.out.println("-------------------------");
				}
			
				
			}
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
