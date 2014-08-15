package com.cs.animators;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.view.ActionMode;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import butterknife.InjectView;
import butterknife.OnItemClick;

import com.cs.animators.adapter.HotAdapter;
import com.cs.animators.base.BaseActivity;
import com.cs.animators.dao.service.DaoFactory;
import com.cs.animators.entity.HotItem;
import com.cs.animators.fragment.HotFragment;
import com.cs.animators.util.CommonUtil;
import com.markmao.pulltorefresh.widget.XListView;
import com.markmao.pulltorefresh.widget.XListView.IXListViewListener;

public class VideoCollectActivity extends BaseActivity implements IXListViewListener {

	@InjectView(R.id.video_collect_lv)
	XListView mXListView;
	
	private Handler mHandler ;
	
	private ActionMode mActionMode ;
	
	private HotAdapter mAdapter ;
	
	private int mLoadPage = 1;
	
	private int mLimit = 10 ;
	
	private List<HotItem> mHotItems = new ArrayList<HotItem>();
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.activity_video_collect);
	}

	@Override
	protected void processLogic() {
		mActionBar.setTitle("动漫收藏");
		mActionBar.setDisplayHomeAsUpEnabled(true);
		
		//XListView 的属性配置
		configXListView();
		bindData();
		
		mHandler = new GetLocalVideoHandler(this);
		showProgress();
		new Thread(new GetLocalVideoRunnable(mLimit , mLoadPage)).start();
	}
	
	public void bindData() {
		mAdapter = new HotAdapter(this, mHotItems);
		mXListView.setAdapter(mAdapter);
	}
	
	private void configXListView() {
		mXListView.setPullLoadEnable(true);
		mXListView.setPullRefreshEnable(false);
		mXListView.setXListViewListener(this);
		mXListView.setRefreshTime("刚刚");
	}
	
	private void onLoad(){
		mXListView.stopRefresh();
		mXListView.stopLoadMore();
		mXListView.setRefreshTime("刚刚");
	}
	
	
	private class GetLocalVideoRunnable implements Runnable{
		
		private int limit ; 
		private int page ;
		
		public GetLocalVideoRunnable(int limit , int page){
			this.limit = limit ;
			this.page = page ;
		}

		@Override
		public void run() {
			List<HotItem> videoCollects = getHotItem(limit , page);
			Message msg = new Message();
			msg.what = 100 ;
			msg.obj = videoCollects ;
			mHandler.sendMessage(msg);
		}
		
	}
	
	static class GetLocalVideoHandler extends Handler{
		private final WeakReference<VideoCollectActivity> mTarget ;
		
		public GetLocalVideoHandler(VideoCollectActivity target)
		{
			mTarget = new WeakReference<VideoCollectActivity>(target);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			VideoCollectActivity activity = mTarget.get();
			if(activity != null)
			{
				//do something
				if(msg.what == 100)
				{
					activity.onLoad();
					activity.dismiss();
					List<HotItem> localVideos = (List<HotItem>) msg.obj;
					activity.mHotItems.addAll(localVideos);
					activity.mAdapter.notifyDataSetChanged();
				}
			}
		}
	}

	public List<HotItem> getHotItem(int limit , int page) {
		List<HotItem> collects = DaoFactory.getVideoCollectInstance(mContext).query(limit,page);
		return collects;
	}
	
	@OnItemClick(R.id.video_collect_lv)
	void onItemClick(AdapterView<?> parent, View view,int position, long id) {
		
		if(mActionMode == null)
		{
			HotItem video = (HotItem) parent.getAdapter().getItem(position);
			String videoId = video.getVideoId()+"";
			Intent detailIntent = new Intent(this, VideoDetailActivity.class);
			detailIntent.putExtra(HotFragment.VIDEO_ID, videoId);
			detailIntent.putExtra("video_name", video.getName());
			startActivity(detailIntent);
		}
		else
		{
			onCheckedItem(parent, view, position, id);
		}
		
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
	
	
	@Override
	public Intent getSupportParentActivityIntent() {
		finish();
		return super.getSupportParentActivityIntent();
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
				List<HotItem> needRemoveVideos = new ArrayList<HotItem>();
				for (int i = 0; i < selectedItems.size(); i++) {
					boolean selected = selectedItems.valueAt(i);
					if(selected)
					{
						int position = selectedItems.keyAt(i);
						HotItem collect = mAdapter.getItem(position);
						needRemoveVideos.add(collect);
						//将选中的视频删除
						DaoFactory.getVideoCollectInstance(mContext).delete(collect.getVideoId());
					}
				}
				
				//刷新ListView
				for (HotItem videoCollect : needRemoveVideos) {
					mAdapter.remove(videoCollect);
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

	@Override
	public void onLoadMore() {
		mLoadPage ++ ;
		new Thread(new GetLocalVideoRunnable(mLimit , mLoadPage)).start();
	}

	@Override
	public void onRefresh() {
		
	}
	
}
