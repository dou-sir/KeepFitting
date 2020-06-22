package com.keepfitting.jit.keepfitting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.keepfitting.jit.keepfitting.entity.EatenFood;
import com.keepfitting.jit.keepfitting.entity.Food;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddFoodConfirmActivity extends AppCompatActivity {
    private ImageView iv_cancel,iv_add_food_img;
    private TextView tv_add_food_name,tv_add_food_cal;
    private NumberPicker np_add_food_style,np_add_food_weight;
    private Button btn_addFood;
    private Food food;

    //定义食物重量 以及 吃的类型 （晚餐？早餐？）
    private int food_weight;
    private int food_style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_confirm);

        initComponent();
    }

    //组件绑定、初始化
    public void initComponent(){

        //获取上个页面传入的foodID和食物图片ID
        Intent intent = getIntent();
        food = (Food) intent.getSerializableExtra("food");
        int foodImg = intent.getIntExtra("foodImg",0);


        iv_cancel = (ImageView) findViewById(R.id.iv_addFood_cancel);
        iv_add_food_img = (ImageView) findViewById(R.id.iv_add_food_img);

        tv_add_food_name = (TextView) findViewById(R.id.tv_add_food_name);
        tv_add_food_cal = (TextView)findViewById(R.id.tv_add_food_cal);
        np_add_food_style = (NumberPicker)findViewById(R.id.np_add_food_style);
        np_add_food_weight = (NumberPicker)findViewById(R.id.np_add_food_weight);
        btn_addFood = (Button)findViewById(R.id.btn_addFood);

        iv_add_food_img.setImageResource(foodImg);
        //System.out.println("add:"+foodImg);
        tv_add_food_name.setText(food.getFoodName());
        tv_add_food_cal.setText(food.getFoodCalorie()+"");


        //给np_weight设置最大最小值
        np_add_food_weight.setMinValue(20);
        np_add_food_weight.setMaxValue(800);

        //设置默认值为100
        np_add_food_weight.setValue(100);
        food_weight = 100;

        //监听事件 获取np_weight选取的数值
        np_add_food_weight.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                food_weight = newVal;
            }
        });

        np_add_food_style.setDisplayedValues(new String[]{"早餐","午餐","晚餐"});
        np_add_food_style.setMinValue(1);
        np_add_food_style.setMaxValue(3);
        np_add_food_style.setValue(2);
        food_style =2;
        np_add_food_style.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                food_style = i1;
            }
        });

    }

    public void Cancel(View view){
        this.finish();
    }

    public void AddFood(View view){

        //TODO 获取USER的ID 及 每天所需的能量

        //获取当前系统的时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String str = df.format(new Date());
        EatenFood eatenFood = new EatenFood(1,food_style,str,food.getFoodId(),food_weight,1500);
        Toast.makeText(AddFoodConfirmActivity.this, eatenFood.toString(), Toast.LENGTH_SHORT).show();

        //将需要添加的食物返回给上一个页面
        Intent intent = new Intent(this,ShowFoodActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("eatenFood",eatenFood);
        intent.putExtra("bundle",bundle);
        setResult(RESULT_OK,intent);
        AddFoodConfirmActivity.this.finish();

    }

    /**
     * 改变dialog Activity的位置
     */
//    @Override
//    public void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        View view = getWindow().getDecorView();
//        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
//        lp.gravity = Gravity.LEFT | Gravity.TOP;
//        lp.x = 30;
//        lp.y = 10;
//        lp.width = 300;
//        lp.height = 300;
//        getWindowManager().updateViewLayout(view, lp);
//    }
}
