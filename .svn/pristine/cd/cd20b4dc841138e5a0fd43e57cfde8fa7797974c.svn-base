package com.zw.rule.web.knowledge.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.datamanage.service.FieldService;
import com.zw.rule.jeval.tools.StringUtil;
import com.zw.rule.knowleage.util.ExcelHeader;
import com.zw.rule.knowleage.util.ExcelUtil;
import com.zw.rule.knowledge.po.ScoreCardJson;
import com.zw.rule.knowledge.po.Scorecard;
import com.zw.rule.knowledge.po.ScorecardField;
import com.zw.rule.knowledge.po.ScorecardRuleContent;
import com.zw.rule.knowledge.service.ScorecardService;
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
 * Created by zhnagtao on 2017/5/11 0011.
 */
@Controller
@RequestMapping({"knowledge/scorecard"})
public class ScorecardController {
    public ScorecardController() {
    }
    @Resource
    private ScorecardService scorecardService;
    @Resource
    private FieldService fieldService;
    @RequestMapping({"list"})
    @ResponseBody
    @WebLogger("获取评分卡列表")
    public Response queryList(@RequestBody ParamFilter queryFilter) {
        queryFilter.getParam().putAll(this.getParam(queryFilter.getParam()));
        if(!queryFilter.getParam().containsKey("status")) {
            queryFilter.getParam().put("status", StringUtil.toLongList("0,1"));
        } else {
            queryFilter.getParam().put("status", StringUtil.toLongList(queryFilter.getParam().get("status").toString()));
        }
        if(queryFilter.getParam().containsKey("parentIds")) {
            queryFilter.getParam().put("parentIds", StringUtil.toLongList(queryFilter.getParam().get("parentIds").toString()));
        }
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List slist = scorecardService.queryScorecardList(queryFilter.getParam());
        Iterator iterator = slist.iterator();
        while(iterator.hasNext()) {
            Scorecard pageInfo = (Scorecard)iterator.next();
            pageInfo.setCode(pageInfo.getCode().replace("SC_", ""));
        }
        PageInfo pageInfo1 = new PageInfo(slist);
        return new Response (pageInfo1);
    }

    @RequestMapping({"/save"})
    @WebLogger("保存评分卡")
    @ResponseBody
    public Response saveScoreCard(@RequestBody Map<String, Object> param) {
        param.putAll(this.getParam(param));
        Response response = new Response();
        if(scorecardService.countOnlyScName(param) > 0){
            response.setCode(1);
            response.setMsg("评分卡名称已存在，请重新输入！");
            return response;
        }
        if(scorecardService.countOnlyScCode(param) >0){
            response.setCode(1);
            response.setMsg("评分卡代码已存在，请重新输入！");
            return response;
        }
        if(scorecardService.addScorecard(param)){
            response.setMsg("保存成功");
        }else response.setMsg("保存失败");
        return response;
    }

    @RequestMapping({"/edit"})
    @ResponseBody
    public Response queryInfo(@RequestBody Map<String, Object> param) {
        Long id = Long.valueOf(param.get("id").toString());
        Scorecard scorecard = scorecardService.findById(id);
        scorecard.setCode(scorecard.getCode().replace("SC_", ""));
        param.putAll(this.getParam(param));
        param.put("valueType", Integer.valueOf(1));
        param.put("isOutput", Integer.valueOf(1));
        List olist = fieldService.queryFieldList(param);
        HashMap map = new HashMap();
        map.put("scorecard", scorecard);
        map.put("olist", olist);
        if(!StringUtil.isBlank(scorecard.getScore())) {
            map.put("scoreJson", JSONObject.parseObject(scorecard.getScore(), ScoreCardJson.class));
        }
        if(!StringUtil.isBlank(scorecard.getPd())) {
            map.put("pdJson", JSONObject.parseObject(scorecard.getPd(), ScoreCardJson.class));
        }
        if(!StringUtil.isBlank(scorecard.getOdds())) {
            map.put("oddsJson", JSONObject.parseObject(scorecard.getOdds(), ScoreCardJson.class));
        }
        return new Response(map);
    }

    @RequestMapping({"/update"})
    @WebLogger("修改评分卡")
    @ResponseBody
    public Response updateScoreCard(@RequestBody Map<String, Object> param) {
        param.putAll(this.getParam(param));
        Response response = new Response();
        int countScName = scorecardService.countOnlyScName(param);
        if(countScName > 0){
            response.setCode(1);
            response.setMsg("评分卡代码已存在，请重新输入！");
            return response;
        }
        int countScCode = scorecardService.countOnlyScCode(param);
        if(countScCode >0){
            response.setCode(1);
            response.setMsg("评分卡名称已存在，请重新输入！");
            return response;
        }
        if(scorecardService.updateScorecard(param)){
            response.setMsg("修改成功");
        }else response.setMsg("修改失败");
        return response;
    }

    @RequestMapping({"/updateStatus"})
    @WebLogger("修改分卡状态")
    @ResponseBody
    public Response updateStatus(@RequestBody Map<String, Object> param) {
        param.putAll(this.getParam(param));
        Response response = new Response();
        if(scorecardService.updateScorecardStatus(param)) {
            response.setMsg("成功！");
        } else {
            response.setMsg("失败！");
        }
        return response;
    }

