package com.common.tools.phone;

import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;

/**
 * 
 * @描述：获取手机基本信息
 * @作者：liang bao xian
 * @时间：2014年8月1日 下午2:36:10
 */
public class TelephonyHelper {
	
	private static TelephonyHelper telHelper;
	private TelephonyManager tm;
	
	private TelephonyHelper(Context context) {
		tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	}
	
	public synchronized static TelephonyHelper getInstance(Context context) {
		if(telHelper == null) {
			telHelper = new TelephonyHelper(context);
		}
		return telHelper;
	}
	
	/**
	 * 电话状态：   
	 * 1.tm.CALL_STATE_IDLE=0          无活动   
	 * 2.tm.CALL_STATE_RINGING=1  响铃   
	 * 3.tm.CALL_STATE_OFFHOOK=2  摘机  
	 * @return
	 */
	public int getCallState() {
		return tm.getCallState();
	}
	
	/**
	 * 电话方位
	 * @return
	 */
	public CellLocation getCellLocation() {
		return tm.getCellLocation();
	}
	
	/**
	 * 唯一的设备ID
	 * GSM手机的 IMEI 和 CDMA手机的 MEID
	 * @return
	 */
	public String getDeviceId() {
		return tm.getDeviceId();
	}
	
	/**
	 * SIM卡的序列号：   
	 * 需要权限：READ_PHONE_STATE 
	 * @return
	 */
	public String getSimSerialNumber() {
		return tm.getSimSerialNumber();
	}
	
	/**
	 * 唯一的用户ID
	 * 例如：IMSI(国际移动用户识别码) for a GSM phone.
	 * 需要权限：READ_PHONE_STATE
	 * @return
	 */
	public String getSubscriberId() {
		return tm.getSubscriberId();
	}
}
