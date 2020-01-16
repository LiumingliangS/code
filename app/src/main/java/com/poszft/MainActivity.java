package com.poszft;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.poszft.utils.BaseActivity;
import com.poszft.utils.MyUtils;

/**
 *  读卡页
 *
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_re, ll_succ;
    private ImageView iv_ring;
    private TextView tv_qrcode, tv_readcard, tv_exit;

    @Override
    protected void setStatus() {

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        ll_re = (LinearLayout) findViewById(R.id.ll_re);
        ll_succ = (LinearLayout) findViewById(R.id.ll_succ);
        iv_ring = (ImageView) findViewById(R.id.iv_ring);
        tv_qrcode = (TextView) findViewById(R.id.tv_qrcode);
        tv_readcard = (TextView) findViewById(R.id.tv_readcard);
        tv_exit = (TextView) findViewById(R.id.tv_exit);

        tv_readcard.setOnClickListener(this);
        tv_exit.setOnClickListener(this);
        tv_qrcode.setOnClickListener(this);
    }

    private void readCard() {
        ll_re.setVisibility(View.GONE);
        MyUtils.setStatusBarFullTransparent(MainActivity.this, Color.parseColor("#ffffff"));
        iv_ring.setVisibility(View.VISIBLE);

        //补间动画
        RotateAnimation rotateAnimation = new RotateAnimation(0, 3600, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000*10);
        iv_ring.startAnimation(rotateAnimation);

//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv_ring,"rotation",3600f);
//        objectAnimator.setDuration(2000*10);
//        objectAnimator.start();

        //读卡逻辑，逻辑完成显示结果，比如3秒
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyUtils.setStatusBarFullTransparent(MainActivity.this, Color.parseColor("#076EED"));
                iv_ring.setVisibility(View.GONE);
                ll_succ.setVisibility(View.VISIBLE);
            }
        }, 5000);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ll_succ.setVisibility(View.GONE);
        ll_re.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_readcard:
                readCard();
                break;
            case R.id.tv_exit:
                finish();
                break;
            case R.id.tv_qrcode:
                startActivity(new Intent(MainActivity.this, ScanActivity.class));
                break;
        }
    }
}



