package com.zw.rule.web.engine.controller;

import com.zw.rule.core.Response;
import com.zw.rule.datamanage.po.ListDb;
import com.zw.rule.datamanage.po.NodeListDb;
import com.zw.rule.datamanage.service.FieldService;
import com.zw.rule.datamanage.service.ListDbService;
import com.zw.rule.engine.po.EngineNode;
import com.zw.rule.engine.po.EngineVersion;
import com.zw.rule.engine.service.EngineNodeService;
import com.zw.rule.engine.service.EngineVersionService;
import com.zw.rule.jeval.tools.StringUtil;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2017/5/12 0012.
 */
@Controller
@RequestMapping({"bwListNode"})
public class ListDbNodeController {
    public ListDbNodeController() {
    }
    @Resource
    private ListDbService listDbService;
    @Resource
    private EngineVersionService engineVersionService;
    @Resource
    private FieldService fieldService;
    @Resource
    private EngineNodeService engineNodeService;
    @RequestMapping({"/blackList"})
    public Response queryBlackList(@RequestBody Map<String, Object> param) {
        String canEdit = (String)param.get("canEdit");
        String innerNodeListDb = null;
        String outerNodeListDb = null;
        if(!param.get("nodeId").equals("0")) {
            NodeListDb mav = listDbService.queryNodeListDbByNodeId(param);
            innerNodeListDb = mav.getInsideListdbs();
            outerNodeListDb = mav.getOutsideListdbs();
        }
        HashMap map = new HashMap();
        map.put("canEdit", canEdit);
        map.put("nodeId", param.get("nodeId"));
        map.put("innerNodeListDb", innerNodeListDb);
        map.put("outerNodeListDb", outerNodeListDb);
        return new Response(map);
    }

    /**
     * 根据节点编号获取黑白名单节点
     * @param   paramMap 包含一下参数：<br>
     * listType | String | 必需 | 黑白名单类型 黑名单:b,白名单:w <br>
     * nodeId | Long | 必需 | 节点编号
     * @return
     * innerNodeListDb: 内部黑白名单节点信息 <br>
     * outerNodeListDb: 外部黑白名单节点信息 <br>
     */
    @RequestMapping({"/findBlackList"})
    @ResponseBody
    public Response queryBlackListByNodeId(@RequestBody Map<String, Object> paramMap) {
        Long userId = UserContextUtil.getUserId();
        paramMap.put("userId", userId);
        List innerNodeListDb = new ArrayList();
        List outerNodeListDb = new ArrayList();
        if(!paramMap.get("nodeId").equals("0")) {
            //获取黑白名单节点信息
            NodeListDb nodeListDb = listDbService.queryNodeListDbByNodeId(paramMap);
            //获取内部黑白名单节点id
            String strInnerNodeListDb = nodeListDb.getInsideListdbs();
            if(!strInnerNodeListDb.equals("")) {
                String[] arrInnerNodeListDb = strInnerNodeListDb.split(",");
                //获取内部黑白名单信息
                for(int i = 0; i < arrInnerNodeListDb.length; i++) {
                    paramMap.put("id", Integer.valueOf(arrInnerNodeListDb[i]));
                    ListDb listDb = listDbService.findById(paramMap);
                    innerNodeListDb.add(listDb);
                }
            }
            //获取外部黑白名单节点id
            String strOuterNodeListDb = nodeListDb.getOutsideListdbs();
            if(!strOuterNodeListDb.equals("")) {
                String[] arrOuterNodeListDb = strOuterNodeListDb.split(",");
                //获取外部黑白名单信息
                for(int i = 0; i < arrOuterNodeListDb.length; i++) {
                    paramMap.put("id", Integer.valueOf(arrOuterNodeListDb[i]));
                    ListDb listDb = listDbService.findById(paramMap);
                    outerNodeListDb.add(listDb);
                }
            }
        }
        Map<String , Object> map = new HashMap<String , Object>();
        map.put("innerNodeListDb", innerNodeListDb);
        map.put("outerNodeListDb", outerNodeListDb);
        return new Response(map);
    }

