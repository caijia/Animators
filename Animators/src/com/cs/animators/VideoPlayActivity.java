package com.cs.animators;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnErrorListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.MediaPlayer.OnSeekCompleteListener;
import io.vov.vitamio.MediaPlayer.OnVideoSizeChangedListener;
import io.vov.vitamio.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.OnClick;

import com.cs.animators.base.BaseActivity;
import com.cs.animators.entity.PlayVideo;
import com.cs.animators.entity.VideoDetail;
import com.cs.animators.eventbus.PlayRecordEvent;
import com.cs.animators.eventbus.PlayerSizeEvent;
import com.cs.animators.eventbus.SelectSeriesEvent;
import com.cs.animators.fragment.PlayerSeriesFragment;
import com.cs.animators.fragment.PlayerSettingFragment;
import com.cs.animators.util.CommonUtil;
import com.cs.animators.util.PlayerUtils;
import com.cs.animators.view.VerticalSeekBar;
import com.cs.cj.http.utils.NetWorkUtil;
import com.cs.cj.http.work.JDataCallback;
import com.cs.cj.http.work.JHttpClient;
import com.cs.cj.http.work.Response;

import de.greenrobot.event.EventBus;

public class VideoPlayActivity extends BaseActivity implements Callback, OnPreparedListener, OnBufferingUpdateListener, OnCompletionListener, OnVideoSizeChangedListener, OnSeekCompleteListener, OnInfoListener, OnErrorListener {
	
	
	//最外层的布局
	@InjectView(R.id.content) RelativeLayout mContent ;
	
	@InjectView(R.id.rlayout_loading) RelativeLayout mLoadingLayout ;
	
	//顶部布局控件
	
	@InjectView(R.id.player_title) TextView mTitle ;
	
	@InjectView(R.id.txt_setting) TextView mSetting ;
	
	@InjectView(R.id.txt_series) TextView mSeries ;
	
	@InjectView(R.id.rlayout_title)RelativeLayout mControllerTop ;
	
	
	//底部布局控件
	
	@InjectView(R.id.txt_brightness)TextView mBrightness ;
	
	@InjectView(R.id.txt_play)TextView mPlay ;
	
	@InjectView(R.id.txt_volume)TextView mVolume ;
	
	@InjectView(R.id.txt_cur_time)TextView mCurPlayTime ;
	
	@InjectView(R.id.txt_total_time)TextView mTotalPlayTime ;
	
	@InjectView(R.id.seekbar_play_progress)SeekBar mPlayProgress ;
	
	@InjectView(R.id.seekbar_brightness)VerticalSeekBar mBrightnessProgress ;
	
	@InjectView(R.id.seekbar_volume)VerticalSeekBar mVolumeProgress ;
	
	@InjectView(R.id.rlayout_brightness)RelativeLayout mControllerBrightness ;
	
	@InjectView(R.id.rlayout_volume)RelativeLayout mControllerVolume ;
	
	@InjectView(R.id.rlayout_player)RelativeLayout mControllerBottom ;
	
	
	//隐藏的手势控件
	
	@InjectView(R.id.geture_tv_brightness_percentage)TextView mGestureBrightness ;
	
	@InjectView(R.id.geture_tv_volume_percentage)TextView mGestureVolume ;
	
	@InjectView(R.id.geture_tv_progress_time)TextView mGesturePlay ;
	
	@InjectView(R.id.buffer_txt_percentage)TextView mBufferProgress ;
	
	@InjectView(R.id.txt_pause_all)TextView mPauseAll ;
	
	
	//展示视频的控件
	
	@InjectView(R.id.surface)SurfaceView mSurface ;
	
	//视频播放的变量
	
	private SurfaceHolder mHolder;
	
	private MediaPlayer mediaPlayer ;
	
	private boolean mPrepare ;
	
	private boolean mError ;
	
	private boolean mkownVideoSize ;
	
	private int maxVolume ;
	
	private long curPlayTime ;
	
	private long totalPlayTime ;
	
	private AudioManager audioManager ;
	
	private Timer timer = new Timer();
	
	//手势操作的变量
	
	private int mSlop ;
	
	
	//控制视频消失和显示
	
	private Handler handler = new Handler() ;
	
