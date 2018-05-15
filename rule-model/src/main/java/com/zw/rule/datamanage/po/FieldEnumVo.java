package com.zw.rule.datamanage.po;

import com.zw.rule.base.BaseEntity;

import java.util.List;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年05月08日<br>
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
public class FieldEnumVo extends BaseEntity{
    private Field field;
    private List<String> enums;

    public FieldEnumVo() {
    }

    public Field getField() {
        return this.field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public List<String> getEnums() {
        return this.enums;
    }

    public void setEnums(List<String> enums) {
        this.enums = enums;
    }
}

