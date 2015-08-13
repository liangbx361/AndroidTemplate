package com.common.androidtemplate.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.androidtemplate.R;
import com.common.androidtemplate.activity.base.BaseBackActivity;

/**
 * 
 * @描述：电子签名DEMO
 * @作者：liang bao xian
 * @时间：2014年8月25日 下午4:20:21
 */
public class SignatureActivity extends BaseBackActivity implements OnClickListener{
	
	private FrameLayout canvasFl;
	private TextView hintTv;
	private Button reSignBtn;
	private Button confirmBtn;
	
	private PaintView pView;
	private View signView;
	private ImageView signIv;
	private Dialog signDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signature);
		
		getIntentData();
		initView();
	}

	@Override
	public void getIntentData() {

	}

	@Override
	public void initView() {
		canvasFl = (FrameLayout) findViewById(R.id.canvas);
		hintTv = (TextView) findViewById(R.id.hint);
		reSignBtn = (Button) findViewById(R.id.resign);
		reSignBtn.setOnClickListener(this);
		confirmBtn = (Button) findViewById(R.id.confirm);
		confirmBtn.setOnClickListener(this);
		
		pView = new PaintView(this);
		canvasFl.addView(pView);	
		
		LayoutInflater factory = LayoutInflater.from(this);	
		signView = factory.inflate(R.layout.dialog_signature, null);
		signIv = (ImageView) signView.findViewById(R.id.signature);
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch(v.getId()) {
		case R.id.resign:
			pView.clear();
			break;
			
		case R.id.confirm:					
			signIv.setImageBitmap(pView.getCachebBitmap());
			if(signDialog == null) {
				signDialog = new AlertDialog.Builder(this)
				.setTitle("生成的签名图像")
				.setView(signView)
				.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				})
				.create();
				signDialog.show();
			} else {
				signDialog.show();
			}
            
			break;
		}
	}

	/**
	 * This view implements the drawing canvas.
	 * 
	 * It handles all of the input events and drawing functions.
	 */
	class PaintView extends View {
		private Paint paint;
		private Canvas cacheCanvas;
		private Bitmap cachebBitmap;
		private Path path;
		
		private int width = 10;
		private int height = 10;
		
		public Bitmap getCachebBitmap() {
			return cachebBitmap;
		}

		public PaintView(Context context) {
			super(context);
			init();			
		}

		private void init() {
			paint = new Paint();
			paint.setAntiAlias(true);
			paint.setStrokeWidth(10);
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(Color.BLACK);
			path = new Path();
			cachebBitmap = Bitmap.createBitmap(width, (int) (height * 0.8),
					Config.ARGB_8888);
			cacheCanvas = new Canvas(cachebBitmap);
			cacheCanvas.drawColor(Color.WHITE);
			invalidate();
		}

		public void clear() {
			if (cacheCanvas != null) {
				paint.setColor(Color.WHITE);
				cacheCanvas.drawPaint(paint);
				paint.setColor(Color.BLACK);
				cacheCanvas.drawColor(Color.WHITE);
				invalidate();
			}
		}

		@Override
		protected void onDraw(Canvas canvas) {
			// canvas.drawColor(BRUSH_COLOR);
			canvas.drawBitmap(cachebBitmap, 0, 0, null);
			canvas.drawPath(path, paint);
		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {

			int curW = cachebBitmap != null ? cachebBitmap.getWidth() : 0;
			int curH = cachebBitmap != null ? cachebBitmap.getHeight() : 0;
			if (curW >= w && curH >= h) {
				return;
			}

			if (curW < w)
				curW = w;
			if (curH < h)
				curH = h;

			Bitmap newBitmap = Bitmap.createBitmap(curW, curH,
					Config.ARGB_8888);
			Canvas newCanvas = new Canvas();
			newCanvas.setBitmap(newBitmap);
			if (cachebBitmap != null) {
				newCanvas.drawBitmap(cachebBitmap, 0, 0, null);
			}
			cachebBitmap = newBitmap;
			cacheCanvas = newCanvas;
			
			cacheCanvas.drawColor(Color.WHITE);
		}

		private float cur_x, cur_y;

		@Override
		public boolean onTouchEvent(MotionEvent event) {

			float x = event.getX();
			float y = event.getY();

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				hintTv.setVisibility(View.GONE);
				cur_x = x;
				cur_y = y;
				path.moveTo(cur_x, cur_y);
				break;
			}

			case MotionEvent.ACTION_MOVE: {
				path.quadTo(cur_x, cur_y, x, y);
				cur_x = x;
				cur_y = y;
				break;
			}

			case MotionEvent.ACTION_UP: {
				cacheCanvas.drawPath(path, paint);
				path.reset();
				break;
			}
			}

			invalidate();

			return true;
		}
	}
}
