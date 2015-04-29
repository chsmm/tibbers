package com.tibbers.context.fileManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DefaultFileManager implements FileManager{

	@Override
	public InputStream loadFile(URL fileUrl) {
		if(fileUrl == null){
			return null;
		}
		InputStream is = openFile(fileUrl);
		return is;
	}
	
	/**
	 * 获取文件流
	 * @param fileUrl
	 * @return InputStream
	 */
	private InputStream openFile(URL fileUrl){
		try {
			InputStream	is = fileUrl.openStream();
			if(is==null){
				 throw new IllegalArgumentException("找不到文件路径 : '" + fileUrl + "'");
			}
			return  is;
		} catch (IOException e) {
			throw new IllegalArgumentException("找不到文件路径 : '" + fileUrl + "'");
		}
	}

}
