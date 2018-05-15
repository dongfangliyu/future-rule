package com.zw.base.util;


import java.io.Closeable;
import java.io.File;

public class IOResourceUtil {



	public static void closeResource( Closeable stream ) {
		if ( stream == null ) {
			return;
		}
		try {
			stream.close( );
		} catch ( Exception e ) {
		}
	}
	
	/**
	 * 获取Classpath路径
	 * 
	 * @return
	 */
	public static String getClasspath( ) {
		String classPath = Thread.currentThread( ).getContextClassLoader( ).getResource( "" ).getPath( );
		String rootPath = "";
		// windows
		if ( "\\".equals( File.separator ) ) {
			rootPath = classPath.substring( 1 );
			rootPath = rootPath.replace( "/", "\\" );
		}
		// linux
		if ( "/".equals( File.separator ) ) {
			rootPath = classPath.substring( 1 );
			rootPath = rootPath.replace( "\\", "/" );
		}
		return rootPath;
	}

}
