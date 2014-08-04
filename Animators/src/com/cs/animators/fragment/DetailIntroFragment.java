package com.cs.animators.fragment;

import android.widget.TextView;
import butterknife.InjectView;
import com.cs.animators.R;
import com.cs.animators.VideoDetailActivity;
import com.cs.animators.base.BaseFragment;
import com.cs.animators.entity.VideoDetail;

public class DetailIntroFragment extends BaseFragment {

	@InjectView(R.id.detail_intro_txt_derector)TextView mTxtDerector ;
	@InjectView(R.id.detail_intro_txt_charactor)TextView mTxtCharactor ;
	@InjectView(R.id.detail_intro_txt_category)TextView mTxtCategory;
	@InjectView(R.id.detail_intro_txt_intro)TextView mTxtIntro ;
	
	@Override
	protected void loadLayout() {
		setContentView(R.layout.fragment_detail_intro);
	}

	@Override
	protected void processLogic() {
		
		VideoDetail videoDetail = ((VideoDetailActivity)getActivity()).getVideoDetail();
		mTxtDerector.setText(videoDetail.getDirector());
		mTxtCharactor.setText(videoDetail.getCharacter());
		mTxtCategory.setText(videoDetail.getCategory());
		mTxtIntro.setText(videoDetail.getIntro());
	}
}
