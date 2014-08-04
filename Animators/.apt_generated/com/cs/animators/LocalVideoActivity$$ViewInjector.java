// Generated code from Butter Knife. Do not modify!
package com.cs.animators;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class LocalVideoActivity$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.LocalVideoActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296328, "field 'mGridView' and method 'onItemClick'");
    target.mGridView = (android.widget.GridView) view;
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

  public static void reset(com.cs.animators.LocalVideoActivity target) {
    target.mGridView = null;
  }
}
