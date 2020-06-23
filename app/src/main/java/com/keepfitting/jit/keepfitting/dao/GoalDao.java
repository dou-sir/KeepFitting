package com.keepfitting.jit.keepfitting.dao;

import com.keepfitting.jit.keepfitting.entity.Goal;

import java.util.List;

/**
 * Created by admin on 2020/6/20.
 */

public interface GoalDao {

    //根据uid 查找所有的计划
    public List<Goal> findAllGoalByUserId(int userId);

    //根据oid 查找具体订单
    public Goal findGoalByGoalId(int goalId);
    //传入新的goal  修改计划中的信息
    public void ChangeGoal(Goal goal);

    //增加新的计划
    public void addGoal(Goal goal);
    //由goalId 删除该计划
    public void deleteGoal(int goalId);
    //由goalId改变是否完成
    public void ChangeStatus(int goalId);

    //--------XY----------
    //通过uid搜索减肥的计划  求得需要每天需要减少的那一部分热量
    public int getLoseWeightData(int uid);

}
