package com.keepfitting.jit.keepfitting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.keepfitting.jit.keepfitting.entity.Goal;
import com.keepfitting.jit.keepfitting.service.impl.GoalServiceImpl;

import java.util.Calendar;

/**
 * Created by admin on 2020/6/22.
 */

public class FragmentAddGoal extends Fragment {
    RadioGroup group;
    RadioButton jf;
    RadioButton zj;
    RadioButton ss;

    Button start;
    Button end;
    Button add;
    TextView starttime;
    TextView endtime;
    EditText startdata;
    EditText goaldata;
    EditText goaldescribe;
    TextView zhushi;
    ImageView back;

    boolean textFlag,startdataFlag,goaldataFlag,starttimeFlag,endtimeFlag;

    GoalServiceImpl goalServiceImpl;

    String text;
    int a;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_add_goal, null);




        group=view.findViewById(R.id.rg);
        jf=view.findViewById(R.id.cb_jf);
        zj=view.findViewById(R.id.cb_zj);
        ss=view.findViewById(R.id.cb_ss);
        zhushi=view.findViewById(R.id.tv_zhushi);
        start=view.findViewById(R.id.bu_start);
        end=view.findViewById(R.id.bu_end);
        starttime=view.findViewById(R.id.et_startTime);
        endtime=view.findViewById(R.id.et_endTime);
        add=view.findViewById(R.id.bu_add);
        startdata=view.findViewById(R.id.et_startData);
        goaldata=view.findViewById(R.id.et_goalData);
        goaldescribe=view.findViewById(R.id.et_goalDescribe);
        back=view.findViewById(R.id.iv_back);

        goalServiceImpl=new GoalServiceImpl(getActivity());

        //Intent intent = getIntent();
        // final int userId = intent.getIntExtra("userId", 0);
        final int userId = MainActivity.userinfo.getUserID();
        start.setOnClickListener(new View.OnClickListener() {
            Calendar c = Calendar.getInstance();

            @Override
            public void onClick(View v) {
                new DoubleDatePickerDialog(getActivity(), 0, new DoubleDatePickerDialog.OnDateSetListener() {

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
                new DoubleDatePickerDialog(getActivity(), 0, new DoubleDatePickerDialog.OnDateSetListener() {

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

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectRadioBtn();
                System.out.println(text);
            }

            private void selectRadioBtn() {

                RadioButton radioButton =view.findViewById(group.getCheckedRadioButtonId());
                text = radioButton.getText().toString();

            }


        });

        jf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhushi.setText("(注：衡量标准为体重(kg))");
            }
        });

        zj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhushi.setText("(注：衡量标准为承受重量(kg))");
            }
        });

        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhushi.setText("(注：衡量标准为体重(kg))");
            }
        });





        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text==null){
                    textFlag=false;
                }else {
                    textFlag=true;
                }

                if(startdata.getText().toString().equals("")){
                    startdataFlag=false;
                }else {
                    startdataFlag=true;
                }
                if(goaldata.getText().toString().equals("")){
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

                if(textFlag==true&&startdataFlag==true&&goaldataFlag==true&&starttimeFlag==true&&endtimeFlag==true){
                    Goal goal=new Goal();
                    goal.setUserId(userId);
                    goal.setStartData(Float.parseFloat(startdata.getText().toString()));
                    goal.setGoalData(Float.parseFloat(goaldata.getText().toString()));
                    goal.setStartTime(starttime.getText().toString());
                    goal.setEndTime(endtime.getText().toString());
                    goal.setGoalDescribe(goaldescribe.getText().toString());
                    goal.setGoalStatus(0);
                    switch (text){
                        case "减肥":
                            a=0;
                            break;
                        case"力量":
                            a=1;
                            break;
                        case "塑身":
                            a=2;
                            break;

                    }
                    goal.setGoalType(a);



                    goalServiceImpl.addGoal(goal);

                    Toast.makeText(getActivity(),"添加成功!",Toast.LENGTH_SHORT).show();
                }else if(textFlag==false&&startdataFlag==true&&goaldataFlag==true&&starttimeFlag==true&&endtimeFlag==true){
                    Toast.makeText(getActivity(),"类型不能为空!",Toast.LENGTH_SHORT).show();
                }else if(textFlag==true&&startdataFlag==false&&goaldataFlag==true&&starttimeFlag==true&&endtimeFlag==true){
                    Toast.makeText(getActivity(),"起始数据不能为空!",Toast.LENGTH_SHORT).show();
                }else if(textFlag==true&&startdataFlag==true&&goaldataFlag==false&&starttimeFlag==true&&endtimeFlag==true){
                    Toast.makeText(getActivity(),"目标数据不能为空!",Toast.LENGTH_SHORT).show();
                }else if(textFlag==true&&startdataFlag==true&&goaldataFlag==true&&starttimeFlag==false&&endtimeFlag==true){
                    Toast.makeText(getActivity(),"开始时间不能为空!",Toast.LENGTH_SHORT).show();
                }else if(textFlag==true&&startdataFlag==true&&goaldataFlag==true&&starttimeFlag==true&&endtimeFlag==false){
                    Toast.makeText(getActivity(),"结束时间不能为空!",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),"除备注外不能为空!",Toast.LENGTH_SHORT).show();
                }






            }
        });




        return view;
    }








}
