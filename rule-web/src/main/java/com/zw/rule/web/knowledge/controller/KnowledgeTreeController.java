package com.zw.rule.web.knowledge.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.datamanage.service.FieldService;
import com.zw.rule.knowledge.po.KnowledgeTree;
import com.zw.rule.knowledge.po.Rule;
import com.zw.rule.knowledge.po.Scorecard;
import com.zw.rule.knowledge.service.*;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.mybatis.page.Page;
import com.zw.rule.po.User;
import com.zw.rule.util.StringUtil;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping({"knowledge/tree"})
public class KnowledgeTreeController {
    @Resource
    private KnowledgeTreeService knowledgeTreeService;
    @Resource
    private RuleService ruleService;
    @Resource
    private ScorecardService scorecardService;
    @Resource
    private KnowledgeTreeRelService knowledgeTreeRelService;
    @Resource
    private EngineRuleRelService engineRuleRelService;
    @Resource
    private FieldService fieldService;

    @RequestMapping
    public String index(Model model,@RequestParam Map<String, Object> param, HttpServletRequest request) {
        //规则根据引擎是否为空分别跳转规则管理-规则集模块和引擎管理的知识库模块
        if(param.get("engineId") != null && param.get("engineId") != "") {
            model.addAttribute("engineId",param.get("engineId"));
            request.setAttribute("e_id", param.get("engineId"));
            UserContextUtil.setAttribute("engId", param.get("engineId"));
        }
        return "rulesMange/rule-collection";
    }

    @GetMapping({"tree"})
    @WebLogger("规则管理知识树")
    @ResponseBody
    public Response queryTree(@RequestParam Map<String, Object> param) {
        param.putAll(getParam(param));
        param.put("treeType", StringUtil.toLongList(param.get("treeType").toString()));
        List kList = knowledgeTreeService.getTreeList(param);
        return new Response(kList);
    }

