package com.zw.rule.web.knowledge.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.datamanage.po.Field;
import com.zw.rule.datamanage.service.FieldService;
import com.zw.rule.engine.service.NodeKnowledgeService;
import com.zw.rule.jeval.tools.StringUtil;
import com.zw.rule.knowleage.util.ExcelHeader;
import com.zw.rule.knowleage.util.ExcelUtil;
import com.zw.rule.knowledge.po.Rule;
import com.zw.rule.knowledge.po.RuleContent;
import com.zw.rule.knowledge.po.RuleField;
import com.zw.rule.knowledge.service.EngineRuleRelService;
import com.zw.rule.knowledge.service.RuleFieldService;
import com.zw.rule.knowledge.service.RuleService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 规则集管理
 * @author zongpeng
 */
@Controller
@RequestMapping({"knowledge/rule"})
public class RuleController {

    @Resource
    private RuleService ruleService;
    @Resource
    private FieldService fieldService;
    @Resource
    private EngineRuleRelService engineRuleRelService;
    @Resource
    private NodeKnowledgeService nodeKnowledgeService;
    @Resource
    private RuleFieldService ruleFieldService;

    @PostMapping({"/list"})
    @ResponseBody
    public Response queryList(@RequestBody ParamFilter queryFilter) {
        queryFilter.getParam().putAll(this.getParam(queryFilter.getParam()));
        if(! queryFilter.getParam().containsKey("status")) {
            queryFilter.getParam().put("status", StringUtil.toLongList("0,1"));
        } else {
            queryFilter.getParam().put("status", StringUtil.toLongList( queryFilter.getParam().get("status").toString()));
        }

        if( queryFilter.getParam().containsKey("parentIds")) {
            queryFilter.getParam().put("parentIds", StringUtil.toLongList( queryFilter.getParam().get("parentIds").toString()));
        }
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<Rule> klist = ruleService.getRuleList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(klist);
        return new Response(pageInfo);

    }

    /**
     * 根据引擎获取规则集
     * @param   queryFilter param 包含一下参数：<br>
     * engineId | Long | 必需 | 引擎id <br>
     * status | Integer | 可选 | 状态，启用:1，停用:0，删除:-1 <br>
     * parentIds | String | 必需 | 规则集树节点id
     * @return
     * rlist: 规则集
     */
    @RequestMapping({"/getRuleDataForEngine"})
    @ResponseBody
    public Response queryRuleForEngine(@RequestBody ParamFilter queryFilter) {
        queryFilter.getParam().putAll(this.getParam(queryFilter.getParam()));
        queryFilter.getParam().put("status", StringUtil.toLongList(queryFilter.getParam().get("status").toString()));
        if(queryFilter.getParam().containsKey("parentIds")) {
            queryFilter.getParam().put("parentIds", StringUtil.toLongList(queryFilter.getParam().get("parentIds").toString()));
        }
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        //获取对应规则集
        List<Rule> rlist = ruleService.getRuleList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(rlist);
        return new Response(pageInfo);
    }

