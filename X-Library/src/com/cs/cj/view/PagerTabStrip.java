package com.cs.cj.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.caijia.library.R;

public class PagerTabStrip extends HorizontalScrollView {

	private int mIndicatorColor = 0xFF666666;
	
	private int mUnderlineColor = 0x1A000000;
	
	private int mDividerColor = 0x1A000000;
	
	private int mIndicatorHeight = 6;
	
	private int mUnderlineHeight = 2;
	
	private int mDiverderPadding = 12;
	
	private int mTabPaddingLeftRight = 24;
	
	private int mTabBackground = R.drawable.background_tab;
	
	private boolean mShouldExpand = false;
	
	private int mTextColor = R.drawable.slide_tabindicator_selector;
	
	private int mTextSize = 14;
	
	private LinearLayout mTabsContainer ;
	
	private LinearLayout.LayoutParams mDefaultParams ;
	
	private LinearLayout.LayoutParams mExpandParams ;
	
	private int mSelectedTabPosition ;
	
	private Paint mIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint mUnderlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	private int mTabCount ;
	
	public PagerTabStrip(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public PagerTabStrip(Context context) {
		this(context,null);
	}
	
	public PagerTabStrip(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		if(isInEditMode())
			return ;
		
		setFillViewport(true);
		setWillNotDraw(false);
		
		mTabsContainer = new LinearLayout(getContext());
		mTabsContainer.setOrientation(LinearLayout.HORIZONTAL);
		mTabsContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		
		mDefaultParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
		mExpandParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT , 1.0f);
		
		addView(mTabsContainer);
		
		DisplayMetrics dm = getResources().getDisplayMetrics();

		mIndicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mIndicatorHeight, dm);
		mUnderlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mUnderlineHeight, dm);
		mDiverderPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mDiverderPadding, dm);
		mTabPaddingLeftRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mTabPaddingLeftRight, dm);
		mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, dm);

		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PagerTabStrip);
		mIndicatorColor = a.getColor(R.styleable.PagerTabStrip_ptsIndicatorColor, mIndicatorColor);
		mUnderlineColor = a.getColor(R.styleable.PagerTabStrip_ptsUnderlineColor, mUnderlineColor);
		mDividerColor = a.getColor(R.styleable.PagerTabStrip_ptsDividerColor, mDividerColor);
		mIndicatorHeight = a.getDimensionPixelSize(R.styleable.PagerTabStrip_ptsIndicatorHeight, mIndicatorHeight);
		mUnderlineHeight = a.getDimensionPixelSize(R.styleable.PagerTabStrip_ptsUnderlineHeight, mUnderlineHeight);
		mDiverderPadding = a.getDimensionPixelSize(R.styleable.PagerTabStrip_ptsDividerPadding, mDiverderPadding);
		mTabPaddingLeftRight = a.getDimensionPixelSize(R.styleable.PagerTabStrip_ptsTabPaddingLeftRight, mTabPaddingLeftRight);
		mTabBackground = a.getResourceId(R.styleable.PagerTabStrip_ptsTabBackground, mTabBackground);
		mShouldExpand = a.getBoolean(R.styleable.PagerTabStrip_ptsShouldExpand, mShouldExpand);
		mTextColor = a.getResourceId(R.styleable.PagerTabStrip_ptsTextColor, mTextColor);
		mTextSize = a.getDimensionPixelSize(R.styleable.PagerTabStrip_ptsTextSize, mTextSize);
		
		mIndicatorPaint.setColor(mIndicatorColor);
		mIndicatorPaint.setStrokeWidth(mIndicatorHeight);
		
		mUnderlinePaint.setColor(mUnderlineColor);
		mIndicatorPaint.setStrokeWidth(mUnderlineHeight);
		
		mDividerPaint.setColor(mDividerColor);
		
		a.recycle();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(isInEditMode() && mTabCount == 0)
		{
			return ;
		}
		
		final int height = getHeight();
		
		//选中的Tab
		mTabCount = mTabsContainer.getChildCount();
		for (int i = 0; i < mTabCount; i++) {
			View childView = mTabsContainer.getChildAt(i);
			boolean isSelected = (i == mSelectedTabPosition);
			childView.setSelected(isSelected);
		}
		
		//画选中Tab下面的指示线
		View selectedTab = mTabsContainer.getChildAt(mSelectedTabPosition);
		int lineLeft = selectedTab.getLeft();
		int lineRight = selectedTab.getRight();
		
		canvas.drawRect(lineLeft, height - mIndicatorHeight, lineRight, height, mIndicatorPaint);
		
		//画最下面的线
		if (mShouldExpand) {
			canvas.drawRect(0, height - mUnderlineHeight, getResources().getDisplayMetrics().widthPixels, height, mUnderlinePaint);
		} else {
			canvas.drawRect(0, height - mUnderlineHeight, mTabsContainer.getWidth(), height, mUnderlinePaint);
		}
		
		
		//画两个Tab的分割线
		for (int i = 0; i < mTabCount - 1; i++) {
			View tab = mTabsContainer.getChildAt(i);
			canvas.drawLine(tab.getRight(), mDiverderPadding, tab.getRight(), height - mDiverderPadding, mDividerPaint);
		}
		
	}
	
	private FragmentTabAdapter mAdapter ;
	
	public void setAdapter(FragmentTabAdapter adapter){
		if(adapter == null || adapter.getCount() == 0)
		{
			throw new RuntimeException("does not adapter instance .");
		}
		
		mAdapter = adapter ;
		notifyDataSetChanged();
	}
	
	public void notifyDataSetChanged(){
		mTabsContainer.removeAllViews();
		
		for (int i = 0; i < mAdapter.getCount(); i++) {
			addTab(i);
			mAdapter.switchFragment(mAdapter.getItem(mAdapter.getCount() - 1 -i), mAdapter.getCount() - 1 -i);
		}
		
	}
	
	
	private void addTab(final int position) {
		TabView tabView = new TabView(getContext());
		tabView.setText(mAdapter.getPagetTitle(position));
		tabView.setFocusable(false);
		tabView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//切换Fragment
				setCurrentTab(position);
			}
		});
		
		tabView.setPadding(mTabPaddingLeftRight, 0, mTabPaddingLeftRight, 0);
		mTabsContainer.addView(tabView , position , mShouldExpand ? mExpandParams : mDefaultParams);
		
	}

	
	public void setCurrentTab(int position)
	{
		mSelectedTabPosition = position ;
		invalidate();
		
		if(pageListener != null){
			pageListener.pageSelected(position);
		}
		mAdapter.switchFragment(mAdapter.getItem(position), position);
		
	}

	private class TabView extends TextView {
		public TabView(Context context) {
			super(context);
			this.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
			ColorStateList cls = getResources().getColorStateList(mTextColor);
			this.setTextColor(cls);
			this.setBackgroundResource(mTabBackground);
			this.setGravity(Gravity.CENTER);
		}
	}
	
	public interface OnPageSelectListener{
		void pageSelected(int position);
	}
	
	private OnPageSelectListener pageListener ;
	
	public void setOnPageSelectListener(OnPageSelectListener l){
		pageListener = l ;
	}
}
