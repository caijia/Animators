package com.cs.animators.adapter;

import io.vov.vitamio.utils.StringUtils;
import java.util.List;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.cs.animationvideo.R;
import com.cs.animators.entity.LocalVideo;
import com.cs.cj.util.FileUtils;
import com.cs.cj.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class LocalVideoAdapter extends ArrayAdapter<LocalVideo> {
	
	public LocalVideoAdapter(Context context,List<LocalVideo> objects) {
		super(context, 0, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null ;
		if(convertView == null)
		{
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_localvideo_item, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) holder.thumb.getLayoutParams();
		int height = (int) (params.width * 240 / (float)200) ;
		params.height = height ;
		holder.thumb.setLayoutParams(params);
		
		//表示平分为几列
		LocalVideo localVideo = getItem(position);
		if(localVideo != null)
		{
			holder.name.setText(localVideo.getVideoName());
			holder.duration.setText(StringUtils.generateTime(localVideo.getVideoDuration()));
			if(!TextUtils.isEmpty(localVideo.getVideoThumb())){
				ImageLoader.getInstance().displayImage("file://" + localVideo.getVideoThumb(), holder.thumb, ImageLoaderUtil.FadeInImageLoaderOptions(300));
			}
			String fileSize = FileUtils.getFilesSize(localVideo.getVideoPath());
			holder.size.setText(fileSize);
		}
		return convertView;
	}
	
	class ViewHolder{
		
		@InjectView(R.id.localvideo_img_pic) ImageView thumb ;
		@InjectView(R.id.localvideo_txt_name) TextView name ;
		@InjectView(R.id.localvideo_txt_duration) TextView duration ;
		@InjectView(R.id.localvideo_txt_size) TextView size ;
		
		public ViewHolder(View v)
		{
			ButterKnife.inject(this, v);
		}
	}

}
