package com.common.androidtemplate.app;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.common.androidtemplate.config.DbConfig;
import com.common.androidtemplate.config.DebugConfig;
import com.common.androidtemplate.config.NetConfig;
import com.common.androidtemplate.utils.datebase.DbUpdateHandler;
import com.common.androidtemplate.utils.volley.VolleyX509TrustManager;
import com.common.androidtemplate.utils.volley.cache.VolleyImageCache;
import com.common.tools.exception.ExceptionLog;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.utils.Utils;

public class TEMApp extends Application {

	// APP实例
	private static TEMApp mApp;

	// 网络
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	private long requestTag = 0;

	// 数据库
	private FinalDb mFinalDb;
	
	//异常日志记录
	private ExceptionLog exLog;

	@Override
	public void onCreate() {
		super.onCreate();
		mApp = this;

		// Volley请求队列、图片类实例化
		mRequestQueue = Volley.newRequestQueue(getApplicationContext(), Utils
				.getDiskCacheDir(getApplicationContext(), "volley")
				.getAbsolutePath(), null);
		float density = getResources().getDisplayMetrics().density;
		VolleyImageCache mImageCache = new VolleyImageCache((int) (density * 4 * 1024 * 1024));
		mImageLoader = new ImageLoader(mRequestQueue, mImageCache);
		
		if(NetConfig.HTTPS_ENABLE) {
			//开启HTTPS功能
			VolleyX509TrustManager.allowAllSSL();
		}

		// 创建数据库
		mFinalDb = FinalDb.create(this, DbConfig.DB_NAME,
				DebugConfig.SHOW_DEBUG_MESSAGE, DbConfig.DB_VERSION,
				new DbUpdateHandler());		
		
		exLog = ExceptionLog.getInstance();  
		exLog.init(getApplicationContext());

		//ActiveAndroid 初始化
//		ActiveAndroid.initialize(this);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
//		ActiveAndroid.dispose();
	}

	/**
	 * 获取APP实例
	 * 
	 * @return 返回App实例
	 */
	public synchronized static TEMApp getInstance() {
		return mApp;
	}

	/**
	 * 获取网络请求队列
	 * 
	 * @return 返回请求队列实例
	 */
	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}

	/**
	 * 获取图片加载者
	 * 
	 * @return 返回ImageLoader实例
	 */
	public ImageLoader getImageLoader() {
		return mImageLoader;
	}

	/**
	 * 获取数据库对象
	 * 
	 * @return 返回FinalDb实例
	 */
	public FinalDb getDb() {
		return mFinalDb;
	}

	/**
	 * 获取请求标签(保证每次返回的都不重复)
	 * 
	 * @return 返回请求的标签
	 */
	public synchronized String getRequestTag() {
		requestTag++;
		return requestTag + "";
	}

	/**
	 * 异常日志
	 * @return 返回异常日志记录
	 */
	public ExceptionLog getExLog() {
		return exLog;
	}
}
