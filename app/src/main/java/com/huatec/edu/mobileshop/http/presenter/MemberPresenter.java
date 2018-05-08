package com.huatec.edu.mobileshop.http.presenter;

import com.huatec.edu.mobileshop.entity.HttpResult;
import com.huatec.edu.mobileshop.entity.MemberEntity;
import com.huatec.edu.mobileshop.http.HttpMethods;
import com.huatec.edu.mobileshop.http.ProgressDialogSubscriber;

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

    //找回密码
    public static void findPassword(Observer observer,String email){
        Observable<HttpResult> observable = memberService.findPassword(email);
        toSubscribe(observable,observer);
    }

    //修改密码
    public static void changePassword(Observer observer, String memberId,String old_pwd,String new_pwd){
        Observable<HttpResult> observable = memberService.changePassword(memberId, old_pwd, new_pwd);
        toSubscribe(observable,observer);
    }

}
