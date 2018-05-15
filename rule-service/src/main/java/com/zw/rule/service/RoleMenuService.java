package com.zw.rule.service;


import com.zw.rule.po.RoleMenu;

import java.util.List;

public interface RoleMenuService {

    List<RoleMenu> getList(String roleId);

    void update(String roleId, String[] menuIds);

	List<String> getMenuByRole(String roleId);
}
