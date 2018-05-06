package com.huatec.edu.mobileshop.http.presenter;

import com.huatec.edu.mobileshop.entity.MemberEntity;
import com.huatec.edu.mobileshop.http.HttpMethods;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class MemberPresenter extends HttpMethods {

    //用户注册
    public static void register(Observer<MemberEntity> observer,String username,String emial, String password){

        Observable<MemberEntity> observable = memberService.register(username, password, emial)
                .map(new HttpResultFunc<MemberEntity>());//转换
        toSubscribe(observable,observer);//线程控制并订阅
    }


    //用户登录
    public static void login(Observer<MemberEntity> observer,String username,String password){

        Observable<MemberEntity> observable = memberService.login(username, password)
                .map(new HttpResultFunc<MemberEntity>());
        toSubscribe(observable,observer);
    }



}
