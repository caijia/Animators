package com.cs.cj.base;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

public abstract class BasePopupWindow extends PopupWindow implements View.OnClickListener{
	protected Context mContext ;
	private View mContentView  ;
	
	public BasePopupWindow(Context context , int style , int width) {
		super(context);
		init(context , style , width);
	}

	private void init(Context context, int style, int width) {
		mContext = context ;
		setFocusable(true);
		setOutsideTouchable(true);
		setAnimationStyle(style);
		setBackgroundDrawable(new ColorDrawable());
		setTouchable(true);
		loadViewLayout();
		
		setWidth(width);
		setHeight(LayoutParams.WRAP_CONTENT);
		
		findViewById();
		processLogic();
		setListener();
	}
	
	public void setContentView(int resId) {
		mContentView = LayoutInflater.from(mContext).inflate(resId, null);
		super.setContentView(mContentView);
	}
	
	public abstract void loadViewLayout();
	
	public abstract void findViewById();

	public abstract void setListener();

	public abstract void processLogic();
	
	public View findViewById(int viewId) {
		return mContentView.findViewById(viewId);
	}

}