    @RequestMapping({"/save"})
    @WebLogger("保存规则")
    @ResponseBody
    public Response saveRule(@RequestBody Map<String, Object> param) {
        param.putAll(this.getParam(param));
        Response response = new Response();
        int countName = ruleService.countOnlyRuleName(param);
        if(countName > 0){
            response.setCode(1);
            response.setMsg("规则名称已存在，请重新输入！");
            return response;
        }
        int countCode = ruleService.countOnlyRuleCode(param);
        if(countCode > 0){
            response.setCode(1);
            response.setMsg("规则代码已存在，请重新输入！");
            return response;
        }
        Rule rule = new Rule();
        rule.setName((String)param.get("name"));//规则名称
        rule.setCode((String)param.get("code"));//规则代码
        rule.setDesc((String)param.get("desc"));//规则描述
        rule.setPriority(Integer.valueOf(param.get("priority").toString()));//规则优先级
        rule.setParentId(Long.valueOf(param.get("parentId").toString()));//父节点id
        rule.setContent((String)param.get("content"));//规则具体内容
        rule.setRuleAudit(Integer.valueOf(param.get("ruleAudit").toString()));//审批建议
        if(!StringUtil.isBlank((String)param.get("score"))){
            rule.setScore(Integer.valueOf(param.get("score").toString()));//得分
        }
        rule.setLastLogical((String)param.get("lastLogical"));//逻辑关系符
        if(rule.getRuleAudit() == 2) {
            rule.setRuleType(0);
        } else {
            rule.setRuleType(1);
        }
        param.put("rule", rule);
        ruleService.insertRule(param);
        response.setMsg("保存成功");
        return response;
    }
    @RequestMapping({"/edit"})
    @ResponseBody
    public Response queryInfo(@RequestBody Map<String , Object> param) {
        //获取规则id
        Long id = Long.valueOf(param.get("id").toString());
        //获取规则信息
        Rule rule = ruleService.findById(id);
        param.putAll(this.getParam(param));
        //获取规则中字段信息
        List<RuleField> ruleFieldList = ruleFieldService.getFieldList(id);
        String engineId = (String)param.get("engineId");
        param.put("isOutput", 0);
        List flist = fieldService.queryFieldList(param);
        param.put("isOutput", 1);
        List olist = fieldService.queryFieldList(param);
        Map<String , Object> map = new HashMap<String , Object>();
        map.put("engineId", engineId);
        map.put("rule", rule);
        map.put("ruleFieldList", ruleFieldList);
        map.put("flist", flist);
        map.put("olist", olist);
        return new Response(map);
    }
    @RequestMapping({"/update"})
    @WebLogger("修改规则")
    @ResponseBody
    public Response updateRule(@RequestBody Map<String, Object> param) {
        param.putAll(this.getParam(param));
        Response response = new Response();
        int countName = ruleService.countOnlyRuleName(param);
        if(countName > 0){
            response.setCode(1);
            response.setMsg("规则名称已存在，请重新输入！");
            return response;
        }
        int countCode = ruleService.countOnlyRuleCode(param);
        if(countCode > 0){
            response.setCode(1);
            response.setMsg("规则代码已存在，请重新输入！");
            return response;
        }
        Rule rule = new Rule();
        rule.setId(Long.valueOf(param.get("id").toString()));
        rule.setName((String)param.get("name"));
        rule.setCode((String)param.get("code"));
        rule.setDesc((String)param.get("desc"));
        rule.setPriority(Integer.valueOf(param.get("priority").toString()));
        rule.setContent((String)param.get("content"));
        rule.setRuleAudit(Integer.valueOf(param.get("ruleAudit").toString()));
        String score = (String)param.get("score");
        if(score != null && !"".equals(score)){
            rule.setScore(Integer.valueOf(score));
        }
        rule.setLastLogical((String)param.get("lastLogical"));
        if(rule.getRuleAudit() == 2) {
            rule.setRuleType(0);
        } else {
            rule.setRuleType(1);
        }
        param.put("rule", rule);
        if(ruleService.updateRule(param)){
            response.setMsg("更新成功！");
        }else{
            response.setCode(1);
            response.setMsg("更新失败！");
        }
        return response;
    }
    @RequestMapping({"/updateStatus"})
    @WebLogger("修改规则状态")
    @ResponseBody
    public Response updateStatus(@RequestBody Map<String, Object> param) {
        param.putAll(this.getParam(param));
        ruleService.updateRuleStatus(param);
        return new Response("修改成功！");
    }
    @RequestMapping({"/updateStatusForReferenceRule"})
    @WebLogger("批量修改引擎引用规则状态")
    @ResponseBody
    public Response updateStatusForReferenceRule(@RequestBody Map<String, Object> param) {
        String idsStr = (String)param.get("ids");
        String engineId = (String)param.get("engineId");
        param.put("idList", StringUtil.toLongList(idsStr));
        int status = Integer.parseInt(param.get("status").toString());
        boolean flag = false;
        flag = engineRuleRelService.deleteRel(param);
        if(status == 1) {
            List ids = ruleService.getFieldIdsByRuleId(StringUtil.toLongList(idsStr));
            StringBuffer sb = new StringBuffer();
            Iterator iterator = ids.iterator();

            while(iterator.hasNext()) {
                String map_1 = (String)iterator.next();
                String[] str = map_1.split("\\|");
                sb.append(str[0]).append(",");
            }

            sb = sb.deleteCharAt(sb.length() - 1);
            HashMap map_11 = new HashMap();
            map_11.put("fieldIds", sb.toString());
            map_11.put("engineId", engineId);
//            fieldService.bindEngineField(map_11);
            flag = engineRuleRelService.insertRel(param);
        }
        Response response = new Response();
        if(flag) {
            response.setMsg("修改成功");
        } else {
            response.setCode(1);
            response.setMsg("修改失败");
        }
        response.setData(param);
        return response;
    }

