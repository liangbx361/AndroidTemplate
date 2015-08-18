package com.common.androidtemplate.widget.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

public class PhoneTypeDialog {
	
	public Dialog getPhoneTypeDialog(Context context, int titleId, String[] items, 
			DialogInterface.OnClickListener listener) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder.setTitle(titleId);
		dialogBuilder.setSingleChoiceItems(items, -1, listener);
		Dialog dialog = dialogBuilder.create();
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		return dialog;
	}
}
