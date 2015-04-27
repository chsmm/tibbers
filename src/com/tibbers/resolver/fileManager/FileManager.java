package com.tibbers.resolver.fileManager;

import java.io.InputStream;
import java.net.URL;

public interface FileManager {
	/**
	 * 加载文件 返回InputStream
	 * @param fileUrl
	 * @return
	 */
	 InputStream loadFile(URL fileUrl);

}
