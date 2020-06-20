package com.keepfitting.jit.keepfitting;

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
import android.widget.ImageView;

import com.keepfitting.jit.keepfitting.fragments.Figure00Fragment;
import com.keepfitting.jit.keepfitting.fragments.Figure01Fragment;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_menu;
    private DrawerLayout dl_menu;
    private NavigationView nav_view;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                switch (item.getItemId()){
                    case R.id.nav_home:
                        //todo 切换fragment
                        break;
                    case R.id.nav_mygoal:
                        //todo
                        break;
                    case R.id.nav_newgol:
                        //todo
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
                        //todo
                        break;
                    case R.id.nav_showconusumeCC:
                        //todo
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

        System.out.println(2);
    }
}
