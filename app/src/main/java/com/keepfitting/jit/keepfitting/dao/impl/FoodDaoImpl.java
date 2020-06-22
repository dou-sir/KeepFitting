package com.keepfitting.jit.keepfitting.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.keepfitting.jit.keepfitting.dao.FoodDao;
import com.keepfitting.jit.keepfitting.entity.EatenFood;
import com.keepfitting.jit.keepfitting.entity.Food;
import com.keepfitting.jit.keepfitting.util.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/6/22.
 */

public class FoodDaoImpl implements FoodDao{

    private DataBaseHelper dbHelper;

    //改构造方法   能够从Service读取到对应的Activity
    public FoodDaoImpl(Context context) {
        dbHelper = new DataBaseHelper(context);
    }


    //根据食物ID查找食物
    @Override
    public Food findFoodByFoodId(int foodId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql="select * from tb_food where foodId = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{foodId+""});
        Food food = new Food();
        while (cursor.moveToNext()){
            food=new Food(
                    cursor.getInt(cursor.getColumnIndex("foodId")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getInt(cursor.getColumnIndex("calory")),
                    cursor.getInt(cursor.getColumnIndex("foodImg"))
            );
        }
        db.close();
        cursor.close();
        return food;
    }

    @Override
    public List<Food> getAllFoodList() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Food> foodList;
        String sql="select * from tb_food";                     //尽量不要用*   请使用具体别名
        Cursor cursor =db.rawQuery(sql,null);

        foodList = new ArrayList<>();

        while (cursor.moveToNext()){
            foodList.add(new Food(
                    cursor.getInt(cursor.getColumnIndex("foodId")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getInt(cursor.getColumnIndex("calory")),
                    cursor.getInt(cursor.getColumnIndex("foodImg"))
            ));
        }

        db.close();
        cursor.close();

        return foodList;
    }


    @Override
    public List<EatenFood> getEatenFoodByUID(int uid, String date) {
        List<EatenFood> eatenFoods = new ArrayList<EatenFood>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "select * from tb_dailyfood where userId=? and date=?";
        Cursor cursor = db.rawQuery(sql,new String[]{uid+"",date});

        while (cursor.moveToNext()){

            eatenFoods.add(new EatenFood(
                    cursor.getInt(cursor.getColumnIndex("userId")),
                    cursor.getInt(cursor.getColumnIndex("foodType")),
                    cursor.getString(cursor.getColumnIndex("date")),
                    cursor.getString(cursor.getColumnIndex("foodsID")),
                    cursor.getString(cursor.getColumnIndex("foodsWeight")),
                    cursor.getInt(cursor.getColumnIndex("needCal"))
            ));
        }
        db.close();
        cursor.close();

        return eatenFoods;
    }

    @Override
    public void AddEatenFood(EatenFood eatenFood) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "insert into tb_dailyfood (userId,foodType,date,foodsID,foodsWeight,needCal) values(?,?,?,?,?,?)";
        db.execSQL(sql,new Object[]{
                eatenFood.getUserID(),
                eatenFood.getFoodType(),
                eatenFood.getDate(),
                eatenFood.getFoodsID(),
                eatenFood.getFoodsWeight(),
                eatenFood.getNeedCal()
        });

        db.close();
    }

    @Override
    public List<Food> SearchEatenFoodByName(String foodName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "Select * from tb_food where name like '%" + foodName + "%'";
        Cursor cursor =db.rawQuery(sql,null);
        List<Food> foods =new ArrayList<>();
        while (cursor.moveToNext()){
            foods.add(new Food(
                    cursor.getInt(cursor.getColumnIndex("foodId")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getInt(cursor.getColumnIndex("calory")),
                    cursor.getInt(cursor.getColumnIndex("foodImg"))
            ));
        }
        db.close();
        cursor.close();
        return foods;
    }

    @Override
    public void ChangeEatenFood(EatenFood eatenFood) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "update tb_dailyfood set foodsID = ?,foodsWeight = ? where userId = ? and foodType = ? and date = ? ";
        db.execSQL(sql,new Object[]{
                eatenFood.getFoodsID(),
                eatenFood.getFoodsWeight(),
                eatenFood.getUserID()+"",
                eatenFood.getFoodType()+"",
                eatenFood.getDate()
        });
        db.close();

    }
}
