package com.keepfitting.jit.keepfitting.entity;

import java.io.Serializable;

/**
 * Created by 顾志豪 on 2020/6/17.
 */

public class User implements Serializable {
    //用户id
    private int userID;
    //用户名称
    private String nickname;
    //密码
    private String authToken;
    //手机号码
    private String phone;
    //性别
    private int sex;
    //生日
    private String birthday;
    //身高
    private String high;
    //BMI
    private float BMI;
    //摄入的能量
    private float intakeCC;
    //消耗的能量
    private float consumeCC;
    //静态能量消耗值
    private float consumeREE;
    //活动系数
    private  float dayrate;
    //标准体重
    private float standardWeight;
    //最高心率
    private float maxHeart;
    //登录状态
    private int ustate;


    public User() {
    }

    public User(int userID, String nickname, String authToken, String phone, int sex, String birthday, String high, float BMI, float intakeCC, float consumeCC, float consumeREE, float dayrate, float standardWeight, float maxHeart, int ustate) {
        this.userID = userID;
        this.nickname = nickname;
        this.authToken = authToken;
        this.phone = phone;
        this.sex = sex;
        this.birthday = birthday;
        this.high = high;
        this.BMI = BMI;
        this.intakeCC = intakeCC;
        this.consumeCC = consumeCC;
        this.consumeREE = consumeREE;
        this.dayrate = dayrate;
        this.standardWeight = standardWeight;
        this.maxHeart = maxHeart;
        this.ustate = ustate;
    }

    public User(String nickname, String authToken) {
        this.nickname=nickname;
        this.authToken=authToken;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", nickname='" + nickname + '\'' +
                ", authToken='" + authToken + '\'' +
                ", phone='" + phone + '\'' +
                ", sex=" + sex +
                ", birthday='" + birthday + '\'' +
                ", high='" + high + '\'' +
                ", BMI=" + BMI +
                ", intakeCC=" + intakeCC +
                ", consumeCC=" + consumeCC +
                ", consumeREE=" + consumeREE +
                ", dayrate=" + dayrate +
                ", standardWeight=" + standardWeight +
                ", maxHeart=" + maxHeart +
                ", ustate=" + ustate +
                '}';
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public float getBMI() {
        return BMI;
    }

    public void setBMI(float BMI) {
        this.BMI = BMI;
    }

    public float getIntakeCC() {
        return intakeCC;
    }

    public void setIntakeCC(float intakeCC) {
        this.intakeCC = intakeCC;
    }

    public float getConsumeCC() {
        return consumeCC;
    }

    public void setConsumeCC(float consumeCC) {
        this.consumeCC = consumeCC;
    }

    public float getConsumeREE() {
        return consumeREE;
    }

    public void setConsumeREE(float consumeREE) {
        this.consumeREE = consumeREE;
    }

    public float getDayrate() {
        return dayrate;
    }

    public void setDayrate(float dayrate) {
        this.dayrate = dayrate;
    }

    public float getStandardWeight() {
        return standardWeight;
    }

    public void setStandardWeight(float standardWeight) {
        this.standardWeight = standardWeight;
    }

    public float getMaxHeart() {
        return maxHeart;
    }

    public void setMaxHeart(float maxHeart) {
        this.maxHeart = maxHeart;
    }

    public int getUstate() {
        return ustate;
    }

    public void setUstate(int ustate) {
        this.ustate = ustate;
    }
}
