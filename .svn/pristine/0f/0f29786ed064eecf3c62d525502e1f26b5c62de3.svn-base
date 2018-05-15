package com.zw.rule.web.shiro;

import com.google.common.collect.Sets;
import com.zw.base.util.DigestUtil;
import com.zw.rule.po.User;
import com.zw.rule.service.LoginService;
import com.zw.rule.service.UserService;
import com.zw.rule.web.util.ServletUtil;
import com.zw.rule.web.util.UserContextUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private LoginService loginService;


    public UserRealm() {
        setName("UserRealm");
    }

    private PermissionResolver permissionResolver;

    public PermissionResolver getPermissionResolver() {
        return permissionResolver;
    }

    public void setPermissionResolver(PermissionResolver permissionResolver) {
        this.permissionResolver = permissionResolver;
    }

    //权限资源角色
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //add Permission Menu
        info.setStringPermissions(Sets.newHashSet(userService.getPermission(username)));
        //add Roles String[Set<String> roles]
        //info.setRoles(roles);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upt = (UsernamePasswordToken) token;
        String account = upt.getUsername();
        String password = String.valueOf(upt.getPassword());
        User user = loginService.doLogin(account, ServletUtil.getIpAddr()); // 登录日志

        if (user == null) {
            throw new UnknownAccountException("账号不存在");
        }
        if (!user.getIsLock()) {
            throw new LockedAccountException("用户已经被锁定");
        }
        upt.setPassword(DigestUtil.sha256().digest(password).toCharArray());
        // 用info 中的password 比较  token 中的password  密码比较
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(account, user.getPassword(), ByteSource.Util.bytes(account), getName());
        UserContextUtil.setAttribute("currentUser", user);
        //获取所有菜单权限，用于权限校验时，没有存储在权限表中的url的权限不进行判断
        UserContextUtil.setAttribute("allPermissions", Sets.newHashSet(userService.getAllPermission()));
        return info;
    }
}





