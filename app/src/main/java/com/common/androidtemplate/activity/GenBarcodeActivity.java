package com.common.androidtemplate.activity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.androidtemplate.R;
import com.common.androidtemplate.activity.base.BaseActivity;

import com.common.androidtemplate.barcode.GenerateHelper;
import com.google.zxing.WriterException;

public class GenBarcodeActivity extends BaseActivity {
	
	private ImageView barcodeIv;
	private TextView contentTv;
	private GenerateHelper genHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gen_barcode);
		
		getIntentData();
		initView();
	}
	
	@Override
	public void getIntentData() {

		
	}

	@Override
	public void initView() {
		barcodeIv = (ImageView) findViewById(R.id.barcode);
		contentTv = (TextView) findViewById(R.id.content);
		
		String content = "二维码测试";
		genHelper = new GenerateHelper(this);
		try {
			barcodeIv.setImageBitmap(genHelper.genBarCode("二维码测试"));
			contentTv.setText(content);
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		setDisplayHomeAsUpEnabled(true);
		
		return super.onCreateOptionsMenu(menu);
	}
}
