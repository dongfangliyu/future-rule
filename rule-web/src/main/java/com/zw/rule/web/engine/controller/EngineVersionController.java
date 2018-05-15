package com.zw.rule.web.engine.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zw.rule.core.Response;
import com.zw.rule.engine.po.EngineNode;
import com.zw.rule.engine.po.EngineResultSet;
import com.zw.rule.engine.po.EngineVersion;
import com.zw.rule.engine.service.EngineNodeService;
import com.zw.rule.engine.service.EngineVersionService;
import com.zw.rule.engine.service.ResultSetService;
import com.zw.rule.jeval.tools.CollectionUtil;
import com.zw.rule.jeval.tools.StringUtil;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2017/5/17.
 */
@Controller
@RequestMapping({"engineVersion"})
public class EngineVersionController{
    public EngineVersionController() {
    }
    @Resource
    private EngineVersionService engineVersionService;
    @Resource
    private EngineNodeService engineNodeService;
    @Resource
    private ResultSetService resultSetService;

    /**
     * 新建
     * @param engineId
     * @param version
     * @return
     */
    @RequestMapping({"add"})
    @WebLogger("新建")
    @ResponseBody
    public Response addVersion(@RequestParam Long engineId, @RequestParam Integer version , HttpServletRequest request) {
        HashMap map = new HashMap();
        Long userId = UserContextUtil.getUserId();
        //Long organId = UserContextUtil.getOrganId();
        EngineVersion engineVer = new EngineVersion();
        engineVer.setEngineId(engineId);
        engineVer.setVersion(version);
        EngineVersion engineVersion = engineVersionService.queryLatestEngineSubVersion(engineVer);
        if(engineVersion != null) {
            EngineVersion newEngineVersion = new EngineVersion();
            newEngineVersion.setBootState(0);
            newEngineVersion.setCreateTime((new Date()).toString());
            newEngineVersion.setEngineId(engineVersion.getEngineId());
            newEngineVersion.setLatestTime((new Date()).toString());
            newEngineVersion.setLatestUser(userId);
            newEngineVersion.setLayout(0);
            newEngineVersion.setStatus(1);
            newEngineVersion.setSubVer(engineVersion.getSubVer() + 1);
            newEngineVersion.setUserId(userId);
            newEngineVersion.setVersion(engineVersion.getVersion());
            Long versionId = this.saveEngineVersion(newEngineVersion , request);
            map.put("result", Integer.valueOf(1));
            map.put("versionId", versionId);
        } else {
            map.put("result", Integer.valueOf(-1));
            map.put("versionId", Integer.valueOf(0));
        }

        return new Response(map);
    }

    /**
     * 清空引擎节点
     * @param versionId
     * @return
     */
    @RequestMapping({"clear"})
    @WebLogger("清空引擎节点")
    @ResponseBody
    public Response clear(Long versionId) {
        EngineVersion engineVersion = engineVersionService.queryByPrimaryKey(versionId);
        Response response=new Response();
        if(engineVersion != null) {
            if(engineVersion.getBootState() == 1) {
                response.setCode(1);
                response.setMsg("版本正在运行,请先停止部署!");
            } else {
                boolean flag = engineVersionService.clear(versionId);
                if(flag) {
                    response.setMsg("清除成功!");
                } else {
                    response.setCode(1);
                    response.setMsg("清除失败,请联系管理员!");
                }
            }
        } else {
            response.setCode(1);
            response.setMsg("数据错误,目标版本不存在!");
        }
        return response;
    }

    /**
     * 引擎部署
     * @param versionId
     * @return
     */
    @RequestMapping({"deploy"})
    @WebLogger("引擎部署")
    @ResponseBody
    public Response deployEngineVersion(Long versionId) {
        int count = engineVersionService.deployEngine(versionId);
        Response response=new Response();
        if(count == 1) {
            response.setMsg("部署成功!");
        } else {
            response.setCode(1);
            response.setMsg("部署失败!");
        }
        return response;
    }

    @RequestMapping({"nodeList"})
    @WebLogger("获取引擎节点列表")
    @ResponseBody
    public Response queryEngineNodeListByVerionId(Long versionId) {
        List nodeList = engineNodeService.queryNodeListByVerId(versionId);
        return new Response((List)(CollectionUtil.isNotNullOrEmpty(nodeList)?nodeList:new ArrayList()));
    }

