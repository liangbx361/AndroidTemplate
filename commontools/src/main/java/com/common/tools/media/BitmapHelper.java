package com.common.tools.media;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.os.Debug;
import android.util.Log;

import com.common.tools.file.FileDirUtil;
import com.common.tools.widget.ToastHelper;

/**
 * 
 * @描述：位图帮助类
 * @作者：liang bao xian
 * @时间：2014年8月21日 上午9:04:08
 */
public class BitmapHelper {
	
	/**
	 * 
	 * @描述: 读取图片属性中图片被旋转的角度
	 * @param path 图片绝对路径
	 * @return degree 图片被旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int angle = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				angle = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				angle = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				angle = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return angle;
	}
	
	/**
	 * 
	 * @描述: 对位图进行旋转、缩放
	 * @param angle 
	 * @param scale
	 * @param bitmap
	 * @return
	 */
   public static Bitmap resizeBitmap(int angle, float scale, Bitmap bitmap) {  
	   if(angle == 0 && scale == 1.0f) {
		   return bitmap;
	   }
	   
       //旋转图片 动作  
       Matrix matrix = new Matrix(); 
       matrix.postRotate(angle);  
       matrix.postScale(scale, scale);

       // 创建新的图片  
       Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,  
               bitmap.getWidth(), bitmap.getHeight(), matrix, true);  
       return resizedBitmap;  
   }	
   
   /**
    * 
    * @描述: 根据指定输出大小选取采样率取位图，输出的图片大小为 原始尺寸 / 采样率
    * （备注：仅支持缩小图片)
    * @param picPath
    * @param outWidth
    * @param outHeight
    * @param rgbType
    * @return
    */
   public static Bitmap getReSizeBmp(String picPath, int outWidth, 
		   int outHeight, Bitmap.Config rgbType) {
	   Bitmap bmp = null;	   
	   BitmapFactory.Options options = new BitmapFactory.Options();
	   options.inJustDecodeBounds = true;
	   BitmapFactory.decodeFile(picPath, options);
	   
	   options.inSampleSize = computeSampleSize(options, outWidth, outHeight);
	   options.inJustDecodeBounds = false;
	   options.inPreferredConfig = rgbType;	   
	   bmp = BitmapFactory.decodeFile(picPath, options);
	   
	   int angle = BitmapHelper.readPictureDegree(picPath);
	   //小图需要放大处理
	   if(options.inSampleSize > 1) {
		   bmp = resizeBitmap(angle, 1.0f, bmp);
	   } else {
		   float scaleX = (float)outWidth / options.outWidth;
		   float scaleY = (float)outHeight / options.outHeight;
		   float scale = scaleX >= scaleY ? scaleX : scaleY;
		   bmp = resizeBitmap(angle, scale, bmp);
	   }
	   
	   return bmp;
   }
   
   /**
    * @描述: 获取压缩后的图片并保存在SD卡中
    * @param context
    * @param picPath
    * @param outWidth
    * @return
    */
   public static File getScaleBitmapFile(Context context, String picPath, int outWidth) {	   	   
	   BitmapFactory.Options options = new BitmapFactory.Options();
	   options.inJustDecodeBounds = true;
	   BitmapFactory.decodeFile(picPath, options);
	   
	   Log.d("hx_bitmap", picPath + "/" + options.outWidth + "," + options.outHeight);
	   
	   int width = options.outWidth < outWidth ? options.outWidth : outWidth;	   
	   int height = options.outHeight  * width /  options.outWidth;
	   float scale = (float)width / options.outWidth;
	   options.inSampleSize = (int) scale;
	   options.outWidth = width;
	   options.outHeight = height;
	   options.inJustDecodeBounds = false;
	   options.inPreferredConfig = Bitmap.Config.RGB_565;	   
	   Bitmap bitmap = BitmapFactory.decodeFile(picPath, options);
	   	   
	   int angle = BitmapHelper.readPictureDegree(picPath);	
	   Bitmap roateBmp = resizeBitmap(angle, scale, bitmap);
	   
	   return saveBitmap(context, roateBmp);
   }
   
   /**
    * 保存位图
    * @param context
    * @param bm
    * @return
    */
   public static File saveBitmap(Context context, Bitmap bm) {	       
       String cachePath = FileDirUtil.getDiskCacheDir(context, "imgCache").getAbsolutePath();
       File mediaStorageDir = new File(cachePath);  
       
       if (!mediaStorageDir.exists()) {  
           if (!mediaStorageDir.mkdirs()) {  
        	   ToastHelper.showToastInBottom(context, "创建目录失败：" + cachePath);
               return null;  
           }  
       }  
       
       String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.CHINA).format(new Date()); 
       File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "tmp_img" + timeStamp + ".jpg");
       try {
    	   FileOutputStream out = new FileOutputStream(mediaFile);
    	   bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
    	   out.flush();
    	   out.close();
    	   
    	   return mediaFile;    	   
       } catch (FileNotFoundException e) {    
    	   e.printStackTrace();
    	   ToastHelper.showToastInBottom(context, "文件未找到");
       } catch (IOException e) {
    	   ToastHelper.showToastInBottom(context, "IO异常");
    	   e.printStackTrace();
       }
       
       return null;
   }
   
   public static Bitmap toRoundCorner(Bitmap bitmap, float radius) {  
       Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),  
               bitmap.getHeight(), Bitmap.Config.ARGB_8888);  
       Canvas canvas = new Canvas(output);  
 
       final Paint paint = new Paint();  
       final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());  
       final RectF rectF = new RectF(rect);  
 
       paint.setAntiAlias(true);  
       canvas.drawARGB(0, 0, 0, 0);  
       canvas.drawRoundRect(rectF, radius, radius, paint);  
       paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN)); 
       canvas.drawBitmap(bitmap, rect, rect, paint);   
 
       return output;  
   }
   
   /**
    * 
    * @描述: 计算横向和纵向的采样率，取出最小的值
    * @param options
    * @param outWidth
    * @param outHeight
    * @return
    */
   public static int computeSampleSize(BitmapFactory.Options options, int outWidth, int outHeight) {
	   int scaleX = options.outWidth / outWidth;
	   int scaleY = options.outHeight / outHeight;
	   
	   return scaleX < scaleY ? scaleX : scaleY;			    
   }
   
   /**
    * 
    * @描述: 计算出合适的SampleSize 
    * @param options
    * @param minSideLength
    * @param maxNumOfPixels
    * @return
    */
//   public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {  
//	    int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);  
//	    int roundedSize;  
//	    if (initialSize <= 8) {  
//	        roundedSize = 1;  
//	        while (roundedSize < initialSize) {  
//	            roundedSize <<= 1;  
//	        }  
//	    } else {  
//	        roundedSize = (initialSize + 7) / 8 * 8;  
//	    }  
//	    return roundedSize;  
//	}  
//	
//	private static int computeInitialSampleSize(BitmapFactory.Options options,int minSideLength, int maxNumOfPixels) {  
//	    double w = options.outWidth;  
//	    double h = options.outHeight;  
//	    int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));  
//	    int upperBound = (minSideLength == -1) ? 128 :(int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));  
//	    if (upperBound < lowerBound) {  
//	        // return the larger one when there is no overlapping zone.  
//	        return lowerBound;  
//	    }  
//	    if ((maxNumOfPixels == -1) && (minSideLength == -1)) {  
//	        return 1;  
//	    } else if (minSideLength == -1) {  
//	        return lowerBound;  
//	    } else {  
//	        return upperBound;  
//	    }  
//	}  
}
