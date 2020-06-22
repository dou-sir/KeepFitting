package com.keepfitting.jit.keepfitting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.TypedValue;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.text.DecimalFormat;


/**
 * Created by 14032 on 2020/6/20.
 */

public class DetailsMarkerView extends MarkerView {

    private TextView tvContent;

    private DecimalFormat format;

    public DetailsMarkerView(Context context) {
        super(context, R.layout.details_makerview);

        tvContent = (TextView) findViewById(R.id.tv_num);
        format = new DecimalFormat("###.0");
    }

    //回调函数每次MarkerView重绘,可以用来更新内容(用户界面)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText(", y: " + format.format(e.getY()));
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }



//    private final int DEFAULT_INDICATOR_COLOR = 0xffFD9138;//指示器默认的颜色
//    private final int ARROW_HEIGHT = dp2px(5); // 箭头的高度
//    private final int ARROW_WIDTH = dp2px(10); // 箭头的宽度
//    private final float ARROW_OFFSET = dp2px(2);//箭头偏移量
//    private final float BG_CORNER = dp2px(2);//背景圆角
//    private final TextView tvContent;//文本
//    private Bitmap bitmapForDot;//选中点图片
//    private int bitmapWidth;//点宽
//    private int bitmapHeight;//点高
//
//    public DetailsMarkerView(Context context) {
//        super(context, R.layout.details_makerview);
//        tvContent = findViewById(R.id.tv_num);
//        //图片自行替换
//        bitmapForDot = BitmapFactory.decodeResource(getResources(), R.drawable.fitness);
//        bitmapWidth = bitmapForDot.getWidth();
//        bitmapHeight = bitmapForDot.getHeight();
//    }
//
//    @Override
//    public void refreshContent(Entry e, Highlight highlight) {
//        if (e instanceof CandleEntry) {
//            CandleEntry ce = (CandleEntry) e;
//            tvContent.setText(Utils.formatNumber(ce.getHigh(), 0, true));
//        } else {
//            tvContent.setText(Utils.formatNumber(e.getY(), 0, true));
//        }
//        super.refreshContent(e, highlight);
//    }
//
//    @Override
//    public void draw(Canvas canvas, float posX, float posY) {
//        Chart chart = getChartView();
//        if (chart == null) {
//            super.draw(canvas, posX, posY);
//            return;
//        }
//
//        //指示器背景画笔
//        Paint bgPaint = new Paint();
//        bgPaint.setStyle(Paint.Style.FILL);
//        bgPaint.setAntiAlias(true);
//        bgPaint.setColor(DEFAULT_INDICATOR_COLOR);
//        //剪头画笔
//        Paint arrowPaint = new Paint();
//        arrowPaint.setStyle(Paint.Style.FILL);
//        arrowPaint.setAntiAlias(true);
//        arrowPaint.setColor(DEFAULT_INDICATOR_COLOR);
//
//        float width = getWidth();
//        float height = getHeight();
//
//        int saveId = canvas.save();
//        //移动画布到点并绘制点
//        canvas.translate(posX, posY);
//        canvas.drawBitmap(bitmapForDot, -bitmapWidth / 2f , -bitmapHeight / 2f ,null);
//
//        //画指示器
//        Path path = new Path();
//        RectF bRectF;
//        if (posY < height + ARROW_HEIGHT + ARROW_OFFSET + bitmapHeight / 2f) {//处理超过上边界
//            //移动画布并绘制三角形和背景
//            canvas.translate(0, height + ARROW_HEIGHT + ARROW_OFFSET + bitmapHeight / 2f);
//            path.moveTo(0, -(height + ARROW_HEIGHT));
//            path.lineTo(ARROW_WIDTH / 2f, -(height - BG_CORNER));
//            path.lineTo(- ARROW_WIDTH / 2f, -(height - BG_CORNER));
//            path.lineTo(0, -(height + ARROW_HEIGHT));
//
//            bRectF=new RectF(-width / 2, -height, width / 2, 0);
//
//            canvas.drawPath(path, arrowPaint);
//            canvas.drawRoundRect(bRectF, BG_CORNER, BG_CORNER, bgPaint);
//            canvas.translate(-width / 2f, -height);
//        } else {//没有超过上边界
//            //移动画布并绘制三角形和背景
//            canvas.translate(0, -height - ARROW_HEIGHT - ARROW_OFFSET - bitmapHeight / 2f);
//            path.moveTo(0, height + ARROW_HEIGHT);
//            path.lineTo(ARROW_WIDTH / 2f, height - BG_CORNER);
//            path.lineTo(- ARROW_WIDTH / 2f, height - BG_CORNER);
//            path.lineTo(0, height + ARROW_HEIGHT);
//
//            bRectF=new RectF(-width / 2, 0, width / 2, height);
//
//            canvas.drawPath(path, arrowPaint);
//            canvas.drawRoundRect(bRectF, BG_CORNER, BG_CORNER, bgPaint);
//            canvas.translate(-width / 2f, 0);
//        }
//        draw(canvas);
//        canvas.restoreToCount(saveId);
//    }
//
//    private int dp2px(int dpValues) {
//        return (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, dpValues,
//                getResources().getDisplayMetrics());
//    }



//    /**
//     * 在构造方法里面传入自己的布局以及实例化控件
//     * @param context 上下文
//     *  自己的布局
//     */
//    public DetailsMarkerView(Context context, int layoutResource) {
//        super(context, layoutResource);
//        tv_num = findViewById(R.id.tv_num);
//    }
//
//    //每次重绘，会调用此方法刷新数据
//    @Override
//    public void refreshContent(Entry e, Highlight highlight) {
//        try {
//            tv_num.setText(String.valueOf(e.getY()));
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        super.refreshContent(e, highlight);
//    }
//
//    //布局的偏移量。就是布局显示在圆点的那个位置
//    // -(width / 2) 布局水平居中
//    //-(height) 布局显示在圆点上方
//    @Override
//    public MPPointF getOffset() {
//        return new MPPointF(-(getWidth() / 2), -getHeight());
//    }

}
