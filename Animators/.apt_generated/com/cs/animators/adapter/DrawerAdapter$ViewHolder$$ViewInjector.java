// Generated code from Butter Knife. Do not modify!
package com.cs.animators.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class DrawerAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.adapter.DrawerAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296369, "field 'drawerTitle'");
    target.drawerTitle = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296368, "field 'drawerImage'");
    target.drawerImage = (android.widget.ImageView) view;
  }

  public static void reset(com.cs.animators.adapter.DrawerAdapter.ViewHolder target) {
    target.drawerTitle = null;
    target.drawerImage = null;
  }
}
