package com.zw.rule.service.impl;


import com.google.common.base.Strings;
import com.zw.rule.mapper.system.UserRoleDao;
import com.zw.rule.po.UserRole;
import com.zw.rule.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class UserRoleServiceImpl  implements UserRoleService {

    @Resource
    private UserRoleDao userRoleDao;

    @Override
    public List<UserRole> getListByUserId(long userId) {
        return userRoleDao.find("getListByUserId",userId);
    }

    @Override
    public List<String> getRoleIdsByUserId(long userId){
        return userRoleDao.findColumn( "getRoleIdsByUserId", String.class, userId );
    }

    @Override
    public void add(String[] roleIds,long userId) {
        //checkArgument(!Strings.isNullOrEmpty(userId),"用户编号不能为空");
        List list = new ArrayList();list.add(userId);
        userRoleDao.delete("deleteByUserId",list);
        if(!Objects.isNull(roleIds)&& roleIds.length>0){
            for(String roleId : roleIds){
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRoleDao.save(userRole);
            }
        }else{
            userRoleDao.delete("deleteByUserId",list);
        }
    }
}
