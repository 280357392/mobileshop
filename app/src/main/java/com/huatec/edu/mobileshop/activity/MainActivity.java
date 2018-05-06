package com.huatec.edu.mobileshop.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.fragment.NavigationFragment;

public class MainActivity extends BaseActivity {

    private NavigationFragment navigationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationFragment = new NavigationFragment();
        FragmentTransaction nafragmentTransaction = getSupportFragmentManager().beginTransaction();
        nafragmentTransaction.add(R.id.main_frame, navigationFragment).commit();

    }
}
