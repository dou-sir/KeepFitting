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

import com.keepfitting.jit.keepfitting.entity.DoneSport;
import com.keepfitting.jit.keepfitting.entity.Sport;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddSportConfirmActivity extends AppCompatActivity {

    private ImageView iv_add_sport_img;
    private TextView tv_add_sport_name,tv_add_sport_cal;
    private NumberPicker np_add_sport_time;
    private Button btn_addSport;

    private Sport sport;
    private int sport_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sport_confirm);

        init();
    }

    public void init(){
        //获取上个页面传入的soprtID和运动图片的ID
        Intent intent = getIntent();
        sport = (Sport) intent.getSerializableExtra("sport");
        int sportImg = intent.getIntExtra("sportImg",0);

        iv_add_sport_img = findViewById(R.id.iv_add_sport_img);
        tv_add_sport_name = findViewById(R.id.tv_add_sport_name);
        tv_add_sport_cal = findViewById(R.id.tv_add_sport_cal);
        np_add_sport_time = findViewById(R.id.np_add_sport_time);
        btn_addSport = findViewById(R.id.btn_addSport);

        iv_add_sport_img.setImageResource(sportImg);
        tv_add_sport_name.setText(sport.getSportName());
        tv_add_sport_cal.setText(sport.getSportCalorie()+"");

        //给np设置最大最小值
        np_add_sport_time.setMinValue(10);
        np_add_sport_time.setMaxValue(120);
        //默认为60
        np_add_sport_time.setValue(60);
        sport_time = 60;

        //监听事件 获取np选的数值
        np_add_sport_time.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                sport_time = newVal;
            }
        });

    }

    public void Cancel(View view){
        this.finish();
    }

    public void AddSport(View view){
        //TODO 获取User ID

        //获取当天日期
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String str = df.format(new Date());
        DoneSport doneSport = new DoneSport(1,str,sport.getSportId(),sport_time);
        Toast.makeText(AddSportConfirmActivity.this,doneSport.toString(), Toast.LENGTH_SHORT).show();

        //将需要添加的运动返回给上一个页面
        Intent intent = new Intent(this,ShowSportActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("doneSport",doneSport);
        intent.putExtra("bundle",bundle);
        setResult(RESULT_OK,intent);
        AddSportConfirmActivity.this.finish();
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
//        lp.x = 300;
//        lp.y = 10;
//        lp.width = 300;
//        lp.height = 300;
//        getWindowManager().updateViewLayout(view, lp);
//    }
}
