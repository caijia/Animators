package com.cs.animators;

import java.util.ArrayList;
import java.util.List;
import io.vov.vitamio.utils.StringUtils;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.OnClick;
import com.cs.animators.base.BaseActivity;
import com.cs.animators.constants.Constants;
import com.cs.animators.dao.bean.VideoPlayRecord;
import com.cs.animators.dao.service.DaoFactory;
import com.cs.animators.entity.HotItem;
import com.cs.animators.entity.VideoDetail;
import com.cs.animators.entity.VideoDetailSeries;
import com.cs.animators.eventbus.PlayRecordEvent;
import com.cs.animators.fragment.DetailIntroFragment;
import com.cs.animators.fragment.DetailSeriesFragment;
import com.cs.animators.fragment.HotFragment;
import com.cs.animators.fragment.SearchFragment;
import com.cs.animators.util.CommonUtil;
import com.cs.animators.view.FloatScrollView;
import com.cs.animators.view.FloatScrollView.OnScrollListener;
import com.cs.cj.http.httplibrary.RequestParams;
import com.cs.cj.http.work.JDataCallback;
import com.cs.cj.http.work.JHttpClient;
import com.cs.cj.http.work.Response;
import com.cs.cj.util.ImageLoaderUtil;
import com.cs.cj.view.FragmentTabAdapter;
import com.cs.cj.view.PagerTabStrip;
import com.nostra13.universalimageloader.core.ImageLoader;
import de.greenrobot.event.EventBus;

public class VideoDetailActivity extends BaseActivity implements OnScrollListener {

	@InjectView(R.id.detail_img_bg) ImageView mImgBg ;
	@InjectView(R.id.detail_img_cover) ImageView mImgCover ;
	@InjectView(R.id.detail_txt_name) TextView mTxtName ;
	@InjectView(R.id.detail_txt_category) TextView mTxtCategory ;
	@InjectView(R.id.detail_txt_year) TextView mTxtYear ;
	@InjectView(R.id.detail_txt_history) TextView mTxtPlayRecord ;
	@InjectView(R.id.detail_btn_play) Button mBtnPlay ;
	@InjectView(R.id.detail_txt_outline) Button mBtnOutline ;
	
	@InjectView(R.id.video_detail_scrollview)FloatScrollView mScrollView ;
	
	@InjectView(R.id.video_detail_indicator)PagerTabStrip mPtsIndicator ;
	
	//indicator 的镜像文件
	@InjectView(R.id.video_detail_mirror_indicator)View mMirrorIndicator ;
	
	private VideoDetail mVideoDetail ;
	
	private String mVideoId ;
	
	private String[] mIndicatorTitle ;
	
	private List<Fragment> mFragmentTabs ;
	
	private String mVideoName ;
	
