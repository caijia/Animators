package com.cs.animators.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import com.cs.animationvideo.R;
import com.cs.animators.adapter.RankAdapter;
import com.cs.animators.base.BaseFragment;
import com.cs.animators.constants.Constants;
import com.cs.animators.entity.Rank;
import com.cs.cj.http.httplibrary.RequestParams;
import com.cs.cj.http.work.FastJsonParserArray;
import com.cs.cj.http.work.JDataCallback;
import com.cs.cj.http.work.Response;
import java.util.List;
import butterknife.InjectView;

/**
 * TODO:
 * Created by cai.jia on 2014/10/7.
 */
public class RankFragment extends BaseFragment {

    @InjectView(R.id.rank_list_view)
    ListView mListView ;

    private View mHeaderFooterView ;

    @Override
    protected void loadLayout() {
        setContentView(R.layout.fragment_rank);
    }

    @Override
    protected void processLogic() {
        mHeaderFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.rank_list_header_footer_view, null);
        loadData();
    }

    private void loadData() {

        RequestParams params = new RequestParams();
        params.put("m", "Api");
        params.put("a", "rank");
        get(Constants.host, params,new FastJsonParserArray<Rank>(Rank.class),new JDataCallback<List<Rank>>() {
            @Override
            public void onSuccess(Response<List<Rank>> data) {
                RankAdapter adapter = new RankAdapter(getActivity(),data.getResult());
                mListView.addHeaderView(mHeaderFooterView);
                mListView.addFooterView(mHeaderFooterView);
                mListView.setAdapter(adapter);
            }
        });

    }
}
