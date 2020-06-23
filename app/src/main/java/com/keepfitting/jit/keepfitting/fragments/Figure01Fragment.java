package com.keepfitting.jit.keepfitting.fragments;

import android.app.Dialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.keepfitting.jit.keepfitting.DetailsMarkerView;
import com.keepfitting.jit.keepfitting.MainActivity;
import com.keepfitting.jit.keepfitting.R;
import com.keepfitting.jit.keepfitting.entity.Figure;
import com.keepfitting.jit.keepfitting.service.FigureService;
import com.keepfitting.jit.keepfitting.service.UserService;
import com.keepfitting.jit.keepfitting.service.impl.FigureServiceImpl;
import com.keepfitting.jit.keepfitting.service.impl.UserServiceImpl;
import com.keepfitting.jit.keepfitting.util.ChartUtils;
import com.keepfitting.jit.keepfitting.util.MyBottomDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Figure01Fragment extends Fragment {

    private TextView tv_unit;
    private LineChart lc_chart;
    private FigureService figureService;
    private List<Figure> figureList;

    private TextView tv_figure_title,tv_recent_time,tv_figure_num;
    private Button btn_addfigure;
    private String figureType="bust";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        figureService = new FigureServiceImpl(getContext());

        Bundle bundle = getArguments();
        if (null != bundle &&bundle.getString("flag").equals("show")){
            view = inflater.inflate(R.layout.show_figure00, container, false);
            initShowView(view);
        }else {
            view = inflater.inflate(R.layout.fragment_figure00, container, false);
            initEditView(view);
        }
        return view;
    }

    private void initEditView(View view){
        tv_figure_title = view.findViewById(R.id.tv_figure_title);
        tv_recent_time = view.findViewById(R.id.tv_recent_time);
        tv_figure_num = view.findViewById(R.id.tv_figure_num);
        btn_addfigure = view.findViewById(R.id.btn_addfigure);

        Figure recentfigure = figureService.findRecentByType(MainActivity.userinfo.getUserID(),figureType);
        tv_figure_title.setText("您最近一次记录胸围");
        if (recentfigure.getUserId()!=0){
            tv_recent_time.setText("上次记录时间："+recentfigure.getRecordDate());
            tv_figure_num.setText(String.valueOf(recentfigure.getFigureData()));
        }


        final MyBottomDialog dialog = new MyBottomDialog(getContext(),1,
                new MyBottomDialog.OnClickListener() {
                    public void onClick(Dialog dialog, int flag, float num) {
                        //todo 填写数据
                        if(flag == 1) {
                            if(num!=0){
                                doAddFigure(num);
                                dialog.dismiss();
                            }else {
                                Toast.makeText(getContext(),"不能取零值",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            dialog.dismiss();
                        }
                    }
                });
        btn_addfigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });


    }

    private void initShowView(View view){
        tv_unit = view.findViewById(R.id.tv_unit);
        lc_chart = view.findViewById(R.id.lc_chart);
        
        Animation mAnimationRight = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_right);
        mAnimationRight.setFillAfter(true);
        tv_unit.setAnimation(mAnimationRight);
        

        figureList = figureService.findFigureByType(MainActivity.userinfo.getUserID(),figureType);
        List<String> date = getDateString();
        ChartUtils.initChart(lc_chart, date.size(),getContext());
        if (date.size() > 1){
            List<Entry> data = getData();
            ChartUtils.notifyDataSetChanged(lc_chart, data, date);

        }
    }

    private List<Entry> getData(){
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < figureList.size(); i++) {
            Float data = figureList.get(i).getFigureData();
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Entry entry = new Entry(i, data);
            entries.add(entry);
        }
        return entries;
    }
    private List<String> getDateString(){
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < figureList.size(); i++) {
            strings.add(figureList.get(i).getRecordDate());
//            System.out.println("aaa"+figureList.get(i).toString());
        }
        return strings;
    }

    private void doAddFigure(float num){
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Figure figure = new Figure(MainActivity.userinfo.getUserID(),figureType, num, formatter.format(new Date(currentTime)));
        boolean flag=figureService.addFigure(figure);
        if (!flag){
            if (figureService.modifyFigure(figure)){
                tv_recent_time.setText("上次记录时间："+figure.getRecordDate());
                tv_figure_num.setText(String.valueOf(figure.getFigureData()));
                Toast.makeText(getContext(),"修改成功",Toast.LENGTH_SHORT).show();
            }else Toast.makeText(getContext(),"修改失败",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(),"添加成功",Toast.LENGTH_SHORT).show();
            tv_recent_time.setText("上次记录时间："+figure.getRecordDate());
            tv_figure_num.setText(String.valueOf(figure.getFigureData()));
        }
    }

//    private void init(View view) {
//
//        lc_chart = view.findViewById(R.id.lc_chart);
//
//        ChartUtils.initChart(lc_chart, getData().size(),getContext());
//        ChartUtils.notifyDataSetChanged(lc_chart, getData());

//        //点击图表坐标监听
//        lc_chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, Highlight h) {
//                //查看覆盖物是否被回收
//                if (ChartUtils.isMarkerAllNull()) {
//                    //重新绑定覆盖物
//                    createMakerView();
//                    //并且手动高亮覆盖物
//                    lc_chart.highlightValue(h);
//                }
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//        });
//
//    }

//    private List<Entry> getData(){
//        List<String> list = new ArrayList<>();
//        list.add("2.3");
//        list.add("5.4");
//        list.add("2.0");
//        list.add("2.0");
//        list.add("1.0");
//        list.add("2.5");
//        list.add("2.0");
//        list.add("2.3");
//        list.add("5.4");
//        list.add("2.0");
//        list.add("2.0");
//        list.add("1.0");
//        list.add("2.5");
//        list.add("2.0");
//        list.add("1.0");
//        list.add("2.5");
//        list.add("2.0");
//        list.add("2.3");
//        list.add("5.4");
//        list.add("2.0");
//        list.add("2.0");
//        List<Entry> entries = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            String data = list.get(i);
//            /**
//             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
//             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
//             */
//            Entry entry = new Entry(i, Float.parseFloat(data));
//            entries.add(entry);
//        }
//        return entries;
//    }

//    /**
//     * 创建覆盖物
//     */
//    public void createMakerView() {
//        DetailsMarkerView detailsMarkerView = new DetailsMarkerView(getContext(),R.layout.details_makerview);
//        detailsMarkerView.setChartView(lc_chart);
//        ChartUtils.setDetailsMarkerView(detailsMarkerView);
//    }


}

