// Generated code from Butter Knife. Do not modify!
package com.cs.animators.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class DrawerAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.adapter.DrawerAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'drawerImage' and field 'drawerTitle'");
    target.drawerImage = (android.widget.ImageView) view;
    target.drawerTitle = (android.widget.TextView) view;
  }

  public static void reset(com.cs.animators.adapter.DrawerAdapter.ViewHolder target) {
    target.drawerImage = null;
    target.drawerTitle = null;
  }
}