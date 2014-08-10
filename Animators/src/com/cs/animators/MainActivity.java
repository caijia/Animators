package com.cs.animators;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.InjectView;
import butterknife.OnItemClick;

import com.cs.animators.adapter.DrawerAdapter;
import com.cs.animators.base.BaseActivity;
import com.cs.animators.entity.DrawerItem;
import com.cs.animators.fragment.MainFragment;
import com.cs.animators.util.CommonUtil;
import com.cs.cj.service.FragmentService;

public class MainActivity extends BaseActivity {
	
	@InjectView(R.id.main_drawer_layout) DrawerLayout mDrawerLayout ;
	@InjectView(R.id.main_lv_left_drawer) ListView mListView ;
	
	private ActionBarDrawerToggle mDrawerToggle ;
	String [] mDrawerTextItem ;
	
	String mAppName ;

	@Override
	protected void loadLayout() {
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void processLogic() {
		mAppName = getResources().getString(R.string.app_name);
		//ActionBar逻辑
		mActionBar.setTitle(mAppName);
		
		//设置主界面 MainFragment
		FragmentService.getInstance().switchToFragment(this, R.id.main_fl_content_frame, new MainFragment(), null);
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close){

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				ActivityCompat.invalidateOptionsMenu(MainActivity.this);
				
				//界面跳转逻辑
				drawerLayoutClick();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				ActivityCompat.invalidateOptionsMenu(MainActivity.this);
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		//设置ListView里面的数据
		mDrawerTextItem = getResources().getStringArray(R.array.main_drawer_title);
		TypedArray mDrawerImgItem = getResources().obtainTypedArray(R.array.main_drawer_img);
		List<DrawerItem> drawerData = new ArrayList<DrawerItem>();
		for (int i = 0; i < mDrawerTextItem.length; i++) {
			drawerData.add(new DrawerItem(mDrawerImgItem.getDrawable(i), mDrawerTextItem[i]));
		}
		mDrawerImgItem.recycle();
		DrawerAdapter adapter = new DrawerAdapter(mContext, drawerData);
		mListView.setAdapter(adapter);
		
	}
	
	protected void drawerLayoutClick() {
		if(mDrawerPosition == 2)
		{
			Intent intent = new Intent(mContext, LocalVideoActivity.class);
			startActivity(intent);
		}
		//动漫收藏
		else if(mDrawerPosition == 1)
		{
			Intent intent = new Intent(mContext, VideoCollectActivity.class);
			startActivity(intent);
		}
		
	}

	private int mDrawerPosition ;
	
	@OnItemClick(R.id.main_lv_left_drawer)
	void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		mDrawerLayout.closeDrawer(mListView);
//		getSupportActionBar().setTitle(mDrawerTextItem[position]);
		mDrawerPosition = position ;
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(mDrawerToggle.onOptionsItemSelected(item))
		{
			return true;
		}
		
		switch (item.getItemId()) {
		case R.id.action_userinfo:
			break;

		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
//	private void showPopMenu(View flowMenu) {
//		PopupMenu popupMenu = new PopupMenu(mContext, flowMenu);
//		
//		/*这段代码 使Actionbar显示图标*/
//		try {
//		    Field[] fields = popupMenu.getClass().getDeclaredFields();
//		    for (Field field : fields) {
//		        if ("mPopup".equals(field.getName())) {
//		            field.setAccessible(true);
//		            Object menuPopupHelper = field.get(popupMenu);
//		            Class<?> classPopupHelper = Class.forName(menuPopupHelper
//		                    .getClass().getName());
//		            Method setForceIcons = classPopupHelper.getMethod(
//		                    "setForceShowIcon", boolean.class);
//		            setForceIcons.invoke(menuPopupHelper, true);
//		            break;
//		        }
//		    }
//		} catch (Exception e) {
//		    e.printStackTrace();
//		}
//		
//		popupMenu.getMenuInflater().inflate(R.menu.main_overflow, popupMenu.getMenu());
//		popupMenu.show();
//		popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//			
//			@Override
//			public boolean onMenuItemClick(MenuItem menu) {
//				switch (menu.getItemId()) {
//				case R.id.action_search:
//					CommonUtil.showMessage(mContext, "search");
//					break;
//
//				default:
//					break;
//				}
//				return false;
//			}
//		});
//	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mListView);
		menu.findItem(R.id.action_search).setVisible(!drawerOpen);
		menu.findItem(R.id.action_edit).setVisible(!drawerOpen);
		menu.findItem(R.id.action_delete).setVisible(!drawerOpen);
		menu.findItem(R.id.action_add).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}
	
	public static final String SEARCH_WORD = "search_word";
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem menuItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String word) {
				//search
				Intent search = new Intent(mContext, SearchActivity.class);
				search.putExtra(SEARCH_WORD, word);
				startActivity(search);
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String arg0) {
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}
	
}
