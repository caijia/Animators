package com.cs.animators.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import butterknife.InjectView;
import com.cs.animators.R;
import com.cs.animators.VideoDetailActivity;
import com.cs.animators.base.BaseFragment;
import com.cs.animators.dao.bean.VideoPlayRecord;
import com.cs.animators.dao.service.DaoFactory;
import com.cs.animators.entity.VideoDetail;
import com.cs.cj.view.FragmentTabAdapter;
import com.cs.cj.view.PagerTabStrip;

public class DetailSeriesFragment extends BaseFragment {

	@InjectView(R.id.detail_series_indicator)PagerTabStrip mPtsIndicator ;
	
	public static final int PAGE_COUNT = 65 ;
	
	private VideoDetail mVideoDetail ;
	
	private int mTotalPage ;
	
	private int mTotalVideoNum ;
	
	private int mLastPlaySeries = -1;
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.fragment_detail_series);
	}

	@Override
	protected void processLogic() {
		mVideoDetail = ((VideoDetailActivity)getActivity()).getVideoDetail();
		mTotalVideoNum = mVideoDetail.getEpisode().size();
		mTotalPage = mTotalVideoNum / PAGE_COUNT + (mTotalVideoNum % PAGE_COUNT == 0 ? 0 : 1);
		
		//当只有一页的时候去掉tab 指示器
		if(mTotalPage == 1)
		{
			mPtsIndicator.setVisibility(View.GONE);
		}
		
		VideoPlayRecord lastPlayRecord = DaoFactory.getVideoRecordInstance(getActivity()).queryLastPlayRecord(mVideoDetail.getVideoId());
		if(lastPlayRecord != null){
			mLastPlaySeries = lastPlayRecord.getSeries();
		}
		
		mPtsIndicator.setAdapter(new DetailSeriesTabAdapter(getChildFragmentManager()));
	}

	
	public static final String CURRENT_PAGER = "current_page";
	public static final String LAST_PLAY_SERIES = "last_play_series";
	
	private class DetailSeriesTabAdapter extends FragmentTabAdapter{

		public DetailSeriesTabAdapter(FragmentManager manager) {
			super(manager);
		}

		@Override
		public int getContainerId() {
			return R.id.detail_series_fragment_container;
		}

		@Override
		public int getCount() {
			return mTotalPage;
		}

		@Override
		public Fragment getItem(int position) {
			DetailSeriesTabFragment fragment = new DetailSeriesTabFragment();
			Bundle args = new Bundle();
			args.putInt(CURRENT_PAGER, position);
			if(mLastPlaySeries >= getFirstPosition(position) && mLastPlaySeries <= getLastPosition(position)){
				args.putInt(LAST_PLAY_SERIES, mLastPlaySeries);
			}
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public String getPagetTitle(int position) {
			return getTabTitle(position);
		}
		
	}
	
	private long getFirstPosition(int position){
		String tabTitle = getTabTitle(position);
		return Long.parseLong(tabTitle.substring(0, tabTitle.indexOf("-")).trim());
	}
	
	private long getLastPosition(int position){
		String tabTitle = getTabTitle(position);
		return Long.parseLong(tabTitle.substring(tabTitle.indexOf("-") + 1).trim());
	}
	
	private String getTabTitle(int position) {
		if(position == mTotalPage - 1)
		{
			return ((position) * PAGE_COUNT + 1 )+ " - " + mTotalVideoNum;
		}
		else
		{
			return ((position) * PAGE_COUNT + 1 )+ " - " + (position + 1) * PAGE_COUNT;
		}
	}
}
