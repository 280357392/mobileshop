package com.huatec.edu.mobileshop.db;

import com.huatec.edu.mobileshop.common.MyApplication;
import com.huatec.edu.mobileshop.gen.DaoMaster;
import com.huatec.edu.mobileshop.gen.DaoSession;

public class GreenDaoManager {

    private static GreenDaoManager mInstance;
    private  DaoMaster mDaoMaster;
    private  DaoSession mDaoSession;

    private GreenDaoManager() {
        DaoMaster.DevOpenHelper devOpenHelper = new
                DaoMaster.DevOpenHelper(MyApplication.getContext(),"mydb",null);

        mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();

    }

    public static GreenDaoManager getInstance(){
        if (mInstance==null){
            synchronized (GreenDaoManager.class){
                if (mInstance==null){
                    mInstance = new GreenDaoManager();
                }
            }
        }
        return mInstance;
    }

    public  DaoMaster getMaster() {
        return mDaoMaster;
    }

    public  DaoSession getSession() {
        return mDaoSession;
    }

    public  DaoSession getNewSession(){
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}
