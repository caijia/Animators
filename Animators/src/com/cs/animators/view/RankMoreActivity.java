package com.cs.animators.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cs.animationvideo.R;
import com.cs.animators.VideoDetailActivity;
import com.cs.animators.adapter.HotAdapter;
import com.cs.animators.base.BaseActivity;
import com.cs.animators.entity.HotItem;
import com.cs.animators.fragment.HotFragment;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnItemClick;

public class RankMoreActivity extends BaseActivity {

    @InjectView(R.id.rank_more_list_view)
    ListView mListView;

    private List<HotItem> rankItems;


    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_rank_more);
    }

    @Override
    protected void processLogic() {
        HotAdapter hotAdapter = new HotAdapter(this, rankItems);
        mListView.setAdapter(hotAdapter);
    }

    @Override
    protected void getExtra(Bundle bundle) {
        if (bundle != null) {
            rankItems = bundle.getParcelableArrayList("rank_more_item");
        }
    }

    @Override
    protected boolean displayHomeAsUpEnabled() {
        return true;
    }

    @OnItemClick(R.id.rank_more_list_view)
    void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        HotItem video = (HotItem) parent.getAdapter().getItem(position);
        String videoId = video.getVideoId()+"";
        Intent detailIntent = new Intent(this, VideoDetailActivity.class);
        detailIntent.putExtra(HotFragment.VIDEO_ID, videoId);
        detailIntent.putExtra("video_name", video.getName());
        startActivity(detailIntent);
    }
}
