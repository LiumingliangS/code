package com.poszft;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.poszft.utils.BaseActivity;

/**
 *  支付页
 *
 */
public class PayActivity extends BaseActivity {
    private TextView tv_return;

    @Override
    protected void setStatus() {

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_pay;
    }

    @Override
    protected void init() {
        tv_return = (TextView) findViewById(R.id.tv_return);
        tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(PayActivity.this, MainActivity.class));
            }
        });
    }
}
