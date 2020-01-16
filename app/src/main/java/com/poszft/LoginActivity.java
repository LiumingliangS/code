package com.poszft;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.poszft.utils.BaseActivity;
import com.poszft.utils.MyUtils;
import com.zhangke.websocket.SimpleListener;
import com.zhangke.websocket.SocketListener;
import com.zhangke.websocket.WebSocketHandler;
import com.zhangke.websocket.response.ErrorResponse;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 *  登录页
 *
 */
public class LoginActivity extends BaseActivity {
    private EditText etname, etpwd;
    private TextView tv_login;
    private ImageView iv_del;
    private boolean nameNotEmp = false;
    private boolean pwdNotEmp = false;

    @Override
    protected void setStatus() {
//        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#076EED"));
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        etname = (EditText) findViewById(R.id.etname);
        etpwd = (EditText) findViewById(R.id.etpwd);
        tv_login = (TextView) findViewById(R.id.tv_login);
        iv_del = (ImageView) findViewById(R.id.iv_del);
        tv_login.setEnabled(false);

        etname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Log.e("测试", charSequence.toString()+String.valueOf(i2));

                if (i2 == 0){
                    iv_del.setVisibility(View.INVISIBLE);
                    nameNotEmp = false;

                    if (nameNotEmp == false || pwdNotEmp == false){
                        tv_login.setBackgroundResource(R.drawable.login_un_bg);
                        tv_login.setEnabled(false);
                    }
                }else {
                    iv_del.setVisibility(View.VISIBLE);
                    nameNotEmp = true;

                    if (nameNotEmp == true && pwdNotEmp == true){
                        tv_login.setBackgroundResource(R.drawable.login_sel_bg);
                        tv_login.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 == 0){
                    pwdNotEmp = false;

                    if (nameNotEmp == false || pwdNotEmp == false){
                        tv_login.setBackgroundResource(R.drawable.login_un_bg);
                        tv_login.setEnabled(false);
                    }
                }else {
                    pwdNotEmp = true;

                    if (nameNotEmp == true && pwdNotEmp == true){
                        tv_login.setBackgroundResource(R.drawable.login_sel_bg);
                        tv_login.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e("测试2", "能点");
                etname.setText("");
                etpwd.setText("");
            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

                WebSocketHandler.getDefault().send("你好");
            }
        });

        WebSocketHandler.getDefault().addListener(socketListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebSocketHandler.getDefault().removeListener(socketListener);
    }

    private void login() {
        String name = etname.getText().toString();
        String pwd = etpwd.getText().toString();

//        if (name == null || name.equals("")){
//            Toast.makeText(LoginActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (pwd == null || pwd.equals("")){
//            Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
//            return;
//        }

        MyUtils.colseKeyBroad(LoginActivity.this);

//        //联网操作
//        OkHttpUtils
//                .post()
//                .url("http://www.guojiayikao.net/home/dealSignIn.php")
//                .addParams("mobile", account)
//                .addParams("imgcode", yzm)
//                .addParams("code", pwd)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Request request, Exception e) {
//                        Toast.makeText(LoginActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onResponse(String response) {
//                        LoginBean loginBean = new Gson().fromJson(response, LoginBean.class);
//                        int issuccess = loginBean.getSuccess();
//
//                        if (issuccess  == 200 ){//请求成功
//                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));

//                            finish();
//                        }else {
//                            //清空密码
//                            etpwd.setText("");
                            Toast toast = Toast.makeText(LoginActivity.this, "账号或密码有误", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
//                        }
//                    }
//                });
    }

    private SocketListener socketListener = new SimpleListener() {
        @Override
        public void onConnected() {
            Log.e("websocket", "Connected");
        }

        @Override
        public void onConnectFailed(Throwable e) {
            if (e != null) {
                Log.e("websocket", "onConnectFailed:" + e.toString());
            } else {
                Log.e("websocket", "onConnectFailed:null");
            }
        }

        @Override
        public void onDisconnect() {
            Log.e("websocket", "onDisconnect");
        }

        @Override
        public void onSendDataError(ErrorResponse errorResponse) {
            Log.e("websocket333", "没网发送数据会蹦");
//            Log.e("websocket333", errorResponse.getDescription());
            errorResponse.release();
        }

        @Override
        public <T> void onMessage(String message, T data) {
//            if (data instanceof CommonResponseEntity) {
//                CommonResponseEntity responseEntity = (CommonResponseEntity) data;
//                appendMsgDisplay(responseEntity.getMessage());
//            }

            Log.e("websocket222", message);
        }
    };
}