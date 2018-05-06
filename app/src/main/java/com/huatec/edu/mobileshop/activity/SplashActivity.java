package com.huatec.edu.mobileshop.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.huatec.edu.mobileshop.R;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 * 欢迎页面
 * 8秒后自动跳转主页
 * 也可手动点击跳到主页
 */
public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.ad_image)
    ImageView mAdImage;
    @BindView(R.id.skip_button)
    Button mSkipButton;
    private MyHandler mHandler;

    /**
     * handler防止内存泄漏
     */
    private static class MyHandler extends Handler {

        WeakReference<Activity> mActivityReference;

        MyHandler(Activity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final Activity activity = mActivityReference.get();
            if (activity != null) {
                if (msg.what == -1) {
                    SplashActivity splashActivity = (SplashActivity) activity;
                    splashActivity.skip();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        mHandler = new MyHandler(SplashActivity.this);
        timer();
    }


    /**
     * 跳过按钮
     * 手动点击后跳转到主页面
     */
    @OnClick(R.id.skip_button)
    public void onViewClicked() {
        skip();
    }


    /**
     * 8秒后自动跳到主页
     */
    private void timer() {
        Message message = mHandler.obtainMessage(-1);
        mHandler.sendMessageDelayed(message, 4000);
    }

    /**
     * 页面之间的跳转
     */
    private void skip() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    /**
     * 将状态栏设置为透明，并设置全屏显示
     */
    private void hideStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//表示活动布局会显示在状态栏上面
            getWindow().setStatusBarColor(Color.TRANSPARENT);//将状态栏设置为透明颜色
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);//清空消息队列
    }
}
