package com.cs.animators;

import android.content.Intent;
import android.os.Bundle;
import com.cs.animators.base.BaseActivity;
import com.cs.animators.fragment.SearchFragment;
import com.cs.cj.service.FragmentService;

public class SearchActivity extends BaseActivity {
	
	private String mSearchWord ;
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.activity_search);
	}

	@Override
	protected void processLogic() {
		getExtra();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		
		SearchFragment fragment = new SearchFragment();
		Bundle args = new Bundle();
		args.putString(MainActivity.SEARCH_WORD, mSearchWord);
		fragment.setArguments(args);
		
		FragmentService.getInstance().switchToFragment(mContext, R.id.search_fragment_container, fragment, null);
		
	}
	
	private void getExtra(){
		Bundle bundle = getIntent().getExtras();
		if(bundle !=null)
		{
			mSearchWord = bundle.getString(MainActivity.SEARCH_WORD);
		}
	}
	
	
	@Override
	public Intent getSupportParentActivityIntent() {
		finish();
		return super.getSupportParentActivityIntent();
	}
	
}
