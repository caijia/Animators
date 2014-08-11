// Generated code from Butter Knife. Do not modify!
package com.cs.animators.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class HotAdapter$HViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.adapter.HotAdapter.HViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296381, "field 'totalNum'");
    target.totalNum = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296379, "field 'score'");
    target.score = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296378, "field 'category'");
    target.category = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296375, "field 'cover'");
    target.cover = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131296376, "field 'name'");
    target.name = (android.widget.TextView) view;
  }

  public static void reset(com.cs.animators.adapter.HotAdapter.HViewHolder target) {
    target.totalNum = null;
    target.score = null;
    target.category = null;
    target.cover = null;
    target.name = null;
  }
}
