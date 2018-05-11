package com.example.leidong.ldkeyguard.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

import com.example.leidong.ldkeyguard.MyApplication;
import com.example.leidong.ldkeyguard.R;
import com.example.leidong.ldkeyguard.beans.User;
import com.example.leidong.ldkeyguard.constants.Constants;
import com.example.leidong.ldkeyguard.greendao.UserDao;
import com.example.leidong.ldkeyguard.storage.MySP;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lei Dong on 2018/5/11.
 */
public class RegisterActivity extends BaseActivity {
    private static final String TAG = "RegisterActivity";

    @BindView(R.id.username_et)
    EditText mUsernameEt;

    @BindView(R.id.password1_et)
    EditText mPassword1Et;

    @BindView(R.id.password2_et)
    EditText mPassword2Et;

    @BindView(R.id.register_button)
    Button mrRgisterButton;

    private MySP mMySP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        initWidgets();

        initActions();
    }

    @Override
    void initWidgets() {
        mMySP = new MySP(this).getmMySP();
    }

    @Override
    void initActions() {

    }

    @OnClick(R.id.register_button)
    public void onViewClicked() {
        String username = mUsernameEt.getText().toString().trim();
        String password1 = mPassword1Et.getText().toString().trim();
        String password2 = mPassword2Et.getText().toString().trim();

        if(username.length() > 0
                && password1.equals(password2)
                && password1.length() > 8){
            UserDao userDao = MyApplication.getInstance().getDaoSession().getUserDao();
            User user = new User();
            user.setUsername(username);
            user.setPassword(password1);
            userDao.insert(user);

            mMySP.save(Constants.IS_HAS_USER, true);

            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
