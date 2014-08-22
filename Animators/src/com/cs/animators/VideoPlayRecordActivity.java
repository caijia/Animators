package com.cs.animators;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyHeaderXListView;
import android.content.Intent;
import android.os.Message;
import android.support.v7.view.ActionMode;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import butterknife.InjectView;

import com.cs.animators.adapter.VideoRecordAdapter;
import com.cs.animators.base.BaseActivity;
import com.cs.animators.constants.Constants;
import com.cs.animators.dao.bean.VideoPlayRecord;
import com.cs.animators.dao.service.DaoFactory;
import com.cs.animators.fragment.HotFragment;
import com.cs.animators.util.CommonUtil;
import com.cs.cj.http.httplibrary.RequestParams;
import com.cs.cj.http.work.JHttpClient;

public class VideoPlayRecordActivity extends BaseActivity implements OnItemClickListener {

	@InjectView(R.id.video_record_sticky_lv) StickyHeaderXListView mXListView ;
	
	private VideoRecordAdapter mAdapter ;
	
	private ActionMode mActionMode ;
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.activity_video_record);
	}

	@Override
	protected void processLogic() {
		
		mActionBar.setTitle("播放记录");
		mXListView.setOnItemClickListener(this);
	}
	
	//打开线程开关 return true doInBackground 和 onPostExecute 才会执行
	@Override
	protected boolean switchThread() {
		return true;
	}
	
	//sub thread
	@Override
	protected Message doInBackground() {
		List<VideoPlayRecord> list = DaoFactory.getVideoRecordInstance(mContext).queryAll();
		Message msg = new Message();
		msg.what = 100 ;
		msg.obj = list ;
		return msg;
		
	}
	
	//main thread
	@SuppressWarnings("unchecked")
	@Override
	protected void onPostExecute(Message msg) {
		if(msg != null && msg.what == 100){
			List<VideoPlayRecord> list = (List<VideoPlayRecord>) msg.obj ;
			mAdapter = new VideoRecordAdapter(mContext, list);
			mXListView.setAdapter(mAdapter);
		}
	}
	
	@Override
	protected boolean displayHomeAsUpEnabled() {
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		if(mActionMode == null)
		{
			//进入视频详细界面
			VideoPlayRecord record = (VideoPlayRecord) parent.getAdapter().getItem(position);
//			playVideo(record);
			if(record != null){
				accessVideoDetail(record);
			}
		}
		else
		{
			onCheckedItem(parent, view, position, id);
		}
	}
	
	private void accessVideoDetail(VideoPlayRecord record) {
		String videoId = record.getVideoId();
		Intent detailIntent = new Intent(mContext , VideoDetailActivity.class);
		detailIntent.putExtra(HotFragment.VIDEO_ID, videoId);
		detailIntent.putExtra("video_name", record.getVideoName());
		mContext.startActivity(detailIntent);
		finish();
	}

	@SuppressWarnings("unused")
	private void playVideo(VideoPlayRecord record) {
		//拼接请求视频播放地址的url
		String videoId = record.getVideoId();
		String id = record.getId() + "";
		
		RequestParams params = new RequestParams();
		params.put("m","Api");
		params.put("a", "play");
		params.put("ios", "1");
		params.put("format", "2");
		params.put("id",id );
		params.put("video_id", videoId);
		String loadVideoAddressUrl = JHttpClient.getUrlWithQueryString(Constants.host, params);
		
		Intent intent = new Intent(this, VideoPlayActivity.class);
		intent.putExtra(VideoPlayActivity.VIDEO_NAME, record.getVideoName());
		intent.putExtra(VideoPlayActivity.LOAD_VIDEO_URL, loadVideoAddressUrl);
		intent.putExtra(VideoPlayActivity.VIDEO_EXTRA, record.getSeries() + "");
		intent.putExtra(VideoPlayActivity.VIDEO_RECORD, record.getPlayRecord());
		startActivity(intent);
	}
	
	
	private void onCheckedItem(AdapterView<?> parent , View v , int position , long id){
		mAdapter.toggleSelection(position - mXListView.getHeaderViewsCount());  //让被选中的Item 保持高亮
		boolean hasSelectItem = mAdapter.getSelectedItemCount() > 0 ;
		if(hasSelectItem && mActionMode == null)
		{
			mActionMode = startSupportActionMode(callback);
		}
		
		if(!hasSelectItem && mActionMode != null)
		{
			mActionMode.finish();
		}
		
		if(mActionMode != null)
		{
			mActionMode.setTitle("已选中"+mAdapter.getSelectedItemCount()+"项");
		}
	}
	
	
	//ActionMode
	private ActionMode.Callback callback = new ActionMode.Callback() {
		
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}
		
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			mAdapter.removeAllSelectedView();
			mActionMode = null ;
		}
		
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			mode.getMenuInflater().inflate(R.menu.videocollect_contextmenu, menu);
			return true;
		}
		
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem menu) {
			switch (menu.getItemId()) {
			case R.id.action_delete:
				//删除已经收藏的Video 并刷新ListView
				//将选中的视频删除
				SparseBooleanArray selectedItems = mAdapter.getSelectedItemIds();
				List<VideoPlayRecord> needRemoveVideos = new ArrayList<VideoPlayRecord>();
				for (int i = 0; i < selectedItems.size(); i++) {
					boolean selected = selectedItems.valueAt(i);
					if(selected)
					{
						int position = selectedItems.keyAt(i);
						VideoPlayRecord record = mAdapter.getItem(position);
						needRemoveVideos.add(record);
						//将选中的视频删除
						DaoFactory.getVideoRecordInstance(mContext).delete(record.getVideoId() ,record.getId());
					}
				}
				
				//刷新ListView
				for (VideoPlayRecord record : needRemoveVideos) {
					mAdapter.remove(record);
				}
				mAdapter.notifyDataSetChanged();
				
				//提示用户删除成功
				CommonUtil.showMessage(mContext, "删除成功");
				
				break;

			case R.id.action_cancel:
				break;
			default:
				break;
			}
			mActionMode.finish();
			return false;
		}
	};
		
	//ActinBar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.videocollect_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_edit:
			if(mActionMode == null)
			{
				mActionMode = startSupportActionMode(callback);
				mActionMode.setTitle("已选中" + mAdapter.getSelectedItemCount()+"项");
			}
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
