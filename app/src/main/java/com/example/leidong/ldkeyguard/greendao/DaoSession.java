package com.example.leidong.ldkeyguard.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.leidong.ldkeyguard.beans.Item;
import com.example.leidong.ldkeyguard.beans.User;

import com.example.leidong.ldkeyguard.greendao.ItemDao;
import com.example.leidong.ldkeyguard.greendao.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig itemDaoConfig;
    private final DaoConfig userDaoConfig;

    private final ItemDao itemDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        itemDaoConfig = daoConfigMap.get(ItemDao.class).clone();
        itemDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        itemDao = new ItemDao(itemDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(Item.class, itemDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        itemDaoConfig.getIdentityScope().clear();
        userDaoConfig.getIdentityScope().clear();
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
