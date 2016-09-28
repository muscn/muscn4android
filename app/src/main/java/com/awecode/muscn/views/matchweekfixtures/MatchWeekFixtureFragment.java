package com.awecode.muscn.views.matchweekfixtures;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awecode.muscn.R;
import com.awecode.muscn.adapter.MatchFixutreRecyclerviewAdapter;
import com.awecode.muscn.views.MasterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by suresh on 9/28/16.
 */

public class MatchWeekFixtureFragment extends MasterFragment {

    @BindView(R.id.matchFixtures)
    RecyclerView matchFixtures;

    private MatchFixutreRecyclerviewAdapter mMatchFixutreRecyclerviewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fixture, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        showProgressView(getString(R.string.loading_fixtures));
        setupMatchFixtureRecylerView();
    }

    /**
     * Setup match fixture recyclerview to show the matchweek mixtures
     */
    public void setupMatchFixtureRecylerView(){
        matchFixtures.setHasFixedSize(true);
        matchFixtures.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMatchFixutreRecyclerviewAdapter = new MatchFixutreRecyclerviewAdapter(getActivity());
        matchFixtures.setAdapter(mMatchFixutreRecyclerviewAdapter);

    }
}
