package com.zw.rule.service.impl;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.zw.rule.mapper.system.RoleDao;
import com.zw.rule.mapper.system.RoleMenuDao;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.Role;
import com.zw.rule.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;

    @Resource
    private RoleMenuDao roleMenuDao;

    @Override
    public void add(Role role) {
        checkNotNull(role,"角色信息不能为空");
        checkArgument(!Strings.isNullOrEmpty(role.getRoleName()),"角色名称不能为空");
        checkArgument(!Strings.isNullOrEmpty(role.getRoleCode()),"角色标识不能为空");

        Map<String, Object> param = Maps.newHashMap();
        param.put("roleCode", role.getRoleCode());
        param.put("orgId", role.getOrgId());
        Role model = roleDao.findUnique("getRoleBySign", param);
        checkArgument(model==null,"角色编号已经存在");
        String roleId = UUID.randomUUID().toString();
        role.setRoleId(roleId);
        roleDao.save("addRole", role);
    }

    @Override
    public void delete(List<String> roleIds) {
        for (String roleId : roleIds) {
            Role model = roleDao.findUnique("getRoleByRoleId", roleId);
            checkNotNull(model,"角色对象不存在");
        }
        roleDao.delete("deleteByRoleId", roleIds);
        roleMenuDao.update("deleteByRoleId", roleIds);
    }

    @Override
    public void update(Role role) {
        checkNotNull(role,"角色信息不能为空");
        checkArgument(!Strings.isNullOrEmpty(role.getRoleName()),"角色名称不能为空");
        checkArgument(!Strings.isNullOrEmpty(role.getRoleCode()),"角色标识不能为空");

        Role model = roleDao.findUnique("getRoleById", role.getRoleId());
        checkNotNull(model,"角色对象不存在");
        Map<String, Object> param = Maps.newHashMap();
        param.put("roleCode", role.getRoleCode());
        param.put("roleId", role.getRoleId());
        model = roleDao.findUnique("getRoleBySignAndNoRoleId", param);
        checkArgument(model==null,"角色标识已存在");

        param = Maps.newHashMap();
        //param.put("sign", role.getSign());
        param.put("roleName", role.getRoleName());
        param.put("roleDesc", role.getRoleDesc());
        param.put("updateTime", new Date());
        param.put("roleCode", role.getRoleCode());
        param.put("roleId", role.getRoleId());
        param.put("status", role.getStatus());
        roleDao.update("updateRole", param);
    }

    @Override
    public List<Role> getList(ParamFilter paramFilter) {
        return roleDao.find("getRoleList", paramFilter.getParam(), paramFilter.getPage());
    }

    @Override
    public Role getByRoleId(String roleId) {
        checkArgument(!Strings.isNullOrEmpty(roleId),"角色编号不能为空");
        Role role = roleDao.findUnique("getRoleByRoleId", roleId);
        checkNotNull(role,"帐号未分配角色或权限");
        return role;
    }

    @Override
    public List getRoleMap(long userId,Integer organId) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("userId", userId);
        param.put("organId", organId);
        return roleDao.findMap( "getRoleIdAndName",param);
    }

    @Override
    public Role getRoleById(String roleId) {
        checkArgument(!Strings.isNullOrEmpty(roleId),"角色编号不能为空");
        Role role = roleDao.findUnique("getRoleById", roleId);
        return role;
    }

}
