package com.huatec.edu.mobileshop.http.service;

import com.huatec.edu.mobileshop.entity.HttpResult;
import com.huatec.edu.mobileshop.entity.MemberEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    //找回密码
    @POST("member/pwd")
    Observable<HttpResult> findPassword(@Field("email") String email);

    //修改密码
    @FormUrlEncoded
    @POST("member/{memberId}")
    Observable<HttpResult> changePassword(
            @Path("memberId") String memberId,
            @Field("old_pwd") String old_pwd,
            @Field("new_pwd") String new_pwd
    );

}
