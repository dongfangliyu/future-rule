package com.zw.rule.web.datamanage.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.ExcelUtil;
import com.zw.rule.core.Response;
import com.zw.rule.datamanage.po.CustList;
import com.zw.rule.datamanage.po.Field;
import com.zw.rule.datamanage.po.ListDb;
import com.zw.rule.datamanage.po.TblColumn;
import com.zw.rule.datamanage.service.FieldService;
import com.zw.rule.datamanage.service.ListDbService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.DownloadUtils;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping({"datamanage/userlist"})
public class CustomerListController {

    @Resource
    private ListDbService listDbService;
    @Resource
    private FieldService fieldService;
    @RequestMapping({""})
    public String index(Model model) {
        return "datamanage/listmanage/userlist";
    }

    /**
     * listType 黑白名单类型 w:白名单 b:黑名单
     * listDbId 黑白名单Id
     * @param queryFilter
     * @return
     */
    @PostMapping("/list")
    @WebLogger("获取用户信息详细列表")
    @ResponseBody
    public Response queryUserList(@RequestBody ParamFilter queryFilter) {
        Map paramMap =queryFilter.getParam();
        Integer pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        Integer pageSize=queryFilter.getPage().getPageSize();
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        paramMap.put("userId", userId);
        paramMap.put("orgId", organId);
        String listType = (String)paramMap.get("listType");
        Integer listDbId = Integer.valueOf(Integer.valueOf((String)paramMap.get("listDbId")).intValue());
        String tableName = "organ_" + organId + "_" + listType + "_" + listDbId;
        paramMap.put("tableName", tableName);
        String colTop4Array = "";
        paramMap.put("schemaName", this.getDbName());
        List custLists = this.listDbService.getColumnList(paramMap);
        Iterator iterator = custLists.iterator();
            label69:
            while(true) {
                TblColumn pageInfo;
                do {
                    if(!iterator.hasNext()) {
                        colTop4Array = colTop4Array.substring(0, colTop4Array.length() - 1);
                        paramMap.put("colTop4Array", colTop4Array);
                        break label69;
                    }
                    pageInfo = (TblColumn)iterator.next();
                } while(!pageInfo.getColName().equals("id") && !pageInfo.getColName().equals("t0") && !pageInfo.getColName().equals("t1") && !pageInfo.getColName().equals("t2") && !pageInfo.getColName().equals("t3") && !pageInfo.getColName().equals("nick_name") && !pageInfo.getColName().equals("created"));
                if(pageInfo.getColName().equals("nick_name")) {
                    colTop4Array = colTop4Array + pageInfo.getColName() + " as nickName,";
                } else {
                    colTop4Array = colTop4Array + pageInfo.getColName() + ",";
                }
            }
        PageHelper.startPage(pageNo.intValue(), pageSize.intValue());
        custLists = this.listDbService.findTop4Column(paramMap);
        iterator = custLists.iterator();
        while(iterator.hasNext()) {
            CustList pageInfo1 = (CustList)iterator.next();
            if(colTop4Array.contains("t0") && pageInfo1.getT0() == null) {
                pageInfo1.setT0("");
            }

            if(colTop4Array.contains("t1") && pageInfo1.getT1() == null) {
                pageInfo1.setT1("");
            }

            if(colTop4Array.contains("t2") && pageInfo1.getT2() == null) {
                pageInfo1.setT2("");
            }

            if(colTop4Array.contains("t3") && pageInfo1.getT3() == null) {
                pageInfo1.setT3("");
            }
        }

        PageInfo pageInfo2 = new PageInfo(custLists);
        return new Response(pageInfo2);
    }

    /**
     * 获取数据库名
     * @return
     */
    @Value("${jdbc.url}")
    private String mysqlUrl;
    private String getDbName() {
        String[] aArray = mysqlUrl.split("/");
        String[] bArray = aArray[3].split("\\?");
        String dbName = bArray[0];
        return dbName;
    }

