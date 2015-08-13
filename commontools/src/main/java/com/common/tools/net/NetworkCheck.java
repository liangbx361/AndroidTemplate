package com.common.tools.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkCheck {
	
	/**
	 * 获取当前的网络类型
	 * @param context
	 * @return
	 */
	public static int getNetWorkType(Context context) {
		ConnectivityManager connectMgr = (ConnectivityManager) context.
				getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		 NetworkInfo info = connectMgr.getActiveNetworkInfo();
		 return info.getType();
	}
	
	/**
	 * 判断是否为Wifi状态
	 * @param context
	 * @return
	 */
	public static boolean checkWifi(Context context) {
		int type = getNetWorkType(context);
		return type == ConnectivityManager.TYPE_WIFI ? true : false;
	}
	
	/**
	 * 网络是否可用
	 * @return
	 */
	public static boolean netwokAvaiable(Context context){
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
		if (connectivity != null) { 
            // 获取网络连接管理的对象 
            NetworkInfo info = connectivity.getActiveNetworkInfo(); 
            if (info != null&& info.isConnected()) { 
                // 判断当前网络是否已经连接 
                if (info.getState() == NetworkInfo.State.CONNECTED) { 
                    return true; 
                }
            } 
        }
		return false;
	}
}
