// Generated code from Butter Knife. Do not modify!
package com.cs.animators.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class DetailSeriesFragment$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.fragment.DetailSeriesFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'mPager', field 'mDividerLine', and field 'mSlideTab'");
    target.mPager = (android.support.v4.view.ViewPager) view;
    target.mDividerLine = view;
    target.mSlideTab = (com.cs.cj.view.PagerSlidingTabStrip) view;
  }

  public static void reset(com.cs.animators.fragment.DetailSeriesFragment target) {
    target.mPager = null;
    target.mDividerLine = null;
    target.mSlideTab = null;
  }
}
