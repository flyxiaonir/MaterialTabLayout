package com.example.administrator.myapplication.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nineton.materialtabview.CustomTabViewPagerAdapter;
import com.nineton.materialtabview.TabDataHolder;
import com.example.administrator.myapplication.R;
import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */

public class ActNewsTabHolder extends TabDataHolder {
    private ImageView mBg, mIconImage;
    private View mTopMask;
    private View mTopRootView;
    private RespNewsCate mTypeData;
    private FragmentManager mFm;
    public ActNewsTabHolder(RespNewsCate mTypeData, FragmentManager fm) {
        this.mTypeData = mTypeData;
        mFm = fm;
    }

    @Override
    public View onCreateTopView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.body_top_layout,container);
        mTopRootView =container;
        mBg = (ImageView) mTopRootView.findViewById(R.id.bg_image);
        mIconImage = (ImageView) mTopRootView.findViewById(R.id.iconImage);
//        mIconImage.setImageResource(R.drawable.ic_tiyu);
        mTopMask = mTopRootView.findViewById(R.id.mask_view);
        return mTopRootView;
    }

    @Override
    public void onBindingPagerData(TabLayout tabLayout, ViewPager viewPager) {
        List<RespNewsCate.DataBean> typeList =mTypeData.getData();
        CustomTabViewPagerAdapter viewPagerAdapter = new CustomTabViewPagerAdapter(mFm);
        for (int i = 0; i <typeList.size() ; i++) {
            FragNewsHot fragment = new FragNewsHot();
            Bundle bundle = new Bundle();
            bundle.putString("title",typeList.get(i).getName());
            bundle.putInt("id",typeList.get(i).getId());
            fragment.setArguments(bundle);
            viewPagerAdapter.addFragment(fragment,typeList.get(i).getName());
            tabLayout.addTab(tabLayout.newTab().setText(typeList.get(i).getName()));
        }
        viewPager.setAdapter(viewPagerAdapter);//设置适配器
        tabLayout.setupWithViewPager(viewPager);//给TabLayout设置关
    }
    public RespNewsCate getmTypeData() {
        return mTypeData;
    }

    public ImageView getBg() {
        return mBg;
    }

    public ImageView getIconImage() {
        return mIconImage;
    }

    public View getmTopMask() {
        return mTopMask;
    }

    public View getmTopRootView() {
        return mTopRootView;
    }
}
