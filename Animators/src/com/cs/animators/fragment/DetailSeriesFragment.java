package com.cs.animators.fragment;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import com.cs.animators.R;
import com.cs.animators.TestVitamioActivity;
import com.cs.animators.VideoDetailActivity;
import com.cs.animators.adapter.SeriesAdapter;
import com.cs.animators.base.BaseFragment;
import com.cs.animators.entity.VideoDetail;
import com.cs.animators.entity.VideoDetailSeries;
import com.cs.cj.view.PagerSlidingTabStrip;

public class DetailSeriesFragment extends BaseFragment {

	@InjectView(R.id.detail_series_slide_tab)PagerSlidingTabStrip mSlideTab ;
	@InjectView(R.id.detail_series_pager)ViewPager mPager ;
	@InjectView(R.id.detail_series_divider)View mDividerLine ;
	
	public static final int PAGE_COUNT = 25 ;
	
	private List<View> mSeriesViews = new ArrayList<View>();
	
	private int mTotalVideoNum ;
	
	private VideoDetail mVideoDetail ;
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.fragment_detail_series);
	}

	@Override
	protected void processLogic() {
		mVideoDetail = ((VideoDetailActivity)getActivity()).getVideoDetail();
		mTotalVideoNum = mVideoDetail.getEpisode().size();
		List<VideoDetailSeries> list = mVideoDetail.getEpisode();
		
		int totalPage = mTotalVideoNum / PAGE_COUNT + (mTotalVideoNum % PAGE_COUNT == 0 ? 0 : 1);
		//只有一页的时候去掉 指示器
		if(totalPage == 1)
		{
			mSlideTab.setVisibility(View.GONE);
			mDividerLine.setVisibility(View.GONE);
		}
		
		//为每一页创建一个GridView
		for (int curPage = 0; curPage < totalPage; curPage++) {
			View v = LayoutInflater.from(getActivity()).inflate(R.layout.adapter_detail_series, null);
			ViewHolder holder = new ViewHolder(v);
			SeriesAdapter adapter = null ;
			if(curPage == (totalPage - 1))  
			{
				adapter = new SeriesAdapter(getActivity(), curPage , list.subList(curPage*PAGE_COUNT,mTotalVideoNum));
			}
			else
			{
				adapter = new SeriesAdapter(getActivity(), curPage , list.subList(curPage*PAGE_COUNT , (curPage+1)*PAGE_COUNT));
			}
			
			holder.gridView.setAdapter(adapter);
			mSeriesViews.add(v);
		}
		
		mPager.setAdapter(new SeriesPagerAdapter());
		mSlideTab.setViewPager(mPager);
		
	}
	
	
	class ViewHolder{
		@InjectView(R.id.detail_series_gv) GridView gridView ;
		public ViewHolder(View v)
		{
			ButterKnife.inject(this, v);
		}
		
		@OnItemClick(R.id.detail_series_gv)
		void onItemClick(AdapterView<?> parent, View view,int position, long a) {
			VideoDetailSeries detailSeries = (VideoDetailSeries) parent.getAdapter().getItem(position);
			//进入视频播放页面
			String videoId = mVideoDetail.getVideoId();
			
			Intent intent = new Intent(getActivity(), TestVitamioActivity.class);
			intent.putExtra(TestVitamioActivity.VIDEO_ID, videoId);
			intent.putExtra(TestVitamioActivity.CUR_PLAY_VIDEO_SERIES, detailSeries);
			intent.putParcelableArrayListExtra(TestVitamioActivity.TOTAL_SERIES, (ArrayList<? extends Parcelable>) mVideoDetail.getEpisode());
			getActivity().startActivity(intent);
			
		}
		
	}
	
	private class SeriesPagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return mSeriesViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mSeriesViews.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mSeriesViews.get(position));
			return mSeriesViews.get(position);
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			
			//46  25  2
			if(mSeriesViews.size() > 0)
			{
				if(position == mSeriesViews.size() - 1)
				{
					return (position) * PAGE_COUNT + " - " + mTotalVideoNum ;
				}
				else
				{
					return (position) * PAGE_COUNT + " - " + (position + 1) * PAGE_COUNT ;
				}
			}
			return super.getPageTitle(position);
		}
		
	}
}
