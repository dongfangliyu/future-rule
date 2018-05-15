package com.zw.rule.service;


import com.zw.rule.core.Response;
import com.zw.rule.po.Menu;
import com.zw.rule.pojo.MenuTitle;
import com.zw.rule.mybatis.ParamFilter;

import java.util.List;

public interface MenuService {

	void add(Menu menu);

	void delete(List<String> menuIds);

	void update(Menu menu);

	List<MenuTitle> getListByRoleId(String roleId);
	
	List<Menu> getList(ParamFilter param);

	List<Menu> getByParentId(String menuId);
	
	Response getResTree(String roleId);
	
	Response getSelectResTree();

	Menu detail(String menuId);

	Response getTree();

}
