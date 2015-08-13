package com.common.androidtemplate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.common.androidtemplate.R;
import com.common.androidtemplate.bean.PullList;
import com.common.androidtemplate.bean.PullList.Item;

public class PullRefreshAdapter extends BaseAdapter {

	private Context mContext;
	private PullList mPullList;

	public PullRefreshAdapter(Context context, PullList list) {
		mContext = context;
		mPullList = list;
	}

	@Override
	public int getCount() {

		return mPullList.itemList.size();
	}

	@Override
	public Object getItem(int position) {

		return mPullList.itemList.get(position);
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
		
		Item item = mPullList.itemList.get(position);
		holder.nameTv.setText(item.name);

		return view;
	}

	class ViewHolder {
		TextView nameTv;
	}
}
