package com.common.androidtemplate.module.function;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.common.androidtemplate.R;
import com.common.androidtemplate.activity.base.BaseBackActivity;


/**
 * @描述：新闻浏览，采用html模板为显示模板
 * @作者：liang bao xian
 * @时间：2014年8月19日 上午9:16:55
 */
public class WebviewEncryActivity extends BaseBackActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWebView();
    }

    @Override
    public void getIntentData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    public void initView() {
        webView = (WebView) findViewById(R.id.webview);
//		webView.loadUrl("file:///android_asset/news/newspage.html");
//		webView.loadUrl("http://www.163.com/");
        webView.loadUrl("file://mnt/sdcard/Conceal/test1/index.html");
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        //设置WebView属性，能够执行JavaScript脚本
        webSettings.setJavaScriptEnabled(true);
        //如果要播放Flash，需要加上这一句  
        webSettings.setPluginState(PluginState.ON);

        String databasePath = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setDatabaseEnabled(false);
        webSettings.setDatabasePath(databasePath);

        webSettings.setDomStorageEnabled(true);

        //在本页面中响应链接
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.d("webview_url", "shouldInterceptRequest=" + url);
                WebResourceResponse response = super.shouldInterceptRequest(view, url);
                return response;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("webview_url", "=============================================");
                Log.d("webview_url", "onPageStarted=" + url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Log.d("webview_url", "shouldInterceptRequest2");
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                Log.d("webview_url", "onLoadResource=" + url);
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.d("webview_url", "shouldOverrideUrlLoading=" + url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("webview_url", "onPageFinished=" + url);
            }
        });
    }

    public final class NewsScriptInterface {

        @JavascriptInterface
        public String getContent() {
            return null;
        }
    }
}
