// Generated code from Butter Knife. Do not modify!
package com.cs.animators;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class AllCategoryActivity$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.AllCategoryActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'mXListView'");
    target.mXListView = (com.markmao.pulltorefresh.widget.XListView) view;
  }

  public static void reset(com.cs.animators.AllCategoryActivity target) {
    target.mXListView = null;
  }
}
