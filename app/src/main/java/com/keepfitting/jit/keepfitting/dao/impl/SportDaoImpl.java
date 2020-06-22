package com.keepfitting.jit.keepfitting.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.keepfitting.jit.keepfitting.dao.SportDao;
import com.keepfitting.jit.keepfitting.entity.DoneSport;
import com.keepfitting.jit.keepfitting.entity.Sport;
import com.keepfitting.jit.keepfitting.util.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/6/22.
 */

public class SportDaoImpl implements SportDao {

    private DataBaseHelper dbHelper;

    public SportDaoImpl(Context context){
        dbHelper = new DataBaseHelper(context);
    }

    @Override
    public Sport findSportBySportId(int sportId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from tb_sport where sportId = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{sportId+""});
        Sport sport = new Sport();
        while (cursor.moveToNext()){
            sport = new Sport(
                    cursor.getInt(cursor.getColumnIndex("sportId")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getInt(cursor.getColumnIndex("calory")),
                    cursor.getInt(cursor.getColumnIndex("sportImg"))
            );
        }
        db.close();
        cursor.close();
        return sport;
    }

    @Override
    public List<Sport> getAllSportList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Sport> sportList;
        String sql ="select * from tb_sport";
        Cursor cursor = db.rawQuery(sql,null);
        sportList = new ArrayList<>();
        while (cursor.moveToNext()){
            sportList.add(new Sport(
                    cursor.getInt(cursor.getColumnIndex("sportId")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getInt(cursor.getColumnIndex("calory")),
                    cursor.getInt(cursor.getColumnIndex("sportImg"))
            ));
        }
        db.close();
        cursor.close();
        return sportList;
    }

    @Override
    public List<DoneSport> getDoneSportByUID(int uid, String date) {
        List<DoneSport> doneSports = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from tb_dailysport where userId = ? and date = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{uid+"",date});


        while (cursor.moveToNext()){
            doneSports.add(new DoneSport(
                    cursor.getInt(cursor.getColumnIndex("userId")),
                    cursor.getString(cursor.getColumnIndex("date")),
                    cursor.getString(cursor.getColumnIndex("sportsID")),
                    cursor.getString(cursor.getColumnIndex("sportsTime"))
            ));
        }
        db.close();
        cursor.close();
        return doneSports;
    }

    @Override
    public void AddDoneSport(DoneSport doneSport) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "insert into tb_dailysport(userId,date,sportsID,sportsTime) values(?,?,?,?)";
        db.execSQL(sql,new Object[]{
                doneSport.getUserID(),
                doneSport.getDate(),
                doneSport.getSportsId(),
                doneSport.getSportsTime()
        });
        db.close();

    }

    @Override
    public List<Sport> SearchSportByName(String sportName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "Select * from tb_sport where name like '%" +sportName+ "%'";
        Cursor cursor = db.rawQuery(sql,null);
        List<Sport> sports = new ArrayList<>();
        while (cursor.moveToNext()){
            sports.add(new Sport(
                    cursor.getInt(cursor.getColumnIndex("sportId")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getInt(cursor.getColumnIndex("calory")),
                    cursor.getInt(cursor.getColumnIndex("sportImg"))
            ));

        }
        db.close();
        cursor.close();
        return sports;
    }

    @Override
    public void ChangeDoneSport(DoneSport doneSport) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "update tb_dailysport set sportsID =?,sportsTime =? where userId = ? and date =?";
        db.execSQL(sql,new Object[]{
                doneSport.getSportsId(),
                doneSport.getSportsTime(),
                doneSport.getUserID()+"",
                doneSport.getDate()
        });
        db.close();

    }
}
