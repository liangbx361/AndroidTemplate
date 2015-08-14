package com.common.androidtemplate.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.common.androidtemplate.R;
import com.common.androidtemplate.activity.base.BaseActivity;
import com.common.androidtemplate.config.DebugConfig;
import com.common.tools.file.AssetsUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends BaseActivity implements OnItemClickListener{
	
	private ListView mListView;
	private List<ActivityInfo> mActivityList;
	private List<Map<String, String>> mdataList;
	private List<Menu> menuList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		parserMenuData();
		getAcInfo();
		initView();
	}
	
	@Override
	public void getIntentData() {
		
	}
	
	@Override
	public void initView() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

//		FrameLayout contentVg = (FrameLayout) findViewById(R.id.content_frame);

//		mListView = (ListView) findViewById(R.id.list);
//		SimpleAdapter adapter = new SimpleAdapter(this, mdataList,
//				android.R.layout.simple_list_item_2, new String[]{"name", "desc"},
//				new int[]{android.R.id.text1, android.R.id.text2});
//		mListView.setAdapter(adapter);
//		mListView.setOnItemClickListener(this);
	}

	private void parserMenuData() {
		String dataStr = AssetsUtil.getStringFromFile(this, "config/Menu.json");
		Gson gson = new Gson();
		menuList = gson.fromJson(dataStr, new TypeToken<List<Menu>>(){}.getType());
		DebugConfig.showLog("json", dataStr);
	}

	public void getAcInfo() {
		try {
			PackageInfo pi = getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
			mActivityList = new ArrayList<>();
			mdataList = new ArrayList<>();
			for(int i=0; i<pi.activities.length; i++) {
				if(pi.activities[i].name.contains(".activity")) {
					mActivityList.add(pi.activities[i]);
					String label = getResources().getString(pi.activities[i].labelRes); 
					String resFullName = getResources().getResourceName(pi.activities[i].labelRes);
					String resName = resFullName.substring(1 + resFullName.lastIndexOf("/"));
					String resType = resFullName.substring(1 + resFullName.lastIndexOf(":"), resFullName.lastIndexOf("/"));
					String descName = resName + "_desc";					
					int descId = getResources().getIdentifier(descName, resType, getPackageName());					
					Map<String, String> map = new HashMap<String, String>();
					map.put("name", i + ". " + label);
					map.put("desc", getResources().getString(descId));
					mdataList.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ActivityInfo ai = mActivityList.get(position);
		Intent intent = new Intent();
		intent.setClassName(this, ai.name);
		startActivity(intent);
	}

}
