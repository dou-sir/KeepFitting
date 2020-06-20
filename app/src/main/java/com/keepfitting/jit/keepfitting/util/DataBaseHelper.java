package com.keepfitting.jit.keepfitting.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 顾志豪 on 2020/6/19.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //自定义构造方法   会创建一个mynote_db作为名字的数据库
    public DataBaseHelper(Context context){
        super(context,"mynote_db",null,1);
    }
    //一般会在onCreate 进行数据库初始化  程序第一次创建时，调用DataBaseHelper构造方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("执行了数据库初始化");
        //1.用户表
        db.execSQL("create table if not exists tb_user(uid integer primary key autoincrement," +
                "nickname String,authtoken String,phone String,sex integer)");
        db.execSQL("insert into tb_user values(1,'a','abc','18551319687',1)");
        //,birthday String,high String,ustate intrger,bmi float,consumeCC float,consumeREE float,standardWeight float,maxHeart float
        //创表如果需要指定长度    uname String(10)       或者写成   uname varchar(10)
        //2.记事本表
        db.execSQL("create table if not exists tb_note(nid integer primary key autoincrement," +
                "uid integer,title String,time String,content String)");
        db.execSQL("insert into tb_note values (1,1,'第一个标题','2019/11/26','这是我的第一个记事本条目')");
        db.execSQL("insert into tb_note values (2,1,'今晚吃什么','2019/11/26','肉')");
        db.execSQL("insert into tb_note values (3,1,'下午喝什么','2019/11/26','咖啡')");
//        db.execSQL("insert into tb_user (uname,upwd) values ('刘','123')");
//        db.execSQL("update tb_note set title='新标题',time='2019/11/27' where nid=1");


        //计划表

        db.execSQL("create table if not exists tb_goal(goalId integer primary key autoincrement,"+
                "userId integer,startData float,goalData float,startTime String,endTime String," +
                "goalType integer, goalStatus integer, goalDescribe String)");




        //计划
        db.execSQL("insert into tb_goal values(0,1,45,40,'2020.6.21','6.25',1,1,'无')");
        db.execSQL("insert into tb_goal values(1,1,50,45,'5.20','5.29',1,0,'无')");
        db.execSQL("insert into tb_goal values(2,1,80,70,'6.1','7.30',1,1,'无')");

        db.execSQL("insert into tb_goal values(3,2,45,40,'2020.6.21','6.25',2,0,'1112')");
        db.execSQL("insert into tb_goal values(4,2,50,45,'5.20','5.29',2,1,'2223')");
        db.execSQL("insert into tb_goal values(5,2,80,70,'6.1','7.30',2,0,'3334')");

        db.execSQL("insert into tb_goal values(6,3,45,40,'2020.6.21','6.25',1,0,'111')");
        db.execSQL("insert into tb_goal values(7,3,50,45,'2020.5.20','5.29',2,1,'222')");
        db.execSQL("insert into tb_goal values(8,3,80,70,'2021.6.1','7.30',1,0,'333')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String sql = "drop table if exists tb_note";
//        db.execSQL(sql);
//        this.onCreate(db);
    }
}
