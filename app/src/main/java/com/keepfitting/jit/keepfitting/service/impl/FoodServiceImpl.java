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

    @Override
    public int getTodayTakenCalBy(int uid, String date) {
        List<EatenFood> eatenFoodList = foodDao.getEatenFoodByUID(uid,date);

        int totalCal = 0;
        if (eatenFoodList.size()>0){
            for (EatenFood e:eatenFoodList){
                int[] cals = getEatenFoodsCal(e.getFoodsID(),e.getFoodsWeight());
                totalCal = totalCal +getTotalCal(cals);
            }
        }
        return totalCal;
    }

    public int[] getEatenFoodsCal(String foodsId,String foodsWeight){
        //将str转化为int[]数组
        String[] ids = foodsId.split(",");
        String[] weights = foodsWeight.split(",");
        int[] takenFoodsId = new int[ids.length];
        int[] takenFoodsWeight = new int[weights.length];
        for (int i=0;i<ids.length;i++){
            takenFoodsId[i] = Integer.parseInt(ids[i]);
            takenFoodsWeight[i] = Integer.parseInt(weights[i]);
        }

        int[] takenFoodsCal = new int[takenFoodsId.length];
        //遍历id列表 找到食物的cal 计算热量
        for(int i=0;i<takenFoodsId.length;i++){
            Food food = findFoodByFoodId(takenFoodsId[i]);
            // 热量的计量单位是 /100g
            double kg = (double) takenFoodsWeight[i]/100;
            double calorie = kg * food.getFoodCalorie();
            //四舍五入
            takenFoodsCal[i] = (int) Math.round(calorie);
        }
        return takenFoodsCal;
    }

    public int getTotalCal(int[] foodsCal){
        int totalCal = 0;
        for (int i =0;i<foodsCal.length;i++){
            totalCal += foodsCal[i];
        }
        return totalCal;
    }
}
