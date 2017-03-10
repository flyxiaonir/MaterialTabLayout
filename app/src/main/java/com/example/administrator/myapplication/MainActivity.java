package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nineton.materialtabview.CustomTabView;
import com.nineton.materialtabview.CustomTabViewPagerAdapter;
import com.nineton.materialtabview.FragTabViewTest;
import com.nineton.materialtabview.TabDataHolder;

public class MainActivity extends AppCompatActivity {
    private MyHolder myHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        final CustomTabView tabView = (CustomTabView) findViewById(R.id.main_customTabView);
        myHolder = new MyHolder();
        tabView.setTopWindowCollpaseMinimumHeight(200);
        tabView.setTopWindowHeight(400);
        tabView.setData(myHolder);

        tabView.setOnTabStatusChangeListener(new CustomTabView.OnTabStatusChangeListener<MyHolder>() {
            @Override
            public void onPageScrolled(MyHolder holder, int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(MyHolder holder, int position) {

            }

            @Override
            public void onPageScrollStateChanged(MyHolder holder, int state) {

            }

            @Override
            public void onExpanded(MyHolder holder, int totalRange, int verticalOffset) {
                holder.iconImage.setAlpha(1.0f);
            }

            @Override
            public void onCollapsed(MyHolder holder, int totalRange, int verticalOffset) {
                holder.iconImage.setAlpha(0f);
            }

            @Override
            public void onInternediate(MyHolder holder, int totalRange, int verticalOffset) {
                float alpha = 1 - Math.abs((float) verticalOffset) / Math.abs(totalRange);
                Log.d("lf", "alpha=" + alpha);
                holder.iconImage.setAlpha(alpha);
            }
        });
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private class MyHolder extends TabDataHolder {
        private ImageView image, iconImage;
        private View mTopMask;
        private View mTopRootView;

        @Override
        public View onCreateTopView(LayoutInflater inflater, ViewGroup container) {
            mTopRootView = inflater.inflate(R.layout.body_top_layout, container);
            image = (ImageView) mTopRootView.findViewById(R.id.image);
            iconImage = (ImageView) mTopRootView.findViewById(R.id.iconImage);
            mTopMask = mTopRootView.findViewById(R.id.mask_view);
            return mTopRootView;
        }

        @Override
        public void onBindingPagerData(TabLayout tabLayout, ViewPager viewPager) {
            CustomTabViewPagerAdapter viewPagerAdapter = new CustomTabViewPagerAdapter(getSupportFragmentManager());
            viewPagerAdapter.addFragment(new FragTabViewTest(), "TabOne");//添加Fragment
            viewPagerAdapter.addFragment(new FragTabViewTest(), "TabTwo");
            viewPagerAdapter.addFragment(new FragTabViewTest(), "TabThree");
            viewPager.setAdapter(viewPagerAdapter);//设置适配器

            tabLayout.addTab(tabLayout.newTab().setText("TabOne"));//给TabLayout添加Tab
            tabLayout.addTab(tabLayout.newTab().setText("TabTwo"));
            tabLayout.addTab(tabLayout.newTab().setText("TabThree"));
            tabLayout.setupWithViewPager(viewPager);//给TabLayout设置关
        }
    }

    class MyView extends View {
        final String DRAW_STR = "自强不息";
        // 画笔
        private Paint paint;
        // 声明路径对象数组
        Path[] paths = new Path[3];

        public MyView(Context context) {
            super(context);
            paths[0] = new Path();
            paths[0].moveTo(0, 0);
            for (int i = 0; i <= 7; i++) {
                // 生成7个点，随机生成Y坐标，并连成一条
                paths[0].lineTo(i * 30, (float) Math.random() * 30);
            }
            paths[1] = new Path();
            RectF rectF = new RectF(0, 0, 200, 200);
            paths[1].addOval(rectF, Path.Direction.CCW);
            paths[2] = new Path();
            paths[2].addArc(rectF, 60, 180);
            // 初始化画笔
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.CYAN);
            paint.setStrokeWidth(1);
        }

        @Override
        protected void onDraw(Canvas canvas) {
//            // 将背景填充为白色
            canvas.drawColor(Color.WHITE);
//            canvas.translate(40, 40);
//            // 设置从右边开始绘制（右对齐）
            paint.setTextAlign(Paint.Align.RIGHT);
//            paint.setTextSize(20);
//
//            // 绘制路径
//            paint.setStyle(Paint.Style.STROKE);
//            canvas.drawPath(paths[0], paint);
//            // 沿着路径绘制一段文本
//            paint.setStyle(Paint.Style.FILL);
//            canvas.drawTextOnPath(DRAW_STR, paths[0], -8, 20, paint);
            canvas.save();
            // 画布下移120
            canvas.translate(getWidth() / 2, getHeight() / 2);
            paint.setTextAlign(Paint.Align.CENTER);
//            canvas.rotate(45 * Math.PI/180);
            // 绘制路径
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(paths[1], paint);
            // 沿着路径绘制一段文本
            paint.setStyle(Paint.Style.FILL);
            canvas.drawTextOnPath(DRAW_STR, paths[1], 0, 4, paint);

//            canvas.rotate(90);
//canvas.drawColor(Color.YELLOW);
            // 画布下移120
            canvas.restore();
            canvas.translate(0, 180);

            // 绘制路径
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(paths[2], paint);
            // 沿着路径绘制一段文本
            paint.setStyle(Paint.Style.FILL);
            canvas.drawTextOnPath(DRAW_STR, paths[2], 0, 0, paint);
        }

    }

}
