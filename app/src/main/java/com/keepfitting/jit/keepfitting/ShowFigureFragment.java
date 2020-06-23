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
import android.widget.TextView;
import android.widget.Toast;

import com.keepfitting.jit.keepfitting.adapter.FigureAdapter;
import com.keepfitting.jit.keepfitting.fragments.Figure00Fragment;
import com.keepfitting.jit.keepfitting.fragments.Figure01Fragment;
import com.keepfitting.jit.keepfitting.fragments.Figure02Fragment;
import com.keepfitting.jit.keepfitting.fragments.Figure03Fragment;
import com.keepfitting.jit.keepfitting.fragments.Figure04Fragment;
import com.keepfitting.jit.keepfitting.fragments.Figure05Fragment;
import com.keepfitting.jit.keepfitting.util.MyBottomDialog;

import java.util.ArrayList;
import java.util.List;


public class ShowFigureFragment extends Fragment  {

    private TabLayout tab;
    private ViewPager pager;
    private FloatingActionButton fab_write;
    private List<String> titlelist;
    private List<Fragment> fragmentList;

    private int pagerflag;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_figure, container, false);

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
                                //todo 填写数据
                                Toast.makeText(getContext(),num+"QQ",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(),num+"微信",Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    });
            fab_write.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.show();
                }
            });
        }else {
            fab_write.setVisibility(View.GONE);
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
                System.out.println("aaa"+pagerflag);
                final MyBottomDialog dialog = new MyBottomDialog(getContext(),pagerflag,
                        new MyBottomDialog.OnClickListener() {
                            public void onClick(Dialog dialog, int flag, float num) {
                                //todo 填写数据
                                if(flag == 1) {
                                    Toast.makeText(getContext(),num+"QQ",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getContext(),num+"微信",Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        });
                fab_write.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.show();
                    }
                });
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

}