    @PostMapping({"/getTreeDataForEngine"})
    @ResponseBody
    @WebLogger("引擎管理规则集按钮的查看详情")//引擎管理-决策流-决策流程布局图-规则集按钮的查看 调用
    public Response queryTreeDataForEngine(@RequestBody ParamFilter queryFilter) {
        Map param = queryFilter.getParam();
        param.putAll(getParam(param));
        param.put("sort", Boolean.valueOf(false));
        param.put("treeType", StringUtil.toLongList("0"));
        param.put("status", StringUtil.toLongList(param.get("status").toString()));
        Page page = queryFilter.getPage();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, page.getPageSize());
        List kList = knowledgeTreeService.getTreeDataForEngine(param);
        PageInfo pageInfo = new PageInfo(kList);
        return new Response(pageInfo);
    }

    @PostMapping({"/save"})
    @ResponseBody
    @WebLogger("规则集新建节点")
    public Response save(@RequestBody KnowledgeTree k) {
        Response response = new Response();
        k.setUserId(UserContextUtil.getUserId());
        k.setOrgId(UserContextUtil.getOrganId());
        k.setStatus(1);
        if(knowledgeTreeService.insertTree(k)) {
            response.setData(k);
            response.setMsg("添加成功！");
        } else {
            response.setCode(1);
            response.setMsg("添加失败！");
        }
        return response;
    }

    @PostMapping({"/update"})
    @ResponseBody
    @WebLogger("规则集编辑节点")
    public Response update(@RequestBody KnowledgeTree k) {
        Response response = new Response();
        k.setUserId(UserContextUtil.getUserId());
        k.setOrgId(UserContextUtil.getOrganId());
        String name = k.getName();
        if(name.equals("规则集") || name.equals("回收站") || name.equals("评分卡")){
            response.setCode(1);
            response.setMsg("该名称不能使用！");
        }else{
            if(knowledgeTreeService.updateTree(k)) {
                response.setMsg("修改成功！");
            } else {
                response.setCode(1);
                response.setMsg("修改失败！");
            }
        }
        return response;
    }

    @PostMapping({"/delete"})
    @ResponseBody
    @WebLogger("规则集删除节点")
    public Response delete(@RequestBody Map<String, Object> param) {
        param.putAll(getParam(param));
        Response response = new Response();
        String ids = param.get("ids").toString();
        if(knowledgeTreeService.deleteStatusById(param)) {
            ArrayList idList = new ArrayList();
            HashMap map = new HashMap();
            //String ids = param.get("ids").toString();这句代码写在这，ids会有中括号，写在上面就是正常的字符串（疑问？）
            map.put("parentIds", StringUtil.toLongList(ids));
            map.put("type", param.get("type"));
            map.put("engineId", param.get("engineId"));
            map.put("orgId", param.get("orgId"));
            List sList;
            Iterator it;
            long treeType = Long.valueOf(Long.parseLong(param.get("treeType").toString()));
            if(treeType == 0) {
                sList = ruleService.getRuleList(map);
                if(sList != null && sList.size() > 0) {
                    it = sList.iterator();
                    while(it.hasNext()) {
                        Rule s = (Rule)it.next();
                        idList.add(s.getId());
                    }
                    map.put("status", -1);
                    map.put("idList", idList);
                    ruleService.updateRuleStatus(map);
                }
            }
            if(treeType == 1) {
                sList = scorecardService.queryScorecardList(map);
                if(sList != null && sList.size() > 0) {
                    it = sList.iterator();
                    while(it.hasNext()) {
                        Scorecard s1 = (Scorecard)it.next();
                        idList.add(s1.getId());
                    }
                    map.put("status", -1);
                    map.put("idList", idList);
                    scorecardService.updateScorecardStatus(map);
                }
            }
            response.setMsg("删除成功！");
        } else {
            response.setCode(1);
            response.setMsg("删除失败！");
        }
        return response;
    }

    /**
     * 添加公共规则取点复选框时调用
     * @param param{parentIds:树节点id}
     * @return
     */
    @RequestMapping({"getRuleIdsByParentId"})
    @ResponseBody
    public Response queryRuleIdsByParentId(@RequestParam Map<String, Object> param) {
        String idsStr = (String)param.get("parentIds");
        param.put("parentIds", StringUtil.toLongList(idsStr));
        List ruleIdList = ruleService.getRuleIdsByParentId(param);
        param.put("ruleIdList", ruleIdList);
        return new Response(param);
    }

    @PostMapping({"/addRel"})
    @ResponseBody
    @WebLogger("添加引擎通用规则")
    public Response addRule(@RequestBody Map<String, Object> param) {
        Response response = new Response();
        Long engineId = Long.valueOf(Long.parseLong(param.get("engineId").toString()));
        //加入之前清空数据库里之前的关联数据
        knowledgeTreeRelService.deleteRel(engineId);
        if(param.get("ids")!=null && !param.get("ids").equals("")){
            String idsStr = (String)param.get("ids");
            List ruleIds = new ArrayList();
            param.put("parentIds", StringUtil.toLongList(idsStr));
            if(!StringUtil.isBlank(idsStr)) {
                ruleIds = ruleService.getRuleIdsByParentId(param);
            }
            if(ruleIds.isEmpty()){
                response.setCode(1);
                response.setMsg("该节点没有规则，请重选！");
                return response;
            }
            HashMap map = new HashMap();
            map.put("idList", ruleIds);
            map.put("engineId", engineId);
            engineRuleRelService.deleteRel(map);
            if(param.containsKey("engineId") && !StringUtil.isBlank(idsStr)) {
                param.put("idList", StringUtil.toLongList(idsStr));
                boolean flag = knowledgeTreeRelService.insertRel(param);
                engineRuleRelService.insertRel(map);
//                List ids = ruleService.getFieldIdsByRuleId((List)ruleIds);
//                if(ids.isEmpty()){
//                    response.setCode(1);
//                    response.setMsg("该节点没有字段，请重选！");
//                    return response;
//                }
//                StringBuffer sb = new StringBuffer();
//                Iterator it = ids.iterator();
//                while(it.hasNext()) {
//                    String map_1 = (String)it.next();
//                    String[] str = map_1.split("\\|");
//                    sb.append(str[0]).append(",");
//                }
//                sb = sb.deleteCharAt(sb.length() - 1);
//                HashMap map_11 = new HashMap();
//                map_11.putAll(getParam(map_11));
//                map_11.put("fieldIds", sb.toString());
//                map_11.put("engineId", "" + engineId);
//                fieldService.bindEngineField(map_11);
                if(flag) {
                    response.setMsg("添加成功！");
                } else {
                    response.setCode(1);
                    response.setMsg("添加失败！");
                }
            } else {
                response.setCode(1);
                response.setMsg("添加失败！");
            }
        }else response.setMsg("清空通用规则！");
        return response;
    }

    /**
     *引擎管理-知识库-编辑 调用
     * @param param
     * @return
     */
    @RequestMapping({"/getTreeIds"})
    @ResponseBody
    public Response queryTreeIds(@RequestBody Map<String, Object> param) {
        Long engineId = Long.valueOf(Long.parseLong(param.get("engineId").toString()));
        List idList = knowledgeTreeRelService.findTreeIdsByEngineId(engineId);
        param.put("idList", idList);
        return new Response(param);
    }

    private Map<String, Object> getParam(Map<String, Object> paramMap) {
        User user = (User)UserContextUtil.getAttribute("currentUser");
        paramMap.put("userId", user.getUserId());
        paramMap.put("orgId", user.getOrgId());
        return paramMap;
    }
}
