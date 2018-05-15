package com.zw.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexUtil {

	public static boolean isEmail( String str ) {
		boolean result = validByRegex( "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", str );
		return result;
	}


	public static boolean isMobile( String str ) {
		boolean result = validByRegex( "^(((13[0-9]{1})|(15[0-9]{1})|17[013678]|(18[0-9]{1}))+\\d{8})$", str );
		return result;
	}


	public static boolean isPhone( String str ) {
		boolean result = validByRegex("^([a-z0-9A-Z]+[-|\\\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\\\.)+[a-zA-Z]{2,}$", str );
		return result;
	}


	public static boolean isZip( String str ) {
		boolean result = validByRegex( "^[0-9]{6}$", str );
		return result;
	}


	public static boolean isQq( String str ) {
		boolean result = validByRegex( "^[1-9]\\d{4,9}$", str );
		return result;
	}

	public static boolean isIp( String str ) {
		boolean result = validByRegex( "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$", str );
		return result;
	}


	public static boolean isChinese( String str ) {
		boolean result = validByRegex( "^[\u4e00-\u9fa5]+$", str );
		return result;
	}


	public static boolean isChrNum( String str ) {
		boolean result = validByRegex( "^([a-zA-Z0-9]+)$", str );
		return result;
	}

	public static boolean isUrl( String url ) {
		return validByRegex( "(http://|https://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", url );
	}

	public static boolean validByRegex( String regex, String str ) {
		Pattern pattern = Pattern.compile( regex, Pattern.CASE_INSENSITIVE );
		Matcher regexMatcher = pattern.matcher( str );
		return regexMatcher.find( );
	}
}
