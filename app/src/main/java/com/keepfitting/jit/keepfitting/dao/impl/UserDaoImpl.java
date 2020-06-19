package com.keepfitting.jit.keepfitting.dao.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.keepfitting.jit.keepfitting.dao.UserDao;
import com.keepfitting.jit.keepfitting.entity.User;
import com.keepfitting.jit.keepfitting.util.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 顾志豪 on 2020/6/19.
 */

public class UserDaoImpl implements UserDao {



    private static List<User> userList = null;
    private DataBaseHelper dbHelper;
    public UserDaoImpl(Context context){
        dbHelper = new DataBaseHelper(context);}



    //模拟数据生成
    static {
        userList = new ArrayList<User>();
     userList.add(new User(1,"a","abc","18551319687",1,"177","77",2,3,2,2,6,5,41));
   //     userList.add(new User(1,"adc","sxc","1111",0,"吃书我","d"));



    }

    @Override
    public User findUserByPhone(String phone) throws Exception {
        return null;
    }

    @Override
    public void RegistUsers(User user) {

        String sql = "insert into tb_user(phone) values(?)";
        //获取可写入数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        userList.add(new User(user.getUname(),user.getUpwd()));


        //new Object[]{nid}  nid的值会赋值给 delete from tb_note where nid+？的问号
        //如果有多个？需要传入多个参数
        db.execSQL(sql,new String []{user.getPhone()});
        db.close();

    }

    @Override
    public User LoginUsers(String phone) {
        return null;
    }

    @Override
    public int getUseruid(String phone) {
        return 0;
    }

    @Override
    public String getUserBirth(int uid) {
        return null;
    }

    @Override
    public User ifUserHave(String uname) {
        return null;
    }
}
