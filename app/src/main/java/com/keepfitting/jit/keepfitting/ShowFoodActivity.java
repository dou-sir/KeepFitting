package com.keepfitting.jit.keepfitting;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.keepfitting.jit.keepfitting.adapter.FoodAdapter;
import com.keepfitting.jit.keepfitting.entity.EatenFood;
import com.keepfitting.jit.keepfitting.entity.Food;
import com.keepfitting.jit.keepfitting.service.FoodService;
import com.keepfitting.jit.keepfitting.service.impl.FoodServiceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowFoodActivity extends AppCompatActivity {
    private ImageView iv_showFood_back;
    private ListView lv_showFood;
    private EditText ed_showFood_search;
    private List<Food> foodList;
    private FoodService foodService;
    private TextView tv_show_food_confirm,tv_show_food_number;
    private List<EatenFood> eatenFoodList;
    private EatenFood eatenFood1,eatenFood2,eatenFood3;
    private FoodAdapter foodAdapter;
    //用于显示已经添加食物的个数
    private int breakfastNum = 0;
    private int lunchNum = 0;
    private int dinnerNum = 0;

    private static int userId;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_food);

        eatenFood1 = new EatenFood();           //存放早饭的数据
        eatenFood2 = new EatenFood();           //存放午饭的数据
        eatenFood3 = new EatenFood();           //存放晚饭的数据  必须在onCreate中创建

        //需要添加到数据库的list 初始化
        eatenFoodList = new ArrayList<>();

        //获取userId
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId",0);

        initComponent();
        init();
    }

    //初始化组件
    public void initComponent(){
        iv_showFood_back = (ImageView) findViewById(R.id.iv_showFood_back);
        lv_showFood = (ListView) findViewById(R.id.lv_showFood);
        tv_show_food_confirm = (TextView)findViewById(R.id.tv_show_food_confirm);

        //在tv_show_food_confirm 左上角显示数字提醒
        tv_show_food_number = (TextView)findViewById(R.id.tv_show_food_number);
        tv_show_food_number.setVisibility(View.GONE);   //设置不可见

        ed_showFood_search = (EditText)findViewById(R.id.ed_showFood_search);

    }

    //初始化方法
    public void init(){

        //获取包含所有食物名单
        foodService = new FoodServiceImpl(ShowFoodActivity.this);
        foodList = foodService.getAllFoodList();
        //与自定义的适配器适配

        //final FoodAdapter foodAdapter = new FoodAdapter(foodList,ShowFoodActivity.this);
        foodAdapter = new FoodAdapter(foodList,ShowFoodActivity.this);
        lv_showFood.setAdapter(foodAdapter);


        //获取今日的数据
        //TODO 测试
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String str = df.format(new Date());

        List<EatenFood> eatenFoods = foodService.getEatenFoodByUID(userId,str);
        if (eatenFoods.size()>0) {
            for (EatenFood eatenFood : eatenFoods) {
                System.out.println(eatenFood);
            }
        }else {
            System.out.println("没有数据");
        }
    }

    //回到上一个activity
    public void goBack(View view){
        ShowFoodActivity.this.finish();
    }

    //添加已经选中的食物到数据库
    public void AddEatenFood(View view){


//        EatenFood eatenFood11 = new EatenFood(0,1,"2020-6-18","1,24,22","80,80,80",1500);
//        eatenFoodList.add(eatenFood11);
        foodService.AddEatenFood(eatenFoodList);
        Intent intent = new Intent(this,FoodConditionActivity.class);
        setResult(RESULT_OK,intent);
        ShowFoodActivity.this.finish();
    }

    //搜索食物功能

    public void SearchFood(View view){
        String foodName = ed_showFood_search.getText().toString();
        foodList = foodService.SearchEatenFoodByName(foodName);
        foodAdapter = new FoodAdapter(foodList,ShowFoodActivity.this);
        lv_showFood.setAdapter(foodAdapter);
    }

    //接收AddFoodConfirm传递来的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1){
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getBundleExtra("bundle");
                EatenFood eatenFood = (EatenFood) bundle.getSerializable("eatenFood");
                //System.out.println(eatenFood);

                if (eatenFood.getFoodType()==1){
                    if (eatenFood1.getFoodsID()==null)                  //如果foodsID为空
                    {
                        //设置其共同的数据
                        eatenFood1.setUserID(eatenFood.getUserID());
                        eatenFood1.setFoodType(eatenFood.getFoodType());
                        eatenFood1.setDate(eatenFood.getDate());
                        eatenFood1.setNeedCal(eatenFood.getNeedCal());
                        eatenFood1.setFoodsID(eatenFood.getFoodID()+"");
                        eatenFood1.setFoodsWeight(eatenFood.getFoodWeight()+"");
                        eatenFood1.setNeedCal(eatenFood.getNeedCal());

                        //Add中 foodsWeight为0 修改以解决
                        //eatenFood1.setFoodWeight(eatenFood.getFoodWeight());
                        breakfastNum = 1;
                        //添加到List中 必须在第一次时添加 不然会重复加入多组数据 后续修改的仍然时同一对象
                        eatenFoodList.add(eatenFood1);
                    }else {
                        String foodsID = eatenFood1.getFoodsID();
                        foodsID = foodsID + "," +eatenFood.getFoodID();
                        eatenFood1.setFoodsID(foodsID);

                        String foodsWeight = eatenFood1.getFoodsWeight();
                        foodsWeight = foodsWeight + "," + eatenFood.getFoodWeight();
                        eatenFood1.setFoodsWeight(foodsWeight);
                        breakfastNum++;
                    }

                }else if(eatenFood.getFoodType()==2){
                    if (eatenFood2.getFoodsID()==null)                  //如果foodsID为空
                    {
                        //设置其共同的数据
                        eatenFood2.setUserID(eatenFood.getUserID());
                        eatenFood2.setFoodType(eatenFood.getFoodType());
                        eatenFood2.setDate(eatenFood.getDate());
                        eatenFood2.setNeedCal(eatenFood.getNeedCal());
                        eatenFood2.setFoodsID(eatenFood.getFoodID()+"");
                        eatenFood2.setFoodsWeight(eatenFood.getFoodWeight()+"");
                        eatenFood2.setNeedCal(eatenFood.getNeedCal());
                        lunchNum = 1;
                        eatenFoodList.add(eatenFood2);              //添加到List中
                    }else {
                        String foodsID = eatenFood2.getFoodsID();
                        foodsID = foodsID + "," +eatenFood.getFoodID();
                        eatenFood2.setFoodsID(foodsID);

                        String foodsWeight = eatenFood2.getFoodsWeight();
                        foodsWeight = foodsWeight + "," + eatenFood.getFoodWeight();
                        eatenFood2.setFoodsWeight(foodsWeight);
                        lunchNum++;
                    }

                }else if(eatenFood.getFoodType()==3){
                    if (eatenFood3.getFoodsID()==null)                  //如果foodsID为空
                    {
                        //设置其共同的数据
                        eatenFood3.setUserID(eatenFood.getUserID());
                        eatenFood3.setFoodType(eatenFood.getFoodType());
                        eatenFood3.setDate(eatenFood.getDate());
                        eatenFood3.setNeedCal(eatenFood.getNeedCal());
                        eatenFood3.setFoodsID(eatenFood.getFoodID()+"");
                        eatenFood3.setFoodsWeight(eatenFood.getFoodWeight()+"");
                        eatenFood3.setNeedCal(eatenFood.getNeedCal());
                        dinnerNum = 1;
                        eatenFoodList.add(eatenFood3);              //添加到List中

                    }else {
                        String foodsID = eatenFood3.getFoodsID();
                        foodsID = foodsID + "," +eatenFood.getFoodID();
                        eatenFood3.setFoodsID(foodsID);

                        String foodsWeight = eatenFood3.getFoodsWeight();
                        foodsWeight = foodsWeight + "," + eatenFood.getFoodWeight();
                        eatenFood3.setFoodsWeight(foodsWeight);
                        dinnerNum++;
                    }
                }

            }
        }

        int num = breakfastNum+lunchNum+dinnerNum;
        if (num>0){
            //设置右上角的图标 显示已经添加的食物数量
            tv_show_food_number.setText(num+"");
            tv_show_food_number.setVisibility(View.VISIBLE);
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    public static int getUserId(){
        return userId;
    }
}
