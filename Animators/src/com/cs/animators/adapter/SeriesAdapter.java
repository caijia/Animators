package com.cs.animators.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.cs.animators.R;
import com.cs.animators.entity.VideoDetailSeries;
import com.cs.animators.fragment.DetailSeriesFragment;
import com.cs.animators.util.CommonUtil;

public class SeriesAdapter extends ArrayAdapter<VideoDetailSeries> {
	
	private int mCurPage ;
	private int mLastPlaySeries ;
	private static final int NUM_COLUMN = 5;
	private static final int HORIZONTAL_SPACING = 8 ;
	
	public SeriesAdapter(Context context, int lastPlaySeries,int curPage, List<VideoDetailSeries> objects) {
		super(context, 0, objects);
		mCurPage = curPage ;
		mLastPlaySeries = lastPlaySeries ;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null ;
		if(convertView == null)
		{
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_detail_series_item, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.seriesNum.setLayoutParams(new LayoutParams(CommonUtil.getWidthMetrics(getContext()) / NUM_COLUMN, CommonUtil.getWidthMetrics(getContext()) / NUM_COLUMN - CommonUtil.dip2px(getContext(), HORIZONTAL_SPACING)));
		
		VideoDetailSeries item = getItem(position);
		if(item != null)
		{
			int series = mCurPage * DetailSeriesFragment.PAGE_COUNT + position + 1;
			holder.seriesNum.setText(series + "");
			if(series == mLastPlaySeries){
				holder.seriesNum.setBackgroundResource(R.drawable.selector_player_series_curpaly_series);
				holder.seriesNum.setTextColor(getContext().getResources().getColor(R.color.white));
			}else{
				holder.seriesNum.setBackgroundResource(R.drawable.selector_seires_item);
				holder.seriesNum.setTextColor(getContext().getResources().getColor(R.color.gray_light));
			}
		}
		return convertView;
	}
	
	
	class ViewHolder{
		@InjectView(R.id.detail_series_txt_seriesnum) TextView seriesNum ;
		
		public ViewHolder(View v)
		{
			ButterKnife.inject(this, v);
		}
		
	}
	
	public void setLastPlaySeries(int lastPlaySeries){
		this.mLastPlaySeries = lastPlaySeries;
		notifyDataSetChanged();
	}

}
