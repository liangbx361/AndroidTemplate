package com.common.tools.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 在ScrollView模式下包含ListView，GridView类型的控件时，只会显示一行的内容
 * 解决方法：ListView、GridView计算并设置自身需要的高度
 * @author Administrator
 *
 */
public class ComputeViewHeight {
	
	/**
	 * 根据子类的高度计算ListView需要的高度
	 * @param listView
	 */
	public static void setListViewHeight(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { 
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽
			totalHeight += listItem.getMeasuredHeight(); // 统计子项的高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		
		listView.setLayoutParams(params);
	}	
}
