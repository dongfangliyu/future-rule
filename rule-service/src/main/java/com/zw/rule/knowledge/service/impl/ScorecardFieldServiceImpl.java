package com.zw.rule.knowledge.service.impl;

import com.zw.rule.knowledge.po.ScorecardField;
import com.zw.rule.knowledge.service.ScorecardFieldService;
import com.zw.rule.mapper.knowledge.ScorecardFieldMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangtao on 2017/5/11 0011.
 */
@Service
public class ScorecardFieldServiceImpl implements ScorecardFieldService {
    @Resource
    public ScorecardFieldMapper scorecardFieldMapper;

    /**
     * 测试通过
     * 根据记分卡Id查询数据
     * @param scorecardId
     * @return
     */
    public List<ScorecardField> queryFieldList(Long scorecardId) {
        return scorecardFieldMapper.queryFieldList(scorecardId);
    }

    /**
     * 测试通过
     * 根据Id获取信息
     * @param id
     * @return
     */
    public ScorecardField findById(Long id) {
        return (ScorecardField)scorecardFieldMapper.selectByPrimaryKey(id);
    }

    /**
     * 测试通过
     * 新增评分卡
     * @param sList
     * @return
     */
    public boolean addField(List<ScorecardField> sList) {
        scorecardFieldMapper.addField(sList);
        return true;
    }

    /**
     * 测试通过
     * 更新评分卡
     * @param slist
     * @return
     */
    public boolean updateField(List<ScorecardField> slist) {
        scorecardFieldMapper.updateField(slist);
        return true;
    }

    /**
     * 测试通过
     * 删除评分卡
     * @param slist
     * @return
     */
    public boolean deleteField(List<ScorecardField> slist) {
        scorecardFieldMapper.deleteField(slist);
        return true;
    }
}
