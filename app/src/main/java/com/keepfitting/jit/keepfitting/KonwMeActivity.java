package com.keepfitting.jit.keepfitting;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.keepfitting.jit.keepfitting.entity.User;
import com.keepfitting.jit.keepfitting.service.GoalService;
import com.keepfitting.jit.keepfitting.service.UserService;
import com.keepfitting.jit.keepfitting.service.impl.GoalServiceImpl;
import com.keepfitting.jit.keepfitting.service.impl.UserServiceImpl;

import java.util.Calendar;

public class KonwMeActivity extends AppCompatActivity {

    private TextView tv_knowme_bmi,tv_konwme_fangwei,tv_konwme_tizhong,tv_konwme_reliang,tv_konwme_xinlv;
    private UserService userService;
    public static User userinfo;
    private float standardWeight;
    private BodyData bodyData;
    private float high;
    private Button bt_know_back;

    private GoalService goalService;

    private int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konw_me);


        userService = new UserServiceImpl(this);
        goalService = new GoalServiceImpl(this);
        userinfo = new User();
        Intent intent =getIntent();
        userId = intent.getIntExtra("userId",0);

        if(intent.getSerializableExtra("user")!=null){

            userinfo = (User) intent.getSerializableExtra("user");
            System.out.println(userinfo.toString());
        }else {
            userinfo = userService.findUserByUstate();
        }



        init();

    }
//    public float getHigh() {
//        return high;
//    }

    private void init(){

        tv_knowme_bmi=findViewById(R.id.tv_knowme_bmi);
        tv_konwme_fangwei=findViewById(R.id.tv_konwme_fangwei);
        tv_konwme_tizhong=findViewById(R.id.tv_konwme_tizhong);
        tv_konwme_reliang=findViewById(R.id.tv_konwme_reliang);
        bt_know_back=findViewById(R.id.bt_know_back);
        tv_konwme_xinlv=findViewById(R.id.tv_konwme_xinlv);


        //获取标准体重并设置
        float weight = userService.getStandardWeight(userId);
        tv_konwme_tizhong.setText(weight+"");

        //获取体重范围并设置
        String weightRange = userService.getWeightRange(userId);
        tv_konwme_fangwei.setText(weightRange);

        //获取每日所需摄取热量
        int cal = goalService.getLoseWeightData(userId);
        int needCal = userService.getNeedCalByUserId(userId)-cal;
        tv_konwme_reliang.setText(needCal+"大卡");

        //设置心率
        BodyData bodyData = new BodyData();
        float heartbeat = bodyData.getMaxHeart();
      //  tv_konwme_xinlv.setText(heartbeat+"");
        tv_konwme_xinlv.setText(String.format("%s 次/分钟到 %s 次/分钟", heartbeat * 0.6, heartbeat * 0.75));



        //设置BMI
        String BMI ;
        BMI= userinfo.getBMI()+"";
        tv_knowme_bmi.setText(BMI);




        bt_know_back=findViewById(R.id.bt_know_back);
        bt_know_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KonwMeActivity.this,MainActivity.class);
                startActivity(intent);
                KonwMeActivity.this.finish();
            }
        });
    }



}
