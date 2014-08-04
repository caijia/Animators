// Generated code from Butter Knife. Do not modify!
package com.cs.animators.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ThemeAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.adapter.ThemeAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'lastup', field 'pic', and field 'name'");
    target.lastup = (android.widget.TextView) view;
    target.pic = (android.widget.ImageView) view;
    target.name = (android.widget.TextView) view;
  }

  public static void reset(com.cs.animators.adapter.ThemeAdapter.ViewHolder target) {
    target.lastup = null;
    target.pic = null;
    target.name = null;
  }
}
