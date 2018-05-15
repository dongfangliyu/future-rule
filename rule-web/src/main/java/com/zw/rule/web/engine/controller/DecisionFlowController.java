package com.zw.rule.web.engine.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.datamanage.po.NodeListDb;
import com.zw.rule.datamanage.service.FieldService;
import com.zw.rule.datamanage.service.ListDbService;
import com.zw.rule.engine.po.*;
import com.zw.rule.engine.service.EngineNodeService;
import com.zw.rule.engine.service.EngineVersionService;
import com.zw.rule.engine.service.NodeKnowledgeService;
import com.zw.rule.engine.util.EngineNodeUtil;
import com.zw.rule.engine.util.EngineUtil;
import com.zw.rule.jeval.tools.CollectionUtil;
import com.zw.rule.jeval.tools.ListPageUtil;
import com.zw.rule.jeval.tools.StringUtil;
import com.zw.rule.knowledge.po.Rule;
import com.zw.rule.knowledge.service.RuleService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2017/5/12 0012.
 */
@Controller
@RequestMapping({"/decision_flow"})
public class DecisionFlowController {

    @Resource
    private EngineNodeService engineNodeService;
    @Resource
    private ListDbService listDbService;
    @Resource
    private EngineVersionService engineVersionService;
    @Resource
    private NodeKnowledgeService nodeKnowledgeService;
    @Resource
    private FieldService fieldService;
    @Resource
    private RuleService ruleService;

    /**
     * 引擎管理页面单击一条数据跳转到决策流页面
     * @param param{
     *             id  long  引擎id
     *       parentId  固定值为1 （暂未明白其意义）
     * }
     * @param request
     * @return
     * getEngineVersionListByEngineId通过单元测试
     */
    @RequestMapping({"/decisionsPage"})
    @WebLogger("进入决策流页面")
    public ModelAndView index(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        Long id = Long.parseLong(param.get("id").toString());
        String flag = param.get("flag").toString();
        //将引擎ID以键engId存入session
        UserContextUtil.setAttribute("engId", id);
        //引擎版本
        List engineVersionList = engineVersionService.queryEngineVersionListByEngineId(id);
        //决策流页面
        ModelAndView mav = new ModelAndView();
        if(flag.equals("1")){
            mav.setViewName("decision/decisions");
        }else mav.setViewName("decision/decision");

        mav.addAllObjects(param);
        //判断该条引擎里有无引擎版本，有显示第一条版本的决策流程图
        if(engineVersionList.size() > 0) {
            mav.addObject("initEngineVersionId", ((EngineVersion)engineVersionList.get(0)).getVerId());
        } else {
            mav.addObject("initEngineVersionId", 0);
        }
        mav.addObject("engineVersionList", engineVersionList);
        mav.addObject("engineId", id);
        return mav;
    }

    /**
     *获取节点列表
     * @param
     * id：Long 必需 版本id（测试数据：10）
     * @return
     * 测试通过
     */
    @GetMapping({"/getNodeList"})
    @WebLogger("获取评分卡节点列表")
    @ResponseBody
    public Response getNodeList(@RequestParam Long id) {
        Map<String,Object> param = new HashMap<String,Object>();
        List nodeList = engineNodeService.queryNodeListByVerId(id);
        int maxOrder = engineNodeService.queryMaxNodeOrder(id);
        if(CollectionUtil.isNotNullOrEmpty(nodeList)) {
            param.put("maxOrder", maxOrder);
        } else {
            param.put("maxOrder", 1);
        }
        param.put("engineNodeList", nodeList);
        return new Response(param);
    }

    /**
     * 编辑节点名称
     * @param param 包含以下参数
     *              nodeName String 节点名字 必需 （测试数据：开始1）
     *              nodeId   Integer  节点Id  必需 （测试数据：107）
     * @return
     * 测试通过
     */
    @RequestMapping({"/renameNode"})
    @WebLogger("修改节点")
    @ResponseBody
    public Response renameNode(@RequestBody Map<String, Object> param) {
        int count = engineNodeService.renameNode(param);
        Response response = new Response();
        if(count == 1) {
            response.setMsg("修改成功");
        } else {
            response.setCode(1);
            response.setMsg("修改失败");
        }
        return response;
    }

