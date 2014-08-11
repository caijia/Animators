package com.cs.animators.fragment;

import java.util.ArrayList;
import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import butterknife.InjectView;
import com.cs.animators.R;
import com.cs.animators.base.BaseFragment;
import com.cs.cj.adapter.RestoreFragmentPagerAdapter;
import com.cs.cj.view.PagerSlidingTabStrip;

public class MainFragment extends BaseFragment {

	@InjectView(R.id.main_tab_slide)
	PagerSlidingTabStrip mPagerSlideTab;

	@InjectView(R.id.main_vpager)
	ViewPager mViewPager;

	String[] mSlideTabTitle;
	
	// 业务逻辑处理
	@Override
	protected void processLogic() {
		
		mSlideTabTitle = getResources().getStringArray(R.array.main_slidetab_title);
		
		//Tab标签
		List<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(new FindFragment());
		fragments.add(new ThemeFragment());
		fragments.add(new NewUpdateFragment());
		fragments.add(new HotFragment());
		
		PagerAdapter adapter = new MainSlideTabFragmentAdapter(getChildFragmentManager(),fragments);
		mViewPager.setAdapter(adapter);
		mPagerSlideTab.setViewPager(mViewPager);
		
	}

	class MainSlideTabFragmentAdapter extends RestoreFragmentPagerAdapter {

		private List<Fragment> mFragments ;
		
		public MainSlideTabFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			mFragments = fragments ;
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

		@Override
		public int getCount() {
			return mSlideTabTitle.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mSlideTabTitle[position];
		}
	}
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.fragment_main);
	}

}
