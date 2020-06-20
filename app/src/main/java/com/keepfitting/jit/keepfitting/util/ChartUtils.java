package com.keepfitting.jit.keepfitting.util;

import android.graphics.Color;
import android.graphics.Matrix;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 14032 on 2020/6/18.
 */

public class ChartUtils {

    public static int dayValue=0;
    public static int monthValue=2;
    public static int weekValue=1;
    private static XAxis xAxis;                //X轴
    private static YAxis leftYAxis;            //左侧Y轴
    private static YAxis rightYaxis;           //右侧Y轴
    private static Legend legend;              //图例
    private static LimitLine limitLine;        //限制线
    /**
     * 初始化图表
     *
     * @param chart 原始图表
     * @return 初始化后的图表
     */
    public static LineChart initChart(LineChart chart) {
        /***图表设置***/
        // 没有数据的时候，显示“暂无数据”
        chart.setNoDataText("暂无数据");
        // 不显示表格颜色
        chart.setDrawGridBackground(false);
        //是否展示网格线
//        chart.setDrawGridBackground(false);
        //是否显示边界
        chart.setDrawBorders(false);
        //是否可以拖动
        chart.setDragEnabled(false);
        //是否有触摸事件
        chart.setTouchEnabled(true);
        // 不显示图例
        Legend legend = chart.getLegend();
        legend.setEnabled(false);
        // 不显示y轴右边的值
        chart.getAxisRight().setEnabled(false);

        //设置XY轴动画效果
        chart.animateY(2500);
        chart.animateX(1500);

        /***XY轴的设置***/
        xAxis = chart.getXAxis();
        leftYAxis = chart.getAxisLeft();
//        rightYaxis = chart.getAxisRight();

        //设置坐标轴线的颜色
        xAxis.setAxisLineColor(Color.parseColor("#FFA500"));
        leftYAxis.setAxisLineColor(Color.parseColor("#8B0000"));
        //轴文字颜色
        xAxis.setTextColor(Color.parseColor("#FFA500"));
        leftYAxis.setTextColor(Color.parseColor("#8B0000"));
        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        //保证Y轴从0开始，不然会上移一点
        leftYAxis.setAxisMinimum(0f);
//        rightYaxis.setAxisMinimum(0f);


        /***折线图例 标签 设置***/
        legend = chart.getLegend();
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);

        Matrix matrix = new Matrix();
//         x轴缩放1.5倍
        matrix.postScale(3f, 1f);
//         在图表动画显示之前进行缩放
        chart.getViewPortHandler().refresh(matrix, chart, false);
//         x轴执行动画
        chart.animateX(500);
        chart.moveViewToX(19);
        chart.invalidate();
        return chart;
    }


    /**
     * 设置图表数据
     *
     * @param chart 图表
     * @param values 数据
     */
    public static void setChartData(LineChart chart, List<Entry> values) {
        LineDataSet lineDataSet;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            lineDataSet = new LineDataSet(values, "");
            // 设置曲线颜色
            lineDataSet.setColor(Color.GREEN);//Color.parseColor("#543255")

            // 设置平滑曲线
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            // 不显示坐标点的小圆点
//            lineDataSet.setDrawCircles(false);
            //设置曲线值的圆点是实心还是空心
            lineDataSet.setDrawCircleHole(false);
            lineDataSet.setValueTextSize(10f);
            // 不显示坐标点的数据
//            lineDataSet.setDrawValues(false);
            // 不显示定位线
            lineDataSet.setHighlightEnabled(false);

            LineData data = new LineData(lineDataSet);
            chart.setData(data);
            chart.invalidate();
        }
    }

    /**
     * 更新图表
     *
     * @param chart   图表
     * @param values  数据
     * @param //valueType 数据类型
     */
    public static void notifyDataSetChanged(LineChart chart, final List<Entry> values) {//,final int valueType
        chart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int size = values.size();
                return xValuesProcess(size)[(int) value];//todo????
            }
        });



        chart.invalidate();
        setChartData(chart, values);
    }

    /**
     * x轴数据处理
     *size数据个数
     * @param //valueType 数据类型
     * @return x轴数据
     */
    private static String[] xValuesProcess(int size) {//, String date
        // 月
        String[] monthValues = new String[size];
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        for (int i = size-1; i >= 0; i--) {
            monthValues[i] = formatter.format(new Date(currentTime));//TimeUtils.dateToString(currentTime, TimeUtils.dateFormat_month);
            currentTime -= (4 * 24 * 60 * 60 * 1000);
        }
        return monthValues;
    }


}
