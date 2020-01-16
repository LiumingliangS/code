package com.poszft.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by 1 on 2018/7/1.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatus();//设置状态栏颜色
        setContentView(setLayoutResID());

        init();
    }

    @Override
    protected void onPause() {
        super.onPause();

        //basefragment用写吗，fragment只有切换，activity才有进退
        //暂停，即失焦，此时焦点存在，关闭系统输入法键盘
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    protected abstract void setStatus();
    protected abstract int setLayoutResID();
    protected abstract void init();
}
