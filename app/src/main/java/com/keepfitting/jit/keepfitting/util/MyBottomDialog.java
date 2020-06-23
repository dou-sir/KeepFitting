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


public class MyBottomDialog extends Dialog implements View.OnClickListener {//, NumberPicker.OnValueChangeListener,NumberPicker.OnScrollListener,NumberPicker.Formatter

    private NumberPicker bigPicker;
    private NumberPicker smallPicker;
    private TextView tv_dialog_left,tv_dialog_right,tv_dialog_unit;//

    private Context context;
    private int pagerflag;
    private OnClickListener listener;

    /**
     * flag=1确认，flag=0取消
     */
    public interface OnClickListener{
        void onClick(Dialog dialog,int conflag,float num);
    }

    public MyBottomDialog(Context context, int pagerflag, OnClickListener listener){
        super(context, R.style.MyDialog);
        this.context=context;
        this.pagerflag=pagerflag;
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

        if (pagerflag==0)
            tv_dialog_unit.setText("KG");
        bigPicker.setMaxValue(200);
        bigPicker.setMinValue(0);
        bigPicker.setValue(50);

        smallPicker.setMaxValue(9);
        smallPicker.setMinValue(0);
        smallPicker.setValue(0);
//        setPickerLimit();
    }

    private void setPickerLimit(){

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

    private float getNum(){
        String num = bigPicker.getValue()+"."+smallPicker.getValue();
        return Float.parseFloat(num);
    }

}
