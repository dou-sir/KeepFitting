package com.keepfitting.jit.keepfitting.service.impl;

import android.content.Context;

import com.keepfitting.jit.keepfitting.dao.UserDao;
import com.keepfitting.jit.keepfitting.dao.impl.UserDaoImpl;
import com.keepfitting.jit.keepfitting.entity.User;
import com.keepfitting.jit.keepfitting.service.UserService;

public class UserServiceImpl  implements UserService {


    private UserDao userDao ;

    public UserServiceImpl(Context context){
        userDao = new UserDaoImpl(context);
    }

    @Override
    public User findUserByUstate() {
        return userDao.findUserByUstate();
    }

    @Override
    public User findUserByUserID(int userID) {
        return userDao.findUserByUserID(userID);
    }

    @Override
    public User findUserByPhone(String phone) {
        return userDao.findUserByPhone(phone);
    }

    @Override
    public User addUser(User user) {
        if(userDao.findUserByPhone(user.getPhone()).getNickname()!=null)
            return new User();

        user.setNickname(user.getPhone());
        return userDao.addUser(user);
    }

    @Override
    public boolean modifyUser(User user) {
        try {
            userDao.modifyUser(user);
            return true;
        }catch (Exception e){
            //
            return false;
        }
    }

    @Override
    public int checkLogin(String nickname, String authToken) {
        User user = userDao.LoginUsers(nickname);
        if (null!=user.getAuthToken()&&user.getAuthToken().equals(authToken)){
            return user.getUserID();
        }
        return 0;

    }

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
