package com.zw.rule.web.datamanage.controller;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.ExcelHeader;
import com.zw.rule.knowleage.util.ExcelUtil;
import com.zw.base.util.ValueType;
import com.zw.rule.core.Response;
import com.zw.rule.datamanage.po.*;
import com.zw.rule.datamanage.service.FieldService;
import com.zw.rule.datamanage.service.FieldTypeService;
import com.zw.rule.jeval.tools.CollectionUtil;
import com.zw.rule.jeval.tools.SectionUtils;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.util.StringUtil;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Controller
@RequestMapping({"datamanage/field"})
public class FieldController   {
        @Resource
        private FieldService fieldService;
        @Resource
        private FieldTypeService fieldTypeService;

        public FieldController() {
        }

        @RequestMapping({""})
        public String index() {
            return "datamanage/datamange";
        }

    /**
     * 获取查询结果
     * @param
     * @param paramMap
     * firstIndex|int|页数|当前页的第一条记录在所有记录中的位置<br>
     * pageSize|int|页面显示总条数|必需<br>
     * engineId |Long |引擎id |可选 <br>
     * fieldTypeId| Long |字段类型 |可选<br>
     * searchKey|String |引擎id |可选 <br>
     * status|String |字段状态 |可选 <br>
     * @return Response
     *
     */
        @RequestMapping({"/list"})
        @ResponseBody
        public Response queryList(@RequestBody ParamFilter paramMap) {
            Long userId = UserContextUtil.getUserId();
            Long orgId = UserContextUtil.getOrganId();
            paramMap.getParam().put("userId", userId);
            paramMap.getParam().put("orgId", orgId);
            String searchKey = (String)paramMap.getParam().get("searchKey");
            paramMap.getParam().put("searchKey", (Object)null);
            //获取页码
            int pageNo = PageConvert.convert(paramMap.getPage().getFirstIndex(),paramMap.getPage().getPageSize());
            PageHelper.startPage(pageNo, paramMap.getPage().getPageSize());
            Integer fieldTypeId = null;
            if(!paramMap.getParam().containsKey("fieldTypeId")) {
                paramMap.getParam().put("fieldTypeId", (Object)null);
            } else if(paramMap.getParam().get("fieldTypeId") != null && !paramMap.getParam().get("fieldTypeId").equals("")) {
                fieldTypeId = Integer.valueOf(Integer.valueOf((String)paramMap.getParam().get("fieldTypeId")).intValue());
            } else if(paramMap.getParam().get("fieldTypeId") == null || paramMap.getParam().get("fieldTypeId").equals("")) {
                paramMap.getParam().put("fieldTypeId", (Object)null);
            }
            if(!paramMap.getParam().containsKey("status")) {
                paramMap.getParam().put("status", (Object)null);
            }
            if(!paramMap.getParam().containsKey("engineId")) {
                paramMap.getParam().put("engineId", (Object)null);
            }
            List<Field> fieldList = fieldService.queryByFieldType(paramMap.getParam());
            Iterator pageInfo = fieldList.iterator();
            while(pageInfo.hasNext()) {
                Field fieldVo = (Field)pageInfo.next();
                String enName = fieldVo.getEnName();
                if(!enName.equals("") && enName != null) {
                    enName = enName.replace("f_", "");
                    fieldVo.setEnName(enName);
                }
            }
            PageInfo pageInfo1 = new PageInfo(fieldList);
            Map<String,Object> data = new HashMap();
            data.put("pager", pageInfo1);
            data.put("fieldVos", pageInfo1.getList());
            data.put("engineId", (Object)null);
            data.put("fieldTypeId", fieldTypeId);
            data.put("searchKey", searchKey);
            data.put("msg","成功");
            return new Response(data);
        }
    /**
     *获取树菜单
     * @param paramMap
     *  userId | Long | 必需 | 用户id；<br>
     *  engineId|String| 可选 | 引擎id;<br>
     *  parentId|int| 可选 | 父节点编号;<br>
     *  status|int| 可选 | 启用删除标志;<br>
     *  isCommon|Integer|可选<br>
     *  fieldTypeId|Long|可选|字段类型id
     * @return  paramMap
     */
        @RequestMapping({"/listTree"})
        @ResponseBody
        public Response queryListTree(@RequestParam Map<String, Object> paramMap) {
            Long userId = UserContextUtil.getUserId();
            Long orgId = UserContextUtil.getOrganId();
            paramMap.put("userId", userId);
            paramMap.put("orgId", orgId);
            Integer isCommon = Integer.valueOf(1);
            Integer engineId = null;
            if(paramMap.containsKey("engineId") && !paramMap.get("engineId").equals("")) {
                isCommon = null;
                engineId = Integer.valueOf(Integer.valueOf((String)paramMap.get("engineId")).intValue());
                paramMap.put("engineId", engineId);
            } else {
                paramMap.put("engineId", (Object)null);
            }

            paramMap.put("isCommon", isCommon);
            List klist = fieldTypeService.queryFieldTypeList(paramMap);
            Iterator iterator = klist.iterator();

            while(iterator.hasNext()) {
                FieldType kArray = (FieldType)iterator.next();
                if(engineId != null && kArray.getIsCommon().intValue() == 1) {
                    String fieldType = kArray.getFieldType();
                    kArray.setFieldType(fieldType + "（通用）");
                }

                paramMap.put("parentId", kArray.getId());
                kArray.setChildren(getChildren(paramMap));
            }

            FieldType[] kArray1 = new FieldType[klist.size()];
            kArray1 = (FieldType[])klist.toArray(kArray1);
            paramMap.put("kArray", kArray1);
            paramMap.put("msg", "成功");
            return new Response(paramMap);
        }


