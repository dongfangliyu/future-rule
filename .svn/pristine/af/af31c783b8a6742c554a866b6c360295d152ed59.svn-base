package com.zw.rule.datamanage.po;

import com.zw.rule.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年05月03日<br>
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
public class Field extends BaseEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long id;
    //字段英文名
    private String enName;
    //字段中文名
    private String cnName;
    //字段类型编号,来源t_fieldtype表主键
    private Long fieldTypeId;
    //字段类型
    private String fieldType;
    //字段存值类型,0：待选,1：数值型,2：字符型,3：枚举型,4：小数型
    private Integer valueType;
    //字段约束范围
    private String restrainScope;
    //是否衍生字段，0：不是，1：是，默认0：不是
    private Integer isDerivative;
    //是否输出字段，0：不是，1：是，默认0：不是
    private Integer isOutput;
    //是否组织定义的通用字段，0：不是，1：是，默认0：不是
    private Integer isCommon;
    //公式
    private String formula;
    //公式回显信息存值
    private String formulaShow;
    //该衍生字段所引用的字段id，用逗号，分割
    private String deriveFieldId;
    //衍生字段使用的所有原生字段编号,用逗号，分割
    private String protogeneFieldId;
    private String creator;
    private String nickName;
    private Date createTime;
    private Long engineId;
    private String engineName;
    private String status;
    private List<FieldCond> fieldCondList;
    private Long fieldRelId;
    private int counts;

    public Field() {
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getRestrainScope() {
        return restrainScope;
    }

    public void setRestrainScope(String restrainScope) {
        this.restrainScope = restrainScope;
    }

    public String getDeriveFieldId() {
        return deriveFieldId;
    }

    public void setDeriveFieldId(String deriveFieldId) {
        this.deriveFieldId = deriveFieldId;
    }

    public String getProtogeneFieldId() {
        return protogeneFieldId;
    }

    public void setProtogeneFieldId(String protogeneFieldId) {
        this.protogeneFieldId = protogeneFieldId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long Id) {
        this.id = Id;
    }

    public Long getFieldTypeId() {
        return this.fieldTypeId;
    }

    public void setFieldTypeId(Long fieldTypeId) {
        this.fieldTypeId = fieldTypeId;
    }

    public Integer getValueType() {
        return this.valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    public Integer getIsDerivative() {
        return this.isDerivative;
    }

    public void setIsDerivative(Integer isDerivative) {
        this.isDerivative = isDerivative;
    }

    public Integer getIsOutput() {
        return this.isOutput;
    }

    public void setIsOutput(Integer isOutput) {
        this.isOutput = isOutput;
    }

    public Integer getIsCommon() {
        return this.isCommon;
    }

    public void setIsCommon(Integer isCommon) {
        this.isCommon = isCommon;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getFormulaShow() {
        return this.formulaShow;
    }

    public void setFormulaShow(String formulaShow) {
        this.formulaShow = formulaShow;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public Long getEngineId() {
        return this.engineId;
    }

    public void setEngineId(Long engineId) {
        this.engineId = engineId;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getFieldRelId() {
        return this.fieldRelId;
    }

    public void setFieldRelId(Long fieldRelId) {
        this.fieldRelId = fieldRelId;
    }

    public List<FieldCond> getFieldCondList() {
        return this.fieldCondList;
    }

    public void setFieldCondList(List<FieldCond> fieldCondList) {
        this.fieldCondList = fieldCondList;
    }

    public String getEngineName() {
        return this.engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }
    public void setCounts(int counts){ this.counts = counts;}
    public int getCounts(){return this.counts;}


    @Override
    public String toString() {
        return "Field{" +
                "enName='" + enName + '\'' +
                ", cnName='" + cnName + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", valueType=" + valueType +
                ", isDerivative=" + isDerivative +
                ", isOutput=" + isOutput +
                '}';
    }


}

