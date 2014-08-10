package com.cs.cj.view;

import java.util.List;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
	
	private List<TabSpec> mTabs;
	
	private int mViewGroupId ;
	
	private LinearLayout mTabsContainer ;
	
	private LinearLayout.LayoutParams mDefaultParams ;
	
	private LinearLayout.LayoutParams mExpandParams ;
	
	private int mSelectedTabPosition ;
	
	private FragmentManager mFragmentManager ;
	
	private TabSpec mCurTabSpec ;
	
	private Paint mIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint mUnderlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	private int mTabCount ;
	
	private int mCurrentTabPosition ;
	
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
		canvas.drawRect(0, height - mUnderlineHeight, getResources().getDisplayMetrics().widthPixels, height, mUnderlinePaint);
		
		//画两个Tab的分割线
		for (int i = 0; i < mTabCount - 1; i++) {
			View tab = mTabsContainer.getChildAt(i);
			canvas.drawLine(tab.getRight(), mDiverderPadding, tab.getRight(), height - mDiverderPadding, mDividerPaint);
		}
		
	}
	
	/**
	 * 
	 * @param tabs Tab页面
	 * @param manager fragment 的管理
	 * @param viewGroupId Fragment 的容器id
	 */
	public void setPagerTab(List<TabSpec> tabs , FragmentManager manager , int viewGroupId){
		if(tabs == null || tabs.size() == 0)
		{
			throw new RuntimeException("does not tabs instance .");
		}
		mTabs = tabs ;
		mFragmentManager = manager ;
		mViewGroupId = viewGroupId ;
		mTabCount = tabs.size();
		
		notifyDataSetChanged();
	}
	
	public void notifyDataSetChanged(){
		mTabsContainer.removeAllViews();
		
		for (int i = 0; i < mTabs.size(); i++) {
			addTab(i , mTabs.get(i));
		}
		invalidate();
	}
	
	
	private void addTab(final int position, final TabSpec tabSpec) {
		TabView tabView = new TabView(getContext());
		tabView.setText(tabSpec.getTitle());
		tabView.setFocusable(true);
		tabView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//切换Fragment
				mSelectedTabPosition = position ;
				invalidate();
				switchFragment(tabSpec);
			}
		});
		
		if(position == mCurrentTabPosition)
		{
			String tag = tabSpec.getFragment().getClass().getName() + tabSpec.getTitle();
			mFragmentManager.beginTransaction().add(mViewGroupId, tabSpec.getFragment(), tag).commit();
			mCurTabSpec = tabSpec ;
		}
		tabView.setPadding(mTabPaddingLeftRight, 0, mTabPaddingLeftRight, 0);
		mTabsContainer.addView(tabView , position , mShouldExpand ? mExpandParams : mDefaultParams);
	}

	
	public void setCurrentTab(int currentTab)
	{
		mCurrentTabPosition = currentTab;
		notifyDataSetChanged();
	}

	/**
	 * 切换Fragment
	 * @param tabSpec 将要切换的页面
	 */
	protected void switchFragment(TabSpec tabSpec) {
		Fragment toFragment = tabSpec.getFragment();
		String simpleName = toFragment.getClass().getName();
		String tabTitle = tabSpec.getTitle();
		String defaultTag = simpleName + tabTitle ;
		Fragment oldFragment = mFragmentManager.findFragmentByTag(defaultTag);
		if(oldFragment != null)
		{
			mFragmentManager.beginTransaction().show(oldFragment).hide(mCurTabSpec.getFragment()).commit();
		}
		else
		{
			if(mCurTabSpec == null)
			{
				mFragmentManager.beginTransaction().add(mViewGroupId, toFragment, defaultTag).commit();
			}
			else
			{
				mFragmentManager.beginTransaction().add(mViewGroupId, toFragment, defaultTag).hide(mCurTabSpec.getFragment()).commit();
			}
		}
		mCurTabSpec = tabSpec ;
	}


	private class TabView extends TextView {
		public TabView(Context context) {
			super(context);
			this.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
			ColorStateList cls = getResources().getColorStateList(mTextColor);
			this.setTextColor(cls);
			this.setBackgroundResource(mTabBackground);
			this.setGravity(Gravity.CENTER);
			this.setSingleLine();
		}
	}

}
