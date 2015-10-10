package com.common.androidtemplate.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.common.androidtemplate.R;
import com.common.androidtemplate.activity.base.BaseBackActivity;
import com.common.androidtemplate.adapter.PhotoAdapter;
import com.common.androidtemplate.config.AcResultCode;
import com.common.tools.file.FileDirUtil;
import com.common.tools.media.CameraHelper;
import com.common.tools.widget.ToastHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @描述：采集相片，摄像机拍摄或者相册拾取
 * @作者：liang bao xian
 * @时间：2014年8月21日 上午9:56:52
 */
public class PickPhotoActivity extends BaseBackActivity implements OnClickListener{
	private static final int CROP_WIDTH = 400;
	private static final int CROP_HEIGHT = 400;
	
	private List<File> fileList = new ArrayList<File>();
	private File photoFile;
	private Uri photoUri;
	
	private CameraHelper cameraHelper;
	
	private Button pickCameraBtn;
	private Button pickAlbumBtn;
	private Button pickCameraWithCropBtn;
	private Button pickAlbumBWithCropBtn;
	
	private GridView photosGv;
	private PhotoAdapter photoAdapter;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		cameraHelper = new CameraHelper(this);		
	}
	
	@Override
	public void getIntentData() {
		
	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_pick_photo;
	}

	@Override
	public void initView() {
		pickCameraBtn = (Button) findViewById(R.id.camera);
		pickCameraBtn.setOnClickListener(this);
		pickAlbumBtn = (Button) findViewById(R.id.album);
		pickAlbumBtn.setOnClickListener(this);
		pickCameraWithCropBtn = (Button) findViewById(R.id.camera_crop);
		pickCameraWithCropBtn.setOnClickListener(this);
		pickAlbumBWithCropBtn = (Button) findViewById(R.id.album_crop);
		pickAlbumBWithCropBtn.setOnClickListener(this);
		
		photosGv = (GridView) findViewById(R.id.photos);
		photoAdapter = new PhotoAdapter(this, fileList);
		photosGv.setAdapter(photoAdapter);
	}
	
	@Override
	public void onClick(View view) {
		super.onClick(view);
		
		switch(view.getId()) {
		case R.id.camera:		
			pickFromCamera();
			break;
			
		case R.id.album:
			pickFromAlbum();
			break;
			
		case R.id.camera_crop:
			pickFromCameraWhithCrop();
			break;
			
		case R.id.album_crop:
			pickFromAlbumWithCrop();
			break;
		}
	}
	
	private void pickFromCamera() {
		pickFromCamera(false);
	}
	
	private void pickFromCameraWhithCrop() {
		pickFromCamera(true);
	}
	
	/**
	 * 
	 * @描述:拍照取图
	 */
	private void pickFromCamera(boolean crop) {
		photoFile = cameraHelper.getOutputMediaFile(this, CameraHelper.MEDIA_TYPE_IMAGE);
		if(photoFile != null) {
			photoUri = Uri.fromFile(photoFile);
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
			intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
			if(crop) {
				startActivityForResult(intent, AcResultCode.REQUEST_CODE_CAMERA_CROP_IMAGE);
			} else {
				startActivityForResult(intent, AcResultCode.REQUEST_CODE_CAMERA_IMAGE);
			}
		} else {
			ToastHelper.showToastInBottom(this, "无法创建图片文件，请检测存储器是否异常");
		}
	}	
	
	/**
	 * 
	 * @描述:相册取图
	 */
	private void pickFromAlbum() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		startActivityForResult(intent, AcResultCode.REQUEST_CODE_ALBUM_IMAGE);
	}
	
	/**
	 * 
	 * @描述:相册取图并剪裁  
	 */
	private void pickFromAlbumWithCrop() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		startActivityForResult(intent, AcResultCode.REQUEST_CODE_ALBUM_CROP_IMAGE);
	}
	
	private void startCrop(Uri photoUri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(photoUri, "image/*");
		intent.putExtra("crop", "true");//可裁剪
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", CROP_WIDTH);
		intent.putExtra("outputY", CROP_HEIGHT);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
		intent.putExtra("return-data", false);//若为false则表示不返回数据
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); 
		startActivityForResult(intent, AcResultCode.REQUEST_CODE_IMAGE_CROP);
	}
	
	@TargetApi(Build.VERSION_CODES.KITKAT) 
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
		case AcResultCode.REQUEST_CODE_CAMERA_IMAGE:
			if(resultCode != 0 && photoFile.exists()) {
				fileList.add(photoFile);	
				photoAdapter.notifyDataSetChanged();
			} else {
				ToastHelper.showToastInBottom(this, "拍照取图失败");
			}
			break;
			
		case AcResultCode.REQUEST_CODE_ALBUM_IMAGE:
			if(resultCode != 0 && data != null) {
				String photoPath = FileDirUtil.getPathFromUri(this, data.getData());
				if(!TextUtils.isEmpty(photoPath)) {
					photoFile = new File(photoPath);
					fileList.add(photoFile);
					photoAdapter.notifyDataSetChanged();
				} else {
					ToastHelper.showToastInBottom(this, "相册选图失败");
				}
			} else {
				ToastHelper.showToastInBottom(this, "相册选图失败");
			}
			break;
				
		case AcResultCode.REQUEST_CODE_CAMERA_CROP_IMAGE:
			if(resultCode != 0 && photoFile.exists()) {
				photoUri = Uri.fromFile(photoFile);
				startCrop(photoUri);
			} else {
				ToastHelper.showToastInBottom(this, "拍照取图失败");
			}
			break;		
	    
		case AcResultCode.REQUEST_CODE_ALBUM_CROP_IMAGE:
			if(resultCode != 0 && data != null) {
				String photoPath = FileDirUtil.getPathFromUri(this, data.getData());
				if(!TextUtils.isEmpty(photoPath)) {
					photoFile = new File(photoPath);
					photoUri = Uri.fromFile(photoFile);
					startCrop(photoUri);
				} else {
					ToastHelper.showToastInBottom(this, "相册选图失败");
				}
			} else {
				ToastHelper.showToastInBottom(this, "相册选图失败");
			}
			break;
				
		case AcResultCode.REQUEST_CODE_IMAGE_CROP:
			if(resultCode != 0 && photoFile.exists()) {
				fileList.add(photoFile);
				photoAdapter.notifyDataSetChanged();
			} else {
				ToastHelper.showToastInBottom(this, "剪裁取消");
			}
			break;
		}
	}
	
	/**
	 * 处理在拍照时屏幕翻转的问题
	 */
	public void onConfigurationChanged(Configuration newConfig) {  

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {   
            Configuration o = newConfig;  
            o.orientation = Configuration.ORIENTATION_PORTRAIT;  
            newConfig.setTo(o);  
        }   
        super.onConfigurationChanged(newConfig);  
    }
	
	@Override
	protected void onDestroy() {
		photoAdapter.recycleBmp();
		super.onDestroy();
	}
}
