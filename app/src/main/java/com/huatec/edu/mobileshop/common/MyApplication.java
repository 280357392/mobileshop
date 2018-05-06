package com.huatec.edu.mobileshop.common;

import android.app.Application;
import android.content.Context;

import com.huatec.edu.mobileshop.db.GreenDaoManager;
import com.huatec.edu.mobileshop.http.HttpMethods;

//注意到清单文件中添加name属性
public class MyApplication extends Application {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //greenDao全局配置
        GreenDaoManager.getInstance();

        //保证app只存在一个实例
        HttpMethods.getInstance();
    }
}
