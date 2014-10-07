package com.cs.cj.service;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import com.cs.cj.application.JApplication;

public class FragmentService {
	
	private FragmentService(){
		
	}
	
	private static FragmentService manager = null ;
	
	public static FragmentService getInstance(){
		if(manager == null)
		{
			synchronized (FragmentService.class) {
				if(manager == null)
				{
					manager = new FragmentService();
				}
			}
		}
		return manager ;
	}
	
	
	public void switchToFragment(FragmentActivity fragmentActivity, int contentid, Fragment to, String tag) {
		JApplication app = (JApplication) fragmentActivity.getApplication();
		FragmentManager manager = fragmentActivity.getSupportFragmentManager();
		String  fragment_tag = to.getClass().getSimpleName();
		if(!TextUtils.isEmpty(tag)){
		  fragment_tag = fragment_tag.concat(tag);
		}
		//获取缓存的Fragment
		Fragment to_fragment = manager.findFragmentByTag(fragment_tag);
		//获取当前集合里面显示的 Fragment
		Fragment current_fragment = app.getCurFragmentArray().get(contentid);

		if (to_fragment == null) {
			if (current_fragment == null) {
				manager.beginTransaction().add(contentid, to, fragment_tag).commit();
			} else {
				manager.beginTransaction().add(contentid, to, fragment_tag).hide(current_fragment).commit();
			}
			to_fragment = to;
		} else if (to_fragment != current_fragment) {
		    manager.beginTransaction().show(to_fragment).hide(current_fragment).commit();
		}
		app.getCurFragmentArray().put(contentid, to_fragment);
	}
	
}
