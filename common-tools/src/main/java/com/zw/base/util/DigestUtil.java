package com.zw.base.util;

import com.google.common.base.Strings;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class DigestUtil {
    private static final String ALGORITHM_MD5 = "MD5";
    private static final String ALGORITHM_SHA = "SHA";
    private static final String ALGORITHM_SHA256 = "SHA-256";
    private static final String ALGORITHM_SHA384 = "SHA-384";
    private static final String ALGORITHM_SHA512 = "SHA-512";

    private static final Map<String, DigestUtil> instanceMap = new ConcurrentHashMap<String, DigestUtil>();

    private final String algorithm;

    private DigestUtil(String algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * 消息摘要 5 (MD5) 哈希算法. MD5 算法的哈希值大小为 128 位
     */
    public static DigestUtil md5() {
        return cache(ALGORITHM_MD5);
    }

    /**
     * 安全哈希算法 (SHA1). SHA1 算法的哈希值大小为 160 位
     */
    public static DigestUtil sha() {
        return cache(ALGORITHM_SHA);
    }

    /**
     * 256 位哈希值的安全哈希算法 (SHA256). SHA256 算法的哈希值大小为 256 位
     */
    public static DigestUtil sha256() {
        return cache(ALGORITHM_SHA256);
    }

    /**
     * 384 位哈希值的安全哈希算法 (SHA384). SHA384 算法的哈希值大小为 384 位
     */
    public static DigestUtil sha384() {
        return cache(ALGORITHM_SHA384);
    }

    /**
     * 512 位哈希值的安全哈希算法 (SHA512). SHA384 算法的哈希值大小为 512 位
     */
    public static DigestUtil sha512() {
        return cache(ALGORITHM_SHA512);
    }

    private static DigestUtil cache(String alg) {
        DigestUtil o = instanceMap.get(alg);
        if (o == null) {
            instanceMap.put(alg, o = new DigestUtil(alg));
        }
        return o;
    }

    /**
     * 获得数据的摘要
     */
    public String digest(String s) {
        if (Strings.isNullOrEmpty(s)) {
            return s;
        }
        try {
            byte[] in = s.getBytes("UTF-8");
            byte[] out = compute(in);
            char[] array = HexUtil.encodeHex(out);
            return new String(array);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("digest failed!", e);
        }
    }

    public byte[] compute(byte[] buffer) {
        if (buffer == null) {
            throw new IllegalArgumentException("Argument buffer must not be null.");
        }
        MessageDigest md = getDigest();
        md.reset();
        md.update(buffer);
        return md.digest();
    }

    /**
     * 计算指定字节数组的指定区域的哈希值
     *
     * @param buffer 要计算其哈希代码的输入
     * @param offset 字节数组中的偏移量，从该位置开始使用数据
     * @param count  数组中用作数据的字节数
     * @return 计算所得的哈希代码
     */
    public byte[] compute(byte[] buffer, int offset, int count) {
        if (buffer == null) {
            throw new IllegalArgumentException("Argument buffer must not be null.");
        }
        if (offset >= buffer.length) {
            throw new IllegalArgumentException("Argument offset is out of range");
        }
        MessageDigest md = getDigest();
        md.reset();
        md.update(buffer, offset, count);
        return md.digest();
    }

    /**
     * 计算指定 ByteBuffer 对象的哈希值
     *
     * @param input 要计算其哈希代码的输入
     * @return 计算所得的哈希代码
     */
    public byte[] compute(ByteBuffer input) {
        if (input == null) {
            throw new IllegalArgumentException("Argument input must not be null.");
        }
        MessageDigest md = getDigest();
        md.reset();
        md.update(input);
        return md.digest();
    }

    private MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            throw new RuntimeException("invalid digest algorithm!", e);
        }
    }
}
