package com.zw.rule.web.controller;

import com.zw.rule.core.Response;
import com.zw.rule.po.LoginLog;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.mybatis.page.Page;
import com.zw.rule.service.LoginLogService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Controller
@RequestMapping("loginLog")
public class LoginLogController {

    @Resource
    private LoginLogService loginLogService;

    @GetMapping("listPage")
    public String list(){
        return "logMange/loginLogList";
    }

    @ResponseBody
    @PostMapping("list")
    @WebLogger("查询登录日志列表")
    public Response list(@RequestBody ParamFilter queryFilter){
        List<LoginLog> loginLogList = loginLogService.getList(queryFilter);
        Page page = queryFilter.getPage();
        return new Response(loginLogList,page);
    }
}
