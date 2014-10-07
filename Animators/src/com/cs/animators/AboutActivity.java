package com.cs.animators;

import com.cs.animationvideo.R;
import com.cs.animators.base.BaseActivity;

public class AboutActivity extends BaseActivity {

	@Override
	protected void loadLayout() {
		setContentView(R.layout.activity_about);
	}

	@Override
	protected void processLogic() {
		mActionBar.setTitle("关于");
	}
	
	@Override
	protected boolean displayHomeAsUpEnabled() {
		return true;
	}

}
