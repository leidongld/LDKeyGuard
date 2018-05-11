package com.example.leidong.ldkeyguard.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * Created by Lei Dong on 2018/5/9.
 */
public class MySP {
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    @SuppressLint("StaticFieldLeak")
    private static MySP mMySP;

    public MySP(Context context){
        this.mContext = context;
        this.mSharedPreferences = context.getSharedPreferences("LDSP", Context.MODE_PRIVATE);
    }

    public MySP getmMySP(){
        if(mMySP == null){
            mMySP = new MySP(mContext);
        }
        return mMySP;
    }

    //布尔型数据
    public void save(String key, boolean value){
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean load(String key, boolean defaultValue){
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    //整型数据
    public void save(String key, int value){
        mSharedPreferences.edit().putInt(key, value).apply();
    }

    public int load(String key, int defaultValue){
        return mSharedPreferences.getInt(key, defaultValue);
    }

    //长整型数据
    public void save(String key, long value){
        mSharedPreferences.edit().putLong(key, value).apply();
    }

    public long load(String key, long defaultValue){
        return mSharedPreferences.getLong(key, defaultValue);
    }

    //浮点型数据
    public void save(String key, float value){
        mSharedPreferences.edit().putFloat(key, value).apply();
    }

    public float load(String key, float defaultValue){
        return mSharedPreferences.getFloat(key, defaultValue);
    }

    //字符串数据
    public void save(String key, String value){
        mSharedPreferences.edit().putString(key, value).apply();
    }

    public String load(String key, String defaultValue){
        return mSharedPreferences.getString(key, defaultValue);
    }

    public Map<String, ?> getAll() {
        return mSharedPreferences.getAll();
    }

}
