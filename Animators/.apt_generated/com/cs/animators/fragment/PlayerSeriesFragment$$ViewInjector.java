// Generated code from Butter Knife. Do not modify!
package com.cs.animators.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class PlayerSeriesFragment$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.fragment.PlayerSeriesFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'mExpandableGridView' and field 'mTxtCancel'");
    target.mExpandableGridView = (com.cs.animators.view.ExpandableGridView) view;
    target.mTxtCancel = (android.widget.TextView) view;
  }

  public static void reset(com.cs.animators.fragment.PlayerSeriesFragment target) {
    target.mExpandableGridView = null;
    target.mTxtCancel = null;
  }
}
