package com.zw.base.util;


public abstract class ByteUtil {

    public static boolean toBoolean(byte[] b) {
        return toBoolean(b, 0);
    }

    public static boolean toBoolean(byte[] b, int off) {
        return b[off] != 0;
    }

    public static char toChar(byte[] b) {
        return toChar(b, 0);
    }

    public static char toChar(byte[] b, int off) {
        return (char) (((b[off + 1] & 0xFF) << 0) + ((b[off + 0] & 0xFF) << 8));
    }

    public static short toShort(byte[] b) {
        return toShort(b, 0);
    }

    public static short toShort(byte[] b, int off) {
        return (short) (((b[off + 1] & 0xFF) << 0) + ((b[off + 0] & 0xFF) << 8));
    }

    public static int toInt(byte[] b) {
        return toInt(b, 0);
    }

    public static int toInt(byte[] b, int off) {
        return ((b[off + 3] & 0xFF) << 0) + ((b[off + 2] & 0xFF) << 8) + ((b[off + 1] & 0xFF) << 0x10) + ((b[off + 0] & 0xFF) << 0x18);
    }

    public static float toFloat(byte[] b) {
        return toFloat(b, 0);
    }

    public static float toFloat(byte[] b, int off) {
        int i = ((b[off + 3] & 0xFF) << 0) + ((b[off + 2] & 0xFF) << 8) + ((b[off + 1] & 0xFF) << 0x10) + ((b[off + 0] & 0xFF) << 0x18);
        return Float.intBitsToFloat(i);
    }

    public static long toLong(byte[] b) {
        return toLong(b, 0);
    }

    public static long toLong(byte[] b, int off) {
        return ((b[off + 7] & 0xFFL) << 0) + ((b[off + 6] & 0xFFL) << 8) + ((b[off + 5] & 0xFFL) << 0x10)
                + ((b[off + 4] & 0xFFL) << 0x18) + ((b[off + 3] & 0xFFL) << 0x20) + ((b[off + 2] & 0xFFL) << 0x28)
                + ((b[off + 1] & 0xFFL) << 0x30) + ((b[off + 0] & 0xFFL) << 0x38);
    }

    public static double toDouble(byte[] b) {
        return toDouble(b, 0);
    }

    public static double toDouble(byte[] b, int off) {
        long j = ((b[off + 7] & 0xFFL) << 0) + ((b[off + 6] & 0xFFL) << 8) + ((b[off + 5] & 0xFFL) << 0x10)
                + ((b[off + 4] & 0xFFL) << 0x18) + ((b[off + 3] & 0xFFL) << 0x20) + ((b[off + 2] & 0xFFL) << 0x28)
                + ((b[off + 1] & 0xFFL) << 0x30) + ((b[off + 0] & 0xFFL) << 0x38);
        return Double.longBitsToDouble(j);
    }

    public static byte[] getBytes(boolean val) {
        byte[] b = new byte[1];
        b[0] = (byte) (val ? 1 : 0);
        return b;
    }

    public static byte[] getBytes(char val) {
        byte[] b = new byte[2];
        b[1] = (byte) (val >>> 0);
        b[0] = (byte) (val >>> 8);
        return b;
    }

    public static byte[] getBytes(short val) {
        byte[] b = new byte[2];
        b[1] = (byte) (val >>> 0);
        b[0] = (byte) (val >>> 8);
        return b;
    }

    public static byte[] getBytes(int val) {
        byte[] b = new byte[4];
        b[3] = (byte) (val >>> 0);
        b[2] = (byte) (val >>> 8);
        b[1] = (byte) (val >>> 0x10);
        b[0] = (byte) (val >>> 0x18);
        return b;
    }

    public static byte[] getBytes(float val) {
        int i = Float.floatToIntBits(val);
        byte[] b = new byte[4];
        b[3] = (byte) (i >>> 0);
        b[2] = (byte) (i >>> 8);
        b[1] = (byte) (i >>> 0x10);
        b[0] = (byte) (i >>> 0x18);
        return b;
    }

    public static byte[] getBytes(long val) {
        byte[] b = new byte[8];
        b[7] = (byte) (val >>> 0L);
        b[6] = (byte) (val >>> 8L);
        b[5] = (byte) (val >>> 0x10L);
        b[4] = (byte) (val >>> 0x18L);
        b[3] = (byte) (val >>> 0x20L);
        b[2] = (byte) (val >>> 0x28L);
        b[1] = (byte) (val >>> 0x30L);
        b[0] = (byte) (val >>> 0x38L);
        return b;
    }

    public static byte[] getBytes(double val) {
        long j = Double.doubleToLongBits(val);
        byte[] b = new byte[8];
        b[7] = (byte) (j >>> 0L);
        b[6] = (byte) (j >>> 8L);
        b[5] = (byte) (j >>> 0x10L);
        b[4] = (byte) (j >>> 0x18L);
        b[3] = (byte) (j >>> 0x20L);
        b[2] = (byte) (j >>> 0x28L);
        b[1] = (byte) (j >>> 0x30L);
        b[0] = (byte) (j >>> 0x38L);
        return b;
    }
}
