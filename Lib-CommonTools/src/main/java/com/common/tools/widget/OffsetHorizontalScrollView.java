package com.common.tools.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * 支持获取水平滑动的偏移量
 * @author liangbx
 *
 */
public class OffsetHorizontalScrollView extends HorizontalScrollView {

	public OffsetHorizontalScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public OffsetHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public OffsetHorizontalScrollView(Context context) {
		super(context);
	}
	
	/**
	 * 获取水平滑动的偏移量
	 * @return
	 */
	public int getHorizontalOffset() {
		return super.computeHorizontalScrollOffset();
	}
	
	
}
