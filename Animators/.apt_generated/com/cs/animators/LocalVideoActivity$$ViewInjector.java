// Generated code from Butter Knife. Do not modify!
package com.cs.animators;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class LocalVideoActivity$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.LocalVideoActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'mGridView'");
    target.mGridView = (android.widget.GridView) view;
  }

  public static void reset(com.cs.animators.LocalVideoActivity target) {
    target.mGridView = null;
  }
}