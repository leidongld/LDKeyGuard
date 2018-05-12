package com.example.leidong.ldkeyguard.secures;

import android.text.TextUtils;

import com.example.leidong.ldkeyguard.MyApplication;
import com.example.leidong.ldkeyguard.beans.Item;
import com.example.leidong.ldkeyguard.beans.User;
import com.example.leidong.ldkeyguard.constants.Constants;
import com.example.leidong.ldkeyguard.greendao.ItemDao;
import com.example.leidong.ldkeyguard.greendao.UserDao;

import java.util.List;

/**
 * Created by Lei Dong on 2018/5/9.
 */
public class BaseSecure {
    /**
     * 判断用户输入的用户名、密码格式是否合法
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public static boolean isUserInputLegal(String username, String password) {
        if(!TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(password)){
            return true;
        }
        return false;
    }

    /**
     * 认证输入的用户信息
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public static boolean authenticateUser(String username, String password) {
        UserDao userDao = MyApplication.getInstance().getDaoSession().getUserDao();
        List<User> userList = userDao.loadAll();

        for (User user : userList) {
            if(user.getUsername().equals(username)
                    && BCrypt.checkpw(password, user.getPassword())){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某一个Category下的条目是否重复
     *
     * @param mSpinnerStr 目录名称
     * @param name 条目名称
     * @return
     */
    public static boolean isItemRepeated(String mSpinnerStr, String name) {
        int categoryId = getCategoryId(mSpinnerStr);
        ItemDao itemDao = MyApplication.getInstance().getDaoSession().getItemDao();
        List<Item> itemList = itemDao.queryBuilder().where(ItemDao.Properties.CategoryId.eq(categoryId)).list();
        if(itemList.size() == 0) {
            return false;
        }
        for (Item item : itemList) {
            if(item.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    /**
     * 得到Category Id
     *
     * @param mSpinnerStr Category名称
     * @return
     */
    private static int getCategoryId(String mSpinnerStr) {
        int categoryId = 0;
        if(mSpinnerStr.equals("个人")){
            categoryId = Constants.SELF_ID;
        }
        else if(mSpinnerStr.equals("社交")){
            categoryId = Constants.COMM_ID;
        }
        else if(mSpinnerStr.equals("网络")){
            categoryId = Constants.NET_ID;
        }
        else if(mSpinnerStr.equals("工作")){
            categoryId = Constants.WORK_ID;
        }
        else if(mSpinnerStr.equals("其他")){
            categoryId = Constants.OTHER_ID;
        }
        else{

        }
        return categoryId;
    }

    /**
     * 验证老密码是否正确
     *
     * @param oldPassword
     * @return
     */
    public static boolean isOldPasswordCorrect(String oldPassword) {
        UserDao userDao = MyApplication.getInstance().getDaoSession().getUserDao();
        User user = userDao.queryBuilder().where(UserDao.Properties.Id.eq(1L)).unique();
        String realPassword = user.getPassword();
        return BCrypt.checkpw(oldPassword, realPassword);
    }
}
