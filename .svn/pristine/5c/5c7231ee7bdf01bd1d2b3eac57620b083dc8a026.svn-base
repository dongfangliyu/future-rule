package com.zw.rule.jeval.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    private static final String[] DIGITS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "H", "i", "j", "k", "l", "m", "n", "~", "$", "@", "%", "*", "#", "&", "!"};

    public MD5() {
    }

    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if(bByte < 0) {
            iRet = bByte + 256;
        }

        int iD1 = iRet / 32;
        int iD2 = iRet % 32;
        return DIGITS[iD1] + DIGITS[iD2];
    }

    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + bByte);
        if(bByte < 0) {
            iRet = bByte + 256;
        }

        return String.valueOf(iRet);
    }

    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();

        for(int i = 0; i < bByte.length; ++i) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }

        return sBuffer.toString();
    }

    public static String GetMD5Code(String param) {
        String resultString = null;

        try {
            new String(param);
            MessageDigest ex = MessageDigest.getInstance("MD5");
            resultString = byteToString(ex.digest(param.getBytes()));
        } catch (NoSuchAlgorithmException var3) {
            var3.printStackTrace();
        }

        return resultString;
    }
}

