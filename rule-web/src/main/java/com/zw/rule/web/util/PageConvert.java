package com.zw.rule.web.util;

/**
 * Created by Administrator on 2017/5/11.
 */
public class PageConvert {
    //通过起始位置和显示条数获取当前页
    public static int convert(int pageIndex, int pageSize){
        int pageNo = pageIndex/pageSize + 1;
        return pageNo;
    }
}
