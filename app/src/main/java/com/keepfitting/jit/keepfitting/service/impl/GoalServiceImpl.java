package com.keepfitting.jit.keepfitting.service.impl;

import android.content.Context;

import com.keepfitting.jit.keepfitting.dao.GoalDao;
import com.keepfitting.jit.keepfitting.dao.impl.GoalDaoImpl;
import com.keepfitting.jit.keepfitting.entity.Goal;
import com.keepfitting.jit.keepfitting.service.GoalService;

import java.util.List;

/**
 * Created by admin on 2020/6/20.
 */

public class GoalServiceImpl implements GoalService {

    private GoalDao goalDao;
    private Context mcontext;



    public GoalServiceImpl(Context context){
        goalDao=new GoalDaoImpl(context);
        mcontext=context;
    }


    @Override
    public List<Goal> findAllGoalByUserId(int userId) {

        return goalDao.findAllGoalByUserId(userId);
    }

    @Override
    public Goal findGoalByGoalId(int goalId) {
        return goalDao.findGoalByGoalId(goalId);
    }



    @Override
    public void ChangeGoal(Goal goal) {
        try{
            goalDao.ChangeGoal(goal);
            System.out.println("更改成功");
        }catch (Exception e){
            System.out.println(e);
        }

    }

    @Override
    public void addGoal(Goal goal) {
        try{
            goalDao.addGoal(goal);
            System.out.println("添加计划成功");
        }catch (Exception e){
            System.out.println(e);
        }

    }

    @Override
    public void deleteGoal(int goalId) {
        try {
            goalDao.deleteGoal(goalId);
            System.out.println("删除成功");
        }catch (Exception e){
            System.out.println(e);
        }

    }

    @Override
    public void ChangeStatus(int goalId) {
        try{
            goalDao.ChangeStatus(goalId);
            System.out.println("完成计划");

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public int getLoseWeightData(int uid) {
        return goalDao.getLoseWeightData(uid);
    }
}
