package com.zw.rule.web.datamanage.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.datamanage.po.Field;
import com.zw.rule.datamanage.po.ListDb;
import com.zw.rule.datamanage.service.FieldService;
import com.zw.rule.datamanage.service.ListDbService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * 数据库管理
 * @author zongpeng
 * @Time 2017-5-8
 */
@Controller
@RequestMapping({"datamanage/listmanage"})
public class ListDbController {

    @Resource
    private ListDbService listDbService;

    @Resource
    private FieldService fieldService;

    @RequestMapping({"/white"})
    public String white() {
        return "datamanage/dblist_white";
    }

    @RequestMapping({"/black"})
    public String black() {
        return "datamanage/dblist_black";
    }

    @RequestMapping({"/whiteDetail"})
    public String whiteDetail() {
        return "datamanage/white_details";
    }

    /**
     * 获取黑白名单列表
     * @param   queryFilter 包含一下参数：<br>
     * listType | String | 必需 | 黑白名单类型 黑名单:b,白名单:w <br>
     * status | Integer | 可选 | 名单状态，启用:1，停用:0，删除:-1 <br>
     * firstIndex | Integer | 必需 | 当前页的第一条记录在所有记录中的位置 <br>
     * pageSize | Integer | 必需 | 每页显示数量
     * @return
     * List: 黑白名单列表
     */
    @PostMapping({"/list"})
    @ResponseBody
    public Object queryListInfo(@RequestBody ParamFilter queryFilter) {
        //获取登录用户id
        Long userId = UserContextUtil.getUserId();
        queryFilter.getParam().put("userId", userId);
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        //获取黑白名单列表
        List<ListDb> listDbList = listDbService.queryByUser(queryFilter.getParam());
        //获取查询主键名
        for(int i = 0; i < listDbList.size(); i++){
            String queryFieldCn = "";
            String[] queryField = listDbList.get(i).getQueryField().split(",");
            for(int j = 0; j < queryField.length; j++){
                queryFieldCn += listDbService.getQueryFieldCn(queryField[j]) + ",";
            }
            queryFieldCn = queryFieldCn.substring(0, queryFieldCn.length() - 1);
            listDbList.get(i).setQueryFieldCn(queryFieldCn);
        }
        PageInfo pageInfo = new PageInfo(listDbList);
        Map<String , Object> map = new HashMap<String , Object>();
        map.put("pageInfo",pageInfo);
        map.put("listType",queryFilter.getParam().get("listType"));
        return new Response(map);
    }

    /**
     * 修改黑白名单状态
     * @param   param 包含一下参数：<br>
     * Ids | List | 必需 | 黑白名单id <br>
     * status | Integer | 必需 | 名单状态，启用:1，停用:0，删除:-1
     * @return
     * msg: success
     */
    @PostMapping({"/updateStatus"})
    @WebLogger("修改黑白名单状态")
    @ResponseBody
    public Object updateStatus(@RequestBody Map<String, Object> param) {
        //获取登录用户id
        Long userId = UserContextUtil.getUserId();
        param.put("userId", userId);
        listDbService.updateStatus(param);
        return new Response("操作成功！");
    }

