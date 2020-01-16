package com.poszft.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class MyUtils {

    /**
     * 获取当前应用版本号
     */
    public static int packageCode(Context context) {
        int code = 0;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     *  px->dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale =  context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     *  dp->px
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     *  px->sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     *  sp->px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 全透状态栏,设置状态栏颜色
     */
    public static void setStatusBarFullTransparent(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //删掉状态栏，底下布局顶上去了，像全面屏
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     *  popupwindow弹窗
     */
    public static List<Object> showPopWindow(final Context context, View currentView, int popXmlResId, int putWhere) {
        List<Object> objects = new ArrayList<>();


        //加载弹窗xml布局
        View contentView = LayoutInflater.from(context).inflate(popXmlResId, null, false);
        //在当前控件上new一个popupwindow
        PopupWindow window = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        window.setBackgroundDrawable(null);
        window.setOutsideTouchable(true); // 设置PopupWindow是否能响应外部点击事件
        window.setTouchable(true); // 设置PopupWindow是否能响应点击事件

        //放哪，0-在下面，1-屏幕中间，2-屏幕底部
        if (putWhere == 0){
//            window.showAsDropDown(currentView, 0, 0);
        }else if (putWhere == 1){
//             第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
//             第三和第四个参数分别是PopupWindow相对父View的x、y偏移
            window.showAtLocation(contentView, Gravity.CENTER, 0, -100);
        }else if (putWhere == 2){
            window.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        }

        //返回值
        objects.add(contentView);//返回xml的view对象，用于findviewbyid
        objects.add(window);//返回popupwindow对象，用于window.dismiss关闭弹窗

        return objects;//返回值为view类型和popupwindow类型
    }

    /**
     *  关闭软键盘
     */
    public static void colseKeyBroad(Activity activity) {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if(imm.isActive()&&activity.getCurrentFocus()!=null){
            //拿到view的token 不为空
            if (activity.getCurrentFocus().getWindowToken()!=null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     *
     * Map转String
     * @param map
     * @return
     */
    public static String getMapToString(Map<String,Object> map){
        Set<String> keySet = map.keySet();
        //将set集合转换为数组
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        //给数组排序(升序)
        Arrays.sort(keyArray);
        //因为String拼接效率会很低的，所以转用StringBuilder
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyArray.length; i++) {
            // 参数值为空，则不参与签名 这个方法trim()是去空格
            if ((String.valueOf(map.get(keyArray[i]))).trim().length() > 0) {
                sb.append(keyArray[i]).append(":").append(String.valueOf(map.get(keyArray[i])).trim());
            }
            if(i != keyArray.length-1){
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     *
     * String转map
     * @param str
     * @return
     */
    public static Map<String,Object> getStringToMap(String str){
        //根据逗号截取字符串数组
        String[] str1 = str.split(",");
        //创建Map对象
        Map<String,Object> map = new HashMap<>();
        //循环加入map集合
        for (int i = 0; i < str1.length; i++) {
            //根据":"截取字符串数组
            String[] str2 = str1[i].split(":");
            //str2[0]为KEY,str2[1]为值
            map.put(str2[0],str2[1]);
        }
        return map;
    }

    /**
     *  年月日
     */
    public static String day(){
        //时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 将毫秒转时分秒格式
     *
     */
    public static String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }
}
