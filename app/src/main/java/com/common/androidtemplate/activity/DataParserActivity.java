package com.common.androidtemplate.activity;

import android.os.Bundle;
import android.view.Menu;

import com.common.tools.file.AssetsUtil;
import com.common.androidtemplate.activity.base.BaseActivity;
import com.common.androidtemplate.bean.EstateDetail;
import com.common.androidtemplate.config.DebugConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DataParserActivity extends BaseActivity{
	
	private EstateDetail eDetail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getIntentData();
		initView();
		parserData();
	}
	
	@Override
	public void getIntentData() {
		
	}

	@Override
	public int getLayoutId() {
		return 0;
	}

	@Override
	public void initView() {
		
	}
	
	private void parserData() {
		String dataStr = AssetsUtil.getStringFromFile(this, "data/json/EstateDetail.txt");
		Gson gson = new Gson();
		eDetail = gson.fromJson(dataStr, new TypeToken<EstateDetail>(){}.getType());
		DebugConfig.showLog("json", dataStr);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		setDisplayHomeAsUpEnabled(true);
//		setIndeterminateBarVisibility(false);
		return super.onCreateOptionsMenu(menu);
	}
}
