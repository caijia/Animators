package com.cs.animators.adapter;

import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.cs.animators.R;
import com.cs.animators.dao.bean.VideoCollect;
import com.cs.cj.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class VideoCollectAdapter extends ActionModeAdapter<VideoCollect> {

	public VideoCollectAdapter(Context context, List<VideoCollect> objects) {
		super(context, 0, objects);
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder = null ;
		if(view == null)
		{
			view = LayoutInflater.from(getContext()).inflate(R.layout.adapter_video_collect_item, parent, false);
			holder = new ViewHolder(view);
			view.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) view.getTag();
		}
		
		VideoCollect item = getItem(position);
		if(item != null)
		{
			ImageLoader.getInstance().displayImage(item.getCover(), holder.cover, ImageLoaderUtil.roundImageLoaderOptions(4));
			holder.name.setText(item.getName());
			holder.category.setText(item.getCategory());
			holder.series.setText("更新至" + item.getCurSeries() +"集  ");
		}
		
		setBackGroundColor(view, position, 0x9934B5E4, Color.TRANSPARENT);
		return view;
	}
	
	class ViewHolder{
		
		@InjectView(R.id.video_collect_img_cover) ImageView cover ;
		@InjectView(R.id.video_collect_txt_name) TextView name ;
		@InjectView(R.id.video_collect_txt_series) TextView series ;
		@InjectView(R.id.video_collect_txt_category) TextView category ;
		
		public ViewHolder(View v)
		{
			ButterKnife.inject(this, v);
		}
	}

}
