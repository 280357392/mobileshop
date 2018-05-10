package com.huatec.edu.mobileshop.http.presenter;

import com.huatec.edu.mobileshop.entity.CategoryEntity;
import com.huatec.edu.mobileshop.http.HttpMethods;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class CategoryPresenter extends HttpMethods {

    /**
     * 获取一级分类
     * @param observer
     */
    public static void getTopList(Observer<List<CategoryEntity>> observer){
        Observable<List<CategoryEntity>> observable = categoryService.getTopList()
                .map(new HttpResultFunc<List<CategoryEntity>>());
        toSubscribe(observable,observer);
    }

    /**
     * 获取二级分类
     * @param observer
     * @param parentId
     */
    public static void getSecondList(Observer observer,int parentId){
        Observable<List<CategoryEntity>> observable = categoryService.getSecondList(parentId)
                .map(new HttpResultFunc<List<CategoryEntity>>());
        toSubscribe(observable,observer);
    }
}
