package com.awecode.muscn.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.resultdetails.Event;
import com.awecode.muscn.model.http.resultdetails.ResultDetailsResponse;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.retrofit.ServiceGenerator;
import com.github.vipulasri.timelineview.TimelineView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by surensth on 3/16/17.
 */

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {

    private ResultDetailsResponse mResponse;
    private Context mContext;
    //    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;

    public TimeLineAdapter(ResultDetailsResponse response/*, Orientation orientation*//*, boolean withLinePadding*/) {
        mResponse = response;
//        mOrientation = orientation;
//        mWithLinePadding = withLinePadding;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public TimeLineAdapter.TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view = mLayoutInflater.inflate(/*mWithLinePadding ? R.layout.item_timeline_line_padding :*/ R.layout.goal_timeline_row_layout, parent, false);
        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {

        Event event = mResponse.getData().getEvents().get(position);

        holder.timelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_marker), ContextCompat.getColor(mContext, R.color.colorPrimary));
        if (event.getM() != null)
            holder.time.setText(event.getM() + "'  ");
        if (event.getScorer() != null)
            holder.scorer.setText(event.getScorer());
        if (event.getAssistBy() != null) {
            holder.assistedBy.setVisibility(View.VISIBLE);
            holder.assistedBy.setText("[Assist: " + event.getAssistBy() + "]");
        } else
            holder.assistedBy.setVisibility(View.GONE);

        String playerTeam = Util.getPlayerTeamName(mResponse.getIsHomeGame(), event.getTeam());
        if (playerTeam.equalsIgnoreCase("manchester united")) {
            holder.team.setText("Manchester United");
            Picasso.with(mContext).load(R.drawable.logo_manutd).into(holder.scorerTeamLogo);

        } else {
            holder.team.setText(mResponse.getOpponentName());
            Picasso.with(mContext).load(ServiceGenerator.API_BASE_URL + mResponse.getOpponentCrest()).into(holder.scorerTeamLogo);
        }


    }

    @Override
    public int getItemCount() {
        return (mResponse.getData().getEvents() != null ? mResponse.getData().getEvents().size() : 0);
    }


    public static class TimeLineViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.timelineView)
        TimelineView timelineView;
        @BindView(R.id.time)
        AppCompatTextView time;
        @BindView(R.id.scorer)
        AppCompatTextView scorer;
        @BindView(R.id.assistedBy)
        AppCompatTextView assistedBy;
        @BindView(R.id.team)
        AppCompatTextView team;
        @BindView(R.id.scorerTeamLogo)
        ImageView scorerTeamLogo;

        public TimeLineViewHolder(View itemView, int viewType) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            timelineView.initLine(viewType);
        }
    }
}