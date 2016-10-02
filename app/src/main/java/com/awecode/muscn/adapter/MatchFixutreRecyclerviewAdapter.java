package com.awecode.muscn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.epl_matchweek_fixture_row_item, parent, false);
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
       if (data.getScore()==null||data.getScore().equalsIgnoreCase("?-?")||data.getScore().equalsIgnoreCase("? - ?")){

       }else {
           String score = data.getScore();
           int indexofDash = score.indexOf("-");
           String homeTeamScore = score.substring(0,indexofDash);
           String awayTeamScore = score.substring(indexofDash+1,score.length());
           holder.eplMatchweekAwayTeamScore.setText(awayTeamScore);
           holder.eplMatchweekHomeTeamScore.setText(homeTeamScore);
       }
        if (data.getMinute().equalsIgnoreCase("FT")){
            holder.eplMatchweekMatchStatus.setText("FullTime");
        }else
            holder.eplMatchweekMatchStatus.setText("PreMatch");
        holder.eplMatchweekTimeandHomeGround.setText(time+",  "+"HomeGround");
        holder.eplMatchweekHomeTeamShortName.setText(data.getHomeTeam().substring(0,3).toUpperCase());
        holder.eplMatchweekAwayTeamShortName.setText(data.getAwayTeam().substring(0,3).toUpperCase());



    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    /**
     * ViewHolder class for fixture
     */

    static class FixtureViewHolder extends RecyclerView.ViewHolder {
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

        FixtureViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
