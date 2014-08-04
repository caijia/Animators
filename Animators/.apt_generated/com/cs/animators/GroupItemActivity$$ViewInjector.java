// Generated code from Butter Knife. Do not modify!
package com.cs.animators;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class GroupItemActivity$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.GroupItemActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296329, "field 'mGridView' and method 'themeItemClick'");
    target.mGridView = (android.widget.GridView) view;
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
  }

  public static void reset(com.cs.animators.GroupItemActivity target) {
    target.mGridView = null;
  }
}