    /**
     * listType 黑白名单类型 w:白名单 b:黑名单
     * listDbId 黑白名单Id
     * @param paramMap
     * @return
     */
    @PostMapping("/getcolumnlist")
    @WebLogger("获取一列用户信息的集合")
    @ResponseBody
    public Response queryColumnlist(@RequestBody HashMap<String, Object> paramMap) {
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        paramMap.put("userId", userId);
        paramMap.put("orgId", organId);
        String listType = (String)paramMap.get("listType");
        Integer listDbId = Integer.valueOf(Integer.valueOf((String)paramMap.get("listDbId")).intValue());
        String tableName = "organ_" + organId + "_" + listType + "_" + listDbId;
        paramMap.put("tableName", tableName);
        paramMap.put("schemaName", this.getDbName());
        List columnList = this.listDbService.getColumnList(paramMap);
        Iterator iterator = columnList.iterator();
        while(iterator.hasNext()) {
            TblColumn tc = (TblColumn)iterator.next();
            if(tc.getColName().startsWith("t")) {
                paramMap.put("id", tc.getColComment());
                Field f = this.fieldService.findByFieldId(paramMap);
                tc.setCnName(f.getCnName());
            }
        }
        paramMap.put("columnList", columnList);
        return new Response(paramMap);
    }

    /**
     * listType 黑白名单类型 w:白名单 b:黑名单
     * listDbId 黑白名单Id
     * t0,t1.t2... 主键key和对应value
     * @param paramMap
     * @return
     */
    @PostMapping("/save")
    @WebLogger("保存黑白名单用户信息")
    @ResponseBody
    public Response saveListInfo(@RequestBody Map<String, Object> paramMap) {
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        String nickName = UserContextUtil.getNickName();
        paramMap.put("userId", userId);
        paramMap.put("orgId", organId);
        paramMap.put("nickName", nickName);
        String listType = (String)paramMap.get("listType");
        Integer listDbId = Integer.valueOf(Integer.valueOf((String)paramMap.get("listDbId")).intValue());
        String tableName = "organ_" + organId + "_" + listType + "_" + listDbId;
        paramMap.put("tableName", tableName);
        paramMap.put("id", listDbId);
        ListDb listDb = this.listDbService.findById(paramMap);
        String queryField = listDb.getQueryField();
        Integer queryType = listDb.getQueryType();
        paramMap.put("schemaName", this.getDbName());
        List tblColumnList = this.listDbService.getColumnList(paramMap);
        HashMap colParam = new HashMap();
        Iterator checkCol = tblColumnList.iterator();

        String fieldEn;
        while(checkCol.hasNext()) {
            TblColumn result = (TblColumn)checkCol.next();
            String i = result.getColName();
            fieldEn = result.getColComment();
            if(i.startsWith("t")) {
                colParam.put(fieldEn, i);
            }
        }

        String checkcol = "";
        if(!queryField.equals("") && queryField != null) {
            String[] str = queryField.split(",");

            for(int i = 0; i < str.length; i++) {
                fieldEn = (String)colParam.get(str[i]);
                if(checkcol.equals("")) {
                    checkcol = fieldEn + "=" + "\'" + paramMap.get(fieldEn) + "\'";
                } else if(queryType.intValue() == 0) {
                    checkcol = checkcol + " or " + fieldEn + "=" + "\'" + paramMap.get(fieldEn) + "\'";
                } else if(queryType.intValue() == 1) {
                    checkcol = checkcol + " and " + fieldEn + "=" + "\'" + paramMap.get(fieldEn) + "\'";
                }
            }
        }

        paramMap.put("checkCol", checkcol);
        int count = this.listDbService.addCustList(paramMap);
        Response response = new Response();
        if(count == 1){
            response.setMsg("保存成功");
        }else if(count == 0){
            response.setMsg("保存失败");
        }else if(count == 2){
            response.setMsg("查询主键上有数据重复");
        }
        HashMap map = new HashMap();
        map.put("count",count);
        map.put("listType", paramMap.get("listType"));
        map.put("listDbId", paramMap.get("listDbId"));
        response.setData(map);
        return response;
    }