	public static int DISMISS_TIME = 5000 ;
	
	private List<RelativeLayout> mControllerLayouts = new ArrayList<RelativeLayout>();
	
	private List<TextView> mGestureViews = new ArrayList<TextView>();
	
	//视频缓存时的变量
	
	private AnimationDrawable mLoadingAnimation ;
	
	//是否按Home键
	private boolean mHomeKey ;
	
	//传进来是url是要加载一个视频地址  
	private String mUrl ;
	
	private String mVideoName ="";
	
	private String mPath ;
	
	//透传参数(这里当做当前的剧集)
	private String mExtra ;
	
	private long mRecord ;
	
	private VideoDetail mAllVideo ;
	
	public static final String VIDEO_NAME = "video_name";
	public static final String VIDEO_PATH = "video_path";
	public static final String LOAD_VIDEO_URL = "load_video_url";
	public static final String VIDEO_EXTRA = "video_extra";
	public static final String VIDEO_RECORD = "video_record";
	public static final String ALL_VIDEO = "all_video";
	public static final String CURRENT_SERIES = "current_series";
	
	
	@Override
	protected void loadLayout() {
		//播放视频时不休眠
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		setContentView(R.layout.activity_play_video);
	}

	@Override
	protected void processLogic() {
		
		if(!LibsChecker.checkVitamioLibs(this))
		{
			return ;
		}
		
		EventBus.getDefault().register(this);
		
		//显示加载视频的动画
		initLoadingAnimation();
		startLoadingAnimation();
		showLoadingLayout();
		
		//Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住
		//设置成这样的目的是隐藏和显示状态栏的时候 视频不会随状态栏显示和隐藏时拉伸视频
//		mContent.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		
		//由于显示的时候Activity上面是覆盖的状态栏 所有顶部的布局应该在状态栏的下面
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mControllerTop.getLayoutParams();
		params.topMargin = CommonUtil.getStatusBarHeight(mContext);
		mControllerTop.setLayoutParams(params);
		
		mControllerLayouts.add(mControllerTop);
		mControllerLayouts.add(mControllerBottom);
		mControllerLayouts.add(mControllerBrightness);
		mControllerLayouts.add(mControllerVolume);
		
		mActionBar.hide();
		hideControllerLayout();
		
		mGestureViews.add(mGestureBrightness);
		mGestureViews.add(mGestureVolume);
		mGestureViews.add(mGesturePlay);
		
		mHolder = mSurface.getHolder();
		mHolder.addCallback(this);
		mHolder.setFormat(PixelFormat.RGBA_8888);
		mHolder.setKeepScreenOn(true);
		
		ViewConfiguration configuration = ViewConfiguration.get(this);
		mSlop = configuration.getScaledTouchSlop();
		
		//音量
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		mVolumeProgress.setMax(maxVolume);
		mVolumeProgress.setProgressAndThumb(curVolume);
		
		//亮度
		mBrightnessProgress.setMax(255 - 30);
		int screenBrightness = PlayerUtils.getScreenBrightness(mContext);
		setScreenBrightness(screenBrightness);
		mBrightnessProgress.setProgress(screenBrightness);
		
		//每隔一秒更新播放进度
		timer.schedule(playerProgressTask, 0, 1000);
		
		//设置SeekBar监听事件
		JSeekBarChangeListener seekbarListener = new JSeekBarChangeListener();
		mBrightnessProgress.setOnSeekBarChangeListener(seekbarListener);
		mVolumeProgress.setOnSeekBarChangeListener(seekbarListener);
		mPlayProgress.setOnSeekBarChangeListener(seekbarListener);
		
		//没有传剧集过来
		if(mAllVideo == null){
			mSeries.setVisibility(View.GONE);
		}

	}
	
	
	public void setScreenBrightness(int screenBrightness) {
		LayoutParams params = getWindow().getAttributes();
		if(screenBrightness < 30)
		{
			screenBrightness = 30 ;
		}
		params.screenBrightness = screenBrightness / (float) 255 ;
		getWindow().setAttributes(params);
		PlayerUtils.setScreenBrightness(this, screenBrightness);
	}
	
