package com.zw.rule.web.controller;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.zw.rule.po.Role;
import com.zw.rule.po.User;
import com.zw.rule.po.UserRole;
import com.zw.rule.service.RoleService;
import com.zw.rule.service.UserRoleService;
import com.zw.rule.web.util.ServletUtil;
import com.zw.rule.web.util.UserContextUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("login")
    public String doLogin(String account, String password, ModelMap modelMap) {
        String msg;
        if (Strings.isNullOrEmpty(account) || Strings.isNullOrEmpty(password)) {
            msg = "帐号或者密码错误";
            ServletUtil.getRequest().setAttribute("msg", msg);
            modelMap.put("msg", msg);
            return "login";
        }
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        Subject subject = SecurityUtils.getSubject();
        ServletUtil.getRequest().setAttribute("account", account);
        try {
            subject.login(token);
            User user = (User) UserContextUtil.getAttribute("currentUser");
            long userId = user.getUserId();
            long organId = user.getOrgId();
            String nickName = user.getNickName();
            Map<String, String> roleMap = Maps.newHashMap();
            List<UserRole> userRoleList = userRoleService.getListByUserId(user.getUserId());
            if (userRoleList == null || userRoleList.size() == 0) {
                throw new UnauthorizedException();
            }
            userRoleList.forEach(userRole -> {
                Role role = roleService.getByRoleId(userRole.getRoleId());
                roleMap.put(userRole.getRoleId(), role.getRoleName());
            });
            if (userRoleList.size() > 0) {
                String roleId = userRoleList.get(0).getRoleId();
                UserContextUtil.setAttribute("roleId", roleId);
            }
            //记录登录信息到上下文
            UserContextUtil.setAttribute("roleMap", roleMap);
            UserContextUtil.setAttribute("userId", userId);
            UserContextUtil.setAttribute("organId", organId);
            UserContextUtil.setAttribute("nickName", nickName);
            UserContextUtil.setAttribute("account", account);
            return "redirect:/index";
        } catch (IncorrectCredentialsException | UnknownAccountException e) {
            msg = "帐号或者密码错误";
            modelMap.put("msg", msg);
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败次数过多";
            modelMap.put("msg", msg);
        } catch (LockedAccountException e) {
            msg = "帐号已被锁定";
            modelMap.put("msg", msg);
        } catch (DisabledAccountException e) {
            msg = "帐号已被禁用";
            modelMap.put("msg", msg);
        } catch (ExpiredCredentialsException e) {
            msg = "帐号已过期";
            modelMap.put("msg", msg);
        } catch (UnauthorizedException e) {
            msg = "帐号未分配角色或权限";
            modelMap.put("msg", msg);
        } catch (Exception e) {
            msg = "系统发生错误，请联系管理员";
            if(e.getClass() == AuthenticationException.class){
                msg = e.getCause().getMessage();
            }
            modelMap.put("msg", msg);
        }
        ServletUtil.getRequest().setAttribute("msg", msg);
        return "login";
    }
}
