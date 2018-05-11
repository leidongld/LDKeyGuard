package com.example.leidong.ldkeyguard.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leidong.ldkeyguard.MyApplication;
import com.example.leidong.ldkeyguard.R;
import com.example.leidong.ldkeyguard.beans.Item;
import com.example.leidong.ldkeyguard.constants.Constants;
import com.example.leidong.ldkeyguard.greendao.ItemDao;
import com.example.leidong.ldkeyguard.secures.BaseSecure;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lei Dong on 2018/5/9.
 */
public class DetailActivity extends BaseActivity {
    private static final String TAG = "DetailActivity";

    @BindView(R.id.nice_spinner)
    NiceSpinner mNiceSpinner;

    @BindView(R.id.name_et)
    EditText mNameEt;

    @BindView(R.id.username_et)
    EditText mUsernameEt;

    @BindView(R.id.password1_et)
    EditText mPassword1Et;

    @BindView(R.id.password2_et)
    EditText mPassword2Et;

    @BindView(R.id.desc_et)
    EditText mDescEt;

    @BindView(R.id.add_btn)
    Button mAddBtn;

    @BindView(R.id.del_btn)
    Button mDelBtn;

    private int mDetailActivityMode;

    private String mSpinnerStr = "个人";
    private Long mItemId = 0L;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        //初始化组件
        initWidgets();

        //初始化动作
        initActions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initWidgets();
    }

    /**
     * 初始化组件
     */
    @Override
    void initWidgets() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.DETAIL_ACTIVITY_MODE_DATA);
        mDetailActivityMode = bundle.getInt(Constants.DETAIL_ACTIVITY_MODE);

        //添加模式
        if (mDetailActivityMode == Constants.DETAIL_ACTIVITY_ADD) {
            List<String> dataset = new LinkedList<>(Arrays.asList("个人", "社交", "网络", "工作", "其他"));
            mNiceSpinner.attachDataSource(dataset);
            mDelBtn.setVisibility(View.GONE);
        }
        //查看模式
        else {
            mAddBtn.setVisibility(View.GONE);
            mNiceSpinner.setVisibility(View.GONE);

            mItemId = bundle.getLong(Constants.ITEM_ID);

            ItemDao itemDao = MyApplication.getInstance().getDaoSession().getItemDao();
            Item item = itemDao.queryBuilder().where(ItemDao.Properties.Id.eq(mItemId)).unique();
            if (item != null) {
                String name = item.getName();
                String username = item.getUsername();
                String password = item.getPassword();
                String desc = item.getDesc();

                mNameEt.setText(name);
                mUsernameEt.setText(username);
                mPassword1Et.setText(password);
                mDescEt.setText(desc);

                mNameEt.setEnabled(false);
                mUsernameEt.setEnabled(false);
                mPassword1Et.setEnabled(false);
                mPassword2Et.setVisibility(View.GONE);
                mDescEt.setEnabled(false);
            }
        }
    }

    /**
     * 初始化动作
     */
    @Override
    void initActions() {
        mNiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Toast.makeText(DetailActivity.this, "个人", Toast.LENGTH_LONG).show();
                        mSpinnerStr = "个人";
                        break;
                    case 1:
                        Toast.makeText(DetailActivity.this, "社交", Toast.LENGTH_LONG).show();
                        mSpinnerStr = "社交";
                        break;
                    case 2:
                        Toast.makeText(DetailActivity.this, "网络", Toast.LENGTH_LONG).show();
                        mSpinnerStr = "网络";
                        break;
                    case 3:
                        Toast.makeText(DetailActivity.this, "工作", Toast.LENGTH_LONG).show();
                        mSpinnerStr = "工作";
                        break;
                    case 4:
                        Toast.makeText(DetailActivity.this, "其他", Toast.LENGTH_LONG).show();
                        mSpinnerStr = "其他";
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSpinnerStr = "个人";
            }
        });
    }

    @OnClick({R.id.add_btn, R.id.del_btn})
    public void onViewClicked(View view) {
        switch(view.getId()){
            case R.id.add_btn:
                clickAddBtn();
                break;
            case R.id.del_btn:
                clickDeleteBtn();
                break;
            default:
                break;
        }

    }

    /**
     * 点击删除按钮
     */
    private void clickDeleteBtn() {
        Toast.makeText(DetailActivity.this, "点击删除按钮", Toast.LENGTH_LONG).show();
        ItemDao itemDao = MyApplication.getInstance().getDaoSession().getItemDao();
        Item item = itemDao.queryBuilder().where(ItemDao.Properties.Id.eq(mItemId)).unique();
        if(item != null){
            itemDao.delete(item);
            finish();
        }
    }

    /**
     * 点击添加按钮
     */
    private void clickAddBtn() {
        Toast.makeText(DetailActivity.this, "点击添加按钮", Toast.LENGTH_LONG).show();
        String name = mNameEt.getText().toString().trim();
        String username = mUsernameEt.getText().toString().trim();
        String password1 = mPassword1Et.getText().toString().trim();
        String password2 = mPassword2Et.getText().toString().trim();
        String desc = mDescEt.getText().toString().trim();

        if (!TextUtils.isEmpty(name)
                && !TextUtils.isEmpty(username)
                && password1.equals(password2)
                && password1.length() > 8
                && !BaseSecure.isItemRepeated(mSpinnerStr, name)) {
            ItemDao itemDao = MyApplication.getInstance().getDaoSession().getItemDao();
            Item item = new Item();
            item.setName(name);
            item.setUsername(username);
            item.setPassword(password1);
            item.setDesc(desc.length() == 0 ? null : desc);
            switch (mSpinnerStr) {
                case "个人":
                    item.setCategoryId(Constants.SELF_ID);
                    break;
                case "社交":
                    item.setCategoryId(Constants.COMM_ID);
                    break;
                case "网络":
                    item.setCategoryId(Constants.NET_ID);
                    break;
                case "工作":
                    item.setCategoryId(Constants.WORK_ID);
                    break;
                case "其他":
                    item.setCategoryId(Constants.OTHER_ID);
                    break;
                default:
                    break;
            }
            itemDao.insert(item);

            finish();
        }
    }
}
