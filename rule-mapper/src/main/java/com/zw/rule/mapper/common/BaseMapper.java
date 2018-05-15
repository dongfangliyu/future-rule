package com.zw.rule.mapper.common;

/**
 * Created by Administrator on 2017/5/4.
 */
import java.util.List;

public interface BaseMapper<T> {
    int deleteByExample(T o);

    int deleteByPrimaryKey(Long value);

    int insertSelective(T o);

    int updateByPrimaryKeySelective(T o);

    int countByExample(T o);

    List<T> selectByExample(T o);

    T selectByPrimaryKey(Long value);
}