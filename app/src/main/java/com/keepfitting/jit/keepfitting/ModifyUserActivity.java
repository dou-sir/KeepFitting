package com.keepfitting.jit.keepfitting;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.keepfitting.jit.keepfitting.entity.Figure;
import com.keepfitting.jit.keepfitting.entity.User;
import com.keepfitting.jit.keepfitting.service.UserService;
import com.keepfitting.jit.keepfitting.service.impl.UserServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ModifyUserActivity extends AppCompatActivity {


    private Button bt_modify_back,bt_modify_complete;
    private EditText et_register_birthday,et_register_high,et_register_weight,et_register_nickname;
    public  User userinfo;
    private UserService userService;
    private RadioGroup rg_sex;
    private RadioGroup rb_register_male;
    private String high,birthday;
    private float bmi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user);
        userService = new UserServiceImpl(this);
        userinfo = new User();
        Intent intent =getIntent();
        if(intent.getSerializableExtra("user")!=null){

            userinfo = (User) intent.getSerializableExtra("user");
            System.out.println(userinfo.toString());
        }else {
            userinfo = userService.findUserByUstate();
        }



        initView();
    }

    private void initView() {

        bt_modify_back=findViewById(R.id.bt_modify_back);
        et_register_birthday=findViewById(R.id.et_register_birthday);
        et_register_high=findViewById(R.id.et_register_high);
        et_register_weight=findViewById(R.id.et_register_weight);
        et_register_nickname=findViewById(R.id.et_register_nickname);
        rg_sex=findViewById(R.id.rg_sex);



        //生日信息
        et_register_birthday.setText(userinfo.getBirthday());
       //身高信息
        et_register_high.setText(userinfo.getHigh());
        //BMI信息
        et_register_weight.setText(userService.getWeightByUserID(userinfo.getUserID())+"");

        et_register_nickname.setText(userinfo.getNickname());


//       high=et_register_high.getText().toString();
//       birthday=et_register_birthday.getText().toString();
      // bmi=et_register_weight.getText();




        bt_modify_complete=findViewById(R.id.bt_modify_complete);
        bt_modify_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyUserActivity.this.finish();
            }
        });

        bt_modify_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(et_register_birthday.getText().toString().equals("") ||
                        et_register_high.getText().toString().equals("") ||
                        et_register_weight.getText().toString().equals("") ||
                        et_register_nickname.getText().toString().equals("") )){
                    //判断通过
                    User user= userinfo;
                    user.setBirthday(et_register_birthday.getText().toString());
                    user.setHigh(et_register_high.getText().toString());
                    user.setNickname(et_register_nickname.getText().toString());
                    System.out.println("ggg"+user.toString());
                    boolean  a = userService.modifyUser(user);
                    long currentTime = System.currentTimeMillis();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Figure figure = new Figure(user.getUserID(),"weight", Float.parseFloat(et_register_weight.getText().toString()), formatter.format(new Date(currentTime)));
                    boolean b = userService.setWeightByFigure(figure);
                    if (a&&b){
                        //成功
                        Intent intent = new Intent();
                        intent.putExtra("user",user);
                        setResult(222,intent);
                        finish();
                    }else if (a && !b){

                    }else if (b && !a){

                    }else {

                    }

                }else {
                    //有空值
                }

            //    int userID= user.getUserID();
//                User user1=new User(1,"","","",1,"","",22.5f,2000f,1800,1607.5f,1.5f,63,165,0);
//                boolean b=userService.modifyUser(user1);
//                if(b){
//                    startActivity(new Intent(ModifyUserActivity.this,MainActivity.class));
//
//                }else{
//                    System.out.println("修改失败!!");
//                }



            }
        });
    }


//
//    public void modify(View v){
//        Intent intent=getIntent();
//        User user=(User) intent.getSerializableExtra("user");
//        int userID= user.getUserID();
//        User suser=new User(userID,);
//        boolean b=userService.modifyUser(suser);
//        if(b){
//            startActivity(new Intent(ModifyUserActivity.this,MainActivity.class));
//            ModifyUserActivity.this.finish();
//        }else{
//            System.out.println("修改失败!");
//        }
//
//
//    }







}
