package com.cs.animators.fragment;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.InjectView;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import com.cs.animators.FindMoreActivity;
import com.cs.animationvideo.R;
import com.cs.animators.VideoDetailActivity;
import com.cs.animators.adapter.HotAdapter;
import com.cs.animators.base.BaseFragment;
import com.cs.animators.constants.Constants;
import com.cs.animators.dao.service.DaoFactory;
import com.cs.animators.entity.AllCategory;
import com.cs.animators.entity.HotItem;
import com.cs.animators.util.CommonUtil;
import com.cs.cj.http.httplibrary.RequestParams;
import com.cs.cj.http.work.JDataCallback;
import com.cs.cj.http.work.Response;
import com.markmao.pulltorefresh.widget.XListView;
import com.markmao.pulltorefresh.widget.XListView.IXListViewListener;

/**
 * 点击FindFragment中的更多(箭头) 跳转过来
 * @Author android_nihao (caijia)
 * @Time 2014-7-15 下午3:09:01
 */
public class FindMoreItemFragment extends BaseFragment implements IXListViewListener {

	@InjectView(R.id.all_category_lv)  
	XListView mXListView ;
	
	private long mLoadPage = 1;
	
	private List<HotItem> mHotItems = new ArrayList<HotItem>();
	
	private HotAdapter mAdapter;
	
	private String mId ;
	
	private long mTotalPage ;
	
	private long mTotalCount ;
	
	private static final int PAGE_NUMBER = 15 ;
	
	private ActionMode mActionMode ;
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.activity_all_category);
	}

	@Override
	protected void processLogic() {
		configXListView();
		bindData();
		loadData(mLoadPage, true);
	}
	
	public void loadData(long page , boolean showProgress) {
		RequestParams params = new RequestParams();
		params.put("m", "Api");
		params.put("a", "listByType");
		params.put("limit", ""+PAGE_NUMBER);
		params.put("id", mId);
		params.put("order", "1");
		params.put("page", page + "");
		get(Constants.host, params, AllCategory.class, new JDataCallback<AllCategory>() {

			@Override
			public void onSuccess(Response<AllCategory> data) {
				mTotalCount = Long.parseLong(data.getResult().getCount());
				mTotalPage = mTotalCount / PAGE_NUMBER + 1 ;
				mHotItems.addAll( data.getResult().getList());
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		},showProgress);
	}
	
	private void configXListView() {
		mXListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mXListView.setPullLoadEnable(true);
		mXListView.setPullRefreshEnable(false);
		mXListView.setXListViewListener(this);
		mXListView.setRefreshTime("刚刚");
	}
	
	public void bindData() {
		mAdapter = new HotAdapter(getActivity(), mHotItems);
		mXListView.setAdapter(mAdapter);
	}

	@Override
	public void onRefresh() {
	}

	@Override
	public void onLoadMore() {
		mLoadPage ++ ;
		if(mLoadPage <= mTotalPage)
		{
			loadData(mLoadPage,false);
		}
		else
		{
			CommonUtil.showMessage(getActivity(), "没有更多数据");
			onLoad();
		}
	}
	
	private void onLoad(){
		mXListView.stopRefresh();
		mXListView.stopLoadMore();
		mXListView.setRefreshTime("刚刚");
	}
	
	
	@Override
	protected void getExtra(Bundle bundle) {
		if(bundle != null)
		{
			mId = bundle.getString(FindMoreActivity.GROUP_IMTE_ID);
		}
	}
	
	@OnItemClick(R.id.all_category_lv)
	void onItemClickListener(AdapterView<?> parent , View v , int position , long id)
	{
		if(mActionMode == null )
		{
			HotItem hotItem  = (HotItem) parent.getAdapter().getItem(position);
			String videoId = hotItem.getVideoId()+"";
			Intent detailIntent = new Intent(getActivity(), VideoDetailActivity.class);
			detailIntent.putExtra(HotFragment.VIDEO_ID, videoId);
			detailIntent.putExtra("video_name", hotItem.getName());
			startActivity(detailIntent);
		}
		else
		{
			onCheckedItem(parent, v, position, id);
		}
	}
	
	@OnItemLongClick(R.id.all_category_lv)
	boolean onItemLongClickListener(AdapterView<?> parent , View v , int position , long id){
		onCheckedItem(parent, v, position, id);
		return true ;
	}
	
	private void onCheckedItem(AdapterView<?> parent , View v , int position , long id){
		mAdapter.toggleSelection(position - mXListView.getHeaderViewsCount());  //让被选中的Item 保持高亮
		boolean hasSelectItem = mAdapter.getSelectedItemCount() > 0 ;
		if(hasSelectItem && mActionMode == null)
		{
			mActionMode = ((FindMoreActivity)getActivity()).startSupportActionMode(callback);
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
			mode.getMenuInflater().inflate(R.menu.video_detail_menu, menu);
			return true;
		}
		
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.action_collect:
				//将选中的视频保存
				SparseBooleanArray selectedItems = mAdapter.getSelectedItemIds();
				for (int i = 0; i < selectedItems.size(); i++) {
					boolean selected = selectedItems.valueAt(i);
					if(selected)
					{
						int position = selectedItems.keyAt(i);
						HotItem hotItem = mAdapter.getItem(position);
						DaoFactory.getVideoCollectInstance(getActivity()).saveOrUpdate(hotItem);
					}
				}
				
				//提示用户收藏成功
				CommonUtil.showMessage(getActivity(), "收藏成功");
				break;

			default:
				break;
			}
			mActionMode.finish();
			return false;
		}
	};
}
