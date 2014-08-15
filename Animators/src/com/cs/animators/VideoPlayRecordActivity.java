package com.cs.animators;

import se.emilsjolander.stickylistheaders.StickyHeaderXListView;
import butterknife.InjectView;

import com.cs.animators.base.BaseActivity;

public class VideoPlayRecordActivity extends BaseActivity {

	@InjectView(R.id.video_record_sticky_lv) StickyHeaderXListView mXListView ;
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.activity_video_record);
	}

	@Override
	protected void processLogic() {

	}

}
