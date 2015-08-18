package com.common.androidtemplate.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.common.androidtemplate.R;
import com.common.androidtemplate.activity.base.BaseActivity;
import com.common.androidtemplate.bean.TimeStamp;
import com.common.androidtemplate.widget.dialog.CusLoadingDialog;
import com.common.androidtemplate.task.TimeStampRequest;


public class TimeStampActivity extends BaseActivity implements Listener<TimeStamp>, ErrorListener{
		
	private TimeStampRequest mTimeStampRequest;
	private TimeStamp mTimeStamp;
	private Dialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timestamp);
		
		getIntentData();
		initView();				
	}
			
	@Override
	public void getIntentData() {
		
	}
	
	@Override
	public void initView() {
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		//此处设置菜单
//		setDisplayHomeAsUpEnabled(true);
//		setDisplayShowHomeEnabled(false);
		
		requestTimeStamp(Method.GET, "", getTimeStampRequestParams(), this, this, "正在请求中，请稍候...");
//		setIndeterminateBarVisibility(true);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * 菜单点击处理
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {			
		return super.onOptionsItemSelected(item);
	}
		
	/**
	 * 获取请求参数
	 * @return
	 */
	private Map<String, String> getTimeStampRequestParams() {
		Map<String, String> params = new HashMap<String, String>();
				
		return params;
	}

	/**
	 * 执行任务请求
	 * @param method
	 * @param methodUrl
	 * @param params
	 * @param listenre
	 * @param errorListener
	 * @param message
	 */
	private void requestTimeStamp(int method, String methodUrl, Map<String, String> params,	 
			Listener<TimeStamp> listenre, ErrorListener errorListener, String message) {			
		if(mTimeStampRequest != null) {
			mTimeStampRequest.cancel();
		}	
		String url = "https://mbank.fjhxbank.com/pmobile/Timestamp.do";
		mTimeStampRequest = new TimeStampRequest(method, url, params, listenre, errorListener);
		startRequest(mTimeStampRequest);		
		
		if(!TextUtils.isEmpty(message)) {
			pDialog = CusLoadingDialog.showDialog(this, message, mTimeStampRequest);
		}
	}
	
	/**
	 * 网络请求错误处理
	 *
	 */
	@Override
	public void onErrorResponse(VolleyError error) {		
//		setIndeterminateBarVisibility(false);
		if(pDialog != null) pDialog.dismiss();
	}
	
	/**
	 * 请求完成，处理UI更新
	 */
	@Override
	public void onResponse(TimeStamp response) {
		mTimeStamp = response;
//		setIndeterminateBarVisibility(false);
		if(pDialog != null) pDialog.dismiss();
	}
}
