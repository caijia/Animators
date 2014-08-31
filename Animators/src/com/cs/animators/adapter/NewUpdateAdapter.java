package com.cs.animators.adapter;

import java.util.List;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.cs.animationvideo.R;
import com.cs.animators.entity.HotItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewUpdateAdapter extends HotAdapter implements StickyListHeadersAdapter {

	public NewUpdateAdapter(Context context, List<HotItem> objects) {
		super(context,objects);
	}
	
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		
//		ViewHolder holder = null ;
//		if(convertView == null)
//		{
//			convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_hot_item, parent, false);
//			holder = new ViewHolder(convertView);
//			convertView.setTag(holder);
//		}
//		else{
//			holder = (ViewHolder) convertView.getTag();
//		}
//		
//		HotItem item = getItem(position);
//		if(item != null)
//		{
//			holder.updateText.setText("更新至");
//			holder.name.setText(item.getName());
//			holder.score.setText(item.getScore());
//			holder.category.setText(item.getCategory());
//			holder.updateSeries.setText(item.getCurNum());
//			ImageLoader.getInstance().displayImage(item.getCover(), holder.cover, ImageLoaderUtil.roundImageLoaderOptions(4));
//		}
//		
//		return convertView;
//	}
	
	class HeaderViewHolder{
		
		@InjectView(R.id.newupdate_txt_updatetime) TextView updateTime ;
		
		public HeaderViewHolder(View v)
		{
			ButterKnife.inject(this, v);
		}
	}
	
//	class ViewHolder{
//		
//		@InjectView(R.id.hot_txt_name) TextView name ;
//		@InjectView(R.id.hot_txt_series) TextView updateSeries ;
//		@InjectView(R.id.hot_img_score) ImageView score ;
//		@InjectView(R.id.hot_txt_category) TextView category ;
//		@InjectView(R.id.hot_txt_total) TextView updateText ;
//		@InjectView(R.id.hot_anim_image_cover) ImageView cover ;
//		
//		public ViewHolder(View v)
//		{
//			ButterKnife.inject(this, v);
//		}
//	}
	

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		HeaderViewHolder holder = null ;
		if(convertView == null)
		{
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_newupdate , parent, false);
			holder = new HeaderViewHolder(convertView);
			convertView.setTag(holder);
		}
		else{
			holder = (HeaderViewHolder) convertView.getTag();
		}
		
		//设置头部的内容
		HotItem item = getItem(position);
		if(item != null)
		{
			holder.updateTime.setText(getUpdateTime(Integer.parseInt(item.getUpdate())));
		}
		return convertView;
	}

	private String getUpdateTime(int update) {
		String updateTime = "";
		switch (update) {
		case 0:
			updateTime = "周日更新" ;
			break;
		case 1:
			updateTime = "周一更新" ;
			break;
		case 2:
			updateTime = "周二更新" ;
			break;
		case 3:
			updateTime = "周三更新" ;
			break;
		case 4:
			updateTime = "周四更新" ;
			break;
		case 5:
			updateTime = "周五更新" ;
			break;
		case 6:
			updateTime = "周六更新" ;
			break;

		default:
			break;
		}
		return updateTime;
	}

	@Override
	public long getHeaderId(int position) {
		return Integer.valueOf(getItem(position).getUpdate());
	}
	
}
