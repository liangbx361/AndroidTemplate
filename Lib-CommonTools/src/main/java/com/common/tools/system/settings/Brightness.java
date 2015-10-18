package com.common.tools.system.settings;

import android.app.Activity;
import android.provider.Settings;
import android.view.WindowManager;

/**
 * Author liangbx
 * Date 2015/10/18.
 */
public enum  Brightness {
    INSTANCE;

    private static final int BRIGHTNESS_MAX_VALUE = 255;
    private static final int BRIGHTNESS_MIN_VALUE = 5;

    private int defaultBrightnessMode;
    private int defaultBrightnessValue;

    public void setBrightness( Activity activity, int brightness ){
        if (brightness < BRIGHTNESS_MIN_VALUE) {
            brightness = BRIGHTNESS_MIN_VALUE;
        }
        if (brightness > BRIGHTNESS_MAX_VALUE) {
            brightness = BRIGHTNESS_MAX_VALUE;
        }
        Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = brightness/(float)BRIGHTNESS_MAX_VALUE;
        activity.getWindow().setAttributes(lp);
    }

    public int getBrightness(Activity activity){
        return Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, -1);
    }

    public void setBrightnessMode(Activity activity, int mode) {
        Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, mode);
    }

    public int getBrightnessMode(Activity activity) {
        return Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, -1);
    }

    public void setBrightnessValue(Activity activity, int value) {
        Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, value);
    }

    public int getBrightnessValue(Activity activity) {
        return Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, -1);
    }

    public int getMaxValue() {
        return BRIGHTNESS_MAX_VALUE;
    }

    public int getMinValue() {
        return BRIGHTNESS_MIN_VALUE;
    }

    /**
     * 备份亮度设置,并将当前亮度模式强制设置为手动
     */
    public void backupBrightnessSettings(Activity activity) {
        defaultBrightnessMode = getBrightnessMode(activity);
        defaultBrightnessValue = getBrightnessValue(activity);
    }

    /**
     * 恢复亮度设置
     */
    public void restoreBrightnessSettings(Activity activity) {
        if(defaultBrightnessMode != -1) {
            setBrightnessMode(activity, defaultBrightnessMode);
            setBrightnessValue(activity, defaultBrightnessValue);
        }
    }

    public int switchMode(Activity activity) {
        if(Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC == getBrightnessMode(activity)) {
            setBrightnessMode(activity, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            return Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL;
        } else {
            setBrightnessMode(activity, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
            return Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        }
    }
}

