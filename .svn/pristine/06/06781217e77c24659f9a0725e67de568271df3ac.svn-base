package com.zw.rule.web.engine.controller;

import com.zw.rule.core.Response;
import com.zw.rule.engine.po.CustomNode;
import com.zw.rule.engine.service.CustomNodeService;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 节点管理
 * @author zongpeng
 * @Time 2017-6-19
 */

@Controller
@RequestMapping({"customNode"})
public class CustomNodeController {

    @Resource
    CustomNodeService customNodeService;

    /**
     * 获取节点列表
     * @param
     * engineId | Long | 必需 | 引擎id
     * @return
     * List: 节点列表
     */
    @GetMapping({"/list"})
    @ResponseBody
    public Object getNodeList(@RequestParam Long engineId){
        //获取登录用户id
        Long userId = UserContextUtil.getUserId();
        List<CustomNode> list = customNodeService.getNodeList(userId);
        return new Response(list);
    }

    /**
     * 添加节点
     * @param
     * customNode | CustomNode | 必需 | 自定义节点
     * @return
     * msg
     */
    @PostMapping({"/addNode"})
    @ResponseBody
    public Object addNode(@RequestBody CustomNode customNode){
        //获取登录用户id
        Long userId = UserContextUtil.getUserId();
        customNode.setCreateBy(userId);
        Response response = new Response();
        Map<String , Object> param = new HashMap<>();
        param.put("text",customNode.getText());
        param.put("engineId",customNode.getEngineId());
        int count = customNodeService.isExists(param);
        if(count > 0){
            response.setCode(1);
            response.setMsg("节点名称已存在，请重新输入！");
            return response;
        }
        customNodeService.addNode(customNode);
        return new Response("添加成功！");
    }

    /**
     * 修改节点
     * @param
     * customNode | CustomNode | 必需 | 自定义节点
     * @return
     * msg
     */
    @PostMapping({"/updateNode"})
    @ResponseBody
    public Object updateNode(@RequestBody CustomNode customNode){
        //获取登录用户id
        Long userId = UserContextUtil.getUserId();
        customNode.setUpdateBy(userId);
        Response response = new Response();
        Map<String , Object> param = new HashMap<>();
        param.put("id",customNode.getId());
        param.put("text",customNode.getText());
        param.put("userId",userId);
        int count = customNodeService.isExists(param);
        if(count > 0){
            response.setCode(1);
            response.setMsg("节点名称已存在，请重新输入！");
            return response;
        }
        customNodeService.updateNode(customNode);
        return new Response("修改成功！");
    }

    /**
     * 查看节点
     * @param
     * id | Long | 必需 | 自定义节点id
     * @return
     * customNode
     */
    @GetMapping({"/getNode"})
    @ResponseBody
    public Object getNode(@RequestParam Long id){
        CustomNode customNode = customNodeService.getNode(id);
        return new Response(customNode);
    }

    /**
     * 删除节点
     * @param
     * id | Long | 必需 | 自定义节点id
     * @return
     * customNode
     */
    @GetMapping({"/deleteNode"})
    @ResponseBody
    public Object deleteNode(@RequestParam Long id){
        customNodeService.deleteNode(id);
        return new Response("删除成功!");
    }

}
