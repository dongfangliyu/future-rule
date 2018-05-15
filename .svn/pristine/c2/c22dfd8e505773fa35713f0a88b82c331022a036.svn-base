package com.zw.rule.web.engine.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.ExcelUtil;
import com.zw.rule.batchtest.util.BatchExcelUtil;
import com.zw.rule.core.Response;
import com.zw.rule.datamanage.po.Field;
import com.zw.rule.datamanage.service.FieldService;
import com.zw.rule.engine.po.Engine;
import com.zw.rule.engine.po.EngineResultSet;
import com.zw.rule.engine.po.EngineVersion;
import com.zw.rule.engine.po.ResultSetList;
import com.zw.rule.engine.service.*;
import com.zw.rule.engine.vo.EngineTestResultVo;
import com.zw.rule.engine.vo.EngineVersionVo;
import com.zw.rule.jeval.tools.CollectionUtil;
import com.zw.rule.knowledge.po.KnowledgeTree;
import com.zw.rule.knowledge.service.KnowledgeTreeService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.DownloadUtils;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by shengkf on 2017/5/12 0012.
 */
@Controller
@RequestMapping({"engine"})
public class EngineController {
    @Resource
    private ResultSetService resultSetService;
    @Resource
    private ResultSetListService resultSetListService;
    @Resource
    private FieldService fieldService;
    @Resource
    private EngineService engineService;
    @Resource
    private EngineVersionService engineVersionService;
    @Resource
    private KnowledgeTreeService knowledgeTreeService;
    @Resource
    private EngineApiService engineApiService;
    /**
     * 页面跳转到结果集界面
     * @return
     */
    @RequestMapping({"/toresult"})
    public String resultList() {
        return "/engineMange/resultList";
    }

    /**
     * 页面跳转到测试信息界面
     * @return
     */
    @RequestMapping({"/engineTest"})
    public String testInfo() {
        return "/engineMange/testInfo";
    }

    /**
     * 结果集中点击查看跳转到结果详情界面
     * @return
     */
    @RequestMapping({"/lookOver"})
    public String lookOver() {
        return "/engineMange/lookOver";
    }
    /**
     * 结果集中点击批量测试跳转页面
     * @return
     */
    @RequestMapping({"/engineBatchTest"})
    public String engineBatchTest() {
        return "/engineMange/engineBatchTest";
    }

