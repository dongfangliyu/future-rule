package com.zw.rule.util;

import com.google.common.base.Strings;

import java.util.Locale;

/**
 * 本地化工具类
 * 
 * @author Liew Apr 8, 2016
 */
public abstract class LocaleUtil {
	private static final char HYPHEN = '-';
	private static final char UNDERSCORE = '_';

	public static Locale parse( String locale, String variant ) {
		String language = locale;
		String country = null;
		int index = -1;

		if ( ( ( index = locale.indexOf( UNDERSCORE ) ) > -1 ) || ( ( index = locale.indexOf( HYPHEN ) ) > -1 ) ) {
			language = locale.substring( 0, index );
			country = locale.substring( index + 1 );
		}

		if ( Strings.isNullOrEmpty( language ) ) {
			return null;
		}

		if ( Strings.isNullOrEmpty( country ) ) {
			country = "";
		}
		if ( variant != null ) {
			return new Locale( language, country, variant );
		}
		return new Locale( language, country );
	}

	public static String language( Locale locale ) {
		return locale.getLanguage( );
	}

	public static String language( String locale ) {
		if ( Strings.isNullOrEmpty( locale ) )
			return Locale.getDefault( ).getLanguage( );
		String language = locale;
		int index = -1;
		if ( ( ( index = locale.indexOf( UNDERSCORE ) ) > -1 ) || ( ( index = locale.indexOf( HYPHEN ) ) > -1 ) ) {
			language = locale.substring( 0, index );
		}
		return language.toLowerCase( );
	}
}
