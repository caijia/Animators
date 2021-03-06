package com.cs.animators.fragment;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import com.cs.animators.FindMoreActivity;
import com.cs.animationvideo.R;
import com.cs.animators.adapter.FindAdapter;
import com.cs.animators.adapter.FindBannerAdapter;
import com.cs.animators.base.BaseFragment;
import com.cs.animators.constants.Constants;
import com.cs.animators.entity.Banner;
import com.cs.animators.entity.BannerItem;
import com.cs.animators.entity.Group;
import com.cs.animators.entity.GroupItem;
import com.cs.animators.util.CommonUtil;
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
	@InjectView(R.id.find_recommed_gv)
	GridView mGvRecommend ;
	
	@InjectView(R.id.find_txt_recommed_more)
	TextView mTxtRecommendMore ;
	
	//地区
	@InjectView(R.id.find_area_gv)
	GridView mGvArea ;
	
	//类型
	@InjectView(R.id.find_category_pts)
	PagerTabStrip mPtsCategory ;
	
	@InjectView(R.id.find_txt_category_more)
	TextView mTxtCategoryMore ;
	
	private List<Group> mServerData ;
	
	//分类布局里面的Tab标题
	private String [] mCategoryTitle ;
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.fragment_new_find);
	}
	
	//业务逻辑
	@Override
	protected void processLogic(){
		
		//为了保持图片的宽高比
		RelativeLayout.LayoutParams params = (LayoutParams) mViewPager.getLayoutParams();
		params.width = CommonUtil.getWidthMetrics(getActivity())- CommonUtil.dip2px(getActivity(), 16);
		params.height = params.width / 2 ;
		mViewPager.setLayoutParams(params);
		
		//初始化category Tab 标题
		mCategoryTitle = getResources().getStringArray(R.array.find_catorgy_tab_title);
		
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
		params.put("limit", "8");
		params.put("tab", "1");
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
	public static final String TAB_DATA = "tab_data";
	public static final String TAB_CURRENT_PAGE = "tab_current_page";
	
	protected void bindData(Response<List<Group>> data) {
		mServerData = data.getResult();

        List<GroupItem> recommend = mServerData.get(RECOMMED).getList().subList(0, 2);
		List<GroupItem> area = mServerData.get(AREA).getList().subList(0, 2);
		
		mGvRecommend.setAdapter(new FindAdapter(getActivity(), 2, recommend, mGvRecommend));
		mGvArea.setAdapter(new FindAdapter(getActivity(), 2, area, mGvArea));
		mPtsCategory.setAdapter(new FindCategoryTabAdapter(getChildFragmentManager()));
		
	}
	
	private class FindCategoryTabAdapter extends FragmentTabAdapter{
		
		public FindCategoryTabAdapter(FragmentManager manager) {
			super(manager);
		}

		@Override
		public int getContainerId() {
			return R.id.find_category_container;
		}

		@Override
		public int getCount() {
			return mCategoryTitle.length;
		}

		@Override
		public Fragment getItem(int position) {
			FindTabItemFragment fragment = new FindTabItemFragment();
			Bundle args = new Bundle();
            List<GroupItem> categorys = new ArrayList<GroupItem>();
            categorys.addAll(mServerData.get(AREA).getList().subList(2,mServerData.get(AREA).getList().size()));
            args.putParcelableArrayList(TAB_DATA,(ArrayList<? extends Parcelable>)categorys );
			args.putInt(TAB_CURRENT_PAGE, position);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public String getPagetTitle(int position) {
			return mCategoryTitle[position];
		}
		
	}
	
	
	@OnClick(R.id.find_txt_recommed_more)
	void onRecommendMoreClick(View v){
		accessFindMoreActivity(mServerData.get(RECOMMED).getList(),0,mServerData.get(RECOMMED).getGroup());
	}
	
//	@OnClick(R.id.find_txt_area_more)
//	void onAreaMoreClick(View v){
//		accessFindMoreActivity(mServerData.get(AREA).getList(),0,mServerData.get(AREA).getGroup());
//	}
	
	@OnClick(R.id.find_txt_category_more)
	void onCategoryMoreClick(View v){
		accessFindMoreActivity(mServerData.get(CATEGORY).getList(),2,mServerData.get(CATEGORY).getGroup());
	}
	
	@OnItemClick(R.id.find_recommed_gv)
	void onRecommendItemClick(AdapterView<?> parent , View v , int position , long id ){
		accessFindMoreActivity(mServerData.get(RECOMMED).getList(),position,mServerData.get(RECOMMED).getGroup());
	}
	
	@OnItemClick(R.id.find_area_gv)
	void onAreaItemClick(AdapterView<?> parent , View v , int position , long id ){
		accessFindMoreActivity(mServerData.get(AREA).getList(),position,mServerData.get(AREA).getGroup());
	}
	
	public static final String  GROUP_ITEM_MORE = "group_item_more";
	public static final String  GROUP_TAB_ITEM = "group_tab_item";
	public static final String  GROUP_TITLE = "group_title";
	
	private void accessFindMoreActivity(List<GroupItem> groupItem,int position,String groupName){
		Intent intent = new Intent(getActivity(), FindMoreActivity.class);
		intent.putParcelableArrayListExtra(GROUP_ITEM_MORE, (ArrayList<? extends Parcelable>) groupItem);
		intent.putExtra(GROUP_TAB_ITEM, position);
		intent.putExtra(GROUP_TITLE, groupName);
		startActivity(intent);
	}
}
