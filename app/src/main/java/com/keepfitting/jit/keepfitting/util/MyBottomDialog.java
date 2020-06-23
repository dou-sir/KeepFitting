package com.keepfitting.jit.keepfitting.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.keepfitting.jit.keepfitting.R;
import com.keepfitting.jit.keepfitting.entity.User;

/**
 * Created by 14032 on 2020/6/22.
 */

public class MyBottomDialog extends Dialog implements View.OnClickListener {//, NumberPicker.OnValueChangeListener,NumberPicker.OnScrollListener,NumberPicker.Formatter

    private NumberPicker bigPicker;
    private NumberPicker smallPicker;
    private TextView tv_dialog_left,tv_dialog_right,tv_dialog_unit;

    private Context context;
    private OnClickListener listener;

    /**
     * flag=1确认，flag=0取消
     */
    public interface OnClickListener{
        void onClick(Dialog dialog,int flag,float num);
    }

    public MyBottomDialog(Context context, OnClickListener listener){
        super(context, R.style.MyDialog);
        this.context=context;
        this.listener=listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_dialog);
        //设置触摸外面可取消对话框
        setCanceledOnTouchOutside(true);
        iniView();
    }
    private void iniView(){
        bigPicker = findViewById(R.id.BigPicker);
        smallPicker = findViewById(R.id.SmallPicker);
        tv_dialog_left = findViewById(R.id.tv_dialog_left);
        tv_dialog_right = findViewById(R.id.tv_dialog_right);
        tv_dialog_unit = findViewById(R.id.tv_dialog_unit);

        tv_dialog_left.setOnClickListener(this);
        tv_dialog_right.setOnClickListener(this);

//        bigPicker.setFormatter(this);
//        bigPicker.setOnValueChangedListener(this);
//        bigPicker.setOnScrollListener(this);
        bigPicker.setMaxValue(300);
        bigPicker.setMinValue(0);
        bigPicker.setValue(50);
//        smallPicker.setFormatter(this);
//        smallPicker.setOnValueChangedListener(this);
//        smallPicker.setOnScrollListener(this);
        smallPicker.setMaxValue(99);
        smallPicker.setMinValue(0);
        smallPicker.setValue(0);
    }

    /**
     * flag=1确认，flag=0取消
     */
    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.tv_dialog_right:
                listener.onClick(this,1,getNum());
                break;
            case R.id.tv_dialog_left:
                listener.onClick(this,0,0);
                break;
        }
    }
    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM;
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);

    }

    public float getNum(){
        String num = bigPicker.getValue()+"."+smallPicker.getValue();
        return Float.parseFloat(num);
    }

//    public String format(int value) {
//        String tmpStr = String.valueOf(value);
//        if (value < 10) {
//            tmpStr = "0" + tmpStr;
//        }
//        return tmpStr;
//    }

//    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
////        Toast.makeText(getContext(),
////                "原来的值 " + oldVal + "--新值: "
////                        + newVal, Toast.LENGTH_SHORT).show();
//    }
//
//    public void onScrollStateChange(NumberPicker view, int scrollState) {
//        switch (scrollState) {
//            case NumberPicker.OnScrollListener.SCROLL_STATE_FLING:
////                Toast.makeText(getContext(), "后续滑动(飞呀飞，根本停下来)", Toast.LENGTH_LONG)
////                        .show();
//                break;
//            case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE:
////                Toast.makeText(getContext(), "不滑动", Toast.LENGTH_LONG).show();
////                break;
//            case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
////                Toast.makeText(getContext(), "滑动中", Toast.LENGTH_LONG)
////                        .show();
//                break;
//        }
//    }

}
