package com.common.tools.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.AsyncTask;

import com.common.tools.net.HttpHelper;
import com.common.tools.net.NetworkCheck;
import com.common.tools.net.ResultCode;
import com.common.tools.task.TaskDoneListener;

public abstract class BaseTask extends AsyncTask<Map<String, String>, Integer, Integer> {
	
	protected Context mContext;
	protected TaskDoneListener mListenter;
	protected List<Object> mList = new ArrayList<Object>();
	protected String url;
	
	public BaseTask(Context context, String url, TaskDoneListener doneListenter) {
		this.url = url;
		mContext = context;
		mListenter = doneListenter;		
	}
	
	@Override
	protected Integer doInBackground(Map<String, String>... params) {
		int result = fetchHttpContent(params[0]);
		return result;
	}
	
	private int fetchHttpContent(Map<String, String> params) {
		if(!NetworkCheck.netwokAvaiable(mContext)){
			return ResultCode.NO_NETWORK;
		}
		
		return fetchContent(params);
	}
	
	abstract public int fetchContent(Map<String, String> params);
	
	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		if(mListenter != null) 
			mListenter.taskDone(result, mList);
	}
}
