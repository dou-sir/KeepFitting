package com.keepfitting.jit.keepfitting.service.impl;

import android.content.Context;

import com.keepfitting.jit.keepfitting.dao.UserDao;
import com.keepfitting.jit.keepfitting.dao.impl.UserDaoImpl;
import com.keepfitting.jit.keepfitting.entity.User;
import com.keepfitting.jit.keepfitting.service.UserService;



public class UserServiceImpl  implements UserService {


    private UserDao userDao ;
    public UserServiceImpl(Context context){
        userDao = new UserDaoImpl(context);}

//
//    @Override
//    public boolean userRegist(User user) {
//        return false;
//    }
//
//    @Override
//    public int getUid(String uname) {
//        return 0;
//    }
}
