package com.common.androidtemplate.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.volley.Request.DownloadProgressListener;
import com.android.volley.toolbox.NetworkImageView;
import com.common.androidtemplate.R;
import com.common.androidtemplate.activity.base.BaseBackActivity;
import com.common.androidtemplate.app.TEMApp;
import com.common.androidtemplate.config.DebugConfig;
import com.common.tools.widget.RoundProgressBar;

public class ImageProgressActivity extends BaseBackActivity implements DownloadProgressListener{
	
	public static final String imgUrl = "http://www.ewang.com/Files/adminfiles/linlu/2010041214470168920.jpg";
	
	private NetworkImageView imgIv;
	private RoundProgressBar progressBar;
	private Button startBtn;

	@Override
	public void getIntentData() {
		
	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_image_progress;
	}

	@Override
	public void initView() {
		imgIv = (NetworkImageView) findViewById(R.id.img);
		imgIv.setDefaultImageResId(R.drawable.base_article_bigimage);
		progressBar = (RoundProgressBar) findViewById(R.id.progress);		
		startBtn = (Button) findViewById(R.id.button1);
		startBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imgIv.setImageUrl(imgUrl, TEMApp.getInstance().getImageLoader(), 
						ImageProgressActivity.this);
			}
		});
	}

	@Override
	public void onLoad(int progress) {
		progressBar.setProgress(progress);
		DebugConfig.showLog("volley_download_progress", progress + "%");
	}
}
