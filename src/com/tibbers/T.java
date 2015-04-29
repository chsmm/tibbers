package com.tibbers;

public class T {
	public String s="ss";
	
	
	
	public String  test() {
		System.out.println(this + "---"+Thread.currentThread().getName()+"invoke test()");
		return "s";
	}
}
