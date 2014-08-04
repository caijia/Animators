// Generated code from Butter Knife. Do not modify!
package com.cs.animators.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class DetailIntroFragment$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.fragment.DetailIntroFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'mTxtIntro', field 'mTxtCharactor', field 'mTxtCategory', and field 'mTxtDerector'");
    target.mTxtIntro = (android.widget.TextView) view;
    target.mTxtCharactor = (android.widget.TextView) view;
    target.mTxtCategory = (android.widget.TextView) view;
    target.mTxtDerector = (android.widget.TextView) view;
  }

  public static void reset(com.cs.animators.fragment.DetailIntroFragment target) {
    target.mTxtIntro = null;
    target.mTxtCharactor = null;
    target.mTxtCategory = null;
    target.mTxtDerector = null;
  }
}