    /**
     * 获取字段列表
     * @param   queryFilter 包含一下参数：<br>
     * firstIndex | Integer | 必需 | 当前页的第一条记录在所有记录中的位置 <br>
     * pageSize | Integer | 必需 | 每页显示数量 <br>
     * engineId | Integer | 可选 | 引擎id
     * @return
     * fieldList: 字段列表
     */
    @PostMapping({"/fieldList"})
    @ResponseBody
    public Object fieldList(@RequestBody ParamFilter queryFilter) {
        //获取登录用户id
        Long userId = UserContextUtil.getUserId();
        //获取登录用户组织id
        Long organId = UserContextUtil.getOrganId();
        queryFilter.getParam().put("userId", userId);
        queryFilter.getParam().put("orgId", organId);
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Integer engineId = null;
        if(queryFilter.getParam().containsKey("engineId")) {
            engineId = Integer.valueOf((String)queryFilter.getParam().get("engineId"));
            queryFilter.getParam().put("engineId", engineId);
        } else {
            queryFilter.getParam().put("engineId", null);
        }
        List<Field> fieldList = fieldService.findByUser(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(fieldList);
        return new Response(pageInfo);
    }

    /**
     * 新增黑白名单
     * @param   param 包含一下参数：<br>
     * status | int | 必需 | 名单状态，启用:1，停用:0，删除:-1 <br>
     * queryType | int | 必需 | 查询字段间逻辑，and:1，or:0 <br>
     * matchType | int | 必需 | 检索匹配类型，精确匹配:1，模糊匹配:0 <br>
     * listDesc | String | 必需 | 名单库描述 <br>
     * listAttribute | String | 必需 | 名单库类型属性 <br>
     * dataSource | int | 必需 | 数据来源：外部黑(白)名单:1、内部黑(白)名单:2、待选:0 <br>
     * listName | String | 必需 | 名单库名称 <br>
     * listType | String | 必需 | 黑白名单类型 黑名单:b,白名单:w <br>
     * maintenance | String | 必需 | 维护字段 <br>
     * queryField | String | 必需 | 查询主键
     * @return
     * msg: success
     */
    @PostMapping({"/add"})
    @WebLogger("新增黑白名单")
    @ResponseBody
    public Object add(@RequestBody Map<String, Object> param) {
        //获取登录用户id
        Long userId = UserContextUtil.getUserId();
        //获取登录用户组织id
        Long organId = UserContextUtil.getOrganId();
        param.put("userId", userId);
        param.put("orgId", organId);
        Response response = new Response();
        //判断名单库名称是否存在
        int count = listDbService.isExists(param);
        if(count > 0){
            response.setCode(1);
            response.setMsg("名单库名称已存在，请重新输入！");
            return response;
        }
        ListDb listDb = new ListDb();
        listDb.setListType((String)param.get("listType"));
        listDb.setMaintenance((String)param.get("maintenance"));
        listDb.setQueryField((String)param.get("queryField"));
        listDb.setStatus(Integer.valueOf(param.get("status").toString()));
        listDb.setQueryType(Integer.valueOf(param.get("queryType").toString()));
        listDb.setMatchType(Integer.valueOf(param.get("matchType").toString()));
        listDb.setListDesc((String)param.get("listDesc"));
        listDb.setListAttribute((String)param.get("listAttribute"));
        listDb.setDataSource(Integer.valueOf(param.get("dataSource").toString()));
        listDb.setListName((String)param.get("listName"));
        listDb.setUserId(userId);
        listDb.setOrgId(organId);
        listDb.setStatus(1);
        listDbService.addListDb(listDb, param);
        response.setMsg("添加成功！");
        return response;
    }

    /**
     * 获取黑白名单详情
     * @param   param 包含一下参数：<br>
     * listType | String | 必需 | 黑白名单类型 黑名单:b,白名单:w <br>
     * id | Long | 必需 | 黑白名单id
     * @return
     * listDb: 黑白名单详情 <br>
     * fieldList: 维护字段信息 <br>
     * queryFieldList: 查询字段信息
     */
    @PostMapping({"/edit"})
    @ResponseBody
    public Object edit(@RequestBody Map<String, Object> param) {
        //获取登录用户id
        Long userId = UserContextUtil.getUserId();
        param.put("userId", userId);
        //获取黑白名单
        ListDb listDb = listDbService.findById(param);
        List fieldList = new ArrayList();
        //获取维护字段信息
        String[] maintenance = listDb.getMaintenance().split(",");
        for(int i = 0; i < maintenance.length; i++ ){
            Map<String , Object> paramMap = new HashMap<String , Object>();
            paramMap.put("userId", userId);
            paramMap.put("id", maintenance[i]);
            Field field = fieldService.findByFieldId(paramMap);
            fieldList.add(field);
        }

        List queryFieldList = new ArrayList();
        //获取查询字段信息
        String[] queryField = listDb.getQueryField().split(",");
        for(int i = 0; i < queryField.length; i++ ){
            Map<String , Object> paramMap = new HashMap<String , Object>();
            paramMap.put("userId", userId);
            paramMap.put("id", queryField[i]);
            Field field = fieldService.findByFieldId(paramMap);
            queryFieldList.add(field);
        }
        Map<String , Object> map = new HashMap<String , Object>();
        map.put("listDb", listDb);
        map.put("listType",param.get("listType"));
        map.put("fieldList", fieldList);
        map.put("queryFieldList", queryFieldList);
        return new Response(map);
    }

    /**
     * 更新黑白名单信息
     * @param   paramMap 包含一下参数：<br>
     * id | Long | 必需 | 黑白名单id <br>
     * listType| String | 必需 | 黑白名单类型 黑名单:b,白名单:w <br>
     * listName | String | 必需 | 黑白名单库名称 <br>
     * dataSource | Integer | 必需 | 数据来源：外部黑(白)名单:1、内部黑(白)名单:2、待选:0 <br>
     * listAttribute | String | 必需 | 名单库类型属性 <br>
     * listDesc | String | 必需 | 名单库描述 <br>
     * maintenance | String | 必需 | 维护字段 <br>
     * matchType | Integer | 必需 | 检索匹配类型，精确匹配:1，模糊匹配:0 <br>
     * queryType | Integer | 必需 | 查询字段间逻辑，and:1，or:0 <br>
     * queryField | String | 必需 | 查询主键
     * @return
     * msg: success
     */
    @PostMapping({"/update"})
    @WebLogger("更新黑白名单信息")
    @ResponseBody
    public Object updateListInfo(@RequestBody Map<String, Object> paramMap) {
        Long userId = UserContextUtil.getUserId();
        paramMap.put("userId", userId);
        Response response = new Response();
        //判断名单库名称是否存在
        int count = listDbService.isExists(paramMap);
        if(count > 0){
            response.setCode(1);
            response.setMsg("名单库名称已存在，请重新输入！");
            return response;
        }
        listDbService.updateListDb(paramMap);
        return new Response("更新成功！");
    }

    /**
     * 获取内、外部黑白名单列表
     * @param   paramMap 包含一下参数：<br>
     * listType | String | 必需 | 黑白名单类型 黑名单:b,白名单:w <br>
     * status | Integer | 可选 | 名单状态，启用:1，停用:0，删除:-1
     * @return
     * insideListDbs: 内部黑白名单列表 <br>
     * outsideListDbs: 外部黑白名单列表
     */
    @PostMapping({"/findListDbByUser"})
    @ResponseBody
    public Object queryListDbByUser(@RequestBody Map<String, Object> paramMap) {
        //获取登录用户id
        Long userId = UserContextUtil.getUserId();
        //获取登录用户组织id
        Long organId = UserContextUtil.getOrganId();
        paramMap.put("userId", userId);
        paramMap.put("orgId", organId);
        if(!paramMap.containsKey("status")) {
            paramMap.put("status", null);
        }
        //获取黑白名单列表
        List<ListDb> listDbs = listDbService.queryByUser(paramMap);
        List insideListDbs = new ArrayList();
        List outsideListDbs = new ArrayList();
        Iterator iterator = listDbs.iterator();
        //分离内外部黑白名单
        while(iterator.hasNext()) {
            ListDb listDb = (ListDb)iterator.next();
            if(listDb.getDataSource() == 1) {
                outsideListDbs.add(listDb);//外部黑白名单
            }else if(listDb.getDataSource() == 2) {
                insideListDbs.add(listDb);//内部黑白名单
            }
        }
        Map<String , Object> map = new HashMap<String , Object>();
        map.put("insideListDbs", insideListDbs);
        map.put("outsideListDbs", outsideListDbs);
        map.put("listType",paramMap.get("listType"));
        return new Response(map);
    }

    /**
     * 根据节点编号获取查询主键
     * @param  paramMap 包含一下参数：<br>
     * nodeId | Long | 必需 | 节点编号
     * @return
     * queryField: 查询主键(以逗号分割)
     */
    @PostMapping({"/getSearchKeyId"})
    @ResponseBody
    public Object querySearchKeyId(@RequestBody Map<String, Object> paramMap) {
        //获取登录用户id
        Long userId = UserContextUtil.getUserId();
        String nodeId = "";
        if(paramMap.containsKey("nodeId")) {
            nodeId = (String)paramMap.get("nodeId");
        }
        //获取查询主键
        String queryField = listDbService.querySearchKeyIdListByNodeId(nodeId , userId);
        return new Response(queryField);
    }
}