    /**
     * listType 黑白名单类型 w:白名单 b:黑名单
     * listDbId 黑白名单Id
     * colAarray 里面包括colname :id t0,t1,t2...,user_id,nick_name,create
     * @param paramMap
     * @return
     */
    @PostMapping("/edit")
    @WebLogger("编辑黑白名单用户信息")
    @ResponseBody
    public Response editListInfo(@RequestBody HashMap<String, Object> paramMap) {
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        paramMap.put("userId", userId);
        paramMap.put("orgId", organId);
        String listType = (String)paramMap.get("listType");
        Integer listDbId = Integer.valueOf(Integer.valueOf((String)paramMap.get("listDbId")).intValue());
        String tableName = "organ_" + organId + "_" + listType + "_" + listDbId;
        paramMap.put("tableName", tableName);
        paramMap.put("id",Integer.valueOf(Integer.valueOf((String)paramMap.get("id")).intValue()));
        paramMap.put("fieldName", paramMap.get("colArray"));
        CustList cl = this.listDbService.queryCustListById(paramMap);
        paramMap.put("schemaName", this.getDbName());
        List dataList = this.listDbService.getColumnList(paramMap);
        Iterator iterator = dataList.iterator();

        while(iterator.hasNext()) {
            TblColumn tc = (TblColumn)iterator.next();
            if(tc.getColName().startsWith("t")) {
                paramMap.put("id", tc.getColComment());
                Field f = this.fieldService.findByFieldId(paramMap);
                tc.setCnName(f.getCnName());
                if(tc.getColName().equals("t0")) {
                    tc.setFieldValue(cl.getT0());
                }

                if(tc.getColName().equals("t1")) {
                    tc.setFieldValue(cl.getT1());
                }

                if(tc.getColName().equals("t2")) {
                    tc.setFieldValue(cl.getT2());
                }

                if(tc.getColName().equals("t3")) {
                    tc.setFieldValue(cl.getT3());
                }

                if(tc.getColName().equals("t4")) {
                    tc.setFieldValue(cl.getT4());
                }

                if(tc.getColName().equals("t5")) {
                    tc.setFieldValue(cl.getT5());
                }

                if(tc.getColName().equals("t6")) {
                    tc.setFieldValue(cl.getT6());
                }

                if(tc.getColName().equals("t7")) {
                    tc.setFieldValue(cl.getT7());
                }

                if(tc.getColName().equals("t8")) {
                    tc.setFieldValue(cl.getT8());
                }

                if(tc.getColName().equals("t9")) {
                    tc.setFieldValue(cl.getT9());
                }

                if(tc.getColName().equals("t10")) {
                    tc.setFieldValue(cl.getT10());
                }

                if(tc.getColName().equals("t11")) {
                    tc.setFieldValue(cl.getT11());
                }

                if(tc.getColName().equals("t12")) {
                    tc.setFieldValue(cl.getT12());
                }

                if(tc.getColName().equals("t13")) {
                    tc.setFieldValue(cl.getT13());
                }

                if(tc.getColName().equals("t14")) {
                    tc.setFieldValue(cl.getT14());
                }

                if(tc.getColName().equals("t15")) {
                    tc.setFieldValue(cl.getT15());
                }

                if(tc.getColName().equals("t16")) {
                    tc.setFieldValue(cl.getT16());
                }

                if(tc.getColName().equals("t17")) {
                    tc.setFieldValue(cl.getT17());
                }

                if(tc.getColName().equals("t18")) {
                    tc.setFieldValue(cl.getT18());
                }

                if(tc.getColName().equals("t19")) {
                    tc.setFieldValue(cl.getT19());
                }
            }
        }

        paramMap.put("dataList", dataList);
        return new Response(paramMap);
    }