	private final class JSeekBarChangeListener implements OnSeekBarChangeListener{

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
			switch (seekBar.getId()) {
			case R.id.seekbar_brightness:
				setScreenBrightness(progress);
				break;

			case R.id.seekbar_volume:
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
				int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
				mVolumeProgress.setProgressAndThumb(curVolume);
				break ;
				
			case R.id.seekbar_play_progress:
				mCurPlayTime.setText(StringUtils.generateTime(progress));
				break ;
			default:
				break;
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			handler.removeCallbacks(dismissRunnable);
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			switch (seekBar.getId()) {
			case R.id.seekbar_play_progress:
				if(mediaPlayer != null&& !mError)
				{
					mediaPlayer.seekTo(seekBar.getProgress());
					
				}
				break ;
				
			case R.id.seekbar_brightness:
				setScreenBrightness(seekBar.getProgress());
				break;

			case R.id.seekbar_volume:
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, seekBar.getProgress(), 0);
				int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
				mVolumeProgress.setProgressAndThumb(curVolume);
				break ;
			}
			handler.postDelayed(dismissRunnable, DISMISS_TIME);
		}
		
	}
	

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if(TextUtils.isEmpty(mPath))
		{
			if(!TextUtils.isEmpty(mUrl)){
				//请求一个视频地址
				requestVideoAddress();
			}
		}
		else
		{
			if(mHomeKey && canPlay()){
				mediaPlayer.setDisplay(mSurface.getHolder());
				startPlay();
			}else{
				playVideo();
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width ,int height) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mHomeKey = true ;
		//保存播放记录
		savePlayRecord();
	}

	/**
	 * 这个方法只发出保存播放记录的事件 具体保存操作由VideoDetailActivity 里面保存
	 */
	private void savePlayRecord() {
		if(canPlay()){
			long record = mediaPlayer.getCurrentPosition();
			long duration = mediaPlayer.getDuration();
			EventBus.getDefault().post(new PlayRecordEvent(record,duration,mExtra));
		}
	}
	
	
	private void requestVideoAddress() {
		JHttpClient.getFromServer(this,mUrl, null, PlayVideo.class, new JDataCallback<PlayVideo>() {

			@Override
			public void onSuccess(Response<PlayVideo> data) {
				mPath = data.getResult().getUrl();
				if(!TextUtils.isEmpty(mPath)){
					playVideo();
				}else{
					showErrorDialog("抱歉,加载视频地址失败!","确定",PLAY_VIDEO_ERROR);
				}
				
			}
			
			@Override
			public void onFailure(String responseString) {
				super.onFailure(responseString);
				stopLoadingAnimation();
				mBufferProgress.setVisibility(View.GONE);
				showErrorDialog("抱歉,加载视频地址失败!","确定",PLAY_VIDEO_ERROR);
			}
		});
	}
	
	private void playVideo() {
		clearState();
		try {
			
			mediaPlayer = new MediaPlayer(this);
			mediaPlayer.setDataSource(mPath);
			mediaPlayer.setDisplay(mHolder);
			mediaPlayer.prepareAsync();
			
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.setOnVideoSizeChangedListener(this);
			mediaPlayer.setOnSeekCompleteListener(this);
			mediaPlayer.setOnInfoListener(this);
			mediaPlayer.setOnErrorListener(this);
			
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			
		} catch (Exception e) {
			e.printStackTrace();
			mError = true ;
		}
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		mError = true ;
		showErrorDialog();
		return true;
	}

	private void showErrorDialog(){
		showErrorDialog("抱歉,不能播放此视频!","确定",PLAY_VIDEO_ERROR);
	}
	
	public static final int NETWORK_CHANGE = 1 ;
	public static final int PLAY_VIDEO_ERROR = 2 ;
	
	private void showErrorDialog(String errorMessage,String positionText,final int errorType) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(errorMessage);
		builder.setTitle("视频");
		builder.setPositiveButton(positionText,new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				VideoPlayActivity.this.finish();
				builder.create().dismiss();
			}
		} );
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if(errorType == NETWORK_CHANGE){
					//继续播放
					startPlay();
				}
			}
		});
		builder.create().show();
	}

	private boolean mBuffering ;
	
	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		switch (what) {
        case MediaPlayer.MEDIA_INFO_BUFFERING_START:
        	mBuffering = true ;
        	stopLoadingAnimation();
        	stopPlay();
        	startLoadingAnimation();
            break;
        case MediaPlayer.MEDIA_INFO_BUFFERING_END:
        	startPlay();
        	mBuffering = false ;
        	stopLoadingAnimation();
			hideLoadingLayout();
            break;
        case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
        	setBufferPercentage(extra,mBufferPercent);
            break;
        }
        return true;
	}

	@Override
	public void onSeekComplete(MediaPlayer mp) {
		
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		if(width != 0 && height != 0 ){
			mkownVideoSize = true ;
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		mCurPlayTime.setText(StringUtils.generateTime(totalPlayTime));
	}

	private int mBufferPercent ;
	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		mBufferPercent = percent ;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		mPrepare = true ;
		startPlayVideo();
	}

	private void startPlayVideo() {
		
		//开始播放时隐藏视频加载时的布局 
		mLoadingLayout.setVisibility(View.GONE);
		
		//设置Video name
		mTitle.setText(mVideoName);
		
		mHolder.setFixedSize(CommonUtil.getWidthMetrics(mContext), CommonUtil.getHeightMetrics(mContext));
		startPlay();
		
		seekToRecord(mRecord);
		
		//设置时间
		totalPlayTime = mediaPlayer.getDuration();
		String generateTime = StringUtils.generateTime(totalPlayTime);
		mTotalPlayTime.setText(generateTime);
		
		//设置进度
		mPlayProgress.setMax((int)totalPlayTime);
		
		//设置controller 消失还是显示
		handler.postDelayed(dismissRunnable, DISMISS_TIME);
	}
	
	private boolean canPlay(){
		if(mediaPlayer != null && mkownVideoSize && mPrepare && !mError){
			return true ;
		}
		return false ;
	}
	
	private void startPlay(){
		startPlay(false);
	}
	
	private void startPlay(boolean buffering){
		if(buffering){
			mPlay.setCompoundDrawablesWithIntrinsicBounds(R.drawable.selector_player_pause, 0, 0, 0);
			mPauseAll.setVisibility(View.GONE);
			mBufferProgress.setVisibility(View.VISIBLE);
		}else{
			if(canPlay()&& !mediaPlayer.isPlaying()){
				mediaPlayer.start();
				mPlay.setCompoundDrawablesWithIntrinsicBounds(R.drawable.selector_player_pause, 0, 0, 0);
				mPauseAll.setVisibility(View.GONE);
				mBufferProgress.setVisibility(View.GONE);
			}
		}
	}
	
	private void stopPlay(boolean buffering){
		if(buffering){
			mPlay.setCompoundDrawablesWithIntrinsicBounds(R.drawable.selector_player_start, 0, 0, 0);
			mPauseAll.setVisibility(View.VISIBLE);
			mBufferProgress.setVisibility(View.GONE);
		}else{
			if(mediaPlayer != null && !mError&& mediaPlayer.isPlaying()){
				mediaPlayer.stop();
				mPlay.setCompoundDrawablesWithIntrinsicBounds(R.drawable.selector_player_start, 0, 0, 0);
				mPauseAll.setVisibility(View.VISIBLE);
				mBufferProgress.setVisibility(View.GONE);
			}
		}
	}
	
	private void stopPlay(){
		stopPlay(false);
	}
	
	private void togglePlay(boolean buffering){
		if(buffering){
			if(mPauseAll.isShown()){
				startPlay(buffering);
			}else{
				stopPlay(buffering);
			}
		}else{
			if(mediaPlayer != null && !mError&& mediaPlayer.isPlaying()){
				stopPlay(buffering);
			}else{
				startPlay(buffering);
			}
		}
	}
	
	private void seekToRecord(long record){
		if(canPlay()){
			mediaPlayer.seekTo(record);
		}
	}
	
	private void release(){
		if(mediaPlayer != null && !mError){
			stopPlay();
			mediaPlayer.release();
			mediaPlayer = null ;
		}
	}
	
	private void clearState(){
		mError = false ;
		mkownVideoSize = false ;
		mPrepare = false ;
	}
	
	float startX , startY ;
	
	//前一个触摸点
	float preStartX , preStartY ;
	boolean isClick , firstScroll;
	
	public  int GESTURE_FLAG = 0 ;
	public static final int GESTURE_MODIFY_VOLUME = 1 ;
	public static final int GESTURE_MODIFY_BRIGTHNESS = 2 ;
	public static final int GESTURE_MODIFY_PROGRESS = 3 ;
	
	//表示调节音量或亮度 两边的触摸区域的宽度  (左边为 亮度  右边为音量)
	public static final int TOUCH_AREA_WIDTH = 300 ;
	
	public int TOUCH_AREA_FLAG = 0 ;
	public static final int TOUCH_AREA_VOLUME = 1 ;
	public static final int TOUCH_AREA_BRIGTHNESS = 2 ;
	
	public static final float RADIO = 2.5f ;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			startX = event.getRawX() ;
			startY = event.getRawY() ;
			preStartX = event.getRawX() ;
			preStartY = event.getRawY() ;
			
			isClick = true ;
			firstScroll = true ;
			//表示开始触摸的点在屏幕0-200的区域
			if(startX <= TOUCH_AREA_WIDTH)
			{
				TOUCH_AREA_FLAG = TOUCH_AREA_BRIGTHNESS  ;
			}
			if(startX >= PlayerUtils.getScreenWidthPixels(this) - TOUCH_AREA_WIDTH)
			{
				TOUCH_AREA_FLAG = TOUCH_AREA_VOLUME ;
			}
			break;
			
		case MotionEvent.ACTION_MOVE:
			
			float deltaX = event.getRawX() - preStartX;
			float deltaY = preStartY - event.getRawY() ;
			
			//从开始点到结束点的总滑动距离
			float slideX = event.getRawX() - startX ;
			float slideY = startY - event.getRawY() ;
			
			//手势滑动的最短距离.如果小于这个距离就不触发移动控件事件.
			if(Math.abs(slideX) > mSlop || Math.abs(slideY) > mSlop)
			{
				handler.removeCallbacks(dismissRunnable);
				isClick = false ;
			}
			
			if(firstScroll )
			{
				if(Math.abs(deltaY) >= Math.abs(deltaX) && (Math.abs(slideX) > mSlop || Math.abs(slideY) > mSlop) )
				{
					//判断是调整什么
					GESTURE_FLAG = TOUCH_AREA_FLAG == TOUCH_AREA_BRIGTHNESS ? GESTURE_MODIFY_BRIGTHNESS : TOUCH_AREA_FLAG == TOUCH_AREA_VOLUME ? GESTURE_MODIFY_VOLUME : 0;
					if(GESTURE_FLAG == GESTURE_MODIFY_BRIGTHNESS)
					{
						showCurGesture(mGestureBrightness);
					}
					if(GESTURE_FLAG == GESTURE_MODIFY_VOLUME)
					{
						showCurGesture(mGestureVolume);
					}
				}
				else if(Math.abs(deltaY) < Math.abs(deltaX) && (Math.abs(slideX) > mSlop || Math.abs(slideY) > mSlop) )
				{
					//表示调整进度
					GESTURE_FLAG = GESTURE_MODIFY_PROGRESS ;
					showCurGesture(mGesturePlay);
				}
			}
				
			if(GESTURE_FLAG == GESTURE_MODIFY_BRIGTHNESS)
			{
				LayoutParams params = getWindow().getAttributes();
				int curScreenBrightness = PlayerUtils.getScreenBrightness(this);
				if(deltaY >= RADIO)
				{
					curScreenBrightness += 10;
				}
				else if(deltaY < -RADIO)
				{
					curScreenBrightness -= 10 ;
				}
				
				if(curScreenBrightness > 255)
				{
					curScreenBrightness = 255;
				}
				
				if(curScreenBrightness < 30)
				{
					curScreenBrightness = 30 ;
				}
				
				float brightnessPerfect = (float) (curScreenBrightness-30) / (255-30) ;
				if(curScreenBrightness -30 > 0){
					params.screenBrightness = brightnessPerfect ;
				}
				else{
					params.screenBrightness = (float)curScreenBrightness / 255 ;
				}
				PlayerUtils.setScreenBrightness(this, curScreenBrightness);
				getWindow().setAttributes(params);
				mGestureBrightness.setText((int)(brightnessPerfect * 100) + "%");
				mBrightnessProgress.setProgressAndThumb(curScreenBrightness);
				
			}
			
			if(GESTURE_FLAG == GESTURE_MODIFY_VOLUME)
			{
				
				int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
				if(deltaY >= RADIO)
				{
					//向上滑动
					if(curVolume < maxVolume)
					{
						curVolume ++ ;
						mGestureVolume.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.play_gesture_volume, 0, 0);
					}
				}
				else if(deltaY < -RADIO)
				{
					//向下滑动
					if(curVolume > 0)
					{
						curVolume -- ;
						mGestureVolume.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.play_gesture_volume, 0, 0);
					}
					
					if(curVolume == 0)
					{
						//静音
						mGestureVolume.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.play_gesture_volume_no, 0, 0);
					}
				}
				mVolumeProgress.setProgressAndThumb(curVolume);
				int progress = curVolume * 100 / maxVolume ;
				mGestureVolume.setText(progress + "%");
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, curVolume, 0);
				
			}
			
			if(GESTURE_FLAG == GESTURE_MODIFY_PROGRESS)
			{
				if(canPlay())
				{
					curPlayTime = curPlayTime == 0 ? mediaPlayer.getCurrentPosition() : curPlayTime;
				}
				if(deltaX >= RADIO)
				{
					curPlayTime += 5*1000;
					mGesturePlay.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.play_gesture_forward, 0, 0);
				}
				else if(deltaX < -RADIO)
				{
					curPlayTime -= 5*1000 ;
					mGesturePlay.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.play_gesture_rewind, 0, 0);
				}
				
				//避免滑动到结束位置
				if(curPlayTime > totalPlayTime - 5*1000 )
				{
					curPlayTime = totalPlayTime - 5*1000 ;
				}
				
				if(curPlayTime < 0)
				{
					curPlayTime = 0 ;
				}
				
				mGesturePlay.setText(StringUtils.generateTime(curPlayTime)+"/"+StringUtils.generateTime(totalPlayTime));
				
			}
			
			//第一次Scroll结束
			if(GESTURE_FLAG != 0)
			{
				firstScroll = false ;
				hideControllerLayout();
			}
			preStartX = event.getRawX() ;
			preStartY = event.getRawY() ;
			break ;
			
		case MotionEvent.ACTION_UP:
			if(isClick)
			{
				if(!isShow())
				{
					showControllerLayout();
					resetDimissTask();
				}
				else
				{
					if(!isClickControllerLayout((int)startX,(int)startY))
					{
						hideControllerLayout();
						handler.removeCallbacks(dismissRunnable);
					}
					else
					{
						resetDimissTask();
					}
				}
			}
			else
			{
				handler.postDelayed(dismissRunnable , DISMISS_TIME);
			}
			
			//触摸改变进度后  手指松开的时候播放
			if(GESTURE_FLAG == GESTURE_MODIFY_PROGRESS && canPlay())
			{
				mediaPlayer.seekTo(curPlayTime);
			}
			
			//状态清除
			isClick = false ;
			GESTURE_FLAG = 0 ;
			TOUCH_AREA_FLAG = 0 ;
			startX = 0 ;
			startY = 0 ;
			hideAllGestureView();
			
			break ;

		default:
			break;
		}
		
		return super.onTouchEvent(event);
	}
	
	
	private Runnable dismissRunnable = new Runnable() {
		
		@Override
		public void run() {
			hideControllerLayout();
		}
	};


	private void hideControllerLayout() {
		for (RelativeLayout controller : mControllerLayouts) {
			controller.setVisibility(View.GONE);
		}
		mBrightness.setSelected(false);
		mVolume.setSelected(false);
//		if(Build.VERSION.SDK_INT >= 11 ){
//			getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
//		}
		full(true);
		
	}
	
	private void showControllerLayout(){
		for (RelativeLayout controller : mControllerLayouts) {
			controller.setVisibility(View.VISIBLE);
		}
		
		mControllerBrightness.setVisibility(View.GONE);
		mControllerVolume.setVisibility(View.GONE);
//		if(Build.VERSION.SDK_INT >= 14 ){
//			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//		}
		full(false);
	}
	
	private void showCurGesture(TextView curGesture){
		for (TextView gesture : mGestureViews) {
			if(curGesture == gesture){
				gesture.setVisibility(View.VISIBLE);
			}else{
				gesture.setVisibility(View.GONE);
			}
		}
	}
	
	private void hideAllGestureView(){
		for (TextView gesture : mGestureViews) {
			gesture.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 判断触摸的点是否在 控制 音量 播放 标题 的 layout 上 如果是点击的这三个Layout 则布局不消失 返回true
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isClickControllerLayout(int x , int y)
	{
		boolean isClick = false ;
		for (int i = 0; i < mControllerLayouts.size(); i++) {
			Rect clickRect = new Rect();  //表示该布局的点击区域
			mControllerLayouts.get(i).getHitRect(clickRect);
			if(clickRect.contains(x, y))
			{
				isClick = true ;
				break ;
			}
		}
		return isClick ;
	}
	
	/**
	 * 控制视频播放界面的布局是否显示 
	 * @return true 表示显示  false 表示隐藏
	 */
	private boolean isShow(){
		if(mControllerTop.isShown() && mControllerBottom.isShown())
		{
			return true ;
		}
		return false ;
	}
	
	
	/**
	 * 视频播放界面更新的定时器(播放进度)
	 */
	private TimerTask playerProgressTask = new TimerTask() {
		
		@Override
		public void run() {
			if(canPlay()&& mediaPlayer.isPlaying())
			{
				long position = mediaPlayer.getCurrentPosition();
				mPlayProgress.setProgress((int) position);
			}
		}
	};
	
	
	@OnClick(R.id.txt_brightness)
	void onClickBrightness(){
		if(isShow()){
			resetDimissTask();
			mControllerBrightness.setVisibility(mControllerBrightness.isShown() ? View.GONE : View.VISIBLE);
			mBrightness.setSelected(mControllerBrightness.isShown() ? true :false);
			mVolume.setSelected(false);
			mControllerVolume.setVisibility(View.GONE);
		}
	}
	
	@OnClick(R.id.txt_volume)
	void onClickVolume(){
		if(isShow())
		{
			resetDimissTask();
			mControllerVolume.setVisibility(mControllerVolume.isShown() ? View.GONE : View.VISIBLE);
			mVolume.setSelected(mControllerVolume.isShown() ? true :false);
			mBrightness.setSelected(false);
			
			//缓冲图片出现让中间的开始按钮消失
			mControllerBrightness.setVisibility(View.GONE);
		}
	}
	
	@OnClick(R.id.txt_pause_all)
	void onClickPauseAll(){
		startPlay(mBuffering);
	}
	
	
	@OnClick(R.id.txt_play)
	void onClickPlay(){
		resetDimissTask();
		togglePlay(mBuffering);
	}
	
	@OnClick(R.id.player_title)
	void onClickBack(){
		finish();
	}
	
	@OnClick(R.id.txt_setting)
	void onClickSetting(){
		PlayerSettingFragment setting = new PlayerSettingFragment();
		Bundle args = new Bundle();
		args.putInt(PlayerSettingFragment.SURFACEVIEW_SIZE, mVideoSize);
		setting.setArguments(args);
		setting.show(getSupportFragmentManager(), "settingDialog");
	}
	
	@OnClick(R.id.txt_series)
	void onClickSeries(){
		PlayerSeriesFragment series = new PlayerSeriesFragment();
		Bundle args = new Bundle();
		args.putParcelable(ALL_VIDEO, mAllVideo);
		args.putString(CURRENT_SERIES, mExtra);
		series.setArguments(args);
		series.show(getSupportFragmentManager(), "seriesDialog");
	}
	
	private void resetDimissTask(){
		handler.removeCallbacks(dismissRunnable);
		handler.postDelayed(dismissRunnable, DISMISS_TIME);
	}
	
	/**
	 * 开始加载或者缓存时的动画
	 */
	private void startLoadingAnimation(){
		if(mLoadingAnimation != null && !mLoadingAnimation.isRunning()){
			mBufferProgress.setVisibility(View.VISIBLE);
			mLoadingAnimation.start();
			mPauseAll.setVisibility(View.GONE);
		}
	}

	/**
	 * 停止缓存时的动画
	 */
	private void stopLoadingAnimation(){
		if(mLoadingAnimation != null && mLoadingAnimation.isRunning()){
			mLoadingAnimation.stop();
			mBufferProgress.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 只有在选集和进入播放页面时才显示背景
	 */
	private void showLoadingLayout() {
		mLoadingLayout.setVisibility(View.VISIBLE);
	}

	private void hideLoadingLayout() {
		mLoadingLayout.setVisibility(View.GONE);
	}
	
	private void initLoadingAnimation(){
		mLoadingAnimation = (AnimationDrawable) getResources().getDrawable(R.drawable.player_loading);
		mBufferProgress.setCompoundDrawablesWithIntrinsicBounds(null, mLoadingAnimation, null, null);
	}
	
	private void setBufferPercentage(int rate ,int percentage){
		if(mBufferProgress.isShown()){
			mBufferProgress.setText("("+rate+"KB/S)缓冲"+percentage + "%");
		}
	}
	
	private int mVideoSize ;
	
	//调整播放视频的窗口大小
	public void onEventMainThread(PlayerSizeEvent event) {
		mVideoSize = event.getVideoSize();
		int width = -1 ;
		int height = -1 ;
		switch (mVideoSize) {
		case PlayerSettingFragment.FULL_SCREEN:
			width = CommonUtil.getWidthMetrics(this);
			height = CommonUtil.getHeightMetrics(this);
			break;
		case PlayerSettingFragment.MEDIUM_SCREEN:
			width = (int) (CommonUtil.getWidthMetrics(this) * 0.75);
			height = (int) (CommonUtil.getHeightMetrics(this)* 0.75);
			break;
		case PlayerSettingFragment.SMALL_SCREEN:
			width = (int) (CommonUtil.getWidthMetrics(this) * 0.5);
			height = (int) (CommonUtil.getHeightMetrics(this)* 0.5);
			break;
		default:
			break;
		}
		if(width != -1 && height != -1)
		{
			mHolder.setFixedSize(width, height);
		}
		
	}
	
	
	//选集
	public void onEventMainThread(SelectSeriesEvent event){
		
		//选集之后要保存上一集的记录
		savePlayRecord();
		
		mUrl = event.getUrl() ;
		mVideoName = event.getVideoName();
		mExtra = event.getSeries();
		mRecord = event.getPlayRecord();
		release();
		showLoadingLayout();
		startLoadingAnimation();
		requestVideoAddress();
		
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		release();
		clearState();
		timer.cancel();
		stopLoadingAnimation();
		
		EventBus.getDefault().unregister(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		stopPlay();
	}
	
	@Override
	protected void getExtra(Bundle bundle) {
		if(bundle != null){
			mPath = bundle.getString(VIDEO_PATH);
			mUrl = bundle.getString(LOAD_VIDEO_URL);
			mVideoName = bundle.getString(VIDEO_NAME);
			mExtra = bundle.getString(VIDEO_EXTRA);
			mRecord = bundle.getLong(VIDEO_RECORD);
			mAllVideo = bundle.getParcelable(ALL_VIDEO);
		}
	}
	
	/**
	 * 动态显示和隐藏状态栏
	 * @param enable
	 */
	private void full(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
	
	
	private ConnectionChangeReceiver mConnectionChangeReceiver ;
	
	@Override
	protected void onStart() {
		super.onStart();
		mConnectionChangeReceiver = new ConnectionChangeReceiver();
		//android.net.wifi.WIFI_STATE_CHANGED
		IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mConnectionChangeReceiver, filter);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(mConnectionChangeReceiver);
	}
	
	private class ConnectionChangeReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			int netType = NetWorkUtil.getNetType(context);
			if(netType == ConnectivityManager.TYPE_MOBILE){
				//提示用户网络状态转为2G/3G/4G
				stopPlay();
				showErrorDialog("当前网络类型不是WIFI,是否退出?","退出",NETWORK_CHANGE);
			}else if(netType == NetWorkUtil.NO_NETWORK_TYPE){
				CommonUtil.showMessage(mContext, "网络已断开!");
			}
		}
		
	}
	

}
