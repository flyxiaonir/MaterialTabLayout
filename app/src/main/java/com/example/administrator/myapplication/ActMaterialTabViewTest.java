package com.example.administrator.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nineton.materialtabview.CustomTabViewPagerAdapter;
import com.nineton.materialtabview.FragTabViewTest;

import static android.R.attr.fragment;

/**
 * Created by Administrator on 2017/3/9.
 */

public class ActMaterialTabViewTest extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.nineton.materialtabview.R.layout.material_tab_view_layout);
        Toolbar mToolbar = (Toolbar) findViewById(com.nineton.materialtabview.R.id.toolBar);
        mToolbar.setTitleTextColor(Color.WHITE);//设置ToolBar的titl颜色
        setSupportActionBar(mToolbar);

        ViewPager mViewPager = (ViewPager) findViewById(com.nineton.materialtabview.R.id.viewpager);
        CustomTabViewPagerAdapter viewPagerAdapter = new CustomTabViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new FragTabViewTest(), "TabOne");//添加Fragment
        viewPagerAdapter.addFragment(new FragTabViewTest(), "TabTwo");
        viewPagerAdapter.addFragment(new FragTabViewTest(), "TabThree");
        mViewPager.setAdapter(viewPagerAdapter);//设置适配器

        TabLayout mTabLayout = (TabLayout) findViewById(com.nineton.materialtabview.R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("TabOne"));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText("TabTwo"));
        mTabLayout.addTab(mTabLayout.newTab().setText("TabThree"));
        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题
    }
}
