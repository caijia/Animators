package com.cs.animators.fragment;

import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import butterknife.InjectView;
import butterknife.OnItemClick;
import com.cs.animationvideo.R;
import com.cs.animators.VideoDetailActivity;
import com.cs.animators.VideoPlayActivity;
import com.cs.animators.adapter.SeriesAdapter;
import com.cs.animators.base.BaseFragment;
import com.cs.animators.constants.Constants;
import com.cs.animators.dao.bean.VideoPlayRecord;
import com.cs.animators.dao.service.DaoFactory;
import com.cs.animators.entity.VideoDetail;
import com.cs.animators.entity.VideoDetailSeries;
import com.cs.animators.eventbus.PlayRecordEvent;
import com.cs.animators.util.CommonUtil;
import com.cs.cj.http.httplibrary.RequestParams;
import com.cs.cj.http.work.JHttpClient;
import de.greenrobot.event.EventBus;
import static com.cs.animators.fragment.DetailSeriesFragment.PAGE_COUNT;

public class DetailSeriesTabFragment extends BaseFragment {

	@InjectView(R.id.detail_series_tab_gv) GridView mGvSeries ;
	
	private List<VideoDetailSeries> mSeriesData ;
	
	private VideoDetail mVideoDetail ;
	
	private int mCurrentPage ;
	
	private int mTotalPage ;
	
	private int mTotalVideoNum ;
	
	private SeriesAdapter mAdapter ;
	
	private int mLastPlaySeries = -1;
	
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
		
		mAdapter = new SeriesAdapter(getActivity(), mLastPlaySeries ,mCurrentPage, mSeriesData);
		mGvSeries.setAdapter(mAdapter);
		
	}
	
	@Override
	protected void getExtra(Bundle arguments) {
		if(arguments != null){
			mCurrentPage = arguments.getInt(DetailSeriesFragment.CURRENT_PAGER);
			mLastPlaySeries = arguments.getInt(DetailSeriesFragment.LAST_PLAY_SERIES);
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
		
		//拼接请求视频播放地址的url
		String videoId = mVideoDetail.getVideoId();
		String id = detailSeries.getId() ;
		RequestParams params = new RequestParams();
		params.put("m","Api");
		params.put("a", "play");
		params.put("ios", "1");
		params.put("format", "2");
		params.put("id",id);
		params.put("video_id", videoId);
		String loadVideoAddressUrl = JHttpClient.getUrlWithQueryString(Constants.host, params);
		
		VideoPlayRecord record = null ;
		if(CommonUtil.isNumber(id)){
			record = DaoFactory.getVideoRecordInstance(getActivity()).queryVideoPlayRecord(videoId,Long.parseLong(id));
		}
		
		Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
		intent.putExtra(VideoPlayActivity.VIDEO_NAME, detailSeries.getName());
		intent.putExtra(VideoPlayActivity.LOAD_VIDEO_URL, loadVideoAddressUrl);
		intent.putExtra(VideoPlayActivity.VIDEO_EXTRA, (mCurrentPage * PAGE_COUNT + position + 1)+"");
		if(mVideoDetail != null){
			intent.putExtra(VideoPlayActivity.ALL_VIDEO, mVideoDetail);
		}
		if(record != null){
			intent.putExtra(VideoPlayActivity.VIDEO_RECORD, record.getPlayRecord());
		}
		getActivity().startActivity(intent);
		
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
	
	public void onEventMainThread(PlayRecordEvent event){
		String series = event.getExtra();
		if(CommonUtil.isNumber(series)){
			mLastPlaySeries = Integer.parseInt(event.getExtra());
			
			int startPositin = mCurrentPage * PAGE_COUNT + 1;
			int endPostion = startPositin + PAGE_COUNT ;
			if(mLastPlaySeries >= startPositin && mLastPlaySeries <= endPostion){
				mAdapter.setLastPlaySeries(mLastPlaySeries);
			}

		}
	}

}
