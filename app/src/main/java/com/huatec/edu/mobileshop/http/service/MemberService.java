package com.huatec.edu.mobileshop.http.service;

import com.huatec.edu.mobileshop.entity.HttpResult;
import com.huatec.edu.mobileshop.entity.MemberEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MemberService {

    //用户注册
    @FormUrlEncoded
    @POST("member")
    Observable<HttpResult<MemberEntity>> register(
            @Field("uname") String name,
            @Field("password") String password,
            @Field("email") String email);

    //登录
    @FormUrlEncoded
    @POST("member/login")
    Observable<HttpResult<MemberEntity>> login(
            @Field("uname") String uname,
            @Field("password") String password);

}
