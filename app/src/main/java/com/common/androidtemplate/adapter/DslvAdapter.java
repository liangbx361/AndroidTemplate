package com.common.androidtemplate.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.common.androidtemplate.bean.DslvItem;
import com.common.androidtemplate.R;


public class DslvAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<DslvItem> mDslvList;

	public DslvAdapter(Context context, List<DslvItem> list) {
		mContext = context;
		mDslvList = list;
	}

	@Override
	public int getCount() {

		return mDslvList.size();
	}

	@Override
	public Object getItem(int position) {

		return mDslvList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = null;
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			view = inflater.inflate(R.layout.dslvlist_item_handle_right, null);
			holder = new ViewHolder();
			holder.nameTv = (TextView) view.findViewById(R.id.name);
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		
		DslvItem dItem = mDslvList.get(position);
		holder.nameTv.setText(dItem.name);

		return view;
	}

	class ViewHolder {
		TextView nameTv;
	}
}
