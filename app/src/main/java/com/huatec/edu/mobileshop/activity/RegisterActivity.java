package com.huatec.edu.mobileshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.entity.MemberEntity;
import com.huatec.edu.mobileshop.http.presenter.MemberPresenter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 注册页面
 * 点击注册按钮后执行：本地验证、网络验证、通过验证后联网注册
 */
public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.title_back)
    ImageButton titleBack;
    @BindView(R.id.login_input_name)
    EditText loginInputName;
    @BindView(R.id.login_input_email)
    EditText loginInputEmail;
    @BindView(R.id.login_input_password)
    EditText loginInputPassword;
    @BindView(R.id.login_input_repassword)
    EditText loginInputRepassword;
    @BindView(R.id.register_button)
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_back, R.id.register_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back://返回
                finish();
                break;
            case R.id.register_button://注册
                register();
                break;
        }
    }

    /**
     * 注册
     */
    private void register() {
        String username = loginInputName.getText().toString().trim();
        String email = loginInputEmail.getText().toString().trim();
        String password = loginInputPassword.getText().toString().trim();
        String rePassword = loginInputRepassword.getText().toString().trim();

        //本地验证
        boolean checkUsername = checkUsername(username);
        if (!checkUsername) return;
        boolean checkEmail = checkEmail(email);
        if (!checkEmail) return;
        boolean checkPWD = checkPWD(password, rePassword);
        if (!checkPWD) return;

        //模拟注册成功（应该让其回到登录页即可）
        Intent returnIntent = new Intent();
        returnIntent.putExtra("logined", true);
        setResult(RESULT_OK, returnIntent);
        finish();

        //联网注册
//        MemberPresenter.register(new Observer<MemberEntity>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(MemberEntity memberEntity) {
//                //服务器返回
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        },username,email,password);

    }

    /**
     * 对用户名的：非空、长度、特殊字符验证。
     *
     * @param username
     * @return 不通过验证返回false
     */
    private boolean checkUsername(String username) {
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (username.length() < 4 || username.length() > 20) {
            Toast.makeText(RegisterActivity.this, "用户名的长度为4—20个字符！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (username.contains("@")) {
            Toast.makeText(RegisterActivity.this, "用户名中不能包含@字符！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 对邮箱的:非空、格式
     *
     * @param email
     * @return 不通过验证返回false
     */
    private boolean checkEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(RegisterActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = p.matcher(email);
        if (!m.matches()) {
            Toast.makeText(RegisterActivity.this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 对密码的验证
     *
     * @param password
     * @param rePassword
     * @return 不通过验证返回false
     */
    private boolean checkPWD(String password, String rePassword) {
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(rePassword)) {
            Toast.makeText(RegisterActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
