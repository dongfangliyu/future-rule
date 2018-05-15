package com.zw.base.util;

import com.google.common.base.Strings;

import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 属性文件工具类
 * @author Liew
 * Apr 8, 2016
 */
public class PropUtil {


	public static Properties loadProps( String propsPath ) {
		Properties props = new Properties( );
		InputStream is = null;
		try {
			if (Strings.isNullOrEmpty(propsPath) ) {
				throw new IllegalArgumentException( );
			}
			String suffix = ".properties";
			if ( propsPath.lastIndexOf( suffix ) == -1 ) {
				propsPath += suffix;
			}
			is = Thread.currentThread( ).getContextClassLoader( ).getResourceAsStream( propsPath );
			if ( is != null ) {
				props.load( is );
			}
		} catch ( Exception e ) {
			throw new RuntimeException( e );
		} finally {
			IOResourceUtil.closeResource( is );
		}
		return props;
	}
	/**
	 * 根据传入的inputh获取props
	 * 
	 * @param input
	 * @return
	 */
	public static Properties loadProps( InputStream input ) {
		Properties props = new Properties( );
		InputStream is = input;
		try {
			if ( is != null ) {
				props.load( is );
			}
		} catch ( Exception e ) {
			throw new RuntimeException( e );
		} finally {
			IOResourceUtil.closeResource( is );
		}
		return props;
	}

	/**
	 * 根据传入的Reade获取props
	 * 
	 * @param reader
	 * @return
	 */
	public static Properties loadProps( Reader reader ) {
		Properties props = new Properties( );
		Reader readerToUse = reader;
		try {
			if ( readerToUse != null ) {
				props.load( readerToUse );
			}
		} catch ( Exception e ) {
			throw new RuntimeException( e );
		} finally {
			IOResourceUtil.closeResource( readerToUse );
		}
		return props;
	}

	/**
	 * 加载属性文件，并转为 Map
	 */
	public static Map< String, String > loadPropsToMap( String propsPath ) {
		Map< String, String > map = new HashMap< String, String >( );
		Properties props = loadProps( propsPath );
		for ( String key : props.stringPropertyNames( ) ) {
			map.put( key, props.getProperty( key ) );
		}
		return map;
	}

	/**
	 * 根据传入的reader转换为Map
	 * 
	 * @param reader
	 * @return
	 */
	public static Map< String, String > loadPropsToMap( Reader reader ) {
		Map< String, String > map = new HashMap< String, String >( );
		Properties props = loadProps( reader );
		if ( props != null ) {
            props.stringPropertyNames().forEach((key)->{
                map.put( key, props.getProperty( key ) );
            });
		}
		return map;
	}

	/**
	 * 根据传入的input转化为Map
	 * 
	 * @param input
	 * @return
	 */
	public static Map< String, String > loadPropsToMap( InputStream input ) {
		Map< String, String > map = new HashMap< >( );
		Properties props = loadProps( input );
		if ( props != null ) {
			for ( String key : props.stringPropertyNames( ) ) {
				map.put( key, props.getProperty( key ) );
			}
		}
		return map;
	}

	public static Map< String, String > loadPropsToMap( Properties props ) {
		Map< String, String > map = new HashMap<>( );
		if ( props != null ) {
			for ( String key : props.stringPropertyNames( ) ) {
				map.put( key, props.getProperty( key ) );
			}
		}
		return map;
	}

	public static String getValue( Properties props, String key ) {
		return props.getProperty( key );
	}

	public static String getValue( Properties props, String key, String defaultValue ) {
		return props.getProperty( key, defaultValue );
	}
}