    /**
     * 保存版本
     * @param
     * versionId  版本id 必需 Long (测试数据：92)
     * @return
     * 测试通过
     */
    @GetMapping({"/saveVersion"})
    @WebLogger("保存版本")
    @ResponseBody
    public Object saveVersion(@RequestParam Long versionId) {
        Map<String , Object> map = new HashMap<String , Object>();
        Response response = new Response();
        Long userId = UserContextUtil.getUserId();
        EngineVersion engineVersion = engineVersionService.queryByPrimaryKey(versionId);
        List<EngineNode> nodeList = engineNodeService.queryNodeListByVerId(versionId);
        if(engineVersion != null) {
            EngineVersion engineVer = engineVersionService.queryLatestEngineSubVersion(engineVersion);
            EngineVersion latestVersion = new EngineVersion();
            latestVersion.setBootState(0);
            latestVersion.setEngineId(engineVersion.getEngineId());
            latestVersion.setLatestUser(userId);
            latestVersion.setLayout(0);
            latestVersion.setStatus(1);
            latestVersion.setUserId(userId);
            latestVersion.setVersion(engineVersion.getVersion());
            latestVersion.setSubVer(engineVer.getSubVer() + 1);
            latestVersion.setCreateTime(String.valueOf(new Date()));//2017/6/17  创建时间
            latestVersion.setLatestTime(String.valueOf(new Date()));//2017/6/17  更新时间
            Long verId = engineVersionService.saveEngineVersion(latestVersion, nodeList);
            map.put("verId", verId);
            response.setData(map);
            response.setMsg("保存成功!");
            return response;
        } else {
            response.setCode(1);
            response.setMsg("保存失败!");
            return response;
        }
    }

    /**
     * 根据id获取节点
     * @param nodeId：节点id Long 必需 （测试参数：107）
     * @return
     * 测试通过
     */
    @RequestMapping({"/getNode"})
    @WebLogger("获取节点")
    @ResponseBody
    public Object getNode(@RequestParam Long nodeId) {
        EngineNode engineNode = this.engineNodeService.findById(nodeId);
        return new Response(engineNode);
    }

    /**
     *保存节点
     * @param param 包含以下参数
     * initEngineVersionId  Long 可选 版本id
     * nodeName String 必需 节点名称
     * nodeCode String 必需 节点代码
     * nodeOrder int   可选  节点顺序
     * nodeType  int   可选   节点类型
     * nodeX     double 可选   节点横坐标
     * nodeY     double 可选   节点纵坐标
     * nextNodes String  可选  下个节点(可能是多个)
     * nodeJson  String  可选  节点信息
     * params    String  可选  节点用到的参数列表
     * addOrSubRules String  可选
     * deny_rules    String  可选
     * @return
     * 测试通过
     */
    @RequestMapping({"/save"})
    @WebLogger("保存节点")
    @ResponseBody
    public Object saveEngineNode(@RequestBody Map<String, Object> param) {
        if(!param.containsKey("nextNodes")){
            param.put("nextNodes","");
        }
        EngineNode eNode = EngineNodeUtil.boxEngineNode(param);
        boolean flag = engineNodeService.saveEngineNode(eNode);
        Response response = new Response();
        if(flag) {
            response.setData(eNode.getNodeId());
            response.setMsg("保存成功！");
        } else {
            response.setMsg("保存失败！");
        }

        return response;
    }

    /**
     *保存节点
     * @param param 包含以下参数
     * initEngineVersionId  Long 可选 版本id
     * id Long 必需 节点id
     * nodeName String 必需 节点名称
     * nodeCode String 必需 节点代码
     * nodeOrder int   可选  节点顺序
     * nodeType  int   可选   节点类型
     * nodeX     double 可选   节点横坐标
     * nodeY     double 可选   节点纵坐标
     * nextNodes String  可选  下个节点(可能是多个)
     * nodeJson  String  可选  节点信息
     * params    String  可选  节点用到的参数列表
     * addOrSubRules String  可选
     * deny_rules    String  可选
     * @return
     * 测试通过
     */
    @RequestMapping({"/update"})
    @WebLogger("修改节点")
    @ResponseBody
    public Object update(@RequestBody Map<String, Object> param) {
        String idStr = param.get("id").toString();
        String targetIdStr=null;
        if(param.get("targetId")!=null){
            targetIdStr = param.get("targetId").toString();
        }
        Long id = idStr == null?null:Long.valueOf(Long.parseLong(idStr));
        Long targetId = targetIdStr == null?null:Long.valueOf(Long.parseLong(targetIdStr));
        EngineNode engineNode = EngineNodeUtil.boxEngineNode(param);
        engineNode.setNodeId(id);
        boolean flag = engineNodeService.updateNode(engineNode, targetId);
        Response resp = new Response();
        if(flag) {
            param.put("nodeId", id);
//            resp.setData(param);
            resp.setMsg("修改成功！");
        } else {
            resp.setMsg("修改失败！");
        }

        return resp;
    }

    /**
     *
     * @param param 包含以下参数
     * currentNodeId String 节点id 必需
     * preNodeId Long 前一个节点id 必需 (如果是-1,表示没有连线)
     * 测试通过
     */
    @RequestMapping({"/removeNode"})
    @WebLogger("删除节点")
    @ResponseBody
    public Object delete(@RequestBody Map<String, Object> param) {
        Long engineNodeId = Long.valueOf(param.get("currentNodeId").toString());
        Long preEngineNodeId = Long.valueOf(param.get("preNodeId").toString());
        engineNodeService.deleteNode(engineNodeId, preEngineNodeId);
        //删除规则节点关联规则
        ruleService.deleteRelateRule(engineNodeId);
        return new Response("删除成功!");
    }

