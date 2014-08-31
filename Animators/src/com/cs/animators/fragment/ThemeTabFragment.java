package com.cs.animators.fragment;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import butterknife.InjectView;
import butterknife.OnItemClick;
import com.cs.animationvideo.R;
import com.cs.animators.VideoDetailActivity;
import com.cs.animators.adapter.ThemeAdapter;
import com.cs.animators.base.BaseFragment;
import com.cs.animators.constants.Constants;
import com.cs.animators.entity.Theme;
import com.cs.animators.entity.ThemeItem;
import com.cs.cj.http.httplibrary.RequestParams;
import com.cs.cj.http.work.JDataCallback;
import com.cs.cj.http.work.Response;
import com.markmao.pulltorefresh.widget.XListView;
import com.markmao.pulltorefresh.widget.XListView.IXListViewListener;

public class ThemeTabFragment extends BaseFragment implements IXListViewListener {
	
	@InjectView(R.id.theme_tab_lv)
	XListView mXListView ;
	
	private int mTabPosition ;
	
	private int mLoadPage = 1;
	
	private List<ThemeItem> mThemeItems = new ArrayList<ThemeItem>();
	
	private ThemeAdapter mAdapter;

	@Override
	protected void loadLayout() {
		setContentView(R.layout.fragment_theme_tab);
	}
	
	@Override
	protected void processLogic(){
		configXListView();
		bindData();
		loadData(mLoadPage,true);
	}

	private void bindData() {
		mAdapter = new ThemeAdapter(getActivity(), mThemeItems);
		mXListView.setAdapter(mAdapter);
	}

	void loadData(int page, boolean showProgress) {
		//m=Cartoon&a=index&limit=10&tab=3&page=1
		RequestParams params = new RequestParams();
		params.put("m", "Cartoon");
		params.put("a", "index");
		params.put("limit", "10");
		params.put("tab", mTabPosition + "");
		params.put("page", page);
		get(Constants.host, params, Theme.class, new JDataCallback<Theme>() {

			@Override
			public void onSuccess(Response<Theme> data) {
				mThemeItems.addAll( data.getResult().getList());
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		},showProgress);
	}
	

	@OnItemClick(R.id.theme_tab_lv)
	void onItemClickListener(AdapterView<?> parent , View v , int position , long id)
	{
		ThemeItem hotItem  = (ThemeItem) parent.getAdapter().getItem(position);
		String videoId = hotItem.getId()+"";
		Intent detailIntent = new Intent(getActivity(), VideoDetailActivity.class);
		detailIntent.putExtra(HotFragment.VIDEO_ID, videoId);
		detailIntent.putExtra("video_name", hotItem.getName());
		startActivity(detailIntent);
	}
	
	
	@Override
	protected void getExtra(Bundle args) {
		if(args != null){
			mTabPosition = args.getInt(ThemeFragment.THEME_TAB_POSITION);
			mLoadPage = mTabPosition == 1 ? mLoadPage + 1 : mLoadPage;
		}
	}
	
	private void onLoad(){
		mXListView.stopRefresh();
		mXListView.stopLoadMore();
		mXListView.setRefreshTime("刚刚");
	}
	
	private void configXListView() {
		mXListView.setPullLoadEnable(true);
		mXListView.setPullRefreshEnable(false);
		mXListView.setXListViewListener(this);
		mXListView.setRefreshTime("刚刚");
	}

	@Override
	public void onLoadMore() {
		mLoadPage ++ ;
		loadData(mLoadPage,false);
	}

	@Override
	public void onRefresh() {
		
	}
	
}
