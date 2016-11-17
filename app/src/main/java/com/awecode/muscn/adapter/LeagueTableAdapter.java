package com.awecode.muscn.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.leaguetable.LeagueTableResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by surensth on 9/27/16.
 */

public class LeagueTableAdapter extends RecyclerView.Adapter<LeagueTableAdapter.LeagueViewHolder> {
    Context mContext;
    List<LeagueTableResponse> leagueList;
    String serialNumber;
    String club, p, gd, pts;

    public LeagueTableAdapter(Context mContext, List<LeagueTableResponse> leagueList) {
        this.mContext = mContext;
        this.leagueList = leagueList;
    }

    @Override
    public LeagueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.league_table_row, parent, false);
        return new LeagueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LeagueViewHolder holder, int position) {
        final LeagueTableResponse leagueTableResponse = leagueList.get(position);
        if (leagueTableResponse != null) {
            serialNumber = leagueTableResponse.getPosition();
            club = leagueTableResponse.getName().toString();
            p = leagueTableResponse.getP();
            gd = leagueTableResponse.getGd();
            pts = leagueTableResponse.getPts();

            if (club.equalsIgnoreCase("Manchester United")) {
                holder.rowLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
//                holder.club.setTypeface(null, Typeface.BOLD);
            } else {
                holder.rowLayout.setBackgroundColor(Color.TRANSPARENT);
//                holder.club.setTypeface(null, Typeface.NORMAL);
            }


//       White divider line is hide if the table item is last
            if (position == getItemCount() - 1)
                holder.mTableRowDividerView.setVisibility(View.GONE);
            else
                holder.mTableRowDividerView.setVisibility(View.VISIBLE);

            if (serialNumber != null)
                holder.serialNumber.setText(serialNumber);
            else
                holder.serialNumber.setText(R.string.no_data_string);

            if (club != null)
                holder.club.setText(club);
            else
                holder.club.setText(R.string.no_data_string);

            if (p != null)
                holder.p.setText(p);
            else
                holder.p.setText(R.string.no_data_string);

            if (gd != null)
                holder.gd.setText(gd);
            else
                holder.gd.setText(R.string.no_data_string);

            if (pts != null)
                holder.pts.setText(pts);
            else
                holder.pts.setText(R.string.no_data_string);
        } else
            return;

    }

    @Override
    public int getItemCount() {
        return leagueList.size();
    }

    public static class LeagueViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.serialNumber)
        TextView serialNumber;
        @BindView(R.id.club)
        TextView club;
        @BindView(R.id.p)
        TextView p;
        @BindView(R.id.gd)
        TextView gd;
        @BindView(R.id.pts)
        TextView pts;
        @BindView(R.id.table_row_divider)
        View mTableRowDividerView;
        @BindView(R.id.rowLayout)
        LinearLayout rowLayout;


        public LeagueViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