    /**
     *批量删除节点
     * @param paramMap
     * idList 节点id 可选
     * array 包含以下参数:
     * subNodeId int 可选
     * preNodeId int 可选
     * 测试通过
     * @return
     */
    @RequestMapping({"/deleteNodes"})
    @WebLogger("批量删除节点")
    @ResponseBody
    public Response deleteNodes(@RequestBody Map<String, Object> paramMap) {
        List<Long> idList = (List)paramMap.get("idList");
        String array = paramMap.get("array").toString();
        Map map = new HashMap();
        Response response = new Response();
        if(CollectionUtil.isNotNullOrEmpty(idList)) {
            Map param = engineNodeService.deleteNodes(idList, array);
            int count = Integer.parseInt("" + param.get("count"));
            int num = Integer.parseInt("" + param.get("num"));
            if(count != idList.size() && num <= 0) {
                response.setCode(1);
                response.setMsg("删除选择区域节点失败！");
                return response;
            } else {
                response.setMsg("删除选择区域节点成功！！");
                return response;
            }
        } else {
            response.setCode(1);
            response.setMsg("区域中无有效节点!！！");
            return response;
        }
    }

    /**
     * 删除节点之间的连线
     * @param param 包含以下参数
     *              currentNodeId String 必需
     *              preNodeId   String  必需
     * @return
     *测试成功
     */
    @RequestMapping({"/removeLink"})
    @WebLogger("删除节点之间的连线")
    @ResponseBody
    public Response removeLink(@RequestBody Map<String, Object> param) {
        Long engineNodeId = Long.valueOf(param.get("currentNodeId").toString());
        Long preEngineNodeId = Long.valueOf(param.get("preNodeId").toString());
        engineNodeService.removeLink(engineNodeId, preEngineNodeId);
        return new Response("删除成功!");
    }

    /**
     *
     * @param param 包含以下参数
     *                    nodeId 节点id String 必需
     *                    type   组件类型 int 可选
     *                    insideListdbs  int   可选 （如果是黑白节点，就是必需）
     *                    outsideListdbs  int   可选 （如果是黑白节点，就是必需）
     *
     * @return
     * 测试通过
     */
    @RequestMapping({"/copy"})
    @WebLogger("复制规则集节点")
    @ResponseBody
    public Response copy(@RequestBody Map<String, Object> param) {
        String idStr = param.get("nodeId").toString();
        Long id = Long.valueOf(Long.parseLong(idStr));
        EngineNode eNode = this.engineNodeService.findById(id);
        if(NodeTypeEnum.CLASSIFY.getValue() == eNode.getNodeType()){//节点是客户分群
            //清空下一个节点连线信息
            eNode.setNextNodes("");
            //节点信息
            JSONObject nodeJson = JSONObject.parseObject(eNode.getNodeJson());
            JSONArray conditions = nodeJson.getJSONArray("conditions");
            for(int i = 0; i < conditions.size();i++){
                JSONObject condition = conditions.getJSONObject(i);
                condition.remove("nextNode");
                condition.put("nextNode","");
            }
            eNode.setNodeJson(nodeJson.toJSONString());
            //节点脚本
            JSONObject nodeScript = JSONObject.parseObject(eNode.getNodeScript());
            nodeScript.remove("conditions");
            nodeScript.put("conditions",new JSONArray());
            eNode.setNodeScript(nodeScript.toJSONString());
        }else if(NodeTypeEnum.SANDBOX.getValue() == eNode.getNodeType()){//节点是沙盒比例
            //清空下一个节点连线信息
            eNode.setNextNodes("");
            //节点信息
            JSONArray nodeJson = JSONArray.parseArray(eNode.getNodeJson());
            for(int i = 0; i < nodeJson.size();i++){
                JSONObject jsonObject = nodeJson.getJSONObject(i);
                jsonObject.remove("nextNode");
            }
            eNode.setNodeJson(nodeJson.toJSONString());
        }
        int maxOrder = this.engineNodeService.queryMaxNodeOrder(eNode.getVerId());
        eNode.setNodeX(eNode.getNodeX() + 50.0D);
        eNode.setNodeY(eNode.getNodeY() + 50.0D);
        eNode.setNodeOrder(Integer.valueOf(maxOrder + 1));
        eNode.setNodeCode("ND_" + (maxOrder + 1));
        eNode.setNextNodes("");
        String params = eNode.getParams();
        JSONObject obj = JSONObject.parseObject(params);
        obj.put("arr_linkId", "");
        eNode.setParams(obj.toJSONString());
        List nodeKnowledges = this.nodeKnowledgeService.queryNodeKnowledgeListByNodeId(param);
        ArrayList rules = null;
        Response response = new Response();
        if(CollectionUtil.isNotNullOrEmpty(nodeKnowledges)) {
            rules = new ArrayList();
            Iterator iterator = nodeKnowledges.iterator();

            while(iterator.hasNext()) {
                NodeKnowledge flag = (NodeKnowledge)iterator.next();
                rules.add(flag.getKnowledgeId());
            }
        }
        eNode.setRuleList(rules);
        boolean flag1 = this.engineNodeService.saveEngineNode(eNode);
        if(eNode.getNodeType() == 5 || eNode.getNodeType() == 6){
            NodeListDb nodeListDb = listDbService.queryDbListByNodeId(id);
            Map<String , Object> map = new HashMap<>();
            map.put("nodeId",eNode.getNodeId());
            map.put("insideListdbs",nodeListDb.getInsideListdbs());
            map.put("outsideListdbs",nodeListDb.getOutsideListdbs());
            //关联节点和内外部名单
            listDbService.addNodeListDb(map);
        }
        if(flag1) {
            response.setData(eNode);
            response.setCode(0);
            response.setMsg("复制成功!");
        } else {
            response.setCode(1);
            response.setMsg("复制失败!");
        }

        return response;
    }

