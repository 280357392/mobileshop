package com.huatec.edu.mobileshop.activity.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录页
 */
public class LoginActivity extends BaseActivity {

    //返回
    @BindView(R.id.title_back)
    ImageButton mTitleBack;
    //账号输入
    @BindView(R.id.login_input_name)
    EditText loginInputName;
    //密码输入
    @BindView(R.id.login_input_password)
    EditText loginInputPassword;
    @BindView(R.id.login_button)
    Button mLoginButton;
    @BindView(R.id.register_link)
    TextView mRegisterLink;
    @BindView(R.id.find_password_text)
    TextView mFindPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_back, R.id.login_button, R.id.register_link, R.id.find_password_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back://返回
                finish();
                break;
            case R.id.login_button://登录
                login();
                break;
            case R.id.register_link://跳转到快速注册
                register();
                break;
            case R.id.find_password_text://跳转到找回密码
                findPWD();
                break;
        }
    }


    /**
     * 登录
     */
    private void login() {
        String username = loginInputName.getText().toString().trim();
        String password = loginInputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)){
            Toast.makeText(LoginActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        //联网请求
//        MemberPresenter.login(new ProgressDialogSubscriber<MemberEntity>(this){
//            @Override
//            public void onNext(MemberEntity memberEntity) {
//                super.onNext(memberEntity);
//                .......
//                  ......
//
//            }
//        },username,password);

        //模拟登录成功
        SharedPreferences.Editor localEditor = getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        localEditor.putInt("login_state",1);
        localEditor.apply();

        //登录成功后返回
        Intent intent = new Intent();
        intent.putExtra("logined", true);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 跳转注册界面
     */
    private void register() {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }


    private void findPWD() {
        startActivity(new Intent(LoginActivity.this,FindPWDActivity.class));
    }
}
