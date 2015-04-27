package com.tibbers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.tibbers.resolver.fileManager.DefaultFileManager;
import com.tibbers.resolver.fileManager.FileManager;
import com.tibbers.util.StringUtil;

public class Test {
	
	public static void main(String[] args) {
			FileManager fileManager = new DefaultFileManager();
			try {
				URL url=Thread.currentThread().getContextClassLoader().getResource("com//config//MyHtml.html");
				System.out.println(url.toString());
			
				InputStream is =fileManager.loadFile(url);
				/*BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				String s;
				while ((s=reader.readLine())!=null ){
					if(StringUtil.hasLength(s)){
						System.out.println(s);
					}	
				}*/
				
				
				InputSource in = new InputSource(is);
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				builderFactory.setNamespaceAware(true);
				DocumentBuilder builder = builderFactory.newDocumentBuilder();
				Document document = builder.parse(in);
				Element  rootElement =  document.getDocumentElement();
				
				
				parse(rootElement);
				/*NodeList list = rootElement.getChildNodes();
				for (int i = 0; i < list.getLength(); i++) {
					Node node =list.item(i);
					if(node instanceof Element){
						Element e =(Element)node;
						System.out.println(e .getNamespaceURI());
						System.out.println(e .getNodeName());
						System.out.println(e .getNodeType());
					}
				
					System.out.println("-------------------------");
				}
				*/
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	public static void parse(Element  rootElement){
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
	

}
