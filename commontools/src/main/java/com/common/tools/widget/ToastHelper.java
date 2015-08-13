package com.common.tools.widget;

import com.common.tools.util.DensityUtil;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastHelper {
	
	private static int DEFAULT_Y_OFFER = 80;
	
	public static void showToastInBottom(Context context, int id) {
		showToastInBottom(context, context.getResources().getString(id));
	}
	
	public static void showToastInBottom(Context context, String toast) {
		if(context != null && !toast.equals("")) {
			int yOffer = DensityUtil.dip2px(context, DEFAULT_Y_OFFER);
			if(yOffer != -1) {
				Toast mToast = Toast.makeText(context, toast, Toast.LENGTH_SHORT);
				mToast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, yOffer);
				mToast.show();
			}
		}
	}
	
	/**
	 * 设置Y轴偏移量
	 * @param offer
	 */
	public static void setYOffer(int offer) {
		DEFAULT_Y_OFFER = offer;
	}
}
