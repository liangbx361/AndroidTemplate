package com.common.tools.widget.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class ViewAdapter extends PagerAdapter {
	
	private List<View> viewList;
	
	public ViewAdapter(List<View> viewList) {
		this.viewList = viewList;
	}
	
	@Override
	public int getCount() {			
		return viewList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(viewList.get(position));
		return viewList.get(position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(viewList.get(position));
	}
	
	public void setListView(List<View> listView) {
		this.viewList = listView;
	}    			
}
