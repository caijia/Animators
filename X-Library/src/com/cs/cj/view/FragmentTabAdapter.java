package com.cs.cj.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * PagerTabStrip的适配器 
 * @author Administrator
 *
 */
public abstract class FragmentTabAdapter {
	
	private FragmentManager mFragmentManager ;
	
	private Fragment mCurFragment ;
	
	public FragmentTabAdapter(FragmentManager manager){
		mFragmentManager = manager ;
	}

	public abstract Fragment getItem(int position);
	
	public abstract int getCount();
	
	public abstract int getContainerId();
	
	public abstract String getPagetTitle(int position);
	
	public long getItemId(int position){
		return position ;
	}
	
	/**
	 * 切换Fragment
	 * @param tabSpec 将要切换的页面
	 */
	protected void switchFragment(Fragment nextFragment,int position) {
		String defaultTag = makeFragmentName(getContainerId(), getItemId(position));
		Fragment oldFragment = mFragmentManager.findFragmentByTag(defaultTag);
		if (oldFragment == null) {
			if (mCurFragment == null) {
				mFragmentManager.beginTransaction().add(getContainerId(), nextFragment, defaultTag).commit();
				System.out.println(11+"--"+defaultTag);
			} else {
				mFragmentManager.beginTransaction().add(getContainerId(), nextFragment, defaultTag).hide(mCurFragment).commit();
				System.out.println(22+"--"+defaultTag);
			}
			oldFragment = nextFragment;
		} else if (oldFragment != mCurFragment) {
			mFragmentManager.beginTransaction().show(oldFragment).hide(mCurFragment).commit();
			System.out.println(33+"--"+defaultTag);
		}
		mCurFragment = oldFragment ;
	}
	
	
	private static String makeFragmentName(int viewGroupId, long id) {
		return "android:switcher:" + viewGroupId + ":" + id;
	}
}
