package com.common.androidtemplate.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.common.androidtemplate.activity.base.BaseBackActivity;
import com.common.androidtemplate.app.TEMApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogActivity extends BaseBackActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		// SLF4J
		Logger log = LoggerFactory.getLogger(LogActivity.class);
		log.info("hello word");
		int j= 0;
		try {
			int i = 3 / 0;						
		} catch (Exception e) {
			TEMApp.getInstance().getExLog().handleException(e);
		} finally {
			j++;
		}
		log.info(j+"");
	}

	@Override
	public void getIntentData() {
		
	}

	@Override
	public void initView() {
		
	}
	
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			
		}
		
	};
}
