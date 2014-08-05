package com.cs.animators;

import io.vov.vitamio.utils.StringUtils;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.cs.animators.fragment.HotFragment;
import com.cs.animators.fragment.VideoDetailFragment;
import com.cs.cj.http.httplibrary.RequestParams;
import com.cs.cj.http.work.JDataCallback;
import com.cs.cj.http.work.Response;
import com.cs.cj.service.FragmentService;
import com.cs.cj.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.greenrobot.event.EventBus;

public class VideoDetailActivity extends BaseActivity {

	@InjectView(R.id.detail_img_bg) ImageView mImgBg ;
	@InjectView(R.id.detail_img_cover) ImageView mImgCover ;
	@InjectView(R.id.detail_txt_name) TextView mTxtName ;
	@InjectView(R.id.detail_txt_category) TextView mTxtCategory ;
	@InjectView(R.id.detail_txt_year) TextView mTxtYear ;
	@InjectView(R.id.detail_txt_history) TextView mTxtPlayRecord ;
	@InjectView(R.id.detail_btn_play) Button mBtnPlay ;
	@InjectView(R.id.detail_txt_outline) Button mBtnOutline ;
	
	private VideoDetail mVideoDetail ;
	
	private String mVideoId ;
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.activity_video_detail);
	}

	@Override
	protected void processLogic() {
		
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
				
				//将VideoDetailFragment加入进来
				FragmentService.getInstance().switchToFragment(mContext, R.id.detail_fragment_content, new VideoDetailFragment(), null);
			}
		});
	}
	
	protected void bindData() {
		mTxtName.setText(mVideoDetail.getName());
		mTxtCategory.setText("类型："+mVideoDetail.getCategory());
		mTxtYear.setText("评分："+mVideoDetail.getScore());
		
		ImageLoader.getInstance().displayImage(mVideoDetail.getCover(), mImgBg, ImageLoaderUtil.roundImageLoaderOptions(0));
		ImageLoader.getInstance().displayImage(mVideoDetail.getCover(), mImgCover, ImageLoaderUtil.roundImageLoaderOptions(0));
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
		VideoPlayRecord playRecord = event.getPlayRecord();
		String note = "上次播放至第" + playRecord.getSeries() + "集" + StringUtils.generateTime(playRecord.getPlayRecord());
		mTxtPlayRecord.setText(note);
	}
	
}
