package com.keepfitting.jit.keepfitting.dao;

import com.keepfitting.jit.keepfitting.entity.User;

/**
 * Created by 顾志豪 on 2020/6/19.
 */

public interface UserDao  {

    public User findUserByPhone(String phone) throws Exception;
    //注册用户
    public void RegistUsers(User user);
    //登录
    public User LoginUsers(String phone);

    //获取uid
    public int getUseruid(String phone);

    //根据uid获取生日
    public String getUserBirth(int uid);

    //判断用户是否存在
    public User ifUserHave(String uname);
}
