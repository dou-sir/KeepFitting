package com.keepfitting.jit.keepfitting.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.keepfitting.jit.keepfitting.DetailsMarkerView;
import com.keepfitting.jit.keepfitting.R;
import com.keepfitting.jit.keepfitting.service.UserService;
import com.keepfitting.jit.keepfitting.service.impl.UserServiceImpl;
import com.keepfitting.jit.keepfitting.util.ChartUtils;

import java.util.ArrayList;
import java.util.List;

public class Figure01Fragment extends Fragment {

    private TextView tv_unit;
    private LineChart lc_chart;
    private UserService userService;

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

        ChartUtils.initChart(lc_chart, getData().size(),getContext());
        ChartUtils.notifyDataSetChanged(lc_chart, getData());
    }

    private void init(View view) {

        lc_chart = view.findViewById(R.id.lc_chart);

        ChartUtils.initChart(lc_chart, getData().size(),getContext());
        ChartUtils.notifyDataSetChanged(lc_chart, getData());

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

//    /**
//     * 创建覆盖物
//     */
//    public void createMakerView() {
//        DetailsMarkerView detailsMarkerView = new DetailsMarkerView(getContext(),R.layout.details_makerview);
//        detailsMarkerView.setChartView(lc_chart);
//        ChartUtils.setDetailsMarkerView(detailsMarkerView);
//    }


}

