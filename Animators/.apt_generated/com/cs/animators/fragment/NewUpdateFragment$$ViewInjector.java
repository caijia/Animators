// Generated code from Butter Knife. Do not modify!
package com.cs.animators.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class NewUpdateFragment$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.fragment.NewUpdateFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'mListView'");
    target.mListView = (se.emilsjolander.stickylistheaders.StickyListHeadersListView) view;
  }

  public static void reset(com.cs.animators.fragment.NewUpdateFragment target) {
    target.mListView = null;
  }
}
