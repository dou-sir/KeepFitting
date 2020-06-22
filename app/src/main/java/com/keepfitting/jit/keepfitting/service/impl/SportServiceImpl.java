package com.keepfitting.jit.keepfitting.service.impl;

import android.content.Context;

import com.keepfitting.jit.keepfitting.dao.SportDao;
import com.keepfitting.jit.keepfitting.dao.impl.SportDaoImpl;
import com.keepfitting.jit.keepfitting.entity.DoneSport;
import com.keepfitting.jit.keepfitting.entity.Sport;
import com.keepfitting.jit.keepfitting.service.SportService;

import java.util.List;

/**
 * Created by Administrator on 2020/6/22.
 */

public class SportServiceImpl implements SportService {
    private SportDao sportDao;
    private Context context;
    private SportService sportService;

    public SportServiceImpl(Context context){
        sportDao = new SportDaoImpl(context);
        this.context = context;
    }

    @Override
    public Sport findSportBySportId(int sportId) {
        return sportDao.findSportBySportId(sportId);
    }

    @Override
    public List<Sport> getAllSportList() {
        return sportDao.getAllSportList();
    }

    @Override
    public List<DoneSport> getDoneSportByUID(int uid, String date) {
        return sportDao.getDoneSportByUID(uid, date);
    }

    @Override
    public void AddDoneSport(DoneSport doneSport) {

        try {
            List<DoneSport> doneSports =  sportDao.getDoneSportByUID(doneSport.getUserID(),doneSport.getDate());
            if (doneSports.size()>0){
                DoneSport done = doneSports.get(0);
                done.setSportsId(done.getSportsId() +","+ doneSport.getSportsId());
                done.setSportsTime(done.getSportsTime() + "," +doneSport.getSportsTime());
                sportDao.ChangeDoneSport(done);
            }else {
                sportDao.AddDoneSport(doneSport);
            }
            System.out.println("添加DoneSport成功");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public List<Sport> SearchSportByName(String sportName) {
        return sportDao.SearchSportByName(sportName);
    }

    @Override
    public void ChangeDoneSport(DoneSport doneSport) {

        try {
            sportDao.ChangeDoneSport(doneSport);
            System.out.println("修改运动数据成功");
        }catch (Exception e){
            System.out.println("修改运动数据失败");
            System.out.println(e);
        }
    }

}
