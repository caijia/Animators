// Generated code from Butter Knife. Do not modify!
package com.cs.animators.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class LocalVideoAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.adapter.LocalVideoAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296383, "field 'duration'");
    target.duration = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296382, "field 'thumb'");
    target.thumb = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131296385, "field 'size'");
    target.size = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296384, "field 'name'");
    target.name = (android.widget.TextView) view;
  }

  public static void reset(com.cs.animators.adapter.LocalVideoAdapter.ViewHolder target) {
    target.duration = null;
    target.thumb = null;
    target.size = null;
    target.name = null;
  }
}