    @RequestMapping({"/findNodeListDb"})
    @ResponseBody
    public Response queryNodeListDb(@RequestBody Map<String, Object> paramMap) {
        String innerNodeListDb = null;
        String outerNodeListDb = null;
        if(!paramMap.get("nodeId").equals("0")) {
            NodeListDb nodeListDb = listDbService.queryNodeListDbByNodeId(paramMap);
            innerNodeListDb = nodeListDb.getInsideListdbs();
            outerNodeListDb = nodeListDb.getOutsideListdbs();
        }
        paramMap.put("innerNodeListDb", innerNodeListDb);
        paramMap.put("outerNodeListDb", outerNodeListDb);
        return new Response(paramMap);
    }

    @PostMapping({"/create"})
    @WebLogger("保存节点")
    @ResponseBody
    public Response addNode(@RequestBody Map<String, Object> paramMap) {
        EngineNode engineNode = new EngineNode();
        engineNode.setNodeX(Double.parseDouble(paramMap.get("node_x").toString()));
        engineNode.setNodeY(Double.parseDouble(paramMap.get("node_y").toString()));
        engineNode.setNodeName((String)paramMap.get("node_name"));
        engineNode.setVerId(Long.valueOf(paramMap.get("verId").toString()));
        engineNode.setNodeCode((String)paramMap.get("node_code"));
        engineNode.setNodeType(Integer.valueOf(paramMap.get("node_type").toString()));
        engineNode.setNodeOrder(Integer.valueOf(paramMap.get("node_order").toString()));
        engineNode.setParams((String)paramMap.get("params"));
        //添加节点信息
        engineNodeService.addNode(engineNode);
        paramMap.put("nodeId", engineNode.getNodeId());
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        paramMap.put("userId", userId);
        paramMap.put("organId", organId);
        //内部黑白名单id,逗号分割
        String insideListdbs = String.valueOf(paramMap.get("insideListdbs"));
        //外部黑白名单id,逗号分割
        String outsideListdbs = String.valueOf(paramMap.get("outsideListdbs"));
        String strIds = "";
        if(!insideListdbs.equals("")) {
            if(!outsideListdbs.equals("")) {
                strIds = insideListdbs + "," + outsideListdbs;
            } else {
                strIds = insideListdbs;
            }
        } else if(!outsideListdbs.equals("")) {
            strIds = outsideListdbs;
        }
        List listDbIds = StringUtil.toLongList(strIds);
        paramMap.put("listDbIds", listDbIds);
        //根据id获取名单库维护字段
        String strFieldIds = listDbService.findFieldsByListDbIds(paramMap);
        //获取版本编号
        Long versionId = Long.valueOf(String.valueOf(paramMap.get("verId")));
        //获取引擎版本
        EngineVersion engineVesion = engineVersionService.queryByPrimaryKey(versionId);
        Long engineId = engineVesion.getEngineId();
        paramMap.put("engineId", engineId);
        paramMap.put("fieldIds", strFieldIds);
        //字段用户映射关联
//        fieldService.bindEngineField(paramMap);
        //关联节点和内外部黑白名单
        listDbService.addNodeListDb(paramMap);
        return new Response(engineNode.getNodeId());
    }

    @RequestMapping({"/update"})
    @WebLogger("修改节点")
    @ResponseBody
    public Response updateNode(@RequestBody HashMap<String, Object> paramMap) {
        Long userId = UserContextUtil.getUserId();
        paramMap.put("userId", userId);
        String innerListdbs = String.valueOf(paramMap.get("insideListdbs"));
        String outerListdbs = String.valueOf(paramMap.get("outsideListdbs"));
        String strIds = "";
        if(!innerListdbs.equals("")) {
            if(!outerListdbs.equals("")) {
                strIds = innerListdbs + "," + outerListdbs;
            } else {
                strIds = innerListdbs;
            }
        } else if(!outerListdbs.equals("")) {
            strIds = outerListdbs;
        }
        List listDbIds = StringUtil.toLongList(strIds);
        paramMap.put("listDbIds", listDbIds);
        String strFieldIds = listDbService.findFieldsByListDbIds(paramMap);
        Long versionId = Long.valueOf(String.valueOf(paramMap.get("verId")));
        EngineVersion engineVesion = engineVersionService.queryByPrimaryKey(versionId);
        Long engineId = engineVesion.getEngineId();
        paramMap.put("engineId", engineId);
        paramMap.put("fieldIds", strFieldIds);
//        fieldService.bindEngineField(paramMap);
        listDbService.updateNodeListDb(paramMap);
        return new Response(paramMap);
    }
}
