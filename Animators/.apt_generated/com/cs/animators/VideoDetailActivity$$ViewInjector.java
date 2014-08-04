// Generated code from Butter Knife. Do not modify!
package com.cs.animators;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class VideoDetailActivity$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.VideoDetailActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296362, "field 'mBtnPlay'");
    target.mBtnPlay = (android.widget.Button) view;
    view = finder.findRequiredView(source, 2131296357, "field 'mImgCover'");
    target.mImgCover = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131296359, "field 'mTxtCategory'");
    target.mTxtCategory = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296358, "field 'mTxtName'");
    target.mTxtName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296356, "field 'mImgBg'");
    target.mImgBg = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131296360, "field 'mTxtYear'");
    target.mTxtYear = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296364, "field 'mTxtPlayRecord'");
    target.mTxtPlayRecord = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296363, "field 'mBtnOutline'");
    target.mBtnOutline = (android.widget.Button) view;
  }

  public static void reset(com.cs.animators.VideoDetailActivity target) {
    target.mBtnPlay = null;
    target.mImgCover = null;
    target.mTxtCategory = null;
    target.mTxtName = null;
    target.mImgBg = null;
    target.mTxtYear = null;
    target.mTxtPlayRecord = null;
    target.mBtnOutline = null;
  }
}
