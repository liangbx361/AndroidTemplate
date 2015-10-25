package com.common.androidtemplate.module.function.system;

import android.app.ActivityManager;
import android.util.Log;

import com.common.androidtemplate.R;
import com.common.androidtemplate.activity.base.BaseActivity;
import com.common.androidtemplate.utils.MemoryUtil;

/**
 * Author liangbx
 * Date 2015/10/22.
 */
public class SystemInfoActivity extends BaseActivity {



    @Override
    public void getIntentData() {
        getMemeryInfo();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_system_info;
    }

    @Override
    public void initView() {

    }

    private void getMemeryInfo() {
//         String totalMem = MemoryUtil.getTotalMemory(this);
//        Log.d("SystemInfoActivity", totalMem);

        int memoryRate = MemoryUtil.getMemoryRate();
        Log.d("SystemInfoActivity", "当前内存使用率" + memoryRate);
        Log.d("SystemInfoActivity", "可用内存" + MemoryUtil.getAvailMemory(this));

        ActivityManager.MemoryInfo memoryInfo = MemoryUtil.getMemoryInfo(this);

//        MemoryUtil.getThisProcessMemeryInfo(this);

    }

}
