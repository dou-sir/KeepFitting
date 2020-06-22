package com.keepfitting.jit.keepfitting.service;

import com.keepfitting.jit.keepfitting.entity.User;



public interface UserService {
    /**
     * 查询单个用户
     * 使用后检查user名是否为空.getNickname()!=null
     * 空则没查询到
     */
    public User findUserByUserID(int userID) ;
    public User findUserByPhone(String phone) ;

    /**
     * 查询系统中已登录的用户ustate=1
     * 打开APP时启用
     * @return
     */
    public User findUserByUstate() ;

    /**
     * 添加用户
     * 添加用户前执行了findUserByPhone，确保手机号无重复
     * @return
     */
    public User addUser(User user) ;

    /**
     * 修改用户
     * @return
     */
    public boolean modifyUser(User user) ;


    //public boolean userRegist(User user);
    //重复
//    public List<User> findUserByPhone(String phone);
    //登录操作
  // public boolean checkLogin(String phone);

    //获取uid
    //public int getUid(String uname);

}
