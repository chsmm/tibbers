package com.tibbers;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Test {
	
	public static void main(String[] args) {
		Test tt= new Test();
		T t= new T();
		tt.test(t);
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
	
	static ThreadLocal<T> threadLocal = new ThreadLocal<T>();
	
	public  void test(final T t){
		for(int i=0;i<100;i++){
			new Thread(){
					@Override
					public void run() {
						threadLocal.set(t);
						T s =threadLocal.get();
						System.out.println(s.s+"----"+Thread.currentThread().getName()+"访问时间"+System.currentTimeMillis());
						s.s="t";	
					}
				}.start();
			}
	

		}
	}
