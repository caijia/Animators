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
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.cs.animators.eventbus.PlayerSizeEvent;
import com.cs.animators.eventbus.SelectSeriesEvent;
import com.cs.animators.fragment.PlayerSeriesFragment;
import com.cs.animators.fragment.PlayerSettingFragment;
import com.cs.animators.util.CommonUtil;
import com.cs.animators.util.Utils;
import com.cs.animators.view.VerticalSeekBar;
import de.greenrobot.event.EventBus;

public class PlayVideoActivity extends ActionBarActivity implements Callback, OnPreparedListener, OnBufferingUpdateListener, OnCompletionListener, OnVideoSizeChangedListener, OnSeekCompleteListener, OnInfoListener, OnErrorListener {

	/*显示视频图像的控件*/
	private SurfaceView mSurfaceView ;
	
	private SurfaceHolder mHolder ;
	
	private MediaPlayer mediaPlayer ;
	
	/*视频播放的路径或者网络地址*/
	private String path = "http://120.37.140.44/youku/6572E7E8FA8367902647759C5/03102001005304269DABF21468DEFEF7C3B798-13C1-E224-A91B-E3823405B642.mp4";
	
	/*要播放的视频是否准备好播放*/
	private boolean mIsPrepare ;
	
	private boolean mIsError ;
	
	/*是否已经视频的大小*/
	private boolean mIsKnowVideoSize ;
	
	private TextView mTitle ;
	
	/*顶部标题栏布局*/
	private RelativeLayout mRlayoutTitleController ;
	
	/*音量的百分比*/
	private TextView mTxtVolumePercentage ; 
	
	/*播放进度*/
	private TextView mTxtPlayerProgressPercentage ; 
	
	/*亮度的百分比*/
	private TextView mTxtBrightness ; 
	
	/*控制播放的布局*/
	private RelativeLayout mRlayoutPlayerController ;
	
	/*控制音量的布局*/
	private RelativeLayout mRlayoutVolumeController ;
	
	/*控制播放进度的SeekBar*/
	private SeekBar mPlayerProgressBar ;
	
	/*显示视频播放的总时间的控件*/
	private TextView mTxtVideoPlayerTotalTime ;
	
	/*显示视频播放的当前时间控件*/
	private TextView mTxtVideoPlayCurTime; 
	
	/*视频播放的图片（播放/暂停）*/
	private ImageView mImgPlayState ;
	
	/*控制音量的SeekBar*/
	private VerticalSeekBar mSkbVolume ;
	
	private TextView mTxtSetting ;
	
	private TextView mTxtSeries ;
	
	private ImageView mImgBattery ;
	
	private TextView mTxtSystemTime ;
	
	private TextView mTxtBufferPercentage ;
	
	/*控制播放布局的显示和隐藏的Handler*/
	private Handler playerHandler = new Handler();
	
	/*控制布局的显示和隐藏的时间*/
	public static final long CONTROLLER_DISMISS_TIME = 5000 ;
	
	private Timer timer = new Timer();
	
	/*SeekBar 为 播放类型*/
	public static final int SEEKBAR_PLAY_PROGRESS = 0 ;
	
	/*SeekBar 为 音量类型*/
	public static final int SEEKBRAR_VOLOME = 1 ;
	
	/* 音量的管理者*/
	private AudioManager audioManager ;
	
	/* 最大音量*/
	private int maxVolume ;
	
	/* 视频播放的总时间*/
	private long totalPlayTime = 0 ;
	
	/*视频播放的当前时间*/
	private long curPlayTime = 0 ;
	
	/*判断为滑动的最短距离*/
	private int mslop ;
	
	/*控制播放界面的布局集合*/
	private List<RelativeLayout> mControllerLayouts = new ArrayList<RelativeLayout>();
	
