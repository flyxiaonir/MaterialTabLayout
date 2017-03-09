package com.nineton.materialtabview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by Administrator on 2017/3/9.
 */

public class MaterialTabView extends FrameLayout {
    private final String TAG = "MaterialTabView";
    private Toolbar toolbar;
    private AppBarLayout appbarLayout;
    private ViewPager viewpager;
    private TabLayout tabLayout;
    private int currentState = -1;
    private FrameLayout mTopWindowFrame;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private final int FLAG_COLLAPSED = 2;//收缩
    private final int FLAG_INTERNEDIATE = 1;//滑动中
    private final int FLAG_EXPANDED = 0;//完全完成
    private OnTabStatusChangeListener mListener;
    private Context mContext;

    public MaterialTabView(Context context) {
        super(context);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.material_custom_tab_layout, this);
    }

    public MaterialTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.material_custom_tab_layout, this);
        initView();
    }


    private void initView() {
        appbarLayout = (AppBarLayout) findViewById(R.id.layout_appbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTopWindowFrame = (FrameLayout) findViewById(R.id.top_window_frameLayout);
        viewpager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.ctb);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mListener != null) {
                    mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (mListener != null) {
                    mListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mListener != null) {
                    mListener.onPageScrollStateChanged(state);
                }
            }
        });
        appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d("verticalOffset", appBarLayout.getTotalScrollRange() + "||" + verticalOffset + "");
                if (verticalOffset == 0) {
                    if (currentState != FLAG_EXPANDED) {
                        currentState = FLAG_EXPANDED;//修改状态标记为展开
                        Log.d(TAG, "控件状态为展开状态");
                        if (mListener != null) {

                            mListener.onExpanded(appBarLayout.getTotalScrollRange(), verticalOffset);
                        }
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {

                    if (currentState != FLAG_COLLAPSED) {

                        currentState = FLAG_COLLAPSED;//修改状态标记为折叠
                        Log.d(TAG, "控件状态为收缩状态");
                        if (mListener != null) {
                            mListener.onCollapsed(appBarLayout.getTotalScrollRange(), verticalOffset);
                        }
                    }
                } else {
                    Log.d(TAG, "控件状态为中间状态");
                    if (mListener != null) {
                        mListener.onInternediate(appBarLayout.getTotalScrollRange(), verticalOffset);
                    }
                    currentState = FLAG_INTERNEDIATE;//修改状态标记为中间

                }
            }
        });
    }

    /**设置顶部窗口滑动最小高度
     * @param height
     */
    private void setTopWindowScrollMinimumHeight(int height){
        toolbar.setMinimumHeight(height);
        requestLayout();
    }

    /**设置顶部窗口展开高度
     * @param height
     */
    private void setTopWindowHeight(int height){
        AppBarLayout.LayoutParams lp= (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
//        AppBarLayout.LayoutParams lp = new AppBarLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height);
        lp.height=height;
        collapsingToolbarLayout.setLayoutParams(lp);
//        collapsingToolbarLayout.(height);
        requestLayout();
    }
    private void setData() {
//        toolbar.setLogo(R.mipmap.ic_launcher);
//        setSupportActionBar(toolbar);
        //设置返回按钮
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置收缩展开toolbar字体颜色
//        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
//        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);

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

    private void addTabView() {

    }

    public interface OnTabStatusChangeListener {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);

        /**
         * 展开状态
         *
         * @param totalRange     顶部窗口可以滑动总距离
         * @param verticalOffset 当前滑动距离（注意负数）
         */
        void onExpanded(int totalRange, int verticalOffset);

        /**
         * 收缩状态
         *
         * @param totalRange     顶部窗口可以滑动总距离
         * @param verticalOffset 当前滑动距离（注意负数）
         */
        void onCollapsed(int totalRange, int verticalOffset);

        /**
         * 中间状态
         *
         * @param totalRange     顶部窗口可以滑动总距离
         * @param verticalOffset 当前滑动距离（注意负数）
         */
        void onInternediate(int totalRange, int verticalOffset);

    }
}
