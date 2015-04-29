package com.tibbers.context.reader;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibbers.container.Container;


public class DefaultDocumentReader implements DocumentReader{
	
	
	private static final String BEAN_ELEMENT="bean";
	private static final String INTERCEPTOR_ELEMENT="interceptor";
	private static final String AUTOSCAN_ELEMENT="auto-scan";
	
	private List<Document> documents;
	
	private Container container;
	
	public DefaultDocumentReader(List<Document> documents,Container container) {
		this.documents=documents;
		this.container = container;
	}
	
	
	@Override
	public void reader() {
		for(Document doc : documents ){
			Element root = doc.getDocumentElement();
			parseElement(root);
		}
	}
	
	/**
	 * 解析
	 * @param element
	 */
	private void parseElement(Element element){
		NodeList nodeList = element.getChildNodes();
		int length = nodeList.getLength();
		for(int i=0;i<length;i++){
			Node node = nodeList.item(i);
			if(node instanceof Element){
				Element el = (Element)node;
				String elementName = el.getNodeName();
				if(BEAN_ELEMENT.equals(elementName)){
					processBeanElement(el);
				}else if(INTERCEPTOR_ELEMENT.equals(elementName)){
					parseElement(el);
				}else if(AUTOSCAN_ELEMENT.equals(elementName)){
					processAutoScanElement(el);
				}
			}
		}
	}
	
	private void processBeanElement(Element element){
		
	}
	
	private void processAutoScanElement(Element element){
		
	}

}