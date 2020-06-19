package com.keepfitting.jit.keepfitting.service;

import com.keepfitting.jit.keepfitting.entity.User;

/**
 * Created by 顾志豪 on 2020/6/19.
 */

public interface UserService {



    public boolean userRegist(User user);
    //重复
//    public List<User> findUserByPhone(String phone);
    //登录操作
   public boolean checkLogin(String phone);

    //获取uid
    public int getUid(String uname);
}
