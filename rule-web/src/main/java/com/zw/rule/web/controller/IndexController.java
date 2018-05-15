package com.zw.rule.web.controller;

import com.zw.rule.pojo.MenuTitle;
import com.zw.rule.service.MenuService;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class IndexController {

    @Resource
    private MenuService menuService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        String roleId = (String) UserContextUtil.getAttribute("roleId");
        List<MenuTitle> menuTitleList = menuService.getListByRoleId(roleId);
        modelMap.put("menuList", menuTitleList);
        modelMap.put("roleMap", UserContextUtil.getAttribute("roleMap"));
        return "index";
    }
}
