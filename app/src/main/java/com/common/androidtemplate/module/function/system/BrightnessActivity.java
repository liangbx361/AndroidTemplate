package com.common.androidtemplate.module.function.system;

import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.common.androidtemplate.R;
import com.common.androidtemplate.activity.base.BaseActivity;
import com.common.tools.system.settings.Brightness;

import butterknife.Bind;

/**
 * Author liangbx
 * Date 2015/10/18.
 */
public class BrightnessActivity extends BaseActivity {

    @Bind(R.id.btn_switch_mode)
    Button mBtnSwitchMode;
    @Bind(R.id.brightness_mode)
    TextView mTvBrightnessMode;
    @Bind(R.id.brightness_value)
    TextView mTvBrightnessValue;

    @Override
    public void getIntentData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_brightness;
    }

    @Override
    public void initView() {
        mBtnSwitchMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMode();
            }
        });

        float brightness = Settings.System.getFloat(getContentResolver(),
                "screen_auto_brightness_adj", 0);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        mTvBrightnessValue.setText("亮度值:" + Brightness.INSTANCE.getBrightnessValue(this) + "|" + brightness + "|" + layoutParams.screenBrightness);
    }

    private void switchMode() {
        int mode = Brightness.INSTANCE.switchMode(BrightnessActivity.this);
        if(mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
            mTvBrightnessMode.setText("自动亮度");
        } else {
            mTvBrightnessMode.setText("手动亮度");
        }
        float brightness = Settings.System.getFloat(getContentResolver(),
                "screen_auto_brightness_adj", 0);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        mTvBrightnessValue.setText("亮度值:" + Brightness.INSTANCE.getBrightnessValue(this) + "|" + brightness + "|" + layoutParams.screenBrightness);
    }
}
