/**
 * 分为三种情况的扩展列表 1：展开前  2：展开后  3：无展开的
 */
package com.common.tools.widget.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ThreeStyleExplandAdapter extends BaseExpandableListAdapter {
	    
	protected List<? extends Map<String, ?>> mGroupData;
    private int mExpandedGroupLayout;
    private int mCollapsedGroupLayout;
    private int mNoChildLayout;
    private String[] mGroupFrom;
    private int[] mGroupTo;
    
    protected List<? extends List<? extends Map<String, ?>>> mChildData;
    private int mChildLayout;
    private int mLastChildLayout;
    private String[] mChildFrom;
    private int[] mChildTo;
    
    private LayoutInflater mInflater;
    
    public ThreeStyleExplandAdapter(Context context,
    		List<? extends Map<String, ?>> groupData, int expandedGroupLayout,
    	    int collapsedGroupLayout, int noChildLayout, String[] groupFrom, int[] groupTo,
    	    List<? extends List<? extends Map<String, ?>>> childData,
    	    int childLayout, int lastChildLayout, String[] childFrom,
    	    int[] childTo) {
    	
        mGroupData = groupData;
        mExpandedGroupLayout = expandedGroupLayout;
        mCollapsedGroupLayout = collapsedGroupLayout;
        mNoChildLayout = noChildLayout;
        mGroupFrom = groupFrom;
        mGroupTo = groupTo;
        
        mChildData = childData;
        mChildLayout = childLayout;
        mLastChildLayout = lastChildLayout;
        mChildFrom = childFrom;
        mChildTo = childTo;
        
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

	@Override
	public Object getChild(int groupPosition, int childPosition) {
        return mChildData.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View v;
		
        if (convertView == null) {
            v = newChildView(isLastChild, parent);
        } else {
            v = convertView;
        }
        
        bindView(v, mChildData.get(groupPosition).get(childPosition), mChildFrom, mChildTo);
        
        return v;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mChildData.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mGroupData.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return mGroupData.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View v;
		
		v = newGroupView(isExpanded, parent, -1);
				
		bindView(v, mGroupData.get(groupPosition), mGroupFrom, mGroupTo);
		return v;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {

		return true;
	}
	
	public View newGroupView(boolean isExpanded, ViewGroup parent, int noChildFlag) {
		View v;
		
		if(noChildFlag > 0)
			v = mInflater.inflate((isExpanded) ? mExpandedGroupLayout : mCollapsedGroupLayout,
                parent, false);
		else 
			v = mInflater.inflate(mNoChildLayout, parent, false);
		
		return v;
    }
	
	public View newChildView(boolean isLastChild, ViewGroup parent) {
        return mInflater.inflate((isLastChild) ? mLastChildLayout : mChildLayout, parent, false);
    }
    
	/**
	 * @param view
	 * @param data
	 * @param from
	 * @param to
	 */
	private void bindView(View view, Map<String, ?> data, String[] from, int[] to) {
        int len = to.length;

        for (int i = 0; i < len; i++) {
            TextView v = (TextView)view.findViewById(to[i]);
            if (v != null) {
                v.setText((String)data.get(from[i]));
            }
        }
    }
	
	public void cleanData() {
		mGroupData.clear();
		mChildData.clear();
		notifyDataSetChanged();
	}
	
	public void setChildData(List<? extends List<? extends Map<String, ?>>> childData) {
		mChildData = childData;
	}
}
