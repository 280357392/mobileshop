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
import android.widget.Toast;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.activity.BaseActivity;
import com.huatec.edu.mobileshop.entity.HttpResult;
import com.huatec.edu.mobileshop.http.ProgressDialogSubscriber;
import com.huatec.edu.mobileshop.http.presenter.MemberPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改密码页
 */
public class ChangePWDActivity extends BaseActivity {

    @BindView(R.id.title_back)
    ImageButton mTitleBack;
    @BindView(R.id.pwd_input_old_pass)
    EditText mPwdInputOldPass;
    @BindView(R.id.pwd_input_new_pass)
    EditText mPwdInputNewPass;
    @BindView(R.id.pwd_input_Repass)
    EditText mPwdInputRepass;
    @BindView(R.id.change_pwd)
    Button mChangePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.title_back, R.id.change_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.change_pwd:
                changePassword();
                break;
        }
    }

    //修改密码
    private void changePassword() {
        String old_password = mPwdInputOldPass.getText().toString().trim();
        String new_password = mPwdInputNewPass.getText().toString().trim();
        String new_rePassword = mPwdInputRepass.getText().toString().trim();
        //省略密码相关验证
        String member_id = getSharedPreferences("user", 0).getString("member_id", "");
//        if (TextUtils.isEmpty(member_id)) {
//            Toast.makeText(ChangePWDActivity.this, "登录已过期，请重新登录", Toast.LENGTH_SHORT).show();
//            return;
//        }

//        MemberPresenter.changePassword(new ProgressDialogSubscriber<HttpResult>(ChangePWDActivity.this){
//            @Override
//            public void onNext(HttpResult httpResult) {
//                super.onNext(httpResult);
//                if (httpResult.getStatus()== 0){
//                    //....可以保存在本地
//                    Intent intent = new Intent();
//                    setResult(RESULT_OK, intent);
//                    finish();
//                }else {
//                    Toast.makeText(ChangePWDActivity.this,"修改密码失败",Toast.LENGTH_SHORT).show();
//                }
//            }
//        },member_id,old_password,new_password);

        //模拟修改成功
        SharedPreferences.Editor localEditor = getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        localEditor.remove("login_state");
        localEditor.apply();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
