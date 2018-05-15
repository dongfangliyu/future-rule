package com.zw.rule.mapper.datamanage;

import com.zw.rule.datamanage.po.Field;
import com.zw.rule.datamanage.po.FieldExcelOut;
import com.zw.rule.mapper.common.BaseMapper;
import com.zw.rule.mybatis.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年05月04日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) zw.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:Administrator <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
public interface FieldMapper extends BaseMapper<Field> {
    List<Field> findByFieldType(Map<String, Object> var1);

    String checkField(Map<String, Object> var1);

    String getSourceField(Map<String, Object> var1);

    List<Field> findFieldByIds(Map<String, Object> var1);

    List<Field> findFieldByIdsbyorganId(Map<String, Object> var1);

    String findFieldIdsByTypeIds(Map<String, Object> var1);

    String findFieldTypeIdsByFieldId(Map<String, Object> var1);

    String findOrgFieldTypeIdsByIds(Map<String, Object> var1);

    List<Field> findFieldByIdsForCheckField(Map<String, Object> var1);

    String findOrgFieldIdsByTypeIds(Map<String, Object> var1);

    List<String> selectFieldIdsByTypeIds(Map<String, Object> var1);

    Field findByFieldName(Map<String, Object> var1);

    Field findByFieldEn(Map<String, Object> var1);

    Field findByFieldEnbyorganId(Map<String, Object> var1);

    Field findByFieldCn(Map<String, Object> var1);

    Field findByFieldCnbyorganId(Map<String, Object> var1);

    Field findByFieldCnNoEngineId(Map<String, Object> var1);

    Field findByFieldCnNoEngineIdbyorganId(Map<String, Object> var1);

    List<Field> findByUser(Map<String, Object> var1);

    List<Field> searchByName(Map<String, Object> var1);

    Long findFieldTypeId(Map<String, Object> var1);

    int countByParams(Map<String, Object> var1);

    Field findByFieldId(Map<String, Object> var1);

    Field findByFieldIdbyorganId(Map<String, Object> var1);

    Field findByFieldIdNoEngineId(Map<String, Object> var1);

    boolean createField(Field var1);

    boolean batchCreateField(List<Field> var1);

    boolean updateField(Map<String, Object> var1);

    int isExists(Map<String, Object> var1);

    List<Field> getFieldList(Map<String, Object> var1);

    List<Field> findFieldByIdsAndIsderivative(Map<String, Object> var1);

    List<Field> findExcelByFieldType(Map<String, Object> var1);

    List<FieldExcelOut> findExcelByField(Map<String, Object> map);

    boolean deleteField(@Param("id")int id);
}

