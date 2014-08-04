// Generated code from Butter Knife. Do not modify!
package com.cs.animators.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class FindFragment$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.fragment.FindFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'mGvRecommend', field 'mViewPager', field 'mGvArea', field 'mGvCategory', field 'mCustomScrollView', field 'mIndicator', and field 'mGvTheme'");
    target.mGvRecommend = (android.widget.GridView) view;
    target.mViewPager = (com.cs.cj.view.AutoScrollViewPager) view;
    target.mGvArea = (android.widget.GridView) view;
    target.mGvCategory = (android.widget.GridView) view;
    target.mCustomScrollView = (com.cs.cj.view.CustomScrollView) view;
    target.mIndicator = (com.cs.cj.view.CircleLoopPageIndicator) view;
    target.mGvTheme = (android.widget.GridView) view;
  }

  public static void reset(com.cs.animators.fragment.FindFragment target) {
    target.mGvRecommend = null;
    target.mViewPager = null;
    target.mGvArea = null;
    target.mGvCategory = null;
    target.mCustomScrollView = null;
    target.mIndicator = null;
    target.mGvTheme = null;
  }
}
