package com.example.leidong.ldkeyguard.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.example.leidong.ldkeyguard.R;
import com.example.leidong.ldkeyguard.constants.Constants;
import com.example.leidong.ldkeyguard.storage.MySP;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

/**
 * Created by Lei Dong on 2018/5/9.
 */
public class SplashActivity extends Activity {
    private static final String TAG = "SplashActivity";

    private MySP mMySP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        //初始化组件
        initWidgets();

        //初始化动作
        initActions();

    }

    /**
     * 初始化组件
     */
    void initWidgets() {
        mMySP = new MySP(this).getmMySP();

    }

    /**
     * 初始化动作
     */
    void initActions() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                boolean flag = mMySP.load(Constants.IS_HAS_USER, false);
                if(flag) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        };
        timer.schedule(timerTask, 3000);
    }
}
