package com.keepfitting.jit.keepfitting;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.keepfitting.jit.keepfitting.entity.User;
import com.keepfitting.jit.keepfitting.service.UserService;
import com.keepfitting.jit.keepfitting.service.impl.UserServiceImpl;

import java.util.Calendar;

public class KonwMeActivity extends AppCompatActivity {

    private TextView tv_knowme_bmi,tv_konwme_fangwei,tv_konwme_tizhong,tv_konwme_reliang;
    private UserService userService;
    public static User userinfo;
    private float standardWeight;
    private BodyData bodyData;
    private float high;
    private Button bt_know_back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konw_me);


        userService = new UserServiceImpl(this);
        userinfo = new User();
        Intent intent =getIntent();
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




        //设置BMI
        String BMI ;
        BMI= userinfo.getBMI()+"";
        tv_knowme_bmi.setText(BMI);



//        Calendar cal = Calendar.getInstance();
//
//        cal.setTime(birthDay);
//        int yearBirth = cal.get(Calendar.YEAR);
//
//










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
