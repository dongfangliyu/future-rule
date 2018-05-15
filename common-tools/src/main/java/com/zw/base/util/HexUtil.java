package com.zw.base.util;


public abstract class HexUtil {

    private static final char[] DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };


    public static byte[] decodeHexString(String hexString) {
        if (hexString == null) {
            throw new IllegalArgumentException("hexString must not be null!");
        }
        byte[] buffer;
        boolean flag = false;
        int num = 0;
        int length = hexString.length();
        if (((length % 2) != 0) && ((length % 3) != 2)) {
            throw new IllegalArgumentException("hexString length is invalid!");
        }
        if (((length >= 2) && (hexString.charAt(0) == '0')) && ((hexString.charAt(1) == 'x') || (hexString.charAt(
                1) == 'X'))) {
            length = hexString.length() - 2;
            num = 2;
        }
        if ((length >= 3) && (hexString.charAt(num + 2) == ' ')) {
            flag = true;
            buffer = new byte[(length / 3) + 1];
        } else {
            buffer = new byte[length / 2];
        }
        for (int i = 0; num < length; i++) {
            int num4 = toHexDigit(hexString.charAt(num));
            int num3 = toHexDigit(hexString.charAt(num + 1));
            buffer[i] = (byte) (num3 | (num4 << 4));
            if (flag) {
                num++;
            }
            num += 2;
        }
        return buffer;
    }


    private static int toHexDigit(char ch) {
        if ((ch <= '9') && (ch >= '0')) {
            return (ch - '0');
        }
        if ((ch >= 'a') && (ch <= 'f')) {
            return ((ch - 'a') + 10);
        }
        if ((ch < 'A') || (ch > 'F')) {
            throw new IllegalArgumentException("Illegal hexadecimal charcter + " + ch);
        }
        return ((ch - 'A') + 10);
    }

    /**
     * 对十六进制的字符数组解码，获得其值的 byte 数组
     */
    public static byte[] decodeHex(char[] data) {
        int len = data.length;

        if ((len & 0x01) != 0) {
            throw new IllegalArgumentException("Odd number of characters.");
        }
        byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toHexDigit(data[j]) << 4;
            j++;
            f = f | toHexDigit(data[j]);
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }

    public static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return out;
    }

    public static long parseLong(CharSequence s) {
        long out = 0;
        byte shifts = 0;
        char c;
        for (int i = 0; i < s.length() && shifts < 16; i++) {
            c = s.charAt(i);
            if ((c > 47) && (c < 58)) {
                ++shifts;
                out <<= 4;
                out |= c - 48;
            } else if ((c > 64) && (c < 71)) {
                ++shifts;
                out <<= 4;
                out |= c - 55;
            } else if ((c > 96) && (c < 103)) {
                ++shifts;
                out <<= 4;
                out |= c - 87;
            }
        }
        return out;
    }

    public static short parseShort(String s) {
        short out = 0;
        byte shifts = 0;
        char c;
        for (int i = 0; i < s.length() && shifts < 4; i++) {
            c = s.charAt(i);
            if ((c > 47) && (c < 58)) {
                ++shifts;
                out <<= 4;
                out |= c - 48;
            } else if ((c > 64) && (c < 71)) {
                ++shifts;
                out <<= 4;
                out |= c - 55;
            } else if ((c > 96) && (c < 103)) {
                ++shifts;
                out <<= 4;
                out |= c - 87;
            }
        }
        return out;
    }

}
