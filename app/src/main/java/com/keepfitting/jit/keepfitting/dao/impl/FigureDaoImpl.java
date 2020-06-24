package com.keepfitting.jit.keepfitting.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.keepfitting.jit.keepfitting.dao.FigureDao;
import com.keepfitting.jit.keepfitting.entity.Figure;
import com.keepfitting.jit.keepfitting.util.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 14032 on 2020/6/23.
 */

public class FigureDaoImpl implements FigureDao {

    private DataBaseHelper dbHelper;
    
    public FigureDaoImpl(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    /**
     * 查询数据
     *
     * @param userId
     * @param figuretype
     */
    @Override
    public List<Figure> findFigureByType(int userId, String figuretype) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Figure> figureList;
        String sql="select * from tb_figure where userId=? and figureType=?";                     //尽量不要用*   请使用具体别名
        Cursor cursor = db.rawQuery(sql,new String[]{userId+"",figuretype});
        figureList = new ArrayList<>();
        while (cursor.moveToNext()){
            figureList.add(new Figure(
                    cursor.getInt(cursor.getColumnIndex("figureId")),
                    cursor.getInt(cursor.getColumnIndex("userId")),
                    cursor.getString(cursor.getColumnIndex("figureType")),
                    cursor.getFloat(cursor.getColumnIndex("figureData")),
                    cursor.getString(cursor.getColumnIndex("recordDate"))
            ));
        }

        db.close();
        cursor.close();

        return figureList;
    }


    /**
     * 添加数据
     *
     * @param figure
     * @return
     */
    @Override
    public Figure addFigure(Figure figure) {
        String sql= "insert into tb_figure (userId,figureType,figureData,recordDate) values (?, ?, ?, ?)";
        //获取可写入数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql,new Object[]{figure.getUserId(), figure.getFigureType(), figure.getFigureData(),figure.getRecordDate()});
        db.close();

        return figure;
    }

    /**
     * 修改数据
     *
     * @param figure
     * @return
     */
    @Override
    public void modifyFigure(Figure figure) {
        String sql= "update tb_figure set figureData=? where userId=? and figureType=? and recordDate=?";
        //获取可写入数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql,new Object[]{figure.getFigureData(),figure.getUserId(),figure.getFigureType(),figure.getRecordDate()});
        db.close();

    }

    /**
     * 删除数据
     *
     * @param figure
     * @return
     */
    @Override
    public void deleteFigure(Figure figure) {

    }
}
