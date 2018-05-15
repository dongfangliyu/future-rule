package com.zw.rule.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.zw.base.util.DigestUtil;
import com.zw.base.util.RegexUtil;
import com.zw.rule.mapper.system.UserDao;
import com.zw.rule.mapper.system.UserRoleDao;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.User;
import com.zw.rule.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;
    @Resource
    private UserRoleDao userRoleDao;

    @Override
    public List getList(ParamFilter param) {
        return userDao.findMap("getList", param.getParam(), param.getPage());
    }

    @Override
    public void updateDefaultPwd(List<Long> userIds) {
        checkArgument((userIds != null && userIds.size() > 0), "用户编号不能为空");
        for (long userId : userIds) {
            User user = userDao.findUnique("getByUserId", userId);
            checkNotNull(user, "用户不存在");
        }
        for (long userId : userIds) {
            String defaultPwd = DigestUtil.sha256().digest("123456");
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("defaultPwd", defaultPwd);
            paramMap.put("userId", userId);
            userDao.update("updateDefaultPwd", paramMap);
        }
    }

    @Override
    public void update(User user) {
        checkNotNull(user, "用户不能为空");
        checkArgument(!Strings.isNullOrEmpty(user.getAccount()), "帐号名不能为空");
/*        checkArgument(!Strings.isNullOrEmpty(user.getPassword()), "密码不能为空");*/
        checkArgument(!Strings.isNullOrEmpty(user.getTel()), "手机号码不能为空");
        //checkNotNull(user.getIsLock(), "帐号名不能为空");
        checkArgument(RegexUtil.isMobile(user.getTel()), "手机号码格式不正确");
        if (!Strings.isNullOrEmpty(user.getEmail())) {
            checkArgument(RegexUtil.isEmail(user.getEmail()), "邮箱格式不正确");
        }
        User model = userDao.findUnique("getByUserId", user.getUserId());
        checkNotNull(model, "用户信息不存在");
        String account = user.getAccount();
        Map<String , Object> map = new HashMap<>();
        map.put("userId",user.getUserId());
        map.put("account",account);
        User model2 = userDao.findUnique("isExists", map);
        checkArgument(model2 == null, "用户已存在");
        //修改用户信息时不修改密码
        user.setPassword(model.getPassword());
        user.setNickName(user.getTrueName());
        user.setUpdateTime(new Date());
        userDao.update("updateUser", user);
    }

    @Override
    @Transactional
    public void add(User user) {
        checkNotNull(user, "用户不能为空");
        checkArgument(!Strings.isNullOrEmpty(user.getAccount()), "帐号名不能为空");
        checkArgument(!Strings.isNullOrEmpty(user.getPassword()), "密码不能为空");
        checkArgument(!Strings.isNullOrEmpty(user.getTel()), "手机号码不能为空");
        checkArgument(RegexUtil.isMobile(user.getTel()), "手机号码格式不正确");
        if (!Strings.isNullOrEmpty(user.getEmail())) {
            checkArgument(RegexUtil.isEmail(user.getEmail()), "邮箱格式不正确");
        }

        String account = user.getAccount();
        User model = userDao.findUnique("getByAccount", account);
        checkArgument(model == null, "用户已存在");
        String password = DigestUtil.sha256().digest(user.getPassword());
        user.setErrorCount(BigInteger.ZERO.intValue());
        user.setPassword(password);
        user.setNickName(user.getTrueName());
        userDao.save(user);
    }

    @Override
    public void updatePwd(String originPwd, String confirmPwd, String newPwd, long userId) {
        checkArgument(!Strings.isNullOrEmpty(originPwd), "原密码不能为空");
        checkArgument(!Strings.isNullOrEmpty(confirmPwd), "确认密码不能为空");
        checkArgument(!Strings.isNullOrEmpty(newPwd), "新密码不能为空");
        checkArgument(confirmPwd.equals(newPwd), "新密码与确认密码不一致");
        User user = userDao.findUnique("getByUserId", userId);
        checkNotNull(user, "用户对象不存在");
        checkArgument(user.getPassword().equals(DigestUtil.sha256().digest(originPwd)), "原密码不正确");

        String newPassword = DigestUtil.sha256().digest(confirmPwd);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("newPassword", newPassword);
        paramMap.put("userId", userId);
        userDao.update("updatePwd", paramMap);
    }

    @Override
    public void delete(List<Long> userIds) {
        checkArgument((userIds != null && userIds.size() > 0), "用户编号不能为空");
        userDao.delete("deleteByUserId", userIds);
        userRoleDao.delete("deleteByUserId", userIds);
    }

    @Override
    public Map getDetail(long userId) {
        //checkArgument(!Strings.isNullOrEmpty(userId), "用户编号不能为空");
        Map<String, Object> resultMap = Maps.newHashMap();
        Map userMap = userDao.findOneColumn("getUserDetail", Map.class, userId);
        checkNotNull(userMap, "用户对象不存在");
        resultMap.put("user", userMap);
        return resultMap;
    }

    @Override
    public User getByUserId(long userId) {
        //checkArgument(!Strings.isNullOrEmpty(userId), "用户编号不能为空");
        return userDao.findUnique("getByUserId", userId);
    }

    @Override
    public List<String> getPermission(String account) {
        return userDao.findColumn("getPermission", String.class, account);
    }
    @Override
    public List<String> getAllPermission() {
        return userDao.findColumn("getAllPermission", String.class, "");
    }

}
