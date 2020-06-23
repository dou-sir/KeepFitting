package com.keepfitting.jit.keepfitting;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.keepfitting.jit.keepfitting.entity.EatenFood;
import com.keepfitting.jit.keepfitting.entity.User;
import com.keepfitting.jit.keepfitting.fragments.Figure00Fragment;
import com.keepfitting.jit.keepfitting.fragments.Figure01Fragment;
import com.keepfitting.jit.keepfitting.service.FoodService;
import com.keepfitting.jit.keepfitting.service.GoalService;
import com.keepfitting.jit.keepfitting.service.SportService;
import com.keepfitting.jit.keepfitting.service.UserService;
import com.keepfitting.jit.keepfitting.service.impl.FoodServiceImpl;
import com.keepfitting.jit.keepfitting.service.impl.GoalServiceImpl;
import com.keepfitting.jit.keepfitting.service.impl.SportServiceImpl;
import com.keepfitting.jit.keepfitting.service.impl.UserServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_menu;
    private DrawerLayout dl_menu;
    private NavigationView nav_view;
    private CircleImageView icon_head;
    private TextView tv_username,tv_usersay;
    private ImageButton ib_logout;
    private FragmentManager fragmentManager;

    private UserService userService;
    private GoalService goalService;
    private FoodService foodService;
    private SportService sportService;
    public static User userinfo;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userService = new UserServiceImpl(this);
        userinfo = new User();

        goalService = new GoalServiceImpl(this);
        foodService = new FoodServiceImpl(this);
        sportService = new SportServiceImpl(this);

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

        iv_menu = findViewById(R.id.iv_menu);
        dl_menu = findViewById(R.id.dl_menu);
        nav_view = findViewById(R.id.nav_view);

        fragmentManager = getSupportFragmentManager();


        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_menu.openDrawer(GravityCompat.START);
            }
        });

        nav_view.setItemIconTintList( null );
        //todo 切换fragment
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int cal = goalService.getLoseWeightData(userinfo.getUserID());     //获取减肥时每天需要减少摄取的能量数值
                int needCal = userService.getNeedCalByUserId(userinfo.getUserID());     //每天需要摄入的能量
                needCal = needCal - cal;

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(new Date());


                switch (item.getItemId()){
                    case R.id.nav_home:
                        //todo 切换fragment
                        break;
                    case R.id.nav_mygoal:
                        FragmentGoal fragmentGoal=new FragmentGoal();
                        fmTransaction(fragmentGoal);
                        break;
                    //另有添加按钮
                    case R.id.nav_newgol:
                        //startActivity(new Intent(MainActivity.this,AddGoalActivity.class));
                        FragmentAddGoal fragmentAddGoal=new FragmentAddGoal();
                        fmTransaction(fragmentAddGoal);
                        break;
                    case R.id.nav_newfigure:
                        ShowFigureFragment showFigureFragment = new ShowFigureFragment();
                        fmTransaction(showFigureFragment);
                        break;
                    case R.id.nav_showfigure:
                        Figure01Fragment figure01Fragment = new Figure01Fragment();
                        fmTransaction(figure01Fragment);
                        break;
                    case R.id.nav_showfoodCC:
                        int sportCal = sportService.getTodayExpandCalBy(userinfo.getUserID(),date);
                        Intent intent = new Intent(MainActivity.this,FoodConditionActivity.class);
                        intent.putExtra("needCal",needCal);
                        intent.putExtra("sportCal",sportCal);
                        intent.putExtra("userId",userinfo.getUserID());
                        startActivity(intent);
                        break;
                    case R.id.nav_showconusumeCC:
                        int takenCal = foodService.getTodayTakenCalBy(userinfo.getUserID(),date);
                        Intent intent1 = new Intent(MainActivity.this,SportConditionActivity.class);
                        intent1.putExtra("needCal",needCal);
                        intent1.putExtra("userId",userinfo.getUserID());
                        intent1.putExtra("takenCal",takenCal);
                        startActivity(intent1);
                        break;
                    case R.id.nav_showself:
                        //todo
                        break;
                    case R.id.nav_:

                        break;
                    default:
                }
                dl_menu.closeDrawers();
                return true;
            }
        });

        //nav_head
        View nav_head = nav_view.getHeaderView(0);
        icon_head = nav_head.findViewById(R.id.icon_head);
        tv_username = nav_head.findViewById(R.id.tv_username);
        tv_usersay = nav_head.findViewById(R.id.tv_usersay);
        ib_logout = nav_head.findViewById(R.id.ib_logout);

        //设置hi name
        String hiName = "Hi,";
        if(userinfo.getNickname()!=null){
            if (userinfo.getNickname().length() > 11)
                hiName += userinfo.getNickname().substring(0,10)+"...";
            else hiName += userinfo.getNickname();
        }
        tv_username.setText(hiName);
        //设置saysomething
        String saysth = "KEEP+APPLE=Kepple";
        if(userinfo.getUserID()!=0 || userinfo.getPhone()!=null){
            saysth = "";
            if(userinfo.getUserID()!=0) saysth+= "ID:"+userinfo.getUserID()+"";
            if(userinfo.getPhone()!=null) saysth+= "       TEL:"+userinfo.getPhone();
        }
        tv_usersay.setText(saysth);

        icon_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 跳到个人信息
            }
        });

        ib_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 登出
            }
        });

        //初始化fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ShowFigureFragment showFigureFragment = new ShowFigureFragment();
        //todo 传值时做
        fragmentTransaction.add(R.id.ll_main,showFigureFragment);
        fragmentTransaction.commit();


    }



    private void fmTransaction(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ll_main,fragment);
        fragmentTransaction.commit();
    }


}
