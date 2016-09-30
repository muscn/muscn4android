package com.awecode.muscn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.eplmatchweek._20161001;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by suresh on 9/25/16.
 */

public class MatchFixutreRecyclerviewAdapter extends RecyclerView.Adapter<MatchFixutreRecyclerviewAdapter.FixtureViewHolder> {
    private Context context;
    private ArrayList<_20161001> mCategoryList;

    public MatchFixutreRecyclerviewAdapter(Context context, ArrayList<_20161001> mCategoryList){
        this.context = context;
        this.mCategoryList = mCategoryList;
    }

    @Override
    public FixtureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_fixture_row_item, parent,false);
        FixtureViewHolder albumViewHolder = new FixtureViewHolder(view);
        return albumViewHolder;
    }

    @Override
    public void onBindViewHolder(FixtureViewHolder holder, int position) {
        _20161001 data = mCategoryList.get(position);

        /**
         * fetch kickoff date and format it into hours and minutes
         */
        String strCurrentDate = data.getKickoff();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat("hh:mm");
        String time = format.format(newDate);

        /**
         * populate data in viewholder
         */
       if (data.getScore()==null){
           holder.matchScore.setText("?-?");
       }else
           holder.matchScore.setText(data.getScore());
        holder.fixtureHomeTeam.setText(data.getHomeTeam());
        holder.fixtureAwayTeam.setText(data.getAwayTeam());
        holder.fixtureKickoffTime.setText(time);


    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    /**
     * ViewHolder class for fixture
     */

    static class FixtureViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fixtureKickoffTime)
        TextView fixtureKickoffTime;
        @BindView(R.id.fixtureHomeTeam)
        TextView fixtureHomeTeam;
        @BindView(R.id.matchScore)
        TextView matchScore;
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