    /**
     * 判断沙盒与分群分组与连线是否匹配
     * @param paramMap
     * @param types
     * @return
     */
    @RequestMapping({"getTypedNodes"})
    @WebLogger("通过节点类型获取引擎节点")
    @ResponseBody
    public Response queryEngineNodesByType(@RequestParam Map<String, Object> paramMap, @RequestParam("types[]") List<Integer> types) {
        Long versionId = Long.valueOf(Long.parseLong(paramMap.get("versionId").toString()));
        List nodeList = engineNodeService.queryEngineTypedNodeListByEngineVerId(versionId, types);
        boolean flag = true;
        boolean flag1 = true;
        if(nodeList != null && nodeList.size() > 0) {
            Iterator var8 = nodeList.iterator();

            while(var8.hasNext()) {
                EngineNode engineNode = (EngineNode)var8.next();
                if(engineNode.getNodeType().intValue() == 7 && flag && !StringUtil.isBlank(engineNode.getNodeJson())) {
                    JSONArray jsonObj = JSONArray.parseArray(engineNode.getNodeJson());
                    if(!StringUtil.isBlank(engineNode.getNextNodes())) {
                        String[] json = engineNode.getNextNodes().split(",");
                        paramMap.put("sanBoxNode", engineNode);
                        if(jsonObj.size() == json.length) {
                            paramMap.put("sanbox", "1");
                        } else {
                            paramMap.put("sanbox", "0");
                            flag = false;
                        }
                    } else {
                        paramMap.put("sanbox", "0");
                        flag = false;
                    }
                }

                if(engineNode.getNodeType().intValue() == 3 && flag1 && !StringUtil.isBlank(engineNode.getNodeJson())) {
                    JSONObject jsonObj1 = JSONObject.parseObject(engineNode.getNodeJson());
                    JSONArray json1 = jsonObj1.getJSONArray("conditions");
                    if(!StringUtil.isBlank(engineNode.getNextNodes())) {
                        String[] nextNodes = engineNode.getNextNodes().split(",");
                        paramMap.put("groupNode", engineNode);
                        if(json1.size() == nextNodes.length) {
                            paramMap.put("group", "1");
                        } else {
                            paramMap.put("group", "0");
                            flag1 = false;
                        }
                    } else {
                        paramMap.put("group", "0");
                        flag1 = false;
                    }
                }
            }
        } else {
            paramMap.put("sanbox", "-1");
            paramMap.put("group", "-1");
        }

        return new Response(paramMap);
    }

    @RequestMapping({"undeploy"})
    @WebLogger("引擎停止部署")
    @ResponseBody
    public Response stopEngineVersion(Long versionId) {
        HashMap map = new HashMap();
        int count = engineVersionService.undeployEngine(versionId);
        Response response=new Response();
        if(count == 1) {
            map.put("status", Integer.valueOf(1));
            response.setMsg("当前版本已停用!");
        } else {
            map.put("status", Integer.valueOf(0));
            response.setMsg("停用当前版本失败!");
        }
        response.setData(map);
        return response;
    }

    /**
     * 删除引擎版本
     * @param engineVersion
     * @return
     */
    @RequestMapping({"delete"})
    @WebLogger("删除引擎版本")
    @ResponseBody
    public Response deleteEngineVersion(@RequestBody EngineVersion engineVersion) {
        HashMap map = new HashMap();
        int bootState = engineVersion.getBootState().intValue();
        Response response=new Response();
        if(1 == bootState) {
            map.put("status", Integer.valueOf(0));
            response.setMsg("当前版本正在运行,不能删除!");
        } else {
            engineVersion.setStatus(Integer.valueOf(0));
            int count = engineVersionService.update(engineVersion);
            if(count == 1) {
                map.put("status", Integer.valueOf(1));
                response.setMsg("当前版本删除成功!");
            } else {
                map.put("status", Integer.valueOf(0));
                response.setMsg(" 异常,当前版本删除失败!");
            }
        }
        response.setData(map);
        return response;
    }

    @RequestMapping({"save"})
    @WebLogger("保存版本")
    public Long saveEngineVersion(@RequestBody EngineVersion engineVersion , HttpServletRequest request) {
        //获取上下文路径
        String url = request.getContextPath();
        List<EngineNode> nodeList = new ArrayList<EngineNode>();
        EngineNode engineNode = new EngineNode();
        engineNode.setNodeX(200.0D);
        engineNode.setNodeY(200.0D);
        engineNode.setNodeName("开始");
        engineNode.setNodeType(1);
        engineNode.setNodeOrder(1);
        engineNode.setNodeCode("ND_START");
        engineNode.setParams("{\"arr_linkId\":\"\",\"dataId\":\"-1\",\"url\":\""+url+"/resources/images/decision/start.png\",\"type\":\"1\"}");
        nodeList.add(engineNode);
        Long versionId = engineVersionService.saveEngineVersion(engineVersion, nodeList);
        return versionId;
    }

    /**
     * 获取报表列表
     * @param verId 版本id
     * @return response
     */
    @GetMapping({"/statement"})
    @ResponseBody
    public Object queryStatementList(@RequestParam Long verId){
        Map<String , Object> param = new HashMap<>();
        param.put("verId",verId);
        //获取当前版本结果集
        List<EngineResultSet> list = resultSetService.queryResultList(param);
        int refuseCount = 0;//拒绝数量
        int passCount = 0;//通过数量
        int elseCount = 0;//其他
        if(list.size()>0){
            for(int i = 0;i < list.size();i++){
                if("拒绝".equals(list.get(i).getResult())){
                    refuseCount++;
                }else if("通过".equals(list.get(i).getResult())){
                    passCount++;
                }else {
                    elseCount++;
                }
            }
        }
        Map<String , Object> map = new HashMap<>();
        map.put("total",list.size());
        map.put("refuseCount",refuseCount);
        map.put("passCount",passCount);
        map.put("elseCount",elseCount);
        return new Response(map);
    }
}

