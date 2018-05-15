package com.zw.rule.engine.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface EngineApiService {
    /**
     * 规则引擎调用接口（demo测试正常）;<br>
     * @param  paramMap    包含以下参数：<br>
     *  token | String | 必需 | 授权token；<br>
     *  ts|Long| 必需 | 时间戳;<br>
     *  act |String| 必需 | API 动作;<br>
     *  nonce | String| 必需 | API 调用方生成的随机串;<br>
     *  sign | String| 必需 | 签名;<br>
     *  pid | int| 必需 | 进件编号;<br>
     *  uid | String| 必需 | 用户编号;<br>
     *  nonce|String| 必需 | 启用删除标志;<br>
     *  engineId | Long| 必需 | 引擎id;<br>
     *  reqType | int | 必需 | 请求类型;<br>
     *  version | int | 可选 | 引擎版本;<br>
     *  subversion | int | 可选 | 引擎子版本;<br>
     *  organId | Long | 必需 | 组织id;<br>
     * @return JSONObject
     */
    JSONObject engineExecute(Map<String, Object> paramMap);

    Map<String, Object> getEngineVersionExecute(Map<String, Object> map, String versionId);

}
