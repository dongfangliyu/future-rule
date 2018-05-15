//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


package com.zw.rule.engine.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zw.rule.datamanage.po.*;
import com.zw.rule.engine.po.*;
import com.zw.rule.engine.service.*;
import com.zw.rule.jeval.EvaluationException;
import com.zw.rule.jeval.Evaluator;
import com.zw.rule.jeval.tools.JevalUtil;
import com.zw.rule.jeval.tools.StringUtil;
import com.zw.rule.knowledge.po.Rule;
import com.zw.rule.knowledge.po.RuleField;
import com.zw.rule.knowledge.po.Scorecard;
import com.zw.rule.knowledge.po.ScorecardRuleContent;
import com.zw.rule.mapper.datamanage.*;
import com.zw.rule.mapper.engine.*;
import com.zw.rule.mapper.knowledge.RuleFieldMapper;
import com.zw.rule.mapper.knowledge.RuleMapper;
import com.zw.rule.mapper.knowledge.ScorecardMapper;
import com.zw.rule.mapper.knowledge.ScorecardRuleContentMapper;
import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.*;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EngineServiceImpl implements EngineService {
    private static final Logger logger = Logger.getLogger(EngineServiceImpl.class);
    @Resource
    private EngineMapper engineMapper;
    @Resource
    private EngineVersionMapper engineVersionMapper;
    @Resource
    private EngineNodeMapper engineNodeMapper;
    @Resource
    private RuleFieldMapper ruleFieldMapper;
    @Resource
    private FieldMapper fieldMapper;
    @Resource
    private ScorecardMapper scorecardMapper;
    @Resource
    private ResultSetListMapper resultSetListMapper;
    @Resource
    private EngineResultSetMapper resultSetMapper;
    @Resource
    private NodeListDbMapper nodeListDbMapper;
    @Resource
    private ListDbMapper listDbMapper;
    @Resource
    private ScorecardRuleContentMapper scorecardContentMapper;
    @Resource
    private CustListMapper custListMapper;
    @Resource
    private RuleMapper ruleMapper;
    @Resource
    private TblColumnMapper tblColumnMapper;

    public EngineServiceImpl() {
    }

    public List<Engine> queryEngineByList(Engine engineVo) {
        return engineMapper.getEngineByList(engineVo);
    }

    public int deleteEngine(Engine engineVo){return engineMapper.deleteEngine(engineVo);}

    public Engine queryEngineById(Engine engineVo) {
        return engineMapper.getEngineById(engineVo);
    }

    public int updateEngine(Engine engineVo) {
        return engineMapper.updateEngine(engineVo);
    }

    public int addEngine(Engine engineVo) {
        return engineMapper.insertEngine(engineVo);
    }

    public int addEngineAndReturnId(Engine engine) {
        return engineMapper.insertEngineAndReturnId(engine);
    }

    public boolean saveEngine(Engine engine, String url) {
        boolean flag = true;
        int engineCount = engineMapper.insertEngineAndReturnId(engine);
        if (engineCount == 1) {
            Long engineId = engine.getId();
            if (engineId != null && engineId > 0L) {
                EngineVersion engineVersion = new EngineVersion();
                engineVersion.setBootState(0);
                engineVersion.setCreateTime((new Date()).toString());
                engineVersion.setEngineId(engine.getId());
                engineVersion.setLatestTime((new Date()).toString());
                engineVersion.setLatestUser(engine.getCreator());
                engineVersion.setLayout(0);
                engineVersion.setStatus(1);
                engineVersion.setUserId(engine.getCreator());
                engineVersion.setVersion(0);
                engineVersion.setSubVer(0);
                EngineNode node = new EngineNode();
                node.setNodeX(200.0D);
                node.setNodeY(200.0D);
                node.setNodeName("开始");
                node.setNodeOrder(1);
                node.setNodeType(NodeTypeEnum.START.getValue());
                node.setNodeCode("ND_START");
                node.setParams("{\"arr_linkId\":\"\",\"dataId\":\"-1\",\"url\":\"" + url + "/resources/images/decision/start.png\",\"type\":\"1\"}");
                int count = engineVersionMapper.insertEngineVersionAndReturnId(engineVersion);
                if (count == 1) {
                    long list_str = engineVersion.getVerId();
                    node.setVerId(list_str);
                    engineNodeMapper.insertSelective(node);
                }

                ArrayList arrayList = new ArrayList();
                String id_str = "1_$engineID$,11_$engineID$,111_$engineID$,112_$engineID$,1121_$engineID$,12_$engineID$,121_$engineID$,122_$engineID$,1221_$engineID$,123_$engineID$,1231_$engineID$,13_$engineID$,131_$engineID$,132_$engineID$,1321_$engineID$,133_$engineID$,1331_$engineID$,14_$engineID$,141_$engineID$,15_$engineID$,151_$engineID$";
                id_str = id_str.replace("$engineID$", String.valueOf(engineId));
                String[] id_arr = id_str.split(",");
                if (id_arr != null && id_arr.length > 0) {
                    for (int sysUser = 0; sysUser < id_arr.length; sysUser++) {
                        arrayList.add(id_arr[sysUser]);
                    }
                }
            } else {
                flag = false;
            }
        } else {
            flag = false;
        }

        return flag;
    }

    public String queryEngineExecute(Map<String, Object> inputParam, Engine engine) {
        long engineId = engineMapper.getEngineByCode(engine).getId().longValue();
        ArrayList errorNodeList = new ArrayList();
        EngineVersion engineVersion = engineVersionMapper.queryDeploiedVersionByEngineId(Long.valueOf(engineId));
        HashMap outMap = new HashMap();
        if (engineVersion != null) {
            List engineNodelist = engineNodeMapper.queryNodeListByVerId(engineVersion.getVerId());
            if (engineNodelist != null) {
                Map engineNodeMap = getEngineNodeListByMap(engineNodelist);

                try {
                    EngineNode engineNode = getEngineNodeByMap("start", engineNodeMap);
                    if (engineNode != null && engineNode.getNextNodes() != null) {
                        recursionEngineNode(inputParam, engineNode, engineNodeMap, outMap, errorNodeList);
                    }
                } catch (Exception e) {
                    logger.error("请求异常", e);
                }

                return "";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Map<String, Object> getEngineVersionExecute(Map<String, Object> inputParam, String id) {
        HashMap outMap = new HashMap();
        outMap.put("userId", inputParam.get("userId"));
        outMap.put("orgId", inputParam.get("orgId"));
        HashMap resultMap = new HashMap();
        ArrayList errorNodeList = new ArrayList();
        Integer resultid = null;
        //获取当前版本节点
        List engineNodelist = engineNodeMapper.queryNodeListByVerId(Long.valueOf(id));
        String batchNo = "";
        if (inputParam.containsKey("batchNo") && !inputParam.get("batchNo").equals("")) {
            batchNo = String.valueOf(inputParam.get("batchNo"));
        }

        inputParam.remove("batchNo");
        inputParam.remove("versionId");
        String json = JSONObject.toJSONString(inputParam);
        if (engineNodelist != null) {
            //获取节点code
            Map engineNodeMap = getEngineNodeListByMap(engineNodelist);

            try {
                EngineNode e = getEngineNodeByMap("ND_START", engineNodeMap);
                if (e != null && e.getNextNodes() != null) {
                    EngineVersion engineVersion = engineVersionMapper.selectByPrimaryKey(Long.valueOf(id).longValue());
                    inputParam.put("engineId", engineVersion.getEngineId());
                    recursionEngineNode(inputParam, (EngineNode) engineNodeMap.get(e.getNextNodes()), engineNodeMap, outMap, errorNodeList);
                    if (errorNodeList.isEmpty()) {
                        EngineResultSet resultSet = new EngineResultSet();
                        if (engineVersion != null) {
                            Engine resultSetLists = new Engine();
                            resultSetLists.setId(engineVersion.getEngineId());
                            Long orgId = (Long) inputParam.get("orgId");//2017/5/17
                            resultSetLists.setOrgId(orgId);//2017/5/17
                            resultSetLists = engineMapper.getEngineById(resultSetLists);
                            resultSet.setEngineCode(resultSetLists.getCode());
                            resultSet.setInput(json);
                            resultSet.setBatchNo(batchNo);
                            resultSet.setEngineId(resultSetLists.getId());
                            resultSet.setEngineName(resultSetLists.getName());
                            resultSet.setType(Integer.valueOf(inputParam.get("type").toString()));
                            resultSet.setSubVer(engineVersion.getSubVer());
                            resultSet.setEngineVer(Integer.valueOf(id));
                            if (outMap.containsKey("scorecardScore")) {
                                resultSet.setScorecardscore(outMap.get("scorecardScore").toString());
                            }

                            if (outMap.containsKey("enginefalg")) {
                                if (outMap.get("enginefalg").equals("true")) {
                                    resultSet.setResult("拒绝");
                                }
                            } else if (outMap.containsKey("engineWhite") && outMap.get("engineWhite").equals("true")) {
                                resultSet.setResult("通过");
                            } else if (outMap.containsKey("formula")) {
                                resultSet.setResult(outMap.get("formula").toString());
                            }

                            String userId = String.valueOf(inputParam.get("userId"));//
                            resultSet.setUuid(userId.toString());//
                        }

                        if (outMap.containsKey("formulaList")) {
                            List list = (List) outMap.get("formulaList");

                            for (int i = 0; i < list.size(); i++) {
                                if (resultSet.getDatilResult() != null) {
                                    resultSet.setDatilResult(resultSet.getDatilResult() + "，　" + (String) list.get(i));
                                } else {
                                    resultSet.setDatilResult((String) list.get(i));
                                }
                            }
                        }

                        resultSetMapper.addResultSet(resultSet);
                        resultid = resultSet.getId();
                        ArrayList arrayList = new ArrayList();
                        if (outMap.containsKey("rulelist")) {
                            ArrayList rulelist = (ArrayList) outMap.get("rulelist");

                            for (int i = 0; i < rulelist.size(); i++) {
                                ResultSetList resultSetList1 = new ResultSetList();
                                resultSetList1.setCode(((Rule) rulelist.get(i)).getCode());
                                resultSetList1.setName(((Rule) rulelist.get(i)).getName());
                                if (((Rule) rulelist.get(i)).getRuleType().intValue() == 0) {
                                    resultSetList1.setType(Integer.valueOf(3));
                                } else {
                                    resultSetList1.setType(Integer.valueOf(4));
                                }

                                if (((Rule) rulelist.get(i)).getDesc() != null) {
                                    resultSetList1.setDesc(((Rule) rulelist.get(i)).getDesc());
                                }

                                resultSetList1.setResultsetId(String.valueOf(resultid));
                                arrayList.add(resultSetList1);
                            }
                        }

                        ListDb listDb;
                        ResultSetList resultSetList;
                        if (outMap.containsKey("black")) {
                            listDb = (ListDb) outMap.get("black");
                            resultSetList = new ResultSetList();
                            resultSetList.setCode("" + listDb.getDataSource());
                            resultSetList.setName(listDb.getListName());
                            resultSetList.setId(listDb.getId());
                            resultSetList.setType(Integer.valueOf(1));
                            if (listDb.getListDesc() != null) {
                                resultSetList.setDesc(listDb.getListDesc());
                            }

                            resultSetList.setResultsetId(String.valueOf(resultid));
                            arrayList.add(resultSetList);
                        }

                        if (outMap.containsKey("white")) {
                            listDb = (ListDb) outMap.get("white");
                            resultSetList = new ResultSetList();
                            resultSetList.setCode("" + listDb.getDataSource());
                            resultSetList.setName(listDb.getListName());
                            resultSetList.setId(listDb.getId());
                            resultSetList.setType(Integer.valueOf(2));
                            if (listDb.getListDesc() != null) {
                                resultSetList.setDesc(listDb.getListDesc());
                            }

                            resultSetList.setResultsetId(String.valueOf(resultid));
                            arrayList.add(resultSetList);
                        }

                        //自定义名单
//                        if(outMap.containsKey("isCustomDb")){
//                            String name = inputParam.get("customDb").toString();
//                            ResultSetList resultSetListCustom = new ResultSetList();
//                            resultSetListCustom.setName(name);
//                            resultSetListCustom.setType(5);
//                            resultSetListCustom.setResultsetId(String.valueOf(resultid));
//                            arrayList.add(resultSetListCustom);
//                        }

                        if (arrayList.size() > 0) {
                            resultSetListMapper.insertResultSetListByList(arrayList);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("请求异常", e);
            }
        }

        resultMap.put("id", resultid);
        resultMap.put("errorNodeList", errorNodeList);
        return resultMap;
    }

    private void recursionEngineNode(Map<String, Object> inputParam, EngineNode engineNode, Map<String, EngineNode> engineNodeMap, Map<String, Object> outMap, List<EngineNode> errorNodeList) {
        if (outMap.containsKey("isBlack") && outMap.get("isBlack").equals("true")) {
            outMap.remove("nextNode");
        }

        if (outMap.containsKey("isWhite") && outMap.get("isWhite").equals("true")) {
            outMap.remove("nextNode");
        }

        if (outMap.containsKey("nextNode")) {
            engineNode = (EngineNode) engineNodeMap.get(outMap.get("nextNode"));
            outMap.remove("nextNode");
        }

        if (engineNode != null) {
            try {
                getRuleEngineNode(inputParam, engineNode, outMap);
            } catch (Exception e) {
                errorNodeList.add(engineNode);
                e.printStackTrace();
                logger.error("请求异常", e);
            }

            if (engineNode.getNextNodes() != null && !"".equals(engineNode.getNextNodes())) {
                EngineNode newengineNode = (EngineNode) engineNodeMap.get(engineNode.getNextNodes());
                recursionEngineNode(inputParam, newengineNode, engineNodeMap, outMap, errorNodeList);
            }
        }

    }

    private Map<String, EngineNode> getEngineNodeListByMap(List<EngineNode> nodelist) {
        HashMap map = new HashMap();

        for (int i = 0; i < nodelist.size(); i++) {
            map.put(((EngineNode) nodelist.get(i)).getNodeCode(), (EngineNode) nodelist.get(i));
        }

        return map;
    }

    private EngineNode getEngineNodeByMap(String nodeCode, Map<String, EngineNode> nodeMap) {
        return nodeMap.containsKey(nodeCode) ? (EngineNode) nodeMap.get(nodeCode) : null;
    }

    private void getRuleEngineNode(Map<String, Object> inputParam, EngineNode engineNode, Map<String, Object> outMap) {
        switch (engineNode.getNodeType().intValue()) {
            case 2:
                getEngineRule(engineNode, inputParam, outMap);
                break;
            case 3:
                getCustomerSegmentation(engineNode, inputParam, outMap);
                break;
            case 4:
                getScoreCard(engineNode, inputParam, outMap);
                break;
            case 5:
                getEngineBlackandWhite(engineNode, inputParam, outMap, Integer.valueOf(1));
                break;
            case 6:
                getEngineBlackandWhite(engineNode, inputParam, outMap, Integer.valueOf(2));
                break;
            case 7:
                getSandboxProportion(engineNode, outMap);
            default:
                break;
            case 9:
                getDecisionOptions(engineNode, inputParam, outMap);
                break;
//            case 14://自定义名单   测试
//                getCustomDb(engineNode, inputParam, outMap);//demo  测试用
//                break;
            case 13:
                getEnginecomplexRule(engineNode, inputParam, outMap);
        }

    }

    //    /**  -----------------   demo 测试用    ------------------ */
//    @Resource
//    private CustomDbMapper customDbMapper;
//    private void getCustomDb(EngineNode engineNode, Map<String, Object> inputParam, Map<String, Object> outMap){
//        String name = inputParam.get("customDb").toString();
//        int count = customDbMapper.getCount(name);
//        if(count == 0){
//            outMap.put("engineWhite","true");
//        }else {
//            outMap.put("enginefalg","true");
//            outMap.put("isCustomDb","true");
//            engineNode.setNextNodes((String)null);
//        }
//    }
//    /**  -----------------   demo 测试用end    --------------- */
    private void getCustomerSegmentation(EngineNode engineNode, Map<String, Object> inputParam, Map<String, Object> outMap) {
        JSONObject jsonScript = JSONObject.parseObject(engineNode.getNodeScript());
        JSONArray fieldsArray = jsonScript.getJSONArray("fields");

        for (int i = 0; i < fieldsArray.size(); i++) {
            JSONObject fieldjsonObject = fieldsArray.getJSONObject(i);
            String field_code = fieldjsonObject.getString("field_code");
            if (!inputParam.containsKey(field_code)) {
                inputParam.put(field_code, (Object) null);
                getFieldResult(inputParam);
            }
        }

        try {
            String nextNode = handleClassify(jsonScript, inputParam);
            outMap.put("nextNode", nextNode);
        } catch (EvaluationException e) {
            e.printStackTrace();
            logger.error("请求异常", e);
        }

    }

    private static String handleClassify(JSONObject jsonScript, Map<String, Object> variablesMap) throws EvaluationException {
        JSONArray conditions = jsonScript.getJSONArray("conditions");
        String nextNode = "";
        if (conditions != null && !conditions.isEmpty()) {
            int size = conditions.size();
            boolean flag = false;
            JSONObject formula = null;

            for (int i = 0; i < size; i++) {
                formula = conditions.getJSONObject(i);
                if ("".equals(formula.getString("formula"))) {
                    if (nextNode.equals("")) {
                        nextNode = formula.getString("nextNode");
                    }
                } else {
                    flag = JevalUtil.evaluateBoolean(formula.getString("formula"), variablesMap).booleanValue();
                    if (flag) {
                        nextNode = formula.getString("nextNode");
                        break;
                    }
                }
            }

            return nextNode;
        } else {
            return nextNode;
        }
    }

    private void getDecisionOptions(EngineNode engineNode, Map<String, Object> inputParam, Map<String, Object> outMap) {
        JSONObject jsonObject = JSONObject.parseObject(engineNode.getNodeScript());
        DecisionOptions decisionOptions = new DecisionOptions();
        decisionOptions.setCode(engineNode.getNodeCode());
        decisionOptions.setName(engineNode.getNodeName());
        HashMap inFields = new HashMap();
        HashMap outFields = new HashMap();
        decisionOptions.setInFields(inFields);
        decisionOptions.setOutFields(outFields);
        JSONArray inputjsonArray = jsonObject.getJSONArray("input");
        JSONArray formulajsonArray = jsonObject.getJSONArray("conditions");
        JSONObject outputJson = jsonObject.getJSONObject("output");
        String outputfield_code = outputJson.getString("field_code");
        String field_type = outputJson.getString("field_type");
        String condition_type = jsonObject.getString("condition_type");
        Object variablesMap = new HashMap();

        int list;
        for (list = 0; list < inputjsonArray.size(); ++list) {
            String formulaJson = inputjsonArray.get(list).toString();
            JSONObject e = JSONObject.parseObject(formulaJson);
            String field_code = e.getString("field_code");
            if (!inputParam.containsKey(field_code)) {
                inputParam.put(field_code, (Object) null);
                getFieldResult(inputParam);
            }

            inFields.put(e.getString("field_code"), inputParam.get(field_code));
            if (e.containsKey("segments")) {
                JSONArray fieldsMap = e.getJSONArray("segments");
                Double segmentsvalue = isFieldValue(e.getInteger("field_type"), inputParam, field_code, fieldsMap);
                HashMap fieldsMap1 = new HashMap();
                fieldsMap1.put(field_code, e.getInteger("field_type"));
                ((Map) variablesMap).put(field_code, segmentsvalue);
                variablesMap = JevalUtil.convertVariables(fieldsMap1, (Map) variablesMap);
            } else {
                HashMap map = new HashMap();
                map.put(field_code, e.getInteger("field_type"));
                ((Map) variablesMap).put(field_code, inputParam.get(field_code));
                variablesMap = JevalUtil.convertVariables(map, (Map) variablesMap);
            }
        }

        if (!outMap.containsKey("formulaList")) {
            outMap.put("formulaList", new ArrayList());
        }

        for (list = 0; list < formulajsonArray.size(); ++list) {
            JSONObject jsonobj = JSONObject.parseObject(formulajsonArray.getString(list));

            try {
                boolean flag;
                if (condition_type.equals("2")) {
                    if (outputJson.getIntValue("field_type") == 2) {
                        String formula = JevalUtil.evaluateString(jsonobj.getString("formula"), (Map) variablesMap);
                        outFields.put(outputfield_code, formula);
                        outMap.put("formula", formula);
                        ((List) outMap.get("formulaList")).add(engineNode.getNodeName() + ":" + formula);
                    } else if (jsonobj.containsKey("result") && "".equals(jsonobj.getString("result"))) {
                        double var27 = JevalUtil.evaluateNumric(jsonobj.getString("formula"), (Map) variablesMap).doubleValue();
                        outFields.put(outputfield_code, jsonobj.getString("resultKey"));
                        outMap.put("formula", Double.valueOf(var27));
                        inputParam.put(engineNode.getNodeCode() + "_" + engineNode.getNodeId(), jsonobj.getString("result"));
                        ((List) outMap.get("formulaList")).add(engineNode.getNodeName() + ":" + var27);
                    } else {
                        flag = JevalUtil.evaluateBoolean(jsonobj.getString("formula"), (Map) variablesMap).booleanValue();
                        if (flag) {
                            outFields.put(outputfield_code, Boolean.valueOf(flag));
                            outMap.put("formula", jsonobj.getString("resultKey"));
                            inputParam.put(engineNode.getNodeCode() + "_" + engineNode.getNodeId(), jsonobj.getString("result"));
                            ((List) outMap.get("formulaList")).add(engineNode.getNodeName() + ":" + jsonobj.getString("resultKey"));
                        }
                    }
                } else {
                    flag = JevalUtil.evaluateBoolean(jsonobj.getString("formula"), (Map) variablesMap).booleanValue();
                    if (flag) {
                        outFields.put(outputfield_code, jsonobj.getString("resultKey"));
                        outMap.put("formula", jsonobj.getString("resultKey"));
                        ((List) outMap.get("formulaList")).add(engineNode.getNodeName() + ":" + jsonobj.getString("resultKey"));
                        inputParam.put(engineNode.getNodeCode() + "_" + engineNode.getNodeId(), jsonobj.getString("result"));
                    }
                }
            } catch (EvaluationException e) {
                e.printStackTrace();
                logger.error("请求异常", e);
            }
        }

        ArrayList arrayList;
        if (outMap.containsKey("decisionoptions")) {
            arrayList = (ArrayList) outMap.get("decisionoptions");
            arrayList.add(decisionOptions);
        } else {
            arrayList = new ArrayList();
            arrayList.add(decisionOptions);
            outMap.put("decisionoptions", arrayList);
        }

    }

    private void getScoreCard(EngineNode engineNode, Map<String, Object> inputParam, Map<String, Object> outMap) {
        List contents = scorecardContentMapper.getRuleContentList(Long.valueOf(engineNode.getNodeJson()));
        Scorecard scorecard = (Scorecard) scorecardMapper.selectByPrimaryKey(Long.valueOf(engineNode.getNodeJson()));
        ScoreCardEngine scoreCardEngine = new ScoreCardEngine();
        scoreCardEngine.setCode(scorecard.getCode());
        scoreCardEngine.setName(scorecard.getName());
        HashMap inFields = new HashMap();
        HashMap outFields = new HashMap();

        for (int i = 0; i < contents.size(); i++) {
            ScorecardRuleContent scorecardRuleContent = (ScorecardRuleContent) contents.get(i);
            String fieldvalue = scorecardRuleContent.getFieldValue();
            JSONObject jsonObject = JSONObject.parseObject(fieldvalue);
            JSONArray array = jsonObject.getJSONArray("fields");
            JSONObject outputJson = jsonObject.getJSONObject("output");
            String exp = jsonObject.getString("formula");
            Object variablesMap = new HashMap();

            for (int outfieldvalue = 0; outfieldvalue < array.size(); outfieldvalue++) {
                JSONObject e = JSONObject.parseObject(array.getString(outfieldvalue));
                String field_code = e.getString("field_code");
                if (!inputParam.containsKey(field_code)) {
                    inputParam.put(field_code, (Object) null);
                    getFieldResult(inputParam);
                }

                inFields.put(field_code, inputParam.get(field_code));
                JSONArray segments = e.getJSONArray("segments");
                Double segmentsvalue = isFieldValue(e.getInteger("field_type"), inputParam, field_code, segments);
                HashMap fieldsMap = new HashMap();
                fieldsMap.put(field_code, e.getInteger("field_type"));
                ((Map) variablesMap).put(field_code, segmentsvalue);
                variablesMap = JevalUtil.convertVariables(fieldsMap, (Map) variablesMap);
            }

            try {
                Double code = JevalUtil.evaluateNumric(exp, (Map) variablesMap);
                outFields.put(outputJson.getString("field_code"), code);
                inputParam.put(scorecard.getCode() + "_" + outputJson.getString("field_code"), code);
                inputParam.put(scorecard.getCode(), code);
            } catch (EvaluationException e) {
                e.printStackTrace();
                logger.error("请求异常", e);
            }
        }

        if (scorecard.getScore() != null) {
            selectScoreCardFinal(scorecard.getScore(), "scorecardScore", outFields, inputParam, scorecard, inFields);
        }

        if (scorecard.getPd() != null) {
            selectScoreCardFinal(scorecard.getPd(), "pd", outFields, inputParam, scorecard, inFields);
        }

        if (scorecard.getOdds() != null) {
            selectScoreCardFinal(scorecard.getOdds(), "odds", outFields, inputParam, scorecard, inFields);
        }

        scoreCardEngine.setOutFields(outFields);
        scoreCardEngine.setInFields(inFields);
        outMap.put(scoreCardEngine.getCode(), scoreCardEngine);
        outMap.put("scorecardScore", scoreCardEngine.getOutFields().get("scorecardScore"));
    }

    private void selectScoreCardFinal(String json, String name, Map<String, Object> outFields, Map<String, Object> inputParam, Scorecard scorecard, Map<String, Object> inFields) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONArray array = jsonObject.getJSONArray("fields");
        JSONObject outputJson = jsonObject.getJSONObject("output");
        Object variablesMap = new HashMap();
        String exp = jsonObject.getString("formula");

        for (int outfieldvalue = 0; outfieldvalue < array.size(); outfieldvalue++) {
            JSONObject e = JSONObject.parseObject(array.getString(outfieldvalue));
            String field_code = e.getString("field_code");
            if (!inputParam.containsKey(field_code)) {
                inputParam.put(field_code, (Object) null);
                getFieldResult(inputParam);
            }

            inFields.put(field_code, inputParam.get(field_code));
            JSONArray segments = e.getJSONArray("segments");
            Double segmentsvalue = isFieldValue(e.getInteger("field_type"), inputParam, field_code, segments);
            HashMap fieldsMap = new HashMap();
            fieldsMap.put(field_code, e.getInteger("field_type"));
            ((Map) variablesMap).put(field_code, segmentsvalue);
            variablesMap = JevalUtil.convertVariables(fieldsMap, (Map) variablesMap);
        }

        try {
            Double code = JevalUtil.evaluateNumric(exp, (Map) variablesMap);
            outFields.put(name, code);
            inputParam.put(scorecard.getCode() + "_" + outputJson.getString("field_code"), code);
            if (name.equals("scorecardScore")) {
                inputParam.put("scorecardScore", code);
            }
        } catch (EvaluationException e) {
            e.printStackTrace();
            logger.error("请求异常", e);
        }

    }

    private Double isFieldValue(Integer type, Map<String, Object> map, String code, JSONArray segments) {
        double fieldvalue = 0.0D;
        int i;
        JSONObject jsonObject;
        if (type.intValue() == 3) {
            for (i = 0; i < segments.size(); i++) {
                jsonObject = segments.getJSONObject(i);
                if (map.get(code).equals(jsonObject.get("segment"))) {
                    fieldvalue = jsonObject.getDoubleValue("value");
                    return Double.valueOf(fieldvalue);
                }
            }
        } else {
            for (i = 0; i < segments.size(); i++) {
                jsonObject = segments.getJSONObject(i);
                String exp = JevalUtil.getNumericInterval(jsonObject.get("segment").toString(), code);

                try {
                    if (JevalUtil.evaluateBoolean(exp, map).booleanValue()) {
                        fieldvalue = jsonObject.getDoubleValue("value");
                        return Double.valueOf(fieldvalue);
                    }
                } catch (EvaluationException e) {
                    e.printStackTrace();
                    logger.error("请求异常", e);
                }
            }
        }

        return Double.valueOf(fieldvalue);
    }

    private Map<String, Object> getSandboxProportion(EngineNode engineNode, Map<String, Object> outMap) {
        if (engineNode.getNodeScript() != null) {
            List list = JSON.parseArray(engineNode.getNodeScript(), Sandbox.class);
            int num = 0;
            int[] range = {};
            for (int i = 0; i < list.size(); i++) {
                Sandbox sandbox = (Sandbox) list.get(i);
                int endNum = num + sandbox.getProportion().intValue();
                if (num == 0) {
                    num = getRandoms(0, sandbox.getSum().intValue(), 1)[0];
                }
                if (i == 0) {
                    range = getRandoms(0, endNum, sandbox.getProportion().intValue());
                } else {
                    range = getRandoms(num, endNum, sandbox.getProportion().intValue());
                }

                for (int j = 0; j < range.length; j++) {
                    if (range[j] == num) {
                        outMap.put("nextNode", sandbox.getNextNode());
                        return outMap;
                    }
                }
            }
        }

        return outMap;
    }

    public int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public int[] getRandoms(int min, int max, int count) {
        int[] randoms = new int[count];
        ArrayList listRandom = new ArrayList();
        if (count > max - min + 1) {
            return null;
        } else {
            int i;
            for (i = min; i <= max; i++) {
                listRandom.add(Integer.valueOf(i));
            }

            for (i = 0; i < count; i++) {
                int index = getRandom(0, listRandom.size() - 1);
                randoms[i] = ((Integer) listRandom.get(index)).intValue();
                listRandom.remove(index);
            }

            return randoms;
        }
    }

    private Map<String, Object> getEngineRule(EngineNode engineNode, Map<String, Object> inputParam, Map<String, Object> outMap) {
        engineFiledBynode(inputParam, engineNode, Integer.valueOf(1));
        getRule(inputParam, engineNode, outMap);
        return outMap;
    }

    private Map<String, Object> getEnginecomplexRule(EngineNode engineNode, Map<String, Object> inputParam, Map<String, Object> outMap) {
        enginecomplexFiledBynode(inputParam, engineNode, Integer.valueOf(1));
        getcomplexRuleRule(inputParam, engineNode, outMap);
        return outMap;
    }

    private Map<String, Object> getEngineBlackandWhite(EngineNode engineNode, Map<String, Object> inputParam, Map<String, Object> outMap, Integer type) {
        inputParam.put("nodeId", engineNode.getNodeId());
        boolean isfalg = findByQueryKey(inputParam, outMap, type);
        if (isfalg) {
            if (type.intValue() == 1) {
                outMap.put("isBlack", "true");
                outMap.put("enginefalg", "true");
                engineNode.setNextNodes((String) null);
            } else {
                outMap.put("isWhite", "true");
                outMap.put("engineWhite", "true");
                engineNode.setNextNodes((String) null);
            }
        }

        return outMap;
    }

    public boolean findByQueryKey(Map<String, Object> paramMap, Map<String, Object> outmap, Integer type) {
        Long userId = (Long) paramMap.get("userId");//用户id
        Long organId = (Long) paramMap.get("orgId");//组织id
        String strlistDbIds = null;
        if (!paramMap.get("nodeId").equals("0")) {
            //获取名单节点信息
            NodeListDb nodeListDb = nodeListDbMapper.findByNodeId(paramMap);
            //获取内部名单id
            strlistDbIds = nodeListDb.getInsideListdbs();
            String[] arraylistDBIds = null;
            Integer matchs = Integer.valueOf(0);
            Integer revMatchs = Integer.valueOf(0);
            if (strlistDbIds.length() <= 0) {
                return false;
            }

            arraylistDBIds = strlistDbIds.split(",");

            for (int i = 0; i < arraylistDBIds.length; ++i) {
                HashMap param1 = new HashMap();
                param1.put("userId", userId);
                Integer listDbId = Integer.valueOf(Integer.valueOf(arraylistDBIds[i]).intValue());
                param1.put("listDbId", listDbId);
                new ListDb();
                //获取选中的名单详情
                ListDb listDb = listDbMapper.findListDbById(param1);
                paramMap.put("listDb", listDb);
                String listType = listDb.getListType();
                String[] queryKeyArray = listDb.getQueryField().split(",");
                if (queryKeyArray.length > 0) {
                    Integer queryType = listDb.getQueryType();
                    Integer matchType = listDb.getMatchType();
                    String queryKey = "";
                    String revQueryKey = "";
                    String tableName = "organ_" + organId + "_" + listType + "_" + listDbId;
                    paramMap.put("tableName", tableName);
                    paramMap.put("schemaName", paramMap.get("dbName").toString());
                    List columnList = tblColumnMapper.getColumnList(paramMap);
                    Integer loc = Integer.valueOf(0);
                    int j = 0;

                    label109:
                    while (true) {
                        if (j >= queryKeyArray.length) {
                            paramMap.put("queryKey", queryKey);
                            paramMap.put("revQueryKey", revQueryKey);
                            break;
                        }

                        HashMap param = new HashMap();
                        param.put("id", queryKeyArray[j]);
                        param.put("userId", userId);
                        param.put("engineId", (Object) null);
                        Field field = fieldMapper.findByFieldId(param);
                        String fieldEn = field.getEnName();
                        Iterator iterator = columnList.iterator();

                        while (true) {
                            while (true) {
                                String colComment;
                                String colName;
                                String paramValue;
                                do {
                                    do {
                                        if (!iterator.hasNext()) {
                                            j++;
                                            continue label109;
                                        }

                                        TblColumn tblColumn = (TblColumn) iterator.next();
                                        colComment = tblColumn.getColComment();
                                        colName = tblColumn.getColName();
                                        paramValue = (String) paramMap.get(fieldEn);
                                    } while (!colName.startsWith("t"));
                                } while (!queryKeyArray[j].equals(colComment));

                                if (paramValue == null || paramValue.equals("")) {
                                    return false;
                                }

                                loc = Integer.valueOf(loc.intValue() + 1);
                                if (matchType.intValue() == 1) {
                                    if (loc.intValue() > 1 && queryType.intValue() == 1) {
                                        queryKey = queryKey + " and ";
                                    } else if (loc.intValue() > 1 && queryType.intValue() == 0) {
                                        queryKey = queryKey + " or ";
                                    }

                                    queryKey = queryKey + colName + " = \'" + paramValue + "\'";
                                } else if (matchType.intValue() == 0) {
                                    if (loc.intValue() > 1 && queryType.intValue() == 1) {
                                        queryKey = queryKey + " and ";
                                    } else if (loc.intValue() > 1 && queryType.intValue() == 0) {
                                        queryKey = queryKey + " or ";
                                        revQueryKey = revQueryKey + " + ";
                                    }

                                    queryKey = queryKey + colName + " like " + "\'%" + paramValue + "%\'";
                                    revQueryKey = revQueryKey + "max(instr(\'" + paramValue + "\'," + colName + "))";
                                }
                            }
                        }
                    }
                }

                matchs = Integer.valueOf(matchs.intValue() + custListMapper.findByQueryKey(paramMap).intValue());
                if (!paramMap.get("revQueryKey").equals("")) {
                    revMatchs = custListMapper.revFindByQueryKey(paramMap);
                }

                if (revMatchs == null) {
                    revMatchs = Integer.valueOf(0);
                }

                if (matchs.intValue() + revMatchs.intValue() > 0) {
                    if (type.intValue() == 1) {
                        outmap.put("black", listDb);
                    } else {
                        outmap.put("white", listDb);
                    }

                    return true;
                }
            }
        }

        return false;
    }

    private String getDbName() {
        ResourceBundle resource = ResourceBundle.getBundle("jdbc");
        String mysqlUrl = resource.getString("mysql.url");
        String[] aArray = mysqlUrl.split("/");
        String[] bArray = aArray[3].split("\\?");
        String dbName = bArray[0];
        return dbName;
    }

    private Map<String, Object> getcomplexRuleRule(Map<String, Object> maps, EngineNode engineNode, Map<String, Object> outmap) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("nodeId", engineNode.getNodeId());
        resultJson.put("status", "0x0000");
        resultJson.put("nodeName", engineNode.getNodeName());
        JSONArray result = new JSONArray();
        List complexlist = (List) maps.get(engineNode.getNodeId().toString());

        for (int arrar = 0; arrar < complexlist.size(); ++arrar) {
            String nodescript = engineNode.getNodeJson();
            JSONObject nodeJson = JSONObject.parseObject(nodescript);
            boolean isfalg = false;
            JSONObject addOrSubRules;
            if (nodeJson.containsKey("deny_rules")) {
                addOrSubRules = JSONObject.parseObject(nodeJson.getString("deny_rules"));
                if (addOrSubRules != null) {
                    ArrayList threshold = new ArrayList();
                    JSONArray list;
                    int array;
                    JSONObject ruleList;
                    List list1;
                    if (addOrSubRules.get("isSerial").toString().equals("1")) {
                        list = addOrSubRules.getJSONArray("rules");

                        for (array = 0; array < list.size(); ++array) {
                            ruleList = list.getJSONObject(array);
                            threshold.add(ruleList.getLong("id"));
                        }

                        list1 = ruleMapper.selectRulesByNodeId(engineNode.getNodeId());
                        isfalg = serialRule(((ComplexRule) complexlist.get(arrar)).getResult(), outmap, list1);
                        if (isfalg) {
                            outmap.put("checkfalg", Integer.valueOf(2));
                        }
                    } else {
                        list = addOrSubRules.getJSONArray("rules");

                        for (array = 0; array < list.size(); ++array) {
                            ruleList = list.getJSONObject(array);
                            threshold.add(ruleList.getLong("id"));
                        }

                        list1 = ruleMapper.selectRulesByNodeId(engineNode.getNodeId());
                        isfalg = parallelRule(((ComplexRule) complexlist.get(arrar)).getResult(), outmap, list1);
                        if (isfalg) {
                            outmap.put("checkfalg", Integer.valueOf(2));
                        }
                    }
                }
            }

            if (nodeJson.containsKey("addOrSubRules")) {
                addOrSubRules = JSONObject.parseObject(nodeJson.getString("addOrSubRules"));
                HashMap hashMap = new HashMap();
                if (addOrSubRules != null) {
                    ArrayList arrayList = new ArrayList();
                    JSONArray jsonArray1 = JSONArray.parseArray(addOrSubRules.getString("rules"));

                    for (int i = 0; i < jsonArray1.size(); i++) {
                        JSONObject ruleArray = jsonArray1.getJSONObject(i);
                        arrayList.add(ruleArray.getLong("id"));
                        hashMap.put(ruleArray.getLong("id"), ruleArray.getInteger("threshold"));
                    }

                    List list = ruleMapper.selectnodeByInRoleid(arrayList);
                    JSONArray jsonArray = new JSONArray();
                    complexaddOrSubRules(hashMap, (ComplexRule) complexlist.get(arrar), outmap, list, jsonArray, engineNode, maps);
                    JSONObject out = null;
                    if (((ComplexRule) complexlist.get(arrar)).getOut() == null) {
                        out = new JSONObject();
                    } else {
                        out = JSONObject.parseObject(((ComplexRule) complexlist.get(arrar)).getOut());
                    }

                    if (((ComplexRule) complexlist.get(arrar)).getReturnResult() != null) {
                        out.put("out", ((ComplexRule) complexlist.get(arrar)).getReturnResult());
                    }

                    result.add(out);
                }
            }
        }

        resultJson.put("result", result);
        JSONArray jsonArray;
        if (outmap.containsKey("complexrulelist")) {
            jsonArray = (JSONArray) outmap.get("complexrulelist");
            jsonArray.add(resultJson);
            outmap.put("complexrulelist", jsonArray);
        } else {
            jsonArray = new JSONArray();
            jsonArray.add(resultJson);
            outmap.put("complexrulelist", jsonArray);
        }

        if (outmap.containsKey("checkfalg") && outmap.get("checkfalg").toString().equals("2")) {
            outmap.put("enginefalg", "true");
            engineNode.setNextNodes((String) null);
        } else if (outmap.containsKey("checkfalg") && outmap.get("checkfalg").toString().equals("3")) {
            outmap.put("formula", "人工审批");
        } else if (outmap.containsKey("checkfalg") && outmap.get("checkfalg").toString().equals("4")) {
            outmap.put("formula", "简化流程");
        } else if (outmap.containsKey("checkfalg") && outmap.get("checkfalg").toString().equals("4")) {
            outmap.put("formula", "通过");
        }

        return outmap;
    }

    private void complexaddOrSubRules(Map<Long, Integer> threshold, ComplexRule complexRule, Map<String, Object> outmap, List<Rule> rules, JSONArray ruleArray, EngineNode engineNode, Map<String, Object> maps) {
        StatefulKnowledgeSession kSession = null;

        for (int i = 0; i < rules.size(); ++i) {
            try {
                KnowledgeBuilder e = KnowledgeBuilderFactory.newKnowledgeBuilder();
                String ruleString = ((Rule) rules.get(i)).getContent().replace("\\r\\n", "\r\n");
                ruleString = ruleString.replace("\\t", "\t");
                e.add(ResourceFactory.newByteArrayResource(ruleString.getBytes("utf-8")), ResourceType.DRL);
                KnowledgeBuilderErrors errors = e.getErrors();
                Iterator resultList = errors.iterator();

                while (resultList.hasNext()) {
                    KnowledgeBuilderError kBase = (KnowledgeBuilderError) resultList.next();
                    System.out.println(kBase);
                }

                KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
                knowledgeBase.addKnowledgePackages(e.getKnowledgePackages());
                kSession = knowledgeBase.newStatefulKnowledgeSession();
                ArrayList arrayList = new ArrayList();
                InputParam inputParam = new InputParam();
                inputParam.setInputParam(complexRule.getResult());
                inputParam.setResult(arrayList);
                kSession.insert(inputParam);
                kSession.fireAllRules();
                kSession.dispose();
                if (arrayList.size() > 0) {
                    if (((Result) arrayList.get(0)).getMap() != null && ((Result) arrayList.get(0)).getMap().size() > 0) {
                        complexRule.setReturnResult(((Result) arrayList.get(0)).getMap());
                    }

                    maps.put("cr_" + engineNode.getNodeId() + "_" + ((Rule) rules.get(i)).getCode(), Integer.valueOf(1));
                    if (maps.containsKey("cs_" + engineNode.getNodeId() + "_" + ((Rule) rules.get(i)).getCode())) {
                        if (((Rule) rules.get(i)).getScore() != null) {
                            maps.put("cs_" + engineNode.getNodeId() + "_" + ((Rule) rules.get(i)).getCode(), Integer.valueOf(Integer.valueOf(maps.get("cs_" + engineNode.getNodeId() + "_" + ((Rule) rules.get(i)).getCode()).toString()).intValue() + ((Rule) rules.get(i)).getScore().intValue()));
                        }
                    } else if (((Rule) rules.get(i)).getScore() != null) {
                        maps.put("cs_" + engineNode.getNodeId() + "_" + ((Rule) rules.get(i)).getCode(), ((Rule) rules.get(i)).getScore());
                    } else {
                        maps.put("cs_" + engineNode.getNodeId() + "_" + ((Rule) rules.get(i)).getCode(), Integer.valueOf(0));
                    }

                    if (maps.containsKey("cs_" + engineNode.getNodeId() + "_" + ((Rule) rules.get(i)).getCode()) && ((Rule) rules.get(i)).getScore() != null && threshold != null && threshold.get(((Rule) rules.get(i)).getId()) != null) {
                        if (Long.valueOf(maps.get("cs_" + engineNode.getNodeId() + "_" + ((Rule) rules.get(i)).getCode()).toString()).longValue() >= Long.valueOf((long) ((Integer) threshold.get(((Rule) rules.get(i)).getId())).intValue()).longValue()) {
                            outmap.put("checkfalg", Integer.valueOf(2));
                        } else if (outmap.containsKey("checkfalg") && Integer.valueOf(outmap.get("checkfalg").toString()).intValue() > ((Rule) rules.get(i)).getRuleAudit()) {
                            outmap.put("checkfalg", Integer.valueOf(((Rule) rules.get(i)).getRuleAudit()));
                        }
                    }

                    ((Result) arrayList.get(0)).setName(((Rule) rules.get(i)).getName());
                    List rulelist;
                    if (outmap.containsKey("rule")) {
                        rulelist = (List) outmap.get("rule");
                        outmap.put("rule", arrayList);
                    } else {
                        outmap.put("rule", arrayList);
                    }

                    if (outmap.containsKey("rulelist")) {
                        rulelist = (List) outmap.get("rulelist");
                        rulelist.add((Rule) rules.get(i));
                        outmap.put("rulelist", rulelist);
                    } else {
                        ArrayList arrayList1 = new ArrayList();
                        arrayList1.add((Rule) rules.get(i));
                        outmap.put("rulelist", arrayList1);
                    }
                } else {
                    if (maps.containsKey("cr_" + engineNode.getNodeId() + "_" + ((Rule) rules.get(i)).getCode()) && maps.containsKey("cr_" + engineNode.getNodeId()) && !maps.get("cr_" + engineNode.getNodeId()).equals("1")) {
                        maps.put("cr_" + engineNode.getNodeId() + "_" + ((Rule) rules.get(i)).getCode(), Integer.valueOf(2));
                    } else {
                        maps.put("cr_" + engineNode.getNodeId() + "_" + ((Rule) rules.get(i)).getCode(), Integer.valueOf(2));
                    }

                    if (!maps.containsKey("cs_" + engineNode.getNodeId() + "_" + ((Rule) rules.get(i)).getCode())) {
                        maps.put("cs_" + engineNode.getNodeId() + "_" + ((Rule) rules.get(i)).getCode(), Integer.valueOf(0));
                    }
                }
            } catch (UnsupportedEncodingException var21) {
                var21.printStackTrace();
                logger.error("请求异常", var21);
            } finally {
                if (kSession != null) {
                    kSession.dispose();
                }

            }
        }

    }

    private Map<String, Object> getRule(Map<String, Object> map, EngineNode engineNode, Map<String, Object> outmap) {
        String nodescript = engineNode.getNodeJson();
        JSONObject nodeJson = JSONObject.parseObject(nodescript);
        boolean isfalg = false;
        JSONObject addOrSubRules;
        ArrayList list;
        JSONArray array;
        if (nodeJson.containsKey("deny_rules")) {
            addOrSubRules = JSONObject.parseObject(nodeJson.getString("deny_rules"));
            if (addOrSubRules != null) {
                list = new ArrayList();
                int threshold;
                JSONObject ruleList;
                List list1;
                if (addOrSubRules.get("isSerial").toString().equals("1")) {
                    array = addOrSubRules.getJSONArray("rules");

                    for (threshold = 0; threshold < array.size(); ++threshold) {
                        ruleList = array.getJSONObject(threshold);
                        list.add(ruleList.getLong("id"));
                    }

                    list1 = ruleMapper.selectRulesByNodeId(engineNode.getNodeId());
                    isfalg = serialRule(map, outmap, list1);
                    if (isfalg) {
                        engineNode.setNextNodes((String) null);
                        outmap.put("enginefalg", "true");
                    }
                } else {
                    array = addOrSubRules.getJSONArray("rules");

                    for (threshold = 0; threshold < array.size(); ++threshold) {
                        ruleList = array.getJSONObject(threshold);
                        list.add(ruleList.getLong("id"));
                    }

                    list1 = ruleMapper.selectRulesByNodeId(engineNode.getNodeId());
                    isfalg = parallelRule(map, outmap, list1);
                    if (isfalg) {
                        outmap.put("enginefalg", "true");
                        engineNode.setNextNodes((String) null);
                    }
                }
            }
        }

        if (nodeJson.containsKey("addOrSubRules") && !isfalg) {
            addOrSubRules = JSONObject.parseObject(nodeJson.getString("addOrSubRules"));
            if (addOrSubRules != null) {
                list = new ArrayList();
                array = JSONArray.parseArray(addOrSubRules.getString("rules"));
                Double threshold = addOrSubRules.getDouble("threshold");

                for (int i = 0; i < array.size(); i++) {
                    JSONObject rulejson = array.getJSONObject(i);
                    list.add(rulejson.getLong("id"));
                }

                List listMapper = ruleMapper.selectnodeByInRoleid(list);
                isfalg = addOrSubRules(threshold, map, outmap, listMapper, engineNode.getNodeId().longValue());
                if (isfalg) {
                    outmap.put("enginefalg", "true");
                    engineNode.setNextNodes((String) null);
                }
            }
        }

        return outmap;
    }

    private boolean specialaddOrSubRules(Double threshold, Map<String, Object> map, Map<String, Object> outmap, List<Rule> rules, long nodeid) {
        boolean isfalg = false;
        StatefulKnowledgeSession kSession = null;

        for (int i = 0; i < rules.size(); i++) {
            try {
                KnowledgeBuilder e = KnowledgeBuilderFactory.newKnowledgeBuilder();
                String ruleString = ((Rule) rules.get(i)).getContent().replace("\\r\\n", "\r\n");
                ruleString = ruleString.replace("\\t", "\t");
                e.add(ResourceFactory.newByteArrayResource(ruleString.getBytes("utf-8")), ResourceType.DRL);
                KnowledgeBuilderErrors errors = e.getErrors();
                Iterator resultList = errors.iterator();

                while (resultList.hasNext()) {
                    KnowledgeBuilderError kBase = (KnowledgeBuilderError) resultList.next();
                    System.out.println(kBase);
                }

                KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
                knowledgeBase.addKnowledgePackages(e.getKnowledgePackages());
                kSession = knowledgeBase.newStatefulKnowledgeSession();
                ArrayList arrayList = new ArrayList();
                InputParam inputParam = new InputParam();
                inputParam.setInputParam(map);
                inputParam.setResult(arrayList);
                kSession.insert(inputParam);
                kSession.fireAllRules();
                kSession.dispose();
                if (arrayList.size() > 0) {
                    map.put(((Rule) rules.get(i)).getCode(), Integer.valueOf(1));
                    ((Result) arrayList.get(0)).setName(((Rule) rules.get(i)).getName());
                    List score;
                    if (outmap.containsKey("rule")) {
                        score = (List) outmap.get("rule");
                        outmap.put("rule", arrayList);
                    } else {
                        outmap.put("rule", arrayList);
                    }

                    if (outmap.containsKey("rulelist")) {
                        score = (List) outmap.get("rulelist");
                        score.add((Rule) rules.get(i));
                        outmap.put("rulelist", score);
                    } else {
                        ArrayList arrList = new ArrayList();
                        arrList.add((Rule) rules.get(i));
                        outmap.put("rulelist", arrList);
                    }

                    boolean flag;
                    if (outmap.containsKey("addOrSubRules")) {
                        score = (List) outmap.get("addOrSubRules");
                        score.addAll(arrayList);
                        outmap.put("addOrSubRules", arrayList);
                        double score1 = getaddOrSubRulesByscore(score).doubleValue();
                        if (score1 >= threshold.doubleValue()) {
                            isfalg = true;
                            flag = isfalg;
                            return flag;
                        }
                    } else {
                        outmap.put("addOrSubRules", arrayList);
                        double var29 = getaddOrSubRulesByscore(arrayList).doubleValue();
                        if (var29 >= threshold.doubleValue()) {
                            isfalg = true;
                            flag = isfalg;
                            return flag;
                        }

                        map.put("s_" + nodeid, Double.valueOf(var29));
                    }
                }

                map.put(((Rule) rules.get(i)).getCode(), Integer.valueOf(2));
            } catch (UnsupportedEncodingException var24) {
                var24.printStackTrace();
                logger.error("请求异常", var24);
            } finally {
                if (kSession != null) {
                    kSession.dispose();
                }

            }
        }

        return isfalg;
    }

    private boolean addOrSubRules(Double threshold, Map<String, Object> map, Map<String, Object> outmap, List<Rule> rules, long nodeid) {
        boolean isfalg = false;
        StatefulKnowledgeSession kSession = null;

        for (int i = 0; i < rules.size(); i++) {
            try {
                KnowledgeBuilder e = KnowledgeBuilderFactory.newKnowledgeBuilder();
                String ruleString = ((Rule) rules.get(i)).getContent().replace("\\r\\n", "\r\n");
                ruleString = ruleString.replace("\\t", "\t");
                e.add(ResourceFactory.newByteArrayResource(ruleString.getBytes("utf-8")), ResourceType.DRL);
                KnowledgeBuilderErrors errors = e.getErrors();
                Iterator resultList = errors.iterator();

                while (resultList.hasNext()) {
                    KnowledgeBuilderError kBase = (KnowledgeBuilderError) resultList.next();
                    System.out.println(kBase);
                }

                KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
                knowledgeBase.addKnowledgePackages(e.getKnowledgePackages());
                kSession = knowledgeBase.newStatefulKnowledgeSession();
                ArrayList arrayList = new ArrayList();
                InputParam inputParam = new InputParam();
                inputParam.setInputParam(map);
                inputParam.setResult(arrayList);
                kSession.insert(inputParam);
                kSession.fireAllRules();
                kSession.dispose();
                if (arrayList.size() > 0) {
                    map.put(((Rule) rules.get(i)).getCode(), Integer.valueOf(1));
                    ((Result) arrayList.get(0)).setName(((Rule) rules.get(i)).getName());
                    List score;
                    if (outmap.containsKey("rule")) {
                        score = (List) outmap.get("rule");
                        outmap.put("rule", arrayList);
                    } else {
                        outmap.put("rule", arrayList);
                    }

                    if (outmap.containsKey("rulelist")) {
                        score = (List) outmap.get("rulelist");
                        score.add((Rule) rules.get(i));
                        outmap.put("rulelist", score);
                    } else {
                        ArrayList arrayList1 = new ArrayList();
                        arrayList1.add((Rule) rules.get(i));
                        outmap.put("rulelist", arrayList1);
                    }

                    boolean flag;
                    if (outmap.containsKey(nodeid + "_addOrSubRules")) {
                        score = (List) outmap.get(nodeid + "_addOrSubRules");
                        score.addAll(arrayList);
                        outmap.put(nodeid + "_addOrSubRules", score);
                        double score1 = getaddOrSubRulesByscore(score).doubleValue();
                        if (threshold != null && score1 >= threshold.doubleValue()) {
                            isfalg = true;
                            flag = isfalg;
                            return flag;
                        }
                    } else {
                        outmap.put(nodeid + "_addOrSubRules", arrayList);
                        double var29 = getaddOrSubRulesByscore(arrayList).doubleValue();
                        if (threshold != null && var29 >= threshold.doubleValue()) {
                            isfalg = true;
                            flag = isfalg;
                            return flag;
                        }

                        map.put("s_" + nodeid, Double.valueOf(var29));
                    }
                }

                map.put(((Rule) rules.get(i)).getCode(), Integer.valueOf(2));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                logger.error("请求异常", e);
            } finally {
                if (kSession != null) {
                    kSession.dispose();
                }

            }
        }

        return isfalg;
    }

    private Double getaddOrSubRulesByscore(List<Result> results) {
        double score = 0.0D;

        for (int i = 0; i < results.size(); i++) {
            Result result = (Result) results.get(i);
            if (result.getMap() != null && result.getMap().containsKey("score")) {
                score += Double.valueOf(result.getMap().get("score").toString()).doubleValue();
            }
        }

        return Double.valueOf(score);
    }

    private boolean serialRule(Map<String, Object> map, Map<String, Object> outmap, List<Rule> rules) {
        boolean isfalg = false;
        StatefulKnowledgeSession kSession = null;

        for (int i = 0; i < rules.size(); i++) {
            try {
                KnowledgeBuilder e = KnowledgeBuilderFactory.newKnowledgeBuilder();
                String ruleString = ((Rule) rules.get(i)).getContent().replace("\\r\\n", "\r\n");
                ruleString = ruleString.replace("\\t", "\t");
                e.add(ResourceFactory.newByteArrayResource(ruleString.getBytes("utf-8")), ResourceType.DRL);
                KnowledgeBuilderErrors errors = e.getErrors();
                Iterator resultList = errors.iterator();

                while (resultList.hasNext()) {
                    KnowledgeBuilderError kBase = (KnowledgeBuilderError) resultList.next();
                    System.out.println(kBase);
                }

                KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
                knowledgeBase.addKnowledgePackages(e.getKnowledgePackages());
                kSession = knowledgeBase.newStatefulKnowledgeSession();
                ArrayList arrayList = new ArrayList();
                InputParam inputParam = new InputParam();
                inputParam.setInputParam(map);
                inputParam.setResult(arrayList);
                kSession.insert(inputParam);
                kSession.fireAllRules();
                kSession.dispose();
                List rulelist;
                if (outmap.containsKey("rule")) {
                    rulelist = (List) outmap.get("rule");
                    rulelist.addAll(arrayList);
                    outmap.put("rule", arrayList);
                } else {
                    outmap.put("rule", arrayList);
                }

                if (arrayList.size() > 0) {
                    ((Result) arrayList.get(0)).setName(((Rule) rules.get(i)).getName());
                    isfalg = true;
                    if (outmap.containsKey("rulelist")) {
                        rulelist = (List) outmap.get("rulelist");
                        rulelist.add((Rule) rules.get(i));
                        outmap.put("rulelist", rulelist);
                    } else {
                        ArrayList arrList = new ArrayList();
                        arrList.add((Rule) rules.get(i));
                        outmap.put("rulelist", arrList);
                    }

                    boolean flag = isfalg;
                    return flag;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                logger.error("请求异常", e);
            } finally {
                if (kSession != null) {
                    kSession.dispose();
                }

            }
        }

        return isfalg;
    }

    private boolean parallelRule(Map<String, Object> map, Map<String, Object> outmap, List<Rule> rules) {
        boolean isfalg = false;
        Integer priority = Integer.valueOf(0);
        StatefulKnowledgeSession kSession = null;

        for (int i = 0; i < rules.size(); i++) {
            try {
                if (isfalg && priority != ((Rule) rules.get(i)).getPriority()) {
                    boolean flag = isfalg;
                    return flag;
                }

                KnowledgeBuilder e = KnowledgeBuilderFactory.newKnowledgeBuilder();
                String ruleString = ((Rule) rules.get(i)).getContent().replace("\\r\\n", "\r\n");
                ruleString = ruleString.replace("\\t", "\t");
                e.add(ResourceFactory.newByteArrayResource(ruleString.getBytes("utf-8")), ResourceType.DRL);
                KnowledgeBuilderErrors errors = e.getErrors();
                Iterator resultList = errors.iterator();

                while (resultList.hasNext()) {
                    KnowledgeBuilderError kBase = (KnowledgeBuilderError) resultList.next();
                    System.out.println(kBase);
                }

                KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
                knowledgeBase.addKnowledgePackages(e.getKnowledgePackages());
                kSession = knowledgeBase.newStatefulKnowledgeSession();
                ArrayList arrayList = new ArrayList();
                InputParam inputParam = new InputParam();
                inputParam.setInputParam(map);
                inputParam.setResult(arrayList);
                kSession.insert(inputParam);
                kSession.fireAllRules();
                kSession.dispose();
                List rulelist;
                if (outmap.containsKey("rule")) {
                    rulelist = (List) outmap.get("rule");
                    rulelist.addAll(arrayList);
                    outmap.put("rule", arrayList);
                } else {
                    outmap.put("rule", arrayList);
                }

                if (arrayList.size() > 0) {
                    ((Result) arrayList.get(0)).setName(((Rule) rules.get(i)).getName());
                    isfalg = true;
                    if (outmap.containsKey("rulelist")) {
                        rulelist = (List) outmap.get("rulelist");
                        rulelist.add((Rule) rules.get(i));
                        outmap.put("rulelist", rulelist);
                    } else {
                        ArrayList arrList = new ArrayList();
                        arrList.add((Rule) rules.get(i));
                        outmap.put("rulelist", arrList);
                    }
                }

                priority = ((Rule) rules.get(i)).getPriority();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                logger.error("请求异常", e);
            } finally {
                if (kSession != null) {
                    kSession.dispose();
                }

            }
        }

        return isfalg;
    }

    private void engineFiledBynode(Map<String, Object> paramMap, EngineNode engineNode, Integer type) {
        NodeKnowledge knowledge = new NodeKnowledge();
        knowledge.setKnowledgeType(type);
        knowledge.setNodeId(engineNode.getNodeId());
        List ruleFields = ruleFieldMapper.getNodeByList(knowledge);
        ArrayList ruleidlist = new ArrayList();

        for (int filedMap = 0; filedMap < ruleFields.size(); filedMap++) {
            Long fieldList = Long.valueOf(((RuleField) ruleFields.get(filedMap)).getFieldId().split("\\|")[0]);
            ruleidlist.add(fieldList);
        }

        HashMap map = new HashMap();
        map.put("Ids", ruleidlist);
        map.put("isDerivative", Integer.valueOf(1));
        List var11 = fieldMapper.findFieldByIdsAndIsderivative(map);

        for (int y = 0; y < var11.size(); y++) {
            paramMap.put(((Field) var11.get(y)).getEnName(), (Object) null);
        }

        if (var11 != null && var11.size() > 0) {
            getFieldResult(paramMap);
        }

    }

    public Map<String, Object> getFieldResult(Map<String, Object> paramMap) {
        HashMap paramMap2 = new HashMap();
        Iterator organId = paramMap.entrySet().iterator();

        while (organId.hasNext()) {
            Entry userId = (Entry) organId.next();
            if (userId.getValue() != null) {
                paramMap2.put((String) userId.getKey(), userId.getValue());
            } else {
                paramMap2.put((String) userId.getKey(), "");
            }
        }

        Long userId1 = (Long) paramMap.get("userId");//用户id
        Long organId1 = (Long) paramMap.get("organId");//组织id
        paramMap2.put("userId", userId1);
        paramMap2.put("organId", organId1);
        Iterator var6 = paramMap.entrySet().iterator();

        while (true) {
            String fieldEn;
            String fieldValue;
            do {
                if (!var6.hasNext()) {
                    return paramMap;
                }

                Entry entry = (Entry) var6.next();
                fieldEn = (String) entry.getKey();
                fieldValue = "";
                if (entry.getValue() != null) {
                    fieldValue = String.valueOf(entry.getValue());
                }
            } while (fieldValue != null && !fieldValue.equals(""));

            paramMap2.put("enName", fieldEn);
            Field field = fieldMapper.findByFieldEn(paramMap2);
            if (field != null && field.getIsDerivative().intValue() == 1) {
                String result = "";
                paramMap2.put("enName", fieldEn);
                paramMap2.put("cnName", fieldMapper.findByFieldEn(paramMap2).getCnName());
                result = getExpAll(fieldMapper.findByFieldEn(paramMap2).getCnName(), "", paramMap);
                result = result.replace("(", "");
                result = result.replace(")", "");
                paramMap.put(fieldEn, result);
            }
        }
    }

    private String getExpAll(String fieldCn, String exp, Map<String, Object> param) {
        String result = "";
        HashMap param2 = new HashMap();
        Iterator organId = param.entrySet().iterator();

        while (organId.hasNext()) {
            Entry userId = (Entry) organId.next();
            if (userId.getValue() != null) {
                param2.put((String) userId.getKey(), userId.getValue());
            } else {
                param2.put((String) userId.getKey(), "");
            }
        }

        Long userId1 = (Long) param.get("userId");//用户id
        Long organId1 = (Long) param.get("organId");//组织id
        HashMap paramMap = new HashMap();
        paramMap.put("userId", userId1);
        paramMap.put("organId", organId1);
        paramMap.put("engineId", param.get("engineId"));
        paramMap.put("cnName", fieldCn);
        String arrFormula = "";
        Field engField = fieldMapper.findByFieldCn(paramMap);
        String engFormula = engField.getFormula();
        if (!engFormula.equals("") && engFormula != null) {
            arrFormula = engFormula;
        }

        String b;
        String v;
        if (!arrFormula.equals("") && arrFormula != null) {
            new ArrayList();
            JSONArray jsonArray = JSONObject.parseArray(arrFormula);

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObj = ((JSONArray) jsonArray).getJSONObject(i);
                String str = (String) jsonObj.get("formula");
                str = str.replace("&gt;", ">");
                str = str.replace("&lt;", "<");
                Pattern pat = Pattern.compile("@[a-zA-Z0-9_一-龥()（）-]+@");
                Matcher matcher = pat.matcher(str);
                String jsonStr = str;
                int n = 0;

                for (exp = ""; matcher.find(); n = matcher.end()) {
                    String str1 = matcher.group(0).replace("@", "");
                    HashMap hashMap = new HashMap();
                    hashMap.put("userId", paramMap.get("userId"));
                    hashMap.put("engineId", paramMap.get("engineId"));
                    hashMap.put("fieldCn", str1);
                    Field field = fieldMapper.findByFieldCn(hashMap);
                    if (field == null) {
                        field = fieldMapper.findByFieldCnNoEngineId(hashMap);
                    }

                    HashMap hashMap1 = new HashMap();
                    hashMap1.put("fieldValue", param2.get(field.getEnName()));
                    hashMap1.put("fieldEn", field.getEnName());
                    hashMap1.put("fieldValueType", field.getValueType());
                    JSONArray jsonArray1 = new JSONArray();
                    JSONArray jsonArray2 = JSONArray.parseArray(jsonObj.get("farr").toString());
                    if (jsonArray2 != null) {
                        Iterator var50 = jsonArray2.iterator();

                        while (var50.hasNext()) {
                            JSONObject var51 = (JSONObject) var50.next();
                            if (var51.get("fieldCN").equals(str1) && !var51.get("fieldCond").equals("")) {
                                jsonArray1 = (JSONArray) var51.get("fieldCond");
                                break;
                            }
                        }
                    }

                    hashMap1.put("fieldCond", jsonArray1);
                    v = "";
                    if (jsonArray1.size() > 0) {
                        v = calcFieldCond(hashMap1);
                    } else {
                        v = (String) param2.get(field.getEnName());
                    }

                    if (field.getIsDerivative().intValue() == 0) {
                        if (field.getValueType().intValue() != 1 && field.getValueType().intValue() != 4) {
                            exp = exp + jsonStr.substring(n, matcher.end()).replace("@" + str1 + "@", "\'" + v + "\'");
                        } else {
                            exp = exp + jsonStr.substring(n, matcher.end()).replace("@" + str1 + "@", v);
                        }
                    } else if (field.getValueType().intValue() != 1 && field.getValueType().intValue() != 4) {
                        exp = exp + jsonStr.substring(n, matcher.end()).replace("@" + str1 + "@", getExpAll(str1, "", param2));
                    } else {
                        exp = exp + jsonStr.substring(n, matcher.end()).replace("@" + str1 + "@", getExpAll(str1, exp, param2));
                    }
                }

                exp = exp + str.substring(n, str.length());
                Evaluator evaluator = new Evaluator();
                b = "";

                try {
                    System.out.println("========字段公式编辑设置的表达式输出：" + exp);
                    b = evaluator.evaluate(exp);
                } catch (EvaluationException e) {
                    e.printStackTrace();
                    logger.error("请求异常", e);
                }

                if (engField.getValueType().intValue() != 1 && engField.getValueType().intValue() != 2) {
                    if (engField.getValueType().intValue() == 3) {
                        if (!b.equals("1.0") && !b.equals("0.0") && !b.equals("")) {
                            result = b;
                            if (StringUtil.isValidStr(b) && b.startsWith("\'") && b.endsWith("\'")) {
                                result = b.substring(1, b.length() - 1);
                            }
                        }

                        if (b.equals("1.0")) {
                            result = (String) jsonObj.get("fvalue");
                            if (isNumeric(result)) {
                                result = "(" + result + ")";
                            } else {
                                result = "\'" + result + "\'";
                            }
                            break;
                        }
                    }
                } else if (!b.equals("")) {
                    result = b;
                    if (StringUtil.isValidStr(b) && b.startsWith("\'") && b.endsWith("\'")) {
                        result = b.substring(1, b.length() - 1);
                    }
                }
            }
        } else {
            new ArrayList();
            List i = fieldMapper.findByFieldCn(paramMap).getFieldCondList();
            List formulaList;
            if (i.size() > 0) {
                formulaList = i;
            } else {
                List formulaJson = fieldMapper.findByFieldCnNoEngineId(paramMap).getFieldCondList();
                formulaList = formulaJson;
            }

            if (formulaList.size() > 0) {
                Iterator formula = formulaList.iterator();

                while (formula.hasNext()) {
                    FieldCond var34 = (FieldCond) formula.next();
                    String pattern = var34.getConditionValue();
                    new ArrayList();
                    JSONArray matcher = JSONObject.parseArray(var34.getConditionContent());
                    exp = "";

                    for (int subexp = 0; subexp < matcher.size(); ++subexp) {
                        JSONObject j = ((JSONArray) matcher).getJSONObject(subexp);
                        paramMap.put("id", j.getString("fieldId"));
                        Field evaluator = fieldMapper.findByFieldId(paramMap);
                        if (evaluator == null) {
                            evaluator = fieldMapper.findByFieldCnNoEngineId(paramMap);
                        }

                        b = evaluator.getEnName();
                        String e = evaluator.getCnName();
                        Integer paramCond = evaluator.getValueType();
                        String fieldCond = j.getString("fieldValue");
                        String jsonArr = j.getString("operator");
                        v = (String) param2.get(b);
                        String job = "";
                        if (evaluator.getIsDerivative().intValue() == 0) {
                            if (j.containsKey("logical")) {
                                job = " " + j.getString("logical") + " ";
                            }

                            if (jsonArr.equals("in")) {
                                exp = exp + "(indexOf(\'" + fieldCond + "\',\'" + v + "\',0) >= 0)" + job;
                            } else if (jsonArr.equals("not in")) {
                                exp = exp + "(indexOf(\'" + fieldCond + "\',\'" + v + "\',0) = -1)" + job;
                            } else if (jsonArr.equals("like")) {
                                exp = exp + "(indexOf(\'" + v + "\',\'" + fieldCond + "\',0) >= 0)" + job;
                            } else if (jsonArr.equals("not like")) {
                                exp = exp + "(indexOf(\'" + v + "\',\'" + fieldCond + "\',0) = -1)" + job;
                            } else if (paramCond.intValue() != 1 && paramCond.intValue() != 4) {
                                exp = exp + " (\'" + v + "\'" + jsonArr + "\'" + fieldCond + "\') " + job;
                            } else {
                                exp = exp + " (" + v + jsonArr + fieldCond + ") " + job;
                            }
                        } else {
                            if (j.containsKey("logical")) {
                                job = " " + j.getString("logical") + " ";
                            }

                            if (jsonArr.equals("in")) {
                                exp = exp + "(indexOf(\'" + fieldCond + "\',\'" + getExpAll(e, "", param2) + "\',0) >= 0)" + job;
                            } else if (jsonArr.equals("not in")) {
                                exp = exp + "(indexOf(\'" + fieldCond + "\',\'" + getExpAll(e, "", param2) + "\',0) = -1)" + job;
                            } else if (jsonArr.equals("like")) {
                                exp = exp + "(indexOf(\'" + getExpAll(e, "", param2) + "\',\'" + fieldCond + "\',0) >= 0)" + job;
                            } else if (jsonArr.equals("not like")) {
                                exp = exp + "(indexOf(\'" + getExpAll(e, "", param2) + "\',\'" + fieldCond + "\',0) = -1)" + job;
                            } else if (paramCond.intValue() != 1 && paramCond.intValue() != 4) {
                                exp = exp + " (\'" + getExpAll(e, "", param2) + "\'" + jsonArr + "\'" + fieldCond + "\') " + job;
                            } else {
                                exp = exp + " (" + getExpAll(e, "", param2) + jsonArr + fieldCond + ") " + job;
                            }
                        }
                    }

                    Evaluator evaluator = new Evaluator();
                    String str = "";

                    try {
                        System.out.println("========字段区域设置的的表达式输出：" + exp);
                        str = evaluator.evaluate(exp);
                    } catch (EvaluationException e) {
                        e.printStackTrace();
                        logger.error("请求异常", e);
                    }

                    if (str.equals("1.0")) {
                        result = pattern;
                        break;
                    }
                }
            }
        }

        return result;
    }

    private String calcFieldCond(Map<String, Object> paramMap) {
        String fieldValue = (String) paramMap.get("fieldValue");
        Integer fieldValueType = (Integer) paramMap.get("fieldValueType");
        String result = "";
        JSONArray jsonArr = (JSONArray) paramMap.get("fieldCond");
        Iterator iterator = jsonArr.iterator();

        while (iterator.hasNext()) {
            JSONObject job = (JSONObject) iterator.next();
            String inputOne = (String) job.get("inputOne");
            String inputThree = (String) job.get("inputThree");
            if (fieldValueType.intValue() == 3) {
                if (fieldValue.equals(inputOne)) {
                    result = inputThree;
                    break;
                }
            } else if (fieldValueType.intValue() == 1 || fieldValueType.intValue() == 4) {
                Double lv = Double.valueOf(Double.parseDouble(inputOne.substring(1, inputOne.indexOf(","))));
                Double rv = Double.valueOf(Double.parseDouble(inputOne.substring(inputOne.indexOf(",") + 1, inputOne.length() - 1)));
                String exp = "";
                if (inputOne.startsWith("(") && !lv.equals("")) {
                    exp = fieldValue + ">" + lv;
                }

                if (inputOne.startsWith("[") && !lv.equals("")) {
                    exp = fieldValue + ">=" + lv;
                }

                if (inputOne.endsWith(")") && !rv.equals("")) {
                    if (exp.equals("")) {
                        exp = exp + fieldValue + "<" + rv;
                    } else {
                        exp = exp + "&&" + fieldValue + "<" + rv;
                    }
                }

                if (inputOne.endsWith("]") && !rv.equals("")) {
                    if (exp.equals("")) {
                        exp = exp + fieldValue + "<=" + rv;
                    } else {
                        exp = exp + "&&" + fieldValue + "<=" + rv;
                    }
                }

                Evaluator evaluator = new Evaluator();
                String b = "";

                try {
                    b = evaluator.evaluate(exp);
                } catch (EvaluationException e) {
                    e.printStackTrace();
                    logger.error("请求异常", e);
                }

                if (b.equals("1.0")) {
                    result = inputThree;
                    break;
                }
            }
        }

        return result;
    }

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^(-|\\+)?\\d+(\\.\\d+)?$");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    public List<Field> getEngineByField(EngineVersion engineVersion, Map<String, Object> map) {
        engineVersion = engineVersionMapper.selectByPrimaryKey(engineVersion.getVerId().longValue());
        Engine engine = new Engine();
        engine.setId(engineVersion.getEngineId());
        engine.setUserId(Long.valueOf(map.get("userId").toString()));//2017/5/17
        engine.setOrgId(Long.valueOf(map.get("orgId").toString()));//2017/5/17
        ArrayList fields = new ArrayList();
        if (engineVersion != null) {
            List engineNodelist = engineNodeMapper.queryNodeListByVerId(engineVersion.getVerId());

            for (int i = 0; i < engineNodelist.size(); i++) {
                if (map != null && ((EngineNode) engineNodelist.get(i)).getNodeType().intValue() == 13) {
                    map.put("type", Integer.valueOf(13));
                    ArrayList complex = new ArrayList();
                    getEngineNodeByField((EngineNode) engineNodelist.get(i), complex, engine);
                    map.put("complex", complex);
                } else {
                    getEngineNodeByField((EngineNode) engineNodelist.get(i), fields, engine);
                }
            }
        }

        return fields;
    }

    private void getEngineNodeByField(EngineNode engineNode, List<Field> fields, Engine engine) {
        switch (engineNode.getNodeType().intValue()) {
            case 2:
                getEngineRuleByField(engineNode, fields, engine);
                break;
            case 3:
                getCustomerSegmentationByFiled(engineNode, fields, engine);
                break;
            case 4:
                getScoreCardByField(engineNode, fields, engine);
                break;
            case 5:
                getEngineBlackOrWhiteByField(engineNode, fields, engine);
                break;
            case 6:
                getEngineBlackOrWhiteByField(engineNode, fields, engine);
            case 7:
            default:
                break;
            case 9:
                getDecisionOptionsByField(engineNode, fields, engine);
                break;
            case 13:
                getEngineRuleByField(engineNode, fields, engine);
        }

    }

    private void getEngineBlackOrWhiteByField(EngineNode engineNode, List<Field> fields, Engine engine) {
        HashMap param = new HashMap();
        param.put("userId", engine.getUserId());//2017/5/17
        param.put("nodeId", engineNode.getNodeId());
        NodeListDb nodeDbList = nodeListDbMapper.findByNodeId(param);
        String strkeyIds = "";
        String strlistDbIds = nodeDbList.getInsideListdbs();
        String[] arraylistDBIds = null;
        if (strlistDbIds.length() > 0) {
            arraylistDBIds = strlistDbIds.split(",");

            for (int str = 0; str < arraylistDBIds.length; ++str) {
                Integer list = Integer.valueOf(Integer.valueOf(arraylistDBIds[str]).intValue());
                param.put("listDbId", list);
                new ListDb();
                ListDb keyIdList = listDbMapper.findListDbById(param);
                String paramMap = keyIdList.getQueryField();
                if (paramMap.length() > 0) {
                    strkeyIds = strkeyIds + paramMap + ",";
                }
            }
        }

        //2017/6/1更改，加strkeyIds非空的判断
        ArrayList arrayList = new ArrayList();
        if (!"".equals(strkeyIds)) {
            String[] strId = strkeyIds.subSequence(0, strkeyIds.length() - 1).toString().split(",");
            for (int i = 0; i < strId.length; i++) {
                if (!arrayList.contains(strId[i])) {
                    arrayList.add(strId[i]);
                }
            }
        }

        String str = "";
        if (arrayList != null && arrayList.size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                str = str + (String) arrayList.get(i) + ',';
            }

            str = str.substring(0, str.length() - 1);
        }

        HashMap map = new HashMap();
        List Ids = StringUtil.toLongList(str);
        if(Ids.size() > 0){
            map.put("Ids", Ids);
        }
        map.put("engineId", engine.getId());
        map.put("userId", engine.getUserId());
        List fieldList = fieldMapper.findFieldByIds(map);
        if (fields == null) {
            fields = new ArrayList();
        }

        ((List) fields).addAll(fieldList);
    }

    private void getEngineRuleByField(EngineNode engineNode, List<Field> fields, Engine engine) {
        NodeKnowledge knowledge = new NodeKnowledge();
        knowledge.setKnowledgeType(Integer.valueOf(1));
        knowledge.setNodeId(engineNode.getNodeId());
        List contents = ruleFieldMapper.selectNodeByRuleList(knowledge);
        ArrayList ids = new ArrayList();

        for (int paramMap = 0; paramMap < contents.size(); paramMap++) {
            RuleField userId = (RuleField) contents.get(paramMap);
            ids.add(Long.valueOf(userId.getFieldId().split("\\|")[0]));
        }

        HashMap map = new HashMap();
        map.put("Ids", ids);
        map.put("engineId", engine.getId());
        Long userId = engine.getUserId();//
        map.put("userId", userId);
        List fieldList = fieldMapper.findFieldByIds(map);
        ArrayList list = new ArrayList();
        ids = new ArrayList();

        for (int lists = 0; lists < fieldList.size(); ++lists) {
            if (((Field) fieldList.get(lists)).getIsDerivative().intValue() == 1) {
                ids.addAll(StringUtil.toLongList(((Field) fieldList.get(lists)).getProtogeneFieldId()));
            } else {
                list.add((Field) fieldList.get(lists));
            }
        }

        if (ids.size() > 0) {
            map.put("Ids", ids);
            List var12 = fieldMapper.findFieldByIds(map);
            list.addAll(var12);
        }

        if (fields == null) {
            fields = new ArrayList();
        }

        ((List) fields).addAll(list);
    }

    private void getScoreCardByField(EngineNode engineNode, List<Field> fields, Engine engine) {
        NodeKnowledge knowledge = new NodeKnowledge();
        knowledge.setKnowledgeType(Integer.valueOf(2));
        knowledge.setNodeId(engineNode.getNodeId());
        List scorecards = scorecardMapper.selectNodeByScCodeList(knowledge);
        List scorecardRuleContents = scorecardContentMapper.selectNodeByScCodeFieldList(knowledge);
        ArrayList ids = new ArrayList();
        selectscorecardRuleContentsField(scorecardRuleContents, ids);
        selectScorecardField(scorecards, ids);
        HashMap paramMap = new HashMap();
        paramMap.put("userId", engine.getUserId());//2017/5/17
        paramMap.put("orgId", engine.getOrgId());//2017/5/17
        paramMap.put("Ids", ids);
        paramMap.put("engineId", engine.getId());
        List fieldList = fieldMapper.findFieldByIds(paramMap);
        ArrayList list = new ArrayList();
        ids = new ArrayList();

        for (int lists = 0; lists < fieldList.size(); lists++) {
            if (((Field) fieldList.get(lists)).getIsDerivative().intValue() == 1) {
                ids.addAll(StringUtil.toLongList(((Field) fieldList.get(lists)).getProtogeneFieldId()));
            } else {
                list.add((Field) fieldList.get(lists));
            }
        }

        if (ids.size() > 0) {
            paramMap.put("Ids", ids);
            List fieldList1 = fieldMapper.findFieldByIds(paramMap);
            list.addAll(fieldList1);
        }

        if (fields == null) {
            fields = new ArrayList();
        }

        ((List) fields).addAll(list);
    }

    private void enginecomplexFiledBynode(Map<String, Object> inputParam, EngineNode engineNode, Integer type) {
        NodeKnowledge knowledge = new NodeKnowledge();
        knowledge.setKnowledgeType(type);
        knowledge.setNodeId(engineNode.getNodeId());
        List ruleFields = ruleFieldMapper.getNodeByList(knowledge);
        ArrayList ruleidlist = new ArrayList();

        for (int filedMap = 0; filedMap < ruleFields.size(); ++filedMap) {
            Long list = Long.valueOf(((RuleField) ruleFields.get(filedMap)).getFieldId().split("\\|")[0]);
            ruleidlist.add(list);
        }

        HashMap hashMap = new HashMap();
        hashMap.put("Ids", ruleidlist);
        hashMap.put("isDerivative", Integer.valueOf(1));
        ArrayList var15 = new ArrayList();
        JSONArray array = JSONArray.parseArray(inputParam.get("complexfield").toString());

        for (int fieldList = 0; fieldList < array.size(); ++fieldList) {
            JSONObject y = array.getJSONObject(fieldList);
            Map n = (Map) JSON.parseObject(y.toString(), Map.class);
            ComplexRule complexRule = new ComplexRule();
            complexRule.setResult(n);
            var15.add(complexRule);
        }

        inputParam.put(engineNode.getNodeId().toString(), var15);
        List list = fieldMapper.findFieldByIdsAndIsderivative(hashMap);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < var15.size(); ++j) {
                    ((ComplexRule) var15.get(j)).getResult().put(((Field) list.get(i)).getEnName(), (Object) null);
                    getFieldResult(((ComplexRule) var15.get(j)).getResult());
                }
            }
        }

    }

    public Map<String, Object> getFieldResult(Map<String, Object> paramMap, Map<String, Object> paramJson) {
        HashMap paramMap2 = new HashMap();
        Iterator organId = paramMap.entrySet().iterator();

        while (organId.hasNext()) {
            Entry userId = (Entry) organId.next();
            if (userId.getValue() != null) {
                paramMap2.put((String) userId.getKey(), userId.getValue());
            } else {
                paramMap2.put((String) userId.getKey(), "");
            }
        }

        Long userId1 = (Long) paramMap.get("userId");//用户id
        Long organId1 = (Long) paramMap.get("organId");//组织id
        paramMap2.put("organId", organId1);
        Iterator iterator = paramMap.entrySet().iterator();

        while (true) {
            String fieldEn;
            String fieldValue;
            do {
                if (!iterator.hasNext()) {
                    return paramMap;
                }

                Entry entry = (Entry) iterator.next();
                fieldEn = (String) entry.getKey();
                fieldValue = "";
                if (entry.getValue() != null) {
                    fieldValue = String.valueOf(entry.getValue());
                }
            } while (fieldValue != null && !fieldValue.equals(""));

            paramMap2.put("fieldEn", fieldEn);
            Field field = fieldMapper.findByFieldEnbyorganId(paramMap2);
            if (field != null && field.getIsDerivative().intValue() == 1) {
                String result = "";
                paramMap2.put("fieldEn", fieldEn);
                paramMap2.put("fieldCn", fieldMapper.findByFieldEnbyorganId(paramMap2).getCnName());
                result = getExpAll(fieldMapper.findByFieldEnbyorganId(paramMap2).getCnName(), "", paramMap);
                result = result.replace("(", "");
                result = result.replace(")", "");
                paramMap.put(fieldEn, result);
            }
        }
    }

    private void selectscorecardRuleContentsField(List<ScorecardRuleContent> scorecardRuleContents, List<Long> ids) {
        for (int i = 0; i < scorecardRuleContents.size(); i++) {
            getFieldIds(((ScorecardRuleContent) scorecardRuleContents.get(i)).getFieldValue(), ids);
        }

    }

    private void selectScorecardField(List<Scorecard> scorecards, List<Long> ids) {
        for (int i = 0; i < scorecards.size(); i++) {
            if (((Scorecard) scorecards.get(i)).getScore() != null) {
                getFieldIds(((Scorecard) scorecards.get(i)).getScore(), ids);
            }

            if (((Scorecard) scorecards.get(i)).getPd() != null) {
                getFieldIds(((Scorecard) scorecards.get(i)).getPd(), ids);
            }

            if (((Scorecard) scorecards.get(i)).getOdds() != null) {
                getFieldIds(((Scorecard) scorecards.get(i)).getOdds(), ids);
            }
        }

    }

    private void getFieldIds(String filedjson, List<Long> ids) {
        JSONObject jsonObject = JSONObject.parseObject(filedjson);
        JSONArray array = jsonObject.getJSONArray("fields");

        for (int i = 0; i < array.size(); i++) {
            Long id = array.getJSONObject(i).getLong("field_id");
            ids.add(id);
        }

    }

    private void getDecisionOptionsByField(EngineNode engineNode, List<Field> fields, Engine engine) {
        JSONObject jsonObject = JSONObject.parseObject(engineNode.getNodeScript());
        JSONArray array = jsonObject.getJSONArray("input");
        ArrayList ids = new ArrayList();

        for (int paramMap = 0; paramMap < array.size(); paramMap++) {
            JSONObject userId = array.getJSONObject(paramMap);
            ids.add(userId.getLong("field_id"));
        }

        HashMap map = new HashMap();
        map.put("userId", engine.getUserId());//2017/5/17
        map.put("orgId", engine.getOrgId());//2015/5/17
        map.put("Ids", ids);
        map.put("engineId", engine.getId());
        List fieldList = fieldMapper.findFieldByIdsbyorganId(map);
        ArrayList list = new ArrayList();
        ids = new ArrayList();

        for (int lists = 0; lists < fieldList.size(); ++lists) {
            if (((Field) fieldList.get(lists)).getIsDerivative().intValue() == 1) {
                ids.addAll(StringUtil.toLongList(((Field) fieldList.get(lists)).getProtogeneFieldId()));
            } else {
                list.add((Field) fieldList.get(lists));
            }
        }

        if (ids.size() > 0) {
            map.put("Ids", ids);
            List var13 = fieldMapper.findFieldByIdsbyorganId(map);
            list.addAll(var13);
        }

        if (fields == null) {
            fields = new ArrayList();
        }

        ((List) fields).addAll(list);
    }

    private void getCustomerSegmentationByFiled(EngineNode engineNode, List<Field> fields, Engine engine) {
        JSONObject jsonObject = JSONObject.parseObject(engineNode.getNodeScript());
        JSONArray array = jsonObject.getJSONArray("fields");
        ArrayList ids = new ArrayList();

        for (int paramMap = 0; paramMap < array.size(); ++paramMap) {
            JSONObject userId = array.getJSONObject(paramMap);
            ids.add(userId.getLong("field_id"));
        }

        HashMap map = new HashMap();
        map.put("userId", engine.getUserId());//2017/5/17
        map.put("orgId", engine.getOrgId());//2017/5/17
        map.put("Ids", ids);
        map.put("engineId", engine.getId());
        List fieldList = fieldMapper.findFieldByIdsbyorganId(map);
        ArrayList list = new ArrayList();
        ids = new ArrayList();

        for (int lists = 0; lists < fieldList.size(); ++lists) {
            if (((Field) fieldList.get(lists)).getIsDerivative().intValue() == 1) {
                ids.addAll(StringUtil.toLongList(((Field) fieldList.get(lists)).getProtogeneFieldId()));
            } else {
                list.add((Field) fieldList.get(lists));
            }
        }

        if (ids.size() > 0) {
            map.put("Ids", ids);
            List list1 = fieldMapper.findFieldByIdsbyorganId(map);
            list.addAll(list1);
        }

        if (fields == null) {
            fields = new ArrayList();
        }

        ((List) fields).addAll(list);
    }

    public List<Engine> queryEngineList(long organId, String searchString, List<Integer> list) {
        return engineMapper.getEngineList(organId, searchString, list);
    }

    public List<Engine> queryTestEngineListByIds(Map<String, Object> paramMap) {
        return engineMapper.getTestEngineListByIds(paramMap);
    }
}
