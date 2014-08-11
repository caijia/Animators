package com.cs.animators.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;
import butterknife.InjectView;

import com.cs.animators.R;
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
import com.cs.cj.view.FragmentTabAdapter;
import com.cs.cj.view.PagerTabStrip;

public class FindFragment extends BaseFragment {
	
	@InjectView(R.id.find_autoscroll_vpager)
	AutoScrollViewPager mViewPager ;
	
	@InjectView(R.id.find_pageindicator)
	CircleLoopPageIndicator mIndicator ;
	
	@InjectView(R.id.find_scrollview)
	CustomScrollView mCustomScrollView ;
	
	//推荐
	@InjectView(R.id.find_pts_recommend)
	PagerTabStrip mPtsRecommend ;
	
	@InjectView(R.id.find_txt_recommed_more)
	TextView mTxtRecommendMore ;
	
	//地区
	@InjectView(R.id.find_pts_area)
	PagerTabStrip mPtsArea ;
	
	@InjectView(R.id.find_txt_area_more)
	TextView mTxtAreaMore ;
	
	//类型
	@InjectView(R.id.find_pts_category)
	PagerTabStrip mPtsCategory ;
	
	@InjectView(R.id.find_txt_category_more)
	TextView mTxtCategoryMore ;
	 

	@Override
	protected void loadLayout() {
		setContentView(R.layout.fragment_new_find);
	}
	
	//业务逻辑
	@Override
	protected void processLogic(){
		//加载数据
		loadData();
		//加载Banner
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
		//主界面Banner
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
		get(Constants.group_url, null, new FastJsonParserArray<Group>(Group.class), new JDataCallback<List<Group>>() {

			@Override
			public void onSuccess(Response<List<Group>> data) {
				bindData(data);
			}
		});
	}

	public static final int RECOMMED = 0 ;
	public static final int AREA = 1 ;
	public static final int CATEGORY = 2 ;
	public static final String TAB_ID_KEY = "tab_id_key";
	
	protected void bindData(Response<List<Group>> data) {
		List<Group> serverData = data.getResult();
		
		List<GroupItem> recommed = serverData.get(RECOMMED).getList().subList(0, 3);
		List<GroupItem> area = serverData.get(AREA).getList().subList(0, 3);
		List<GroupItem> category = serverData.get(CATEGORY).getList().subList(0, 3);
		
		mPtsRecommend.setAdapter(new TabAdapter(getChildFragmentManager(), recommed, R.id.find_ll_recommend_container));
		mPtsArea.setAdapter(new TabAdapter(getChildFragmentManager(), area, R.id.find_ll_area_container));
		mPtsCategory.setAdapter(new TabAdapter(getChildFragmentManager(), category, R.id.find_ll_category_container));
		
	}
	
	private class TabAdapter extends FragmentTabAdapter{

		private List<GroupItem> list ;
		private int containerId ;
		
		public TabAdapter(FragmentManager manager , List<GroupItem> list , int containerId) {
			super(manager);
			this.list = list ;
			this.containerId = containerId ;
		}

		@Override
		public int getContainerId() {
			return containerId;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Fragment getItem(int position) {
			FindTabItemFragment fragment = new FindTabItemFragment();
			Bundle args = new Bundle();
			args.putString(TAB_ID_KEY, list.get(position).getId());
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public String getPagetTitle(int position) {
			return list.get(position).getName().substring(0, 2);
		}
		
	}
	
	
}