    /**
     *
     * @param param 包含以下参数
     *                    nodeId_1  节点1 String 必需
     *                    nextNodes_1   节点代码 String 必需
     *                    nodeId_2  节点2    String 必需
     *                    nextNodes_2   节点代码2 String 必需
     *
     * @return
     * 测试通过
     */
    @RequestMapping({"/updateProperty"})
    @WebLogger("修改节点连线")
    @ResponseBody
    public Response updateProperty(@RequestBody Map<String, Object> param) {
        List<EngineNode> eList = new ArrayList<EngineNode>();
        EngineNode e1 = new EngineNode();
        EngineNode e2 = new EngineNode();
        long nodeId_1 = 0L;
        if(param.containsKey("nodeId_1")) {
            nodeId_1 = Long.parseLong(param.get("nodeId_1").toString());
            e1 = engineNodeService.findById(nodeId_1);
        }
        if(e1 != null && e1.getNodeId() == nodeId_1) {
            String flag = e1.getNodeJson();
            if(flag == null) {
                flag = "";
            }
            if(param.containsKey("params_1")) {
                e1.setParams(param.get("params_1").toString());
            }

            if(param.containsKey("nextNodes_1")) {
                e1.setNextNodes(param.get("nextNodes_1").toString());
            }

            if(param.containsKey("node_json_1") && e1.getNodeType() == 7) {
                String temp = String.valueOf(param.get("node_json_1"));
                e1.setNodeJson(temp);
            }

            eList.add(e1);
        }
        if(param.containsKey("params_2")) {
            e2.setParams(param.get("params_2").toString());
        }
        e2.setNextNodes(param.get("nextNodes_2").toString());
        e2.setNodeId(Long.parseLong(param.get("nodeId_2").toString()));
        e2.setParentId(nodeId_1);
        eList.add(e2);
        engineNodeService.updateNodeForNextOrderAndParams(eList);
        return new Response(param);
    }

    /**
     *
     * @param param 包含以下参数
     *                    nodeX String 必需  节点横坐标
     *                    nodeY String 必需  节点纵坐标
     *                    nodeId String 必需  节点ID
     * @return
     * 测试通过
     */
    @RequestMapping({"/updatePropertyForMove"})
//    @WebLogger("修改节点")
    @ResponseBody
    public Response updatePropertyForMove(@RequestBody Map<String, Object> param) {
        EngineNode e = new EngineNode();
        e.setNodeX(Double.parseDouble(param.get("nodeX").toString()));
        e.setNodeY(Double.parseDouble(param.get("nodeY").toString()));
        e.setNodeId(Long.parseLong(param.get("nodeId").toString()));
        boolean flag = this.engineNodeService.updateNodeForMove(e);
        Response response = new Response();
        if(flag) {
            response.setCode(0);
        } else {
            response.setCode(1);
        }

        return response;
    }

//    @RequestMapping({"/createDecOption"})
//    public ModelAndView createDecOption(@RequestParam Map<String, Object> paramMap) {
//        ModelAndView mav = new ModelAndView("decision/decision_options");
//        mav.addAllObjects(paramMap);
//        return mav;
//    }

    @RequestMapping({"/previewRule"})
    public ModelAndView previewRule(@RequestParam Map<String, Object> paramMap) {
        ModelAndView mav = new ModelAndView("decision/previewRule");
        mav.addAllObjects(paramMap);
        return mav;
    }

