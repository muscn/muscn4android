package com.awecode.muscn.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.eplmatchweek.EplMatchweekResponse;
import com.awecode.muscn.model.listener.MatchweekItemClickListener;
import com.awecode.muscn.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by suresh on 9/25/16.
 */

public class MatchWeekAdapter extends RealmRecyclerViewAdapter<EplMatchweekResponse, MatchWeekAdapter.FixtureViewHolder> {


    public MatchweekItemClickListener mMatchweekItemClickListener;

    public MatchWeekAdapter(@Nullable OrderedRealmCollection<EplMatchweekResponse> data) {
        super(data, true);
    }

    @Override
    public FixtureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.epl_matchweek_row_iten_new, parent, false);
        FixtureViewHolder albumViewHolder = new FixtureViewHolder(view);
        return albumViewHolder;
    }

    @Override
    public void onBindViewHolder(FixtureViewHolder holder, int position) {
        final EplMatchweekResponse data = getItem(position);

//        /**
//         * fetch kickoff date and format it into hours and minutes
//         */
        String strCurrentDate = data.getKickoff();

        /**
         * populate data in viewholder
         */
        if (data.getScore() == null || data.getScore().equalsIgnoreCase("?-?") || data.getScore().equalsIgnoreCase("? - ?")) {
            holder.eplMatchweekAwayTeamScore.setText("");
            holder.eplMatchweekHomeTeamScore.setText("");
        } else {
            String score = data.getScore();
            int indexofDash = score.indexOf("-");
            String homeTeamScore = score.substring(0, indexofDash);
            String awayTeamScore = score.substring(indexofDash + 1, score.length());
            holder.eplMatchweekAwayTeamScore.setText(awayTeamScore);
            holder.eplMatchweekHomeTeamScore.setText(homeTeamScore);
        }
        holder.eplMatchweekTimeandHomeGround.setText(Util.commonDateFormatter(strCurrentDate, "yyyy-MM-dd'T'hh:mm:ss"));
        holder.eplMatchweekHomeTeam.setText(data.getHomeTeam());
        holder.eplMatchweekAwayTeam.setText(data.getAwayTeam());

        holder.matchResultRowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getFixtureId() != "null") {
                    mMatchweekItemClickListener.onEplMatchWeekClicked(data.getFixtureId());
                }
            }
        });


    }

    /**
     * ViewHolder class for fixture
     */

    static class FixtureViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.table_row_divider)
        View tableRowDivider;
        @BindView(R.id.eplMatchweekHomeTeam)
        TextView eplMatchweekHomeTeam;
        @BindView(R.id.eplMatchweekHomeTeamScore)
        TextView eplMatchweekHomeTeamScore;
        @BindView(R.id.eplMatchweekTimeandHomeGround)
        TextView eplMatchweekTimeandHomeGround;
        @BindView(R.id.eplMatchweekAwayTeamScore)
        TextView eplMatchweekAwayTeamScore;
        @BindView(R.id.eplMatchweekAwayTeam)
        TextView eplMatchweekAwayTeam;
        @BindView(R.id.matchResultRowLayout)
        LinearLayout matchResultRowLayout;

        FixtureViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
