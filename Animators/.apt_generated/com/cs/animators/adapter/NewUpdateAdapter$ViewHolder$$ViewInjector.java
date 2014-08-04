// Generated code from Butter Knife. Do not modify!
package com.cs.animators.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class NewUpdateAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.adapter.NewUpdateAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'updateSeries', field 'category', field 'cover', field 'score', field 'updateText', and field 'name'");
    target.updateSeries = (android.widget.TextView) view;
    target.category = (android.widget.TextView) view;
    target.cover = (android.widget.ImageView) view;
    target.score = (android.widget.TextView) view;
    target.updateText = (android.widget.TextView) view;
    target.name = (android.widget.TextView) view;
  }

  public static void reset(com.cs.animators.adapter.NewUpdateAdapter.ViewHolder target) {
    target.updateSeries = null;
    target.category = null;
    target.cover = null;
    target.score = null;
    target.updateText = null;
    target.name = null;
  }
}
