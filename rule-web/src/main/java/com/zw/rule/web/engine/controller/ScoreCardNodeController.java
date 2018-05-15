package com.zw.rule.web.engine.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.engine.po.NodeKnowledge;
import com.zw.rule.engine.service.NodeKnowledgeService;
import com.zw.rule.jeval.tools.StringUtil;
import com.zw.rule.knowledge.po.Scorecard;
import com.zw.rule.knowledge.service.ScorecardService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/5/12 0012.
 */
@Controller
@RequestMapping({"cardNode"})
public class ScoreCardNodeController {
    public ScoreCardNodeController() {
    }
    @Resource
    private NodeKnowledgeService nodeKnowledgeService;
    @Resource
    private ScorecardService scorecardService;

    @RequestMapping({"/cardList"})
    @WebLogger("获取评分卡列表")
    @ResponseBody
    public Response queryCardNode(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        if(queryFilter.getParam().containsKey("status")) {
            List status = StringUtil.toLongList(queryFilter.getParam().get("status").toString());
            queryFilter.getParam().put("status",status);
        }
        List list = scorecardService.queryScorecardList(queryFilter.getParam());
        Long nodeId = Long.parseLong(queryFilter.getParam().get("nodeId").toString());
        if(nodeId != null && nodeId.longValue() != 0L) {
            NodeKnowledge nodeKnowledge = nodeKnowledgeService.queryByNodeId(nodeId, Long.valueOf(2L));
            if(nodeKnowledge != null) {
                long knowledgeId = nodeKnowledge.getKnowledgeId().longValue();
                if(list != null && list.size() > 0) {
                    for(int i = 0; i < list.size(); i++) {
                        long cardId = ((Scorecard)list.get(i)).getId().longValue();
                        if(cardId == knowledgeId) {
                            ((Scorecard)list.get(i)).setChecked(true);
                        }
                    }
                }
            }
        }
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

}
