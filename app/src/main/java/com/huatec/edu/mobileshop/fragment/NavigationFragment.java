package com.huatec.edu.mobileshop.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huatec.edu.mobileshop.R;

public class NavigationFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "NavigationFragment";
    private LinearLayout tabItemHome;
    private LinearLayout tabItemCategory;
    private LinearLayout tabItemCart;
    private LinearLayout tabItemPersonal;
    private ImageView tabItemHomeBtn;
    private ImageView tabItemCategoryBtn;
    private ImageView tabItemCartBtn;
    private ImageView tabItemPersonalBtn;
    private FragmentManager fragmentManager;
    private View view;
    private TextView tabItemHomeTxt;
    private TextView tabItemCategoryTxt;
    private TextView tabItemCartTxt;
    private TextView tabItemPersonalTxt;
    private HomeFragment mHomeFragment;
    private CategoryFragment mCategoryFragment;
    private ThreeFragment threeFragment;
    private PersonFragment mPersonFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_navigation, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(view);
        setTabSelection(R.id.tab_item_home);
    }

    private void initView(View view) {
        //主页面
        tabItemHome = view.findViewById(R.id.tab_item_home);
        tabItemHome.setOnClickListener(this);
        //分类页面
        tabItemCategory = view.findViewById(R.id.tab_item_category);
        tabItemCategory.setOnClickListener(this);
        //购物车页面
        tabItemCart = view.findViewById(R.id.tab_item_cart);
        tabItemCart.setOnClickListener(this);
        //个人中心页面
        tabItemPersonal = view.findViewById(R.id.tab_item_personal);
        tabItemPersonal.setOnClickListener(this);
        tabItemHomeBtn = view.findViewById(R.id.tab_item_home_btn);
        tabItemCategoryBtn = view.findViewById(R.id.tab_item_category_btn);
        tabItemCartBtn = view.findViewById(R.id.tab_item_cart_btn);
        tabItemPersonalBtn = view.findViewById(R.id.tab_item_personal_btn);
        tabItemHomeTxt = view.findViewById(R.id.tab_item_home_txt);
        tabItemCategoryTxt = view.findViewById(R.id.tab_item_category_txt);
        tabItemCartTxt = view.findViewById(R.id.tab_item_cart_txt);
        tabItemPersonalTxt = view.findViewById(R.id.tab_item_personal_txt);
        //获得fragment管理类对象
        fragmentManager = getChildFragmentManager();
    }

    private void setTabSelection(int id) {
        //重置所有状态
        tabItemHomeBtn.setImageResource(R.drawable.tab_home_normal);
        tabItemCategoryBtn.setImageResource(R.drawable.tab_category_normal);
        tabItemCartBtn.setImageResource(R.drawable.tab_shopping_normal);
        tabItemPersonalBtn.setImageResource(R.drawable.tab_my_normal);
        tabItemHomeTxt.setTextColor(Color.parseColor("#ffffff"));
        tabItemCategoryTxt.setTextColor(Color.parseColor("#ffffff"));
        tabItemCartTxt.setTextColor(Color.parseColor("#ffffff"));
        tabItemPersonalTxt.setTextColor(Color.parseColor("#ffffff"));

        //开启一个事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //隐藏所有fragment
        if (mHomeFragment != null)
            fragmentTransaction.hide(mHomeFragment);
        if (mCategoryFragment != null)
            fragmentTransaction.hide(mCategoryFragment);
        if (threeFragment != null)
            fragmentTransaction.hide(threeFragment);
        if (mPersonFragment != null) {
            fragmentTransaction.hide(mPersonFragment);
        }

        //根据tabItem的id来执行不同的操作
        switch (id) {
            case R.id.tab_item_home:
                tabItemHomeBtn.setImageResource(R.drawable.tab_home_pressed);
                tabItemHomeTxt.setTextColor(Color.parseColor("#bfbfbf"));
                Log.d(TAG, "setTabSelection: 0");
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.content, mHomeFragment);
                } else {
                    fragmentTransaction.show(mHomeFragment);
                }

                //购物车不缓存页面
                if (threeFragment != null) {
                    fragmentTransaction.remove(threeFragment);
                    threeFragment = null;
                }
                break;

            case R.id.tab_item_category:
                tabItemCategoryBtn.setImageResource(R.drawable.tab_category_pressed);
                tabItemCategoryTxt.setTextColor(Color.parseColor("#bfbfbf"));
                Log.d(TAG, "setTabSelection: 1");
                if (mCategoryFragment == null) {
                    mCategoryFragment = new CategoryFragment();
                    fragmentTransaction.add(R.id.content, mCategoryFragment);
                } else {
                    fragmentTransaction.show(mCategoryFragment);
                }

                //购物车不缓存页面
                if (threeFragment != null) {
                    fragmentTransaction.remove(threeFragment);
                    threeFragment = null;
                }
                break;

            case R.id.tab_item_cart:
                tabItemCartBtn.setImageResource(R.drawable.tab_shopping_pressed);
                tabItemCartTxt.setTextColor(Color.parseColor("#bfbfbf"));
                Log.d(TAG, "setTabSelection: 2");
                if (threeFragment == null) {
                    threeFragment = new ThreeFragment();
                    fragmentTransaction.add(R.id.content, threeFragment);//replace
                } else {
                    fragmentTransaction.show(threeFragment);
                }
                break;

            case R.id.tab_item_personal:
                tabItemPersonalBtn.setImageResource(R.drawable.tab_my_pressed);
                tabItemPersonalTxt.setTextColor(Color.parseColor("#bfbfbf"));
                Log.d(TAG, "setTabSelection: 3");

                if (mPersonFragment == null) {
                    mPersonFragment = new PersonFragment();
                    fragmentTransaction.add(R.id.content, mPersonFragment);
                } else {
                    fragmentTransaction.show(mPersonFragment);
                }

                //购物车不缓存页面
                if (threeFragment != null) {
                    fragmentTransaction.remove(threeFragment);
                    threeFragment = null;
                }
                break;

        }

        fragmentTransaction.commit();//提交事务

    }

    @Override
    public void onClick(View v) {
        setTabSelection(v.getId());
    }

}
