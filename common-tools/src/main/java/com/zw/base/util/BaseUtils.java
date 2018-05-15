package com.zw.base.util;

import com.zw.security.util.CryptUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 通用功能类
 * 
 * @author wu.yy
 * @data   2013-11-08 16:06
 */
public class BaseUtils {

	/**
	 * 校验数组是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String[] str){
		if(str!=null && str.length>0){
			return true;
		}
		return false;
	}
	/**
	 * 校验List是否为空
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isNotEmpty(List<?> list){
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	/**
	 * 根据正则表达式 判断字符串是否符合规则
	 * @author wu.yy
	 * @date   2013-12-17 14:08
	 * @param strRegExp
	 * @param value
	 * @return
	 */
	public static boolean isRegExp(String strRegExp,String value){
		if(StringUtils.isEmpty(strRegExp) || StringUtils.isEmpty(value)){
			return false;
		}
		Pattern pat = Pattern.compile(strRegExp);
		Matcher mat = pat.matcher(value);
		return mat.matches();
	}
	
	/**
	 * 检验字符串是否为null或者""
	 * @return
	 */
	public static boolean isblack(String value){
		return value==null?true:"".equals(value);
	}
	
	/**
	 * 获取盐
	 * @return
	 */
	public static String getSalt(){
		return RandomStringUtils.randomAlphanumeric(10);
	}
	
	/**
	 * 加密
	 * @param username
	 * @param password
	 * @param salt
	 * @return
	 */
	public static String encryptPassword(String username, String password, String salt) {
		return CryptUtils.hash(password, username + salt);
	}
}
