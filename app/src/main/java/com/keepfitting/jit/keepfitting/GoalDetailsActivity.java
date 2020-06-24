package com.keepfitting.jit.keepfitting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.keepfitting.jit.keepfitting.entity.Goal;
import com.keepfitting.jit.keepfitting.service.impl.GoalServiceImpl;

import java.util.Calendar;

public class GoalDetailsActivity extends AppCompatActivity {

    Goal goal;

    TextView type;
    EditText startdata;
    EditText goalData;
    TextView starttime;
    TextView endtime;
    EditText describe;
    Button change;
    Button cancel;
    Button start;
    Button end;
    ImageView back;
    boolean startdataFlag,goaldataFlag,starttimeFlag,endtimeFlag,typeFlag;
    int b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_details);

        type=findViewById(R.id.tv_type);
        startdata=findViewById(R.id.et_startData);
        goalData=findViewById(R.id.et_goalData);
        starttime=findViewById(R.id.et_startTime);
        endtime=findViewById(R.id.et_endTime);
        describe=findViewById(R.id.et_goalDescribe);
        change=findViewById(R.id.bu_change);
        cancel=findViewById(R.id.bu_cancel);
        start=findViewById(R.id.bu_start);
        end=findViewById(R.id.bu_end);
        back=findViewById(R.id.iv_back);

        final GoalServiceImpl goalServiceImpl=new GoalServiceImpl(this);


        Intent intent = getIntent();
        int goalId = intent.getIntExtra("goalId", 0);
        goal = goalServiceImpl.findGoalByGoalId(goalId);
        int a=goal.getGoalType();
        switch (a){
            case 0:
                type.setText("减肥");

                break;
            case 1:
                type.setText("力量");

                break;
            case 2:
                type.setText("塑身");

                break;
        }
        startdata.setText(goal.getStartData()+"");
        goalData.setText(goal.getGoalData()+"");
        starttime.setText(goal.getStartTime());
        endtime.setText(goal.getEndTime());
        describe.setText(goal.getGoalDescribe());
        b=goal.getGoalStatus();
        if (b==1){
            typeFlag=false;
        }else {
            typeFlag=true;
        }

        if(typeFlag==false){
            change.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
            start.setVisibility(View.GONE);
            end.setVisibility(View.GONE);
            startdata.setEnabled(false);
            goalData.setEnabled(false);
            describe.setEnabled(false);
        }

        start.setOnClickListener(new View.OnClickListener() {
            Calendar c = Calendar.getInstance();

            @Override
            public void onClick(View v) {
                new DoubleDatePickerDialog(GoalDetailsActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth
                    ) {
                        String textString = String.format("%d-%d-%d\n", startYear,
                                startMonthOfYear + 1, startDayOfMonth);
                        starttime.setText(textString);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            Calendar c = Calendar.getInstance();

            @Override
            public void onClick(View v) {
                new DoubleDatePickerDialog(GoalDetailsActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth
                    ) {
                        String textString = String.format("%d-%d-%d\n", startYear,
                                startMonthOfYear + 1, startDayOfMonth);
                        endtime.setText(textString);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startdata.getText().toString().equals("")){
                    startdataFlag=false;
                }else {
                    startdataFlag=true;
                }
                if(goalData.getText().toString().equals("")){
                    goaldataFlag=false;
                }else {
                    goaldataFlag=true;
                }
                if(starttime.getText().toString().equals("")){
                    starttimeFlag=false;
                }else {
                    starttimeFlag=true;
                }
                if(endtime.getText().toString().equals("")){
                    endtimeFlag=false;
                }else {
                    endtimeFlag=true;
                }

                if(startdataFlag==true&&goaldataFlag==true&&starttimeFlag==true&&endtimeFlag==true){
                    goal.setStartData(Float.parseFloat(startdata.getText().toString()));
                    goal.setGoalData(Float.parseFloat(goalData.getText().toString()));
                    goal.setStartTime(starttime.getText().toString());
                    goal.setEndTime(endtime.getText().toString());
                    goal.setGoalDescribe(describe.getText().toString());
                    goalServiceImpl.ChangeGoal(goal);



                    Toast.makeText(GoalDetailsActivity.this,"修改成功",Toast.LENGTH_SHORT).show();

                }else if(startdataFlag==false&&goaldataFlag==true&&starttimeFlag==true&&endtimeFlag==true){
                    Toast.makeText(GoalDetailsActivity.this,"起始数据不能为空!",Toast.LENGTH_SHORT).show();
                }else if(startdataFlag==true&&goaldataFlag==false&&starttimeFlag==true&&endtimeFlag==true){
                    Toast.makeText(GoalDetailsActivity.this,"目标数据不能为空!",Toast.LENGTH_SHORT).show();
                }else if(startdataFlag==true&&goaldataFlag==true&&starttimeFlag==false&&endtimeFlag==true){
                    Toast.makeText(GoalDetailsActivity.this,"开始时间不能为空!",Toast.LENGTH_SHORT).show();
                }else if(startdataFlag==true&&goaldataFlag==true&&starttimeFlag==true&&endtimeFlag==false){
                    Toast.makeText(GoalDetailsActivity.this,"结束时间不能为空!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(GoalDetailsActivity.this,"除备注外不能为空!",Toast.LENGTH_SHORT).show();
                }

//                goal.setStartData(Float.parseFloat(startdata.getText().toString()));
//                goal.setGoalData(Float.parseFloat(goalData.getText().toString()));
//                goal.setStartTime(starttime.getText().toString());
//                goal.setEndTime(endtime.getText().toString());
//                goal.setGoalDescribe(describe.getText().toString());
//                goalServiceImpl.ChangeGoal(goal);
//
//
//
//                Toast.makeText(GoalDetailsActivity.this,"修改成功",Toast.LENGTH_SHORT).show();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GoalDetailsActivity.this,GoalActivity.class);
                startActivity(intent);
                GoalDetailsActivity.this.finish();
            }
        });




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
