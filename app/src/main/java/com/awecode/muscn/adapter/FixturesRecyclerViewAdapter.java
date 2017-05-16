package com.awecode.muscn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awecode.muscn.R;
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
    private List<Result> mDataList;

    public FixturesRecyclerViewAdapter(Context context, List<Result> results) {
        this.context = context;
        this.mDataList = results;
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
            holder.mMonthYearTextView.setText(month + " " + String.valueOf(mCalendar.get(Calendar.YEAR)));

            holder.mStadiumTimeTextView.setText(result.getVenue() + " at " + String.valueOf(mCalendar.get(Calendar.HOUR)) + ":" + String.valueOf(mCalendar.get(Calendar.MINUTE)) + mCalendar.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault()));

            if (result.getIsHomeGame())
                holder.mHomeVsAwayTeamTextView.setText(context.getString(R.string.manchester_united) + " vs " + result.getOpponent().getName() + " - " + result.getCompetitionYear().getCompetition().getName());
            else if (!result.getIsHomeGame())
                holder.mHomeVsAwayTeamTextView.setText(result.getOpponent().getName() + " vs " + context.getString(R.string.manchester_united) + " - " + result.getCompetitionYear().getCompetition().getName());

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
