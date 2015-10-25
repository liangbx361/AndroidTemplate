package com.common.androidtemplate.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.text.format.Formatter;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Author liangbx
 * Date 2015/10/19.
 */
public class MemoryUtil {

    /**
     * 获取系统的可用内存
     * @param activity
     * @return
     */
    public static String getAvailMemory(Activity activity) {
        return Formatter.formatFileSize(activity, getMemoryInfo(activity).availMem);
    }

    /**
     * 获取系统的内存信息
     * @param activity
     * @return
     */
    public static ActivityManager.MemoryInfo getMemoryInfo(Activity activity) {
        ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList = am.getRunningAppProcesses();
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }

    /**
     * 获取当前进程占用的内存量
     * @param activity
     */
    public static String getProcessMemeryInfo(Activity activity) {
        ActivityManager activityManager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        int pid = android.os.Process.myPid();
        android.os.Debug.MemoryInfo[] memoryInfoArray = activityManager.getProcessMemoryInfo(new int[] {pid});

        return Formatter.formatFileSize(activity, memoryInfoArray[0].getTotalPss() * 1024);
    }

    /**
     * 通过反射方式获取CPU的内存信息, 并计算当前内存的使用率
     * @return
     */
    public static int getMemoryRate() {
        Method _readProclines = null;
        try {
            Class procClass;
            procClass = Class.forName("android.os.Process");
            Class parameterTypes[]= new Class[] {String.class, String[].class, long[].class };
            _readProclines = procClass.getMethod("readProcLines", parameterTypes);
            Object arglist[] = new Object[3];
            final String[] mMemInfoFields = new String[] {"MemTotal:", "MemFree:", "Buffers:", "Cached:"};
            long[] mMemInfoSizes = new long[mMemInfoFields.length];
            mMemInfoSizes[0] = 30;
            mMemInfoSizes[1] = -30;
            arglist[0] = new String("/proc/meminfo");
            arglist[1] = mMemInfoFields;
            arglist[2] = mMemInfoSizes;
            if(_readProclines!=null){
                _readProclines.invoke(null, arglist);
                for (int i=0; i < mMemInfoSizes.length; i++) {
                    Log.d("GetFreeMem", mMemInfoFields[i]+" : "+mMemInfoSizes[i]/1024);
                }
                return (int) (100 * mMemInfoSizes[1] / mMemInfoSizes[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
