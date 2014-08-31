package com.cs.animators.fragment;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import butterknife.InjectView;
import butterknife.OnItemClick;
import com.cs.animators.FindMoreActivity;
import com.cs.animationvideo.R;
import com.cs.animators.adapter.FindAdapter;
import com.cs.animators.base.BaseFragment;
import com.cs.animators.entity.GroupItem;

public class FindTabItemFragment extends BaseFragment {

	@InjectView(R.id.findtab_gv)
	GridView mGridView ;
	
	private List<GroupItem> mCategory ;
	
	private int mCurPage ;
	
	public static final int PAGE_NUMBER = 6 ;
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.fragment_find_tab);
	}

	@Override
	protected void processLogic() {
		FindAdapter adapter = new FindAdapter(getActivity(), 2, mCategory.subList(mCurPage * PAGE_NUMBER, (mCurPage + 1) * PAGE_NUMBER), mGridView);
		mGridView.setAdapter(adapter);
	}
	
	@Override
	protected void getExtra(Bundle bundle) {
		if(bundle != null){
			mCategory = bundle.getParcelableArrayList(FindFragment.TAB_DATA);
			mCurPage = bundle.getInt(FindFragment.TAB_CURRENT_PAGE);
		}
	}
	
	@OnItemClick(R.id.findtab_gv)
	void onCategoryItemClick(AdapterView<?> parent , View v , int position , long id ){
		Intent intent = new Intent(getActivity(), FindMoreActivity.class);
		intent.putParcelableArrayListExtra(FindFragment.GROUP_ITEM_MORE, (ArrayList<? extends Parcelable>) mCategory);
		intent.putExtra(FindFragment.GROUP_TAB_ITEM, mCurPage * PAGE_NUMBER + position);
		intent.putExtra(FindFragment.GROUP_TITLE, "分类");
		startActivity(intent);
	}
	
	
}
