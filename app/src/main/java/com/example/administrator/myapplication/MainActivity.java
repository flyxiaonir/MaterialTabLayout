package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
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
            canvas.translate(getWidth()/2, getHeight()/2);
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
