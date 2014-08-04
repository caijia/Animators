// Generated code from Butter Knife. Do not modify!
package com.cs.animators.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class NewUpdateAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.adapter.NewUpdateAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296377, "field 'category'");
    target.category = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296376, "field 'updateText'");
    target.updateText = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296378, "field 'score'");
    target.score = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296380, "field 'updateSeries'");
    target.updateSeries = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296375, "field 'name'");
    target.name = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296374, "field 'cover'");
    target.cover = (android.widget.ImageView) view;
  }

  public static void reset(com.cs.animators.adapter.NewUpdateAdapter.ViewHolder target) {
    target.category = null;
    target.updateText = null;
    target.score = null;
    target.updateSeries = null;
    target.name = null;
    target.cover = null;
  }
}