    /**
     *获取树菜单
     * @param prmMap
     *  userId | Long | 必需 | 用户id；<br>
     *  engineId|String| 可选 | 引擎id;<br>
     *  parentId|int| 可选 | 父节点编号;<br>
     *  status|int| 可选 | 启用删除标志;<br>
     *  isCommon|Integer|可选<br>
     *  fieldTypeId|Long|可选|字段类型id
     * @return  paramMap
     */
    @RequestMapping({"/listTreeFolder"})
    @ResponseBody
    public Response queryListTreeFolder(@RequestBody ParamFilter prmMap) {
        Long userId = UserContextUtil.getUserId();
        Long orgId = UserContextUtil.getOrganId();
        Map<String, Object> paramMap=new HashMap<String ,Object>();
        paramMap.put("userId", userId);
        paramMap.put("orgId", orgId);
        Integer isCommon = Integer.valueOf(1);
        Integer engineId = null;
        if(prmMap.getParam().containsKey("engineId") && !prmMap.getParam().get("engineId").equals("")) {
            isCommon = null;
            engineId = Integer.valueOf((String)prmMap.getParam().get("engineId"));
            paramMap.put("engineId", engineId);
        } else {
            paramMap.put("engineId", (Object)null);
        }

        paramMap.put("isCommon", isCommon);

        //获取页码
        int pageNo = PageConvert.convert(prmMap.getPage().getFirstIndex(),prmMap.getPage().getPageSize());
        PageHelper.startPage(pageNo, prmMap.getPage().getPageSize());
        List klist = fieldTypeService.queryFieldTypeList(paramMap);
        PageInfo pageInfo1 = new PageInfo(klist);
        Iterator iterator = klist.iterator();

        while(iterator.hasNext()) {
            FieldType kArray = (FieldType)iterator.next();
            if(engineId != null && kArray.getIsCommon().intValue() == 1) {
                String fieldType = kArray.getFieldType();
                kArray.setFieldType(fieldType + "（通用）");
            }

            paramMap.put("parentId", kArray.getId());
            kArray.setChildren(getChildren(paramMap));
        }

        FieldType[] kArray1 = new FieldType[klist.size()];
        kArray1 = (FieldType[])klist.toArray(kArray1);
        List<FieldType> list = java.util.Arrays.asList(kArray1);
        //PageInfo pageInfo1 = new PageInfo(list);
        paramMap.put("pager", pageInfo1);
        paramMap.put("kArray", list);
        paramMap.put("msg", "成功");
        return new Response(paramMap);
    }
    /**
     * 获取子菜单
     * @param paramMap
     *  userId | Long | 必需 | 用户id；<br>
     *  engineId|String| 可选 | 引擎id;<br>
     *  parentId|int| 可选 | 父节点编号;<br>
     *  status|int| 可选 | 启用删除标志;<br>
     *  isCommon|Integer| 可选 | 启用删除标志;<br>
     *
     * @return kArray1
     */
        private FieldType[] getChildren(Map<String, Object> paramMap) {
            List klist = fieldTypeService.queryFieldTypeList(paramMap);
            Iterator iterator = klist.iterator();

            while(iterator.hasNext()) {
                FieldType kArray = (FieldType)iterator.next();
                paramMap.put("parentId", kArray.getId());
                kArray.setChildren(getChildren(paramMap));
            }

            FieldType[] kArray1 = new FieldType[klist.size()];
            kArray1 = (FieldType[])klist.toArray(kArray1);
            return kArray1;
        }

    /**
     * 新增树节点
     * @param queryFilter
     *  fieldType | String | 必需 | 字段类型；<br>
     *  parentId| int | 必需 | 父节点编号;<br>
     *  isCommon| Integer| 可选 | 是否组织定义的通用字段类型;<br>
     *  @param  queryFilter    包含以下参数：<br>
     *  fieldTypeId| Long| 必需 | 字段类型编号;<br>
     *  status|int| 必需 | 启用删除标志;<br>
     *  engineId | Long | 可选 | 引擎id；<br>
     * @return
     */

        @PostMapping({"/addTree"})
        @ResponseBody
        public Response addTree(@RequestBody ParamFilter queryFilter) {
            Map<String, Object> paramMap = queryFilter.getParam();
            int number = fieldTypeAjaxValidate(paramMap);
            if(number>0){
                paramMap.put("msg","字段类型已存在");
                return new Response(paramMap);
            }
            Long userId = UserContextUtil.getUserId();
            Long orgId = UserContextUtil.getOrganId();
            paramMap.put("userId", userId);
            paramMap.put("orgId", orgId);
            Integer isCommon = Integer.valueOf(1);
            Integer engineId = null;
            if(paramMap.containsKey("engineId") && !"".equals(paramMap.get("engineId")) && paramMap.get("engineId")!=null) {
                isCommon = Integer.valueOf(0);
                engineId = Integer.valueOf(Integer.valueOf((String)paramMap.get("engineId")).intValue());
                paramMap.put("engineId", engineId);
            } else {
                isCommon = Integer.valueOf(1);
                paramMap.put("engineId", (Object)null);
            }

            paramMap.put("isCommon", isCommon);
            FieldType fieldTypeVo = new FieldType();
            fieldTypeVo.setIsCommon(isCommon);
            fieldTypeVo.setParentId(Integer.valueOf(Integer.valueOf((String)paramMap.get("parentId")).intValue()));
            fieldTypeVo.setFieldType((String)paramMap.get("fieldType"));
            boolean flag = fieldTypeService.addFieldType(fieldTypeVo, paramMap);
            if(flag) {
                paramMap.put("result", Integer.valueOf(1));
                paramMap.put("msg","添加成功");
            } else {
                paramMap.put("result", Integer.valueOf(-1));
                paramMap.put("msg","添加失败");
            }

            return new Response(paramMap);
        }

