package com.shpro.xus.shproject.util;

import java.security.KeyStore.Entry;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 公司: 云筑网 -------------- 作者: 王旭
 * 
 * 日期: 2017年3月24日 说明:
 * 
 * 网站: http://www.yunzhuw.com/ 修订: 日期: 作者: 说明：
 * 
 */

public class MD5Util {
	private static final String encryModel = "MD5";

	/**
	 * 32λmd5. 32位小写md5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		return encrypt(encryModel, str);
	}

	public static String encrypt(String algorithm, String str) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(str.getBytes());
			StringBuffer sb = new StringBuffer();
			byte[] bytes = md.digest();
			for (int i = 0; i < bytes.length; i++) {
				int b = bytes[i] & 0xFF;
				if (b < 0x10) {
					sb.append('0');
				}
				sb.append(Integer.toHexString(b));
			}
			return sb.toString();
		} catch (Exception e) {
			return "";
		}
	}


}
