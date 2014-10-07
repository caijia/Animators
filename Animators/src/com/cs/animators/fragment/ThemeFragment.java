package com.cs.animators.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import butterknife.InjectView;
import com.cs.animationvideo.R;
import com.cs.animators.base.BaseFragment;
import com.cs.cj.view.FragmentTabAdapter;
import com.cs.cj.view.PagerTabStrip;

public class ThemeFragment extends BaseFragment {
	
	@InjectView(R.id.theme_tab_indicator)
	PagerTabStrip mPtsIndicator ;
	
	private String [] mTabTitle ;

	@Override
	protected void loadLayout() {
		setContentView(R.layout.fragment_theme);
	}
	
	@Override
	protected void processLogic(){
		
		mTabTitle = getResources().getStringArray(R.array.theme_tab_title);
		mPtsIndicator.setAdapter(new ThemeTabFragmentAdapter(getChildFragmentManager()));
	}
	
	public static final String THEME_TAB_POSITION = "theme_tab_position";
	
	private class ThemeTabFragmentAdapter extends FragmentTabAdapter{

		public ThemeTabFragmentAdapter(FragmentManager manager) {
			super(manager);
		}

		@Override
		public int getContainerId() {
			return R.id.theme_tab_container;
		}

		@Override
		public int getCount() {
			return mTabTitle.length;
		}

		@Override
		public Fragment getItem(int position) {
			ThemeTabFragment fragment = new ThemeTabFragment();
			Bundle args = new Bundle();
			args.putInt(THEME_TAB_POSITION, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public String getPagetTitle(int position) {
			return mTabTitle[position];
		}
		
	}

	
}
