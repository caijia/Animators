package com.cs.animators.adapter;

import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.cs.animators.R;
import com.cs.animators.entity.GroupItem;
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

public class FindAdapter extends ArrayAdapter<GroupItem> {

	private int mNumColumns ;
	private static final int HORIZONTAL_SPACING = 8 ;
	
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
		
		//表示平分为几列
		holder.pic.setLayoutParams(new RelativeLayout.LayoutParams(CommonUtil.getWidthMetrics(getContext()) / mNumColumns,CommonUtil.getWidthMetrics(getContext()) / mNumColumns - CommonUtil.dip2px(getContext(), HORIZONTAL_SPACING)));
		holder.pic.setScaleType(ScaleType.FIT_XY);
		
		//note : 因为在xml文件中如果设置singleLine = true gravity = center 会导致触摸TextView区域ViewPager不能滑动 所有用这种方式设计单行
		holder.name.setMaxLines(1);
		
		GroupItem item = getItem(position);
		if(item != null)
		{
			holder.name.setText(item.getName());
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
