package com.poszft;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.poszft.utils.BaseActivity;

/**
 *  选择支付方式页
 *
 */
public class PaySelectActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_wechat;



    @Override
    protected void setStatus() {

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_pay_select;
    }

    @Override
    protected void init() {
        ll_wechat = (LinearLayout) findViewById(R.id.ll_wechat);
        ll_wechat.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_wechat:
                startActivity(new Intent(PaySelectActivity.this, PayActivity.class));
                break;
        }
    }
}
