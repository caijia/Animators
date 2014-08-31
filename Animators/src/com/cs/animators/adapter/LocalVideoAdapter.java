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
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.cs.animationvideo.R;
import com.cs.animators.entity.LocalVideo;
import com.cs.cj.util.FileUtils;

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
			Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(getContext(), localVideo.getVideoPath(), Thumbnails.MICRO_KIND);
			if(bitmap != null)
			{
				holder.thumb.setImageBitmap(bitmap);	
			}
			else
			{
				holder.thumb.setImageResource(R.drawable.defalut_loadimage_icon);
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
