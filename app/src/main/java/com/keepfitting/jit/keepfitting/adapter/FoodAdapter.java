package com.keepfitting.jit.keepfitting.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.keepfitting.jit.keepfitting.AddFoodConfirmActivity;
import com.keepfitting.jit.keepfitting.R;
import com.keepfitting.jit.keepfitting.ShowFoodActivity;
import com.keepfitting.jit.keepfitting.entity.Food;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Administrator on 2020/6/22.
 */

public class FoodAdapter extends BaseAdapter {
    private ImageView iv_item_food_img,iv_showFood_add;
    private TextView tv_item_food_name,tv_item_food_cal;

    private List<Food> foodList;
    private Context context;

    private int foodImg;

    public FoodAdapter(List<Food> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.lv_style_food,null);

        Food nowFood = foodList.get(position);

        iv_item_food_img = convertView.findViewById(R.id.iv_item_food_img);
        tv_item_food_name = convertView.findViewById(R.id.tv_item_food_name);
        tv_item_food_cal = convertView.findViewById(R.id.tv_item_food_cal);
        iv_showFood_add = convertView.findViewById(R.id.iv_showFood_add);


        //用反射 通过图片的名字 如food0 获取图片的id
        Class drawable = R.drawable.class;
        Field field = null;
        int img = nowFood.getFoodImg();
        String str = "food"+img;
        try {
            field = drawable.getField(str);
            foodImg = field.getInt(field.getName());

            //System.out.println("----------"+foodImg);
            iv_item_food_img.setImageResource(foodImg);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GET FoodImg ERROR!");
        }

        tv_item_food_name.setText(nowFood.getFoodName()+"");
        tv_item_food_cal.setText(nowFood.getFoodCalorie()+"");


        //绑定事件 跳转至添加食物页面
        iv_showFood_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, AddFoodConfirmActivity.class);
                Food food = foodList.get(position);
                intent.putExtra("food",food);

                //从Activity中获得userId 并传递
                int userId = ShowFoodActivity.getUserId();
                intent.putExtra("userId",userId);

                //用反射 通过图片的名字 如food0 获取图片的id
                Class drawable = R.drawable.class;
                Field field = null;
                int img = food.getFoodImg();
                String str = "food"+img;
                try {
                    field = drawable.getField(str);
                    foodImg = field.getInt(field.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("GET FoodImg ERROR!");
                }

                intent.putExtra("foodImg",foodImg);

                String date = ShowFoodActivity.getDate();
                intent.putExtra("date",date);

                //设置startActivityForResult 接收下个页面回传的数据
                ((Activity)context).startActivityForResult(intent,1);

                //在Adapter中关闭activity
//                if (Activity.class.isInstance(context)) {
//                    // 转化为activity，然后finish就行了
//                    Activity activity = (Activity) context;
//                    activity.finish();
//                }
            }
        });


        return convertView;
    }

}
