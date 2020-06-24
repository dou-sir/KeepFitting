package com.keepfitting.jit.keepfitting;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.keepfitting.jit.keepfitting.adapter.FigureAdapter;
import com.keepfitting.jit.keepfitting.entity.Figure;
import com.keepfitting.jit.keepfitting.fragments.Figure00Fragment;
import com.keepfitting.jit.keepfitting.fragments.Figure01Fragment;
import com.keepfitting.jit.keepfitting.fragments.Figure02Fragment;
import com.keepfitting.jit.keepfitting.fragments.Figure03Fragment;
import com.keepfitting.jit.keepfitting.fragments.Figure04Fragment;
import com.keepfitting.jit.keepfitting.fragments.Figure05Fragment;
import com.keepfitting.jit.keepfitting.service.FigureService;
import com.keepfitting.jit.keepfitting.service.impl.FigureServiceImpl;
import com.keepfitting.jit.keepfitting.util.MyBottomDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ShowFigureFragment extends Fragment  {

    private TabLayout tab;
    private ViewPager pager;
    private LinearLayout ll_bottom;
    private TextView tv_recenttime;
    private FloatingActionButton fab_write;
    private List<String> titlelist;
    private List<Fragment> fragmentList;

    private int pagerflag;
    private FigureService figureService;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_figure, container, false);
        figureService = new FigureServiceImpl(getContext());

        Bundle bundle = getArguments();
        //通过showflag判断用哪个界面，showflag=0为编辑，=1为图表
        if (null != bundle &&bundle.getString("flag").equals("show")){
            init(view, 1);
        }else {
            init(view, 0);
        }

        return view;
    }


    private void init(View view, int showflag){

        this.pager = view.findViewById(R.id.pager);
        this.tab = view.findViewById(R.id.tab);
        fab_write = view.findViewById(R.id.fab_write);
        tv_recenttime = view.findViewById(R.id.tv_recenttime);
        ll_bottom = view.findViewById(R.id.ll_bottom);


        /*初始化数据*/
        titlelist = new ArrayList<>();
        fragmentList = new ArrayList<>();
        fragmentList.add(new Figure00Fragment());
        fragmentList.add(new Figure01Fragment());
        fragmentList.add(new Figure02Fragment());
        fragmentList.add(new Figure03Fragment());
        fragmentList.add(new Figure04Fragment());
        fragmentList.add(new Figure05Fragment());
        addTitle();
        /**
         * 通过showflag判断用哪个界面，showflag=0为编辑，=1为图表
         * showflag=1初始化fab_write，=0则隐藏
         */
        if (showflag != 0){
            Bundle bundle = new Bundle();
            bundle.putString("flag","show");
            for (int i = 0; i < fragmentList.size() ; i++) {
                fragmentList.get(i).setArguments(bundle);
            }
            final MyBottomDialog dialog = new MyBottomDialog(getContext(),pagerflag,
                    new MyBottomDialog.OnClickListener() {
                        public void onClick(Dialog dialog, int flag, float num) {
                            if(flag == 1) {
                                if(num!=0){
                                    doAddFigure(num);
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(getContext(),"不能取零值",Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                dialog.dismiss();
                            }
                        }
                    });
            fab_write.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.show();
                }
            });
            tv_recenttime.setText(figureService.findRecentByType(MainActivity.userinfo.getUserID(),findFigureType()).getRecordDate());
        }else {
            fab_write.setVisibility(View.GONE);
            ll_bottom.setVisibility(View.GONE);
        }

        /*设置Adapter*/
        pager.setAdapter(new FigureAdapter(getChildFragmentManager(),titlelist,fragmentList));
        /*Tab与ViewPager绑定*/
        tab.setupWithViewPager(pager);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                pagerflag = position;

                final MyBottomDialog dialog = new MyBottomDialog(getContext(),pagerflag,
                        new MyBottomDialog.OnClickListener() {
                            public void onClick(Dialog dialog, int flag, float num) {
                                //todo 填写数据
                                if(flag == 1) {
                                    if(num!=0){
                                        doAddFigure(num);
                                        dialog.dismiss();
                                    }else {
                                        Toast.makeText(getContext(),"不能取零值",Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    dialog.dismiss();
                                }
                            }
                        });
                fab_write.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.show();
                    }
                });
                String recenttime = "暂无数据";
                if(figureService.findRecentByType(MainActivity.userinfo.getUserID(),findFigureType()).getUserId()!=0)
                    recenttime = figureService.findRecentByType(MainActivity.userinfo.getUserID(),findFigureType()).getRecordDate();
                tv_recenttime.setText(recenttime);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void doAddFigure(float num){
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Figure figure = new Figure(MainActivity.userinfo.getUserID(),findFigureType(), num, formatter.format(new Date(currentTime)));
        boolean flag=figureService.addFigure(figure);
        if (!flag){
            if (figureService.modifyFigure(figure)){//todo
                tv_recenttime.setText(figureService.findRecentByType(MainActivity.userinfo.getUserID(),findFigureType()).getRecordDate());
                Toast.makeText(getContext(),"修改成功",Toast.LENGTH_SHORT).show();
            }else Toast.makeText(getContext(),"修改失败",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(),"添加成功",Toast.LENGTH_SHORT).show();
            System.out.println("bbb");
            tv_recenttime.setText(figureService.findRecentByType(MainActivity.userinfo.getUserID(),findFigureType()).getRecordDate());
        }
    }

    private void addTitle(){
        for (int i = 0; i < fragmentList.size() ; i++) {
            switch (i){
                case 0:
                    titlelist.add("体重");
                    break;
                case 1:
                    titlelist.add("胸围");
                    break;
                case 2:
                    titlelist.add("腰围");
                    break;
                case 3:
                    titlelist.add("左肩围");
                    break;
                case 4:
                    titlelist.add("右肩围");
                    break;
                case 5:
                    titlelist.add("肩宽");
                    break;
                default:
                    titlelist.add(fragmentList.get(i).getClass().getSimpleName());
            }

        }
    }

    private String findFigureType(){
        String str;
        switch (pagerflag){
            case 0:
                str = "weight";   break;
            case 1:
                str = "bust";   break;
            case 2:
                str = "waist";   break;
            case 3:
                str = "lshoulderCirc";   break;
            case 4:
                str = "rshoulderCirc";   break;
            case 5:
                str = "shoulderwidth";   break;
            default:    str = "weight";
        }
        return str;
    }

}