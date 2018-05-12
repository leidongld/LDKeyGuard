package com.example.leidong.ldkeyguard.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.leidong.ldkeyguard.R;
import com.example.leidong.ldkeyguard.constants.Constants;
import com.example.leidong.ldkeyguard.storage.MySP;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lei Dong on 2018/5/12.
 */
public class SettingsActivity extends BaseActivity {
    private static final String TAG = "SettingsActivity";

    @BindView(R.id.password_length)
    RelativeLayout mPasswordLength;

    @BindView(R.id.switch_alphabat)
    SwitchCompat mSwitchAlphabat;

    @BindView(R.id.switch_number)
    SwitchCompat mSwitchNumber;

    @BindView(R.id.switch_symbol)
    SwitchCompat mSwitchSymbol;

    @BindView(R.id.change_password_tv)
    TextView mChangePasswordTv;

    private MySP mMySP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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
        mMySP = new MySP(this).getmMySP();

        mSwitchAlphabat.setChecked(mMySP.load(Constants.IS_ALPHABAT_ON, false));
        mSwitchNumber.setChecked(mMySP.load(Constants.IS_NUMBER_ON, false));
        mSwitchSymbol.setChecked(mMySP.load(Constants.IS_SYMBOL_ON, false));

    }

    /**
     * 初始化动作
     */
    @Override
    void initActions() {
        mSwitchAlphabat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mMySP.save(Constants.IS_ALPHABAT_ON, true);
                } else {
                    mMySP.save(Constants.IS_ALPHABAT_ON, false);
                }
            }
        });

        mSwitchNumber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mMySP.save(Constants.IS_NUMBER_ON, true);
                } else {
                    mMySP.save(Constants.IS_NUMBER_ON, false);
                }
            }
        });

        mSwitchSymbol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mMySP.save(Constants.IS_SYMBOL_ON, true);
                } else {
                    mMySP.save(Constants.IS_SYMBOL_ON, false);
                }
            }
        });


    }

    /**
     * 点击跳转到更改主密码界面
     */
    @OnClick(R.id.change_password_tv)
    public void onViewClicked() {
        Intent intent = new Intent(SettingsActivity.this, ChangePasswordActivity.class);
        startActivity(intent);
    }
}
