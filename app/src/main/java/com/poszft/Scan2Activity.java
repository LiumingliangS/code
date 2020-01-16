package com.poszft;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.poszft.utils.BaseActivity;
import com.poszft.utils.MyUtils;

public class Scan2Activity extends BaseActivity {
    private LinearLayout ll_ring, ll_succ;
    private ImageView iv_ring;
    private TextView tv_sure;


    @Override
    protected void setStatus() {

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_scan2;
    }

    @Override
    protected void init() {

        //子线程，参考诊付通
//        CardReader cardReader = new CardReader();
//        String json_result = cardReader.Init(json_request);
//        CardReader cardReader = new CardReader();
//        String json_result = cardReader.Call(json_request);



        ll_succ = (LinearLayout) findViewById(R.id.ll_succ);
        ll_ring = (LinearLayout) findViewById(R.id.ll_ring);
        iv_ring = (ImageView) findViewById(R.id.iv_ring);
        tv_sure = (TextView) findViewById(R.id.tv_sure);

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Scan2Activity.this, PaySelectActivity.class));
            }
        });

        MyUtils.setStatusBarFullTransparent(Scan2Activity.this, Color.parseColor("#ffffff"));
        ll_ring.setVisibility(View.VISIBLE);

        //补间动画
        RotateAnimation rotateAnimation = new RotateAnimation(0, 3600, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000*10);
        iv_ring.startAnimation(rotateAnimation);

        //读卡逻辑，逻辑完成显示结果，比如3秒
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyUtils.setStatusBarFullTransparent(Scan2Activity.this, Color.parseColor("#076EED"));
                ll_ring.setVisibility(View.GONE);
                //成功或失败页出现
                ll_succ.setVisibility(View.VISIBLE);
            }
        }, 5000);
    }
}
