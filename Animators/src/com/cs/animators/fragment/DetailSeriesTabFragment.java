package com.cs.animators.fragment;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import butterknife.InjectView;
import butterknife.OnItemClick;
import com.cs.animators.R;
import com.cs.animators.TestVitamioActivity;
import com.cs.animators.VideoDetailActivity;
import com.cs.animators.adapter.SeriesAdapter;
import com.cs.animators.base.BaseFragment;
import com.cs.animators.entity.VideoDetail;
import com.cs.animators.entity.VideoDetailSeries;
import static com.cs.animators.fragment.DetailSeriesFragment.PAGE_COUNT;

public class DetailSeriesTabFragment extends BaseFragment {

	@InjectView(R.id.detail_series_tab_gv) GridView mGvSeries ;
	
	private List<VideoDetailSeries> mSeriesData ;
	
	private VideoDetail mVideoDetail ;
	
	private int mCurrentPage ;
	
	private int mTotalPage ;
	
	private int mTotalVideoNum ;
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.fragment_detail_series_tab);
	}

	@Override
	protected void processLogic() {
		
		mVideoDetail = ((VideoDetailActivity)getActivity()).getVideoDetail();
		mTotalVideoNum = mVideoDetail.getEpisode().size();
		mTotalPage = mTotalVideoNum / PAGE_COUNT + (mTotalVideoNum % PAGE_COUNT == 0 ? 0 : 1);
		
		mSeriesData = getTabData(mCurrentPage);
		SeriesAdapter adapter = new SeriesAdapter(getActivity(), mCurrentPage, mSeriesData);
		mGvSeries.setAdapter(adapter);
		
	}
	
	@Override
	protected void getExtra(Bundle arguments) {
		if(arguments != null){
			mCurrentPage = arguments.getInt(DetailSeriesFragment.CURRENT_PAGER);
		}
	}
	
	private List<VideoDetailSeries> getTabData(int position) {

		List<VideoDetailSeries> totalData = mVideoDetail.getEpisode();

		if (position == (mTotalPage - 1)) 
		{
			return totalData.subList(position * PAGE_COUNT, mTotalVideoNum);
		} 
		else 
		{
			return totalData.subList(position * PAGE_COUNT, (position + 1)* PAGE_COUNT);
		}
	}
	
	@OnItemClick(R.id.detail_series_tab_gv)
	void onItemClick(AdapterView<?> parent, View view,int position, long a) {
		VideoDetailSeries detailSeries = (VideoDetailSeries) parent.getAdapter().getItem(position);
		//进入视频播放页面
		String videoId = mVideoDetail.getVideoId();
		
		Intent intent = new Intent(getActivity(), TestVitamioActivity.class);
		intent.putExtra(TestVitamioActivity.VIDEO_ID, videoId);
		intent.putExtra(TestVitamioActivity.CUR_PLAY_VIDEO_SERIES, detailSeries);
		intent.putExtra(TestVitamioActivity.CUR_VIDEO_SERIES, mCurrentPage*DetailSeriesFragment.PAGE_COUNT + position + 1);
		intent.putParcelableArrayListExtra(TestVitamioActivity.TOTAL_SERIES, (ArrayList<? extends Parcelable>) mVideoDetail.getEpisode());
		getActivity().startActivity(intent);
		
	}
	

}
