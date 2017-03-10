package com.nineton.materialtabview;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2017/3/10.
 */

public abstract class TabDataHolder {


    public abstract View onCreateTopView(LayoutInflater inflater, ViewGroup container);

    public abstract void onBindingPagerData(TabLayout tabLayout, ViewPager viewPager);


}
