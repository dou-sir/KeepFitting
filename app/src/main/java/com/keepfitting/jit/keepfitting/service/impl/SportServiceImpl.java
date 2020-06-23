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

    @Override
    public int getTodayExpandCalBy(int uid, String date) {
        List<DoneSport> doneSportList = sportDao.getDoneSportByUID(uid, date);
        int totalCal = 0;
        if (doneSportList.size()>0){
            DoneSport doneSport = doneSportList.get(0);
            int[] cals = getDoneSportsCal(doneSport.getSportsId(),doneSport.getSportsTime());
            totalCal = getSportCal(cals);
        }
        return totalCal;
    }


    //通过传入sportsId 和 sportsTime 来计算消耗的热量数组
    public int[] getDoneSportsCal(String sportsId,String sportsTime){
        //将str转化为数组
        String[] ids = sportsId.split(",");
        String[] times = sportsTime.split(",");

        int[] doneSportsId = new int[ids.length];
        int[] doneSportsTime = new int[times.length];
        for (int i =0;i<ids.length;i++){
            doneSportsId[i] = Integer.parseInt(ids[i]);
            doneSportsTime[i] = Integer.parseInt(times[i]);
        }

        int[] doneSportsCal = new int[doneSportsId.length];
        //遍历id列表 找到运动的cal 计算热量
        for (int i = 0;i<doneSportsId.length;i++){
            Sport sport = findSportBySportId(doneSportsId[i]);
            // 热量的计量单位是 /60min
            double hour = (double) doneSportsTime[i]/60;
            double calorie = hour * sport.getSportCalorie();
            doneSportsCal[i] = (int) Math.round(calorie);
        }
        return doneSportsCal;
    }

    //通过sportsCal 计算总共消耗的热量
    public int getSportCal(int[] sportsCal){
        int totalSportCal = 0;
        for (int i =0;i<sportsCal.length;i++){
            totalSportCal += sportsCal[i];
        }
        return  totalSportCal;
    }
}
