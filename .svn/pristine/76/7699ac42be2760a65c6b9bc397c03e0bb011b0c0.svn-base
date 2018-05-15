package com.zw.rule.knowledge.service.impl;

import com.zw.rule.jeval.tools.StringUtil;
import com.zw.rule.knowledge.service.KnowledgeTreeService;
import com.zw.rule.knowledge.service.RuleService;
import com.zw.rule.knowledge.po.KnowledgeTree;
import com.zw.rule.knowledge.service.ScorecardService;
import com.zw.rule.mapper.knowledge.KnowledgeTreeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *
 *Filename：
 *
 *Description：
 *
 *Author:
 *		 李开艳
 *Finished：
 *		2017/5/11
 ********************************************************/
@Service
public class KnowledgeTreeServiceImpl implements KnowledgeTreeService {
    @Resource
    private RuleService ruleService;
    @Resource
    private ScorecardService scorecardService;
    @Resource
    private KnowledgeTreeMapper knowledgeTreeMapper;

    /**
     * 对t_knowledge_tree和t_engine_knowledge_tree_rel关联 查询
     * @param paramMap
     * type   int    目录类型：0系统，1组织，2引擎
     * parentId   long   父id
     * status     List<Integer>  状态：0停用，1启用，-1删除
     * treeType   List<Integer>  树形分类：0规则树  1评分卡的树 2回收站的树
     * organId    long    组织id
     * engineId   long    引擎id
     * @return
     */
    public List getTreeList(Map<String, Object> paramMap) {
        return knowledgeTreeMapper.getTreeList(paramMap);
    }

    /**
     * 对t_knowledge_tree 按主键id查
     * @param id
     * @return
     * 方法通过单元测试
     */
    public KnowledgeTree findById(Long id) {
        return (KnowledgeTree)knowledgeTreeMapper.selectByPrimaryKey(id);
    }

    /**
     * 对t_knowledge_tree 添加数据
     * @param k
     * name     String   目录名称
     * parentId  long    父节点id
     * userId    long    创建人id
     * organId   long  可为空
     * engineId  long  可为空
     * status    int     状态
     * type      int     目录类型
     * treeType  int     树形分类
     * created   Date  取系统当前时间(sql语句中添加now())
     * updated   Date  取系统当前时间(sql语句中添加now())
     * @return
     * 方法通过单元测试
     */
    public boolean insertTree(KnowledgeTree k) {
        int num = knowledgeTreeMapper.insertSelective(k);
        return num >0;
    }

    /**
     * 对t_knowledge_tree 修改数据
     * @param k
     * name    String  可空
     * status    int   可空
     * type      int   可空
     * parentId  long  可空
     * updated   Date  now()
     * id
     * @return
     * 方法通过单元测试
     */
    public boolean updateTree(KnowledgeTree k) {
        int num = knowledgeTreeMapper.updateByPrimaryKeySelective(k);
        return num>0;
    }

    public boolean deleteStatusById(Map<String, Object> map) {
        map.put("id",Long.valueOf(Long.parseLong(map.get("id").toString())));
        map.put("ids", StringUtil.toLongList(map.get("ids").toString()));
        int num = knowledgeTreeMapper.deleteStatusById(map);
        return num>0;
    }

    public int batchCopyKnowledge(Map<String, Object> param) {
        int num = 0;
        Long organId = Long.valueOf(Long.parseLong("" + param.get("newOrganId")));
        Integer type = Integer.valueOf(Integer.parseInt("" + param.get("newType")));
        List klist = getTreeList(param);

        for(Iterator var7 = klist.iterator(); var7.hasNext(); ++num) {
            KnowledgeTree k = (KnowledgeTree)var7.next();
            k.setType(type);
            k.setOrgId(organId);
            param.put("parentId", k.getId());
            insertTree(k);
            param.put("newParentId", k.getId());
            List rlist = ruleService.getRuleList(param);
            ruleService.batchAddRule(param, rlist);
            List slist = scorecardService.queryScorecardList(param);
            scorecardService.batchAddScorecard(param, slist);
            batchCopyChildren(param);
        }

        return num;
    }

    private void batchCopyChildren(Map<String, Object> param) {
        List klist = getTreeList(param);
        Long parentId = Long.valueOf(Long.parseLong("" + param.get("newParentId")));
        Long organId = Long.valueOf(Long.parseLong("" + param.get("newOrganId")));
        Integer type = Integer.valueOf(Integer.parseInt("" + param.get("newType")));
        Iterator pageInfo = klist.iterator();

        while(pageInfo.hasNext()) {
            KnowledgeTree k = (KnowledgeTree)pageInfo.next();
            k.setParentId(parentId);
            k.setOrgId(organId);
            k.setType(type);
            param.put("parentId", k.getId());
            insertTree(k);
            param.put("newParentId", k.getId());
            List rules = ruleService.getRuleList(param);
            ruleService.batchAddRule(param, rules);
            List slist = scorecardService.queryScorecardList(param);
            scorecardService.batchAddScorecard(param, slist);
            batchCopyChildren(param);
        }

    }

    /**
     * 对t_knowledge_tree 批量添加数据
     * @param k
     * name     String   目录名称
     * parentId  long    父节点id
     * userId    long    创建人id
     * organId   long  可为空
     * engineId  long  可为空
     * status    int     状态
     * type      int     目录类型
     * treeType  int     树形分类
     * created   Date  取系统当前时间(sql语句中添加now())
     * updated   Date  取系统当前时间(sql语句中添加now())
     * @return
     * 通过(解决办法，在连接数据库的url中加上allowMultiQueries=true)
     */
    public int batchInsert(List<KnowledgeTree> k) {
        return knowledgeTreeMapper.batchInsert(k);
    }

    /**
     * 对t_knowledge_tree和t_engine_knowledge_tree_rel关联 查询
     * parentId  long       父节点id
     * status    List<int>  状态
     * treeType  List<int>  树形分类
     * organId
     * engineId
     * @param paramMap
     * @return
     */
    public List<KnowledgeTree> getTreeDataForEngine(Map<String, Object> paramMap) {
        return knowledgeTreeMapper.getTreeDataForEngine(paramMap);
    }
}
