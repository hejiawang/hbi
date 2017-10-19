package com.wang.hbi.core.utils.password;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 密码盐，生成扰码
 * @author HeJiawang
 * @date   20171019
 */
public class SaltUtil {

	/**
	 * 随机生成4为数字+字符扰码
	 * @return
	 */
	public static String generateWord(int len) {
		String[] beforeShuffle = new String[] {"1","2", "3", "4", "5", "6", "7",
				"8", "9","0", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j"
		};
		List<String> list = Arrays.asList(beforeShuffle);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		String result = afterShuffle.substring(0, len);
		return result;
	}
}
