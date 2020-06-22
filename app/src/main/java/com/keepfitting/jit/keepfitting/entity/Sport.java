package com.keepfitting.jit.keepfitting.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/6/22.
 */

public class Sport implements Serializable {
    private int sportId;                            //运动id
    private String sportName;                       //运动名称
    private int sportCalorie;                       //运动能量 每小时
    private int sportImg;                           //运动图片id

    public Sport() {
    }

    public Sport(int sportId, String sportName, int sportCalorie, int sportImg) {
        this.sportId = sportId;
        this.sportName = sportName;
        this.sportCalorie = sportCalorie;
        this.sportImg = sportImg;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public int getSportCalorie() {
        return sportCalorie;
    }

    public void setSportCalorie(int sportCalorie) {
        this.sportCalorie = sportCalorie;
    }

    public int getSportImg() {
        return sportImg;
    }

    public void setSportImg(int sportImg) {
        this.sportImg = sportImg;
    }
}
