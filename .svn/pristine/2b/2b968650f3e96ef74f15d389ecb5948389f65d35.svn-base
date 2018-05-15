package com.zw.rule.datamanage.service.impl;
//

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.zw.base.util.ExcelUtil;
import com.zw.rule.datamanage.po.*;
import com.zw.rule.datamanage.service.FieldService;
import com.zw.rule.engine.po.EngineResultSet;
import com.zw.rule.engine.po.ResultSetList;
import com.zw.rule.jeval.EvaluationException;
import com.zw.rule.jeval.Evaluator;
import com.zw.rule.mapper.datamanage.*;
import com.zw.rule.mapper.engine.EngineResultSetMapper;
import com.zw.rule.mapper.knowledge.RuleMapper;
import com.zw.rule.mapper.knowledge.ScorecardMapper;
import com.zw.rule.util.StringUtil;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FieldServiceImp implements FieldService {

    //   张涛******************************************************************************************************************************
    @Resource
    public FieldCondMapper fieldCondMapper;
    @Resource
    public ListDbMapper listDbMapper;
    @Resource
    public NodeListDbMapper nodeListDbMapper;
    @Resource
    public RuleMapper ruleMapper;
    @Resource
    public ScorecardMapper scorecardMapper;

    /**
     * 去掉"，'转化格式
     *
     * @param usedFieldStr
     * @return
     */
    public StringBuffer getUniqueStr(String usedFieldStr) {
        String[] arrUsedFieldStr = usedFieldStr.split(",");
        HashSet usedFieldSet = new HashSet();

        for (int arrUsedField = 0; arrUsedField < arrUsedFieldStr.length; arrUsedField++) {
            usedFieldSet.add(arrUsedFieldStr[arrUsedField]);
        }

        String[] str = (String[]) usedFieldSet.toArray(new String[usedFieldSet.size()]);
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < str.length; i++) {
            if (i != str.length - 1) {
                sb.append(str[i]).append(",");
            } else {
                sb.append(str[i]);
            }
        }

        return sb;
    }

    /**
     * 保存字段映射
     *
     * @param paramMap 包含以下参数：
     *                 formulaHidden | String | 必须 |隐藏公式
     *                 fieldContent  | String | 必须 |数据内容
     *                 userId  | Long |   必须|用户id
     *                 organId | Long | 必须 |组织id
     *                 fieldEn | String | 必须 |英文名字
     *                 fieldCn | String | 必须 |中文名字
     *                 Field| 实体类
     * @return true
     */
    public boolean addField(Field fieldVo, Map<String, Object> paramMap) {
        String formulaHidden = "";
        JSONArray fieldContent;
        String fieldCondVoList;
        String condList;
        int i;
        JSONObject cond;
        JSONArray subCondList;
        int j;
        JSONObject subCond;
        if (paramMap.containsKey("formulaHidden") && !paramMap.get("formulaHidden").equals("")) {
            formulaHidden = (String) paramMap.get("formulaHidden");
            fieldVo.setFormula(formulaHidden);
            new ArrayList();
            JSONArray array = JSONObject.parseArray(formulaHidden);
            fieldContent = new JSONArray();
            fieldCondVoList = "";
            condList = "";

            for (i = 0; i < array.size(); i++) {
                cond = ((JSONArray) array).getJSONObject(i);
                JSONObject object = new JSONObject();
                object.put("fvalue", cond.getString("fvalue"));
                object.put("formula", cond.getString("formula"));
                object.put("idx", cond.getString("idx"));
                fieldContent.add(object);
                new ArrayList();
                String formula = cond.getString("formula");
                Pattern pat = Pattern.compile("@[a-zA-Z0-9_一-龥()（）-]+@");

                Field field1;
                for (Matcher matc = pat.matcher(formula); matc.find(); condList = condList + field1.getId() + ",") {
                    String fieldCN = matc.group(0).replace("@", "");
                    HashMap fieldMap = new HashMap();
                    fieldMap.put("userId", paramMap.get("userId"));
                    fieldMap.put("engineId", paramMap.get("engineId"));
                    fieldMap.put("cnName", fieldCN);
                    field1 = fieldMapper.findByFieldCn(fieldMap);
                    if (("").equals(field1.getProtogeneFieldId())) {
                        if (fieldCondVoList.equals("")) {
                            fieldCondVoList = Long.toString(field1.getId());
                        } else {
                            fieldCondVoList = fieldCondVoList + "," + field1.getId();
                        }
                    } else if (fieldCondVoList.equals("")) {
                        fieldCondVoList = field1.getProtogeneFieldId();
                    } else {
                        fieldCondVoList = fieldCondVoList + "," + field1.getProtogeneFieldId();
                    }
                }
            }

            fieldVo.setFormulaShow(JSON.toJSONString(fieldContent));
            if (!fieldCondVoList.equals("")) {
                fieldVo.setProtogeneFieldId(getUniqueStr(fieldCondVoList).toString());
            }

            if (!condList.equals(",") && !condList.equals("")) {
                condList = condList.substring(0, condList.length() - 1);
                fieldVo.setDeriveFieldId(getUniqueStr(condList).toString());
            }
        } else if (paramMap.containsKey("fieldContent") && !paramMap.get("fieldContent").equals("")) {
            String fieldUserVo = (String) paramMap.get("fieldContent");
            new ArrayList();
            fieldContent = JSONObject.parseArray(fieldUserVo);
            fieldCondVoList = "";
            condList = "";

            for (i = 0; i < fieldContent.size(); i++) {
                cond = ((JSONArray) fieldContent).getJSONObject(i);
                new ArrayList();
                if (!cond.getString("fieldContent2").equals("") && cond.getString("fieldContent2") != null) {
                    subCondList = JSONObject.parseArray(cond.getString("fieldContent2"));

                    for (j = 0; j < subCondList.size(); j++) {
                        subCond = ((JSONArray) subCondList).getJSONObject(j);
                        condList = condList + subCond.get("fieldId") + ",";
                        HashMap fieldCondVo = new HashMap();
                        fieldCondVo.put("userId", paramMap.get("userId"));
                        fieldCondVo.put("engineId", paramMap.get("engineId"));
                        fieldCondVo.put("id", subCond.get("fieldId"));
                        Field field = fieldMapper.findByFieldId(fieldCondVo);
                        if (("").equals(field.getProtogeneFieldId())) {
                            if (fieldCondVoList.equals("")) {
                                fieldCondVoList = Long.toString(field.getId());
                            } else {
                                fieldCondVoList = fieldCondVoList + "," + field.getId();
                            }
                        } else if (fieldCondVoList.equals("")) {
                            fieldCondVoList = field.getProtogeneFieldId();
                        } else {
                            fieldCondVoList = fieldCondVoList + "," + field.getProtogeneFieldId();
                        }
                    }
                }
            }

            if (!condList.equals(",") && !condList.equals("")) {
                condList = condList.substring(0, condList.length() - 1);
                fieldVo.setDeriveFieldId(getUniqueStr(condList).toString());
            }

            if (!fieldCondVoList.equals("")) {
                fieldVo.setProtogeneFieldId(getUniqueStr(fieldCondVoList).toString());
            }
        }

        if (fieldMapper.isExists(paramMap) == 0) {
            fieldMapper.createField(fieldVo);
            FieldUser fieldUser = new FieldUser();
            fieldUser.setFieldId(fieldVo.getId());
            Long orgId = Long.valueOf(paramMap.get("orgId").toString());
            fieldUser.setOrgId(orgId);
            if (paramMap.get("engineId") != null) {
                fieldUser.setEngineId(Long.valueOf(paramMap.get("engineId").toString()));
            }
            String userId = String.valueOf(paramMap.get("userId"));
            fieldUser.setUserId(userId);
            fieldUser.setStatus(Status.enable.value);
            fieldUserMapper.createFieldUserRel(fieldUser);
            if (paramMap.containsKey("fieldContent")) {
                String fieldCon = (String) paramMap.get("fieldContent");
                if (!fieldCon.equals("")) {
                    ArrayList list = new ArrayList();
                    new ArrayList();
                    JSONArray array = JSONObject.parseArray(fieldCon);

                    for (i = 0; i < array.size(); i++) {
                        cond = ((JSONArray) array).getJSONObject(i);
                        new ArrayList();
                        if (!cond.getString("fieldContent2").equals("")) {
                            subCondList = JSONObject.parseArray(cond.getString("fieldContent2"));

                            for (j = 0; j < subCondList.size(); j++) {
                                subCond = ((JSONArray) subCondList).getJSONObject(j);
                                FieldCond fieldCond = new FieldCond();
                                fieldCond.setFieldId(fieldVo.getId());
                                fieldCond.setConditionValue(cond.getString("conditionValue"));
                                fieldCond.setConditionContent(cond.getString("fieldContent2"));
                                fieldCond.setConditionFieldId(Long.valueOf(subCond.getString("fieldId")));
                                fieldCond.setConditionFieldOperator(subCond.getString("operator"));
                                fieldCond.setConditionFieldValue(subCond.getString("fieldValue"));
                                fieldCond.setConditionFieldLogical(subCond.getString("logical"));
                                list.add(fieldCond);
                            }
                        }
                    }
                    fieldCondMapper.createFieldCond(list);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改字段映射中的字段
     *
     * @param paramMap 包含以下参数：
     *                 formulaHidden | String | 必须 |隐藏公式
     *                 fieldContent  | String | 必须 |数据内容
     *                 userId  | Long |   必须|用户id
     *                 engineId | String | 必须 |引擎id
     * @return true
     */
    public boolean updateField(Map<String, Object> paramMap) {
        String formulaHidden = "";
        JSONArray oldFieldVo;
        String fieldContent;
        String fieldCondVoList;
        int condList;
        JSONObject i;
        JSONObject jsonObject;
        if (paramMap.containsKey("formulaHidden") && !paramMap.get("formulaHidden").equals("") && !paramMap.get("formulaHidden").equals("y")) {
            formulaHidden = (String) paramMap.get("formulaHidden");
            paramMap.put("formula", formulaHidden);
            new ArrayList();
            JSONArray array = JSONObject.parseArray(formulaHidden);
            oldFieldVo = new JSONArray();
            fieldContent = "";
            fieldCondVoList = "";

            for (condList = 0; condList < array.size(); condList++) {
                i = ((JSONArray) array).getJSONObject(condList);
                jsonObject = new JSONObject();
                jsonObject.put("fvalue", i.getString("fvalue"));
                jsonObject.put("formula", i.getString("formula"));
                jsonObject.put("idx", i.getString("idx"));
                oldFieldVo.add(jsonObject);
                new ArrayList();
                String formula = i.getString("formula");
                Pattern pattern = Pattern.compile("@[a-zA-Z0-9_一-龥()（）-]+@");

                Field field;
                for (Matcher matcher = pattern.matcher(formula); matcher.find(); fieldCondVoList = fieldCondVoList + field.getId() + ",") {
                    String fieldCN = matcher.group(0).replace("@", "");
                    HashMap fieldMap = new HashMap();
                    fieldMap.put("userId", paramMap.get("userId"));
                    fieldMap.put("engineId", paramMap.get("engineId"));
                    fieldMap.put("cnName", fieldCN);
                    field = fieldMapper.findByFieldCn(fieldMap);
                    if (("").equals(field.getProtogeneFieldId())) {
                        if (fieldContent.equals("")) {
                            fieldContent = Long.toString(field.getId());
                        } else {
                            fieldContent = fieldContent + "," + field.getId();
                        }
                    } else if (fieldContent.equals("")) {
                        fieldContent = field.getProtogeneFieldId();
                    } else {
                        fieldContent = fieldContent + "," + field.getProtogeneFieldId();
                    }
                }
            }

            paramMap.put("formulaShow", JSON.toJSONString(oldFieldVo));
            if (!fieldContent.equals("")) {
                paramMap.put("protogeneFieldId", getUniqueStr(fieldContent).toString());
            }

            if (!fieldCondVoList.equals(",") && !fieldCondVoList.equals("")) {
                fieldCondVoList = fieldCondVoList.substring(0, fieldCondVoList.length() - 1);
                paramMap.put("deriveFieldId", getUniqueStr(fieldCondVoList).toString());
            }
        } else if (paramMap.containsKey("fieldContent") && !paramMap.get("fieldContent").equals("")) {
            String id = (String) paramMap.get("fieldContent");
            new ArrayList();
            oldFieldVo = JSONObject.parseArray(id);
            fieldContent = "";
            fieldCondVoList = "";

            for (condList = 0; condList < oldFieldVo.size(); condList++) {
                i = ((JSONArray) oldFieldVo).getJSONObject(condList);
                new ArrayList();
                if (!i.getString("fieldContent2").equals("") && i.getString("fieldContent2") != null) {
                    JSONArray cond = JSONObject.parseArray(i.getString("fieldContent2"));

                    for (int subCondList = 0; subCondList < cond.size(); subCondList++) {
                        JSONObject j = ((JSONArray) cond).getJSONObject(subCondList);
                        fieldCondVoList = fieldCondVoList + j.get("fieldId") + ",";
                        HashMap subCond = new HashMap();
                        subCond.put("userId", paramMap.get("userId"));
                        subCond.put("engineId", paramMap.get("engineId"));
                        subCond.put("id", j.get("fieldId"));
                        Field fieldCondVo = fieldMapper.findByFieldId(subCond);
                        if (("").equals(fieldCondVo.getProtogeneFieldId())) {
                            if (fieldContent.equals("")) {
                                fieldContent = Long.toString(fieldCondVo.getId());
                            } else {
                                fieldContent = fieldContent + "," + fieldCondVo.getId();
                            }
                        } else if (fieldContent.equals("")) {
                            fieldContent = fieldCondVo.getProtogeneFieldId();
                        } else {
                            fieldContent = fieldContent + "," + fieldCondVo.getProtogeneFieldId();
                        }
                    }
                }
            }

            if (!fieldCondVoList.equals(",") && !fieldCondVoList.equals("")) {
                fieldCondVoList = fieldCondVoList.substring(0, fieldCondVoList.length() - 1);
                paramMap.put("deriveFieldId", getUniqueStr(fieldCondVoList).toString());
            }

            if (!fieldContent.equals("")) {
                paramMap.put("protogeneFieldId", getUniqueStr(fieldContent).toString());
            }
        }

        Long id = Long.valueOf(String.valueOf(paramMap.get("id")));
        new Field();
        Field field = fieldMapper.findByFieldId(paramMap);
        if (field.getId().equals((Object) null)) {
            return false;
        } else {
            fieldMapper.updateField(paramMap);
            fieldCondMapper.deleteFieldCondById(id);
            fieldContent = (String) paramMap.get("fieldContent");
            ArrayList arrayList = new ArrayList();
            new ArrayList();
            if (!fieldContent.equals("")) {
                JSONArray array = JSONObject.parseArray(fieldContent);

                for (int t = 0; t < array.size(); t++) {
                    jsonObject = ((JSONArray) array).getJSONObject(t);
                    new ArrayList();
                    JSONArray jsonArray = JSONObject.parseArray(jsonObject.getString("fieldContent2"));

                    for (int b = 0; b < jsonArray.size(); b++) {
                        JSONObject jsonObject1 = ((JSONArray) jsonArray).getJSONObject(b);
                        FieldCond fieldCond = new FieldCond();
                        fieldCond.setFieldId(id);
                        fieldCond.setConditionValue(jsonObject.getString("conditionValue"));
                        fieldCond.setConditionContent(jsonObject.getString("fieldContent2"));
                        fieldCond.setConditionFieldId(Long.valueOf(jsonObject1.getString("fieldId")));
                        fieldCond.setConditionFieldOperator(jsonObject1.getString("operator"));
                        fieldCond.setConditionFieldValue(jsonObject1.getString("fieldValue"));
                        fieldCond.setConditionFieldLogical(jsonObject1.getString("logical"));
                        arrayList.add(fieldCond);
                    }
                }

                fieldCondMapper.createFieldCond(arrayList);
            }

            return true;
        }
    }

    /**
     * 获取字段
     * fieldIds | String | 必须 |字段id
     * usedFieldId  | String | 必须 |用户字段ID
     * userId  | Long |   必须|用户id
     * engineId | String | 必须 |引擎id
     *
     * @return fieldIds or usedFieldId
     */
    public String queryField(String fieldIds, String usedFieldId, String engineId, Long userId) {
        HashMap param = new HashMap();
        param.put("userId", userId);
        param.put("fieldId", usedFieldId);
        param.put("engineId", engineId);
        String str = fieldMapper.checkField(param);
        if (str != null && str.length() > 0) {
            String[] arrIds = str.split(",");

            for (int i = 0; i < arrIds.length; i++) {
                if (fieldIds.equals("")) {
                    fieldIds = queryField("", arrIds[i], engineId, userId);
                } else {
                    fieldIds = fieldIds + "," + queryField("", arrIds[i], engineId, userId);
                }
            }

            return fieldIds;
        } else {
            return usedFieldId;
        }
    }

    /**
     * 获取原始字段
     * fieldIds | String | 必须 |
     * fieldId  | String | 必须 |字段Id
     * userId  | Long |   必须|用户id
     *
     * @return fieldId or fieldIds
     */
    public String querySourceField(String fieldIds, String fieldId, String userId) {
        HashMap paramMap = new HashMap();
        paramMap.put("userId", userId);
        paramMap.put("fieldId", fieldId);
        String usedFieldId = fieldMapper.getSourceField(paramMap);//该衍生字段引用的字段id，逗号分割
        if (usedFieldId != null && usedFieldId.length() >= 0) {
            String[] arrIds = usedFieldId.split(",");

            for (int i = 0; i < arrIds.length; i++) {
                if (fieldIds.equals("")) {
                    fieldIds = querySourceField("", arrIds[i], userId);
                } else {
                    fieldIds = fieldIds + "," + querySourceField("", arrIds[i], userId);
                }
            }

            return fieldIds;
        } else {
            return fieldId;
        }
    }

    /**
     * 检查字段
     *
     * @param paramMap 包含
     *                 engineId | String | 不必须 |引擎Id
     *                 fieldId  | String | 必须 |字段Id
     *                 userId  | Long |   必须|用户id
     * @return paramMap
     */
    public Map<String, Object> checkField(Map<String, Object> paramMap) {
        boolean beUsed = false;
        Object fieldList = new ArrayList();
        Object listDbList = new ArrayList();
        new ArrayList();
        new ArrayList();
        new ArrayList();
        String fieldIds;
        String fieldId = String.valueOf(paramMap.get("fieldId"));
        String s = queryField("", fieldId, (String) paramMap.get("engineId"), (Long) paramMap.get("userId"));
        List Ids;
        if (!s.equals("") && !s.equals(fieldId)) {
            fieldIds = getUniqueStr(s).toString();
            new ArrayList();
            Ids = StringUtil.toLongList(fieldIds);
            paramMap.put("Ids", Ids);
            if (!fieldIds.equals("") && fieldIds != null) {
                fieldList = fieldMapper.findFieldByIdsForCheckField(paramMap);
                if (((List) fieldList).size() > 0) {
                    beUsed = true;
                }
            }

            s = fieldId + "," + s;
        } else {
            s = fieldId;
        }

        fieldIds = getUniqueStr(s).toString();
        new ArrayList();
        Ids = StringUtil.toLongList(fieldIds);
        paramMap.put("Ids", Ids);
        String listDbIdStr = "";
        Object listDbIds = new ArrayList();
        Iterator nodeIdStr = Ids.iterator();

        while (nodeIdStr.hasNext()) {
            Long str = (Long) nodeIdStr.next();
            paramMap.put("fieldId", str);
            String nodeIds = listDbMapper.checkByField(paramMap);
            if (nodeIds != null) {
                if (listDbIdStr.equals("")) {
                    listDbIdStr = nodeIds;
                } else {
                    listDbIdStr = listDbIdStr + "," + nodeIds;
                }
            }
        }

        String nodeIdStr1;
        if (!listDbIdStr.equals("") && !listDbIdStr.equals(",")) {
            nodeIdStr1 = getUniqueStr(listDbIdStr).toString();
            listDbIds = StringUtil.toLongList(nodeIdStr1);
            paramMap.put("listDbIds", listDbIds);
            listDbList = listDbMapper.findListDbByIds(paramMap);
        }

        if (((List) listDbList).size() > 0) {
            beUsed = true;
        }

        paramMap.put("fieldIds", Ids);
        List ruleList = ruleMapper.checkByField(paramMap);
        if (ruleList.size() > 0) {
            beUsed = true;
        }

        List scorecardList = scorecardMapper.checkByField(paramMap);
        if (scorecardList.size() > 0) {
            beUsed = true;
        }

        nodeIdStr1 = "";
        Iterator str2 = ((List) listDbIds).iterator();

        while (str2.hasNext()) {
            Long nodeIds1 = (Long) str2.next();
            paramMap.put("listDbId", nodeIds1);
            String str1 = nodeListDbMapper.checkByListDb(paramMap);
            if (str1 != null) {
                if (nodeIdStr1.equals("")) {
                    nodeIdStr1 = str1;
                } else {
                    nodeIdStr1 = nodeIdStr1 + "," + str1;
                }
            }
        }

        Object nodelistDbList = new ArrayList();
        if (!nodeIdStr1.equals("") && !nodeIdStr1.equals(",")) {
            String str3 = getUniqueStr(nodeIdStr1).toString();
            List nodeIds2 = StringUtil.toLongList(str3);
            paramMap.put("nodeIds", nodeIds2);
            nodelistDbList = nodeListDbMapper.findByNodeIds(paramMap);
        }

        if (((List) nodelistDbList).size() > 0) {
            beUsed = true;
        }

        paramMap.put("fieldList", fieldList);
        paramMap.put("listDbList", listDbList);
        paramMap.put("ruleList", ruleList);
        paramMap.put("scorecardList", scorecardList);
        paramMap.put("nodelistDbList", nodelistDbList);
        paramMap.put("beUsed", Boolean.valueOf(beUsed));
        return paramMap;
    }


    //   盛克峰******************************************************************************************************************************
    @Resource
    public FormulaFieldMapper formulaFieldMapper;
    @Resource
    public FieldInterMapper fieldInterMapper;
    @Resource
    public FieldTypeUserMapper fieldTypeUserMapper;

    /**
     * 更新状态
     *
     * @param paramMap Ids String 必需
     *                 status String 必需
     * @return
     */
    public Map<String, Object> updateStatus(Map<String, Object> paramMap) {
        boolean result = false;
        List Ids = (List) paramMap.get("Ids");
        paramMap.put("Ids", Ids);
        if (paramMap.containsKey("status") && !paramMap.get("status").toString().equals("1")) {
            Iterator iterator = Ids.iterator();

            while (iterator.hasNext()) {
                String Id = String.valueOf(iterator.next());
                paramMap.put("fieldId", Id);
                checkField(paramMap);
                if (((Boolean) paramMap.get("beUsed")).booleanValue()) {
                    break;
                }
            }

            if (!((Boolean) paramMap.get("beUsed")).booleanValue()) {
                paramMap.put("Ids", Ids);
                result = fieldUserMapper.updateStatus(paramMap);
            }
        } else if (paramMap.containsKey("listType") && paramMap.get("listType").toString().equals("cabage") && paramMap.get("status").toString().equals("1")) {
            result = backEngFieldType(paramMap);
        } else {
            result = fieldUserMapper.updateStatus(paramMap);
        }

        paramMap.put("result", Boolean.valueOf(result));
        return paramMap;
    }

    /**
     * @param paramMap userId  String 用户id 必需
     *                 engineId String 引擎id 非必需
     * @return
     */
    public List<Field> queryByFieldType(Map<String, Object> paramMap) {
        return fieldMapper.findByFieldType(paramMap);
    }

    /**
     * @param paramMap userId  String 用户id 必需
     * @return
     */
    public int isExists(Map<String, Object> paramMap) {
        return fieldMapper.isExists(paramMap);
    }

    @Override
    public boolean deleteField(int id) {
        return fieldMapper.deleteField(id);
    }

    /**
     * @param paramMap
     * @return
     */
    public boolean addFormulaField(Map<String, Object> paramMap) {
        return formulaFieldMapper.createFormulaField(paramMap);
    }

    /**
     * @param paramMap userId  String 用户id 必需
     *                 engineId String 引擎id 非必需
     *                 id int 字段编号 必需
     * @return Field
     */
    public Field findByFieldId(Map<String, Object> paramMap) {
        return fieldMapper.findByFieldId(paramMap);
    }

    /**
     * @param paramMap userId  String 用户id 必需
     *                 engineId String 引擎id 非必需
     *                 searchKey String 查询关键字 非必需
     *                 fieldId long 字段编号 非必需
     * @return
     */
    public List<Field> findByUser(Map<String, Object> paramMap) {
        return fieldMapper.findByUser(paramMap);
    }

    /**
     * @param paramMap userId  String 用户id 必需
     *                 item id的集合 必需
     *                 engineId int 引擎id 非必需
     * @return
     */
    public Long findFieldTypeId(Map<String, Object> paramMap) {
        return fieldMapper.findFieldTypeId(paramMap);
    }

    /**
     * @param paramMap userId  String 用户id 必需
     *                 searchKey String 查询关键字 非必需
     *                 fieldTypeId int 字段类型编号 非必需
     *                 engineId int 引擎id 非必需
     * @return
     */
    public List<Field> findExcelByFieldType(Map<String, Object> paramMap) {
        return fieldMapper.findExcelByFieldType(paramMap);
    }

    /**
     * @param paramMap userId  String 用户id 必需
     *                 engineId int 引擎id 非必需
     *                 fieldRelId int 衍生字段编号 非必需
     *                 seq int 衍生字段公式里用到同名字段的顺序,从0开始对应计算器中自左向右 非必需
     * @return
     */
    public List<Map<String, String>> queryFieldInterByFieldRelId(Map<String, Object> paramMap) {
        return fieldInterMapper.findByFieldRelId(paramMap);
    }

    /**
     * 未通（mapper里少方法）
     * userId  String 用户id 必需
     *
     * @param paramMap
     * @return
     */
    public boolean batchCreateFieldInter(Map<String, Object> paramMap) {
        return fieldInterMapper.batchCreateInter(paramMap);
    }

    /**
     * userId  String 用户id 必需
     *
     * @param paramMap
     * @return
     */
    public boolean deleteFieldInter(Map<String, Object> paramMap) {
        return fieldInterMapper.deleteInter(paramMap);
    }

    /**
     * @param paramMap userId  String 用户id 必需
     *                 engineId int 引擎id 非必需
     *                 searchKey String 查询关键字
     * @return
     */
    public List<Field> queryFieldList(Map<String, Object> paramMap) {
        return fieldMapper.getFieldList(paramMap);
    }

    /**
     * userId  String 用户id 必需
     *
     * @param paramMap
     * @return
     */
    public List<Field> queryByName(Map<String, Object> paramMap) {
        return fieldMapper.searchByName(paramMap);
    }

    /**
     * @param paramMap userId  long 用户id 必需
     *                 engineId String 引擎id 可选
     *                 isDerivative String 可选
     *                 item List id的集合 必需
     * @return
     */
    public List<Field> queryFieldByIds(Map<String, Object> paramMap) {
        return fieldMapper.findFieldByIds(paramMap);
    }

    /**
     * 通过英文查询字段信息
     *
     * @param paramMap userId  long 用户id 必需
     *                 fieldEn String 字段英文名称 必需
     *                 engineId String 引擎id 必需
     * @return Field
     */
    public Field queryByFieldEn(Map<String, Object> paramMap) {
        return fieldMapper.findByFieldEn(paramMap);
    }

    /**
     * userId  String 用户id 必需
     *
     * @param paramMap item
     *                 engineId String 引擎id 非必需
     *                 organId int 组织id 非必需
     *                 status
     * @return
     */
    public boolean addEngineField(Map<String, Object> paramMap) {
        fieldTypeUserMapper.batchBindEngineFieldTypeUserRel(paramMap);
        fieldUserMapper.batchCreateEngineFieldUserRel(paramMap);
        return true;
    }

    /**
     * 绑定引擎字段
     * userId  String 用户id 必需
     * fieldIds String
     *
     * @param paramMap
     * @return
     */
    public boolean bindEngineField(Map<String, Object> paramMap) {
        List<Integer> fieldTypeIds = fieldTypeUserMapper.selectEngineFieldTypeRel(paramMap);
        if (fieldTypeIds.size() > 0) {
            paramMap.put("fieldTypeIdsCom",fieldTypeIds);
            boolean flag1 = fieldTypeUserMapper.deleteEngineFieldTypeRel(paramMap);
        }
        fieldTypeUserMapper.engineFieldTypeUserRel(paramMap);

        List<Integer> fieldUserIds = fieldUserMapper.selectEngineFieldUserRel(paramMap);
        if (fieldUserIds.size() > 0) {
            paramMap.put("fieldUserIdsCom",fieldUserIds);
            boolean flag2 = fieldUserMapper.deleteEngineFieldUserRel(paramMap);
        }
        fieldUserMapper.engineFieldUserRel(paramMap);


//        String userId = paramMap.get("userId").toString();
//        String iFieldIds = paramMap.get("fieldIds").toString();
//        String oFieldIds = iFieldIds;
//        if(iFieldIds != null && iFieldIds.length() >= 0) {
//            String[] strFieldIds = iFieldIds.split(",");
//
//            for(int strFieldTypeIds = 0; strFieldTypeIds < strFieldIds.length; strFieldTypeIds++) {
//                String tem = querySourceField("", strFieldIds[strFieldTypeIds],userId);
//                if(!StringUtil.isBlank(tem)){
//                    oFieldIds = oFieldIds + "," + tem;
//                }
//            }
//        }
//
//        String uniqueStr = getUniqueStr(oFieldIds).toString();
//        if(!uniqueStr.equals("") && uniqueStr != null) {
//            List list = StringUtil.toLongList(uniqueStr);
//            paramMap.put("fieldIds", list);
//            fieldUserMapper.batchBindEngineFieldUserRel(paramMap);
//        }
//
//        String orgFieldTypeIds = fieldMapper.findOrgFieldTypeIdsByIds(paramMap);
//        if(!orgFieldTypeIds.equals("") && orgFieldTypeIds != null) {
//            String parentFieldTypeIds = "";
//            if(!orgFieldTypeIds.equals("")) {
//                orgFieldTypeIds = getUniqueStr(orgFieldTypeIds).toString();
//                String[] fieldTypeIds = orgFieldTypeIds.split(",");
//
//                for(int i = 0; i < fieldTypeIds.length; i++) {
//                    if(parentFieldTypeIds.equals("")) {
//                        parentFieldTypeIds = queryAllParentFieldTypeId("", fieldTypeIds[i], "",userId);
//                    } else {
//                         parentFieldTypeIds = parentFieldTypeIds + "," + queryAllParentFieldTypeId("", fieldTypeIds[i], "",userId);
//                    }
//                }
//            }
//
//            if(!parentFieldTypeIds.equals("")) {
//                orgFieldTypeIds = orgFieldTypeIds + "," + parentFieldTypeIds;
//            }
//
//            List fieldTypeIds = StringUtil.toLongList(orgFieldTypeIds);
//            paramMap.put("fieldTypeIds", fieldTypeIds);
//            fieldTypeUserMapper.batchBindEngineFieldTypeUserRel(paramMap);
//        }

        return true;
    }

    /**
     * userId  String 用户id 必需
     *
     * @param paramMap
     * @return
     */
    private String calcFieldCond(Map<String, Object> paramMap) {
        String fieldValue = (String) paramMap.get("fieldValue");
        Integer fieldValueType = (Integer) paramMap.get("fieldValueType");
        String result = "";
        JSONArray jsonArr = (JSONArray) paramMap.get("fieldCond");
        Iterator iterator = jsonArr.iterator();

        while (iterator.hasNext()) {
            JSONObject job = (JSONObject) iterator.next();
            String inputOne = (String) job.get("inputOne");
            String inputThree = (String) job.get("inputThree");
            if (fieldValueType.intValue() == 3) {
                if (fieldValue.equals(inputOne)) {
                    result = inputThree;
                    break;
                }
            } else if (fieldValueType.intValue() == 1 || fieldValueType.intValue() == 4) {
                Double lv = Double.valueOf(inputOne.substring(1, inputOne.indexOf(",")));
                Double rv = Double.valueOf(inputOne.substring(inputOne.indexOf(",") + 1, inputOne.length() - 1));
                String exp = "";
                if (inputOne.startsWith("(") && !lv.equals("")) {
                    exp = fieldValue + ">" + lv;
                }

                if (inputOne.startsWith("[") && !lv.equals("")) {
                    exp = fieldValue + ">=" + lv;
                }

                if (inputOne.endsWith(")") && !rv.equals("")) {
                    if (exp.equals("")) {
                        exp = exp + fieldValue + "<" + rv;
                    } else {
                        exp = exp + "&&" + fieldValue + "<" + rv;
                    }
                }

                if (inputOne.endsWith("]") && !rv.equals("")) {
                    if (exp.equals("")) {
                        exp = exp + fieldValue + "<=" + rv;
                    } else {
                        exp = exp + "&&" + fieldValue + "<=" + rv;
                    }
                }

                Evaluator evaluator = new Evaluator();
                String b = "";

                try {
                    b = evaluator.evaluate(exp);
                } catch (EvaluationException var16) {
                    var16.printStackTrace();
                }

                if (b.equals("1.0")) {
                    result = inputThree;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * @param fieldCn
     * @param exp
     * @param param
     * @return
     */
    private String getExpAll(String fieldCn, String exp, Map<String, Object> param) {
        String result = "";
        HashMap param2 = new HashMap();
        Iterator organId = param.entrySet().iterator();

        while (organId.hasNext()) {
            Map.Entry userId = (Map.Entry) organId.next();
            if (userId.getValue() != null) {
                param2.put((String) userId.getKey(), userId.getValue());
            } else {
                param2.put((String) userId.getKey(), "");
            }
        }

        HashMap paramMap = new HashMap();
        paramMap.put("engineId", param.get("engineId"));
        paramMap.put("fieldCn", fieldCn);
        String arrFormula = "";
        Field engField = fieldMapper.findByFieldCn(paramMap);
        String engFormula = engField.getFormula();
        if (!engFormula.equals("") && engFormula != null) {
            arrFormula = engFormula;
        } else {
            Field formulaList = fieldMapper.findByFieldCnNoEngineId(paramMap);
            String i = formulaList.getFormula();
            if (!formulaList.equals("") && formulaList != null) {
                arrFormula = i;
            }
        }

        String b;
        String v;
        if (!arrFormula.equals("") && arrFormula != null) {
            new ArrayList();
            JSONArray var33 = JSONObject.parseArray(arrFormula);

            for (int var35 = 0; var35 < var33.size(); var35++) {
                JSONObject var37 = ((JSONArray) var33).getJSONObject(var35);
                String var38 = (String) var37.get("formula");
                var38 = var38.replace("&gt;", ">");
                var38 = var38.replace("&lt;", "<");
                Pattern var39 = Pattern.compile("@[a-zA-Z0-9_一-龥()（）-]+@");
                Matcher var40 = var39.matcher(var38);
                String var42 = var38;
                int var44 = 0;

                for (exp = ""; var40.find(); var44 = var40.end() + 1) {
                    String var45 = var40.group(0).replace("@", "");
                    HashMap var47 = new HashMap();
                    var47.put("userId", paramMap.get("userId"));
                    var47.put("engineId", paramMap.get("engineId"));
                    var47.put("fieldCn", var45);
                    Field var48 = fieldMapper.findByFieldCn(var47);
                    if (var48 == null) {
                        var48 = fieldMapper.findByFieldCnNoEngineId(var47);
                    }

                    HashMap var49 = new HashMap();
                    var49.put("fieldValue", param2.get(var48.getEnName()));
                    var49.put("fieldEn", var48.getEnName());
                    var49.put("fieldValueType", var48.getValueType());
                    JSONArray var50 = new JSONArray();
                    JSONArray var51 = (JSONArray) var37.get("farr");
                    Iterator var52 = var51.iterator();

                    while (var52.hasNext()) {
                        JSONObject var53 = (JSONObject) var52.next();
                        if (var53.get("fieldCN").equals(var45) && !var53.get("fieldCond").equals("")) {
                            var50 = (JSONArray) var53.get("fieldCond");
                            break;
                        }
                    }

                    var49.put("fieldCond", var50);
                    v = "";
                    if (var50.size() > 0) {
                        v = calcFieldCond(var49);
                    } else {
                        v = (String) param2.get(var48.getEnName());
                    }

                    if (var48.getIsDerivative() == 0) {
                        if (var42.indexOf("substring") >= 0) {
                            exp = exp + var42.substring(var44, var40.end()).replace("@" + var45 + "@", "\'" + v + "\'");
                        } else {
                            exp = exp + var42.substring(var44, var40.end()).replace("@" + var45 + "@", v);
                        }
                    } else if (var48.getValueType() != 1 && var48.getValueType() != 4) {
                        exp = exp + var42.substring(var44, var40.end()).replace("@" + var45 + "@", getExpAll(var45, "", param2));
                    } else {
                        exp = exp + var42.substring(var44, var40.end()).replace("@" + var45 + "@", getExpAll(var45, exp, param2));
                    }
                }

                exp = exp + var38.substring(var44, var38.length());
                Evaluator var46 = new Evaluator();
                b = "";

                try {
                    System.out.println("========字段公式编辑设置的表达式输出：" + exp);
                    b = var46.evaluate(exp);
                } catch (EvaluationException var28) {
                    var28.printStackTrace();
                }

                if (b.equals("1.0")) {
                    result = (String) var37.get("fvalue");
                    if (isNumeric(result)) {
                        result = "(" + result + ")";
                    } else {
                        result = "\'" + result + "\'";
                    }
                    break;
                }

                if (!b.equals("1.0") && !b.equals("0.0") && !b.equals("")) {
                    result = b;
                }
            }
        } else {
            new ArrayList();
            List var34 = fieldMapper.findByFieldCn(paramMap).getFieldCondList();
            List var32;
            if (var34.size() > 0) {
                var32 = var34;
            } else {
                List formulaJson = fieldMapper.findByFieldCnNoEngineId(paramMap).getFieldCondList();
                var32 = formulaJson;
            }

            if (var32.size() > 0) {
                Iterator formula = var32.iterator();

                while (formula.hasNext()) {
                    FieldCond var36 = (FieldCond) formula.next();
                    String pattern = var36.getConditionValue();
                    new ArrayList();
                    JSONArray matcher = JSONObject.parseArray(var36.getConditionContent());
                    exp = "";

                    for (int subexp = 0; subexp < matcher.size(); subexp++) {
                        JSONObject j = ((JSONArray) matcher).getJSONObject(subexp);
                        paramMap.put("id", j.getString("fieldId"));
                        Field evaluator = fieldMapper.findByFieldId(paramMap);
                        if (evaluator == null) {
                            evaluator = fieldMapper.findByFieldCnNoEngineId(paramMap);
                        }

                        b = evaluator.getEnName();
                        String e = evaluator.getCnName();
                        Integer paramCond = evaluator.getValueType();
                        String fieldCond = j.getString("fieldValue");
                        String jsonArr = j.getString("operator");
                        v = (String) param2.get(b);
                        String job = "";
                        if (evaluator.getIsDerivative() == 0) {
                            if (j.containsKey("logical")) {
                                job = " " + j.getString("logical") + " ";
                            }

                            if (jsonArr.equals("in")) {
                                exp = exp + "(indexOf(\'" + fieldCond + "\',\'" + v + "\',0) >= 0)" + job;
                            } else if (jsonArr.equals("not in")) {
                                exp = exp + "(indexOf(\'" + fieldCond + "\',\'" + v + "\',0) = -1)" + job;
                            } else if (jsonArr.equals("like")) {
                                exp = exp + "(indexOf(\'" + v + "\',\'" + fieldCond + "\',0) >= 0)" + job;
                            } else if (jsonArr.equals("not like")) {
                                exp = exp + "(indexOf(\'" + v + "\',\'" + fieldCond + "\',0) = -1)" + job;
                            } else if (paramCond != 1 && paramCond != 4) {
                                exp = exp + " (\'" + v + "\'" + jsonArr + "\'" + fieldCond + "\') " + job;
                            } else {
                                exp = exp + " (" + v + jsonArr + fieldCond + ") " + job;
                            }
                        } else {
                            if (j.containsKey("logical")) {
                                job = " " + j.getString("logical") + " ";
                            }

                            if (jsonArr.equals("in")) {
                                exp = exp + "(indexOf(\'" + fieldCond + "\',\'" + getExpAll(e, "", param2) + "\',0) >= 0)" + job;
                            } else if (jsonArr.equals("not in")) {
                                exp = exp + "(indexOf(\'" + fieldCond + "\',\'" + getExpAll(e, "", param2) + "\',0) = -1)" + job;
                            } else if (jsonArr.equals("like")) {
                                exp = exp + "(indexOf(\'" + getExpAll(e, "", param2) + "\',\'" + fieldCond + "\',0) >= 0)" + job;
                            } else if (jsonArr.equals("not like")) {
                                exp = exp + "(indexOf(\'" + getExpAll(e, "", param2) + "\',\'" + fieldCond + "\',0) = -1)" + job;
                            } else if (paramCond != 1 && paramCond != 4) {
                                exp = exp + " (\'" + getExpAll(e, "", param2) + "\'" + jsonArr + "\'" + fieldCond + "\') " + job;
                            } else {
                                exp = exp + " (" + getExpAll(e, "", param2) + jsonArr + fieldCond + ") " + job;
                            }
                        }
                    }

                    Evaluator var41 = new Evaluator();
                    String var43 = "";

                    try {
                        System.out.println("========字段区域设置的的表达式输出：" + exp);
                        var43 = var41.evaluate(exp);
                    } catch (EvaluationException var29) {
                        var29.printStackTrace();
                    }

                    if (var43.equals("1.0")) {
                        result = pattern;
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * @param str
     * @return
     */
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^(-|\\+)?\\d+(\\.\\d+)?$");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 无测试数据（传参不明确）
     * 具体用途不明确
     *
     * @param paramMap userId  String 用户id 必需
     * @return
     */
    public Map<String, Object> queryFieldResult(Map<String, Object> paramMap) {
        Map paramMap2 = new HashMap();
        Iterator organId = paramMap.entrySet().iterator();

        while (organId.hasNext()) {
            Map.Entry userId = (Map.Entry) organId.next();
            paramMap2.put((String) userId.getKey(), userId.getValue().toString());
        }

        paramMap2.put("userId", paramMap.get("userId"));
        paramMap2.put("organId", paramMap.get("organId"));
        Iterator var6 = paramMap.entrySet().iterator();

        while (var6.hasNext()) {
            Map.Entry entry = (Map.Entry) var6.next();
            String fieldEn = (String) entry.getKey();
            String fieldValue = entry.getValue().toString();
            if (fieldValue.equals("")) {
                paramMap2.put("fieldEn", fieldEn);
                Field field = fieldMapper.findByFieldEn(paramMap2);
                if (field != null && field.getIsDerivative() == 1) {
                    String result = "";
                    paramMap2.put("fieldEn", fieldEn);
                    paramMap2.put("fieldCn", fieldMapper.findByFieldEn(paramMap2).getCnName());
                    result = getExpAll(fieldMapper.findByFieldEn(paramMap2).getCnName(), "", paramMap);
                    result = result.replace("(", "");
                    result = result.replace(")", "");
                    paramMap.put(fieldEn, result);
                }
            }
        }

        return paramMap;
    }

    /**
     * @param url
     * @param paramMap
     * @return
     */
    public boolean importExcel(String url, Map<String, Object> paramMap) {
        FileInputStream is = null;
        Workbook Workbook = null;

        try {
            is = new FileInputStream(url);
            Workbook = WorkbookFactory.create(is);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (InvalidFormatException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        } catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e4) {//2017/5/5
            e4.printStackTrace();
        }

        ArrayList fieldVoList = new ArrayList();

        for (int numSheet = 0; numSheet < Workbook.getNumberOfSheets(); numSheet++) {
            Sheet Sheet = Workbook.getSheetAt(numSheet);
            if (Sheet != null) {
                for (int rowNum = 1; rowNum <= Sheet.getLastRowNum(); rowNum++) {
                    Row Row = Sheet.getRow(rowNum);
                    if (Row != null) {
                        Field fieldVo = new Field();
                        fieldVo.setCreator(paramMap.get("author").toString());//
                        fieldVo.setIsCommon(Integer.valueOf(paramMap.get("isCommon").toString()));

                        for (int OldFieldVo = 0; OldFieldVo <= Row.getLastCellNum(); OldFieldVo++) {
                            Cell cell = Row.getCell(OldFieldVo);
                            String cellStr = ExcelUtil.getCellValue(cell).trim();
                            switch (OldFieldVo) {
                                case 0:
                                    fieldVo.setEnName(cellStr);
                                    break;
                                case 1:
                                    fieldVo.setCnName(cellStr);
                                    break;
                                case 2:
                                    paramMap.put("fieldType", cellStr);
                                    Long fieldTypeId = Long.valueOf(fieldTypeMapper.findIdByFieldType(paramMap));
                                    if (fieldTypeId != 0L) {
                                        fieldVo.setFieldTypeId(fieldTypeId);
                                    } else {
                                        fieldVo.setFieldTypeId(new Long(0L));
                                    }
                                    break;
                                case 3:
                                    Integer valueType = Integer.valueOf(0);
                                    if (cellStr.equals("数值型")) {
                                        valueType = Integer.valueOf(1);
                                    }

                                    if (cellStr.equals("字符型")) {
                                        valueType = Integer.valueOf(2);
                                    }

                                    if (cellStr.equals("枚举型")) {
                                        valueType = Integer.valueOf(3);
                                    }

                                    if (cellStr.equals("小数型")) {
                                        valueType = Integer.valueOf(4);
                                    }

                                    fieldVo.setValueType(valueType);
                                    break;
                                case 4:
                                    fieldVo.setRestrainScope(cellStr);
                                    break;
                                case 5:
                                    if (ExcelUtil.getCellValue(cell).equals("Y")) {
                                        fieldVo.setIsDerivative(Integer.valueOf(1));
                                    } else {
                                        fieldVo.setIsDerivative(Integer.valueOf(0));
                                    }
                                    break;
                                case 6:
                                    if (cellStr.equals("Y")) {
                                        fieldVo.setIsOutput(Integer.valueOf(1));
                                    } else if (cellStr.equals("N")) {
                                        fieldVo.setIsOutput(Integer.valueOf(0));
                                    }
                                    break;
                                case 7:
                                    fieldVo.setFormula(cellStr);
                            }
                        }

                        if (fieldVo.getEnName() != null) {
                            paramMap.put("fieldEn", fieldVo.getEnName());
                            Field var19 = fieldMapper.findByFieldName(paramMap);
                            if (var19 != null) {
                                fieldVo.setId(var19.getId());
                                fieldMapper.updateField(paramMap);
                            } else {
                                fieldVoList.add(fieldVo);
                            }
                        }
                    }
                }
            }
        }

        if (fieldVoList.size() > 0) {
            fieldMapper.batchCreateField(fieldVoList);
            paramMap.put("status", Integer.valueOf(1));
            fieldUserMapper.batchCreateFieldUserRel(paramMap);
        }

        return true;
    }

//   牛登峰******************************************************************************************************************************

    @Resource
    public FieldMapper fieldMapper;
    @Resource
    public FieldTypeMapper fieldTypeMapper;

    /**
     * 根据中文名称去查询
     *
     * @param paramMap 包含以下参数：
     *                 fieldCn | String | 不是必须 |字段中文名
     *                 userId  | String |   不是必须|用户id
     *                 engineId | String | 不是必须 |引擎id
     * @return Feild
     */
    public Field queryByFieldCn(Map<String, Object> paramMap) {
        return fieldMapper.findByFieldCn(paramMap);
    }

    /**
     * @param ids      | String |必须
     * @param pid      | String | 必须
     * @param engineId |String |必须|引擎id
     * @return 返回字段类型id
     */
    public String queryAllFieldTypeId(String ids, String pid, String engineId, String userId) {
        HashMap param = new HashMap();
        param.put("userId", userId);
        param.put("engineId", engineId);
        param.put("parentId", pid);
        String sid = fieldTypeMapper.findTypeIdByParentId(param);
        if (sid != null && sid.length() > 0) {
            if (ids.equals("")) {
                ids = sid;
            } else {
                ids = ids + "," + sid;
            }

            String[] arrIds = sid.split(",");

            for (int i = 0; i < arrIds.length; i++) {
                String str = queryAllFieldTypeId("", arrIds[i], engineId, userId);
                if (!str.equals("")) {
                    ids = ids + "," + str;
                }
            }
        }

        return ids;
    }

    /**
     * @param ids      String 必须
     * @param id       String  必须  字段类型id
     * @param engineId String 必须 引擎id
     * @return 返回id
     */
    public String queryAllParentFieldTypeId(String ids, String id, String engineId, String userId) {
        HashMap param = new HashMap();
        param.put("userId", userId);
        if (engineId == null || engineId.equals("")) {
            engineId = null;
        }

        param.put("engineId", engineId);
        param.put("fieldTypeId", id);
        String pid = fieldTypeMapper.findParentIdByTypeId(param);
        if (!pid.equals("0")) {
            ids = id + "," + queryAllParentFieldTypeId("", pid, engineId, userId);
            return ids;
        } else {
            return id;
        }
    }

    /**
     * param
     * pid String 必须
     * engineId String 必须 引擎id
     * userId 必须 String 用户id
     *
     * @return 返回map
     */
    public Map<String, Object> checkFieldType(Map<String, Object> paramMap, String userId) {
        String pid = (String) paramMap.get("pid");
        String engineId = (String) paramMap.get("engineId");
        String strfieldTypeIds = queryAllFieldTypeId("", pid, engineId, userId);
        if (strfieldTypeIds != null && strfieldTypeIds.length() > 0) {
            strfieldTypeIds = pid + "," + strfieldTypeIds;
        } else {
            strfieldTypeIds = pid;
        }

        List fieldTypeIds = StringUtil.toLongList(strfieldTypeIds);
        paramMap.put("fieldTypeIds", fieldTypeIds);
        String fieldIds = "";
        fieldIds = fieldMapper.findFieldIdsByTypeIds(paramMap);
        if (fieldIds != null && fieldIds != "") {
            String[] arrIds = fieldIds.split(",");

            for (int i = 0; i < arrIds.length; i++) {
                paramMap.put("fieldId", arrIds[i]);
                checkField(paramMap);
                if (((Boolean) paramMap.get("beUsed")).booleanValue()) {
                    break;
                }
            }
        }

        paramMap.put("fieldTypeIds", strfieldTypeIds);
        paramMap.put("fieldIds", fieldIds);
        return paramMap;
    }

    @Resource
    public FieldUserMapper fieldUserMapper;

    public Map<String, Object> deleteNode(@RequestParam Map<String, Object> paramMap, String userId) {
        paramMap.put("userId", userId);
        String strFieldIds = "";
        strFieldIds = (String) paramMap.get("fieldIds");
        List fieldIds = StringUtil.toLongList(strFieldIds);
        paramMap.put("fieldIds", fieldIds);
        List fieldTypeIds = StringUtil.toLongList((String) paramMap.get("fieldTypeIds"));
        paramMap.put("fieldTypeIds", fieldTypeIds);
        boolean f = false;
        if (!strFieldIds.equals("")) {
            f = fieldUserMapper.deleteFieldByIds(paramMap);
        } else {
            f = true;
        }

        boolean ft = fieldTypeMapper.updateFieldTypeByTypeIds(paramMap);
        fieldTypeMapper.deleteFieldTypeByTypeIds(paramMap);
        paramMap.put("fieldUpdate", Boolean.valueOf(f));
        paramMap.put("fieldTypeUpdate", Boolean.valueOf(ft));
        Integer result = Integer.valueOf(-1);
        if (f && ft) {
            result = Integer.valueOf(1);
        }

        paramMap.put("result", result);
        return paramMap;
    }

    public boolean backEngFieldType(Map<String, Object> paramMap) {
        String userId = String.valueOf(paramMap.get("userId"));
        paramMap.put("userId", userId);
        String basicFieldTypeIds = fieldMapper.findFieldTypeIdsByFieldId(paramMap);
        String strFieldTypeIds = basicFieldTypeIds;
        String[] arrIds = basicFieldTypeIds.split(",");

        for (int f = 0; f < arrIds.length; f++) {
            String fieldTypeIds = queryAllParentFieldTypeId("", arrIds[f], (String) paramMap.get("engineId"), userId);
            if (!fieldTypeIds.equals("")) {
                strFieldTypeIds = strFieldTypeIds + "," + fieldTypeIds;
            }
        }

        boolean var10 = fieldUserMapper.backFieldByIds(paramMap);
        List var11 = StringUtil.toLongList(strFieldTypeIds);
        paramMap.put("fieldTypeIds", var11);
        fieldTypeMapper.backFieldTypeByTypeIds(paramMap);
        boolean result = false;
        if (var10) {
            result = true;
        }

        return result;
    }

    /**
     * @param paramMap userId  String 必须 用户id
     *                 engineId String 必须 引擎id
     * @return返回int
     */
    public int isExistsFieldType(Map<String, Object> paramMap) {
        return fieldTypeMapper.isExists(paramMap);
    }

    public int isExistsDefaultTreeName(Map<String, Object> paramMap) {
        return fieldTypeMapper.isExistsDefaultTreeName(paramMap);
    }

    public String queryFieldIdsByTypeIds(Map<String, Object> paramMap) {
        return fieldMapper.findFieldIdsByTypeIds(paramMap);
    }

    public String queryOrgFieldIdsByTypeIds(Map<String, Object> paramMap) {
        return fieldMapper.findOrgFieldIdsByTypeIds(paramMap);
    }

    public List<String> selectFieldIdsByTypeIds(Map<String, Object> paramMap) {
        return fieldMapper.selectFieldIdsByTypeIds(paramMap);
    }

    public String addEngineTestData(@RequestParam Map<String, Object> paramMap, String userId) {
        paramMap.put("userId", userId);
        List fieldList = (List) paramMap.get("fieldList");
        Integer fieldCt = fieldList.size();
        Long rowCt = Long.valueOf((String) paramMap.get("rowCt"));
        double nullCtRatio = (new Double((String) paramMap.get("nullCtRatio")));
        Long nullCt = Long.valueOf((long) (nullCtRatio * (double) rowCt.longValue() * (double) fieldCt.intValue() / 100.0D));
        double elseCtRatio = (new Double((String) paramMap.get("elseCtRatio"))).doubleValue();
        Long elseCt = Long.valueOf((long) (elseCtRatio * (double) rowCt.longValue() * (double) fieldCt.intValue() / 100.0D));
        int iNullCt = 0;
        int iElseCt = 0;
        Random random = new Random();
        StringBuffer testData = new StringBuffer();

        for (int i = 0; (long) i < rowCt.longValue(); i++) {
            int fileUrl = fieldList.size();
            int e = 0;
            Iterator newFile = fieldList.iterator();

            while (newFile.hasNext()) {
                boolean p = false;
                Field field = (Field) newFile.next();
                String fieldEn = field.getEnName();
                Integer valueType = field.getValueType();
                String valueScope = field.getRestrainScope();
                testData.append("\"").append(fieldEn).append("\"").append(":");
                int nullKey = random.nextInt(2);
                if (nullKey == 1 && (long) iNullCt < nullCt.longValue()) {
                    testData.append("\"\"");
                    iNullCt++;
                    p = true;
                }

                String l;
                int j;
                if (!p) {
                    int arrValueScope = random.nextInt(2);
                    if (arrValueScope == 1 && (long) iElseCt < elseCt.longValue() && valueType.intValue() != 2) {
                        if (valueType.intValue() == 1) {
                            l = valueScope.substring(1, valueScope.indexOf(","));
                            String k = valueScope.substring(valueScope.indexOf(",") + 1, valueScope.length() - 1);
                            if (!k.equals("")) {
                                j = Integer.parseInt(k) + 1;
                                testData.append("\"" + j + "\"");
                            } else if (k.equals("") && !l.equals("")) {
                                j = Integer.parseInt(l) - 1;
                                testData.append("\"" + j + "\"");
                            }
                        }

                        if (valueType.intValue() == 3) {
                            testData.append("\"-999999\"");
                        }

                        iElseCt++;
                        p = true;
                    }
                }

                if (!p) {
                    if (valueType.intValue() == 1) {
                        String valueSco = valueScope.substring(1, valueScope.indexOf(","));
                        l = valueScope.substring(valueScope.indexOf(",") + 1, valueScope.length() - 1);
                        testData.append("\"" + getRandomInt(valueSco, l) + "\"");
                    } else if (valueType.intValue() == 2) {
                        testData.append("\"" + getRandomString(6) + "\"");
                    } else if (valueType.intValue() == 3) {
                        String[] valueScope_ = valueScope.split(",");
                        int size = valueScope_.length;
                        int count = random.nextInt(size) % (size + 1);

                        for (j = 0; j < valueScope_.length; j++) {
                            if (j == count) {
                                String value = valueScope_[j];
                                String tV = value.substring(value.indexOf(":") + 1, value.length());
                                testData.append("\"" + tV + "\"");
                            }
                        }
                    }
                }

                e++;
                if (e != fieldCt.intValue()) {
                    testData.append(",");
                }
            }

            testData.append("\r\n");
        }

        String file = "";
        String filenameurl = "";

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            file = paramMap.get("engineId") + "_" + paramMap.get("versionId") + "_" + simpleDateFormat.format(new Date()) + ".txt";
            filenameurl = paramMap.get("downloadDir") + "/" + file;
            File file1 = new File(filenameurl);
            if (file1.createNewFile()) {
                PrintWriter printWriter = new PrintWriter(new FileOutputStream(file1.getAbsolutePath()));
                printWriter.write(testData.toString());
                printWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }

    @Resource
    public EngineResultSetMapper resultSetMapper;

    public String addEngineTestResultPdf(@RequestParam Map<String, Object> paramMap) {
        Integer id = Integer.valueOf(String.valueOf(paramMap.get("resultSetId")));
        EngineResultSet resultSet = new EngineResultSet();
        resultSet.setId(id);
        resultSet = resultSetMapper.queryResultSetById(resultSet);
        String fileName = "";
        String fileUrl = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
        fileName = sdf.format(new Date()) + ".pdf";
        fileUrl = paramMap.get("path") + fileName;

        try {
            Document e = new Document();
            PdfWriter.getInstance(e, new FileOutputStream(fileUrl));
            e.open();
            BaseFont bfChi = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
            Font fontChi = new Font(bfChi, 12.0F, 0);
            e.add(new Paragraph("客户分析报告", fontChi));
            e.add(new Paragraph("结果详情", fontChi));
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100.0F);
            table.setWidthPercentage(100.0F);
            table.addCell(new Paragraph("用户ID： " + id, fontChi));
            table.addCell(new Paragraph("引擎名称： " + resultSet.getEngineName(), fontChi));
            String result = resultSet.getResult();
            if (result != null && !result.equals("") && !result.equals("1")) {
                if (result.equals("2")) {
                    result = "拒绝";
                }
            } else {
                result = "通过";
            }

            table.addCell(new Paragraph("决策建议：" + result, fontChi));
            String score = resultSet.getScorecardscore();
            if (score == null) {
                score = "";
            }

            table.addCell(new Paragraph("信用评分： " + score + "分", fontChi));
            e.add(table);
            e.add(new Paragraph("黑名单", fontChi));
            PdfPTable table2 = new PdfPTable(3);
            table2.setWidthPercentage(100.0F);
            table2.setWidthPercentage(100.0F);
            table2.addCell(new Paragraph("名单类型", fontChi));
            table2.addCell(new Paragraph("名单名称", fontChi));
            table2.addCell(new Paragraph("名单描述", fontChi));
            List resultSetLists = resultSet.getResultSetList();
            boolean flag = false;
            if (resultSet.getResultSetList().size() > 0) {
                Iterator table3 = resultSetLists.iterator();

                while (table3.hasNext()) {
                    ResultSetList table4 = (ResultSetList) table3.next();
                    if (table4.getType().intValue() == 1) {
                        flag = true;
                        table2.addCell(new Paragraph(table4.getCode(), fontChi));
                        table2.addCell(new Paragraph(table4.getName(), fontChi));
                        table2.addCell(new Paragraph(table4.getDesc(), fontChi));
                    }
                }
            }

            if (!flag) {
                PdfPCell table31 = new PdfPCell(new Paragraph("未命中", fontChi));
                table31.setColspan(3);
                table2.addCell(table31);
            }

            e.add(table2);
            e.add(new Paragraph("白名单", fontChi));
            PdfPTable table32 = new PdfPTable(3);
            table32.setWidthPercentage(100.0F);
            table32.setWidthPercentage(100.0F);
            table32.addCell(new Paragraph("名单类型", fontChi));
            table32.addCell(new Paragraph("名单名称", fontChi));
            table32.addCell(new Paragraph("名单描述", fontChi));
            flag = false;
            if (resultSet.getResultSetList().size() > 0) {
                Iterator table41 = resultSetLists.iterator();

                while (table41.hasNext()) {
                    ResultSetList table5 = (ResultSetList) table41.next();
                    if (table5.getType().intValue() == 2) {
                        flag = true;
                        table32.addCell(new Paragraph(table5.getCode(), fontChi));
                        table32.addCell(new Paragraph(table5.getName(), fontChi));
                        table32.addCell(new Paragraph(table5.getDesc(), fontChi));
                    }
                }
            }

            if (!flag) {
                PdfPCell table42 = new PdfPCell(new Paragraph("未命中", fontChi));
                table42.setColspan(3);
                table32.addCell(table42);
            }

            e.add(table32);
            e.add(new Paragraph("硬性拒绝规则", fontChi));
            PdfPTable table43 = new PdfPTable(4);
            table43.setWidthPercentage(100.0F);
            table43.setWidthPercentage(100.0F);
            table43.addCell(new Paragraph("规则ID", fontChi));
            table43.addCell(new Paragraph("规则名称", fontChi));
            table43.addCell(new Paragraph("命中原因", fontChi));
            table43.addCell(new Paragraph("指标表现", fontChi));
            flag = false;
            if (resultSet.getResultSetList().size() > 0) {
                Iterator table51 = resultSetLists.iterator();

                while (table51.hasNext()) {
                    ResultSetList cell = (ResultSetList) table51.next();
                    if (cell.getType().intValue() == 3) {
                        flag = true;
                        table43.addCell(new Paragraph(cell.getCode(), fontChi));
                        table43.addCell(new Paragraph(cell.getName(), fontChi));
                        table43.addCell(new Paragraph(cell.getDesc(), fontChi));
                        table43.addCell(new Paragraph("", fontChi));
                    }
                }
            }

            if (!flag) {
                PdfPCell table52 = new PdfPCell(new Paragraph("未命中", fontChi));
                table52.setColspan(4);
                table43.addCell(table52);
            }

            e.add(table43);
            e.add(new Paragraph("加减分规则", fontChi));
            e.add(new Paragraph());
            PdfPTable table53 = new PdfPTable(4);
            table53.setWidthPercentage(100.0F);
            table53.setWidthPercentage(100.0F);
            table53.addCell(new Paragraph("规则ID", fontChi));
            table53.addCell(new Paragraph("规则名称", fontChi));
            table53.addCell(new Paragraph("命中原因", fontChi));
            table53.addCell(new Paragraph("指标表现", fontChi));
            flag = false;
            if (resultSet.getResultSetList().size() > 0) {
                Iterator cell1 = resultSetLists.iterator();

                while (cell1.hasNext()) {
                    ResultSetList resultSetList = (ResultSetList) cell1.next();
                    if (resultSetList.getType().intValue() == 4) {
                        flag = true;
                        table53.addCell(new Paragraph(resultSetList.getCode(), fontChi));
                        table53.addCell(new Paragraph(resultSetList.getName(), fontChi));
                        table53.addCell(new Paragraph(resultSetList.getDesc(), fontChi));
                        table53.addCell(new Paragraph("", fontChi));
                    }
                }
            }

            if (!flag) {
                PdfPCell cell2 = new PdfPCell(new Paragraph("未命中", fontChi));
                cell2.setColspan(4);
                table53.addCell(cell2);
            }

            e.add(table53);
            e.close();
        } catch (FileNotFoundException var21) {
            var21.printStackTrace();
        } catch (DocumentException var22) {
            var22.printStackTrace();
        } catch (IOException var23) {
            var23.printStackTrace();
        }

        return fileName;
    }

    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }

        return sb.toString();
    }

    public static int getRandomInt(String minS, String maxS) {
        int min = 0;
        int max = 0;
        if (minS.indexOf(".") >= 0) {
            minS = minS.substring(0, minS.indexOf("."));
        }

        if (maxS.indexOf(".") >= 0) {
            maxS = maxS.substring(0, maxS.indexOf("."));
        }

        if (maxS.equals("") && !minS.equals("")) {
            min = Integer.parseInt(minS);
            max = min + 10000;
        } else if (minS.equals("") && !maxS.equals("")) {
            max = Integer.parseInt(maxS);
            min = max - 10000;
        } else if (!minS.equals("") && !maxS.equals("")) {
            min = Integer.parseInt(minS);
            max = Integer.parseInt(maxS);
        }

        Random random = new Random();
        int i = random.nextInt(max) % (max - min + 1) + min;
        return i;
    }

    public String uploadField(List list, Map paramMap) {
        fieldMapper.batchCreateField(list);
        fieldUserMapper.batchCreateFieldUserRel(paramMap);
        return "true";
    }

    /**
     * 通用字段导出
     *
     * @param paramMap
     * @return
     */
    public List<FieldExcelOut> queryExcelByField(Map<String, Object> paramMap) {
        return fieldMapper.findExcelByField(paramMap);
    }
}

