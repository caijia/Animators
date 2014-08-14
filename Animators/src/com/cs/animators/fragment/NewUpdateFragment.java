package com.cs.animators.fragment;

import java.util.ArrayList;
import java.util.List;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import butterknife.InjectView;
import com.cs.animators.R;
import com.cs.animators.VideoDetailActivity;
import com.cs.animators.adapter.NewUpdateAdapter;
import com.cs.animators.base.BaseFragment;
import com.cs.animators.constants.Constants;
import com.cs.animators.entity.HotItem;
import com.cs.animators.parser.NewUpdateParser;
import com.cs.cj.http.httplibrary.RequestParams;
import com.cs.cj.http.work.JDataCallback;
import com.cs.cj.http.work.Response;

public class NewUpdateFragment extends BaseFragment {

	@InjectView(R.id.newupdate_lv) StickyListHeadersListView mListView ;
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.fragment_newupdate);
	}

	@Override
	protected void processLogic() {
		
		//m=Api&a=updateTime
		RequestParams params = new RequestParams();
		params.put("m", "Api");
		params.put("a", "updateTime");
		get(Constants.host, params, new NewUpdateParser(), new JDataCallback<List<List<HotItem>>>() {

			@Override
			public void onSuccess(Response<List<List<HotItem>>> data) {
				
				//这里的目的是将服务端返回的数据与设计的界面匹配  
				List<HotItem> hotItems = new ArrayList<HotItem>();
				for (int i = 0; i < data.getResult().size(); i++) {
					List<HotItem> list = data.getResult().get(i);
					for (int j = 0; j < list.size(); j++) {
						list.get(j).setUpdate(i + "");
					}
					hotItems.addAll(list);
				}
				
				NewUpdateAdapter adapter = new NewUpdateAdapter(getActivity(), hotItems);
				mListView.setAdapter(adapter);
			}
		});
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				HotItem hotItem  = (HotItem) parent.getAdapter().getItem(position);
				String videoId = hotItem.getVideoId()+"";
				Intent detailIntent = new Intent(getActivity(), VideoDetailActivity.class);
				detailIntent.putExtra(HotFragment.VIDEO_ID, videoId);
				detailIntent.putExtra("video_name", hotItem.getName());
				startActivity(detailIntent);
			}
		});
	}
	
}
