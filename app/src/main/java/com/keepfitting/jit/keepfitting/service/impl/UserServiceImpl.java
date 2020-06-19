package com.keepfitting.jit.keepfitting.service.impl;

import android.content.Context;

import com.keepfitting.jit.keepfitting.dao.UserDao;
import com.keepfitting.jit.keepfitting.dao.impl.UserDaoImpl;
import com.keepfitting.jit.keepfitting.entity.User;
import com.keepfitting.jit.keepfitting.service.UserService;

/**
 * Created by 顾志豪 on 2020/6/19.
 */

public class UserServiceImpl  implements UserService {


    private UserDao userDao ;
    public UserServiceImpl(Context context){
        userDao = new UserDaoImpl(context);}
    /**
     *
     * @param user  传入参数  （EditText 获取到的内容）
     * @return
     * @throws Exception
     */

    @Override
    public boolean userRegist(User user) {
        return false;
    }

    @Override
    public int getUid(String uname) {
        return 0;
    }
}
