package com.keepfitting.jit.keepfitting.service;

import com.keepfitting.jit.keepfitting.entity.EatenFood;
import com.keepfitting.jit.keepfitting.entity.Food;

import java.util.List;

/**
 * Created by Administrator on 2020/6/22.
 */

public interface FoodService {
    /**
     * 根据食物ID查找食物
     * @param foodId
     * @return Food
     */
    public Food findFoodByFoodId(int foodId);


    /**
     *获取所有食物
     * @return List<food>
     */
    public List<Food> getAllFoodList();


    /**
     * 通过uid 及 date 获取当日的饮食
     * @return
     */
    public List<EatenFood> getEatenFoodByUID(int uid, String date);

    /**
     * 添加list<EatenFood>到数据库中
     * @param eatenFoodList
     */
    public void AddEatenFood(List<EatenFood> eatenFoodList);


    /**
     * 通过 食物名称 搜索食物
     * @param foodName
     * @return
     */
    public List<Food> SearchEatenFoodByName(String foodName);

    /**
     * 传入eatenFood 修改tb_daily中的数据
     * @param eatenFood
     */
    public void ChangeEatenFood(EatenFood eatenFood);

}
