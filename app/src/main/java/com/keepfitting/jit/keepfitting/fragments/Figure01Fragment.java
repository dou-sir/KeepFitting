package com.keepfitting.jit.keepfitting.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.keepfitting.jit.keepfitting.R;
import com.keepfitting.jit.keepfitting.util.ChartUtils;

import java.util.ArrayList;
import java.util.List;

public class Figure01Fragment extends Fragment {

    private LineChart lc_chart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.show_figure00, container, false);

        init(view);
        return view;

    }

    private void init(View view) {

        lc_chart = view.findViewById(R.id.lc_chart);

        ChartUtils.initChart(lc_chart);
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



}

