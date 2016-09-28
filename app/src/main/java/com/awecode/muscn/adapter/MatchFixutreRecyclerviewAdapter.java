package com.awecode.muscn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awecode.muscn.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by suresh on 9/25/16.
 */

public class MatchFixutreRecyclerviewAdapter extends RecyclerView.Adapter<MatchFixutreRecyclerviewAdapter.FixtureViewHolder> {
    private Context context;

    public MatchFixutreRecyclerviewAdapter(Context context){
        this.context = context;
    }

    @Override
    public FixtureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_fixture_row_item, parent,false);
        FixtureViewHolder albumViewHolder = new FixtureViewHolder(view);
        return albumViewHolder;
    }

    @Override
    public void onBindViewHolder(FixtureViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    static class FixtureViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fixtureKickoffTime)
        TextView fixtureKickoffTime;
        @BindView(R.id.fixtureHomeTeam)
        TextView fixtureHomeTeam;
        @BindView(R.id.fixtureHomeTeamScore)
        TextView fixtureHomeTeamScore;
        @BindView(R.id.fixtureAwayTeamScore)
        TextView fixtureAwayTeamScore;
        @BindView(R.id.fixtureAwayTeam)
        TextView fixtureAwayTeam;
        @BindView(R.id.matchFixtureRowItem)
        LinearLayout matchFixtureRowItem;

        FixtureViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
