package com.cs.animators.fragment;

import java.util.ArrayList;
import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import butterknife.InjectView;
import com.cs.animators.R;
import com.cs.animators.base.BaseFragment;
import com.cs.cj.adapter.RestoreFragmentPagerAdapter;
import com.cs.cj.view.PagerSlidingTabStrip;

public class VideoDetailFragment extends BaseFragment {
	
	@InjectView(R.id.detail_slidetab) PagerSlidingTabStrip mSlideTab ;
	@InjectView(R.id.detail_pager) ViewPager mPager ;
	
	private List<Fragment> mDetailFragments ;
	private String [] mSlideTabTitle ;
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.fragment_video_detail);
	}

	@Override
	protected void processLogic() {
		
		mDetailFragments = new ArrayList<Fragment>();
		DetailIntroFragment introFragment = new DetailIntroFragment();
		DetailSeriesFragment seriesFragment = new DetailSeriesFragment();
		
		mDetailFragments.add(introFragment);
		mDetailFragments.add(seriesFragment);
		
		mSlideTabTitle = getResources().getStringArray(R.array.detail_slide_tab);
		mPager.setAdapter(new DetailVideoPagerAdapter(getChildFragmentManager()));
		mSlideTab.setViewPager(mPager);
		mPager.setCurrentItem(1);
	}
	
	private class DetailVideoPagerAdapter extends RestoreFragmentPagerAdapter{

		public DetailVideoPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return mDetailFragments.get(position);
		}

		@Override
		public int getCount() {
			return mDetailFragments.size();
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			return mSlideTabTitle[position];
		}
		
	}
}
