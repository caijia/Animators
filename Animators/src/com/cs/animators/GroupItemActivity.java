package com.cs.animators;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import butterknife.InjectView;
import butterknife.OnItemClick;

import com.cs.animators.adapter.FindAdapter;
import com.cs.animators.base.BaseActivity;
import com.cs.animators.entity.GroupItem;
import com.cs.animators.fragment.FindFragment;

/**
 * 点击FindFragment中加载更多跳转而来
 * @Author android_nihao (caijia)
 * @Time 2014-7-15 下午3:09:46
 */
public class GroupItemActivity extends BaseActivity {
	
	@InjectView(R.id.groupitem_gv)
	GridView mGridView ;
	
	@Override
	protected void processLogic(){
		mActionBar.setDisplayHomeAsUpEnabled(true);
		//取得参数
		Bundle bundle = getIntent().getExtras();
		if(bundle != null)
		{
			List<GroupItem> groupItems = new ArrayList<GroupItem>();
			groupItems = bundle.getParcelableArrayList(FindFragment.EXTRA_GROUPITEM_KEY);
			
			FindAdapter adapter = new FindAdapter(mContext, 3, groupItems,mGridView);
			mGridView.setAdapter(adapter);
			
		}
	}

	@Override
	protected void loadLayout() {
		setContentView(R.layout.activity_group_item);
	}
	
	@OnItemClick(R.id.groupitem_gv)
	void themeItemClick(AdapterView<?> parent , View v, int position , long id){
		GroupItem item = (GroupItem) parent.getAdapter().getItem(position);
		Intent intent = new Intent(this, AllCategoryActivity.class);
		intent.putExtra(FindFragment.ID_KEY, item.getId());
		startActivity(intent);
	}
	
	@Override
	public Intent getSupportParentActivityIntent() {
		finish();
		return super.getSupportParentActivityIntent();
	}
	
}
