package com.zw.rule.web.datamanage.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.ExcelHeader;
import com.zw.base.util.ExcelUtil;
import com.zw.rule.core.Response;
import com.zw.rule.datamanage.po.*;
import com.zw.rule.datamanage.service.FieldService;
import com.zw.rule.datamanage.service.FieldTypeService;
import com.zw.rule.jeval.tools.StringUtil;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping({"engineManage/fieldmapping"})
public class FieldMappingController  {
    @Resource
    private FieldService fieldService;
    @Resource
    private FieldTypeService fieldTypeService;

    public FieldMappingController() {
    }

      @RequestMapping({""})
      public String index(Model model) {
          return "engineMange/fieldmapping";
      }

    /**
     * 通过字段类型获取字段列表
     * engineId /String / 可选 / 引擎id /<br>
     * fieldTypeId /String / 可选 / 字段类型id /<br>
     * status /Integer /可选 /字段状态，启用:1，停用:0，删除:-1
     * isCommon /Integer /可选/是否组织定义的通用字段，0代表不是，1代表是，默认不是(0)<br>
     * listType /String /可选<br>
     * canAdd /String /可选<br>
     * @return
     */
    @PostMapping({"list"})
    @ResponseBody
    @WebLogger("获取字段映射信息列表")
    public Response queryListInfo(@RequestBody ParamFilter queryFilter) {
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        int pageSize=queryFilter.getPage().getPageSize();
        Map paramMap=queryFilter.getParam();
        paramMap.put("userId", userId);
        paramMap.put("orgId", organId);
        Integer fieldTypeId = null;
        if(!paramMap.containsKey("fieldTypeId")) {
            paramMap.put("fieldTypeId", (Object)null);
        } else if(paramMap.get("fieldTypeId") != null && !paramMap.get("fieldTypeId").equals("") && !paramMap.get("fieldTypeId").equals("null")) {
            fieldTypeId = Integer.valueOf(Integer.valueOf((String)paramMap.get("fieldTypeId")).intValue());
        } else if(paramMap.get("fieldTypeId") == null || paramMap.get("fieldTypeId").equals("") || paramMap.get("fieldTypeId").equals("null")) {
            paramMap.put("fieldTypeId", (Object)null);
        }
        if(!paramMap.containsKey("status") && paramMap.containsKey("isCommon") && paramMap.get("isCommon").equals("1")) {
            paramMap.put("status", Integer.valueOf(1));
        }

        PageHelper.startPage(pageNo, pageSize);
        List fieldList = fieldService.queryByFieldType(paramMap);
        Iterator listType = fieldList.iterator();
        String canAdd;
        while(listType.hasNext()) {
            Field pageInfo = (Field)listType.next();
            canAdd = pageInfo.getEnName();
            if(!canAdd.equals("") && canAdd != null) {
                canAdd = canAdd.replace("f_", "");
                pageInfo.setEnName(canAdd);
            }
        }

        String listType1 = "";
        if(paramMap.containsKey("listType")) {
            listType1 = (String)paramMap.get("listType");
        }
        PageInfo pageInfo1 = new PageInfo(fieldList);
        canAdd = "";
        if(paramMap.containsKey("canAdd")) {
            canAdd = paramMap.get("canAdd")+"";
        }
        paramMap.put("pager", pageInfo1);
        paramMap.put("fieldVos", pageInfo1.getList());
        paramMap.put("fieldTypeId", fieldTypeId);
        paramMap.put("listType", listType1);
        paramMap.put("canAdd", canAdd);
        return new Response(paramMap);
    }

    /**
     * 新增字段打开页面并回显数据
     * @param param
     * fieldTypeId /String / 可选 / 字段类型id /<br>
     * engineId /int /可选/引擎id <br>
     * @return
     */
    @PostMapping({"/preadd"})
    @ResponseBody
    @WebLogger("新增字段打开页面并回显数据")
    public Response add(@RequestBody Map<String, Object> param) {
        Long userId = UserContextUtil.getUserId();
        param.put("userId", userId);
        FieldType fieldType = new FieldType();
        Object fieldTypeList = new ArrayList();
        if(!param.get("fieldTypeId").equals("")) {
            param.put("id", Integer.valueOf(Integer.valueOf((String)param.get("fieldTypeId")).intValue()));
            fieldType = fieldTypeService.queryFieldTypeById(param);
        } else {
            fieldTypeList = fieldTypeService.queryFieldType(param);
        }
        param.put("fieldTypeList", fieldTypeList);
        param.put("fieldType", fieldType.getFieldType());
        param.put("fieldVo", new Field());
        return new Response(param);
    }