    /**
     * listType 黑白名单类型 w:白名单 b:黑名单
     * listDbId 黑白名单Id
     * id 这一行的id
     * t0,t1,t2...对应的key和value值
     * @param paramMap
     * @return
     */
    @PostMapping("/update")
    @WebLogger("修改黑白名单用户信息")
    @ResponseBody
    public Response updateListInfo(@RequestBody Map<String, Object> paramMap) {
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        paramMap.put("userId", userId);
        String listType = (String)paramMap.get("listType");
        Integer listDbId = Integer.valueOf(Integer.valueOf((String)paramMap.get("listDbId")).intValue());
        String tableName = "organ_" + organId + "_" + listType + "_" + listDbId;
        paramMap.put("tableName", tableName);
        Integer custId = Integer.valueOf(Integer.valueOf((String)paramMap.get("id")).intValue());
        paramMap.put("id", listDbId);
        ListDb listDb = this.listDbService.findById(paramMap);
        String queryField = listDb.getQueryField();
        Integer queryType = listDb.getQueryType();
        paramMap.put("schemaName", this.getDbName());
        List tblColumnList = this.listDbService.getColumnList(paramMap);
        HashMap colParam = new HashMap();
        Iterator checkCol = tblColumnList.iterator();

        String fieldEn;
        while(checkCol.hasNext()) {
            TblColumn result = (TblColumn)checkCol.next();
            String i = result.getColName();
            fieldEn = result.getColComment();
            if(i.startsWith("t")) {
                colParam.put(fieldEn, i);
            }
        }

        String str = "";
        if(!queryField.equals("") && queryField != null) {
            String[] strings = queryField.split(",");

            for(int i = 0; i < strings.length; i++) {
                fieldEn = (String)colParam.get(strings[i]);
                if(str.equals("")) {
                    str = fieldEn + "=" + "\'" + paramMap.get(fieldEn) + "\'";
                } else if(queryType.intValue() == 0) {
                    str = str + " or " + fieldEn + "=" + "\'" + paramMap.get(fieldEn) + "\'";
                } else if(queryType.intValue() == 1) {
                    str = str + " and " + fieldEn + "=" + "\'" + paramMap.get(fieldEn) + "\'";
                }
            }
        }

        str = str + " and id != " + custId;
        paramMap.put("checkCol", str);
        paramMap.put("id", custId);
        int count = this.listDbService.updateCustList(paramMap);
        Response response = new Response();
        if(count == 1){
            response.setMsg("保存成功");
        }else if(count == 0){
            response.setMsg("保存失败");
        }else{
            response.setMsg("查询主键上有数据重复");
        }
        HashMap map = new HashMap();
        map.put("count",count);
        map.put("listType", paramMap.get("listType"));
        map.put("listDbId", paramMap.get("listDbId"));
        response.setData(map);
        return response;
    }

