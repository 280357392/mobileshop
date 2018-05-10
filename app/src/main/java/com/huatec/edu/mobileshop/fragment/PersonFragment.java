package com.huatec.edu.mobileshop.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.activity.AddressActivity;
import com.huatec.edu.mobileshop.activity.login.ChangePWDActivity;
import com.huatec.edu.mobileshop.activity.login.LoginActivity;
import com.huatec.edu.mobileshop.activity.MyFavoriteActivity;
import com.huatec.edu.mobileshop.activity.MyOrderActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PersonFragment extends BaseFragment {

    private static final String TAG = "PersonFragment";
    private static final int MY_ORDER = 1;
    private static final int MY_FAVORITE = 2;
    private static final int MY_ADDRESS = 3;
    private static final int MY_ACCOUNT_AFTER = 4;
    private static final int MY_ACCOUNT_BEFORE = 5;
    Unbinder unbinder;
    @BindView(R.id.user_img_view)
    ImageView userImgView;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_level)
    TextView userLevel;
    @BindView(R.id.person_for_login)
    RelativeLayout personForLogin;
    @BindView(R.id.person_login)
    Button personLogin;
    @BindView(R.id.personal_for_not_login)
    RelativeLayout personalForNotLogin;
    @BindView(R.id.person_my_order)
    RelativeLayout personMyOrder;
    @BindView(R.id.my_collect)
    RelativeLayout myCollect;
    @BindView(R.id.my_address)
    RelativeLayout myAddress;
    @BindView(R.id.my_account)
    RelativeLayout myAccount;
    //退出登录
    @BindView(R.id.person_logout_layout)
    RelativeLayout personLogoutLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 登录回来要更新布局
     */
    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    @OnClick({R.id.person_login, R.id.person_my_order, R.id.my_collect,
            R.id.my_address, R.id.my_account, R.id.person_logout_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.person_login:
                Log.d(TAG, "onClick: 注册/登录");
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.person_my_order:
                Log.d(TAG, "onClick: 我的订单");
                if (isLogin()){//已登录
                    startActivity(new Intent(getActivity(),MyOrderActivity.class));
                }else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), MY_ORDER);
                }
                break;
            case R.id.my_collect:
                Log.d(TAG, "onClick: 我的收藏");
                if (isLogin()){//已登录时
                    startActivity(new Intent(getActivity(),MyFavoriteActivity.class));
                }else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), MY_FAVORITE);
                }
                break;
            case R.id.my_address:
                Log.d(TAG, "onClick: 收货地址");
                if (isLogin()){//已登录
                    startActivity(new Intent(getActivity(),AddressActivity.class));
                }else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), MY_ADDRESS);
                }
                break;
            case R.id.my_account:
                Log.d(TAG, "onClick: 修改密码");
                if (isLogin()){//已登录
                    startActivityForResult(new Intent(getActivity(),ChangePWDActivity.class),MY_ACCOUNT_AFTER);
                }else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), MY_ACCOUNT_BEFORE);
                }
                break;
            case R.id.person_logout_layout:
                Log.d(TAG, "onClick: 退出登录");
                new AlertDialog.Builder(getActivity())
                        .setTitle("退出登录")
                        .setMessage("您确认要退出登录吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                break;
        }
    }

    /**
     * 退出登录，删除登录状态并更新布局
     */
    private void logout() {
        SharedPreferences.Editor localEditor = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        localEditor.remove("login_state");
        localEditor.apply();
        init();
        Toast.makeText(getActivity(),"退出登录成功",Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据登录状态更新布局
     */
    private void init() {
        if (isLogin()){
            personForLogin.setVisibility(View.VISIBLE);//显示"已登录"布局文件
            personalForNotLogin.setVisibility(View.GONE);//隐藏"未登录"布局文件
            personLogoutLayout.setVisibility(View.VISIBLE);//显示"退出登录"
        }else {
            personForLogin.setVisibility(View.GONE);
            personalForNotLogin.setVisibility(View.VISIBLE);
            personLogoutLayout.setVisibility(View.GONE);
        }
    }


    /**
     * 判断是否已登录
     * @return
     */
    private boolean isLogin(){
        SharedPreferences user = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        int login_state = user.getInt("login_state", 0);
        if (login_state == 1){
            return true;
        }else{
            return false;
        }
    }


    /**
     * 登录成功后自行进入之前想去的页面
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case MY_ORDER://登录完，返回时，自动跳转要去的页面
                if (resultCode == Activity.RESULT_OK
                        && data.getBooleanExtra("logined",false)){
                    startActivity(new Intent(getActivity(),MyOrderActivity.class));
                }
                break;
            case MY_FAVORITE:
                if (resultCode == Activity.RESULT_OK
                        && data.getBooleanExtra("logined",false)){
                    startActivity(new Intent(getActivity(),MyFavoriteActivity.class));
                }
                break;
            case MY_ADDRESS:
                if (resultCode == Activity.RESULT_OK
                        && data.getBooleanExtra("logined",false)){
                    startActivity(new Intent(getActivity(),AddressActivity.class));
                }
                break;
            case MY_ACCOUNT_BEFORE:
                if (resultCode == Activity.RESULT_OK
                        && data.getBooleanExtra("logined",false)){
                    //登录--修改密码--登录
                    Intent intent = new Intent(getActivity(),ChangePWDActivity.class);
                    startActivityForResult(intent,MY_ACCOUNT_AFTER);
                }
                break;
            case MY_ACCOUNT_AFTER:
                if (resultCode == Activity.RESULT_OK){
                    //修改密码--登录
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

