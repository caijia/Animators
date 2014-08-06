package com.cs.animators.adapter;

import java.util.List;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;

public class ActionModeAdapter<T> extends ArrayAdapter<T> {

	protected SparseBooleanArray mSelectedItemIds;

	public ActionModeAdapter(Context context, int resource,
			int textViewResourceId, List<T> objects) {
		super(context, resource, textViewResourceId, objects);
		init();
	}

	public ActionModeAdapter(Context context, int resource,
			int textViewResourceId, T[] objects) {
		super(context, resource, textViewResourceId, objects);
		init();
	}

	public ActionModeAdapter(Context context, int resource,
			int textViewResourceId) {
		super(context, resource, textViewResourceId);
		init();
	}

	public ActionModeAdapter(Context context, int resource, List<T> objects) {
		super(context, resource, objects);
		init();
	}

	public ActionModeAdapter(Context context, int resource, T[] objects) {
		super(context, resource, objects);
		init();
	}

	public ActionModeAdapter(Context context, int resource) {
		super(context, resource);
		init();
	}
	
	private void init(){
		mSelectedItemIds = new SparseBooleanArray();
	}

	public void toggleSelection(int position) {
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

	public void removeAllSelectedView() {
		mSelectedItemIds.clear();
		notifyDataSetChanged();
	}
	
	public int getSelectedItemCount() {
		return mSelectedItemIds.size();
	}

	public SparseBooleanArray getSelectedItemIds() {
		return mSelectedItemIds ;
	}
	
	public void setBackGroundColor(View convertView ,int position ,int pressedColor , int normalColor)
	{
		convertView.setBackgroundColor(mSelectedItemIds.get(position) ? pressedColor : normalColor);
	}

}