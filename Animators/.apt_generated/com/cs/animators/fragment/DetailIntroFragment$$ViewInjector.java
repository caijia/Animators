// Generated code from Butter Knife. Do not modify!
package com.cs.animators.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class DetailIntroFragment$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.fragment.DetailIntroFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'mTxtCharactor', field 'mTxtCategory', field 'mTxtDerector', and field 'mTxtIntro'");
    target.mTxtCharactor = (android.widget.TextView) view;
    target.mTxtCategory = (android.widget.TextView) view;
    target.mTxtDerector = (android.widget.TextView) view;
    target.mTxtIntro = (android.widget.TextView) view;
  }

  public static void reset(com.cs.animators.fragment.DetailIntroFragment target) {
    target.mTxtCharactor = null;
    target.mTxtCategory = null;
    target.mTxtDerector = null;
    target.mTxtIntro = null;
  }
}