    @RequestMapping({"/list"})
    @WebLogger("引擎列表")
    @ResponseBody
    public Response enginList(@RequestBody ParamFilter queryFilter) {
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        String roleId = UserContextUtil.getCurrentRoleId();
        queryFilter.getParam().put("userId", userId);
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        int pageSize=queryFilter.getPage().getPageSize();
        String searchString=(String)queryFilter.getParam().get("searchString");
        Engine engine =new Engine();
        engine.setOrgId(organId);
        engine.setSearchString(searchString);
        engine.setRoleId(roleId);
        engine.setUserId(userId);
        PageHelper.startPage(pageNo, pageSize);
        List list = engineService.queryEngineByList(engine);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    /**
     * 引擎测试列表
     * @param queryFilter
     * @return
     */
    @RequestMapping({"engineTestList"})
    @WebLogger("引擎测试列表")
    @ResponseBody
    public Response engineTestList(@RequestBody ParamFilter queryFilter) {
        Long userId = UserContextUtil.getUserId();
        queryFilter.getParam().put("userId", userId);
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List engineVersionList = engineVersionService.queryEngineVersionByEngineId(queryFilter.getParam());
        Iterator pageInfo = engineVersionList.iterator();
        while(pageInfo.hasNext()) {
            EngineVersion engineVersion = (EngineVersion)pageInfo.next();
            String latestTime = engineVersion.getLatestTime();
            if(latestTime != null && !latestTime.equals("")) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss \'CST\' yyyy", Locale.US);

                try {
                    Date e = sdf2.parse(latestTime);
                    engineVersion.setLatestTime(sdf1.format(e));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        PageInfo pageInfo1 = new PageInfo(engineVersionList);
        return new Response(pageInfo1);
    }

    /**
     *
     * @param request
     * @param paramMap
     * versionId Long 版本id
     * rowCt
     * nullCtRatio
     * elseCtRatio
     * engineId
     * @return
     */
    @RequestMapping({"createSampleData"})
    @WebLogger("创建模板数据")
    @ResponseBody
    public Response querySampleData(HttpServletRequest request,@RequestBody HashMap<String, Object> paramMap) {
        Long userId = UserContextUtil.getUserId();
        Long orgId = UserContextUtil.getOrganId();
        EngineVersion engineVersion = new EngineVersion();
        Long versionId = Long.valueOf(String.valueOf(paramMap.get("versionId")));
        engineVersion.setVerId(versionId);
        paramMap.put("userId",userId);//2017/06/07
        paramMap.put("orgId",orgId);//2017/06/07
        List fieldList = engineService.getEngineByField(engineVersion,paramMap);
        ArrayList newFieldList = new ArrayList();
        HashMap filedmap = new HashMap();

        for(int downloadDir = 0; downloadDir < fieldList.size(); ++downloadDir) {
            if(!filedmap.containsKey(((Field)fieldList.get(downloadDir)).getId())) {
                filedmap.put(((Field)fieldList.get(downloadDir)).getId(), (Object)null);
                newFieldList.add((Field)fieldList.get(downloadDir));
            }
        }

        paramMap.put("fieldList", newFieldList);
        if(fieldList.size() > 0) {
            String url = request.getSession().getServletContext().getRealPath("/") + "download/engineTestSampleData";
            if(!(new File(url)).exists()) {
                File dir = new File(url);
                dir.mkdirs();
            }
            paramMap.put("downloadDir", url);
            return new Response(fieldService.addEngineTestData(paramMap,userId.toString()));
        } else {
            return new Response();
        }
    }

    /**
     * 下载引擎测试数据
     * @param request
     * @param fileName
     * @return
     * @throws IOException
     */
    @RequestMapping({"/downEngineTestData"})
    @WebLogger("下载引擎测试数据")
    public ResponseEntity<byte[]> downLoadEngineTestData(HttpServletRequest request,HttpServletResponse response, String fileName) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/") + "download/engineTestSampleData/" + fileName;
        DownloadUtils.download(response, path);//浏览器下载txt数据到本地
        return null;
    }

    /**
     * 下载PDF的引擎测试结果数据
     * @param request
     * @param paramMap
     * @return
     * @throws IOException
     */
    @RequestMapping({"downloadPDFEngineTestResultData"})
    @WebLogger("下载PDF的引擎测试结果数据")
    public ResponseEntity<byte[]> downLoadPDFEngineTestResultData(HttpServletRequest request, HttpServletResponse response,@RequestParam HashMap<String, Object> paramMap) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/") + "download/engineTestResult/";
        if(!(new File(path)).exists()) {
            File fileName = new File(path);
            fileName.mkdirs();
        }

        paramMap.put("path", path);
        String fileName1 = fieldService.addEngineTestResultPdf(paramMap);
        String filePath = path + fileName1;//文件在服务器上的路径
        DownloadUtils.download(response, filePath);//浏览器下载pdf数据到本地
        return null;
    }

    /**
     * 引擎测试数据上传
     * @param request
     * @param response
     * @param paramMap
     * versionId Long 版本id
     * fileName String
     * @return
     * @throws Exception
     */
    @RequestMapping({"/engineTestDataUpload"})
    @WebLogger("引擎测试数据上传")
    @ResponseBody
    public Response engineTestDataUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> paramMap) throws Exception {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        String accessUrl = "";
        String fileName = "";
        if(multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest versionId = (MultipartHttpServletRequest)request;
            Iterator rows = versionId.getFileNames();

            while(rows.hasNext()) {
                MultipartFile e = versionId.getFile(rows.next().toString());
                if(e != null) {
                    String file = request.getSession().getServletContext().getRealPath("/") + "upload/engineTestSampleData/";
                    if(!(new File(file)).exists()) {
                        File read = new File(file);
                        read.mkdirs();
                    }

                    fileName = System.currentTimeMillis() + ".txt";
                    String url = file + fileName;
                    e.transferTo(new File(url));
                    accessUrl = url;
                }
            }
        }
        String verId = String.valueOf(paramMap.get("versionId"));
        int n = 0;
        try {
            String code = "GBK";
            File fileUrl = new File(accessUrl);
            if(fileUrl.isFile() && fileUrl.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(fileUrl), code);
                BufferedReader bufferedReader = new BufferedReader(isr);
                for(String lineTxt = null; (lineTxt = bufferedReader.readLine()) != null; n++) {
                    HashMap param = new HashMap();
                    param.put("batchNo", paramMap.get("fileName"));
                    String[] fieldList = lineTxt.split(",");
                    for(int i = 0; i < fieldList.length; ++i) {
                        String[] field = fieldList[i].split(":");
                        param.put(field[0].replace("\"", ""), field[1].replace("\"", ""));
                    }
                    Long userId = UserContextUtil.getUserId();//2017/5/17
                    Long orgId = UserContextUtil.getOrganId();//2017/6/1
                    param.put("userId",userId);//2017/5/17
                    param.put("orgId",orgId);//2017/6/1
                    engineService.getEngineVersionExecute(param, verId);
                }
                isr.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return new Response(n);
    }

    /**
     * 下载引擎测试结果数据
     * @param request
     * @param response
     * @param paramMap
     * batchNo String 引擎批量测试批次号
     * @return
     * @throws Exception
     */
    @RequestMapping({"/downloadEngineTestResultData"})
    @WebLogger("下载引擎测试结果数据")
    @ResponseBody
    public Response downLoadEngineTestResultData(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> paramMap) throws Exception {
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        paramMap.put("userId", userId);
        paramMap.put("orgId", organId);
        new ArrayList();
        ArrayList resultVoList = new ArrayList();
        List engineResultSetList = resultSetService.queryBatchTestResultSetByBatchNo(paramMap);
        Iterator headerStr = engineResultSetList.iterator();

        String fileName;
        while(headerStr.hasNext()) {
            EngineResultSet headerArr = (EngineResultSet)headerStr.next();
            EngineTestResultVo fieldEn = new EngineTestResultVo();
            fieldEn.setId(headerArr.getId());
            fieldEn.setEngineName(headerArr.getEngineName());
            String fieldEnArr = headerArr.getResult();
            if(fieldEnArr != null && !fieldEnArr.equals("") && !fieldEnArr.equals("1")) {
                if(fieldEnArr.equals("2")) {
                    fieldEnArr = "拒绝";
                }
            } else {
                fieldEnArr = "通过";
            }

            fieldEn.setResult(fieldEnArr);
            fieldEn.setScorecardScore(headerArr.getScorecardscore());
            String df = "";
            fileName = "";
            String hardnessRefuseRuleHitReason = "";
            String plusMinusRuleHitReason = "";
            List resultSetList = headerArr.getResultSetList();
            Iterator subIterator = resultSetList.iterator();

            while(subIterator.hasNext()) {
                ResultSetList resultSet = (ResultSetList)subIterator.next();
                String reason = resultSet.getDesc();
                if(!reason.equals("") && reason != null) {
                    if(resultSet.getType().intValue() == 1) {
                        if(df.equals("")) {
                            df = reason;
                        } else {
                            df = df + "," + reason;
                        }
                    } else if(resultSet.getType().intValue() == 2) {
                        if(fileName.equals("")) {
                            fileName = reason;
                        } else {
                            fileName = fileName + "," + reason;
                        }
                    } else if(resultSet.getType().intValue() == 3) {
                        if(hardnessRefuseRuleHitReason.equals("")) {
                            hardnessRefuseRuleHitReason = reason;
                        } else {
                            hardnessRefuseRuleHitReason = hardnessRefuseRuleHitReason + "," + reason;
                        }
                    } else if(resultSet.getType().intValue() == 4) {
                        if(plusMinusRuleHitReason.equals("")) {
                            plusMinusRuleHitReason = reason;
                        } else {
                            plusMinusRuleHitReason = plusMinusRuleHitReason + "," + reason;
                        }
                    }
                }
            }
            fieldEn.setBlackHitReason(df);
            fieldEn.setWhiteHitReason(fileName);
            fieldEn.setHardnessRefuseRuleHitReason(hardnessRefuseRuleHitReason);
            fieldEn.setPlusMinusRuleHitReason(plusMinusRuleHitReason);
            resultVoList.add(fieldEn);
        }
        String headerStr1 = "用户ID,引擎名称,决策建议,信用评分,黑名单,白名单,硬性拒绝规则,加减分规则";
        String[] headerArr1 = headerStr1.split(",");
        String fieldEn1 = "id,engineName,result,scorecardScore,blackHitReason,whiteHitReason,hardnessRefuseRuleHitReason,plusMinusRuleHitReason";
        String[] fieldEnArr1 = fieldEn1.split(",");
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fileName = df1.format(new Date()) + ".xlsx";
        response.setContentType("application/x-msdownload;");
        response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
        ExcelUtil.exportEngineTestResultExcel(response.getOutputStream(), "xlsx", headerArr1, fieldEnArr1, resultVoList);
        return new Response("下载成功");
    }

    @RequestMapping({"add"})
    @WebLogger("保存引擎")
    @ResponseBody
    public Response addEngine(@RequestBody Engine engine , HttpServletRequest request) {
        //获取上下文路径
        String url = request.getContextPath();
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        String roleId = UserContextUtil.getCurrentRoleId();
        Date currDate = new Date();
        engine.setUserId(userId);
        engine.setOrgId(organId);
        engine.setCreator(userId);
        engine.setCreateTime(currDate);
        engine.setUpdateTime(currDate);
        engine.setRoleId(roleId);
        engine.setStatus(1);
        boolean flag = engineService.saveEngine(engine , url);
        this.addKnowledgeTree(engine);
        HashMap param = new HashMap();
        Response response = new Response();
        response.setData(param);
        if(flag) {
            response.setCode(0);
        } else {
            response.setCode(1);
        }
        return new Response(param);
    }

    /**
     * 修改弹出窗口
     * @param id
     * @return
     */
    @RequestMapping({"initupdate"})
    @WebLogger("修改")
    @ResponseBody
    public Response initUpdate(String id) {
        HashMap map = new HashMap();
        Engine engineVo = new Engine();
        engineVo.setId(Long.valueOf(Long.parseLong(id)));
        Long organId = UserContextUtil.getOrganId();
        engineVo.setOrgId(organId);
        engineVo = engineService.queryEngineById(engineVo);
        map.put("data", engineVo);
        return new Response(map);
    }

    @RequestMapping({"delete"})
    @WebLogger("删除引擎")
    @ResponseBody
    public Response delete(String id) {
        Response response=new Response();
        HashMap map = new HashMap();
        Engine engineVo = new Engine();
        engineVo.setId(Long.valueOf(Long.parseLong(id)));
        Long organId = UserContextUtil.getOrganId();
        engineVo.setOrgId(organId);
        if(engineService.deleteEngine(engineVo)>0) {
                response.setMsg("删除成功！");
        } else {
            response.setMsg("删除失败！");
        }
        return response;
    }

    @RequestMapping({"update"})
    @WebLogger("保存或修改引擎")
    @ResponseBody
    public Response updateEngine(@RequestBody Engine engine , HttpServletRequest request) {
        //获取上下文路径
        String url = request.getContextPath();
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        String roleId = UserContextUtil.getCurrentRoleId();
        engine.setOrgId(organId);
        Response response=new Response();
        engine.setCreator(userId);
        engine.setStatus(1);
        engine.setUserId(userId);
        engine.setRoleId(roleId);
        if(engine.getId() == null) {
            if(engineService.saveEngine(engine , url)){
                response.setMsg("添加成功！");
            }else response.setMsg("添加失败！");
            this.addKnowledgeTree(engine);
        } else {
            if(engineService.updateEngine(engine)>0){
                response.setMsg("修改成功！");
            }else response.setMsg("修改失败！");
        }
        return response;
    }

    @RequestMapping({"/getUUID"})
    @WebLogger("创建引擎弹出窗口")
    @ResponseBody
    public Response getUUID() {
        HashMap map = new HashMap();
        map.put("uuid", UUID.randomUUID());
        map.put("result", "1");
        return new Response(map);
    }

    /**
     * 创建知识树
     * @param engine
     */
    private void addKnowledgeTree(Engine engine) {
        ArrayList treeList = new ArrayList();
        KnowledgeTree knowledgeTree_1 = new KnowledgeTree();
        knowledgeTree_1.setName("引擎规则集");
        knowledgeTree_1.setParentId(Long.valueOf(0L));
        knowledgeTree_1.setStatus(Integer.valueOf(1));
        knowledgeTree_1.setOrgId(engine.getOrgId());
        knowledgeTree_1.setEngineId(engine.getId());
        knowledgeTree_1.setTreeType(Integer.valueOf(0));
        knowledgeTree_1.setType(Integer.valueOf(2));
        knowledgeTree_1.setUserId(engine.getUserId());
        treeList.add(knowledgeTree_1);
        KnowledgeTree knowledgeTree_2 = new KnowledgeTree();
        knowledgeTree_2.setName("评分卡");
        knowledgeTree_2.setParentId(Long.valueOf(0L));
        knowledgeTree_2.setStatus(Integer.valueOf(1));
        knowledgeTree_2.setOrgId(engine.getOrgId());
        knowledgeTree_2.setEngineId(engine.getId());
        knowledgeTree_2.setTreeType(Integer.valueOf(1));
        knowledgeTree_2.setType(Integer.valueOf(2));
        knowledgeTree_2.setUserId(engine.getUserId());
        treeList.add(knowledgeTree_2);
        knowledgeTreeService.batchInsert(treeList);
    }

    /**
     * 获取引擎版本列表
     * @param engineId  /Long/引擎id
     * @return
     */
    @RequestMapping({"version"})
    @WebLogger("获取引擎版本列表")
    @ResponseBody
    public Response queryEngineVerList(@RequestBody long engineId) {
        List versions = engineVersionService.queryEngineVersionListByEngineId(Long.valueOf(engineId));
        TreeMap map = new TreeMap();//2017/5/17
        if(CollectionUtil.isNotNullOrEmpty(versions)) {
            EngineVersionVo engineVersionVos = null;
            boolean v = false;
            Collections.sort(versions);

            int v1;
            for(Iterator var8 = versions.iterator(); var8.hasNext(); map.put(Integer.valueOf(v1), engineVersionVos)) {
                EngineVersion engineVersion = (EngineVersion)var8.next();
                v1 = engineVersion.getVersion().intValue();
                if(map.containsKey(engineVersion.getVersion())) {
                    if(engineVersion.getSubVer().intValue() != 0) {
                        ((EngineVersionVo)map.get(engineVersion.getVersion())).getSubEngineVersionList().add(engineVersion);
                    }
                } else {
                    engineVersionVos = new EngineVersionVo();
                    if(engineVersion.getSubVer().intValue() == 0) {
                        engineVersionVos.setEngineVersion(engineVersion);
                        engineVersionVos.setSubEngineVersionList(new ArrayList());
                    } else {
                        ArrayList engineVersions = new ArrayList();
                        engineVersions.add(engineVersion);
                        engineVersionVos.setSubEngineVersionList(engineVersions);
                    }
                }
            }
        }

        ArrayList engineVersionVos1 = new ArrayList();
        Iterator engineVersion1 = map.values().iterator();

        while(engineVersion1.hasNext()) {
            EngineVersionVo engineVersionVo = (EngineVersionVo)engineVersion1.next();
            engineVersionVos1.add(engineVersionVo);
        }
        return new Response(engineVersionVos1);
    }

    /**
     * 数据填写显示
     * @param engineVersion 引擎版本实体
     * verId /Long /引擎版本主键
     * @return
     */
    @RequestMapping({"/engineField"})
    @WebLogger("数据填写弹框")
    @ResponseBody
    public Response queryEngineField(@RequestBody EngineVersion engineVersion) {
        Long userId = UserContextUtil.getUserId();
        Long orgId = UserContextUtil.getOrganId();
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("orgId",orgId);
        List fields = engineService.getEngineByField(engineVersion,paramMap);
        List fieldlist = new ArrayList();
        Map filedmap = new HashMap();
        for(int i = 0; i < fields.size(); i++) {
            if(!filedmap.containsKey(((Field)fields.get(i)).getId())) {
                filedmap.put(((Field)fields.get(i)).getId(), (Object)null);
                fieldlist.add((Field)fields.get(i));
            }
        }
        paramMap.put("fields",fieldlist);
        return new Response(paramMap);
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
     * 数据填写执行
     * engineId /String / 可选 / 引擎id /<br>
     * versionId /String /引擎版本id /<br>
     * valueScope /String /字段约束范围 /<br>
     */
    @RequestMapping({"/pageCheck"})
    @WebLogger("数据填写执行")
    @ResponseBody
    public Response pageCheckExecute(@RequestParam String valueScope,@RequestParam int type,@RequestParam String complex, @RequestParam String versionId) {
        JSONArray array = JSONArray.parseArray(valueScope);
        HashMap inputMap = new HashMap();
        for(int i = 0; i < array.size(); i++) {
            JSONObject map = JSONObject.parseObject(array.get(i).toString());
            Iterator json = map.entrySet().iterator();
            while(json.hasNext()) {
                Map.Entry entry = (Map.Entry)json.next();
                inputMap.put((String)entry.getKey(), entry.getValue());
            }
        }
        //获取用户id
        Long userId = UserContextUtil.getUserId();
        inputMap.put("userId",userId);
        //获取组织id
        Long orgId = UserContextUtil.getOrganId();
        inputMap.put("orgId",orgId);
        String dbName = getDbName();
        inputMap.put("dbName",dbName);
        inputMap.put("complexfield", complex);
        inputMap.put("apiType", type);
        Map<String,Object> resultMap = engineApiService.getEngineVersionExecute(inputMap, versionId);
        if(resultMap!=null&&resultMap.containsKey("message")){
            Response response =  new Response((String)resultMap.get("message"));
            response.setCode(1);
            return  response;
        }
        return new Response(resultMap);
    }
    /**
     * 创建引擎测试
     * @paramMap
     * engineId /String / 可选 / 引擎id /<br>
     * fieldList
     * rowCt
     * nullCtRatio
     * elseCtRatio
     * @return
     */
    @RequestMapping({"createEngineTestSample"})
    @ResponseBody
    public Response addEngineTestSample(@RequestParam Map<String, Object> paramMap) {
        Long userId = UserContextUtil.getUserId();
        return new Response(fieldService.addEngineTestData(paramMap,userId.toString()));
    }

    /**
     * 结果集页面点击批量测试调用
     * @param queryFilter
     * param{
     *      engineId:
     * page
     * @return
     */
    @RequestMapping({"/engineBatchTestList"})
    @ResponseBody
    @WebLogger("查询批量测试")
    public Response queryBatchTestResult(@RequestBody ParamFilter queryFilter) {
        Long userId = UserContextUtil.getUserId();
        queryFilter.getParam().put("userId", userId);
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List resultSetList = resultSetService.queryBatchTestResultSetByEngineId(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(resultSetList);
        return new Response(pageInfo);
    }

    /**
     *批量测试页面点击查看调用
     * @param queryFilter
     * param{
     *      engineId:
     *      searchKey:查询条件，sql查询语句中没有用这个参数
     *      batchNo: 引擎批量测试批次号
     *      }
     * page
     * @return
     */
    @RequestMapping({"/engineBatchTestResultList"})
    @ResponseBody
    @WebLogger("查看批量测试结果集")
    public Response queryEngineTestResultList(@RequestBody ParamFilter queryFilter) {
        Long userId = UserContextUtil.getUserId();
        queryFilter.getParam().put("userId", userId);
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List resultSetList = resultSetService.queryBatchTestResultSetByBatchNo(queryFilter.getParam());
        EngineResultSet resultSet;
        String result;
        for(Iterator pageInfo = resultSetList.iterator(); pageInfo.hasNext(); resultSet.setResult(result)) {
            resultSet = (EngineResultSet)pageInfo.next();
            result = resultSet.getResult();
            if(result != null && !result.equals("") && !result.equals("1")) {
                if(result.equals("2")) {
                    result = "拒绝";
                }
            } else {
                result = "通过";
            }
        }
        PageInfo pageInfo1 = new PageInfo(resultSetList);
        return new Response(pageInfo1);
    }


    /**
     * 引擎管理左侧菜单-结果集-查询结果集数据
     * @param queryFilter
     * param{engineId: startDate: endDate: }
     * page
     * @return
     */
    @RequestMapping({"/results"})
    @ResponseBody
    @WebLogger("查询结果集")
    public Response queryResults(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List resultSets = resultSetService.queryEngineResultSetBySegment(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(resultSets);
        return new Response(pageInfo);
    }

    /**
     * @param resultsetId
     * @return
     */
    @RequestMapping({"/todetail"})
    @ResponseBody
    public Response detail(@RequestParam Long resultsetId) {
        List resultSetLists = resultSetListService.queryResultSetListByResultsetId(resultsetId);
        return new Response(resultSetLists);
    }

    /**
     * 结果集列表点击查看调用
     * @param resultSetId
     * @return
     */
    @RequestMapping({"/pageinput"})
    @ResponseBody
    @WebLogger("查看结果集某条数据的详情")
    public Response queryResultInfo(@RequestParam Long resultSetId) {
        List list = resultSetService.queryResultSetDetailsById(resultSetId.longValue());
        EngineResultSet er = (EngineResultSet)list.get(0);
        HashMap resultMap = new HashMap();
        HashMap map = new HashMap();
        if(er != null) {
            List resuSetLists = er.getResultSetList();
            if(CollectionUtil.isNotNullOrEmpty(resuSetLists)) {
                Iterator json = resuSetLists.iterator();
                while(json.hasNext()) {
                    ResultSetList resultSetList = (ResultSetList)json.next();
                    if(resultMap.containsKey(resultSetList.getType())) {
                        ((List)resultMap.get(resultSetList.getType())).add(resultSetList);
                    } else {
                        resultMap.put(resultSetList.getType(), new ArrayList());
                        ((List)resultMap.get(resultSetList.getType())).add(resultSetList);
                    }
                }
            }
            JSONObject json = JSONObject.parseObject(er.getInput());
            map.put("denyRules",json.get("denyRules"));
            map.put("addOrSubRules",json.get("addOrSubRules"));
        }
        map.put("er", er);
        map.put("blackList", resultMap.get(Integer.valueOf(1)));
        map.put("whiteList", resultMap.get(Integer.valueOf(2)));
        map.put("deadRules", resultMap.get(Integer.valueOf(3)));
        map.put("focusRules", resultMap.get(Integer.valueOf(4)));
        return new Response(map);
    }

    /**
     * 批量生成测试用例
     * @param
     * verId / Long /引擎版本主键
     * count / int /生成测试用例数量
     * @return
     */
    @GetMapping({"/engineTestExport"})
    public void downLoadEngineTestExport(@RequestParam Long verId ,@RequestParam int count , HttpServletResponse response) {
        EngineVersion engineVersion = new EngineVersion();
        engineVersion.setVerId(verId);
        Long userId = UserContextUtil.getUserId();
        Long orgId = UserContextUtil.getOrganId();
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("userId",userId);
        paramMap.put("orgId",orgId);
        //获取引擎版本关联字段
        List<Field> fields = engineService.getEngineByField(engineVersion,paramMap);
        List<Field> fieldList = new ArrayList<>();
        Map<Long , Object> filedmap = new HashMap<>();
        for(int i = 0; i < fields.size(); i++) {
            if(!filedmap.containsKey((fields.get(i)).getId())) {
                filedmap.put((fields.get(i)).getId(), null);
                fieldList.add(fields.get(i));
            }
        }
        Map<String , Object> mapHeader = new HashMap<>();
        Map<String , Object> mapClass = new HashMap<>();
        //生成excelHEADER
        String[] engineHeader = new String[fieldList.size()];
        for(int i = 0;i < fieldList.size();i++){
            engineHeader[i] = fieldList.get(i).getCnName();
        }
        mapHeader.put("普通测试",engineHeader);
        //生成excelClASS
        String[] engineClass = new String[fieldList.size()];
        for(int i = 0;i < fieldList.size();i++){
            engineClass[i] = fieldList.get(i).getEnName();
        }
        mapClass.put("普通测试",engineClass);
        //测试用例
        Map<String , List<List<Field>>> map = new LinkedHashMap<String , List<List<Field>>>();
        List<List<Field>> sheetList = new ArrayList<>();
        for(int i = 0;i < count;i++){
            List<Field> list = new ArrayList<>();
            radomField(fieldList,list);
            sheetList.add(list);
        }
        map.put("普通测试",sheetList);
        //复杂规则
        if(paramMap.containsKey("complex")){
            //获取复杂规则字段
            List<Field> fieldList2 = (List<Field>) paramMap.get("complex");
            //生成excelHEADER
            String[] engineHeader2 = new String[fieldList2.size()];
            for(int i = 0;i < fieldList2.size();i++){
                engineHeader2[i] = fieldList2.get(i).getCnName();
            }
            mapHeader.put("复杂规则测试",engineHeader2);
            //生成excelClASS
            String[] engineClass2 = new String[fieldList2.size()];
            for(int i = 0;i < fieldList2.size();i++){
                engineClass2[i] = fieldList2.get(i).getEnName();
            }
            mapClass.put("复杂规则测试",engineClass2);
            List<List<Field>> sheetList2 = new ArrayList<>();
            for(int i = 0;i < 3*count;i++){
                List<Field> list = new ArrayList<>();
                radomField(fieldList2,list);
                sheetList2.add(list);
            }
            map.put("复杂规则测试",sheetList2);
        }
        try {
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename=" + new String("测试用例.xls".getBytes("utf-8"), "ISO8859-1"));
            BatchExcelUtil.exportExcel(response.getOutputStream(), ".xls", mapHeader, mapClass, map);
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

    private void radomField(List<Field> fieldList , List<Field> list){
        for(int j = 0;j < fieldList.size();j++){
            Field field = fieldList.get(j);
            Field fieldValue = new Field();
            fieldValue.setEnName(field.getEnName());
            fieldValue.setCnName(field.getCnName());
            Random random = new Random();
            if(field.getValueType() == 1){//字段是数值型
                String restrainScope = field.getRestrainScope();//获取字段取值范围
                String[] scope = restrainScope.split(",");
                int min = Integer.valueOf(scope[0].substring(1));//字段最小值
                int max = Integer.valueOf(scope[1].substring(0,scope[1].length()-1));//字段最大值
                String value = random.nextInt(max-min) + min + "";
                fieldValue.setRestrainScope(value);
            }else if(field.getValueType() == 3){//字段是枚举型
                String restrainScope = field.getRestrainScope();//获取字段取值范围
                String[] scope = restrainScope.split(",");
                int index = random.nextInt(scope.length);
                String value = scope[index];
                fieldValue.setRestrainScope(value);
            }else if(field.getValueType() == 2){//字段是字符型
                fieldValue.setRestrainScope("abc");
            }
            list.add(fieldValue);
        }
    }

    /**
     * 引擎测试数据上传
     * @throws Exception
     */
    @RequestMapping({"/engineTestUpload"})
    @ResponseBody
    public Response engineTestUpload(@RequestParam("filename") CommonsMultipartFile file,@RequestParam("verId") String verId) throws Exception {
        POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet hssfSheet = wb.getSheet("普通测试");
        HSSFSheet hssfSheet2 = wb.getSheet("复杂规则测试");
        List<String> jsonList = new ArrayList<>();
        List<String> complexList = new ArrayList<>();
        List<Response> list = new ArrayList<>();
        if(hssfSheet != null) {
            HSSFRow hssfRow1 = hssfSheet.getRow(0);//普通测试第一行
            int count = hssfRow1.getLastCellNum();//获取普通测试列数
            List<String> codeList = new ArrayList<>();
            //普通测试字段code
            for (int i = 0; i <count; i++) {
                String code = BatchExcelUtil.formatCell(hssfRow1.getCell(i));
                String codeValue = code.split("\\|")[1];
                codeList.add(codeValue);
            }

            for (int i = 1; i <= hssfSheet.getLastRowNum(); i++) {
                HSSFRow hssfRow = hssfSheet.getRow(i);
                int lastnum = hssfRow.getLastCellNum();
                String json = "[";
                for(int j=0;j<lastnum;j++) {
                    String value = BatchExcelUtil.formatCell(hssfRow.getCell(j));

                    if(value.split(":").length>1){
                        value = value.split(":")[1];
                    }else{
                        value = value.split(":")[0];
                    }
                    json += "{'"+codeList.get(j)+"':'"+value+"'},";
                }
                json = json.substring(0,json.length()-1);
                json += "]";
                jsonList.add(json);
            }
        }
        //复杂规则
        if(hssfSheet2 != null){
            HSSFRow hssfRow2 = hssfSheet2.getRow(0);//复杂规则测试第一行
            int cellNum = hssfRow2.getLastCellNum();//获取复杂规则测试列数
            List<String> codeList2 = new ArrayList<>();
            //复杂规则测试字段code
            for (int i = 0; i <cellNum; i++) {
                String code2 = BatchExcelUtil.formatCell(hssfRow2.getCell(i));
                String codeValue2 = code2.split("\\|")[1];
                codeList2.add(codeValue2);
            }
            int rowNum = hssfSheet2.getLastRowNum()/3;
            for (int i = 1; i <= rowNum; i++) {
                String complex = "[";
                for(int j = 1;j <= 3;j++){
                    HSSFRow hssfRow = hssfSheet2.getRow(3*i+j-3);
                    complex += "{'lineId':'"+j+"',";
                    for(int k = 0;k < cellNum;k++){
                        String value = BatchExcelUtil.formatCell(hssfRow.getCell(k));

                        if(value.split(":").length>1){
                            value = value.split(":")[1];
                        }else{
                            value = value.split(":")[0];
                        }
                        complex += "'"+codeList2.get(k)+"':'"+value+"',";
                    }
                    complex = complex.substring(0,complex.length()-1);
                    complex += "},";
                }
                complex = complex.substring(0,complex.length()-1);
                complex += "]";
                complexList.add(complex);
            }
        }
        //数据执行
        for(int i = 0;i < jsonList.size();i++){
            Response response = new Response();
            if(complexList.size() > 0){
                response = pageCheckExecute(jsonList.get(i),3,complexList.get(i),verId);
            }else{
                response = pageCheckExecute(jsonList.get(i),3,"",verId);
            }
            list.add(response);
        }
        return new Response(list);
    }

    /**
     * 根据版本获取结果集
     * @param queryFilter
     * verId 版本id
     * type 结果集类型 1.api、2.数据填写、3.批量测试
     */
    @PostMapping({"/result"})
    @ResponseBody
    public Response queryResultList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<EngineResultSet> list = resultSetService.queryResultList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }
}
