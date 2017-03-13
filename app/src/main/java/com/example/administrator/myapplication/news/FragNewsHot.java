package com.example.administrator.myapplication.news;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/13.
 */

public class FragNewsHot extends Fragment {
    private String mUri_article = "http://newweather.nineton.cn/article/articlelist?cateid=1&page=3";//文章接口
    private View mRootView;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_news_hot_layout,null);
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.news_recyclerView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getArticleData(mUri_article,HANDLER_ARTICLE);
//        initRecyclerView();
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter();
    }

//    private static final int HANDLER_TYPE = 10;//类型数据
    private static final int HANDLER_ARTICLE = 11;//文章数据

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

  /*  private final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.arg1) {
                case HANDLER_ARTICLE:
                    Log.d("lf", "获取到文章数据");

                    break;
            }
            return false;
        }
    });*/
    private void getArticleData(String url, final int handlerType) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(url);
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null);
        Request request = requestBuilder.build();
        Call mcall = mOkHttpClient.newCall(request);

        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "获取失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
//                    String res = response.body().string();
                    NewsDataParser dataParser = new NewsDataParser(response.body().string());
//                    Log.d("lf","获取到文章列表数据："+res);
//                    RespNewsCate result = new Gson().fromJson(res, RespNewsCate.class);
//                    Message msg = mHandler.obtainMessage();
//                    msg.obj = result;
//                    msg.arg1 = handlerType;
//                    mHandler.sendMessage(msg);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        /* if (mcall.isExecuted()){
            mcall.cancel();
        }*/
    }
}
