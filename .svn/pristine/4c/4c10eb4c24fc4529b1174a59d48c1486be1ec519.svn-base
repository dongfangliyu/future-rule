package com.zw.rule.knowledge.service.impl;

import com.zw.rule.knowledge.po.ScorecardRuleContent;
import com.zw.rule.knowledge.service.ScorecardRuleContentService;
import com.zw.rule.mapper.knowledge.ScorecardRuleContentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangtao on 2017/5/11 0011.
 */
@Service
public class ScorecardRuleContentServiceImpl implements ScorecardRuleContentService {
    @Resource
    private ScorecardRuleContentMapper scorecardContentMapper;
    public ScorecardRuleContentServiceImpl() {
    }

    /**
     * 测试通过
     * 根据评分卡Id 获取规则内容集合
     * @param scorecardId String 必须
     * @return
     */
    public List<ScorecardRuleContent> getRuleContentList(Long scorecardId) {
        return scorecardContentMapper.getRuleContentList(scorecardId);
    }

    /**
     * 测试通过
     * 根据Id查询信息
     * @param id Long 必须
     * @return
     */
    public ScorecardRuleContent findById(Long id) {
        return (ScorecardRuleContent)scorecardContentMapper.selectByPrimaryKey(id);
    }
    /**
     * 测试通过
     * 向数据插入规则内容
     * @param sList
     * @return
     */
    public boolean insertRuleContent(List<ScorecardRuleContent> sList) {
        scorecardContentMapper.insertRuleContent(sList);
        return true;
    }
    /**
     * 测试通过
     * 更新数据库中规则集的数据
     * @param sList
     * fieldId  string 可需 字段ID
     * fieldValue string 可需 字段的值
     * id Long 必须
     * @return
     */
    public boolean updateRuleContent(List<ScorecardRuleContent> sList) {
        scorecardContentMapper.updateRuleContent(sList);
        return true;
    }
    /**
     * 测试通过
     *删除数据库中规则集的数据
     * @param sList
     * id Long 必须
     * @return
     */
    public boolean deleteRuleContent(List<ScorecardRuleContent> sList) {
        scorecardContentMapper.deleteRuleContent(sList);
        return true;
    }

}
