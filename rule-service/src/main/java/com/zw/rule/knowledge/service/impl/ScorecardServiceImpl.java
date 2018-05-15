package com.zw.rule.knowledge.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zw.rule.jeval.tools.StringUtil;
import com.zw.rule.knowledge.po.Scorecard;
import com.zw.rule.knowledge.po.ScorecardExcel;
import com.zw.rule.knowledge.po.ScorecardField;
import com.zw.rule.knowledge.po.ScorecardRuleContent;
import com.zw.rule.knowledge.service.ScorecardFieldService;
import com.zw.rule.knowledge.service.ScorecardRuleContentService;
import com.zw.rule.knowledge.service.ScorecardService;
import com.zw.rule.mapper.knowledge.ScorecardMapper;
import com.zw.rule.mapper.knowledge.ScorecardRuleContentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangtao on 2017/5/11 0011.
 * 评分卡
 */
@Service
public class ScorecardServiceImpl implements ScorecardService {
    @Resource
    private ScorecardFieldService sFieldService;
    @Resource
    private ScorecardRuleContentService sContentService;
    @Resource
    private ScorecardMapper scorecardMapper;
    @Resource
    private ScorecardRuleContentMapper scorecardContentMapper;
    public ScorecardServiceImpl() {
    }

    /**
     *获取评分卡列表
     * @param paramMap
     * engineId 引擎id 可选
     * parentIds 父节点 可选
     * status    状态    可选
     * organId    组织id  可选
     * type        类型 可选
     * @return
     * 测试通过
     */
    public List<Scorecard> queryScorecardList(Map<String, Object> paramMap) {
        return scorecardMapper.getScorecardList(paramMap);
    }

    /**
     * 向数据库中插入评分集数据
     * 测试通过
     * @param param
     * @return
     */
    public boolean addScorecard(Map<String, Object> param) {
        Scorecard scorecard = new Scorecard();
        scorecard.setCode("SC_" + param.get("code"));
        scorecard.setName((String)param.get("name"));
        scorecard.setDesc((String)param.get("desc"));
        scorecard.setParentId(Long.valueOf(Long.parseLong(param.get("parentId").toString())));
        scorecard.setPd((String)param.get("pd"));
        scorecard.setScore((String)param.get("score"));
        scorecard.setOdds((String)param.get("odds"));
        scorecard.setCreator(Long.valueOf(Long.parseLong(param.get("userId").toString())));
        scorecard.setUserId(Long.valueOf(Long.parseLong(param.get("userId").toString())));
        scorecard.setStatus(Integer.valueOf(1));
        scorecard.setType(Integer.valueOf(Integer.parseInt(param.get("type").toString())));
        scorecard.setOrgId(Long.valueOf(Long.parseLong(param.get("organId").toString())));
        String engineId = (String)param.get("engineId");
        if(!StringUtil.isBlank(engineId)) {
            scorecard.setEngineId(Long.valueOf(Long.parseLong(param.get("engineId").toString())));
        }
        int num = scorecardMapper.insertSelective(scorecard);
        String content = (String)param.get("content");
        if(!StringUtil.isBlank(content)) {
            addScorecardRuleContent(content, scorecard);
        }
        return num > 0;
    }

    private void addScorecardRuleContent(String content, Scorecard scorecard) {
        ArrayList ruleContentList = new ArrayList();
        List jsonArray = JSONObject.parseArray(content, JSONObject.class);
        Iterator json  = jsonArray.iterator();
        while(json.hasNext()) {
            JSONObject jsonObject = (JSONObject)json.next();
            JSONObject output = JSONObject.parseObject(jsonObject.getString("output"));
            ScorecardRuleContent s = new ScorecardRuleContent();
            s.setFieldId(output.getLong("field_id"));
            s.setFieldValue(jsonObject.toJSONString());
            s.setScorecardId(scorecard.getId());
            ruleContentList.add(s);
        }
        scorecardContentMapper.insertRuleContent(ruleContentList);
    }

    /**
     * 更新评分卡
     * @param param
     * @return
     */
    public boolean updateScorecard(Map<String, Object> param) {
        Scorecard scorecard = new Scorecard();
        scorecard.setId(Long.valueOf(Long.parseLong(param.get("id").toString())));
        scorecard.setCode("SC_" + param.get("code"));
        scorecard.setName((String)param.get("name"));
        scorecard.setDesc((String)param.get("desc"));
//        scorecard.setParentId(Long.valueOf(Long.parseLong(param.get("parentId").toString())));
        scorecard.setPd((String)param.get("pd"));
        scorecard.setScore((String)param.get("score"));
        scorecard.setOdds((String)param.get("odds"));
        scorecard.setCreator(Long.valueOf(Long.parseLong(param.get("userId").toString())));
        scorecard.setUserId(Long.valueOf(Long.parseLong(param.get("userId").toString())));
        scorecard.setStatus(Integer.valueOf(1));
        scorecard.setType(Integer.valueOf(Integer.parseInt(param.get("type").toString())));
        scorecard.setOrgId(Long.valueOf(Long.parseLong(param.get("organId").toString())));
        scorecardMapper.updateByPrimaryKeySelective(scorecard);
        Scorecard oldScorecard = findById(scorecard.getId());
        String content = (String)param.get("content");
        if(!StringUtil.isBlank(content)) {
            upadteRuleContent(content, oldScorecard);
        }
//        scorecardMapper.updateByPrimaryKeySelective(scorecard);
        return true;
    }

