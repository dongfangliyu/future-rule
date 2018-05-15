package com.zw.base.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FileNewName {
	 /**
     * 文件重命名
     * @param file
     * @return
     */
	public static String gatFileName(){
		StringBuilder str = new StringBuilder();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		str.append(sdf.format(date));
		str.append(new Random().nextInt(1000)); 
		return str.toString();
	}
	/*public static void main(String[] args) {
		System.out.println(gatFileName());
	}*/
}
