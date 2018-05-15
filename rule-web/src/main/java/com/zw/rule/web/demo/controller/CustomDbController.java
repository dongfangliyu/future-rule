package com.zw.rule.web.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.demo.pojo.CustomDb;
import com.zw.rule.demo.service.CustomDbService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 自定义名单管理
 * @author zongpeng
 * @Time 2017-6-21
 */
@Controller
@RequestMapping({"CustomDb"})
public class CustomDbController {

    @Resource
    private CustomDbService customDbService;

    /**
     * 获取自定义名单
     */
    @PostMapping({"/list"})
    @ResponseBody
    public Object getList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Long engineId = Long.valueOf(queryFilter.getParam().get("engineId").toString());
        List<CustomDb> list = customDbService.getList(engineId);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    /**
     * 添加自定义名单
     */
    @PostMapping({"/add"})
    @ResponseBody
    public Object add(@RequestBody CustomDb customDb){
        //获取用户id
        Long userId = UserContextUtil.getUserId();
        customDb.setCreateBy(userId);
        customDbService.add(customDb);
        return new Response("添加成功!");
    }

    /**
     * 修改自定义名单
     */
    @PostMapping({"/update"})
    @ResponseBody
    public Object update(@RequestBody CustomDb customDb){
        customDbService.update(customDb);
        return new Response("修改成功!");
    }

    /**
     * 删除名单
     */
    @GetMapping({"/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long id){
        customDbService.delete(id);
        return new Response("删除成功!");
    }

    /**
     * 获取自定义名单详情
     */
    @GetMapping({"/detail"})
    @ResponseBody
    public Object detail(@RequestParam Long id){
        CustomDb customDb = customDbService.detail(id);
        return new Response(customDb);
    }

    /**
     * 查看是否有自定义名单节点
     */
    @GetMapping({"/isCustom"})
    @ResponseBody
    public Object isCustom(@RequestParam Long verId){
        int count = customDbService.isCustom(verId);
        Response response = new Response();
        if(count > 0){
            return response;
        }
        response.setCode(1);
        return response;
    }
}
