package com.zw.rule.mapper.system;

import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.mybatis.page.Page;
import com.zw.rule.po.SysOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *          规则引擎
 *Filename：
 *          组织管理mapper
 *Description：
 *
 *Author:
 *		 李开艳
 *Finished：
 *		2017年5月5日
 ********************************************************/
public interface SysOrganizationMapper {
    List<SysOrganization> getAllSysOrganization(ParamFilter param);

    List<SysOrganization> getAllValidOrgan();

    SysOrganization findById(long id);

    int createSysOrganization(SysOrganization sysOrganization);

    int updateSysOrganization(SysOrganization sysOrganization);

    int deleteSysOrganization(@Param("list") List<String> list);

    int updateStatus(@Param("status") int status, @Param("list") List<String> list);

    List<SysOrganization> validateOrganOnly(SysOrganization sysOrganization);

    int countSysOrganization(Map map);
}