	private VideoPlayRecord mLastPlayRecord ;
	
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.activity_video_detail);
	}

	@Override
	protected void processLogic() {
		
		mScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				scroll(mScrollView.getScrollY());
			}
		});
		
		mIndicatorTitle = getResources().getStringArray(R.array.detail_indicator_title);
		
		//onCreate的时候注册  onDestroy的时候销毁
		EventBus.getDefault().register(this);
		mActionBar.setTitle(TextUtils.isEmpty(mVideoName) ? "动漫家" : mVideoName);
		
		//查找播放记录
		mLastPlayRecord = DaoFactory.getVideoRecordInstance(mContext).queryLastPlayRecord(mVideoId);
		if(mLastPlayRecord != null){
			mTxtPlayRecord.setVisibility(View.VISIBLE);
			String note = "上次播放至第" + mLastPlayRecord.getSeries() + "集" + StringUtils.generateTime(mLastPlayRecord.getPlayRecord());
			mTxtPlayRecord.setText(note);
			mBtnPlay.setText("继续");
		}
		
		loadData();
		mScrollView.SetOnScrollListener(this);
	}
	
	public VideoDetail getVideoDetail(){
		return mVideoDetail ;
	}
	
	private void loadData() {
		RequestParams params = new RequestParams();
		params.put("m", "Api");
		params.put("a", "getInfo");
		params.put("video_id", mVideoId);
		get(Constants.host, params, VideoDetail.class, new JDataCallback<VideoDetail>() {

			@Override
			public void onSuccess(Response<VideoDetail> data) {
				mVideoDetail = data.getResult() ;
				bindData();
			}
		});
	}
	
	protected void initFragmentTabs() {
		mFragmentTabs = new ArrayList<Fragment>();
		
		DetailSeriesFragment series = new DetailSeriesFragment();
		DetailIntroFragment intro = new DetailIntroFragment();
		
		SearchFragment similar = new SearchFragment();
		Bundle args = new Bundle();
		String searchWord = mVideoDetail.getName();
		args.putString(MainActivity.SEARCH_WORD, searchWord.length() > 1 ? searchWord.substring(0, 2) :searchWord);
		similar.setArguments(args);
		
		mFragmentTabs.add(series);
		mFragmentTabs.add(similar);
		mFragmentTabs.add(intro);
		
	}

	protected void bindData() {
		mTxtName.setText(mVideoDetail.getName());
		mTxtCategory.setText("类型："+mVideoDetail.getCategory());
		mTxtYear.setText("评分："+mVideoDetail.getScore());
		
		ImageLoader.getInstance().displayImage(mVideoDetail.getCover(), mImgBg, ImageLoaderUtil.roundImageLoaderOptions(0));
		ImageLoader.getInstance().displayImage(mVideoDetail.getCover(), mImgCover, ImageLoaderUtil.roundImageLoaderOptions(0));
		
		initFragmentTabs();
		mPtsIndicator.setAdapter(new VideoDetailTabFragment(getSupportFragmentManager()));
	}

	@Override
	public void getExtra(Bundle bundle){
		if(bundle != null)
		{
			mVideoId = bundle.getString(HotFragment.VIDEO_ID);
			mVideoName = bundle.getString("video_name");
		}
	}
	
	@Override
	protected boolean displayHomeAsUpEnabled() {
		return true;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
	
	//当播放退出后 要将播放记录回传
	public void onEventMainThread(PlayRecordEvent event)
	{
		mTxtPlayRecord.setVisibility(View.VISIBLE);
		long playRecord = event.getPlayRecord();
		long duration = event.getDuration();
		String extra = event.getExtra();
		int series = Integer.parseInt(extra);
		String note = "上次播放至第" + series + "集" + StringUtils.generateTime(playRecord);
		mTxtPlayRecord.setText(note);
		
		//保存播放记录
		VideoDetailSeries detailSeries = mVideoDetail.getEpisode().get(series - 1);
		VideoPlayRecord record = new VideoPlayRecord(Long.parseLong(detailSeries.getId()),mVideoId, playRecord, series, System.currentTimeMillis(), duration,detailSeries.getName());
		mLastPlayRecord = record ;
		mBtnPlay.setText("继续");
		DaoFactory.getVideoRecordInstance(this).saveOrUpdate(record);
	}
	
	private class VideoDetailTabFragment extends FragmentTabAdapter{
		
		public VideoDetailTabFragment(FragmentManager manager) {
			super(manager);
		}

		@Override
		public int getContainerId() {
			return R.id.detail_fragment_content;
		}

		@Override
		public int getCount() {
			return mIndicatorTitle.length;
		}

		@Override
		public Fragment getItem(int position) {
			return mFragmentTabs.get(position);
		}

		@Override
		public String getPagetTitle(int position) {
			return mIndicatorTitle[position];
		}
		
	}

	
	//Scroll Y 轴上滚动的距离
	@Override
	public void scroll(int scrollY) {
		int indicatorToParentTop = Math.max(scrollY, mMirrorIndicator.getTop());
		mPtsIndicator.layout(0, indicatorToParentTop, mPtsIndicator.getWidth(),
				indicatorToParentTop + mPtsIndicator.getHeight());
	}
	
	private boolean mCollect ;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.video_detail_menu, menu);
		HotItem item = DaoFactory.getVideoCollectInstance(mContext).query(mVideoId);
		if(item == null){
			mCollect = false ;
			menu.findItem(R.id.action_collect).setTitle("收藏");
		}else{
			mCollect = true ;
			menu.findItem(R.id.action_collect).setTitle("取消收藏");
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.action_collect).setTitle(mCollect ? "取消收藏":"收藏");
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_collect:
			if(mCollect){
				DaoFactory.getVideoCollectInstance(mContext).delete(mVideoId);
				CommonUtil.showMessage(mContext, "已取消收藏");
				mCollect = false ;
			}else{
				if(mVideoDetail != null){
					HotItem videoCollect = new HotItem(mVideoId,
							mVideoDetail.getName(), mVideoDetail.getCover(),
							mVideoDetail.getCategory(), mVideoDetail.getScore(),
							mVideoDetail.getCurNum(), mVideoDetail.getTotalNum(),
							mVideoDetail.getUpdateTime());
					DaoFactory.getVideoCollectInstance(mContext).save(videoCollect);
					CommonUtil.showMessage(mContext, "收藏成功");
					mCollect = true ;
				}
			}
			break;

		default:
			break;
		}
		//将调用onPrepareOptionsMenu()
		ActivityCompat.invalidateOptionsMenu(this);
		return super.onOptionsItemSelected(item);
	}
	
	
	@OnClick(R.id.detail_btn_play)
	void onClickPlay(){
		
		if(mLastPlayRecord != null){
			//继续播放
			continuePlay(true);
		}else{
			//播放第一集
			continuePlay(false);
		}
	}

	private void continuePlay(boolean continueplay) {
		
		if(mVideoDetail == null){
			return ;
		}
		
		//拼接请求视频播放地址的url
		String videoId = mVideoDetail.getVideoId();
		String id = continueplay ? mLastPlayRecord.getId() + "" : mVideoDetail.getEpisode().get(0).getId();
		
		RequestParams params = new RequestParams();
		params.put("m","Api");
		params.put("a", "play");
		params.put("ios", "1");
		params.put("format", "2");
		params.put("id",id );
		params.put("video_id", videoId);
		String loadVideoAddressUrl = JHttpClient.getUrlWithQueryString(Constants.host, params);
		
		Intent intent = new Intent(this, VideoPlayActivity.class);
		intent.putExtra(VideoPlayActivity.VIDEO_NAME, continueplay ? mLastPlayRecord.getVideoName() : mVideoDetail.getEpisode().get(0).getName());
		intent.putExtra(VideoPlayActivity.LOAD_VIDEO_URL, loadVideoAddressUrl);
		intent.putExtra(VideoPlayActivity.VIDEO_EXTRA, continueplay ? mLastPlayRecord.getSeries()+"" : "1");
		intent.putExtra(VideoPlayActivity.ALL_VIDEO, mVideoDetail);
		intent.putExtra(VideoPlayActivity.VIDEO_RECORD, continueplay ? mLastPlayRecord.getPlayRecord() : 0);
		startActivity(intent);
	}

}
