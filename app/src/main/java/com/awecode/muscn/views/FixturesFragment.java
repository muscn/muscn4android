package com.awecode.muscn.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awecode.muscn.R;
import com.awecode.muscn.adapter.FixturesRecyclerViewAdapter;
import com.awecode.muscn.model.http.fixtures.Result;
import com.awecode.muscn.model.listener.RecyclerViewScrollListener;
import com.awecode.muscn.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by suresh on 10/2/16.
 */

public class FixturesFragment extends MasterFragment {

    @BindView(R.id.matchFixtures)
    RecyclerView matchFixtures;
    private FixturesRecyclerViewAdapter fixturesRecyclerViewAdapter;
    private RecyclerViewScrollListener mRecyclerViewScrollListener;

    public static FixturesFragment newInstance() {
        FixturesFragment fixturesFragment = new FixturesFragment();
        return fixturesFragment;
    }

    public FixturesFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerViewScrollListener = (RecyclerViewScrollListener) this.getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_epl_matchweek_fixture, container, false);
        mActivity.setCustomTitle(R.string.manutd_fixtures);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupFixturesRecyclerview();

    }

    public void setupFixturesRecyclerview() {
        try {
            //delete past fixtures from db
            List<Result> results = deletePastFixtureTable();
            if (results != null
                    && results.size() > 0) {
                matchFixtures.setHasFixedSize(true);
                matchFixtures.setLayoutManager(new LinearLayoutManager(getActivity()));
                fixturesRecyclerViewAdapter = new FixturesRecyclerViewAdapter(getActivity(), results);
                matchFixtures.setAdapter(Util.getAnimationAdapter(fixturesRecyclerViewAdapter));
                mRecyclerViewScrollListener.onRecyclerViewScrolled(matchFixtures);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
