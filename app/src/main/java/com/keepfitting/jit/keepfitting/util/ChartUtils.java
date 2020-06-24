package com.keepfitting.jit.keepfitting.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.keepfitting.jit.keepfitting.DetailsMarkerView;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 14032 on 2020/6/18.
 */

public class ChartUtils extends CombinedChart{

    public static int dayValue=0;
    public static int monthValue=2;
    public static int weekValue=1;
    private static XAxis xAxis;                //X轴
    private static YAxis leftYAxis;            //左侧Y轴
    private static YAxis rightYaxis;           //右侧Y轴
    private static Legend legend;              //图例
    private static LimitLine limitLine;        //限制线
    PointF downPoint = new PointF();
//
//    //弱引用覆盖物对象,防止内存泄漏,不被回收
//    private static WeakReference<DetailsMarkerView> mDetailsReference;

    public ChartUtils(Context context) {
        super(context);
    }

    /**
     * 初始化图表
     *
     * @param chart 原始图表
     * @return 初始化后的图表
     */
    public static LineChart initChart(LineChart chart, int size, Context mcontext) {
        /***图表设置***/
        // 没有数据的时候，显示“暂无数据”
        chart.setNoDataText("数据不足(至少需要两条数据)");
        chart.setNoDataTextColor(Color.RED);
        // 不显示表格颜色
        chart.setDrawGridBackground(false);
        //是否展示网格线
//        chart.setDrawGridBackground(false);
        //是否显示边界
        chart.setDrawBorders(false);
        //是否可以拖动
        chart.setDragEnabled(true);
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
        //轴宽度
        xAxis.setAxisLineWidth(3f);
        leftYAxis.setAxisLineWidth(3f);
        //x轴文字斜着
        xAxis.setLabelRotationAngle(-30);

        //轴文字大小
        xAxis.setTextSize(15f);
        leftYAxis.setTextSize(13f);
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
//                定位到最新
        chart.moveViewToX(size-1);

        Description description = new Description();
        description.setText("kg");//需要展示的内容
        description.setTextSize(50f);
//        description.setPosition(0,0);
        description.setEnabled(false);
        chart.setDescription(description);

        DetailsMarkerView detailsMarkerView = new DetailsMarkerView(mcontext);
        detailsMarkerView.setChartView(chart);
        chart.setMarker(detailsMarkerView);

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
            lineDataSet.setColor(Color.parseColor("#FF8BB708"));//Color.parseColor("#543255")
            //3.5倍粗曲线
            lineDataSet.setLineWidth(3.5f);
            lineDataSet.setLabel("54646");
            // 设置平滑曲线
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            // 不显示坐标点的小圆点
//            lineDataSet.setDrawCircles(false);
            //设置曲线值的圆点是实心还是空心
            lineDataSet.setDrawCircleHole(false);
            lineDataSet.setCircleRadius(3.5f);
            lineDataSet.setCircleColor(Color.parseColor("#FF8BB703"));
            //坐标点的数据字体大小
            lineDataSet.setValueTextSize(13f);
            //字体颜色
            lineDataSet.setValueTextColor(Color.BLUE);
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
    public static void notifyDataSetChanged(LineChart chart, final List<Entry> values,final List<String> dates) {//
        chart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int size = values.size();

                return xValuesProcess(size,dates)[(int) value];//todo????
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
    private static String[] xValuesProcess(int size,List<String> dates) {//, String date
        // 月
        String[] monthValues = new String[size];
        for (int i = size-1; i >= 0; i--) {
//            if (dates.get(i).substring(5,10).equals("01-01"))
                monthValues[i] = dates.get(i).substring(5,10);
//            else
//                monthValues[i] = dates.get(i).substring(5,10);
        }

//        long currentTime = System.currentTimeMillis();
//        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
//        for (int i = size-1; i >= 0; i--) {
//            monthValues[i] = formatter.format(new Date(currentTime));//TimeUtils.dateToString(currentTime, TimeUtils.dateFormat_month);
//            if(monthValues[i].equals("06-10")){
//                monthValues[i] = "20/06-10";
//            }
//            currentTime -= (24 * 60 * 60 * 1000);
//        }
        return monthValues;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);// 用getParent去请求,
        // 不拦截
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downPoint.x = event.getX();
                downPoint.y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (getScaleX() > 1 && Math.abs(event.getX() - downPoint.x) > 5) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

//    /**
//     * 所有覆盖物是否为空
//     *
//     * @return TRUE FALSE
//     */
//    public static boolean isMarkerAllNull() {
//        return mDetailsReference.get() == null ;
//    }
//
//    public static void setDetailsMarkerView(DetailsMarkerView detailsMarkerView) {
//        mDetailsReference = new WeakReference<>(detailsMarkerView);
//    }

//    /**
//     复制父类的 drawMarkers方法，并且更换上自己的markerView
//     * draws all MarkerViews on the highlighted positions
//     */
//    protected void drawMarkers(Canvas canvas) {
//        DetailsMarkerView mDetailsMarkerView = mDetailsReference.get();
//
//        // if there is no marker view or drawing marker is disabled
//        if (mDetailsMarkerView == null || !isDrawMarkersEnabled() || !valuesToHighlight())
//            return;
//
//        for (int i = 0; i < mIndicesToHighlight.length; i++) {
//
//            Highlight highlight = mIndicesToHighlight[i];
//
//            IDataSet set = mData.getDataSetByIndex(highlight.getDataSetIndex());
//
//            Entry e = mData.getEntryForHighlight(mIndicesToHighlight[i]);
//
//            int entryIndex = set.getEntryIndex(e);
//
//            // make sure entry not null
//            if (e == null || entryIndex > set.getEntryCount() * mAnimator.getPhaseX())
//                continue;
//
//            float[] pos = getMarkerPosition(highlight);
//
//            LineDataSet dataSetByIndex = (LineDataSet) getLineData().getDataSetByIndex(highlight.getDataSetIndex());
//
//            // check bounds
//            if (!mViewPortHandler.isInBounds(pos[0], pos[1]))
//                continue;
//
//            float circleRadius = dataSetByIndex.getCircleRadius();
//
//            //pos[0], pos[1] x 和 y
//            // callbacks to update the content
//            mDetailsMarkerView.refreshContent(e, highlight);
//
//            mDetailsMarkerView.draw(canvas, pos[0], pos[1]);
//
//
//        }
//    }
}
