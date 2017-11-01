package com.wang.hbi.core.http;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * HBI请求包装
 * @author HeJiawang
 * @date   20171101
 */
public class HbiHttpRequest extends HttpServletRequestWrapper {
	
	private HttpServletRequest request = null;
	private boolean flag = false;

	public HbiHttpRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		String method = this.request.getMethod();
		if ("post".equalsIgnoreCase(method)) {
			try {
				request.setCharacterEncoding("utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Map<String, String[]> map = this.request.getParameterMap();
			return map;

		} else if ("get".equalsIgnoreCase(method)) {
			Map<String, String[]> map = this.request.getParameterMap();
			if (flag)  return map;

			if (map == null) {
				return super.getParameterMap();
			} else {
				Set<String> key = map.keySet();
				key.forEach(string -> {
					String[] value = map.get(string);
					value = Arrays.stream(value).map(valueTemp -> {
						String string2 = "";
						try {
							string2 = new String(valueTemp.getBytes("iso-8859-1"), "utf-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						return string2;
					}).toArray(type -> new String[type]);
				});

				flag = true;
				return map;
			}
		} else {
			return super.getParameterMap();
		}
	}

	@Override
	public String[] getParameterValues(String name) {
		Map<String, String[]> map = this.getParameterMap();
		return map==null ? super.getParameterValues(name) : map.get(name) ;
	}

	@Override
	public String getParameter(String name) {
		String[] values = this.getParameterValues(name);
		return values==null ? super.getParameter(name) : values[0] ;
	}

}
