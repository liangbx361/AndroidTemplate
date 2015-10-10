package com.common.androidtemplate.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.common.androidtemplate.R;
import com.common.androidtemplate.activity.base.BaseBackActivity;
import com.common.tools.pinyin.PinYin;

public class HzToPinyinActivity extends BaseBackActivity {
	
	private EditText hzEt;
	private TextView pyTv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void getIntentData() {
		
	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_hz_to_pinyin;
	}

	@Override
	public void initView() {
		hzEt = (EditText) findViewById(R.id.hz);
		pyTv = (TextView) findViewById(R.id.pinyin);
		
		hzEt.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				String hzStr = arg0.toString();
				String pyStr = PinYin.getPinYin(hzStr, PinYin.TYPE_FIRST_UP_CASE);
	            pyTv.setText(pyStr);
			}
		});
	}
	
	
}
