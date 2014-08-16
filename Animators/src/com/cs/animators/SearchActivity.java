package com.cs.animators;

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
		
		SearchFragment fragment = new SearchFragment();
		Bundle args = new Bundle();
		args.putString(MainActivity.SEARCH_WORD, mSearchWord);
		fragment.setArguments(args);
		
		FragmentService.getInstance().switchToFragment(mContext, R.id.search_fragment_container, fragment, null);
		
	}
	
	@Override
	public void getExtra(Bundle bundle){
		if(bundle !=null)
		{
			mSearchWord = bundle.getString(MainActivity.SEARCH_WORD);
		}
	}
	
	@Override
	protected boolean displayHomeAsUpEnabled() {
		return true;
	}
	
}
