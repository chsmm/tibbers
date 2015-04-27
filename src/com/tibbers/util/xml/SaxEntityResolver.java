package com.tibbers.util.xml;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibbers.util.ClassLoaderUtil;
import com.tibbers.util.CollectionUtils;
import com.tibbers.util.StringUtil;

public class SaxEntityResolver implements EntityResolver{
	private static final String DEFAULT_SCHEMA_MAPPINGS_LOCATION = "META-INF/tibbers.schemas";
	private volatile  Map<String, String> schemaMappings;

	@Override
	public InputSource resolveEntity(String publicId, String systemId)
			throws SAXException, IOException {
		String resourceLocation = getSchemaMappings().get(systemId);
		InputSource source = null;
		if(StringUtil.hasLength(resourceLocation)){
			source =  new InputSource(ClassLoaderUtil.getInputStream(resourceLocation));
			source.setPublicId(publicId);
			source.setSystemId(systemId);
		}
		return source;
	}
	
	/**
	 * 加载本地schemaMappings配置文件
	 * @return schemaMappings
	 */
	@SuppressWarnings("static-access")
	private Map<String, String> getSchemaMappings(){
		if(this.schemaMappings==null){
			synchronized (this) {
				if(this.schemaMappings==null){
					try {
						Properties props = ClassLoaderUtil.loadAllProperties(DEFAULT_SCHEMA_MAPPINGS_LOCATION);
						Map<String, String> schemaMappings = new ConcurrentHashMap<String, String>(props.size());
						CollectionUtils.mergePropertiesIntoMap(props, schemaMappings);
						this.schemaMappings = schemaMappings;
					} catch (IOException ex) {
						throw new IllegalStateException(
								"加载 schemaMappings 配置文件[" + this.DEFAULT_SCHEMA_MAPPINGS_LOCATION + "] 失败", ex);
					}
				}
			}
			
		}
		return this.schemaMappings;
	}

}
