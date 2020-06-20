package com.keepfitting.jit.keepfitting.entity;

import java.io.Serializable;

/**
 * Created by admin on 2020/6/20.
 */

public class Goal implements Serializable {
    private int goalId;             //计划的id
    private int userId;             //用户id
    private float startData;        //制定计划时的数据 例如体重45.5kg 胸围80cm
    private float goalData;         //目标数据
    private String startTime;         //开始时间
    private String endTime;           //结束时间
    private int goalType;           //计划类型 例如 1-体重 2-胸围等
    private int goalStatus;         //计划的状态 例如 0-未完成 1-完成
    private String goalDescribe;    //备注


    public Goal() {
    }

    public Goal(float startData, float goalData, String startTime, String endTime, int goalType, int goalStatus, String goalDescribe) {
        this.startData = startData;
        this.goalData = goalData;
        this.startTime = startTime;
        this.endTime = endTime;
        this.goalType = goalType;
        this.goalStatus = goalStatus;
        this.goalDescribe = goalDescribe;
    }

    public Goal(int goalId, int userId, float startData, float goalData, String startTime, String endTime, int goalType, int goalStatus, String goalDescribe) {
        this.goalId = goalId;
        this.userId = userId;
        this.startData = startData;
        this.goalData = goalData;
        this.startTime = startTime;
        this.endTime = endTime;
        this.goalType = goalType;
        this.goalStatus = goalStatus;
        this.goalDescribe = goalDescribe;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "goalId=" + goalId +
                ", userId=" + userId +
                ", startData=" + startData +
                ", goalData=" + goalData +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", goalType=" + goalType +
                ", goalStatus=" + goalStatus +
                ", goalDescribe='" + goalDescribe + '\'' +
                '}';
    }


    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getStartData() {
        return startData;
    }

    public void setStartData(float startData) {
        this.startData = startData;
    }

    public float getGoalData() {
        return goalData;
    }

    public void setGoalData(float goalData) {
        this.goalData = goalData;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getGoalType() {
        return goalType;
    }

    public void setGoalType(int goalType) {
        this.goalType = goalType;
    }

    public int getGoalStatus() {
        return goalStatus;
    }

    public void setGoalStatus(int goalStatus) {
        this.goalStatus = goalStatus;
    }

    public String getGoalDescribe() {
        return goalDescribe;
    }

    public void setGoalDescribe(String goalDescribe) {
        this.goalDescribe = goalDescribe;
    }
}
