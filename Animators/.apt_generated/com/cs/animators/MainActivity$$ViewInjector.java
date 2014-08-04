// Generated code from Butter Knife. Do not modify!
package com.cs.animators;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainActivity$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.MainActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296330, "field 'mDrawerLayout'");
    target.mDrawerLayout = (android.support.v4.widget.DrawerLayout) view;
    view = finder.findRequiredView(source, 2131296332, "field 'mListView' and method 'onItemClick'");
    target.mListView = (android.widget.ListView) view;
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.onItemClick(p0, p1, p2, p3);
        }
      });
  }

  public static void reset(com.cs.animators.MainActivity target) {
    target.mDrawerLayout = null;
    target.mListView = null;
  }
}
