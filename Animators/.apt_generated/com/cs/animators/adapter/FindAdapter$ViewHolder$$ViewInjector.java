// Generated code from Butter Knife. Do not modify!
package com.cs.animators.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class FindAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.adapter.FindAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296373, "field 'name'");
    target.name = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296372, "field 'pic'");
    target.pic = (android.widget.ImageView) view;
  }

  public static void reset(com.cs.animators.adapter.FindAdapter.ViewHolder target) {
    target.name = null;
    target.pic = null;
  }
}
