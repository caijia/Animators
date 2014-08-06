// Generated code from Butter Knife. Do not modify!
package com.cs.animators.base;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class BaseActivity$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.base.BaseActivity.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296325, "field 'mainContent'");
    target.mainContent = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2131296326, "field 'mViewStubProgress'");
    target.mViewStubProgress = (android.view.ViewStub) view;
    view = finder.findRequiredView(source, 2131296327, "field 'mViewStubError'");
    target.mViewStubError = (android.view.ViewStub) view;
  }

  public static void reset(com.cs.animators.base.BaseActivity.ViewHolder target) {
    target.mainContent = null;
    target.mViewStubProgress = null;
    target.mViewStubError = null;
  }
}
