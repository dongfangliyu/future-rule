package com.zw.base.util;

/**
 * Created by Administrator on 2017/5/5.
 */
public enum ValueType {
    Unknown(0),
    Num(1),
    Char(2),
    Enum(3),
    Dec(4);

    public final int value;

    private ValueType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
