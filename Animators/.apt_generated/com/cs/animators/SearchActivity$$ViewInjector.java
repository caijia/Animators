// Generated code from Butter Knife. Do not modify!
package com.cs.animators;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SearchActivity$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.SearchActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'mListView'");
    target.mListView = (android.widget.ListView) view;
  }

  public static void reset(com.cs.animators.SearchActivity target) {
    target.mListView = null;
  }
}
