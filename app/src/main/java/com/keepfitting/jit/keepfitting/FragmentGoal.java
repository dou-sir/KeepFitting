package com.keepfitting.jit.keepfitting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.keepfitting.jit.keepfitting.adapter.GoalAdapter;
import com.keepfitting.jit.keepfitting.entity.Goal;
import com.keepfitting.jit.keepfitting.entity.User;
import com.keepfitting.jit.keepfitting.service.impl.GoalServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2020/6/20.
 */

public class FragmentGoal extends Fragment implements GoalAdapter.CompleteListener{

    private ListView listView;
    List<Goal> goalList=new ArrayList<>();
    private Button button;
    GoalServiceImpl goalServiceImpl;

    // TODO: 自定义了一个userid
    int userId;
    GoalAdapter goalAdapter;
    //private User user;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_goal_list,null);
        //goalAdapter.notifyDataSetChanged();

        listView = (ListView)view.findViewById(R.id.lv_goalList);
        button=(Button)view.findViewById(R.id.bu_add);
        //GoalListActivity goalListActivity=new GoalListActivity();
        //goalList=goalListActivity.goaldata();

        goalServiceImpl=new GoalServiceImpl(getActivity());


        // TODO: 此处得到当前用户的计划
        userId=MainActivity.userinfo.getUserID();
        goalList=goalServiceImpl.findAllGoalByUserId(userId);




        goalAdapter=new GoalAdapter(getActivity(),R.layout.goal_item,goalList,this);
        listView.setAdapter(goalAdapter);





        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //弹出对话框
                //定义一个AltertDialog.Builder  用于弹出确认删除对话框
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setMessage("确认删除？");
                builder.setTitle("提示");
                //弹出对话框  按钮设置
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //页面删除数据之前      先获取到每次点击的订单编号
                        int goalId=(Integer)goalList.get(position).getGoalId();
                        //ListView删除
                        //1.操作页面中显示的数据删除    position是长按事件的位置
                        if(goalList.remove(position)!=null){
                            //2.调用删除方法进行删除
                            goalServiceImpl.deleteGoal(goalId);
                            System.out.println("删除成功");
                        }else{
                            System.out.println("删除失败");
                        }

                        //适配器重新适配
                        goalAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(),"删除成功!",Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),"取消!",Toast.LENGTH_SHORT).show();
                    }
                });
                //创建对话框
                builder.create().show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int i=position;
                //通过点击的位置position获取到这个订单
                Intent intent=new Intent(getActivity(),GoalDetailsActivity.class);
                int goalId=(Integer)goalList.get(position).getGoalId();
                intent.putExtra("goalId",goalId);
                startActivity(intent);
                //startActivityForResult(intent,1);
                //getActivity().finish();
            }
        });





//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
////                Intent intent=new Intent(getActivity(),AddGoalActivity.class);
////                intent.putExtra("userId",userId);
////                startActivity(intent);
//
//
//            }
//        });



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ChangeStatus();
    }

    @Override
    public void ChangeStatus() {
        goalList.clear();
        goalList.addAll(goalServiceImpl.findAllGoalByUserId(userId));
        goalAdapter.notifyDataSetChanged();

    }
}