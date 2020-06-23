package com.keepfitting.jit.keepfitting;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.keepfitting.jit.keepfitting.entity.DoneSport;
import com.keepfitting.jit.keepfitting.entity.Sport;
import com.keepfitting.jit.keepfitting.service.FoodService;
import com.keepfitting.jit.keepfitting.service.GoalService;
import com.keepfitting.jit.keepfitting.service.SportService;
import com.keepfitting.jit.keepfitting.service.UserService;
import com.keepfitting.jit.keepfitting.service.impl.FoodServiceImpl;
import com.keepfitting.jit.keepfitting.service.impl.GoalServiceImpl;
import com.keepfitting.jit.keepfitting.service.impl.SportServiceImpl;
import com.keepfitting.jit.keepfitting.service.impl.UserServiceImpl;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SportConditionActivity extends AppCompatActivity {

    private TextView tv_sportCon_date,tv_sportCon_taken,tv_sportCon_leftCal,tv_sportCon_needCal,tv_sportCon_expend;
    private TextView tv_sportCon_middle_text,tv_sport_remind;
    private ListView lv_todaySport;
    private ImageView iv_toShowSport,iv_sportCon_toBack,iv_sport_dayBack,iv_sport_dayForward;
    private SportService sportService;
    private FoodService foodService;
    private GoalService goalService;
    private UserService userService;
    private List<DoneSport> doneSportList;              //今天的运动列表
    private List<Map<String,Object>> sport;
    private int takenCal ;

    //TODO
    private int sportCal =0;
    private int needCal;
    private int leftCal ;

    private String date;

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_condition);

        initComponent();

        //TODO 获取已经摄取食物的能量
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId",0);
        needCal = intent.getIntExtra("needCal",1500);
        takenCal = intent.getIntExtra("takenCal",0);
        //TODO leftCal的计算
        tv_sportCon_needCal.setText(needCal+"");
        tv_sportCon_taken.setText(takenCal +"");
        leftCal = needCal - takenCal +sportCal;
        //tv_sportCon_leftCal.setText(leftCal+"");
        init();
    }

    public void initComponent(){
        tv_sportCon_date = findViewById(R.id.tv_sportCon_date);
        tv_sportCon_taken = findViewById(R.id.tv_sportCon_taken);
        tv_sportCon_leftCal = findViewById(R.id.tv_sportCon_leftCal);
        tv_sportCon_needCal = findViewById(R.id.tv_sportCon_needCal);
        tv_sportCon_expend = findViewById(R.id.tv_sportCon_expend);

        tv_sportCon_middle_text = findViewById(R.id.tv_sportCon_middle_text);
        tv_sport_remind = findViewById(R.id.tv_sport_remind);

        lv_todaySport = findViewById(R.id.lv_todaySport);

        iv_toShowSport = findViewById(R.id.iv_toShowSport);
        iv_sportCon_toBack = findViewById(R.id.iv_sportCon_toBack);
        iv_sport_dayBack = findViewById(R.id.iv_sport_dayBack);
        iv_sport_dayForward = findViewById(R.id.iv_sport_dayForward);

    }

    public void init(){
        sportService = new SportServiceImpl(SportConditionActivity.this);
        foodService = new FoodServiceImpl(SportConditionActivity.this);
        userService = new UserServiceImpl(SportConditionActivity.this);
        goalService = new GoalServiceImpl(SportConditionActivity.this);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        date = df.format(new Date());
        tv_sportCon_date.setText(date);

        doneSportList = sportService.getDoneSportByUID(userId,date);
        showSport();
    }

    //判断今天有没有运动 有就展示
    public void showSport(){
        if (doneSportList.size()>0){
            DoneSport doneSport = doneSportList.get(0);
            int[] clas = getDoneSportsCal(doneSport.getSportsId(),doneSport.getSportsTime());
            sport = getTodayDoneSport(doneSport,clas);
            SimpleAdapter simpleAdapter = new SimpleAdapter(
                    SportConditionActivity.this,
                    sport,
                    R.layout.lv_style_donesport,
                    new String[]{"name","time","cal","img"},
                    new int[]{R.id.tv_item_doneSport_name,R.id.tv_item_doneSport_time,R.id.tv_item_doneSport_cal,R.id.iv_item_doneSport_img}
            );
            lv_todaySport.setAdapter(simpleAdapter);
            lv_todaySport.setVisibility(View.VISIBLE);

            tv_sport_remind.setVisibility(View.GONE);

            sportCal=getSportCal(clas);

            refresh(needCal,takenCal,leftCal,sportCal);



        }else {
            clearListView();
        }

    }


    //通过传入sportsId 和 sportsTime 来计算消耗的热量数组
    public int[] getDoneSportsCal(String sportsId,String sportsTime){
        //将str转化为数组
        String[] ids = sportsId.split(",");
        String[] times = sportsTime.split(",");

        int[] doneSportsId = new int[ids.length];
        int[] doneSportsTime = new int[times.length];
        for (int i =0;i<ids.length;i++){
            doneSportsId[i] = Integer.parseInt(ids[i]);
            doneSportsTime[i] = Integer.parseInt(times[i]);
        }

        int[] doneSportsCal = new int[doneSportsId.length];
        //遍历id列表 找到运动的cal 计算热量
        for (int i = 0;i<doneSportsId.length;i++){
            Sport sport = sportService.findSportBySportId(doneSportsId[i]);
            // 热量的计量单位是 /60min
            double hour = (double) doneSportsTime[i]/60;
            double calorie = hour * sport.getSportCalorie();
            doneSportsCal[i] = (int) Math.round(calorie);
        }
        return doneSportsCal;
    }

    //通过sportsCal 计算总共消耗的热量
    public int getSportCal(int[] sportsCal){
        int totalSportCal = 0;
        for (int i =0;i<sportsCal.length;i++){
            totalSportCal += sportsCal[i];
        }
        return  totalSportCal;
    }

    //用于和simpleAdapter 进行适配
    public List<Map<String,Object>> getTodayDoneSport(DoneSport doneSport,int[] cals){
        //str 改造为int[]
        String strSport = doneSport.getSportsId();
        String[] ids = strSport.split(",");
        String strTime = doneSport.getSportsTime();
        String[] times = strTime.split(",");
        int[] doneSportsId = new int[ids.length];
        int[] doneSportsTime = new int[times.length];
        for(int i=0;i<ids.length;i++){
            doneSportsId[i] = Integer.parseInt(ids[i]);
            doneSportsTime[i] = Integer.parseInt(times[i]);
        }

        int[] sportImg = new int[doneSportsId.length];
        List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
        for (int i=0;i<doneSportsId.length;i++){
            Map<String,Object> map = new HashMap<String,Object>();
            Sport sport = sportService.findSportBySportId(doneSportsId[i]);
            map.put("name",sport.getSportName());
            map.put("time",doneSportsTime[i]);
            map.put("cal",cals[i]);

            //过去imgid的数组
            Class drawable = R.drawable.class;
            Field field = null;
            int img = sport.getSportImg();
            String str = "sport"+img;
            try {
                field = drawable.getField(str);
                sportImg[i] = field.getInt(field.getName());
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("GET SportImg ERROR!");
            }
            map.put("img",sportImg[i]);
            data.add(map);
        }

        return data;
    }

    //展示前一天内容
    public void dayBack(View view){
        date = getLastDay(date);
        tv_sportCon_date.setText(date);
        takenCal = foodService.getTodayTakenCalBy(userId,date);
        sportCal = sportService.getTodayExpandCalBy(userId,date);
        int cal = goalService.getLoseWeightData(userId);     //获取减肥时每天需要减少摄取的能量数值
        needCal = userService.getNeedCalByUserId(userId) - cal;     //每天需要摄入的能量
        leftCal = needCal;
        clearListView();
        doneSportList = sportService.getDoneSportByUID(userId,date);
        showSport();
    }
    //展示后一天内容
    public void dayForward(View view){
        date = getTomorrowDay(date);
        tv_sportCon_date.setText(date);
        takenCal = foodService.getTodayTakenCalBy(userId,date);
        sportCal = sportService.getTodayExpandCalBy(userId,date);
        int cal = goalService.getLoseWeightData(userId);     //获取减肥时每天需要减少摄取的能量数值
        needCal = userService.getNeedCalByUserId(userId) - cal;     //每天需要摄入的能量
        leftCal = needCal;
        clearListView();
        doneSportList = sportService.getDoneSportByUID(userId,date);
        showSport();
    }

    //跳转 showsport 界面 注意：只能添加当日的饮食
    public void toShowSport(View view){
        Intent intent = new Intent(this,ShowSportActivity.class);
        intent.putExtra("userId",userId);
        startActivityForResult(intent,2);
    }

    //回到上一个界面
    public void goBack(View view){
        //TODO 注意
        //如果上一个页面没有finish 才能直接用
        //否则要用intent 打开上一个界面
        SportConditionActivity.this.finish();
    }

    //回调方法 完成添加后 展示
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2){
            if (resultCode == RESULT_OK){
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                date = df.format(new Date());
                tv_sportCon_date.setText(date);
                clearListView();
                doneSportList = sportService.getDoneSportByUID(userId,date);
                showSport();
            }
        }
    }

    //传入字符串 修改日期为前一天
    public static String getLastDay(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        try {
            date = sdf.parse(time);
        }catch (Exception e){
            e.printStackTrace();
        }
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);

        calendar.set(Calendar.DATE,day-1);
        String lastDay = sdf.format(calendar.getTime());
        return lastDay;
    }

    //传入字符串 修改日期为后一天
    public String getTomorrowDay(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        try {
            date = sdf.parse(time);
        }catch (Exception e){
            e.printStackTrace();
        }
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);

        calendar.set(Calendar.DATE,day+1);
        String tomorrowDay = sdf.format(calendar.getTime());
        return tomorrowDay;
    }

    //清屏 将若没有数据恢复到初始状态
    public void clearListView(){
        lv_todaySport.setVisibility(View.GONE);
        tv_sport_remind.setVisibility(View.VISIBLE);


        //TODO 获取当天运动热量
        refresh(needCal,takenCal,leftCal,sportCal);

    }

    //刷新各个数据 并展示
    public void refresh(int need,int taken,int left,int expand){
        left = need + expand - taken;
        if (left<0){
            //如果摄入过多
            tv_sportCon_middle_text.setText("多吃了！");
            tv_sportCon_middle_text.setTextColor(Color.RED);
            left = taken - expand -need;
            tv_sportCon_leftCal.setText(left+"");
            tv_sportCon_leftCal.setTextColor(Color.RED);
            tv_sportCon_taken.setText(taken+"");
            tv_sportCon_needCal.setText(need+"");
            tv_sportCon_expend.setText(expand+"");
        }else {
            tv_sportCon_middle_text.setText("还可以吃");
            tv_sportCon_middle_text.setTextColor(Color.BLACK);

            tv_sportCon_leftCal.setText(left+"");
            tv_sportCon_leftCal.setTextColor(Color.parseColor("#0097ff"));

            tv_sportCon_taken.setText(taken+"");
            tv_sportCon_needCal.setText(needCal+"");
            tv_sportCon_expend.setText(expand+"");

        }

    }


}
