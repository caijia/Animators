package com.cs.animators.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.cs.animators.R;
import com.cs.animators.entity.GroupItem;
import com.cs.animators.util.CommonUtil;
import com.cs.cj.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FindAdapter extends ArrayAdapter<GroupItem> {

	private int mNumColumns ;
	private static final int HORIZONTAL_SPACING = 5 ;
	private static final int PADDING = 8 ;
	
	
	public FindAdapter(Context context, int numColumns, List<GroupItem> objects , GridView gridview) {
		super(context, 0, objects);
		mNumColumns = numColumns ;
		gridview.setNumColumns(numColumns);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null ;
		if(convertView == null)
		{
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_find_item, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		//215 * 150 左右两边的padding == 8  间隔是5dp
		int imageItemWidth = (int) ((CommonUtil.getWidthMetrics(getContext())
				- CommonUtil.dip2px(getContext(), PADDING) * 2
				- (mNumColumns - 1)* CommonUtil.dip2px(getContext(), HORIZONTAL_SPACING)) / (float)mNumColumns);
		int imageItemHeight = (int) (imageItemWidth * 150 / (float)215) ;
		holder.pic.setLayoutParams(new RelativeLayout.LayoutParams(imageItemWidth,imageItemHeight));
		holder.pic.setScaleType(ScaleType.FIT_XY);
		
		GroupItem item = getItem(position);
		if(item != null)
		{
			holder.name.setText(TextUtils.isEmpty(item.getName()) ? "":item.getName());
			ImageLoader.getInstance().displayImage(item.getPic(), holder.pic, ImageLoaderUtil.roundImageLoaderOptions(0));
		}
		return convertView;
	}
	
	 class ViewHolder {
		 
		@InjectView(R.id.find_img_pic) ImageView pic ;
		@InjectView(R.id.find_txt_name) TextView name ;
		
		public ViewHolder(View v)
		{
			ButterKnife.inject(this, v);
		}
	}
}