    /**
     * 保存字段映射
     * @param  /Field/必需/字段映射实体<br>
     * @param paramMap
     * fieldEn String /必需/字段英文<br>
     * fieldCn String /必需/字段中文<br>
     * @return
     */
    @RequestMapping({"/save"})
    @ResponseBody
    @WebLogger("保存字段映射中的字段")
    public Response save(@RequestBody Map<String, Object> paramMap) {
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        Field fieldVo = new Field();
        fieldVo.setEnName((String) paramMap.get("enName"));
        fieldVo.setCnName((String) paramMap.get("cnName"));
        fieldVo.setFieldTypeId(Long.valueOf(paramMap.get("fieldTypeId").toString()).longValue());
        fieldVo.setFormula(paramMap.get("formula").toString());
        fieldVo.setFormulaShow(paramMap.get("formulaShow").toString());
        fieldVo.setIsDerivative(Integer.valueOf(paramMap.get("isDerivative").toString()));
        fieldVo.setIsOutput(Integer.valueOf(paramMap.get("isOutput").toString()));
        fieldVo.setRestrainScope(paramMap.get("restrainScope").toString());
        fieldVo.setValueType(Integer.valueOf(paramMap.get("valueType").toString()));
        paramMap.put("userId", userId);
        paramMap.put("enName", fieldVo.getEnName());
        paramMap.put("cnName", fieldVo.getCnName());
        paramMap.put("orgId", organId);
        fieldVo.setCreator(userId.toString());
        fieldVo.setIsCommon(Integer.valueOf(0));
        Response response = new Response();
        String enName = paramMap.get("enName").toString();
        int numCn = fieldCnAjaxValidate(paramMap);
        if (numCn>0){
            response.setMsg("字段中文名已经存在");
            return response;
        }
        paramMap.put("enName",enName);
        int numEn = fieldEnAjaxValidate(paramMap);
        if (numEn>0){
            response.setMsg("字段名称已经存在");
            return response;
        }
        boolean flag=fieldService.addField(fieldVo, paramMap);
        if(flag){
            response.setMsg("创建成功！");
        }else{
            response.setMsg("创建失败！");
        }
        Long engineId = Long.valueOf((String)paramMap.get("engineId"));
        HashMap paramResp = new HashMap();
        paramResp.put("engineId", engineId);
        paramResp.put("fieldTypeId", Long.valueOf(fieldVo.getFieldTypeId().longValue()));
        paramResp.put("isCommon", Integer.valueOf(0));
        paramResp.put("canAdd", paramMap.get("canAdd"));
        response.setData(paramResp);
        return response;
    }

