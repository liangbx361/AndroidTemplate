package com.common.tools.util;

import android.content.Context;   

/**
 * 
 * @描述：密度和像素点转换工具
 * @作者：liang bao xian
 * @时间：2014年8月1日 上午11:57:20
 */
public class DensityUtil {   
   
    /**  
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)  
     */   
    public static int dip2px(Context context, float dpValue) {   
    	try{
    		final float scale = context.getResources().getDisplayMetrics().density;   
    		return (int) (dpValue * scale + 0.5f);
    	} catch (Exception e) {
    		return -1;
    	}
    }   
   
    /**  
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp  
     */   
    public static int px2dip(Context context, float pxValue) {   
        final float scale = context.getResources().getDisplayMetrics().density;   
        return (int) (pxValue / scale + 0.5f);   
    }   
    
    /**
     * 计算出每个列表显示占屏幕的宽度
     * @param context
     * @param disNum
     * @return
     */
    public static int getItemWidth(Context context, int disNum) {
    	return context.getResources().getDisplayMetrics().widthPixels / disNum;
    }
} 
