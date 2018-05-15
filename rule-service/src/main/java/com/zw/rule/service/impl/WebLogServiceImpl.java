package com.zw.rule.service.impl;

import com.zw.rule.mapper.system.WebLogDao;
import com.zw.rule.po.WebLog;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.service.WebLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class WebLogServiceImpl implements WebLogService {

    @Resource
    private WebLogDao webLogDao;

    @Override
    public void add(WebLog webLog) {
        checkNotNull(webLog,"操作日志不能为空");
        webLogDao.save(webLog);
    }

    @Override
    public void delete(String[] webLogIds) {
        checkArgument(webLogIds!=null&& webLogIds.length>0,"操作日志编号不能为空");
        for(String webLogId : webLogIds){
            webLogDao.delete("delete",webLogId);
        }
    }

    @Override
    public void update(WebLog webLog) {
        checkNotNull(webLog,"操作日志不能为空");
        webLogDao.update(webLog);
    }

    @Override
    public List<WebLog> getList(ParamFilter paramFilter) {
        return webLogDao.find("getList", paramFilter.getParam(), paramFilter.getPage());
    }
}
