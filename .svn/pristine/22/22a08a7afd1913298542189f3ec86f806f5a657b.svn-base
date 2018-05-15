package com.zw.rule.service.impl;

import com.zw.rule.mapper.system.*;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.Role;
import com.zw.rule.po.SysOrganization;
import com.zw.rule.po.User;
import com.zw.rule.service.SysOrganizationService;
import com.zw.rule.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *          规则引擎
 *Filename：
 *          组织管理serviceImpl
 *Description：
 *
 *Author:
 *		 李开艳
 *Finished：
 *		2017年5月5日
 ********************************************************/
@Service
public class SysOrganizationServiceImpl implements SysOrganizationService {

    @Resource
    public SysOrganizationMapper sysOrganizationMapper;

    @Resource
    private UserDao userDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private UserRoleDao userRoleDao;
    @Resource
    private RoleMenuDao roleMenuDao;

    public List<SysOrganization> getAllSysOrganization(ParamFilter param) {
        int resultCount = this.countSysOrganization(param.getParam());
        param.getPage().setResultCount(resultCount);
        return sysOrganizationMapper.getAllSysOrganization(param);
    }

    public List<SysOrganization> getAllValidOrgan() {
        return sysOrganizationMapper.getAllValidOrgan();
    }

    public SysOrganization findById(long id) {
        return sysOrganizationMapper.findById(id);
    }

    public int createSysOrganization(SysOrganization sysOrganization, User currentUser) {
        String nickName = currentUser.getAccount();
        sysOrganization.setCreator(nickName);
        String uuid = UUID.randomUUID().toString();
        sysOrganization.setToken(uuid);
        return sysOrganizationMapper.createSysOrganization(sysOrganization);
    }

    /**
     * 组织停用，联动停用 用户，角色
     * @param sysOrganization
     * @return
     */
    public int updateSysOrganization(SysOrganization sysOrganization) {
        //判断该组织是否停用
        if(sysOrganization.getStatus()==0){
            long orgId = sysOrganization.getId();
            userDao.update("updateStatusByOrgId", orgId);
            roleDao.update("updateStatusByOrgId", orgId);
        }
        return sysOrganizationMapper.updateSysOrganization(sysOrganization);
    }

    /**
     * 删除组织，联动删除用户，角色，用户-角色，角色-菜单
     * @param ids
     * @return
     */
    public int deleteSysOrganization(List<String> ids) {
        int num = sysOrganizationMapper.deleteSysOrganization(ids);
        userDao.delete("deleteByOrgId", ids);
        userRoleDao.delete("deleteByOrgId", ids);
        roleDao.delete("deleteByOrgId", ids);
        //通过组织id查出组织下的所有角色id
        List roleIds = roleDao.findColumn("getRoleIdByOrgId",String.class, ids);
        //如果组织下创建了角色，删除组织下所有角色的关联的菜单权限
        if(roleIds!=null&&roleIds.size()>0){
            roleMenuDao.update("deleteByRoleId", roleIds);
        }
        return num;
    }

    //未用
    public int updateStatus(int status, List<String> list) {
        /*this.sysRoleMapper.deleteRolesByOrgans(Integer.valueOf(status), list);
        this.sysUserMapper.deleteUsersByOrgans(Integer.valueOf(status), list);
        this.sysUserMapper.deleteUserRoleByOrgan(Integer.valueOf(status), list);*/
        int num = sysOrganizationMapper.updateStatus(status, list);
        return num;
    }

    public List<SysOrganization> validateOrganOnly(SysOrganization SysOrganization) {
        return sysOrganizationMapper.validateOrganOnly(SysOrganization);
    }

    @Override
    public int countSysOrganization(Map map) {
        return sysOrganizationMapper.countSysOrganization(map);
    }
}
