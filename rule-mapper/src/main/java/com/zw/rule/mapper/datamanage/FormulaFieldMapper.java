package com.zw.rule.mapper.datamanage;

import com.zw.rule.datamanage.po.FormulaField;
import com.zw.rule.mapper.common.BaseMapper;

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
public interface FormulaFieldMapper extends BaseMapper<FormulaField> {
    boolean createFormulaField(Map<String, Object> var1);

    int createFormulaField(List<FormulaField> var1);

    boolean deleteFormulaField(Map<String, Object> var1);
}

