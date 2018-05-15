package com.zw.rule.datamanage.po;

import com.zw.rule.base.BaseEntity;

import java.io.Serializable;

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
public class FormulaField extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long fieldId;
    private Long formulaFieldId;

    public FormulaField() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFieldId() {
        return this.fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getFormulaFieldId() {
        return this.formulaFieldId;
    }

    public void setFormulaFieldId(Long formulaFieldId) {
        this.formulaFieldId = formulaFieldId;
    }

}

