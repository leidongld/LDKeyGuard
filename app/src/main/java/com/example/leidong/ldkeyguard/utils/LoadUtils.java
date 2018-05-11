package com.example.leidong.ldkeyguard.utils;

import com.example.leidong.ldkeyguard.MyApplication;
import com.example.leidong.ldkeyguard.beans.Item;
import com.example.leidong.ldkeyguard.greendao.ItemDao;

import java.util.List;

/**
 * Created by Lei Dong on 2018/5/10.
 */
public class LoadUtils {

    /**
     * 通过枚举的CategoryId填充对应目录下的全部条目
     *
     * @param categoryId
     */
    public static Item[] loadSelfKeyItemsByCategoryId(int categoryId) {
        ItemDao itemDao = MyApplication.getInstance().getDaoSession().getItemDao();
        List<Item> itemList = itemDao.queryBuilder().where(ItemDao.Properties.CategoryId.eq(categoryId)).list();
        if(itemList.size() == 0){
            return new Item[0];
        }
        Item[] items = new Item[itemList.size()];
        for(int i = 0; i < itemList.size(); i++){
            items[i] = itemList.get(i);
        }
        return items;
    }
}
