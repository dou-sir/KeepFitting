package com.keepfitting.jit.keepfitting;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.keepfitting.jit.keepfitting.entity.EatenFood;
import com.keepfitting.jit.keepfitting.entity.Food;
import com.keepfitting.jit.keepfitting.service.FoodService;
import com.keepfitting.jit.keepfitting.service.GoalService;
import com.keepfitting.jit.keepfitting.service.SportService;
import com.keepfitting.jit.keepfitting.service.UserService;
import com.keepfitting.jit.keepfitting.service.impl.FoodServiceImpl;
import com.keepfitting.jit.keepfitting.service.impl.GoalServiceImpl;
import com.keepfitting.jit.keepfitting.service.impl.SportServiceImpl;
import com.keepfitting.jit.keepfitting.service.impl.UserServiceImpl;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodConditionActivity extends AppCompatActivity {
    private TextView tv_foodCon_date,tv_foodCon_taken,tv_foodCon_leftCal,tv_foodCon_needCal,tv_foodCon_expend;
    private TextView tv_foodCon_middle_text,tv_breakfast_remind,tv_lunch_remind,tv_dinner_remind;
    private ListView lv_breakfast,lv_lunch,lv_dinner;
    private ImageView iv_toShowFood,iv_foodCon_toBack,iv_dayBack,iv_dayForward;
    private FoodService foodService;
    private UserService userService;
    private SportService sportService;
    private GoalService goalService;
    private List<EatenFood> eatenFoodList;              //今天吃的食物列表
    private List<Map<String,Object>> breakfast,lunch,dinner;
    private int breakfastCal=0,lunchCal=0,dinnerCal=0,totalCal=0;
    private int sportCal ;
    private static int needCal ;
    private int leftCal ;

    private static int userId;


    private String date;

    //TODO 运动的热量



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_condition);

        foodService = new FoodServiceImpl(FoodConditionActivity.this);
        sportService = new SportServiceImpl(FoodConditionActivity.this);
        userService = new UserServiceImpl(FoodConditionActivity.this);
        goalService = new GoalServiceImpl(FoodConditionActivity.this);

        initComponent();


        //TODO  获取每日能够摄取的能量 和 运动的能量
        Intent intent=getIntent();
        userId = intent.getIntExtra("userId",0);
        getAllData(userId);
        tv_foodCon_needCal.setText(needCal+"");

        init();
    }

    //组将绑定
    public void initComponent(){
        tv_foodCon_date = (TextView)findViewById(R.id.tv_foodCon_date);
        tv_foodCon_taken = (TextView)findViewById(R.id.tv_foodCon_taken);
        tv_foodCon_leftCal = (TextView)findViewById(R.id.tv_foodCon_leftCal);
        tv_foodCon_needCal = (TextView)findViewById(R.id.tv_foodCon_needCal);
        tv_foodCon_expend = (TextView)findViewById(R.id.tv_foodCon_expend);
        tv_foodCon_middle_text = (TextView)findViewById(R.id.tv_foodCon_middle_text);
        tv_breakfast_remind = (TextView)findViewById(R.id.tv_breakfast_remind);
        tv_lunch_remind = (TextView)findViewById(R.id.tv_lunch_remind);
        tv_dinner_remind = (TextView)findViewById(R.id.tv_dinner_remind);

        lv_breakfast = (ListView)findViewById(R.id.lv_breakfast);
        lv_lunch = (ListView)findViewById(R.id.lv_lunch);
        lv_dinner = (ListView)findViewById(R.id.lv_dinner);

        iv_toShowFood = (ImageView)findViewById(R.id.iv_toShowFood);
        iv_foodCon_toBack = (ImageView)findViewById(R.id.iv_foodCon_toBack);
        iv_dayBack = (ImageView)findViewById(R.id.iv_dayBack);
        iv_dayForward = (ImageView)findViewById(R.id.iv_dayForward);

    }

    public void init(){


        //获取今日日期 修改格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        date = df.format(new Date());
        tv_foodCon_date.setText(date);
        eatenFoodList = foodService.getEatenFoodByUID(userId,date);
        showFood();
    }

    //判断今天是否有吃东西 如果有就展示 如果没有就不展示
    public void showFood(){

        if (eatenFoodList.size()>0){
            for (EatenFood e:eatenFoodList){
                //如果有吃早饭
                if (e.getFoodType()==1){

                    int[] clas =  getEatenFoodsCal(e.getFoodsID(),e.getFoodsWeight());
                    breakfast = getTodayEatenFood(e,clas);
                    SimpleAdapter simpleAdapter =new SimpleAdapter(
                            FoodConditionActivity.this,                 //当前的Activity
                            breakfast,
                            R.layout.lv_style_takenfood,
                            new String[]{"name","weight","cal","img"},
                            new int[]{R.id.tv_item_takenFood_name,R.id.tv_item_takenFood_weight,R.id.tv_item_takenFood_cal,R.id.iv_item_takenFood_img}
                    );
                    lv_breakfast.setAdapter(simpleAdapter);
                    lv_breakfast.setVisibility(View.VISIBLE);

                    //提示消失
                    tv_breakfast_remind.setVisibility(View.GONE);
                    //更新并设置已经摄取的热量
                    breakfastCal = getTotalCal(clas);

                }else if(e.getFoodType()==2){

                    int[] clas =  getEatenFoodsCal(e.getFoodsID(),e.getFoodsWeight());
                    lunch = getTodayEatenFood(e,clas);
                    SimpleAdapter simpleAdapter =new SimpleAdapter(
                            FoodConditionActivity.this,                 //当前的Activity
                            lunch,
                            R.layout.lv_style_takenfood,
                            new String[]{"name","weight","cal","img"},
                            new int[]{R.id.tv_item_takenFood_name,R.id.tv_item_takenFood_weight,R.id.tv_item_takenFood_cal,R.id.iv_item_takenFood_img}
                    );
                    lv_lunch.setAdapter(simpleAdapter);
                    lv_lunch.setVisibility(View.VISIBLE);

                    //提示消失
                    tv_lunch_remind.setVisibility(View.GONE);
                    lunchCal = getTotalCal(clas);


                }else if (e.getFoodType()==3){
                    int[] clas =  getEatenFoodsCal(e.getFoodsID(),e.getFoodsWeight());
                    dinner = getTodayEatenFood(e,clas);
                    SimpleAdapter simpleAdapter =new SimpleAdapter(
                            FoodConditionActivity.this,                 //当前的Activity
                            dinner,
                            R.layout.lv_style_takenfood,
                            new String[]{"name","weight","cal","img"},
                            new int[]{R.id.tv_item_takenFood_name,R.id.tv_item_takenFood_weight,R.id.tv_item_takenFood_cal,R.id.iv_item_takenFood_img}
                    );
                    lv_dinner.setAdapter(simpleAdapter);
                    lv_dinner.setVisibility(View.VISIBLE);

                    //提示消失
                    tv_dinner_remind.setVisibility(View.GONE);
                    dinnerCal = getTotalCal(clas);
                }
            }

            //更新并设置已经摄取的热量
            totalCal = breakfastCal + lunchCal + dinnerCal;
            refresh(needCal,totalCal,leftCal,sportCal);
//            tv_foodCon_taken.setText(totalCal+"");
//            //更新还能摄入的热量
//            leftCal = needCal - totalCal - sportCal;
//            tv_foodCon_leftCal.setText(leftCal+"");
//
//            //判断摄入量是否超出范围
//            if((totalCal - sportCal)>needCal){
//                int a = (totalCal-sportCal)-needCal;
//                tv_foodCon_middle_text.setText("多吃了！");
//                tv_foodCon_middle_text.setTextColor(Color.RED);
//                tv_foodCon_leftCal.setText(a+"");
//                tv_foodCon_leftCal.setTextColor(Color.RED);
//            }


        }else {
            clearListView();
        }


    }

    //通过传入foodsId 和foodsWeight 来计算此次摄入的热量数组
    public int[] getEatenFoodsCal(String foodsId,String foodsWeight){
        //将str转化为int[]数组
        String[] ids = foodsId.split(",");
        String[] weights = foodsWeight.split(",");
        int[] takenFoodsId = new int[ids.length];
        int[] takenFoodsWeight = new int[weights.length];
        for (int i=0;i<ids.length;i++){
            takenFoodsId[i] = Integer.parseInt(ids[i]);
            takenFoodsWeight[i] = Integer.parseInt(weights[i]);
        }

        int[] takenFoodsCal = new int[takenFoodsId.length];
        //遍历id列表 找到食物的cal 计算热量
        for(int i=0;i<takenFoodsId.length;i++){
            Food food = foodService.findFoodByFoodId(takenFoodsId[i]);
            // 热量的计量单位是 /100g
            double kg = (double) takenFoodsWeight[i]/100;
            double calorie = kg * food.getFoodCalorie();
            //四舍五入
            takenFoodsCal[i] = (int) Math.round(calorie);
        }
        return takenFoodsCal;
    }

    //通过foodsCal 计算总共摄入的热量
    public int getTotalCal(int[] foodsCal){
        int totalCal = 0;
        for (int i =0;i<foodsCal.length;i++){
            totalCal += foodsCal[i];
        }
        return totalCal;
    }

    //用于与simpleAdapter 进行适配
    public List<Map<String,Object>> getTodayEatenFood(EatenFood eatenFood,int[] cals){
        //字符串改造为int数组
        String strFood = eatenFood.getFoodsID();
        String[] ids = strFood.split(",");
        String strWeight = eatenFood.getFoodsWeight();
        String[] weights = strWeight.split(",");
        int[] takenFoodsId = new int[ids.length];
        int[] takenFoodsWeight = new int[weights.length];
        for (int i=0;i<ids.length;i++){
            takenFoodsId[i] = Integer.parseInt(ids[i]);
            takenFoodsWeight[i] = Integer.parseInt(weights[i]);
        }

        int[] foodImg = new int[takenFoodsId.length];

        List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
        for (int i=0;i<takenFoodsId.length;i++){
            Map<String,Object> map = new HashMap<String, Object>();
            Food food = foodService.findFoodByFoodId(takenFoodsId[i]);
            map.put("name",food.getFoodName());
            map.put("weight",takenFoodsWeight[i]);
            map.put("cal",cals[i]);

            //获取imgid的数组
            Class drawable = R.drawable.class;
            Field field = null;
            int img = food.getFoodImg();
            String str = "food"+img;
            try {
                field = drawable.getField(str);
                foodImg[i] = field.getInt(field.getName());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("GET FoodImg ERROR!");
            }
            map.put("img",foodImg[i]);
            data.add(map);
        }

        return data;
    }

    //展示前一天的内容
    public void dayBack(View view){
        date = getLastDay(date);
        tv_foodCon_date.setText(date);
        totalCal = foodService.getTodayTakenCalBy(userId,date);
        sportCal = sportService.getTodayExpandCalBy(userId,date);
        int cal = goalService.getLoseWeightData(userId);
        needCal = userService.getNeedCalByUserId(userId)-cal;
        leftCal = needCal;
        clearListView();
        eatenFoodList = foodService.getEatenFoodByUID(userId,date);
        showFood();

    }

    //展示后一天的内容
    public void dayForward(View view){
        date = getTomorrowDay(date);
        tv_foodCon_date.setText(date);
        totalCal = foodService.getTodayTakenCalBy(userId,date);
        sportCal = sportService.getTodayExpandCalBy(userId,date);
        int cal = goalService.getLoseWeightData(userId);
        needCal = userService.getNeedCalByUserId(userId)-cal;
        leftCal = needCal;
        clearListView();
        eatenFoodList = foodService.getEatenFoodByUID(userId,date);
        showFood();
    }

    //跳转 showfood 界面 注意：只能添加当日的饮食
    public void toShowFood(View view){
        Intent intent = new Intent(this,ShowFoodActivity.class);
        intent.putExtra("userId",userId);
        startActivityForResult(intent,2);
    }

    //回到上一个界面
    public void goBack(View view){
        //TODO 注意
        //如果上一个页面没有finish 才能直接用
        //否则要用intent 打开上一个界面
        FoodConditionActivity.this.finish();
    }

    //回调方法 完成添加后 展示
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==2){
            if (resultCode == RESULT_OK){
                //获取今天日期 再次展示
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                date = df.format(new Date());
                tv_foodCon_date.setText(date);
                clearListView();
                eatenFoodList = foodService.getEatenFoodByUID(userId,date);
                showFood();
            }
        }
    }

    //传入字符串 修改日期为前一天
    public static String getLastDay(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date=null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int day=calendar.get(Calendar.DATE);
        //                      此处修改为+1则是获取后一天
        calendar.set(Calendar.DATE,day-1);
        String lastDay = sdf.format(calendar.getTime());
        return lastDay;
    }

    //传入字符串 修改日期为后一天
    public String getTomorrowDay(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date=null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int day=calendar.get(Calendar.DATE);
        //                      此处修改为+1则是获取后一天
        calendar.set(Calendar.DATE,day+1);
        String tomorrowDay = sdf.format(calendar.getTime());
        return tomorrowDay;
    }

    //输入footType类型 完成清屏
    //否则获取前（后）一天 数据时，数据为空 则会显示上次显示的内容
    public void clearListView(){
        lv_breakfast.setVisibility(View.GONE);
        lv_lunch.setVisibility(View.GONE);
        lv_dinner.setVisibility(View.GONE);
        tv_breakfast_remind.setVisibility(View.VISIBLE);
        tv_lunch_remind.setVisibility(View.VISIBLE);
        tv_dinner_remind.setVisibility(View.VISIBLE);

        refresh(needCal,totalCal,leftCal,sportCal);
    }

    //刷新各个数据 并展示
    public void refresh(int need,int taken,int left,int expand){
        left = need + expand - taken;
        if (left<0){
            //如果摄入过多
            tv_foodCon_middle_text.setText("多吃了！");
            tv_foodCon_middle_text.setTextColor(Color.RED);
            left = taken - expand -need;
            tv_foodCon_leftCal.setText(left+"");
            tv_foodCon_leftCal.setTextColor(Color.RED);
            tv_foodCon_taken.setText(taken+"");
            tv_foodCon_needCal.setText(need+"");
            tv_foodCon_expend.setText(expand+"");
        }else {
            tv_foodCon_middle_text.setText("还可以吃");
            tv_foodCon_middle_text.setTextColor(Color.BLACK);

            tv_foodCon_leftCal.setText(left+"");
            tv_foodCon_leftCal.setTextColor(Color.parseColor("#0097ff"));

            tv_foodCon_taken.setText(taken+"");
            tv_foodCon_needCal.setText(needCal+"");
            tv_foodCon_expend.setText(expand+"");

        }

    }

    //获取所有数据
    public void getAllData(int uid){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(new Date());
        int cal = goalService.getLoseWeightData(uid);
        needCal = userService.getNeedCalByUserId(uid) - cal;
        sportCal = sportService.getTodayExpandCalBy(uid,date);
        leftCal = needCal;

    }
}
