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
import com.awecode.muscn.model.http.fixtures.FixturesResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by suresh on 10/2/16.
 */

public class FixturesFragment extends MasterFragment {

    @BindView(R.id.matchFixtures)
    RecyclerView matchFixtures;
    private FixturesResponse fixturesResponse;
    private FixturesRecyclerViewAdapter fixturesRecyclerViewAdapter;

    public static FixturesFragment newInstance(FixturesResponse fixturesResponse) {
        FixturesFragment fixturesFragment = new FixturesFragment();
        fixturesFragment.fixturesResponse = fixturesResponse;
        return fixturesFragment;
    }

    public FixturesFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        matchFixtures.setHasFixedSize(true);
        matchFixtures.setLayoutManager(new LinearLayoutManager(getActivity()));
        fixturesRecyclerViewAdapter = new FixturesRecyclerViewAdapter(getActivity(), fixturesResponse);
        matchFixtures.setAdapter(fixturesRecyclerViewAdapter);

    }

}
