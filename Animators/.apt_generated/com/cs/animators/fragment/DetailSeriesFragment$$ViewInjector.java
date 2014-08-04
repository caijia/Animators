// Generated code from Butter Knife. Do not modify!
package com.cs.animators.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class DetailSeriesFragment$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.fragment.DetailSeriesFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'mSlideTab', field 'mDividerLine', and field 'mPager'");
    target.mSlideTab = (com.cs.cj.view.PagerSlidingTabStrip) view;
    target.mDividerLine = view;
    target.mPager = (android.support.v4.view.ViewPager) view;
  }

  public static void reset(com.cs.animators.fragment.DetailSeriesFragment target) {
    target.mSlideTab = null;
    target.mDividerLine = null;
    target.mPager = null;
  }
}
