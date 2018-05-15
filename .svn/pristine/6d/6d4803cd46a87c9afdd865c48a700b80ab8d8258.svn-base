package com.zw.security.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.google.common.base.Throwables;
import com.zw.security.util.CryptUtils;

public class CryptUtils {

	
	private static byte[] hmac(String data, String secret) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
		Mac mac = Mac.getInstance("HmacSHA256");
		// get the bytes of the hmac key and data string
		byte[] secretByte = secret.getBytes("UTF-8");
		byte[] dataBytes = data.getBytes("UTF-8");
		SecretKey secretKey = new SecretKeySpec(secretByte, "HMACSHA256");
		mac.init(secretKey);
		return mac.doFinal(dataBytes);
	}

	private static final String toHex(byte hash[]) {
		if (hash == null) {
			return null;
		}
		StringBuffer buf = new StringBuffer(hash.length * 2);
		int i;

		for (i = 0; i < hash.length; i++) {
			if ((hash[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString(hash[i] & 0xff, 16));
		}
		return buf.toString();
	}

	/**
	 * 本系统加密方式
	 * @param data
	 * @param key
	 * @return
	 */
	public static String hash(String data, String key) {
		try {
			return new String(toHex(hmac(data, key)).getBytes("UTF-8"), "UTF-8");
		} catch (Exception e) {
			//logger.error("not supported charset...{}", e);
			return data;
		}
	}
	
	public static byte[] MD5(byte[] msg) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(msg);
		} catch (NoSuchAlgorithmException e) {
			//logger.error("MD5 NoSuchAlgorithmException caught!");
			Throwables.propagate(e);
		}
		return messageDigest.digest();

	}

	/**
	 * 这是pm系统用户密码加密方式
	 * @param msg
	 * @return
	 */
	public static String MD5STR(String msg) {
		byte[] b = MD5(msg.getBytes());
		return new String(Base64.encodeBase64(b));
	}

}
