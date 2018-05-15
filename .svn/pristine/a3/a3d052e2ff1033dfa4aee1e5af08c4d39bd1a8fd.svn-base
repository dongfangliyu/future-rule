package com.zw.rule.service;

import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.SysOrganization;
import com.zw.rule.po.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *          规则引擎
 *Filename：
 *          组织管理service
 *Description：
 *
 *Author:
 *		 李开艳
 *Finished：
 *		2017年5月5日
 ********************************************************/
public interface SysOrganizationService  {
    List<SysOrganization> getAllSysOrganization(ParamFilter param);

    List<SysOrganization> getAllValidOrgan();

    SysOrganization findById(long id);

    int createSysOrganization(SysOrganization sysOrganization, User currentUser);

    int updateSysOrganization(SysOrganization sysOrganization);

    int deleteSysOrganization(List<String> ids);

    int updateStatus(@Param("status") int status, @Param("list") List<String> list);

    List<SysOrganization> validateOrganOnly(SysOrganization sysOrganization);

    int countSysOrganization(Map map);
}
