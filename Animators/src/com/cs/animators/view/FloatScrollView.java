package com.cs.animators.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 可以监听到Scroll滚动的距离的ScrollView
 * @author Administrator
 *
 */
public class FloatScrollView extends ScrollView {

	public FloatScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FloatScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FloatScrollView(Context context) {
		super(context);
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if(scrollListener != null)
		{
			scrollListener.scroll(t);
		}
	}
	
	public interface OnScrollListener{
		void scroll(int y);
	}
	
	private OnScrollListener  scrollListener ;
	
	public void SetOnScrollListener(OnScrollListener l){
		this.scrollListener = l ;
	}
	

}
