package com.keepfitting.jit.keepfitting.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.keepfitting.jit.keepfitting.dao.GoalDao;
import com.keepfitting.jit.keepfitting.entity.Goal;
import com.keepfitting.jit.keepfitting.util.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2020/6/20.
 */

public class GoalDaoImpl implements GoalDao {
    private DataBaseHelper dbHelper;

    //改构造方法   能够从Service读取到对应的Activity
    public GoalDaoImpl(Context context) {

        dbHelper = new DataBaseHelper(context);
    }


    @Override
    public List<Goal> findAllGoalByUserId(int userId) {
        //获取可读数据库
        SQLiteDatabase db= dbHelper.getReadableDatabase();

        String sql="select * from tb_goal where userId=?";     //尽量不要用*   请使用具体别名
        //new String[]{uid}    对应sql语句的 ？    有几个 ？  就需要几个参数
        Cursor cursor=db.rawQuery(sql,new String[]{userId+""});
        List<Goal> goalList=new ArrayList<>();

        while (cursor.moveToNext()){

            goalList.add(new Goal(
                    cursor.getInt(cursor.getColumnIndex("goalId")),// 计划id
                    cursor.getInt(cursor.getColumnIndex("userId")),  // 用户id
                    cursor.getFloat(cursor.getColumnIndex("startData")),//开始数据
                    cursor.getFloat(cursor.getColumnIndex("goalData")),//目标数据
                    cursor.getString(cursor.getColumnIndex("startTime")),//开始时间
                    cursor.getString(cursor.getColumnIndex("endTime")),//结束时间
                    cursor.getInt(cursor.getColumnIndex("goalType")),//目标类型
                    cursor.getInt(cursor.getColumnIndex("goalStatus")),//是否完成
                    cursor.getString(cursor.getColumnIndex("goalDescribe"))//备注

            ));
        }
        //关闭连接

        db.close();
        cursor.close();
        return goalList;
    }

    @Override
    public Goal findGoalByGoalId(int goalId) {
        SQLiteDatabase db= dbHelper.getReadableDatabase();

        String sql="select * from tb_goal where goalId=?";     //尽量不要用*   请使用具体别名
        //new String[]{uid}    对应sql语句的 ？    有几个 ？  就需要几个参数
        Cursor cursor=db.rawQuery(sql,new String[]{goalId+""});
        Goal goal=new Goal();
        // 将Cursor中的内容取出
        while (cursor.moveToNext()){

            goal=new Goal(
                    cursor.getInt(cursor.getColumnIndex("goalId")),// 计划id
                    cursor.getInt(cursor.getColumnIndex("userId")),  // 用户id
                    cursor.getFloat(cursor.getColumnIndex("startData")),//开始数据
                    cursor.getFloat(cursor.getColumnIndex("goalData")),//目标数据
                    cursor.getString(cursor.getColumnIndex("startTime")),//开始时间
                    cursor.getString(cursor.getColumnIndex("endTime")),//结束时间
                    cursor.getInt(cursor.getColumnIndex("goalType")),//目标类型
                    cursor.getInt(cursor.getColumnIndex("goalStatus")),//是否完成
                    cursor.getString(cursor.getColumnIndex("goalDescribe"))//备注
            );
        }
        //关闭连接
        db.close();
        cursor.close();
        return goal;
    }

    @Override
    public void ChangeGoal(Goal goal) {
        int n=goal.getGoalId();
        String sql="update tb_goal set startData=?,goalData=?,startTime=?,endTime=?,goalDescribe=? where goalId=?";
        //获取可写入数据库
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        //如果有多个问号       需要传入多个参数
        db.execSQL(sql,new Object[]{goal.getStartData(),goal.getGoalData(),goal.getStartTime(),goal.getEndTime(),goal.getGoalDescribe(),n});
        db.close();

    }

    @Override
    public void addGoal(Goal goal) {
        String sql = "insert into tb_goal (userId,startData,goalData,startTime,endTime,goalType,goalStatus,goalDescribe) values(?,?,?,?,?,?,?,?)";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql, new Object[]{goal.getUserId(),goal.getStartData(),goal.getGoalData(),goal.getStartTime(),goal.getEndTime(),goal.getGoalType(),goal.getGoalStatus(),goal.getGoalDescribe()});
        db.close();

    }

    @Override
    public void deleteGoal(int goalId) {

        String sql="delete from tb_goal where goalId=?";
        //获取可写入数据库
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        //execSQL 方法无返回值    new Object[]{nid}   nid的值会赋值给delete from tb_note where nid=?的问号
        //如果有多个问号       需要传入多个参数
        db.execSQL(sql,new Object[]{goalId});
        db.close();

    }

    @Override
    public void ChangeStatus(int goalId) {

        String sql="update tb_goal set goalStatus=? where goalId=?";

        //获取可写入数据库
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        //如果有多个问号       需要传入多个参数
        db.execSQL(sql,new Object[]{1,goalId});
        db.close();
    }


}
