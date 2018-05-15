package com.zw.rule.mybatis.page;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {

    private PageUtil() {

    }

    public static int getFirstNumber(int currentPage, int pageSize) {
        if (pageSize <= 0) {
            throw new IllegalArgumentException("pageSize must greater than zero");
        }
        return (currentPage - 1) * pageSize;
    }

    public static int getLastNumber(int currentPage, int pageSize, int totalCount) {
        int last = currentPage * pageSize;
        if (last > totalCount)
            return totalCount - 1;
        return last - 1;
    }

    public static int getTailPage(int pageSize, int totalCount) {
        int result = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        if (result <= 1) {
            return 1;
        }
        return result;
    }

    public static int getPageNumber(int pageSize, int currentPage, int totalCount) {
        if (currentPage <= 1) {
            return 1;
        }
        if (Integer.MAX_VALUE == currentPage && currentPage > getTailPage(pageSize, totalCount)) {
            return getTailPage(pageSize, totalCount);
        }
        return currentPage;
    }

    public static List<Integer> getPageNumbers(int currentPage, int totalPage, int count) {
        int avg = count / 2;
        int startPage = currentPage - avg;
        if (startPage <= 0) {
            startPage = 1;
        }
        int endPage = startPage + count - 1;
        if (endPage > totalPage) {
            endPage = totalPage;
        }
        if (endPage - startPage < count) {
            startPage = endPage - count;
            if (startPage <= 0) {
                startPage = 1;
            }
        }
        List<Integer> result = new ArrayList<>();
        for (int i = startPage; i <= endPage; i++) {
            result.add(i);
        }
        return result;
    }
}
