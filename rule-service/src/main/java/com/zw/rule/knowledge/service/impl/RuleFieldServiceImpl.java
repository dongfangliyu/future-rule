package com.zw.rule.knowledge.service.impl;

import com.zw.rule.knowledge.po.RuleField;
import com.zw.rule.knowledge.service.RuleFieldService;
import com.zw.rule.mapper.knowledge.RuleFieldMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangtao on 2017/5/11 0011.
 * 规则管理-规则集
 */
@Service
public class RuleFieldServiceImpl implements RuleFieldService {
    public RuleFieldServiceImpl() {
    }
    @Resource
    public RuleFieldMapper ruleFieldMapper;

    /**
     * 测试通过
     * 根据规划ID 查询规则集集合
     * @param ruleId long 必须
     * @return
     */
    public List<RuleField> getFieldList(Long ruleId) {
        return ruleFieldMapper.getFieldList(ruleId);
    }

    /**
     * 测试通过
     *根据Id查询信息
     * @param id long 必须
     * @return
     */
    public RuleField findById(Long id) {
        return (RuleField)ruleFieldMapper.selectByPrimaryKey(id);
    }

    /**
     * 测试通过
     * 新增规则
     * @param rlist
     * @return
     */
    public boolean insertField(List<RuleField> rlist) {
        ruleFieldMapper.insertField(rlist);
        return true;
    }

    /**
     * 测试通过
     * 更新规则
     * @param rlist
     * @return
     */
    public boolean updateField(List<RuleField> rlist) {
        ruleFieldMapper.updateField(rlist);
        return true;
    }
    /**
     *测试通过
     * 删除规则
     * @param rlist
     * @return
     */
    public boolean deleteField(List<RuleField> rlist) {
        ruleFieldMapper.deleteField(rlist);
        return true;
    }
}
