// Generated code from Butter Knife. Do not modify!
package com.cs.animators.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class LocalVideoAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.adapter.LocalVideoAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'name', field 'duration', and field 'thumb'");
    target.name = (android.widget.TextView) view;
    target.duration = (android.widget.TextView) view;
    target.thumb = (android.widget.ImageView) view;
  }

  public static void reset(com.cs.animators.adapter.LocalVideoAdapter.ViewHolder target) {
    target.name = null;
    target.duration = null;
    target.thumb = null;
  }
}