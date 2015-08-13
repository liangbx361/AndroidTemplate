package com.common.androidtemplate.dialog;

import com.android.volley.Request;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.text.TextUtils;

public class CusProgressDialog {
	
	public static ProgressDialog getDialog(Context context, String title) {
		ProgressDialog pDialog = new ProgressDialog(context);
		if(!TextUtils.isEmpty(title)) {
			pDialog.setTitle(title);
		}
		pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pDialog.setMax(100);		
		return pDialog;
	}
	
	public static ProgressDialog getDialog(Context context, String title, 
			OnCancelListener listener) {
		ProgressDialog pDialog = new ProgressDialog(context);
		if(!TextUtils.isEmpty(title)) {
			pDialog.setTitle(title);
		}
		pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pDialog.setMax(100);
		pDialog.setCancelable(true);
		pDialog.setOnCancelListener(listener);
		return pDialog;
	}
	
	public static ProgressDialog getDialog(Context context, String title, final Request<?> request) {
		ProgressDialog pDialog = new ProgressDialog(context);
		if(!TextUtils.isEmpty(title)) {
			pDialog.setTitle(title);
		}
		pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pDialog.setMax(100);
		pDialog.setCancelable(true);
		pDialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				if(request != null) {
					request.cancel();
				}
			}
		});
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
}
