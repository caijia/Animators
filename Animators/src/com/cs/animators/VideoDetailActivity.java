package com.cs.animators;

import java.util.ArrayList;
import java.util.List;
import io.vov.vitamio.utils.StringUtils;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.InjectView;
import com.cs.animators.base.BaseActivity;
import com.cs.animators.constants.Constants;
import com.cs.animators.dao.bean.VideoPlayRecord;
import com.cs.animators.dao.service.DaoFactory;
import com.cs.animators.entity.VideoDetail;
import com.cs.animators.eventbus.PlayRecordEvent;
import com.cs.animators.fragment.DetailIntroFragment;
import com.cs.animators.fragment.DetailSeriesFragment;
import com.cs.animators.fragment.DetailSimilarFragment;
import com.cs.animators.fragment.HotFragment;
import com.cs.animators.view.FloatScrollView;
import com.cs.animators.view.FloatScrollView.OnScrollListener;
import com.cs.cj.http.httplibrary.RequestParams;
import com.cs.cj.http.work.JDataCallback;
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
		mActionBar.setDisplayHomeAsUpEnabled(true);
		getExtra();
		
		//查找播放记录
		VideoPlayRecord lastPlayRecord = DaoFactory.getVideoRecordInstance(mContext).queryLastPlayRecord(mVideoId);
		if(lastPlayRecord != null)
		{
			mTxtPlayRecord.setVisibility(View.VISIBLE);
			String note = "上次播放至第" + lastPlayRecord.getSeries() + "集" + StringUtils.generateTime(lastPlayRecord.getPlayRecord());
			mTxtPlayRecord.setText(note);
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
		DetailSimilarFragment similar = new DetailSimilarFragment();
		
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

	private void getExtra(){
		Bundle bundle = getIntent().getExtras();
		if(bundle != null)
		{
			mVideoId = bundle.getString(HotFragment.VIDEO_ID);
		}
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		finish();
		return super.getSupportParentActivityIntent();
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
		VideoPlayRecord playRecord = event.getPlayRecord();
		String note = "上次播放至第" + playRecord.getSeries() + "集" + StringUtils.generateTime(playRecord.getPlayRecord());
		mTxtPlayRecord.setText(note);
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

}
