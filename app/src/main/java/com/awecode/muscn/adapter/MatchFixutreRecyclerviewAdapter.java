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

    public MatchFixutreRecyclerviewAdapter(Context context, ArrayList<_20161001> mCategoryList) {
        this.context = context;
        this.mCategoryList = mCategoryList;
    }

    @Override
    public FixtureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.epl_matchweek_row_iten_new, parent, false);
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
        format = new SimpleDateFormat("dd MMM, yyyy\nhh:mm a");
        String time = format.format(newDate);

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
        holder.eplMatchweekTimeandHomeGround.setText(time);
        holder.eplMatchweekHomeTeam.setText(data.getHomeTeam());
        holder.eplMatchweekAwayTeam.setText(data.getAwayTeam());


    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
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
