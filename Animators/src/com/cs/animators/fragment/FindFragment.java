package com.cs.animators.fragment;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import com.cs.animators.AllCategoryActivity;
import com.cs.animators.GroupItemActivity;
import com.cs.animators.R;
import com.cs.animators.adapter.FindAdapter;
import com.cs.animators.adapter.FindBannerAdapter;
import com.cs.animators.base.BaseFragment;
import com.cs.animators.constants.Constants;
import com.cs.animators.entity.Banner;
import com.cs.animators.entity.BannerItem;
import com.cs.animators.entity.Group;
import com.cs.animators.entity.GroupItem;
import com.cs.cj.http.httplibrary.RequestParams;
import com.cs.cj.http.work.FastJsonParserArray;
import com.cs.cj.http.work.JDataCallback;
import com.cs.cj.http.work.JHttpClient;
import com.cs.cj.http.work.Response;
import com.cs.cj.view.AutoScrollViewPager;
import com.cs.cj.view.CircleLoopPageIndicator;
import com.cs.cj.view.CustomScrollView;

public class FindFragment extends BaseFragment {
	
	@InjectView(R.id.find_autoscroll_vpager)
	AutoScrollViewPager mViewPager ;
	
	@InjectView(R.id.find_pageindicator)
	CircleLoopPageIndicator mIndicator ;
	
	@InjectView(R.id.find_scrollview)
	CustomScrollView mCustomScrollView ;
	
	//推荐
	@InjectView(R.id.find_gv_recommend)
	GridView mGvRecommend ;
	
	//地区
	@InjectView(R.id.find_gv_area)
	GridView mGvArea ;
	
	//类型
	@InjectView(R.id.find_gv_category)
	GridView mGvCategory ;
	 
	//题材
	@InjectView(R.id.find_gv_theme)
	GridView mGvTheme ;
	

	@Override
	protected void loadLayout() {
		setContentView(R.layout.fragment_find);
		
	}
	
	
	//业务逻辑
	@Override
	protected void processLogic(){
		//加载数据
		loadData();
		
		loadBanner();
	}
	
	private void loadBanner() {
		//m=Api&a=allTopic&limit=5&page=2
		RequestParams params = new RequestParams();
		params.put("m", "Cartoon");
		params.put("a", "index");
		params.put("limit", "5");
		params.put("tab", "2");
		params.put("page", "1");
		JHttpClient.get(getActivity() , Constants.host, params, Banner.class, new JDataCallback<Banner>() {

			@Override
			public void onSuccess(Response<Banner> data) {
				setBanner(data.getResult().getList());
			}
		});
	}

	private void setBanner(List<BannerItem> banners){
		//主界面Bannder
		FindBannerAdapter bannerAdapter = new FindBannerAdapter(getActivity(), banners);
		mViewPager.startAutoScroll(4000);
		mViewPager.setInterval(4000);
        mCustomScrollView.setAutoScrollViewPager(mViewPager);
        mViewPager.setAdapter(bannerAdapter);
        mViewPager.setCurrentItem(banners.size() * 10000);
        mIndicator.setPageCount(banners.size());
        mIndicator.setViewPager(mViewPager);
	}

	void loadData() {
		RequestParams params = new RequestParams();
		params.put("m", "Cartoon");
		params.put("a", "channelgroup");
		get(Constants.host, params, new FastJsonParserArray<Group>(Group.class), new JDataCallback<List<Group>>() {

			@Override
			public void onSuccess(Response<List<Group>> data) {
				bindData(data);
			}
		});
	}
	
	private static final int RECOMMEND = 0 ;
	private static final int AREA = 1 ;
	public static final int CATEGORY = 2 ;
	public static final int THEME = 3 ;
	private List<Group> mResult ;
	
	private void bindData(Response<List<Group>> data) { 
		//服务器返回的数据
		mResult = data.getResult();
		
		//生成与主界面匹配的数据
		List<List<GroupItem>> newResult = new ArrayList<List<GroupItem>>();
		
		ArrayList<GroupItem> recommend = (ArrayList<GroupItem>) mResult.get(RECOMMEND).getList();
		
		List<GroupItem> totalArea = mResult.get(AREA).getList();
		List<GroupItem> area = totalArea.subList(0, 2);
		
		List<GroupItem> totalCategory = mResult.get(CATEGORY).getList();
		List<GroupItem> category = totalCategory.subList(0, 3);
		
		List<GroupItem> totalTheme = mResult.get(THEME).getList();
		List<GroupItem> theme = totalTheme.subList(0, 2);
		
		newResult.add(recommend);
		newResult.add(area);
		newResult.add(category);
		newResult.add(theme);
		
		GridView gridView[] = new GridView[]{mGvRecommend , mGvArea , mGvCategory , mGvTheme};
			
		for (int i = 0; i < gridView.length; i++) {
			FindAdapter adapter = new FindAdapter(getActivity(), i == 1 || i == 3 ? 2 : 3, newResult.get(i) , gridView[i]);
			gridView[i].setAdapter(adapter);
		}
	}
	
	public static final String EXTRA_GROUPITEM_KEY = "groupitem_key";
	
	@OnClick(R.id.find_txt_category_more)
	void categoryMore(){
		Intent intent = new Intent(getActivity(), GroupItemActivity.class);
		intent.putParcelableArrayListExtra(EXTRA_GROUPITEM_KEY,(ArrayList<GroupItem>) mResult.get(CATEGORY).getList());
		startActivity(intent);
	}
	
	@OnClick(R.id.find_txt_theme_more)
	void themeMore(){
		Intent intent = new Intent(getActivity(), GroupItemActivity.class);
		intent.putParcelableArrayListExtra(EXTRA_GROUPITEM_KEY,(ArrayList<GroupItem>) mResult.get(THEME).getList());
		startActivity(intent);
	}
	
	@OnClick(R.id.find_txt_area_more)
	void areaMore(){
		Intent intent = new Intent(getActivity(), GroupItemActivity.class);
		intent.putParcelableArrayListExtra(EXTRA_GROUPITEM_KEY,(ArrayList<GroupItem>) mResult.get(AREA).getList());
		startActivity(intent);
	}
	
	public static final String ID_KEY = "id_key";
	
	@OnItemClick(R.id.find_gv_recommend)
	void recommendItemClick(AdapterView<?> parent , View v, int position , long id){
		accessAllCategoryActivity(parent, position);
	}
	
	@OnItemClick(R.id.find_gv_category)
	void categoryItemClick(AdapterView<?> parent , View v, int position , long id){
		accessAllCategoryActivity(parent, position);
	}
	
	@OnItemClick(R.id.find_gv_theme)
	void themeItemClick(AdapterView<?> parent , View v, int position , long id){
		accessAllCategoryActivity(parent, position);
	}
	
	@OnItemClick(R.id.find_gv_area)
	void areaItemClick(AdapterView<?> parent , View v, int position , long id){
		accessAllCategoryActivity(parent, position);
	}
	
	public void accessAllCategoryActivity(AdapterView<?> parent, int position) {
		GroupItem item = (GroupItem) parent.getAdapter().getItem(position);
		Intent intent = new Intent(getActivity(), AllCategoryActivity.class);
		intent.putExtra(ID_KEY, item.getId());
		startActivity(intent);
	}
	
}
