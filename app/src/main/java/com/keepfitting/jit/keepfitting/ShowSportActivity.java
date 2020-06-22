package com.keepfitting.jit.keepfitting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.keepfitting.jit.keepfitting.adapter.SportAdapter;
import com.keepfitting.jit.keepfitting.entity.DoneSport;
import com.keepfitting.jit.keepfitting.entity.Sport;
import com.keepfitting.jit.keepfitting.service.SportService;
import com.keepfitting.jit.keepfitting.service.impl.SportServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ShowSportActivity extends AppCompatActivity {
    private ImageView iv_showSport_back;
    private TextView tv_show_sport_number;
    private EditText ed_showSport_search;
    private Button btn_showSport_search;
    private ListView lv_showSport;

    private List<Sport> sportList;
    private SportService sportService;
    private SportAdapter sportAdapter;

    //统一存放运动数据
    private DoneSport doneSportFinal;

    //用于显示已经添加运动的个数
    private int sportNum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sport);

        initComponent();
        init();

    }

    //初始化组件
    public void initComponent(){
        iv_showSport_back = (ImageView)findViewById(R.id.iv_showSport_back);
        tv_show_sport_number = (TextView)findViewById(R.id.tv_show_sport_number);
        ed_showSport_search = (EditText)findViewById(R.id.ed_showSport_search);
        btn_showSport_search = (Button)findViewById(R.id.btn_showSport_search);
        lv_showSport = (ListView)findViewById(R.id.lv_showSport);

        //设置不可见
        tv_show_sport_number.setVisibility(View.GONE);

    }

    //初始化方法
    public void init(){

        doneSportFinal = new DoneSport();

        //获取包含所有运动名单
        sportService = new SportServiceImpl(ShowSportActivity.this);
        sportList = sportService.getAllSportList();
        //与自定义适配器适配
        sportAdapter = new SportAdapter(sportList,ShowSportActivity.this);
        lv_showSport.setAdapter(sportAdapter);

        //获取今日数据
        //TODO 测试
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String str = df.format(new Date());

        List<DoneSport> doneSports = sportService.getDoneSportByUID(1,str);
        if (doneSports.size()>0) {
            for (DoneSport doneSport : doneSports) {
                System.out.println(doneSport);
            }
        }else {
            System.out.println("没有数据");
        }
    }

    //回到上一个Activity
    public void goBack(View view){
        ShowSportActivity.this.finish();
    }

    //添加已经选中的运动到数据库
    public void AddDoneSport(View view){
        sportService.AddDoneSport(doneSportFinal);

        Intent intent = new Intent(this,SportConditionActivity.class);
        setResult(RESULT_OK,intent);
        ShowSportActivity.this.finish();
    }

    //搜索食物功能
    public void SearchSport(){
        String str = ed_showSport_search.getText().toString();
        sportList = sportService.SearchSportByName(str);
        sportAdapter = new SportAdapter(sportList,ShowSportActivity.this);
        lv_showSport.setAdapter(sportAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==1){
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getBundleExtra("bundle");
                DoneSport doneSport = (DoneSport) bundle.getSerializable("doneSport");

                if(doneSportFinal.getSportsId()==null){
                    //设置共同的数据
                    doneSportFinal.setUserID(doneSport.getUserID());
                    doneSportFinal.setDate(doneSport.getDate());
                    doneSportFinal.setSportsId(doneSport.getSportId()+"");
                    doneSportFinal.setSportsTime(doneSport.getSportTime()+"");

                    sportNum = 1;
                }else {
                    doneSportFinal.setSportsId(doneSportFinal.getSportsId()+","+doneSport.getSportId());
                    doneSportFinal.setSportsTime(doneSportFinal.getSportsTime()+","+doneSport.getSportTime());

                    sportNum ++;
                }

            }

        }

        if(sportNum>0){
            tv_show_sport_number.setText(sportNum+"");
            tv_show_sport_number.setVisibility(View.VISIBLE);
        }

    }

}
