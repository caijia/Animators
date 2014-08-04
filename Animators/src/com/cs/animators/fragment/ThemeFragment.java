package com.cs.animators.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.InjectView;
import butterknife.OnItemClick;

import com.cs.animators.R;
import com.cs.animators.VideoDetailActivity;
import com.cs.animators.adapter.ThemeAdapter;
import com.cs.animators.base.BaseFragment;
import com.cs.animators.constants.Constants;
import com.cs.animators.entity.Theme;
import com.cs.animators.entity.ThemeItem;
import com.cs.cj.http.httplibrary.RequestParams;
import com.cs.cj.http.work.JDataCallback;
import com.cs.cj.http.work.Response;

public class ThemeFragment extends BaseFragment {
	
	@InjectView(R.id.newupdate_lv)
	ListView mListView ;

	@Override
	protected void loadLayout() {
		setContentView(R.layout.fragment_theme);
	}
	
	@Override
	protected void processLogic(){
		loadData();
	}

	void loadData() {
		//m=Cartoon&a=index&limit=10&tab=3&page=1
		RequestParams params = new RequestParams();
		params.put("m", "Cartoon");
		params.put("a", "index");
		params.put("limit", "10");
		params.put("tab", "3");
		params.put("page", "1");
		get(Constants.host, params, Theme.class, new JDataCallback<Theme>() {

			@Override
			public void onSuccess(Response<Theme> data) {
				ThemeAdapter adapter = new ThemeAdapter(getActivity(), data.getResult().getList());
				mListView.setAdapter(adapter);
			}
		});
	}
	
	@OnItemClick(R.id.newupdate_lv)
	void onItemClickListener(AdapterView<?> parent , View v , int position , long id)
	{
		ThemeItem hotItem  = (ThemeItem) parent.getAdapter().getItem(position);
		String videoId = hotItem.getId()+"";
		Intent detailIntent = new Intent(getActivity(), VideoDetailActivity.class);
		detailIntent.putExtra(HotFragment.VIDEO_ID, videoId);
		startActivity(detailIntent);
	}
	
}
