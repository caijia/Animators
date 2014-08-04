// Generated code from Butter Knife. Do not modify!
package com.cs.animators.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class HotAdapter$HViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.adapter.HotAdapter.HViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'score', field 'category', field 'cover', field 'totalNum', and field 'name'");
    target.score = (android.widget.TextView) view;
    target.category = (android.widget.TextView) view;
    target.cover = (android.widget.ImageView) view;
    target.totalNum = (android.widget.TextView) view;
    target.name = (android.widget.TextView) view;
  }

  public static void reset(com.cs.animators.adapter.HotAdapter.HViewHolder target) {
    target.score = null;
    target.category = null;
    target.cover = null;
    target.totalNum = null;
    target.name = null;
  }
}
