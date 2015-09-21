package com.common.androidtemplate.module.layout;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.common.androidtemplate.R;
import com.common.androidtemplate.widget.TitlePopup;

/**
 * Author liangbx
 * Date 2015/8/14
 * Desc Google 官方推出的Navigation Drawer使用ToolBar和DrawerLayout 实现
 * 参考资料：
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

    private ImageButton mBtnMenu;

    private TitlePopup mTitlePopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        initViews();
        initMenuData();

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

        mBtnMenu = (ImageButton) findViewById(R.id.cust_nav_menu);
        mBtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mTitlePopup.show(v);
                showPopuMenu();
            }
        });

        mTitlePopup = new TitlePopup(this, getResources().getDimensionPixelSize(R.dimen.cust_nav_menu_width), ViewGroup.LayoutParams.WRAP_CONTENT,
                R.layout.custom_navigation_menu);
    }

    private void initMenuData() {
        mTitlePopup.addAction(mTitlePopup.new ActionItem(this, "精品课程"));
        mTitlePopup.addAction(mTitlePopup.new ActionItem(this, "课程"));
        mTitlePopup.addAction(mTitlePopup.new ActionItem(this, "培训认证"));
        mTitlePopup.addAction(mTitlePopup.new ActionItem(this, "职业规划"));
    }

    private void showMenu() {

    }

    private void showPopuMenu() {
        PopupMenu popupMenu = new PopupMenu(this, mBtnMenu);
        popupMenu.inflate(R.menu.ele_menu_study_index);
        popupMenu.show();
    }

}