    /**
     *listType 黑白名单类型 w:白名单 b:黑名单
     * listDbId 黑白名单Id
     * id 这一行的id
     * @param param
     * @return
     */
    @PostMapping("/delete")
    @WebLogger("删除黑白名单")
    @ResponseBody
    public Response delete(@RequestBody Map<String, Object> param) {
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        param.put("userId", userId);
        param.put("orgId", organId);
        String listType = (String)param.get("listType");
        Integer listDbId = Integer.valueOf(Integer.valueOf((String)param.get("listDbId")).intValue());
        String tableName = "organ_" + organId + "_" + listType + "_" + listDbId;
        param.put("tableName", tableName);
        listDbService.deleteCustList(param);
        HashMap map = new HashMap();
        map.put("listType", listType);
        map.put("searchKey", param.get("searchKey"));
        map.put("listDbId", param.get("listDbId"));
        return new Response(map);
    }
    /**
     * 查询是否命中黑白名单;<br>
     * @param  paramMap
     * @return String
     */
    @GetMapping("findByQueryKey")
    @WebLogger("findByQueryKey")
    @ResponseBody
    public boolean findByQueryKey(@RequestParam HashMap<String, Object> paramMap) {
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        return listDbService.findByQueryKey(paramMap ,userId ,organId);
    }
    /**
     * 下载黑白名单数据;<br>
     * @param  paramMap 包含以下参数：
     * listType | String | 必需 | 名单类型 <br>
     * listDbId | String | 必需 |名单id<br>
     * downType | String | 必需 |下载类型<br>
     * @return null
     */
    @RequestMapping("down")
    @WebLogger("下载黑白名单数据以及模板")
    @ResponseBody
    public Response downloadExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> paramMap) throws Exception {
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        paramMap.put("userId", userId);
        paramMap.put("orgId", organId);
        String listType = (String)paramMap.get("listType");
        Integer listDbId = Integer.valueOf(Integer.valueOf((String)paramMap.get("listDbId")).intValue());
        String tableName = "organ_" + organId + "_" + listType + "_" + listDbId;
        paramMap.put("tableName", tableName);
        String validColArray = "";
        String headerStr = "";
        String fieldEn = "";
        paramMap.put("schemaName", this.getDbName());
        List columnList = listDbService.getColumnList(paramMap);
        Iterator custLists = columnList.iterator();

        while(true) {
            TblColumn fileType;
            do {
                if(!custLists.hasNext()) {
                    validColArray = validColArray.substring(0, validColArray.length() - 1);
                    paramMap.put("validColArray", validColArray);
                    String fileType1 = "模版导出";
                    Object custLists1 = new ArrayList();
                    if(paramMap.get("downType").equals("downData")) {
                        fileType1 = "数据导出";
                        custLists1 = listDbService.queryValidColumnData(paramMap);
                    }

                    headerStr = headerStr.substring(0, headerStr.length() - 1);
                    String[] headerArr1 = headerStr.split(",");
                    fieldEn = fieldEn.substring(0, fieldEn.length() - 1);
                    String[] fieldEnArr = fieldEn.split(",");
                    String listTypeName = "";
                    if(listType.equals("b")) {
                        listTypeName = "黑名单库-";
                    } else if(listType.equals("w")) {
                        listTypeName = "白名单库-";
                    }

                    paramMap.put("id", listDbId);
                    String custDbName = listDbService.findById(paramMap).getListName();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String fileName = listTypeName + custDbName + fileType1 + df.format(new Date()) + ".xlsx";
                    response.setContentType("application/x-msdownload;");
                    response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
                    ExcelUtil.exportCustListExcel(response.getOutputStream(), "xlsx", headerArr1, fieldEnArr, (List)custLists1);
                    return null;
                }

                fileType = (TblColumn)custLists.next();
            } while(!fileType.getColName().equals("id") && !fileType.getColName().startsWith("t") && !fileType.getColName().equals("nick_name") && !fileType.getColName().equals("create_time"));

            if(fileType.getColName().equals("nick_name")) {
                validColArray = validColArray + fileType.getColName() + " as nickName,";
            } else if(fileType.getColName().equals("create_time")) {
                validColArray = validColArray + fileType.getColName() + ",";
            } else if(fileType.getColName().startsWith("t")) {
                validColArray = validColArray + fileType.getColName() + ",";
                paramMap.put("id", fileType.getColComment());
                Field headerArr = fieldService.findByFieldId(paramMap);
                headerStr = headerStr + headerArr.getCnName() + ",";
                fieldEn = fieldEn + fileType.getColName() + ",";
            }
        }
    }
    /**
     *  用途接口;<br>
     * @param  request
     * @return String
     */
    @PostMapping("upload/{resourceType}")
    @WebLogger("upload/{resourceType}")
    @ResponseBody
    public Response upload(HttpServletRequest request) {
        String accessUrl = request.getSession().getServletContext().getRealPath("/WEB-INF/hei.xlsx");
        HashMap paramMap = new HashMap();
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        paramMap.put("userId", userId);
        paramMap.put("orgId", organId);
        paramMap.put("tableName", "organ_1_b_1");
        boolean flag = listDbService.importExcel(accessUrl, paramMap);
        return  new Response(flag);
    }


    /**
     * 上传黑白名单execl数据;<br>
     * @param  paramMap
     * listType | String | 必需 | 名单类型 <br>
     * listDbId | String | 必需 |名单id<br>
     * @return Response
     */
    @RequestMapping("springUpload")
    @WebLogger("上传黑白名单数据")
    @ResponseBody
    public Response springUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> paramMap) throws Exception {
        try{
            Integer listDbId = Integer.valueOf(Integer.valueOf((String)paramMap.get("listDbId")).intValue());
            long startTime = System.currentTimeMillis();
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            String accessUrl = "";
            String listType;
            String xlsxName;
            Response res = new Response();
            if(multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest userId = (MultipartHttpServletRequest)request;
                Iterator nickName = userId.getFileNames();

                while(nickName.hasNext()) {
                    MultipartFile organId = userId.getFile(nickName.next().toString());
                    if(organId != null) {
                        listType = request.getSession().getServletContext().getRealPath("/") + "upload/" + listDbId + "/";
                        if(!(new File(listType)).exists()) {
                            File tableName = new File(listType);
                            tableName.mkdirs();
                        }

                        xlsxName = listType + System.currentTimeMillis() + ".xlsx";
                        organId.transferTo(new File(xlsxName));
                        accessUrl = xlsxName;
                    }
                }
            }

            Long userId = UserContextUtil.getUserId();
            String nickName = UserContextUtil.getNickName();
            Long organId = UserContextUtil.getOrganId();
            paramMap.put("userId", userId);
            paramMap.put("nickName", nickName);
            paramMap.put("orgId", organId);
            listType = (String)paramMap.get("listType");
            xlsxName = "organ_" + organId + "_" + listType + "_" + listDbId;
            paramMap.put("tableName", xlsxName);
            paramMap.put("id", listDbId);
            ListDb listDb = listDbService.findById(paramMap);
            //判断表格数据是否匹配
            String queryField = listDb.getQueryField();
            String[] fieldIds = listDb.getQueryField().split(",");
            List<String> fieldNameList = new ArrayList<>();
            for(int i = 0;i < fieldIds.length;i++){
                Map map = new HashMap();
                map.put("userId",userId);
                map.put("id",Long.valueOf(fieldIds[i]));
                Field field = fieldService.findByFieldId(map);
                fieldNameList.add(field.getCnName());
            }
            //获取Excel第一行
            FileInputStream input = new FileInputStream(accessUrl);
            Workbook Workbook = WorkbookFactory.create(input);
            Sheet sheet = Workbook.getSheetAt(0);
            if(sheet != null){
                Row row = sheet.getRow(0);
                int count = row.getLastCellNum();
                for(int i = 0;i < count;i++){
                    String fieldName = ExcelUtil.getCellValue(row.getCell(i)).trim();//字段名
                    if(!((String)fieldNameList.get(i)).equals(fieldName)){
                        res.setCode(1);
                        res.setMsg("数据有误，请重新上传！");
                        return res;
                    }
                }
            }

            Integer queryType = listDb.getQueryType();
            paramMap.put("schemaName", this.getDbName());
            List tblColumnList = listDbService.getColumnList(paramMap);
            HashMap colParam = new HashMap();
            Iterator checkCol = tblColumnList.iterator();

            String path;
            String fileName;
            while(checkCol.hasNext()) {
                TblColumn dupCustList = (TblColumn)checkCol.next();
                path = dupCustList.getColName();
                fileName = dupCustList.getColComment();
                if(path.startsWith("t")) {
                    colParam.put(fileName, path);
                }
            }

            String str = "";
            if(!queryField.equals("") && queryField != null) {
                String[] queryFieldStr = queryField.split(",");

                for(int i = 0; i < queryFieldStr.length; i++) {
                    if(str.equals("")) {
                        str = (String)colParam.get(queryFieldStr[i]) + "=" + "\'" + (String)colParam.get(queryFieldStr[i]) + "\'";
                    } else if(queryType.intValue() == 0) {
                        str = str + " or " + (String)colParam.get(queryFieldStr[i]) + "=" + "\'" + (String)colParam.get(queryFieldStr[i]) + "\'";
                    } else if(queryType.intValue() == 1) {
                        str = str + " and " + (String)colParam.get(queryFieldStr[i]) + "=" + "\'" + (String)colParam.get(queryFieldStr[i]) + "\'";
                    }
                }
            }

            paramMap.put("checkCol", str);
            listDbService.importExcel(accessUrl, paramMap);
            new ArrayList();
            List list = (List)paramMap.get("dupCustList");
            if(list == null){
                Response resp = new Response();
                String name = listType + listDbId + System.currentTimeMillis() + ".xlsx";
                resp.setData(name);
                resp.setCode(1);
                resp.setMsg("数据为空，请重新上传！");
                return resp;
            }

            return res;

        }catch (Exception e){
            e.printStackTrace();
            Response response2 = new Response();
            response2.setCode(1);
            response2.setMsg("数据有误，请重新上传！");
            return response2;
        }

    }
    /**
     * 上传的名单数据与数据库中有重复时，会生成重复的列表，此接口用于下载重复数据的excel;<br>
     * @param  fileName  | String | 必需 | 文件名；<br>
     *  @param  listDbId | String | 必需 | 名单id;<br>
     * @return ResponseEntity<byte[]>
     */
    @WebLogger("上传黑白名单数据后，部分与数据库中数据冲突的数据信息下载")
    @RequestMapping("downDupCustList")
    public ResponseEntity<byte[]> downDupCustList(HttpServletRequest request,HttpServletResponse response, String fileName, String listDbId) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/") + "download/" + listDbId + "/" + fileName;
        DownloadUtils.download(response, path);//浏览器下载数据到本地
        return null;
    }
}

