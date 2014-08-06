package com.cs.animators;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.InjectView;
import butterknife.OnItemClick;

import com.cs.animators.adapter.ActionModeAdapter;
import com.cs.animators.adapter.HotAdapter;
import com.cs.animators.base.BaseActivity;
import com.cs.animators.constants.Constants;
import com.cs.animators.entity.HotItem;
import com.cs.animators.entity.Search;
import com.cs.animators.fragment.HotFragment;
import com.cs.cj.http.httplibrary.RequestParams;
import com.cs.cj.http.work.JDataCallback;
import com.cs.cj.http.work.Response;

public class SearchActivity extends BaseActivity {
	
	@InjectView(R.id.search_lv) ListView mListView ;
	private String mSearchWord ;
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.activity_search);
	}

	@Override
	protected void processLogic() {
		mActionBar.setDisplayHomeAsUpEnabled(true);
		getExtra();
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
				ActionModeAdapter adapter = new HotAdapter(mContext, data.getResult().getList());
				mListView.setAdapter(adapter);
			}
		});
	}
	
	private void getExtra(){
		Bundle bundle = getIntent().getExtras();
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
		Intent detailIntent = new Intent(this, VideoDetailActivity.class);
		detailIntent.putExtra(HotFragment.VIDEO_ID, videoId);
		startActivity(detailIntent);
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		finish();
		return super.getSupportParentActivityIntent();
	}
	
}
