// Generated code from Butter Knife. Do not modify!
package com.cs.animators;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class AllCategoryActivity$$ViewInjector {
  public static void inject(Finder finder, final com.cs.animators.AllCategoryActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296324, "field 'mXListView', method 'onItemClickListener', and method 'onItemLongClickListener'");
    target.mXListView = (com.markmao.pulltorefresh.widget.XListView) view;
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.onItemClickListener(p0, p1, p2, p3);
        }
      });
    ((android.widget.AdapterView<?>) view).setOnItemLongClickListener(
      new android.widget.AdapterView.OnItemLongClickListener() {
        @Override public boolean onItemLongClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          return target.onItemLongClickListener(p0, p1, p2, p3);
        }
      });
  }

  public static void reset(com.cs.animators.AllCategoryActivity target) {
    target.mXListView = null;
  }
}
