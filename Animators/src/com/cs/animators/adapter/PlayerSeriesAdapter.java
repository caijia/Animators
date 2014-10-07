package com.cs.animators.adapter;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.cs.animationvideo.R;
import com.cs.animators.entity.PlayerSeries;
import com.cs.animators.entity.VideoDetailSeries;
import com.cs.animators.fragment.PlayerSeriesFragment;
import com.cs.animators.util.CommonUtil;

public class PlayerSeriesAdapter extends BaseExpandableListAdapter {

	private Context mContext ;
	private List<PlayerSeries> mGroup ;
	public static final int PAGE_COUNT = 24 ;
	
	public PlayerSeriesAdapter(Context context , List<PlayerSeries> group)
	{
		mContext = context ;
		mGroup = group ;
	}
	
	@Override
	public int getGroupCount() {
		return mGroup.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mGroup.get(groupPosition).getChild().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mGroup.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mGroup.get(groupPosition).getChild().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
		GroupViewHolder holder = null ;
		if(convertView == null)
		{
			convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_playerseries_group_item, parent, false);
			holder = new GroupViewHolder(convertView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (GroupViewHolder) convertView.getTag();
		}
		
		PlayerSeries groupItem = (PlayerSeries) getGroup(groupPosition);
		holder.groupTitle.setText(groupItem.getGroupTitle());
		holder.expandablePic.setImageResource(isExpanded ? R.drawable.arrow_up : R.drawable.arrow_down);
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
		TextView textView = null ;
		if(convertView == null)
		{
			textView = new TextView(mContext);
		}
		else
		{
			textView = (TextView) convertView;
		}
		
		textView.setLayoutParams(new AbsListView.LayoutParams(CommonUtil.getWidthMetrics(mContext) / 4, CommonUtil.dip2px(mContext, PlayerSeriesFragment.SERIES_LAYOUT_WIDTH / 4)));
		
		VideoDetailSeries detailSeries = (VideoDetailSeries) getChild(groupPosition, childPosition);
		boolean curPlay = detailSeries.isCurPlay();
		if(curPlay){
			textView.setBackgroundResource(R.drawable.selector_player_series_curpaly_series);
			textView.setTextColor(mContext.getResources().getColor(R.color.white));
		}else{
			textView.setBackgroundResource(R.drawable.shape_player_series);
			textView.setTextColor(mContext.getResources().getColor(R.color.player_series_child_color));
		}
		textView.setGravity(Gravity.CENTER);
		
		String curSeries = groupPosition * PAGE_COUNT + childPosition + 1+"";
		textView.setText(curSeries);
		return textView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	class GroupViewHolder{
		
		@InjectView(R.id.txt_playser_series) TextView groupTitle ;
		@InjectView(R.id.img_playser_series_arrow) ImageView expandablePic ;
		
		public GroupViewHolder(View v )
		{
			ButterKnife.inject(this, v);
		}
	}
	
}
