package com.example.administrator.myapplication;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineton.materialtabview.CustomTabViewPagerAdapter;
import com.nineton.materialtabview.FragTabViewTest;

/**
 * Created by Administrator on 2017/3/9.
 */

public class ActCustomTabView extends AppCompatActivity {
    private final String TAG = "ActCustomTabView";
    private Toolbar toolbar;
    private AppBarLayout appbarLayout;
    private ImageView image,iconImage;
    private ViewPager viewpager;
    private TabLayout tabLayout;
    private View mask_view;
    private int currentState = -1;
    private final int FLAG_COLLAPSED = 2;//收缩
    private final int FLAG_INTERNEDIATE = 1;//滑动中
    private final int FLAG_EXPANDED = 0;//完全完成
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }*/
        setContentView(com.nineton.materialtabview.R.layout.material_custom_tab_layout);
        initView();
        initData();
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("Page", position + "显示显示效果");
                iconInAnim(iconImage,null);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        appbarLayout = (AppBarLayout) findViewById(com.nineton.materialtabview.R.id.layout_appbar);
        toolbar = (Toolbar) findViewById(com.nineton.materialtabview.R.id.toolbar);
        image = (ImageView) findViewById(com.nineton.materialtabview.R.id.image);
        mask_view =findViewById(com.nineton.materialtabview.R.id.mask_view);
        iconImage = (ImageView) findViewById(com.nineton.materialtabview.R.id.iconImage);
        viewpager = (ViewPager) findViewById(com.nineton.materialtabview.R.id.viewPager);
        tabLayout = (TabLayout) findViewById(com.nineton.materialtabview.R.id.tabLayout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(com.nineton.materialtabview.R.id.ctb);
//        AppBarLayout.LayoutParams lp= (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
////        AppBarLayout.LayoutParams lp = new AppBarLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height);
//        lp.height=800;
//        AppBarLayout.LayoutParams lp = new AppBarLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,500);
//        collapsingToolbarLayout.setLayoutParams(lp);
//        collapsingToolbarLayout.setTitle("");
        appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d("verticalOffset", appBarLayout.getTotalScrollRange() + "||" + verticalOffset + "");
                if (verticalOffset == 0) {
                    if (currentState != FLAG_EXPANDED) {
                        currentState = FLAG_EXPANDED;//修改状态标记为展开
                        Log.d(TAG, "控件状态为展开状态");
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (currentState != FLAG_COLLAPSED) {
                        Log.d(TAG, "控件状态为收缩状态");
                        currentState = FLAG_COLLAPSED;//修改状态标记为折叠
                    }
                } else {
                    Log.d(TAG, "控件状态为中间状态");
                    currentState = FLAG_INTERNEDIATE;//修改状态标记为中间
                }
            }
        });
    }
    private void iconInAnim(final View v, Animation anim){

        Animation  anim1 = AnimationUtils.loadAnimation(this, com.nineton.materialtabview.R.anim.anim_icon_out);
//        v.setAnimation(anim);
        anim1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(TAG,"返回动画");
                Animation  anim2 = AnimationUtils.loadAnimation(ActCustomTabView.this, com.nineton.materialtabview.R.anim.anim_icon_in);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Animator animator = ViewAnimationUtils.createCircularReveal(
                            mask_view,
                            mask_view.getWidth()/2,
                            mask_view.getHeight()/2,
                            0,
                            mask_view.getWidth()/2);
                    mask_view.setBackgroundColor(Color.BLUE);
                    mask_view.setAlpha(1.0f);
                    animator.setInterpolator(new AccelerateInterpolator());
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            Log.d(TAG,"animator返回动画");
//                        mask_view.setBackgroundColor(Color.GREEN);
                            ObjectAnimator anpim4 =ObjectAnimator.ofFloat(mask_view,"alpha",0.9f,0.4f);
                            anpim4.setDuration(300);
                            anpim4.setStartDelay(100);
                            anpim4.setInterpolator(new AccelerateInterpolator());
                            anpim4.start();
//                        Animation  anim3 = AnimationUtils.loadAnimation(ActCustomTabView.this,R.anim.mask_alpha_anim);
//                        anim3.setFillAfter(true);
//                        mask_view.startAnimation(anim3);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    animator.setDuration(400);
                    animator.start();

                    v.startAnimation(anim2);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(anim1);
    }
    private void initData() {
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
        CustomTabViewPagerAdapter viewPagerAdapter = new CustomTabViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new FragTabViewTest(), "TabOne");//添加Fragment
        viewPagerAdapter.addFragment(new FragTabViewTest(), "TabTwo");
        viewPagerAdapter.addFragment(new FragTabViewTest(), "TabThree");
        viewpager.setAdapter(viewPagerAdapter);//设置适配器

        tabLayout = (TabLayout) findViewById(com.nineton.materialtabview.R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("TabOne"));//给TabLayout添加Tab
        tabLayout.addTab(tabLayout.newTab().setText("TabTwo"));
        tabLayout.addTab(tabLayout.newTab().setText("TabThree"));
        tabLayout.setupWithViewPager(viewpager);//给TabLayout设置关
    }

    class TestViewPageAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView textView = new TextView(ActCustomTabView.this);
            textView.setGravity(Gravity.CENTER);
            textView.setText("pager " + (position + 1));
            textView.setTextSize(30);
            textView.setTextColor(Color.BLUE);
            container.addView(textView);
            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /*获得标题*/
        /*该方法必须写,不然tablayout不能显示标题*/
        @Override
        public CharSequence getPageTitle(int position) {
            return "TAB" + (position + 1);
        }
    }

}
