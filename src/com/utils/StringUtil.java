package com.utils;



public class StringUtil {
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static Boolean isNullOrEmpty(String str){
		if(null==str||"".equals(str.trim())){
			return true;
		}
		return false;	
	}

}
