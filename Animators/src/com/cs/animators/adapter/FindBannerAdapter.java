package com.cs.animators.adapter;

import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.cs.animators.R;
import com.cs.animators.VideoDetailActivity;
import com.cs.animators.entity.BannerItem;
import com.cs.animators.fragment.HotFragment;
import com.cs.cj.adapter.RecyclingPagerAdapter;
import com.cs.cj.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FindBannerAdapter extends RecyclingPagerAdapter {

	private Activity mContext ;
	private List<BannerItem> mAreaList ;
	
	public FindBannerAdapter(Activity context , List<BannerItem> list){
		mContext = context ;
		mAreaList = list ;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup container) {
		ViewHolder holder =  null ;
		if(convertView == null)
		{
			convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_find_banner, container, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		//宽高比是 1280 *　800
		holder.pic.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		holder.pic.setScaleType(ScaleType.FIT_XY);
//		holder.pic.setAdjustViewBounds(true);
		
		final BannerItem item = mAreaList.get(position % mAreaList.size());
		if(item != null)
		{
			holder.name.setText(item.getName());
			ImageLoader.getInstance().displayImage(item.getPic(), holder.pic, ImageLoaderUtil.roundImageLoaderOptions(0, R.drawable.image_loading));
		}
		
		holder.pic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String videoId = item.getId()+"";
				Intent detailIntent = new Intent(mContext , VideoDetailActivity.class);
				detailIntent.putExtra(HotFragment.VIDEO_ID, videoId);
				detailIntent.putExtra("video_name", item.getName());
				mContext.startActivity(detailIntent);
			}
		});
		
		return convertView;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}
	
	class ViewHolder {
		@InjectView(R.id.find_banner_img_pic) ImageView pic ;
		@InjectView(R.id.find_banner_txt_name) TextView name ;
		
		public ViewHolder(View v)
		{
			ButterKnife.inject(this, v);
		}
	}
	
}
