package com.keepfitting.jit.keepfitting.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.keepfitting.jit.keepfitting.R;

import java.lang.reflect.Field;

/**
 * Created by 14032 on 2020/6/22.
 */

public class MyNumberPicker extends NumberPicker {
//todo 滑动速度
//    private VelocityTracker mTracker;
//    private OnTrackerListener mListener;

    public MyNumberPicker(Context context) {
        super(context);
    }

    public MyNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    public interface OnTrackerListener{
//        void getXSpeed(int speed);
//        void getYSpeed(int speed);
//    }
//
//    public void setTrackerListener(OnTrackerListener listener){
//        mListener = listener;
//    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        updateView(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    public void updateView(View view) {
        if (view instanceof EditText) {
            //这里修改字体的属性
            ((EditText) view).setTextColor(Color.BLACK);
//            ((EditText) view).setTex);
//            ((EditText) view).setTextSize();
        }
        try {
            //设置分割线大小颜色
            Field mSelectionDivider = this.getFile("mSelectionDivider");
            mSelectionDivider.set(this, new ColorDrawable(getResources().getColor(R.color.gray)));
            mSelectionDivider.set(this, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //反射获取控件 mSelectionDivider mInputText当前选择的view
    public Field getFile(String fieldName) {
        try {
            //设置分割线的颜色值
            Field pickerFields = NumberPicker.class.getDeclaredField(fieldName);
            pickerFields.setAccessible(true);
            return pickerFields;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

//    /**
//     * obtain() 这个方法是静态方法，是用来获取VelocityTracker的一个实例。
//     * addMovement(MotionEvent event)将event对象添加到VelocityTracker的实例当中。
//     * recycle 回收VelocityTracker的一个实例。
//     * computeCurrentVelocity(int units) 该方法用来计算在units毫秒内，运动了多少个像素。如果units值为1000，那么就是在1秒内运动的像素数，也就是速度。
//     * computeCurrentVelocity(int units，float maxVelocity) maxVelocity表示速率的最大值，如果设置成200，那么当速度大于200时，返回值为200，速度低于200时则正常返回。
//     * @param event
//     * @return
//     */
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        getTracker(event);
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                if(mTracker==null){
//                    mTracker = VelocityTracker.obtain();
//                }else{
//                    mTracker.clear();
//                }
//                mTracker.addMovement(event);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                mTracker.addMovement(event);
//                mTracker.computeCurrentVelocity(1000);
//                getSpeed();
//                break;
//            case MotionEvent.ACTION_UP:
//                cancelTracker();
//                break;
//        }
//        return true;
//    }
//
//    private void cancelTracker(){
//        if(mTracker!=null){
//            mTracker.recycle();
//            mTracker = null;
//        }
//    }
//
//    /**
//     * getXVelocity 返回在X方向上的速度
//     * getYVelocity 返回在Y方向上的速度
//     */
//    private void getSpeed(){
//        mTracker.computeCurrentVelocity(1000);
//        int xSpeed = (int)Math.abs(mTracker.getXVelocity());
//        int ySpeed = (int)Math.abs(mTracker.getYVelocity());
//        if(mListener!=null){
//            mListener.getXSpeed(xSpeed);
//            mListener.getYSpeed(ySpeed);
//        }
//    }
//
//    private void getTracker(MotionEvent event){
//        if(mTracker==null){
//            mTracker = VelocityTracker.obtain();
//            mTracker.addMovement(event);
//        }
//    }
}
