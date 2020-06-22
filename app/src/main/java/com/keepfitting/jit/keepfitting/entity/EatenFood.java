package com.keepfitting.jit.keepfitting.entity;

/**
 * Created by Administrator on 2020/6/22.
 */

import java.io.Serializable;

/**
 * 已经吃的食物,为了实现查找功能
 */

public class EatenFood implements Serializable {
    private int userID;                             //用户的ID
    private int foodType;                           //用餐类型 例如 1-早饭 2-午饭 3-晚饭
    private String date;                            //当天日期 格式2020-06-17
    private String foodsID;                          //食用食物的ID组数
    private String foodsWeight;                      //使用食物的重量
    private int foodID;                             //使用单一的食物
    private int foodWeight;                         //使用单一食物的重量
    private int needCal;                            //每日所需的能量 //TODO 计算每天还能吃多少热量的食物

    public EatenFood() {
    }

    //AddFood 用到 添加单一食物
    public EatenFood(int userID, int foodType, String date, int foodID, int foodWeight,int needCal) {
        this.userID = userID;
        this.foodType = foodType;
        this.date = date;
        this.foodID = foodID;
        this.foodWeight = foodWeight;
        this.needCal = needCal;
    }

    //FoodDaoImpl 用到 获取当日的饮食
    public EatenFood(int userID, int foodType, String date, String foodsID, String foodsWeight, int needCal) {
        this.userID = userID;
        this.foodType = foodType;
        this.date = date;
        this.foodsID = foodsID;
        this.foodsWeight = foodsWeight;
        this.needCal = needCal;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getFoodType() {
        return foodType;
    }

    public void setFoodType(int foodType) {
        this.foodType = foodType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFoodsID() {
        return foodsID;
    }

    public void setFoodsID(String foodsID) {
        this.foodsID = foodsID;
    }

    public String getFoodsWeight() {
        return foodsWeight;
    }

    public void setFoodsWeight(String foodsWeight) {
        this.foodsWeight = foodsWeight;
    }

    public int getNeedCal() {
        return needCal;
    }

    public void setNeedCal(int needCal) {
        this.needCal = needCal;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public int getFoodWeight() {
        return foodWeight;
    }

    public void setFoodWeight(int foodWeight) {
        this.foodWeight = foodWeight;
    }

    @Override
    public String toString() {
        return "EatenFood{" +
                "userID=" + userID +
                ", foodType=" + foodType +
                ", date='" + date + '\'' +
                ", foodsID='" + foodsID + '\'' +
                ", foodsWeight='" + foodsWeight + '\'' +
                ", foodID=" + foodID +
                ", foodWeight=" + foodWeight +
                ", needCal=" + needCal +
                '}';
    }

    /**
     * 返回图片id数组
     * @param foodsID
     * @return
     */
    private int[] getFoodImgs(int[] foodsID){
        int[] foodsImg;
        //TODO 通过id数组获得图片id数组
        foodsImg = foodsID;
        return foodsImg;
    }

    /**
     * 返回食物数组对应的能量数组
     * @param foodsID
     * @param foodsWeight
     * @return
     */
    private int[] getFoodCalories(int[] foodsID,int[] foodsWeight){

        int[] foodCalories= {11,22,33};
        //TODO 计算获得 能量数组
        return foodCalories;
    }
}