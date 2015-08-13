package com.common.tools.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * 支持刷新功能的AutoCompleteTextView
 * @author liangbx
 *
 */
public class RefreshAutoCompleteTextView extends AutoCompleteTextView {

	public RefreshAutoCompleteTextView(Context context) {
		super(context);
	}
	
	public RefreshAutoCompleteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public RefreshAutoCompleteTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void refresh() {
		super.performFiltering(getText(), 0);
	}
}
