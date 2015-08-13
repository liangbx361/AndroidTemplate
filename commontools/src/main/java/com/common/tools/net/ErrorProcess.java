package com.common.tools.net;

import com.common.tools.widget.ToastHelper;

import android.content.Context;

public class ErrorProcess {
	
	public static void showError(Context context, int errorCode) {
		String errorMessage = "";
		switch (errorCode) {
		case ResultCode.CONTENT_SERVER_ERROR:
			errorMessage = ResultCode.CONTENT_SERVER_ERROR_DESC;
			break;
			
		case ResultCode.DATA_PARSER_FAIL:
			errorMessage = "数据解析失败";
			break;
			
		case ResultCode.NO_NETWORK:
			errorMessage = ResultCode.NO_NETWORK_DESC;
			break;
			
		default:
			errorMessage = "其它错误";
			break;
		}
		
		ToastHelper.showToastInBottom(context, errorMessage);
	}
}
