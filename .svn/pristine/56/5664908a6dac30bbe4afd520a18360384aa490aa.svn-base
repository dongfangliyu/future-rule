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
import com.zw.rule.https.HttpClient;
import com.zw.rule.jeval.EvaluationException;
import com.zw.rule.jeval.Evaluator;
import com.zw.rule.jeval.tools.JevalUtil;
import com.zw.rule.jeval.tools.MD5;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EngineApiServiceImpl implements EngineApiService {
    private static final Logger logger = Logger.getLogger(EngineApiServiceImpl.class);
    public EngineApiServiceImpl() {
    }
    @Resource
    private EngineMapper engineMapper;
    @Resource
    private NodeListDbMapper nodeListDbMapper;
    @Resource
    private CustListMapper custListMapper;
    @Resource
    private FieldMapper fieldMapper;
    @Resource
    private ScorecardMapper scorecardMapper;
    @Resource
    private RuleMapper ruleMapper;
    @Resource
    private EngineVersionMapper engineVersionMapper;
    @Resource
    private EngineNodeMapper engineNodeMapper;
    @Resource
    private RuleFieldMapper ruleFieldMapper;
    @Resource
    private ListDbMapper listDbMapper;
    @Resource
    private ScorecardRuleContentMapper scorecardContentMapper;
    @Resource
    private TblColumnMapper tblColumnMapper;
    @Resource
    private EngineResultSetMapper engineResultSetMapper;
    @Resource
    public ResultSetListMapper resultSetListMapper;

    @Override
    public Map<String, Object> getEngineVersionExecute(Map<String, Object> map, String versionId) {
        EngineVersion engineVersion = engineVersionMapper.selectByPrimaryKey(Long.valueOf(versionId));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("reqType",1);
        paramMap.put("engineId",engineVersion.getEngineId());
        paramMap.put("organId",map.get("orgId"));
        paramMap.put("apiData",map);
        paramMap.put("apiType",map.get("apiType"));
        if(!paramMap.containsKey("pid")){
            paramMap.put("pid",System.currentTimeMillis());
        }
        paramMap.put("uid","api");
        paramMap.put("verId",versionId);
        JSONObject jsonObject = engineExecute(paramMap);
        Map<String, Object> mapResp = new HashMap<>();
        if(jsonObject!=null&&"0x0000".equals(jsonObject.get("status"))){
            mapResp.put("id",jsonObject.get("resultSetId"));
        }
        if(jsonObject.containsKey("msg")){
            mapResp.put("msg",jsonObject.get("msg"));
        }
        return mapResp;
    }

    public JSONObject engineExecute(Map<String, Object> paramMap) {
        logger.info("请求参数--paramMap:" + JSONObject.toJSONString(paramMap));

        JSONObject jsonResult = new JSONObject();
        JSONObject resultJson = new JSONObject();

        //获取引擎节点列表
        List<EngineNode> list = getNodeListByEngineInfo(paramMap);
        Engine engine = new Engine();
        engine.setId(Long.valueOf(paramMap.get("engineId").toString()));
        Long organId = Long.valueOf(paramMap.get("organId").toString());
        Map<String,Object> inputParam = new HashMap<>();
        if(paramMap.get("apiData")!=null){
            inputParam = (HashMap)paramMap.get("apiData");
            inputParam.put("apiType",paramMap.get("apiType"));
        }
        if(list != null) {
            //将列表中的节点转为map，key为node_code(节点代号)，值为EngineNode（节点对象）
            Map<String,EngineNode> engineNodeMap = getEngineNodeListByMap(list);
            //创建响应结果对象用于保存
            EngineResultSet resultSet = new EngineResultSet();
            try {

                //取出开始节点
                EngineNode engineNode = getEngineNodeByMap("ND_START", engineNodeMap);
                //开始节点不为空 and 关联的下个节点也不为空
                if(engineNode != null && engineNode.getNextNodes() != null) {
                    engine.setOrgId(organId);
                    //获取引擎信息
                    engine = engineMapper.getEngineById(engine);
                    //返回引擎名称
                    resultJson.put("engineName", engine.getName());
                    Map<String,Object> outMap = new HashMap<>();
                    //递归遍历节点
                    recursionEngineNode( inputParam, engineNodeMap.get(engineNode.getNextNodes()), engineNodeMap, outMap, engine, paramMap);
                    jsonResult.put("status", "0x0000");
                    jsonResult.put("msg", "执行成功");

                    //当返回结果中包含key为centens，value为true，表明从数据中心获取数据失败
                    if(outMap.containsKey("centens") && outMap.get("centens").equals("true")) {
                        jsonResult.put("status", "0x0006");
                        jsonResult.put("msg", "获取数据---"+String.valueOf(outMap.get("errorField"))+"---失败");
                        return jsonResult;
                    }
                    //保存响应结果
                    resultSet.setEngineCode(engine.getCode());
                    //保存执行过程中用到的规则和对应的字段值，用于显示引擎详情时使用
                    inputParam.put("denyRules",outMap.get("denyRules"));
                    inputParam.put("addOrSubRules",outMap.get("addOrSubRules"));
                    String input = JSONObject.toJSONString(inputParam);
                    resultSet.setInput(input);
                    resultSet.setEngineId(engine.getId());
                    resultSet.setEngineName(engine.getName());
                    resultSet.setType((int)paramMap.get("apiType"));
                    EngineVersion engineVersion = engineVersionMapper.selectByPrimaryKey(engineNode.getVerId());
                    if(paramMap.containsKey("verId")){
                        resultSet.setEngineVer(Integer.valueOf((String)paramMap.get("verId")));
                    }else{
                        resultSet.setEngineVer(engineVersion.getVerId().intValue());
                    }
                    resultSet.setBatchNo(paramMap.get("pid").toString());
                    resultSet.setSubVer(engineVersion.getSubVer());

                    if(outMap.containsKey("scorecardScore")) {
                        jsonResult.put("score", outMap.get("scorecardScore").toString());
                        resultSet.setScorecardscore(outMap.get("scorecardScore").toString());
                    }

                    //表示有规则集的某条规则被命中,获取命中黑名单
                    if(outMap.containsKey("enginefalg")&&outMap.get("enginefalg").equals("true")) {
                            resultSet.setResult("拒绝");
                            jsonResult.put("result", "拒绝");
                        //命中白名单
                    } else if(outMap.containsKey("engineWhite") && outMap.get("engineWhite").equals("true")) {
                        resultSet.setResult("通过");
                        jsonResult.put("result", "通过");
                    } else if(outMap.containsKey("formula")) {
                        resultSet.setResult(outMap.get("formula").toString());
                        jsonResult.put("result", outMap.get("formula").toString());
                    }

                    if(outMap.containsKey("formulaList")) {
                        List<String> resultSetLists = (List<String>)outMap.get("formulaList");
                        for(int i = 0; i < resultSetLists.size(); ++i) {
                            if(resultSet.getDatilResult() != null) {
                                resultSet.setDatilResult(resultSet.getDatilResult() + "，　" + resultSetLists.get(i));
                            } else {
                                resultSet.setDatilResult(resultSetLists.get(i));
                            }
                        }
                    }

                    engineResultSetMapper.addResultSet(resultSet);
                    //引擎调用结果的ResultSet的id
                    Integer id =  resultSet.getId();
                    //用于数据执行查看结果时用
                    jsonResult.put("resultSetId", id);
                    List<ResultSetList> resultSetLists  = new ArrayList<>();

                    if(outMap.containsKey("blackJson")) {
                        ListDb listDb = (ListDb)outMap.get("black");
                        getResultSetList(resultSetLists, listDb, id, 1);
                        resultJson.put("blackJson",JSONObject.parse(outMap.get("blackJson").toString()));
                    }

                    if(outMap.containsKey("whiteJson")) {
                        ListDb listDb = (ListDb)outMap.get("white");
                        getResultSetList(resultSetLists, listDb, id, 2);
                        resultJson.put("whiteJson",JSONObject.parse(outMap.get("whiteJson").toString()));
                    }

                    addResult( 2,"ruleJson",outMap, resultJson );
                    addResult( 2,"resultJson",outMap, resultJson );
                    addResult( 4,"scoreJson",outMap, resultJson );
                    addResult( 9,"decisionJson",outMap, resultJson );
                    addResult( 13,"complexrulelist",outMap, resultJson );

                    if (outMap.containsKey("rulelist")) {
                        ArrayList<Rule> ruleList = (ArrayList<Rule>) outMap.get("rulelist");

                        for (int i = 0; i < ruleList.size(); i++) {
                            ResultSetList resultSetList = new ResultSetList();
                            resultSetList.setCode(ruleList.get(i).getCode());
                            resultSetList.setName(ruleList.get(i).getName());
                            if (ruleList.get(i).getRuleType() == 0) {
                                resultSetList.setType(3);//拒绝规则
                            } else {
                                resultSetList.setType(4);//加减分规则
                            }

                            if ( ruleList.get(i).getDesc() != null) {
                                resultSetList.setDesc(ruleList.get(i).getDesc());
                            }

                            resultSetList.setResultsetId(String.valueOf(id));
                            resultSetLists.add(resultSetList);
                        }
                    }
                    if(resultSetLists.size() > 0) {
                        resultSetListMapper.insertResultSetListByList(resultSetLists);
                    }
                    //取出所有规则返回
                    addAllRules(outMap, resultJson );

                    jsonResult.put("data", resultJson);
                }
            } catch (Exception e) {
                logger.error("请求异常", e);
                jsonResult.put("status", "0x0005");
                jsonResult.put("msg", "执行失败");
                jsonResult.put("data", "");
                engineResultSetMapper.addResultSet(resultSet);
            }
        }else{
            jsonResult.put("status", "0x0009");
            jsonResult.put("msg","请部署需要执行的引擎,再执行！");
            jsonResult.put("data", "");
        }

        logger.info("响应参数:" + jsonResult.toString());
        return jsonResult;
    }

    private void addAllRules(Map outMap, JSONObject resultJson ){
        //取出所有规则返回
        List<Rule> deny_rules = (List)outMap.get("deny_rules");
        List<Rule> add_SubRules = (List)outMap.get("add_SubRules");
        List<Rule> allRules = new ArrayList<>();
        if(deny_rules!=null){
            allRules.addAll(deny_rules);
        }
        if(add_SubRules!=null){
            allRules.addAll(add_SubRules);
        }
        List<Map> mapRules = new ArrayList<>();
        for (int i = 0; i < allRules.size(); i++) {
            Map<String ,Object> tem = new HashMap<>();
            tem.put("code",allRules.get(i).getCode());
            tem.put("name",allRules.get(i).getName());
            tem.put("desc",allRules.get(i).getDesc());
            mapRules.add(tem);
        }

        JSONObject jsonTem = new JSONObject();
        jsonTem.put("resultType", "200");
        jsonTem.put("resultJson", mapRules);
        resultJson.put("ruleAll",jsonTem);
    }


    //将各个节点的返回结果放到resultJson
    private void addResult(int resultType,String name,Map outMap, JSONObject resultJson ){
        if(outMap.containsKey(name)) {
            JSONObject jsonTem = new JSONObject();
            jsonTem.put("resultType", resultType);
            jsonTem.put("resultJson", outMap.get(name));
            resultJson.put(name,jsonTem);
        }
    }

    private List<ResultSetList> getResultSetList(List<ResultSetList> resultSetLists, ListDb listDb, Integer resultId, int type) {
        ResultSetList resultSetList = new ResultSetList();
        if(listDb.getListAttribute() != null) {
            resultSetList.setCode(listDb.getListAttribute());
        }

        resultSetList.setName(listDb.getListName());
        resultSetList.setId(listDb.getId());
        resultSetList.setType(type);
        if(listDb.getListDesc() != null) {
            resultSetList.setDesc(listDb.getListDesc());
        }

        resultSetList.setResultsetId(String.valueOf(resultId));
        resultSetLists.add(resultSetList);
        return resultSetLists;
    }
    /**
     * 递归进行节点处理;<br>
     * @param  inputParam | Map<String, Object> | 必需 | 初始值为空，用于存放从数据中心获取的字段值；<br>
     * @param  engineNode | EngineNode | 必需 | 递归时当前节点对象；<br>
     * @param  engineNodeMap | Map<String, EngineNode> | 必需 | 存放所有节点的map；<br>
     * @param  outMap | Map<String, Object> | 必需 | 初始值为空，用于保存各函数间的公共参数；<br>
     * @param  engine | Engine | 必需 | 引擎对象；<br>
     * @param  paramJson | Map<String, Object> | 必需 | controller调用时传来的参数；<br>
     * @return
     */
    private void recursionEngineNode( Map<String, Object> inputParam, EngineNode engineNode, Map<String, EngineNode> engineNodeMap, Map<String, Object> outMap, Engine engine, Map<String, Object> paramJson) {
        logger.info("请求参数--inputParam:" + JSONObject.toJSONString(inputParam) + ",engineNode:" + JSONObject.toJSONString(engineNode) + ",engineNodeMap:" + JSONObject.toJSONString(engineNodeMap) + ",engine:" + JSONObject.toJSONString(engine) + ",paramJson:" + JSONObject.toJSONString(paramJson));
        //当命中黑名单后，移除下一个节点，结束递归
        if(outMap.containsKey("isBlack") && outMap.get("isBlack").equals("true")) {
            outMap.remove("nextNode");
        }
        //当命中白名单后，移除下一个节点，结束递归
        if(outMap.containsKey("isWhite") && outMap.get("isWhite").equals("true")) {
            outMap.remove("nextNode");
        }
        //取出下一个节点
        if(outMap.containsKey("nextNode")) {
            engineNode = engineNodeMap.get(outMap.get("nextNode"));
            outMap.remove("nextNode");
        }

        if(engineNode != null && engineNode.getNodeType() != NodeTypeEnum.POLICY.getValue()) {
            ArrayList<Field> fields = new ArrayList<>();
            //通过nodeType进行分节点处理，取出节点中的字段
            getEngineNodeByField(engineNode, fields, engine, paramJson);
            byte type = 1;
            //标记是否是复杂规则，后续解析数据中心的业务数据方法不同
            if(engineNode.getNodeType() == NodeTypeEnum.NODE_COMPLEXRULE.getValue()) {
                type = 2;
            }
            //从数据中心获取字段的数据值
            boolean flag = getEnginFiled(fields, paramJson.get("pid").toString(), paramJson.get("uid").toString(), inputParam, type, engineNode.getNodeId());
            if(!flag) {//获取数据失败
                outMap.put("errorField", inputParam.get("errorField"));
                outMap.put("centens", "true");
            } else {//获取数据成功
                //获取规则并执行
                getRuleEngineNode(inputParam, engineNode, outMap, paramJson, engine);
                //当前节点后还有节点，则进入递归
                if(engineNode.getNextNodes() != null && !"".equals(engineNode.getNextNodes())) {
                    EngineNode engineNodeNext = engineNodeMap.get(engineNode.getNextNodes());
                    recursionEngineNode(inputParam, engineNodeNext, engineNodeMap, outMap, engine, paramJson);
                }
            }
        } else if(engineNode != null) {
            getRuleEngineNode(inputParam, engineNode, outMap, paramJson, engine);
            if(engineNode.getNextNodes() != null && !"".equals(engineNode.getNextNodes())) {
                EngineNode newEngineNode = engineNodeMap.get(engineNode.getNextNodes());
                recursionEngineNode(inputParam, newEngineNode, engineNodeMap, outMap, engine, paramJson);
            }
        }

    }
    /*
    * 获取规则并执行,执行结果存放于outMap中
    *
    * */
    private void getRuleEngineNode(Map<String, Object> inputParam, EngineNode engineNode, Map<String, Object> outMap, Map<String, Object> paramJson, Engine engine) {
        logger.info("请求参数--,inputParam:" + JSONObject.toJSONString(inputParam) + ",engineNode:" + JSONObject.toJSONString(engineNode) + ",outMap:" + JSONObject.toJSONString(outMap) + ",paramJson:" + JSONObject.toJSONString(paramJson));
        switch(engineNode.getNodeType()) {
            case 2://规则集
                getEngineRule(engineNode, inputParam, outMap, paramJson, engine);
                break;
            case 3://客户分群
                getCustomerSegmentation(engineNode, inputParam, outMap, paramJson);
                break;
            case 4://评分卡
                getScoreCard(engineNode, inputParam, outMap, paramJson);
                break;
            case 5://黑名单
                getEngineBlackAndWhite(engineNode, inputParam, outMap, Integer.valueOf(1), paramJson);
                break;
            case 6://白名单
                getEngineBlackAndWhite(engineNode, inputParam, outMap, Integer.valueOf(2), paramJson);
                break;
            case 7://沙盒比例
                getSandboxProportion(engineNode, outMap);
                break;
            case 9://决策选项
                getDecisionOptions(engineNode, inputParam, outMap, paramJson);
                break;
            case 13://复杂规则
                getEngineComplexRule(engineNode, inputParam, outMap, paramJson);
                break;
            default:
                break;
        }

    }
    /**
     * 处理客户分群节点,通过规则，计算出下个节点的node_code存入outMap的nextNode;<br>
     * @param  inputParam | Map<String, Object> | 必需 | 初始值为空，用于存放从数据中心获取的字段值；<br>
     * @param  engineNode | EngineNode | 必需 | 递归时当前节点对象；<br>
     * @param  outMap | Map<String, Object> | 必需 | 初始值为空，用于保存各函数间的公共参数；<br>
     * @param  paramJson | Map<String, Object> | 必需 | controller调用时传来的参数；<br>
     */
    private void getCustomerSegmentation(EngineNode engineNode, Map<String, Object> inputParam, Map<String, Object> outMap, Map<String, Object> paramJson) {
        logger.info("请求参数--,inputParam:" + JSONObject.toJSONString(inputParam) + ",engineNode:" + JSONObject.toJSONString(engineNode) + ",outMap:" + JSONObject.toJSONString(outMap) + ",paramJson:" + JSONObject.toJSONString(paramJson));
        JSONObject jsonScript = JSONObject.parseObject(engineNode.getNodeScript());
        JSONArray fieldsArray = jsonScript.getJSONArray("fields");
        //遍历客户分群节点中涉及的字段
//        for(int e = 0; e < fieldsArray.size(); ++e) {
//            JSONObject fieldjsonObject = fieldsArray.getJSONObject(e);
//            String field_code = fieldjsonObject.getString("field_code");
//            //当数据中心中获取的字段值不包含时
//            if(!inputParam.containsKey(field_code)) {
//                inputParam.put(field_code, (Object)null);
//                getFieldResult(inputParam, paramJson);
//            }
//        }
        Map<String, Object> map = new HashMap<>();
        EngineVersion version = this.engineVersionMapper.selectByPrimaryKey(engineNode.getVerId());
        Long engineId = version.getEngineId();
        map.put("engineId", engineId);
        Engine engineVo = new Engine();
        engineVo.setId(engineId);
//        engineVo.setOrgId((Long) inputParam.get("orgId"));
        Engine engine = this.engineMapper.getEngineById(engineVo);
        map.put("userId", engine.getUserId());
        Field field = null;

        Map<String, Integer> fieldsMap = new HashMap<>();
        for (int i = 0; i < fieldsArray.size(); i++)
        {
            JSONObject fieldjsonObject = fieldsArray.getJSONObject(i);
            String field_code = fieldjsonObject.getString("field_code");
            Integer field_type = fieldjsonObject.getInteger("field_type");
            if (!inputParam.containsKey(field_code))
            {
                map.put("fieldEn", field_code);
                field = this.fieldMapper.findByFieldEn(map);
                if (field != null) {
                    inputParam.put(field_code, null);
                } else {
                    inputParam.put(field_code, Integer.valueOf(0));
                }
                getFieldResult(inputParam, paramJson);
            }
            fieldsMap.put(field_code, field_type);
        }

        try {
            //计算出下个节点的node_code
            Map<String, Object> variablesMap = JevalUtil.convertVariables(fieldsMap, inputParam);
            String str = handleClassify(jsonScript, variablesMap);
            outMap.put("nextNode", str);
        } catch (EvaluationException e) {
            logger.error("请求异常", e);
        }

    }
    /*
    * 处理节点中条件，返回下个节点的node_code
    * */
    private static String handleClassify(JSONObject jsonScript, Map<String, Object> variablesMap) throws EvaluationException {
        logger.info("请求参数--,jsonScript:" + jsonScript + ",variablesMap:" + JSONObject.toJSONString(variablesMap));
        JSONArray conditions = jsonScript.getJSONArray("conditions");
        String nextNode = "";
        if(conditions != null && !conditions.isEmpty()) {
            boolean flag = false;
            JSONObject formula = null;

            for(int i = 0; i < conditions.size(); ++i) {
                formula = conditions.getJSONObject(i);
                if("".equals(formula.getString("formula"))) {
                    if(nextNode.equals("")) {
                        nextNode = formula.getString("nextNode");
                    }
                } else {
                    flag = JevalUtil.evaluateBoolean(formula.getString("formula"), variablesMap);
                    if(flag) {
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
    //处理决策选项规则
    private void getDecisionOptions(EngineNode engineNode, Map<String, Object> inputParam, Map<String, Object> outMap, Map<String, Object> paramJson) {
        logger.info("请求参数--,inputParam:" + JSONObject.toJSONString(inputParam) + ",engineNode:" + JSONObject.toJSONString(engineNode) + ",outMap:" + JSONObject.toJSONString(outMap) + ",paramJson:" + JSONObject.toJSONString(paramJson));
        JSONObject jsonObject = JSONObject.parseObject(engineNode.getNodeScript());//节点脚本
        DecisionOptions decisionOptions = new DecisionOptions();
        decisionOptions.setCode(engineNode.getNodeCode());
        decisionOptions.setName(engineNode.getNodeName());
        Map<String,Object> inFields = new HashMap<>();
        Map<String,Object> outFields = new HashMap<>();
        decisionOptions.setInFields(inFields);
        decisionOptions.setOutFields(outFields);
        JSONArray inputjsonArray = jsonObject.getJSONArray("input");
        JSONArray formulajsonArray = jsonObject.getJSONArray("conditions");
        JSONObject outputJson = jsonObject.getJSONObject("output");
        String outputfield_code = outputJson.getString("field_code");
        String outputfield_name = outputJson.getString("field_name");
        int outputfield_id = outputJson.getIntValue("field_id");
        String field_type = outputJson.getString("field_type");
        String condition_type = jsonObject.getString("condition_type");
        Map<String ,Object> variablesMap = new HashMap<>();

        JSONArray resultJson;
        for(int i = 0; i < inputjsonArray.size(); ++i) {
            String formulaJson = inputjsonArray.get(i).toString();
            JSONObject e = JSONObject.parseObject(formulaJson);
            String json = e.getString("field_code");
            if(!inputParam.containsKey(json)) {
                inputParam.put(json, (Object)null);
                getFieldResult(inputParam, paramJson);
            }

            inFields.put(e.getString("field_code"), inputParam.get(json));
            if(e.containsKey("segments")) {
                resultJson = e.getJSONArray("segments");
                Double resultJson1 = isFieldValue(e.getInteger("field_type"), inputParam, json, resultJson);
                Map<String,Integer> fieldsMap = new HashMap<>();
                fieldsMap.put(json, e.getInteger("field_type"));
                variablesMap.put(json, resultJson1);
                variablesMap = JevalUtil.convertVariables(fieldsMap, variablesMap);
            } else {
                Map<String,Integer> hashMap = new HashMap<>();
                hashMap.put(json, e.getInteger("field_type"));
                variablesMap.put(json, inputParam.get(json));
                variablesMap = JevalUtil.convertVariables(hashMap, variablesMap);
            }
        }

        if(!outMap.containsKey("formulaList")) {
            outMap.put("formulaList", new ArrayList());
        }

        for(int i = 0; i < formulajsonArray.size(); ++i) {
            JSONObject formulaJson = JSONObject.parseObject(formulajsonArray.getString(i));
            try {
                boolean resultFlag;
                JSONObject jsonTem;
                if(condition_type.equals("2")) {
                    if(formulaJson.containsKey("result") && "".equals(formulaJson.getString("result"))) {
                        double flag_1 = JevalUtil.evaluateNumric(formulaJson.getString("formula"), variablesMap);
                        outFields.put("outValue", formulaJson.getString("resultKey"));
                        outFields.put("fieldId", outputfield_id);
                        outFields.put("fieldName", outputfield_name);
                        outFields.put("fieldCode", outputfield_code);
                        outMap.put("formula", flag_1);
                        ((List)outMap.get("formulaList")).add(engineNode.getNodeName() + ":" + flag_1);
                        inputParam.put(engineNode.getNodeCode() + "_" + engineNode.getNodeId(), flag_1);
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put("nodeId", engineNode.getNodeId());
                        jsonObj.put("status", "0x0000");
                        jsonObj.put("nodeName", engineNode.getNodeName());
                        jsonObj.put("outFields", JSONObject.parseObject(JSON.toJSONString(outFields)));
                        JSONArray jsonArray = (JSONArray)outMap.get("decisionJson");
                        if(!outMap.containsKey("decisionJson")) {
                            jsonArray = new JSONArray();
                        }
                        jsonArray.add(jsonObj);
                        outMap.put("decisionJson", jsonArray);
                    } else {
                        resultFlag = JevalUtil.evaluateBoolean(formulaJson.getString("formula"), variablesMap);
                        if(resultFlag) {
                            outFields.put("outValue", resultFlag);
                            outFields.put("fieldId", outputfield_id);
                            outFields.put("fieldName", outputfield_name);
                            outFields.put("fieldCode", outputfield_code);
                            outMap.put("formula", formulaJson.getString("resultKey"));
                            inputParam.put(engineNode.getNodeCode() + "_" + engineNode.getNodeId(), formulaJson.getString("result"));
                            ((List)outMap.get("formulaList")).add(engineNode.getNodeName() + ":" + formulaJson.getString("resultKey"));
                            jsonTem = new JSONObject();
                            jsonTem.put("nodeId", engineNode.getNodeId());
                            jsonTem.put("status", "0x0000");
                            jsonTem.put("nodeName", engineNode.getNodeName());
                            jsonTem.put("outFields", JSONObject.parseObject(JSON.toJSONString(outFields)));
                            resultJson = (JSONArray)outMap.get("decisionJson");
                            if(!outMap.containsKey("decisionJson")) {
                                resultJson = new JSONArray();
                            }
                            resultJson.add(jsonTem);
                            outMap.put("decisionJson", resultJson);
                        }
                    }
                } else {
                    //进行判断是否通过
                    resultFlag = JevalUtil.evaluateBoolean(formulaJson.getString("formula"), variablesMap);
                    if(resultFlag) {
                        outFields.put("outValue", formulaJson.getString("resultKey"));
                        outFields.put("fieldId", outputfield_id);
                        outFields.put("fieldName", outputfield_name);
                        outFields.put("fieldCode", outputfield_code);
                        outMap.put("formula", formulaJson.getString("resultKey"));
                        inputParam.put(engineNode.getNodeCode() + "_" + engineNode.getNodeId(), formulaJson.getString("result"));
                        ((List)outMap.get("formulaList")).add(engineNode.getNodeName() + ":" + formulaJson.getString("resultKey"));
                        jsonTem = new JSONObject();
                        jsonTem.put("nodeId", engineNode.getNodeId());
                        jsonTem.put("status", "0x0000");
                        jsonTem.put("nodeName", engineNode.getNodeName());
                        jsonTem.put("outFields", JSONObject.parseObject(JSON.toJSONString(outFields)));
                        resultJson = (JSONArray)outMap.get("decisionJson");
                        if(!outMap.containsKey("decisionJson")) {
                            resultJson = new JSONArray();
                        }
                        resultJson.add(jsonTem);
                        outMap.put("decisionJson", resultJson);
                    }
                }
            } catch (EvaluationException e) {
                logger.error("请求异常", e);
            }
        }

        ArrayList dos=new ArrayList();;
        if(outMap.containsKey("decisionoptions")) {
            dos = (ArrayList)outMap.get("decisionoptions");
        }
        dos.add(decisionOptions);
        outMap.put("decisionoptions", dos);


    }
    //处理评分卡
    private void getScoreCard(EngineNode engineNode, Map<String, Object> inputParam, Map<String, Object> outMap, Map<String, Object> paramJson) {
        logger.info("请求参数--,inputParam:" + JSONObject.toJSONString(inputParam) + ",engineNode:" + JSONObject.toJSONString(engineNode) + ",outMap:" + JSONObject.toJSONString(outMap) + ",paramJson:" + JSONObject.toJSONString(paramJson));
        //查询出节点中关联的评分卡除了固定的三个字段外，用户添加的输出字段的信息列表，engineNode.getNodeJson()存放的是评分卡的id
        List<ScorecardRuleContent> contents = scorecardContentMapper.getRuleContentList(Long.valueOf(engineNode.getNodeJson()));
        //查询出评分卡信息
        Scorecard scorecard = scorecardMapper.selectByPrimaryKey(Long.valueOf(engineNode.getNodeJson()));
        ScoreCardEngine scoreCardEngine = new ScoreCardEngine();
        scoreCardEngine.setCode(scorecard.getCode());
        scoreCardEngine.setName(scorecard.getName());
        JSONArray inputJsonArray = new JSONArray();
        JSONArray outJsonArray = new JSONArray();
        Map<String,Object> inFields = new HashMap<>();
        Map<String,Object> outFields = new HashMap<>();

        for(int i = 0; i < contents.size(); ++i) {
            ScorecardRuleContent resultJson = contents.get(i);
            //取出评分卡字段值生成公式的json
            String fieldValue = resultJson.getFieldValue();
            JSONObject fieldValueJson = JSONObject.parseObject(fieldValue);
            JSONArray array = fieldValueJson.getJSONArray("fields");
            JSONObject outputJson = fieldValueJson.getJSONObject("output");
            JSONObject outJson = new JSONObject();
            outJson.put("fieldId", outputJson.getIntValue("field_id"));
            outJson.put("fieldName", outputJson.getString("field_name"));
            outJson.put("fieldCode", outputJson.getString("field_code"));
            String exp = fieldValueJson.getString("formula");
            Object variablesMap = new HashMap();
            //遍历字段id
            for(int j = 0; j < array.size(); ++j) {
                JSONObject jsonObject = JSONObject.parseObject(array.getString(j));
                JSONObject inputJson = new JSONObject();
                String field_code = jsonObject.getString("field_code");
                inputJson.put("fieldId", jsonObject.getIntValue("field_id"));
                inputJson.put("fieldCode", field_code);
                inputJson.put("fieldName", jsonObject.getString("field_name"));
                inputJson.put("fieldValue", inputParam.get(field_code));
                if(!inputParam.containsKey(field_code)) {
                    inputParam.put(field_code, (Object)null);
                    getFieldResult(inputParam, paramJson);
                }

                inFields.put(field_code, inputParam.get(field_code));
                JSONArray segments = jsonObject.getJSONArray("segments");
                //进行字段的区间运算
                Double segmentsvalue = isFieldValue(jsonObject.getInteger("field_type"), inputParam, field_code, segments);
                inputJson.put("calcValue", segmentsvalue);
                inputJsonArray.add(inputJson);
                HashMap fieldsMap = new HashMap();
                fieldsMap.put(field_code, jsonObject.getInteger("field_type"));
                ((Map)variablesMap).put(field_code, segmentsvalue);
                variablesMap = JevalUtil.convertVariables(fieldsMap, (Map)variablesMap);
            }

            try {
                Double var29 = JevalUtil.evaluateNumric(exp, (Map)variablesMap);
                outFields.put(outputJson.getString("field_code"), var29);
                outJson.put("outValue", var29);
                outJsonArray.add(outJson);
                inputParam.put(scorecard.getCode() + "_" + outputJson.getString("field_code"), var29);
                inputParam.put(scorecard.getCode(), var29);
            } catch (EvaluationException e) {
                logger.error("请求异常", e);
            }
        }
        //设置评分卡评分值
        if(scorecard.getScore() != null) {
            selectScoreCardFinal(scorecard.getScore(), "scorecardScore", outFields, inputParam, scorecard, inFields, paramJson, inputJsonArray, outJsonArray);
        }
//        //设置评分卡PD值
//        if(scorecard.getPd() != null) {
//            selectScoreCardFinal(scorecard.getPd(), "pd", outFields, inputParam, scorecard, inFields, paramJson, inputJsonArray, outJsonArray);
//        }
//        //设置评分卡ODDS值
//        if(scorecard.getOdds() != null) {
//            selectScoreCardFinal(scorecard.getOdds(), "odds", outFields, inputParam, scorecard, inFields, paramJson, inputJsonArray, outJsonArray);
//        }

        scoreCardEngine.setOutFields(outFields);
        scoreCardEngine.setInFields(inFields);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nodeId", engineNode.getNodeId());
        jsonObject.put("status", "0x0000");
        jsonObject.put("nodeName", engineNode.getNodeName());
        jsonObject.put("cardId", scorecard.getId());
        jsonObject.put("cardName", scorecard.getName());
        if(scorecard.getDesc() != null) {
            jsonObject.put("desc", scorecard.getDesc());
        }

        jsonObject.put("outFields", outJsonArray);
        jsonObject.put("inFields", inputJsonArray);
        JSONArray jsonArray;
        if(outMap.containsKey("scoreJson")) {
            jsonArray = (JSONArray)outMap.get("scoreJson");
            jsonArray.add(jsonObject);
        } else {
            jsonArray = new JSONArray();
            jsonArray.add(jsonObject);
            outMap.put("scoreJson", jsonArray);
        }

        outMap.put(scoreCardEngine.getCode(), scoreCardEngine);

        double   scoreBig   =   Double.valueOf(String.valueOf(scoreCardEngine.getOutFields().get("scorecardScore")));
        BigDecimal bigDecimal   =   new   BigDecimal(scoreBig);
        double   resuleScore   =   bigDecimal.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();

        outMap.put("scorecardScore", resuleScore);
    }
    //计算得分卡中评分字段的数值
    private void selectScoreCardFinal(String json, String name, Map<String, Object> outFields, Map<String, Object> inputParam, Scorecard scorecard, Map<String, Object> inFields, Map<String, Object> paramJson, JSONArray inputJsonArray, JSONArray outJsonArray) {
        logger.info("请求参数--,json:" + json + ",name:" + name + ",outFields:" + JSONObject.toJSONString(outFields) + ",inputParam:" + JSONObject.toJSONString(inputParam) + ",scorecard:" + JSONObject.toJSONString(scorecard) + ",inFields:" + JSONObject.toJSONString(inFields) + ",paramJson:" + JSONObject.toJSONString(paramJson));
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONArray array = jsonObject.getJSONArray("fields");
        JSONObject outputJson = jsonObject.getJSONObject("output");
        JSONObject outJson = new JSONObject();
        outJson.put("fieldId", outputJson.getIntValue("field_id"));
        outJson.put("fieldName", outputJson.getString("field_name"));
        outJson.put("fieldCode", outputJson.getString("field_code"));
        Map<String,Object> variablesMap = new HashMap<>();
        String exp = jsonObject.getString("formula");

        for(int i = 0; i < array.size(); ++i) {
            JSONObject e = JSONObject.parseObject(array.getString(i));
            JSONObject inputJson = new JSONObject();
            String field_code = e.getString("field_code");
            inputJson.put("fieldId", e.getIntValue("field_id"));
            inputJson.put("fieldCode", field_code);
            inputJson.put("fieldName", e.getString("field_name"));
            //给衍生字段赋值
            if(!inputParam.containsKey(field_code)) {
                inputParam.put(field_code, (Object)null);
                getFieldResult(inputParam, paramJson);
            }
            inputJson.put("fieldValue", inputParam.get(field_code));

            inFields.put(field_code, inputParam.get(field_code));
            JSONArray segments = e.getJSONArray("segments");
            Double segmentsvalue = isFieldValue(e.getInteger("field_type"), inputParam, field_code, segments);
            inputJson.put("calcValue", segmentsvalue);
            inputJsonArray.add(inputJson);
            HashMap fieldsMap = new HashMap();
            fieldsMap.put(field_code, e.getInteger("field_type"));
            ((Map)variablesMap).put(field_code, segmentsvalue);
            variablesMap = JevalUtil.convertVariables(fieldsMap, (Map)variablesMap);
        }

        try {
            Double doubleValue = JevalUtil.evaluateNumric(exp, (Map)variablesMap);
            outFields.put(name, doubleValue);
            outJson.put("outValue", doubleValue);
            outJsonArray.add(outJson);
            inputParam.put(scorecard.getCode() + "_" + outputJson.getString("field_code"), doubleValue);
            if(name.equals("scorecardScore")) {
                inputParam.put("scorecardScore", doubleValue);
            }
        } catch (EvaluationException e) {
            logger.error("请求异常", e);
        }

    }
    //计算出字段通过设定区间后的值
    private Double isFieldValue(Integer type, Map<String, Object> map, String code, JSONArray segments) {
        //type,字段存值类型,待选(0),数值型(1),字符型(2),枚举型(3),小数型(4)
        logger.info("请求参数--,type:" + type + ",map:" + JSONObject.toJSONString(map) + ",code:" + JSONObject.toJSONString(code) + ",segments:" + JSONObject.toJSONString(segments));
        double fieldValue = 0.0D;
        int i;
        JSONObject jsonObject;
        if(type == 3) {
            for(i = 0; i < segments.size(); ++i) {
                jsonObject = segments.getJSONObject(i);
                if(map.get(code).equals(jsonObject.get("segment"))) {
                    fieldValue = jsonObject.getDoubleValue("value");
                    return fieldValue;
                }
            }
        } else {
            for(i = 0; i < segments.size(); ++i) {
                jsonObject = segments.getJSONObject(i);
                String exp = JevalUtil.getNumericInterval(jsonObject.get("segment").toString(), code);

                try {
                    if(JevalUtil.evaluateBoolean(exp, map)) {
                        fieldValue = jsonObject.getDoubleValue("value");
                        return fieldValue;
                    }
                } catch (EvaluationException e) {
                    logger.error("请求异常", e);
                }
            }
        }

        return fieldValue;
    }

    //获取1-100之间的随机数，[1,100]
    public int getRandom() {
        Random random = new Random();
        return random.nextInt(100)+1;
    }

    /*
    * 通过沙盒比例中的数据，计算出下一个节点，将node_code存入outMap中nextNode中
    * */
    private Map<String, Object> getSandboxProportion(EngineNode engineNode, Map<String, Object> outMap) {
        logger.info("请求参数--engineNode:" + JSONObject.toJSONString(engineNode) + ",outMap:" + JSONObject.toJSONString(outMap));
        if (engineNode.getNodeScript() != null) {
            List<Sandbox> list = JSON.parseArray(engineNode.getNodeJson(), Sandbox.class);
//            List<Sandbox> list = JSON.parseArray(engineNode.getNodeScript(), Sandbox.class);
// 原来取值是engineNode.getNodeScript()，但是数据库中数据存储在engineNode.getNodeJson()中，老代码貌似有问题
            int ran = getRandom();
            int tem = 0;
            for (int i = 0; i < list.size(); ++i) {
                Sandbox sandbox = list.get(i);
                if (ran > tem && ran <= tem + sandbox.getProportion()) {
                    outMap.put("nextNode", sandbox.getNextNode());
                    return outMap;
                } else {
                    tem = tem + sandbox.getProportion();
                }
            }
        }
        return outMap;
    }
    /**
     * 处理规则节点;<br>
     * @param  inputParam | Map<String, Object> | 必需 | 初始值为空，用于存放从数据中心获取的字段值；<br>
     * @param  engineNode | EngineNode | 必需 | 递归时当前节点对象；<br>
     * @param  outMap | Map<String, Object> | 必需 | 初始值为空，用于保存各函数间的公共参数；<br>
     * @param  paramJson | Map<String, Object> | 必需 | controller调用时传来的参数；<br>
     */
    private Map<String, Object> getEngineRule(EngineNode engineNode, Map<String, Object> inputParam, Map<String, Object> outMap, Map<String, Object> paramJson, Engine engine) {
        getRule(inputParam, engineNode, outMap, paramJson, engine);
        addResultSet(inputParam,outMap);
        return outMap;
    }

    private void addResultSet( Map<String, Object> inputParam, Map<String, Object> outMap){
        //用于判断多节点时，取出已存的数据
        List<Map> dr = (List<Map>)outMap.get("denyRules");
        List<Map> ar = (List<Map>)outMap.get("addOrSubRules");
        if(dr==null){
            dr = new ArrayList();
        }
        if(ar==null){
            ar = new ArrayList();
        }
        //取出当前节点中的拒绝规则
        List<Rule> deny_rules =(List<Rule>)outMap.get("deny_rules");
        //将规则和参数对应
        handleRules(dr,deny_rules,inputParam);
        //取出当前节点中的加减分规则
        List<Rule> addOrSubRules =(List<Rule>)outMap.get("add_SubRules");
        //将规则和参数对应
        handleRules(ar,addOrSubRules,inputParam);
        outMap.put("denyRules",dr);
        outMap.put("addOrSubRules",ar);
    }

    private void handleRules( List<Map> maps ,List<Rule> rules,Map<String, Object> inputParam){
        if(rules!=null&&!rules.isEmpty()){
            for (int i = 0; i < rules.size(); i++) {
                if(maps.size() > i){
                    Map<String,Object> mapRus = maps.get(i);
                    String ruleId = String.valueOf(rules.get(i).getId());
                    if(mapRus != null && !ruleId.equals(String.valueOf(mapRus.get("id")))){
                        addResultSetRule(maps,rules.get(i),inputParam);
                    }
                }else{
                    addResultSetRule(maps,rules.get(i),inputParam);
                }

            }
        }
    }

    /**
     * 结果集添加规则
     * @param maps
     * @param rule
     * @param inputParam
     */
    public void addResultSetRule(List<Map> maps,Rule rule,Map<String, Object> inputParam){
        Map<String,Object> mapTem = new HashMap();
        mapTem.put("id",rule.getId());
        mapTem.put("name",rule.getName());
        mapTem.put("desc",rule.getDesc());
        mapTem.put("fieldInfo",getFiledValue(rule.getRuleFieldList(),inputParam));
        maps.add(mapTem);
    }

    private String getFiledValue(List<RuleField> ruleFields,Map<String, Object> inputParam){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ruleFields.size(); i++) {
            String tem = ruleFields.get(i).getEnName();
            if(sb.indexOf(tem) == -1){
                sb.append(tem).append(":");
                if(inputParam.containsKey(tem)){
                    sb.append(inputParam.get(tem)).append("|");
                }else {
                    sb.append("未获取").append("|");
                }
            }
        }
        return sb.toString();
    }

    private Map<String, Object> getEngineComplexRule(EngineNode engineNode, Map<String, Object> inputParam, Map<String, Object> outMap, Map<String, Object> paramJson) {
        engineComplexFiledBynode(inputParam, engineNode, 1, paramJson);
        getComplexRuleRule(inputParam, engineNode, outMap);
        return outMap;
    }

    private Map<String, Object> getComplexRuleRule(Map<String, Object> maps, EngineNode engineNode, Map<String, Object> outmap) {
        logger.info("请求参数--,outmap:" + JSONObject.toJSONString(outmap) + ",engineNode:" + JSONObject.toJSONString(engineNode) + ",maps:" + JSONObject.toJSONString(maps));
        JSONObject resultJson = new JSONObject();
        resultJson.put("nodeId", engineNode.getNodeId());
        resultJson.put("status", "0x0000");
        resultJson.put("nodeName", engineNode.getNodeName());
        JSONArray result = new JSONArray();
        List complexlist = (List)maps.get(engineNode.getNodeId().toString());

        for(int i = 0; i < complexlist.size(); ++i) {
            String nodeScript = engineNode.getNodeJson();
            JSONObject nodeJson = JSONObject.parseObject(nodeScript);
            boolean isFalg = false;
            JSONObject addOrSubRules;
            JSONObject ruleArray;
            JSONObject out;
            if(nodeJson.containsKey("deny_rules")) {
                addOrSubRules = JSONObject.parseObject(nodeJson.getString("deny_rules"));
                if(addOrSubRules != null) {
                    List<Long> ids = new ArrayList<>();
                    JSONArray list;
                    JSONObject ruleList;
                    if(addOrSubRules.get("isSerial").toString().equals("1")) {
                        list = addOrSubRules.getJSONArray("rules");

                        for(int j = 0; j < list.size(); ++j) {
                            ruleList = list.getJSONObject(j);
                            ids.add(ruleList.getLong("id"));
                        }

                        ruleMapper.selectRulesByNodeId(engineNode.getNodeId());
                        new JSONArray();
                        ruleArray = JSONObject.parseObject(((ComplexRule)complexlist.get(i)).getOut());
                        if(isFalg) {
                            outmap.put("checkfalg", 2);
                            out = new JSONObject();
                            out.put("ruleResult", "拒绝");
                            ruleArray.put("out", out);
                        }

                        result.add(ruleArray);
                    } else {
                        list = addOrSubRules.getJSONArray("rules");

                        for(int k = 0; k < list.size(); ++k) {
                            ruleList = list.getJSONObject(k);
                            ids.add(ruleList.getLong("id"));
                        }

                        List rules = ruleMapper.selectRulesByNodeId(engineNode.getNodeId());
                        JSONArray jsonArray = new JSONArray();
                        isFalg = parallelRule(((ComplexRule)complexlist.get(i)).getResult(), outmap, rules, jsonArray);
//                        ruleArray = JSONObject.parseObject(((ComplexRule)complexlist.get(i)).getOut());
                        if(isFalg) {
                            outmap.put("checkfalg", 2);
                            out = new JSONObject();
                            out.put("ruleResult", "拒绝");
//                            ruleArray.put("out", out);
                        }

//                        result.add(ruleArray);
                    }
                }
            }

            if(nodeJson.containsKey("addOrSubRules")) {
                addOrSubRules = JSONObject.parseObject(nodeJson.getString("addOrSubRules"));
                //用于存放复杂规则中每一条规则的阈值
                HashMap map = new HashMap();
                if(addOrSubRules != null) {
                    List<Long> ids = new ArrayList();
                    JSONArray rules = JSONArray.parseArray(addOrSubRules.getString("rules"));

                    for(int m = 0; m < rules.size(); ++m) {
                        ruleArray = rules.getJSONObject(m);
                        ids.add(ruleArray.getLong("id"));
                        map.put(ruleArray.getLong("id"), ruleArray.getInteger("threshold"));
                    }

                    List<Rule> ruleList = ruleMapper.selectnodeByInRoleid(ids);
                    JSONArray jsonArray = new JSONArray();
                    complexAddOrSubRules(map, (ComplexRule)complexlist.get(i), outmap, ruleList, jsonArray, engineNode, maps);
                    out = JSONObject.parseObject(((ComplexRule)complexlist.get(i)).getOut());
                    if(((ComplexRule)complexlist.get(i)).getReturnResult() != null) {
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.putAll(((ComplexRule)complexlist.get(i)).getReturnResult());
                        if(outmap.containsKey("checkfalg") && outmap.get("checkfalg").toString().equals("2")) {
                            jsonObj.put("ruleResult", "拒绝");
                        } else if(outmap.containsKey("checkfalg") && outmap.get("checkfalg").toString().equals("3")) {
                            jsonObj.put("ruleResult", "人工审批");
                        } else if(outmap.containsKey("checkfalg") && outmap.get("checkfalg").toString().equals("4")) {
                            jsonObj.put("ruleResult", "简化流程");
                        } else if(outmap.containsKey("checkfalg") && outmap.get("checkfalg").toString().equals("5")) {
                            jsonObj.put("ruleResult", "通过");
                        }

                        out.put("out", jsonObj);
                    }

                    result.add(out);
                }
            }
        }

        resultJson.put("result", result);

        JSONArray jsonArray = new JSONArray();
        if(outmap.containsKey("complexrulelist")) {
            jsonArray = (JSONArray)outmap.get("complexrulelist");
        }
        jsonArray.add(resultJson);
        outmap.put("complexrulelist", jsonArray);

        if(outmap.containsKey("checkfalg") && outmap.get("checkfalg").toString().equals("2")) {
            outmap.put("enginefalg", "true");
            engineNode.setNextNodes((String)null);
        } else if(outmap.containsKey("checkfalg") && outmap.get("checkfalg").toString().equals("3")) {
            outmap.put("formula", "人工审批");
        } else if(outmap.containsKey("checkfalg") && outmap.get("checkfalg").toString().equals("4")) {
            outmap.put("formula", "简化流程");
        } else if(outmap.containsKey("checkfalg") && outmap.get("checkfalg").toString().equals("5")) {
            outmap.put("formula", "通过");
        }

        return outmap;
    }

    private void complexAddOrSubRules(Map<Long, Integer> threshold, ComplexRule complexRule, Map<String, Object> outmap, List<Rule> rules, JSONArray ruleArray, EngineNode engineNode, Map<String, Object> maps) {
        logger.info("请求参数--threshold:" + JSONObject.toJSONString(threshold) + ",complexRule:" + JSONObject.toJSONString(complexRule) + ",outmap:" + JSONObject.toJSONString(outmap) + ",rules:" + JSONObject.toJSONString(rules) + ",engineNode:" + JSONObject.toJSONString(engineNode) + ",maps:" + JSONObject.toJSONString(maps) + ",ruleArray:" + ruleArray);
        StatefulKnowledgeSession kSession = null;

        for(int i = 0; i < rules.size(); ++i) {
            try {
                KnowledgeBuilder e = KnowledgeBuilderFactory.newKnowledgeBuilder();
                String ruleString = rules.get(i).getContent().replace("\\r\\n", "\r\n");
                ruleString = ruleString.replace("\\t", "\t");
                e.add(ResourceFactory.newByteArrayResource(ruleString.getBytes("utf-8")), ResourceType.DRL);
                KnowledgeBuilderErrors errors = e.getErrors();
                Iterator resultList = errors.iterator();

                while(resultList.hasNext()) {
                    KnowledgeBuilderError kBase = (KnowledgeBuilderError)resultList.next();
                    System.out.println(kBase);
                }

                KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
                knowledgeBase.addKnowledgePackages(e.getKnowledgePackages());
                kSession = knowledgeBase.newStatefulKnowledgeSession();
                ArrayList<Result> arrayList = new ArrayList<>();
                InputParam inputParam = new InputParam();
                inputParam.setInputParam(complexRule.getResult());
                inputParam.setResult(arrayList);
                kSession.insert(inputParam);
                kSession.fireAllRules();
                kSession.dispose();
                if(arrayList.size() > 0) {
                    if(arrayList.get(0).getMap() != null && arrayList.get(0).getMap().size() > 0) {
                        complexRule.setReturnResult(arrayList.get(0).getMap());
                    }

                    maps.put("cr_" + engineNode.getNodeId() + "_" + rules.get(i).getCode(),1);
                    if(maps.containsKey("cs_" + engineNode.getNodeId() + "_" + rules.get(i).getCode())) {
                        if(rules.get(i).getScore() != null) {
                            maps.put("cs_" + engineNode.getNodeId() + "_" + rules.get(i).getCode(), Integer.valueOf(maps.get("cs_" + engineNode.getNodeId() + "_" + rules.get(i).getCode()).toString()) + rules.get(i).getScore());
                        }
                    } else if(rules.get(i).getScore() != null) {
                        maps.put("cs_" + engineNode.getNodeId() + "_" + rules.get(i).getCode(), rules.get(i).getScore());
                    } else {
                        maps.put("cs_" + engineNode.getNodeId() + "_" + rules.get(i).getCode(), 0);
                    }

                    if(maps.containsKey("cs_" + engineNode.getNodeId() + "_" + rules.get(i).getCode()) && rules.get(i).getScore() != null && rules.get(i).getScore() != null && threshold != null && threshold.get(rules.get(i).getId()) != null) {
                        if(Long.valueOf(maps.get("cs_" + engineNode.getNodeId() + "_" + rules.get(i).getCode()).toString()) >= (long)((Integer)threshold.get(rules.get(i).getId()))) {
                            outmap.put("checkfalg", 2);
                        } else if(outmap.containsKey("checkfalg") && Integer.valueOf(outmap.get("checkfalg").toString()) > rules.get(i).getRuleAudit()) {
                            outmap.put("checkfalg", rules.get(i).getRuleAudit());
                        }
                    }

                    arrayList.get(0).setName(rules.get(i).getName());
                    List<Rule> rulelist=(List)outmap.get("rule");
                    if(outmap.containsKey("rule")) {
                        rulelist.addAll(rulelist);
                        outmap.put("rule", rulelist);
                    } else {
                        outmap.put("rule", arrayList);
                    }

                    List<Rule> rulesTem = (List)outmap.get("rulelist");
                    if(outmap.containsKey("rulelist")) {
                        rulesTem.add(rules.get(i));
                        outmap.put("rulelist", rulesTem);
                    } else {
                        ArrayList tem = new ArrayList();
                        tem.add(rules.get(i));
                        outmap.put("rulelist", tem);
                    }
                } else {
                    if(maps.containsKey("cr_" + engineNode.getNodeId() + "_" + rules.get(i).getCode()) && maps.containsKey("cr_" + engineNode.getNodeId()) && !maps.get("cr_" + engineNode.getNodeId()).equals("1")) {
                        maps.put("cr_" + engineNode.getNodeId() + "_" + rules.get(i).getCode(), 2);
                    } else {
                        maps.put("cr_" + engineNode.getNodeId() + "_" + rules.get(i).getCode(), 2);
                    }

                    if(!maps.containsKey("cs_" + engineNode.getNodeId() + "_" + rules.get(i).getCode())) {
                        maps.put("cs_" + engineNode.getNodeId() + "_" + rules.get(i).getCode(), 0);
                    }
                }
            } catch (UnsupportedEncodingException e) {
                logger.error("请求异常", e);
            } finally {
                if(kSession != null) {
                    kSession.dispose();
                }

            }
        }

    }
    //按照规则类型进行规则的验证
    private Map<String, Object> getRule(Map<String, Object> inputParam, EngineNode engineNode, Map<String, Object> outmap, Map<String, Object> paramJson, Engine engine) {
        logger.info("请求参数--map:" + JSONObject.toJSONString(inputParam) + ",outmap:" + JSONObject.toJSONString(outmap) + ",engineNode:" + JSONObject.toJSONString(engineNode));
        String nodeScript = engineNode.getNodeJson();
        JSONObject nodeJson = JSONObject.parseObject(nodeScript);
        boolean isFalg = false;
        JSONObject addOrSubRules;
        ArrayList list;
        JSONArray array;
        JSONObject falg;
        //处理拒绝规则
        if(nodeJson.containsKey("deny_rules")) {
            addOrSubRules = JSONObject.parseObject(nodeJson.getString("deny_rules"));
            if(addOrSubRules != null) {
                list = new ArrayList();
                JSONObject fields;
                List<Rule> rules;
                if(addOrSubRules.get("isSerial").toString().equals("1")) {
                    array = addOrSubRules.getJSONArray("rules");

                    for(int i = 0; i < array.size(); ++i) {
                        fields = array.getJSONObject(i);
                        list.add(fields.getLong("id"));
                    }

                    rules = ruleMapper.selectRulesByNodeId(engineNode.getNodeId());
                    outmap.put("deny_rules",rules);
                    JSONArray jsonArray = new JSONArray();
                    //串行执行规则
                    isFalg = serialRule(inputParam, outmap, rules, jsonArray, paramJson, engineNode, engine);
                    if(isFalg) {
                        engineNode.setNextNodes((String)null);
                        falg = new JSONObject();
                        falg.put("nodeId", engineNode.getNodeId());
                        falg.put("status", "0x0000");
                        falg.put("refusedScore", "200");
                        falg.put("nodeName", engineNode.getNodeName());
                        falg.put("refusedRules", jsonArray);
                        JSONArray ruleList;
                        if(outmap.containsKey("resultJson")) {
                            ruleList = (JSONArray)outmap.get("resultJson");
                            ruleList.add(falg);
                        } else {
                            ruleList = new JSONArray();
                            ruleList.add(falg);
                            outmap.put("resultJson", ruleList);
                        }

                        outmap.put("enginefalg", "true");
                    }
                } else {
                    array = addOrSubRules.getJSONArray("rules");

                    for(int i = 0; i < array.size(); ++i) {
                        fields = array.getJSONObject(i);
                        list.add(fields.getLong("id"));
                    }

                    rules = ruleMapper.selectRulesByNodeId(engineNode.getNodeId());
                    outmap.put("deny_rules",rules);
                    TreeMap treeMap = getGroupRule(rules);
                    Iterator iterator = treeMap.entrySet().iterator();

                    while(iterator.hasNext()) {
                        Entry entry = (Entry)iterator.next();
                        List<Rule> ruleArray = (List)entry.getValue();
                        ArrayList jsonObject = new ArrayList();

                        for(int resultJson = 0; resultJson < ruleArray.size(); ++resultJson) {
                            jsonObject.add(ruleArray.get(resultJson).getId());
                        }

                        List<Field>  fieldsParam = new ArrayList<>();
                        getEngineRuleByFields(engineNode, fieldsParam, engine, paramJson, jsonObject);
                        boolean falg1 = getEnginFiled(fieldsParam, paramJson.get("pid").toString(), paramJson.get("uid").toString(), inputParam, 1, engineNode.getNodeId());
                        if(!falg1) {
                            if(inputParam.containsKey("errorField")){
                                outmap.put("errorField", String.valueOf(inputParam.get("errorField")));
                            }
                            outmap.put("centens", "true");
                            engineNode.setNextNodes((String)null);
                            return null;
                        }

                        engineRuleFiledBynode(inputParam, engineNode, 1, paramJson, jsonObject);
                        JSONArray ruleArray1 = new JSONArray();
                        //并行执行规则，命中规则返回true,否则返回false
                        isFalg = parallelRule(inputParam, outmap, ruleArray, ruleArray1);
                        if(isFalg) {
                            JSONObject jsonObj = new JSONObject();
                            jsonObj.put("nodeId", engineNode.getNodeId());
                            jsonObj.put("status", "0x0000");
                            jsonObj.put("refusedScore", "200");
                            jsonObj.put("nodeName", engineNode.getNodeName());
                            jsonObj.put("rules", ruleArray1);
                            JSONArray jsonArray=new JSONArray();
                            if(outmap.containsKey("ruleJson")) {
                                jsonArray = (JSONArray)outmap.get("ruleJson");
                            }
                            jsonArray.add(jsonObj);
                            outmap.put("ruleJson", jsonArray);

                            outmap.put("enginefalg", "true");
                            engineNode.setNextNodes((String)null);
                            return outmap;
                        }
                    }
                }
            }
        }
        //当存在加减分规则并且没有命中决绝规则时
        if(nodeJson.containsKey("addOrSubRules") && !isFalg) {
            addOrSubRules = JSONObject.parseObject(nodeJson.getString("addOrSubRules"));
            if(addOrSubRules != null) {
                list = new ArrayList<>();
                array = JSONArray.parseArray(addOrSubRules.getString("rules"));
                Double threshold = addOrSubRules.getDouble("threshold");

                for(int i = 0; i < array.size(); ++i) {
                    falg = array.getJSONObject(i);
                    list.add(falg.getLong("id"));
                }

                List<Field> fields  = new ArrayList<>();
                getEngineRuleByFields(engineNode, fields, engine, paramJson, list);
                boolean temFlag = getEnginFiled(fields, paramJson.get("pid").toString(), paramJson.get("uid").toString(), inputParam, 1, engineNode.getNodeId());
                if(!temFlag) {
                    if(inputParam.containsKey("errorField")){
                        outmap.put("errorField", String.valueOf(inputParam.get("errorField")));
                    }
                    outmap.put("centens", "true");
                    engineNode.setNextNodes((String)null);
                    return null;
                }

                engineRuleFiledBynode(inputParam, engineNode, 1, paramJson, list);
                List<Rule> rules = ruleMapper.selectnodeByInRoleid(list);
                outmap.put("add_SubRules",rules);
                JSONArray ruleArray = new JSONArray();
                isFalg = addOrSubRules(threshold, inputParam, outmap, rules, ruleArray, engineNode);
                if(isFalg) {
                    outmap.put("enginefalg", "true");
                    engineNode.setNextNodes((String)null);
                }

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("nodeId", engineNode.getNodeId());
                jsonObject.put("status", "0x0000");
                jsonObject.put("refusedScore", threshold);
                jsonObject.put("nodeName", engineNode.getNodeName());
                jsonObject.put("rules", ruleArray);
                JSONArray jsonArray;
                if(outmap.containsKey("ruleJson")) {
                    jsonArray = (JSONArray)outmap.get("ruleJson");
                    jsonArray.add(jsonObject);
                } else {
                    jsonArray = new JSONArray();
                    jsonArray.add(jsonObject);
                    outmap.put("ruleJson", jsonArray);
                }
            }
        }

        return outmap;
    }

    private TreeMap<Integer, List<Rule>> getGroupRule(List<Rule> list) {
        TreeMap map = new TreeMap();

        for(int i = 0; i < list.size(); ++i) {
            Rule rule = list.get(i);
            if(map.containsKey(rule.getPriority())) {
                List<Rule> rules = (List)map.get(rule.getPriority());
                rules.add(rule);
                map.put(rule.getPriority(), rules);
            } else {
                List<Rule> arrayList = new ArrayList<>();
                arrayList.add(rule);
                map.put(rule.getPriority(), arrayList);
            }
        }

        return map;
    }
    //加减分规则验证
    private boolean addOrSubRules(Double threshold, Map<String, Object> map, Map<String, Object> outmap, List<Rule> rules, JSONArray ruleArray, EngineNode engineNode) {
        logger.info("请求参数--map:" + JSONObject.toJSONString(map) + ",outmap:" + JSONObject.toJSONString(outmap) + ",ruleArray:" + ruleArray.toString() + ",rules:" + JSONObject.toJSONString(rules) + ",engineNode:" + JSONObject.toJSONString(engineNode));
        boolean isfalg = false;
        StatefulKnowledgeSession kSession = null;

        for(int i = 0; i < rules.size(); ++i) {
            try {
                KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
                String ruleString = (rules.get(i)).getContent().replace("\\r\\n", "\r\n");
                ruleString = ruleString.replace("\\t", "\t");
                knowledgeBuilder.add(ResourceFactory.newByteArrayResource(ruleString.getBytes("utf-8")), ResourceType.DRL);
                KnowledgeBuilderErrors errors = knowledgeBuilder.getErrors();
                Iterator resultList = errors.iterator();

                while(resultList.hasNext()) {
                    KnowledgeBuilderError kBase = (KnowledgeBuilderError)resultList.next();
                    logger.info(kBase);
                }

                KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
                knowledgeBase.addKnowledgePackages(knowledgeBuilder.getKnowledgePackages());
                kSession = knowledgeBase.newStatefulKnowledgeSession();
                ArrayList<Result> list = new ArrayList<>();
                InputParam inputParam = new InputParam();
                inputParam.setInputParam(map);
                inputParam.setResult(list);
                kSession.insert(inputParam);
                kSession.fireAllRules();
                kSession.dispose();
                if(list.size() > 0) {
                    list.get(0).getMap().put("score",rules.get(i).getScore());
                    map.put(engineNode.getNodeId() + "_" + (rules.get(i)).getCode(), 1);
                    double ruleScore = getAddOrSubRulesByScore(list);
                    if(map.containsKey("s_" + engineNode.getNodeId())) {
                        map.put("s_" + engineNode.getNodeId(), Double.valueOf(map.get("s_" + engineNode.getNodeId()).toString()) + ruleScore);
                    } else {
                        map.put("s_" + engineNode.getNodeId(), ruleScore);
                    }

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("refused", 0);
                    jsonObject.put("ruleId", (rules.get(i)).getId());
                    jsonObject.put("ruleCode", (rules.get(i)).getCode());
                    jsonObject.put("ruleName", (rules.get(i)).getName());
                    jsonObject.put("ruleResult", conversionRuleAuditCode((rules.get(i)).getRuleAudit()));
                    Result result = list.get(0);
                    if(result.getMap() != null && result.getMap().containsKey("score")) {
                        jsonObject.put("ruleScore", Double.valueOf(result.getMap().get("score").toString()));
                    }

                    if((rules.get(i)).getDesc() != null) {
                        jsonObject.put("desc", (rules.get(i)).getDesc());
                    }

                    ruleArray.add(jsonObject);
                    list.get(0).setName(rules.get(i).getName());
                    if(outmap.containsKey("rule")) {
                        List<Result> rule = (List)outmap.get("rule");
                        rule.addAll(list);
                        outmap.put("rule", rule);
                    } else {
                        outmap.put("rule", list);
                    }

                    List ruleList = (List)outmap.get("rulelist");
                    if(!outmap.containsKey("rulelist")) {
                        ruleList = new ArrayList();
                    }
                    ruleList.add(rules.get(i));
                    outmap.put("rulelist", ruleList);

                    boolean temFlag;
                    if(!outmap.containsKey(engineNode.getNodeId() + "_" + "addOrSubRules")) {
                        outmap.put(engineNode.getNodeId() + "_" + "addOrSubRules", list);
                        double thresholdTem = getAddOrSubRulesByScore(list);
                        if(threshold != null && thresholdTem >= threshold) {
                            isfalg = true;
                            temFlag = isfalg;
                            return temFlag;
                        }
                    } else {
                        List score = (List)outmap.get(engineNode.getNodeId() + "_" + "addOrSubRules");
//                        score.addAll(list);
                        outmap.put(engineNode.getNodeId() + "_" + "addOrSubRules", score);
                        double score1 = getAddOrSubRulesByScore(score);
                        map.containsKey("s_" + engineNode.getNodeId());
                        if(threshold != null && score1 >= threshold.doubleValue()) {
                            isfalg = true;
                            temFlag = isfalg;
                            return temFlag;
                        }
                    }
                } else {
                    map.put(engineNode.getNodeId() + "_" + (rules.get(i)).getCode(), 2);
                }
            } catch (UnsupportedEncodingException e) {
                logger.error("请求异常", e);
            } finally {
                if(kSession != null) {
                    kSession.dispose();
                }
            }
        }
        return isfalg;
    }

    private Double getAddOrSubRulesByScore(List<Result> results) {
        double score = 0.0D;

        for(int i = 0; i < results.size(); ++i) {
            Result result = results.get(i);
            if(result.getMap() != null && result.getMap().containsKey("score")) {
                score += Double.valueOf(result.getMap().get("score").toString());
            }
        }

        return score;
    }
    //串行执行规则
    private boolean serialRule(Map<String, Object> map, Map<String, Object> outmap, List<Rule> rules, JSONArray ruleArray, Map<String, Object> paramJson, EngineNode engineNode, Engine engine) {
        logger.info("请求参数--map:" + JSONObject.toJSONString(map) + ",outmap:" + JSONObject.toJSONString(outmap) + ",ruleArray:" + ruleArray.toString() + ",rules:" + JSONObject.toJSONString(rules));
        boolean isfalg = false;
        StatefulKnowledgeSession kSession = null;

        for(int i = 0; i < rules.size(); ++i) {
            ArrayList ruleFieldList = new ArrayList<>();
            ruleFieldList.add(rules.get(i).getId());
            ArrayList fields = new ArrayList();
            getEngineRuleByFields(engineNode, fields, engine, paramJson, ruleFieldList);
            byte type = 1;
            if(engineNode.getNodeType() == NodeTypeEnum.NODE_COMPLEXRULE.getValue()) {
                type = 2;
            }

            boolean falg = getEnginFiled(fields, paramJson.get("pid").toString(), paramJson.get("uid").toString(), map, type, engineNode.getNodeId());
            if(!falg) {
                if(map.containsKey("errorField")){
                    outmap.put("errorField", String.valueOf(map.get("errorField")));
                }
                outmap.put("centens", "true");
                engineNode.setNextNodes((String)null);
                return false;
            }

            engineRuleFiledBynode(map, engineNode, 1, paramJson, ruleFieldList);

            boolean temFlag;
            try {
                KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
                String ruleString = (rules.get(i)).getContent().replace("\\r\\n", "\r\n");
                ruleString = ruleString.replace("\\t", "\t");
                knowledgeBuilder.add(ResourceFactory.newByteArrayResource(ruleString.getBytes("utf-8")), ResourceType.DRL);
                KnowledgeBuilderErrors errors = knowledgeBuilder.getErrors();
                Iterator resultList = errors.iterator();

                while(resultList.hasNext()) {
                    KnowledgeBuilderError kBase = (KnowledgeBuilderError)resultList.next();
                    logger.info(kBase);
                }

                KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
                knowledgeBase.addKnowledgePackages(knowledgeBuilder.getKnowledgePackages());
                kSession = knowledgeBase.newStatefulKnowledgeSession();
                ArrayList<Result> arrayList = new ArrayList<>();
                InputParam inputParam = new InputParam();
                inputParam.setInputParam(map);
                inputParam.setResult(arrayList);
                kSession.insert(inputParam);
                kSession.fireAllRules();
                kSession.dispose();
                List<Result> ruleTem = (List)outmap.get("rule");
                if(outmap.containsKey("rule")) {
                    ruleTem.addAll(arrayList);
                    outmap.put("rule", ruleTem);
                } else {
                    outmap.put("rule", arrayList);
                }

                if(arrayList.size() <= 0) {
                    continue;
                }

                arrayList.get(0).setName(rules.get(i).getName());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("refused", 0);
                jsonObject.put("ruleId", (rules.get(i)).getId());
                jsonObject.put("ruleCode", (rules.get(i)).getCode());
                jsonObject.put("ruleName", (rules.get(i)).getName());
                if((rules.get(i)).getDesc() != null) {
                    jsonObject.put("desc", (rules.get(i)).getDesc());
                }

                ruleArray.add(jsonObject);
                isfalg = true;
                List<Rule> ruleList = (List)outmap.get("rulelist");
                if(!outmap.containsKey("rulelist")) {
                    ruleList= new ArrayList<>();
                }
                ruleList.add(rules.get(i));
                outmap.put("rulelist", ruleList);

                temFlag = isfalg;
            } catch (UnsupportedEncodingException e) {
                logger.error("请求异常", e);
                continue;
            } finally {
                if(kSession != null) {
                    kSession.dispose();
                }

            }
            return temFlag;
        }
        return isfalg;
    }
    //并行执行规则
    private boolean parallelRule(Map<String, Object> map, Map<String, Object> outmap, List<Rule> rules, JSONArray ruleArray) {
        logger.info("请求参数--map:" + JSONObject.toJSONString(map) + ",outmap:" + JSONObject.toJSONString(outmap) + ",ruleArray:" + ruleArray.toString() + ",rules:" + JSONObject.toJSONString(rules));
        boolean isFalg = false;
        Integer priority = 0;
        StatefulKnowledgeSession kSession = null;

        for(int i = 0; i < rules.size(); ++i) {
            try {
                if(isFalg && priority != rules.get(i).getPriority().intValue()) {
                    return isFalg;
                }

                KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
                String ruleString = rules.get(i).getContent().replace("\\r\\n", "\r\n");
                ruleString = ruleString.replace("\\t", "\t");
                knowledgeBuilder.add(ResourceFactory.newByteArrayResource(ruleString.getBytes("utf-8")), ResourceType.DRL);
                KnowledgeBuilderErrors errors = knowledgeBuilder.getErrors();
                Iterator resultList = errors.iterator();

                while(resultList.hasNext()) {
                    KnowledgeBuilderError kBase = (KnowledgeBuilderError)resultList.next();
                    logger.info(kBase);
                }

                KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
                knowledgeBase.addKnowledgePackages(knowledgeBuilder.getKnowledgePackages());
                kSession = knowledgeBase.newStatefulKnowledgeSession();
                List<Result> arrayList = new ArrayList<>();
                InputParam inputParam = new InputParam();
                inputParam.setInputParam(map);
                inputParam.setResult(arrayList);
                kSession.insert(inputParam);
                kSession.fireAllRules();
                kSession.dispose();

                List<Result>  rule = (List)outmap.get("rule");
                if(outmap.containsKey("rule")) {
                    rule.addAll(arrayList);
                    outmap.put("rule", rule);
                } else {
                    outmap.put("rule", arrayList);
                }

                if(arrayList.size() > 0) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("refused", 0);
                    jsonObject.put("ruleId", rules.get(i).getId());
                    jsonObject.put("ruleCode", rules.get(i).getCode());
                    jsonObject.put("ruleName", rules.get(i).getName());
                    if(rules.get(i).getDesc() != null) {
                        jsonObject.put("desc", rules.get(i).getDesc());
                    }

                    ruleArray.add(jsonObject);
                    arrayList.get(0).setName(rules.get(i).getName());
                    isFalg = true;
                    List<Rule>  ruleList = (List)outmap.get("rulelist");
                    if(!outmap.containsKey("rulelist")) {
                        ruleList = new ArrayList<>();
                    }
                    ruleList.add(rules.get(i));
                    outmap.put("rulelist", ruleList);
                }

                priority = rules.get(i).getPriority();
            } catch (UnsupportedEncodingException e) {
                logger.error("请求异常", e);
            } finally {
                if(kSession != null) {
                    kSession.dispose();
                }

            }
        }
        return isFalg;
    }

    private void engineRuleFiledBynode(Map<String, Object> paramMap, EngineNode engineNode, Integer type, Map<String, Object> paramJson, List<Long> ruleIdList) {
        logger.info("请求参数--paramMap:" + JSONObject.toJSONString(paramMap) + ",engineNode:" + JSONObject.toJSONString(engineNode) + ",type:" + type + ",paramJson:" + JSONObject.toJSONString(paramJson));
        Map<String,Object>  filedMap = new HashMap<>();
        filedMap.put("Ids", ruleIdList);
        filedMap.put("isDerivative", 1);
        List ruleFields = ruleFieldMapper.selectByRuleList(filedMap);
        List<Long> fields = new ArrayList<>();

        for(int i = 0; i < ruleFields.size(); ++i) {
            Long fieldList = Long.valueOf(((RuleField)ruleFields.get(i)).getFieldId().split("\\|")[0]);
            fields.add(fieldList);
        }
        Map<String,Object> hashMap = new HashMap<>();
        hashMap.put("Ids", fields);
        hashMap.put("isDerivative", 1);
        List<Field> fieldList = fieldMapper.findFieldByIdsAndIsderivative(hashMap);

        for(int j = 0; j < fieldList.size(); ++j) {
            paramMap.put(fieldList.get(j).getEnName(), (Object)null);
        }
        if(fieldList != null && fieldList.size() > 0) {
            getFieldResult(paramMap, paramJson);
        }

    }

    private void engineComplexFiledBynode(Map<String, Object> inputParam, EngineNode engineNode, Integer type, Map<String, Object> paramJson) {
        logger.info("请求参数--inputParam:" + JSONObject.toJSONString(inputParam) + ",engineNode:" + JSONObject.toJSONString(engineNode) + ",type:" + type + ",paramJson:" + JSONObject.toJSONString(paramJson));
        NodeKnowledge knowledge = new NodeKnowledge();
        knowledge.setKnowledgeType(type);
        knowledge.setNodeId(engineNode.getNodeId());
        List<RuleField> ruleFields = ruleFieldMapper.getNodeByList(knowledge);
        List<Long> ruleIdList = new ArrayList<>();

        for(int i = 0; i < ruleFields.size(); ++i) {
            Long fieldList = Long.valueOf((ruleFields.get(i)).getFieldId().split("\\|")[0]);
            ruleIdList.add(fieldList);
        }

        Map<String,Object> hashMap = new HashMap<>();
        hashMap.put("Ids", ruleIdList);
        hashMap.put("isDerivative", 1);
        List<Field> fields = fieldMapper.findFieldByIdsAndIsderivative(hashMap);
        List list = (List)inputParam.get(engineNode.getNodeId().toString());
        if(fields != null && fields.size() > 0) {
            for(int i = 0; i < fields.size(); ++i) {
                for(int n = 0; n < list.size(); ++n) {
                    ((ComplexRule)list.get(n)).getResult().put(fields.get(i).getEnName(), (Object)null);
                    getFieldResult(((ComplexRule)list.get(n)).getResult(), paramJson);
                }
            }
        }

    }
    /**
     * 黑白名单处理，设置是否命中标志存于outMap中;<br>
     * @param  inputParam | Map<String, Object> | 必需 | 初始值为空，用于存放从数据中心获取的字段值；<br>
     * @param  engineNode | EngineNode | 必需 | 递归时当前节点对象；<br>
     * @param  outMap | Map<String, Object> | 必需 | 初始值为空，用于保存各函数间的公共参数；<br>
     * @param  paramJson | Map<String, Object> | 必需 | controller调用时传来的参数；<br>
     * @return map
     */
    private Map<String, Object> getEngineBlackAndWhite(EngineNode engineNode, Map<String, Object> inputParam, Map<String, Object> outMap, Integer type, Map<String, Object> paramJson) {
        logger.info("请求参数--inputParam:" + JSONObject.toJSONString(inputParam) + ",engineNode:" + JSONObject.toJSONString(engineNode) + ",type:" + type + ",paramJson:" + JSONObject.toJSONString(paramJson) + ",outMap:" + JSONObject.toJSONString(outMap));
        inputParam.put("nodeId", engineNode.getNodeId());
        inputParam.put("nodeName", engineNode.getNodeName());
        //判断是否命中黑白名单
        boolean isfalg = findByQueryKey(inputParam, outMap, type, paramJson);
        if(isfalg) {
            engineNode.setNextNodes((String)null);
            if(type == 1) {
                outMap.put("isBlack", "true");
                outMap.put("enginefalg", "true");
            } else {
                outMap.put("isWhite", "true");
                outMap.put("engineWhite", "true");
            }
        }

        return outMap;
    }
    /**
     * 判断是否命中黑白名单，通过boolean返回，具体的命中信息存放于outmap中的resultJson带回;<br>
     * @param  paramMap | Map<String, Object> | 必需 | 初始值为空，用于存放从数据中心获取的字段值；<br>
     * @param  type | Integer | 必需 | 名单类型；<br>
     * @param  outmap | Map<String, Object> | 必需 | 初始值为空，用于保存各函数间的公共参数；<br>
     * @param  paramJson | Map<String, Object> | 必需 | controller调用时传来的参数；<br>
     * @return
     */
    public boolean findByQueryKey(Map<String, Object> paramMap, Map<String, Object> outmap, Integer type, Map<String, Object> paramJson) {
        logger.info("请求参数--paramMap:" + JSONObject.toJSONString(paramMap) + ",outmap:" + JSONObject.toJSONString(outmap) + ",type:" + type + ",paramJson:" + JSONObject.toJSONString(paramJson));
        Long organId = Long.valueOf(paramJson.get("organId").toString());
        JSONObject blackAndWhite = new JSONObject();
        JSONObject resultJson = new JSONObject();
        String strlistDbIds = null;
        if(!paramMap.get("nodeId").equals("0")) {
            //查询出节点所设置的所有内部和外部名单库的编号
            NodeListDb nodeListDb = nodeListDbMapper.findByNodeId(paramMap);
            //内部名单遍历
            strlistDbIds = nodeListDb.getInsideListdbs();
            String[] arraylistDBIds = null;
            Integer matchs = 0;
            Integer revMatchs = 0;
            if(strlistDbIds.length() <= 0) {
                return false;
            }
            arraylistDBIds = strlistDbIds.split(",");
            for(int i = 0; i < arraylistDBIds.length; ++i) {
                HashMap param1 = new HashMap();
                param1.put("orgId", organId);
                Integer listDbId = Integer.valueOf(Integer.valueOf(arraylistDBIds[i]));
                param1.put("listDbId", listDbId);
                //通过组织id和名单编号查询名单信息
                ListDb listDb = listDbMapper.findListDbByIdandByorganId(param1);
                paramMap.put("listDb", listDb);
                String listType = listDb.getListType();
                //查询出黑白名单中使用的字段id
                String[] queryKeyArray = listDb.getQueryField().split(",");
                if(queryKeyArray.length > 0) {
                    //查询字段间逻辑，and（1），or（0）
                    Integer queryType = listDb.getQueryType();
                    //检索匹配类型，1：精确匹配，0：模糊匹配
                    Integer matchType = listDb.getMatchType();
                    String queryKey = "";
                    String revQueryKey = "";
                    String tableName = "organ_" + organId + "_" + listType + "_" + listDbId;
                    paramMap.put("tableName", tableName);
                    paramMap.put("schemaName", getDbName());
                    //黑白名单的表头字段信息列表
                    List columnList = tblColumnMapper.getColumnList(paramMap);
                    Integer loc = Integer.valueOf(0);

                    label114:
                    for(int j = 0; j < queryKeyArray.length; ++j) {
                        HashMap param = new HashMap();
                        param.put("id", queryKeyArray[j]);
                        param.put("orgId", organId);
                        param.put("engineId", (Object)null);
                        Field field = fieldMapper.findByFieldIdbyorganId(param);
                        String fieldEn = field.getEnName();
                        Iterator var31 = columnList.iterator();

                        while(true) {
                            while(true) {
                                String colComment;
                                String colName;
                                String paramValue;
                                do {
                                    do {
                                        if(!var31.hasNext()) {
                                            continue label114;
                                        }

                                        TblColumn tblColumn = (TblColumn)var31.next();
                                        colComment = tblColumn.getColComment();
                                        colName = tblColumn.getColName();
                                        paramValue = (String)paramMap.get(fieldEn);
                                    } while(!colName.startsWith("t"));
                                } while(!queryKeyArray[j].equals(colComment));

                                if(paramValue == null || paramValue.equals("")) {
                                    return false;
                                }

                                loc = Integer.valueOf(loc + 1);
                                if(matchType == 1) {
                                    if(loc > 1 && queryType == 1) {
                                        queryKey = queryKey + " and ";
                                    } else if(loc > 1 && queryType == 0) {
                                        queryKey = queryKey + " or ";
                                    }

                                    queryKey = queryKey + colName + " = \'" + paramValue + "\'";
                                } else if(matchType == 0) {
                                    if(loc > 1 && queryType == 1) {
                                        queryKey = queryKey + " and ";
                                    } else if(loc > 1 && queryType == 0) {
                                        queryKey = queryKey + " or ";
                                        revQueryKey = revQueryKey + " + ";
                                    }

                                    queryKey = queryKey + colName + " like " + "\'%" + paramValue + "%\'";
                                    revQueryKey = revQueryKey + "max(instr(\'" + paramValue + "\'," + colName + "))";
                                }
                            }
                        }
                    }

                    paramMap.put("queryKey", queryKey);
                    paramMap.put("revQueryKey", revQueryKey);
                }

                matchs = Integer.valueOf(matchs + custListMapper.findByQueryKey(paramMap));
                if(!paramMap.get("revQueryKey").equals("")) {
                    revMatchs = custListMapper.revFindByQueryKey(paramMap);
                }

                if(revMatchs == null) {
                    revMatchs = Integer.valueOf(0);
                }

                if(matchs + revMatchs > 0) {
                    resultJson.put("nodeId", paramMap.get("nodeId").toString());
                    resultJson.put("nodeName", paramMap.get("nodeName").toString());
                    resultJson.put("status", "0x0000");
                    if(type == 1) {
                        blackAndWhite.put("resultType", Integer.valueOf(5));
                        outmap.put("black", listDb);
                        resultJson.put("blackId", listDb.getId());
                        resultJson.put("blackName", listDb.getListName());
                        resultJson.put("blackType", listDb.getListType());
                        if(listDb.getListDesc() != null) {
                            resultJson.put("desc", listDb.getListDesc());
                        }

                        blackAndWhite.put("resultJson", resultJson);
                        outmap.put("blackJson", blackAndWhite);
                    } else {
                        blackAndWhite.put("resultType", 6);
                        resultJson.put("status", "0x0000");
                        resultJson.put("whiteId", listDb.getId());
                        resultJson.put("whiteName", listDb.getListName());
                        resultJson.put("whiteType", listDb.getListType());
                        if(listDb.getListDesc() != null) {
                            resultJson.put("desc", listDb.getListDesc());
                        }

                        blackAndWhite.put("resultJson", resultJson);
                        outmap.put("whiteJson", blackAndWhite);
                        outmap.put("white", listDb);
                    }

                    return true;
                }
            }
        }

        return false;
    }

    @Value("${jdbc.url}")
    private String mysqlUrl;
    //获取数据库名称
    private String getDbName() {
        String[] aArray = mysqlUrl.split("/");
        String[] bArray = aArray[3].split("\\?");
        String dbName = bArray[0];
        return dbName;
    }
    //将列表中的节点转为map，key为node_code(节点代号)，值为EngineNode（节点对象）
    private Map<String, EngineNode> getEngineNodeListByMap(List<EngineNode> nodeList) {
        HashMap map = new HashMap();

        for(int i = 0; i < nodeList.size(); ++i) {
            map.put(nodeList.get(i).getNodeCode(), nodeList.get(i));
        }

        return map;
    }
    //取出节点nodeMap中的节点对象
    private EngineNode getEngineNodeByMap(String nodeCode, Map<String, EngineNode> nodeMap) {
        return nodeMap.containsKey(nodeCode)?nodeMap.get(nodeCode):null;
    }
    /*
    * 将字段的英文名称拼接成字符串，通过逗号隔开
    * */
    private String getListFieldByString(List<Field> list) {
        String fields = "";
        for(int i = 0; i < list.size(); ++i) {
            fields = fields + list.get(i).getEnName() + ",";
        }
        return fields;
    }
    /*
    * 当执行是数据执行获取批量测试时，map中已经包含了数据，不需要调用数据中心
    * */
    private void handleTestParam(Map<String, Object> map, int type, Long nodeId){
        if(type==2){//如果是复杂规则，需要解析前端传来数据
            JSONArray array = JSONObject.parseArray((String)map.get("complexfield"));
            ArrayList list = new ArrayList();
            for(int i = 0; i < array.size(); ++i) {
                ComplexRule complexRule = new ComplexRule();
                HashMap item = new HashMap();
                JSONObject joTem = array.getJSONObject(i);
                Iterator iterator = joTem.entrySet().iterator();
                while(iterator.hasNext()) {
                    Entry entry = (Entry)iterator.next();
                    item.put((String)entry.getKey(), entry.getValue());
                }
                complexRule.setResult(item);
                list.add(complexRule);
            }
            map.put("" + nodeId, list);
        }
    }


    //数据中心接口地址
    @Value("${url}")
    private String dataUrl;
    //动作
    @Value("${act}")
    private String act;
    //授权token
    @Value("${token}")
    private String token;
    /*
    *调用数据中心接口，获取字段对应的数据，存放于入参的map中
    * */
    private boolean getEnginFiled(List<Field> fields, String pid, String uid, Map<String, Object> map, int type, Long nodeId) {
        logger.info("请求参数--fields:" + JSONObject.toJSONString(fields) + ",pid:" + pid + ",type:" + type + ",map:" + JSONObject.toJSONString(map) + ",nodeId:" + nodeId);
        //当调用类型不是api调用时，直接解析map中数据返回
        if(map!=null&&map.containsKey("apiType")&&(int)map.get("apiType")!=1){
            handleTestParam(map,type,nodeId);
            return true;
        }
        if(fields != null && fields.size() < 1) {
            return true;
        } else {
            String nonce = UUID.randomUUID().toString();
            Date date = new Date();
            long ts = date.getTime();
            String sign = MD5.GetMD5Code(act.trim() + "," + date.getTime() + "," + nonce.trim() + "," + pid.trim() + "," + uid.trim() + "," + token.trim());
            HttpClient httpClient = new HttpClient();
            String url = dataUrl + "?token=" + token.trim() + "&ts=" + ts + "&act=" + act.trim() + "&nonce=" + nonce.trim() + "&pid=" + pid.trim() + "&uid=" + uid.trim();
            HashMap pam = new HashMap();
            pam.put("fields", getListFieldByString(fields));
            pam.put("sign", sign);
            pam.put("type", String.valueOf(type));

            try {
                String resp = httpClient.post(url, pam);
                JSONObject jsonObject = JSONObject.parseObject(resp);
                if(!jsonObject.getString("status").equals("0x0000")) {
                    return false;
                } else {
                    JSONArray array = jsonObject.getJSONArray("data");
                    //表示数据中心返回的结果是简单结果，data只有key-value组成的结果
                    if(type == 1) {
                        for(int i = 0; i < array.size(); ++i) {
                            JSONObject jsonResult = array.getJSONObject(i);
                            changeJsonObjectToMap(jsonResult,map);
                        }
                        String[] fieldName = pam.get("fields").toString().split(",");
                        for(int i = 0; i < fieldName.length; i++){
                            if(!map.containsKey(fieldName[i]) || map.get(fieldName[i]) == null  || "".equals(map.get(fieldName[i])) || "null".equals(map.get(fieldName[i]))){
                                logger.error("请求字段----》"+fieldName[i]+"----失败");
                                map.put("errorField",fieldName[i]);
                                return false;
                            }
                        }
                        return true;
                    } else {//表示数据中心返回的结果是复杂结果，data除了有key-value组成的结果外，还有list
                        ArrayList list = new ArrayList();
                        for(int i = 0; i < array.size(); ++i) {
                            JSONObject jo = array.getJSONObject(i);
                            JSONArray result = jo.getJSONArray("result");
                            ComplexRule complexRule = new ComplexRule();
                            Map item = new HashMap();
                            for(int j = 0; j < result.size(); ++j) {
                                JSONObject joTem = result.getJSONObject(j);
                                changeJsonObjectToMap(joTem,map);
                            }

                            complexRule.setResult(item);
                            JSONObject out = jo.getJSONObject("out");
                            complexRule.setOut(out.toJSONString());
                            list.add(complexRule);
                        }
                        map.put("" + nodeId, list);
                        return true;
                    }
                }
            } catch (Exception e) {
                logger.error("请求异常", e);
                return false;
            }
        }
    }
    private  void changeJsonObjectToMap(JSONObject json,Map map){
        Iterator iterator = json.entrySet().iterator();
        while(iterator.hasNext()) {
            Entry entry = (Entry)iterator.next();
            map.put(entry.getKey(), entry.getValue());
        }
    }

    //获取节点所涉及的字段，存放于fields
    private void getEngineNodeByField(EngineNode engineNode, List<Field> fields, Engine engine, Map<String, Object> paramJson) {
        logger.info("请求参数--fields:" + JSONObject.toJSONString(fields) + ",engineNode:" + JSONObject.toJSONString(engineNode) + ",paramJson:" + JSONObject.toJSONString(paramJson) + ",engine:" + JSONObject.toJSONString(engine));
        switch(engineNode.getNodeType()) {
            case 2:
                getEngineRuleByField(engineNode, fields, engine, paramJson);
                break;
            case 3:
                getCustomerSegmentationByFiled(engineNode, fields, engine, paramJson);
                break;
            case 4:
                getScoreCardByField(engineNode, fields, engine, paramJson);
                break;
            case 5:
                getEngineBlackOrWhiteByField(engineNode, fields, engine, paramJson);
                break;
            case 6:
                getEngineBlackOrWhiteByField(engineNode, fields, engine, paramJson);
                break;
            case 7:
            default:
                break;
            case 9:
                getDecisionOptionsByField(engineNode, fields, engine, paramJson);
                break;
            case 13:
                getEngineRuleByField(engineNode, fields, engine, paramJson);
        }

    }
    //取出黑白名单节点中涉及的字段列表
    private void getEngineBlackOrWhiteByField(EngineNode engineNode, List<Field> fields, Engine engine, Map<String, Object> paramJson) {
        Map<String,Object> param = new HashMap<>();
        param.put("nodeId", engineNode.getNodeId());
        NodeListDb nodeDbList = nodeListDbMapper.findByNodeId(param);
        String strkeyIds = "";
        String strListDbIds = nodeDbList.getInsideListdbs();
        String[] arrayListDBIds = null;
        if(strListDbIds.length() > 0) {
            arrayListDBIds = strListDbIds.split(",");

            for(int i = 0; i < arrayListDBIds.length; ++i) {
                Integer list = Integer.valueOf(Integer.valueOf(arrayListDBIds[i]));
                param.put("listDbId", list);
                param.put("orgId", paramJson.get("organId"));
                ListDb keyIdList = listDbMapper.findListDbByIdandByorganId(param);
                String paramMap = keyIdList.getQueryField();
                if(paramMap.length() > 0) {
                    strkeyIds = strkeyIds + paramMap + ",";
                }
            }
        }

        String[] tem = strkeyIds.subSequence(0, strkeyIds.length() - 1).toString().split(",");
        ArrayList<String> list = new ArrayList<>();

        for(int i = 0; i < tem.length; ++i) {
            if(!list.contains(tem[i])) {
                list.add(tem[i]);
            }
        }

        String str = "";
        if(list != null && list.size() > 0) {
            for(int i = 0; i < list.size(); ++i) {
                str = str + list.get(i) + ',';
            }

            str = str.substring(0, str.length() - 1);
        }

        HashMap hashMap = new HashMap();
        List Ids = StringUtil.toLongList(str);
        hashMap.put("Ids", Ids);
        hashMap.put("engineId", engine.getId());
        hashMap.put("orgId", Long.valueOf(paramJson.get("organId").toString()));
        List fieldList = fieldMapper.findFieldByIdsbyorganId(hashMap);
        if(fields == null) {
            fields = new ArrayList();
        }
        fields.addAll(fieldList);
    }
    //取出规则节点中涉及的字段列表
    private void getEngineRuleByField(EngineNode engineNode, List<Field> fields, Engine engine, Map<String, Object> paramJson) {
        NodeKnowledge knowledge = new NodeKnowledge();
        knowledge.setKnowledgeType(1);
        knowledge.setNodeId(engineNode.getNodeId());
        //获取 RuleField 列表
        List contents = ruleFieldMapper.selectNodeByRuleList(knowledge);
        //用于存储字段id
        List<Long> ids = new ArrayList<>();
        //将 RuleField 列表中的字段id存入list
        for(int paramMap = 0; paramMap < contents.size(); ++paramMap) {
            RuleField fieldList = (RuleField)contents.get(paramMap);
            ids.add(Long.valueOf(fieldList.getFieldId().split("\\|")[0]));
        }

        Map<String,Object> hashMap = new HashMap<>();
        hashMap.put("Ids", ids);
        hashMap.put("engineId", engine.getId());
        hashMap.put("orgId", Long.valueOf(paramJson.get("organId").toString()));
        List<Field> fieldList = fieldMapper.findFieldByIdsbyorganId(hashMap);
        List<Field> list = new ArrayList<>();
        ids = new ArrayList<>();

        for(int i = 0; i < fieldList.size(); ++i) {
            //是否衍生字段，0：不是，1：是
            if(((Field)fieldList.get(i)).getIsDerivative() == 1) {
                //衍生字段使用的所有原生字段编号,用逗号，分割,转为List存储
                ids.addAll(StringUtil.toLongList(fieldList.get(i).getProtogeneFieldId()));
            } else {
                list.add(fieldList.get(i));
            }
        }

        if(ids.size() > 0) {
            hashMap.put("Ids", ids);
            List tem = fieldMapper.findFieldByIdsbyorganId(hashMap);
            list.addAll(tem);
        }

        if(fields == null) {
            fields = new ArrayList();
        }
        fields.addAll(list);
    }

    private void getEngineRuleByFields(EngineNode engineNode, List<Field> fields, Engine engine, Map<String, Object> paramJson, List<Long> ruleids) {
        Map<String,Object>  map = new HashMap<>();
        map.put("Ids", ruleids);
        List contents = ruleFieldMapper.selectByRuleList(map);
        ArrayList ids = new ArrayList();

        for(int paramMap = 0; paramMap < contents.size(); ++paramMap) {
            RuleField fieldList = (RuleField)contents.get(paramMap);
            ids.add(Long.valueOf(fieldList.getFieldId().split("\\|")[0]));
        }

        Map<String,Object> hashMap = new HashMap<>();
        hashMap.put("Ids", ids);
        hashMap.put("engineId", engine.getId());
        hashMap.put("orgId", Long.valueOf(paramJson.get("organId").toString()));
        List<Field> filedList = fieldMapper.findFieldByIdsbyorganId(hashMap);
        ArrayList list = new ArrayList();
        ids = new ArrayList();

        for(int lists = 0; lists < filedList.size(); ++lists) {
            if(filedList.get(lists).getIsDerivative() == 1) {
                ids.addAll(StringUtil.toLongList(filedList.get(lists).getProtogeneFieldId()));
            } else {
                list.add(filedList.get(lists));
            }
        }

        if(ids.size() > 0) {
            hashMap.put("Ids", ids);
            List tem = fieldMapper.findFieldByIdsbyorganId(hashMap);
            list.addAll(tem);
        }
        if(fields == null) {
            fields = new ArrayList();
        }
        fields.addAll(list);
    }
    //取出评分卡节点中涉及的字段列表，存放到fields带回
    private void getScoreCardByField(EngineNode engineNode, List<Field> fields, Engine engine, Map<String, Object> paramJson) {
        NodeKnowledge knowledge = new NodeKnowledge();
        knowledge.setKnowledgeType(2);
        knowledge.setNodeId(engineNode.getNodeId());
        //通过knowledge_type和node_id，查询出节点中关联的评分卡基础信息列表
        List scorecards = scorecardMapper.selectNodeByScCodeList(knowledge);
        //通过knowledge_type和node_id，查询出节点中关联的评分卡除了固定的三个字段外，用户添加的输出字段的信息列表
        List scorecardRuleContents = scorecardContentMapper.selectNodeByScCodeFieldList(knowledge);
        ArrayList ids = new ArrayList();
        //将评分卡自定义的输出字段的信息中涉及到字段的id全部提取出，存放到ids中
        selectScorecardRuleContentsField(scorecardRuleContents, ids);
        //把评分卡中三个固定输出字段中涉及到的字段的id取出添加到ids中
        selectScorecardField(scorecards, ids);
        Map<String,Object> paramMap = new HashMap<>();
        Long organId = Long.valueOf(paramJson.get("organId").toString());
        paramMap.put("orgId", organId);
        paramMap.put("Ids", ids);
        paramMap.put("engineId", engine.getId());
        //通过ids、organId、engineId查询出字段信息列表
        List<Field> fieldList = fieldMapper.findFieldByIdsbyorganId(paramMap);
        ArrayList list = new ArrayList();
        ids = new ArrayList();

        for(int i = 0; i < fieldList.size(); ++i) {
            //是否衍生字段，0：不是，1：是，默认0：不是
            if(fieldList.get(i).getIsDerivative() == 1) {
                //衍生字段用到的所有原生字段编号，逗号分割，将衍生字段中用到原生字段id加入ids
                ids.addAll(StringUtil.toLongList((fieldList.get(i)).getProtogeneFieldId()));
            } else {
                list.add(fieldList.get(i));
            }
        }
        //当衍生字段用到的字段不为空时，查出这些字段信息存入list
        if(ids.size() > 0) {
            paramMap.put("Ids", ids);
            List tem = fieldMapper.findFieldByIdsbyorganId(paramMap);
            list.addAll(tem);
        }

        if(fields == null) {
            fields = new ArrayList();
        }
        //将所有的字段信息带回
        fields.addAll(list);
    }
    //把评分卡中三个固定输出字段中涉及到的字段的id取出添加到ids中
    private void selectScorecardField(List<Scorecard> scorecards, List<Long> ids) {
        for(int i = 0; i < scorecards.size(); ++i) {
            if(scorecards.get(i).getScore() != null) {
                getFieldIds(scorecards.get(i).getScore(), ids);
            }

            if(scorecards.get(i).getPd() != null) {
                getFieldIds(scorecards.get(i).getPd(), ids);
            }

            if(scorecards.get(i).getOdds() != null) {
                getFieldIds(scorecards.get(i).getOdds(), ids);
            }
        }

    }
    //将字符串中的字段id取出，存到列表中
    private void getFieldIds(String filedJson, List<Long> ids) {
        JSONObject jsonObject = JSONObject.parseObject(filedJson);
        JSONArray array = jsonObject.getJSONArray("fields");

        for(int i = 0; i < array.size(); ++i) {
            Long id = array.getJSONObject(i).getLong("field_id");
            ids.add(id);
        }

    }
    //将评分卡自定义的输出字段的信息中涉及到字段的id全部提取出，存放到ids中
    private void selectScorecardRuleContentsField(List<ScorecardRuleContent> scorecardRuleContents, List<Long> ids) {
        for(int i = 0; i < scorecardRuleContents.size(); ++i) {
            getFieldIds(scorecardRuleContents.get(i).getFieldValue(), ids);
        }

    }
    //取出决策选项节点中涉及的字段列表
    private void getDecisionOptionsByField(EngineNode engineNode, List<Field> fields, Engine engine, Map<String, Object> paramJson) {
        JSONObject jsonObject = JSONObject.parseObject(engineNode.getNodeScript());//节点信息，包含输入字段，输出字段，条件等
        JSONArray array = jsonObject.getJSONArray("input");//取出输入字段信息
        ArrayList ids = new ArrayList();
        //将输入字段的字段id加入列表中
        for(int i = 0; i < array.size(); ++i) {
            JSONObject json = array.getJSONObject(i);
            ids.add(json.getLong("field_id"));
        }

        HashMap map = new HashMap();
        Long organId = Long.valueOf(paramJson.get("organId").toString());
        map.put("orgId", organId);
        map.put("Ids", ids);
        map.put("engineId", engine.getId());
        //通过组织id和字段id查询出字段列表
        List<Field> fieldList = fieldMapper.findFieldByIdsbyorganId(map);
        ArrayList list = new ArrayList();
        ids = new ArrayList();

        for(int i = 0; i < fieldList.size(); ++i) {
            if(fieldList.get(i).getIsDerivative() == 1) {
                ids.addAll(StringUtil.toLongList(fieldList.get(i).getProtogeneFieldId()));
            } else {
                list.add(fieldList.get(i));
            }
        }

        if(ids.size() > 0) {
            map.put("Ids", ids);
            List tem = fieldMapper.findFieldByIdsbyorganId(map);
            list.addAll(tem);
        }

        if(fields == null) {
            fields = new ArrayList();
        }
        fields.addAll(list);
    }
    //取出客户分群节点中涉及的字段列表
    private void getCustomerSegmentationByFiled(EngineNode engineNode, List<Field> fields, Engine engine, Map<String, Object> paramJson) {
        JSONObject jsonObject = JSONObject.parseObject(engineNode.getNodeScript());
        JSONArray array = jsonObject.getJSONArray("fields");
        ArrayList ids = new ArrayList();

        for(int paramMap = 0; paramMap < array.size(); ++paramMap) {
            JSONObject organId = array.getJSONObject(paramMap);
            ids.add(organId.getLong("field_id"));
        }

        HashMap hashMap = new HashMap();
        Long organId = Long.valueOf(paramJson.get("organId").toString());
        hashMap.put("orgId", organId);
        hashMap.put("Ids", ids);
        hashMap.put("engineId", engine.getId());
        List<Field> fieldList = fieldMapper.findFieldByIdsbyorganId(hashMap);
        ArrayList list = new ArrayList();
        ids = new ArrayList();

        for(int i = 0; i < fieldList.size(); ++i) {
            if(fieldList.get(i).getIsDerivative() == 1) {
                ids.addAll(StringUtil.toLongList(fieldList.get(i).getProtogeneFieldId()));
            } else {
                list.add(fieldList.get(i));
            }
        }

        if(ids.size() > 0) {
            hashMap.put("Ids", ids);
            List tem = fieldMapper.findFieldByIdsbyorganId(hashMap);
            list.addAll(tem);
        }

        if(fields == null) {
            fields = new ArrayList();
        }
        fields.addAll(list);
    }

    /*
    * 通过引擎版本号获取节点列表
    *
    * */
    private List<EngineNode> getNodeListByEngineInfo(Map<String, Object> paramMap) {
        Integer reqType = (Integer)paramMap.get("reqType");
        Long engineId = (Long)paramMap.get("engineId");
        EngineVersion engineVersion = null;
        //获取当前引擎正在运行版本
        if(reqType == 1) {
            engineVersion = engineVersionMapper.queryDeploiedVersionByEngineId(engineId);
            //获取节点信息
            return engineVersion == null?null:engineNodeMapper.queryNodeListByVerId(engineVersion.getVerId());
        } else if(reqType == 2) {
            engineVersion = engineVersionMapper.getTargetEngineVersion(paramMap);
            //获取节点信息
            return engineNodeMapper.queryNodeListByVerId(engineVersion.getVerId());
        } else {
            return new ArrayList();
        }
    }
    /*
    * map复制
    * */
    public void mapCopy(Map mapOld,Map mapNew){
        Iterator iterator = mapOld.entrySet().iterator();
        while(iterator.hasNext()) {
            Entry entry = (Entry)iterator.next();
            if(entry.getValue() != null) {
                mapNew.put(entry.getKey(), entry.getValue());
            } else {
                mapNew.put(entry.getKey(), "");
            }
        }
    }

    public Map<String, Object> getFieldResult(Map<String, Object> paramMap, Map<String, Object> paramJson) {
        HashMap paramMap2 = new HashMap();
        mapCopy(paramMap,paramMap2);

        Long organId = Long.valueOf(paramJson.get("organId").toString());
        Long engineId = Long.valueOf(paramJson.get("engineId").toString());
        paramMap2.put("orgId", organId);
        paramMap2.put("engineId", engineId);
        paramMap.put("engineId", engineId);

        Iterator entryIterator = paramMap.entrySet().iterator();
        while(true) {
            String fieldEn;
            String fieldValue;
            do {
                if(!entryIterator.hasNext()) {
                    return paramMap;
                }
                Entry entry = (Entry)entryIterator.next();
                fieldEn = (String)entry.getKey();
                fieldValue = "";
                if(entry.getValue() != null) {
                    fieldValue = String.valueOf(entry.getValue());
                }
            } while(!StringUtil.isBlank(fieldValue));

            paramMap2.put("enName", fieldEn);
            Field field = fieldMapper.findByFieldEnbyorganId(paramMap2);
            if(field != null && field.getIsDerivative() == 1) {
                String result = "";
                paramMap2.put("enName", fieldEn);
                paramMap2.put("cnName", fieldMapper.findByFieldEnbyorganId(paramMap2).getCnName());
                result = getExpAll(fieldMapper.findByFieldEnbyorganId(paramMap2).getCnName(), "", paramMap, paramJson);
                result = result.replace("(", "");
                result = result.replace(")", "");
                paramMap.put(fieldEn, result);
            }
        }
    }

    private String getExpAll(String fieldCn, String exp, Map<String, Object> param, Map<String, Object> paramJson) {
        String result = "";
        HashMap paramTem = new HashMap();
        mapCopy(param,paramTem);

        Long organId = Long.valueOf(paramJson.get("organId").toString());
        HashMap map = new HashMap();
        map.put("orgId", organId);
        map.put("engineId", param.get("engineId"));
        map.put("cnName", fieldCn);
        String arrFormula = "";
        Field engField = fieldMapper.findByFieldCnbyorganId(map);
        String engFormula = engField.getFormula();
        if(!engFormula.equals("") && engFormula != null) {
            arrFormula = engFormula;
        }

        String b;
        String v;
        if(!arrFormula.equals("") && arrFormula != null) {
            new ArrayList();
            JSONArray jsonArray = JSONObject.parseArray(arrFormula);

            for(int i = 0; i < jsonArray.size(); ++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String formula = (String)jsonObject.get("formula");
                formula = formula.replace("&gt;", ">");
                formula = formula.replace("&lt;", "<");
                Pattern pattern = Pattern.compile("@[a-zA-Z0-9_一-龥()（）-]+@");
                Matcher matcher = pattern.matcher(formula);
                String formulaStr = formula;
                int intValue = 0;

                for(exp = ""; matcher.find(); intValue = matcher.end()) {
                    String matcherStr = matcher.group(0).replace("@", "");
                    HashMap hashMap = new HashMap();
                    map.put("orgId", organId);
                    hashMap.put("engineId", map.get("engineId"));
                    hashMap.put("cnName", matcherStr);
                    hashMap.put("orgId", organId);
                    Field field = fieldMapper.findByFieldCnbyorganId(hashMap);
                    if(field == null) {
                        field = fieldMapper.findByFieldCnNoEngineIdbyorganId(hashMap);
                    }

                    HashMap mapParam = new HashMap();
                    mapParam.put("fieldValue", paramTem.get(field.getEnName()));
                    mapParam.put("enName", field.getEnName());
                    mapParam.put("fieldValueType", field.getValueType());
                    JSONArray var48 = new JSONArray();
                    JSONArray var49 = (JSONArray)jsonObject.get("farr");
                    Iterator var50 = var49.iterator();

                    while(var50.hasNext()) {
                        JSONObject var51 = (JSONObject)var50.next();
                        if(var51.get("cnName").equals(matcherStr) && !var51.get("fieldCond").equals("")) {
                            var48 = (JSONArray)var51.get("fieldCond");
                            break;
                        }
                    }

                    mapParam.put("fieldCond", var48);
                    v = "";
                    if(var48.size() > 0) {
                        v = calcFieldCond(mapParam);
                    } else {
                        v = (String)paramTem.get(field.getEnName());
                    }

                    if(field.getIsDerivative() == 0) {
                        if(field.getValueType() != 1 && field.getValueType() != 4) {
                            exp = exp + formulaStr.substring(intValue, matcher.end()).replace("@" + matcherStr + "@", "\'" + v + "\'");
                        } else {
                            exp = exp + formulaStr.substring(intValue, matcher.end()).replace("@" + matcherStr + "@", v);
                        }
                    } else if(field.getValueType() != 1 && field.getValueType() != 4) {
                        exp = exp + formulaStr.substring(intValue, matcher.end()).replace("@" + matcherStr + "@", getExpAll(matcherStr, "", paramTem, paramJson));
                    } else {
                        exp = exp + formulaStr.substring(intValue, matcher.end()).replace("@" + matcherStr + "@", getExpAll(matcherStr, exp, paramTem, paramJson));
                    }
                }

                exp = exp + formula.substring(intValue, formula.length());
                Evaluator evaluator = new Evaluator();
                b = "";

                try {
//                    System.out.println("========字段公式编辑设置的表达式输出：" + exp);
                    b = evaluator.evaluate(exp);
                } catch (EvaluationException e) {
//                    var28.printStackTrace();
                    logger.error("请求异常", e);
                }

                if(engField.getValueType() != 1 && engField.getValueType() != 2) {
                    if(engField.getValueType() == 3) {
                        if(!b.equals("1.0") && !b.equals("0.0") && !b.equals("")) {
                            result = b;
                            if(StringUtil.isValidStr(b) && b.startsWith("\'") && b.endsWith("\'")) {
                                result = b.substring(1, b.length() - 1);
                            }
                        }

                        if(b.equals("1.0")) {
                            result = (String)jsonObject.get("fvalue");
                            if(isNumeric(result)) {
                                result = "(" + result + ")";
                            } else {
                                result = "\'" + result + "\'";
                            }
                            break;
                        }
                    }
                } else if(!b.equals("")) {
                    result = b;
                    if(StringUtil.isValidStr(b) && b.startsWith("\'") && b.endsWith("\'")) {
                        result = b.substring(1, b.length() - 1);
                    }
                }
            }
        } else {
            List i = fieldMapper.findByFieldCnbyorganId(map).getFieldCondList();
            List formulaList;
            if(i.size() > 0) {
                formulaList = i;
            } else {
                List formulaJson = fieldMapper.findByFieldCnNoEngineIdbyorganId(map).getFieldCondList();
                formulaList = formulaJson;
            }

            if(formulaList.size() > 0) {
                Iterator formula = formulaList.iterator();

                while(formula.hasNext()) {
                    FieldCond var34 = (FieldCond)formula.next();
                    String pattern = var34.getConditionValue();
                    new ArrayList();
                    JSONArray matcher = JSONObject.parseArray(var34.getConditionContent());
                    exp = "";

                    for(int subexp = 0; subexp < matcher.size(); ++subexp) {
                        JSONObject j = matcher.getJSONObject(subexp);
                        map.put("id", j.getString("fieldId"));
                        Field evaluator = fieldMapper.findByFieldIdbyorganId(map);
                        if(evaluator == null) {
                            evaluator = fieldMapper.findByFieldCnNoEngineIdbyorganId(map);
                        }

                        b = evaluator.getEnName();
                        String e = evaluator.getCnName();
                        Integer paramCond = evaluator.getValueType();
                        String fieldCond = j.getString("fieldValue");
                        String jsonArr = j.getString("operator");
                        v = paramTem.get(b).toString();
                        String job = "";
                        if(evaluator.getIsDerivative() == 0) {
                            if(j.containsKey("logical")) {
                                job = " " + j.getString("logical") + " ";
                            }

                            if(jsonArr.equals("in")) {
                                exp = exp + "(indexOf(\'" + fieldCond + "\',\'" + v + "\',0) >= 0)" + job;
                            } else if(jsonArr.equals("not in")) {
                                exp = exp + "(indexOf(\'" + fieldCond + "\',\'" + v + "\',0) = -1)" + job;
                            } else if(jsonArr.equals("like")) {
                                exp = exp + "(indexOf(\'" + v + "\',\'" + fieldCond + "\',0) >= 0)" + job;
                            } else if(jsonArr.equals("not like")) {
                                exp = exp + "(indexOf(\'" + v + "\',\'" + fieldCond + "\',0) = -1)" + job;
                            } else if(paramCond != 1 && paramCond != 4) {
                                exp = exp + " (\'" + v + "\'" + jsonArr + "\'" + fieldCond + "\') " + job;
                            } else {
                                exp = exp + " (" + v + jsonArr + fieldCond + ") " + job;
                            }
                        } else {
                            if(j.containsKey("logical")) {
                                job = " " + j.getString("logical") + " ";
                            }

                            if(jsonArr.equals("in")) {
                                exp = exp + "(indexOf(\'" + fieldCond + "\',\'" + getExpAll(e, "", paramTem, paramJson) + "\',0) >= 0)" + job;
                            } else if(jsonArr.equals("not in")) {
                                exp = exp + "(indexOf(\'" + fieldCond + "\',\'" + getExpAll(e, "", paramTem, paramJson) + "\',0) = -1)" + job;
                            } else if(jsonArr.equals("like")) {
                                exp = exp + "(indexOf(\'" + getExpAll(e, "", paramTem, paramJson) + "\',\'" + fieldCond + "\',0) >= 0)" + job;
                            } else if(jsonArr.equals("not like")) {
                                exp = exp + "(indexOf(\'" + getExpAll(e, "", paramTem, paramJson) + "\',\'" + fieldCond + "\',0) = -1)" + job;
                            } else if(paramCond != 1 && paramCond != 4) {
                                exp = exp + " (\'" + getExpAll(e, "", paramTem, paramJson) + "\'" + jsonArr + "\'" + fieldCond + "\') " + job;
                            } else {
                                exp = exp + " (" + getExpAll(e, "", paramTem, paramJson) + jsonArr + fieldCond + ") " + job;
                            }
                        }
                    }

                    Evaluator evaluator = new Evaluator();
                    String str = "";

                    try {
//                        System.out.println("========字段区域设置的的表达式输出：" + exp);
                        str = evaluator.evaluate(exp);
                    } catch (EvaluationException e) {
                        logger.error("请求异常", e);
                    }

                    if(str.equals("1.0")) {
                        result = pattern;
                        break;
                    }
                }
            }
        }

        return result;
    }

    private String calcFieldCond(Map<String, Object> paramMap) {
        String fieldValue = (String)paramMap.get("fieldValue");
        Integer fieldValueType = (Integer)paramMap.get("fieldValueType");
        String result = "";
        JSONArray jsonArr = (JSONArray)paramMap.get("fieldCond");
        Iterator iterator = jsonArr.iterator();

        while(iterator.hasNext()) {
            JSONObject job = (JSONObject)iterator.next();
            String inputOne = (String)job.get("inputOne");
            String inputThree = (String)job.get("inputThree");
            if(fieldValueType == 3) {
                if(fieldValue.equals(inputOne)) {
                    result = inputThree;
                    break;
                }
            } else if(fieldValueType == 1 || fieldValueType == 4) {
                Double lv = Double.parseDouble(inputOne.substring(1, inputOne.indexOf(",")));
                Double rv = Double.parseDouble(inputOne.substring(inputOne.indexOf(",") + 1, inputOne.length() - 1));
                String exp = "";
                if(inputOne.startsWith("(") && !lv.equals("")) {
                    exp = fieldValue + ">" + lv;
                }

                if(inputOne.startsWith("[") && !lv.equals("")) {
                    exp = fieldValue + ">=" + lv;
                }

                if(inputOne.endsWith(")") && !rv.equals("")) {
                    if(exp.equals("")) {
                        exp = exp + fieldValue + "<" + rv;
                    } else {
                        exp = exp + "&&" + fieldValue + "<" + rv;
                    }
                }

                if(inputOne.endsWith("]") && !rv.equals("")) {
                    if(exp.equals("")) {
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
                    logger.error("请求异常", e);
                }

                if(b.equals("1.0")) {
                    result = inputThree;
                    break;
                }
            }
        }

        return result;
    }

    public String conversionRuleAuditCode(int auditStatus) {
        return auditStatus == 2?"拒绝":(auditStatus == 3?"人工审批":(auditStatus == 4?"简化流程":(auditStatus == 5?"通过":null)));
    }

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^(-|\\+)?\\d+(\\.\\d+)?$");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }
}
