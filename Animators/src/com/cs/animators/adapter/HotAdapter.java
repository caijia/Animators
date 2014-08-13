package com.cs.animators.adapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.cs.animators.R;
import com.cs.animators.entity.HotItem;
import com.cs.cj.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HotAdapter extends ActionModeAdapter<HotItem> {

	public HotAdapter(Context context, List<HotItem> objects) {
		super(context, 0, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null ;
		if(convertView == null)
		{
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_hot_item, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) holder.cover.getLayoutParams();
		int height = (int) (params.width * 240 / (float)200) ;
		params.height = height ;
		holder.cover.setLayoutParams(params);
		
		HotItem item = getItem(position);
		if(item != null)
		{
			holder.name.setText(item.getName());
			holder.category.setText(item.getCategory());
			String score = item.getScore();
			if(!TextUtils.isEmpty(score) && score.contains("."))
			{
				String htmlScore = "<big><font color=" + getScoreColor(score)+ ">" + score.substring(0, score.indexOf("."))+ "</big>"
						+ "<small><font color=" + getScoreColor(score)+ ">" + score.substring(score.indexOf("."))+ "</small>";
				holder.score.setText(Html.fromHtml(htmlScore));
				holder.score.getCompoundDrawables()[1].setLevel((int)Float.parseFloat(item.getScore()));
			}
			
			String htmlCurSeries = "更新至<font color=\"#168FFA\">"
					+ (!TextUtils.isEmpty(item.getTotalNum()) ? item.getCurNum() : item.getUpdate())
					+ "</font>集";
			
			holder.series.setText(Html.fromHtml(htmlCurSeries));
			ImageLoader.getInstance().displayImage(item.getCover(), holder.cover, ImageLoaderUtil.roundImageLoaderOptions(0));
		}
		
		//设置高亮的颜色 变化
		setBackGroundColor(convertView, position, 0x9934B5E4, Color.TRANSPARENT);
		return convertView;
	}
	
	class ViewHolder{
		
		@InjectView(R.id.hot_anim_image_cover) ImageView cover ;
		
		@InjectView(R.id.hot_txt_series) TextView series ;
		
		@InjectView(R.id.hot_txt_score) TextView score ;
		
		@InjectView(R.id.hot_txt_category) TextView category ;
		
		@InjectView(R.id.hot_txt_name) TextView name ;
		
		
		public ViewHolder(View v)
		{
			ButterKnife.inject(this, v);
		}
	}
	
	/**
	 * 根据评分得到颜色
	 * @param score
	 * @return
	 */
	private String getScoreColor(String score){
		int level = (int)Float.parseFloat(score);
		String color = "";
		if(level >= 7)
		{
			color =  getContext().getResources().getString(R.string.score_red);
		}else if(level >= 4){
			color =  getContext().getResources().getString(R.string.score_orange);
			
		}else if(level >= 1){
			color =  getContext().getResources().getString(R.string.score_green);
		}else{
			color =  getContext().getResources().getString(R.string.score_gray);
		}
		return color;
	}
	
}
