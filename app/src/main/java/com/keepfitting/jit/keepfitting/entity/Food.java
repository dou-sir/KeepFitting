package com.keepfitting.jit.keepfitting.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/6/22.
 */

public class Food implements Serializable {
    private int foodId;                     //食物的id
    private String foodName;                //食物名字
    private int foodCalorie;                   //食物能量 每百克
    private int foodImg;                    //食物图片

    public Food() {
    }

    public Food(int foodId, String foodName, int foodCalorie, int foodImg) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodCalorie = foodCalorie;
        this.foodImg = foodImg;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodCalorie() {
        return foodCalorie;
    }

    public void setFoodCalorie(int foodCalorie) {
        this.foodCalorie = foodCalorie;
    }

    public int getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(int foodImg) {
        this.foodImg = foodImg;
    }
}
