package com.common.androidtemplate.barcode;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.common.tools.util.DensityUtil;

public class GenerateHelper {

	private Activity mActivity;

	public GenerateHelper(Activity activity) {
		mActivity = activity;
	}

	public Bitmap genBarCode(String content) throws WriterException {

		// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
		Point point = getBarcodeSize();
		BitMatrix matrix = new MultiFormatWriter().encode(content,
				BarcodeFormat.QR_CODE, point.x, point.y);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		// 二维矩阵转为一维像素数组,也就是一直横着排了
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[y * width + x] = matrix.get(x, y) ? 0xff000000
						: 0xfffefefe;
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		// 通过像素数组生成bitmap
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

		return createFramedPhoto(point.x, point.y, bitmap, 30f);
	}

	private Point getBarcodeSize() {
		Point point = new Point();
		DisplayMetrics metrics = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		point.x = metrics.widthPixels * 6 / 10;
		point.y = point.x;
		return point;
	}

	/**
	 * 
	 * @param x
	 *            图像的宽度
	 * @param y
	 *            图像的高度
	 * @param image
	 *            源图片
	 * @param outerRadiusRat
	 *            圆角的大小
	 * @return 圆角图片
	 */
	@SuppressWarnings("deprecation")
	Bitmap createFramedPhoto(int x, int y, Bitmap image, float outerRadiusRat) {
		// 根据源文件新建一个darwable对象
		Drawable imageDrawable = new BitmapDrawable(image);

		// 新建一个新的输出图片
		Bitmap output = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		// 新建一个矩形
		RectF outerRect = new RectF(0, 0, x, y);

		// 产生一个红色的圆角矩形
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.RED);
		canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint);

		// 将源图片绘制到这个圆角矩形上
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		imageDrawable.setBounds(0, 0, x, y);
		canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
		imageDrawable.draw(canvas);
		canvas.restore();

		// 绘制红边框
		int offer = DensityUtil.dip2px(mActivity, 10);
		int strokeWidht = DensityUtil.dip2px(mActivity, 6);
		RectF rect = new RectF(offer, offer, x - offer, y - offer);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(strokeWidht);
		paint.setColor(Color.RED);
		canvas.drawRoundRect(rect, outerRadiusRat, outerRadiusRat, paint);

		return output;
	}
}
