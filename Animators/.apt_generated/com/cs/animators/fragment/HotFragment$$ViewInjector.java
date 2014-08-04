// Generated code from Butter Knife. Do not modify!
package com.cs.animators.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class HotFragment$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.fragment.HotFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'mXListView'");
    target.mXListView = (com.markmao.pulltorefresh.widget.XListView) view;
  }

  public static void reset(com.cs.animators.fragment.HotFragment target) {
    target.mXListView = null;
  }
}
