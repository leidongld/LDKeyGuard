package com.example.leidong.ldkeyguard.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.leidong.ldkeyguard.MyApplication;
import com.example.leidong.ldkeyguard.R;
import com.example.leidong.ldkeyguard.adapters.KeyListAdapter;
import com.example.leidong.ldkeyguard.beans.Item;
import com.example.leidong.ldkeyguard.constants.Constants;
import com.example.leidong.ldkeyguard.greendao.ItemDao;
import com.example.leidong.ldkeyguard.storage.MySP;
import com.example.leidong.ldkeyguard.utils.LoadUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lei Dong on 2018/5/9.
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.category_self)
    LinearLayout mCategorySelf;

    @BindView(R.id.category_comm)
    LinearLayout mCategoryComm;

    @BindView(R.id.category_net)
    LinearLayout mCategoryNet;

    @BindView(R.id.category_work)
    LinearLayout mCategoryWork;

    @BindView(R.id.category_other)
    LinearLayout mCategoryOther;

    @BindView(R.id.category_layout)
    LinearLayout mCategoryLayout;

    @BindView(R.id.items_list)
    RecyclerView mItemsList;

    private MySP mMySP;

    private Item[] mKeyItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        ItemDao itemDao = MyApplication.getInstance().getDaoSession().getItemDao();
        List<Item> itemList = itemDao.queryBuilder().where(ItemDao.Properties.CategoryId.eq(5)).list();
        Toast.makeText(MainActivity.this, "现在有 " + itemList.size() + " 个条目", Toast.LENGTH_LONG).show();
    }

    /**
     * 初始化组件
     */
    @Override
    void initWidgets() {
        mMySP = new MySP(this).getmMySP();
        mMySP.save("is_first_load", false);

        mKeyItems = LoadUtils.loadSelfKeyItemsByCategoryId(Constants.SELF_ID);
        mItemsList.setLayoutManager(new LinearLayoutManager(this));
        mItemsList.setAdapter(new KeyListAdapter(this, mKeyItems, Constants.SELF_ID));
    }

    /**
     * 初始化动作
     */
    @Override
    void initActions() {
    }

    @OnClick({R.id.category_self, R.id.category_comm, R.id.category_net, R.id.category_work, R.id.category_other, R.id.add_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.category_self:
                mKeyItems = LoadUtils.loadSelfKeyItemsByCategoryId(Constants.SELF_ID);
                mItemsList.setAdapter(new KeyListAdapter(this, mKeyItems, Constants.SELF_ID));
                break;
            case R.id.category_comm:
                mKeyItems = LoadUtils.loadSelfKeyItemsByCategoryId(Constants.COMM_ID);
                mItemsList.setAdapter(new KeyListAdapter(this, mKeyItems, Constants.COMM_ID));
                break;
            case R.id.category_net:
                mKeyItems = LoadUtils.loadSelfKeyItemsByCategoryId(Constants.NET_ID);
                mItemsList.setAdapter(new KeyListAdapter(this, mKeyItems, Constants.NET_ID));
                break;
            case R.id.category_work:
                mKeyItems = LoadUtils.loadSelfKeyItemsByCategoryId(Constants.WORK_ID);
                mItemsList.setAdapter(new KeyListAdapter(this, mKeyItems, Constants.WORK_ID));
                break;
            case R.id.category_other:
                mKeyItems = LoadUtils.loadSelfKeyItemsByCategoryId(Constants.OTHER_ID);
                mItemsList.setAdapter(new KeyListAdapter(this, mKeyItems, Constants.OTHER_ID));
                break;
            case R.id.add_button:
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.DETAIL_ACTIVITY_MODE, Constants.DETAIL_ACTIVITY_ADD);
                intent.putExtra(Constants.DETAIL_ACTIVITY_MODE_DATA, bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
