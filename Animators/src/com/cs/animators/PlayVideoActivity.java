package com.cs.animators;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.os.Bundle;
import android.text.TextUtils;
import butterknife.InjectView;

import com.cs.animators.base.BaseActivity;
import com.cs.animators.constants.Constants;
import com.cs.animators.entity.PlayVideo;
import com.cs.animators.util.CommonUtil;
import com.cs.cj.http.httplibrary.RequestParams;
import com.cs.cj.http.work.JDataCallback;
import com.cs.cj.http.work.Response;

public class PlayVideoActivity extends BaseActivity {

	@InjectView(R.id.playvideo_vv) VideoView mVideoView ;
	
	private String mVideoId ;
	private String mId ;
	
	public static final String VIDEO_ID = "video_id";
	public static final String ID = "id";
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.activity_play_video);
		if (!LibsChecker.checkVitamioLibs(this))
			return;
	}

	@Override
	protected void processLogic() {
		mActionBar.hide();
		requestPlayUrl();
		
	}

	////m=Api&a=play&ios=1&id=520082&video_id=4271&format=2
	private void requestPlayUrl() {
		RequestParams params = new RequestParams();
		params.put("m","Api");
		params.put("a", "play");
		params.put("ios", "1");
		params.put("format", "2");
		params.put("id",mId);
		params.put("video_id", mVideoId);
		
		get(Constants.host, params, PlayVideo.class, new JDataCallback<PlayVideo>() {

			@Override
			public void onSuccess(Response<PlayVideo> data) {
				String url = data.getResult().getUrl();
				playVideo(url);
			}
		});
	}

	private void playVideo(String url) {
		if(!TextUtils.isEmpty(url))
		{
			mVideoView.setVideoPath(url);
			mVideoView.setMediaController(new MediaController(this));
			mVideoView.requestFocus();
			mVideoView.setOnPreparedListener(new OnPreparedListener() {
				
				@Override
				public void onPrepared(MediaPlayer mp) {
					mp.setPlaybackSpeed(1.0f);
				}
			});
		}
		else
		{
			CommonUtil.showMessage(mContext, "不能播放此视频!");
		}
	}
	
	@Override
	public void getExtra(Bundle bundle){
		if(bundle !=  null)
		{
			mVideoId = bundle.getString(VIDEO_ID);
			mId = bundle.getString(ID);
		}
	}
	
	
}
