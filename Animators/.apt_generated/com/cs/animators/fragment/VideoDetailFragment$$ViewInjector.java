// Generated code from Butter Knife. Do not modify!
package com.cs.animators.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class VideoDetailFragment$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.fragment.VideoDetailFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296437, "field 'mSlideTab'");
    target.mSlideTab = (com.cs.cj.view.PagerSlidingTabStrip) view;
    view = finder.findRequiredView(source, 2131296438, "field 'mPager'");
    target.mPager = (android.support.v4.view.ViewPager) view;
  }

  public static void reset(com.cs.animators.fragment.VideoDetailFragment target) {
    target.mSlideTab = null;
    target.mPager = null;
  }
}
