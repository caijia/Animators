package com.cs.animators.adapter;

import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.cs.animationvideo.R;
import com.cs.animators.entity.ThemeItem;
import com.cs.animators.util.CommonUtil;
import com.cs.cj.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class ThemeAdapter extends ArrayAdapter<ThemeItem> {
	
	private static final int PADDING = 10 ;
	
	public ThemeAdapter(Context context, List<ThemeItem> objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null ;
		if(convertView == null )
		{
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_theme_item, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		int picWidth = CommonUtil.getWidthMetrics(getContext()) - CommonUtil.dip2px(getContext(), PADDING) * 2 ;
		int picHeight = (int) (picWidth / (float)2) ;
		LayoutParams layoutParams = (LayoutParams) holder.pic.getLayoutParams();
		layoutParams.width = picWidth ;
		layoutParams.height = picHeight ;
		holder.pic.setLayoutParams(layoutParams);
		
		ThemeItem item = getItem(position);
		if(item != null)
		{
			holder.name.setText(item.getName());
			holder.lastup.setText(item.getLastup());
			ImageLoader.getInstance().displayImage(item.getPic(), holder.pic, ImageLoaderUtil.roundImageLoaderOptions(0,R.drawable.image_loading));
		}
		
		return convertView;
	}
	
	class ViewHolder {
		 
		@InjectView(R.id.newupdate_img_pic) ImageView pic ;
		@InjectView(R.id.newupdate_txt_name) TextView name ;
		@InjectView(R.id.newupdate_txt_lastup) TextView lastup ;
		
		public ViewHolder(View v)
		{
			ButterKnife.inject(this, v);
		}
	}

}
