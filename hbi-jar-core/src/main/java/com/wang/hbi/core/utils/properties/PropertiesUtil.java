package com.wang.hbi.core.utils.properties;

import java.util.Properties;  
import java.util.concurrent.ConcurrentHashMap;  
import java.util.concurrent.ConcurrentMap;  
  
/** 
 * 用ConcurrentMap来缓存属性文件的key-value 
 */  
public class PropertiesUtil {  
      
    private static ResourceLoader loader = ResourceLoader.getInstance();  
    private static ConcurrentMap<String, String> configMap = new ConcurrentHashMap<String, String>();  
    private static final String DEFAULT_CONFIG_PUSH_FILE = "pushManage.properties";  
    
    private static Properties prop = null;  
  
    public static String getStringByKeyForPush(String key) {  
        try {  
            prop = loader.getPropFromProperties(DEFAULT_CONFIG_PUSH_FILE);  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
        key = key.trim();  
        if (!configMap.containsKey(key)) {  
            if (prop.getProperty(key) != null) {  
                configMap.put(key, prop.getProperty(key));  
            }  
        }  
        return configMap.get(key);  
    }
}
