package com.zw.rule.service;

import com.zw.rule.po.User;
import com.zw.rule.mybatis.ParamFilter;

import java.util.List;
import java.util.Map;

public interface UserService {

    List getList(ParamFilter param);

    void updateDefaultPwd(List<Long> userIds);

    void update(User user);

    void add(User user);

    void updatePwd(String originPwd,String confirmPwd,String newPwd,long userId);

    void delete(List<Long> userIds);

    Map getDetail(long userId);

    User getByUserId(long userId);

    List<String> getPermission(String account);
    //获取数据库中所有在使用的菜单的url
    List<String> getAllPermission();
}
