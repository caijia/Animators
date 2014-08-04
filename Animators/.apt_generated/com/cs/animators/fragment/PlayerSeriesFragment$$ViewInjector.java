// Generated code from Butter Knife. Do not modify!
package com.cs.animators.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class PlayerSeriesFragment$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.fragment.PlayerSeriesFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296427, "field 'mTxtCancel' and method 'closeDialog'");
    target.mTxtCancel = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.closeDialog();
        }
      });
    view = finder.findRequiredView(source, 2131296428, "field 'mExpandableGridView'");
    target.mExpandableGridView = (com.cs.animators.view.ExpandableGridView) view;
  }

  public static void reset(com.cs.animators.fragment.PlayerSeriesFragment target) {
    target.mTxtCancel = null;
    target.mExpandableGridView = null;
  }
}
