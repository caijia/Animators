package com.cs.animators.adapter;

import io.vov.vitamio.ThumbnailUtils;
import io.vov.vitamio.provider.MediaStore.Video.Thumbnails;
import io.vov.vitamio.utils.StringUtils;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.cs.animators.R;
import com.cs.animators.entity.LocalVideo;
import com.cs.animators.util.CommonUtil;

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
		
		//表示平分为几列
		holder.thumb.setLayoutParams(new RelativeLayout.LayoutParams(CommonUtil.getWidthMetrics(getContext()) / 3,CommonUtil.getWidthMetrics(getContext()) / 3 - CommonUtil.dip2px(getContext(), 5)));
		holder.thumb.setScaleType(ScaleType.FIT_XY);
		
		LocalVideo localVideo = getItem(position);
		if(localVideo != null)
		{
			holder.name.setText(localVideo.getVideoName());
			holder.duration.setText(StringUtils.generateTime(localVideo.getVideoDuration()));
			Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(getContext(), localVideo.getVideoPath(), Thumbnails.MINI_KIND);
			holder.thumb.setImageBitmap(bitmap);
		}
		return convertView;
	}
	
	class ViewHolder{
		
		@InjectView(R.id.localvideo_img_pic) ImageView thumb ;
		@InjectView(R.id.localvideo_txt_name) TextView name ;
		@InjectView(R.id.localvideo_txt_duration) TextView duration ;
		
		public ViewHolder(View v)
		{
			ButterKnife.inject(this, v);
		}
	}

}
