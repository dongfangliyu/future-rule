package com.zw.rule.knowledge.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zw.rule.knowledge.po.*;
import com.zw.rule.knowledge.service.RuleContentService;
import com.zw.rule.knowledge.service.RuleFieldService;
import com.zw.rule.knowledge.service.RuleService;
import com.zw.rule.mapper.knowledge.EngineRuleRelMapper;
import com.zw.rule.mapper.knowledge.RuleContentMapper;
import com.zw.rule.mapper.knowledge.RuleFieldMapper;
import com.zw.rule.mapper.knowledge.RuleMapper;
import com.zw.rule.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
public class RuleServiceImpl implements RuleService {

    @Resource
    private RuleFieldService ruleFieldService;
    @Resource
    private RuleContentService ruleContentService;

    @Resource
    private RuleMapper ruleMapper;

    @Resource
    private RuleFieldMapper ruleFieldMapper;

    @Resource
    private RuleContentMapper ruleContentMapper;
    @Resource
    private EngineRuleRelMapper engineRuleRelMapper;

    /**
     *
     * @param paramMap 包含以下参数
     *    status       |可选 |状态
     *    parentIds    |可选|父节点
     *    type         |必需|类型
     *    engineId     |可选|引擎id
     *    organId      |可选|组织id
     * @return
     * 测试通过
     */
    public List<Rule> getRuleList(Map<String, Object> paramMap) {
        return ruleMapper.getRuleList(paramMap);
    }

    /**
     *
     * @param param 包含以下参数
     *测试通过
     * @return
     */
    public boolean insertRule(Map<String, Object> param) {
        Rule rule = (Rule)param.get("rule");
        rule.setCreator(Long.valueOf(Long.parseLong(param.get("userId").toString())));
        rule.setUserId(Long.valueOf(Long.parseLong(param.get("userId").toString())));
        rule.setStatus(Integer.valueOf(1));
        rule.setType(Integer.valueOf(Integer.parseInt(param.get("type").toString())));
        rule.setOrgId(Long.valueOf(Long.parseLong(param.get("orgId").toString())));
        String engineId = (String)param.get("engineId");
        if(!StringUtil.isBlank(engineId)) {
            rule.setEngineId(Long.valueOf(Long.parseLong(param.get("engineId").toString())));
        }

        ruleMapper.insertSelective(rule);
        String fieldContent = (String)param.get("fieldContent");
        if(!StringUtil.isBlank(fieldContent)) {
            addRuleField(fieldContent, rule);
        }

        String content = (String)param.get("outcontent");
        if(!StringUtil.isBlank(content)) {
            addRuleContent(content, rule);
        }

        return true;
    }

    private void addRuleField(String fieldContent, Rule rule) {
        new ArrayList();
        List ruleFieldList = JSONObject.parseArray(fieldContent, RuleField.class);
        Iterator pageInfo = ruleFieldList.iterator();

        while(pageInfo.hasNext()) {
            RuleField ruleField = (RuleField)pageInfo.next();
            ruleField.setRuleId(rule.getId());
        }

        ruleFieldMapper.insertField(ruleFieldList);
    }

    private void addRuleContent(String content, Rule rule) {
        new ArrayList();
        List ruleContentList = JSONObject.parseArray(content, RuleContent.class);
        Iterator pageInfo = ruleContentList.iterator();

        while(pageInfo.hasNext()) {
            RuleContent ruleContent = (RuleContent)pageInfo.next();
            ruleContent.setRuleId(rule.getId());
        }

        ruleContentMapper.insertRuleContent(ruleContentList);
    }

    /**
     *
     * @param param 包含以下参数
     *              Rule实体类
     *              id             必需   规则id
     *              name           可选   规则名称
     *              code           可选   规则代码
     *              content        可选   规则内容
     *              description     可选   规则描述
     *              priority        可选
     *              status          可选   规则状态
     *              type            可选   类型
     *              isNon           可选
     *              ruleType        可选   规则类型
     *              ruleAudit       可选
     *              score           可选
     *              lastLogical     可选
     *
     * @return
     * 测试通过
     */
    public boolean updateRule(Map<String, Object> param) {
//        User user = ShiroSessionUtils.getLoginAccount();
        Rule rule = (Rule)param.get("rule");
//        rule.setUserId(Long.valueOf(Long.parseLong("" + user.getUserId())));
        ruleMapper.updateByPrimaryKeySelective(rule);
        Rule oldRule = findById(rule.getId());
        String fieldContent = (String)param.get("fieldContent");
        if(!StringUtil.isBlank(fieldContent)) {
            upadteRuleField(fieldContent, oldRule);
        }

        String content = (String)param.get("outcontent");
        if(!StringUtil.isBlank(content)) {
            upadteRuleContent(content, oldRule);
        }

        ruleMapper.updateByPrimaryKeySelective(rule);
        return true;
    }

