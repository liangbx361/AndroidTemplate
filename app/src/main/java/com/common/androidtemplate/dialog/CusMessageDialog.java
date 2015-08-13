package com.common.androidtemplate.dialog;

import com.common.androidtemplate.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * 
 * @描述：确认类型对话框
 * @作者：liang bao xian
 * @时间：2014年8月1日 下午3:18:17
 */
public class CusMessageDialog {
	
	/**
	 * 带确认和取消两个按钮的对话框
	 * @param context
	 * @param titleId 为-1时不显示标题
	 * @param messageId
	 * @param confirmListener
	 * @return
	 */
	public static Dialog getDialog(Context context, int titleId, int messageId, 
			DialogInterface.OnClickListener confirmListener) {
		String title = null;
		if(titleId != -1) {
			title = context.getString(titleId);
		}
		String message = context.getString(messageId);
		return getDialog(context, title, message, confirmListener);
	}

	/**
	 * 带确认和取消两个按钮的对话框
	 * @param context
	 * @param title 为null时不显示标题
	 * @param message
	 * @param confirmListener
	 * @return
	 */
	public static Dialog getDialog(Context context, String title, String message, 
			DialogInterface.OnClickListener confirmListener) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		if(title != null) {
			dialogBuilder.setTitle(title);
		}
		
		return dialogBuilder.setMessage(message)
        .setPositiveButton(R.string.dialog_confirm, confirmListener)
        .setNegativeButton(R.string.dialog_cancle, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	dialog.dismiss();                
            }
        })
        .create();
	}
	
	/**
	 * 带确认和取消两个按钮的对话框
	 * @param context
	 * @param titleId 为-1时不显示标题
	 * @param messageId
	 * @param confirmListener
	 * @return
	 */
	public static Dialog getDialog(Context context, int titleId, int messageId, 
			int confirmNameId, int cancleNameId, 
			DialogInterface.OnClickListener confirmListener) {
		String title = null;
		if(titleId != -1) {
			title = context.getString(titleId);
		}
		String message = context.getString(messageId);
		String confirmName = context.getString(confirmNameId);
		String cancleName = context.getString(cancleNameId);
		
		return getDialog(context, title, message, confirmName, cancleName, confirmListener);
	}
	
	/**
	 * 
	 * @描述: 支持自定义按钮名称的对话框
	 * @param context
	 * @param title
	 * @param message
	 * @param confirmName
	 * @param cancleName
	 * @param confirmListener
	 * @return
	 */
	public static Dialog getDialog(Context context, String title, String message,
			String confirmName, String cancleName, DialogInterface.OnClickListener confirmListener) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		if(title != null) {
			dialogBuilder.setTitle(title);
		}
		dialogBuilder.setMessage(message);
		dialogBuilder.setPositiveButton(confirmName, confirmListener);
		dialogBuilder.setNegativeButton(cancleName, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		return dialogBuilder.create();
	}
	
	/**
	 * 
	 * @描述: 只有确认按钮的对话框
	 * @param context
	 * @param titleId
	 * @param messageId
	 * @return
	 */
	public static Dialog getConfirmDialog(Context context, int titleId, int messageId) {		
		return getConfirmDialog(context, titleId, messageId, null);
	}
	
	/**
	 * 
	 * @描述: 只有确认按钮的对话框
	 * @param context
	 * @param titleId
	 * @param messageId
	 * @param confirmListener
	 * @return
	 */
	public static Dialog getConfirmDialog(Context context, int titleId, int messageId, 
			DialogInterface.OnClickListener confirmListener) {
		String title = null;
		if(titleId != -1) {
			title = context.getString(titleId);			
		}		
		String message = context.getString(messageId);
		return getConfirmDialog(context, title, message, confirmListener);
	}

	/**
	 * @描述: 只有确认按钮的对话框
	 * @param context
	 * @param title
	 * @param message
	 * @return
	 */
	public static Dialog getConfirmDialog(Context context, String title, String message) {
		return getConfirmDialog(context, title, message, null);
	}

	/**
	 * @描述: 只有确认按钮的对话框
	 * @param context
	 * @param title
	 * @param message
	 * @param confirmListener
	 * @return
	 */
	public static Dialog getConfirmDialog(Context context, String title, String message, 
			DialogInterface.OnClickListener confirmListener) {
		return getConfirmDialog(context, title, message, null, confirmListener);
	}
	
	/**
	 * 
	 * @描述: 只有确认按钮的对话框
	 * @param context
	 * @param titleId
	 * @param messageId
	 * @return
	 */
	public Dialog getConfirmDialog(Context context, int titleId, int messageId, 
			int btnNameId, DialogInterface.OnClickListener confirmListener) {
		String title = null;
		if(titleId == -1) {
			title = context.getString(titleId);
		}
		String message = context.getString(messageId);
		String btnName = context.getString(btnNameId);
		return getConfirmDialog(context, title, message, btnName, confirmListener);
	}

	/**
	 * @描述: 只有确认按钮的对话框
	 * @param context
	 * @param title
	 * @param message
	 * @param btnName
	 * @param confirmListener
	 * @return
	 */
	public static Dialog getConfirmDialog(Context context, String title, String message, 
			String btnName, DialogInterface.OnClickListener confirmListener) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		if(title != null) {
			dialogBuilder.setTitle(title);
		}		
		dialogBuilder.setMessage(message);
		if(btnName == null) {
			btnName = context.getString(R.string.dialog_confirm);
		}
		if(confirmListener == null) {
			dialogBuilder.setPositiveButton(btnName, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
		} else {
			dialogBuilder.setPositiveButton(btnName, confirmListener);
		}
        return dialogBuilder.create();
	}
}
