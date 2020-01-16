package com.poszft.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *  SharePreference工具类
 */
public class SpUtils {
    private static SharedPreferences sp;

    private static SpUtils instance = new SpUtils();
    private SpUtils(){}

    public static SpUtils getInstance(String packageName) {
        if (sp == null) {
            synchronized (SpUtils.class) {
                if (sp == null) {
                    sp = MyApplication.getGlobalContext().getSharedPreferences(packageName, Context.MODE_PRIVATE);//第一个参数是文件名,各数据放各自文件中
                }
            }
        }
        return instance;
    }

    //保存
    public void save(String key, Object value){
        if (value instanceof String){
            sp.edit().putString(key, (String) value).commit();
        }else if (value instanceof Boolean){
            sp.edit().putBoolean(key, (Boolean) value).commit();
        }else if(value instanceof Integer){
            sp.edit().putInt(key, (Integer) value).commit();
        }
    }

    //获取数据的方法
    public String getString(String key, String defValue){
        return sp.getString(key, defValue);
    }

    //获取boolean数据
    public boolean getBoolean(String key, boolean defValue){
        return sp.getBoolean(key, defValue);
    }

    //获取int数据
    public int getInt(String key, int defValue){
        return sp.getInt(key, defValue);
    }

    //清除某个key
    public void remove(String key){
        sp.edit().remove(key).commit();
    }

    //清除文件
    public void clear(){
        sp.edit().clear().commit();
    }
}
