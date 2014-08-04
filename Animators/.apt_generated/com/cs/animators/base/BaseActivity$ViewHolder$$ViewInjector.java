// Generated code from Butter Knife. Do not modify!
package com.cs.animators.base;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class BaseActivity$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.base.BaseActivity.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'mViewStubProgress', field 'mainContent', and field 'mViewStubError'");
    target.mViewStubProgress = (android.view.ViewStub) view;
    target.mainContent = (android.widget.LinearLayout) view;
    target.mViewStubError = (android.view.ViewStub) view;
  }

  public static void reset(com.cs.animators.base.BaseActivity.ViewHolder target) {
    target.mViewStubProgress = null;
    target.mainContent = null;
    target.mViewStubError = null;
  }
}
