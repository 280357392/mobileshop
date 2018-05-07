package com.huatec.edu.mobileshop.fragment;

import android.annotation.SuppressLint;
import android.app.Application;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.common.MyApplication;
import com.huatec.edu.mobileshop.common.Urls;
import com.huatec.edu.mobileshop.utils.NetworkUtils;
import com.huatec.edu.mobileshop.view.MyWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";
    Unbinder unbinder;
    @BindView(R.id.home_search)
    TextView searchTV;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.web_view_content_fl)
    FrameLayout mWebViewContentFl;

    private MyWebView mWebView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initMyWebView(view);//webView初始化设置
        initSwipeRefreshLayout();
        return view;
    }

    @OnClick(R.id.home_search)
    public void onViewClicked() {
        Toast.makeText(getActivity(), "等待开发", Toast.LENGTH_SHORT).show();
    }

    /**
     * 将webView加入容器中
     */
    private void initView() {
//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mWebViewContentFl.getLayoutParams();
//        ViewGroup.LayoutParams layoutParams = mWebViewContentFl.getLayoutParams();
//        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
//        params.height = FrameLayout.LayoutParams.MATCH_PARENT;
        mWebView = new MyWebView(MyApplication.getContext());
        mWebViewContentFl.addView(mWebView);
    }

    /**
     * webView初始化设置
     *
     * @param view
     */
    @SuppressLint("JavascriptInterface")
    private void initMyWebView(View view) {
        mWebView.addJavascriptInterface(this, "app");//添加JavaScript接口和接口名称
        mWebView.setVerticalScrollBarEnabled(false);//设置无垂直方向的scrollbar
        mWebView.setHorizontalScrollBarEnabled(false);//设置无水平方向的scrollbar

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);//启用js脚本
        settings.setSupportZoom(false);//支持缩放
        settings.setBuiltInZoomControls(false);//启用内置缩放装置

        //滑动监听
        mWebView.setIWebViewScroll(new MyWebView.IWebViewScroll() {
            @Override
            public void onTop() {
                mSwipeRefreshLayout.setEnabled(true);
            }

            @Override
            public void notOnTop() {
                mSwipeRefreshLayout.setEnabled(false);
            }
        });

        //点击后退按钮，让WebView后退（无效）
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                        mWebView.goBack();
                        Log.d(TAG, "onKey: ");
                        return true;
                    }
                }
                return true;
            }
        });

        //页面加载处理
        mWebView.setWebViewClient(new WebViewClient() {
            //当点击链接时，希望覆盖而不是打开浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //加载错误
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //用javascript隐藏系统定义的404页面
                //mWebView.loadUrl("file:///android_asset/error.html");
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(false);//隐藏下拉刷新
                }
                Log.d(TAG, "onReceivedError: ");
            }

            //开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(true);//显示下拉刷新
                }
                Log.d(TAG, "onPageStarted: ");
            }

            //加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(false);//隐藏下拉刷新
                }
                Log.d(TAG, "onPageFinished: ");
            }
        });
        mWebView.loadUrl(Urls.INDEX);
    }

    //下拉刷新
    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright
                , android.R.color.holo_green_light
                , android.R.color.holo_orange_light
                , android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkUtils.isNetworkAvailable(getActivity()) != NetworkUtils.NOTNETWORK) {
                    mWebView.reload();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);//无网络刷新时，马上隐藏
                    Toast.makeText(getContext(), "网络不可用", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.stopLoading();
            mWebView.removeAllViews();
            mWebView.clearHistory();
            mWebViewContentFl.removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroyView();
        unbinder.unbind();
    }


}
