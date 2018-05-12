package com.example.leidong.ldkeyguard.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.leidong.ldkeyguard.MyApplication;
import com.example.leidong.ldkeyguard.R;
import com.example.leidong.ldkeyguard.beans.Item;
import com.example.leidong.ldkeyguard.constants.Constants;
import com.example.leidong.ldkeyguard.greendao.ItemDao;
import com.example.leidong.ldkeyguard.secures.BaseSecure;
import com.example.leidong.ldkeyguard.secures.GenKey;
import com.squareup.picasso.Picasso;

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

    @BindView(R.id.edit_ibt)
    ImageButton mEditIbt;

    @BindView(R.id.see_ibt)
    ImageButton mSeeIbt;

    @BindView(R.id.ibt_layout)
    RelativeLayout mIbtLayout;

    @BindView(R.id.password2_tv)
    TextView mPassword2Tv;

    @BindView(R.id.update_btn)
    Button mUpdateBtn;

    @BindView(R.id.top_image)
    ImageView mTopImage;
    @BindView(R.id.gen_key_ibt)
    ImageButton mGenKeyIbt;

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
            mIbtLayout.setVisibility(View.GONE);
            mUpdateBtn.setVisibility(View.GONE);
            List<String> dataset = new LinkedList<>(Arrays.asList("个人", "社交", "网络", "工作", "其他"));
            mNiceSpinner.attachDataSource(dataset);
            mDelBtn.setVisibility(View.GONE);
            Picasso.get().load(R.drawable.top_image_add).into(mTopImage);
        }
        //查看模式
        else {
            mAddBtn.setVisibility(View.GONE);
            mNiceSpinner.setVisibility(View.GONE);
            mUpdateBtn.setVisibility(View.GONE);
            mGenKeyIbt.setVisibility(View.GONE);

            mItemId = bundle.getLong(Constants.ITEM_ID);
            initTopImage(mItemId);

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
                mPassword2Tv.setVisibility(View.GONE);
                mPassword2Et.setVisibility(View.GONE);
                mDescEt.setEnabled(false);
            }
        }
    }

    /**
     * 根据Item的Id更改上方的图片
     *
     * @param mItemId
     */
    private void initTopImage(Long mItemId) {
        Long cursor = mItemId % 10;
        if (0L == cursor) {
            Picasso.get().load(R.drawable.top_image1).into(mTopImage);
        } else if (1L == cursor) {
            Picasso.get().load(R.drawable.top_image2).into(mTopImage);
        } else if (2L == cursor) {
            Picasso.get().load(R.drawable.top_image3).into(mTopImage);
        } else if (3L == cursor) {
            Picasso.get().load(R.drawable.top_image4).into(mTopImage);
        } else if (4L == cursor) {
            Picasso.get().load(R.drawable.top_image5).into(mTopImage);
        } else if (5L == cursor) {
            Picasso.get().load(R.drawable.top_image6).into(mTopImage);
        } else if (6L == cursor) {
            Picasso.get().load(R.drawable.top_image7).into(mTopImage);
        } else if (7L == cursor) {
            Picasso.get().load(R.drawable.top_image8).into(mTopImage);
        } else if (8L == cursor) {
            Picasso.get().load(R.drawable.top_image9).into(mTopImage);
        } else if (9L == cursor) {
            Picasso.get().load(R.drawable.top_image10).into(mTopImage);
        }
    }

    /**
     * 初始化动作
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    void initActions() {
        mNiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mSpinnerStr = "个人";
                        break;
                    case 1:
                        mSpinnerStr = "社交";
                        break;
                    case 2:
                        mSpinnerStr = "网络";
                        break;
                    case 3:
                        mSpinnerStr = "工作";
                        break;
                    case 4:
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

        mSeeIbt.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mPassword1Et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        mPassword2Et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        mPassword1Et.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        mPassword1Et.setTextColor(R.color.red);
                        mPassword2Et.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        mPassword2Et.setTextColor(R.color.red);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @OnClick({R.id.add_btn, R.id.del_btn, R.id.update_btn, R.id.edit_ibt, R.id.gen_key_ibt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_btn:
                clickAddBtn();
                break;
            case R.id.del_btn:
                clickDeleteBtn();
                break;
            case R.id.update_btn:
                clickUpdateBtn();
                break;
            case R.id.edit_ibt:
                clickEditIbtn();
                break;
            case R.id.gen_key_ibt:
                clickGenKeyIbtn();
                break;
            default:
                break;
        }

    }

    /**
     * 点击生成密码按钮
     */
    private void clickGenKeyIbtn() {
        final String key = GenKey.genKey();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("生成的密码为：");
        builder.setMessage(key);
        builder.setPositiveButton("复制", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClipboardManager clipManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copy_key", key);
                clipManager.setPrimaryClip(clip);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }


    /**
     * 点击编辑按钮
     */
    private void clickEditIbtn() {
        mIbtLayout.setVisibility(View.GONE);
        mDelBtn.setVisibility(View.GONE);

        mNameEt.setEnabled(true);
        mUsernameEt.setEnabled(true);
        mPassword1Et.setEnabled(true);
        mPassword2Tv.setVisibility(View.VISIBLE);
        mPassword2Et.setVisibility(View.VISIBLE);
        mDescEt.setEnabled(true);

        mUpdateBtn.setVisibility(View.VISIBLE);
        mGenKeyIbt.setVisibility(View.VISIBLE);

        mPassword2Et.setText(mPassword1Et.getText().toString().trim());
    }

    /**
     * 点击保存按钮
     */
    private void clickUpdateBtn() {
        String name = mNameEt.getText().toString().trim();
        String username = mUsernameEt.getText().toString().trim();
        String password1 = mPassword1Et.getText().toString().trim();
        String password2 = mPassword2Et.getText().toString().trim();
        String desc = mDescEt.getText().toString().trim();

        if (!TextUtils.isEmpty(name)
                && !TextUtils.isEmpty(username)
                && password1.equals(password2)
                && password1.length() > 0) {
            ItemDao itemDao = MyApplication.getInstance().getDaoSession().getItemDao();
            Item item = itemDao.queryBuilder().where(ItemDao.Properties.Id.eq(mItemId)).unique();
            item.setName(name);
            item.setUsername(username);
            item.setPassword(password1);
            item.setDesc(desc.length() == 0 ? null : desc);
            itemDao.update(item);

            finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("注意");
            builder.setMessage("更新失败！");
            builder.setPositiveButton("确定", null);
            builder.create().show();
        }
    }

    /**
     * 点击删除按钮
     */
    private void clickDeleteBtn() {
        ItemDao itemDao = MyApplication.getInstance().getDaoSession().getItemDao();
        Item item = itemDao.queryBuilder().where(ItemDao.Properties.Id.eq(mItemId)).unique();
        if (item != null) {
            itemDao.delete(item);
            finish();
        }
    }

    /**
     * 点击添加按钮
     */
    private void clickAddBtn() {
        String name = mNameEt.getText().toString().trim();
        String username = mUsernameEt.getText().toString().trim();
        String password1 = mPassword1Et.getText().toString().trim();
        String password2 = mPassword2Et.getText().toString().trim();
        String desc = mDescEt.getText().toString().trim();

        if (!TextUtils.isEmpty(name)
                && !TextUtils.isEmpty(username)
                && password1.equals(password2)
                && password1.length() > 0
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
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("注意");
            builder.setMessage("添加失败！");
            builder.setPositiveButton("确定", null);
            builder.create().show();
        }
    }
}
