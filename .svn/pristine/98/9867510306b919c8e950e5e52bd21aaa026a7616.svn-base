package com.zw.rule.web.controller;

import com.google.common.base.Preconditions;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.mybatis.page.Page;
import com.zw.rule.po.Dict;
import com.zw.rule.service.DictService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;


@Controller
@RequestMapping("dict")
public class DictController {

    @Resource
    private DictService dictService;

    @GetMapping("list")
    public String list() {
        return "systemMange/dictList";
    }

    /**
     * 不需要，未用
     * @param queryFilter
     * @return
     */
    @ResponseBody
    @PostMapping("list")
    @WebLogger("查询字典列表")
    public Response list(@RequestBody ParamFilter queryFilter) {
        List<Dict> list = dictService.getList(queryFilter);
        Page page = queryFilter.getPage();
        return new Response(list, page);
    }

    @ResponseBody
    @PostMapping("add")
    @WebLogger("添加字典")
    public Response add(@RequestBody Dict dict) {
        Preconditions.checkNotNull(dict, "不能为空");
        Response response = new Response();
        if (dict.getId() == null) {
            if(dict.getIsCatagory().equals("Y")){
                int count = dictService.isExists(dict);
                if(count > 0){
                    response.setCode(1);
                    response.setMsg("字典名称已存在，请重新输入！");
                    return response;
                }
            }
            dictService.add(dict);
        } else {
            if(dict.getIsCatagory().equals("Y")){
                int count = dictService.isExists(dict);
                if(count > 0){
                    response.setCode(1);
                    response.setMsg("字典名称已存在，请重新输入！");
                    return response;
                }
            }
            dictService.update(dict);
        }
        response.setMsg("添加成功");
        return response;
    }

    @ResponseBody
    @GetMapping("detail")
    @WebLogger("查询字典详细")
    public Response detail(String id) {
        Preconditions.checkNotNull(id, "不能为空");
        Dict dict = dictService.getById(id);
        return new Response(dict);
    }


    @ResponseBody
    @PostMapping("delete")
    @WebLogger("删除字典")
    public Response delete(@RequestBody List<String> ids) {
        checkArgument((ids != null && ids.size() > 0), "不能为空");
        dictService.delete(ids);
        return new Response();
    }

    @ResponseBody
    @GetMapping("getTree")
    @WebLogger("字典树")
    public Response getTree() {
        return new Response(dictService.getTree());
    }

    @ResponseBody
    @GetMapping("getByParentId")
    @WebLogger("查询字典列表")
    public Response getByParentId(String parentId) {
        List<Dict> dictList = dictService.getListByParentId(parentId);
        return new Response(dictList);
    }

    @ResponseBody
    @GetMapping("getCatagory")
    public Response getCatagory() {
        List<Dict> list = dictService.getCatagory();
        Response response = new Response();
        response.setData(list);
        return response;
    }
}
