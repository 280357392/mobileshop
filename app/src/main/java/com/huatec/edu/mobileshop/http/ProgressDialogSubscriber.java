package com.huatec.edu.mobileshop.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 封装一个有加载进度的Observer
 * @param <T>
 */
public class ProgressDialogSubscriber<T> implements Observer<T> {

    private static final String TAG = "ProgressDialogSubscribe";
    private Context mContext;
    private ProgressDialog mDialog;
    private Disposable mDisposable;

    public ProgressDialogSubscriber(Context context) {
        mContext = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        showProgressDialog();
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        //友好提示
        if (e instanceof SocketTimeoutException){
            Toast.makeText(mContext,"网络中断，请检查您的网络状态",Toast.LENGTH_SHORT).show();
        }else if (e instanceof ConnectException){
            Toast.makeText(mContext,"网络中断，请检查您的网络状态",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext,"error"+e.getMessage(),Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onError: "+e.getMessage());
        }
        dismissProgressDialog();//关闭对话框
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();//关闭对话框
    }

    /**
     * 显示对话框并监听
     */
    private void showProgressDialog(){
        if (mDialog==null){
            mDialog = new ProgressDialog(mContext);
            mDialog.setCancelable(true);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (mDisposable != null && mDisposable.isDisposed()) {
                        mDisposable.dispose();//取消订阅，取消网络请求
                    }
                }
            });
            if (mDialog!=null && !mDialog.isShowing()){
                mDialog.show();
            }
        }
    }

    /**
     * 隐藏对话框
     */
    private void dismissProgressDialog(){
        if (mDialog!=null && mDialog.isShowing()){
            mDialog.dismiss();
            mDialog=null;
        }
    }
}
