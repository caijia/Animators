package com.cs.animators;

import io.vov.vitamio.LibsChecker;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.InjectView;
import butterknife.OnItemClick;
import com.cs.animators.adapter.LocalVideoAdapter;
import com.cs.animators.base.BaseActivity;
import com.cs.animators.entity.LocalVideo;

public class LocalVideoActivity extends BaseActivity {

	@InjectView(R.id.local_video_lv) ListView mListView ;
	private Handler mHandler ;
	
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.activity_cache_video);  
	}

	@Override
	protected void processLogic() {
		if(!LibsChecker.checkVitamioLibs(this))
		{
			return ;
		}
		
		mActionBar.setTitle("本地缓存");
		mActionBar.setDisplayHomeAsUpEnabled(true);
		
		mHandler = new GetLocalVideoHandler(this);
		showProgress();
		new Thread(new GetLocalVideoRunnable()).start();
	}
	
	private class GetLocalVideoRunnable implements Runnable{

		@Override
		public void run() {
			List<LocalVideo> localVideos = getLocalVideo();
			Message msg = new Message();
			msg.what = 100 ;
			msg.obj = localVideos ;
			mHandler.sendMessage(msg);
		}
		
	}
	
	static class GetLocalVideoHandler extends Handler{
		private final WeakReference<LocalVideoActivity> mTarget ;
		
		public GetLocalVideoHandler(LocalVideoActivity target)
		{
			mTarget = new WeakReference<LocalVideoActivity>(target);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			LocalVideoActivity activity = mTarget.get();
			if(activity != null)
			{
				//do something
				if(msg.what == 100)
				{
					activity.dismiss();
					List<LocalVideo> localVideos = (List<LocalVideo>) msg.obj;
					LocalVideoAdapter adapter = new LocalVideoAdapter(activity,localVideos);
					activity.mListView.setAdapter(adapter);
				}
			}
		}
	}
	

	private List<LocalVideo> getLocalVideo() {

		List<LocalVideo> localVideos = new ArrayList<LocalVideo>();
		
		String[] mediaColumns = { 
				MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
				MediaStore.Video.Media.MIME_TYPE,
				MediaStore.Video.Media.DISPLAY_NAME,
				MediaStore.Video.Media.DURATION};

		Cursor cursor = getContentResolver().query(
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns,
				null, null, null);
		
		while (cursor != null && cursor.moveToNext()) {
			//得到视频文件路径
			String videoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
			
			//得到视频的标题   test.mp4 -> test
			String videoTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
			
			//得到视频的播放时长
			long videoDuration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
			
			//得到视频的文件名test.mp4 -> test.mp4
			String videoName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
			
			LocalVideo video = new LocalVideo(videoPath, videoName, videoTitle, videoDuration);
			localVideos.add(video);
		}
		
		if(cursor != null)
			cursor.close();
		
		return localVideos ;
	}
	
	@OnItemClick(R.id.local_video_lv)
	void onItemClick(AdapterView<?> parent, View view,int position, long id) {
		LocalVideo video = (LocalVideo) parent.getAdapter().getItem(position);
		String videoPath = video.getVideoPath();
		Intent intent = new Intent(mContext, VideoPlayActivity.class);
		intent.putExtra(VideoPlayActivity.VIDEO_PATH, videoPath);
		intent.putExtra(VideoPlayActivity.VIDEO_NAME, video.getVideoTitle());
		startActivity(intent);
	}
	
	
	@Override
	public Intent getSupportParentActivityIntent() {
		finish();
		return super.getSupportParentActivityIntent();
	}
	
}
