package com.cs.animators.fragment;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import butterknife.InjectView;
import butterknife.OnItemClick;
import com.cs.animationvideo.R;
import com.cs.animators.VideoDetailActivity;
import com.cs.animators.adapter.HotAdapter;
import com.cs.animators.base.BaseFragment;
import com.cs.animators.constants.Constants;
import com.cs.animators.entity.Hot;
import com.cs.animators.entity.HotItem;
import com.cs.cj.http.httplibrary.RequestParams;
import com.cs.cj.http.work.JDataCallback;
import com.cs.cj.http.work.Response;
import com.markmao.pulltorefresh.widget.XListView;
import com.markmao.pulltorefresh.widget.XListView.IXListViewListener;

public class HotTabFragment extends BaseFragment implements IXListViewListener {
	
	@InjectView(R.id.hot_lv)
	XListView mXListView ;

	private int mLoadPage = 1;
	
	private List<HotItem> mHotItems = new ArrayList<HotItem>();
	
	private HotAdapter mAdapter;
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.fragment_tab_hot);
	}

	@Override
	protected void processLogic() {
		
		configXListView();
		bindData();
		loadData(mLoadPage,true);
		
	}

	public void bindData() {
		mAdapter = new HotAdapter(getActivity(), mHotItems);
		mXListView.setAdapter(mAdapter);
	}

	public void loadData(int page , boolean showProgress) {
		RequestParams params = new RequestParams();
		params.put("m", "Cartoon");
		params.put("a", "newListVideo");
		params.put("tab", "2");
		params.put("limit", "12");
		params.put("page", page);
		get(Constants.host, params, Hot.class, new JDataCallback<Hot>() {

			@Override
			public void onSuccess(Response<Hot> data) {
				mHotItems.addAll( data.getResult().getList());
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		},showProgress);
	}

	private void configXListView() {
		mXListView.setPullLoadEnable(true);
		mXListView.setPullRefreshEnable(false);
		mXListView.setXListViewListener(this);
		mXListView.setRefreshTime("刚刚");
	}

	@Override
	public void onRefresh() {
//		loadData(1,false);
//		onLoad();
	}

	@Override
	public void onLoadMore() {
		mLoadPage ++ ;
		loadData(mLoadPage,false);
	}
	
	private void onLoad(){
		mXListView.stopRefresh();
		mXListView.stopLoadMore();
		mXListView.setRefreshTime("刚刚");
	}
	
	@OnItemClick(R.id.hot_lv)
	void onItemClickListener(AdapterView<?> parent , View v , int position , long id)
	{
		HotItem hotItem  = (HotItem) parent.getAdapter().getItem(position);
		String videoId = hotItem.getVideoId()+"";
		Intent detailIntent = new Intent(getActivity(), VideoDetailActivity.class);
		detailIntent.putExtra(HotFragment.VIDEO_ID, videoId);
		detailIntent.putExtra("video_name", hotItem.getName());
		startActivity(detailIntent);
	}
	
}
