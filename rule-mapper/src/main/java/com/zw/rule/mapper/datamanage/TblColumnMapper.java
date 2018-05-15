package com.zw.rule.mapper.datamanage;

import com.zw.rule.datamanage.po.TblColumn;
import com.zw.rule.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/4.
 */
public interface TblColumnMapper extends BaseMapper<TblColumn> {
    List<TblColumn> getColumnList(Map<String, Object> param);
}
