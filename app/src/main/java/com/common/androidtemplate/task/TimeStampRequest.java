package com.common.androidtemplate.task;

import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.common.androidtemplate.bean.TimeStamp;
import com.common.androidtemplate.config.DebugConfig;
import com.common.androidtemplate.net.volley.ParamsRequest;
import com.common.androidtemplate.parser.TimeStampParser;


public class TimeStampRequest extends ParamsRequest<TimeStamp> {
	public TimeStampRequest (int method, String url, Map<String, String> params, 
			Listener<TimeStamp> listenre, ErrorListener errorListener) {
		super(method, url, params, listenre, errorListener);
	}
	
	@Override
	protected Response<TimeStamp> parseNetworkResponse(NetworkResponse response) {
		String resultStr = new String(response.data);
		DebugConfig.showLog("volleyresponse", resultStr);
							
		TimeStampParser parser = new TimeStampParser();
		return Response.success(parser.parse(resultStr), getCacheEntry());
	}
	
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = super.getHeaders(); 
		
		return headers;
	}
	
	
}
