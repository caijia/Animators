package com.cs.animators.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cs.animationvideo.R;
import com.cs.cj.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * TODO:
 * Created by cai.jia on 2014/10/8.
 */
public class RankItemView extends RelativeLayout {

    private View rootView ;

    private ImageView cover ;

    private TextView series ;

    private TextView name ;

    public RankItemView(Context context, AttributeSet attrs) {
        this(context, attrs ,0);
    }

    public RankItemView(Context context) {
        this(context, null);
    }

    public RankItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        rootView = LayoutInflater.from(context).inflate(R.layout.adapter_rank_grid_item, this, false);

        cover = (ImageView) rootView.findViewById(R.id.rank_animation_cover);
        series = (TextView) rootView.findViewById(R.id.rank_animation_update_series);
        name = (TextView) rootView.findViewById(R.id.rank_animation_name);

//        LayoutParams params = (LayoutParams) cover.getLayoutParams();


        this.addView(rootView);

    }

    public void setSeries(CharSequence updateSeries) {
        series.setText(updateSeries);
    }

    public void setName(String name1) {
        name.setText(name1);
    }

    public void setCover(String url) {
        ImageLoader.getInstance().displayImage(url, cover, ImageLoaderUtil.FadeInImageLoaderOptions(0));
    }

    public ImageView getImageView() {
        return cover;
    }

}
