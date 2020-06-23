package com.keepfitting.jit.keepfitting.entity;

import java.io.Serializable;

public class Figure implements Serializable {
    private int figureId;
    private int userId;
    private String figureType;
    private float figureData;
    private String recordDate;

    public Figure() {
    }

    public Figure(int figureId, int userId, String figureType, float figureData, String recordDate) {
        this.figureId = figureId;
        this.userId = userId;
        this.figureType = figureType;
        this.figureData = figureData;
        this.recordDate = recordDate;
    }

    public int getFigureId() {
        return figureId;
    }

    public void setFigureId(int figureId) {
        this.figureId = figureId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFigureType() {
        return figureType;
    }

    public void setFigureType(String figureType) {
        this.figureType = figureType;
    }

    public float getFigureData() {
        return figureData;
    }

    public void setFigureData(float figureData) {
        this.figureData = figureData;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
}
