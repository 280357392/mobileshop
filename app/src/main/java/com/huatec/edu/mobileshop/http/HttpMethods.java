package com.huatec.edu.mobileshop.http;

import com.huatec.edu.mobileshop.entity.HttpResult;
import com.huatec.edu.mobileshop.http.service.MemberService;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Function;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpMethods {

    protected static final String BASE_URL = "http://www.hao123.com";
    private static final int DEFAULT_TIMEOUT = 5;
    protected static final String TAG = "HttpMethods";
    private Retrofit retrofit;
    private static HttpMethods mInstance;
    protected static MemberService memberService;

    public HttpMethods() {
        if (mInstance == null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
            memberService = retrofit.create(MemberService.class);
        }
    }

    public static HttpMethods getInstance(){
        if (mInstance == null){
            synchronized (HttpMethods.class){
                if (mInstance == null){
                    mInstance = new HttpMethods();
                }
            }
        }
        return mInstance;
    }

    /**
     * 剥离data数据，返回给Subscriber观察者
     * @param <T>
     */
    public static class HttpResultFunc<T> implements Function<HttpResult<T>,T>{
        @Override
        public T apply(HttpResult<T> tHttpResult) {
            return tHttpResult.getData();
        }
    }

    //公共部分
    public static <T> void toSubscribe(Observable<T> o, Observer<T> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

}
