package com.zw.base.util;

import java.util.Random;


/**
 * 
 * 功能说明：生成随机数
 * 典型用法：用于生成验证码
 * 特殊用法：该类在系统中的特殊用法的说明	
 * @author panshangbin
 * 修改人: 
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：2015-5-11
 * Copyright zzl-apt
 */
public class RandomUtil {

	/** * 产生随机字符串 * */
	private static Random randGen = null;
	private static Random randGen1 = null;
	private static char[] numbersAndLetters = null;
	private static char[] numbersAndLetters1 = null;

	
	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			randGen = new Random();
			numbersAndLetters = ("0123456789").toCharArray();
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(10)];
		}
		return new String(randBuffer);
	}

	public static final String randomString1(int length) {
		if (length < 1) {
			return null;
		}
		if (randGen1 == null) {
			randGen1 = new Random();
			numbersAndLetters1 = ("0123456789abcdefghijklmnopqrstuvwxyz"
					+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		}
		char[] randBuffer1 = new char[length];
		for (int i = 0; i < randBuffer1.length; i++) {
			randBuffer1[i] = numbersAndLetters1[randGen1.nextInt(71)];
		}
		return new String(randBuffer1);
	}

	public synchronized static String createToke(){
		return new java.math.BigInteger(165, new Random()).toString(36).toUpperCase();
	}
	
	public static void main(String[] args) {
		for(int i =0 ;i<4;i++){
			
			System.out.println(randomString(3));
		}
	}
	
}
