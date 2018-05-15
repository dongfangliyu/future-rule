package com.zw.rule.web.engine.controller;

import com.alibaba.fastjson.JSONObject;
import com.zw.rule.core.Response;
import com.zw.rule.engine.service.EngineApiService;
import com.zw.rule.jeval.tools.MD5;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12 0012.
 */
@Controller
@RequestMapping({"/QueryString"})
public class EngineApiController {
    public EngineApiController() {
    }
    @Resource
    private EngineApiService engineApiService;

    @RequestMapping(
            value = {"/decision"},
            method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Response decision(@RequestParam Map<String, Object> map) {

        JSONObject jsonObject = JSONObject.parseObject((String) map.get("paramJson"));
        if(jsonObject.getInteger("reqType").intValue() == 2) {
            map.put("version", jsonObject.getInteger("version"));
            map.put("subversion", jsonObject.getInteger("subversion"));
        }

        map.put("reqType", jsonObject.getInteger("reqType"));
        map.put("engineId", jsonObject.getLong("engineId"));
        map.put("organId", jsonObject.getLong("organId"));
        map.put("sign", jsonObject.getString("sign"));
        map.put("apiType",1);
        JSONObject result = new JSONObject();
        Date date = new Date();
        //发起请求的时间和服务收到请求的时间差在10秒内 and 签名正常
        String newsign = MD5.GetMD5Code(map.get("act") + "," + map.get("ts") + "," + map.get("nonce") + "," + map.get("pid") + "," + map.get("uid") + "," + map.get("token"));
        if(date.getTime() - Long.valueOf(map.get("ts").toString()).longValue() < 10000L && newsign.equals(map.get("sign").toString())) {
            result = this.engineApiService.engineExecute(map);
        }else{
            result.put("status", "0x0001");
            result.put("msg", "鉴权失败，非法调用");
            result.put("data", "");
        }


        Response response = new Response();
        response.setData(result.get("data"));
        response.setResult(result.get("result"));
        response.setStatus(result.get("status"));
        response.setMsg((String)result.get("msg"));
        response.setScore(result.get("score"));
        return response;
    }
}
