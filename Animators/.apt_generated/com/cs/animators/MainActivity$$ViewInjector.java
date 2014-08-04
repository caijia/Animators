// Generated code from Butter Knife. Do not modify!
package com.cs.animators;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainActivity$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.MainActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'mDrawerLayout' and field 'mListView'");
    target.mDrawerLayout = (android.support.v4.widget.DrawerLayout) view;
    target.mListView = (android.widget.ListView) view;
  }

  public static void reset(com.cs.animators.MainActivity target) {
    target.mDrawerLayout = null;
    target.mListView = null;
  }
}
