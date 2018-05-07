package com.huatec.edu.mobileshop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class MyWebView extends WebView {

    private IWebViewScroll mIWebViewScroll;

    public void setIWebViewScroll(IWebViewScroll IWebViewScroll) {
        mIWebViewScroll = IWebViewScroll;
    }

    public interface IWebViewScroll {
        void onTop();//处于顶部

        void notOnTop();
    }

    public MyWebView(Context context) {
        super(context, null);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 当webView当前位于顶部时接口回调。
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mIWebViewScroll != null && t == 0) {
            mIWebViewScroll.onTop();
        } else if (mIWebViewScroll != null && t != 0) {
            mIWebViewScroll.notOnTop();
        }
    }


}
