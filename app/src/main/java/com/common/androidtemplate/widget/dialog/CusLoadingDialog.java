package com.common.androidtemplate.widget.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;

import com.android.volley.Request;

/**
 * 
 * @描述：自定义加载用的对话框,无进度条显示
 * @作者：liang bao xian
 * @时间：2015年4月1日 上午11:06:27
 */
public class CusLoadingDialog {
	
	/**
	 * 
	 * @描述: 创建一个加载条
	 * @param context
	 * @param messageId
	 * @param cancelable
	 * @return
	 */
	public static ProgressDialog getDialog(Context context, int messageId, boolean cancelable) {
		return getDialog(context, context.getResources().getString(messageId), cancelable);
	}
	
	/**
	 * 
	 * @描述: 创建一个加载条
	 * @param context
	 * @param message
	 * @param cancelable
	 * @return
	 */
	public static ProgressDialog getDialog(Context context, String message, boolean cancelable) {		
		ProgressDialog pDialog = new ProgressDialog(context);		
		pDialog.setIndeterminate(true);
		pDialog.setMessage(message);
		pDialog.setCancelable(cancelable);
		pDialog.setCanceledOnTouchOutside(cancelable);
		
		return pDialog;
	}
	
	/**
	 * 
	 * @描述: 创建并显示一个加载条
	 * @param context
	 * @param message
	 * @param cancelable
	 * @return
	 */
	public static ProgressDialog showDialog(Context context, String message, boolean cancelable) {
		ProgressDialog pDialog = getDialog(context, message, cancelable);
		pDialog.show();
		return pDialog;
	}
	
	/**
	 * 
	 * @描述: 创建一个加载条
	 * @param context
	 * @param messageId
	 * @param request
	 * @return
	 */
	public static ProgressDialog getDialog(Context context, int messageId, final Request<?> request) {
		return getDialog(context, context.getResources().getString(messageId), request);
	}
	
	/**
	 * 
	 * @描述: 创建一个加载条
	 * @param context
	 * @param message
	 * @param request
	 * @return
	 */
	public static ProgressDialog getDialog(Context context, String message, final Request<?> request) {
		ProgressDialog pDialog = new ProgressDialog(context);		
		pDialog.setIndeterminate(true);
		pDialog.setMessage(message);
		pDialog.setCancelable(true);
		pDialog.setCanceledOnTouchOutside(true);
		pDialog.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				if(request != null) {
					request.cancel();
				}
			}
		});
		
		return pDialog;
	}
	
	/**
	 * 
	 * @描述: 创建并显示一个加载条
	 * @param context
	 * @param message
	 * @param request
	 * @return
	 */
	public static ProgressDialog showDialog(Context context, String message, final Request<?> request) {
		ProgressDialog pDialog = getDialog(context, message, request);
		pDialog.show();
		return pDialog;
	}
}
