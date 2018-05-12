package com.example.leidong.ldkeyguard.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.leidong.ldkeyguard.R;

import butterknife.ButterKnife;

/**
 * Created by Lei Dong on 2018/5/12.
 */
public class DevActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev);
        ButterKnife.bind(this);

        //初始化组件
        initWidgets();

        //初始化动作
        initActions();
    }

    /**
     * 初始化组件
     */
    @Override
    void initWidgets() {

    }

    /**
     * 初始化动作
     */
    @Override
    void initActions() {

    }
}
