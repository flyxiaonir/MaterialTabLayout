package com.example.administrator.myapplication.news;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.google.gson.Gson;
import com.nineton.materialtabview.CustomTabView;
import com.nineton.materialtabview.DensityUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/10.
 */

public class ActNews extends AppCompatActivity {
    private static final String TAG = "ActNews";
    private String mUri_type = "http://newweather.nineton.cn/article/catelist";//分类接口

    private CustomTabView customTabView;
    private ActNewsTabHolder mTabHolder;
//    private ImageView testImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_news_layout);
        setView();
        setData();
    }

    private void setView() {

        customTabView = (CustomTabView) findViewById(R.id.customTabView);

    }

    private void setData() {
        getTypeData(mUri_type, HANDLER_TYPE);
    }

    private static final int HANDLER_TYPE = 10;//类型数据
//    private static final int HANDLER_ARTICLE = 11;//文章数据

    private final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.arg1) {
                case HANDLER_TYPE:
                    Log.d("lf", "获取到类型数据");
                    initTabView((RespNewsCate) msg.obj);
                    break;
            }
            return false;
        }
    });

    private void initTabView(RespNewsCate data) {
//        testImage = (ImageView) findViewById(R.id.test_image);
        mTabHolder = new ActNewsTabHolder(data, getSupportFragmentManager());
        //初始化tabView控件
        customTabView.setTopWindowCollpaseMinimumHeight(DensityUtil.dip2px(ActNews.this, 100));
        customTabView.setTopWindowHeight(DensityUtil.dip2px(ActNews.this, 176));
        customTabView.setData(mTabHolder);
        customTabView.getViewPager().setCurrentItem(0);
//        mTabHolder.getBg().setImageResource(R.drawable.bg_baoxiao);
        if (mTabHolder.getmTypeData() != null && mTabHolder.getmTypeData().getData() != null && !mTabHolder.getmTypeData().getData().isEmpty()) {
            Loger.debug(mTabHolder.getmTypeData().getData().get(0).getName());
            Glide.with(ActNews.this).load("file:///android_asset/news/ic_" + mTabHolder.getmTypeData().getData().get(0).getName() + ".png").dontAnimate().into(mTabHolder.getIconImage());
            Glide.with(ActNews.this).load("file:///android_asset/news/bg_" + mTabHolder.getmTypeData().getData().get(0).getName() + ".jpg").dontAnimate().into(mTabHolder.getBg());
        }

        customTabView.setOnTabStatusChangeListener(new CustomTabView.OnTabStatusChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                if (mTabHolder.get)
//                mTabHolder.getIconImage().setImageResource(R.drawable.ic_tiyu);
                Glide.with(ActNews.this).load("file:///android_asset/news/ic_" + mTabHolder.getmTypeData().getData().get(position).getName() + ".png").dontAnimate().into(mTabHolder.getIconImage());
                Glide.with(ActNews.this).load("file:///android_asset/news/bg_" + mTabHolder.getmTypeData().getData().get(position).getName() + ".jpg").dontAnimate().into(mTabHolder.getBg());
                Loger.debug("position=" + position + "||name=" + mTabHolder.getmTypeData().getData().get(position).getName());
                iconAnim();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            @Override
            public void onExpanded(int totalRange, int verticalOffset) {
                mTabHolder.getIconImage().setAlpha(1.0f);
            }

            @Override
            public void onCollapsed(int totalRange, int verticalOffset) {
            }

            @Override
            public void onInternediate(int totalRange, int verticalOffset) {
                float alpha = 1 - Math.abs((float) verticalOffset) / Math.abs(totalRange);
//                Log.d("lf","alpha="+alpha);
                mTabHolder.getIconImage().setAlpha(alpha);
            }
        });
    }
    private void iconAnim(){

        Animation  anim1 = AnimationUtils.loadAnimation(this, com.nineton.materialtabview.R.anim.anim_icon_out);
//        v.setAnimation(anim);
        anim1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation  anim2 = AnimationUtils.loadAnimation(ActNews.this, com.nineton.materialtabview.R.anim.anim_icon_in);
                /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Animator animator = ViewAnimationUtils.createCircularReveal(
                            mTabHolder.getmTopMask(),
                            mTabHolder.getmTopMask().getWidth()/2,
                            mTabHolder.getmTopMask().getHeight()/2,
                            0,
                            mTabHolder.getmTopMask().getWidth()/2);
                    mTabHolder.getmTopMask().setBackgroundColor(Color.BLUE);

                    animator.setInterpolator(new AccelerateInterpolator());
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            mTabHolder.getmTopMask().setAlpha(1.0f);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            Log.d(TAG,"animator返回动画");
//                        mask_view.setBackgroundColor(Color.GREEN);
                            ObjectAnimator anpim4 =ObjectAnimator.ofFloat(mTabHolder.getmTopMask(),"alpha",0.9f,0.0f);
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
                    animator.setDuration(300);
                    animator.start();

                    mTabHolder.getIconImage().startAnimation(anim2);
                }*/

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mTabHolder.getIconImage().startAnimation(anim1);
    }
    private void getTypeData(String url, final int handlerType) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(url);
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null);
        Request request = requestBuilder.build();
        Call mcall = mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "获取失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String res = response.body().string();

                    RespNewsCate result = new Gson().fromJson(res, RespNewsCate.class);
                    Message msg = mHandler.obtainMessage();
                    msg.obj = result;
                    msg.arg1 = handlerType;
                    mHandler.sendMessage(msg);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "获取失败！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private static class Loger {
        static final boolean DEBUG = true;

        static void debug(String meg) {
            if (DEBUG)
                Log.d(TAG, meg);
        }
    }
}