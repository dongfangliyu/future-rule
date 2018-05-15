package com.zw.rule.service;

import com.zw.rule.po.Dict;
import com.zw.rule.pojo.JSTree;
import com.zw.rule.mybatis.ParamFilter;

import java.util.List;
import java.util.Map;

public interface DictService{

	void add(Dict dict);

	List<Dict> getList(ParamFilter queryFilter);

	void update(Dict dict);

	List<JSTree> getTree();

	List<Dict> getListByParentId(String parentId);

	List<Dict> getCatagory();

	void delete(List<String> id);

	Dict getById(String id);

	int isExists(Dict dict);
}