    /**
     * 导出规则
     * @param param
     * @param response
     */
    @RequestMapping({"/export"})
    @ResponseBody
    public void downLoad(@RequestParam Map<String, Object> param, HttpServletResponse response) {
        List parentIds = null;
        if("rule_root".equals(param.get("parentId").toString())){//导出所有
            parentIds = null;
        }else{
            parentIds = StringUtil.toLongList(param.get("parentId").toString());//规则所属文件的id
        }
        List status =  StringUtil.toLongList("1");//状态为启用的
        param.put("parentIds", parentIds);
        param.put("status", status);
        param.putAll(this.getParam(param));
        List list = ruleService.getRuleExcelData(param);
        try {
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename=" + new String("规则.xls".getBytes("utf-8"), "ISO8859-1"));
            ExcelUtil.exportExcel(response.getOutputStream(), ".xls", ExcelHeader.RULE_HEADER, ExcelHeader.RULE_ClASS, list);
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 导入规则
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(
            value = {"/upload"},
            method = {RequestMethod.POST, RequestMethod.GET}
    )
    @ResponseBody
    public Response upload(@RequestParam("filename") CommonsMultipartFile file,@RequestParam("nodeId") String nodeId) throws Exception {
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        Response response = new Response();
        try {
            Long parentId = Long.valueOf(nodeId);//规则所属文件夹的id
            POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet hssfSheet = wb.getSheetAt(0);
            HashMap map = new HashMap();
            if(hssfSheet != null) {
                ArrayList list = new ArrayList();
                for(int param = 1; param <= hssfSheet.getLastRowNum(); ++param) {
                    HSSFRow hssfRow = hssfSheet.getRow(param);
                    if(hssfRow != null && hssfRow.getCell(0) != null) {
                        Rule rule = new Rule();
                        rule.setName(ExcelUtil.formatCell(hssfRow.getCell(0)));
                        rule.setCode(ExcelUtil.formatCell(hssfRow.getCell(1)));
                        rule.setDesc(ExcelUtil.formatCell(hssfRow.getCell(2)));
                        rule.setPriority(Integer.valueOf(Integer.parseInt(ExcelUtil.formatCell(hssfRow.getCell(3)).replace(".0", ""))));
                        rule.setUserId(userId);
                        rule.setStatus(Integer.valueOf(1));
                        String ruleFields = ExcelUtil.formatCell(hssfRow.getCell(4));
                        String str = ExcelUtil.formatCell(hssfRow.getCell(5));
                        if(!ExcelUtil.formatCell(hssfRow.getCell(5)).equals("") && ExcelUtil.formatCell(hssfRow.getCell(5)) != null){
                            rule.setScore(Integer.valueOf(subZeroAndDot(ExcelUtil.formatCell(hssfRow.getCell(5)))));
                        }
                        rule.setRuleFieldList(this.handExcelDataForRuleField(ruleFields));
                        String ruleCont = ExcelUtil.formatCell(hssfRow.getCell(8));//规则包路径
                        rule.setContent(ruleCont);
                        if(!"".equals(ExcelUtil.formatCell(hssfRow.getCell(9)))){
                            rule.setType(Integer.valueOf(ExcelUtil.formatCell(hssfRow.getCell(9))));
                        }
                        rule.setRuleAudit(Integer.valueOf(ExcelUtil.formatCell(hssfRow.getCell(10))));
                        rule.setRuleType(Integer.valueOf(ExcelUtil.formatCell(hssfRow.getCell(11))));
                        if(!"".equals(ExcelUtil.formatCell(hssfRow.getCell(12)))){
                            rule.setLastLogical(ExcelUtil.formatCell(hssfRow.getCell(12)));
                        }
                        list.add(rule);
                    }
                }
                HashMap param = new HashMap();
                param.put("newOrganId", organId);
                param.put("newParentId", parentId);
                param.put("newType", Integer.valueOf(1));
                param.put("creator",userId);
                ruleService.batchAddRule(param, list);
                response.setMsg("导入成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(1);
            response.setMsg("数据有误，请重新上传！");
        }
        return response;
    }

    private List<RuleField> handExcelDataForRuleField(String ruleFields) {
        ArrayList ruleFieldList = new ArrayList();
        if(!StringUtil.isBlank(ruleFields)) {
            String[] field = ruleFields.trim().split("\n");

            for(int i = 0; i < field.length; ++i) {
                String[] str = field[i].split(";");
                RuleField ruleField = new RuleField();
                ruleField.setLogicalSymbol(str[0]);
                ruleField.setCnName(str[1]);
                ruleField.setOperator(str[2]);
                ruleField.setFieldValue(str[3]);
                ruleField.setFieldId(str[4]);
                ruleFieldList.add(ruleField);
            }
        }
        return ruleFieldList;
    }

    private List<RuleContent> handExcelDataForRuleContent(String ruleContents) {
        ArrayList ruleContentList = new ArrayList();
        if(!StringUtil.isBlank(ruleContents)) {
            String[] content = ruleContents.trim().split("\n");

            for(int i = 0; i < content.length; ++i) {
                String[] str = content[i].split("=");
                RuleContent ruleContent = new RuleContent();
                ruleContent.setCnName(str[0]);
                ruleContent.setFieldValue(str[1]);
                ruleContentList.add(ruleContent);
            }
        }
        return ruleContentList;
    }

    @RequestMapping({"/getFieldList"})
    @WebLogger("规则集获取字段列表")
    @ResponseBody
    public Response queryFieldByUser(@RequestBody ParamFilter queryFilter) {
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        queryFilter.getParam().put("userId", userId);
        queryFilter.getParam().put("orgId", organId);
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<Field> fieldList = fieldService.queryFieldList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(fieldList);
        return new Response(pageInfo);
    }

    @RequestMapping({"/getEnginesByRuleId"})
    @ResponseBody
    public Response queryEnginesByRuleId(@RequestBody Map<String, Object> param) {
        param.put("idList", StringUtil.toLongList(param.get("ruleIds").toString()));
        List engineList = nodeKnowledgeService.queryEnginesByRuleId(param);
        param.put("engineList", engineList);
        return new Response(param);
    }

    private Map<String, Object> getParam(Map<String, Object> paramMap) {
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        paramMap.put("userId", userId);
        String engineId = "";
        paramMap.put("orgId", organId);
        if(paramMap.containsKey("engineId")) {
            engineId = paramMap.get("engineId").toString();
        }
        if(StringUtil.isBlank(engineId)) {
            paramMap.put("type", 1);
        } else {
            paramMap.put("type", 2);
        }
        return paramMap;
    }

    /**
     * 关联规则集节点----规则
     * @param param
     * nodeId 节点id
     * relateRule 关联规则 ruleId|priority  之间竖线分割
     * @return Response
     */
    @PostMapping({"/saveRuleNode"})
    @ResponseBody
    public Object addRuleNode(@RequestBody Map<String , Object> param){
        Long nodeId = Long.valueOf(param.get("nodeId").toString());//规则集节点id
        //删除之前关联规则
        ruleService.deleteRelateRule(nodeId);
        //保存关联规则
        List list = (List)param.get("relateRule");
        for(int i =0;i < list.size();i++){
            String[] ruleArr = list.get(i).toString().split("\\|");
            Map<String , Object> map = new HashMap<>();
            map.put("nodeId",nodeId);
            map.put("ruleId",Integer.valueOf(ruleArr[0]));
            map.put("priority",Integer.valueOf(ruleArr[1]));
            ruleService.saveRuleNode(map);
        }

        return new Response("保存成功！");
    }
    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
}
