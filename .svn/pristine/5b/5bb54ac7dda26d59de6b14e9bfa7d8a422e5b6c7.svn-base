package com.zw.rule.engine.util;

import com.zw.rule.engine.po.EngineNode;
import com.zw.rule.jeval.tools.CollectionUtil;
import com.zw.rule.util.StringUtil;

import java.util.*;

/**
 * Created by Administrator on 2017/6/21.
 */
public class EngineUtil {

    public static Map<Long, EngineNode> convertNodeList2Map(List<EngineNode> nodes) {
        HashMap nodeMap = new HashMap();
        if(CollectionUtil.isNotNullOrEmpty(nodes)) {
            Iterator var3 = nodes.iterator();

            while(var3.hasNext()) {
                EngineNode engineNode = (EngineNode)var3.next();
                nodeMap.put(engineNode.getNodeId(), engineNode);
            }
        }

        return nodeMap;
    }

    public static Map<String, EngineNode> convertNodeList2MapNodeCodeKey(List<EngineNode> nodes) {
        HashMap nodeMap = new HashMap();
        if(CollectionUtil.isNotNullOrEmpty(nodes)) {
            Iterator var3 = nodes.iterator();

            while(var3.hasNext()) {
                EngineNode engineNode = (EngineNode)var3.next();
                nodeMap.put(engineNode.getNodeCode(), engineNode);
            }
        }

        return nodeMap;
    }

    public static List<EngineNode> getUpdateParentIdNodes(EngineNode engineNode, List<EngineNode> nodeList) {
        Map nodeMap = convertNodeList2MapNodeCodeKey(nodeList);
        String nextNodes = engineNode.getNextNodes();
        ArrayList resultNodes = new ArrayList();
        if(StringUtil.isValidStr(nextNodes)) {
            if(nextNodes.contains(",")) {
                String[] nextNode = nextNodes.split(",");
                EngineNode nextNode1 = null;
                String[] var10 = nextNode;
                int var9 = nextNode.length;

                for(int var8 = 0; var8 < var9; ++var8) {
                    String nextNodeCode = var10[var8];
                    nextNode1 = (EngineNode)nodeMap.get(nextNodeCode);
                    nextNode1.setParentId((Long)null);
                    resultNodes.add(nextNode1);
                }
            } else {
                EngineNode var11 = (EngineNode)nodeMap.get(nextNodes);
                var11.setParentId((Long)null);
                resultNodes.add(var11);
            }
        }

        return resultNodes;
    }

}
