package com.keepfitting.jit.keepfitting.dao;

import com.keepfitting.jit.keepfitting.entity.User;


public interface UserDao  {

    /**
     * 查询单个用户
     */
    public User findUserByUserID(int userID) ;
    public User findUserByPhone(String phone) ;

    /**
     * 查询系统中已登录的用户ustate=1
     * @return
     */
    public User findUserByUstate() ;

    /**
     * 添加用户
     * @return
     */
    public User addUser(User user) ;

    /**
     * 修改用户
     * @return
     */
    public void modifyUser(User user) ;




//    public User findUserByPhone(String phone) throws Exception;
//    //注册用户
//    public void RegistUsers(User user);

    /**账号密码登录
     * @param
     * @return
     */
    public User LoginUsers(String nickname);
//
//    //获取uid
//    public int getUseruid(String phone);
//
//    //根据uid获取生日
//    public String getUserBirth(int uid);
//
//    //判断用户是否存在
//    public User ifUserHave(String uname);
}
