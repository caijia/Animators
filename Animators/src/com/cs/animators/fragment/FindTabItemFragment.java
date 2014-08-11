package com.cs.animators.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import butterknife.InjectView;
import butterknife.OnItemClick;

import com.cs.animators.R;
import com.cs.animators.VideoDetailActivity;
import com.cs.animators.adapter.FindAdapter;
import com.cs.animators.base.BaseFragment;
import com.cs.animators.constants.Constants;
import com.cs.animators.entity.AllCategory;
import com.cs.animators.entity.HotItem;
import com.cs.cj.http.httplibrary.RequestParams;
import com.cs.cj.http.work.JDataCallback;
import com.cs.cj.http.work.Response;

public class FindTabItemFragment extends BaseFragment {

	@InjectView(R.id.findtab_gv)
	GridView mGridView ;
	
	//通过这个id可以取得更多的视频信息
	private String mId ;
	
	private List<HotItem> mList = new ArrayList<HotItem>();
	
	private FindAdapter mAdapter ;
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.fragment_find_tab);
	}

	@Override
	protected void processLogic() {
		getExtra();
		
		mAdapter = new FindAdapter(getActivity(), 3, mList, mGridView);
		
		//这里的目的是让GridView初始有六个数据  防止第一次加载数据时 填充出现界面跳动
		if(mList.size() == 0)
		{
			for (int i = 0; i < 6; i++) {
				mList.add(new HotItem());
			}
		}
		mGridView.setAdapter(mAdapter);
		
		loadData();
	}

	private void loadData() {
		RequestParams params = new RequestParams();
		params.put("m", "Api");
		params.put("a", "listByType");
		params.put("limit", "6");
		params.put("id", mId);
		params.put("order", "1");
		params.put("page", "1");
		get(Constants.host, params, AllCategory.class, new JDataCallback<AllCategory>() {

			@Override
			public void onSuccess(Response<AllCategory> data) {
				mList.clear();
				mList.addAll(data.getResult().getList());
				mAdapter.notifyDataSetChanged();
			}
		},false);
		
	}

	private void getExtra(){
		Bundle bundle = getArguments();
		if(bundle != null){
			mId = bundle.getString(FindFragment.TAB_ID_KEY);
		}
	}
	
	@OnItemClick(R.id.findtab_gv)
	void onItemClickListener(AdapterView<?> parent , View v , int position , long id)
	{
		HotItem hotItem  = (HotItem) parent.getAdapter().getItem(position);
		if(hotItem != null)
		{
			String videoId = hotItem.getVideoId()+"";
			Intent detailIntent = new Intent(getActivity(), VideoDetailActivity.class);
			detailIntent.putExtra(HotFragment.VIDEO_ID, videoId);
			startActivity(detailIntent);
		}
	}
	
}
