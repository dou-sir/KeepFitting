package com.keepfitting.jit.keepfitting;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.keepfitting.jit.keepfitting.entity.User;
import com.keepfitting.jit.keepfitting.service.UserService;
import com.keepfitting.jit.keepfitting.service.impl.UserServiceImpl;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.SharePrefrenceHelper;

import org.json.JSONObject;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.UserInterruptException;
import cn.smssdk.gui.CountryPage;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //创建播放视频的控件对象
    private CustomVideoView videoview;

    private static final String TAG = "VerifyActivity";
    private static final String[] DEFAULT_COUNTRY = new String[]{"中国", "42", "86"};
    private static final int COUNTDOWN = 60;
    private static final String TEMP_CODE = "1319972";
    private static final String KEY_START_TIME = "start_time";
    private static final int REQUEST_CODE_VERIFY = 1001;
    private TextView tvSms;
    private TextView tvAudio;
    private TextView tvCountry;
    private EditText etPhone;
    private EditText etCode;
    private TextView tvCode;
    private TextView tvVerify;
    private TextView tvToast;
    private String currentId;
    private String currentPrefix;
    private FakeActivity callback;
    private Toast toast;
    private Handler handler;
    private EventHandler eventHandler;
    private int currentSecond;
    private SharePrefrenceHelper helper;
    private EditText et_login_phonenumber;    // 电话号码
    private Button bt_login_sendmessage;  // 发送验证码
    private EditText et_login_password;  // 验证码
    private Button   bt_login_verify;        // 验证
    private String phoneNumber;     // 电话号码
    private String verificationCode;  // 验证码
    private TimeCount time;  //按钮倒计时
    private boolean flag;  // 操作是否成功
    private UserService userService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userService = new UserServiceImpl(this);
        if (userService.findUserByUstate().getUserID()!=0){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            User user = userService.findUserByUstate();
            intent.putExtra("user",user);
            startActivity(intent);
            LoginActivity.this.finish();
        }

        initView();


        initListener();
        //默认获取短信和验证按钮不可点击，输入达到规范后，可点击
      //  bt_login_sendmessage.setEnabled(false);
  //      bt_login_verify.setEnabled(false);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_VERIFY) {
            et_login_password.setText("");
            et_login_phonenumber.setText("");
            // 重置"获取验证码"按钮
            bt_login_sendmessage.setText("获取验证码");
            bt_login_sendmessage.setEnabled(true);
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
        }
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




        et_login_phonenumber = findViewById(R.id.et_login_phonenumber);
        et_login_password = findViewById(R.id.et_login_password);
        bt_login_sendmessage = findViewById(R.id.bt_login_sendmessage);
        bt_login_verify=findViewById(R.id.bt_login_verify);
    }


    private void initListener() {



       bt_login_sendmessage.setOnClickListener(this);
        bt_login_sendmessage = findViewById(R.id.bt_login_sendmessage);
       bt_login_sendmessage.setOnClickListener(this);
       bt_login_verify=findViewById(R.id.bt_login_verify);
       bt_login_verify.setOnClickListener(this);
        et_login_phonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //手机号输入大于5位，获取验证码按钮可点击
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bt_login_sendmessage.setEnabled(et_login_phonenumber.getText() != null && et_login_phonenumber.getText().length() > 5);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        et_login_password.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            //验证码输入6位并且手机大于5位，验证按钮可点击
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                bt_login_verify.setEnabled(et_login_password.getText() != null && et_login_password.getText().length() >= 6 && et_login_phonenumber.getText() != null && et_login_phonenumber.getText().length() > 5);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        }
  //      );


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (bt_login_sendmessage != null) {
                    if (currentSecond > 0) {
                        bt_login_sendmessage.setText("获取验证码" + " (" + currentSecond + "s)");
                        bt_login_sendmessage.setEnabled(false);
                        currentSecond--;
                        handler.sendEmptyMessageDelayed(0, 1000);
                    } else {
                        bt_login_sendmessage.setText("获取验证码");
                        bt_login_sendmessage.setEnabled(true);
                    }
                }
            }
        };

        eventHandler = new EventHandler() {
            public void afterEvent(final int event, final int result, final Object data) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //提交验证成功，跳转成功页面，否则toast提示
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                Toast.makeText(LoginActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                User user = new User();
                                user.setPhone(phoneNumber);
                                user=userService.addUser(user);
                                intent.putExtra("user",user);
                                startActivity(intent);
                                LoginActivity.this.finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "请重试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
//                else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE || event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                            if (result == SMSSDK.RESULT_COMPLETE) {
////                                currentSecond = COUNTDOWN;
////                                handler.sendEmptyMessage(0);
////                                helper.putLong(KEY_START_TIME, System.currentTimeMillis());
////                            } else {
////                                if (data != null && (data instanceof UserInterruptException)) {
////                                    // 由于此处是开发者自己决定要中断发送的，因此什么都不用做
////                                    return;
////                                }
////                          //      processError(data);
////                            }
//                        }
//                    });
//                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bt_login_sendmessage:
                //获取验证码间隔时间小于1分钟，进行toast提示，在当前页面不会有这种情况，但是当点击验证码返回上级页面再进入会产生该情况
                if (!TextUtils.isEmpty(et_login_phonenumber.getText())) {
                    if (et_login_phonenumber.getText().length() == 11) {
                        phoneNumber = et_login_phonenumber.getText().toString();
                        SMSSDK.getVerificationCode("86", phoneNumber); // 发送验证码给号码的 phoneNumber 的手机
                        et_login_phonenumber.requestFocus();
                        time = new TimeCount(60000, 1000);
                        time.start();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("number", String.valueOf(et_login_phonenumber));



                    }
                    else {
                        Toast.makeText(this, "请输入完整的电话号码", Toast.LENGTH_SHORT).show();
                        et_login_phonenumber.requestFocus();
                    }
                } else {
                    Toast.makeText(this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                    et_login_phonenumber.requestFocus();
                }

//                if (!isNetworkConnected()) {
//                    Toast.makeText(LoginActivity.this, "网络异常，请稍后重试", Toast.LENGTH_SHORT).show();
//                    break;
//                }

//                currentSecond = COUNTDOWN;
//                handler.sendEmptyMessage(0);
//                helper.putLong(KEY_START_TIME, System.currentTimeMillis());
                break;
                case R.id.bt_login_verify:
                if (!TextUtils.isEmpty(et_login_password.getText())) {
                    if (et_login_password.getText().length() == 6) {
                        verificationCode = et_login_password.getText().toString();
                        SMSSDK.submitVerificationCode("86", phoneNumber, verificationCode);
                        flag = false;
                    } else {
                        Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                        et_login_password.requestFocus();
                    }
                } else {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    et_login_password.requestFocus();
                }
                break;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    private void showErrorToast(String text) {
        if (toast == null) {
            toast = new Toast(this);
            View rootView = LayoutInflater.from(this).inflate(R.layout.login_error_layout, null);
            tvToast = rootView.findViewById(R.id.tvToast);
            toast.setView(rootView);
            toast.setGravity(Gravity.CENTER, 0, ResHelper.dipToPx(this, -100));
        }
        tvToast.setText(text);
        toast.show();
    }

//    private boolean isNetworkConnected() {
//        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
//            return true;
//        }
//        return false;
//    }

//    private void processError(Object data) {
//        int status = 0;
//        // 根据服务器返回的网络错误，给toast提示
//        try {
//            ((Throwable) data).printStackTrace();
//            Throwable throwable = (Throwable) data;
//
//            JSONObject object = new JSONObject(
//                    throwable.getMessage());
//            String des = object.optString("detail");
//            status = object.optInt("status");
//            if (!TextUtils.isEmpty(des)) {
//                showErrorToast(des);
//                return;
//            }
//        } catch (Exception e) {
//            Log.w(TAG, "", e);
//        }
//        // 如果木有找到资源，默认提示
//        int resId = DemoResHelper.getStringRes(getApplicationContext(),
//                "smsdemo_network_error");
//        String netErrMsg = getApplicationContext().getResources().getString(resId);
//        showErrorToast(netErrMsg);
//    }
class TimeCount extends CountDownTimer {

    public TimeCount(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        bt_login_sendmessage.setClickable(false);
        bt_login_sendmessage.setText("("+millisUntilFinished / 1000 +") ");
    }

    @Override
    public void onFinish() {
        bt_login_sendmessage.setText("重新获取验证码");
        bt_login_sendmessage.setClickable(true);


    }
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

    public void toLoginbypwd(View v){
        startActivity(new Intent(this,LoginByPwdActivity.class));
        this.finish();
    }
}
