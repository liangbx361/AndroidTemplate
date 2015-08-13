package com.common.androidtemplate.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.common.androidtemplate.R;
import com.common.androidtemplate.activity.base.BaseBackActivity;


/**
 * 
 * @描述：新闻浏览，采用html模板为显示模板
 * @作者：liang bao xian
 * @时间：2014年8月19日 上午9:16:55
 */
public class NewsReaderActivity extends BaseBackActivity {
	
	private WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		
		getIntentData();
		initView();
		initWebView();
	}
	
	@Override
	public void getIntentData() {
		
	}

	@Override
	public void initView() {
		webView = (WebView) findViewById(R.id.webview);
		webView.loadUrl("file:///android_asset/news/newspage.html");
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
        webSettings.setDatabaseEnabled(true);
        webSettings.setDatabasePath(databasePath);
        
        webSettings.setDomStorageEnabled(true);
        
        //在本页面中响应链接
        webView.setWebViewClient(new WebViewClient(){       
            public boolean shouldOverrideUrlLoading(WebView view, String url) {       
                view.loadUrl(url);       
                return true;       
            }

			@Override
			public void onPageFinished(WebView view, String url) {
				Log.d("webview_url", url);
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
