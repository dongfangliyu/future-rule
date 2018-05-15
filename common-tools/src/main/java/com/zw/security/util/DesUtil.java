package com.zw.security.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.SecureRandom;
 
public class DesUtil {
 
    private final static String DES = "DES";
 
    public static void main(String[] args) throws Exception {
        String data = "2y9rxVHJo6oSQNa0yIYQCJ4ObkQtI8CP6bxPCS6zhjJUGMJoQ800SqnYlqueVA8Qs7Wm0sZZzJTp%5CncEuenXylTBjHDjzE5L2Xb7WcYyD4bxzq2RVjCYc92HwzaFcq0grHdjICIdnp0owkutnfqf%2B71USt%5CnK4kicWNH42skKO1iJ9pcVinkAYBR40LzFv0974p%2ByyvVavHnCTN9rMy6RycIAKzDleGI8cuQn%2Fqy%5CnPMiOnWjYPOxI%2FzOZPpqCyfZo9ojwuqD2TloQI54ewVIqI9UaDQ9B90QV8Q0OIdSNMQ48h5alJtNO%5Cn1zAjUK5sz91r4UX0EQReWTdqXw5SkUAW3kdlAekjQKVcDm5PWOTWIsMXdfhPq9Byw8iIn8%2FONQzd%5CnaOWqYejnlmQtBM3danXua9Q53F7IjA%2BScd3HyCsc3pAVkBGHvvMj0fmpOmIP8aRAKbdswJgJRKfp%5CnqmM0p%2FkifEHk4T0dkbA1CaBO";
        String key = "@!123456";
        System.out.println(decrypt(data, key));
//        System.err.println(encrypt(data, key));
//        System.err.println(decrypt(encrypt(data, key), key));
 
    }
     
    /**
     * Description 根据键值进行加密
     * @param data 
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes("gb2312"), key.getBytes("gb2312"));
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }
 
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws IOException,
            Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf,key.getBytes("gb2312"));
        return new String(bt, "gb2312");
    }
 
    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
     
     
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
}
