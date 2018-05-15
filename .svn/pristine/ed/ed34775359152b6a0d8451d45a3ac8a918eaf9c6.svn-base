package com.zw.rule.web.controller;

import com.zw.rule.core.Response;
import com.zw.rule.po.WebLog;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.mybatis.page.Page;
import com.zw.rule.service.WebLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Controller
@RequestMapping("webLog")
public class WebLogController {

    @Resource
    private WebLogService webLogService;

    @GetMapping("listPage")
    public String list(){
        return "logMange/webLogList";
    }

    @ResponseBody
    @PostMapping("list")
    public Response list(@RequestBody ParamFilter queryFilter){
        List<WebLog> webLogList = webLogService.getList(queryFilter);
        Page page = queryFilter.getPage();
        return new Response(webLogList,page);
    }
}