    /**
     *  决策流-客户分群-右键编辑-分群管理-分组1 调用
     * @param queryFilter
     * param{ engineId: }
     * page
     * @return
     */
    @RequestMapping({"/getFieldList"})
    @ResponseBody
    @WebLogger("获取字段列表")
    public Response findFieldByUserWithPage(@RequestBody ParamFilter queryFilter) {
        Long userId = UserContextUtil.getUserId();
        Long organId =UserContextUtil.getOrganId();
        queryFilter.getParam().put("userId", userId);
        queryFilter.getParam().put("orgId", organId);
        Integer isCommon = Integer.valueOf(1);
        Integer engineId = null;
        if(queryFilter.getParam().containsKey("engineId")) {
            isCommon = Integer.valueOf(0);
            engineId = Integer.valueOf(Integer.valueOf((String)queryFilter.getParam().get("engineId")).intValue());
            queryFilter.getParam().put("engineId", engineId);
        } else {
            isCommon = Integer.valueOf(1);
            queryFilter.getParam().put("engineId", (Object)null);
        }
        queryFilter.getParam().put("isCommon", isCommon);
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List fieldList = this.fieldService.queryFieldList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(fieldList);
        return new Response(pageInfo);
    }
    /**
     * 决策选项右键编辑后，输入，输出变量时调用
     * @param queryFilter
     * param{
     *     opType  int   只知道用来判断查字段还是评分卡
     *     nodeId   int  引擎节点信息编号
     *     engineId  int
     *     isOutput  int  是否输出字段，0：不是，1：是，默认0：不是
     * }
     * page
     * @return
     */
    @RequestMapping({"/getFieldOrScorecardForOption"})
    @ResponseBody
    @WebLogger("决策选项查询字段或评分卡列表")
    public Response getFieldOrScorecardForOption(@RequestBody ParamFilter queryFilter) {
        int opType = Integer.parseInt(queryFilter.getParam().get("opType").toString());
        //获取页码
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        int pageSize = queryFilter.getPage().getPageSize();
        Map map = new HashMap();
        PageInfo pageInfo = new PageInfo();
        if(opType == 2) {
            pageInfo = this.getFields(queryFilter.getParam(), pageNo, pageSize);
        } else if(opType == 1) {
            map = this.getScorecards(queryFilter.getParam(), pageNo, pageSize, 1);
            pageInfo = (PageInfo)map.get("pager");
        } else if(opType == 3) {
            map = getRuleMap(queryFilter.getParam(), pageNo, pageSize, 1, true);
            return new Response(map);
        } else if(opType == 4) {
            map = getRuleMap(queryFilter.getParam(), pageNo, pageSize, 2, true);
            return new Response(map);
        } else if(opType == 5) {
            map = getDecisionMap(queryFilter.getParam(), pageNo, pageSize, true);
            return new Response(map);
        }
        return new Response(pageInfo);
    }

    /**
     * 决策流中决策选项右键编辑时调用
     * @param paramMap{engineId: nodeId: }
     * @return
     */
    @RequestMapping({"/getScorecardForOption"})
    @ResponseBody
    @WebLogger("进入决策选项编辑或查看详情")
    public Response getFieldOrScorecardForOption(@RequestBody Map<String, Object> paramMap) {
        paramMap = this.getScorecards(paramMap, Integer.valueOf(0), Integer.valueOf(0), 2);
        return new Response(paramMap);
    }

    @RequestMapping({"/getRuleForOption"})
    @ResponseBody
    public Response getRuleForOption( @RequestParam Map<String, Object> paramMap) {
        paramMap = this.getRuleMap(paramMap, 0, 0, 1, false);
        return new Response(paramMap);
    }

    @RequestMapping({"/getComplexRuleForOption"})
    @ResponseBody
    public Response getComplexRuleForOption( @RequestParam Map<String, Object> paramMap) {
        paramMap = this.getRuleMap(paramMap, 0, 0, 2, false);
        return new Response(paramMap);
    }

    @RequestMapping({"/getOptionForOption"})
    @ResponseBody
    public Response getOptionForOption(@RequestParam Map<String, Object> paramMap) {
        paramMap = this.getDecisionMap(paramMap, 0, 0, false);
        return new Response(paramMap);
    }

