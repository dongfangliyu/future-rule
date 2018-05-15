package com.zw.rule.demo.service;

import com.zw.rule.demo.pojo.CustomDb;

import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */
public interface CustomDbService {
    List<CustomDb> getList(Long engineId);

    void add(CustomDb customDb);

    void delete(Long id);

    CustomDb detail(Long id);

    void update(CustomDb customDb);

    int isCustom(Long verId);
}
