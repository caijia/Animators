// Generated code from Butter Knife. Do not modify!
package com.cs.animators.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class HotAdapter$HViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.adapter.HotAdapter.HViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'cover', field 'score', field 'name', field 'category', and field 'totalNum'");
    target.cover = (android.widget.ImageView) view;
    target.score = (android.widget.TextView) view;
    target.name = (android.widget.TextView) view;
    target.category = (android.widget.TextView) view;
    target.totalNum = (android.widget.TextView) view;
  }

  public static void reset(com.cs.animators.adapter.HotAdapter.HViewHolder target) {
    target.cover = null;
    target.score = null;
    target.name = null;
    target.category = null;
    target.totalNum = null;
  }
}