    /**
     * 下载字段列表(已测通)
     * @param request
     * @param response
     * @param paramMap
     * @return
     * @throws Exception
     */
    @RequestMapping({"/down"})
    @ResponseBody
    public String downloadExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> paramMap) throws Exception {
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        paramMap.put("userId", userId);
        paramMap.put("orgId", organId);
        List fieldExcelList = fieldService.findExcelByFieldType(paramMap);
        response.setContentType("application/x-msdownload;");
        response.setHeader("Content-disposition", "attachment; filename=" + new String("字段列表.xlsx".getBytes("utf-8"), "ISO8859-1"));
        ExcelUtil.exportFieldExcel(response.getOutputStream(), "xlsx", ExcelHeader.fieldExcelHeader, ExcelHeader.fieldExcelClass, fieldExcelList);
        return null;
    }

    /**
     * 下载字段导入模板
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping({"/downTemplate"})
    public ResponseEntity<byte[]> downExcelTemplate(HttpServletRequest request) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/fieldExcelTemplate.xlsx");
        File file = new File(path);
        HttpHeaders headers = new HttpHeaders();
        String fileName = new String("字段导入模板.xlsx".getBytes("UTF-8"), "iso-8859-1");
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    /**
     * 修改字段映射中的字段状态
     * @param param
     * ids String /必需/逗号隔开的id字符串<br>
     * @return
     */
    @PostMapping({"/updateStatus"})
    @WebLogger("修改字段映射中的字段状态")
    @ResponseBody
    public Response updateStatus(@RequestBody Map<String, Object> param) {
        Long userId =UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        param.put("userId", userId);
        param.put("orgId", organId);
        List Ids = (List) param.get("ids");
        param.put("Ids", Ids);
//        fieldService.findFieldTypeId(param);
        Map map=fieldService.updateStatus(param);
        boolean result= (boolean) map.get("result");
        Response response = new Response();
        if(result){
            response.setMsg("更新成功！");
        }else{
            response.setMsg("更新失败！");
        }
        param.put("fieldTypeId", param.get("fieldTypeId"));
        response.setData(param);
        return response;
    }

    /**
     * 修改字段映射信息
     * @param  /Long/必需/字段映射id <br>
     * @param param
     * engineId String /必需/引擎id <br>
     * isCommon String /可选<br>
     * searchKey String /必需<br>
     * fieldTypeId /String/必需/字段类型id <br>
     * @return
     */
    @PostMapping({"/edit/{id}"})
    @ResponseBody
    @WebLogger("修改字段映射中的字段信息")
    public Response edit(@RequestBody Map<String, Object> param) {
        HashMap paramMap = new HashMap();
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        paramMap.put("userId", userId);
        paramMap.put("orgId", organId);
        Long engineId =Long.valueOf((String)param.get("engineId"));
        if(param.get("isCommon").equals("1")) {
            paramMap.put("engineId", (Object)null);
        } else {
            paramMap.put("engineId", engineId);
        }
        long id=Long.parseLong(param.get("id").toString());
        paramMap.put("id", Long.valueOf(id));
        paramMap.put("searchKey", param.get("searchKey"));
        Field fieldVo = fieldService.findByFieldId(paramMap);
        String fieldEn = fieldVo.getEnName();
        if(!fieldEn.equals("") && fieldEn != null) {
            fieldEn = fieldEn.replace("f_", "");
            fieldVo.setEnName(fieldEn);
        }

        Iterator fieldFormulaList = fieldVo.getFieldCondList().iterator();
        List scopeList;
        while(fieldFormulaList.hasNext()) {
            FieldCond flist = (FieldCond)fieldFormulaList.next();
            scopeList = JSONObject.parseArray(flist.getConditionContent(), FieldSubCondVo.class);
            Iterator iterator = scopeList.iterator();

            while(iterator.hasNext()) {
                FieldSubCondVo fieldTypeId = (FieldSubCondVo)iterator.next();
                HashMap paramMap2 = new HashMap();
                paramMap2.put("userId", userId);
                paramMap2.put("orgId", organId);
                paramMap2.put("id", fieldTypeId.getFieldId());
                paramMap2.put("engineId", engineId);
                Field subField = fieldService.findByFieldId(paramMap2);
                fieldTypeId.setValueType(subField.getValueType());
                fieldTypeId.setValueScope(subField.getRestrainScope());
                fieldTypeId.setFieldCn(subField.getCnName());
            }

            flist.setFieldSubCond(scopeList);
        }

        paramMap.put("fieldId", Long.valueOf(id));
        List flist1 = fieldService.findByUser(paramMap);
        scopeList = Arrays.asList(fieldVo.getRestrainScope().split(","));
        Object fieldFormulaList1 = new ArrayList();
        if(fieldVo.getFormulaShow() != null && !fieldVo.getFormulaShow().equals("")) {
            fieldFormulaList1 = JSONObject.parseArray(fieldVo.getFormulaShow(), FieldFormulaVo.class);
        }

        String hasFormula1 = null;
        if(!fieldVo.getFormula().equals("") && fieldVo.getFormula() != null) {
            hasFormula1 = "y";
        }

        String fieldTypeId1 = "";
        if(param.containsKey("fieldTypeId") && !param.get("fieldTypeId").equals("")) {
            fieldTypeId1 = String.valueOf(param.get("fieldTypeId"));
        } else if(param.containsKey("fieldTypeId") && param.get("fieldTypeId").equals("")) {
            fieldTypeId1 = fieldVo.getFieldTypeId().toString();
        }
        Map<String,Object> paramRes = new HashedMap();
        paramRes.put("fieldVo", fieldVo);
        paramRes.put("hasFormula", hasFormula1);
        paramRes.put("fieldTypeId", fieldTypeId1);
        paramRes.put("engineId", param.get("engineId"));
        paramRes.put("searchKey", param.get("searchKey"));
        paramRes.put("flist", flist1);
        paramRes.put("isCommon", param.get("isCommon"));
        paramRes.put("fieldFormulaList", fieldFormulaList1);
        paramRes.put("scopeList", scopeList);
        paramRes.put("canAdd", param.get("canAdd"));
        Response response = new Response();
        response.setData(paramRes);
        return response;
    }
    @RequestMapping({"/update"})
    @WebLogger("修改字段映射中的字段")
    @ResponseBody
    public Response updateInfo(@RequestBody Map<String, Object> paramMap) {
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        paramMap.put("userId", userId);
        paramMap.put("orgId", organId);
        Integer isCommon = Integer.valueOf(0);
        paramMap.put("isCommon", isCommon);
        Response response = new Response();
        boolean b = fieldService.updateField(paramMap);
        if(b){
            response.setMsg("修改成功");
        }else{
            response.setMsg("修改失败");
        }
        paramMap.put("fieldTypeId", Long.valueOf((String)paramMap.get("fieldTypeId")));
        response.setData(paramMap);
        return response;
    }
    @RequestMapping({"/copyField"})
    @WebLogger("添加引擎引用字段关系")
    @ResponseBody
    public Response copyField(@RequestBody Map<String, Object> param) {
        Response response = new Response();
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        param.put("userId", userId);
        param.put("orgId", organId);
        param.put("rFieldTypeIds", param.get("fieldTypeIds"));
        String Ids = (String)param.get("fieldTypeIds");
        List fieldTypeIds = StringUtil.toLongList(Ids);
        param.put("fieldTypeIds", fieldTypeIds);
//        String fieldIds = fieldService.queryOrgFieldIdsByTypeIds(param);
        List<String> fieldIds = fieldService.selectFieldIdsByTypeIds(param);
//        if(fieldIds == "" && fieldIds.length() <= 0){
//            response.setCode(1);
//            response.setMsg("该节点没有字段，请重选！");
//            return response;
//        }
        if(fieldIds == null && fieldIds.size() <= 0){
            response.setCode(1);
            response.setMsg("该节点没有字段，请重选！");
            return response;
        }
        param.put("fieldIds", fieldIds);
        boolean b = fieldService.bindEngineField(param);
        Integer result = Integer.valueOf(-1);

        if(b) {
            result = Integer.valueOf(1);
            response.setMsg("添加成功");
        }else{
            response.setMsg("添加失败");
        }
        param.put("result", result);
        response.setData(param);
        return response;
    }
    @RequestMapping({"/getNodes"})
    @WebLogger("获取节点")
    @ResponseBody
    public Response queryNodes(@RequestBody Map<String, Object> param) {
        Long userId = UserContextUtil.getUserId();
        param.put("userId", userId);
        String nodes = this.fieldTypeService.queryNodeIds(param);
        param.put("selNodes", nodes);
        return new Response(param);
    }

    @PostMapping({"/checkFieldType"})
    @WebLogger("检查类型")
    @ResponseBody
    public Response checkFieldType(@RequestBody Map<String, Object> paramMap) {
        Long userId = UserContextUtil.getUserId();
        paramMap.put("userId", userId);
        return new Response(this.fieldService.checkFieldType(paramMap,String.valueOf(userId)));
    }
    @PostMapping({"/deleteNode"})
    @WebLogger("删除节点")
    @ResponseBody
    public Response deleteNode(@RequestBody Map<String, Object> paramMap) {
        Long userId = UserContextUtil.getUserId();
        paramMap.put("userId", userId);
        Response response = new Response();
        Map map = this.fieldService.deleteNode(paramMap , String.valueOf(userId));
        int  b = (Integer)(map.get("result"));
        if(b==1){
            response.setMsg("删除成功");
        }else{
            response.setMsg("删除失败");
        }
        response.setData(map);
        return response;
    }
    //判断字段名称是否已经存在
    public int  fieldEnAjaxValidate(Map<String, Object> paramMap) {
        Long userId = UserContextUtil.getUserId();
        paramMap.put("userId", userId);
        if(paramMap.get("engineId").equals("")) {
            paramMap.put("engineId", (Object)null);
        }
        if(paramMap.get("id").equals("")) {
            paramMap.put("Id", (Object)null);
        }
        paramMap.put("cnName", (Object)null);
        int num = fieldService.isExists(paramMap);
        return num;
    }
     //判断字段中文名称是否已经存在
    public int fieldCnAjaxValidate(Map<String, Object> paramMap) {
        Long userId = UserContextUtil.getUserId();
        paramMap.put("userId", userId);
        if(paramMap.get("engineId").equals("")) {
            paramMap.put("engineId", (Object)null);
        }
        if(paramMap.get("id").equals("")) {
            paramMap.put("Id", (Object)null);
        }
        paramMap.put("enName", (Object)null);
        int num = fieldService.isExists(paramMap);
        return num;
    }
}

