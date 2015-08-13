package com.common.tools.media;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.net.Uri;

import com.common.tools.file.FileDirUtil;
import com.common.tools.widget.ToastHelper;

public class CameraHelper {
	
	public static final int MEDIA_TYPE_IMAGE = 0;
	public static final int MEDIA_TYPE_VIDEO = 1;
	
	private Context mContext;
	
	public CameraHelper (Context context) {
		mContext = context;
	}		
	
	/**
	 * 创建图片/视频文件,用于保存图片,并以Uri返回
	 * @param type
	 * @return
	 */
	public Uri getOutputMediaFileUri(Context context, int type) {  
        return Uri.fromFile(getOutputMediaFile(context, type));  
    } 
	
	/**
	 * 创建图片/视频文件,用于保存图片
	 * @param type
	 * @return
	 */
    public File getOutputMediaFile(Context context, int type) {      	
        // To be safe, you should check that the SDCard is mounted  
        // using Environment.getExternalStorageState() before doing this.      	
    	String cachePath = FileDirUtil.getDiskCacheDir(mContext, "imgCache").getAbsolutePath();
        File mediaStorageDir = new File(cachePath); 
    	        
        // File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(  
        // Environment.DIRECTORY_PICTURES), "tfm");  
        // This location works best if you want the created images to be shared  
        // between applications and persist after your app has been uninstalled.  
        // Create the storage directory if it does not exist  
        if (!mediaStorageDir.exists()) {  
            if (!mediaStorageDir.mkdirs()) {  
            	ToastHelper.showToastInBottom(context, "创建目录失败：" + cachePath);
                return null;  
            }  
        }  
        
        // Create a media file name  
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());  
        File mediaFile;  
        if (type == MEDIA_TYPE_IMAGE) {  
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");  
        } else if (type == MEDIA_TYPE_VIDEO) {  
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");  
        } else {  
            return null;  
        }  
        return mediaFile;  
    }
}
