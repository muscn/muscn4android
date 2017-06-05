package com.awecode.muscn.views.fixture;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.awecode.muscn.R;
import com.awecode.muscn.adapter.FixturesListAdapter;
import com.awecode.muscn.model.http.fixtures.Result;
import com.awecode.muscn.model.listener.RecyclerViewScrollListener;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.views.MasterFragment;

import java.util.List;

import butterknife.BindView;

/**
 * Created by suresh on 10/2/16.
 */

public class FixturesFragment extends MasterFragment {

    @BindView(R.id.matchFixtures)
    RecyclerView matchFixtures;
    private FixturesListAdapter fixturesListAdapter;
    private RecyclerViewScrollListener mRecyclerViewScrollListener;

    public static FixturesFragment newInstance() {
        FixturesFragment fixturesFragment = new FixturesFragment();
        return fixturesFragment;
    }

    public FixturesFragment() {

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_epl_matchweek_fixture;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerViewScrollListener = (RecyclerViewScrollListener) this.getContext();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.setCustomTitle(R.string.manutd_fixtures);
        initializeRecyclerview();
        populateFixtureList();

    }

    private void initializeRecyclerview() {
        matchFixtures.setHasFixedSize(true);
        matchFixtures.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     * populate saved fixture db in listview
     */
    public void populateFixtureList() {
        try {
            //delete past fixtures from db
            List<Result> results = deletePastFixtureTable();
            if (results != null
                    && results.size() > 0) {
                fixturesListAdapter = new FixturesListAdapter(mRealm.where(Result.class).findAll());
                matchFixtures.setAdapter(Util.getAnimationAdapter(fixturesListAdapter));
                mRecyclerViewScrollListener.onRecyclerViewScrolled(matchFixtures);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