	/*手势控件的集合*/
	private List<TextView> mGestureViews = new ArrayList<TextView>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,WindowManager.LayoutParams. FLAG_FULLSCREEN);//全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//init lib
		if(!LibsChecker.checkVitamioLibs(this))
		{
			return ;
		}
		setContentView(R.layout.activity_main1);
		getSupportActionBar().hide();
		
		ViewConfiguration configuration = ViewConfiguration.get(this);
		mslop = configuration.getScaledTouchSlop();
		
		//init SurfaceView
		mSurfaceView = (SurfaceView) findViewById(R.id.surface);
		
		//config SurfaceHolder
		mHolder = mSurfaceView.getHolder();
		mHolder.addCallback(this);
		mHolder.setFormat(PixelFormat.RGBA_8888);
		mHolder.setKeepScreenOn(true);
		
		//controller layout  ui
		mRlayoutTitleController = (RelativeLayout) findViewById(R.id.rlayout_title);
		mRlayoutPlayerController = (RelativeLayout) findViewById(R.id.rlayout_player);
		mRlayoutVolumeController = (RelativeLayout) findViewById(R.id.rlayout_volume);
		
		mControllerLayouts.add(mRlayoutTitleController);
		mControllerLayouts.add(mRlayoutPlayerController);
		mControllerLayouts.add(mRlayoutVolumeController);
		
		
		mPlayerProgressBar = (SeekBar) findViewById(R.id.seekbar_play_progress);
		mTxtVideoPlayerTotalTime = (TextView) findViewById(R.id.txt_total_time);
		mTxtVideoPlayCurTime = (TextView) findViewById(R.id.txt_cur_time);
		mImgPlayState = (ImageView) findViewById(R.id.img_play_controller);
		mSkbVolume = (VerticalSeekBar) findViewById(R.id.seekbar_volume);
		
		mTxtPlayerProgressPercentage = (TextView) findViewById(R.id.geture_tv_progress_time);         
		mTxtVolumePercentage = (TextView) findViewById(R.id.geture_tv_volume_percentage);
		mTxtBrightness = (TextView) findViewById(R.id.geture_tv_brightness_percentage);
		
		mGestureViews.add(mTxtPlayerProgressPercentage);
		mGestureViews.add(mTxtVolumePercentage);
		mGestureViews.add(mTxtBrightness);
		
		
		mTxtSetting = (TextView) findViewById(R.id.txt_setting);
		mTxtSeries = (TextView) findViewById(R.id.txt_series);
		mTxtSystemTime = (TextView) findViewById(R.id.txt_system_time);
		mImgBattery = (ImageView) findViewById(R.id.img_battery);
		
		mTxtBufferPercentage = (TextView) findViewById(R.id.buffer_txt_percentage);
		
		mTitle = (TextView) findViewById(R.id.player_title);
		
		mPlayerProgressBar.setOnSeekBarChangeListener(new MySeekBarChangeListener(SEEKBAR_PLAY_PROGRESS));
		mSkbVolume.setOnSeekBarChangeListener(new MySeekBarChangeListener(SEEKBRAR_VOLOME) );
		
		//每隔一秒更新播放进度
		timer.schedule(playerProgressTask, 0, 1000);
		timer.schedule(showSystemTimeTask, 0, 60*1000);
		
		//设置当前的音量
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		
		mSkbVolume.setMax(maxVolume);
		mSkbVolume.setProgressAndThumb(curVolume);
		
		mImgPlayState.setOnClickListener(new PlayStateListener());
		
		//设置系统时间
		mTxtSystemTime.setText(Utils.getSystemTime());
		mTxtSetting.setOnClickListener(new SettingClick());
		mTxtSeries.setOnClickListener(new SeriesClick());
		
	}
	
	private class SeriesClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			PlayerSeriesFragment series = new PlayerSeriesFragment();
			series.show(getSupportFragmentManager(), "seriesDialog");
		}
		
	}
	
	private class SettingClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			PlayerSettingFragment setting = new PlayerSettingFragment();
			Bundle args = new Bundle();
			args.putInt(PlayerSettingFragment.SURFACEVIEW_SIZE, mVideoSize);
			setting.setArguments(args);
			setting.show(getSupportFragmentManager(), "settingDialog");
		}
	}
	
	
	private class PlayStateListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(mediaPlayer != null&& !mIsError ){
				if(mediaPlayer.isPlaying())
				{
					mediaPlayer.stop();
					mImgPlayState.setImageResource(R.drawable.mediacontroller_play);
				}
				else
				{
					startPlayVideo();
					mImgPlayState.setImageResource(R.drawable.mediacontroller_pause);
				}
			}
		}
		
	}
	
	private class MySeekBarChangeListener implements OnSeekBarChangeListener{
		
		private int type ;

		public MySeekBarChangeListener(int type) {
			this.type = type ;
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
			switch (type) {
			case SEEKBAR_PLAY_PROGRESS:
				mTxtVideoPlayCurTime.setText(StringUtils.generateTime(progress));
				break;

			case SEEKBRAR_VOLOME:
				
				break ;
			default:
				break;
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			int progress = seekBar.getProgress();
			switch (type) {
			case SEEKBAR_PLAY_PROGRESS:
				if(mediaPlayer != null&& !mIsError)
				{
					mediaPlayer.seekTo(progress);
					
				}
				break;

			case SEEKBRAR_VOLOME:
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
				int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
				mSkbVolume.setProgressAndThumb(curVolume);
				break ;
			default:
				break;
			}
		
		}
		
	}

	private void playVideo(String path) {
		clearStatus();
		try {
			mediaPlayer = new MediaPlayer(this);
			
			//set play path
//			String cachePath = "cache:/"+Environment.getExternalStorageDirectory().getAbsolutePath()+"/cache.ts:";
//			String cachePath = "cache:/sdcard/download.ts:";
			mediaPlayer.setDataSource(path);
			//set display 
			mediaPlayer.setDisplay(mHolder);
			mediaPlayer.prepareAsync();
			
			//set MediaPlayer Listener
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.setOnVideoSizeChangedListener(this);
			mediaPlayer.setOnSeekCompleteListener(this);
			mediaPlayer.setOnInfoListener(this);
			mediaPlayer.setOnErrorListener(this);
			
			//set volume
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			
		} catch (Exception e) {
			e.printStackTrace();
			mIsError = true ;
			Toast.makeText(this, "此视频不能播放！", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		isSurfaceDestroy = false ;
		// 播放网络视频
		if(path == null)
		{
			return ;
		}
		
		//按 home 键后重新回到播放界面
		if(canPlayback())
		{
			mediaPlayer.setDisplay(mSurfaceView.getHolder());
			start();
		}
		//播放本地视频文件
		else
		{
			playVideo(path);
		}
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
		
	}

	boolean isSurfaceDestroy ;
	long playPosition = 0 ;
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isSurfaceDestroy = true ;
		
		//保存记录
		if(canPlayback())
		{
			playPosition = mediaPlayer.getCurrentPosition();
			//保存播放记录并通知前一个界面
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		stop();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(!isSurfaceDestroy)
		{
			start();
		}
	}
	
	private void start(){
		if(canPlayback()&& !mediaPlayer.isPlaying())
		{
			mediaPlayer.start();
		}
	}
	
	private void stop(){
		if(canPlayback()&& mediaPlayer.isPlaying())
		{
			mediaPlayer.stop();
		}
	}

	private boolean canPlayback() {
		return mediaPlayer != null && !mIsError;
	}
	
	
	@Override
	public void onSeekComplete(MediaPlayer mp) {
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		if(width == 0 || height == 0 )
		{
			return ;
		}
		mIsKnowVideoSize = true ;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
	}

	private int mBufferPercent ;
	
	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		mBufferPercent = percent ;
		mPlayerProgressBar.setSecondaryProgress((int) (totalPlayTime * percent));
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		mIsPrepare = true ;
		startPlayVideo();
	}
	
	private void startPlayVideo(){
		if(mIsKnowVideoSize && mIsPrepare && !mIsError)
		{
			startPlayVideo(Utils.getScreenWidthPixels(this), Utils.getScreenHeightPixels(this));
		}
	}
	
	private void startPlayVideo(int width , int height)
	{
		mHolder.setFixedSize(width, width);
		mediaPlayer.start();
		
		//设置时间
		final long duration = mediaPlayer.getDuration();
		totalPlayTime = duration ;
		String generateTime = StringUtils.generateTime(duration);
		mTxtVideoPlayerTotalTime.setText(generateTime);
		
		//设置进度
		mPlayerProgressBar.setMax((int) duration);
		
		//设置controller 消失还是显示
		playerHandler.postDelayed(controllerDismissTask, CONTROLLER_DISMISS_TIME);
	}
	
	private void clearStatus(){
		mIsPrepare = false ;
		mIsError = false ;
		mIsKnowVideoSize = false ;
	}
	
	private void release(){
		if(canPlayback())
		{
			mediaPlayer.release();
			mediaPlayer = null ;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		release();
		clearStatus();
		timer.cancel();
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
				TOUCH_AREA_FLAG = TOUCH_AREA_VOLUME  ;
			}
			if(startX >= Utils.getScreenWidthPixels(this) - TOUCH_AREA_WIDTH)
			{
				TOUCH_AREA_FLAG = TOUCH_AREA_BRIGTHNESS ;
			}
			break;
			
		case MotionEvent.ACTION_MOVE:
			
			float deltaX = event.getRawX() - preStartX;
			float deltaY = preStartY - event.getRawY() ;
			
			//从开始点到结束点的总滑动距离
			float slideX = event.getRawX() - startX ;
			float slideY = startY - event.getRawY() ;
			
			//手势滑动的最短距离.如果小于这个距离就不触发移动控件事件.
			if(Math.abs(slideX) > mslop || Math.abs(slideY) > mslop)
			{
				playerHandler.removeCallbacks(controllerDismissTask);
				isClick = false ;
			}
			
			if(firstScroll )
			{
				if(Math.abs(deltaY) >= Math.abs(deltaX) && (Math.abs(slideX) > mslop || Math.abs(slideY) > mslop) )
				{
					//判断是调整什么
					GESTURE_FLAG = TOUCH_AREA_FLAG == TOUCH_AREA_BRIGTHNESS ? GESTURE_MODIFY_BRIGTHNESS : TOUCH_AREA_FLAG == TOUCH_AREA_VOLUME ? GESTURE_MODIFY_VOLUME : 0;
					if(GESTURE_FLAG == GESTURE_MODIFY_BRIGTHNESS)
					{
						onlyShowGestureView(mTxtBrightness);
					}
					if(GESTURE_FLAG == GESTURE_MODIFY_VOLUME)
					{
						onlyShowGestureView(mTxtVolumePercentage);
					}
				}
				else if(Math.abs(deltaY) < Math.abs(deltaX) && (Math.abs(slideX) > mslop || Math.abs(slideY) > mslop) )
				{
					//表示调整进度
					GESTURE_FLAG = GESTURE_MODIFY_PROGRESS ;
					onlyShowGestureView(mTxtPlayerProgressPercentage);
				}
			}
				
			if(GESTURE_FLAG == GESTURE_MODIFY_BRIGTHNESS)
			{
//				System.out.println("brightness");
				LayoutParams params = getWindow().getAttributes();
				int curScreenBrightness = Utils.getScreenBrightness(this);
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
				Utils.setScreenBrightness(this, curScreenBrightness);
				getWindow().setAttributes(params);
				mTxtBrightness.setText((int)(brightnessPerfect * 100) + "%");
				
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
						mTxtVolumePercentage.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.play_gesture_volume, 0, 0);
					}
				}
				else if(deltaY < -RADIO)
				{
					//向下滑动
					if(curVolume > 0)
					{
						curVolume -- ;
						mTxtVolumePercentage.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.play_gesture_volume, 0, 0);
					}
					
					if(curVolume == 0)
					{
						//静音
						mTxtVolumePercentage.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.play_gesture_volume_no, 0, 0);
					}
				}
				mSkbVolume.setProgressAndThumb(curVolume);
				int progress = curVolume * 100 / maxVolume ;
				mTxtVolumePercentage.setText(progress + "%");
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, curVolume, 0);
				
			}
			
			if(GESTURE_FLAG == GESTURE_MODIFY_PROGRESS)
			{
				if(canPlayback())
				{
					curPlayTime = curPlayTime == 0 ? mediaPlayer.getCurrentPosition() : curPlayTime;
				}
				if(deltaX >= RADIO)
				{
					curPlayTime += 5*1000;
					mTxtPlayerProgressPercentage.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.play_gesture_forward, 0, 0);
				}
				else if(deltaX < -RADIO)
				{
					curPlayTime -= 5*1000 ;
					mTxtPlayerProgressPercentage.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.play_gesture_rewind, 0, 0);
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
				
				mTxtPlayerProgressPercentage.setText(StringUtils.generateTime(curPlayTime)+"/"+StringUtils.generateTime(totalPlayTime));
				
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
					playerHandler.removeCallbacks(controllerDismissTask);
					playerHandler.postDelayed(controllerDismissTask , CONTROLLER_DISMISS_TIME);
				}
				else
				{
					if(!isClickControllerLayout((int)startX,(int)startY))
					{
						hideControllerLayout();
						playerHandler.removeCallbacks(controllerDismissTask);
					}
					else
					{
						playerHandler.removeCallbacks(controllerDismissTask);
						playerHandler.postDelayed(controllerDismissTask , CONTROLLER_DISMISS_TIME);
					}
				}
			}
			else
			{
				playerHandler.postDelayed(controllerDismissTask , CONTROLLER_DISMISS_TIME);
			}
			
			//触摸改变进度后  手指松开的时候播放
			if(GESTURE_FLAG == GESTURE_MODIFY_PROGRESS && mediaPlayer != null&& !mIsError)
			{
				mediaPlayer.seekTo(curPlayTime);
			}
			
			//状态清除
			isClick = false ;
			GESTURE_FLAG = 0 ;
			TOUCH_AREA_FLAG = 0 ;
			startX = 0 ;
			startY = 0 ;
			mTxtPlayerProgressPercentage.setVisibility(View.GONE);
			mTxtVolumePercentage.setVisibility(View.GONE);
			mTxtBrightness.setVisibility(View.GONE);
			
			break ;

		default:
			break;
		}
		
		return super.onTouchEvent(event);
	}
	
	/**
	 * 控制播放界面的布局消失的Runnable
	 */
	private final Runnable controllerDismissTask = new Runnable() {
		
		@Override
		public void run() {
			hideControllerLayout();
		}
	};
	
	/**
	 * 视频播放界面更新的定时器(播放进度)
	 */
	TimerTask playerProgressTask = new TimerTask() {
		
		@Override
		public void run() {
			if(canPlayback()&& mediaPlayer.isPlaying())
			{
				long position = mediaPlayer.getCurrentPosition();
				mPlayerProgressBar.setProgress((int) position);
			}
		}
	};
	
	/**
	 * 控制视频播放界面的布局是否显示 
	 * @return true 表示显示  false 表示隐藏
	 */
	private boolean isShow(){
		if(mRlayoutPlayerController.isShown() && mRlayoutVolumeController.isShown()&& mRlayoutTitleController.isShown())
		{
			return true ;
		}
		return false ;
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
	 * 隐藏所有控制播放界面的布局
	 */
	private void hideControllerLayout(){
		for (int i = 0; i < mControllerLayouts.size(); i++) {
			mControllerLayouts.get(i).setVisibility(View.GONE);
		}
	}
	
	
	/**
	 * 显示所有控制播放界面的布局
	 */
	private void showControllerLayout(){
		for (int i = 0; i < mControllerLayouts.size(); i++) {
			mControllerLayouts.get(i).setVisibility(View.VISIBLE);
		}
	}
	
	
	/**
	 * 仅仅显示哪个控制播放界面的手势控件
	 * @param controllerLayout
	 */
	private void onlyShowGestureView(TextView gestureView){
		for (TextView controller : mGestureViews) {
			if(gestureView == controller){
				controller.setVisibility(View.VISIBLE);
			}else{
				controller.setVisibility(View.GONE);
			}
		}
	}
	
	BroadcastReceiver receiver ;
	@Override
	protected void onStart() {
		super.onStart();
		IntentFilter batteryChange = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		receiver = new BatteryChangeReceiver();
		registerReceiver(receiver, batteryChange);
		
		EventBus.getDefault().register(this);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(receiver);
		EventBus.getDefault().unregister(this);
	}
	
	public static final int BATTERY_CHARGING = 0 ;
	public static final int BATTERY_TWENTY_PERCENT = 1 ;
	public static final int BATTERY_FORTY_PERCENT = 2 ;
	public static final int BATTERY_SIXTY_PERCENT = 3 ;
	public static final int BATTERY_EIGHTY_PERCENT = 4 ;
	public static final int BATTERY_ONE_HUNDRED_PERCENT = 5 ;
	
	private class BatteryChangeReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent != null)
			{
				int level = -1 ;
				int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
				boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
				if(isCharging)
				{
					level = BATTERY_CHARGING ;
				}
				else
				{
					int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
					if(batteryLevel >= 80)
					{
						level = BATTERY_ONE_HUNDRED_PERCENT ;
					}
					else if(batteryLevel >= 60){
						level = BATTERY_EIGHTY_PERCENT ;
					}
					else if(batteryLevel >= 40)
					{
						level = BATTERY_SIXTY_PERCENT ;
					}
					else if(batteryLevel >= 20)
					{
						level = BATTERY_FORTY_PERCENT ;
					}
					else
					{
						level = BATTERY_TWENTY_PERCENT;
					}
				}
				if(level != -1)
				{
					mImgBattery.getDrawable().setLevel(level);
				}
			}
		}
	}
	
	/**
	 * 视频播放界面更新的定时器(系统时间)
	 */
	private TimerTask showSystemTimeTask = new TimerTask() {
		@Override
		public void run() {
			Message msg = new Message();
			msg.what = 100 ;
			systemTimeHandler.sendMessage(msg);
		}
	};
	
	private Handler systemTimeHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 100)
			{
				mTxtSystemTime.setText(Utils.getSystemTime());
			}
		}
	};
	
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
		
	}

	
	//这里是缓存时界面显示的变化(显示缓存进度和当前的下载速度)
	boolean needResume ;
	
	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		switch (what) {
        case MediaPlayer.MEDIA_INFO_BUFFERING_START:
            //开始缓存，暂停播放
            if (mp.isPlaying()) {
                stop();
                needResume = true;
            }
            mTxtBufferPercentage.setVisibility(View.VISIBLE);
            break;
        case MediaPlayer.MEDIA_INFO_BUFFERING_END:
            //缓存完成，继续播放
            if (needResume)
                start();
            mTxtBufferPercentage.setVisibility(View.GONE);
            break;
        case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
            //显示 下载速度
        	mTxtBufferPercentage.setText(extra+"K/S" +"("+mBufferPercent+"%)");
            break;
        }
        return true;
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		mIsError = true ;
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("不能播放此视频！");
		builder.setTitle("视频");
		builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				PlayVideoActivity.this.finish();
				builder.create().dismiss();
			}
		} );
		builder.setNegativeButton("取消", null);
		builder.create().show();
		return true;
	}
	
}
