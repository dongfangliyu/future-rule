package com.zw.rule.mapper.datamanage;

import com.zw.rule.datamanage.po.FieldType;
import com.zw.rule.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/4.
 */
public interface FieldTypeMapper extends BaseMapper<FieldType> {
    List<FieldType> queryFieldTypeList(Map<String, Object> var1);

    List<FieldType> getSubFieldTypeList(Map<String, Object> var1);

    FieldType queryFieldTypeById(Map<String, Object> var1);

    String findTypeIdByParentId(Map<String, Object> var1);

    String findParentIdByTypeId(Map<String, Object> var1);

    List<FieldType> queryFieldType(Map<String, Object> var1);

    boolean addFieldType(FieldType var1);

    long findIdByFieldType(Map<String, Object> var1);

    boolean updateFieldType(Map<String, Object> var1);

    boolean deleteFieldType(Map<String, Object> var1);

    boolean updateFieldTypeByTypeIds(Map<String, Object> var1);

    boolean deleteFieldTypeByTypeIds(Map<String, Object> var1);

    boolean backFieldTypeByTypeIds(Map<String, Object> var1);

    int isExists(Map<String, Object> var1);

    int isExistsDefaultTreeName(Map<String, Object> var1);
}
