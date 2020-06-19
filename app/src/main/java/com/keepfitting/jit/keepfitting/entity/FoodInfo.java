package com.keepfitting.jit.keepfitting.entity;

/**
 * Created by 顾志豪 on 2020/6/15.
 */

public class FoodInfo {
    private int foodId;//食物编号
    private float name;//食物名字
    private  float calory;//食物热量

    public FoodInfo(int foodId, float name, float calory) {
        this.foodId = foodId;
        this.name = name;
        this.calory = calory;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public float getName() {
        return name;
    }

    public void setName(float name) {
        this.name = name;
    }

    public float getCalory() {
        return calory;
    }

    public void setCalory(float calory) {
        this.calory = calory;
    }

    @Override
    public String toString() {
        return "FoodInfo{" +
                "foodId=" + foodId +
                ", name=" + name +
                ", calory=" + calory +
                '}';
    }
}
