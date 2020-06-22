package com.keepfitting.jit.keepfitting.service.impl;

import android.content.Context;

import com.keepfitting.jit.keepfitting.dao.FoodDao;
import com.keepfitting.jit.keepfitting.dao.impl.FoodDaoImpl;
import com.keepfitting.jit.keepfitting.entity.EatenFood;
import com.keepfitting.jit.keepfitting.entity.Food;
import com.keepfitting.jit.keepfitting.service.FoodService;

import java.util.List;

/**
 * Created by Administrator on 2020/6/22.
 */

public class FoodServiceImpl implements FoodService {
    private FoodDao foodDao;
    private Context context;

    private FoodService foodService;

    public FoodServiceImpl(Context context) {
        foodDao=new FoodDaoImpl(context);
        this.context = context;
    }

    @Override
    public Food findFoodByFoodId(int foodId) {
        return foodDao.findFoodByFoodId(foodId);
    }

    @Override
    public List<Food> getAllFoodList() {
        return foodDao.getAllFoodList();
    }

    @Override
    public List<EatenFood> getEatenFoodByUID(int uid, String date) {
        return foodDao.getEatenFoodByUID(uid,date);
    }

    @Override
    public void AddEatenFood(List<EatenFood> eatenFoodList) {

        try{
            //获取数据库的数据 判断吃饭的类型
            a:for (EatenFood eatenFood:eatenFoodList){
                List<EatenFood> eatenData = foodDao.getEatenFoodByUID(eatenFood.getUserID(),eatenFood.getDate());
                for (EatenFood e:eatenData){
                    if (eatenFood.getFoodType()==e.getFoodType()){
                        //TODO 修改数据库
                        //整合字符串 foodsID
                        e.setFoodsID(e.getFoodsID()+","+eatenFood.getFoodsID());
                        e.setFoodsWeight(e.getFoodsWeight()+","+eatenFood.getFoodsWeight());
                        foodDao.ChangeEatenFood(e);
                        break a;        //跳出整个循环
                    }
                }
                foodDao.AddEatenFood(eatenFood);
            }
            System.out.println("添加eatenFoodList成功");
        }catch (Exception e){
            System.out.println(e);
        }

    }

    @Override
    public List<Food> SearchEatenFoodByName(String foodName) {
        return foodDao.SearchEatenFoodByName(foodName);
    }

    @Override
    public void ChangeEatenFood(EatenFood eatenFood) {
        try {
            foodDao.ChangeEatenFood(eatenFood);
            System.out.println("修改饮食数据成功");
        }catch (Exception e){
            System.out.println("修改饮食数据失败");
            System.out.println(e);
        }
    }
}
