package com.cs.animators.adapter;

import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.cs.animators.R;
import com.cs.animators.entity.HotItem;
import com.cs.animators.util.CommonUtil;
import com.cs.cj.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FindAdapter extends ArrayAdapter<HotItem> {

	private int mNumColumns ;
	private static final int HORIZONTAL_SPACING = 5 ;
	private static final int PADDING = 10 ;
	
	
	public FindAdapter(Context context, int numColumns, List<HotItem> objects , GridView gridview) {
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
		
		
		//200 * 240 左右两边的padding == 10  间隔是5dp
		int imageItemWidth = (int) ((CommonUtil.getWidthMetrics(getContext())
				- CommonUtil.dip2px(getContext(), PADDING) * 2
				- (mNumColumns - 1)* CommonUtil.dip2px(getContext(), HORIZONTAL_SPACING)) / (float)3);
		int imageItemHeight = (int) (imageItemWidth * 240 / (float)200) ;
		holder.pic.setLayoutParams(new RelativeLayout.LayoutParams(imageItemWidth,imageItemHeight));
		holder.pic.setScaleType(ScaleType.FIT_XY);
		
		HotItem item = getItem(position);
		if(item != null)
		{
			holder.name.setText(item.getName());
			ImageLoader.getInstance().displayImage(item.getCover(), holder.pic, ImageLoaderUtil.roundImageLoaderOptions(0));
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