    private void upadteRuleField(String fieldContent, Rule oldRule) {
        new ArrayList();
        List ruleFieldList = JSONObject.parseArray(fieldContent, RuleField.class);
        if(ruleFieldList != null && ruleFieldList.size() > 0) {
            ArrayList newAddFieldList = new ArrayList();
            ArrayList oldUpdateFieldList = new ArrayList();
            ArrayList oldDeleteFieldList = new ArrayList();
            Iterator pageInfo = ruleFieldList.iterator();

            RuleField oldRuleField;
            while(pageInfo.hasNext()) {
                oldRuleField = (RuleField)pageInfo.next();
                if(oldRuleField.getId() == null) {
                    oldRuleField.setRuleId(oldRule.getId());
                    newAddFieldList.add(oldRuleField);
                } else {
                    oldUpdateFieldList.add(oldRuleField);
                }
            }

            pageInfo = oldRule.getRuleFieldList().iterator();

            while(pageInfo.hasNext()) {
                oldRuleField = (RuleField)pageInfo.next();
                int num = 0;
                Iterator pageInfo1 = ruleFieldList.iterator();

                while(pageInfo1.hasNext()) {
                    RuleField ruleField = (RuleField)pageInfo1.next();
                    if(ruleField.getId() != null && oldRuleField.getId().longValue() == ruleField.getId().longValue()) {
                        ++num;
                        break;
                    }
                }

                if(num == 0) {
                    oldDeleteFieldList.add(oldRuleField);
                }
            }

            if(newAddFieldList.size() > 0) {
                ruleFieldMapper.insertField(newAddFieldList);
            }

            if(oldUpdateFieldList.size() > 0) {
                ruleFieldMapper.updateField(oldUpdateFieldList);
            }

            if(oldDeleteFieldList.size() > 0) {
                ruleFieldMapper.deleteField(oldDeleteFieldList);
            }

        }
    }

    private void upadteRuleContent(String content, Rule oldRule) {
        new ArrayList();
        List ruleContentList = JSONObject.parseArray(content, RuleContent.class);
        if(ruleContentList != null && ruleContentList.size() > 0) {
            ArrayList newAddContentList = new ArrayList();
            ArrayList oldUpdateContentList = new ArrayList();
            ArrayList oldDeleteContentList = new ArrayList();
            Iterator pageInfo = ruleContentList.iterator();

            RuleContent oldRuleContent;
            while(pageInfo.hasNext()) {
                oldRuleContent = (RuleContent)pageInfo.next();
                if(oldRuleContent.getId() == null) {
                    oldRuleContent.setRuleId(oldRule.getId());
                    newAddContentList.add(oldRuleContent);
                } else {
                    oldUpdateContentList.add(oldRuleContent);
                }
            }

            pageInfo = oldRule.getRuleContentList().iterator();

            while(pageInfo.hasNext()) {
                oldRuleContent = (RuleContent)pageInfo.next();
                int num = 0;
                Iterator pageInfo1 = ruleContentList.iterator();

                while(pageInfo1.hasNext()) {
                    RuleContent ruleContent = (RuleContent)pageInfo1.next();
                    if(ruleContent.getId() != null && oldRuleContent.getId().longValue() == ruleContent.getId().longValue()) {
                        ++num;
                        break;
                    }
                }

                if(num == 0) {
                    oldDeleteContentList.add(oldRuleContent);
                }
            }

            if(newAddContentList.size() > 0) {
                ruleContentMapper.insertRuleContent(newAddContentList);
            }

            if(oldUpdateContentList.size() > 0) {
                ruleContentMapper.updateRuleContent(oldUpdateContentList);
            }

            if(oldDeleteContentList.size() > 0) {
                ruleContentMapper.deleteRuleContent(oldDeleteContentList);
            }

        }
    }

    /**
     * 根据id集合和状态去修改状态
     * @param paramMap 包含以下参数
     *                 idList List|必需|所选的id
     *                 status|String|必需|状态
     * @return boolean
     * 测试通过
     */
    public boolean updateRuleStatus(Map<String, Object> paramMap) {
        int num = ruleMapper.updateRuleStatus(paramMap);
        return num > 0;
    }

    /**
     *
     * @param id
     * @return
     * 测试通过
     */
    public Rule findById(Long id) {
        return (Rule)ruleMapper.selectByPrimaryKey(id);
    }

    public int batchAddRule(Map<String, Object> param, List<Rule> klist) {
        int num = 0;
        Long parentId = Long.valueOf(Long.parseLong("" + param.get("newParentId")));
        Long organId = Long.valueOf(Long.parseLong("" + param.get("newOrganId")));
        Integer type = Integer.valueOf(Integer.parseInt("" + param.get("newType")));
        Long creator = Long.valueOf(Long.parseLong("" + param.get("creator")));

        for(Iterator pageInfo = klist.iterator(); pageInfo.hasNext(); ++num) {
            Rule ruleVo = (Rule)pageInfo.next();
            ruleVo.setParentId(parentId);
            ruleVo.setOrgId(organId);
            ruleVo.setType(type);
            ruleVo.setCreator(creator);//创建者
            ruleMapper.insertSelective(ruleVo);
            List ruleFieldList = ruleVo.getRuleFieldList();
            List ruleContentList = ruleVo.getRuleContentList();
            Iterator pageInfo1 = ruleFieldList.iterator();

            while(pageInfo1.hasNext()) {
                RuleField ruleContentVo = (RuleField)pageInfo1.next();
                ruleContentVo.setRuleId(ruleVo.getId());
            }

            if(ruleFieldList.size() > 0) {
                ruleFieldService.insertField(ruleFieldList);
            }

           /* pageInfo1 = ruleContentList.iterator();

            while(pageInfo1.hasNext()) {
                RuleContent var13 = (RuleContent)pageInfo1.next();
                var13.setRuleId(ruleVo.getId());
            }

            if(ruleContentList.size() > 0) {
                ruleContentService.insertRuleContent(ruleContentList);
            }*/
        }

        return num;
    }