    /**
     * 目前未找到接口怎么触发
     * @param paramMap
     * paramMap{
     *   engineNodeId
     *   branch
     *  }
     * @return
     */
    @RequestMapping({"/validateBranch"})
    @ResponseBody
    public Response validateBranch(@RequestBody Map<String, Object> paramMap) {
        String engineNodeIdStr = paramMap.get("engineNodeId").toString();
        Long engineNodeId = Long.valueOf(Long.parseLong(engineNodeIdStr));
        EngineNode engineNode = engineNodeService.findById(engineNodeId);
        if(engineNode != null) {
            String branch = paramMap.get("branch").toString();
            String jsonStr = engineNode.getNodeJson();
            JSONArray object;
            if(engineNode.getNodeType().intValue() == NodeTypeEnum.CLASSIFY.getValue()) {
                if(StringUtil.isValidStr(jsonStr)) {
                    JSONObject conditions = JSONObject.parseObject(jsonStr);
                    object = conditions.getJSONArray("conditions");
                    if(object != null && !object.isEmpty()) {
                        JSONObject i = null;
                        for(int nextNode = 0; nextNode < object.size();nextNode++) {
                            i = object.getJSONObject(nextNode);
                            if(i.getString("group_name").equals(branch)) {
                                String nextNode1 = i.getString("nextNode");
                                if(StringUtil.isValidStr(nextNode1)) {
                                    paramMap.put("result", Integer.valueOf(1));
                                    break;
                                }
                            }
                        }
                    } else {
                        paramMap.put("result", Integer.valueOf(0));
                    }
                } else {
                    paramMap.put("result", Integer.valueOf(0));
                }
            } else if(engineNode.getNodeType().intValue() == NodeTypeEnum.SANDBOX.getValue()) {
                if(StringUtil.isValidStr(jsonStr)) {
                    JSONArray jsonArray = JSONArray.parseArray(jsonStr);
                    if(jsonArray != null && !jsonArray.isEmpty()) {
                        object = null;
                        for(int i = 0; i < jsonArray.size(); i++) {
                            JSONObject jsonObj = jsonArray.getJSONObject(i);
                            if(jsonObj.getString("sandbox").equals(branch)) {
                                String str = jsonObj.getString("nextNode");
                                if(StringUtil.isValidStr(str)) {
                                    paramMap.put("result", Integer.valueOf(1));
                                    break;
                                }
                            }
                        }
                    } else {
                        paramMap.put("result", Integer.valueOf(0));
                    }
                } else {
                    paramMap.put("result", Integer.valueOf(0));
                }
            }
        } else {
            paramMap.put("result", Integer.valueOf(1));
        }

        return new Response(paramMap);
    }

    public Map<String, Object> getScorecards(Map<String, Object> paramMap, Integer pageNo, Integer pageSize, int type) {
        Long userId = UserContextUtil.getUserId();
        Long organId =UserContextUtil.getOrganId();
        paramMap.put("userId", userId);
        paramMap.put("orgId", organId);
        String idStr = String.valueOf(paramMap.get("nodeId"));
        ArrayList slist = new ArrayList();
        if(!idStr.equals("0")) {
            Long id = Long.valueOf(Long.parseLong(idStr));
            EngineNode eNode = engineNodeService.findById(id);
            List nodeIds = getExNodeLineScorecards(paramMap);
            paramMap.put("idList", nodeIds);
            paramMap.put("verId", eNode.getVerId());
            if(nodeIds.size() > 0) {
                List slist1;
                if(type == 1) {
                    PageHelper.startPage(pageNo.intValue(), pageSize.intValue());
                    slist1 = nodeKnowledgeService.queryScorecardList(paramMap);
                    PageInfo pageInfo = new PageInfo(slist1);
                    paramMap.put("scorecardList", slist1.size() > 0?slist1:new ArrayList());
                    paramMap.put("pager", pageInfo);
                } else {
                    slist1 = nodeKnowledgeService.queryScorecardList(paramMap);
                    paramMap.put("scorecardList", slist1.size() > 0?slist1:new ArrayList());
                }
            } else {
                paramMap.put("scorecardList", slist.size() > 0?slist:new ArrayList());
                paramMap.put("pager", new PageInfo(slist));
            }
        } else {
            paramMap.put("scorecardList", slist.size() > 0?slist:new ArrayList());
            paramMap.put("pager", new PageInfo(slist));
        }

        return paramMap;
    }

    private void getParntNodes(EngineNode node, List<Long> nodeIds) {
        if(node != null) {
            HashMap map = new HashMap();
            map.put("verId", node.getVerId());
            map.put("nodeCode", node.getNodeCode());
            List eList = this.engineNodeService.queryNodesByNextNode(map);
            Iterator iterator = eList.iterator();
            while(iterator.hasNext()) {
                EngineNode e = (EngineNode)iterator.next();
                String[] nextNodes = e.getNextNodes().split(",");
                List nList = Arrays.asList(nextNodes);
                if(nList.contains(node.getNodeCode())) {
                    JSONObject obj = JSONObject.parseObject(e.getParams());
                    if(obj.getIntValue("type") == 4) {
                        nodeIds.add(e.getNodeId());
                    }

                    if(!e.getNextNodes().isEmpty()) {
                        this.getParntNodes(e, nodeIds);
                    }
                }
            }
        }

    }

    public PageInfo getFields(Map<String, Object> paramMap, Integer pageNo, Integer pageSize) {
        Long userId = UserContextUtil.getUserId();
        Long organId =UserContextUtil.getOrganId();
        paramMap.put("userId", userId);
        paramMap.put("orgId", organId);
        Integer engineId = null;
        if(paramMap.containsKey("engineId")) {
            engineId = Integer.valueOf(paramMap.get("engineId").toString());
            paramMap.put("engineId", engineId);
        } else {
            paramMap.put("engineId", (Object)null);
        }
        PageHelper.startPage(pageNo, pageSize);
        List fieldList = fieldService.queryFieldList(paramMap);
        PageInfo pageInfo = new PageInfo(fieldList);
        paramMap.put("pager", pageInfo);
        return pageInfo;
    }

