package com.keepfitting.jit.keepfitting;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.keepfitting.jit.keepfitting.entity.User;
import com.keepfitting.jit.keepfitting.service.UserService;
import com.keepfitting.jit.keepfitting.service.impl.UserServiceImpl;

public class LoginByPwdActivity extends AppCompatActivity {
    private UserService userService ;
    private CustomVideoView videoview;
    private Button bt_loginbypwd_verify;
    private EditText et_login_nickname,et_loginbypwd_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_pwd);
        userService = new UserServiceImpl(this);



        initView();
    }


    private void initView() {

        //加载视频资源控件
        videoview = (CustomVideoView) findViewById(R.id.videoview);
        //设置播放加载路径
        videoview.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video));
        //播放
        videoview.start();
        //循环播放
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
            }
        });

        et_login_nickname=(EditText)findViewById(R.id.et_login_nickname) ;
        et_loginbypwd_password=(EditText)findViewById(R.id.et_loginbypwd_password) ;
        bt_loginbypwd_verify=findViewById(R.id.bt_loginbypwd_verify);
        bt_loginbypwd_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!et_login_nickname.getText().toString().equals("") )&& (!et_loginbypwd_password.getText().toString().equals(""))){
                   // System.out.println(et_login_nickname.getText().toString()+"aaaa"+et_loginbypwd_password.getText().toString());
                    User inUser = new User();
                    //向User 对象注入  用户名   密码
                    inUser.setNickname(et_login_nickname.getText().toString());
                    inUser.setAuthToken(et_loginbypwd_password.getText().toString());
                    //调用Service 进行判断
                    int result = userService.checkLogin(inUser.getNickname(),inUser.getAuthToken());

                    if(result!=0){
                        User checked =userService.findUserByUserID(result);
                        Intent intent = new Intent(LoginByPwdActivity.this,MainActivity.class);
                        intent.putExtra("user",checked);
                        checked.setUstate(1);
                        userService.modifyUser(checked);
                        startActivity(intent);
                        LoginByPwdActivity.this.finish();
                    }else{
                        System.out.println("用户名或密码错误！" );

                    }
                }else{
                  System.out.println("请输入正确的用户名或密码");//todo请输入正确的用户名或密码
                }
            }
        });


    }








    //返回重启加载
    @Override
    protected void onRestart() {
        initView();
        super.onRestart();
    }

    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        videoview.stopPlayback();
        super.onStop();
    }

    public void toLogin(View v){
        startActivity(new Intent(this,LoginActivity.class));
        this.finish();
    }
}
