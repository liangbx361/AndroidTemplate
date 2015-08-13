package com.common.androidtemplate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TemAdapter extends BaseAdapter {

	private Context mContext;

	public TemAdapter(Context context) {
		mContext = context;
	}

	@Override
	public int getCount() {

		return 0;
	}

	@Override
	public Object getItem(int position) {

		return null;
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
			// view = inflater.inflate(R.layout.channel_add_layout, null);
			holder = new ViewHolder();
			// holder.nameTv = (TextView) view.findViewById(R.id.name);
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}

		return view;
	}

	class ViewHolder {

	}
}