    private Map<String, Object> getRuleMap(Map<String, Object> paramMap, int pageNo, int pageSize, int type, boolean isPaging) {
        List ruleList = this.getExNodeLineRules(paramMap, type);
        Object ruleList_1 = null;
        if(paramMap.containsKey("searchKey") && !paramMap.get("searchKey").equals("")) {
            ruleList_1 = new ArrayList();
            String pager = (String)paramMap.get("searchKey");
            Iterator var10 = ruleList.iterator();

            while(var10.hasNext()) {
                Rule listPageUtil = (Rule)var10.next();
                if(listPageUtil.getName().contains(pager)) {
                    ((List)ruleList_1).add(listPageUtil);
                }
            }
        } else {
            ruleList_1 = ruleList;
        }

        if(type == 2) {
            pageSize = 12;
        }

        if(isPaging) {
            PageInfo pager1 = new PageInfo();
            if(ruleList != null && ruleList.size() > 0) {
                ListPageUtil listPageUtil1 = new ListPageUtil((List)ruleList_1, pageNo, pageSize);
                pager1.setPageNum(pageNo);
                pager1.setPages(listPageUtil1.getTotalPage());
                paramMap.put("ruleList", listPageUtil1.getPagedList());
            }

            paramMap.put("pager", pager1);
        } else {
            paramMap.put("ruleList", ruleList_1 == null?new ArrayList():ruleList_1);
        }

        return paramMap;
    }

    public List<Rule> getExNodeLineRules(Map<String, Object> paramMap, int type) {
        Long nodeId = Long.valueOf(Long.parseLong(paramMap.get("nodeId").toString()));
        EngineNode eNode = engineNodeService.findById(nodeId);
        Long versionId = eNode.getVerId();
        List nodeList = engineNodeService.queryNodeListByVerId(versionId);
        Map nodeMap = EngineUtil.convertNodeList2Map(nodeList);
        ArrayList ruleList = new ArrayList();
        if(CollectionUtil.isNotNullOrEmpty(nodeMap)) {
            EngineNode currentNode = (EngineNode)nodeMap.get(nodeId);
            recursiveNode(currentNode, nodeMap, ruleList, type);
        }

        return ruleList;
    }

    private void recursiveNode(EngineNode currentNode, Map<Long, EngineNode> nodeMap, List<Rule> ruleList, int type) {
        Long parentId = currentNode.getParentId();
        EngineNode exNode = null;
        if(parentId != null) {
            exNode = (EngineNode)nodeMap.get(parentId);
            if(type == 1) {
                if(exNode != null && exNode.getNodeType().intValue() == NodeTypeEnum.POLICY.getValue()) {
                    this.getRuleList(ruleList, exNode, type);
                }
            } else if(exNode != null && exNode.getNodeType().intValue() == NodeTypeEnum.NODE_COMPLEXRULE.getValue()) {
                this.getRuleList(ruleList, exNode, type);
            }
        }

        if(exNode != null) {
            this.recursiveNode(exNode, nodeMap, ruleList, type);
        }

    }


    private List<Rule> getRuleList(List<Rule> ruleList, EngineNode exNode, int type) {
        List rules = ruleService.getNodeAddOrSubRulesByNodeId(exNode.getNodeId());
        Iterator var6 = rules.iterator();

        Rule rule;
        while(var6.hasNext()) {
            rule = (Rule)var6.next();
            if(type == 2) {
                try {
                    Rule ruleClone = (Rule)rule.clone();
                    ruleClone.setName(exNode.getNodeName() + "-" + ruleClone.getName() + "得分");
                    ruleClone.setCode("cs_" + exNode.getNodeId() + "_" + rule.getCode());
                    ruleClone.setShowType(1);
                    ruleClone.setEngineNodeId(exNode.getNodeId());
                    ruleClone.setEngineNodeName(exNode.getNodeName());
                    ruleList.add(ruleClone);
                } catch (CloneNotSupportedException var9) {
                    var9.printStackTrace();
                }

                rule.setCode("cr_" + exNode.getNodeId() + "_" + rule.getCode());
            } else {
                rule.setCode("r_" + exNode.getNodeId() + "_" + rule.getCode());
            }

            rule.setName(exNode.getNodeName() + "-" + rule.getName() + "是否命中");
            rule.setEngineNodeId(exNode.getNodeId());
            rule.setEngineNodeName(exNode.getNodeName());
        }

        if(type == 1 && CollectionUtil.isNotNullOrEmpty(rules)) {
            rule = new Rule();
            rule.setShowType(1);
            rule.setName(exNode.getNodeName() + "得分");
            rule.setEngineNodeName(exNode.getNodeName());
            rule.setCode("s_" + exNode.getNodeId());
            rule.setEngineNodeId(exNode.getNodeId());
            ruleList.add(rule);
        }

        ruleList.addAll(rules);
        return ruleList;
    }

