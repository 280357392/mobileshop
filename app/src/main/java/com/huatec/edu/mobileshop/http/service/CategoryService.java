package com.huatec.edu.mobileshop.http.service;

import com.huatec.edu.mobileshop.entity.CategoryEntity;
import com.huatec.edu.mobileshop.entity.HttpResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoryService {

    //加载一级分类
    @GET("cat/show")
    Observable<HttpResult<List<CategoryEntity>>> getTopList();

    //加载二级分类
    @GET("cat/show/{parentId}")
    Observable<HttpResult<List<CategoryEntity>>> getSecondList(@Path("parentId") int parentId);
}
