package com.example.leidong.ldkeyguard.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leidong.ldkeyguard.R;
import com.example.leidong.ldkeyguard.beans.Item;
import com.example.leidong.ldkeyguard.constants.Constants;
import com.example.leidong.ldkeyguard.ui.DetailActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lei Dong on 2018/5/10.
 */
public class KeyListAdapter extends RecyclerView.Adapter<KeyListAdapter.ViewHolder> {
    private Context mContext;
    private Item[] mKeyItems;
    private int mCategoryId;

    public KeyListAdapter(Context context, Item[] keyItems, int categoryId) {
        this.mContext = context;
        this.mKeyItems = keyItems;
        this.mCategoryId = categoryId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.key_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(mKeyItems != null){
            switch (mCategoryId) {
                case Constants.SELF_ID:
                    Picasso.get().load(R.drawable.self_icon).into(holder.keyItemIcon);
                    break;
                case Constants.NET_ID:
                    Picasso.get().load(R.drawable.net_icon).into(holder.keyItemIcon);
                    break;
                case Constants.COMM_ID:
                    Picasso.get().load(R.drawable.comm_icon).into(holder.keyItemIcon);
                    break;
                case Constants.WORK_ID:
                    Picasso.get().load(R.drawable.work_icon).into(holder.keyItemIcon);
                    break;
                case Constants.OTHER_ID:
                    Picasso.get().load(R.drawable.other_icon).into(holder.keyItemIcon);
                    break;
                default:
                    break;
            }
            holder.keyItemName.setText(mKeyItems[position].getName());

            holder.keyItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.DETAIL_ACTIVITY_MODE, Constants.DETAIL_ACTIVITY_SEE);
                    bundle.putLong(Constants.ITEM_ID, mKeyItems[position].getId());
                    intent.putExtra(Constants.DETAIL_ACTIVITY_MODE_DATA, bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        if(mKeyItems != null){
            return mKeyItems.length;
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.key_item_icon)
        ImageView keyItemIcon;

        @BindView(R.id.key_item_name)
        TextView keyItemName;

        @BindView(R.id.key_item_layout)
        LinearLayout keyItemLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
