// Generated code from Butter Knife. Do not modify!
package com.cs.animators.base;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class BaseFragment$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.base.BaseFragment.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'mViewStubProgress', field 'mViewStubError', and field 'parentContent'");
    target.mViewStubProgress = (android.view.ViewStub) view;
    target.mViewStubError = (android.view.ViewStub) view;
    target.parentContent = (android.widget.LinearLayout) view;
  }

  public static void reset(com.cs.animators.base.BaseFragment.ViewHolder target) {
    target.mViewStubProgress = null;
    target.mViewStubError = null;
    target.parentContent = null;
  }
}
