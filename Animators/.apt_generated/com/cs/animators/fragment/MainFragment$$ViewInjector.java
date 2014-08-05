// Generated code from Butter Knife. Do not modify!
package com.cs.animators.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainFragment$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.fragment.MainFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296423, "field 'mPagerSlideTab'");
    target.mPagerSlideTab = (com.cs.cj.view.PagerSlidingTabStrip) view;
    view = finder.findRequiredView(source, 2131296424, "field 'mViewPager'");
    target.mViewPager = (android.support.v4.view.ViewPager) view;
  }

  public static void reset(com.cs.animators.fragment.MainFragment target) {
    target.mPagerSlideTab = null;
    target.mViewPager = null;
  }
}
