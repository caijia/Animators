// Generated code from Butter Knife. Do not modify!
package com.cs.animators.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class PlayerSettingFragment$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.fragment.PlayerSettingFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296433, "field 'mRgroupCanvas'");
    target.mRgroupCanvas = (android.widget.RadioGroup) view;
    view = finder.findRequiredView(source, 2131296429, "field 'mTxtClose' and method 'closeDialog'");
    target.mTxtClose = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.closeDialog();
        }
      });
    view = finder.findRequiredView(source, 2131296431, "field 'mSkbBrightness'");
    target.mSkbBrightness = (android.widget.SeekBar) view;
  }

  public static void reset(com.cs.animators.fragment.PlayerSettingFragment target) {
    target.mRgroupCanvas = null;
    target.mTxtClose = null;
    target.mSkbBrightness = null;
  }
}
