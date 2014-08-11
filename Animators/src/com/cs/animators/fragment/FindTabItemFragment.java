package com.cs.animators.fragment;

import android.os.Bundle;
import android.widget.GridView;
import butterknife.InjectView;
import com.cs.animators.R;
import com.cs.animators.adapter.FindAdapter;
import com.cs.animators.base.BaseFragment;
import com.cs.animators.constants.Constants;
import com.cs.animators.entity.AllCategory;
import com.cs.cj.http.httplibrary.RequestParams;
import com.cs.cj.http.work.JDataCallback;
import com.cs.cj.http.work.Response;

public class FindTabItemFragment extends BaseFragment {

	@InjectView(R.id.findtab_gv)
	GridView mGridView ;
	
	//通过这个id可以取得更多的视频信息
	private String mId ;
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.fragment_find_tab);
	}

	@Override
	protected void processLogic() {
		getExtra();
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
				FindAdapter adapter = new FindAdapter(getActivity(), 3, data.getResult().getList(), mGridView);
				mGridView.setAdapter(adapter);
			}
		});
		
	}

	private void getExtra(){
		Bundle bundle = getArguments();
		if(bundle != null){
			mId = bundle.getString(FindFragment.TAB_ID_KEY);
		}
	}
	
}
