package com.cs.animators;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import butterknife.InjectView;
import butterknife.OnItemClick;

import com.cs.animators.adapter.LocalVideoAdapter;
import com.cs.animators.base.BaseActivity;
import com.cs.animators.entity.LocalVideo;

public class LocalVideoActivity extends BaseActivity {

	@InjectView(R.id.local_video_gridview) GridView mGridView ;
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.activity_cache_video);  
	}

	@Override
	protected void processLogic() {
		List<LocalVideo> localVideos = getLocalVideo();
		LocalVideoAdapter adapter = new LocalVideoAdapter(mContext, localVideos);
		mGridView.setAdapter(adapter);
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
	
	@OnItemClick(R.id.local_video_gridview)
	void onItemClick(AdapterView<?> parent, View view,int position, long id) {
		LocalVideo video = (LocalVideo) parent.getAdapter().getItem(position);
		String videoPath = video.getVideoPath();
		Intent intent = new Intent(mContext, TestVitamioActivity.class);
		intent.putExtra(TestVitamioActivity.VIDEO_PATH, videoPath);
		intent.putExtra(TestVitamioActivity.VEDIO_TITLE, video.getVideoTitle());
		startActivity(intent);
	}
	
}
