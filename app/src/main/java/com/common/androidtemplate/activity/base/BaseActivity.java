package com.common.androidtemplate.activity.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View.OnClickListener;

import butterknife.ButterKnife;

/**
 * Activity基类
 * 
 * @author liangbx
 * 
 */
public abstract class BaseActivity extends BaseNetActivity {
	
	private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getIntentData();
		setContentView(getLayoutId());
		ButterKnife.bind(this);
		initView();
	}

	public void setContentview(int layoutId) {

	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	public void showDialog(String message) {
		pDialog = new ProgressDialog(this);
		pDialog.setIndeterminate(true);
		pDialog.setMessage(message);
		pDialog.setCancelable(false);
		pDialog.show();
	}
	
	public void dismissDialog () {
		pDialog.dismiss();
	}
	
	public void setViewOnClick(int id, OnClickListener listener) {
		findViewById(id).setOnClickListener(listener);
	}

	/**
	 * 获取Intent数据
	 */
	public abstract void getIntentData();

	/**
	 * 设置布局
	 * @return
	 */
	public abstract int getLayoutId();

	/**
	 * 初始化控件
	 */
	public abstract void initView();
}
