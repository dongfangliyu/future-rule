package com.zw.rule.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.zw.rule.mapper.system.UserDao;
import com.zw.rule.po.User;
import com.zw.rule.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserDao userDao;

    @Override
    public User doLogin(String account, String clientIp) {
        checkArgument(!Strings.isNullOrEmpty(account),"帐号不能为空");
        String accountToUse = account.toUpperCase();
        User user = userDao.findUnique("getByAccount", accountToUse);
        if(user==null){
            //帐号不存在
            throw new RuntimeException("帐号不存在");
        }
        if(!user.getIsLock()){
            //帐号被锁定
            throw new RuntimeException("帐号被禁用");
        }
        long userId = user.getUserId();
        Map<String,Object> updateParam =Maps.newHashMap();
        updateParam.put("latestTime", new Date());
        updateParam.put("latestIp", clientIp);
        updateParam.put("errorCount", 0);
        updateParam.put("userId", userId);
        updateParam.put("account", account);
        userDao.update("updateLoginInfo", updateParam);
        return user;
    }
}
