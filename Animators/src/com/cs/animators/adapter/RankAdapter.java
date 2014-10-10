package com.cs.animators.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cs.animationvideo.R;
import com.cs.animators.VideoDetailActivity;
import com.cs.animators.entity.HotItem;
import com.cs.animators.entity.Rank;
import com.cs.animators.fragment.HotFragment;
import com.cs.animators.util.CommonUtil;
import com.cs.animators.view.RankItemView;
import com.cs.animators.view.RankMoreActivity;
import com.cs.cj.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * TODO:
 * Created by cai.jia on 2014/10/7.
 */
public class RankAdapter extends ArrayAdapter<Rank> {


    private float mImageScale = 315 / 210f;

    public RankAdapter(Context context,List<Rank> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder rankViewHolder ;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_rank_item, parent, false);
            rankViewHolder = new ViewHolder(convertView);
            convertView.setTag(rankViewHolder);
        } else {
            rankViewHolder = (ViewHolder) convertView.getTag();
        }

        Rank item = getItem(position);
        if (item != null) {
            rankViewHolder.mText.setText(item.getTitle());
            int size = item.getList().size();
            if (size > 0) {
                HotItem rankItem = item.getList().get(0);
                String cover = rankItem.getCover();
                ImageLoader.getInstance().displayImage(cover,rankViewHolder.mImage, ImageLoaderUtil.FadeInImageLoaderOptions(0));
            }

            RankItemView[] rankItemViews = new RankItemView[]{rankViewHolder.itemView1, rankViewHolder.itemView2, rankViewHolder.itemView3, rankViewHolder.itemView4, rankViewHolder.itemView5};
            final List<HotItem> rankItems = item.getList();

            if (rankItems.size() >= 5) {
                for (int i = 0; i < rankItemViews.length; i++) {

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rankItemViews[i].getImageView().getLayoutParams();
                    if( i == 0){
                        params.width = (int) ((CommonUtil.getWidthMetrics(getContext()) - CommonUtil.dip2px(getContext(),22))*0.666 - CommonUtil.dip2px(getContext(),5)) ;
                        params.height = (int) ((params.width - CommonUtil.dip2px(getContext(),5))* mImageScale / 2f);
                    }else{
                        params.width = (int) ((CommonUtil.getWidthMetrics(getContext()) - CommonUtil.dip2px(getContext(),22))*0.333- CommonUtil.dip2px(getContext(),5)) ;
                        params.height = (int) (params.width * mImageScale);
                    }
                    rankItemViews[i].getImageView().setLayoutParams(params);
                    rankItemViews[i].getImageView().setScaleType(ImageView.ScaleType.CENTER_CROP);

                    HotItem rankItem = rankItems.get(i);

                    String htmlCurSeries = "更新至<font color=\"#168FFA\">"+ rankItem.getCurNum()+ "</font>集";
                    rankItemViews[i].setSeries(Html.fromHtml(htmlCurSeries));
                    rankItemViews[i].setName(rankItem.getName());
                    rankItemViews[i].setCover(rankItem.getCover());
                }


                rankViewHolder.mMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent rankMoreIntent = new Intent(getContext(), RankMoreActivity.class);
                        rankMoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        List<HotItem> rankMoreItems = new ArrayList<HotItem>();
                        rankMoreItems.addAll(rankItems.subList(5, rankItems.size()));
                        rankMoreIntent.putParcelableArrayListExtra("rank_more_item", (ArrayList<? extends android.os.Parcelable>) rankMoreItems);
                        getContext().startActivity(rankMoreIntent);
                    }
                });

            }

            for (int i = 0; i < rankItemViews.length; i++) {
                final int finalI = i;
                rankItemViews[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HotItem rankItem = getItem(position).getList().get(finalI);
                        Intent detailIntent = new Intent(getContext(), VideoDetailActivity.class);
                        detailIntent.putExtra(HotFragment.VIDEO_ID, rankItem.getVideoId());
                        detailIntent.putExtra("video_name", rankItem.getName());
                        detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(detailIntent);
                    }
                });
            }
        }

        return convertView;
    }

    static class ViewHolder {

        @InjectView(R.id.rank_title_image_view)
        ImageView mImage ;

        @InjectView(R.id.rank_title_text)
        TextView mText ;

        @InjectView(R.id.rank_title_more)
        TextView mMore ;

        @InjectView(R.id.rankItem1)
        RankItemView itemView1 ;

        @InjectView(R.id.rankItem2)
        RankItemView itemView2 ;

        @InjectView(R.id.rankItem3)
        RankItemView itemView3 ;

        @InjectView(R.id.rankItem4)
        RankItemView itemView4 ;

        @InjectView(R.id.rankItem5)
        RankItemView itemView5 ;

        public ViewHolder(View view) {
            ButterKnife.inject(this,view);
        }

    }

}
