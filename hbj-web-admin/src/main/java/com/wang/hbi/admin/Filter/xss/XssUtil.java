package com.wang.hbi.admin.Filter.xss;

import java.util.Iterator;
import java.util.Map;

/**
 * 防跨域脚本攻击过滤器
 *
 * @author HeJiawang
 * @date   20171019
 */
public class XssUtil {

	private static Map<String, Object> map = XssPropertyConfigurer.getPropMap();

	public static String cleanXss(String value) {
		if (map != null) {
			Iterator<String> it = map.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				value = value.replaceAll(key, String.valueOf(map.get(key)));
			}
			/*map.forEach((k,v)->{
				value = value.replaceAll(k, String.valueOf(v));
			});*/
		}
		return value.trim();
	}
}
