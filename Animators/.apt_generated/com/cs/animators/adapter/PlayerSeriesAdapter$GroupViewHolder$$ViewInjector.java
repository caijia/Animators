// Generated code from Butter Knife. Do not modify!
package com.cs.animators.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class PlayerSeriesAdapter$GroupViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.adapter.PlayerSeriesAdapter.GroupViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'groupTitle' and field 'expandablePic'");
    target.groupTitle = (android.widget.TextView) view;
    target.expandablePic = (android.widget.ImageView) view;
  }

  public static void reset(com.cs.animators.adapter.PlayerSeriesAdapter.GroupViewHolder target) {
    target.groupTitle = null;
    target.expandablePic = null;
  }
}
