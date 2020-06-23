package com.keepfitting.jit.keepfitting.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.keepfitting.jit.keepfitting.R;
import com.keepfitting.jit.keepfitting.entity.User;
import com.keepfitting.jit.keepfitting.service.UserService;
import com.keepfitting.jit.keepfitting.service.impl.UserServiceImpl;
import com.keepfitting.jit.keepfitting.util.ChartUtils;

import java.util.ArrayList;
import java.util.List;


public class Figure00Fragment extends Fragment{// implements NumberPicker.OnValueChangeListener,NumberPicker.OnScrollListener,NumberPicker.Formatter

    private TextView tv_unit;
    private LineChart lc_chart;
    private UserService userService;

//    RadioGroup rg_test;
//    Button btn_test;
//    TextView tv_test;
//    NumberPicker bigPicker;
//    NumberPicker smallPicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        Bundle bundle = getArguments();
        if (null != bundle &&bundle.getString("flag").equals("show")){
            view = inflater.inflate(R.layout.show_figure00, container, false);
            initShowView(view);
        }else {
            view = inflater.inflate(R.layout.fragment_figure00, container, false);
            initEditView(view);
        }
        userService = new UserServiceImpl(getContext());

        return view;
    }

    private void initEditView(View view){



    }

    private void initShowView(View view){
        tv_unit = view.findViewById(R.id.tv_unit);
        lc_chart = view.findViewById(R.id.lc_chart);

        Animation mAnimationRight = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_right);
        mAnimationRight.setFillAfter(true);
        tv_unit.setAnimation(mAnimationRight);
        tv_unit.setText("KG");

        ChartUtils.initChart(lc_chart, getData().size(),getContext());
        ChartUtils.notifyDataSetChanged(lc_chart, getData());
    }

    private List<Entry> getData(){
        List<String> list = new ArrayList<>();
        list.add("2.3");
        list.add("5.4");
        list.add("2.0");
        list.add("2.0");
        list.add("1.0");
        list.add("2.5");
        list.add("2.0");
        list.add("2.3");
        list.add("5.4");
        list.add("2.0");
        list.add("2.0");
        list.add("1.0");
        list.add("2.5");
        list.add("2.0");
        list.add("1.0");
        list.add("2.5");
        list.add("2.0");
        list.add("2.3");
        list.add("5.4");
        list.add("2.0");
        list.add("2.0");
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String data = list.get(i);
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Entry entry = new Entry(i, Float.parseFloat(data));
            entries.add(entry);
        }
        return entries;
    }


//
//
//
//    public String format(int value) {
//        String tmpStr = String.valueOf(value);
//        if (value < 10) {
//            tmpStr = "0" + tmpStr;
//        }
//        return tmpStr;
//    }
//
//    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//        Toast.makeText(getContext(),
//                "原来的值 " + oldVal + "--新值: "
//                        + newVal, Toast.LENGTH_SHORT).show();
//    }
//
//    public void onScrollStateChange(NumberPicker view, int scrollState) {
//        switch (scrollState) {
//            case NumberPicker.OnScrollListener.SCROLL_STATE_FLING:
//                Toast.makeText(getContext(), "后续滑动(飞呀飞，根本停下来)", Toast.LENGTH_LONG)
//                        .show();
//                break;
//            case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE:
//                Toast.makeText(getContext(), "不滑动", Toast.LENGTH_LONG).show();
//                break;
//            case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//                Toast.makeText(getContext(), "滑动中", Toast.LENGTH_LONG)
//                        .show();
//                break;
//        }
//    }

    private void setTest(View view){
        //        bigPicker = view.findViewById(R.id.BigPicker);
//        smallPicker = view.findViewById(R.id.SmallPicker);

//
//        bigPicker.setFormatter(this);
//        bigPicker.setOnValueChangedListener(this);
//        bigPicker.setOnScrollListener(this);
//        bigPicker.setMaxValue(300);
//        bigPicker.setMinValue(0);
//        bigPicker.setValue(50);
//
//        smallPicker.setFormatter(this);
//        smallPicker.setOnValueChangedListener(this);
//        smallPicker.setOnScrollListener(this);
//        smallPicker.setMaxValue(99);
//        smallPicker.setMinValue(0);
//        smallPicker.setValue(0);
//        setTest(view);

//        rg_test = view.findViewById(R.id.rg_test);
//        btn_test = view.findViewById(R.id.btn_test);
//        tv_test = view.findViewById(R.id.tv_test);
//
//        btn_test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                User user = new User();
//                switch (rg_test.getCheckedRadioButtonId()){
//                    case R.id.rb_1 :
//                        user = userService.findUserByUserID(1);
//                        tv_test.setText(user.toString());
//                        break;
//                    case R.id.rb_2 :
//                        user = userService.findUserByPhone("121");
//                        tv_test.setText(user.toString());
//                        break;
//                    case R.id.rb_3 :
//                        user = userService.findUserByUstate();
//                        tv_test.setText(user.toString());
//                        break;
//                    case R.id.rb_4 :
//                        user.setPhone("BLM!");
//                        user = userService.addUser(user);
//                        tv_test.setText(user.toString());
//                        break;
//                    case R.id.rb_5 :
//                        user.setNickname("nmslcxk");
//                        user.setAuthToken("233");
//                        user.setUstate(0);
//                        String flag = "flase";
//                        if(userService.modifyUser(user)) flag="true";
//                        tv_test.setText(flag+userService.findUserByUserID(0).toString());
//                        break;
//
//                    default:tv_test.setText("NOTHING");
//                }
//            }
//        });
    }


}
