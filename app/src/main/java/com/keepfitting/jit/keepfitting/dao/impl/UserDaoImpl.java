package com.keepfitting.jit.keepfitting.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.keepfitting.jit.keepfitting.dao.UserDao;
import com.keepfitting.jit.keepfitting.entity.User;
import com.keepfitting.jit.keepfitting.util.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl implements UserDao {

    private  DataBaseHelper dbHelper;

    public UserDaoImpl(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    @Override
    public User findUserByUstate() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "Select * from tb_user where ustate=1";
        Cursor cursor = db.rawQuery(sql,null);
        User user = new User();
        //原来的while循环移到了另一个函数里
        user = getUserFromDB(user,cursor);

        return user;
    }

    @Override
    public User findUserByUserID(int userID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "Select * from tb_user where userId=?";
        Cursor cursor = db.rawQuery(sql,new String[]{userID+""});
        User user = new User();
        //原来的while循环移到了另一个函数里
        user = getUserFromDB(user,cursor);

        return user;
    }

    @Override
    public User findUserByPhone(String phone) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "Select * from tb_user where phone=?";
        Cursor cursor = db.rawQuery(sql,new String[]{phone});
        User user = new User();
        //原来的while循环移到了另一个函数里
        user = getUserFromDB(user,cursor);

        return user;
    }

    /**
     *getWritableDatabase取得的实例是以读写的方式打开数据库，如果打开的数据库磁盘满了，此时只能读不能写，此时调用了getWritableDatabase的实例，那么将会发生错误（异常）
     *getReadableDatabase取得的实例是先调用getWritableDatabase以读写的方式打开数据库，如果数据库的磁盘满了，此时返回打开失败，继而用getReadableDatabase的实例以只读的方式去打开数据库
     */
    @Override
    public User addUser(User user) {
        String sql= "insert into tb_user (nickname, phone, ustate)values(?, ?, ?)";
        //获取可写入数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql,new Object[]{user.getNickname(), user.getPhone(), 1});
        db.close();

        return user;
    }

    @Override
    public void modifyUser(User user) {
        String sql= "update tb_user set nickname=?, authtoken=?, phone=?, sex=?, birthday=?, high=?, " +
                "BMI=?, intakeCC=?, consumeCC=?, consumeREE=?, dayrate=?, standardweight=?, maxHeart=?, ustate=? " +
                "where userId=?";
        //获取可写入数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql,new Object[]{user.getNickname(), user.getAuthToken(), user.getPhone(), user.getSex(), user.getBirthday(),
                user.getHigh(), user.getBMI(), user.getIntakeCC(), user.getConsumeCC(), user.getConsumeREE(),
                user.getDayrate(), user.getStandardWeight(), user.getMaxHeart(), user.getUstate() ,user.getUserID()});
        db.close();
    }


    private User getUserFromDB(User user, Cursor cursor){
        while (cursor.moveToNext()){
            user = new User(
                    cursor.getInt(cursor.getColumnIndex("userId")),//顺序与user构造一致
                    cursor.getString(cursor.getColumnIndex("nickname")),
                    cursor.getString(cursor.getColumnIndex("authToken")),
                    cursor.getString(cursor.getColumnIndex("phone")),
                    cursor.getInt(cursor.getColumnIndex("sex")),
                    cursor.getString(cursor.getColumnIndex("birthday")),
                    cursor.getString(cursor.getColumnIndex("high")),
                    cursor.getFloat(cursor.getColumnIndex("BMI")),
                    cursor.getFloat(cursor.getColumnIndex("intakeCC")),
                    cursor.getFloat(cursor.getColumnIndex("consumeCC")),
                    cursor.getFloat(cursor.getColumnIndex("consumeREE")),
                    cursor.getFloat(cursor.getColumnIndex("dayrate")),
                    cursor.getFloat(cursor.getColumnIndex("standardweight")),
                    cursor.getFloat(cursor.getColumnIndex("maxHeart")),
                    cursor.getInt(cursor.getColumnIndex("ustate"))
            );
        }
        return user;
    }

//    private static List<User> userList = null;
//    private DataBaseHelper dbHelper;
//    public UserDaoImpl(Context context){
//        dbHelper = new DataBaseHelper(context);}
//
//    @Override
//    public User findUserByPhone(String phone) throws Exception {
//        return null;
//    }
//
//    @Override
//    public void RegistUsers(User user) {
//
//        String sql = "insert into tb_user(phone) values(?)";
//        //获取可写入数据库
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
////        userList.add(new User(user.getUname(),user.getUpwd()));
//
//
//        //new Object[]{nid}  nid的值会赋值给 delete from tb_note where nid+？的问号
//        //如果有多个？需要传入多个参数
//        db.execSQL(sql,new String []{user.getPhone()});
//        db.close();
//
//    }
//
//    @Override
//    public User LoginUsers(String phone) {
//        return null;
//    }
//
//    @Override
//    public int getUseruid(String phone) {
//        return 0;
//    }
//
//    @Override
//    public String getUserBirth(int uid) {
//        return null;
//    }
//
//    @Override
//    public User ifUserHave(String uname) {
//        return null;
//    }

}
