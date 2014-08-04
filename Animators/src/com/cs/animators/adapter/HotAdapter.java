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
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HotAdapter extends ArrayAdapter<HotItem> {

	private SparseBooleanArray mSelectedItemIds ;
	
	public HotAdapter(Context context, List<HotItem> objects) {
		super(context, 0, objects);
		mSelectedItemIds = new SparseBooleanArray();
	}
	
	public void toggleSelection(int position){
		boolean isSelected = mSelectedItemIds.get(position);
		if(!isSelected)
		{
			mSelectedItemIds.put(position, true);
		}
		else
		{
			mSelectedItemIds.delete(position);
		}
		notifyDataSetChanged();
	}
	
	public void removeAllSelectedView(){
		mSelectedItemIds.clear();
		notifyDataSetChanged();
	}
	
	public int getSelectedItemCount(){
		return mSelectedItemIds.size();
	}
	
	public SparseBooleanArray getSelectedItemIds(){
		return mSelectedItemIds ;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HViewHolder holder = null ;
		if(convertView == null)
		{
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_hot_item, parent, false);
			holder = new HViewHolder(convertView);
			convertView.setTag(holder);
		}
		else{
			holder = (HViewHolder) convertView.getTag();
		}
		
		HotItem item = getItem(position);
		if(item != null)
		{
			holder.name.setText(item.getName());
			holder.category.setText(item.getCategory());
			holder.score.setText(item.getScore());
			//这里为了重用adapter
			holder.totalNum.setText(!TextUtils.isEmpty(item.getTotalNum()) ? item.getTotalNum() : item.getUpdate());
			ImageLoader.getInstance().displayImage(item.getCover(), holder.cover, ImageLoaderUtil.roundImageLoaderOptions(4));
		}
		
		convertView.setBackgroundColor(mSelectedItemIds.get(position) ? 0x9934B5E4 : Color.TRANSPARENT);
		return convertView;
	}
	
	class HViewHolder{
		
		@InjectView(R.id.hot_anim_image_cover) ImageView cover ;
		
		@InjectView(R.id.hot_txt_series) TextView totalNum ;
		
		@InjectView(R.id.hot_txt_score) TextView score ;
		
		@InjectView(R.id.hot_txt_category) TextView category ;
		
		@InjectView(R.id.hot_txt_name) TextView name ;
		
		
		public HViewHolder(View v)
		{
			ButterKnife.inject(this, v);
		}
	}
}
