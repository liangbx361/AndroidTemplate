package com.common.tools.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 解决嵌入到ScrollView或者ListView中只显示一行的问题
 * @author Administrator
 *
 */
public class ReHeightGridView extends GridView {

	public ReHeightGridView(Context context) {
		super(context);
	}

	public ReHeightGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ReHeightGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}
}
