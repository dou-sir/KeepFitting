package com.keepfitting.jit.keepfitting.service.impl;

import android.content.Context;

import com.keepfitting.jit.keepfitting.dao.FigureDao;
import com.keepfitting.jit.keepfitting.dao.UserDao;
import com.keepfitting.jit.keepfitting.dao.impl.FigureDaoImpl;
import com.keepfitting.jit.keepfitting.dao.impl.UserDaoImpl;
import com.keepfitting.jit.keepfitting.entity.Figure;
import com.keepfitting.jit.keepfitting.entity.User;
import com.keepfitting.jit.keepfitting.service.UserService;

public class UserServiceImpl  implements UserService {


    private UserDao userDao ;
    private FigureDao figureDao;

    public UserServiceImpl(Context context){
        userDao = new UserDaoImpl(context);
        figureDao = new FigureDaoImpl(context);
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
            return userDao.findUserByPhone(user.getPhone());

        user.setNickname(user.getPhone());
        userDao.addUser(user);
        return userDao.findUserByPhone(user.getPhone());

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

    @Override
    public float getWeightByUserID(int UserID) {
        int size = figureDao.findFigureByType(UserID,"weight").size();
        if (size==0){
            return 0;
        }
        Float weight = figureDao.findFigureByType(UserID,"weight").get(size-1).getFigureData();
        return weight;
    }

    @Override
    public boolean setWeightByFigure(Figure figure) {
        //尝试添加用户体重数据
        int size = figureDao.findFigureByType(figure.getUserId(),figure.getFigureType()).size();
        if (size!=0){
            Figure recentFigure = figureDao.findFigureByType(figure.getUserId(),figure.getFigureType()).get(size-1);
            if (recentFigure.getRecordDate().equals(figure.getRecordDate())) {
                //修改用户体重数据
                try {
                    figureDao.modifyFigure(figure);
                    return true;
                }catch (Exception e){
                    return false;
                }
            }
        }
        figureDao.addFigure(figure);
        return true;
    }

    @Override
    public int getNeedCalByUserId(int userId) {
        User user = userDao.findUserByUserID(userId);
        float ree = user.getConsumeREE();
        float dayRate = user.getDayrate();
        //判断为空或者0
        if(ree!=0&&dayRate!=0){
            int needCal = (int) Math.round(user.getConsumeREE()*user.getDayrate());
            return needCal;
        }else {
            return 2000;
        }
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
