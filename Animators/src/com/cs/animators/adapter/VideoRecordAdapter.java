package com.cs.animators.adapter;

import io.vov.vitamio.utils.StringUtils;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.cs.animators.R;
import com.cs.animators.dao.bean.VideoPlayRecord;
import com.cs.animators.util.Utils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class VideoRecordAdapter extends ActionModeAdapter<VideoPlayRecord> implements StickyListHeadersAdapter {

	public VideoRecordAdapter(Context context, List<VideoPlayRecord> objects) {
		super(context, 0, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null ;
		if(convertView ==  null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_video_record, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		VideoPlayRecord item = getItem(position);
		if(item != null){
			holder.videoName.setText(item.getVideoName());
			holder.recordTime.setText("已经观看至"+StringUtils.generateTime(item.getPlayRecord()));
		}
		
		//设置高亮的颜色 变化
		setBackGroundColor(convertView, position, 0x9934B5E4, R.drawable.selector_adapter_video_record);
		return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		VideoPlayRecord item = getItem(position);
		long headerId = 0 ;
		if(item != null){
			headerId = Utils.formatTime(item.getRecordTime()).hashCode();
		}
		return headerId;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		HeadViewHolder holder = null ;
		if(convertView ==  null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_video_record_head, parent, false);
			holder = new HeadViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (HeadViewHolder) convertView.getTag();
		}
		
		VideoPlayRecord item = getItem(position);
		if(item != null){
			holder.videoDate.setText(Utils.formatTime(item.getRecordTime()));
		}
		
		return convertView;
	}

	
	class ViewHolder{
		@InjectView(R.id.video_record_videoname) TextView videoName ;
		@InjectView(R.id.video_record_recordtime) TextView recordTime ;
		
		public ViewHolder(View v){
			ButterKnife.inject(this, v);
		}
		
	}
	
	class HeadViewHolder{
		
		@InjectView(R.id.video_record_head) TextView videoDate ;
		
		public HeadViewHolder(View v){
			ButterKnife.inject(this, v);
		}
	}

}
