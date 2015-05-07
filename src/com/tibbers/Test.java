package com.tibbers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class Test {
	
	public static void main(String[] args) {
	
		//tt.test(t);
	/*	System.out.println(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
		System.out.println(Runtime.getRuntime().availableProcessors());
		Class<?>[] cs = DefaultContainer.class.getDeclaredClasses();

		for (int i = 0; i < cs.length; i++) {
			System.out.println(cs[i].getName());
		}*/
		
		
		String basePakge = "com.tibbers.util";
		
		Set<String> set = new HashSet<String>(Arrays.asList("com.tibbers.a.ss.a.action","com.tibbers.a.xx.ss.action","com.tibbers.action"));
		try {
			Enumeration<URL> urls=  Test.class.getClassLoader().getResources("com/tibbers");
			while (urls.hasMoreElements()) {
				URL url = (URL) urls.nextElement();
				File f = new File(URLDecoder.decode(url.getFile(),"UTF-8"));
				Set<String> fileNames = new HashSet<String>(64);
				getClassName(f,fileNames,"com/tibbers",basePakge,false);
				System.out.println(fileNames);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	
	public static void getClassName(File file,Set<String> set,String path,String basePakge,boolean isAdd){
			for(File s  : file.listFiles()){
				String dir = path+"/"+s.getName();
				String name = dir.replace('/','.');
				if(s.isDirectory()){
					getClassName(s,set,dir,basePakge,getString(0,basePakge,0,name));
				}else{
					if(isAdd){
						set.add(name);
					}
				}	
			}
	}
	
	public static boolean getString(int basePakgeStart, String basePakge,int pakgeStart, String pakges) {
		if (basePakge.length() <= pakges.length()) {
			int start = basePakge.indexOf("*", basePakgeStart);
			boolean b = start == - 1;
			String context = basePakge.substring(basePakgeStart,!b?start:basePakge.length());
			if(!b){
				if (pakges.startsWith(context, pakgeStart)) {
					int len = context.length();
					basePakgeStart = basePakgeStart + len + 1;
					pakgeStart = pakges.indexOf(".",pakgeStart + len);
					return getString(basePakgeStart, basePakge, pakgeStart,pakges);
				}
			}else{
				return pakges.startsWith(context, pakgeStart);
			}
		}
		return false;
	}
	
}

