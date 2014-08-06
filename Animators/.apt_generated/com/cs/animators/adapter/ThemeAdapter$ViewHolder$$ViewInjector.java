// Generated code from Butter Knife. Do not modify!
package com.cs.animators.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ThemeAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.adapter.ThemeAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296389, "field 'name'");
    target.name = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296398, "field 'lastup'");
    target.lastup = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296397, "field 'pic'");
    target.pic = (android.widget.ImageView) view;
  }

  public static void reset(com.cs.animators.adapter.ThemeAdapter.ViewHolder target) {
    target.name = null;
    target.lastup = null;
    target.pic = null;
  }
}
