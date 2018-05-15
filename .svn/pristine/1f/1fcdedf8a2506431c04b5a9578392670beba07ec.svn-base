//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zw.rule.engine.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.zw.rule.datamanage.po.NodeListDb;
import com.zw.rule.engine.po.EngineNode;
import com.zw.rule.engine.po.EngineVersion;
import com.zw.rule.engine.po.NodeKnowledge;
import com.zw.rule.engine.po.NodeTypeEnum;
import com.zw.rule.engine.service.*;
import com.zw.rule.jeval.tools.CollectionUtil;
import com.zw.rule.knowledge.po.RuleNode;
import com.zw.rule.mapper.datamanage.NodeListDbMapper;
import com.zw.rule.mapper.engine.EngineNodeMapper;
import com.zw.rule.mapper.engine.EngineVersionMapper;
import com.zw.rule.mapper.engine.NodeKnowledgeMapper;
import com.zw.rule.mapper.knowledge.RuleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class EngineVersionServiceImpl implements EngineVersionService {
    public EngineVersionServiceImpl() {
    }
    @Resource
    private EngineVersionMapper engineVersionMapper;
    @Resource
    private NodeKnowledgeMapper nodeKnowledgeMapper;
    @Resource
    private EngineNodeMapper engineNodeMapper;
    @Resource
    private NodeListDbMapper nodeListDbMapper;
    @Resource
    private RuleMapper ruleMapper;


    /**
     * 部署引擎
     * @param versionId Long 版本编号 必须
     * @return
     */
    @Transactional
    public int deployEngine(Long versionId) {
        EngineVersion engineVersion = engineVersionMapper.selectByPrimaryKey(versionId.longValue());
        int count = 0;
        if(engineVersion != null) {
            long engineId = engineVersion.getEngineId().longValue();
            engineVersionMapper.undeployVersion(engineId);
            int version = engineVersion.getVersion().intValue();
            int subVersion = engineVersion.getSubVer().intValue();
            HashMap map = new HashMap();
            map.put("engineId", Long.valueOf(engineId));
            map.put("version", Integer.valueOf(version));
            if(subVersion != 0) {
                EngineVersion latestEngineVersion = engineVersionMapper.queryLatestEngineVersion(engineVersion);
                engineVersion.setVersion(Integer.valueOf(latestEngineVersion.getVersion().intValue() + 1));
                engineVersion.setSubVer(Integer.valueOf(0));
                engineVersion.setBootState(Integer.valueOf(1));
                count = engineVersionMapper.updateByPrimaryKeySelective(engineVersion);
                if(count == 1) {
                    engineVersionMapper.cleanSubVersionByVersion(map);
                }
            } else {
                engineVersion.setBootState(Integer.valueOf(1));
                count = engineVersionMapper.updateByPrimaryKeySelective(engineVersion);
            }
        }
        return count;
    }

    /**
     * 更新引擎状态
     * @param versionId Long 版本编号 必须
     * @return
     */
    public int undeployEngine(Long versionId) {
        EngineVersion engineVersion = engineVersionMapper.selectByPrimaryKey(versionId.longValue());
        int count = 0;
        if(engineVersion != null) {
            engineVersion.setBootState(Integer.valueOf(0));
            count = engineVersionMapper.updateByPrimaryKeySelective(engineVersion);
        }
        return count;
    }

    /**
     * 获取引擎版本列表
     * @param engineId Long 引擎Id 必须
     * @return
     */
    public List<EngineVersion> queryEngineVersionListByEngineId(Long engineId) {
        return engineVersionMapper.queryEngineVersionListByEngineId(engineId.longValue());
    }

    /**
     * 插入引擎版本数据到数据库
     * @param engineVersion
     * version 版本 必须 String
     * boot_state 状态 1未部署 0 部署
     * @return
     */
    @Transactional
    public int addEngineVersion(EngineVersion engineVersion) {
        return engineVersionMapper.insertEngineVersionAndReturnId(engineVersion);
    }

    /**
     * 更新引擎版本信息
     * @param engineVersion
     * @return
     */
    public int update(EngineVersion engineVersion) {
        return engineVersionMapper.updateByPrimaryKey(engineVersion);
    }

    /**
     * 获取最新的引擎版本
     * @param engineVersion
     * engineId 引擎Id Long 必须
     * @return
     */
    public EngineVersion queryLatestEngineVersion(EngineVersion engineVersion) {
        return engineVersionMapper.queryLatestEngineVersion(engineVersion);
    }

    /**
     * 获取最新的更改版本
     * @param engineVersion
     * engineId 引擎Id Long 必须
     * version 版本 String 必须
     * @return
     */
    public EngineVersion queryLatestEngineSubVersion(EngineVersion engineVersion) {
        return engineVersionMapper.queryLatestEngineSubVersion(engineVersion);
    }

    /**
     * 保存引擎版本
     * @param engineVersion
     * @param nodeList
     * @return
     */
    public Long saveEngineVersion(EngineVersion engineVersion, List<EngineNode> nodeList) {
        int count = engineVersionMapper.insertEngineVersionAndReturnId(engineVersion);
        if(count != 1) {
            return new Long(0L);
        } else {
            long versionId = engineVersion.getVerId().longValue();
            ArrayList nodeKnowledges = null;
            Iterator pageInfo = nodeList.iterator();
            while(true) {
                List engineNodeId1;
                long nodeKnowledge2;
                label39:
                do {
                    while(pageInfo.hasNext()) {
                        EngineNode engineNode = (EngineNode)pageInfo.next();
                        engineNode.setVerId(Long.valueOf(versionId));
                        if(engineNode.getNodeType().intValue() == NodeTypeEnum.POLICY.getValue()||engineNode.getNodeType().intValue() == NodeTypeEnum.NODE_COMPLEXRULE.getValue()) {
                            HashMap nodeListDb2 = new HashMap();
                            nodeListDb2.put("nodeId", engineNode.getNodeId());
                            nodeListDb2.put("type", Integer.valueOf(1));
                            engineNodeId1 = nodeKnowledgeMapper.queryNodeKnowledgeListByNodeId(nodeListDb2);
                            //获取关联的规则
                            List<RuleNode> ruleNodelist = ruleMapper.selectRuleNodeByNodeId(engineNode.getNodeId());
                            engineNodeMapper.insertNodeAndReturnId(engineNode);
                            for(int i = 0;i < ruleNodelist.size();i++){
                                RuleNode ruleNode = ruleNodelist.get(i);
                                ruleNode.setNodeId(engineNode.getNodeId());
                                ruleMapper.saveVerRuleNode(ruleNode);
                            }
                            nodeKnowledge2 = engineNode.getNodeId().longValue();
                            continue label39;
                        }

                        if(engineNode.getNodeType().intValue() == NodeTypeEnum.SCORECARD.getValue()) {
                            engineNodeMapper.insertNodeAndReturnId(engineNode);
                            long nodeListDb1 = engineNode.getNodeId().longValue();
                            NodeKnowledge nodeKnowledge = new NodeKnowledge();
                            nodeKnowledge.setKnowledgeType(Integer.valueOf(2));
                            nodeKnowledge.setNodeId(Long.valueOf(nodeListDb1));
                            nodeKnowledge.setKnowledgeId(Long.valueOf(Long.parseLong(engineNode.getNodeJson())));
                            nodeKnowledgeMapper.insertNode(nodeKnowledge);
                        } else if(engineNode.getNodeType().intValue() != NodeTypeEnum.BLACKLIST.getValue() && engineNode.getNodeType().intValue() != NodeTypeEnum.WHITELIST.getValue()) {
                            engineNodeMapper.insertSelective(engineNode);
                        } else {
                            NodeListDb nodeListDb = nodeListDbMapper.findDbListByNodeId(engineNode.getNodeId());
                            engineNodeMapper.insertNodeAndReturnId(engineNode);
                            long engineNodeId = engineNode.getNodeId().longValue();
                            if(nodeListDb != null) {
                                nodeListDb.setNodeId(Long.valueOf(engineNodeId));
                                nodeListDbMapper.insertNodeListDb(nodeListDb);
                            }
                        }
                    }
                    return Long.valueOf(versionId);
                } while(!CollectionUtil.isNotNullOrEmpty(engineNodeId1));

                nodeKnowledges = new ArrayList();
                NodeKnowledge nodeKnowledge1 = null;
                Iterator pageInfo1 = engineNodeId1.iterator();
                while(pageInfo1.hasNext()) {
                    NodeKnowledge rule = (NodeKnowledge)pageInfo1.next();
                    nodeKnowledge1 = new NodeKnowledge();
                    nodeKnowledge1.setKnowledgeType(Integer.valueOf(1));
                    nodeKnowledge1.setNodeId(Long.valueOf(nodeKnowledge2));
                    nodeKnowledge1.setKnowledgeId(rule.getKnowledgeId());
                    nodeKnowledges.add(nodeKnowledge1);
                }
                nodeKnowledgeMapper.batchInsert(nodeKnowledges);
            }
        }
    }

    /**
     * 通过引擎Id获取已经部署过的引擎版本
     * @param engineId 必须 Long 引擎Id
     * @return
     */
    public EngineVersion queryDeploiedVersionByEngineId(Long engineId) {
        return engineVersionMapper.queryDeploiedVersionByEngineId(engineId);
    }

    /**
     * 查询信息 zw_engine_version
     * @param versionId Long 必须 版本Id
     * @return
     */
    public EngineVersion queryByPrimaryKey(Long versionId) {
        return engineVersionMapper.selectByPrimaryKey(versionId.longValue());
    }

    public boolean clear(Long versionId) {
        EngineNode startNode = null;
        ArrayList knowledges = new ArrayList();
        ArrayList blackWhites = new ArrayList();
        ArrayList commons = new ArrayList();
        List engineNodes = engineNodeMapper.queryNodeListByVerId(versionId);
        if(CollectionUtil.isNotNullOrEmpty(engineNodes)) {
            Iterator pageInfo = engineNodes.iterator();

            while(pageInfo.hasNext()) {
                EngineNode engineNode = (EngineNode)pageInfo.next();
                switch(engineNode.getNodeType().intValue()) {
                    case 1:
                        startNode = engineNode;
                        engineNode.setNextNodes("");
                        break;
                    case 2:
                    case 4:
                        knowledges.add(engineNode.getNodeId());
                        commons.add(engineNode.getNodeId());
                        break;
                    case 3:
                    default:
                        commons.add(engineNode.getNodeId());
                        break;
                    case 5:
                    case 6:
                        blackWhites.add(engineNode.getNodeId());
                        commons.add(engineNode.getNodeId());
                }
            }
            if(CollectionUtil.isNotNullOrEmpty(knowledges)) {
                nodeKnowledgeMapper.deleteKnowledgesBatchByNodeIds(knowledges);
            }
            if(CollectionUtil.isNotNullOrEmpty(blackWhites)) {
                nodeListDbMapper.deleteFieldRefsBatchByNodeIds(blackWhites);
            }
            if(CollectionUtil.isNotNullOrEmpty(commons)) {
                engineNodeMapper.deleteNodesByNodeIds(commons);
            }
            if(startNode != null) {
                engineNodeMapper.updateNextNodes(startNode);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 通过引擎Id，用户Id获取引擎版本
     * @param paramMap
     * engineId Long 引擎Id 必须
     * userId Long 用户Id 必须
     * @return
     */
    public List<EngineVersion> queryEngineVersionByEngineId(Map<String, Object> paramMap) {
        return engineVersionMapper.queryEngineVersionByEngineId(paramMap);
    }

    /**
     * 获取节点列表用过引擎信息
     * @param paramMap
     * @return
     */
    public List<EngineNode> getNodeListByEngineInfo(Map<String, Object> paramMap) {
        Integer reqtype = (Integer)paramMap.get("reqtype");
        Long engineId = (Long)paramMap.get("engineId");
        EngineVersion engineVersion = null;
        if(reqtype.intValue() == 1) {
            engineVersion = engineVersionMapper.queryDeploiedVersionByEngineId(engineId);
            return engineNodeMapper.queryNodeListByVerId(engineVersion.getVerId());
        } else if(reqtype.intValue() == 2) {
            engineVersion = engineVersionMapper.getTargetEngineVersion(paramMap);
            return engineNodeMapper.queryNodeListByVerId(engineVersion.getVerId());
        } else {
            return new ArrayList();
        }
    }
}
