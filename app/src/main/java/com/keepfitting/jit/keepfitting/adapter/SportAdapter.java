package com.keepfitting.jit.keepfitting.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.keepfitting.jit.keepfitting.AddSportConfirmActivity;
import com.keepfitting.jit.keepfitting.R;
import com.keepfitting.jit.keepfitting.entity.Sport;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Administrator on 2020/6/22.
 */

public class SportAdapter extends BaseAdapter {

    private ImageView iv_item_sport_img,iv_showSport_add;
    private TextView tv_item_sport_name,tv_item_sport_cal;

    private int sportImg;

    private List<Sport> sportList;
    private Context context;

    public SportAdapter(List<Sport> sportList, Context context) {
        this.sportList = sportList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return sportList.size();
    }

    @Override
    public Object getItem(int position) {
        return sportList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.lv_style_sport,null);

        Sport nowSport = sportList.get(position);

        iv_item_sport_img = convertView.findViewById(R.id.iv_item_sport_img);
        iv_showSport_add = convertView.findViewById(R.id.iv_showSport_add);
        tv_item_sport_name = convertView.findViewById(R.id.tv_item_sport_name);
        tv_item_sport_cal = convertView.findViewById(R.id.tv_item_sport_cal);

        //用反射 通过图片的名字获取图片的id
        Class drawable = R.drawable.class;
        Field field = null;
        int img = nowSport.getSportImg();
        String str = "sport"+img;
        try {
            field = drawable.getField(str);
            sportImg = field.getInt(field.getName());
            iv_item_sport_img.setImageResource(sportImg);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("GET SportImg ERROR!");
        }

        tv_item_sport_name.setText(nowSport.getSportName()+"");
        tv_item_sport_cal.setText(nowSport.getSportCalorie()+"");


        iv_showSport_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddSportConfirmActivity.class);
                Sport sport = sportList.get(position);
                intent.putExtra("sport",sport);

                //用反射 通过图片名字获取id
                Class drawable = R.drawable.class;
                Field field = null;
                int img = sport.getSportImg();
                String str = "sport"+img;
                try {
                    field = drawable.getField(str);
                    sportImg = field.getInt(field.getName());
                } catch (Exception e){
                    e.printStackTrace();
                    System.out.println("GET SportImg ERROR!");
                }

                intent.putExtra("sportImg",sportImg);

                ((Activity)context).startActivityForResult(intent,1);
            }

        });

        return convertView;
    }
}
