package com.common.androidtemplate.module.main;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.common.androidtemplate.R;
import com.common.androidtemplate.activity.base.BaseActivity;
import com.common.androidtemplate.bean.MenuItem;
import com.common.androidtemplate.config.DebugConfig;
import com.common.tools.file.AssetsUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements OnItemClickListener{

	private DrawerLayout mDrawerLayout;

	private List<MenuItem> mMenuList;

	private ListView mContentLv;
	private List<ActivityInfo> mActivityList;

	@Override
	public void getIntentData() {
		parserMenuData();
	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	public void initView() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		//创建返回键，并实现打开关/闭监听
		ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}
		};
		drawerToggle.syncState();
		mDrawerLayout.setDrawerListener(drawerToggle);
		ActionBar actionBar = getSupportActionBar();
		getSupportActionBar().setTitle(mMenuList.get(0).menuDesc);

		//初始化菜单
		ListView mMenuLv = (ListView) findViewById(R.id.left_drawer);
		String[] menus = new String[mMenuList.size()];
		for(int i=0; i<menus.length; i++) {
			menus[i] = mMenuList.get(i).menuDesc;
		}
		ArrayAdapter<String> menuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menus);
		mMenuLv.setAdapter(menuAdapter);
		mMenuLv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				setCurrentData(position);
				getSupportActionBar().setTitle(mMenuList.get(position).menuDesc);
				mDrawerLayout.closeDrawers();
			}
		});

		//初始化默认内容数据
		mContentLv = (ListView) findViewById(R.id.content_lv);
		setCurrentData(0);
	}

	private void setCurrentData(int index) {
		MenuItem menuItem = mMenuList.get(index);
		List<Map<String, String>> dataList = getAcInfo(menuItem.menuName);
		SimpleAdapter adapter = new SimpleAdapter(this, dataList,
				android.R.layout.simple_list_item_2, new String[]{"name", "desc"},
				new int[]{android.R.id.text1, android.R.id.text2});
		mContentLv.setAdapter(adapter);
		mContentLv.setOnItemClickListener(this);
	}

	private void parserMenuData() {
		String dataStr = AssetsUtil.getStringFromFile(this, "config/Menu.json");
		Gson gson = new Gson();
		mMenuList = gson.fromJson(dataStr, new TypeToken<List<MenuItem>>(){}.getType());
		DebugConfig.showLog("json", dataStr);
	}

	public List<Map<String, String>> getAcInfo(String module) {
		List<Map<String, String>> dataList = new ArrayList<>();
		try {
			PackageInfo pi = getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
			mActivityList = new ArrayList<>();
			int realIndex = 0;
			for(int i=0; i<pi.activities.length; i++) {
				if(pi.activities[i].name.contains("." + module)) {
					mActivityList.add(pi.activities[i]);
					String label = getResources().getString(pi.activities[i].labelRes); 
					String resFullName = getResources().getResourceName(pi.activities[i].labelRes);
					String resName = resFullName.substring(1 + resFullName.lastIndexOf("/"));
					String resType = resFullName.substring(1 + resFullName.lastIndexOf(":"), resFullName.lastIndexOf("/"));
					String descName = resName + "_desc";					
					int descId = getResources().getIdentifier(descName, resType, getPackageName());					
					Map<String, String> map = new HashMap<>();
					realIndex++;
					map.put("name", realIndex + ". " + label);
					map.put("desc", getResources().getString(descId));
					dataList.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ActivityInfo ai = mActivityList.get(position);
		Intent intent = new Intent();
		intent.setClassName(this, ai.name);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.setting, menu);
		return super.onCreateOptionsMenu(menu);
	}

}
