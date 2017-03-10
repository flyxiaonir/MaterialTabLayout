package com.nineton.materialtabview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/9.
 */

public class CustomTabViewPagerAdapter extends FragmentPagerAdapter {
    private  List<Fragment> mFragments ;//Fragment集合
    private  List<String> mFragmentsTitles ;//title集合

    public CustomTabViewPagerAdapter(FragmentManager fm,List<Fragment>fragments,List<String> tabsTitle) {
        super(fm);
        mFragments = fragments;
        mFragmentsTitles=tabsTitle;
    }
    public CustomTabViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
        mFragmentsTitles=new ArrayList<>();
    }
    public void addFragment(Fragment fragment, String fragmentTitle) {

        mFragments.add(fragment);
        mFragmentsTitles.add(fragmentTitle);
    }

    @Override
    public Fragment getItem(int position) {

        return mFragments.get(position);
    }

    @Override
    public int getCount() {

        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mFragmentsTitles.get(position);
    }
}