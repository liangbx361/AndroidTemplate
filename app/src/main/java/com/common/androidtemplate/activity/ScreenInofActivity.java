package com.common.androidtemplate.activity;

import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.common.androidtemplate.R;
import com.common.androidtemplate.activity.base.BaseBackActivity;


/**
 *
 * @描述：获取屏幕信息
 * @作者：liang bao xian
 * @时间：2015年1月22日 上午10:44:50
 */
public class ScreenInofActivity extends BaseBackActivity {
	
	private TextView resolutionRatioTv;
	private TextView statusbarHeightTv;
	private TextView dpiTv;

	@Override
	public void getIntentData() {
		
	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_screen_info;
	}

	@Override
	public void initView() {
		resolutionRatioTv = (TextView) findViewById(R.id.resolution_ratio);
		statusbarHeightTv = (TextView) findViewById(R.id.statusbar_height);
		dpiTv = (TextView) findViewById(R.id.dpi);
		
		//分辨率
		DisplayMetrics dm = getResources().getDisplayMetrics();
		String info = resolutionRatioTv.getText().toString() + dm.widthPixels + " X " + dm.heightPixels;
		resolutionRatioTv.setText(info);
		
		//DPI
		info = dpiTv.getText().toString() + dm.densityDpi + "( " + dm.density + " )";
		dpiTv.setText(info);
		
		mHandler.sendEmptyMessage(0);
				
	}
	
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			//状态栏
			Rect frame = new Rect();  
			getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);  
			int statusBarHeight = frame.top;  
			String info = statusbarHeightTv.getText().toString() + statusBarHeight;
			statusbarHeightTv.setText(info);
		}
		
	};
}
