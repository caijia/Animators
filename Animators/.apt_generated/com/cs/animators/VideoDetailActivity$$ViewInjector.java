// Generated code from Butter Knife. Do not modify!
package com.cs.animators;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class VideoDetailActivity$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.VideoDetailActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 0, "field 'mTxtCategory', field 'mBtnOutline', field 'mTxtName', field 'mTxtYear', field 'mImgCover', field 'mImgBg', and field 'mBtnPlay'");
    target.mTxtCategory = (android.widget.TextView) view;
    target.mBtnOutline = (android.widget.Button) view;
    target.mTxtName = (android.widget.TextView) view;
    target.mTxtYear = (android.widget.TextView) view;
    target.mImgCover = (android.widget.ImageView) view;
    target.mImgBg = (android.widget.ImageView) view;
    target.mBtnPlay = (android.widget.Button) view;
  }

  public static void reset(com.cs.animators.VideoDetailActivity target) {
    target.mTxtCategory = null;
    target.mBtnOutline = null;
    target.mTxtName = null;
    target.mTxtYear = null;
    target.mImgCover = null;
    target.mImgBg = null;
    target.mBtnPlay = null;
  }
}
