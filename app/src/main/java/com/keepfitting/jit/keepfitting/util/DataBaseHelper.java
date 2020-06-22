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

        db.execSQL("insert into tb_goal values(9,0,45,40,'2020-6-21','2020-6-25',1,0,'cxk')");
        db.execSQL("insert into tb_goal values(10,0,50,45,'2020-5-20','2020-5-29',2,1,'cxk')");
        db.execSQL("insert into tb_goal values(11,0,80,70,'2021-6-1','2020-7-30',1,0,'cxk')");

        //身材数据
        db.execSQL("insert into tb_figure (userId,figureType,figureData,recordDate) values(0,'体重','70.2','06-21')");
        db.execSQL("insert into tb_figure (userId,figureType,figureData,recordDate) values(0,'体重','69.2','06-15')");
        db.execSQL("insert into tb_figure (userId,figureType,figureData,recordDate) values(0,'体重','66.2','06-13')");
        db.execSQL("insert into tb_figure (userId,figureType,figureData,recordDate) values(0,'体重','68.6','06-11')");
        db.execSQL("insert into tb_figure (userId,figureType,figureData,recordDate) values(0,'体重','67.2','03-11')");
        db.execSQL("insert into tb_figure (userId,figureType,figureData,recordDate) values(0,'体重','67.5','02-21')");
        db.execSQL("insert into tb_figure (userId,figureType,figureData,recordDate) values(0,'体重','66.5','01-01')");
        db.execSQL("insert into tb_figure (userId,figureType,figureData,recordDate) values(0,'体重','65.7','12-21')");


        /**
         *     ----------XY-----------
         */
        //4.食物热量信息表
        db.execSQL("create table if not exists tb_food(foodId integer primary key autoincrement,"+
                "name String, calory int, foodImg int)");
        //5、运动热量记录表
        db.execSQL("create table if not exists tb_sport(sportId integer primary key autoincrement,"+
                "name String, calory int, sportImg int)");
        //6.每日饮食表
        db.execSQL("create table if not exists tb_dailyfood(userId integer,"+
                "foodType int, date String,foodsID String,foodsWeight String,needCal int)");
        //7.每日运动表
        db.execSQL("create table if not exists tb_dailysport(userId integer,"+
                "date String,sportsID String,sportsTime String)");


        //添加食物数据
        db.execSQL("insert into tb_food values(0,'米饭',116,0)");
        db.execSQL("insert into tb_food values(1,'乳酸菌面包',306,1)");
        db.execSQL("insert into tb_food values(2,'鸡蛋',144,2)");
        db.execSQL("insert into tb_food values(3,'苹果',53,3)");
        db.execSQL("insert into tb_food values(4,'蜂蜜',321,4)");
        db.execSQL("insert into tb_food values(5,'酸奶',72,5)");
        db.execSQL("insert into tb_food values(6,'豆浆',31,6)");
        db.execSQL("insert into tb_food values(7,'牛奶',54,7)");
        db.execSQL("insert into tb_food values(8,'玉米',112,8)");
        db.execSQL("insert into tb_food values(9,'西瓜',31,9)");
        db.execSQL("insert into tb_food values(10,'桃子',42,10)");
        db.execSQL("insert into tb_food values(11,'橙子',48,11)");
        db.execSQL("insert into tb_food values(12,'燕麦片',338,12)");
        db.execSQL("insert into tb_food values(13,'红薯',61,13)");
        db.execSQL("insert into tb_food values(14,'小米粥',46,14)");
        db.execSQL("insert into tb_food values(15,'马铃薯',81,15)");
        db.execSQL("insert into tb_food values(16,'白粥',30,16)");
        db.execSQL("insert into tb_food values(17,'炸猪排',215,17)");
        db.execSQL("insert into tb_food values(18,'清煎牛排',118,18)");
        db.execSQL("insert into tb_food values(19,'凉面',167,19)");
        db.execSQL("insert into tb_food values(20,'清炒芹菜',44,20)");
        db.execSQL("insert into tb_food values(21,'西葫芦',19,21)");
        db.execSQL("insert into tb_food values(22,'鸡蛋羹',62,22)");
        db.execSQL("insert into tb_food values(23,'大白菜',20,23)");
        db.execSQL("insert into tb_food values(24,'菠菜',28,24)");
        db.execSQL("insert into tb_food values(25,'卷心菜',24,25)");
        db.execSQL("insert into tb_food values(26,'鸡胸脯肉',133,26)");
        db.execSQL("insert into tb_food values(27,'草鱼',113,27)");
        db.execSQL("insert into tb_food values(28,'毛豆',131,28)");
        db.execSQL("insert into tb_food values(29,'丝瓜',20,29)");
        db.execSQL("insert into tb_food values(30,'酸菜鱼',98,30)");
        db.execSQL("insert into tb_food values(31,'牛肉',106,31)");
        db.execSQL("insert into tb_food values(32,'鲫鱼',108,32)");
        db.execSQL("insert into tb_food values(33,'基围虾',101,33)");
        db.execSQL("insert into tb_food values(34,'猪肉',155,34)");
        db.execSQL("insert into tb_food values(35,'白斩鸡',172,35)");
        db.execSQL("insert into tb_food values(36,'牛肚',72,36)");
        db.execSQL("insert into tb_food values(37,'兔肉',102,37)");
        db.execSQL("insert into tb_food values(38,'猪蹄',260,38)");

        //添加运动数据
        db.execSQL("insert into tb_sport values(0,'走路(慢)',116,0)");
        db.execSQL("insert into tb_sport values(1,'走路(快)',356,1)");
        db.execSQL("insert into tb_sport values(2,'跑步',490,2)");
        db.execSQL("insert into tb_sport values(3,'跳绳',517,3)");
        db.execSQL("insert into tb_sport values(4,'游泳',490,4)");
        db.execSQL("insert into tb_sport values(5,'自行车',517,5)");
        db.execSQL("insert into tb_sport values(6,'健身操',446,6)");
        db.execSQL("insert into tb_sport values(7,'瑜伽',178,7)");
        db.execSQL("insert into tb_sport values(8,'足球',535,8)");
        db.execSQL("insert into tb_sport values(9,'篮球',490,9)");
        db.execSQL("insert into tb_sport values(10,'排球',419,10)");
        db.execSQL("insert into tb_sport values(11,'羽毛球',401,11)");
        db.execSQL("insert into tb_sport values(12,'乒乓球',267,12)");

        //添加饮食数据
        db.execSQL("insert into tb_dailyfood values(1,1,'2020-06-16','0,1,2','188,82,120',1500)");
        db.execSQL("insert into tb_dailyfood values(1,2,'2020-06-16','15,12,20','18,80,100',1500)");
        db.execSQL("insert into tb_dailyfood values(1,3,'2020-06-16','10,13,24','50,85,120',1500)");

        db.execSQL("insert into tb_dailyfood values(1,1,'2020-06-19','0,1,2','188,82,120',1500)");
        db.execSQL("insert into tb_dailyfood values(1,2,'2020-06-19','15,12,20','18,80,100',1500)");
        db.execSQL("insert into tb_dailyfood values(1,3,'2020-06-19','10,13,24','50,85,120',1500)");

        //添加运动数据
        db.execSQL("insert into tb_dailysport values(1,'2020-06-16','0,1,2','60,60,60')");

        db.execSQL("insert into tb_dailysport values(1,'2020-06-19','5,6,7','45,30,60')");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String sql = "drop table if exists tb_note";
//        db.execSQL(sql);
//        this.onCreate(db);
    }
}
