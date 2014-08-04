package com.cs.animators.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.cs.animators.R;
import com.cs.animators.entity.DrawerItem;

public class DrawerAdapter extends ArrayAdapter<DrawerItem> {

	public static final int ABOVE = 0 ;
	public static final int BELOW = 1 ;
	
	public DrawerAdapter(Context context,  List<DrawerItem> objects) {
		super(context, 0, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null ;
		int type = getItemViewType(position);
		
		if(convertView == null)
		{
			switch (type) {
			case ABOVE:
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_drawer_above, parent, false);
				break;

			case BELOW:
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_drawer_below, parent, false);
				break;
			}
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		DrawerItem item = getItem(position);
		if(item != null)
		{
			holder.drawerImage.setImageDrawable(item.getDrawerImage());
			holder.drawerTitle.setText(item.getDrawerTitle());
		}
		
		return convertView;
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	
	@Override
	public int getItemViewType(int position) {
		if(position == 0 || position == 1 || position == 2)
		{
			return ABOVE;
		}
		return BELOW;
	}
	
	class ViewHolder{
		
		@InjectView(R.id.main_img_drawer) ImageView drawerImage ;
		@InjectView(R.id.main_txt_drawer) TextView drawerTitle ;
		
		public ViewHolder(View v)
		{
			ButterKnife.inject(this, v);
		}
	}
}
