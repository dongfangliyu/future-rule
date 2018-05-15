package com.zw.rule.engine.po;

import java.io.Serializable;
import java.util.List;

/**
 * 引擎节点实体
 * @author zongpeng
 */
public class EngineNode implements Serializable {

    private static final long serialVersionUID = -1867357850853531748L;

    private Long nodeId;//节点id

    private Long parentId;//父节点id

    private Long verId;//版本编号

    private String nodeName;//节点名称

    private String nodeCode;//节点代号

    private Integer nodeOrder;//节点顺序

    private Integer nodeType;//节点类型

    private double nodeX;//节点横坐标

    private double nodeY;//节点纵坐标

    private String nodeScript;//节点脚本

    private String nodeJson;//节点信息

    private String params;//节点用到的参数列表

    private String nextNodes;//下个节点(可能是多个)

    private Long cardId;

    private List<Long> ruleList;

    private Integer lastNextnode;

    public Integer getLastNextnode() {
        return this.lastNextnode;
    }

    public void setLastNextnode(Integer lastNextnode) {
        this.lastNextnode = lastNextnode;
    }

    public Long getCardId() {
        return this.cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getNodeScript() {
        return this.nodeScript;
    }

    public void setNodeScript(String nodeScript) {
        this.nodeScript = nodeScript;
    }

    public String getNodeJson() {
        return this.nodeJson;
    }

    public void setNodeJson(String nodeJson) {
        this.nodeJson = nodeJson;
    }

    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getNextNodes() {
        return this.nextNodes;
    }

    public void setNextNodes(String nextNodes) {
        this.nextNodes = nextNodes;
    }

    public Long getNodeId() {
        return this.nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Long getVerId() {
        return verId;
    }

    public void setVerId(Long verId) {
        this.verId = verId;
    }

    public String getNodeName() {
        return this.nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null?null:nodeName.trim();
    }

    public String getNodeCode() {
        return this.nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode == null?null:nodeCode.trim();
    }

    public Integer getNodeOrder() {
        return this.nodeOrder;
    }

    public void setNodeOrder(Integer nodeOrder) {
        this.nodeOrder = nodeOrder;
    }

    public Integer getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public double getNodeX() {
        return this.nodeX;
    }

    public void setNodeX(double nodeX) {
        this.nodeX = nodeX;
    }

    public double getNodeY() {
        return this.nodeY;
    }

    public void setNodeY(double nodeY) {
        this.nodeY = nodeY;
    }

    public List<Long> getRuleList() {
        return this.ruleList;
    }

    public void setRuleList(List<Long> ruleList) {
        this.ruleList = ruleList;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
