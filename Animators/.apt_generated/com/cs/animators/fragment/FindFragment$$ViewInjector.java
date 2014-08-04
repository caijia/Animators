// Generated code from Butter Knife. Do not modify!
package com.cs.animators.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class FindFragment$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.fragment.FindFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'mGvRecommend', field 'mGvTheme', field 'mIndicator', field 'mViewPager', field 'mGvCategory', field 'mGvArea', and field 'mCustomScrollView'");
    target.mGvRecommend = (android.widget.GridView) view;
    target.mGvTheme = (android.widget.GridView) view;
    target.mIndicator = (com.cs.cj.view.CircleLoopPageIndicator) view;
    target.mViewPager = (com.cs.cj.view.AutoScrollViewPager) view;
    target.mGvCategory = (android.widget.GridView) view;
    target.mGvArea = (android.widget.GridView) view;
    target.mCustomScrollView = (com.cs.cj.view.CustomScrollView) view;
  }

  public static void reset(com.cs.animators.fragment.FindFragment target) {
    target.mGvRecommend = null;
    target.mGvTheme = null;
    target.mIndicator = null;
    target.mViewPager = null;
    target.mGvCategory = null;
    target.mGvArea = null;
    target.mCustomScrollView = null;
  }
}
