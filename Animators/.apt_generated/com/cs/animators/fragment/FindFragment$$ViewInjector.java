// Generated code from Butter Knife. Do not modify!
package com.cs.animators.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class FindFragment$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.fragment.FindFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296418, "field 'mGvRecommend' and method 'recommendItemClick'");
    target.mGvRecommend = (android.widget.GridView) view;
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.recommendItemClick(p0, p1, p2, p3);
        }
      });
    view = finder.findRequiredView(source, 2131296415, "field 'mViewPager'");
    target.mViewPager = (com.cs.cj.view.AutoScrollViewPager) view;
    view = finder.findRequiredView(source, 2131296427, "field 'mGvTheme' and method 'themeItemClick'");
    target.mGvTheme = (android.widget.GridView) view;
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.themeItemClick(p0, p1, p2, p3);
        }
      });
    view = finder.findRequiredView(source, 2131296416, "field 'mIndicator'");
    target.mIndicator = (com.cs.cj.view.CircleLoopPageIndicator) view;
    view = finder.findRequiredView(source, 2131296414, "field 'mCustomScrollView'");
    target.mCustomScrollView = (com.cs.cj.view.CustomScrollView) view;
    view = finder.findRequiredView(source, 2131296424, "field 'mGvCategory' and method 'categoryItemClick'");
    target.mGvCategory = (android.widget.GridView) view;
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.categoryItemClick(p0, p1, p2, p3);
        }
      });
    view = finder.findRequiredView(source, 2131296421, "field 'mGvArea' and method 'areaItemClick'");
    target.mGvArea = (android.widget.GridView) view;
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.areaItemClick(p0, p1, p2, p3);
        }
      });
    view = finder.findRequiredView(source, 2131296426, "method 'themeMore'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.themeMore();
        }
      });
    view = finder.findRequiredView(source, 2131296423, "method 'categoryMore'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.categoryMore();
        }
      });
    view = finder.findRequiredView(source, 2131296420, "method 'areaMore'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.areaMore();
        }
      });
  }

  public static void reset(com.cs.animators.fragment.FindFragment target) {
    target.mGvRecommend = null;
    target.mViewPager = null;
    target.mGvTheme = null;
    target.mIndicator = null;
    target.mCustomScrollView = null;
    target.mGvCategory = null;
    target.mGvArea = null;
  }
}
