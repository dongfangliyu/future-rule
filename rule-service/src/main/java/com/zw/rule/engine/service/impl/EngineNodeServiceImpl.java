//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zw.rule.engine.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.zw.rule.engine.po.EngineNode;
import com.zw.rule.engine.po.NodeKnowledge;
import com.zw.rule.engine.po.NodeTypeEnum;
import com.zw.rule.engine.service.EngineNodeService;


import com.zw.rule.engine.util.EngineNodeUtil;
import com.zw.rule.engine.util.EngineUtil;
import com.zw.rule.jeval.tools.CollectionUtil;
import com.zw.rule.jeval.tools.StringUtil;
import com.zw.rule.mapper.datamanage.NodeListDbMapper;
import com.zw.rule.mapper.engine.EngineNodeMapper;
import com.zw.rule.mapper.engine.NodeKnowledgeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EngineNodeServiceImpl implements EngineNodeService {
    public EngineNodeServiceImpl() {
    }
    @Resource
    private EngineNodeMapper engineNodeMapper;
    @Resource
    private NodeKnowledgeMapper nodeKnowledgeMapper;
    @Resource
    private NodeListDbMapper nodeListDbMapper;

    public boolean addNode(EngineNode engineNo) {
        int flag = engineNodeMapper.insertSelective(engineNo);
        return flag > 0;
    }

    public List<EngineNode> queryNodeListByVerId(Long engineVersionId) {
        return engineNodeMapper.queryNodeListByVerId(engineVersionId);
    }

    public EngineNode findById(Long id) {
        return (EngineNode)engineNodeMapper.selectByPrimaryKey(id);
    }

    public boolean deleteNode(EngineNode node) {
        int num = engineNodeMapper.deleteByExample(node);
        return num > 0;
    }

    public boolean updateNode(EngineNode engineNode, Long targetId) {
        boolean flag = true;
        int count = engineNodeMapper.updateByPrimaryKeySelective(engineNode);
        if(count == 1) {
            int nodeType = engineNode.getNodeType();
            long nodeId = engineNode.getNodeId();
            HashMap map;
            if(NodeTypeEnum.POLICY.getValue() != nodeType && NodeTypeEnum.NODE_COMPLEXRULE.getValue() != nodeType) {
                if(NodeTypeEnum.SCORECARD.getValue() == nodeType) {
                    NodeKnowledge parentId1 = nodeKnowledgeMapper.findScorecardByNodeId(nodeId);
                    parentId1.setKnowledgeId(engineNode.getCardId());
                    int map1 = nodeKnowledgeMapper.updateByPrimaryKeySelective(parentId1);
                    if(map1 != 1) {
                        flag = false;
                    }
                }
            } else {
                List parentId = engineNode.getRuleList();
                map = new HashMap();
                map.put("nodeId", Long.valueOf(nodeId));
                map.put("type", Integer.valueOf(1));
                nodeKnowledgeMapper.deleteKnowledgesByNodeId(map);
                ArrayList nodeKnowledges = null;
                if(CollectionUtil.isNotNullOrEmpty(parentId)) {
                    nodeKnowledges = new ArrayList();
                    NodeKnowledge batchCount = null;
                    Iterator var13 = parentId.iterator();

                    while(var13.hasNext()) {
                        Long rule = (Long)var13.next();
                        batchCount = new NodeKnowledge();
                        batchCount.setKnowledgeId(rule);
                        batchCount.setKnowledgeType(Integer.valueOf(1));
                        batchCount.setNodeId(Long.valueOf(nodeId));
                        nodeKnowledges.add(batchCount);
                    }
                }

                int batchCount1 = nodeKnowledgeMapper.batchInsert(nodeKnowledges);
                if(batchCount1 != nodeKnowledges.size()) {
                    flag = false;
                }
            }

            if(targetId != null) {
                Long parentId2 = Long.valueOf(nodeId);
                map = new HashMap();
                map.put("nodeId", targetId);
                map.put("parentId", parentId2);
                engineNodeMapper.updateParentIdByNodeId(map);
            }
        } else {
            flag = false;
        }

        return flag;
    }

    public boolean saveEngineNode(EngineNode eNode) {
        boolean flag = true;
        int count = engineNodeMapper.insertNodeAndReturnId(eNode);
        if(count == 1) {
            int nodeType = eNode.getNodeType().intValue();
            long nodeId = eNode.getNodeId().longValue();
            if(NodeTypeEnum.POLICY.getValue() == nodeType || NodeTypeEnum.NODE_COMPLEXRULE.getValue() == nodeType) {
                ArrayList card_ids = new ArrayList();
                List nodeKnowledge = eNode.getRuleList();
                NodeKnowledge subCount = null;
                Iterator iterator = nodeKnowledge.iterator();

                while(iterator.hasNext()) {
                    Long batchCount = (Long)iterator.next();
                    subCount = new NodeKnowledge();
                    subCount.setNodeId(Long.valueOf(nodeId));
                    subCount.setKnowledgeId(batchCount);
                    subCount.setKnowledgeType(Integer.valueOf(1));
                    card_ids.add(subCount);
                }

                int batchCount1 = nodeKnowledgeMapper.batchInsert(card_ids);
                if(batchCount1 != card_ids.size()) {
                    flag = false;
                }
            } else if(NodeTypeEnum.SCORECARD.getValue() == nodeType) {
                List card_ids1 = eNode.getRuleList();
                if(CollectionUtil.isNotNullOrEmpty(card_ids1)) {
                    Long nodeKnowledge1 = (Long)card_ids1.get(0);
                    eNode.setCardId(nodeKnowledge1);
                }

                NodeKnowledge nodeKnowledge2 = new NodeKnowledge();
                nodeKnowledge2.setKnowledgeId(eNode.getCardId());
                nodeKnowledge2.setKnowledgeType(Integer.valueOf(2));
                nodeKnowledge2.setNodeId(Long.valueOf(nodeId));
                int subCount1 = nodeKnowledgeMapper.insertSelective(nodeKnowledge2);
                if(subCount1 != 1) {
                    flag = false;
                }
            }
        } else {
            flag = false;
        }

        return flag;
    }

    public boolean deleteNode(Map<String, Object> param) {
        boolean flag = true;
        Long nodeId = Long.valueOf(Long.parseLong(param.get("nodeId").toString()));
        if(param.containsKey("type")) {
            nodeKnowledgeMapper.deleteKnowledgesByNodeId(param);
        }

        int count = engineNodeMapper.deleteEngineNodeByNodeId(nodeId);
        EngineNode node = (EngineNode)engineNodeMapper.selectByPrimaryKey(nodeId);
        if(count != 1) {
            flag = false;
        }

        return flag;
    }

    public boolean updateNodeForNextOrderAndParams(List<EngineNode> eList) {
        int count = engineNodeMapper.updateNodeForNextOrderAndParams(eList);
        return count > 0;
    }

    public int queryMaxNodeOrder(Long versionId) {
        return engineNodeMapper.queryMaxNodeOrder(versionId);
    }

    public boolean updateNodeForMove(EngineNode engineNode) {
        int count = engineNodeMapper.updateNodePosition(engineNode);
        return count > 0;
    }

    public int renameNode(Map<String, Object> param) {
        return engineNodeMapper.renameNode(param);
    }

    public Map<String, Object> deleteNodes(List<Long> idList, String array) {
        int num = removeNodes(idList, array);
        int count = engineNodeMapper.deleteNodes(idList);
        findById((Long)idList.get(0));
        HashMap map = new HashMap();
        map.put("num", Integer.valueOf(num));
        map.put("count", Integer.valueOf(count));
        return map;
    }

    public List<EngineNode> queryNodesByNextNode(Map<String, Object> param) {
        return engineNodeMapper.queryNodesByNextNode(param);
    }

    public boolean updateNextNodes(EngineNode engineNode) {
        engineNodeMapper.updateNextNodes(engineNode);
        EngineNode var10000 = (EngineNode)engineNodeMapper.selectByPrimaryKey(engineNode.getNodeId());
        return true;
    }

    public void deleteNode(Long engineNodeId, Long preEngineNodeId) {
        EngineNode engineNode = (EngineNode)engineNodeMapper.selectByPrimaryKey(engineNodeId);
        if(engineNode != null) {
            if(preEngineNodeId.longValue() == -1L) {
                if(engineNode.getNodeType().intValue() == NodeTypeEnum.SCORECARD.getValue() || engineNode.getNodeType().intValue() == NodeTypeEnum.POLICY.getValue() || NodeTypeEnum.NODE_COMPLEXRULE.getValue() == engineNode.getNodeType().intValue()) {
                    deleteKnowledgeRef(engineNode);
                }

                engineNodeMapper.deleteByExample(engineNode);
            } else {
                List nodeList = EngineUtil.getUpdateParentIdNodes(engineNode, engineNodeMapper.queryNodeListByVerId(engineNode.getVerId()));
                if(CollectionUtil.isNotNullOrEmpty(nodeList)) {
                    engineNodeMapper.updateNodeForNextOrderAndParams(nodeList);
                }

                EngineNode preEngineNode = (EngineNode)engineNodeMapper.selectByPrimaryKey(preEngineNodeId);
                if(preEngineNode != null) {
                    int nodeType = preEngineNode.getNodeType().intValue();
                    switch(nodeType) {
                        case 1:
                            handlePreCommonNode(engineNode, preEngineNode);
                            break;
                        case 2:
                            handlePreCommonNode(engineNode, preEngineNode);
                            break;
                        case 3:
                            handlePreClassifyOrSandBoxNode(engineNode, preEngineNode);
                            break;
                        case 4:
                            handlePreCommonNode(engineNode, preEngineNode);
                            break;
                        case 5:
                            handlePreBlackOrWhiteNode(engineNode, preEngineNode);
                            break;
                        case 6:
                            handlePreBlackOrWhiteNode(engineNode, preEngineNode);
                            break;
                        case 7:
                            handlePreClassifyOrSandBoxNode(engineNode, preEngineNode);
                        default:
                            break;
                        case 9:
                            handlePreCommonNode(engineNode, preEngineNode);
//                            自定义名单
//                        case 14:
//                            preEngineNode.setNextNodes("");
//                            engineNodeMapper.updateByPrimaryKeySelective(preEngineNode);
//                            engineNodeMapper.deleteByExample(engineNode);
//                            break;
                    }
                }
            }
        }

    }

    private void handlePreBlackOrWhiteNode(EngineNode engineNode, EngineNode preEngineNode) {
        preEngineNode.setNextNodes("");
        engineNodeMapper.updateByPrimaryKeySelective(preEngineNode);
        nodeListDbMapper.deleteFieldRefByNodeId(engineNode.getNodeId());
        deleteKnowledgeRef(engineNode);
        engineNodeMapper.deleteByExample(engineNode);
    }

    private void deleteKnowledgeRef(EngineNode engineNode) {
        HashMap params = new HashMap();
        params.put("nodeId", engineNode.getNodeId());
        if(engineNode.getNodeType().intValue() == NodeTypeEnum.SCORECARD.getValue()) {
            params.put("type", Integer.valueOf(2));
        } else if(engineNode.getNodeType().intValue() == NodeTypeEnum.POLICY.getValue()) {
            params.put("type", Integer.valueOf(1));
        }

        nodeKnowledgeMapper.deleteKnowledgesByNodeId(params);
    }

    private void handlePreClassifyOrSandBoxNode(EngineNode engineNode, EngineNode preEngineNode) {
        String nextNodes = preEngineNode.getNextNodes();
        if(StringUtil.isValidStr(nextNodes)) {
            nextNodes = nextNodes.replace(engineNode.getNodeCode(), "");
            if(nextNodes.startsWith(",")) {
                nextNodes = nextNodes.substring(1);
            }

            if(nextNodes.endsWith(",")) {
                nextNodes = nextNodes.substring(0, nextNodes.length() - 1);
            }

            preEngineNode.setNextNodes(nextNodes.replace(",,", ","));
        }

        String nodeJson = preEngineNode.getNodeJson();
        if(StringUtil.isValidStr(nodeJson)) {
            nodeJson = nodeJson.replace(engineNode.getNodeCode(), "");
            preEngineNode.setNodeJson(nodeJson);
        }

        preEngineNode = EngineNodeUtil.boxEngineNodeJson(preEngineNode);
        engineNodeMapper.updateByPrimaryKeySelective(preEngineNode);
        engineNodeMapper.deleteByExample(engineNode);
    }

    private void handlePreCommonNode(EngineNode engineNode, EngineNode preEngineNode) {
        preEngineNode.setNextNodes("");
        engineNodeMapper.updateByPrimaryKeySelective(preEngineNode);
        if(engineNode.getNodeType().intValue() == NodeTypeEnum.SCORECARD.getValue() || engineNode.getNodeType().intValue() == NodeTypeEnum.POLICY.getValue()) {
            deleteKnowledgeRef(engineNode);
        }

        engineNodeMapper.deleteByExample(engineNode);
    }

    public void removeLink(Long engineNodeId, Long preEngineNodeId) {
        EngineNode engineNode = (EngineNode)engineNodeMapper.selectByPrimaryKey(engineNodeId);
        EngineNode preEngineNode = (EngineNode)engineNodeMapper.selectByPrimaryKey(preEngineNodeId);
        if(preEngineNode != null) {
            int nodeType = preEngineNode.getNodeType().intValue();
            switch(nodeType) {
                case 3:
                    handlePreClassifyOrSandBoxLink(engineNode, preEngineNode);
                    break;
                case 4:
                case 5:
                case 6:
                default:
                    handleCommonLink(engineNode, preEngineNode);
                    break;
                case 7:
                    handlePreClassifyOrSandBoxLink(engineNode, preEngineNode);
            }
        }

    }

    private void handlePreClassifyOrSandBoxLink(EngineNode engineNode, EngineNode preEngineNode) {
        String nextNodes = preEngineNode.getNextNodes();
        if(StringUtil.isValidStr(nextNodes)) {
            nextNodes = nextNodes.replace(engineNode.getNodeCode(), "");
            if(nextNodes.startsWith(",")) {
                nextNodes = nextNodes.substring(1);
            }

            if(nextNodes.endsWith(",")) {
                nextNodes = nextNodes.substring(0, nextNodes.length() - 1);
            }

            preEngineNode.setNextNodes(nextNodes.replace(",,", ","));
        }

        String nodeJson = preEngineNode.getNodeJson();
        if(StringUtil.isValidStr(nodeJson)) {
            nodeJson = nodeJson.replace(engineNode.getNodeCode(), "");
            preEngineNode.setNodeJson(nodeJson);
        }

        preEngineNode = EngineNodeUtil.boxEngineNodeJson(preEngineNode);
        engineNodeMapper.updateByPrimaryKeySelective(preEngineNode);
    }

    private void handleCommonLink(EngineNode engineNode, EngineNode preEngineNode) {
        String nextNodes = preEngineNode.getNextNodes();
        if(StringUtil.isValidStr(nextNodes)) {
            nextNodes = nextNodes.replace(engineNode.getNodeCode(), "");
            if(nextNodes.startsWith(",")) {
                nextNodes = nextNodes.substring(1);
            }

            if(nextNodes.endsWith(",")) {
                nextNodes = nextNodes.substring(0, nextNodes.length() - 1);
            }

            preEngineNode.setNextNodes(nextNodes);
        }

        engineNodeMapper.updateByPrimaryKeySelective(preEngineNode);
    }

    private int removeNodes(List<Long> idList, String array) {
        int num = 0;
        JSONArray jsonArray = JSONArray.parseArray(array);
        Iterator iterator = jsonArray.iterator();

        while(iterator.hasNext()) {
            Object object = iterator.next();
            JSONObject obj = (JSONObject)object;
            Long subNodeId = obj.getLong("subNodeId");
            Long preNodeId = obj.getLong("preNodeId");
            if(!idList.contains(preNodeId)) {
                deleteNode(subNodeId, preNodeId);
                ++num;
            }
        }

        return num;
    }

    public List<EngineNode> queryEngineTypedNodeListByEngineVerId(Long versionId, List<Integer> types) {
        return engineNodeMapper.queryEngineTypedNodeListByEngineVerId(versionId, types);
    }
}
