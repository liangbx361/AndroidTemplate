package com.common.androidtemplate.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.common.androidtemplate.R;
import com.common.androidtemplate.activity.base.BaseActivity;
import com.common.androidtemplate.adapter.PullRefreshAdapter;
import com.common.androidtemplate.bean.PullList;
import com.common.androidtemplate.widget.PullRefreshListViewHelper;
import com.common.tools.util.PageInfo;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

/**
 * 
 * @描述：
 * @作者：liang bao xian
 * @时间：2014年8月13日 上午9:27:08
 */
public class PullRefreshListViewActivity extends BaseActivity implements OnItemClickListener,
	OnItemLongClickListener{

	//下拉刷新
	private PullToRefreshListView mPullListView;
	private ListView mListView;
	private PullRefreshAdapter mAdapter;
	
	private PullRefreshListViewHelper pullHelper;
	private int loadState = PullRefreshListViewHelper.BOTTOM_STATE_LOAD_IDLE;
	private PageInfo pageInfo;
	
	private PullList mPullList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		test(pageInfo.pageNo);		
	}
	
	@Override
	public void getIntentData() {
		
	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_pull_refresh;
	}

	@Override
	public void initView() {
		mPullListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		mPullListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				//处理下拉刷新
				pageInfo.pageNo = 1;
				test(pageInfo.pageNo);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				//处理上拉加载	
				test(++pageInfo.pageNo);
			}
		});
		
		mPullListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				//滑动到底部触发的事件
				
				mHandler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						test(++pageInfo.pageNo);
					}
				}, 1000);
			}
			
		});
		
		//设置刷新模式
		mPullListView.setMode(Mode.PULL_FROM_START);
		
		mListView = mPullListView.getRefreshableView();
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this);
		
		pageInfo = new PageInfo();
		pullHelper = new PullRefreshListViewHelper(this, mListView, pageInfo.pageSize);
		pullHelper.setBottomClick(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				if(loadState == PullRefreshListViewHelper.BOTTOM_STATE_LOAD_FAIL) {
					//加载失败，点击重试
					loadState = PullRefreshListViewHelper.BOTTOM_STATE_LOADING;
					pullHelper.setBottomState(loadState);					
				}
			}
		});		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		setDisplayHomeAsUpEnabled(true);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		return true;
	}
	
	private void test(int pageNo) {		
		if(pageNo == 1) {
			mPullList = new PullList();
			mPullList.itemList = new ArrayList<PullList.Item>();
		} 
		
		for(int i=1; i<=pageInfo.pageSize; i++) {
			PullList.Item item = mPullList.new Item();
			item.name = ((pageInfo.pageNo-1) * pageInfo.pageSize + i) + "";
			mPullList.itemList.add(item);
		}
		
		if(pageNo == 1) {
			mAdapter = new PullRefreshAdapter(this, mPullList);
			mListView.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}
		
		mHandler.sendEmptyMessageDelayed(0, 1000);
	}
	
	@SuppressLint("HandlerLeak") 
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			mPullListView.onRefreshComplete();
		}
		
	};
}