    private void upadteRuleContent(String content, Scorecard oldScorecard) {
        if(oldScorecard.getsRuleContentList().size() > 0) {
            scorecardContentMapper.deleteRuleContent(oldScorecard.getsRuleContentList());
        }
        ArrayList ruleContentList = new ArrayList();
        List jsonArray = JSONObject.parseArray(content, JSONObject.class);
        Iterator iterator = jsonArray.iterator();
        while(iterator.hasNext()) {
            JSONObject jsonObject = (JSONObject)iterator.next();
            JSONObject output = JSONObject.parseObject(jsonObject.getString("output"));
            ScorecardRuleContent s = new ScorecardRuleContent();
            s.setFieldId(output.getLong("field_id"));
            s.setFieldValue(jsonObject.toJSONString());
            s.setScorecardId(oldScorecard.getId());
            ruleContentList.add(s);
        }
        scorecardContentMapper.insertRuleContent(ruleContentList);
    }

    /**
     * 更新评分卡状态
     * @param paramMap
     * @return
     */
    public boolean updateScorecardStatus(Map<String, Object> paramMap) {
        int num = scorecardMapper.updateScorecardStatus(paramMap);
        return num > 0;
    }
    public Scorecard findById(Long id) {
        return (Scorecard)scorecardMapper.selectByPrimaryKey(id);
    }

    public int batchAddScorecard(Map<String, Object> param, List<Scorecard> slist) {
        int num = 0;
        Long parentId = Long.valueOf(Long.parseLong("" + param.get("newParentId")));
        Long organId = Long.valueOf(Long.parseLong("" + param.get("newOrganId")));
        Integer type = Integer.valueOf(Integer.parseInt("" + param.get("newType")));
        for(Iterator pageInfo = slist.iterator(); pageInfo.hasNext(); ++num) {
            Scorecard s = (Scorecard)pageInfo.next();
            s.setParentId(parentId);
            s.setOrgId(organId);
            s.setType(type);
            scorecardMapper.insertSelective(s);
            List sFieldList = s.getsFieldList();
            List sContentList = s.getsRuleContentList();
            Iterator pageInfo1 = sFieldList.iterator();
            while(pageInfo1.hasNext()) {
                ScorecardField ScorecardContentVo = (ScorecardField)pageInfo1.next();
                ScorecardContentVo.setScorecardId(s.getId());
            }
            if(sFieldList.size() > 0) {
                sFieldService.addField(sFieldList);
            }
            pageInfo1 = sContentList.iterator();
            while(pageInfo1.hasNext()) {
                ScorecardRuleContent src = (ScorecardRuleContent)pageInfo1.next();
                src.setScorecardId(s.getId());
            }
            if(sContentList.size() > 0) {
                sContentService.insertRuleContent(sContentList);
            }
        }
        return num;
    }

    public List<ScorecardExcel> queryRuleExcelData(Map<String, Object> param) {
        List slist = queryScorecardList(param);
        ArrayList list = new ArrayList();
        Iterator pageInfo = slist.iterator();
        while(pageInfo.hasNext()) {
            Scorecard scorecardVo = (Scorecard)pageInfo.next();
            ScorecardExcel sExcelVo = new ScorecardExcel();
            sExcelVo.setName(scorecardVo.getName());
            sExcelVo.setCode(scorecardVo.getCode());
            sExcelVo.setDescription(scorecardVo.getDesc());
            sExcelVo.setVersion(scorecardVo.getVersion());
            sExcelVo.setFieldContent(getFieldContents(scorecardVo));
            sExcelVo.setContent(getRuleContents(scorecardVo));
            list.add(sExcelVo);
        }
        return list;
    }

    public String getFieldContents(Scorecard scorecardVo) {
        StringBuffer sb = new StringBuffer();
        List sFieldList = scorecardVo.getsFieldList();
        Iterator pageInfo = sFieldList.iterator();
        while(pageInfo.hasNext()) {
            ScorecardField scorecardFieldVo = (ScorecardField)pageInfo.next();
            sb.append(scorecardFieldVo.getFieldId());
            sb.append("\n");
        }
        return sb.toString();
    }

    public String getRuleContents(Scorecard scorecardVo) {
        StringBuffer sb = new StringBuffer();
        List sRuleContentList = scorecardVo.getsRuleContentList();
        Iterator pageInfo = sRuleContentList.iterator();

        while(pageInfo.hasNext()) {
            ScorecardRuleContent sRuleContentVo = (ScorecardRuleContent)pageInfo.next();
            sb.append(sRuleContentVo.getCnName());
            sb.append("=");
            sb.append(sRuleContentVo.getFieldValue());
            sb.append("\n");
        }

        return sb.toString();
    }
    public int countOnlyScName(Map<String, Object> param) {
        return scorecardMapper.countOnlyScName(param);
    }
    public int countOnlyScCode(Map<String, Object> param) {
        return scorecardMapper.countOnlyScCode(param);
    }
}
