// Generated code from Butter Knife. Do not modify!
package com.cs.animators;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class VideoDetailActivity$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.VideoDetailActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'mBtnOutline', field 'mTxtYear', field 'mBtnPlay', field 'mImgCover', field 'mImgBg', field 'mTxtCategory', and field 'mTxtName'");
    target.mBtnOutline = (android.widget.Button) view;
    target.mTxtYear = (android.widget.TextView) view;
    target.mBtnPlay = (android.widget.Button) view;
    target.mImgCover = (android.widget.ImageView) view;
    target.mImgBg = (android.widget.ImageView) view;
    target.mTxtCategory = (android.widget.TextView) view;
    target.mTxtName = (android.widget.TextView) view;
  }

  public static void reset(com.cs.animators.VideoDetailActivity target) {
    target.mBtnOutline = null;
    target.mTxtYear = null;
    target.mBtnPlay = null;
    target.mImgCover = null;
    target.mImgBg = null;
    target.mTxtCategory = null;
    target.mTxtName = null;
  }
}
