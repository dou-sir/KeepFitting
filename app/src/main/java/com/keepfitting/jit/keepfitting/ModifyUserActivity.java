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



public class ModifyUserActivity extends AppCompatActivity {


    private Button bt_modify_back,bt_modify_complete;
    private EditText et_register_birthday,et_register_high,et_register_weight;
    public static User userinfo;
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
        rg_sex=findViewById(R.id.rg_sex);




        //生日信息
        et_register_birthday.setText(userinfo.getBirthday());
       //身高信息
        et_register_high.setText(userinfo.getHigh());
        //BMI信息
        et_register_weight.setText(userinfo.getBMI()+"");




       high=et_register_high.getText().toString();
       birthday=et_register_birthday.getText().toString();
      // bmi=et_register_weight.getText();

//
//        RadioGroup radgroup = (RadioGroup) findViewById(R.id.rg_sex);
//        //第一种获得单选按钮值的方法
//        //为radioGroup设置一个监听器:setOnCheckedChanged()
//        radgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton radbtn = (RadioButton) findViewById(checkedId);
//                Toast.makeText(getApplicationContext(), "你选了" + radbtn.getText(), Toast.LENGTH_SHORT).show();
//            }
//        });




        bt_modify_complete=findViewById(R.id.bt_modify_complete);
        bt_modify_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyUserActivity.this,MainActivity.class);
                startActivity(intent);
                ModifyUserActivity.this.finish();
            }
        });

        bt_modify_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   User tomodify =  User();
             //   tomodify.setHigh(high);
              //  tomodify.setBirthday(birthday);
                //          System.out.println("aaa"+phoneNumber+toadd.toString());
               // tomodify = userService.modifyUser(tomodify);

                Intent intent = new Intent(ModifyUserActivity.this,MainActivity.class);
           //     intent.putExtra("user",tomodify);
            //    tomodify.setUstate(1);
           //     userService.modifyUser(tomodify);

                Toast.makeText(ModifyUserActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                startActivity(intent);
                ModifyUserActivity.this.finish();











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