    public List<RuleExcel> getRuleExcelData(Map<String, Object> param) {
        List klist = getRuleList(param);
        ArrayList list = new ArrayList();
        Iterator pageInfo = klist.iterator();

        while(pageInfo.hasNext()) {
            Rule ruleVo = (Rule)pageInfo.next();
            RuleExcel ruleExcelVo = new RuleExcel();
            ruleExcelVo.setName(ruleVo.getName());
            ruleExcelVo.setCode(ruleVo.getCode());
            ruleExcelVo.setDescription(ruleVo.getDesc());
            ruleExcelVo.setPriority(ruleVo.getPriority());
            ruleExcelVo.setFieldContent(getFieldContents(ruleVo));
            ruleExcelVo.setContent(getRuleContents(ruleVo));
            ruleExcelVo.setRuleContent(ruleVo.getContent());//规则代码包
            ruleExcelVo.setScore(ruleVo.getScore());
            ruleExcelVo.setType(ruleVo.getType());
            ruleExcelVo.setRuleAudit(Integer.valueOf(ruleVo.getRuleAudit()));
            ruleExcelVo.setRuleType(ruleVo.getRuleType());
            ruleExcelVo.setLastLogical(ruleVo.getLastLogical());
            list.add(ruleExcelVo);
        }

        return list;
    }

    public String getFieldContents(Rule ruleVo) {
        StringBuffer sb = new StringBuffer();
        List ruleFieldList = ruleVo.getRuleFieldList();
        Iterator pageInfo = ruleFieldList.iterator();

        while(pageInfo.hasNext()) {
            RuleField ruleFieldVo = (RuleField)pageInfo.next();
            sb.append(ruleFieldVo.getLogicalSymbol());
            sb.append(";");
            sb.append(ruleFieldVo.getCnName());
            sb.append(";");
            sb.append(ruleFieldVo.getOperator());
            sb.append(";");
            sb.append(ruleFieldVo.getFieldValue());
            sb.append(";");
            sb.append(ruleFieldVo.getFieldId());
            sb.append("\n");
        }

        return sb.toString();
    }

    public String getRuleContents(Rule ruleVo) {
        StringBuffer sb = new StringBuffer();
        List ruleContentList = ruleVo.getRuleContentList();
        Iterator pageInfo = ruleContentList.iterator();

        while(pageInfo.hasNext()) {
            RuleContent ruleContentVo = (RuleContent)pageInfo.next();
            sb.append(ruleContentVo.getCnName());
            sb.append("=");
            sb.append(ruleContentVo.getFieldValue());
            sb.append("\n");
        }

        return sb.toString();
    }

    public List<Long> getRuleIdsByParentId(Map<String, Object> param) {
        return ruleMapper.getRuleIdsByParentId(param);
    }

    public int countOnlyRuleName(Map<String, Object> param) {
        return ruleMapper.countOnlyRuleName(param);
    }

    public int countOnlyRuleCode(Map<String, Object> param) {
        return ruleMapper.countOnlyRuleCode(param);
    }

    /**
     *
     * @param idList id集合 List 必需
     * @return
     * 测试通过
     */
    public List<String> getFieldIdsByRuleId(List<Long> idList) {
        return ruleMapper.getFieldIdsByRuleId(idList);
    }

    public List<Rule> getRuleListByType(Map<String, Object> paramMap) {
        Long engineId = (Long)paramMap.get("engineId");
        List ruleIds = engineRuleRelMapper.getRuleIdsByEngineId(engineId);
        paramMap.put("ids", ruleIds);
        paramMap.put("type", Integer.valueOf(1));
        List rules = ruleMapper.getRuleListByType(paramMap);
        return rules;
    }
    public List<Rule> getNodeAddOrSubRulesByNodeId(Long nodeId) {
        return ruleMapper.getNodeAddOrSubRulesByNodeId(nodeId);
    }

    @Override
    public void deleteRelateRule(Long nodeId) {
        ruleMapper.deleteRelateRule(nodeId);
    }

    @Override
    public void saveRuleNode(Map<String, Object> map) {
        ruleMapper.saveRuleNode(map);
    }

    @Override
    public List<RuleNode> selectRuleNodeByNodeId(Long nodeId) {
        return ruleMapper.selectRuleNodeByNodeId(nodeId);
    }
}
