// Generated code from Butter Knife. Do not modify!
package com.cs.animators.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class PlayerSettingFragment$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.fragment.PlayerSettingFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'mRgroupCanvas', field 'mSkbBrightness', and field 'mTxtClose'");
    target.mRgroupCanvas = (android.widget.RadioGroup) view;
    target.mSkbBrightness = (android.widget.SeekBar) view;
    target.mTxtClose = (android.widget.TextView) view;
  }

  public static void reset(com.cs.animators.fragment.PlayerSettingFragment target) {
    target.mRgroupCanvas = null;
    target.mSkbBrightness = null;
    target.mTxtClose = null;
  }
}