    /**
     * 修改树菜单
     * @param queryFilter
     *  fieldType | String | 必需 | 字段类型；<br>
     *  userId | Long | 必需 | 用户id；<br>
     *  engineId| Long | 可选 | 引擎id;<br>
     *  id| int | 可选 | 字段类型编号;<br>
     *  isCommon| Integer| 可选 | 是否组织定义的通用字段类型;<br>
     *  fieldTypeId| Long| 必需 | 字段类型编号;<br>
     * @return
     */
        @RequestMapping({"/updateTree"})
        @ResponseBody
        public Response updateTree(@RequestBody ParamFilter queryFilter) {
            Map<String, Object> paramMap = queryFilter.getParam();
            int number = fieldTypeAjaxValidate(paramMap);
            if(number>0){
                paramMap.put("msg","字段类型已存在");
                return new Response(paramMap);
            }
            Long userId = UserContextUtil.getUserId();
            Long orgId = UserContextUtil.getOrganId();
            paramMap.put("userId", userId);
            paramMap.put("orgId", orgId);
            Integer isCommon = Integer.valueOf(1);
            Integer engineId = null;
            if(paramMap.containsKey("engineId") && !"".equals(paramMap.get("engineId")) && paramMap.get("engineId")!=null) {
                isCommon = Integer.valueOf(0);
                engineId = Integer.valueOf(Integer.valueOf((String)paramMap.get("engineId")).intValue());
                paramMap.put("engineId", engineId);
            } else {
                isCommon = Integer.valueOf(1);
                paramMap.put("engineId", (Object)null);
            }

            paramMap.put("isCommon", isCommon);
            boolean flag = fieldTypeService.updateFieldType(paramMap);
            if(flag) {
                paramMap.put("result", Integer.valueOf(1));
                paramMap.put("msg", "更新成功");
            } else {
                paramMap.put("result", Integer.valueOf(-1));
                paramMap.put("msg", "更新失败");
            }

            return new Response(paramMap);
        }


    /**
     * 修改树菜单
     * @param queryFilter
     *  fieldType | String | 必需 | 字段类型；<br>
     *  userId | Long | 必需 | 用户id；<br>
     *  engineId| Long | 可选 | 引擎id;<br>
     *  id| int | 可选 | 字段类型编号;<br>
     *  isCommon| Integer| 可选 | 是否组织定义的通用字段类型;<br>
     *  fieldTypeId| Long| 必需 | 字段类型编号;<br>
     * @return
     */
    @RequestMapping({"/deleteTree"})
    @ResponseBody
    public Response deleteTree(@RequestBody ParamFilter queryFilter) {
        Map<String, Object> paramMap = queryFilter.getParam();
        int number = fieldTypeAjaxValidate(paramMap);
        if(number>0){
            paramMap.put("msg","字段类型已存在");
            return new Response(paramMap);
        }
        Long userId = UserContextUtil.getUserId();
        Long orgId = UserContextUtil.getOrganId();
        paramMap.put("userId", userId);
        paramMap.put("orgId", orgId);
        Integer isCommon = Integer.valueOf(1);
        Integer engineId = null;
        if(paramMap.containsKey("engineId") && !"".equals(paramMap.get("engineId")) && paramMap.get("engineId")!=null) {
            isCommon = Integer.valueOf(0);
            engineId = Integer.valueOf(Integer.valueOf((String)paramMap.get("engineId")).intValue());
            paramMap.put("engineId", engineId);
        } else {
            isCommon = Integer.valueOf(1);
            paramMap.put("engineId", (Object)null);
        }

        paramMap.put("isCommon", isCommon);
        boolean flag = fieldTypeService.deleteFieldType(paramMap);
        if(flag) {
            paramMap.put("result", Integer.valueOf(1));
            paramMap.put("msg", "更新成功");
        } else {
            paramMap.put("result", Integer.valueOf(-1));
            paramMap.put("msg", "更新失败");
        }

        return new Response(paramMap);
    }
    /**
     * 点击新增按钮跳转页面并带数据至新增页面
     * @param queryFilter
     * id| Long | 必需 | 字段类型编号;<br>
     *  engineId| int | 可选 | 引擎id;<br>
     *  fieldTypeId| Long| 必需 | 字段类型编号;<br>
     * @return
     */
        @RequestMapping({"/preadd"})
        @ResponseBody
        public Response add(@RequestBody ParamFilter queryFilter) {
            Map<String, Object> param=queryFilter.getParam();
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

            Map<String,Object> data = new HashedMap();
            data.put("fieldVo", new Field());
            data.put("engineId", (Object)null);
            data.put("fieldTypeId", param.get("fieldTypeId"));
            data.put("fieldType", fieldType.getFieldType());
            data.put("fieldTypeList", fieldTypeList);
            data.put("msg","成功");
            return new Response(data);
        }

