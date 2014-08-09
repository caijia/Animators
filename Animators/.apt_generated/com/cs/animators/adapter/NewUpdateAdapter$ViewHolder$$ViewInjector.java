// Generated code from Butter Knife. Do not modify!
package com.cs.animators.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class NewUpdateAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.adapter.NewUpdateAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296381, "field 'updateSeries'");
    target.updateSeries = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296379, "field 'score'");
    target.score = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296375, "field 'cover'");
    target.cover = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131296376, "field 'name'");
    target.name = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296378, "field 'category'");
    target.category = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296377, "field 'updateText'");
    target.updateText = (android.widget.TextView) view;
  }

  public static void reset(com.cs.animators.adapter.NewUpdateAdapter.ViewHolder target) {
    target.updateSeries = null;
    target.score = null;
    target.cover = null;
    target.name = null;
    target.category = null;
    target.updateText = null;
  }
}
