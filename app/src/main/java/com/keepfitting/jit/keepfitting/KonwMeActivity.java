package com.keepfitting.jit.keepfitting;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.keepfitting.jit.keepfitting.entity.User;
import com.keepfitting.jit.keepfitting.service.UserService;
import com.keepfitting.jit.keepfitting.service.impl.UserServiceImpl;

public class KonwMeActivity extends AppCompatActivity {

    private TextView tv_knowme_bmi,tv_konwme_fangwei,tv_konwme_tizhong,tv_konwme_reliang;
    private UserService userService;
    public static User userinfo;




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

    private void init(){

        tv_knowme_bmi=findViewById(R.id.tv_knowme_bmi);
        tv_konwme_fangwei=findViewById(R.id.tv_konwme_fangwei);
        tv_konwme_tizhong=findViewById(R.id.tv_konwme_tizhong);
        tv_konwme_reliang=findViewById(R.id.tv_konwme_reliang);

        //设置BMI
        String BMI = "55";
            BMI = userinfo.getBMI()+"";



        tv_knowme_bmi.setText(BMI);





    }

}
