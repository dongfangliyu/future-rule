package com.zw.rule.web.controller;

import com.zw.rule.po.Role;
import com.zw.rule.po.User;
import com.zw.rule.service.RoleService;
import com.zw.rule.service.UserService;
import com.zw.rule.web.util.SystemInfoUtil;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

@Controller
public class HomeController {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @GetMapping("home")
    public String home(ModelMap modelMap) {
        modelMap.put("systemInfo", SystemInfoUtil.getSystemInfo());
        User user = userService.getByUserId(UserContextUtil.getUserId());
        Role role = roleService.getByRoleId(UserContextUtil.getCurrentRoleId());
        modelMap.put("account",user.getAccount());
        modelMap.put("lastLoginIp",user.getLatestIp());
        modelMap.put("role",role.getRoleName());
        return "home";
    }
    @GetMapping("engineHome")
    public String engineHome() {
        return "engineMange/engineList";
    }
}
