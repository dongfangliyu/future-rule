package com.zw.rule.demo.service.impl;

import com.zw.rule.demo.pojo.CustomDb;
import com.zw.rule.demo.service.CustomDbService;
import com.zw.rule.mapper.demo.CustomDbMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */
@Service
public class CustomDbServiceImpl implements CustomDbService {

    @Resource
    private CustomDbMapper customDbMapper;

    @Override
    public List<CustomDb> getList(Long engineId) {
        return customDbMapper.getList(engineId);
    }

    @Override
    public void add(CustomDb customDb) {
        customDbMapper.add(customDb);
    }

    @Override
    public void delete(Long id) {
        customDbMapper.delete(id);
    }

    @Override
    public CustomDb detail(Long id) {
        return customDbMapper.detail(id);
    }

    @Override
    public void update(CustomDb customDb) {
        customDbMapper.update(customDb);
    }

    @Override
    public int isCustom(Long verId) {
        return customDbMapper.isCustom(verId);
    }
}
