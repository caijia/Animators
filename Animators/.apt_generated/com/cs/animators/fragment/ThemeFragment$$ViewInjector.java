// Generated code from Butter Knife. Do not modify!
package com.cs.animators.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ThemeFragment$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.fragment.ThemeFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296431, "field 'mListView' and method 'onItemClickListener'");
    target.mListView = (android.widget.ListView) view;
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.onItemClickListener(p0, p1, p2, p3);
        }
      });
  }

  public static void reset(com.cs.animators.fragment.ThemeFragment target) {
    target.mListView = null;
  }
}
