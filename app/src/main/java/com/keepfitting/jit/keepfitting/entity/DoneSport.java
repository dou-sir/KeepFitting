package com.keepfitting.jit.keepfitting.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/6/22.
 */

public class DoneSport implements Serializable {
    private int userID;                     //用户的ID
    private String date;                    //运动日期
    private String sportsId;                //完成运动的ID数组
    private String sportsTime;              //完成运动的时间
    private int sportId;                    //完成单一的运动
    private int sportTime;                  //完成单一运动的时间

    public DoneSport() {
    }

    //AddSport 用到添加单一的运动


    public DoneSport(int userID, String date, int sportId, int sportTime) {
        this.userID = userID;
        this.date = date;
        this.sportId = sportId;
        this.sportTime = sportTime;
    }

    //SportDaoImpl 用到 获取当日的运动
    public DoneSport(int userID, String date, String sportsId, String sportsTime) {
        this.userID = userID;
        this.date = date;
        this.sportsId = sportsId;
        this.sportsTime = sportsTime;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSportsId() {
        return sportsId;
    }

    public void setSportsId(String sportsId) {
        this.sportsId = sportsId;
    }

    public String getSportsTime() {
        return sportsTime;
    }

    public void setSportsTime(String sportsTime) {
        this.sportsTime = sportsTime;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public int getSportTime() {
        return sportTime;
    }

    public void setSportTime(int sportTime) {
        this.sportTime = sportTime;
    }

    @Override
    public String toString() {
        return "DoneSport{" +
                "userID=" + userID +
                ", date='" + date + '\'' +
                ", sportsId='" + sportsId + '\'' +
                ", sportsTime='" + sportsTime + '\'' +
                ", sportId=" + sportId +
                ", sportTime=" + sportTime +
                '}';
    }
}
