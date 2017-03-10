package com.nineton.materialtabview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2017/3/9.
 */

public class CustomTabView extends FrameLayout {
    private final String TAG = "MaterialTabView";
    private Toolbar mToolbar;
    private AppBarLayout mAppbarLayout;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private int mCurrentState = -1;
    private FrameLayout mTopWindowFrame;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private final int FLAG_COLLAPSED = 2;//收缩
    private final int FLAG_INTERNEDIATE = 1;//滑动中
    private final int FLAG_EXPANDED = 0;//完全完成
    private OnTabStatusChangeListener mListener;
    private TabDataHolder mDataHolder;
    private Context mContext;
    private int mTopWindowHeight =240,mTopWindowCollpaseMinimumHeight=100;

    public CustomTabView(Context context) {
        super(context);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.custom_tab_layout, this);
    }

    public CustomTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.custom_tab_layout, this);
        initView();
    }


    private void initView() {
        mAppbarLayout = (AppBarLayout) findViewById(R.id.layout_appbar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTopWindowFrame = (FrameLayout) findViewById(R.id.top_window_frameLayout);


        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.ctb);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mListener != null) {
                    mListener.onPageScrolled(mDataHolder,position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (mListener != null) {
                    mListener.onPageSelected(mDataHolder,position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mListener != null) {
                    mListener.onPageScrollStateChanged(mDataHolder,state);
                }
            }
        });
        mAppbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                Log.d("verticalOffset", appBarLayout.getTotalScrollRange() + "||" + verticalOffset + "");
                if (verticalOffset == 0) {
                    if (mCurrentState != FLAG_EXPANDED) {
                        mCurrentState = FLAG_EXPANDED;//修改状态标记为展开
//                        Log.d(TAG, "控件状态为展开状态");
                        if (mListener != null) {

                            mListener.onExpanded(mDataHolder,appBarLayout.getTotalScrollRange(), verticalOffset);
                        }
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {

                    if (mCurrentState != FLAG_COLLAPSED) {

                        mCurrentState = FLAG_COLLAPSED;//修改状态标记为折叠
//                        Log.d(TAG, "控件状态为收缩状态");
                        if (mListener != null) {
                            mListener.onCollapsed(mDataHolder,appBarLayout.getTotalScrollRange(), verticalOffset);
                        }
                    }
                } else {
//                    Log.d(TAG, "控件状态为中间状态");
                    if (mListener != null) {
                        mListener.onInternediate(mDataHolder,appBarLayout.getTotalScrollRange(), verticalOffset);
                    }
                    mCurrentState = FLAG_INTERNEDIATE;//修改状态标记为中间

                }
            }
        });
    }
    public void setData(@NonNull TabDataHolder dataHolder){
       //初始化顶部frame的高度和折叠剩余高度并添加顶部view
        setTopWindowHeight(mTopWindowHeight);
        setTopWindowCollpaseMinimumHeight(mTopWindowCollpaseMinimumHeight);
//        if(mDataHolder.bindingTopView(mTopWindowFrame)!=null){
//            mTopWindowFrame.addView(mDataHolder.bindingTopView(mTopWindowFrame));
//        }
        mDataHolder = dataHolder;
        mDataHolder.onCreateTopView(LayoutInflater.from(mContext),mTopWindowFrame);
//        if (topView!=null){
//            mTopWindowFrame.addView(topView);
//        }
        mDataHolder.onBindingPagerData(mTabLayout,mViewPager);
        //初始化tab与ViewPager数据
//        if (mDataHolder.getPagerFragments()!=null&&!mDataHolder.getPagerFragments().isEmpty()){
//            CustomTabViewPagerAdapter viewPagerAdapter = new CustomTabViewPagerAdapter(mContext.getSupportFragmentManager());
//        }
//        if (mDataHolder.getTabTitle()!=null&&!mDataHolder.getTabTitle().isEmpty()){
//
//        }
    }
    /**设置顶部窗口折叠最小高度
     * @param height
     */
    public void setTopWindowCollpaseMinimumHeight(int height){
        if (height>=mTopWindowHeight){
            mToolbar.setMinimumHeight(mTopWindowHeight);
            mTopWindowCollpaseMinimumHeight=mTopWindowHeight;
        }else if(height<=0){
            //完全隐藏
            mTopWindowCollpaseMinimumHeight=0;
            mToolbar.setMinimumHeight(mTopWindowCollpaseMinimumHeight);
        }else  {
            mTopWindowCollpaseMinimumHeight=height;
            mToolbar.setMinimumHeight(mTopWindowCollpaseMinimumHeight);
        }
        requestLayout();
    }

    /**设置顶部窗口展开最大高度
     * @param height
     */
    public void setTopWindowHeight(int height){
        mTopWindowHeight = height;
        AppBarLayout.LayoutParams lp= (AppBarLayout.LayoutParams) mCollapsingToolbarLayout.getLayoutParams();
//        AppBarLayout.LayoutParams lp = new AppBarLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height);
        lp.height=mTopWindowHeight;
        mCollapsingToolbarLayout.setLayoutParams(lp);
//        mCollapsingToolbarLayout.(height);
        requestLayout();
    }
    private void setData() {
//        toolbar.setLogo(R.mipmap.ic_launcher);
//        setSupportActionBar(toolbar);
        //设置返回按钮
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置收缩展开toolbar字体颜色
//        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
//        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);

        //设置tablayout与viewPager
//        viewpager.setAdapter(new CustomTabViewPagerAdapter());
//        tabLayout.setupWithViewPager(viewpager);
//        CustomTabViewPagerAdapter viewPagerAdapter = new CustomTabViewPagerAdapter(getSupportFragmentManager());
//
//        viewPagerAdapter.addFragment(new FragTabViewTest(), "TabOne");//添加Fragment
//        viewPagerAdapter.addFragment(new FragTabViewTest(), "TabTwo");
//        viewPagerAdapter.addFragment(new FragTabViewTest(), "TabThree");
//        viewpager.setAdapter(viewPagerAdapter);//设置适配器
//
//        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
//        tabLayout.addTab(tabLayout.newTab().setText("TabOne"));//给TabLayout添加Tab
//        tabLayout.addTab(tabLayout.newTab().setText("TabTwo"));
//        tabLayout.addTab(tabLayout.newTab().setText("TabThree"));
//        tabLayout.setupWithViewPager(viewpager);//给TabLayout设置关
    }




    public void setOnTabStatusChangeListener(OnTabStatusChangeListener listener){
        mListener=listener;
    }
    public  interface OnTabStatusChangeListener<Holder extends TabDataHolder> {
        void onPageScrolled(Holder holder, int position, float positionOffset, int positionOffsetPixels);

        void onPageSelected(Holder holder, int position);

        void onPageScrollStateChanged(Holder holder, int state);

        /**
         * 顶部窗口展开状态
         *
         * @param totalRange     顶部窗口可以滑动总距离
         * @param verticalOffset 当前滑动位置（注意负数）
         */
        void onExpanded(Holder holder, int totalRange, int verticalOffset);

        /**
         * 顶部窗口收缩状态
         *
         * @param totalRange     顶部窗口可以滑动总距离
         * @param verticalOffset 当前滑动位置（注意负数）
         */
        void onCollapsed(Holder holder, int totalRange, int verticalOffset);

        /**
         * 顶部窗口中间状态
         *
         * @param totalRange     顶部窗口可以滑动总距离
         * @param verticalOffset 当前滑动位置（注意负数）
         */
        void onInternediate(Holder holder, int totalRange, int verticalOffset);

    }
}
