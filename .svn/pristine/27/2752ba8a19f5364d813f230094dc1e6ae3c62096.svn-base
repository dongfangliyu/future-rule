package com.zw.rule.datamanage.service;

import com.zw.rule.datamanage.po.FieldType;

import java.util.List;
import java.util.Map;


/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *         规则引擎
 *All rights reserved.
 *
 *Filename：
 *		  FieldTypeServiceImp
 *Description：
 *		  字段的维护
 *Author:
 *		 沈华陶
 *Finished：
 *		2017年5月4日
 ********************************************************/
public interface FieldTypeService {
    /**
     * 通过用户id查询相关FieldType列表（测试正常）;<br>
     * @param  paramMap    包含以下参数：<br>
     *  userId | Long | 必需 | 用户id；<br>
     *  engineId|String| 可选 | 引擎id;<br>
     *  parentId|int| 可选 | 父节点编号;<br>
     *  status|int| 可选 | 启用删除标志;<br>
     * @return List<FieldType>
     */
    List<FieldType> queryFieldTypeList(Map<String, Object> paramMap);
    /**
     * 通过id查询FieldType对象;<br>
     *  @param  paramMap    包含以下参数：<br>
     *  id| int | 必需 | 字段类型编号;<br>
     * @return FieldType
     */
    FieldType queryFieldTypeById(Map<String, Object> paramMap);
    /**
     * 通过用户id查询FieldType对象;<br>
     *  @param  paramMap    包含以下参数：<br>
     *  userId| Long | 必需 | 用户id;<br>
     *  engineId| int | 可选 | 引擎id;<br>
     * @return List<FieldType>
     */
    List<FieldType> queryFieldType(Map<String, Object> paramMap);
    /**
     * 创建FieldType对象存入数据库，同时创建FieldTypeUserRel;<br>
     * @param  fieldTypeVo    包含以下参数：<br>
     *  fieldType | String | 必需 | 用户id；<br>
     *  parentId| int | 必需 | 父节点编号;<br>
     *  isCommon| int| 必需 | 是否组织定义的通用字段类型;<br>
     *  @param  paramMap    包含以下参数：<br>
     *  fieldTypeId| int| 必需 | 字段类型编号;<br>
     *  organId| int| 必需 | 归属的组织编号;<br>
     *  status|int| 必需 | 启用删除标志;<br>
     *  userId | Long | 必需 | 用户id；<br>
     * @return boolean
     */
    boolean addFieldType(FieldType fieldTypeVo, Map<String, Object> paramMap);
    /**
     * 更新FieldType对象存入数据库，同时更新FieldTypeUserRel;<br>
     *  @param  paramMap    包含以下参数：<br>
     *  fieldType | String | 必需 | 用户id；<br>
     *  userId | Long | 必需 | 用户id；<br>
     *  engineId| int | 可选 | 引擎id;<br>
     *  id| int | 可选 | 字段类型编号;<br>
     * @return boolean
     */
    boolean updateFieldType(Map<String, Object> paramMap);

    boolean deleteFieldType(Map<String, Object> paramMap);

    String queryNodeIds(Map<String, Object> paramMap);

}