    /**
     * 保存新增的数据
     * @param paramFilter（）
     * fieldEn|String |必需 |字段英文名<br>
     * fieldCn|String |必需|字段中文名<br>
     * fieldTypeId | Long| 必需 | 字段类型编号;<br>
     * engineId| int | 可选 | 引擎id;<br>
     * Id| Long | 必需 | 字段类型编号;<br>
     * valueScope|必需|String|<br>
     * valueType|必需|String|字段存值类型,待选(0),数值型(1),字符型(2),枚举型(3),小数型(4)<br>
     * isDerivative|可选|String|是否衍生<br>
     * isOutput|可选|String|是否输出<br>
     * formulaHidden|可选|String|<br>
     * fieldContent |可选|String|<br>
     * @param paramFilter
     * @return
     */
        @RequestMapping({"/save"})
        @ResponseBody
        public Response saveField(@RequestBody ParamFilter paramFilter) {
            Map<String, Object> param=paramFilter.getParam();
            Field fieldVo = new Field();
            fieldVo.setCnName(param.get("cnName").toString());
            fieldVo.setEnName(param.get("enName").toString());
            fieldVo.setFieldTypeId(Long.valueOf(param.get("fieldTypeId").toString()).longValue());
            fieldVo.setFormula(param.get("formula").toString());
            fieldVo.setFormulaShow(param.get("formulaShow").toString());
            fieldVo.setIsDerivative(Integer.valueOf(param.get("isDerivative").toString()));
            fieldVo.setIsOutput(Integer.valueOf(param.get("isOutput").toString()));
            fieldVo.setRestrainScope(param.get("restrainScope").toString());
            fieldVo.setValueType(Integer.valueOf(param.get("valueType").toString()));
            param=paramFilter.getParam();
            Response response = new Response();
            String enName = param.get("enName").toString();
            int numCn = fieldCnAjaxValidate(param);
            if (numCn>0){
                response.setMsg("字段中文名已经存在");
                return response;
            }
            param.put("enName",enName);
            int numEn = fieldEnAjaxValidate(param);
            if (numEn>0){
                response.setMsg("字段名称已经存在");
                return response;
            }
            Long userId = UserContextUtil.getUserId();
            Long orgId = UserContextUtil.getOrganId();
            param.put("userId", userId);
            fieldVo.setCreator(String.valueOf(userId));
            String fieldEn = (String) param.get("enName");
            Pattern.matches("\\[A-z]|_|d+", fieldEn);
            param.put("orgId", orgId);
            param.put("engineId", (Object)null);
            Integer isCommon = Integer.valueOf(1);
            fieldVo.setIsCommon(isCommon);
            boolean flag = fieldService.addField(fieldVo, param);
            if (flag){
                response.setMsg("保存成功");
            }else{
                response.setMsg("保存失败");
            }
            return response;
        }
    /**
     *
     * @param request
     * @return
     * @throws IOException
     */
        @RequestMapping({"/down"})
        @ResponseBody
        public void downloadExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> paramMap) throws Exception {
            if(paramMap.get("fieldTypeId").equals("") || paramMap.get("fieldTypeId") == null) {
                paramMap.put("fieldTypeId", (Object)null);
            }
            Long userId = UserContextUtil.getUserId();
            Long orgId = UserContextUtil.getOrganId();
            paramMap.put("userId", userId);
            paramMap.put("orgId", orgId);
            paramMap.put("engineId", (Object)null);
           // List fieldExcelList = fieldService.findExcelByFieldType(paramMap);
            List<FieldExcelOut> fieldExcelList = fieldService.queryExcelByField(paramMap);
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename=" + new String("字段列表.xls".getBytes("utf-8"), "ISO8859-1"));
            ExcelUtil.exportExcel(response.getOutputStream(), ".lsx", ExcelHeader.fieldExcelHeader, ExcelHeader.fieldExcelClass, fieldExcelList);
        }

    /**
     *
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
     *
     * 更新字段状态
     * @param paramFilter
     * 包含以下参数
     * Ids String 必需<br>
     * status String 必需<br>
     * fieldTypeId | Long| 必需 | 字段类型编号;<br>
     * beUsed|String |可选<br>
     * listType|String |可选<br>
     *
     * @return
     */
        @RequestMapping({"/updateStatus"})
        @ResponseBody
        //@RequestParam HashMap<String, Object> paramMap
        public Response updateStatus(@RequestBody ParamFilter paramFilter) {
            Long userId = UserContextUtil.getUserId();
            Long orgId = UserContextUtil.getOrganId();
            Map param = paramFilter.getParam();
            param.put("userId", userId);
            param.put("orgId", orgId);
            param.put("engineId", (Object)null);
            List Ids = (List) param.get("ids");
            param.put("Ids", Ids);
            String strFieldTypeId = String.valueOf(param.get("fieldTypeId"));
            if(strFieldTypeId == null) {
                strFieldTypeId = "";
            }
            Map<String,Object> flag = fieldService.updateStatus(param);
            String status = String.valueOf(param.get("status"));
            if("-1".equals(status)){
                fieldService.deleteField((int)(Ids.get(0)));
            }
            param.put("fieldTypeId", strFieldTypeId);
            String msg = null;
            boolean flag1 = (boolean)flag.get("result");
            if (flag1){
                msg="保存成功";
            }else{
                msg = "保存失败";
            }
            param.put("msg",msg);
            return new Response(param);
        }

    /**
     *检查字段
     * @param paramFilter
     * id|Long|必需|字段类型id<br>
     * userId  String 用户id 必需<br>
     * engineId String 引擎id 非必需<br>
     * fieldId |Long|字段类型id 必需<br></>
     * listDbId|Long|必需<br></>
     * node_id|Long|节点id 必需<br></>
     * @return
     */
        @RequestMapping({"/checkField"})
        @ResponseBody
        public Response checkField(@RequestBody ParamFilter paramFilter) {
            Long userId = UserContextUtil.getUserId();
            Long orgId = UserContextUtil.getOrganId();
            Map<String, Object> param=paramFilter.getParam();
            param.put("userId", userId);
            param.put("orgId", orgId);
            param.put("engineId", (Object)null);
            Map<String,Object> data = fieldService.checkField(param);
            return  new Response(data);
        }

    /**
     * 修改字段
     * @param |Long|必需|字段类型id<br>
     * searchKey|String|可选<br></>
     * engineId String 引擎id 非必需<br>
     * fieldEn|String|必需|字段英文名<br>
     * fieldId|Long|必需|字段类型id<br></>
     * @param
     * @return
     */
        @RequestMapping({"/edit/{id}"})
        @ResponseBody
        //@RequestBody ParamFilter paramFilter
        public Response edit( @RequestBody ParamFilter paramFilter) {
            Map<String, Object> param=paramFilter.getParam();
            long id=Long.parseLong(param.get("id").toString());
            HashMap paramMap = new HashMap();
            Long userId = UserContextUtil.getUserId();
            Long orgId = UserContextUtil.getOrganId();
            paramMap.put("userId", userId);
            paramMap.put("orgId", orgId);
            paramMap.put("engineId", (Object)null);
            paramMap.put("id", Long.valueOf(id));
            paramMap.put("searchKey", param.get("searchKey"));
            Field fieldVo = fieldService.findByFieldId(paramMap);
            String enName = fieldVo.getEnName();
            if(!enName.equals("") && enName != null) {
                enName = enName.replace("f_", "");
                fieldVo.setEnName(enName);
            }

            Iterator valueScope = fieldVo.getFieldCondList().iterator();

            List scopeList;
            while(valueScope.hasNext()) {
                FieldCond flist = (FieldCond)valueScope.next();
                scopeList = JSONObject.parseArray(flist.getConditionContent(), FieldSubCondVo.class);
                Iterator hasFormula = scopeList.iterator();

                while(hasFormula.hasNext()) {
                    FieldSubCondVo fieldFormulaList = (FieldSubCondVo)hasFormula.next();
                    HashMap paramMap2 = new HashMap();
                    paramMap2.put("userId", userId);
                    paramMap2.put("orgId", orgId);
                    paramMap2.put("id", fieldFormulaList.getFieldId());
                    Field subField = fieldService.findByFieldId(paramMap2);
                    fieldFormulaList.setValueType(subField.getValueType());
                    fieldFormulaList.setValueScope(subField.getRestrainScope());
                    fieldFormulaList.setFieldCn(subField.getCnName());
                }

                flist.setFieldSubCond(scopeList);
            }

            paramMap.put("fieldId", Long.valueOf(id));
            List flist1 = fieldService.findByUser(paramMap);
            String valueScope1 = fieldVo.getRestrainScope();
            scopeList = Arrays.asList(valueScope1.split(","));
            Object fieldFormulaList1 = new ArrayList();
            if(fieldVo.getFormulaShow() != null && !fieldVo.getFormulaShow().equals("")) {
                fieldFormulaList1 = JSONObject.parseArray(fieldVo.getFormulaShow(), FieldFormulaVo.class);
            }

            String hasFormula1 = null;
            if( fieldVo.getFormula() != null&&!fieldVo.getFormula().equals("")) {
                hasFormula1 = "y";
            }

            Map<String,Object> data = new HashedMap();
            data.put("fieldVo", fieldVo);
            data.put("hasFormula", hasFormula1);
            data.put("engineId", (Object)null);
            data.put("fieldTypeId", Long.valueOf(fieldVo.getFieldTypeId().longValue()));
            data.put("searchKey", param.get("searchKey"));
            data.put("flist", flist1);
            data.put("scopeList", scopeList);
            data.put("fieldFormulaList", fieldFormulaList1);
            return new Response(data);
        }

    /**
     * 修改字段
     * @param paramFilter
     * formulaHidden|String|可选<br></>
     * fieldCn|String |必需|字段中文名<br></>
     * fieldContent|String |可选|<br></>
     * fieldId|Long |必需|字段编号id<br></>
     * engineId|Long|可选|引擎id<br>
     * fieldEn|String |必需|字段英文名<br></>
     * fieldTypeId|Long|必需|字段类型id<br>
     * valueScope|必需|String|字段约束范围<br>
     * valueType|必需|String|字段存值类型,待选(0),数值型(1),字符型(2),枚举型(3),小数型(4)<br>
     * isDerivative|可选|String|是否衍生<br>
     * isOutput|可选|String|是否输出<br>
     * formula|可选|String|公式<br>
     * origFieldId |可选|String|衍生字段用到的所有原生字段编号，逗号分割<br>
     * usedFieldId |可选|String|该衍生字段引用的字段id，逗号分割<br>
     * @return
     */
        @RequestMapping({"update"})
        @ResponseBody
        public Response updateInfo(@RequestBody ParamFilter paramFilter) {
            Response response = new Response();
            Long userId = UserContextUtil.getUserId();
            Long orgId = UserContextUtil.getOrganId();
            Map<String, Object> paramMap=paramFilter.getParam();
            paramMap.put("userId", userId);
            paramMap.put("orgId", orgId);
            paramMap.put("engineId", (Object)null);
            Integer isCommon = Integer.valueOf(1);
            paramMap.put("isCommon", isCommon);
            String enName = paramMap.get("enName").toString();
            String cnName = paramMap.get("cnName").toString();
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
            paramMap.put("cnName",cnName);
            boolean code = fieldService.updateField(paramMap);
            if (code){
                response.setMsg("修改成功");
            }else {
                response.setMsg("修改失败");
            }
            return response;
        }

    /**
     *通过用户信息查询字段
     * @param queryFilter
     *
     * engineId String 引擎id 可选<br>
     * fieldId|String 字段编号id 可选<br>
     * searchKey|String 查询关键字 可选<br>
     * @return
     */
        @RequestMapping({"/findFieldByUser"})
        @ResponseBody
        public Response queryFieldByUser(@RequestBody ParamFilter queryFilter) {
            Map<String, Object> paramMap=queryFilter.getParam();
            Long userId = UserContextUtil.getUserId();
            Long orgId = UserContextUtil.getOrganId();
            paramMap.put("userId", userId);
            paramMap.put("orgId", orgId);
            Integer isCommon = Integer.valueOf(1);
            Integer engineId = null;
            if(paramMap.containsKey("engineId") && !paramMap.get("engineId").equals("")) {
                isCommon = Integer.valueOf(0);
                engineId = Integer.valueOf(Integer.valueOf((String)paramMap.get("engineId")).intValue());
                paramMap.put("engineId", engineId);
            } else {
                isCommon = Integer.valueOf(1);
                paramMap.put("engineId", (Object)null);
            }

            paramMap.put("isCommon", isCommon);
            if(!paramMap.containsKey("fieldId")) {
                paramMap.put("fieldId", (Object)null);
            }

            List fieldList = fieldService.findByUser(paramMap);
            paramMap.put("fieldList", fieldList);
            return new Response(paramMap);
        }
    /**
     *
     * @param paramMap
     * engineId String 引擎id 非必需<br>
     * fieldId|String 字段编号id 可选<br>
     * searchKey|String 查询关键字 可选<br>
     * @return
     */
        @RequestMapping({"/findFieldByUserWithPage"})
        @ResponseBody
        public Response queryFieldByUserWithPage(@RequestParam HashMap<String, Object> paramMap, @RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "12") Integer pageSize) {

            Long userId = UserContextUtil.getUserId();
            Long orgId = UserContextUtil.getOrganId();
            paramMap.put("userId", userId);
            paramMap.put("orgId", orgId);
            Integer isCommon = Integer.valueOf(1);
            Integer engineId = null;
            if(paramMap.containsKey("engineId") && !paramMap.get("engineId").equals("")) {
                isCommon = Integer.valueOf(0);
                engineId = Integer.valueOf(Integer.valueOf((String)paramMap.get("engineId")).intValue());
                paramMap.put("engineId", engineId);
            } else {
                isCommon = Integer.valueOf(1);
                paramMap.put("engineId", (Object)null);
            }

            paramMap.put("isCommon", isCommon);
            PageHelper.startPage(pageNo.intValue(), pageSize.intValue());
            List fieldList = fieldService.findByUser(paramMap);
            PageInfo pageInfo = new PageInfo(fieldList);
            paramMap.put("pager", pageInfo);
            paramMap.put("fieldList", pageInfo.getList());
            return new Response(paramMap);
        }

    /**
     *
     * @param paramMap
     *  userId|Long|必需|用户id<br>
     *  engineId|int|可选|引擎id<br>
     *  id|int|必需|字段类型id<br>
     *  canAdd|int|可选<br>
     *
     * @return
     */
        @RequestMapping({"/findByFieldId"})
        @ResponseBody
        public Response queryByFieldId(@RequestParam HashMap<String, Object> paramMap) {
            Long userId = UserContextUtil.getUserId();
            Long orgId = UserContextUtil.getOrganId();
            paramMap.put("userId", userId);
            paramMap.put("orgId", orgId);
            Integer isCommon = Integer.valueOf(1);
            Integer engineId = null;
            if(paramMap.containsKey("engineId") && !paramMap.get("engineId").equals("")) {
                if(!paramMap.containsKey("canAdd") || !paramMap.get("canAdd").equals("1")) {
                    engineId = Integer.valueOf(Integer.valueOf((String)paramMap.get("engineId")).intValue());
                }

                isCommon = Integer.valueOf(0);
                paramMap.put("engineId", engineId);
            } else {
                isCommon = Integer.valueOf(1);
                paramMap.put("engineId", (Object)null);
            }

            paramMap.put("isCommon", isCommon);
            Field field = fieldService.findByFieldId(paramMap);
            paramMap.put("fieldFormula", field.getFormula());
            return new Response(paramMap);
        }

    /**
     *
     * @param paramMap
     *  isDerivative|String|可选|<br>
     *  engineId|int|可选|引擎id<br>
     *  Ids|List|必需|字段类型id<br>
     * @return
     */
        @RequestMapping({"/findFieldByIds"})
        @ResponseBody
        public Response queryFieldByIds(@RequestParam HashMap<String, Object> paramMap) {
            Long userId = UserContextUtil.getUserId();
            Long orgId = UserContextUtil.getOrganId();
            paramMap.put("userId", userId);
            paramMap.put("orgId", orgId);
            Integer isCommon = Integer.valueOf(1);
            Integer engineId = null;
            if(paramMap.containsKey("engineId") && !paramMap.get("engineId").equals("")) {
                isCommon = Integer.valueOf(0);
                engineId = Integer.valueOf(Integer.valueOf((String)paramMap.get("engineId")).intValue());
                paramMap.put("engineId", engineId);
            } else {
                isCommon = Integer.valueOf(1);
                paramMap.put("engineId", (Object)null);
            }

            paramMap.put("isCommon", isCommon);
            String idsStr = (String)paramMap.get("ids");
            List Ids = StringUtil.toLongList(idsStr);
            paramMap.put("Ids", Ids);
            List fieldList = fieldService.queryFieldByIds(paramMap);
            paramMap.put("fieldList", fieldList);
            return new Response(paramMap);
        }

    /**
     *
     *  @param paramMap  包含以下参数：<br>
     *  searchKey|String|可选|查询关键字 <br>
     *  engineId|Long|可选|引擎id<br>
     * @return
     */
        @RequestMapping({"/searchByName"})
        @ResponseBody
        public Response queryByName(@RequestParam HashMap<String, Object> paramMap) {
            Long userId = UserContextUtil.getUserId();
            Long orgId = UserContextUtil.getOrganId();
            paramMap.put("userId", userId);
            paramMap.put("orgId", orgId);
            Integer isCommon = Integer.valueOf(1);
            Integer engineId = null;
            if(paramMap.containsKey("engineId") && !paramMap.get("engineId").equals("")) {
                isCommon = Integer.valueOf(0);
                engineId = Integer.valueOf(Integer.valueOf((String)paramMap.get("engineId")).intValue());
                paramMap.put("engineId", engineId);
            } else {
                isCommon = Integer.valueOf(1);
                paramMap.put("engineId", (Object)null);
            }

            paramMap.put("isCommon", isCommon);
            List fieldList = fieldService.queryByName(paramMap);
            paramMap.put("fieldList", fieldList);
            return new Response(paramMap);
        }

    /**
     *
     * @param paramMap  包含以下参数：<br>
     *  fieldEn|String|可选|字段英文名
     *  fieldCn|String|可选|字段中文名
     *  engineId|int|可选|引擎id
     *  Id |int|  可选|字段类型编号
     */
        public int  fieldEnAjaxValidate(Map<String, Object> paramMap) {
            Long userId = UserContextUtil.getUserId();
            paramMap.put("userId", userId);
            if("".equals(paramMap.get("engineId"))) {
                paramMap.put("engineId", (Object)null);
            }

            if("".equals(paramMap.get("Id"))) {
                paramMap.put("Id", (Object)null);
            }

            paramMap.put("cnName", (Object)null);
            int num = fieldService.isExists(paramMap);
            return num;
        }

    /**
     *
     * @param paramMap  包含以下参数：<br>
     *  fieldEn|String|可选|字段英文名
     *  fieldCn|String|可选|字段中文名
     *  engineId|Long|可选|引擎id
     *  Id |Long|  可选|字段类型编号
     */
        public int fieldCnAjaxValidate(Map<String, Object> paramMap) {
            Long userId = UserContextUtil.getUserId();
            paramMap.put("userId", userId);
            if("".equals(paramMap.get("engineId"))) {
                paramMap.put("engineId", (Object)null);
            }

            if("".equals(paramMap.get("Id"))) {
                paramMap.put("Id", (Object)null);
            }
            paramMap.put("enName", (Object)null);
            int num = fieldService.isExists(paramMap);
            return num;
        }

    /**
     * 字段类型唯一性检测;<br>
     *  @param  paramMap    包含以下参数：<br>
     *  parentId | int | 必需 | 父节点编号；<br>
     *  id  | int | 可选 | 字段类型编号;<br>
     *  engineId| int | 可选 | 引擎id;<br>
     * @return num
     */
    public int fieldTypeAjaxValidate(Map<String, Object> paramMap) {
        Long userId = UserContextUtil.getUserId();
        paramMap.put("userId", userId);
        if(!paramMap.containsKey("engineId")) {
            paramMap.put("engineId", (Object)null);
        }
        int num = fieldService.isExistsFieldType(paramMap);
        return num;
    }
    /**
     * 字段类型默认名称唯一性检测;<br>
     *  @param  paramMap    包含以下参数：<br>
     *  parentId | int | 必需 | 父节点编号；<br>
     *  engineId| int | 可选 | 引擎id;<br>
     * @return Response
     */
    @RequestMapping({"/defaultTreeNameAjaxValidate"})
    @ResponseBody
    public Response defaultTreeNameAjaxValidate(@RequestParam HashMap<String, Object> paramMap) {
        Long userId = UserContextUtil.getUserId();
        paramMap.put("userId", userId);
        if(!paramMap.containsKey("engineId")) {
            paramMap.put("engineId", (Object)null);
        }
        int num  = fieldService.isExistsDefaultTreeName(paramMap);
        return new Response(num);
    }

    /**
     *  用途（ ）;<br>
     * @return Response
     */
        @RequestMapping({"upload/{resourceType}"})
        public String upload(HttpServletRequest request) {
            String accessUrl = request.getSession().getServletContext().getRealPath("/WEB-INF/field.xlsx");
            Integer isCommon = Integer.valueOf(0);
            HashMap paramMap = new HashMap();
            Long userId = UserContextUtil.getUserId();
            Long orgId = UserContextUtil.getOrganId();
            paramMap.put("userId", userId);
            paramMap.put("creator", userId);
            paramMap.put("orgId", orgId);
            Object engineId = null;
            paramMap.put("engineId", engineId);
            if(((Integer)engineId).equals((Object)null)) {
                isCommon = Integer.valueOf(1);
            } else {
                isCommon = Integer.valueOf(0);
            }

            paramMap.put("isCommon", isCommon);
            fieldService.importExcel(accessUrl, paramMap);
            return "redirect:list";
        }
    /**
     * 创建FieldType对象存入数据库，同时创建FieldTypeUserRel;<br>
     *  @param  paramMap    包含以下参数：<br>
     *  fieldType | String | 必需 | 用户id；<br>
     *  parentId| int | 必需 | 父节点编号;<br>
     *  isCommon| int| 必需 | 是否组织定义的通用字段类型;<br>
     *  fieldTypeId| int| 必需 | 字段类型编号;<br>
     *  status|int| 必需 | 启用删除标志;<br>
     *  engineId| int | 必需 | 引擎id;<br>
     * @return Response
     */
        @RequestMapping({"/createFieldType"})
        @ResponseBody
        public Response addFieldType(@RequestParam Map<String, Object> paramMap) {
            Long userId = UserContextUtil.getUserId();
            Long orgId = UserContextUtil.getOrganId();
            paramMap.put("userId", userId);
            paramMap.put("orgId", orgId);
            paramMap.put("engineId", (Object)null);
            FieldType fieldTypeVo = new FieldType();
            fieldTypeVo.setFieldType((String)paramMap.get("fieldType"));
            fieldTypeVo.setIsCommon(Integer.valueOf((String)paramMap.get("isCommon")));
            fieldTypeVo.setParentId(Integer.valueOf((String)paramMap.get("parentId")));
            boolean flag = fieldTypeService.addFieldType(fieldTypeVo, paramMap);
            return new Response(flag);
        }

    /**
     * 查询引擎字段信息列表;<br>
     *  @param  paramMap    包含以下参数：<br>
     *  engineId | int | 可选 | 引擎id;<br>
     * searchKey | String | 必需 |    ;<br>
     * isOutput | String | 必需 |  是否输出 ;<br>
     * valueType | String | 必需 | 值类型;<br>
     * @return Response
     */
    @RequestMapping({"/getEngineFields"})
    @ResponseBody
    public Response queryEngineFields(@RequestParam Map<String, Object> paramMap) {
        Long userId = UserContextUtil.getUserId();
        Long orgId = UserContextUtil.getOrganId();
        paramMap.put("userId", userId);
        paramMap.put("orgId", orgId);
        List fields = fieldService.queryFieldList(paramMap);
        if(CollectionUtil.isNotNullOrEmpty(fields)) {
            ArrayList fieldEnumVos = new ArrayList();
            FieldEnumVo fieldEnumVo = null;

            for(Iterator iterator = fields.iterator(); iterator.hasNext(); fieldEnumVos.add(fieldEnumVo)) {
                Field field = (Field)iterator.next();
                fieldEnumVo = new FieldEnumVo();
                fieldEnumVo.setField(field);
                if(field.getValueType().intValue() == ValueType.Enum.getValue()) {
                    String valueScope = field.getRestrainScope();
                    if(StringUtil.isValidStr(valueScope)) {
                        fieldEnumVo.setEnums(Arrays.asList(valueScope.split(",")));
                    }
                }
            }

            return new Response(fieldEnumVos);
        } else {
            return new Response();
        }
    }
    /**
     *  用途;<br>
     *  @param  paramMap    包含以下参数：<br>
     * fieldEn 必需
     * userId String 用户id 必需
     * engineId String 引擎id 必需
     * @return Response
     */
    @RequestMapping({"/findFieldCond"})
    @ResponseBody
    public Response queryFieldCond(@RequestParam Map<String, Object> paramMap) {
        paramMap.put("userId",UserContextUtil.getUserId());
        Map<String, Object> data = fieldService.queryFieldResult(paramMap);
        return new Response(data);
    }

    @PostMapping({"/section"})
    @ResponseBody
    public Object saveEngineNode(@RequestParam("sections[]") List<String> sections) {
        Response response = new Response();
        if(SectionUtils.checkSectionValid(sections)) {
            if(SectionUtils.checkSectionCoincide(sections)) {
                response.setData(1);
                response.setMsg("区间有重叠,请核准!");
            } else {
                response.setData(0);
                response.setMsg("区间有效!");
            }
        } else {
            response.setData(1);
            response.setMsg("区间不完整,请核准!");
        }

        return response;
    }

    /**
     * 导入excel字段
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(
            value = {"/uploadField"},
            method = {RequestMethod.POST, RequestMethod.GET}
    )
    @ResponseBody
    public Response upload(@RequestParam("filename") CommonsMultipartFile file,@RequestParam("nodeId") String nodeId) throws Exception {
        Long userId = UserContextUtil.getUserId();
        Long orgId = UserContextUtil.getOrganId();
        Response response = new Response();
        try {
            Long fieldTypeId =Long.valueOf(nodeId);//字段类型id，要导入到的文件夹id
            POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet hssfSheet = wb.getSheetAt(0);
                if(hssfSheet != null) {

                    ArrayList list = new ArrayList();
                    for(int param = 1; param <= hssfSheet.getLastRowNum(); ++param) {
                        HSSFRow hssfRow = hssfSheet.getRow(param);
                        if(hssfRow != null && hssfRow.getCell(0) != null) {
                            Field field = new Field();
                            field.setId(Long.valueOf(subZeroAndDot(ExcelUtil.formatCell(hssfRow.getCell(0)))));//id
                            field.setEnName(ExcelUtil.formatCell(hssfRow.getCell(1)));
                            field.setCnName(ExcelUtil.formatCell(hssfRow.getCell(2)));
                            field.setFieldTypeId(fieldTypeId);//字段类型编号
                            field.setFieldType(ExcelUtil.formatCell(hssfRow.getCell(4)));//字段类型
                            field.setValueType(Integer.valueOf(subZeroAndDot(ExcelUtil.formatCell(hssfRow.getCell(5)))));
                            field.setRestrainScope(ExcelUtil.formatCell(hssfRow.getCell(6)));
                            field.setIsDerivative(Integer.valueOf(subZeroAndDot(ExcelUtil.formatCell(hssfRow.getCell(7)))));
                            field.setIsCommon(Integer.valueOf(subZeroAndDot(ExcelUtil.formatCell(hssfRow.getCell(8)))));
                            field.setFormula(ExcelUtil.formatCell(hssfRow.getCell(9)));
                            field.setIsOutput(Integer.valueOf(subZeroAndDot(ExcelUtil.formatCell(hssfRow.getCell(10)))));
                            field.setFormulaShow(ExcelUtil.formatCell(hssfRow.getCell(11)));
                            field.setDeriveFieldId(subZeroAndDot(ExcelUtil.formatCell(hssfRow.getCell(12))));
                            field.setProtogeneFieldId(ExcelUtil.formatCell(hssfRow.getCell(13)));
                            field.setCreator(String.valueOf(userId));//创建者
                            list.add(field);
                        }
                    }
                    Map paramMap = new HashMap();
                    paramMap.put("userId", userId);
                    paramMap.put("orgId", orgId);
                    paramMap.put("engineId", (Object)null);
                    paramMap.put("status",Integer.valueOf(1));
                    paramMap.put("creator",userId);
                    fieldService.uploadField(list,paramMap);
                    response.setMsg("导入成功");
                }
            } catch (Exception e) {
                response.setCode(1);
                response.setMsg("导入失败,请先导出模板！");
            }

        return response;
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

