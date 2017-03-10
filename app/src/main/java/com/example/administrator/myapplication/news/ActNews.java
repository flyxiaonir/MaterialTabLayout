package com.example.administrator.myapplication.news;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

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
    private String mUri_type = "http://newweather.nineton.cn/article/catelist";//分类接口
    private String mUri_article = "http://newweather.nineton.cn/article/articlelist?cateid=1&page=3";//文章接口
    private CustomTabView customTabView;
    private ActNewsTabHolder mTabHolder;

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
        getAsynHttp(mUri_type, HANDLER_TYPE);
    }

    private static final int HANDLER_TYPE = 10;//类型数据
    private static final int HANDLER_ARTICLE = 11;//文章数据

    private final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.arg1) {
                case HANDLER_TYPE:
                    Log.d("lf", "获取到类型数据");
                    initTabView((RespNewsCate)msg.obj);
                    break;
                case HANDLER_ARTICLE:
                    break;
            }
            return false;
        }
    });
    private void initTabView(RespNewsCate data){
        mTabHolder = new ActNewsTabHolder(data,getSupportFragmentManager());
        //初始化tabView控件
        customTabView.setTopWindowCollpaseMinimumHeight(DensityUtil.dip2px(ActNews.this,100));
        customTabView.setTopWindowHeight(DensityUtil.dip2px(ActNews.this,176));
        customTabView.setData(mTabHolder);
        customTabView.setOnTabStatusChangeListener(new CustomTabView.OnTabStatusChangeListener<ActNewsTabHolder>() {
            @Override
            public void onPageScrolled(ActNewsTabHolder holder, int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(ActNewsTabHolder holder, int position) {
//                if (mTabHolder.get)
//                mTabHolder.getBg()
            }

            @Override
            public void onPageScrollStateChanged(ActNewsTabHolder holder, int state) {

            }

            @Override
            public void onExpanded(ActNewsTabHolder holder, int totalRange, int verticalOffset) {
//                holder.iconImage.setAlpha(1.0f);
            }

            @Override
            public void onCollapsed(ActNewsTabHolder holder, int totalRange, int verticalOffset) {
//                holder.iconImage.setAlpha(0f);
            }

            @Override
            public void onInternediate(ActNewsTabHolder holder, int totalRange, int verticalOffset) {
//                float alpha = 1-Math.abs((float)verticalOffset)/Math.abs(totalRange);
//                Log.d("lf","alpha="+alpha);
//                holder.iconImage.setAlpha(alpha);
            }
        });
    }
    private void getAsynHttp(String url, final int handlerType) {
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
}