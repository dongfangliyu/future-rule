package com.zw.rule.service;


import com.zw.rule.po.Role;
import com.zw.rule.mybatis.ParamFilter;

import java.util.List;

public interface RoleService{

    void add(Role role);

    void delete(List<String> roleIds);

    void update(Role role);

    List<Role> getList(ParamFilter param);

    Role getByRoleId(String roleId);

    List getRoleMap(long userId,Integer organId);

    Role getRoleById(String roleId);
}
