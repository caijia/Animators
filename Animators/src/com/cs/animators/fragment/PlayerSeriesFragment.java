package com.cs.animators.fragment;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.cs.animators.R;
import com.cs.animators.VideoPlayActivity;
import com.cs.animators.adapter.PlayerSeriesAdapter;
import com.cs.animators.entity.PlayerSeries;
import com.cs.animators.entity.VideoDetail;
import com.cs.animators.entity.VideoDetailSeries;
import com.cs.animators.eventbus.SelectSeriesEvent;
import com.cs.animators.util.CommonUtil;
import com.cs.animators.view.ExpandableGridView;
import com.cs.animators.view.ExpandableGridView.OnItemChildClickListener;
import de.greenrobot.event.EventBus;

public class PlayerSeriesFragment extends DialogFragment {

	@InjectView(R.id.player_series_expand_gv)
	ExpandableGridView mExpandableGridView ;
	
	@InjectView(R.id.txt_series_cancel)
	TextView mTxtCancel ;
	
	public static final int SERIES_LAYOUT_WIDTH = 230 ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, R.style.AnimationDialogStyle);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		LayoutParams params = this.getDialog().getWindow().getAttributes();
		params.dimAmount = 0.0f ;
		params.gravity = Gravity.RIGHT ;
		this.getDialog().getWindow().setAttributes(params);
		getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		getDialog().getWindow().setLayout(CommonUtil.dip2px(getActivity(), SERIES_LAYOUT_WIDTH), CommonUtil.getHeightMetrics(getActivity()));
		getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_player_series, container,false);
		ButterKnife.inject(this, rootView);
		processLogic();
		return rootView;
	}

	private void processLogic() {
		getExtra();
		
		if(mTotalSeries != null && !TextUtils.isEmpty(mVideoId))
		{
			
			List<PlayerSeries> groups = new ArrayList<PlayerSeries>();
			int totalPage = mTotalSeries.size() / PAGE_COUNT + (mTotalSeries.size() % PAGE_COUNT == 0 ? 0 : 1);
			for (int curPage = 0; curPage < totalPage; curPage++) {
				PlayerSeries group = new PlayerSeries() ;
				List<VideoDetailSeries> child = new ArrayList<VideoDetailSeries>();
				String goupTitle = "";
				if(curPage == totalPage - 1)
				{
					goupTitle = (curPage * PAGE_COUNT + 1 ) +  " - " + (mTotalSeries.size() + 1);
					child.addAll(mTotalSeries.subList(curPage * PAGE_COUNT, mTotalSeries.size()));
				}
				else
				{
					goupTitle = (curPage* PAGE_COUNT + 1 )+ " - " + ((curPage + 1) * PAGE_COUNT +1) ;
					child.addAll(mTotalSeries.subList(curPage* PAGE_COUNT, (curPage + 1) * PAGE_COUNT));
				}
				group.setGroupTitle(goupTitle);
				group.setChild(child);
				
				groups.add(group);
			}
			
			PlayerSeriesAdapter adapter = new PlayerSeriesAdapter(getActivity(), groups);
			mExpandableGridView.setAdapter(adapter);
			
			mExpandableGridView.setOnItemChildClickListener(new OnItemChildClickListener() {
				
				@Override
				public void onChildItemClick(ExpandableGridView gridView,int groupPosition, int childPosition) {
					VideoDetailSeries series = (VideoDetailSeries) gridView.getInnerAdapter().getChild(groupPosition, childPosition);
					EventBus.getDefault().post(new SelectSeriesEvent(series , mVideoId));
				}
			});
			
		}
	}
	
	@OnClick(R.id.txt_series_cancel)
	void closeDialog(){
		dismiss();
	}
	
	private String mVideoId ;
	private List<VideoDetailSeries> mTotalSeries ;
	public static final int PAGE_COUNT = 24 ;
	
	private void getExtra(){
		Bundle bundle = getArguments();
		if(bundle != null)
		{
			VideoDetail videoDetail = bundle.getParcelable(VideoPlayActivity.ALL_VIDEO);
			if(videoDetail != null){
				mVideoId = videoDetail.getVideoId();
				mTotalSeries = videoDetail.getEpisode();
			}
		}
	}
	
	
}
