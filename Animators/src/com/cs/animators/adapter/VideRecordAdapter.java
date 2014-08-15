package com.cs.animators.adapter;

import java.util.List;
import com.cs.animators.dao.bean.VideoPlayRecord;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class VideRecordAdapter extends ArrayAdapter<VideoPlayRecord> implements StickyListHeadersAdapter {

	public VideRecordAdapter(Context context, List<VideoPlayRecord> objects) {
		super(context, 0, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return super.getView(position, convertView, parent);
	}

	@Override
	public long getHeaderId(int position) {
		return 0;
	}

	@Override
	public View getHeaderView(int position, View view, ViewGroup parent) {
		return null;
	}


}
