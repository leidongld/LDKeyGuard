package com.example.leidong.ldkeyguard.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.leidong.ldkeyguard.R;
import com.example.leidong.ldkeyguard.secures.BaseSecure;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lei Dong on 2018/5/9.
 */
public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    @BindView(R.id.username)
    EditText mUsernameEt;

    @BindView(R.id.password)
    EditText mPasswordEt;

    @BindView(R.id.login_button)
    Button mLoginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsernameEt.getText().toString().trim();
                String password = mPasswordEt.getText().toString().trim();
                if(BaseSecure.isUserInputLegal(username, password)
                        && BaseSecure.authenticateUser(username, password)){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    showAlertAlertDialog();
                }
            }
        });
    }

    /**
     * 显示Dialog提醒
     */
    private void showAlertAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("警告");
        builder.setMessage("用户名或密码输入错误，请重新输入！");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mUsernameEt.setText(null);
                mPasswordEt.setText(null);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }
}
