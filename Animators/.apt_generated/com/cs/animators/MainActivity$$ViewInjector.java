// Generated code from Butter Knife. Do not modify!
package com.cs.animators;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainActivity$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.MainActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'mListView' and field 'mDrawerLayout'");
    target.mListView = (android.widget.ListView) view;
    target.mDrawerLayout = (android.support.v4.widget.DrawerLayout) view;
  }

  public static void reset(com.cs.animators.MainActivity target) {
    target.mListView = null;
    target.mDrawerLayout = null;
  }
}
