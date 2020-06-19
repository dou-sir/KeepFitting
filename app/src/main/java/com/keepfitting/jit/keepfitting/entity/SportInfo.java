package com.keepfitting.jit.keepfitting.entity;

/**
 * Created by 顾志豪 on 2020/6/15.
 */

public class SportInfo {
    private int activityId;//运动编号
    private float name;//运动名字
    private float calory;//运动热量

    public SportInfo(int activityId, float name, float calory) {
        this.activityId = activityId;
        this.name = name;
        this.calory = calory;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
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
        return "SportInfo{" +
                "activityId=" + activityId +
                ", name=" + name +
                ", calory=" + calory +
                '}';
    }
}
