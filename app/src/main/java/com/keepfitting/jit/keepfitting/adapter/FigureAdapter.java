package com.keepfitting.jit.keepfitting.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 14032 on 2020/6/17.
 */

public class FigureAdapter extends FragmentPagerAdapter {
    private List<String> titlelist;
    private List<Fragment> fragmentList;
    private Fragment mCurrentFragment;
    public FigureAdapter(FragmentManager fm, List<String> titlelist, List<Fragment> fragmentList) {
        super(fm);
        this.titlelist = titlelist;
        this.fragmentList = fragmentList;
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
//        return new Figure01Fragment();
    }
    @Override
    public int getCount() {
        return titlelist.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titlelist.get(position);
    }

//    @Override
//    public void setPrimaryItem(ViewGroup container, int position, Object object) {
//        mCurrentFragment = (Fragment)object;
//        super.setPrimaryItem(container, position, object);
//    }
//
//    public Fragment getCurrentFragment() {
//        return mCurrentFragment;
//    }

}