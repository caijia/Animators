package com.cs.animators;

import java.util.List;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import butterknife.InjectView;
import com.cs.animators.base.BaseActivity;
import com.cs.animators.entity.GroupItem;
import com.cs.animators.fragment.FindFragment;
import com.cs.animators.fragment.FindMoreItemFragment;
import com.cs.cj.adapter.RestoreFragmentPagerAdapter;
import com.cs.cj.view.PagerSlidingTabStrip;

public class FindMoreActivity extends BaseActivity {

	@InjectView(R.id.find_more_tab_indicator) PagerSlidingTabStrip mTabindicator ;
	
	@InjectView(R.id.find_more_viewpager) ViewPager mViewPager ;
	
	private List<GroupItem> mMoreGroupItem ;
	
	private int mCurTabPosition ;
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.activity_find_more);
	}

	@Override
	protected void processLogic() {
		
		mTabindicator.setShouldExpand(mMoreGroupItem.size() < 4);
		mViewPager.setAdapter(new GroupItemMoreAdapter(getSupportFragmentManager()));
		mTabindicator.setViewPager(mViewPager);
		mViewPager.setCurrentItem(mCurTabPosition);
	}
	
	@Override
	public void getExtra(Bundle bundle){
		if(bundle != null){
			mMoreGroupItem = bundle.getParcelableArrayList(FindFragment.GROUP_ITEM_MORE);
			mCurTabPosition = bundle.getInt(FindFragment.GROUP_TAB_ITEM);
		}
	}
	
	public static final String GROUP_IMTE_ID = "group_imte_id";
	
	private class GroupItemMoreAdapter extends RestoreFragmentPagerAdapter{

		public GroupItemMoreAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			FindMoreItemFragment fragment = new FindMoreItemFragment();
			Bundle args = new Bundle();
			args.putString(GROUP_IMTE_ID, mMoreGroupItem.get(position).getId());
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return mMoreGroupItem.size();
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			return mMoreGroupItem.get(position).getName();
		}
		
	}
	
	@Override
	protected boolean displayHomeAsUpEnabled() {
		return true;
	}

}
