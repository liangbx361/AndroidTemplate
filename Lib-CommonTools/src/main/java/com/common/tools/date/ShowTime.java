package com.common.tools.date;

import java.util.Date;

/**
 * 
 * @描述：上次的时间与当前时间比较，获取精确的时间提示。（如多少天、多少小时、多少分钟、刚刚)
 * @作者：liang bao xian
 * @时间：2014年8月1日 下午2:38:56
 */
public class ShowTime {
	
	/**
	 * @param lastTime
	 * @param dateFormat
	 * @return
	 */
	public String getShowTime(String lastTime, String dateFormat) {
		Date nowDate = new Date();	
		Date lastDate = FormatDateTime.string2Date(lastTime, dateFormat);
		
		long subTime = nowDate.getTime() - lastDate.getTime();
		long day=subTime/(24*60*60*1000);
		if(day > 0) {						
			return day + "天"; 
		} 
		
		long hour=(subTime/(60*60*1000)-day*24);
		if(hour > 0) {
			return hour + "小时";
		}	
		
		long min=((subTime/(60*1000))-day*24*60-hour*60);
		if(min > 0) {
			return min + "分";
		}
		
		return "刚刚";				
	}
}