    @RequestMapping({"/getFieldList"})
    @WebLogger("评分卡获取字段列表")
    @ResponseBody
    public Response queryFieldByUserWithPage(@RequestBody ParamFilter queryFilter) {
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        queryFilter.getParam().put("userId", userId);
        queryFilter.getParam().put("organId", organId);
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List fieldList = fieldService.queryFieldList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(fieldList);
        return new Response(pageInfo);
    }

    @RequestMapping({"/export"})
    @WebLogger("导出Excel")
    public void downLoad(@RequestParam Map<String, Object> param, HttpServletResponse response) {
        param.put("parentId", Long.valueOf(1L));
        param.put("status", Integer.valueOf(1));
        param.putAll(this.getParam(param));
        List list = scorecardService.queryRuleExcelData(param);
        try {
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename=" + new String("评分卡.xls".getBytes("utf-8"), "ISO8859-1"));
            ExcelUtil.exportExcel(response.getOutputStream(), ".xls", ExcelHeader.SCORECARD_HEADER, ExcelHeader.SCORECARD_ClASS, list);
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        } catch (SecurityException se) {
            se.printStackTrace();
        } catch (NoSuchFieldException nsfe) {
            nsfe.printStackTrace();
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        } catch (IllegalAccessException iace) {
            iace.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(
            value = {"/upload"},
            method = {RequestMethod.POST, RequestMethod.GET}
    )
    public Response upload(@RequestParam("filename") CommonsMultipartFile file) throws Exception {
        POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet hssfSheet = wb.getSheetAt(0);
        HashMap map = new HashMap();
        if(hssfSheet != null) {
            try {
                ArrayList arrayList = new ArrayList();

                for(int param = 1; param <= hssfSheet.getLastRowNum(); ++param) {
                    HSSFRow hssfRow = hssfSheet.getRow(param);
                    if(hssfRow != null && hssfRow.getCell(0) != null) {
                        Scorecard scorecard = new Scorecard();
                        scorecard.setName(ExcelUtil.formatCell(hssfRow.getCell(0)));
                        scorecard.setCode(ExcelUtil.formatCell(hssfRow.getCell(1)));
                        scorecard.setDesc(ExcelUtil.formatCell(hssfRow.getCell(2)));
                        scorecard.setVersion(ExcelUtil.formatCell(hssfRow.getCell(3)));
                        scorecard.setUserId(Long.valueOf(1L));
                        scorecard.setStatus(Integer.valueOf(1));
                        String fields = ExcelUtil.formatCell(hssfRow.getCell(4));
                        scorecard.setsFieldList(this.handExcelDataForScorecardField(fields));
                        String ruleContents = ExcelUtil.formatCell(hssfRow.getCell(5));
                        scorecard.setsContentList(this.handExcelDataForScorecardContent(ruleContents));
                        arrayList.add(scorecard);
                    }
                }
                HashMap map1 = new HashMap();
                map1.put("newOrganId", Long.valueOf(2L));
                map1.put("newParentId", Long.valueOf(1L));
                map1.put("newType", Integer.valueOf(1));
                scorecardService.batchAddScorecard(map1, arrayList);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("result", "-1");
                return new Response(map);
            }
        }
        map.put("result", "1");
        return new Response(map);
    }

    private List<ScorecardField> handExcelDataForScorecardField(String scorecardFields) {
        ArrayList scorecardFieldList = new ArrayList();
        if(!StringUtil.isBlank(scorecardFields)) {
            String[] field = scorecardFields.trim().split("\n");

            for(int i = 0; i < field.length; ++i) {
                String[] str = field[i].split("-");
                ScorecardField scorecardField = new ScorecardField();
                scorecardField.setFieldId(Long.valueOf(Long.parseLong(str[0])));
                scorecardFieldList.add(scorecardField);
            }
        }

        return scorecardFieldList;
    }

    private List<ScorecardRuleContent> handExcelDataForScorecardContent(String scorecardContents) {
        ArrayList scorecardContentList = new ArrayList();
        if(!StringUtil.isBlank(scorecardContents)) {
            String[] content = scorecardContents.trim().split("\n");

            for(int i = 0; i < content.length; ++i) {
                String[] str = content[i].split("=");
                ScorecardRuleContent ScorecardContent = new ScorecardRuleContent();
                ScorecardContent.setCnName(str[0]);
                ScorecardContent.setFieldValue(str[1]);
                scorecardContentList.add(ScorecardContent);
            }
        }
        return scorecardContentList;
    }


    private Map<String, Object> getParam(Map<String, Object> paramMap) {
        paramMap.put("userId", UserContextUtil.getUserId());
        paramMap.put("organId", UserContextUtil.getOrganId());
        String engineId = "";
        if(paramMap.containsKey("engineId")) {
            engineId = (String)paramMap.get("engineId");
        }
        if(StringUtil.isBlank(engineId)) {
            paramMap.put("type", Integer.valueOf(1));
        } else {
            paramMap.put("type", Integer.valueOf(2));
        }
        return paramMap;
    }

}
