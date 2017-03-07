package com.awecode.muscn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.fixtures.FixturesResponse;
import com.awecode.muscn.model.http.fixtures.Result;
import com.awecode.muscn.util.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by suresh on 10/2/16.
 */

public class FixturesRecyclerViewAdapter extends RecyclerView.Adapter<FixturesRecyclerViewAdapter.FixtureViewHolder> {

    private Context context;
    private FixturesResponse fixturesResponse;
    private List<Result> mDataList;

    public FixturesRecyclerViewAdapter(Context context, FixturesResponse fixturesResponse) {
        this.context = context;
        this.fixturesResponse = fixturesResponse;
        this.mDataList = fixturesResponse.getResults();
    }

    @Override
    public FixtureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixtures_row_item, null);
        FixtureViewHolder fixtureViewHolder = new FixtureViewHolder(view);
        return fixtureViewHolder;
    }

    Date newDate = null;
    Calendar mCalendar = null;

    @Override
    public void onBindViewHolder(FixtureViewHolder holder, int position) {

        try {
            Result result = mDataList.get(position);
            /**
             * Populate data for Home Game
             */
//            if (result.getHomeGame()) {
//                holder.eplMatchweekFixtureHomeTeamLogo.setImageResource(R.drawable.logo_manutd);
//                Picasso.with(context).load(result.getOpponent().getCrest())
//                        .into(holder.eplMatchweekFixtureAwayTeamLogo);
//
//                holder.eplMatchweekHomeTeamShortName.setText(R.string.manutd_shortname);
//                if (result.getOpponent().getShortName() == null || result.getOpponent().getShortName().isEmpty())
//                    holder.eplMatchweekAwayTeamShortName.setText(result.getOpponent().getName().substring(0, 3).toUpperCase());
//                else
//                    holder.eplMatchweekAwayTeamShortName.setText(result.getOpponent().getShortName().toUpperCase());
//                holder.eplMatchweekTimeandHomeGround.setText(Util.commonDateFormatter(result.getDatetime(), "yyyy-MM-dd'T'hh:mm:ss'Z'") + "\n" + result.getVenue());

            //new

            Log.v("ttt", "jhj" + result.getDatetime());
            mCalendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                newDate = format.parse(result.getDatetime());
                mCalendar.setTime(newDate);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

            holder.mDayTextView.setText(Util.getTwoDigitNumber(mCalendar.get(Calendar.DAY_OF_MONTH)));
            holder.mWeekTextView.setText(mCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
            holder.mMonthYearTextView.setText(month + " " +String.valueOf(mCalendar.get(Calendar.YEAR)));

            holder.mHomeVsAwayTeamTextView.setText(context.getString(R.string.manchester_united) + " vs " + result.getOpponent().getName() + " - " + result.getCompetitionYear().getCompetition().getName());
            holder.mStadiumTimeTextView.setText(result.getVenue() + " at " + String.valueOf(mCalendar.get(Calendar.HOUR))+":"+ String.valueOf(mCalendar.get(Calendar.MINUTE))+mCalendar.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault()));


//            }
//            else if (!result.getHomeGame()) {
//                holder.eplMatchweekFixtureAwayTeamLogo.setImageResource(R.drawable.logo_manutd);
//                Picasso.with(context)
//                        .load(result.getOpponent().getCrest())
//                        .into(holder.eplMatchweekFixtureHomeTeamLogo);
//                holder.eplMatchweekAwayTeamShortName.setText(R.string.manutd_shortname);
//                if (result.getOpponent().getShortName() == null || result.getOpponent().getShortName().isEmpty())
//                    holder.eplMatchweekHomeTeamShortName.setText(result.getOpponent().getName().substring(0, 3).toUpperCase());
//                else
//                    holder.eplMatchweekHomeTeamShortName.setText(result.getOpponent().getShortName().toUpperCase());
//
//
//                String venue = result.getVenue();
//                holder.eplMatchweekTimeandHomeGround.setText(Util.commonDateFormatter(result.getDatetime(), "yyyy-MM-dd'T'hh:mm:ss'Z'") + "\n" + venue);
//
//            }

//            holder.eplMatchweekMatchStatus.setText(result.getCompetitionYear().getCompetition().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class FixtureViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.table_row_divider)
        View tableRowDivider;
        //        @BindView(R.id.eplMatchweekFixtureHomeTeamLogo)
//        ImageView eplMatchweekFixtureHomeTeamLogo;
//        @BindView(R.id.eplMatchweekHomeTeamScore)
//        TextView eplMatchweekHomeTeamScore;
//        @BindView(R.id.eplMatchweekMatchStatus)
//        TextView eplMatchweekMatchStatus;
//        @BindView(R.id.eplMatchweekAwayTeamScore)
//        TextView eplMatchweekAwayTeamScore;
//        @BindView(R.id.eplMatchweekFixtureAwayTeamLogo)
//        ImageView eplMatchweekFixtureAwayTeamLogo;
//        @BindView(R.id.eplMatchweekHomeTeamShortName)
//        TextView eplMatchweekHomeTeamShortName;
//        @BindView(R.id.eplMatchweekTimeandHomeGround)
//        TextView eplMatchweekTimeandHomeGround;
//        @BindView(R.id.eplMatchweekAwayTeamShortName)
//        TextView eplMatchweekAwayTeamShortName;
        @BindView(R.id.matchResultRowLayout)
        LinearLayout matchResultRowLayout;

        @BindView(R.id.homeVsAwayTeamTextView)
        TextView mHomeVsAwayTeamTextView;
        @BindView(R.id.stadiumTimeTextView)
        TextView mStadiumTimeTextView;
        @BindView(R.id.dayTextView)
        TextView mDayTextView;
        @BindView(R.id.weekTextView)
        TextView mWeekTextView;
        @BindView(R.id.monthYearTextView)
        TextView mMonthYearTextView;


        public FixtureViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
