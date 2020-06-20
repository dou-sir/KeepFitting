package com.keepfitting.jit.keepfitting.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.keepfitting.jit.keepfitting.FragmentGoal;
import com.keepfitting.jit.keepfitting.R;
import com.keepfitting.jit.keepfitting.entity.Goal;
import com.keepfitting.jit.keepfitting.service.impl.GoalServiceImpl;

import java.util.List;

/**
 * Created by admin on 2020/6/20.
 */

public class GoalAdapter extends BaseAdapter {

    List<Goal> goalList;
    Context context;
    int resource;
    Button sure;
    GoalServiceImpl goalServiceImpl;
    private CompleteListener listener;


    public GoalAdapter(Context context, int resource, List<Goal> goalList, FragmentGoal fragmentGoal) {
        this.goalList = goalList;
        this.context = context;
        this.resource = resource;
        this.listener=fragmentGoal;

    }

    public interface CompleteListener{
        void ChangeStatus();
    }


    @Override
    public int getCount() {
        return goalList.size();
    }

    @Override
    public Object getItem(int position) {
        return goalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.goal_item,null);
        final Goal goal=goalList.get(position);
        goalServiceImpl=new GoalServiceImpl(context);

        final TextView goalType=(TextView) convertView.findViewById(R.id.tv_type);
        TextView startData=(TextView) convertView.findViewById(R.id.tv_startData);
        TextView goalData=(TextView) convertView.findViewById(R.id.tv_goalData);
        TextView startTime=(TextView) convertView.findViewById(R.id.tv_startTime);
        TextView endTime=(TextView) convertView.findViewById(R.id.tv_endTime);
        TextView goalDescribe=(TextView) convertView.findViewById(R.id.tv_goalDescribe);
        final TextView goalStatus=(TextView) convertView.findViewById(R.id.tv_goalStatus);
        sure=(Button)convertView.findViewById(R.id.bu_sure);



        int a=goal.getGoalType();
        switch (a){
            case 0:
                goalType.setText("减肥");
                startData.setText(goal.getStartData()+"kg");
                goalData.setText(goal.getGoalData()+"kg");
                break;
            case 1:
                goalType.setText("力量");
                startData.setText(goal.getStartData()+"kg");
                goalData.setText(goal.getGoalData()+"kg");
                break;
            case 2:
                goalType.setText("塑身");
                startData.setText(goal.getStartData()+"kg");
                goalData.setText(goal.getGoalData()+"kg");
                break;
        }


//        startData.setText(goal.getStartData()+"");
//        goalData.setText(goal.getGoalData()+"");
        startTime.setText(goal.getStartTime());
        endTime.setText(goal.getEndTime());
        int b=goal.getGoalStatus();
        switch (b){
            case 0:
                goalStatus.setText("未完成");
                break;
            case 1:
                goalStatus.setText("已完成");
                sure.setVisibility(View.GONE);
                break;
        }
        goalDescribe.setText(goal.getGoalDescribe());


        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                goalStatus.setText("已完成");
                //sure.setEnabled(false);
                sure.setVisibility(View.GONE);
                int a=goal.getGoalId();
                goalServiceImpl.ChangeStatus(a);


                if (listener != null) {
                    listener.ChangeStatus();
                }

                System.out.println(" ");









            }

        });





        return  convertView;
    }
}
