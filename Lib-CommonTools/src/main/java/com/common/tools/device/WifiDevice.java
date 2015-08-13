package com.common.tools.device;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

public class WifiDevice {
	
	/**
	 * 
	 * @描述:获取WIFI的MAC地址 
	 * @param context
	 */
	public static String getWifiMac(Context context) {
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE); 
		WifiInfo info = wifi.getConnectionInfo(); 
		return info.getMacAddress();
	}
	
	/**
	 * 
	 * @描述: 尝试打开wifi
	 * @param manager
	 * @return
	 */
	private static boolean tryOpenMAC(WifiManager manager) {
	    boolean softOpenWifi = false;
	    int state = manager.getWifiState();
	    if (state != WifiManager.WIFI_STATE_ENABLED && state != WifiManager.WIFI_STATE_ENABLING)
	    {
	        manager.setWifiEnabled(true);
	        softOpenWifi = true;
	    }
	    return softOpenWifi;
	}
	
	/**
	 * 
	 * @描述: 尝试关闭MAC
	 * @param manager
	 */
	private static void tryCloseMAC(WifiManager manager) {
		manager.setWifiEnabled(false);
	}

	/**
	 * 
	 * @描述: 尝试获取MAC地址
	 * @param manager
	 * @return
	 */
	private static String tryGetMAC(WifiManager manager) {
	    WifiInfo wifiInfo = manager.getConnectionInfo();
	    if (wifiInfo == null || TextUtils.isEmpty(wifiInfo.getMacAddress())) 
	    {
	        return null;
	    }
	    String mac = wifiInfo.getMacAddress();
	    return mac;
	}

	/**
	 * 
	 * @描述: 尝试读取MAC地址
	 * @param context
	 * @param internal
	 * @return
	 */
	public static String getMacFromDevice(Context context, int internal) {
	    String mac=null;
	    WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE); 
	    mac = tryGetMAC(wifiManager);
	    if(!TextUtils.isEmpty(mac))
	    {
	        return mac;
	    }
	    
	    //获取失败，尝试打开wifi获取
	    boolean isOkWifi = tryOpenMAC(wifiManager);
	    for(int index=0; index<internal; index++) {
	        //如果第一次没有成功，第二次做100毫秒的延迟。
	        try {
	        	Thread.sleep(100);
	        } catch (InterruptedException e) {
	                e.printStackTrace();
	        }
	    
	        mac = tryGetMAC(wifiManager);
	        if(!TextUtils.isEmpty(mac)) {
	        	break;
	        }
	    }
	    //尝试关闭wifi
	    if(isOkWifi)
	    {
	        tryCloseMAC(wifiManager);
	    } 
	    return mac;
	}
}
