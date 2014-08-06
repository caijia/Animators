// Generated code from Butter Knife. Do not modify!
package com.cs.animators.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class DetailIntroFragment$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.fragment.DetailIntroFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296408, "field 'mTxtDerector'");
    target.mTxtDerector = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296410, "field 'mTxtCharactor'");
    target.mTxtCharactor = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296409, "field 'mTxtCategory'");
    target.mTxtCategory = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296407, "field 'mTxtIntro'");
    target.mTxtIntro = (android.widget.TextView) view;
  }

  public static void reset(com.cs.animators.fragment.DetailIntroFragment target) {
    target.mTxtDerector = null;
    target.mTxtCharactor = null;
    target.mTxtCategory = null;
    target.mTxtIntro = null;
  }
}
