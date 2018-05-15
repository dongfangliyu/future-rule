package com.zw.rule.datamanage.service;

import com.zw.rule.datamanage.po.Field;
import com.zw.rule.datamanage.po.FieldExcelOut;
import com.zw.rule.mybatis.page.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


public interface FieldService {
    /**
     * 保存字段映射
     * @param field
     * @param map
     *  formulaHidden  String  必须 隐藏公式
     *  fieldContent  String  必须 数据内容
     *  userId   Long 必须 用户id
     *  orgId  Long 必须  组织id
     *  fieldEn  String  必须 英文名字
     *  fieldCn  String  必须 中文名字
     * @return
     */
    boolean addField(Field field, Map<String, Object> map);

    /**
     *检查字段是否存在
     * @param paramMap
     * userId  String 用户id 必需
     * fieldEn String 可选
     * fieldCn String 可选
     * engineId String 引擎id 可选
     * @return int
     */
    int isExists(Map<String, Object> paramMap);

    int isExistsFieldType(Map<String, Object> map);

    int isExistsDefaultTreeName(Map<String, Object> map);
    /**
     * 修改字段映射中的字段
     * @param  map 包含以下参数：
     *  formulaHidden String 必须 隐藏公式
     *  fieldContent   String 必须 数据内容
     *  userId   Long 必须 用户id
     *  engineId String  必须  引擎id
     * @return true
     */
    boolean updateField(Map<String, Object> map);

    /**
     * 查询字段类型
     *  @param paramMap
     * userId  String 用户id 必需
     * engineId String 引擎id 可选
     * fieldTypeId String 字段类型 可选
     * @return List<Field>
     */
    List<Field> queryByFieldType(Map<String, Object> paramMap);

    /**
     *
     * @param paramMap
     * userId  String 用户id 必需
     * engineId String 引擎id 非必需
     * isDerivative String 非必需
     * @return List<Field>
     */
    List<Field> queryFieldByIds(Map<String, Object> paramMap);

    /**
     * 通过名字查询
     * @param paramMap
     ** userId  String 用户id 必需
     * engineId String 引擎id 非必需
     * @return List<Field>
     */
    List<Field> queryByName(Map<String, Object> paramMap);

    /**
     *
     * @param paramMap
     * userId  String 用户id 必需
     * @return
     */
    List<Field> findExcelByFieldType(Map<String, Object> paramMap);

    /**
     *通过字段id查询字段
     * @param paramMap
     * userId  String 用户id 必需
     * engineId String 引擎id 非必需
     * @return  Field
     */
    Field findByFieldId(Map<String, Object> paramMap);

    /**
     *
     * @param map
     * fieldEn String  必需
     * organId String 组织id 必需
     * engineId String 引擎id 必需
     * @return
     */
    Field queryByFieldEn(Map<String, Object> map);

    /**
     *查询字段类型id
     * @param paramMap
     * userId  String 用户id 必需
     * engineId String 引擎id 非必需
     * @return Long
     */
    Long findFieldTypeId(Map<String, Object> paramMap);

    /**
     *通过用户查询字段
     * @param paramMap
     * userId  String 用户id 必需
     * engineId String 引擎id 非必需
     * @return  List<Field>
     */
    List<Field> findByUser(Map<String, Object> paramMap);

    /**
     *
     * @param paramMap
     * @return  boolean
     */
    boolean addFormulaField(Map<String, Object> paramMap);

    /**
     * 更新状态
     * @param paramMap
     * Ids String 必需
     * status String 必需
     * @return  Map
     */
    Map<String, Object> updateStatus(Map<String, Object> paramMap);
    /**
     * 检查字段
     * @param map 包含
     *   engineId String  必须 引擎Id
     *  fieldId   String  必须 字段Id
     *  userId   Long    必须 用户id
     * @return paramMap
     */
    Map<String, Object> checkField(Map<String, Object> map);
    /**
     * 获取原始字段
     *  fieldIds  String 必须
     *  fieldId   String  必须  字段Id
     *  userId   Long   必须 用户id
     * @return fieldId or fieldIds
     */
    String querySourceField(String fieldIds, String fieldId, String userId);

    Map<String, Object> checkFieldType(Map<String, Object> map,String userId);

    String queryFieldIdsByTypeIds(Map<String, Object> map);

    String queryOrgFieldIdsByTypeIds(Map<String, Object> map);

    List<String> selectFieldIdsByTypeIds(Map<String, Object> map);

    /**
     *导入excel
     * @param url
     * @param paramMap
     * @return
     */
    boolean importExcel(String url, Map<String, Object> paramMap);

    /**
     *
     * @param paramMap
     * userId  String 用户id 必需
     * @return
     */
    List<Map<String, String>> queryFieldInterByFieldRelId(Map<String, Object> paramMap);

    /**
     *批量创建字段
     * @param paramMap
     * @return
     */
    boolean batchCreateFieldInter(Map<String, Object> paramMap);

    /**
     *删除字段
     * @param paramMap
     * userId  String 用户id 必需
     * fieldRelId String  必需
     * engineId String 引擎id 可选
     * @return
     */
    boolean deleteFieldInter(Map<String, Object> paramMap);

    /**
     *获取字段列表
     * @param paramMap
     * organId String 组织id 必需
     * searchKey String 可选
     * isOutput String 可选
     * engineId String 引擎id 可选
     * valueType String 可选
     * @return
     */
    List<Field> queryFieldList(Map<String, Object> paramMap);

    /**
     * 创建引擎字段
     * @param paramMap
     * item 必需
     * engineId String 引擎id 必需
     * @return
     */
    boolean addEngineField(Map<String, Object> paramMap);

    /**
     *
     * @param paramMap
     * organId String 组织id 必需
     * engineId String 引擎id 必需
     * userId String  用户id 必需
     * @return  boolean
     */
    boolean bindEngineField(Map<String, Object> paramMap);

    /**
     *
     * @param paramMap
     * fieldEn 必需
     * userId String 用户id 必需
     * engineId String 引擎id 必需
     * @return Map
     */
    Map<String, Object> queryFieldResult(Map<String, Object> paramMap);

    Field queryByFieldCn(Map<String, Object> map);
    /**
     * 获取字段
     *  fieldIds String  必须 字段id
     *  usedFieldId  String  必须 用户字段ID
     *  userId  Long 必须 用户id
     *  engineId String  必须 引擎id
     * @return fieldIds or usedFieldId
     */
    String queryField(String fieldIds, String usedFieldId, String engineId, Long userId);

    String queryAllFieldTypeId(String ids, String pid, String engineId, String userId);

    String queryAllParentFieldTypeId(String ids, String id, String engineId, String userId);

    Map<String, Object> deleteNode(Map<String, Object> map,String userId);

    String addEngineTestData(Map<String, Object> map, String userId);

    String addEngineTestResultPdf(Map<String, Object> map);

    String uploadField(List list,Map paramMap);//插入字段表

    List<FieldExcelOut> queryExcelByField(Map<String, Object> paramMap);

    boolean deleteField(int id);
}

