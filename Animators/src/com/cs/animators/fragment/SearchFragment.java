package com.cs.animators.fragment;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.InjectView;
import butterknife.OnItemClick;
import com.cs.animators.MainActivity;
import com.cs.animators.R;
import com.cs.animators.VideoDetailActivity;
import com.cs.animators.adapter.HotAdapter;
import com.cs.animators.base.BaseFragment;
import com.cs.animators.constants.Constants;
import com.cs.animators.entity.HotItem;
import com.cs.animators.entity.Search;
import com.cs.cj.http.httplibrary.RequestParams;
import com.cs.cj.http.work.JDataCallback;
import com.cs.cj.http.work.Response;

public class SearchFragment extends BaseFragment {
	
	@InjectView(R.id.search_lv) ListView mListView ;
	private String mSearchWord ;
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.fragment_search);
	}

	@Override
	protected void processLogic() {
		//m=Cartoon&a=search&limit=10&word=航海王&page=1&type=0
		RequestParams params = new RequestParams();
		params.put("m", "Cartoon");
		params.put("a", "search");
		params.put("limit", "10");
		params.put("word", mSearchWord);
		params.put("page", "1");
		params.put("type", "0");
		get(Constants.host, params, Search.class, new JDataCallback<Search>() {

			@Override
			public void onSuccess(Response<Search> data) {
				
				List<HotItem> filterVideoList = new ArrayList<HotItem>();
				List<HotItem> list = data.getResult().getList();
				for (HotItem hotItem : list) {
					boolean notVideo = hotItem.getUpdate().contains("话");
					if(!notVideo){
						filterVideoList.add(hotItem);
					}
				}
				HotAdapter adapter = new HotAdapter(getActivity(), filterVideoList);
				mListView.setAdapter(adapter);
			}
		});
	}
	
	@Override
	protected void getExtra(Bundle bundle) {
		if(bundle !=null)
		{
			mSearchWord = bundle.getString(MainActivity.SEARCH_WORD);
		}
	}
	
	@OnItemClick(R.id.search_lv)
	void onItemClickListener(AdapterView<?> parent , View v , int position , long id)
	{
		HotItem hotItem  = (HotItem) parent.getAdapter().getItem(position);
		String videoId = hotItem.getId()+"";
		Intent detailIntent = new Intent(getActivity(), VideoDetailActivity.class);
		detailIntent.putExtra(HotFragment.VIDEO_ID, videoId);
		detailIntent.putExtra("video_name", hotItem.getName());
		startActivity(detailIntent);
	}
	
}
