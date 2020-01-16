package com.poszft;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.poszft.utils.BaseActivity;
import com.poszft.utils.MyUtils;

import cn.bertsir.zbar.Qr.ScanResult;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;
import cn.bertsir.zbar.view.ScanLineView;


/**
 *  扫码页
 *
 *  关闭扫码页，扫码中回退会退到上一页
 *
 */
public class ScanActivity extends BaseActivity {



    @Override
    protected void setStatus() {

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_qrcode;
    }

    protected void init() {
        QrConfig qrConfig = new QrConfig.Builder()
                .setDesText("对准二维码/条形码后，即可自动扫描")//扫描框下文字
                .setShowDes(true)//是否显示扫描框下面文字
                .setShowTitle(true)//显示Title
                .setTitleText("扫码")//设置Tilte文字
                .setCornerColor(Color.parseColor("#076EED"))//设置扫描框颜色
                .setLineColor(Color.parseColor("#076EED"))//设置扫描线颜色
                .setLineSpeed(QrConfig.LINE_MEDIUM)//设置扫描线速度
                .setScanType(QrConfig.TYPE_ALL)//设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
                .setScanViewType(QrConfig.SCANVIEW_TYPE_QRCODE)//设置扫描框类型（二维码还是条形码，默认为二维码）
                .setCustombarcodeformat(QrConfig.BARCODE_PDF417)//此项只有在扫码类型为TYPE_CUSTOM时才有效
//                .setIsOnlyCenter(cb_only_center.isChecked())//是否只识别框中内容(默认为全屏识别)

                .setTitleBackgroudColor(Color.parseColor("#66000000"))//设置状态栏颜色
                .setTitleTextColor(Color.WHITE)//设置Title文字颜色
                .setDoubleEngine(true)//是否开启双引擎识别(仅对识别二维码有效，并且开启后只识别框内功能将失效)
                .setScreenOrientation(QrConfig.SCREEN_PORTRAIT)//设置屏幕方式
                .setScanLineStyle(ScanLineView.style_line)//扫描线样式
                .create();

        QrManager.getInstance().init(qrConfig).startScan(ScanActivity.this, new QrManager.OnScanResultCallback() {
            @Override
            public void onScanSuccess(ScanResult result) {
//                Log.e("扫描结果", "onScanSuccess: " + result);
                Log.e("扫描结果", "内容：" + result.getContent() + "  类型：" + result.getType());
//                Toast.makeText(getApplicationContext(), "内容：" + result.getContent() + "  类型：" + result.getType(), Toast.LENGTH_SHORT).show();

                //将扫描结果传给扫描2
                startActivity(new Intent(ScanActivity.this, Scan2Activity.class));
            }
        });
    }
}