    public List<Long> getExNodeLineScorecards(Map<String, Object> paramMap) {
        Long nodeId = Long.valueOf(Long.parseLong(paramMap.get("nodeId").toString()));
        EngineNode eNode = engineNodeService.findById(nodeId);
        Long versionId = eNode.getVerId();
        List nodeList = engineNodeService.queryNodeListByVerId(versionId);
        Map nodeMap = EngineUtil.convertNodeList2Map(nodeList);
        ArrayList idList = new ArrayList();
        if(CollectionUtil.isNotNullOrEmpty(nodeMap)) {
            EngineNode currentNode = (EngineNode)nodeMap.get(nodeId);
            recursiveNodeForScorecard(currentNode, nodeMap, idList);
        }

        return idList;
    }

    private void recursiveNodeForScorecard(EngineNode currentNode, Map<Long, EngineNode> nodeMap, List<Long> idList) {
        Long parentId = currentNode.getParentId();
        EngineNode exNode = null;
        if(parentId != null) {
            exNode = (EngineNode)nodeMap.get(parentId);
            if(exNode != null && exNode.getNodeType().intValue() == NodeTypeEnum.SCORECARD.getValue()) {
                idList.add(exNode.getNodeId());
            }

            if(exNode != null) {
                this.recursiveNodeForScorecard(exNode, nodeMap, idList);
            }
        }

    }

    public Map<String, Object> getDecisionMap(Map<String, Object> paramMap, int pageNo, int pageSize, boolean isPaging) {
        Long nodeId = Long.valueOf(Long.parseLong(paramMap.get("nodeId").toString()));
        EngineNode eNode = engineNodeService.findById(nodeId);
        Long versionId = eNode.getVerId();
        List nodeList = engineNodeService.queryNodeListByVerId(versionId);
        Map nodeMap = EngineUtil.convertNodeList2Map(nodeList);
        ArrayList dlist = new ArrayList();
        if(CollectionUtil.isNotNullOrEmpty(nodeMap)) {
            this.recursiveNodeForDecision(eNode, nodeMap, dlist);
        }

        ArrayList d_list = null;
        if(paramMap.containsKey("searchKey") && !paramMap.get("searchKey").equals("")) {
            d_list = new ArrayList();
            String pager = (String)paramMap.get("searchKey");
            Iterator var14 = dlist.iterator();

            while(var14.hasNext()) {
                DecisionOptions listPageUtil = (DecisionOptions)var14.next();
                if(listPageUtil.getName().contains(pager)) {
                    d_list.add(listPageUtil);
                }
            }
        } else {
            d_list = dlist;
        }

        if(isPaging) {
            PageInfo pager1 = new PageInfo();
            if(d_list != null && d_list.size() > 0) {
                ListPageUtil listPageUtil1 = new ListPageUtil(d_list, pageNo, pageSize);
                pager1.setPageNum(pageNo);
                pager1.setPages(listPageUtil1.getTotalPage());
                paramMap.put("d_list", listPageUtil1.getPagedList());
            }

            paramMap.put("pager", pager1);
        } else {
            paramMap.put("d_list", d_list == null?new ArrayList():d_list);
        }

        return paramMap;
    }
    private void recursiveNodeForDecision(EngineNode currentNode, Map<Long, EngineNode> nodeMap, List<DecisionOptions> dlist) {
        int fType = 0;
        String fieldScope = "";
        Long parentId = currentNode.getParentId();
        EngineNode exNode = null;
        if(parentId != null) {
            exNode = (EngineNode)nodeMap.get(parentId);
            if(exNode != null && exNode.getNodeType().intValue() == NodeTypeEnum.DECISION.getValue()) {
                Long node_id = exNode.getNodeId();
                String node_name = exNode.getNodeName();
                String node_code = exNode.getNodeCode();
                String node_json = exNode.getNodeJson();
                if(node_json != "") {
                    JSONObject deo = JSONObject.parseObject(node_json);
                    String output = deo.getString("output");
                    if(output != "") {
                        JSONObject jput = JSONObject.parseObject(output);
                        fType = Integer.parseInt(jput.getString("field_type"));
                        fieldScope = jput.getString("field_scope");
                    }
                }

                DecisionOptions deo1 = new DecisionOptions();
                deo1.setName(node_name);
                deo1.setCode(node_code + "_" + node_id);
                deo1.setfType(Integer.valueOf(fType));
                deo1.setNodId(node_id);
                deo1.setFieldScope(fieldScope);
                dlist.add(deo1);
            }

            if(exNode != null) {
                this.recursiveNodeForDecision(exNode, nodeMap, dlist);
            }
        }

    }
}
