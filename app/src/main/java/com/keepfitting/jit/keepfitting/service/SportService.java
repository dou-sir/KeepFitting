package com.keepfitting.jit.keepfitting.service;

import com.keepfitting.jit.keepfitting.entity.DoneSport;
import com.keepfitting.jit.keepfitting.entity.Sport;

import java.util.List;

/**
 * Created by Administrator on 2020/6/22.
 */

public interface SportService {
    /**
     * 根据运动ID查找运动
     * @param sportId
     * @return Food
     */
    public Sport findSportBySportId(int sportId);

    /**
     *获取所有运动
     * @return List<food>
     */
    public List<Sport> getAllSportList();

    /**
     * 通过uid 及 date 获取当日的运动
     * @return
     */
    public List<DoneSport> getDoneSportByUID(int uid, String date);

    /**
     * 添加doneSport的数据到数据库中
     * @param doneSport
     */
    public void AddDoneSport(DoneSport doneSport);

    /**
     * 通过 运动名称 搜索运动
     * @param sportName
     * @return
     */
    public List<Sport> SearchSportByName(String sportName);

    /**
     * 传入eatenFood 修改tb_daily中的数据
     * @param doneSport
     */
    public void ChangeDoneSport(DoneSport doneSport);

    /**
     *显示某天 总共消耗的能量      food模块中用到
     */
    public int getTodayExpandCalBy(int uid,String date);
}
