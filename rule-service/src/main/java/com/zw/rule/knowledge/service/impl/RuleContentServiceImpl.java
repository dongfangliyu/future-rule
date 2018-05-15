package com.zw.rule.knowledge.service.impl;

import com.zw.rule.knowledge.service.RuleContentService;
import com.zw.rule.knowledge.po.RuleContent;
import com.zw.rule.mapper.knowledge.RuleContentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *
 *Filename：
 *
 *Description：
 *
 *Author:
 *		 李开艳
 *Finished：
 *		2017/5/11
 ********************************************************/
@Service
public class RuleContentServiceImpl implements RuleContentService {

    @Resource
    private RuleContentMapper ruleContentMapper;

    /**
     *  对t_rule_content和t_field的关联 查询
     * @param ruleId  long 规则id
     * @return
     * 方法通过单元测试
     */
    public List<RuleContent> getRuleContentList(Long ruleId) {
        return ruleContentMapper.getRuleContentList(ruleId);
    }

    /**
     * 对t_rule_content 查询
     * @param id  long  主键id
     * @return
     * 方法通过单元测试（bug：sql错误）
     */
    public RuleContent findById(Long id) {
        return (RuleContent)ruleContentMapper.selectByPrimaryKey(id);
    }

    /**
     * 对t_rule_content 批量添加数据
     * @param rlist
     * list List<RuleContent>  RuleContent集合
     * @return
     * 方法通过单元测试
     */
    public boolean insertRuleContent(List<RuleContent> rlist) {
        ruleContentMapper.insertRuleContent(rlist);
        return true;
    }

    /**
     * 对t_rule_content 批量修改数据
     * @param rlist
     * list List<RuleContent>  RuleContent集合
     * @return
     * 通过(解决办法，在连接数据库的url中加上allowMultiQueries=true)
     */
    public boolean updateRuleContent(List<RuleContent> rlist) {
        ruleContentMapper.updateRuleContent(rlist);
        return true;
    }

    /**
     * 对t_rule_content 批量删除数据
     * @param rlist
     * list List<RuleContent>  RuleContent集合
     * @return
     * 通过(解决办法，在连接数据库的url中加上allowMultiQueries=true)
     */
    public boolean deleteRuleContent(List<RuleContent> rlist) {
        ruleContentMapper.deleteRuleContent(rlist);
        return true;
    }
}
