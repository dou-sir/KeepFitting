package com.keepfitting.jit.keepfitting.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //自定义构造方法   会创建一个fitness_db作为名字的数据库
    public DataBaseHelper(Context context){
        super(context,"fitness_db",null,1);
    }
    //一般会在onCreate 进行数据库初始化  程序第一次创建时，调用DataBaseHelper构造方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        //1.用户表 autoincrement自增长
        db.execSQL("create table if not exists tb_user(userId integer primary key autoincrement," +
                "nickname String,authToken String,phone String,sex integer, birthday String, high String, " +
                "BMI float, intakeCC float, consumeCC float, consumeREE float, dayrate float, standardweight float, maxHeart float, ustate int)");

        //,birthday String,high String,ustate intrger,bmi float,consumeCC float,consumeREE float,standardWeight float,maxHeart float
        //创表如果需要指定长度    uname String(10)       或者写成   uname varchar(10)
//        db.execSQL("insert into tb_user (uname,upwd) values ('刘','123')");
//        db.execSQL("update tb_note set title='新标题',time='2019/11/27' where nid=1");

        //2.计划表
        db.execSQL("create table if not exists tb_goal(goalId integer primary key autoincrement,"+
                "userId integer,startData float,goalData float,startTime String,endTime String," +
                "goalType integer, goalStatus integer, goalDescribe String)");

        //3、身材记录表
        db.execSQL("create table if not exists tb_figure(figureId integer primary key autoincrement,"+
                "userId int,figureType String,figureData String,recordDate String)");

        //user数据
        db.execSQL("insert into tb_user values (0,'cxk','123','233',1,'1999-09-09','170',22.5,2000,1800,1607.5,1.2,63,165,0)");
        db.execSQL("insert into tb_user values (1,'dyy','123','456',1,'1999-09-09','170',22.5,2000,1800,1607.5,1.2,63,165,0)");
        db.execSQL("insert into tb_user values (2,'xy','123','453',1,'1999-09-09','170',22.5,2000,1800,1607.5,1.2,63,165,0)");
        db.execSQL("insert into tb_user values (3,'cwn','123','4512',1,'1999-09-09','170',22.5,2000,1800,1607.5,1.2,63,165,0)");
        db.execSQL("insert into tb_user values (4,'gzh','123','2545',1,'1999-09-09','170',22.5,2000,1800,1607.5,1.2,63,165,0)");
//        db.execSQL("insert into tb_user (userId,nickname,authtoken,phone) values (1,'dyy','123','123')");
//        db.execSQL("insert into tb_user (userId,nickname,authtoken,phone) values (2,'xy','123','321')");
//        db.execSQL("insert into tb_user (userId,nickname,authtoken,phone) values (3,'cwn','123','121')");
//        db.execSQL("insert into tb_user (userId,nickname,authtoken,phone) values (4,'gzh','123','131')");

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

        //身材数据
        db.execSQL("insert into tb_figure (userId,figureType,figureData,recordDate) values(0,'体重','70.2','06-21')");
        db.execSQL("insert into tb_figure (userId,figureType,figureData,recordDate) values(0,'体重','69.2','06-15')");
        db.execSQL("insert into tb_figure (userId,figureType,figureData,recordDate) values(0,'体重','66.2','06-13')");
        db.execSQL("insert into tb_figure (userId,figureType,figureData,recordDate) values(0,'体重','68.6','06-11')");
        db.execSQL("insert into tb_figure (userId,figureType,figureData,recordDate) values(0,'体重','67.2','03-11')");
        db.execSQL("insert into tb_figure (userId,figureType,figureData,recordDate) values(0,'体重','67.5','02-21')");
        db.execSQL("insert into tb_figure (userId,figureType,figureData,recordDate) values(0,'体重','66.5','01-01')");
        db.execSQL("insert into tb_figure (userId,figureType,figureData,recordDate) values(0,'体重','65.7','12-21')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String sql = "drop table if exists tb_note";
//        db.execSQL(sql);
//        this.onCreate(db);
    }
}
