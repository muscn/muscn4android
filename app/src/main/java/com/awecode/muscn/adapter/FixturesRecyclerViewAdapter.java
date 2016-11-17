package com.awecode.muscn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.fixtures.Competition;
import com.awecode.muscn.model.http.fixtures.FixturesResponse;
import com.awecode.muscn.model.http.fixtures.Result;
import com.awecode.muscn.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by suresh on 10/2/16.
 */

public class FixturesRecyclerViewAdapter extends RecyclerView.Adapter<FixturesRecyclerViewAdapter.FixtureViewHolder> {

    private Context context;
    private FixturesResponse fixturesResponse;

    public FixturesRecyclerViewAdapter(Context context, FixturesResponse fixturesResponse) {
        this.context = context;
        this.fixturesResponse = fixturesResponse;
    }

    @Override
    public FixtureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.epl_matchweek_fixture_row_item, null);
        FixtureViewHolder fixtureViewHolder = new FixtureViewHolder(view);
        return fixtureViewHolder;
    }

    @Override
    public void onBindViewHolder(FixtureViewHolder holder, int position) {
        Result result = fixturesResponse.getResults().get(position);
        /**
         * Populate data for Home Game
         */
        if (result.getIsHomeGame()){
            holder.eplMatchweekFixtureHomeTeamLogo.setImageResource(R.drawable.logo_manutd);
            Picasso.with(context).load(result.getOpponent().getCrest()).into(holder.eplMatchweekFixtureAwayTeamLogo);
            holder.eplMatchweekHomeTeamShortName.setText(R.string.manutd_shortname);
            if (result.getOpponent().getShortName()==null||result.getOpponent().getShortName().isEmpty())
                holder.eplMatchweekAwayTeamShortName.setText(result.getOpponent().getName().substring(0,3).toUpperCase());
            else
                holder.eplMatchweekAwayTeamShortName.setText(result.getOpponent().getShortName().toUpperCase());
            holder.eplMatchweekTimeandHomeGround.setText(Util.dateFormatter(result.getDatetime(),"yyyy-MM-dd'T'hh:mm:ss'Z'","hh:mm a")+", "+context.getString(R.string.manutd_home_stadium));
        }
        /**
         * Populate data for Away Game
         */
        else {
            holder.eplMatchweekFixtureAwayTeamLogo.setImageResource(R.drawable.logo_manutd);
            Picasso.with(context).load(result.getOpponent().getCrest()).into(holder.eplMatchweekFixtureHomeTeamLogo);
            holder.eplMatchweekAwayTeamShortName.setText(R.string.manutd_shortname);
            if (result.getOpponent().getShortName()==null||result.getOpponent().getShortName().isEmpty())
                holder.eplMatchweekHomeTeamShortName.setText(result.getOpponent().getName().substring(0,3).toUpperCase());
            else
                holder.eplMatchweekHomeTeamShortName.setText(result.getOpponent().getShortName().toUpperCase());
            holder.eplMatchweekTimeandHomeGround.setText(Util.dateFormatter(result.getDatetime(),"yyyy-MM-dd'T'hh:mm:ss'Z'","hh:mm")+",  "+result.getVenue().substring(0,result.getVenue().indexOf(",")));
        }

        holder.eplMatchweekMatchStatus.setText(result.getCompetitionYear().getCompetition().getName());
    }

    @Override
    public int getItemCount() {
        return fixturesResponse.getResults().size();
    }

    public static class FixtureViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.table_row_divider)
        View tableRowDivider;
        @BindView(R.id.eplMatchweekFixtureHomeTeamLogo)
        ImageView eplMatchweekFixtureHomeTeamLogo;
        @BindView(R.id.eplMatchweekHomeTeamScore)
        TextView eplMatchweekHomeTeamScore;
        @BindView(R.id.eplMatchweekMatchStatus)
        TextView eplMatchweekMatchStatus;
        @BindView(R.id.eplMatchweekAwayTeamScore)
        TextView eplMatchweekAwayTeamScore;
        @BindView(R.id.eplMatchweekFixtureAwayTeamLogo)
        ImageView eplMatchweekFixtureAwayTeamLogo;
        @BindView(R.id.eplMatchweekHomeTeamShortName)
        TextView eplMatchweekHomeTeamShortName;
        @BindView(R.id.eplMatchweekTimeandHomeGround)
        TextView eplMatchweekTimeandHomeGround;
        @BindView(R.id.eplMatchweekAwayTeamShortName)
        TextView eplMatchweekAwayTeamShortName;
        @BindView(R.id.matchResultRowLayout)
        LinearLayout matchResultRowLayout;

        public FixtureViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
