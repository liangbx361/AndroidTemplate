package com.common.androidtemplate.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.common.androidtemplate.R;

/**
 * Author liangbx
 * Date 2015/8/14
 * Desc Google 官方推出的Navigation Drawer使用ToolBar和DrawerLayout 实现
 * 参考文章：
 *      http://blog.chengyunfeng.com/?p=493
 *      http://chenqichao.me/2014/12/08/108-Android-Toolbar-DrawerLayout-01/
 *      实现左右两个侧滑菜单
 *      http://fengbohaishang.blog.51cto.com/5106297/1539203
 *      实现悬浮的效果
 *      http://www.jianshu.com/p/3fe2acac0ddb
 *      设计规范
 *      https://www.google.com/design/spec/patterns/navigation-drawer.html#
 */
public class NavigationDrawerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ListView lvLeftMenu;
    private String[] lvs = {"List Item 01", "List Item 02", "List Item 03", "List Item 04"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        initViews();

        //设置添加ToolBar
        setSupportActionBar(toolbar);

        //创建返回键，并实现打开关/闭监听
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //设置菜单列表
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lvs);
        lvLeftMenu.setAdapter(arrayAdapter);
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        lvLeftMenu = (ListView) findViewById(R.id.left_drawer);
    }
}
